package logica;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import logicaDAO.OfertaDAO;




/**
 *
 */
public class Oferta {

	public enum Estados {
        ingresada,
        confirmada,
        rechazada,
        finalizada
    }
	private Map<String, Keyword> misKeywords = new HashMap<>();
	private Set<Postulacion> misPostulaciones = new HashSet<>();

	private TipoPublicacion miTPublicacion = null;

	private Compra miCompra = null;

    private Empresa miEmpresa = null;

    private String imagen;

    private String nombre;
    private String descripcion;
    private String ciudad;
    private String departamento;
    private String horario;
    private double remuneracion;
    private Date fechaAlta;
	private Estados estado;
	private int cantVisitas;
	private int cantFavoritos;

    /**
     * Default constructor
     */
    public Oferta() {
    }

    /**
     *
     */
    public Oferta(String nom, String des, String ciu, String dep, String hora, double rem, Date fAlta) {
    	nombre = nom;
    	descripcion = des;
    	ciudad = ciu;
    	departamento = dep;
    	horario = hora;
    	remuneracion = rem;
    	fechaAlta = fAlta;
        this.estado = Estados.ingresada;
        cantVisitas = 0;
        cantFavoritos = 0;
    }

    /**
     *
     */
    public Oferta(String nom, String des, String ciu, String dep, String hora, double rem, Date fAlta, TipoPublicacion mtp) {
    	nombre = nom;
    	descripcion = des;
    	ciudad = ciu;
    	departamento = dep;
    	horario = hora;
    	remuneracion = rem;
    	fechaAlta = fAlta;
        miTPublicacion = mtp;
        this.estado = Estados.ingresada;
        cantVisitas = 0;
        cantFavoritos = 0;

    }

    public Oferta(String nom, String des, String ciu, String dep, String hora, double rem, Date fAlta, TipoPublicacion mtp, String imagen, Empresa emp) {
    	nombre = nom;
    	descripcion = des;
    	ciudad = ciu;
    	departamento = dep;
    	horario = hora;
    	remuneracion = rem;
    	fechaAlta = fAlta;
        miTPublicacion = mtp;
        this.estado = Estados.ingresada;
        this.imagen = imagen;
        miEmpresa = emp;
        cantVisitas = 0;
        cantFavoritos = 0;

    }

    public TipoPublicacion getTipoPublicacion() {
    	return miTPublicacion;
    }

    public void setTipoPublicacion(TipoPublicacion tpub) {
    	miTPublicacion = tpub;
    }

    public DTPostulacion getDTPostulacion(String nickname) {
    	//TODO
        for (Postulacion pos : misPostulaciones) {
            if (pos.getNicknamePostulante().equals(nickname)) {
                return pos.getDTPostulacion();
            }
        }
        return null;
    }
	public Estados getEstado() {
        return estado;
    }

    /**
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    public boolean esVigente() {
    	Date hoy = new Date();
    	Date venc = getVencimiento();
    	return hoy.before(venc) && hoy.after(fechaAlta);
    }

    public Set<Postulacion> getMisPostulaciones() {
    	return misPostulaciones;
    }

    //retorna el nick de los postulantes a la oferta
    public Set<String> getNicksMisPostulaciones() {
    	Set<String> nps = new HashSet<>();
    	for (Postulacion pos : misPostulaciones) {
    		nps.add(pos.getNicknamePostulante());
    	}
    	return nps;
    }

    /**
     * @param k
     */
    public void asignarleKeyword(Keyword kws) {
        misKeywords.put(kws.getNombre(), kws);
    }

    /**
     * @return
     */
    public DTOferta getDTOferta() {
    	String nom = nombre;
    	String des = descripcion;
    	String ciu = ciudad;
    	String dep = departamento;
    	String hora = horario;
    	Double rem = remuneracion;
    	Date fAl = fechaAlta;
    	Set<String> pos = getNicksMisPostulaciones();
        String tipoPubli = miTPublicacion.getNombre();
        Set<String> kws = misKeywords.keySet();


    	// Set<DTPaquete> packs = new HashSet<>(); // tmb se va
        DTPaquete pack = null;
        if (miCompra != null) {
        	pack = miCompra.getDTPaqueteAsociado();
           // NO SERIa packs = miCompra.getDTPaquetesDeCompra();
        }

    	DTOferta dtO = new DTOferta(nom, des, ciu, dep, hora, rem, fAl, pos, pack, tipoPubli, kws, imagen, miEmpresa.getNickname(), estado, cantVisitas,cantFavoritos);
        return dtO;
    }

    public Date getVencimiento() {
    	int val = miTPublicacion.getDuracion();
    	Calendar cal = Calendar.getInstance();
		cal.setTime(fechaAlta);
		cal.add(Calendar.DAY_OF_MONTH, val);
		return cal.getTime();
    }

    /**
     * @param postul
     */
    public void addPostulacion(Postulacion postul) {
        // TODO implement here
    	misPostulaciones.add(postul);
    }

    /**
     * @param e
     */
    public void setEstado(Estados est) {
        Estados estadoAnterior = estado;
    	estado = est;
    	if (estadoAnterior != est)
    		miEmpresa.cambiarEstadoOferta(this, estadoAnterior, estado);

    }

    /**
     * @param nom
     */
    public void setNombre(String nom) {
        nombre = nom;
    }

    /**
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param desc
     */
    public void setDescripcion(String desc) {
        descripcion = desc;
    }

    /**
     * @return
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciu
     */
    public void setCiudad(String ciu) {
        ciudad = ciu;
    }

    /**
     * @return
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param dep
     */
    public void setDepartamento(String dep) {
        departamento = dep;
    }

    public String getHorario() {
    	return horario;
    }

    /**
     * @param value
     */
    public void setHorario(String value) {
        horario = value;
    }

    /**
     * @return
     */
    public double getRemuneracion() {
        return remuneracion;
    }

    /**
     * @param rem
     */
    public void setRemuneracion(double rem) {
        remuneracion = rem;
    }

    /**
     * @return
     */
    public Date getFechaAlta() {
        return fechaAlta;
    }

    /**
     * @param fA
     */
    public void setFechaAlta(Date fAl) {
        fechaAlta = fAl;
    }

	public boolean esOfVigConfKw(Set<String> keyword) {
		// TODO Auto-generated method stub
		return esVigente() && esConfirmada() && keywordsValidas(keyword);
	}

	public boolean keywordsValidas(Set<String> kws) {
		boolean aux = true;
		for (String key: kws) {
			if (!misKeywords.containsKey(key)) {
				aux = false;
				break;
			}
		}return aux;
	}

	public boolean esConfirmada() {
		return getEstado()== Estados.confirmada;
	}


	public boolean tieneCompraAsociada() {
		return miCompra != null;
	}

	public void setEmpresa(Empresa emp) {
		miEmpresa =emp;
	}

	public Empresa getEmpresa() {
		return miEmpresa;
	}

	public void setImagen(String img) {
        imagen = img;
    }

    public String getImagen() {
		return imagen;
	}

	public void quitarKeywords() {
	    misKeywords.clear();
	}

	public void setCompra(Compra com) {
		miCompra=com;
	}

	public Compra getCompra() {
		return miCompra;
	}

	public int getCantVisitas() {
		return cantVisitas;
	}

	public void setCantVisitas(int vis) {
		cantVisitas = vis;
	}

	public void incrementarVisita() {
		cantVisitas++;
	}
	public void cambiarEstadoFinalizada() {
		estado = Estados.finalizada;
	}

	public int getCantFavoritos() {
		return cantFavoritos;
	}

	public void incrementarFav() {
		cantFavoritos++;
	}

	public void decrementarFav() {
		cantFavoritos--;
	}


	public OfertaDAO crearOfertaDAO() {
		String nombreTP = miTPublicacion.getNombre();
		OfertaDAO ofeDAO = new OfertaDAO(nombre,descripcion,ciudad,departamento,horario,remuneracion,fechaAlta,new Date(),nombreTP,imagen);
		if(miCompra!=null) {
			Paquete paq = miCompra.getPaqueteAsociado();
			ofeDAO.setPaquete(paq.getNombre());
		}
		return ofeDAO;
	}

	public DTOferta getDTOfertaDAO(OfertaDAO oferta) {
 		DTOferta res = oferta.getDTOferta();
		return res;
	}

	public Set<String> getKeyWords() {
		Set<String> keys = new HashSet<>();
		for(String key : misKeywords.keySet()) {
			keys.add(key);
		}
		return keys;
	}


}