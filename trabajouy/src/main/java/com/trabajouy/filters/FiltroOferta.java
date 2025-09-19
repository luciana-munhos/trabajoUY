package com.trabajouy.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import servidor.NoExisteOferta_Exception;

/**
 * Servlet Filter implementation class FiltroOferta
 */
public class FiltroOferta extends HttpFilter implements Filter {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public FiltroOferta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


	    chain.doFilter(request, response);
	    HttpServletRequest httpRequest = (HttpServletRequest) request;
	    String accept = httpRequest.getHeader("accept");

	    // Todas las request pasan por el filtro
	    // aseguro que sea solo la del text/html
	    if (accept != null && accept.toLowerCase().startsWith("text/html")) {
			String oferta = request.getParameter("nombre");

			servidor.PublicadorService service = new servidor.PublicadorService();
			servidor.Publicador port = service.getPublicadorPort();
			try {
				port.incrementarCantVisitas(oferta);
			} catch (NoExisteOferta_Exception e) {
				System.out.println(e.getMessage()); //????
			}

	    }

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
