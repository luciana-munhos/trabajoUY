package logica;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class DTPaquete {
	@XmlElement
    private String nombre;
	@XmlElement
	private String descripcion;
	@XmlElement
	private int validez;
	@XmlElement
	private double descuento;
	@XmlElement
	private double costo;
	@XmlElement
	private Date fechaAlta;
	@XmlElement
	private Set<String> tiposPub;
	@XmlElement
	private String imagen;
	@XmlElement
	private Map<String,Integer> cantTotales;



    /**
     * Default constructor
     */
    public DTPaquete() {
    }

    /**
     * Constructor con parametros
     * @param nombre nombre del paquete
     * @paramdescripciondescripcion del paquete
     * @param validez validez del paquete
     * @param descuento descuento del paquete
     * @param costo costo del paquete
     * @param fechaAlta fecha de alta del paquete
     * @param tiposPub  tipos de publicacion del paquete
     * @param cantidadOfertas   cantidad de ofertas del paquete
     */
    public DTPaquete(String nombr, String des, int valide, double descuent, double cost, Date fechaAlt, Set<String> tiposPu) {
        nombre = nombr;
        descripcion= des;
        validez = valide;
        descuento = descuent;
        costo = cost;
        fechaAlta = fechaAlt;
        tiposPub = tiposPu;
        cantTotales = new HashMap<>();
        imagen = "";
    }


    // constructor NUEVO con Imagen
    public DTPaquete(String nom, String descripcio, int valide, double descuent, double cost, Date fechaAlt, Set<String> tiposPu, String ima) {
        nombre = nom;
        descripcion= descripcio;
        validez = valide;
        descuento = descuent;
        costo = cost;
        fechaAlta = fechaAlt;
        tiposPub = tiposPu;
        imagen = ima;
        cantTotales = new HashMap<>();
    }

    public DTPaquete(String nombre, String descripcion, int validez, double descuento, double costo, Date fechaAlta, Set<String> tiposPub, String ima, Map<String,Integer> tot) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.validez = validez;
        this.descuento = descuento;
        this.costo = costo;
        this.fechaAlta = fechaAlta;
        this.tiposPub = tiposPub;
        imagen = ima;
        cantTotales = tot;
    }

    /**
     *
     * @return Retorna el nombre del paquete
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return Retorna ladescripciondel paquete
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @return Retorna la validez del paquete
     */
    public int getValidez() {
        return validez;
    }

    /**
     *
     * @return Retorna el descuento del paquete
     */
    public double getDescuento() {
        return descuento;
    }

    /**
     *
     * @return Retorna el costo del paquete
     */
    public double getCosto() {
        return costo;
    }

    /**
     *
     * @return Retorna la fecha de alta del paquete
     */
    public Date getFechaAlta() {
        return fechaAlta;
    }

    /**
     *
     * @return Retorna los tipos de publicacion del paquete
     */
    public Set<String> getTiposPub() {
        return tiposPub;
    }

    public String getImagen() {
    	return imagen;
    }

    public Map<String, Integer> getCantTotales() {
		return cantTotales;
    }
   public void setNombre(String nom) {
       nombre = nom;
   }


   public void setDescripcion(String des) {
       descripcion = des;
   }


   public void setValidez(int val) {
       validez = val;
   }


   public void setDescuento(double des) {
       descuento = des;
   }


   public void setCosto(double cos) {
       costo = cos;
   }


   public void setFechaAlta(Date dia) {
       fechaAlta = dia;
   }


   public void setTiposPub(Set<String> str) {
       tiposPub = str;
   }

   public void setImagen(String img) {
   	imagen = img;
   }

   public void setCantTotales(Map<String,Integer> cant) {
		cantTotales = cant;
	}
}
