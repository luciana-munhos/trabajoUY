package com.trabajouy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

/**
 * Servlet implementation class formModificarOferta
 */
public class formModificarOferta extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public formModificarOferta() {
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

		boolean esMobile = request.getHeader("User-Agent").contains("Mobi");
	    if(esMobile) {
	    	response.sendRedirect("/trabajouy/home");
			return;
		}

	    if (session.getAttribute("estado_sesion") != EstadoSesion.LOGUEADO_EMPRESA) {
			request.getRequestDispatcher("/WEB-INF/errorPages/403.jsp").forward(request, response);
			return;
	    }
	    
		DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");
		servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();
        
        
	    List<String> ofertasIngresadasEmp = new ArrayList<>();
		try {
			ofertasIngresadasEmp = port.mostrarOfertasDeEmpresaIngresadas(usr.getNickname()).getItem();
		} catch (NoExisteEmpresa_Exception e) {
			request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").forward(request, response);
			return;
		}

	    /* 
	    NO SE PUEDE MODIFICAR UNA OFERTA QUE NO SEA TUYA
	    
	    Esto es por si alguien pone el url e intenta modificar ILEGALMENTE, se va a chequear que esa oferta sea una ingresada (esperando a ser confirmada) que
	    le pertenezca al usuario presente, que ya se que es empresa pues si no arriba se lo expulso. 
	    */
	    if (!ofertasIngresadasEmp.contains(session.getAttribute("nombreOferta"))) {
            System.out.println("PROHIBIDO: No puedes modificar una oferta que no te pertenece.");

	    	request.getRequestDispatcher("/WEB-INF/errorPages/403.jsp").forward(request, response);
			return;
			
	    
	    
		} else {            
            /* Todo esto por si desea volver y seguir con el alta de oferta desde cero, para otro nombre */

    		List<String> listaKeys = port.getKeywords().getItem();
    		Set<String> keys = new HashSet<>(listaKeys);
			request.setAttribute("allKeys", keys);


            List<String> listaPaqsEmpVig;

		    if (usr != null) {
				try {
					listaPaqsEmpVig = port.mostrarComprasPaquetesEmpresa(usr.getNickname()).getItem();

					Set<String> paqsEmpVig = new HashSet<>(listaPaqsEmpVig);

					Set<DtPaquete> paqs = new HashSet<>();
					DtPaquete dtpaq = null;
					DtCompra dtc = null;
		            Set<String> tiposDePublicacionSet = new HashSet<>();

		            Set<String> paquetesDispo = new HashSet<>();
		            Map<String, Map<String, Integer>> paqueteTipoCantidadMap = new HashMap<>();

		            List<DtCompra> listMisCompras = port.getDTComprasEmpresa(usr.getNickname()).getItem();
					Set<DtCompra> misCompras = new HashSet<>(listMisCompras);

					for (String paquete: paqsEmpVig) {
							for (DtCompra compra : misCompras) {
							    if (compra.getPaquete().getNombre().equals(paquete)) {
							    	dtc = compra;
							        dtpaq = compra.getPaquete();
							        break;
							    }
							}
						if (dtpaq!= null) {
							paqs.add(dtpaq);
		                    tiposDePublicacionSet.addAll(dtpaq.getTiposPub());
		                    paquetesDispo.add(dtpaq.getNombre());

		                    Set<String> tiposEnCero = new HashSet<>(); // Aca voy a guardar los que borrar

		                        for (String tip : tiposDePublicacionSet) {
		                           // tipo = tip;

		                        	int cantidad = 0;
		    		            	CantActualTP cantidadActual = dtc.getCantActualTP();
		                            List<DtCompra.CantActualTP.Entry> entries = cantidadActual.getEntry();

		                            // encuentro el tipo buscado
		                            for (DtCompra.CantActualTP.Entry entry : entries) {
		                                if (entry.getKey().equals(tip)) {
		                                    // obtengo la cantidad, si esta
		                                    cantidad = entry.getValue();
		                                    break;
		                                }
		                            }
		                            if (cantidad == 0) {
		                                tiposEnCero.add(tip);  //Quedan 0 unidades, sera removido
		                            }

		                            paqueteTipoCantidadMap.computeIfAbsent(paquete, k -> new HashMap<>()).put(tip, cantidad);
		                        }
		                    // remuevo tipos con cantidad en 0
		                    tiposDePublicacionSet.removeAll(tiposEnCero);
		                    for (String paq : paqueteTipoCantidadMap.keySet()) {
		                        paqueteTipoCantidadMap.get(paq).keySet().removeAll(tiposEnCero);
		                    }
						}
					}

	            	session.setAttribute("paqueteTipoCantidadMap", paqueteTipoCantidadMap);

				    request.setAttribute("paquetesAdqVig", paqsEmpVig);
				    request.setAttribute("tiposDePublicacion", tiposDePublicacionSet);
				} catch (NoExisteEmpresa_Exception e) {
					request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").forward(request, response);
					return;
				}
		    }

			request.getRequestDispatcher("/WEB-INF/ofertas/formModificarOferta.jsp").forward(request, response);
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
