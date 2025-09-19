package com.trabajouy.controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.trabajouy.model.EstadoSesion;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtOferta;
import servidor.DtUsuario;
import servidor.ErrorFecha;
import servidor.NoExisteEmpresa_Exception;
import servidor.NoExistePostulante_Exception;

/**
 * Servlet implementation class home
 */
public class home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static boolean datosCargados = false;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public home() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * inicializa la sesión si no estaba creada
	 * @param request
	 */
	public static void initSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("estado_sesion") == null) {
			session.setAttribute("estado_sesion", EstadoSesion.NO_LOGUEADO);
		}
	}

	/**
	 * Devuelve el estado de la sesión
	 * @param request
	 * @return
	 */
	public static EstadoSesion getEstado(HttpServletRequest request)
	{
		return (EstadoSesion) request.getSession().getAttribute("estado_sesion");
	}

	public boolean isValidURL(String s) throws IOException {
		URL url = new URL(s);
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		int responseCode = huc.getResponseCode();

		return responseCode != 404;
	}



    /**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
     * @throws ErrorFecha
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		initSession(request);


		servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();

        if(session.getAttribute("estado_sesion") == EstadoSesion.LOGUEADO_POSTULANTE) {
			DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");

			try {
				List<String> favs = port.getFavoritosUsuario(usr.getNickname()).getItem();
				request.setAttribute("favs", favs);
			} catch (NoExistePostulante_Exception e) {
				response.sendError(404);
				return;
			}
		}

        List<String> keys = port.getKeywords().getItem();

        request.setAttribute("set_keys",keys);

		//busco las ofertas
        List<DtOferta> ofertas = port.getOfertasVigentesOrdenadas().getItem();


        request.setAttribute("ofertas", ofertas);

        //busco las empresas con fotos para el visitante
        if(getEstado(request) == EstadoSesion.NO_LOGUEADO) {
        	List<String> empresas = port.mostrarEmpresas().getItem();
        	Map<String,String> imagenes  = new HashMap<>();

        	int iterador = 0;
        	for(String s : empresas) {
        		if(iterador==10) break;
        		iterador++;
    			String imagen;
				try {
					DtUsuario dtu = port.mostrarDatosUsuario(s);
					imagen = dtu.getImagen();
					if(imagen != null && !imagen.equals("")){
	    				imagenes.put(s, imagen);
					}
				} catch (NoExisteEmpresa_Exception | NoExistePostulante_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


    		}

        	request.setAttribute("map_imagenes_empresas", imagenes);
        }

        if(request.getHeader("User-Agent").contains("Mobi")) {
			EstadoSesion est = (EstadoSesion) request.getSession().getAttribute("estado_sesion");
			if(est == EstadoSesion.LOGUEADO_POSTULANTE) {
				request.getRequestDispatcher("/WEB-INF/home/homeMobile.jsp").forward(request, response);
			}
			else {
				request.setAttribute("invalid_data", false);
				request.getSession().setAttribute("estado_sesion", EstadoSesion.NO_LOGUEADO);
				request.getSession().setAttribute("usuario_logueado", null);
				request.getRequestDispatcher("/WEB-INF/form/formLoginMobile.jsp").forward(request, response);
			}
			return;
		}

		request.getRequestDispatcher("/WEB-INF/home/home.jsp").forward(request, response);


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
