package com.trabajouy.controllers;

import java.io.IOException;

import com.trabajouy.model.EstadoSesion;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtTipoPublicacion;
import servidor.NoExisteTipoPublicacion_Exception;

/**
 * Servlet implementation class consultarTipoPubli
 */
public class consultarTipoPubli extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public consultarTipoPubli() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

    	HttpSession session = request.getSession();

		if (session.getAttribute("estado_sesion") == null){
			session.setAttribute("estado_sesion",EstadoSesion.NO_LOGUEADO);

	    }


		boolean esMobile = request.getHeader("User-Agent").contains("Mobi");
	    if(esMobile) {
	    	response.sendRedirect("/trabajouy/home");
			return;
		}

		servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();
        String tipopubli = request.getParameter("nombreTP");
    	DtTipoPublicacion dttipopubli = null;

    	try {
			dttipopubli = port.getInfoTipoPublicacion(tipopubli);
		} catch (NoExisteTipoPublicacion_Exception e) {
			request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
			return;
		}

        request.setAttribute("tipoPubli", dttipopubli);
        if (request.getHeader("User-Agent").contains("Mobi")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/tipospubli/consultarTipoPubliMobile.jsp");
			dispatcher.forward(request, response);
        }
        else {
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/tipospubli/consultarTipoPubli.jsp");
			dispatcher.forward(request, response);
        }
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}
