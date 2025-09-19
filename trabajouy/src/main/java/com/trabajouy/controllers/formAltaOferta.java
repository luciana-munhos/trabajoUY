package com.trabajouy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.XMLGregorianCalendar;

import com.trabajouy.model.EstadoSesion;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtCompra;
import servidor.DtCompra.CantActualTP;
import servidor.DtPaquete;
import servidor.DtUsuario;
import servidor.NoExisteEmpresa_Exception;
import servidor.NoExisteTipoPublicacion_Exception;

/**
 * Servlet implementation class formAltaOferta
 */
public class formAltaOferta extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public formAltaOferta() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

    	HttpSession session = request.getSession();

    	boolean esMobile = request.getHeader("User-Agent").contains("Mobi");
	    if(esMobile) {
	    	response.sendRedirect("/trabajouy/home");
			return;
		}

		if (session.getAttribute("estado_sesion") == null){
			session.setAttribute("estado_sesion",EstadoSesion.NO_LOGUEADO);
	    }


		if (session.getAttribute("estado_sesion") != EstadoSesion.LOGUEADO_EMPRESA) {
			request.getRequestDispatcher("/WEB-INF/errorPages/403.jsp").include(request, response);
			return;

		} else {

			servidor.PublicadorService service = new servidor.PublicadorService();
	        servidor.Publicador port = service.getPublicadorPort();

			DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");


	        List<String> paqsEmpVig = new ArrayList<>();
	        List<DtCompra> misCompras = new ArrayList<>();
		    if (usr != null) {

				try {
					paqsEmpVig = port.mostrarComprasPaquetesEmpresa(usr.getNickname()).getItem();
					Set<DtPaquete> paqs = new HashSet<>();
					DtPaquete dtpaq = null;
					DtCompra dtc = null;
		            Set<String> tiposDePublicacionSet = new HashSet<>();

		            Set<String> paquetesDispo = new HashSet<>();


		            Map<String, Map<String, Integer>> paqueteTipoCantidadMap = new HashMap<>();

		             misCompras = port.getDTComprasEmpresa(usr.getNickname()).getItem();

		            for(DtCompra compra : misCompras) {
		            	CantActualTP cant = compra.getCantActualTP();
		            	List<DtCompra.CantActualTP.Entry> listaCantidades = cant.getEntry();

		            	Map<String, Integer> cantidades = new HashMap<>();

		            	for (DtCompra.CantActualTP.Entry entry : listaCantidades) {
		            		cantidades.put(entry.getKey(), entry.getValue());
		            	}

		            	DtPaquete paq = compra.getPaquete();
		            	XMLGregorianCalendar fechaCompra = compra.getFechaCompra();
		            	Date fechaCompraDate = fechaCompra.toGregorianCalendar().getTime();
		            	Calendar cal = Calendar.getInstance();
		            	cal.setTime(fechaCompraDate);
		            	cal.add(Calendar.DATE, compra.getVencimiento());

		            	Calendar cal2 = Calendar.getInstance();
		            	cal2.setTime(new Date());

		            	Set<String> tiposPubli = cantidades.keySet();
		            	for(String tp : tiposPubli) {
		            		if(cal.after(cal2) && cantidades.get(tp)>0) {
		            			tiposDePublicacionSet.add(tp);
		            			paqueteTipoCantidadMap.put(compra.getPaquete().getNombre(),cantidades);
		            		}

		            	}
		            }

	            	session.setAttribute("paqueteTipoCantidadMap", paqueteTipoCantidadMap);


				    request.setAttribute("paquetesAdqVig", paqsEmpVig);
				    request.setAttribute("tiposDePublicacion", tiposDePublicacionSet);

					List<String> listaKeys = port.getKeywords().getItem();
					Set<String> setKeys = new HashSet<>(listaKeys);
					request.setAttribute("allKeys", setKeys);

					List<String> listaTipos = port.listarNombresTipos().getItem();
					Set<String> setTipos = new HashSet<>(listaTipos);
					request.setAttribute("allTipos", setTipos);

				    request.getRequestDispatcher("/WEB-INF/ofertas/formAltaOferta.jsp").forward(request, response);



				} catch (NoExisteEmpresa_Exception | NoExisteTipoPublicacion_Exception e) {
					request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").forward(request, response);
				}
		    }


		}
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
