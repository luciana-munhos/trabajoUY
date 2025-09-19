package com.trabajouy.controllers;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.trabajouy.model.EstadoSesion;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtCompra;
import servidor.DtPaquete;
import servidor.DtUsuario;
import servidor.NoExisteEmpresa_Exception;
import servidor.NoExistePaquete_Exception;
import servidor.ParseException_Exception;
/**
 * Servlet implementation class consPaq
 */
public class consPaq extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public consPaq() {
        super();
        // TODO Auto-generated constructor stub
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
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

    		DtUsuario user = (DtUsuario) session.getAttribute("usuario_logueado");

			String compra = request.getParameter("compra");

			if (compra==null) {

				String ubicacion = request.getParameter("ubic");
				String nombreP = request.getParameter("dato");
				request.setAttribute("ubic", ubicacion);
				request.setAttribute("esEmpresa", false);
				request.setAttribute("noCompro", true);

				//veo si el usuario es empresa
				if (request.getSession().getAttribute("estado_sesion")== EstadoSesion.LOGUEADO_EMPRESA) {

					request.setAttribute("esEmpresa", true);

					//Set<DTCompra> compras;
					List<DtCompra> compras = new ArrayList<>();
					try {
						//veo si el usuario ya tiene ese paquete
						compras = port.getDTComprasEmpresa(user.getNickname()).getItem();
						for (DtCompra com : compras) {

							if (nombreP.equals(com.getPaquete().getNombre())) {


								request.setAttribute("noCompro", false);
								request.setAttribute("infoCompra", com);
								XMLGregorianCalendar fechaCom = com.getFechaCompra();
								Date fechaCompra = fechaCom.toGregorianCalendar().getTime();

								Calendar calCompra = Calendar.getInstance();
								calCompra.setTime(fechaCompra);
								Calendar hoy = Calendar.getInstance();
								hoy.setTime(new Date());

								calCompra.add(Calendar.DATE, com.getVencimiento());

								SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
								String fechaVenc = null;
								fechaVenc = formater.format(calCompra.getTime());
								request.setAttribute("fechaVenc", fechaVenc.toString());
								request.setAttribute("estaVencido", hoy.after(calCompra));
								request.setAttribute("fechaCompra", formater.format(fechaCompra));

							}
						}

					} catch (NoExisteEmpresa_Exception e) {
						request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").
								include(request, response);
						return;
					}


				}
				try {
					DtPaquete infoPaq = port.getInfoPaquete(nombreP);
    				request.setAttribute("paquete", infoPaq);
					request.getRequestDispatcher("/WEB-INF/paquete/consultarPaq.jsp").
							forward(request, response);
				} catch (NoExistePaquete_Exception e) {
					request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").
							include(request, response);
					return;
				}
			}
			else {

				String nombreT = request.getParameter("paquete");

				Date date = new Date(); // Reemplaza esto con tu objeto Date

				// Crea un objeto GregorianCalendar y configúralo con la fecha
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTime(date);

				// Crea un objeto XMLGregorianCalendar utilizando DatatypeFactory
				XMLGregorianCalendar fechaxml = null;
				try {
					fechaxml = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

				port.ingresarPaqueteAEmpresa(nombreT, user.getNickname(), fechaxml);
				response.sendRedirect("/trabajouy/home");

				} catch (NoExisteEmpresa_Exception | NoExistePaquete_Exception | ParseException_Exception | DatatypeConfigurationException  e) {

					request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").
							include(request, response);
				}


			}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
