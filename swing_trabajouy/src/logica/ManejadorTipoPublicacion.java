package logica;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import excepciones.NoExisteTipoPublicacion;
import excepciones.NombreTipoPublicacionExistente;
/**
 *
 */
public class ManejadorTipoPublicacion {
    private Map<String, TipoPublicacion> misTiposPublicacion;
    private static ManejadorTipoPublicacion Instancia = null;

    /**
     * Default constructor
     */
    private ManejadorTipoPublicacion() {
        misTiposPublicacion = new HashMap<>();
    }

    /**
     * @param nombreTP
     * @return
     */
    public TipoPublicacion getTP(String nombreTP) throws NoExisteTipoPublicacion{
        return misTiposPublicacion.get(nombreTP);
    }

    /**
     *
     */
    public static ManejadorTipoPublicacion getInstance() {
        if (Instancia == null) {
            Instancia = new ManejadorTipoPublicacion();
        }
        return Instancia;
    }

    /**
     * @return
     */
    public Set<String> obtenerNombreTipos() {
        return misTiposPublicacion.keySet();
    }

    /**
     * @param nombreTP nombre del tipo de publicacion
     * @param descTP descripcion del tipo de publicacion
     * @param expTP exposicion del tipo de publicacion (de 1 a 5)
     * @param duracionTP duracion del tipo de publicacion (en dias)
     * @param costoTP costo del tipo de publicacion
     * @param fechaAltaTP fecha de alta del tipo de publicacion (en formato DD/MM/AAAA)
     */
    public TipoPublicacion agregarTipoDePublicacion(String nombreTP, String descTP, int expTP, int duracionTP, double costoTP, Date fechaAltaTP) throws NombreTipoPublicacionExistente{
    	if (misTiposPublicacion.containsKey(nombreTP))
           	throw new NombreTipoPublicacionExistente("Ya existe un TipoDePublicacion con ese nombre");
        else {
            TipoPublicacion tpu = new TipoPublicacion(nombreTP, descTP, expTP, duracionTP, costoTP, fechaAltaTP);
            misTiposPublicacion.put(nombreTP, tpu);
            return tpu;
       }
    }


    /**
     * @return
     */
    public Set<String> getTiposP() {
        // TODO implement here
        return misTiposPublicacion.keySet();
    }

}