package logica;

import java.util.Date;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTTipoPublicacion {
    @XmlElement
	private String nombre;
    @XmlElement
    private String descripcion;
    @XmlElement
    private int exposicion;
    @XmlElement
    private int duracion;
    @XmlElement
    private double costo;
    @XmlElement
    private Date fechaAlta;

    /**
     * Default constructor
     */
    public DTTipoPublicacion() {
    }

    /**
     * Constructor con parametros
     * @param nombre nombre del tipo de publicacion
     * @param descripcion descripcion del tipo de publicacion
     * @param exposicion exposicion del tipo de publicacion
     * @param duracion duracion del tipo de publicacion
     * @param costo costo del tipo de publicacion
     * @param fechaAlta fecha de alta del tipo de publicacion
     */
    public DTTipoPublicacion(String nombr, String descripcio, int exposicio, int duracio, double cost, Date fechaAlt) {
        nombre = nombr;
        descripcion = descripcio;
        exposicion = exposicio;
        duracion = duracio;
        costo = cost;
        fechaAlta = fechaAlt;
    }

    /**
     *
     * @return Retorna el nombre del tipo de publicacion
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return Retorna la descripcion del tipo de publicacion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @return Retorna la exposicion del tipo de publicacion
     */
    public int getExposicion() {
        return exposicion;
    }

    /**
     *
     * @return Retorna la duracion del tipo de publicacion
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     *
     * @return Retorna el costo del tipo de publicacion
     */
    public double getCosto() {
        return costo;
    }

    /**
     *
     * @return Retorna la fecha de alta del tipo de publicacion
     */
    public Date getFechaAlta() {
        return fechaAlta;
    }

    /**
     *
     * @param nom Setea el nombre del tipo de publicacion
     */
    public void setNombre(String nom) {
        nombre = nom;
    }

    /**
     *
     * @param desc Setea la descripcion del tipo de publicacion
     */
    public void setDescripcion(String desc) {
        descripcion = desc;
    }

    /**
     *
     * @param exp Setea la exposicion del tipo de publicacion
     */
    public void setExposicion(int exp) {
        exposicion = exp;
    }

    /**
     *
     * @param dur Setea la duracion del tipo de publicacion
     */
    public void setDuracion(int dur) {
        duracion = dur;
    }

    /**
     *
     * @param cost Setea el costo del tipo de publicacion
     */
    public void setCosto(double cost) {
        costo = cost;
    }

    /**
     *
     * @param fecha Setea la fecha de alta del tipo de publicacion
     */
    public void setFechaAlta(Date fecha) {
        fechaAlta = fecha;
    }
}
