package logicaDAO;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity

// Definimos un nombre específico para el nombre de la tabla. Por defecto sería EmpresaDAO.
@Table(name="EMPRESAS")

public class EmpresaDAO {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_empresa", nullable = false)
    private int  idempresa; // Clave principal autoincremental

	@Column(unique = true, nullable = false)
	private String nickname;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(unique = true, nullable = false)
    private String correo;

    @Column(length = 1000) // Ajusta el tamaño según tus necesidades
	private String descripcion;

	@Column(name = "sitio_web")
	private String link;

	private String imagen;

	@OneToMany(mappedBy = "idempresa") // Nombre del atributo en OfertaDAO
	private List<OfertaDAO> misOfertasFinalizadas;

	public EmpresaDAO() {
	    // Constructor sin parámetros
	}

	public EmpresaDAO(String nickE, String nomE, String surnameE, String mailE, String descE, String linkE, String img) {
		nickname = nickE;
    	nombre = nomE;
    	apellido = surnameE;
    	correo = mailE;
    	descripcion = descE;
    	link = linkE;
    	setImagen(img);
		misOfertasFinalizadas = new ArrayList<>();
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
    public void setDescripcion(String value) {
        // TODO implement here
    	descripcion = value;
    }

    /**
     * @return
     */
    public String getLink() {
        // TODO implement here
        return link;
    }

    /**
     * @param value
     */
    public void setLink(String value) {
        // TODO implement here
    	link = value;
    }

 public List<OfertaDAO> getOfertasFinalizadas() {

        return misOfertasFinalizadas;
    }

 public void setOfertasFinalizadas(List<OfertaDAO> ofertas) {
	    misOfertasFinalizadas = ofertas;
	}

    public void addOfertaDAO(OfertaDAO ofertas) {
    	misOfertasFinalizadas.add(ofertas);
    }

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public int getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(int idempresa) {
		this.idempresa = idempresa;
	}


}
