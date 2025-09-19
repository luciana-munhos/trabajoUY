package com.trabajouy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.trabajouy.model.EstadoSesion;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtEmpresa;
import servidor.DtOferta;
import servidor.DtUsuario;
import servidor.NoExisteEmpresa_Exception;
import servidor.NoExistePostulante_Exception;
import servidor.NoExistenEmpresas_Exception;

/**
 * Servlet implementation class busqueda
 */
public class busqueda extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public busqueda() {
        super();
        // TODO Auto-generated constructor stub
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	HttpSession session = request.getSession();

		if (session.getAttribute("estado_sesion") == null){
			session.setAttribute("estado_sesion",EstadoSesion.NO_LOGUEADO);
		}
    	String buscar = request.getParameter("buscar").toLowerCase();

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

    	List<DtEmpresa> empresas = new ArrayList<>();
		try {
			empresas = port.mostrarDTEmpresas().getItem();
		} catch (NoExisteEmpresa_Exception | NoExistePostulante_Exception | NoExistenEmpresas_Exception e) {

		}

    	for (Iterator<DtEmpresa> i = empresas.iterator(); i.hasNext();) {
    	    DtEmpresa emp = i.next();
    	    String descripcion = emp.getDescripcion().toLowerCase();
    	    String nickname = emp.getNickname().toLowerCase();
    	    String nombre = emp.getNombre().toLowerCase();
    	    String apellido = emp.getApellido();

    	    if (!descripcion.contains(buscar) && !nickname.contains(buscar) && !nombre.contains(buscar)
    	    		&& !apellido.contains(buscar) && !(nombre+apellido).contains(buscar)) {
    	        i.remove();
    	    }
    	}

    	request.setAttribute("empresas_busqueda", empresas);

    	List<DtOferta> ofertas = port.getOfertasVigentesOrdenadas().getItem();
    	for(Iterator<DtOferta> i = ofertas.iterator();i.hasNext();) {
    		DtOferta oferta = i.next();
    		String nombre = oferta.getNombre().toLowerCase();
    		String descripcion = oferta.getDescripcion().toLowerCase();

    		if(!nombre.contains(buscar) && !descripcion.contains(buscar))
    			i.remove();
    	}

    	request.setAttribute("ofertas_busqueda", ofertas);

		request.getRequestDispatcher("/WEB-INF/busqueda/busqueda.jsp").forward(request, response);

	}

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
