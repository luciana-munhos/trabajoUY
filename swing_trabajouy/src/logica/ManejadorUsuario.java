package logica;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import excepciones.ErrorFecha;
import excepciones.NoExisteEmpresa;
import excepciones.NoExistePostulante;
import excepciones.NoExistenEmpresas;
import excepciones.NoExistenUsuarios;

/**
 *
 */
public class ManejadorUsuario {

	private static ManejadorUsuario Instancia;
	private Map<String, Usuario> misUsuarios;
	private Map<String, Postulante> misPostulantes;
	private Map<String, Empresa> misEmpresas;
    /**
     * Default constructor
     */
    private ManejadorUsuario() {
    	misUsuarios = new HashMap<>();
    	misPostulantes = new HashMap<>();
    	misEmpresas = new HashMap<>();
    }

    /**
     *
     */
    public static ManejadorUsuario getInstance() {
    	if (Instancia == null) {
    		Instancia = new ManejadorUsuario();
    	}
    	return Instancia;
    }

    /**
     * @return retorna un set con los nicknames de las empresas
     */
    public Set<String> obtenerNicknamesEmpresas() throws NoExistenEmpresas {
    	Set<String> res =  misEmpresas.keySet();
        if (res.isEmpty()) {
        	throw new NoExistenEmpresas("No hay empresas");
        }
        return res;
    }

    /**
     * @param empresa
     * @return
     */
    public Empresa obtenerEmpresa(String empresa) throws NoExisteEmpresa {
        if (!misEmpresas.containsKey(empresa)) {
            throw new NoExisteEmpresa("No existe la empresa");
        }
        return misEmpresas.get(empresa);
    }

    /**
     * @return
     * @throws ErrorFecha
     */
    public Set<String> obtenerNicknamesPostulantes() throws NoExistenUsuarios {
    	Set<String> res =misPostulantes.keySet();
    	if (res.isEmpty()) {
    		throw new NoExistenUsuarios("No existen usuarios en el sistema.");
    	}
    	return res;
    }

    /**
     * @param postulante
     * @return
     */
    public Postulante obtenerInstanciaP(String postulante) throws NoExistePostulante {
        if (!misPostulantes.containsKey(postulante)) {
        	throw new NoExistePostulante("No existe el postulante deseado");
        }


        return misPostulantes.get(postulante);
    }

    /**
     * @param u
     */
    public void addUsuario(Usuario usr) {
    	String clave = usr.getNickname();
    	misUsuarios.put(clave, usr);
    	// dynamic_cast to add to misPostulantes or misEmpresas
        if (usr instanceof Postulante) {
            misPostulantes.put(clave, (Postulante) usr);
        } else if (usr instanceof Empresa) {
            misEmpresas.put(clave, (Empresa) usr);
        }
    }

    public boolean existeNicknameUsuario(String nick) {
    	return misUsuarios.containsKey(nick);
    }

    public boolean existeCorreoUsuario(String mailU) {
    	String correoMinu;
    	for (Usuario usu: misUsuarios.values()) {
    		correoMinu = usu.getCorreo().toLowerCase();
    		if (correoMinu.equals(mailU.toLowerCase())) { // comparar todo el minuscula
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * @param nickElegido
     */
    public DTUsuario getDTUsuario(String nickElegido) throws NoExisteEmpresa, NoExistePostulante {

    	Usuario usr = misUsuarios.get(nickElegido);
    	if (usr == null) {
    		usr = buscarUsuarioPorCorreo(nickElegido);
    	}
    	if (usr == null) { // no existe ese usu
    		throw new NoExisteEmpresa("No existe el usuario solicitado");
    	}else {
	    	if (usr instanceof Postulante) { //if es postulante
	    		Postulante pos = (Postulante) usr;
	    		return pos.getDTUsuario();
	    	}
	        //if es empresa
	    	else {
	    		Empresa emp = (Empresa) usr;
	    		return emp.getDTUsuario();
	    	}
    	}
    }

    /**
     * @return
     */
    public Set<String> obtenerNicknames() {
        return misUsuarios.keySet();
    }


    /**
     * @param nickElegido
     * @return
     */
    public Set<DTOferta> getDTOfertas(String nickElegido) throws NoExisteEmpresa, NoExistePostulante{
        Usuario usr = misUsuarios.get(nickElegido);
        if (usr == null) {
        	throw new NoExistePostulante("No existe el usuario");
        }
        return usr.getDTOfertas();


    }

	public Set<DTOferta> getDTOfertasConfirmadas(String nickElegido) throws NoExisteEmpresa, NoExistePostulante{
		Empresa emp = misEmpresas.get(nickElegido); // se que es una empresa, porque me llamaron desde presentacion por serlo
		if (emp == null) {
			throw new NoExisteEmpresa("No existe la empresa");
		}
		Set<DTOferta> res = emp.getDTOfertasConfirmadas();
		return res;
	}

	public boolean iniciarSesion(String nickMail, String contra) throws NoExistePostulante, NoExisteEmpresa{
		boolean res = false;
		Usuario usr = null;
		if (nickMail.contains("@")){
			if (!existeCorreoUsuario(nickMail)){
				throw new NoExistePostulante("No existe el usuario ingresado");
			}
			// existe el usuario, lo busco a mano en misUsuarios
			usr = buscarUsuarioPorCorreo(nickMail);
		}else {
	        if (!existeNicknameUsuario(nickMail)){
	        	throw new NoExistePostulante("No existe el usuario ingresado");
	        }
	        // existe el usuario
	       usr = misUsuarios.get(nickMail);
		}
		if (usr != null) { // aca siempre lo sera
		   res = usr.compararContrasenias(contra);
		}
		return res;
	}

	public Usuario buscarUsuarioPorCorreo(String mailBuscado) {
	    for (Usuario usuario : misUsuarios.values()) {
	        if (usuario.getCorreo().equals(mailBuscado)) {
	            return usuario; // Encontraste al usuario con el correo buscado
	        }
	    }
	    return null; // No se encontró ningún usuario con el correo buscado
	}

	public DTPostulante getDTPostulante(String nick) throws NoExistePostulante {
		Usuario u = misPostulantes.get(nick);
		DTPostulante dtp = null;
		if (u != null) { // es postulante
			DTUsuario dtu = u.getDTUsuario();
			dtp = (DTPostulante) dtu;
		}else {
			throw new NoExistePostulante("No existe el postulante ingresado");
		}
		return dtp;
	}

	public void seguirUsuario(String miNick, String nickSeguir) throws NoExisteEmpresa, NoExistePostulante {
		Usuario miUsr = misUsuarios.get(miNick);
		Usuario seguirUsr = misUsuarios.get(nickSeguir);
		if (miUsr == null || seguirUsr == null) {
			throw new NoExistePostulante("No existe usuario que interviene");
		}
		miUsr.addSeguido(nickSeguir);
		seguirUsr.addSeguidor(miNick);
	}

	public void dejarDeSeguirUsuario(String miNick, String nickDejar) throws NoExisteEmpresa, NoExistePostulante {
		Usuario miUsr = misUsuarios.get(miNick);
		Usuario dejarUsr = misUsuarios.get(nickDejar);
		if (miUsr == null || dejarUsr == null) {
			throw new NoExistePostulante("No existe usuario que interviene");
		}
		miUsr.removeSeguido(nickDejar);
		dejarUsr.removeSeguidor(miNick);
	}

	public DTEmpresa getDTEmpresa(String nick) throws NoExisteEmpresa {
		Usuario u = misEmpresas.get(nick);
		DTEmpresa dtp = null;
		if (u != null) { // es empresa
			DTUsuario dtu = u.getDTUsuario();
			dtp = (DTEmpresa) dtu;
		}else {
			throw new NoExisteEmpresa("No existe la empresa ingresada");
		}
		return dtp;
	}


	public Set<DTOferta> getDTOfertasConfirmadasNoVigentes(String nickElegido) throws NoExisteEmpresa, NoExistePostulante{
		Empresa emp = misEmpresas.get(nickElegido); // se que es una empresa, porque me llamaron desde presentacion por serlo
		if (emp == null) {
			throw new NoExisteEmpresa("No existe la empresa");
		}
		Set<DTOferta> res = emp.obtenerOfertasConfirmadasNoVigentes();
		return res;
	}


	public Set<DTOferta> getDTOfertasConfirmadasVigentes(String nickElegido) throws NoExisteEmpresa, NoExistePostulante{
		Empresa emp = misEmpresas.get(nickElegido); // se que es una empresa, porque me llamaron desde presentacion por serlo
		if (emp == null) {
			throw new NoExisteEmpresa("No existe la empresa");
		}
		Set<DTOferta> res = emp.obtenerOfertasConfirmadasVigentes();
		return res;
	}
}
