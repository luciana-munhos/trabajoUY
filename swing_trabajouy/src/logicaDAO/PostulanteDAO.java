package logicaDAO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity

// Definimos un nombre específico para el nombre de la tabla. Por defecto sería EmpresaDAO.
@Table(name="Postulantes")

public class PostulanteDAO {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_postulante", nullable = false)
    private int  idpostulante; // Clave principal autoincremental

    @Column(unique = true, nullable = false)
	private String nickname;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(unique = true, nullable = false)
    private String correo;

    @Column(nullable = false)
    private String nacionalidad;

	@Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNac;

    private String imagen;

    @OneToMany(mappedBy = "idpostulante")
    private Set<PostulacionDAO> misPostulaciones;

	public PostulanteDAO() {}

	 public PostulanteDAO(String nickP, String nomP, String surnameP, String mailP, Date date, String nacionP, String img) {
	    	nickname = nickP;
	    	nombre = nomP;
	    	apellido = surnameP;
	    	correo = mailP;
	    	imagen = img;
	    	fechaNac = date;
	    	setNacionalidad(nacionP);
	    	misPostulaciones = new HashSet<>();
	    }




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

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	 public Set<PostulacionDAO> getPostulaciones() {

	        return misPostulaciones;
	    }

	    public void setPostulacionesDAO(Set<PostulacionDAO> post) {
	    	misPostulaciones = post;
	    }

	    public void addPostulacionesDAO(PostulacionDAO post) {
	    	misPostulaciones.add(post);
	    }

		public String getNacionalidad() {
			return nacionalidad;
		}

		public void setNacionalidad(String nacionalidad) {
			this.nacionalidad = nacionalidad;
		}

		public int getIdpostulante() {
			return idpostulante;
		}

		public void setIdpostulante(int idpostulante) {
			this.idpostulante = idpostulante;
		}




}