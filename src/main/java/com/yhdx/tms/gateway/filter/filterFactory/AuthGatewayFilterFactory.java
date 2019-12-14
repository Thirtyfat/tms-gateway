package com.yhdx.tms.gateway.filter.filterFactory;

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
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private static final Logger logger = LoggerFactory.getLogger(AuthGatewayFilterFactory.class);

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest host = exchange.getRequest().mutate().headers(httpHeaders -> {
                httpHeaders.remove("gate_way_auth");
                httpHeaders.add("gate_way_auth", "yes");
            }).build();
            logger.info("auth gateway filter factory host {}" , host);
            //将现在的request 变成 change对象
            ServerWebExchange build = exchange.mutate().request(host).build();
            return chain.filter(build);
        };
    }
}
