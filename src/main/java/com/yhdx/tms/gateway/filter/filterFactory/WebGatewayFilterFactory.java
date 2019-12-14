package com.yhdx.tms.gateway.filter.filterFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * Created by yj on 2019/12/14.
 **/
@Component
public class WebGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {


    private static final Logger logger = LoggerFactory.getLogger(WebGatewayFilterFactory.class);

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            logger.info("web gateway filter factory ");
            return chain.filter(exchange);

        };
    }
}
