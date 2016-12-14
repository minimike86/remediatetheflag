package handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import actions.controller.ActionController;
import actions.controller.UserActionsController;

public class UserHandler extends HttpServlet {

	private static final long serialVersionUID = 2554084684895448448L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ActionController controller = new UserActionsController();
		try {
			controller.executeAction(request, response);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
}