package logicaDAO;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import logica.DTOferta;
import logica.DTPostulacion;


@Entity

//Definimos un nombre específico para el nombre de la tabla. Por defecto sería EmpresaDAO.
@Table(name="Postulaciones")

public class PostulacionDAO {
	@EmbeddedId
    private clavePrimaria idpostulacion;

	@Column(name = "fecha_postulacion", nullable = false)
    private Date fecha;

	@Column(length = 1000, nullable = false) // Ajusta el tamaño según tus necesidades
    private String cVR;

	@Column(length = 1000, nullable = false) // Ajusta el tamaño según tus necesidades
    private String motivacion;

	private String video;


	@ManyToOne
    @MapsId("idpostulante")
    @JoinColumn(name = "ID_POSTULANTE", nullable = false)
    private PostulanteDAO idpostulante;

    @ManyToOne
    @MapsId("idoferta")
    @JoinColumn(name = "ID_OFERTA", nullable = false)
    private OfertaDAO idoferta;


	 public PostulacionDAO() {}

	 public PostulacionDAO(PostulanteDAO pos, String cvRed, String motivacio, Date fech,OfertaDAO oferta) {
		 	setIdoferta(oferta);
		 	setIdpostulante(pos);
	    	cVR = cvRed;
	    	motivacion = motivacio;
	    	fecha = fech;
	    }


	public PostulanteDAO getIdPostulante() {
		return getIdpostulante();
	}

	public void setIdPostulante(PostulanteDAO idPostulante) {
		this.setIdpostulante(idPostulante);
	}
	public OfertaDAO getIdOferta() {
		return getIdoferta();
	}

	public void setIdOferta(OfertaDAO idOferta) {
		this.setIdoferta(idOferta);
	}


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

		public String getVideo() {
			return video;
		}

		public void setVideo(String video) {
			this.video = video;
		}

		public PostulanteDAO getIdpostulante() {
			return idpostulante;
		}

		public void setIdpostulante(PostulanteDAO idpostulante) {
			this.idpostulante = idpostulante;
		}

		public OfertaDAO getIdoferta() {
			return idoferta;
		}

		public void setIdoferta(OfertaDAO idoferta) {
			this.idoferta = idoferta;
		}

		public DTPostulacion getDTPostulacion() {
			DTOferta ofe = idoferta.getDTOferta();
			DTPostulacion pos = new DTPostulacion(fecha,cVR,motivacion,ofe,video);
			return pos;
		}
}
