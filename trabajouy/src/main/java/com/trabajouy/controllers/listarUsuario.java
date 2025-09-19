package com.trabajouy.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtUsuario;
import servidor.NoExisteEmpresa_Exception;
import servidor.NoExistePostulante_Exception;

/**
 * Servlet implementation class listarUsuario
 */
public class listarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public listarUsuario() {
        super();
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		if (session.getAttribute("estado_sesion") == null){
			response.sendRedirect("/trabajouy/home");
			return;
	    }

		servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();
		List<String> nickUsuarios = port.listarNicknamesUsuarios().getItem();

		// conseguir listado de DTUsuarios del sistema
		Map<String,DtUsuario> usrs = new HashMap<>();
		DtUsuario dtU;
		for (String nick: nickUsuarios) {
			try {
				dtU = port.mostrarDatosUsuario(nick);
				usrs.put(nick, dtU);
			}catch(NoExisteEmpresa_Exception e1) {
				response.sendError(404);
				request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
				return;
			}catch(NoExistePostulante_Exception e2) {
				response.sendError(404);
				request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
				return;
			}
		}
		request.setAttribute("lista_usuarios",usrs);
		request.getRequestDispatcher("/WEB-INF/usuarios/listarUsuario.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
