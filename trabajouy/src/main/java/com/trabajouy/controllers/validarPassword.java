package com.trabajouy.controllers;

import java.io.IOException;

import com.trabajouy.model.EstadoSesion;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtUsuario;


/**
 * Servlet implementation class Login
 */
public class validarPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public validarPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	HttpSession session = request.getSession();

    	if (session.getAttribute("estado_sesion") == null){
    		response.sendRedirect("/trabajouy/home");
    		return;
        }

        if (session.getAttribute("estado_sesion") == EstadoSesion.NO_LOGUEADO) {
    		request.getRequestDispatcher("/WEB-INF/errorPages/403.jsp").forward(request, response);
    		return;
        }


		DtUsuario user = (DtUsuario) request.getSession().getAttribute("usuario_logueado");

		String password = request.getParameter("password");
		request.setAttribute("verifico", false);
		request.setAttribute("user", user);

		if (password==null) {
			request.getRequestDispatcher("/WEB-INF/form/validarPassword.jsp").forward(request, response);
		}
		else {
			if (password.equals(user.getContra())) {
				response.sendRedirect("/trabajouy/modificarUsuario");
			}
			else {
				request.setAttribute("verifico", true);
				request.getRequestDispatcher("/WEB-INF/form/validarPassword.jsp").forward(request, response);

			}
		}


	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request,response);
	}

}