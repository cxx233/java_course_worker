package com.cxx.week03.gateway.outbound.httpclient4;

import com.cxx.week03.gateway.filter.HeaderHttpResponseFilter;
import com.cxx.week03.gateway.filter.HttpRequestFilter;
import com.cxx.week03.gateway.filter.HttpResponseFilter;
import com.cxx.week03.gateway.router.HttpEndpointRouter;
import com.cxx.week03.gateway.router.RandomHttpEndpointRouter;
import com.cxx.week03.gateway.utils.UrlUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.List;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 试用httpclient 同步处理内容
 * @author cxx
 */
public class MyHttpOutboundHandler {

    private List<String> backendUrls;

    HttpResponseFilter responseFilter = new HeaderHttpResponseFilter();
    HttpEndpointRouter router = new RandomHttpEndpointRouter();

    /**
     *
     * @param backends 连接节点
     */
    public MyHttpOutboundHandler(List<String>backends) {
        this.backendUrls = backends.stream().map(UrlUtils::formatUrl).collect(Collectors.toList());
    }

    /**
     * 整合httpclient 情况
     * @param fullRequest
     * @param ctx
     * @param filter
     */
    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) {
        String backendUrl = router.route(this.backendUrls);
        final String url = backendUrl + fullRequest.uri();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        FullHttpResponse returnResponse = null;
        try {
            filter.filter(fullRequest, ctx);
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            byte[] body = EntityUtils.toByteArray(responseEntity);

            returnResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));

            returnResponse.headers().set("Content-Type", "application/json");
            returnResponse.headers().setInt("Content-Length", body.length);
            // 这里过滤器进行处理结果
            responseFilter.filter(returnResponse);
        } catch (Exception e) {
            e.printStackTrace();
            returnResponse = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if ( !HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(returnResponse).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(returnResponse);
                }
            }
            ctx.flush();
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
