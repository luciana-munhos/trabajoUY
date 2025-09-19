package com.trabajouy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtTipoPublicacion;
import servidor.NoExisteTipoPublicacion_Exception;

/**
 * Servlet implementation class listarTP
 */
public class listarTP extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public listarTP() {
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

		boolean esMobile = request.getHeader("User-Agent").contains("Mobi");
	    if(esMobile) {
	    	response.sendRedirect("/trabajouy/home");
			return;
		}

		servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();


    	List<String> tipospubli = new ArrayList<>();
		try {
			tipospubli = port.listarNombresTipos().getItem();
		} catch (NoExisteTipoPublicacion_Exception e) {
			//Si no hay tipos de publi, no importa porque quedara vacio
			//No saque la excepcion pues se usa en varios lados mas pero en este contexto no me afecta
		}


		//necesito pedirle los DtTipoPublicacion
		DtTipoPublicacion dttp;
        Set<DtTipoPublicacion> dttipospubli = new HashSet<>();

    	for (String tp : tipospubli) {
    		try {
				dttp = port.getInfoTipoPublicacion(tp);
	    		dttipospubli.add(dttp);
			} catch (NoExisteTipoPublicacion_Exception e) {
				response.sendError(404);
				request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
				return;
			}
    	}
    	request.setAttribute("tiposPubli", dttipospubli);
    	request.getRequestDispatcher("/WEB-INF/tipospubli/listarTP.jsp").forward(request, response);
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
