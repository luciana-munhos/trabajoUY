package com.trabajouy.controllers;
import java.io.IOException;
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
import servidor.DtEmpresa;
import servidor.DtPostulante;
import servidor.DtUsuario;
import servidor.NoExisteEmpresa_Exception;
import servidor.NoExistePostulante_Exception;


/**
 * Servlet implementation class listPaquete
 */
@MultipartConfig // Tamaño máximo de 10 MB para la imagen
public class modificarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public modificarUsuario() {
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

		    boolean esMobile = request.getHeader("User-Agent").contains("Mobi");
		    if(esMobile) {
		    	response.sendRedirect("/trabajouy/home");
				return;
			}


		    servidor.PublicadorService service = new servidor.PublicadorService();
	        servidor.Publicador port = service.getPublicadorPort();
	        String modificacion = request.getParameter("modificar");


	        // Cerrar el PrintWriter
			if (modificacion==null) {
				if (request.getSession().getAttribute("estado_sesion")!= EstadoSesion.NO_LOGUEADO) {
					DtUsuario user = (DtUsuario) request.getSession().getAttribute("usuario_logueado");
					request.setAttribute("user", user);
	                if (user instanceof DtPostulante){
	                	DtPostulante userP2 = (DtPostulante) user;
	                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                	XMLGregorianCalendar xmlDate = userP2.getFechaNac();
	                	Date date = xmlDate.toGregorianCalendar().getTime();
	                	String fechaFormateada = sdf.format(date);
	                	request.setAttribute("miFecha", fechaFormateada);
	                }
				}

				request.getRequestDispatcher("/WEB-INF/usuarios/modificarUsuario.jsp").
					forward(request, response);
			}
			else {
				DtUsuario user = (DtUsuario) request.getSession().getAttribute("usuario_logueado");
				String nombre = request.getParameter("nombre");
				String apellido = request.getParameter("apellido");
				String contra = request.getParameter("password");
				String fileName = user.getImagen();
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
					fileName = user.getNickname() + ext;
				}


                if (user instanceof DtEmpresa){
					String desc = request.getParameter("desc");
					String link = request.getParameter("link");
						try {

							port.modificarDatosEmpresa(user.getNickname(), nombre, apellido, contra, fileName, fileBytes, desc, link);

						} catch (NoExisteEmpresa_Exception e) {
							response.sendError(404); // la empresa no existe
							request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").
									include(request, response);
							return;
						}

				}
				else {
					String nac = request.getParameter("nac");
					Date f = null;
					String fecha = request.getParameter("fecha");
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					XMLGregorianCalendar fechaxml = null;
					try {
						f = dateFormat.parse(fecha);
						GregorianCalendar calendar = new GregorianCalendar();
						calendar.setTime(f);
						// Crea un objeto XMLGregorianCalendar utilizando DatatypeFactory
						fechaxml = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
						port.modificarDatosPostulante(user.getNickname(), nombre, apellido, contra, fileName, fileBytes, nac, fechaxml);
					} catch (NoExistePostulante_Exception | ParseException | DatatypeConfigurationException e ) {
						response.sendError(404); // la empresa no existe
						request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").
								include(request, response);
						return;
						}
				}

				DtUsuario usr_log;
				try {
					usr_log = port.mostrarDatosUsuario(user.getNickname());
					session.setAttribute("usuario_logueado", usr_log);
					request.setAttribute("user", usr_log);
					String urlDestino = "/consultarUsuario?nickname=" + user.getNickname();
					request.getRequestDispatcher(urlDestino).
					forward(request, response);
				} catch (NoExisteEmpresa_Exception | NoExistePostulante_Exception e) {
					response.sendError(404); // la empresa no existe
					request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").
							include(request, response);
					return;
				}
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


