package logica;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import excepciones.NoExistePaquete;
import excepciones.NombrePaqueteExistente;

/**
 *
 */
public class ManejadorPaquete {
	private static ManejadorPaquete Instancia = null;
	private Map<String, Paquete> misPaquetes;
    /**
     * Default constructor
     */
    private ManejadorPaquete() {
    	misPaquetes = new HashMap<>();
    }

    /**
     *
     */
    public static ManejadorPaquete getInstance() {
        if (Instancia == null) {
        	Instancia = new ManejadorPaquete();
        }
        return Instancia;
    }

    /**
     * @return
     */
    public Map<String, Paquete> getPaquetes() {
        // TODO implement here
        return misPaquetes;
    }

    /**
     * @param nombreP nombre del paquete
     * @return Retorna el paquete con el nombre ingresado
     * @throws NoExistePaquete si no existe un paquete con el nombre ingresado
     */
    public Paquete getPaquete(String nombreP) throws NoExistePaquete{
        if (misPaquetes.get(nombreP) == null) {
        	throw new NoExistePaquete("No existe un paquete con dicho nombre");
        }
        else
            return misPaquetes.get(nombreP);
    }

    public void crearPaquete(String nom, String descr, int validez, double des, Date fechaAlta, String img) throws NombrePaqueteExistente{
    	if (misPaquetes.get(nom) != null) {
    		throw new NombrePaqueteExistente("Ya existe un paquete con dicho nombre");
    	}else {
    		Paquete paq = new Paquete(nom, descr, validez, des, fechaAlta, img);
    		misPaquetes.put(nom, paq);
    	}

    }
    public void crearPaqueteConImg(String nom, String descr, int validez, double des, Date fechaAlta, String img) throws NombrePaqueteExistente{
    	if (misPaquetes.get(nom) != null) {
    		throw new NombrePaqueteExistente("Ya existe un paquete con dicho nombre");
    	}else {
    		Paquete paq = new Paquete(nom, descr, validez, des, fechaAlta, img);
    		misPaquetes.put(nom, paq);
    	}

    }



}