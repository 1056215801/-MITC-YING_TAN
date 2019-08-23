package com.mit.community.config.interceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class URLInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse, Object paramObject,
			Exception paramException) throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse, Object paramObject,
			ModelAndView paramModelAndView) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURI();
		System.out.println("===被拦截的请求："+url);

		if(url.indexOf("upload") == -1){
			return true;
		}
		String token = url.substring(url.indexOf("upload")+7);
		request.getRequestDispatcher("/download?lingPai="+token)
				.forward(request, response);
		return false;
	}
}
