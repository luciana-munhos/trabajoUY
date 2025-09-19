package logica;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import excepciones.NoExisteOfertaEmpresa;
import excepciones.NoHayTPRestantes;
import excepciones.NoTieneOfertasConfirmadasVigentes;
import excepciones.PaqueteYaComprado;
import logica.Oferta.Estados;
import logicaDAO.EmpresaDAO;

/**
 *
 */
public class Empresa extends Usuario {
	private Map<String, Oferta> misOfertas;
	private Map<String, Oferta> misOfertasConfirmadas;
	private Set<Compra> misCompras; // en paquete o individuales, set pues no tienen id las compras
	private Map<String, Paquete> misPaquetesComprados;
    private String descripcion;
    private String link;


	/**
     * Default constructor
     */
    public Empresa(String nickU, String nomU, String surnameU, String mailU, String contra,  String imagen, String descE, String linkE) {
    	super(nickU, nomU, surnameU, mailU, contra, imagen);
    	this.descripcion = descE;
    	this.link = linkE;
    	misOfertas = new HashMap<>();
		misOfertasConfirmadas = new HashMap<>();
		misCompras = new HashSet<>();
		misPaquetesComprados = new HashMap<>();
    }


    /**
     * @return
     * @throws NoExisteOfertaEmpresa
     */

    //DEVUELVE TODAS LAS OFERTAS!!!!!! Al final devuelve TODAS las ofertas pues no distinguimos.
    //No tiene sentido tener esta si ya esta obtenerTodasLasOfertas pero por si se uso en algun lado se deja
    public Set<String> obtenerOfertasVigentes() throws NoExisteOfertaEmpresa {
        Set<String> ovs = misOfertas.keySet();
        if (ovs.isEmpty()) {
        	throw new NoExisteOfertaEmpresa("La empresa seleccionada no posee ofertas vigentes.");
        }
        return ovs;
    }

    public Set<String> obtenerTodasLasOfertas() throws NoExisteOfertaEmpresa {
        Set<String> ofertasTodas = misOfertas.keySet();
       // ofertasTodas.addAll(MisOfertasVigentes.keySet());
       // ofertasTodas.addAll(MisOfertasNoVigentes.keySet());
        /*
        if(ofertasTodas.isEmpty()) {
        	throw new NoExisteOfertaEmpresa("La empresa seleccionada no posee ofertas, ni vigentes ni no vigentes.");
        }
        */
        return ofertasTodas;
    }

    /**
     * @param ofe
     */
    public void agregarOferta(Oferta ofe) {
        misOfertas.put(ofe.getNombre(), ofe);
        if (ofe.getEstado() == Estados.confirmada) {
        	misOfertasConfirmadas.put(ofe.getNombre(), ofe);
        }
    }

	public void agregarCompra(Compra com) {
		misCompras.add(com);
		misPaquetesComprados.put(com.getPaqueteAsociado().getNombre(),com.getPaqueteAsociado());

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
    	this.descripcion = value;
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
    	this.link = value;
    }


    @Override
    public DTUsuario getDTUsuario() {
    	String nick = getNickname();
    	String nom = getNombre();
    	String ape = getApellido();
    	String corr = getCorreo();
    	String desc = getDescripcion();
    	String link = getLink();
    	String contra = getContrasenia();
    	String imagen = getImagen();
    	Set<String> seguidores = getSeguidores();
    	Set<String> seguidos = getSeguidos();

    	return new DTEmpresa(nick, nom, ape, corr, contra, imagen, desc, link,seguidores, seguidos);
    }

    @Override
    public Set<DTOferta> getDTOfertas(){
    	Set<DTOferta> res = new HashSet<>();
    	DTOferta dtO = null;

    	for (Oferta ofe: misOfertas.values()) {
    		dtO = ofe.getDTOferta();
    		res.add(dtO);
    	}

    	return res;
    }

	public void modificarDatosE(String nombreU, String surnameU, String contra, String ima, String descE, String linkE) {
		// TODO Auto-generated method stub
		setNombre(nombreU);
		setApellido(surnameU);
		setContrasenia(contra);
		setImagen(ima);
		descripcion = descE;
		link = linkE;
	}

	public Set<Oferta> obtenerOfertasVigentesConfirmadas() throws NoTieneOfertasConfirmadasVigentes {
		Set<Oferta> ofes = getMisOfertasConfirmadas();
		for (Iterator<Oferta> itr = ofes.iterator(); itr.hasNext();) {
		    Oferta ofe = itr.next();
		    if (!ofe.esVigente()) {
		    	itr.remove();
		    }
		}
		if (ofes.isEmpty()) {
			throw new NoTieneOfertasConfirmadasVigentes("No tiene");
		}
		return ofes;
	}

	public Set<Oferta> getMisOfertasConfirmadas() {
		Set<Oferta> aux = new HashSet<>(misOfertasConfirmadas.values());
		return aux;
	}

	public Set<String> obtenerOfertasIngresadas() {
		// TODO Auto-generated method stub
   	 	Set<String> nombres = new HashSet<>();
		for (String clave : misOfertas.keySet()) {
			if (misOfertas.get(clave).getEstado() == Estados.ingresada) {
				nombres.add(clave);

			}
		}
		return nombres;
	}


	//devuelve los paquetes adquiridos que tienen un tipo de publicacion del tipo buscado, y que no esten expiradas
	public Set<String> mostrarComprasPaquetesConElTipo(String nombreTipo) {
		Set<String> paquetes = new HashSet<>();
		Date fechaActual = new Date();

		for(Compra com : misCompras) {
			Map<String,Integer> cantTotales = com.getCantActualPorTipo();
			if(cantTotales.get(nombreTipo)!=null && cantTotales.get(nombreTipo)>0){
				Paquete paq = com.getPaqueteAsociado();
				Calendar cal = Calendar.getInstance();
				cal.setTime(com.getFecha());
				cal.add(Calendar.DAY_OF_MONTH, paq.getValidez());
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(fechaActual);
				if(cal.after(cal2))
					paquetes.add(paq.getNombre());

			}
		}

		/*for (String clave : misPaquetesComprados.keySet()) {
			Paquete paquete = misPaquetesComprados.get(clave);

			if (paquete.getValorCantidadPorTipo(nombreTipo) > 0) {

	            if (paquete.getVencimiento().after(fechaActual)) {
	                paquetes.add(clave);
	            }
	        }
		}*/
		return paquetes;
	}

	public void pagarConElPaquete(String nombrePaq, String nombreTipo) throws NoHayTPRestantes {
		Compra com = getCompraPorNombrePaquete(nombrePaq);
		com.disminuirTipoEnCompra(nombreTipo);
	}


	public Set<String> mostrarComprasPaquetes() { // retorna los nombres de los paquetes comprados por la empresa
		Set<String> nomPaquetes = new HashSet<>();
		for (Compra com : misCompras) {
			if (com.getPaqueteAsociado() != null) { // si la compra esta vinculada a un paquete, agrego el nombre de dicho paquete
				nomPaquetes.add(com.getPaqueteAsociado().getNombre());
			}
		}
		return nomPaquetes;
	}

	public Set<DTOferta> getDTOfertasConfirmadas() { // para consulta de usuario
    	Set<DTOferta> res = new HashSet<>();
    	DTOferta dtO = null;

    	for (Oferta ofe: misOfertasConfirmadas.values()) {
    		dtO = ofe.getDTOferta();
    		res.add(dtO);
    	}

    	return res;
	}

	public void cambiarEstadoOferta(Oferta ofe, Estados estadoAnterior, Estados estado) {
		if (estado == Estados.confirmada) {
			this.misOfertasConfirmadas.put(ofe.getNombre(), ofe);
		}
		else if (estadoAnterior == Estados.confirmada)
			this.misOfertasConfirmadas.remove(ofe.getNombre());

	}



	public Compra comprarPaquete(Paquete paq,Empresa empr) throws PaqueteYaComprado {
		for (Compra c: misCompras) {

			if (paq.getNombre()==(c.getPaqueteAsociado().getNombre())) {
	        	throw new PaqueteYaComprado("El paquete ya fue comprado");
			}
		}
		Date fechaActual = new Date();
		Compra nuevaCompra = new Compra(fechaActual,paq,empr);
		misCompras.add(nuevaCompra);
		return nuevaCompra;

	}

	public Set<DTCompra> getDTCompras() {
		// TODO Auto-generated method stub
		Set<DTCompra> res = new HashSet<>();
		for (Compra comp : misCompras) {
			res.add(comp.getDTCompra(getNickname()));
		}
		return res;
	}

	public Compra getCompraPorNombrePaquete(String nombrePaq) {
		for(Compra compra : misCompras) {
			if(compra.getPaqueteAsociado().getNombre().equals(nombrePaq))
				return compra;
		}
		return null;
	}


	public DTOferta obtenerDTUltimaOfertaVigente() throws NoTieneOfertasConfirmadasVigentes {
		Set<Oferta> ofertas = obtenerOfertasVigentesConfirmadas();

		Oferta ult = ofertas.iterator().next();
		Date fechaUlt = ult.getFechaAlta();
		for(Oferta ofe: ofertas) {
			Date fecha = ofe.getFechaAlta();
			if(fecha.after(fechaUlt)) {
				fechaUlt = fecha;
				ult = ofe;
			}

		}
		return ult.getDTOferta();
	}

	public Set<DTOferta> obtenerOfertasConfirmadasNoVigentes(){
	    	Set<DTOferta> res = new HashSet<>();
	    	DTOferta dtO = null;
	    	for (Oferta ofe: misOfertasConfirmadas.values()) {
	    		if (!ofe.esVigente()) {
	    			dtO = ofe.getDTOferta();
					res.add(dtO);
				}
	    	}
		return res;
	}

	public Set<DTOferta> obtenerOfertasConfirmadasVigentes(){
    	Set<DTOferta> res = new HashSet<>();
    	DTOferta dtO = null;
    	for (Oferta ofe: misOfertasConfirmadas.values()) {
    		if (ofe.esVigente()) {
    			dtO = ofe.getDTOferta();
				res.add(dtO);
			}
    	}
	return res;
	}

	public void finalizarOferta(String oferta) {
		misOfertasConfirmadas.remove(oferta);
	}



	public EmpresaDAO crearEmpresaDAO(String oferta) {
		EmpresaDAO empDAO = new EmpresaDAO(getNickname(),getNombre(),getApellido(),getCorreo(),descripcion,link,getImagen());
		misOfertas.remove(oferta);
		misOfertasConfirmadas.remove(oferta);
		return empDAO;
	}

}
