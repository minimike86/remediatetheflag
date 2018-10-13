package com.remediatetheflag.global.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.remediatetheflag.global.controllers.RTFAdminController;

public class RTFAdminServlet extends HttpServlet  {

	private static final long serialVersionUID = 6806666231301625516L;
	private static Logger logger = LoggerFactory.getLogger(UserServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RTFAdminController controller = new RTFAdminController();
		try {
			controller.executeAction(request, response);
		} catch (Exception e1) {
			logger.error("RTFAdminServlet Error: "+e1.getMessage());
		}
	}

}
