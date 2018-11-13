package com.mit.auth.interceptor;

import com.mit.auth.common.util.jwt.IJWTInfo;
import com.mit.auth.configuration.ClientConfiguration;
import com.mit.auth.service.AuthClientService;
import com.mit.auth.util.client.ClientTokenUtil;
import com.mit.common.exception.auth.ClientForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* 服务调用权限拦截器
* @author: shuyy
* @date: 2018/11/6 17:19
* @company mitesofor
*/
public class ServiceAuthRestInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private ClientTokenUtil clientTokenUtil;
    @Autowired
    private AuthClientService authClientService;
    @Autowired
    private ClientConfiguration clientConfiguration;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String token = request.getHeader(clientConfiguration.getClientTokenHeader());
        IJWTInfo infoFromToken = clientTokenUtil.getInfoFromToken(token);
        String uniqueName = infoFromToken.getUniqueName();
        for(String client: authClientService.getAllowedClient(clientConfiguration.getClientId())){
            if(client.equals(uniqueName)){
                return super.preHandle(request, response, handler);
            }
        }
        throw new ClientForbiddenException("Client is Forbidden!");
    }
}
