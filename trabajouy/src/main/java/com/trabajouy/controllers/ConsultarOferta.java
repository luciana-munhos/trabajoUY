package com.trabajouy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.trabajouy.model.EstadoSesion;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtOferta;
import servidor.DtPostulacion;
import servidor.DtUsuario;
import servidor.Estados;
import servidor.NoExisteEmpresa_Exception;
import servidor.NoExisteOferta_Exception;
import servidor.NoExistePostulante_Exception;

/**
 * Servlet implementation class ConsultarOferta
 */
public class ConsultarOferta extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultarOferta() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

    	HttpSession session = request.getSession();

		if (session.getAttribute("estado_sesion") == null){
			session.setAttribute("estado_sesion",EstadoSesion.NO_LOGUEADO);
	    }

		boolean esMobile = request.getHeader("User-Agent").contains("Mobi");

		if(esMobile && session.getAttribute("estado_sesion")!=EstadoSesion.LOGUEADO_POSTULANTE) {
			response.sendRedirect("/trabajouy/home");
			return;
		}

    	String nombre_oferta = request.getParameter("nombre");
    	String vencida = request.getParameter("Vencida");

    	if (nombre_oferta == null) {
    		request.getRequestDispatcher("/listarOfertas").forward(request, response);
    		return;
    	}
    	try {
    		servidor.PublicadorService service = new servidor.PublicadorService();
	        servidor.Publicador port = service.getPublicadorPort();
    		DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");
    		DtOferta oferta = port.getDTOfertaDAO(nombre_oferta);

    		if(oferta.getEstado()==Estados.FINALIZADA) {
				request.setAttribute("finalizada", "true");
    		}
    		else {
				request.setAttribute("finalizada", "false");
    		}

    		boolean vigencia = port.vigenciaOferta(nombre_oferta);

    		if(session.getAttribute("estado_sesion") == EstadoSesion.LOGUEADO_POSTULANTE) {

    			try {
    				List<String> favs = port.getFavoritosUsuario(usr.getNickname()).getItem();

    				if(favs.contains(oferta.getNombre()))
    					request.setAttribute("favs", "h");

    			} catch (NoExistePostulante_Exception e) {
    				request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
    				return;
    			}
    		}

			if ((EstadoSesion) session.getAttribute("estado_sesion") == EstadoSesion.LOGUEADO_POSTULANTE) {
	    		if (oferta.getEstado() != Estados.CONFIRMADA && (usr==null ||  !(oferta.getPostul().contains(usr.getNickname())))) {
	    			request.getRequestDispatcher("/WEB-INF/errorPages/403.jsp").include(request, response);
	    			return;
	    		}
			}
			else {
				if (oferta.getEstado() != Estados.CONFIRMADA && (usr==null || !oferta.getNickEmpresa().equals(usr.getNickname()))) {
					request.getRequestDispatcher("/WEB-INF/errorPages/403.jsp").include(request, response);
	    			return;
	    		}
			}


			request.setAttribute("oferta_a_consultar", oferta);
			List<String> postul = oferta.getPostul();
			if ((EstadoSesion) session.getAttribute("estado_sesion") == EstadoSesion.LOGUEADO_POSTULANTE) {
				request.setAttribute("duenio_oferta", false);
				//if usr nickname is sin postul


				if (postul.contains(usr.getNickname())) {
					DtPostulacion postulacion = null;
					if (oferta.getEstado()==Estados.FINALIZADA) {
						List <DtPostulacion> post = port.listarDTPostulacionesDB(usr.getNickname()).getItem();
						for (DtPostulacion pos : post) {
							if(nombre_oferta.equals(pos.getMiOferta().getNombre())) {
								postulacion = pos;
							}
						}
					}
					else {
						postulacion = port.getDTPostulacion(usr.getNickname(),nombre_oferta);
					}

					request.setAttribute("postulacion",postulacion);
				}
				else
					request.setAttribute("postulacion", null);
			}
			else if (session.getAttribute("estado_sesion") == EstadoSesion.LOGUEADO_EMPRESA) {

	    		if (vencida!=null) {
	    			if ("true".equals(vencida)) {
	    			request.setAttribute("vencida", "true");
	    			}
	    			else {
	        			request.setAttribute("vencida", "false");
	        			port.finalizarOferta(nombre_oferta, usr.getNickname());
	        			//aca va la persistencia, guardar los datos basicos en la base de datos;
	        	        System.out.println("Esto se imprimirá en la consola estándar.");
	        	        port.guardarDatosOferta(nombre_oferta);
	    				response.sendRedirect("/trabajouy/home");
	    				return;
	    			}
	    		}
	    		else {
	    			request.setAttribute("vencida", "false");
	    		}

				request.setAttribute("postulacion", null);
				if (usr.getNickname().equals(oferta.getNickEmpresa())) {
					request.setAttribute("duenio_oferta", true);
					List<String> nombresPostulantes = oferta.getPostul();
					List<DtUsuario> postulados = new ArrayList<>();
					List<DtPostulacion> postulaciones = new ArrayList<>();


					for (String usuario : nombresPostulantes) {
           				postulados.add(port.mostrarDatosUsuario(usuario));
           				if(oferta.getEstado()==Estados.FINALIZADA) {
    						List <DtPostulacion> post = port.listarDTPostulacionesDB(usuario).getItem();
    						for(DtPostulacion pos : post) {
    							if(nombre_oferta.equals(pos.getMiOferta().getNombre())) {
    								postulaciones.add(pos);
    							}
    						}
    					}
    					else {
    						postulaciones.add(port.getDTPostulacion(usuario, oferta.getNombre()));
    					}
					}

					request.setAttribute("postulaciones", postulaciones);

					request.setAttribute("postulados", postulados);

					if(vigencia)
						request.setAttribute("vigencia", "true");
					else
						request.setAttribute("vigencia", "false");
				}else
					request.setAttribute("duenio_oferta", false);
			}
			else {
				request.setAttribute("postulacion", null);
				request.setAttribute("duenio_oferta", false);
			}
    	} catch (NoExisteOferta_Exception | NoExisteEmpresa_Exception | NoExistePostulante_Exception e) {
			//response.sendError(404);
			request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
			return;
		}

        if (esMobile)
        	request.getRequestDispatcher("/WEB-INF/ofertas/consultarOfertaMobile.jsp").forward(request, response);
        else
        	request.getRequestDispatcher("/WEB-INF/ofertas/consultarOferta.jsp").forward(request, response);


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
