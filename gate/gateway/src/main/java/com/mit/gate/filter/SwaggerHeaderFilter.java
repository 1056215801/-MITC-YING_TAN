package com.mit.gate.filter;

import com.mit.gate.config.SwaggerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;

/**
 * swagger过滤器
 *
 * @author shuyy
 * @date 2018/11/7 16:22
 * @company mitesofor
 */
@Configuration
@Slf4j
public class SwaggerHeaderFilter implements GlobalFilter {
    private static final String HEADER_NAME = "X-Forwarded-Prefix";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if (!StringUtils.endsWithIgnoreCase(path, SwaggerProvider.API_URI)) {
            return chain.filter(exchange);
        }
        LinkedHashSet requiredAttribute = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        String s = requiredAttribute.toString();
//        String basePath = path.substring(0, path.lastIndexOf(SwaggerProvider.API_URI));
        String basePath = this.extractString(s);
//        String basePath = "/api/admin/";
        ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
        return chain.filter(newExchange);
    }

    private String extractString(String s) {
        int max = 3;
        for (int i = 0; i < max; i++) {
            s = s.substring(s.indexOf("/") + 1);
        }
        s = "/" + s.substring(0, s.indexOf("/v2/api-docs"));
        return s;
    }
}
