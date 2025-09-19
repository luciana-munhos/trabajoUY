package com.trabajouy.controllers;

import java.io.IOException;

import javax.xml.datatype.XMLGregorianCalendar;

import com.trabajouy.model.EstadoSesion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtUsuario;
import servidor.NoExisteEmpresa_Exception;
import servidor.NoExisteKeyword_Exception;
import servidor.NoExisteOferta_Exception;
import servidor.NoExistePaquete_Exception;
import servidor.NoExisteTipoPublicacion_Exception;
import servidor.NoHayTPRestantes_Exception;
import servidor.NombreOfertaExistente_Exception;
import servidor.StringArray;




/**
 * Servlet implementation class pagoConPaquete
 */
@MultipartConfig
public class pagoConPaquete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public pagoConPaquete() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

    	HttpSession session = request.getSession();

    	boolean esMobile = request.getHeader("User-Agent").contains("Mobi");
	    if (esMobile || (session.getAttribute("estado_sesion") == null)){
			response.sendRedirect("/trabajouy/home");
			return;
	    }

	    if (session.getAttribute("estado_sesion") != EstadoSesion.LOGUEADO_EMPRESA) {
			request.getRequestDispatcher("/WEB-INF/errorPages/403.jsp").forward(request, response);
			return;
	    }
	    
	    servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();

		DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");

		/* Si no hay comprador o el que esta entrando no es el comprador, entonces no puede ingresar aqui */
	    if (session.getAttribute("nickComprador") == null || session.getAttribute("nickComprador") != usr.getNickname()) {
	    	request.getRequestDispatcher("/WEB-INF/errorPages/403.jsp").forward(request, response);
			return;
	    }
	    
	    
	    String selectedTipo = request.getParameter("selectedTipo");
	    String nombrePaq = request.getParameter("nombrePaq");
	    String nomOferta = request.getParameter("nombreOferta");
	    
	    /* 
	    Es medio cualquier cosa este control pero lo que hace es no permitir que alguien entre a pagoConPaquete mediante el url, pues selectedTipo va a ser null
	    si no se accedio a esta pagina desde la seleccion de paquete en el pago con paquete
	    */
	    
	    if (selectedTipo == null) {
            System.out.println("PROHIBIDO: No puedes entrar.");
	    	request.getRequestDispatcher("/WEB-INF/errorPages/403.jsp").forward(request, response);
			return;
	    } 

	    String descripOferta = (String) session.getAttribute("descripcionOferta");
	    String departOferta = (String) session.getAttribute("departamentoSelect");
	    String ciudadOferta = (String) session.getAttribute("ciudadOferta");
	    String hInicioOferta = (String) session.getAttribute("hinicioOferta");
	    String hFinOferta = (String) session.getAttribute("hfinOferta");
	    String remOferta = (String) session.getAttribute("remuneracionOferta");
	    XMLGregorianCalendar fechaXML = (XMLGregorianCalendar) session.getAttribute("fechaXML");
	    StringArray keywords = (StringArray) session.getAttribute("keysOferta");
	    String horario = hInicioOferta + "-" + hFinOferta;


	    String fileName = (String) session.getAttribute("fileName");
		byte[] fileBytes = (byte[]) session.getAttribute("fileBytes");


		try {
    	    port.ingresarOferta(usr.getNickname(), selectedTipo, nomOferta, descripOferta, horario, Double.valueOf(remOferta), ciudadOferta, departOferta, fechaXML, keywords, fileName, fileBytes);
			port.pagarConPaquete(nomOferta, nombrePaq, selectedTipo, usr.getNickname());


		} catch (NoExisteEmpresa_Exception | NoExisteOferta_Exception | NoExistePaquete_Exception
				| NoExisteTipoPublicacion_Exception | NoHayTPRestantes_Exception | NumberFormatException | NoExisteKeyword_Exception | NombreOfertaExistente_Exception e) {

			response.sendError(404);
			request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
			return;
		}

        request.getRequestDispatcher("/WEB-INF/ofertas/pagoRealizado.jsp").forward(request, response);
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
