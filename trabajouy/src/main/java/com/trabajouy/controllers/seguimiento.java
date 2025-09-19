package com.trabajouy.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtUsuario;
import servidor.NoExisteEmpresa_Exception;
import servidor.NoExistePostulante_Exception;

/**
 * Servlet implementation class seguimiento
 */
public class seguimiento extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public seguimiento() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();

		HttpSession session = request.getSession();

		if (session.getAttribute("estado_sesion") == null){
			response.sendRedirect("/trabajouy/home");
			return;
	    }

		String nick = request.getParameter("nickname");
		if (nick == null) {
		    response.sendError(404);
		    request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
		    return;
		}

		DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");
		String miNick = usr.getNickname();
		String otroNick = request.getParameter("nickname");
		boolean dejar = Integer.parseInt(request.getParameter("dejar")) == 1;

		//System.out.println(miNick);
		//System.out.println(otroNick);
		//System.out.println(Integer.parseInt(request.getParameter("dejar")));


		if (dejar){
			try {
				port.dejarDeSeguirUsuario(miNick,otroNick);
			} catch (NoExisteEmpresa_Exception | NoExistePostulante_Exception e) {
				request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
				return;
			}
		}else{
			try {
				port.seguirUsuario(miNick,otroNick);
				//System.out.print(usr.getSeguidos());
				//System.out.print(port.mostrarDatosUsuario(otroNick).getSeguidores());


			} catch (NoExisteEmpresa_Exception | NoExistePostulante_Exception e) {
				request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
				return;
			}
		}

		// actualizo el DtUsuario que esta logueado tal vez ????
		DtUsuario miUsr = null;
		try {
			miUsr = port.mostrarDatosUsuario(miNick);
		} catch (NoExisteEmpresa_Exception | NoExistePostulante_Exception e) {
			request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
			return;
		}
		session.setAttribute("usuario_logueado", miUsr);

		// tengo que redireccionar al servlet de consulta usuario de vuelta. ES VOLVER
		response.sendRedirect("/trabajouy/consultarUsuario?nickname=" + otroNick);

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
