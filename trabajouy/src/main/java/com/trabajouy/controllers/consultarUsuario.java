package com.trabajouy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.trabajouy.model.EstadoSesion;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // sacare: dtcompra, dtoferta, dtpostulacion, dtpostulante, dt usuario, favrica, iusuario, oferta. estados
import servidor.DtCompra;
import servidor.DtOferta;
import servidor.DtPostulacion;
import servidor.DtPostulante;
import servidor.DtUsuario;
import servidor.NoExisteEmpresa_Exception;
import servidor.NoExisteOferta_Exception;
import servidor.NoExistePostulante_Exception;

/**
 * Servlet implementation class consultarUsuario
 */
public class consultarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public consultarUsuario() {
        super();
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();

		HttpSession session = request.getSession();

		boolean esMobile = request.getHeader("User-Agent").contains("Mobi");


		if (session.getAttribute("estado_sesion") == null){
			session.setAttribute("estado_sesion",EstadoSesion.NO_LOGUEADO);
	    }

		String nick = request.getParameter("nickname");
		if (nick == null) {
			request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
			return;
		}

		if(esMobile && session.getAttribute("estado_sesion")!=EstadoSesion.LOGUEADO_POSTULANTE) {
			response.sendRedirect("/trabajouy/home");
			return;
		}

		// ver si usuario consulta su perfil
		DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");
		boolean miPerfil = usr != null && nick.equals(usr.getNickname());

		if(miPerfil && session.getAttribute("estado_sesion")==EstadoSesion.LOGUEADO_POSTULANTE)
			try {
				request.setAttribute("favs", port.getFavoritosUsuario(nick).getItem());
			} catch (NoExistePostulante_Exception e) {
				request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
				return;
			}

		try {
			DtUsuario u = port.mostrarDatosUsuario(nick);
			request.setAttribute("usuario_consultado", u);

			// sus seguidos y seguidores
			List<DtUsuario> seguidos = new ArrayList<>();
			List<DtUsuario> seguidores = new ArrayList<>();

			List<String> nicks_seguidos = u.getSeguidos();
			List<String> nicks_seguidores = u.getSeguidores();

			DtUsuario dtu = null;
			for(String n: nicks_seguidos) {
				dtu = port.mostrarDatosUsuario(n);
				seguidos.add(dtu);
			}
			for(String n: nicks_seguidores) {
				dtu = port.mostrarDatosUsuario(n);
				seguidores.add(dtu);
			}

			request.setAttribute("seguidos", seguidos);
			request.setAttribute("seguidores", seguidores);

			if (u instanceof DtPostulante){
				request.setAttribute("usuario_tipo", "Postulante");
				if (miPerfil) {
					// mandar sus postulaciones
					List<DtPostulacion> postulaciones = new ArrayList<>();
					DtPostulante p = (DtPostulante) u;
					postulaciones = p.getPostulaciones();
					request.setAttribute("postulaciones_propias", postulaciones);

					// postulaciones clasificadas
					List<DtPostulacion> esperando_res = new ArrayList<>();
					List<DtPostulacion> sin_res = new ArrayList<>();
					List<DtPostulacion> con_res = new ArrayList<>();

					for (DtPostulacion dtp: postulaciones) {
						DtOferta oferta_asoc = dtp.getMiOferta();
						if (oferta_asoc.getEstado() != servidor.Estados.FINALIZADA) {
							if (dtp.getOrden() != 0) { // ya tiene resultado
								con_res.add(dtp);
							}else { // aun no han seleccionado postulantes o finalizado la oferta
								esperando_res.add(dtp);
							}
						}
					}
					sin_res = port.listarDTPostulacionesDB(nick).getItem();
					request.setAttribute("esperando_resultado", esperando_res);
					request.setAttribute("sin_resultado", sin_res);
					request.setAttribute("con_resultado", con_res);
				}

			}else { // es DtEmpresa
				request.setAttribute("usuario_tipo", "Empresa");

				// para mandar ofertas confirmadas
				List<DtOferta> of_confirmadas = new ArrayList<>();

				try {
					of_confirmadas = port.mostrarOfertasConfirmadas(nick).getItem();
					request.setAttribute("ofertas_confirmadas", of_confirmadas);
				}catch (NoExisteEmpresa_Exception e1) {
					request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
					return;
				}catch (NoExistePostulante_Exception e2) {
					request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
					return;
				}

				if (miPerfil) {
					// para mandar ofertas rechazadas, pendientes, confirmadas segun 2 clasificaciones, finalizadas
					List<DtOferta> ofertas = new ArrayList<>();
					List<DtOferta> of_rechazadas = new ArrayList<>();
					List<DtOferta> of_pendientes = new ArrayList<>();
					List<DtOferta> of_finalizadas = new ArrayList<>();
					List<DtOferta> of_conf_noVigentes = new ArrayList<>();
					List<DtOferta> of_conf_Vigentes = new ArrayList<>();
					try{
						ofertas = port.mostrarInfoBasicaOfertas(nick).getItem();

						// mando ofertas confirmadas separadas segun vigencia
						of_conf_noVigentes = port.mostrarOfertasConfirmadasNoVigentes(nick).getItem();
						of_conf_Vigentes = port.mostrarOfertasConfirmadasVigentes(nick).getItem();
					}catch(NoExistePostulante_Exception | NoExisteEmpresa_Exception e) {
						request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
						return;
					}

					// recorro y voy agregando rechazadas o pendientes
					for (DtOferta o : ofertas) {
						servidor.Estados est = o.getEstado();
						if (est == servidor.Estados.RECHAZADA) {
							of_rechazadas.add(o);
						}else if (est == servidor.Estados.INGRESADA) {
							of_pendientes.add(o);
						}
					}

					of_finalizadas = port.listarOfertasFinalizadasAsoc(nick).getItem();

					// mando ofertas confirmadas separadas segun ranking
					List<DtOferta> of_confi_ranking = new ArrayList<>();
					List<DtOferta> of_confi_sin_ranking = new ArrayList<>();
					DtPostulacion dtp = null;
					List<String> nicks = null;
					for (DtOferta ofi: of_confirmadas) {
						nicks = ofi.getPostul();
						if (nicks.isEmpty()) { // no tiene postulantes -> no tiene ranking
							of_confi_sin_ranking.add(ofi);
						}else {
							dtp = port.getDTPostulacion(nicks.get(0), ofi.getNombre());
							if (dtp.getOrden() == 0) { // orden == 0 -> oferta sin ranking
								of_confi_sin_ranking.add(ofi);
							}else {
								of_confi_ranking.add(ofi);
							}
						}
					}

					// mando las ofertas para el perfil
					request.setAttribute("ofertas_rechazadas",of_rechazadas);
					request.setAttribute("ofertas_pendientes",of_pendientes);
					request.setAttribute("ofertas_finalizadas",of_finalizadas);
					request.setAttribute("ofertas_confi_ranking",of_confi_ranking);
					request.setAttribute("ofertas_confi_sin_ranking",of_confi_sin_ranking);
					request.setAttribute("ofertas_confirmadas_NoVigentes", of_conf_noVigentes);
					request.setAttribute("ofertas_confirmadas_Vigentes", of_conf_Vigentes);

					// para mandar paquetes de empresa
					try{
						List<DtCompra> compras = port.getDTComprasEmpresa(nick).getItem();
						request.setAttribute("paquetes_propios", compras);
					}catch(NoExisteEmpresa_Exception e) {
						request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
						return;
					}
				}

			}
			if(esMobile)
				request.getRequestDispatcher("/WEB-INF/usuarios/consultarUsuarioMobile.jsp").forward(request, response);
			else
				request.getRequestDispatcher("/WEB-INF/usuarios/consultarUsuario.jsp").forward(request, response);

		}catch(NoExisteEmpresa_Exception | NoExistePostulante_Exception | NoExisteOferta_Exception e1) {
			request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
			return;
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
