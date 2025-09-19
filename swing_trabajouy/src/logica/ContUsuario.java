package logica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import excepciones.CorreoUsuarioExistente;
import excepciones.ErrorFecha;
import excepciones.NicknameUsuarioExistente;
import excepciones.NoExisteEmpresa;
import excepciones.NoExisteOferta;
import excepciones.NoExisteOfertaEmpresa;
import excepciones.NoExistePaquete;
import excepciones.NoExistePostulante;
import excepciones.NoExistenEmpresas;
import excepciones.NoExistenUsuarios;
import excepciones.NoTieneOfertasConfirmadasVigentes;
import excepciones.OfertaExpirada;
import excepciones.PostulantePoseeOferta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import logica.Oferta.Estados;
import logicaDAO.EmpresaDAO;
import logicaDAO.KeywordDAO;
import logicaDAO.OfertaDAO;
import logicaDAO.PostulacionDAO;
import logicaDAO.PostulanteDAO;
/**
 *
 */
public class ContUsuario implements IUsuario{
	private ManejadorUsuario mUsuario;
	private ManejadorOferta mOferta;
    /**
     * Default constructor
     */
    public ContUsuario() {
    	mUsuario = ManejadorUsuario.getInstance();
    	mOferta = ManejadorOferta.getInstance();
    }

    @Override
    public boolean nickValido(String nick) {
    	return !mUsuario.existeNicknameUsuario(nick);
    }

    /**
     * @param empresa
     * @return
     * @throws NoTieneOfertasConfirmadasVigentes
     */
    @Override
	public Set<String> mostrarOfertasVigentesEmpresa(String empresa) throws NoExisteEmpresa, NoExisteOfertaEmpresa, NoTieneOfertasConfirmadasVigentes {
    	Empresa emp = mUsuario.obtenerEmpresa(empresa);
        Set<Oferta> ofs = emp.obtenerOfertasVigentesConfirmadas();
        Set<String> res = new HashSet<>();
        for(Oferta oferta : ofs) {
        	res.add(oferta.getNombre());
        }
        return res;
    }

    /**bt
     * @return
     * @throws ErrorFecha
     */
    @Override
	public Set<String> mostrarEmpresas() throws NoExistenEmpresas {
    	return mUsuario.obtenerNicknamesEmpresas();
    }

    /**
     * @param nombreOferta
     * @return
     * @throws NoExisteOferta
     */
    @Override
	public DTOferta mostrarInfoDetalladaOferta(String nombreOferta) throws NoExisteOferta {
    	Oferta ofe = mOferta.obtenerInstanciaO(nombreOferta);
        return ofe.getDTOferta();
    }

    /**
     * @return
     * @throws NoExistenUsuarios
     */
    @Override
	public Set<String> mostrarPostulantes() throws NoExistenUsuarios{
    	Set<String> res = mUsuario.obtenerNicknamesPostulantes();
    	return res;

    }

    /**
     * @param postulante
     * @param cvRed
     * @param motivacion
     * @param fecha
     * @param oferta
     */
    @Override
	public void postularAOferta(String postulante, String cvRed, String motivacion, Date fecha, String video, String oferta) throws OfertaExpirada, NoExistePostulante, PostulantePoseeOferta, NoExisteOferta, ErrorFecha {

 		EntityManagerFactory emf = Persistence.createEntityManagerFactory("trabajouy");
 		EntityManager entm = emf.createEntityManager();
 		TypedQuery<OfertaDAO> query = entm.createQuery("SELECT o FROM OfertaDAO o WHERE o.nombre = :nombre", OfertaDAO.class);
 		query.setParameter("nombre", oferta);

 		List<OfertaDAO> resultados = query.getResultList();
 	    boolean noEsta = true;
 		if(!resultados.isEmpty()) {
 			OfertaDAO ofeDAO = resultados.get(0);
 	 	    Set<PostulacionDAO> postulaciones = ofeDAO.getPostulacionesDAO();
 	 	    for(PostulacionDAO postulacion :  postulaciones) {
 	 	    	if(oferta.equals(postulacion.getIdoferta().getNombre())){
 	 	    		noEsta = false;
 	 	    	}
 	 	    }
 		}
 	    if(noEsta) {
	    	Postulante pos = mUsuario.obtenerInstanciaP(postulante);
	        pos.asociadoAOferta(oferta);
	        Oferta ofe = mOferta.obtenerInstanciaO(oferta);
	        Date venc = ofe.getVencimiento();
	        if (venc.before(fecha)) {
	        	throw new OfertaExpirada("Ya expiro oferta");
	        }
	        if (!((ofe.getFechaAlta()).before(fecha)) && !ofe.getFechaAlta().equals(fecha)) {
	        	throw new ErrorFecha("Fecha errada");
	        }
	        Postulacion postul = pos.crearPostulacion(cvRed, motivacion, fecha, video, oferta);
	        postul.addOferta(ofe);
	        ofe.addPostulacion(postul);
 	    }
    }

    /**
     * @return
     */
    @Override
	public void postularAOferta(String postulante, String cvRed, String motivacion, Date fecha, String oferta) throws OfertaExpirada, NoExistePostulante, PostulantePoseeOferta, NoExisteOferta, ErrorFecha {
 		EntityManagerFactory emf = Persistence.createEntityManagerFactory("trabajouy");
 		EntityManager entm = emf.createEntityManager();
 		TypedQuery<PostulanteDAO> query = entm.createQuery("SELECT FROM PostulanteDAO p WHERE p.nickname = :nickname", PostulanteDAO.class);
 		query.setParameter("nickname", postulante);
 		query.setMaxResults(1);
 	    PostulanteDAO resultado = query.getSingleResult();
 	    Set<PostulacionDAO> postulaciones = resultado.getPostulaciones();
 	    boolean noEsta = true;
 	    for(PostulacionDAO pos :  postulaciones) {
 	    	if(oferta.equals(pos.getIdoferta().getNombre())){
 	    		noEsta = false;
 	    	}
 	    }
 	    if(noEsta) {
	    	Postulante pos = mUsuario.obtenerInstanciaP(postulante);
	        pos.asociadoAOferta(oferta);
	        Oferta ofe = mOferta.obtenerInstanciaO(oferta);
	        Date venc = ofe.getVencimiento();
	        if (venc.before(fecha)) {
	        	throw new OfertaExpirada("Ya expiro oferta");
	        }
	        if (!((ofe.getFechaAlta()).before(fecha)) && !ofe.getFechaAlta().equals(fecha)) {
	        	throw new ErrorFecha("Fecha errada");
	        }
	        Postulacion postul = pos.crearPostulacion(cvRed, motivacion, fecha, "", oferta);
	        postul.addOferta(ofe);
	        ofe.addPostulacion(postul);
 	    }
    }

    /**
     * @return
     */

    @Override
	public Set<String> listarNicknamesUsuarios() {
        // TODO implement here
        Set<String> nicknames = mUsuario.obtenerNicknames();
        return nicknames;
    }

    /**
     * @param nickElegido
     * @return
     */
    @Override
	public DTUsuario mostrarDatosUsuario(String nickElegido) throws NoExisteEmpresa, NoExistePostulante{
    	return mUsuario.getDTUsuario(nickElegido);
    }


    @Override
	public Set<String> mostrarComprasPaquetesEmpresa(String nickElegido) throws NoExisteEmpresa{ // da los nombres de los paquetes
    	Set<String> str = new HashSet<>();
		Empresa emp = mUsuario.obtenerEmpresa(nickElegido);
		str = emp.mostrarComprasPaquetes(); // nombres de paquetes que compro la empresa

    	return str;
    }

    /**
     * @param nickElegido
     * @return
     */
    @Override
	public Set<DTOferta> mostrarInfoBasicaOfertas(String nickElegido) throws NoExisteEmpresa, NoExistePostulante{
        // TODO implement here
        return mUsuario.getDTOfertas(nickElegido);
    }

    @Override
	public Set<DTOferta> mostrarOfertasConfirmadas(String nickElegido) throws NoExisteEmpresa, NoExistePostulante{
    	return mUsuario.getDTOfertasConfirmadas(nickElegido);

    }

    /**
     * @param nickElegido
     * @param surnameU
     * @param fNac
     * @param nac
     * @throws NoExistePostulante
     */
    @Override
    public void modificarDatosPostulante(String nickElegido, String nombreU, String surnameU, String contra, String ima, String nac, Date fNac) throws NoExistePostulante  {
        // TODO implement here
    	Postulante pos = mUsuario.obtenerInstanciaP(nickElegido);
		if (contra.matches("^\\s+$") || contra.isEmpty()) {
			contra = pos.getContrasenia(); // NO CAMBIAR
		}
    	pos.modificarDatosP(nombreU, surnameU, contra, ima, nac, fNac);

    }
    /**
     * @param nickElegido
     * @param descE
     * @param linkE
     * @throws NoExisteEmpresa
     */
    @Override
	public void modificarDatosEmpresa(String nickElegido, String NombreU, String surnameU, String contra, String ima, String descE, String linkE) throws NoExisteEmpresa {
        // TODO implement here
    	Empresa emp = mUsuario.obtenerEmpresa(nickElegido);
    	// si no se ingresa contrasenia nueva en la presentacion, se deja la misma de antes.
		if (contra.matches("^\\s+$") || contra.isEmpty()) {
			contra = emp.getContrasenia(); // NO CAMBIAR
		}
    	emp.modificarDatosE(NombreU, surnameU, contra, ima, descE, linkE);
    }


    @Override
	public boolean iniciarSesion(String nickMail, String contra) throws NoExisteEmpresa, NoExistePostulante{
    	boolean logueado = false;
    	logueado = mUsuario.iniciarSesion(nickMail, contra);
    	return logueado;
    }
    /**
     * @param nickU
     * @param nomU
     * @param surnameU
     * @param mailU
     * @param fechaNacU
     * @param nacionU
     */
    @Override
	public void darAltaPostulante(String nickU, String nomU, String surnameU, String mailU, String contra, String imagen, Date fechaNacU, String nacionU) throws NicknameUsuarioExistente, CorreoUsuarioExistente {
    	if (mUsuario.existeNicknameUsuario(nickU)) {
        	throw new NicknameUsuarioExistente("Ya existe un usuario con dicho nickname");
        }
    	if (mUsuario.existeCorreoUsuario(mailU)) {
        	throw new CorreoUsuarioExistente("Ya existe un usuario con dicho correo");
        }
    	Postulante pos = new Postulante(nickU, nomU, surnameU, mailU, contra, imagen, fechaNacU, nacionU);
    	mUsuario.addUsuario(pos);
    }

    /**
     * @param nickU
     * @param nomU
     * @param surnameU
     * @param mailU
     * @param descE
     * @param linkE
     */
    @Override
	public void darAltaEmpresa(String nickU, String nomU, String surnameU, String mailU, String contra, String imagen, String descE, String linkE) throws NicknameUsuarioExistente, CorreoUsuarioExistente{
    	if (mUsuario.existeNicknameUsuario(nickU)) {
        	throw new NicknameUsuarioExistente("Ya existe un usuario con dicho nickname");
        }
    	if (mUsuario.existeCorreoUsuario(mailU)) {
        	throw new CorreoUsuarioExistente("Ya existe un usuario con dicho correo");
        }
    	Empresa emp = new Empresa(nickU, nomU, surnameU, mailU, contra, imagen, descE, linkE);
    	mUsuario.addUsuario(emp);
    }

    @Override
	public Set<String> mostrarOfertasVigentesConfirmadasEK(String empresa, Set<String> keyword) throws NoExisteEmpresa, NoTieneOfertasConfirmadasVigentes{
		Set<String> ofertas = new HashSet<>();
    	if (empresa != null) {
		    	Empresa emp = mUsuario.obtenerEmpresa(empresa);
		    	Set<Oferta> conjOferta = emp.obtenerOfertasVigentesConfirmadas();
		    	if (keyword!=null) {
			    	for (Oferta ofs: conjOferta) {
			    		if (ofs.esOfVigConfKw(keyword)) {
			    			ofertas.add(ofs.getNombre());
			    		}
			    	}
		    	}
		}else {
			ofertas = mOferta.obtenerOfertasKeywords(keyword);
		}

		return ofertas;
    }

	@Override
	public Set<String> mostrarOfertasDeEmpresaIngresadas(String nickE) throws NoExisteEmpresa {
		// TODO Auto-generated method stub
		Empresa emp = mUsuario.obtenerEmpresa(nickE);
		return emp.obtenerOfertasIngresadas();
	}

	@Override
	public void aceptarOferta(String nomO) throws NoExisteOferta {
		// TODO Auto-generated method stub
		Oferta ofe = mOferta.obtenerInstanciaO(nomO);
		ofe.setEstado(Estados.confirmada);

	}

	@Override
	public void rechazarOferta(String nomO) throws NoExisteOferta {
		// TODO Auto-generated method stub
		Oferta ofe = mOferta.obtenerInstanciaO(nomO);
		ofe.setEstado(Estados.rechazada);
	}

	@Override
	public Set<String> mostrarOfertasPostulante(String postulante) throws NoExistePostulante{
		Postulante pos = mUsuario.obtenerInstanciaP(postulante);
		return pos.mostrarOfertasPostulante();
	}

	@Override
	public DTPostulacion mostrarInfoPostulacion(String pos, String oferta) throws NoExistePostulante {
		Postulante post = mUsuario.obtenerInstanciaP(pos);
		return post.infoPostulacion(oferta);
	}

	@Override
	public Set<String> listarPaquetesAdquiridosVigentesTipo(String nombreTP, String empresa) throws NoExisteEmpresa {
		Empresa emp = mUsuario.obtenerEmpresa(empresa);
		return emp.mostrarComprasPaquetesConElTipo(nombreTP);
	}

    @Override
	public Set<String> mostrarPostulantes(String ofs) throws NoExisteOferta{
    	Oferta ofe = mOferta.obtenerInstanciaO(ofs);
    	return ofe.getNicksMisPostulaciones();
    }

    @Override
	public void ingresarPaqueteAEmpresa(String nomPaquete, String empresa, Date fecha) throws NoExistePaquete, NoExisteEmpresa{
        Empresa emp = mUsuario.obtenerEmpresa(empresa);
        Paquete paq = ManejadorPaquete.getInstance().getPaquete(nomPaquete);
        Compra com = new Compra(fecha, paq, emp);
        emp.agregarCompra(com);
        paq.agregarCompra(com);
    }


    @Override
	public Set<DTCompra> getDTComprasEmpresa(String nick) throws NoExisteEmpresa{
    	Empresa emp = mUsuario.obtenerEmpresa(nick);
    	return emp.getDTCompras();
    }

    @Override
	public Set<String> getPaquetesSinComprar(String empresa) throws NoExisteEmpresa {
    	Empresa emp = mUsuario.obtenerEmpresa(empresa);
    	Set<String> paquetesYaComprados = emp.mostrarComprasPaquetes();
    	Set<String> paquetes = ManejadorPaquete.getInstance().getPaquetes().keySet();
    	Set<String> res = new HashSet<>();
    	for(String paq : paquetes)
    		res.add(paq);

    	for (Iterator<String> it = res.iterator(); it.hasNext();) {
    	    String actual = it.next();
    	    if (paquetesYaComprados.contains(actual))
    	        it.remove();
    	}
    	/*
    	for(String actual:res) {
    		if(paquetesYaComprados.contains(actual))
    			res.remove(actual);
    	}*/
    	return res;
    }

    @Override
    public Set<DTEmpresa> mostrarDTEmpresas() throws NoExistenEmpresas, NoExisteEmpresa, NoExistePostulante{
    	Set<String> nick_empresas = new HashSet<>();
    	Set<DTEmpresa> res = new TreeSet<>(new ComparadorDTEmpresa());
		nick_empresas = mUsuario.obtenerNicknamesEmpresas();
	    for(String nick: nick_empresas)
	    	res.add((DTEmpresa) mUsuario.getDTUsuario(nick));

    	return res;
    }

	@Override
	public DTPostulante getDTPostulante(String nick) throws NoExistePostulante {
		return mUsuario.getDTPostulante(nick);
	}

	@Override
	public DTEmpresa getDTEmpresa(String nick) throws NoExisteEmpresa {
		return mUsuario.getDTEmpresa(nick);
	}

	@Override
	public void darAltaEmpresa(String nickU, String nomU, String surnameU, String mailU, String contra, String imagen, byte[] datos,
			String descE, String linkE) throws NicknameUsuarioExistente, CorreoUsuarioExistente {
		if (mUsuario.existeNicknameUsuario(nickU)) {
        	throw new NicknameUsuarioExistente("Ya existe un usuario con dicho nickname");
        }
    	if (mUsuario.existeCorreoUsuario(mailU)) {
        	throw new CorreoUsuarioExistente("Ya existe un usuario con dicho correo");
        }
    	String url = "media/img/Usuarios/default/default.jpeg";

    	//Almaceno en folder
    	if(datos!=null && datos[0]!='\0') {
    		try {
				url = "media/img/Usuarios/" + imagen;
				Path copia = Paths.get("data/"+url);
				Files.write(copia, datos);
			}catch(IOException exc) {
				System.out.println("No se pudo guardar la foto");
			}
    	}


    	Empresa emp = new Empresa(nickU, nomU, surnameU, mailU, contra, url, descE, linkE);
    	mUsuario.addUsuario(emp);

	}

	@Override
    public void darAltaPostulante(String nick, String nom, String ape, String mail, String contra, String img, byte[] datos, Date fecha, String pais) throws CorreoUsuarioExistente, NicknameUsuarioExistente {
		if (mUsuario.existeNicknameUsuario(nick)) {
        	throw new NicknameUsuarioExistente("Ya existe un usuario con dicho nickname");
        }
    	if (mUsuario.existeCorreoUsuario(mail)) {
        	throw new CorreoUsuarioExistente("Ya existe un usuario con dicho correo");
        }
    	String url = "media/img/Usuarios/default/default.jpeg";

    	//Almaceno en folder
    	if(datos!= null && datos[0] != '\0') {
    		try {
				url = "media/img/Usuarios/" + img;
				Path copia = Paths.get("data/"+url);
				Files.write(copia, datos);
			}catch(IOException exc) {
				System.out.println("No se pudo guardar la foto");
			}
    	}


    	Postulante pos = new Postulante(nick, nom, ape, mail, contra, url, fecha, pais);
    	mUsuario.addUsuario(pos);

	}

	@Override
	public void seguirUsuario(String miNick, String nickSeguir) throws NoExistePostulante, NoExisteEmpresa {
		mUsuario.seguirUsuario(miNick,nickSeguir);
	}

	@Override
	public void dejarDeSeguirUsuario(String miNick, String nickDejar) throws NoExistePostulante, NoExisteEmpresa {
		mUsuario.dejarDeSeguirUsuario(miNick,nickDejar);
	}


	  @Override
	    public Set<DTOferta> mostrarOfertasConfirmadasNoVigentes(String empresa) throws NoExisteEmpresa, NoExistePostulante{
	    	return mUsuario.getDTOfertasConfirmadasNoVigentes(empresa);

	    }

	    @Override
		public void finalizarOferta(String oferta, String empresa) throws NoExisteOferta, NoExisteEmpresa {
	    	Oferta ofe = mOferta.obtenerInstanciaO(oferta);
	    	Empresa emp = mUsuario.obtenerEmpresa(empresa);
	    	emp.finalizarOferta(oferta);
	    	ofe.cambiarEstadoFinalizada();
	    }

	    @Override
		public void guardarDatosOferta(String oferta) throws NoExisteOferta, NoExistePostulante {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("trabajouy");
			EntityManager entm = emf.createEntityManager();


	    	Oferta ofe = mOferta.obtenerInstanciaO(oferta);
	    	OfertaDAO ofeDAO = ofe.crearOfertaDAO();

	    	Set<String> keys = ofe.getKeyWords();
	    	Set<KeywordDAO> misKeywords = new HashSet<>();
	    	for(String nom : keys) {
		    	TypedQuery<KeywordDAO> query2 = entm.createQuery("SELECT k FROM KeywordDAO k WHERE k.nombre = :nombre", KeywordDAO.class);
				query2.setParameter("nombre", nom);
				List<KeywordDAO> keywords = query2.getResultList();
				if(keywords.isEmpty()) {
					KeywordDAO key = new KeywordDAO(nom);
					misKeywords.add(key);
				}
				else {
					misKeywords.add(keywords.get(0));
				}
	    	}
	    	ofeDAO.setMisKeyWords(misKeywords);

	    	Set<PostulacionDAO> postulacionesDAO= new HashSet<>();
	    	Empresa emp = ofe.getEmpresa();
	    	String nickEmpresaUnico = emp.getNickname(); // Reemplaza con el valor único
			TypedQuery<EmpresaDAO> query = entm.createQuery("SELECT e FROM EmpresaDAO e WHERE e.nickname = :nickname", EmpresaDAO.class);
			query.setParameter("nickname", nickEmpresaUnico);
			List<EmpresaDAO> resultados = query.getResultList();
			EmpresaDAO empDAO;
			if (resultados.isEmpty()) {
				 empDAO = emp.crearEmpresaDAO(oferta);
				 empDAO.addOfertaDAO(ofeDAO);
			}
			else {
			     empDAO = resultados.get(0); // Obtiene el elemento único
			}
	    	ofeDAO.setEmpresa(empDAO);
	    	Set<Postulacion> post = ofe.getMisPostulaciones();
	    	for (Postulacion postu : post) {
	    		String nickP = postu.getNicknamePostulante();
	    		Postulante pos = mUsuario.obtenerInstanciaP(nickP);
	    		String nickPostulanteUnico = pos.getNickname(); // Reemplaza con el valor único
				TypedQuery<PostulanteDAO> query2 = entm.createQuery("SELECT p FROM PostulanteDAO p WHERE p.nickname = :nickname", PostulanteDAO.class);
				query2.setParameter("nickname", nickPostulanteUnico);
				List<PostulanteDAO> resultados2 = query2.getResultList();
				PostulanteDAO posDAOS;
				if (resultados2.isEmpty()) {
					posDAOS = pos.crearPostulanteDAO(oferta);
				}
				else {
					posDAOS = resultados2.get(0); // Obtiene el elemento único
				}
	    		PostulacionDAO postuDAO = postu.crearPostulacionDAO(posDAOS,ofeDAO);
	    		postulacionesDAO.add(postuDAO);
	    		posDAOS.addPostulacionesDAO(postuDAO);
	    	}
	    	ofeDAO.setPostulacionDAO(postulacionesDAO);
	    	//TERMINE DE CREAR LA OFERTADAO Y TODAS LAS CLASES ASOCIADAS
	    	// Acá creamos una transacción asociada con el EntityManager y las entidades
	    				// pasan a estar managed.
	    	EntityTransaction txt = entm.getTransaction();
			txt.begin();
			entm.persist(ofeDAO);

			for(KeywordDAO key : misKeywords) {
				entm.persist(key);
			}

			if (resultados.isEmpty()) {
			    // La oferta no existe en la base de datos, insértala
			    entm.persist(empDAO);
			}

			//PERSISTO LOS POSTULANTES

			//TypedQuery<PostulanteDAO> query3 = em.createQuery("SELECT e FROM PostulanteDAO e", PostulanteDAO.class);
			//List<PostulanteDAO> resultados3 = query3.getResultList();
			for (PostulacionDAO postuDAO : postulacionesDAO) {
			    PostulanteDAO postulante = postuDAO.getIdPostulante();
			    if (!entm.contains(postulante)) {
			        entm.persist(postulante);
			    }
			}

			txt.commit();
			entm.close();
			emf.close();
				// Cerramos el Entity Manager y la Factory. Es importante para que cierre las
				// conexiones con la base de datos.
	    }




	    /**
	     * @param nickElegido
	     * @param surnameU
	     * @param fNac
	     * @param nac
	     * @throws NoExistePostulante
	     */
	    @Override
	    public void modificarDatosPostulante(String nickElegido, String nombreU, String surnameU, String contra, String ima,byte[] datos, String nac, Date fNac) throws NoExistePostulante  {
	        // TODO implement here
	    	Postulante pos = mUsuario.obtenerInstanciaP(nickElegido);
			if (contra.matches("^\\s+$") || contra.isEmpty()) {
				contra = pos.getContrasenia(); // NO CAMBIAR
			}
			String url = pos.getImagen();

	    	//Almaceno en folder
	    	if(datos[0]!='\0') {
	    		try {
					url = "media/img/Usuarios/" + ima;
					Path copia = Paths.get("data/"+url);
					Files.write(copia, datos);
				}catch(IOException exc) {
					System.out.println("No se pudo guardar la foto");
				}
	    	}
	    	pos.modificarDatosP(nombreU, surnameU, contra, url, nac, fNac);

	    }
	    /**
	     * @param nickElegido
	     * @param descE
	     * @param linkE
	     * @throws NoExisteEmpresa
	     */
	    @Override
		public void modificarDatosEmpresa(String nickElegido, String NombreU, String surnameU, String contra, String ima, byte[] datos, String descE, String linkE) throws NoExisteEmpresa {
	        // TODO implement here
	    	Empresa emp = mUsuario.obtenerEmpresa(nickElegido);
	    	// si no se ingresa contrasenia nueva en la presentacion, se deja la misma de antes.
			if (contra.matches("^\\s+$") || contra.isEmpty()) {
				contra = emp.getContrasenia(); // NO CAMBIAR
			}

			String url = emp.getImagen();

	    	//Almaceno en folder
	    	if(datos[0]!='\0') {
	    		try {
					url = "media/img/Usuarios/" + ima;
					Path copia = Paths.get("data/"+url);
					Files.write(copia, datos);
				}catch(IOException exc) {
					System.out.println("No se pudo guardar la foto");
				}
	    	}
	    	emp.modificarDatosE(NombreU, surnameU, contra, url, descE, linkE);
	    }

	    @Override
	    public Set<DTOferta> mostrarOfertasConfirmadasVigentes(String empresa) throws NoExisteEmpresa, NoExistePostulante{
	    	return mUsuario.getDTOfertasConfirmadasVigentes(empresa);

	    }
		@Override
		public Set<DTOferta> listarOfertasFinalizadasAsoc(String miNick){
			Set<DTOferta> res = new HashSet<>();
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("trabajouy");
			EntityManager em = emf.createEntityManager();

			Query query = em.createQuery("SELECT e.misOfertasFinalizadas FROM EmpresaDAO e WHERE e.nickname =:nickname");
			query.setParameter("nickname", miNick);
			List<OfertaDAO> misOfertas = query.getResultList();
			for (OfertaDAO ofe : misOfertas) {
				res.add(ofe.getDTOferta());
			}
			em.close();
			emf.close();

			return res;
		}


	    @Override
	    public Set<String> getFavoritosPostulante(String nickname) throws NoExistePostulante{
	    	Postulante usr = mUsuario.obtenerInstanciaP(nickname);
	    	return usr.getNombresFavoritos();
	    }

	    @Override
	    public void agregarFavoritos(String nickname, String oferta,boolean esFav) throws NoExistePostulante, NoExisteOferta {
	    	Postulante usr = mUsuario.obtenerInstanciaP(nickname);
	    	Oferta ofe = mOferta.obtenerInstanciaO(oferta);
	    	if(esFav)
	    		usr.addFavorito(ofe);
	    	else
	    		usr.removeFavorito(ofe);
	    }

	    @Override
		public Set<DTPostulacion> listarDTPostulacionesDB(String nick){
	    Set<DTPostulacion> res = new HashSet<>();
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("trabajouy");
 		EntityManager entm = emf.createEntityManager();
 		TypedQuery<PostulanteDAO> query = entm.createQuery("SELECT p FROM PostulanteDAO p WHERE p.nickname = :nickname", PostulanteDAO.class);
 		query.setParameter("nickname", nick);
 		List<PostulanteDAO> postulante = query.getResultList();
 		if(!postulante.isEmpty()) {
 			for (PostulacionDAO pos: postulante.get(0).getPostulaciones()){
 				res.add(pos.getDTPostulacion());
 			}
 		}
		entm.close();
		emf.close();
	    return res;
	    }
}
