package com.trabajouy.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtPostulante;
import servidor.DtUsuario;
import servidor.NoExisteOferta_Exception;
import servidor.NoExistePostulante_Exception;

/**
 * Servlet implementation class favorito
 */
public class favorito extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public favorito() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oferta = request.getParameter("nombreOferta");
		String marcar = request.getParameter("marcar");

		servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();

		HttpSession session = request.getSession();

        DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");

        if(usr == null || !(usr instanceof DtPostulante)) {
        	request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
			return;
        }

        try {
			port.marcarFavorito(usr.getNickname(), oferta, marcar.equals("fav"));
		} catch (NoExisteOferta_Exception | NoExistePostulante_Exception e) {
			request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
			return;
		}
	}

}
