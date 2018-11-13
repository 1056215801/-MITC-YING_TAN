package com.mit.gate.filter;

import com.alibaba.fastjson.JSONObject;
import com.mit.api.vo.authority.PermissionInfo;
import com.mit.api.vo.log.LogInfo;
import com.mit.auth.client.config.ServiceAuthConfig;
import com.mit.auth.client.config.UserAuthConfig;
import com.mit.auth.client.jwt.ServiceAuthUtil;
import com.mit.auth.client.jwt.UserAuthUtil;
import com.mit.auth.common.util.jwt.IJWTInfo;
import com.mit.common.context.BaseContextHandler;
import com.mit.common.msg.BaseResponse;
import com.mit.common.msg.auth.TokenForbiddenResponse;
import com.mit.gate.feign.ILogService;
import com.mit.gate.feign.IUserService;
import com.mit.gate.utils.DBLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * gateway filter
 *
 * @author shuyy
 * @date 2018/11/7 11:34
 * @company mitesofor
 */
@Configuration
@Slf4j
public class AccessGatewayFilter implements GlobalFilter {

    @Autowired
    @Lazy
    private IUserService userService;
    @Autowired
    @Lazy
    private ILogService logService;

    @Value("${gate.ignore.startWith}")
    private String startWith;

    private static final String GATE_WAY_PREFIX = "/api";

    @Autowired
    private UserAuthUtil userAuthUtil;

    @Autowired
    private ServiceAuthConfig serviceAuthConfig;

    @Autowired
    private UserAuthConfig userAuthConfig;

    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain gatewayFilterChain) {
        log.info("check token and user permission....");
        LinkedHashSet requiredAttribute = serverWebExchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        ServerHttpRequest request = serverWebExchange.getRequest();
        String requestUri = request.getPath().pathWithinApplication().value();
        for (Object aRequiredAttribute : requiredAttribute) {
            URI next = (URI) aRequiredAttribute;
            if (next.getPath().startsWith(GATE_WAY_PREFIX)) {
                requestUri = next.getPath().substring(GATE_WAY_PREFIX.length());
            }
        }
        final String method = Objects.requireNonNull(request.getMethod()).toString();
        BaseContextHandler.setToken(null);
        // 为了获得jwtUser
        ServerHttpRequest.Builder mutate = request.mutate();
        // 不进行拦截的地址
        if (isStartWith(requestUri)) {
            ServerHttpRequest build = mutate.build();
            return gatewayFilterChain.filter(serverWebExchange.mutate().request(build).build());
        }
        IJWTInfo user;
        try {
            user = getJWTUser(request, mutate);
        } catch (Exception e) {
            log.error("用户Token过期异常", e);
            return getVoidMono(serverWebExchange, new TokenForbiddenResponse("User Token Forbidden or Expired!"));
        }
        // 返回所有权限
        List<PermissionInfo> permissionIfs = userService.getAllPermissionInfo();
        // 判断资源是否启用权限约束
        Stream<PermissionInfo> stream = getPermissionIfs(requestUri, method, permissionIfs);
        List<PermissionInfo> result = stream.collect(Collectors.toList());
        PermissionInfo[] permissions = result.toArray(new PermissionInfo[]{});
        if (permissions.length > 0) {
            //检查权限
            if (checkUserPermission(permissions, serverWebExchange, user)) {
                //没权限则禁止访问
                return getVoidMono(serverWebExchange, new TokenForbiddenResponse("User Forbidden!Does not has Permission!"));
            }
        }
        // 申请客户端密钥头，通过私钥进行加密，公钥进行解密
        mutate.header(serviceAuthConfig.getTokenHeader(), serviceAuthUtil.getClientToken());
        ServerHttpRequest build = mutate.build();
        return gatewayFilterChain.filter(serverWebExchange.mutate().request(build).build());

    }

    /**
     * 网关抛异常
     *
     * @param serverWebExchange serverWebExchange
     * @param body              body
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * @author shuyy
     * @date 2018/11/7 11:50
     * @company mitesofor
     */
    @NotNull
    private Mono<Void> getVoidMono(ServerWebExchange serverWebExchange, BaseResponse body) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
        byte[] bytes = JSONObject.toJSONString(body).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = serverWebExchange.getResponse().bufferFactory().wrap(bytes);
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

    /**
     * 获取目标权限资源
     *
     * @param requestUri  requestUri
     * @param method      method
     * @param serviceInfo serviceInfo
     * @return java.util.stream.Stream<PermissionInfo>
     * @author shuyy
     * @date 2018/11/7 11:50
     * @company mitesofor
     */
    private Stream<PermissionInfo> getPermissionIfs(final String requestUri, final String method, List<PermissionInfo> serviceInfo) {
        return serviceInfo.parallelStream().filter(permissionInfo -> {
            String uri = permissionInfo.getUri();
            String kh = "{";
            if (uri.indexOf(kh) > 0) {
                uri = uri.replaceAll("\\{\\*}", "[a-zA-Z\\\\d]+");
            }
            String regEx = "^" + uri + "$";
            return (Pattern.compile(regEx).matcher(requestUri).find())
                    && method.equals(permissionInfo.getMethod());
        });
    }

    private void setCurrentUserInfoAndLog(ServerWebExchange serverWebExchange, IJWTInfo user, PermissionInfo pm) {
        String host = Objects.requireNonNull(serverWebExchange.getRequest().getRemoteAddress()).toString();
        LogInfo logInfo = new LogInfo(pm.getMenu(), pm.getName(), pm.getUri(), new Date(), user.getId(), user.getName(), host);
        DBLog.getInstance().setLogService(logService).offerQueue(logInfo);
    }

    /**
     * 返回session中的用户信息
     *
     * @param request request
     * @param ctx     ctx
     * @return com.mit.auth.common.util.jwt.IJWTInfo
     * @throws Exception 异常
     * @author shuyy
     * @date 2018/11/7 11:55
     * @company mitesofor
     */
    private IJWTInfo getJWTUser(ServerHttpRequest request, ServerHttpRequest.Builder ctx) throws Exception {
        // 获得token
        List<String> strings = request.getHeaders().get(userAuthConfig.getTokenHeader());
        String authToken = null;
        if (strings != null) {
            authToken = strings.get(0);
        }
        if (StringUtils.isBlank(authToken)) {
            strings = request.getQueryParams().get("token");
            if (strings != null) {
                authToken = strings.get(0);
            }
            if (StringUtils.isBlank(authToken)) {
                authToken = request.getCookies().get("Admin-Token").toString().split("=")[1];
                authToken = authToken.substring(0, authToken.length() - 1);
            }
        }
        // 把request头重新加入token
        ctx.header(userAuthConfig.getTokenHeader(), authToken);
        // 把token放入threadLocal
        BaseContextHandler.setToken(authToken);
        // 解析token，获取user信息，通过公钥
        return userAuthUtil.getInfoFromToken(authToken);
    }

    private boolean checkUserPermission(PermissionInfo[] permissions, ServerWebExchange ctx, IJWTInfo user) {
        List<PermissionInfo> permissionInfos = userService.getPermissionByUsername(user.getUniqueName());
        PermissionInfo current = null;
        for (PermissionInfo info : permissions) {
            boolean anyMatch = permissionInfos.parallelStream().anyMatch(permissionInfo -> permissionInfo.getCode().equals(info.getCode()));
            if (anyMatch) {
                current = info;
                break;
            }
        }
        if (current == null) {
            return true;
        } else {
            if (!RequestMethod.GET.toString().equals(current.getMethod())) {
                // 往堵塞队列中推送日志
                setCurrentUserInfoAndLog(ctx, user, current);
            }
            return false;
        }
    }

    /**
     * URI是否以什么打头
     *
     * @param requestUri uri
     * @return boolean
     * @author shuyy
     * @date 2018/11/7 11:57
     * @company mitesofor
     */
    private boolean isStartWith(String requestUri) {
        String d = ",";
        for (String s : startWith.split(d)) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return false;
    }

}
