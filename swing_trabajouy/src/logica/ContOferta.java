package logica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import excepciones.NoExisteEmpresa;
import excepciones.NoExisteKeyword;
import excepciones.NoExisteOferta;
import excepciones.NoExisteOfertaEmpresa;
import excepciones.NoExistePaquete;
import excepciones.NoExistePostulante;
import excepciones.NoExisteTipoPublicacion;
import excepciones.NoExistenEmpresas;
import excepciones.NoHayTPRestantes;
import excepciones.NoTieneOfertasConfirmadasVigentes;
import excepciones.NombreKeywordExistente;
import excepciones.NombreOfertaExistente;
import excepciones.NombrePaqueteExistente;
import excepciones.NombreTipoDePublicacionExistente;
import excepciones.NombreTipoPublicacionExistente;
import excepciones.PaqueteYaComprado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import logica.Oferta.Estados;
import logicaDAO.OfertaDAO;

/**
 *
 */
public class ContOferta implements IOferta {
	private ManejadorPaquete mPaquete;
	private ManejadorKeywords mKeywords;
	private ManejadorTipoPublicacion mTPublicacion;
	private ManejadorOferta mOferta;
	private ManejadorUsuario mUsuario;
    /**
     * Default constructor
     */
    public ContOferta() {
    	mPaquete = ManejadorPaquete.getInstance();
        mKeywords = ManejadorKeywords.getInstance();
        mTPublicacion = ManejadorTipoPublicacion.getInstance();
        mOferta = ManejadorOferta.getInstance();
        mUsuario = ManejadorUsuario.getInstance();
    }

    @Override
	public Set<String> getKeywords(){
        return mKeywords.obtenerNombreKeywords();
    }

    @Override
    public int getExposicionOferta(String oferta) throws NoExisteOferta{
        return mOferta.obtenerInstanciaO(oferta).getTipoPublicacion().getExposicion();
    }

    @Override
	public Map<String, DTOferta> getOfertasVigentes(){
        Map<String, Oferta> ofertas = mOferta.getMisOfertas();
        Map<String, DTOferta> dtos = new HashMap<>();
        for (Map.Entry<String, Oferta> oferta : ofertas.entrySet()) {
            boolean condicion = oferta.getValue().getEstado().equals(Oferta.Estados.confirmada) && oferta.getValue().esVigente();
            if (condicion){
                dtos.put(oferta.getKey(), oferta.getValue().getDTOferta());
            }
        }
        return dtos;
    }


    @Override
	public DTPostulacion getDTPostulacion(String nickname, String nombreOferta) throws NoExisteOferta{
        return mOferta.obtenerInstanciaO(nombreOferta).getDTPostulacion(nickname);
    }


    @Override
	public DTOferta getDTOferta(String nombreOferta) throws NoExisteOferta {
        return mOferta.obtenerInstanciaO(nombreOferta).getDTOferta();
    }

    /**
     * @param nombreK: String que representa el nombre de la keyword
     */
    @Override
	public void altaKeyword(String nombreK) throws NombreKeywordExistente{
        Keyword kws = new Keyword(nombreK);
        mKeywords.agregarKeyword(kws);
    }


    /**
     * @return Set de strings con los nombres de los paquetes registrados
     */
    @Override
	public Set<String> listarPaquetesRegistrados() {
        Map<String, Paquete> paquetes = mPaquete.getPaquetes();
        Set<String> nombres = new HashSet<>();
        for (Map.Entry<String, Paquete> entry : paquetes.entrySet()) {
            nombres.add(entry.getKey());
        }
        return nombres;
    }



    /**
     * @param nombreP nombre del paquete
     * @return Retorna un DTPaquete con la informacion del paquete
     * @throws NoExistePaquete  si no existe un paquete con el nombre ingresado
     */
    @Override
	public DTPaquete getInfoPaquete(String nombreP) throws NoExistePaquete{
        Paquete paq = mPaquete.getPaquete(nombreP);
        DTPaquete dtp = paq.getInfo();
        return dtp;
    }

    /**
     * @param nombreTP
     * @return
     * @throws NoExisteTipoPublicacion
     */
    @Override
	public DTTipoPublicacion getInfoTipoPublicacion(String nombreTP) throws NoExisteTipoPublicacion {
        TipoPublicacion tpb = mTPublicacion.getTP(nombreTP);
        DTTipoPublicacion dtp = tpb.getInfo();
		return dtp;
    }

    /**
     * @param nombreTP
     * @param descTP
     * @param expTP
     * @param duracionTP
     * @param costoTP
     * @param fechaAltaTP
     * @return
     * @throws NombreTipoDePublicacionExistente
     */
    @Override
	public void altaTipoDePublicacion(String nombreTP, String descTP, int expTP, int duracionTP, double costoTP, Date fechaAltaTP) throws NombreTipoPublicacionExistente {
        mTPublicacion.agregarTipoDePublicacion(nombreTP, descTP, expTP, duracionTP, costoTP, fechaAltaTP);
    }

    /**
     * @return
     * @throws NoExisteTipoPublicacion
     */
    @Override
	public Set<String> listarNombresTipos() throws NoExisteTipoPublicacion {
    	Set<String> res = mTPublicacion.obtenerNombreTipos();
    	if (res.isEmpty())
    		throw new NoExisteTipoPublicacion("No existe ningun Tipo de Publicación en el sistema");
        return res;
    }

    @Override
	public Set<String> listarNombresKeywords() {
    	return mKeywords.obtenerNombreKeywords();
    }

    /**
     * @param nombreP
     * @param nombreTP
     * @param cantTP
     * @throws NoExisteTipoPublicacion
     * @throws NoExistePaquete
     */
    @Override
	public void ingresarTipoAPaquete(String nombreP, String nombreTP, int cantTP) throws NoExisteTipoPublicacion, NoExistePaquete {
    	TipoPublicacion tpb = mTPublicacion.getTP(nombreTP);
    	Paquete paq = mPaquete.getPaquete(nombreP);
    	CantidadPorTipo cant = new CantidadPorTipo(cantTP, tpb, paq);
    	paq.ingresarTipoAPaquete(cant);

    }

    /**
     * @param nombre
     * @param descripcion
     * @param validez
     * @param descuento
     * @param fechaAlta
     * @return
     * @throws NombrePaqueteExistente
     */
    @Override
	public void crearPaquete(String nombre, String descripcion, int validez, double descuento, Date fechaAlta, String img) throws NombrePaqueteExistente {
        mPaquete.crearPaquete(nombre, descripcion, validez, descuento, fechaAlta, img);
    }




    /**
     * @param empresa
     * @param nombreTP
     * @param nomOferta
     * @param descrip
     * @param horario
     * @param remuneracion
     * @param ciudad
     * @param depart
     * @param f@Override
	echaAlta
     * @param keywords
     * @throws NoExisteTipoPublicacion
     * @throws NoExisteEmpresa
     */

    @Override
	public void ingresarOferta(String empresa, String nombreTP, String nomOferta, String descrip, String horario, double remuneracion, String ciudad, String depart, Date fechaAlta, Set<String> keywords,String img) throws NoExisteKeyword, NombreOfertaExistente, NoExisteTipoPublicacion, NoExisteEmpresa{


	    	//Chequear que existan las keywords
	        for (String kws : keywords) {
	            mKeywords.getKeyword(kws);
	         }
	        Empresa emp = mUsuario.obtenerEmpresa(empresa);
	        TipoPublicacion tpb = mTPublicacion.getTP(nombreTP);

	        if (img == null || img.equals(""))
	            img = "media/img/OfertasLaborales/default/default.jpg";

	        Oferta ofe = new Oferta(nomOferta, descrip, ciudad, depart, horario, remuneracion, fechaAlta, tpb, img, emp);

	        //se agrega la oferta a la coleccion gral de ofertas
	        mOferta.agregarOferta(ofe);

		    //SI LA OFERTA ESTA EN LA BASE DE DATOS NO LA PONE EN MISPAQUETES DE EMPRESA
	 		EntityManagerFactory emf = Persistence.createEntityManagerFactory("trabajouy");
	 		EntityManager entm = emf.createEntityManager();
	 		TypedQuery<OfertaDAO> query = entm.createQuery("SELECT o FROM OfertaDAO o WHERE o.nombre = :nombre", OfertaDAO.class);
	 		query.setParameter("nombre", nomOferta);
	 		List<OfertaDAO> resultados = query.getResultList();
	        if(resultados.isEmpty()) {	//ENTRA SI LA OFERTA NO ESTA PERSISTIDA
	        //se asocia a su empresario, agregandose entre sus ofertas
	        	emp.agregarOferta(ofe);
	        }
	        tpb.asignarOferta(ofe);
	        ofe.setTipoPublicacion(tpb);

	        for (String k : keywords) {
	            Keyword key = mKeywords.getKeyword(k);
	            key.asignarOferta(ofe);
	            ofe.asignarleKeyword(key);
	        }

	}

	@Override
	public Set<String> listarEmpresas() throws NoExistenEmpresas{
    	return mUsuario.obtenerNicknamesEmpresas();
    }

    @Override
	public Set<String> listarOfertasDeEmpresa(String empresa) throws NoExisteEmpresa, NoExisteOfertaEmpresa {
    	return mUsuario.obtenerEmpresa(empresa).obtenerTodasLasOfertas();
    }

    @Override
	public DTOferta obtenerOfertaPorNombre(String selectedOferta) throws NoExisteOferta {
    	return mOferta.obtenerInstanciaO(selectedOferta).getDTOferta();
    }

    @Override
    public int getCantPorTP(String pSeleccionado, String tSeleccionado) throws NoExistePaquete{
    	Paquete paq = mPaquete.getPaquete(pSeleccionado);
    	// debo acceder a la CantidadPorTipo asociada a tSeleccionado y devolver esa cant
        int cant = paq.getValorCantidadPorTipo(tSeleccionado);
    	return cant;
    }

	 /**
     * @return Set de strings con los nombres de los paquetes registrados
     */
    @Override
    public Set<String> listarPaquetesLibres(){
    	Map<String, Paquete> paq = mPaquete.getPaquetes();
    	 Set<String> nombres = new HashSet<>();
    	 Boolean res;
    	for (String clave :paq.keySet()) {
    		Paquete paquete = paq.get(clave);
    		res = paquete.noEstaComprado();

    		if (res) {
    			nombres.add(clave);
    		}
    	}
    	return nombres;
	}


    @Override
    public boolean fuePagadaConPaquete(String nomOferta) throws NoExistePaquete, NoExisteOferta {
    	Oferta ofe = mOferta.obtenerInstanciaO(nomOferta);
    	return ofe.tieneCompraAsociada();
    }

    @Override
    public void pagarConPaquete(String nomOferta, String nombrePaq, String nombreTipo, String empresa) throws NoExisteOferta, NoExisteEmpresa, NoExisteTipoPublicacion, NoExistePaquete, NoHayTPRestantes {
    	Empresa empr = mUsuario.obtenerEmpresa(empresa);
    	empr.pagarConElPaquete(nombrePaq, nombreTipo);
    	Compra com = empr.getCompraPorNombrePaquete(nombrePaq);
    	Oferta oferta = mOferta.obtenerInstanciaO(nomOferta);
    	oferta.setCompra(com);
    }

    @Override
    public void cambiarEstadoOferta(String nombreOferta, Estados estado) throws NoExisteOferta{
        Oferta ofe = mOferta.obtenerInstanciaO(nombreOferta);
        ofe.setEstado(estado);

    }

    @Override
    public void modificarOferta(String empresa, String nomOferta, String descrip, String horario, double remuneracion, String ciudad, String depart, Set<String> keywords, String imagen) throws NoExisteOferta, NoExisteEmpresa, NoExisteKeyword {
        // Consigo la oferta existente a modificar
        Oferta existingOferta = mOferta.obtenerInstanciaO(nomOferta);

        // Le cambio los atributos (recordar q nombre, tipo publi y fecha alta se mantienen)
        existingOferta.setDescripcion(descrip);
        existingOferta.setHorario(horario);
        existingOferta.setRemuneracion(remuneracion);
        existingOferta.setCiudad(ciudad);
        existingOferta.setDepartamento(depart);
        existingOferta.setImagen(imagen);

        // Se quitan todas las keywords, para luego asignarle solo las que se marcaron
        existingOferta.quitarKeywords();
        for (String key : keywords) {
            Keyword keyword = mKeywords.getKeyword(key);
            keyword.asignarOferta(existingOferta);
            existingOferta.asignarleKeyword(keyword);
        }
    }



    @Override
    public TreeSet<DTOferta> getOfertasVigentesOrdenadas(){
        Map<String, Oferta> ofertas = mOferta.getMisOfertas();
        TreeSet<DTOferta> dtos = new TreeSet<>(new ComparadorExposicionSet());
        for (Map.Entry<String, Oferta> oferta : ofertas.entrySet()) {
            boolean condicion = oferta.getValue().getEstado().equals(Oferta.Estados.confirmada) && oferta.getValue().esVigente();
            if (condicion){
                dtos.add(oferta.getValue().getDTOferta());
            }
        }
        return dtos;
    }

    @Override
	public void comprarPaquete(String nombreP, String empresa) throws NoExisteEmpresa, NoExistePaquete, PaqueteYaComprado {
		Empresa empr = mUsuario.obtenerEmpresa(empresa);
		Paquete paq = mPaquete.getPaquete(nombreP);
		Compra compraNueva = empr.comprarPaquete(paq,empr);
		paq.agregarCompra(compraNueva);
	}




    /**
     * @param nombre
     * @param descripcion
     * @param validez
     * @param descuento
     * @param fechaAlta
     * @return
     * @throws NombrePaqueteExistente
     */
    @Override
	public void crearPaqueteConImg(String nombre, String descripcion, int validez, double descuento, Date fechaAlta, String img) throws NombrePaqueteExistente {
        mPaquete.crearPaqueteConImg(nombre, descripcion, validez, descuento, fechaAlta, img);
    }



    /**
     * @param nombreP nombre del paquete
     * @return Retorna un DTPaquete con la informacion del paquete
     * @throws NoExistePaquete  si no existe un paquete con el nombre ingresado
     */
    @Override
	public DTPaquete getInfoPaqueteConImg(String nombreP) throws NoExistePaquete{
        Paquete paq = mPaquete.getPaquete(nombreP);
        DTPaquete dtp = paq.getInfoConImg();
        return dtp;
    }

    @Override
    public DTOferta obtenerDTUltimaOfertaVigente(String empresa) throws NoExisteEmpresa, NoExisteOfertaEmpresa, NoTieneOfertasConfirmadasVigentes {
    	Empresa emp = mUsuario.obtenerEmpresa(empresa);
    	return emp.obtenerDTUltimaOfertaVigente();
    }

    @Override
    public int getCantVisitas(String nom_oferta) throws NoExisteOferta {
    	Oferta ofe = mOferta.obtenerInstanciaO(nom_oferta);
    	return ofe.getCantVisitas();
    }

    @Override
    public void incrementarVisitas(String nom_oferta) throws NoExisteOferta {
    	Oferta ofe = mOferta.obtenerInstanciaO(nom_oferta);
    	ofe.incrementarVisita();
    }

    @Override
    public Set<DTOferta> getTOPofertas() throws NoExisteOferta{
    	Map<String,Oferta> ofertas = mOferta.getMisOfertas();
    	Set<DTOferta> top5 = new TreeSet<>(new ComparadorCantVisitas());
        //get top 5 ofertas con mas visitas
        for (Map.Entry<String, Oferta> oferta : ofertas.entrySet()) {
            if (oferta.getValue().getEstado().equals(Oferta.Estados.confirmada)){
            	top5.add(oferta.getValue().getDTOferta());
            }
        }
        if(top5.size()==0)
        	throw new NoExisteOferta("No existen ofertas confirmadas");
    	return top5;
    }

    @Override
	public void asignarOrdenPostulacion(String nickPost, String nomOferta, int orden, Date fechaSelec) throws NoExisteOfertaEmpresa, NoExistePostulante, NoExisteOferta {
    	Oferta of = mOferta.obtenerInstanciaO(nomOferta);
        Postulante p = mUsuario.obtenerInstanciaP(nickPost);
        Postulacion po = p.getPostulacionOferta(nomOferta);
        po.setOrden(orden);
        po.setFechaSelec(fechaSelec);

    }

    @Override
    public boolean vigenciaOferta(String nomOferta) throws NoExisteOferta {
    	Oferta of = mOferta.obtenerInstanciaO(nomOferta);
    	return of.esVigente();
    }

    @Override
   	public void ingresarOferta(String empresa, String nombreTP, String nomOferta, String descrip, String horario, double remuneracion, String ciudad, String depart, Date fechaAlta, Set<String> keywords,String img, byte[] datos) throws NoExisteKeyword, NombreOfertaExistente, NoExisteTipoPublicacion, NoExisteEmpresa{
           //Chequear que existan las keywords
           for (String kws : keywords) {
               mKeywords.getKeyword(kws);
            }
           Empresa emp = mUsuario.obtenerEmpresa(empresa);
           TipoPublicacion tpb = mTPublicacion.getTP(nombreTP);

           String url = "media/img/OfertasLaborales/default/default.jpg";

          	//Almaceno en folder
          	if(datos[0]!='\0') {
          		try {
      				url = "media/img/OfertasLaborales/" + img;
      				Path copia = Paths.get("data/"+url);
      				Files.write(copia, datos);
      			}catch(IOException exc) {
      				System.out.println("No se pudo guardar la foto");
      			}
          	}


           Oferta ofe = new Oferta(nomOferta, descrip, ciudad, depart, horario, remuneracion, fechaAlta, tpb, url, emp);

           //se agrega la oferta a la coleccion gral de ofertas
           mOferta.agregarOferta(ofe);

           //se asocia a su empresario, agregandose entre sus ofertas
           emp.agregarOferta(ofe);


           tpb.asignarOferta(ofe);
           ofe.setTipoPublicacion(tpb);

           for (String k : keywords) {
               Keyword key = mKeywords.getKeyword(k);
               key.asignarOferta(ofe);
               ofe.asignarleKeyword(key);
           }

       }

    @Override
    public void modificarOferta(String empresa, String nomOferta, String descrip, String horario, double remuneracion, String ciudad, String depart, Set<String> keywords, String img, byte[] datos) throws NoExisteOferta, NoExisteEmpresa, NoExisteKeyword {
        // Consigo la oferta existente a modificar
        Oferta existingOferta = mOferta.obtenerInstanciaO(nomOferta);

        // Le cambio los atributos (recordar q nombre, tipo publi y fecha alta se mantienen)
        existingOferta.setDescripcion(descrip);
        existingOferta.setHorario(horario);
        existingOferta.setRemuneracion(remuneracion);
        existingOferta.setCiudad(ciudad);
        existingOferta.setDepartamento(depart);

        // Se quitan todas las keywords, para luego asignarle solo las que se marcaron
        existingOferta.quitarKeywords();
        for (String key : keywords) {
            Keyword keyword = mKeywords.getKeyword(key);
            keyword.asignarOferta(existingOferta);
            existingOferta.asignarleKeyword(keyword);
        }

        String url = "media/img/OfertasLaborales/default/default.jpg";

      	//Almaceno en folder
      	if(datos[0]!='\0') {
      		try {
  				url = "media/img/OfertasLaborales/" + img;
  				Path copia = Paths.get("data/"+url);
  				Files.write(copia, datos);
  			}catch(IOException exc) {
  				System.out.println("No se pudo guardar la foto");
  			}
      	}
        existingOferta.setImagen(url);
    }

    @Override
	public DTOferta getDTOfertaDAO(String nombreOferta) throws NoExisteOferta{
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("trabajouy");
 		EntityManager entm = emf.createEntityManager();
 		TypedQuery<OfertaDAO> query = entm.createQuery("SELECT o FROM OfertaDAO o WHERE o.nombre = :nombre", OfertaDAO.class);
 		query.setParameter("nombre", nombreOferta);
 		List<OfertaDAO> oferta = query.getResultList();

 		DTOferta res= null;
 		if(!oferta.isEmpty()) {

 			res = mOferta.obtenerInstanciaO(nombreOferta).getDTOfertaDAO(oferta.get(0));
 		}
 		else {
 			res = mOferta.obtenerInstanciaO(nombreOferta).getDTOferta();
 		}
    	return res;
    }


}
