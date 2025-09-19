package logica;

/**
 *
 */
public class CantidadPorTipo {
    private TipoPublicacion miTipoPublicacion;
    private Paquete miPaquete;
    private int cantidadActual;
    private static int cantidadTotal;


    /**
     * Default constructor
     */
    public CantidadPorTipo() {
    }

    /**
     *
     */
    public CantidadPorTipo(int cant, TipoPublicacion tpub, Paquete paq) {
    	cantidadActual = cant;
        miPaquete = paq;
        miTipoPublicacion = tpub;
        cantidadTotal = cant;
    }



    /**
     * @return Retorna la cantidad de publicaciones de un tipo dentro de un paquete
     */
    public int getCantidad() {
        return cantidadActual;
    }

    /**
     * Setea la cantidad de publicaciones de un tipo
     * @param value representa la cantidad de publicaciones de un tipo
     */
    public void setCantidad(int value) {
    	cantidadActual = value;
    }

    /**
     * @return Retorna el tipo de publicacion
     */
    public TipoPublicacion getTipoPublicacion() {
        return miTipoPublicacion;
    }

    /**
     * Setea el tipo de publicacion
     * @param value representa el tipo de publicacion
     */
    public void setTipoPublicacion(TipoPublicacion value) {
        miTipoPublicacion = value;
    }

    /**
     * @return Retorna el paquete
     */
    public Paquete getPaquete() {
        return miPaquete;
    }

    /**
     * Setea el paquete
     * @param value representa el paquete
     */
    public void setPaquete(Paquete value) {
        miPaquete = value;
    }

    public int getCantidadTotal() {
    	return cantidadTotal;
    }

}