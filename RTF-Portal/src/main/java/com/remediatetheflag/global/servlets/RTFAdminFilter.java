package com.remediatetheflag.global.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.remediatetheflag.global.utils.Constants;

public class RTFAdminFilter implements Filter {

	public RTFAdminFilter() {}

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		Integer role = (Integer) req.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_ROLE);
		if(null==role || role != Constants.ROLE_RTF_ADMIN)
			res.sendRedirect(Constants.INDEX_PAGE);
		else
			chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {}

}
