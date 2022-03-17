package com.cxx.week03.gateway.filter;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

/**
 * 代理过滤器
 * TODO 这个filter 功能挂在netty 下的pipeline 处理内容
 * @author cxx
 */
public class ProxyBizFilter implements HttpRequestFilter{
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String uri = fullRequest.uri();
        System.out.println(" filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx)接收到的请求,url: " + uri);
        if (uri.startsWith("/api")) {
            // 放过
        } else {
            throw new RuntimeException("不支持uri:" + uri);
        }
        HttpHeaders headers = fullRequest.headers();
        if (headers == null) {
            headers = new DefaultHttpHeaders();
        }
        // 打上自己的印记
        headers.add("proxy-tag", this.getClass().getSimpleName());
    }
}
