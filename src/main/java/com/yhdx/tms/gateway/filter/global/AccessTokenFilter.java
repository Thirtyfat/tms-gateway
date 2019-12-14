package com.yhdx.tms.gateway.filter.global;

import com.alibaba.fastjson.JSONObject;
import com.yhdx.baseframework.common.lang.Result;
import com.yhdx.tms.gateway.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created by yj on 2019/12/14.
 **/
@Component
public class AccessTokenFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenFilter.class);

    private static final String WMS_CONTENT = "wms";
    private static final String SCHEDULE_CONTENT = "schedule";


    /**
     * Process the Web request and (optionally) delegate to the next
     * {@code WebFilter} through the given {@link GatewayFilterChain}.
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Void>} to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("token");
        if (!StringUtils.isEmpty(token)) {
            if (WMS_CONTENT.equals(token)) {
                token = TokenUtils.getWmsToken();
            }
            if (SCHEDULE_CONTENT.equals(token)) {
                String realServerName = exchange.getRequest().getHeaders().getFirst("realServerName");
                if (!StringUtils.hasText(realServerName)){
                    token = TokenUtils.getSchedualToken();
                }
            }
            logger.info("access token filter {}" , token);
            String userToken = token;
            ServerHttpRequest host = exchange.getRequest().mutate().headers(httpHeaders -> {
                httpHeaders.remove("token");
                httpHeaders.add("token", userToken);
            }).build();
            ServerWebExchange build = exchange.mutate().request(host).build();
            return chain.filter(build);
        } else {
            String gateWayAuth = exchange.getRequest().getHeaders().getFirst("gate_way_auth");
            if (!StringUtils.isEmpty(gateWayAuth)) {
                return chain.filter(exchange);
            }
        }
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //设置headers
        HttpHeaders httpHeaders = response.getHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        //设置body
        Result<String> result = Result.errorWithMsg("UNAUTHORIZED"," 无相关授权");
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(JSONObject.toJSONString(result).getBytes());
        return response.writeWith(Mono.just(bodyDataBuffer));
    }
}
