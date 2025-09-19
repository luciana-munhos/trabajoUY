package logica;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public abstract class Usuario {

    /**
     * Default constructor
     */
    public Usuario(String nickU, String nomU, String surnameU, String mailU, String contra, String imagenU) {
    	nickname = nickU;
    	nombre = nomU;
    	apellido = surnameU;
    	correo = mailU;
        contrasenia = contra;
        imagen = imagenU;
        seguidores = new HashSet<>();
		seguidos = new HashSet<>();
    }

    public Usuario(String nickU, String nomU, String surnameU, String mailU, String contra, String imagenU, Set<String> seguidores, Set<String> seguidos) {
    	nickname = nickU;
    	nombre = nomU;
    	apellido = surnameU;
    	correo = mailU;
        contrasenia = contra;
        imagen = imagenU;
        this.seguidores = seguidores;
        this.seguidos = seguidos;
    }

    /**
     *
     */
    private String nickname;

    /**
     *
     */
    private String nombre;

    /**
     *
     */
    private String apellido;

    /**
     *
     */
    private String correo;

    private String contrasenia;

    private String imagen;

    private Set<String> seguidores;

    private Set<String> seguidos;

    /**
     * @return
     */
    public abstract DTUsuario getDTUsuario();

    /**
     * @return
     */
    public abstract Set<DTOferta> getDTOfertas();

    /**
     * @return representa el nickname del usuario
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param value representa el nuevo nickname del usuario
     */
    public void setNickname(String value) {
        nickname = value;
    }

    /**
     * @return representa el nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param value representa el nuevo nombre del usuario
     */
    public void setNombre(String value) {
        nombre = value;
    }

    /**
     * @return representa el apellido del usuario
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param value representa el nuevo apellido del usuario
     */
    public void setApellido(String value) {
        apellido = value;
    }

    /**
     * @return representa el correo del usuario
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param value representa el nuevo correo del usuario
     */
    public void setCorreo(String value) {
        correo=value;
    }

    /**
     * @return representa la contraseña del usuario
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param value representa la nueva contraseña del usuario
     */
    public void setContrasenia(String value) {
        contrasenia = value;
    }

    public void setImagen(String ima) {
    	imagen = ima;
    }
    public String getImagen() {
    	return imagen;
    }

	protected boolean compararContrasenias(String contra){
		return contra.equals(getContrasenia());
	}

	public Set<String> getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(Set<String> seguidores) {
		this.seguidores = seguidores;
	}

	public Set<String> getSeguidos() {
		return seguidos;
	}

	public void setSeguidos(Set<String> seguidos) {
		this.seguidos = seguidos;
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
