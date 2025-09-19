package logica;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import logica.Oferta.Estados;

//@XmlAccessorType(XmlAccessType.FIELD)
public class DTOferta {
	private String nombre;
	private String descripcion;
	private String ciudad;
	private String departamento;
	private String horario;
	private double remuneracion;
	private Date fechaAlta;
	private Date fechaBaja;
	private Set<String> postulaciones;
	private DTPaquete miPaquete;
	private String tipoPublicacion;
	private Set<String> misKeywords = new HashSet<>();
	private String imagen;
	private String nickEmpresa;
	private Estados estado;
	private int cantVisitas = 0;
	private int cantFavoritos = 0;

	public DTOferta(String nom, String desc, String  ciu, String  dep, String hora, double rem, Date fAlta, Set<String> pos, DTPaquete pack) {
		nombre = nom;
		descripcion = desc;
		ciudad = ciu;
		departamento = dep;
		horario = hora;
		remuneracion = rem;
		fechaAlta = fAlta;
		postulaciones = pos;
		miPaquete = pack;
		imagen = "media/img/OfertasLaborales/default/default.jpg";
	}

	public DTOferta(String nom, String desc, String ciu, String dep, String hora, double rem, Date fAlta, Set<String> pos, DTPaquete pack, String tipo, Set<String> key) {
		nombre = nom;
		descripcion = desc;
		ciudad = ciu;
		departamento = dep;
		horario = hora;
		remuneracion = rem;
		fechaAlta = fAlta;
		postulaciones = pos;
		miPaquete = pack;
		tipoPublicacion = tipo;
		misKeywords = key;
		imagen = "media/img/OfertasLaborales/default/default.jpg";
	}

	public DTOferta(String nom, String desc, String ciu, String dep, String hora, double rem, Date fAlta, Set<String> pos, DTPaquete pack, String tipo, Set<String> key, String ima, String emp, Estados est) {
		nombre = nom;
		descripcion = desc;
		ciudad = ciu;
		departamento = dep;
		horario = hora;
		remuneracion = rem;
		fechaAlta = fAlta;
		postulaciones = pos;
		miPaquete = pack;
		tipoPublicacion = tipo;
		misKeywords = key;
		if (ima == null || ima == "")
			imagen = "media/img/OfertasLaborales/default/default.jpg";
		else
			imagen = ima;
		nickEmpresa = emp;
		estado = est;
	}

	public DTOferta(String nom, String desc, String ciu, String dep, String hora, double rem, Date fAlta, Set<String> pos, DTPaquete pack, String tipo, Set<String> key, String ima, String emp, Estados est, int cantVis) {
		nombre = nom;
		descripcion = desc;
		ciudad = ciu;
		departamento = dep;
		horario = hora;
		remuneracion = rem;
		fechaAlta = fAlta;
		postulaciones = pos;
		miPaquete = pack;
		tipoPublicacion = tipo;
		misKeywords = key;
		if (ima == null || ima == "")
			imagen = "media/img/OfertasLaborales/default/default.jpg";
		else
			imagen = ima;
		nickEmpresa = emp;
		estado = est;
		cantVisitas = cantVis;
	}

	public DTOferta(String nom, String desc, String ciu, String dep, String hora, double rem, Date fAlta, Date fBaja,Set<String> post, String tipo, String ima, String emp) {
		nombre = nom;
		descripcion = desc;
		ciudad = ciu;
		departamento = dep;
		horario = hora;
		remuneracion = rem;
		fechaAlta = fAlta;
		fechaBaja = fBaja;
		postulaciones = post;
		tipoPublicacion = tipo;
		if (ima == null || ima == "")
			imagen = "media/img/OfertasLaborales/default/default.jpg";
		else
			imagen = ima;
		nickEmpresa = emp;
	}

	public DTOferta(String nom, String desc, String ciu, String dep, String hora, double rem, Date fAlta, Set<String> pos, DTPaquete pack, String tipo, Set<String> key, String ima, String emp, Estados est, int cantVis, int cantFavs) {
		nombre = nom;
		descripcion = desc;
		ciudad = ciu;
		departamento = dep;
		horario = hora;
		remuneracion = rem;
		fechaAlta = fAlta;
		postulaciones = pos;
		miPaquete = pack;
		tipoPublicacion = tipo;
		misKeywords = key;
		if (ima == null || ima == "")
			imagen = "media/img/OfertasLaborales/default/default.jpg";
		else
			imagen = ima;
		nickEmpresa = emp;
		estado = est;
		cantVisitas = cantVis;
		cantFavoritos = cantFavs;
	}

	public Date fechaVencimiento(int duracion) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaAlta);
		cal.add(Calendar.DAY_OF_MONTH, duracion);
		return cal.getTime();
	}


	public Estados getEstado() {
		return estado;
	}

	public void setEstado(Estados est) {
		estado = est;
	}

	public String getTipoPublicacion() {
		return tipoPublicacion;
	}

	public void setTipoPublicacion(String tipo) {
		tipoPublicacion = tipo;
	}

	public Set<String> getKeywords(){
		return misKeywords;
	}

	public void setKeywords(Set<String> keys) {
		misKeywords = keys;
	}

	public String getNombre() {
        return nombre;
    }

	public void setNombre(String nom) {
		nombre = nom;
	}

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String desc) {
    	descripcion = desc;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciu) {
    	ciudad = ciu;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String dep) {
    	departamento = dep;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String hor) {
    	horario = hor;
    }

    public double getRemuneracion() {
        return remuneracion;
    }

	public void setRemuneracion(double rem) {
		remuneracion = rem;
	}

    public Date getFechaAlta() {
        return fechaAlta;
    }

	public void setFechaAlta(Date fAlta) {
		fechaAlta = fAlta;
	}

	public Set<String> getPostul() {
		return postulaciones;
	}

	public void setPostul(Set<String> pos) {
		postulaciones = pos;
	}

	public DTPaquete getDTPaqueteAsociado(){
		return miPaquete;
	}

	public void setDTPaqueteAsociado(DTPaquete pack) {
		miPaquete = pack;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String ima) {
		imagen = ima;
	}

	public String getNickEmpresa() {
		return nickEmpresa;
	}

	public void setNickEmpresa(String emp) {
		nickEmpresa = emp;
	}

	public int getCantVisitas() {
		return cantVisitas;
	}

	public void setCantVisitas(int cant) {
		cantVisitas = cant;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public int getCantFavoritos(){
		return cantFavoritos;
	}

	public void setCantFavoritos(int cant) {
		cantFavoritos = cant;
	}
}
