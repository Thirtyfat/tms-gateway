package com.yhdx.tms.gateway.filter.filterFactory;

import com.yhdx.tms.gateway.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * Created by yj on 2019/12/14.
 **/
@Component
public class WmsTokenGatewayFilterFactory  extends AbstractGatewayFilterFactory<Object> {

    private static final Logger logger = LoggerFactory.getLogger(WmsTokenGatewayFilterFactory.class);

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String token = TokenUtils.getWmsToken();
            ServerHttpRequest host = exchange.getRequest().mutate().headers(httpHeaders -> {
                httpHeaders.remove("token");
                httpHeaders.add("token", token);
            }).build();
            //将现在的request 变成 change对象
            logger.info("wms gateway filter factory host {}, token {} " ,host,token);
            ServerWebExchange build = exchange.mutate().request(host).build();
            return chain.filter(build);
        };
    }
}
