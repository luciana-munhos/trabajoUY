package logica;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa una keyword
 */
public class Keyword {
	private Map<String, Oferta> misOfertas = new HashMap<>(); //Las ofertas que tienen esta keyword!!

    private String nombre;

    /**
     * Default constructor
     */
    public Keyword() {
    }

    /**
     * Constructor con parametro
     */
    public Keyword(String nom) {
        nombre = nom;
    }



    /**
     * @param o
     */
    public void asignarOferta(Oferta ofe) {
        misOfertas.put(ofe.getNombre(), ofe);
    }

    /**
     * @return Retorna el nombre de la keyword
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param value Setea el nombre de la keyword
     */
    public void setNombre(String value) {
        nombre = value;
    }

}