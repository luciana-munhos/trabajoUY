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
 * Servlet implementation class pagoRealizado
 */
public class pagoRealizado extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public pagoRealizado() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	HttpSession session = request.getSession();

    	boolean esMobile = request.getHeader("User-Agent").contains("Mobi");
	    if (esMobile || (session.getAttribute("estado_sesion") == null)){
			response.sendRedirect("/trabajouy/home");
			return;
	    }

	    if (session.getAttribute("estado_sesion") != EstadoSesion.LOGUEADO_EMPRESA) {
			request.getRequestDispatcher("/WEB-INF/errorPages/403.jsp").forward(request, response);
			return;
	    }
	    
	    DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");
        System.out.println("El comprador fue este: "+ session.getAttribute("nickComprador"));


		/* Si no hay comprador o el que esta entrando no es el comprador, entonces no puede ingresar aqui */
	    if (session.getAttribute("nickComprador") == null || session.getAttribute("nickComprador") != usr.getNickname()) {
            System.out.println("PROHIBIDO: No puedes entrar. El comprador es "+ session.getAttribute("nickComprador"));

	    	request.getRequestDispatcher("/WEB-INF/errorPages/403.jsp").forward(request, response);
			return;
	    }

        request.getRequestDispatcher("/WEB-INF/ofertas/pagoRealizado.jsp").forward(request, response);



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
