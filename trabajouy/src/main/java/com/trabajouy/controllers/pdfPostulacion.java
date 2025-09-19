package com.trabajouy.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.trabajouy.model.EstadoSesion;
import com.trabajouy.pdf.pdfGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidor.DtPostulacion;
import servidor.DtPostulante;
import servidor.DtUsuario;

/**
 * Servlet implementation class pdfPostulacion
 */
public class pdfPostulacion extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public pdfPostulacion() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();
		HttpSession session = request.getSession();

		if (session.getAttribute("estado_sesion") == null){
			session.setAttribute("estado_sesion", EstadoSesion.NO_LOGUEADO);
	    }

		// generar pdf
		DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");
		String nomOferta = request.getParameter("oferta_pdf");
		DtPostulante dtp = (DtPostulante) usr;
		List<DtPostulacion> postus = dtp.getPostulaciones();
		String nomOf = "";
		int i = 0;
		while (i < postus.size() && !nomOf.equals(nomOferta)) {
			nomOf = postus.get(i).getMiOferta().getNombre();
			i++;
		}
    	i--;
    	DtPostulacion postu = postus.get(i);
    	pdfGenerator pdf = new pdfGenerator();

    	String uploadPath = getServletContext().getRealPath("") + File.separator + "media" + File.separator + "pdfs";
    	File uploadDir = new File(uploadPath);
		if (!uploadDir.exists())
			uploadDir.mkdir();
		uploadPath += File.separator + "infoPostulacion.pdf";
		String nick = usr.getNickname();
    	pdf.generatePDF(uploadPath,postu,dtp,getServletContext().getRealPath(""));

    	/// arranca posta
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=infoPostulacion.pdf");

        try(InputStream in = new FileInputStream(uploadPath);
          OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[1024];

            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        }

        // BORRAR ARCHIVO
        File f = new File(uploadPath);
        f.delete();

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
