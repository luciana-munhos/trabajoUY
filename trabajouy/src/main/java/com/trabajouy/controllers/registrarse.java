package com.trabajouy.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.trabajouy.model.EstadoSesion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import servidor.CorreoUsuarioExistente_Exception;
import servidor.DtUsuario;
import servidor.NicknameUsuarioExistente_Exception;
import servidor.NoExisteEmpresa_Exception;
import servidor.NoExistePostulante_Exception;
/**
 * Servlet implementation class registrarse
 */
@MultipartConfig
public class registrarse extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public registrarse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();
		// AJAX
		HttpSession session = request.getSession();
		if (session.getAttribute("estado_sesion") != EstadoSesion.NO_LOGUEADO) {
			response.sendRedirect("/trabajouy/home");
			return;
		}
		response.setContentType("text/xml");
		/*Chequear nick*/
		boolean res = port.esNickValido(request.getParameter("nick"));
		String xmlResponse = "<valid>" + res + "</valid>";

	    if (xmlResponse != null) {
	        PrintWriter out = response.getWriter();
	        out.println(xmlResponse);
	    } else {
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("estado_sesion") == null) {
			session.setAttribute("estado_sesion", EstadoSesion.NO_LOGUEADO);
		}
		EstadoSesion nuevoEstado = null;
		String nick = request.getParameter("nick");
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String mail = request.getParameter("mail");
		String c = request.getParameter("con");
		String c2 = request.getParameter("con2");
		String el = request.getParameter("elegir"); //Me fijo que elemento eligio
		String pais = "";
		String fecha = "";
		String descripcion = "";
		String link = "";
		String img = "media/img/Usuarios/default/default.jpeg";
		String fileName = "default.jpeg";
		/*Imagen*/
		Part part = request.getPart("img");
		byte[] fileBytes = new byte[1];
		fileBytes[0] = '\0';
		if (part != null && part.getSize() > 0) {
			fileBytes = part.getInputStream().readAllBytes();
			fileName = part.getSubmittedFileName();
			String ext = "";
			int i = fileName.lastIndexOf('.');
			ext = fileName.substring(i);
			fileName = nick + ext;
		}else if (session.getAttribute("pathImg") != null) {
			img = (String) session.getAttribute("pathImg");
		}

		servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();

		Date f = null;
		if (el.equals("1")) { //agregar empresa
			descripcion = request.getParameter("des");
			link = request.getParameter("link");

			/*ALTA DE USUARIO*/
			try {
		        port.darAltaEmpresa(nick, nombre, apellido, mail, c, fileName, fileBytes, descripcion, link);
				nuevoEstado = EstadoSesion.LOGUEADO_EMPRESA;
				request.setAttribute("estado", "");
				session.setAttribute("estado_sesion", nuevoEstado);
				DtUsuario usr = port.mostrarDatosUsuario(nick);
				session.setAttribute("usuario_logueado", usr);
				response.sendRedirect("/trabajouy/home?registrado=1");
			}catch (NicknameUsuarioExistente_Exception | CorreoUsuarioExistente_Exception e){
				String url = "/trabajouy/formReg?estado=invalido"
				        + "&nombre=" + nombre
				        + "&apellido=" + apellido
				        + "&elegir=" + el
				        + "&pais=" + pais
				        + "&fecha=" + fecha
				        + "&des=" + descripcion
				        + "&link=" + link
				        + "&contra=" + c
				        + "&contra2=" +c2;
				response.sendRedirect(url);
				return;
			}catch (NoExisteEmpresa_Exception | NoExistePostulante_Exception e) {
				request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
				return;
			}

		}else if (el.equals("2")) { //agregar postulante
			pais = request.getParameter("pais");
			fecha = request.getParameter("fecha");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				f = dateFormat.parse(fecha);
			}catch (ParseException e) {
				System.out.print("ERROR FECHA");
			}

			/*ALTA DE USUARIO*/
			try {
				/*Ver tema de la fecha*/
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(f);
				XMLGregorianCalendar fechaXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		        port.darAltaPostulante(nick, nombre, apellido, mail, c, fileName,fileBytes, fechaXML, pais);
				nuevoEstado = EstadoSesion.LOGUEADO_POSTULANTE;
				request.setAttribute("estado", "");
				session.setAttribute("estado_sesion", nuevoEstado);
				DtUsuario usr = port.mostrarDatosUsuario(nick);
				session.setAttribute("usuario_logueado", usr);

				response.sendRedirect("/trabajouy/home?registrado=1");
			} catch (NicknameUsuarioExistente_Exception | CorreoUsuarioExistente_Exception e) {
				String url = "/trabajouy/formReg?estado=invalido"
				        + "&nombre=" + nombre
				        + "&apellido=" + apellido
				        + "&elegir=" + el
				        + "&pais=" + pais
				        + "&fecha=" + fecha
				        + "&des=" + descripcion
				        + "&link=" + link
				        + "&contra=" + c
				        + "&contra2=" +c2;
				response.sendRedirect(url);
			} catch (NoExisteEmpresa_Exception | NoExistePostulante_Exception | DatatypeConfigurationException e) {
				response.sendError(404);
				request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
				return;
			}
		}
	}
}
