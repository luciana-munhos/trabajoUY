package logica;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import excepciones.PostulantePoseeOferta;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import logicaDAO.PostulanteDAO;
/**
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Postulante extends Usuario {
	/*Alamacena el nombre de la oferta como clave*/
	@XmlElement
	private Map<String, Postulacion> misPostulacionesAOfertas;
    /**
     * Default constructor
     */
    public Postulante(String nickU, String nomU, String surnameU, String mailU, String contra, String imagen, Date fechaNacU, String nacionU) {
    	super(nickU, nomU, surnameU, mailU , contra, imagen);
    	this.fechaNac = fechaNacU;
    	this.nacionalidad = nacionU;
    	misPostulacionesAOfertas = new HashMap<>();
    	favoritos = new HashSet<>();
    }

    /**
     *
     */
    @XmlElement
    private Date fechaNac;

    /**
     *
     */
    @XmlElement
    private String nacionalidad;

    @XmlElement
    private Set<Oferta> favoritos;

    public Set<String> mostrarOfertasPostulante(){
        return misPostulacionesAOfertas.keySet();
    }
    /**
     * @param oferta
     */
    public void asociadoAOferta(String oferta) throws PostulantePoseeOferta {
        if (misPostulacionesAOfertas.containsKey(oferta)) {
        	throw new PostulantePoseeOferta("El postulante seleccionado ya se encuentra asociado a dicha oferta");
        }
    }

    public DTPostulacion infoPostulacion(String oferta) {
    	Postulacion aux = misPostulacionesAOfertas.get(oferta);
    	DTPostulacion dtp = new DTPostulacion(aux.getFecha() , aux.getCVR(), aux.getMotivacion(), aux.getVideo());
    	return dtp;
    }
    /**
     * @param cvRed
     * @param motivacion
     * @param fecha
     */
    public Postulacion crearPostulacion(String cvRed, String motivacion, Date fecha,String video, String oferta) {
        Postulacion postul = new Postulacion(this, cvRed, motivacion, fecha, video);
        misPostulacionesAOfertas.put(oferta, postul);
        return postul;
    }

    /**
     * @return
     */
    public Date getFechaNac() {
        return fechaNac;
    }

    /**
     * @param value
     */
    public void setFechaNac(Date value) {
    	this.fechaNac = value;
    }

    /**
     * @return
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * @param value
     */
    public void setNacionalidad(String value) {
    	this.nacionalidad = value;
    }


    public void setFavoritos(Set<Oferta> favs) {
    	this.favoritos = favs;
    }

    public Set<Oferta> getFavoritos(){
    	return favoritos;
    }

    public Set<String> getNombresFavoritos(){
    	Set<String> res = new HashSet<>();
    	for(Oferta ofe: favoritos)
    		res.add(ofe.getNombre());
    	return res;
    }

    public void addFavorito(Oferta ofe) {
    	favoritos.add(ofe);
    	ofe.incrementarFav();
    }

    public void removeFavorito(Oferta ofe) {
    	favoritos.remove(ofe);
    	ofe.decrementarFav();
    }

	public Set<DTPostulacion> getDTPostulaciones() {
		Set<DTPostulacion> res = new HashSet<>();
		DTPostulacion dtp;
		for (Postulacion pos: misPostulacionesAOfertas.values()) {
			dtp = pos.getDTPostulacion();
			res.add(dtp);
		}
		return res;
	}

    @Override
    public DTUsuario getDTUsuario() {
    	String nick = getNickname();
    	String nom = getNombre();
    	String ape = getApellido();
    	String corr = getCorreo();
    	Date fNac = getFechaNac();
    	String nac = getNacionalidad();
        Set<DTOferta> ofertas = getDTOfertas();
        String contra = getContrasenia();
        String imagen = getImagen();
        Set<DTPostulacion> postulaciones = getDTPostulaciones();
        Set<String> seguidores = getSeguidores();
        Set<String> seguidos = getSeguidos();
        Set<DTOferta> favs = getDTFavoritos();
    	return new DTPostulante(nick, nom, ape, corr, contra, imagen, fNac , nac, ofertas, postulaciones, favs, seguidores,seguidos);
    }

    private Set<DTOferta> getDTFavoritos() {
		Set<Oferta> ofs = getFavoritos();
		Set<DTOferta> dtofs = new HashSet<>();
		for(Oferta oferta: ofs) {
			dtofs.add(oferta.getDTOferta());
		}
		return dtofs;
	}
	@Override
    public Set<DTOferta> getDTOfertas(){
    	Set<DTOferta> res = new HashSet<>();

    	for (Postulacion pos: misPostulacionesAOfertas.values()) {
    		Oferta ofer = pos.getMiOferta();
    		DTOferta dtO = ofer.getDTOferta();
    		res.add(dtO);
    	}

    	return res;
    }


	public void modificarDatosP(String nombreU, String surnameU, String contra, String ima, String nac, Date fNac) {
		// TODO Auto-generated method stub
		setNombre(nombreU);
		setApellido(surnameU);
		setContrasenia(contra);
		setImagen(ima);
		nacionalidad = nac;
		fechaNac = fNac;
	}


    public Postulacion getPostulacionOferta(String oferta) {
    	Postulacion postulacion = misPostulacionesAOfertas.get(oferta);
    	return postulacion;
    }


	public PostulanteDAO crearPostulanteDAO(String oferta) {
		PostulanteDAO posDAO = new PostulanteDAO(getNickname(),getNombre(),getApellido(),getCorreo(),fechaNac,nacionalidad,getImagen());
		misPostulacionesAOfertas.remove(oferta);
		return posDAO;
	}


}
