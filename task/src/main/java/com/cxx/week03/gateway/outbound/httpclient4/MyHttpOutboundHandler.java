package com.cxx.week03.gateway.outbound.httpclient4;

import com.cxx.week03.gateway.filter.HeaderHttpResponseFilter;
import com.cxx.week03.gateway.filter.HttpRequestFilter;
import com.cxx.week03.gateway.filter.HttpResponseFilter;
import com.cxx.week03.gateway.router.HttpEndpointRouter;
import com.cxx.week03.gateway.router.RandomHttpEndpointRouter;
import com.cxx.week03.gateway.utils.UrlUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 试用httpclient 同步处理内容
 */
public class MyHttpOutboundHandler {

    private List<String> backendUrls;

    HttpResponseFilter filter = new HeaderHttpResponseFilter();
    HttpEndpointRouter router = new RandomHttpEndpointRouter();

    /**
     *
     * @param backends 连接节点
     */
    public MyHttpOutboundHandler(List<String>backends) {
        this.backendUrls = backends.stream().map(UrlUtils::formatUrl).collect(Collectors.toList());
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) {
        String backendUrl = router.route(this.backendUrls);
        final String url = backendUrl + fullRequest.uri();
        try {
            filter.filter(fullRequest, ctx);
        } catch (Exception e) {

        }
    }
}
