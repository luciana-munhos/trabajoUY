package com.trabajouy.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
import servidor.DtCompra;
import servidor.DtOferta;
import servidor.DtUsuario;
import servidor.NoExisteEmpresa_Exception;
import servidor.NoExisteKeyword_Exception;
import servidor.NoExisteOferta_Exception;
import servidor.NoExisteTipoPublicacion_Exception;
import servidor.NombreOfertaExistente_Exception;
import servidor.StringArray;

/**
 * Servlet implementation class altaOferta
 */
@MultipartConfig
public class altaOferta extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public altaOferta() {
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
	    HttpSession session = request.getSession();

	    boolean esMobile = request.getHeader("User-Agent").contains("Mobi");
	    if (esMobile || (session.getAttribute("estado_sesion") == null)){
			response.sendRedirect("/trabajouy/home");
			return;
	    }

	    if (session.getAttribute("estado_sesion") != EstadoSesion.LOGUEADO_EMPRESA) {
	    	request.getRequestDispatcher("/WEB-INF/errorPages/403.jsp").include(request, response);
			return;
	    }

	    if(esMobile) {
			request.getRequestDispatcher("/trabajouy/home").include(request, response);
			return;
		}

	    servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();

	    DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");

	    String nombreOferta = request.getParameter("nombreOferta");
	    String descripOferta = request.getParameter("descripcionOferta");
	    String departOferta = request.getParameter("departamentoSelect");
	    String ciudadOferta = request.getParameter("ciudadOferta");
	    String hInicioOferta = request.getParameter("hinicioOferta");
	    String hFinOferta = request.getParameter("hfinOferta");
	    String remOferta = request.getParameter("remuneracionOferta");

	    String imgOferta = "media/img/OfertasLaborales/default/default.jpg";
		String fileName = "default.jpg";

        System.out.println("imgOferta: " + imgOferta);
	    /*Imagen*/
		Part part = request.getPart("imgOferta");

		byte[] fileBytes = new byte[1];
		fileBytes[0] = '\0';
		if (part != null && part.getSize() > 0) {

			fileBytes = part.getInputStream().readAllBytes();
			fileName = part.getSubmittedFileName();
			String ext = "";
			int i = fileName.lastIndexOf('.');
			ext = fileName.substring(i);
			fileName = nombreOferta + ext;
		}else if (session.getAttribute("pathImg") != null) {
			imgOferta = (String) session.getAttribute("pathImg");
		}

		session.setAttribute("fileName", fileName);
		session.setAttribute("fileBytes", fileBytes);




        String[] selectKeywords = request.getParameterValues("selectedKeywords");
	    Set<String> keysOferta = new HashSet<>();
        StringArray keywordsArray = new StringArray();


	    if (selectKeywords != null) {
            for (String keywordNombre : selectKeywords) {
                keysOferta.add(keywordNombre);
            }
            for (String keyword : selectKeywords) {
                keywordsArray.getItem().add(keyword);
            }
        }

        String tpOferta = request.getParameter("tpOferta");
	    String tipoPago = request.getParameter("pago");
	    String horario = hInicioOferta + "-" + hFinOferta;

	    try {

		    String nick = usr.getNickname();

		    boolean ofertaExiste = false;
		    List<String> ofertasIngresadasEmp = port.mostrarOfertasDeEmpresaIngresadas(nick).getItem();

		    //SI LA OFERTA ES INGRESADA, NO SE HA PUBLICADO AUN Y SE PODRIA MODIFICAR
			ofertaExiste = ofertasIngresadasEmp.contains(nombreOferta);

	        request.setAttribute("ofertaExistente", ofertaExiste); 


        	/* EMPRESA YA TIENE OFERTA INGRESADA CON ESE NOMBRE, ESPERANDO A SER ACEPTADA -> SE OFRECE POSIBILIDAD DE MODIFICAR */
	        if (ofertaExiste) {
	        	session.setAttribute("nombreOferta", nombreOferta); 
	        	session.setAttribute("descripcionOferta", descripOferta);
	        	session.setAttribute("departamentoSelect", departOferta);
	        	session.setAttribute("ciudadOferta", ciudadOferta);
	        	session.setAttribute("hinicioOferta", hInicioOferta);
	        	session.setAttribute("hfinOferta", hFinOferta);
	        	session.setAttribute("remuneracionOferta", remOferta);
	        	session.setAttribute("imgOferta", imgOferta);
	        	session.setAttribute("keysOferta", keysOferta);
                
                response.sendRedirect("/trabajouy/formModificarOferta");
                return;
                

	        } else if (tipoPago.equals("pagoGeneral")) { //Se paga inmediatamente de forma general y se da de alta ya mismo
                System.out.println("Tipo de pago seleccionado: PAGO GENERAL");

	        	try {
	        		LocalDate fechaHoy = LocalDate.now();
	                GregorianCalendar gregorianCalendar = GregorianCalendar.from(fechaHoy.atStartOfDay(ZoneId.systemDefault()));
	                XMLGregorianCalendar fechaActualXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);

	        	    port.ingresarOferta(nick, tpOferta, nombreOferta, descripOferta, horario, Double.valueOf(remOferta), ciudadOferta, departOferta, fechaActualXML, keywordsArray, fileName, fileBytes);

			        request.getRequestDispatcher("/WEB-INF/ofertas/pagoRealizado.jsp").forward(request, response);

	        	} catch (NombreOfertaExistente_Exception e) {
	        		
	        		/* Lo mando indicando que ya existe una oferta que fue dada de alta con el mismo nombre */
	        		response.sendRedirect("/trabajouy/formAltaOferta?ya_existe=existe");	  
	        		return;

	        	} catch (NoExisteEmpresa_Exception | NoExisteTipoPublicacion_Exception | NoExisteKeyword_Exception | DatatypeConfigurationException e) {
	        		//EN ESTE CASO REDIRIGIR A PAGINA DE ERROR
					request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
					return;
	        	}

	        } else { //tipoPago == pagoConPaquete, se va a dar de alta tras seleccionar el paquete de pago
                System.out.println("Tipo de pago seleccionado: PAGO CON PAQUETE");

				
				/* 
				Esto lo hago para ver si lanza excepcion. Si lanza, es porque NO existe una oferta dada de alta con ese nombre.
				Y si es asi, no es de las pendientes tuyas (pues seguiste de largo, no se te ofrecio modificar), entonces
				no te queda otra que elegir otro nombre.
				Es como la contraparte del ingresarOferta que te lanza excepcion si el nombre de la oferta es existente. 
				*/
				try {
					DtOferta ofe = port.getDTOferta(nombreOferta);
					
					//Ya existe oferta con ese nombre
					request.setAttribute("ya_existe", "existe");
	        		request.getRequestDispatcher("/formAltaOferta?ya_existe=existe").forward(request, response);	
					
				} catch (NoExisteOferta_Exception e) {
					
					//No existe oferta con ese nombre, puedo proceder al pago con paquete y luego ingresar
					
					 	@SuppressWarnings("unchecked")
	 					Map<String, Map<String, Integer>> paqueteTipoCantidadMap = (Map<String, Map<String, Integer>>) session.getAttribute("paqueteTipoCantidadMap");

	 	            	request.setAttribute("selectedTipo", tpOferta);
	 				    request.setAttribute("paqueteTipoCantidadMap", paqueteTipoCantidadMap);

	 				    List<DtCompra> compras = port.getDTComprasEmpresa(nick).getItem();
	 			        Map<String, Integer> vencimientosPaquetes = new HashMap<>();

	 			        for (DtCompra compra : compras) {
	 			            String nombrePaquete = compra.getPaquete().getNombre();
	 			            int validezDias = compra.getPaquete().getValidez();

	 			            XMLGregorianCalendar fechaCompra = compra.getFechaCompra();
	 			            Date fechaCompraDate = fechaCompra.toGregorianCalendar().getTime();

	 			            //se calcula la fecha de vencimiento segun la fecha en que fue comprada y los dias antes del vencimiento
	 			            Date fechaDeHoy = new Date();
	 			            long diffInMilliseconds = fechaDeHoy.getTime() - fechaCompraDate.getTime();
	 			            long diasPasaron =  TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);

	 			            int diasRestantes = (int) (validezDias - diasPasaron);

	 			            vencimientosPaquetes.put(nombrePaquete, diasRestantes);
	 			        }

	 			        request.setAttribute("vencimientosPaquetes", vencimientosPaquetes);
	                	request.setAttribute("nombreOferta", nombreOferta); 	                	

	                	/* Los mando como atributo de la sesion para dar la oferta de alta una vez se haya pagado con el paquete */
	                	
	                	//Para mantener un control de que usuario esta haciendo la compra del paquete
	                	session.setAttribute("nickComprador", usr.getNickname());
	                	
	    			    session.setAttribute("descripcionOferta", descripOferta);
	    			    session.setAttribute("departamentoSelect", departOferta);
	    			    session.setAttribute("ciudadOferta", ciudadOferta);
	    			    session.setAttribute("hinicioOferta", hInicioOferta);
	    			    session.setAttribute("hfinOferta", hFinOferta);
	    			    session.setAttribute("remuneracionOferta", remOferta);
	    			    session.setAttribute("imgOferta", imgOferta);
	    			    session.setAttribute("keysOferta", keywordsArray);

	    			    
						try {
							LocalDate fechaHoy = LocalDate.now();
		                    GregorianCalendar gregorianCalendar = GregorianCalendar.from(fechaHoy.atStartOfDay(ZoneId.systemDefault()));
							XMLGregorianCalendar fechaActualXML;
							fechaActualXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
							session.setAttribute("fechaXML", fechaActualXML);
						} catch (DatatypeConfigurationException e1) {
							request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
							return;
						}
					
	 			        request.getRequestDispatcher("/WEB-INF/ofertas/pagoConPaquete.jsp").forward(request, response);
						
						

				} 
	        }
	    } catch (NoExisteEmpresa_Exception exc) {
			request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
			return;
	    }
	}


}
