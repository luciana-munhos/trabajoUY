package logica;

import java.util.Date;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;



@XmlAccessorType(XmlAccessType.FIELD)
public class DTPostulacion {
    @XmlElement
    private Date fecha;
    @XmlElement
    private String cVR;
    @XmlElement
    private String motivacion;
    @XmlElement
    private DTOferta miOferta;
    @XmlElement
    private String video;
    @XmlElement
    private int orden;
    @XmlElement
    private Date fechaSelec;
    //default constructor
    public DTPostulacion() {
    	fecha = new Date();
    	cVR = "";
    	motivacion = "";
    	miOferta = null;
    	video = "";
    	fechaSelec = new Date();
    	orden = 0;
    }

  //constructor
    public DTPostulacion(Date date, String cvr, String moti) {
        fecha = date;
        cVR = cvr;
        motivacion = moti;
        orden = 0;
        fechaSelec = null;
    }

    //constructor
    public DTPostulacion(Date date, String cvr, String moti, String vid) {
        fecha = date;
        cVR = cvr;
        motivacion = moti;
        video = vid;
        orden = 0;
        fechaSelec = null;
    }

    // constructor nuevo con DTOFERTA
    public DTPostulacion(Date date, String cvr, String motivacion,  DTOferta MiOf) {
        fecha = date;
        cVR = cvr;
        this.motivacion = motivacion;
        miOferta = MiOf;
        video = null;
        orden = 0;
        fechaSelec = null;
    }

    public DTPostulacion(Date date, String cvr, String motivacion, DTOferta MiOf, String vid) {
        fecha = date;
        cVR = cvr;
        this.motivacion = motivacion;
        miOferta = MiOf;
        video = vid;
        orden = 0;
        fechaSelec = null;
    }

    public DTPostulacion(Date date, String cvr, String motivacion, DTOferta MiOf, String vid, int ord, Date fS) {
        fecha = date;
        cVR = cvr;
        this.motivacion = motivacion;
        miOferta = MiOf;
        video = vid;
        orden = ord;
        fechaSelec = fS;
    }

    public DTPostulacion(Date date, String cvr, String motivacion, DTOferta MiOf, int ord, Date fS) {
        fecha = date;
        cVR = cvr;
        this.motivacion = motivacion;
        miOferta = MiOf;
        video = null;
        orden = ord;
        fechaSelec = fS;
    }

    //getters
    public Date getFecha() {
        return fecha;
    }

    public String getCVR() {
        return cVR;
    }

    public String getMotivacion() {
        return motivacion;
    }

    public DTOferta getMiOferta() {
    	return miOferta;
    }
    public String getVideo() {
    	return video;
    }

    //setters
    public void setFecha(Date fec) {
    	fecha = fec;
    }
    public void setcvr(String cvr) {
    	cVR = cvr;
    }
    public void setMot(String mot) {
    	motivacion = mot;
    }
    public void setVid(String vid) {
    	video = vid;
    }
    public void setOferta(DTOferta miO) {
    	miOferta = miO;
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

}
