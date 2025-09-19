package logica;

import java.util.Date;

import logicaDAO.OfertaDAO;
import logicaDAO.PostulacionDAO;
import logicaDAO.PostulanteDAO;

/**
 *
 */
public class Postulacion {
	private Postulante miPostulante;
	private Oferta miOferta;
	private String cVR;
	private String motivacion;
	private Date fecha;
	private String video;
	private int orden;
	private Date fechaSelec;

    /**
     * Default constructor
     */

	public Postulacion(Postulante pos, String cvRed, String motivacio, Date fech, String vid) {
    	miPostulante = pos;
    	cVR = cvRed;
    	motivacion = motivacio;
    	fecha = fech;
    	video = vid;
    	orden = 0;
    	fechaSelec = null;
	}

    public Postulacion(Postulante pos, String cvRed, String motivacio, Date fech) {
    	miPostulante = pos;
    	cVR = cvRed;
    	motivacion = motivacio;
    	fecha = fech;
    	orden = 0;
    	fechaSelec = null;
    }

    public Postulacion(Postulante pos, String cvRed, String motivacio, Date fech,String vid, int ord, Date fechS) {
    	miPostulante = pos;
    	cVR = cvRed;
    	motivacion = motivacio;
    	fecha = fech;
    	video = vid;
    	setOrden(ord);
    	setFechaSelec(fechS);
    }

    public void setVideo(String vid) {
    	video = vid;
    }

    public String getVideo() {
    	return video;
    }

    public DTPostulacion getDTPostulacion(){
    	DTOferta MiOf = miOferta.getDTOferta();
        DTPostulacion dtp = new DTPostulacion(fecha, cVR, motivacion, MiOf,video,orden,fechaSelec);
        return dtp;
    }

    public void getNombreOfertaPostulado() {
        // TODO implement here
    }

    /**
     * @param o
     */
    public void addOferta(Oferta ofe) {
        miOferta = ofe;
    }


    /**
     * @param postul
     */
    public void addPostulacion(Postulacion postul) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param value
     */
    public void setFecha(Date value) {
        fecha = value;
    }

    /**
     * @return
     */
    public String getCVR() {
        return cVR;
    }

    /**
     * @param value
     */
    public void setCVR(String value) {
        cVR = value;
    }

    /**
     * @return
     */
    public String getMotivacion() {
        return motivacion;
    }

    /**
     * @param value
     */
    public void setMotivacion(String value) {
        motivacion = value;
    }

    public Oferta getMiOferta() {
    	return miOferta;
    }

    public String getNicknamePostulante() {
    	return miPostulante.getNickname();
    }

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public Date getFechaSelec() {
		return fechaSelec;
	}

	public void setFechaSelec(Date fechaSelec) {
		this.fechaSelec = fechaSelec;
	}

	public PostulacionDAO crearPostulacionDAO(PostulanteDAO posDAO,OfertaDAO ofeDAO) {
		PostulacionDAO postuDAO = new PostulacionDAO(posDAO,cVR,motivacion,fecha,ofeDAO);
		if(video!=null) {
			postuDAO.setVideo(video);
		}
		return postuDAO;
	}


}