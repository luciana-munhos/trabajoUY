package logicaDAO;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="KEYWORDS")
public class KeywordDAO {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_KEYWORD", unique = true, nullable = false)
    private int  idkeyword; // Clave principal autoincremental

	@Column(unique = true, nullable = false)
    private String nombre;


	public KeywordDAO() {}

    public KeywordDAO(String nombre) {
    	this.nombre = nombre;
    }


	public int getIdkeyword() {
		return idkeyword;
	}

	public void setIdkeyword(int idkeyword) {
		this.idkeyword = idkeyword;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

}
