package com.trabajouy.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.trabajouy.model.EstadoSesion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import servidor.DtUsuario;
import servidor.NoExisteEmpresa_Exception;
import servidor.NoExisteKeyword_Exception;
import servidor.NoExisteOferta_Exception;
import servidor.StringArray;

/**
 * Servlet implementation class modificarOferta
 */
@MultipartConfig
public class modificarOferta extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public modificarOferta() {
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

		if (session.getAttribute("estado_sesion") == null){
			response.sendRedirect("/trabajouy/home");
			return;
	    }

	    if (session.getAttribute("estado_sesion") != EstadoSesion.LOGUEADO_EMPRESA) {
			request.getRequestDispatcher("/WEB-INF/errorPages/403.jsp").forward(request, response);
			return;
	    }

	    boolean esMobile = request.getHeader("User-Agent").contains("Mobi");
	    if(esMobile) {
	    	response.sendRedirect("/trabajouy/home");
			return;
		}

	    DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");

	    servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();

	    String nombreOferta = request.getParameter("nombreOferta");
	    String descripOferta = request.getParameter("descripcionOferta");
	    String departOferta = request.getParameter("departamentoSelect");
	    String ciudadOferta = request.getParameter("ciudadOferta");
	    String hInicioOferta = request.getParameter("hinicioOferta");
	    String hFinOferta = request.getParameter("hfinOferta");
	    String remOferta = request.getParameter("remuneracionOferta");
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

	    String horario = hInicioOferta + "-" + hFinOferta;

	    String nick = usr.getNickname();

	    String imgOferta = "media/img/OfertasLaborales/default/default.jpg";
		String fileName = "default.jpg";

	    Part part = request.getPart("imgOferta");

	    /*Imagen*/
	    byte[] fileBytes = new byte[1];
		fileBytes[0] = '\0';
	    if (part != null && part.getSize() > 0) {
	    	fileBytes = part.getInputStream().readAllBytes();
			fileName = part.getSubmittedFileName();
			String ext = "";
			int i = fileName.lastIndexOf('.');
			ext = fileName.substring(i);
			fileName = nombreOferta + ext;
		} else if (session.getAttribute("pathImg") != null) {
			imgOferta = (String) session.getAttribute("pathImg");
		}


	    /* MODIFICACION DE LA OFERTA LABORAL */
		try {
			port.modificarOferta(nick, nombreOferta, descripOferta, horario, Double.valueOf(remOferta), ciudadOferta, departOferta, keywordsArray, fileName, fileBytes);
			String consultarOfertaURL = request.getContextPath() + "/consultarOferta?nombre=" + nombreOferta;
		    response.sendRedirect(consultarOfertaURL);


		} catch ( NoExisteOferta_Exception | NoExisteEmpresa_Exception | NoExisteKeyword_Exception e) {
			request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
			return;
		}

	}

}
