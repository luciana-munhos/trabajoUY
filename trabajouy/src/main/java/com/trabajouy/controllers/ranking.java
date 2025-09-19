package com.trabajouy.controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.trabajouy.model.EstadoSesion;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtOferta;
import servidor.DtPostulacion;
import servidor.DtPostulante;
import servidor.NoExisteOfertaEmpresa_Exception;
import servidor.NoExisteOferta_Exception;
import servidor.NoExistePostulante_Exception;

/**
 * Servlet implementation class ranking
 */
public class ranking extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ranking() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//processRequest(request,response);
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();

		if (session.getAttribute("estado_sesion") == null){
			response.sendRedirect("/trabajouy/home");
			return;
	    }

	    if (session.getAttribute("estado_sesion") != EstadoSesion.LOGUEADO_EMPRESA) {
			request.getRequestDispatcher("/WEB-INF/errorPages/403.jsp").forward(request, response);
			return;
	    }

	    DtOferta oferta = (DtOferta) session.getAttribute("oferta");

	    @SuppressWarnings("unchecked")
	    Map<DtPostulante, DtPostulacion> postus = (Map<DtPostulante, DtPostulacion>) session.getAttribute("postulantes");


	    servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();


        // Voy por cada DtPostulante y seteo el orden de su DtPostulacion
        for (Map.Entry<DtPostulante, DtPostulacion> entry : postus.entrySet()) {
            DtPostulante postulante = entry.getKey();
            DtPostulacion postulacion = entry.getValue();

            //Consigo el orden ingresado (el numero) del form
            String orderParameter = request.getParameter("pos_" + postulante.getNickname());

            //parseo porq la opcion era string pero el atributo del datatype es un int
            int order = Integer.parseInt(orderParameter);

            //y seteo el orden en el DtPostulacion
            postulacion.setOrden(order);


            Calendar calendar = Calendar.getInstance();
            DatatypeFactory datatypeFactory;
            XMLGregorianCalendar xmlGregorianCalendar = null;
			try {
				datatypeFactory = DatatypeFactory.newInstance();
				xmlGregorianCalendar = datatypeFactory.newXMLGregorianCalendar((GregorianCalendar) calendar);
	            //y seteo la fecha de hoy como la fecha de seleccion
	            postulacion.setFechaSelec(xmlGregorianCalendar);
			} catch (DatatypeConfigurationException e) {
				// TODO Auto-generated catch block
				response.sendError(404);
				request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
				return;
			}

			try {
				port.asignarOrdenPostulacion(postulante.getNickname(),oferta.getNombre(),order,xmlGregorianCalendar);
				//port.cambiarEstadoOferta(oferta.getNombre(),Estados.CONFIRMADA); //La oferta seguiria confirmada y no vigente
			} catch (NoExisteOfertaEmpresa_Exception | NoExisteOferta_Exception | NoExistePostulante_Exception e) {
				response.sendError(404);
				request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
				return;
			}
        }

        String url = "/trabajouy/consultarOferta?nombre="+oferta.getNombre()+"#link_ranking";
		response.sendRedirect(url);
		return;
	}

}
