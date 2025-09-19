package com.trabajouy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtPaquete;
import servidor.NoExistePaquete_Exception;



/**
 * Servlet implementation class listPaquete
 */
public class listarPaquetes extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public listarPaquetes() {
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

		//Set<String> paqs = port.listarPaquetesRegistrados();
		List<String> paqs = port.listarPaquetesRegistrados().getItem();
        List<DtPaquete> infoPaqs = new ArrayList<>();
		for (String p : paqs) {
			try {
				infoPaqs.add(port.getInfoPaqueteConImg(p));


			} catch (NoExistePaquete_Exception e) {
				response.sendError(404); // el paquete no existe
				request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").
						include(request, response);
				return;
			}
		}

		request.setAttribute("paquetes", infoPaqs);
		request.getRequestDispatcher("/WEB-INF/paquete/listarPaq.jsp").
				forward(request, response);
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

