package com.iti.pizza.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iti.pizza.entity.Admin;
import com.iti.pizza.services.AdminService;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/loginController")
public class LoginController extends HttpServlet {
	
	private static final long serialVersionUID = -7396745807506281216L;
	
	AdminService adminService = new AdminService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login = request.getParameter("login");
		String password = request.getParameter("password");

		HttpSession session = request.getSession();

		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
		} else {
			Admin admin = adminService.isValidLogin(login, password);
			if (admin != null) {
				session.setAttribute("adminId", admin.getId());
				response.sendRedirect(request.getContextPath()+"/admin/admin.html");
			} else {
				request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
			}
		}
	}
}
