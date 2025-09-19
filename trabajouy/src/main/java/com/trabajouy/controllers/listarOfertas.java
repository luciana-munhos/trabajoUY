package com.trabajouy.controllers;

import java.io.IOException;
import java.util.List;

import com.trabajouy.model.EstadoSesion;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtOferta;
import servidor.DtUsuario;
import servidor.NoExistePostulante_Exception;
/**
 * Servlet implementation class listarOfertas
 */
public class listarOfertas extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public listarOfertas() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
    	HttpSession session = request.getSession();

		if (session.getAttribute("estado_sesion") == null){
			response.sendRedirect("/trabajouy/home");
			return;
	    }

		//busco las ofertas
		servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();

		if(session.getAttribute("estado_sesion") == EstadoSesion.LOGUEADO_POSTULANTE) {
			DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");

			try {
				List<String> favs = port.getFavoritosUsuario(usr.getNickname()).getItem();
				request.setAttribute("favs", favs);
			} catch (NoExistePostulante_Exception e) {
				request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
				return;
			}
		}




        List<DtOferta> ofertas = port.getOfertasVigentesOrdenadas().getItem();
		//order by exposicion de tipo de publicacion
        request.setAttribute("ofertas", ofertas);
        if(request.getHeader("User-Agent").contains("Mobi"))
        	request.getRequestDispatcher("/WEB-INF/ofertas/listarOfertasMobile.jsp").forward(request, response);
        else
        	request.getRequestDispatcher("/WEB-INF/ofertas/listarOfertas.jsp").forward(request, response);
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
