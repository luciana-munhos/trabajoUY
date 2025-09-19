package com.trabajouy.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

// genera el pdf
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import servidor.DtPostulacion;
import servidor.DtPostulante;

public class pdfGenerator {

	public pdfGenerator() {
	}
	public void generatePDF(String outputFilePath, DtPostulacion postu, DtPostulante dtp, String realPath) {
		// para fechas
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		GregorianCalendar gregC;
		Date date;
		String fechaFormateada;

	    try {
	        Document doc = new Document(PageSize.A4);
	        PdfWriter.getInstance(doc, new FileOutputStream(outputFilePath));
	        doc.open();

	        doc.addTitle("Información de la Postulación");

	        // logo
	        String path = realPath + File.separator + "media" + File.separator + "img" + File.separator + "logo-sin-fondo.png";
	        Image image = Image.getInstance(path);
	        image.scaleToFit(160, 160);
	        float x = (PageSize.A4.getWidth() - image.getScaledWidth()) / 2; // pos horizontal
	        float y = PageSize.A4.getHeight() - 80; // pos vertical
	        image.setAbsolutePosition(x, y);
	        doc.add(image);
	        // fin logo

	        doc.add(new Paragraph("\n\n\n\n"));

	        // Título
	        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13); // Establece el tipo de fuente en negrita y el tamaño
	        Paragraph title = new Paragraph();
	        Chunk chunk = new Chunk("Información de la Postulación a " + postu.getMiOferta().getNombre(), boldFont);
	        title.add(chunk);
	        title.setAlignment(Element.ALIGN_CENTER);
	        doc.add(title);


	        doc.add(new Paragraph("\n\n"));

	        // Información del postulante
	        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);

	        Paragraph postulanteInfo = new Paragraph("Nombre del postulante: " + dtp.getNombre() + " " + dtp.getApellido(), normalFont);
	        postulanteInfo.setIndentationLeft(40); // Margen izquierdo
	        doc.add(postulanteInfo);

	        Paragraph empresaInfo = new Paragraph("Nombre de la empresa: " + postu.getMiOferta().getNickEmpresa(), normalFont);
	        empresaInfo.setIndentationLeft(40); // Margen izquierdo
	        doc.add(empresaInfo);

	        Paragraph ofertaInfo = new Paragraph("Nombre de la oferta laboral: " + postu.getMiOferta().getNombre(), normalFont);
	        ofertaInfo.setIndentationLeft(40); // Margen izquierdo
	        doc.add(ofertaInfo);

	        Paragraph resultadoInfo = new Paragraph("Resultado de la selección: " + postu.getOrden(), normalFont);
	        resultadoInfo.setIndentationLeft(40); // Margen izquierdo
	        doc.add(resultadoInfo);

	        // Fecha postulación
	        gregC = postu.getFecha().toGregorianCalendar();
            date = gregC.getTime();
            fechaFormateada = dateFormat.format(date);
            String fechaPos = fechaFormateada;

	        // Fecha resultado
            String fechaRes = "";
            if (postu.getFechaSelec() != null) { // siempre verdadero en las que ya se hizo ranking
            	gregC = postu.getFechaSelec().toGregorianCalendar();
                date = gregC.getTime();
                fechaFormateada = dateFormat.format(date);
                fechaRes = fechaFormateada;
            }

	        Paragraph fechaPosInfo = new Paragraph("Fecha de postulación: " + fechaPos, normalFont);
	        fechaPosInfo.setIndentationLeft(40); // Margen izquierdo
	        doc.add(fechaPosInfo);

	        Paragraph fechaResInfo = new Paragraph("Fecha del resultado: " + fechaRes, normalFont);
	        fechaResInfo.setIndentationLeft(40); // Margen izquierdo
	        doc.add(fechaResInfo);


	        doc.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
