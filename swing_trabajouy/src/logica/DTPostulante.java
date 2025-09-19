package logica;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTPostulante extends DTUsuario {
	@XmlElement
	private Date fechaNac;
	@XmlElement
	private String nacionalidad;
	@XmlElement
	private Set<DTOferta> ofertas;
	@XmlElement
	private Set<DTPostulacion> postulaciones;
	@XmlElement
	private Set<DTOferta> favoritos;

	public DTPostulante(String nick, String nom, String ape, String corr, String password, String ima, Date fec, String nac, Set<DTOferta> oferta) {
		super(nick, nom, ape, corr, password, ima);
		fechaNac = fec;
		nacionalidad = nac;
		ofertas = oferta;
		postulaciones = new HashSet<>();
	}


	// nuevo constructor que tiene POSTULACIONES
	public DTPostulante(String nick, String nom, String ape, String corr, String password, String ima, Date fec, String nac, Set<DTOferta> oferta, Set<DTPostulacion> postulacion) {
		super(nick, nom, ape, corr, password, ima);
		fechaNac = fec;
		nacionalidad = nac;
		ofertas = oferta;
		postulaciones = postulacion;
	}

	public DTPostulante(String nick, String nom, String ape, String corr, String password, String ima, Date fec, String nac, Set<DTOferta> oferta, Set<DTPostulacion> postulacion, Set<DTOferta> favs) {
		super(nick, nom, ape, corr, password, ima);
		fechaNac = fec;
		nacionalidad = nac;
		ofertas = oferta;
		postulaciones = postulacion;
		favoritos = favs;
	}

	public DTPostulante(String nick, String nom, String ape, String corr, String contra, String imagen, Date fNac,
			String nac, Set<DTOferta> ofertas2, Set<DTPostulacion> postulaciones2, Set<DTOferta> favs, Set<String> seguidores,
			Set<String> seguidos) {
		super(nick,nom,ape,corr,contra,imagen,seguidores,seguidos);
		fechaNac = fNac;
		nacionalidad = nac;
		ofertas = ofertas2;
		postulaciones = postulaciones2;
		favoritos = favs;
	}


	public Date getFechaNac() {
		return fechaNac;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public Set<DTOferta> getOfertas() {
		return ofertas;
	}

	public Set<DTPostulacion> getPostulaciones(){
		return postulaciones;
	}
}
