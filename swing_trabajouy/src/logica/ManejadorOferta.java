package logica;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import excepciones.NoExisteOferta;
import excepciones.NombreOfertaExistente;

/**
 *
 */
public class ManejadorOferta {
    private Map<String, Oferta> misOfertas = new HashMap<>();

    /**
     * Default constructor
     */
    public ManejadorOferta() {
    }

    public Map<String , Oferta> getMisOfertas() {
    	return misOfertas;
    }
    /**
     *
     */
    private static ManejadorOferta Instancia = null;

    /**
     * @param oferta
     * @return
     */
    public Oferta obtenerInstanciaO(String oferta) throws NoExisteOferta {
        if (!misOfertas.containsKey(oferta)) {
        	throw new NoExisteOferta("La oferta seleccionada no existe");
        }
        return misOfertas.get(oferta);
    }

    /**
     *
     */
    public static ManejadorOferta getInstance() {
        if (Instancia == null) {
            Instancia = new ManejadorOferta();
        }
        return Instancia;
    }

    /**
     * @param ofe
     */
    public void agregarOferta(Oferta ofe) throws NombreOfertaExistente{
        if (misOfertas.containsKey(ofe.getNombre())) {
            throw new NombreOfertaExistente("Ya existe una oferta con ese nombre");
        }
        misOfertas.put(ofe.getNombre(), ofe);
    }

	public Set<String> obtenerOfertasKeywords(Set<String> keyword) {
		Set<Oferta> aux = new HashSet<>(getMisOfertas().values());
		Set<String> res = new HashSet<>();
		for (Oferta ofes: aux) {
			if (ofes.esOfVigConfKw(keyword)) {
				res.add(ofes.getNombre());
			}
		}
		return res;
	}

}