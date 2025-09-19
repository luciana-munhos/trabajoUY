package logica;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class Paquete {
    private Map<String,Compra> mapCompras;
	private Set<Compra> misCompras;
    private String imagen;
    private Set<CantidadPorTipo> misCantidadPorTipos;
    private String nombre;
    private String descripcion;
    private int validez;
    private double descuento;
    private double costo;
    private Date fechaAlta;


    /**
     * Default constructor
     */
    public Paquete(String nom, String desc, int val, double des, Date fAlta) {
    	nombre = nom;
    	descripcion = desc;
    	validez = val;
    	descuento = des;
    	fechaAlta = fAlta;
    	costo = 0;
        misCantidadPorTipos = new HashSet<>();
        misCompras = new HashSet<>();
        mapCompras = new HashMap<>();
    }

    public Paquete(String nom, String desc, int val, double des, Date fAlta, String img) {
    	nombre = nom;
    	descripcion = desc;
    	validez = val;
    	descuento = des;
    	fechaAlta = fAlta;
        misCantidadPorTipos = new HashSet<>();
        imagen = img;
        misCompras = new HashSet<>();
        costo = 0;
        mapCompras = new HashMap<>();
    }

    public DTPaquete getInfo() {
        Set<String> tipos = new HashSet<>();
        Map<String,Integer> tot = new HashMap<>();
        for(CantidadPorTipo can : misCantidadPorTipos) {
        	tipos.add(can.getTipoPublicacion().getNombre());
        	tot.put(can.getTipoPublicacion().getNombre(),can.getCantidad());
        }
        return new DTPaquete(nombre,descripcion,validez,descuento,costo,fechaAlta,tipos,imagen,tot);
    }

    /**
     * @return
     */
    public String getNombre() {
        // TODO implement here
        return nombre;
    }

    public Map<String,Integer> getTiposConCantidad(){
    	Map<String,Integer> res = new HashMap<>();
    	for(CantidadPorTipo cpt : misCantidadPorTipos) {
    		String nombre = cpt.getTipoPublicacion().getNombre();
    		Integer cant = cpt.getCantidad();
    		res.put(nombre, cant);
    	}
    	return res;
    }


    /**
     * @param c
     */
    public void ingresarTipoAPaquete(CantidadPorTipo cpti) {
        // TODO implement here
    	misCantidadPorTipos.add(cpti);
    	// cada vez que se inserta un tipo, se debe recalcular el costo.
    	double cost = 0;
    	TipoPublicacion tpAsoc;
    	double costTP;
    	// estoy en un paquete. recorro misCantidadPorTipos y para cada una de ellas, multiplico esa cant x el costo de dicho paquete y los voy sumando
    	for (CantidadPorTipo cpt : misCantidadPorTipos) {
    		tpAsoc = cpt.getTipoPublicacion();
    		costTP = tpAsoc.getCosto();
    		cost = cost + costTP*cpt.getCantidad();
    	}
    	// aplicar descuento general
    	cost = cost * (1 - getDescuento()/100);
    	setCosto(cost);
    }

    /**
     * @param value
     */
    public void setNombre(String nombreP) {
        // TODO implement here
    	nombre = nombreP;
    }

    /**
     * @return
     */
    public String getDescripcion() {
        // TODO implement here
        return descripcion;
    }

    /**
     * @param value
     */
    public void setDescripcion(String descP) {
        // TODO implement here
    	descripcion = descP;
    }

    /**
     * @return
     */
    public int getValidez() {
        // TODO implement here
        return validez;
    }

    /**
     * @param value
     */
    public void setValidez(int valP) {
        // TODO implement here
    	validez = valP;
    }

    /**
     * @return
     */
    public double getDescuento() {
        // TODO implement here
        return descuento;
    }

    /**
     * @param value
     */
    public void setDescuento(double descP) {
        // TODO implement here
    	descuento = descP;
    }

    /**
     * @return
     */
    public double getCosto() {
        // TODO implement here
        return costo;
    }

    /**
     * @param value
     */
    public void setCosto(double costoP) {
        // TODO implement here
    	costo = costoP;
    }

    /**
     * @return
     */
    public Date getFechaAlta() {
        // TODO implement here
        return fechaAlta;
    }

    /**
     * @param value
     */
    public void setFechaAlta(Date fechaAltaP) {
        // TODO implement here
    	fechaAlta = fechaAltaP;
    }

    /**
	 * @return
	 */
	public String getImagen() {
	    // TODO implement here
	    return imagen;
	}
	/**
	 * @param value
	 */
	public void setImagen(String img) {
	    // TODO implement here
		imagen = img;
	}

    // tipoSeleccionado es un tipo que esta en el paquete
	public int getValorCantidadPorTipo(String tipoSeleccionado) {
		// tSeleccionado id al TipoPublicacion
		int cant = 0;
		for (CantidadPorTipo cpt : misCantidadPorTipos) { // entrara aqui
			if (cpt.getTipoPublicacion().getNombre().equals(tipoSeleccionado)) {
				cant = cpt.getCantidad();
				break;
			}
		}
		return cant;
	}

	public boolean noEstaComprado() {
		if (misCompras.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

	/*public Date getVencimiento() {
    	int val = getValidez();
    	Calendar cal = Calendar.getInstance();
		cal.setTime(getFechaAlta());
		cal.add(Calendar.DAY_OF_MONTH, val);
		return cal.getTime();
    }*/

	//lo uso para disminuir en uno la cant x tipo de un tipo de publi en particular del paquete. Se que el paquete
	//tiene al menos una unidad de publicacion del tipo porque lo sabria antes tras usar listarPaquetesAdquiridosVigentesTipo, si se fuera a usar
	//para otra cosa cuidado
	/*public void disminuirTipoEnPaquete(String nomTipo) {
		CantidadPorTipo cant = null;

		for (CantidadPorTipo cpt : misCantidadPorTipos) {
            if (cpt.getTipoPublicacion().getNombre().equals(nomTipo)) {
                cant = cpt;
                break;
            }
        }
		int cantidadPrevia = cant.getCantidad();
		cant.setCantidad(cantidadPrevia-1);
	}*/

    public void agregarCompra(Compra com) {
    	misCompras.add(com);
    	mapCompras.put(com.getEmpresaAsociada().getNickname(), com);
    }



	 /**
     * @return
     */
    public DTPaquete getInfoConImg() {
        Set<String> tipos = new HashSet<>();
        for (CantidadPorTipo cpt : misCantidadPorTipos) {
        	tipos.add(cpt.getTipoPublicacion().getNombre());
        }
        DTPaquete dtp = new DTPaquete(nombre, descripcion, validez, descuento, costo, fechaAlta, tipos, imagen);
        return dtp;
    }
}
