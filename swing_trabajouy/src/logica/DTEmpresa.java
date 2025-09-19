package logica;

import java.util.Set;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTEmpresa extends DTUsuario {
	@XmlElement
	private String descripcion;
	@XmlElement
	private String link;

	public DTEmpresa(String nick, String nom, String ape, String corr, String password, String ima, String desc, String link){
		super(nick , nom, ape, corr , password, ima);
		descripcion = desc;
		this.link = link;
	}

	public DTEmpresa(String nick, String nom, String ape, String corr, String contra, String imagen, String desc,
			String link2, Set<String> seguidores, Set<String> seguidos) {
		super(nick,nom,ape,corr,contra,imagen,seguidores,seguidos);
		descripcion = desc;
		this.link = link2;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getLink() {
		return link;
	}
}