package io.kimmking.rpcfx.client;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.api.Filter;
import io.kimmking.rpcfx.api.LoadBalancer;
import io.kimmking.rpcfx.api.Router;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }

    public static <T, filters> T createFromRegistry(final Class<T> serviceClass, final String zkUrl, Router router, LoadBalancer loadBalance, Filter filter) throws URISyntaxException {

        // ???filte??????

        // curator Provider list from zk
        List<String> invokers = new ArrayList<>();
        // 1. ????????????zk???????????????????????????
        // 2. ???????????????zk??????????????????????????????????????????list??????????????????????????????map??????????????????????????????List???

        List<String> urls = router.route(invokers);

        String url = loadBalance.select(urls); // router, loadbalance

        return (T) create(serviceClass, url, filter);

    }

    public static <T> T create(final Class<T> serviceClass, final String url, Filter... filters) throws URISyntaxException {

        // 0. ?????????????????? -> ???????????????
        // ???????????????https://www.jianshu.com/p/e983ecf3e7a5
        // ???????????????https://tech.meituan.com/2019/09/05/java-bytecode-enhancement.html
//        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url, filters));

        // TODO ????????????
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(serviceClass);
        enhancer.setCallback(new RpcNettyHttpIntercept(serviceClass, url, filters));
//        enhancer.setCallback(new RpcIntercept(serviceClass, url, filters));
        return (T) enhancer.create();
    }


    public static class RpcNettyHttpIntercept implements MethodInterceptor {
        private final Class<?> serviceClass;
        private final String url;
        private final Filter[] filters;

        private final URI uri  ;

        public <T> RpcNettyHttpIntercept(Class<T> serviceClass, String url, Filter[] filters) throws URISyntaxException {
            this.serviceClass = serviceClass;
            this.url = url;
            this.filters = filters;
            uri = new URI(url);
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            // https://dev-tang.com/post/2020/06/httpclient-netty.html
            NioEventLoopGroup workerGroup = new NioEventLoopGroup();
            final OutputResultHandler handler = new OutputResultHandler();
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(workerGroup).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel sc) throws Exception {
                                // HttpClientCodec ????????????????????????HttpResponseDecoder???HttpRequestEncoder
                                sc.pipeline().addLast(new HttpClientCodec());
                                sc.pipeline().addLast(new HttpObjectAggregator(512 * 1024));
                                sc.pipeline().addLast(handler);
                            }
                        });
                Channel channel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();

                RpcfxRequest rpcfxRequest = new RpcfxRequest();
                rpcfxRequest.setServiceClass(this.serviceClass.getName());
                rpcfxRequest.setMethod(method.getName());
                rpcfxRequest.setParams(objects);

                if (null!=filters) {
                    for (Filter filter : filters) {
                        if (!filter.filter(rpcfxRequest)) {
                            return null;
                        }
                    }
                }


                String reqJson = JSON.toJSONString(rpcfxRequest);
                HttpEntity httpEntity = new ByteArrayEntity(reqJson.getBytes(StandardCharsets.UTF_8));
                // ???????????? body ?????????????????????formdata?????????
                ByteBuf byteBuf = Unpooled.wrappedBuffer(EntityUtils.toByteArray(httpEntity));
                DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri.getPath(), byteBuf);

                request.headers().set(HttpHeaders.Names.CONTENT_TYPE, HttpHeaders.Values.APPLICATION_JSON);
                request.headers().set(HttpHeaders.Names.HOST, uri.getHost());
                request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());

                channel.writeAndFlush(request).sync();
                channel.closeFuture().sync();
                return handler.getResult();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
            }
            return null;
        }
    }

    private static class OutputResultHandler extends SimpleChannelInboundHandler<FullHttpResponse> {
        String result = null;

        public String getResult() {
            return result;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpResponse fullHttpResponse) throws Exception {
            ByteBuf buf = fullHttpResponse.content();

            result = buf.toString(CharsetUtil.UTF_8);
            System.out.println(result);

        }
    }

    public static class RpcIntercept implements MethodInterceptor {

        public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

        private final Class<?> serviceClass;
        private final String url;
        private final Filter[] filters;

        public <T> RpcIntercept(Class<T> serviceClass, String url, Filter[] filters) {
            this.serviceClass = serviceClass;
            this.url = url;
            this.filters = filters;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            // ???filter????????????
            // mock == true, new Student("hubao");

            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(this.serviceClass.getName());
            request.setMethod(method.getName());
            request.setParams(objects);

            if (null!=filters) {
                for (Filter filter : filters) {
                    if (!filter.filter(request)) {
                        return null;
                    }
                }
            }

            RpcfxResponse response = post(request, url);

            // ???filter????????????
            // Student.setTeacher("cuijing");

            // ????????????response.status???????????????
            // ???????????????????????????RpcfxException

            return JSON.parse(response.getResult().toString());
        }

        private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
            String reqJson = JSON.toJSONString(req);
            System.out.println("req json: "+reqJson);

            // 1.????????????client
            // 2.????????????httpclient??????netty client
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSONTYPE, reqJson))
                    .build();
            String respJson = client.newCall(request).execute().body().string();
            System.out.println("resp json: "+respJson);
            return JSON.parseObject(respJson, RpcfxResponse.class);
        }
    }

    public static class RpcfxInvocationHandler implements InvocationHandler {

        public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

        private final Class<?> serviceClass;
        private final String url;
        private final Filter[] filters;

        public <T> RpcfxInvocationHandler(Class<T> serviceClass, String url, Filter... filters) {
            this.serviceClass = serviceClass;
            this.url = url;
            this.filters = filters;
        }

        // ??????????????????????????????????????????????????????????????????????????????rpcfx???xml????????????????????????????????????json: code.google.com/p/rpcfx
        // int byte char float double long bool
        // [], data class

        @Override
        public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {

            // ???filter????????????
            // mock == true, new Student("hubao");

            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(this.serviceClass.getName());
            request.setMethod(method.getName());
            request.setParams(params);

            if (null!=filters) {
                for (Filter filter : filters) {
                    if (!filter.filter(request)) {
                        return null;
                    }
                }
            }

            RpcfxResponse response = post(request, url);

            // ???filter????????????
            // Student.setTeacher("cuijing");

            // ????????????response.status???????????????
            // ???????????????????????????RpcfxException

            return JSON.parse(response.getResult().toString());
        }

        private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
            String reqJson = JSON.toJSONString(req);
            System.out.println("req json: "+reqJson);

            // 1.????????????client
            // 2.????????????httpclient??????netty client
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSONTYPE, reqJson))
                    .build();
            String respJson = client.newCall(request).execute().body().string();
            System.out.println("resp json: "+respJson);
            return JSON.parseObject(respJson, RpcfxResponse.class);
        }
    }
}
