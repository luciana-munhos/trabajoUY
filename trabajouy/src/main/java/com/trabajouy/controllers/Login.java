package com.trabajouy.controllers;

import java.io.IOException;

import com.trabajouy.model.EstadoSesion;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtEmpresa;
import servidor.DtUsuario;
import servidor.NoExisteEmpresa_Exception;
import servidor.NoExistePostulante_Exception;


/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	boolean esMobile = false;
    	if(request.getHeader("User-Agent").contains("Mobi")) {
			esMobile = true;
		}


    	String nickMail = request.getParameter("nickMail");
		String password = request.getParameter("password");

		if(nickMail == null || password == null) {
			response.sendRedirect("/trabajouy/home");
			return;
		}

		EstadoSesion nuevoEstado = EstadoSesion.NO_LOGUEADO;

		servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();


		try {
			if(port.iniciarSesion(nickMail, password)) {
				DtUsuario usr_log = port.mostrarDatosUsuario(nickMail);

				if(usr_log instanceof DtEmpresa)
					nuevoEstado = EstadoSesion.LOGUEADO_EMPRESA;
				else
					nuevoEstado = EstadoSesion.LOGUEADO_POSTULANTE;

				if(esMobile && nuevoEstado == EstadoSesion.LOGUEADO_EMPRESA) {
					nuevoEstado = EstadoSesion.NO_LOGUEADO;
					request.setAttribute("invalid_data",true);
				}
				else {
					session.setAttribute("usuario_logueado",usr_log);
					session.setAttribute("estado_sesion", nuevoEstado);
					response.sendRedirect("/trabajouy/home");
					return;
				}

			}else {
				request.setAttribute("invalid_data",true);
			}

		} catch (NoExisteEmpresa_Exception | NoExistePostulante_Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("invalid_data", true);

		}
		if(!esMobile)
			request.getRequestDispatcher("/WEB-INF/form/formLogin.jsp").forward(request, response);
		else
			request.getRequestDispatcher("/WEB-INF/form/formLoginMobile.jsp").forward(request, response);
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
