package com.trabajouy.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtOferta;
import servidor.DtPostulacion;
import servidor.DtPostulante;
import servidor.DtUsuario;
import servidor.NoExisteEmpresa_Exception;
import servidor.NoExisteOferta_Exception;
import servidor.NoExistePostulante_Exception;



/**
 * Servlet implementation class realizarRanking
 */
public class realizarRanking extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public realizarRanking() {
        super();
        // TODO Auto-generated constructor stub
    }
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    	boolean esMobile = request.getHeader("User-Agent").contains("Mobi");
	    if(esMobile) {
	    	response.sendRedirect("/trabajouy/home");
			return;
		}

		HttpSession session = request.getSession();

    	DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");

    	servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();


		if (session.getAttribute("estado_sesion") == null){
			response.sendRedirect("/trabajouy/home");
			return;
	    }

		String nomOferta = request.getParameter("nombreOferta");
		DtOferta dtOfe = null;
		try {
			dtOfe = port.getDTOferta(nomOferta);
		} catch (NoExisteOferta_Exception e) {
			response.sendError(404);
			return;
		}

		if(!usr.getNickname().equals(dtOfe.getNickEmpresa())) {
			response.sendError(403);
			return;
		}
		Map<DtPostulante,DtPostulacion> postus = new HashMap<>();
		for(String postulante: dtOfe.getPostul())
			try {
				DtPostulante dtpostulante = (DtPostulante) port.mostrarDatosUsuario(postulante);
				DtPostulacion dtpostulacion = port.getDTPostulacion(postulante, nomOferta);
	///!!			//System.out.println("Requesting postus NOMBRE: " + dtpostulante.getNombre());

				postus.put(dtpostulante,dtpostulacion);
			} catch (NoExisteOferta_Exception | NoExisteEmpresa_Exception | NoExistePostulante_Exception e) {
				response.sendError(404);
			}

		session.setAttribute("oferta", dtOfe);
///!!		//System.out.println("Setting postus attribute: " + postus);

		session.setAttribute("postulantes", postus);
		request.getRequestDispatcher("/WEB-INF/ofertas/realizarRanking.jsp").include(request, response);
		return;
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
