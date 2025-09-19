package logica;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 *
 */
public class TipoPublicacion {
	private Map<String, Oferta> ofertasDeMiTipo;
	private String nombre;
	private String descripcion;
	private int exposicion;
	private int duracion;
	private double costo;
	private Date fechaAlta;

    /**
     * Default constructor
     */
    public TipoPublicacion(String nom, String des, int exp, int dur, double cos, Date fAlta){
        nombre = nom;
    	descripcion = des;
    	exposicion = exp;
    	duracion = dur;
    	costo = cos;
    	fechaAlta = fAlta;
    	ofertasDeMiTipo = new HashMap<>();
    }



    /**
     * @return Retorna un DT con la informacion del tipo de publicacion
     */
    public DTTipoPublicacion getInfo() {
        DTTipoPublicacion dtp = new DTTipoPublicacion(nombre, descripcion, exposicion, duracion, costo, fechaAlta);
        return dtp;
    }

    /**
     * @return
     */
    public String getNombre() {
        // TODO implement here
        return nombre;
    }



    /**
     * @param o
     */
    public void asignarOferta(Oferta ofer) {
    	ofertasDeMiTipo.put(ofer.getNombre(), ofer);
    }

    /**
     * @param nombreT
     */
    public void setNombre(String nombreT) {
        // TODO implement here
    	nombre = nombreT;
    }

    /**
     * @return
     */
    public String getDescripcion() {
        // TODO implement here
        return descripcion;
    }

    /**
     * @param descT
     */
    public void setDescripcion(String descT) {
        // TODO implement here
    	descripcion = descT;
    }

    /**
     * @return
     */
    public int getExposicion() {
        // TODO implement here
        return exposicion;
    }

    /**
     * @param expT
     */
    public void setExposicion(int expT) {
        // TODO implement here
    	exposicion = expT;
    }

    /**
     * @return
     */
    public int getDuracion() {
        // TODO implement here
        return duracion;
    }

    /**
     * @param durT
     */
    public void setDuracion(int durT) {
        // TODO implement here
    	duracion = durT;
    }

    /**
     * @return
     */
    public double getCosto() {
        // TODO implement here
        return costo;
    }

    /**
     * @param costoT
     */
    public void setCosto(double costoT) {
        // TODO implement here
    	costo = costoT;
    }

    /**
     * @return
     */
    public Date getFechaAlta() {
        // TODO implement here
        return fechaAlta;
    }

    /**
     * @param fAlta
     */
    public void setFechaAlta(Date fAlta) {
        // TODO implement here
    	fechaAlta = fAlta;
    }

}