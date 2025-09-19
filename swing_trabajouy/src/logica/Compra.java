package logica;

import java.util.Date;
import java.util.Map;

import excepciones.NoHayTPRestantes;

/**
 *
 */
public class Compra {
	private Date fecha;
    private int vencimiento;
    Map<String, Integer> cantActualPorTipo;
	private Paquete miPaqueteAsociado; // si no tiene, es porque la compra fue hecha por separado
	private Empresa miEmpresaAsociada;
    /**
     * Default constructor
     */
    public Compra() {
    }

    public Compra(Date fecha, int vencimiento) {
        this.fecha = fecha;
        this.vencimiento = vencimiento;
    }

    public Compra(Date fecha,Paquete pos,Empresa emp){
        this.fecha = fecha;
        miPaqueteAsociado = pos;
        miEmpresaAsociada = emp;
        vencimiento = pos.getValidez();
        cantActualPorTipo = pos.getTiposConCantidad();
    }




    public DTPaquete getDTPaqueteAsociado() {
    	return miPaqueteAsociado.getInfo();
    }


    /**
     * @return
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param value
     */
    public void setFecha(Date value) {
        fecha = value;
    }

    /**
     * @return
     */
    public int getVencimiento() {
        return vencimiento;
    }

    /**
     * @param value
     */
    public void setVencimiento(int value) {
        vencimiento = value;
    }


    public void setPaqueteAsociado(Paquete paq) {
    	miPaqueteAsociado = paq;
    }

    public Paquete getPaqueteAsociado(){
    	return miPaqueteAsociado;
    }

    public DTCompra getDTCompra(String nick) {
		DTPaquete paquete = miPaqueteAsociado.getInfo();
		DTCompra res = new DTCompra(nick,paquete,vencimiento,fecha,cantActualPorTipo);
		return res;
	}

    public void setEmpresaAsociada(Empresa emp) {
    	miEmpresaAsociada = emp;
    }

    public Empresa getEmpresaAsociada() {
    	return miEmpresaAsociada;
    }

    public void disminuirTipoEnCompra(String nombreTipo) throws NoHayTPRestantes {
    	Integer cant = cantActualPorTipo.get(nombreTipo);
    	if(cant == 0 || cant == null)
            throw new NoHayTPRestantes("No quedan TP para esta empresa");

    	cantActualPorTipo.remove(nombreTipo);
    	cantActualPorTipo.put(nombreTipo, cant-1);
    }

    public Map<String, Integer> getCantActualPorTipo(){
    	return cantActualPorTipo;
    }
}
