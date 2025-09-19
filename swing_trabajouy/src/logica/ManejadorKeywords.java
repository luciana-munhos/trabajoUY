package logica;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import excepciones.NoExisteKeyword;
import excepciones.NombreKeywordExistente;

/**
 *
 */
public class ManejadorKeywords {

    /**
     * Default constructor
     */
    private ManejadorKeywords() {
        misKeywords = new HashMap<>();
    }

    /**
     * Instancia unica de la clase
     *
     *
     */
    private static ManejadorKeywords Instancia = null;

    /**
     * Mapa de keywords
     */
    private Map<String, Keyword> misKeywords;

    /**
     * @param key keyword a agregar al mapa
     */
    public void agregarKeyword(Keyword key) throws NombreKeywordExistente {
        if (misKeywords.get(key.getNombre()) == null)
        	misKeywords.put(key.getNombre(), key);
        else    //throw exception
            throw new NombreKeywordExistente("Ya existe una keyword con ese nombre");
    }

    /**
     * funcion que retorna la instancia unica de la clase
     */
    public static ManejadorKeywords getInstance() {
        if (Instancia == null)
            Instancia = new ManejadorKeywords();

        return Instancia;
    }

    /**
     * @param k string que representa el nombre de la keyword
     * @return retorna la keyword con el nombre k
     */
    public Keyword getKeyword(String kws) throws NoExisteKeyword{
        if (misKeywords.get(kws) == null)
            throw new NoExisteKeyword("No existe una keyword con ese nombre");
        else
            return misKeywords.get(kws);
    }

    public Set<String> obtenerNombreKeywords() {
    	Set<String> res = new HashSet<>();
    	for(String key: misKeywords.keySet())
    		res.add(key);
        return res;
    }

    public Map<String, Keyword> getKeywords(){
    	return misKeywords;
    }

}