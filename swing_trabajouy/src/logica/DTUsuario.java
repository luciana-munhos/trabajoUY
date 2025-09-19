package logica;

import java.util.HashSet;
import java.util.Set;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTUsuario {
	@XmlElement
	private String nickname;
	@XmlElement
	private String nombre;
	@XmlElement
	private String apellido;
	@XmlElement
	private String correo;
	@XmlElement
	private String contra;
	@XmlElement
	private String imagen;
	@XmlElement
	private Set<String> seguidores;
	@XmlElement
	private Set<String> seguidos;

	public DTUsuario() {
		nickname = "";
		nombre = "";
		apellido = "";
		correo = "";
		contra = "";
		imagen = "";
		seguidores = new HashSet<>();
		seguidos = new HashSet<>();
	}

	public DTUsuario(String nick, String nom, String ape, String corr, String cont, String ima) {
		nickname = nick;
		nombre = nom;
		apellido = ape;
		correo = corr;
		contra = cont;
		imagen = ima;
		seguidores = new HashSet<>();
		seguidos = new HashSet<>();
	}

	public DTUsuario(String nick, String nom, String ape, String corr, String cont, String ima,Set<String> set_seguidores,Set<String> set_seguidos) {
		nickname = nick;
		nombre = nom;
		apellido = ape;
		correo = corr;
		contra = cont;
		imagen = ima;
		seguidores = set_seguidores;
		seguidos = set_seguidos;

	}

	public Set<String> getSeguidores(){
		return seguidores;
	}

	public Set<String> getSeguidos(){
		return seguidos;
	}

	public String getContrasenia() {
		return contra;
	}

	public String getImagen() {
		return imagen;
	}

	public String getNickname() {
		return nickname;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setSeguidores(Set<String> seg){
		seguidores = seg;
	}

	public void setSeguidos(Set<String> seg){
		seguidos = seg;
	}

	public void setContrasenia(String con) {
		contra = con;
	}

	public void setImagen(String img) {
		imagen = img;
	}

	public void setNickname(String nick) {
		nickname = nick;
	}

	public void setNombre(String nom) {
		nombre = nom;
	}

	public void setApellido(String ape) {
		apellido = ape;
	}

	public void setCorreo(String corr) {
		correo = corr;
	}

	public void addSeguidor(String nick) {
		seguidores.add(nick);
	}

	public void addSeguido(String nick) {
		seguidos.add(nick);
	}

	public void removeSeguidor(String nick) {
		seguidores.remove(nick);
	}

	public void removeSeguido(String nick) {
		seguidos.remove(nick);
	}
}
