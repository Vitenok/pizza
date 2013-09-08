package com.iti.pizza.controller.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter(filterName="LoginFilter", dispatcherTypes = { DispatcherType.REQUEST }, servletNames = {"LoginController"} , urlPatterns={"/admin/*", "/admin/templatesManagement.html", "/admin/ingredientsManagement.html", "/admin/tagsManagement.html", "/admin/statistics.html"})
public class LoginFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();

		Integer adminId = (Integer) session.getAttribute("adminId");
		if (adminId != null) {
			// Go to AdminController -- jax rs
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			httpResponse.setHeader("Pragma", "no-cache");
			httpResponse.setDateHeader("Expires", 0);
			chain.doFilter(request, response);
		} else {
			// Go to LoginController
			httpRequest.getRequestDispatcher("/loginController").forward(request, response);
		}
	}

	public void destroy() {
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	
}
