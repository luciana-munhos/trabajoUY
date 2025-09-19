package com.trabajouy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.trabajouy.model.EstadoSesion;

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
 * Servlet implementation class exponerRanking
 */
public class exponerRanking extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public exponerRanking() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

    	HttpSession session = request.getSession();
    	servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();

        if (session.getAttribute("estado_sesion") == null){
        	session.setAttribute("estado_sesion",EstadoSesion.NO_LOGUEADO);
	    }

    	DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");
        String nombreOf = request.getParameter("nombreOferta");

        try {
			DtOferta dtof = port.getDTOferta(nombreOf);

			if (usr==null || !dtof.getNickEmpresa().equals(usr.getNickname())) {
				request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
				return;
			}

			List<String> postulaciones = dtof.getPostul();
			Map<DtPostulante,DtPostulacion> postus = new HashMap<>();


	        for (String p : postulaciones) {
	        	DtPostulante dtpostulante = (DtPostulante) port.mostrarDatosUsuario(p);
				DtPostulacion dtpostulacion = port.getDTPostulacion(p, nombreOf);
				postus.put(dtpostulante,dtpostulacion);
	        }

	        // Como no puedo ordenarlos en el map voy a tener que pasarlo a una lista...
	        List<Entry<DtPostulante, DtPostulacion>> postulacionesLista = new ArrayList<>(postus.entrySet());

	        // Y la voy a ordenar segun el orden de cada postulacion
	        Collections.sort(postulacionesLista, new Comparator<Entry<DtPostulante, DtPostulacion>>() {
	            @Override
	            public int compare(Entry<DtPostulante, DtPostulacion> entry1, Entry<DtPostulante, DtPostulacion> entry2) {
	                int orden1 = entry1.getValue().getOrden();
	                int orden2 = entry2.getValue().getOrden();
	                return Integer.compare(orden1, orden2);
	            }
	        });

	        request.setAttribute("oferta", dtof);
	        request.setAttribute("postulantes", postulacionesLista);


		} catch (NoExisteEmpresa_Exception | NoExistePostulante_Exception | NoExisteOferta_Exception e) {
			// TODO Auto-generated catch block
			request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
			return;
		}


    	request.getRequestDispatcher("/WEB-INF/ofertas/exponerRanking.jsp").forward(request, response);

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
