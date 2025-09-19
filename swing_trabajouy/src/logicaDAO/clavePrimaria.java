package logicaDAO;

import java.util.Objects;

import jakarta.persistence.Embeddable;
@Embeddable
public class clavePrimaria{
	    private int idpostulante;
	    private int idoferta;


	    // Constructor, getters y setters
	    //setters
	    public clavePrimaria() {}

	    public void setidPostulante(int post) {
	    	idpostulante = post;
	    }
	    //setters
	    public void setidOferta(int ofe) {
	    	idoferta = ofe;
	    }
	    public int getidPostulante() {
	    	return idpostulante;
	    }
	    public int getidOferta() {
	    	return idoferta;
	    }


	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        PostulacionDAO that = (PostulacionDAO) o;
	        return Objects.equals(idpostulante, that.getIdpostulante()) &&
	               Objects.equals(idoferta, that.getIdoferta());
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(idpostulante, idoferta);
	    }


}
