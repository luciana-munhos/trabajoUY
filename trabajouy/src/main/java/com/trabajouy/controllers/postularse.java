package com.trabajouy.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trabajouy.model.EstadoSesion;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtUsuario;
import servidor.ErrorFecha_Exception;
import servidor.NoExisteOferta_Exception;
import servidor.NoExistePostulante_Exception;
import servidor.OfertaExpirada_Exception;
import servidor.PostulantePoseeOferta_Exception;

/**
 * Servlet implementation class postularse
 */
public class postularse extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public postularse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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

		if ( session.getAttribute("estado_sesion").equals(EstadoSesion.LOGUEADO_POSTULANTE)) {
			servidor.PublicadorService service = new servidor.PublicadorService();
	        servidor.Publicador port = service.getPublicadorPort();
			String cv = request.getParameter("cv_abreviado");
			String motivacion = request.getParameter("motivacion");
			String vid = request.getParameter("video");
			if (vid != null && vid.length()>0) {
				vid = obtenerCodigoDeVideo(vid);
			}else {
				vid = "";
			}
			String oferta = request.getParameter("oferta");
			DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");
			String nick = usr.getNickname();
			try {
				//Falta pasarle fecha
				port.postularAOferta(nick, cv, motivacion, "", vid, oferta);
				String url = "/trabajouy/home?postulado=1";
				response.sendRedirect(url);
			}catch (OfertaExpirada_Exception | ErrorFecha_Exception e) {
				String url = "/trabajouy/listarOfertas?postulado=0";
				response.sendRedirect(url);
			} catch (NoExistePostulante_Exception | NoExisteOferta_Exception e) {
				request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").forward(request, response);
				return;
			} catch (PostulantePoseeOferta_Exception e) {
				request.getRequestDispatcher("/WEB-INF/errorPages/403.jsp").forward(request, response);
				return;
			}
		}else {
			request.getRequestDispatcher("/WEB-INF/errorPages/403.jsp").forward(request, response);
			return;
		}
	}

	private static String obtenerCodigoDeVideo(String url) {
        String codigoVideo = null;
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F|%2Fe%2F|embed%3A|watch\\?v=|\\/watch\\?v=|%2Fv%2F|\\/e\\/|watch\\?feature=player_embedded&v=|e\\/|v=|u\\?v=)([^#\\&\\?\\n]*)";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            codigoVideo = matcher.group();
        }

        return codigoVideo;
    }

}
