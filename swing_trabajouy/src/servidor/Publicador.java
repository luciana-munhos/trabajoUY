package servidor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import excepciones.CorreoUsuarioExistente;
import excepciones.ErrorFecha;
import excepciones.NicknameUsuarioExistente;
import excepciones.NoExisteEmpresa;
import excepciones.NoExisteKeyword;
import excepciones.NoExisteOferta;
import excepciones.NoExisteOfertaEmpresa;
import excepciones.NoExistePaquete;
import excepciones.NoExistePostulante;
import excepciones.NoExisteTipoPublicacion;
import excepciones.NoExistenEmpresas;
import excepciones.NoHayTPRestantes;
import excepciones.NombreOfertaExistente;
import excepciones.OfertaExpirada;
import excepciones.PostulantePoseeOferta;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;
import logica.DTCompra;
import logica.DTEmpresa;
import logica.DTOferta;
import logica.DTPaquete;
import logica.DTPostulacion;
import logica.DTPostulante;
import logica.DTTipoPublicacion;
import logica.DTUsuario;
import logica.Fabrica;
import logica.IOferta;
import logica.IUsuario;
import logica.Oferta.Estados;


@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class Publicador {

    private Endpoint endpoint = null;
    Properties prop = null;

    //Constructor
    public Publicador(){
    	prop = new Properties();

        String propFileName = System.getProperty("user.home")+"/conf.properties";
        System.out.println(propFileName);

        try {
        	InputStream inputStream = new FileInputStream(propFileName);
			prop.load(inputStream);
		} catch (IOException e) {
			System.out.println("No se pudo cargar el archivo de configuración.");
		}

    }

    //Operaciones las cuales quiero publicar

    @WebMethod(exclude = true)
    public void publicar(){
         endpoint = Endpoint.publish("http://"+prop.getProperty("host")+":"+prop.getProperty("port")+"/publicador", this);
    }

    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
            return endpoint;
    }

    @WebMethod
    public void darAltaEmpresa(String nick, String nom, String ape, String mail, String contra, String img, byte[] datos, String desc, String link)
    				throws NicknameUsuarioExistente, CorreoUsuarioExistente{
    	IUsuario iUsr = Fabrica.getIUsuario();
		iUsr.darAltaEmpresa(nick, nom, ape, mail, contra, img, datos, desc, link);
    }

    @WebMethod
    public void darAltaPostulante(String nick, String nom, String ape, String mail, String contra, String img,byte[] datos, Date fecha, String pais)
    				throws NicknameUsuarioExistente, CorreoUsuarioExistente{
    	IUsuario iUsr = Fabrica.getIUsuario();
		iUsr.darAltaPostulante(nick, nom, ape, mail, contra, img,datos, fecha, pais);
    }

    @WebMethod
    public DTUsuario mostrarDatosUsuario(String nick) throws NoExisteEmpresa, NoExistePostulante{
		IUsuario iUsr = Fabrica.getIUsuario();
		return iUsr.mostrarDatosUsuario(nick);
    }

    @WebMethod
    public boolean esNickValido(String nick) {
    	IUsuario iUsr = Fabrica.getIUsuario();
    	return iUsr.nickValido(nick);
    }

    @WebMethod
    public void postularAOferta(String nick, String cvr, String mot, String dia, String video, String ofe) throws OfertaExpirada, NoExistePostulante, PostulantePoseeOferta , NoExisteOferta, ErrorFecha{
    	//Falta ver fechas
    	IUsuario iUsr = Fabrica.getIUsuario();
    	Date diafalso = new Date();
    	iUsr.postularAOferta(nick, cvr, mot, diafalso, video, ofe);
    }

    @WebMethod
    public DTPostulacion getDTPostulacion(String nick, String oferta) throws NoExisteOferta {
    	IOferta iOf = Fabrica.getIOferta();
    	return iOf.getDTPostulacion(nick, oferta);
    }

    @WebMethod
    public DTOferta getDTOferta(String oferta) throws NoExisteOferta {
    	IOferta iOf = Fabrica.getIOferta();
    	return iOf.getDTOferta(oferta);
    }
    @WebMethod
    public byte[] getFile(@WebParam(name = "fileName") String name)
                    throws  IOException {
        byte[] byteArray = null;
        try {
                File f = new File(name);
                FileInputStream streamer = new FileInputStream(f);
                byteArray = new byte[streamer.available()];
                streamer.read(byteArray);
        } catch (IOException e) {
                throw e;
        }
        return byteArray;
    }

    @WebMethod
    public String[] getKeywords(){
    	IOferta iOf = Fabrica.getIOferta();
    	Set<String> keys = iOf.getKeywords();
    	String[] res = new String[keys.size()];
    	return keys.toArray(res);
    }

    @WebMethod
    public DTOferta[] getOfertasVigentesOrdenadas(){
    	IOferta iOf = Fabrica.getIOferta();
    	Set<DTOferta> ofertas = iOf.getOfertasVigentesOrdenadas();
    	DTOferta[] res = new DTOferta[ofertas.size()];
    	return ofertas.toArray(res);
    }

    @WebMethod
    public String[] mostrarEmpresas(){
    	IUsuario iUsr = Fabrica.getIUsuario();
    	Set<String> empresas = new HashSet<>();
    	try {
    		empresas = iUsr.mostrarEmpresas();

		} catch (NoExistenEmpresas e) {
		}
    	String[] res = new String[empresas.size()];
		return empresas.toArray(res);
    }

    @WebMethod
    public boolean iniciarSesion(String nickMail,String password) {
    	try {
			return Fabrica.getIUsuario().iniciarSesion(nickMail, password);
		} catch (NoExisteEmpresa | NoExistePostulante e) {
			return false;
		}
    }

    @WebMethod
    public boolean esEmpresa(String nickMail) {
    	try {
			DTUsuario dtu = Fabrica.getIUsuario().mostrarDatosUsuario(nickMail);
			if(dtu instanceof DTEmpresa){
				return true;
			}
			else
				return false;
		} catch (NoExisteEmpresa | NoExistePostulante e) {
			e.printStackTrace();
		}
    	return false;
    }

    @WebMethod
    public DTEmpresa[] mostrarDTEmpresas() throws NoExistenEmpresas, NoExisteEmpresa, NoExistePostulante {
    	Set<DTEmpresa> empresas = Fabrica.getIUsuario().mostrarDTEmpresas();
    	DTEmpresa[] res = new DTEmpresa[empresas.size()];
    	empresas.toArray(res);
    	return res;
    }

    @WebMethod
    public void incrementarCantVisitas(String nom_oferta) throws NoExisteOferta {
    	IOferta iOf = Fabrica.getIOferta();
    	iOf.incrementarVisitas(nom_oferta);
    }

    @WebMethod
    public String[] listarNicknamesUsuarios(){
    	IUsuario iUsr = Fabrica.getIUsuario();
    	Set<String> nicks = iUsr.listarNicknamesUsuarios();
    	String[] res = new String[nicks.size()];
		return nicks.toArray(res);
    }

    @WebMethod
    public DTPostulante getDtPostulante(String nick) throws NoExistePostulante{
    	IUsuario iUsr = Fabrica.getIUsuario();
   		return iUsr.getDTPostulante(nick);
    }

    @WebMethod
    public DTEmpresa getDtEmpresa(String nick) throws NoExisteEmpresa {
    	IUsuario iUsr = Fabrica.getIUsuario();
   		return iUsr.getDTEmpresa(nick);
    }


    @WebMethod
    public DTOferta[] mostrarInfoBasicaOfertas(String nick) throws NoExistePostulante, NoExisteEmpresa {
    	IUsuario iUsr = Fabrica.getIUsuario();
    	Set<DTOferta> ofs = new HashSet<>();
    	ofs = iUsr.mostrarInfoBasicaOfertas(nick);
    	DTOferta[] res = new DTOferta[ofs.size()];
    	ofs.toArray(res);
    	return res;
    }

    @WebMethod
    public DTOferta[] mostrarOfertasConfirmadas(String nick) throws NoExisteEmpresa, NoExistePostulante {
    	IUsuario iUsr = Fabrica.getIUsuario();
    	Set<DTOferta> ofs_confirmadas = new HashSet<>();
		ofs_confirmadas = iUsr.mostrarOfertasConfirmadas(nick);
    	DTOferta[] res = new DTOferta[ofs_confirmadas.size()];
    	ofs_confirmadas.toArray(res);
    	return res;
    }

    @WebMethod
    public DTCompra[] getDTComprasEmpresa(String nick) throws NoExisteEmpresa {
    	IUsuario iUsr = Fabrica.getIUsuario();
    	Set<DTCompra> compras_empresa = new HashSet<>();
    	compras_empresa = iUsr.getDTComprasEmpresa(nick);
    	DTCompra[] res = new DTCompra[compras_empresa.size()];
    	compras_empresa.toArray(res);
    	return res;
    }

    @WebMethod
    public void seguirUsuario(String miNick, String otroNick) throws NoExistePostulante, NoExisteEmpresa {
    	IUsuario iUsr = Fabrica.getIUsuario();
    	iUsr.seguirUsuario(miNick, otroNick);
    }

    @WebMethod
    public void dejarDeSeguirUsuario(String miNick, String otroNick) throws NoExistePostulante, NoExisteEmpresa {
    	IUsuario iUsr = Fabrica.getIUsuario();
    	iUsr.dejarDeSeguirUsuario(miNick, otroNick);
    }


    @WebMethod
    public void ingresarPaqueteAEmpresa(String nomPaquete,String empresa,Date fecha) throws NoExistePaquete, NoExisteEmpresa, ParseException {
    	IUsuario iUsr = Fabrica.getIUsuario();

    	iUsr.ingresarPaqueteAEmpresa(nomPaquete,empresa,fecha);
    }

    @WebMethod
    public String[] listarPaquetesRegistrados(){
    	Set<String> nomPaquetes = Fabrica.getIOferta().listarPaquetesRegistrados();
        String[] res = new String[nomPaquetes.size()];
    	nomPaquetes.toArray(res);
    	return res;
    }

    @WebMethod
    public DTPaquete getInfoPaqueteConImg(String nomPaquete) throws NoExistePaquete {
    	IOferta iOf = Fabrica.getIOferta();
    	return iOf.getInfoPaqueteConImg(nomPaquete);
    }

    @WebMethod
    public DTTipoPublicacion getInfoTipoPublicacion(String nomTipo) throws NoExisteTipoPublicacion {
    	IOferta iOf = Fabrica.getIOferta();
    	return iOf.getInfoTipoPublicacion(nomTipo);
    }


    @WebMethod
    public DTPaquete getInfoPaquete(String nomPaquete) throws NoExistePaquete {
    	IOferta iOf = Fabrica.getIOferta();
    	return iOf.getInfoPaquete(nomPaquete);
    }

    @WebMethod
    public void modificarDatosEmpresa(String nick, String nombre, String apellido, String contra, String img,byte[] datos, String desc, String link) throws NoExisteEmpresa {
    	IUsuario iUsr = Fabrica.getIUsuario();
    	iUsr.modificarDatosEmpresa(nick, nombre, apellido, contra, img, datos, desc, link);
    }

    @WebMethod
    public void modificarDatosPostulante(String nick, String nombre, String apellido, String contra, String img,byte[] datos, String nac, Date f) throws NoExistePostulante {
    	IUsuario iUsr = Fabrica.getIUsuario();
    	iUsr.modificarDatosPostulante(nick, nombre, nac, contra, img, datos, nac, f);
    }

    @WebMethod
	public DTOferta[] mostrarOfertasConfirmadasNoVigentes(String empresa) throws NoExisteEmpresa, NoExistePostulante{
    	IUsuario iUsr = Fabrica.getIUsuario();
    	Set<DTOferta> ofe = iUsr.mostrarOfertasConfirmadasNoVigentes(empresa);
    	DTOferta[] res = new DTOferta[ofe.size()];
     	ofe.toArray(res);
     	return res;
    }

    @WebMethod
  	public DTOferta[] mostrarOfertasConfirmadasVigentes(String empresa) throws NoExisteEmpresa, NoExistePostulante{
      	IUsuario iUsr = Fabrica.getIUsuario();
      	Set<DTOferta> ofe = iUsr.mostrarOfertasConfirmadasVigentes(empresa);
      	DTOferta[] res = new DTOferta[ofe.size()];
       	ofe.toArray(res);
       	return res;
      }

    @WebMethod
    public void finalizarOferta(String oferta,String empresa) throws NoExisteOferta, NoExisteEmpresa {
    	IUsuario iUsr = Fabrica.getIUsuario();
    	iUsr.finalizarOferta(oferta,empresa);
    }

    @WebMethod
    public void guardarDatosOferta(String oferta) throws NoExisteOferta, NoExistePostulante{
    	IUsuario iUsr = Fabrica.getIUsuario();
    	iUsr.guardarDatosOferta(oferta);
    }

    @WebMethod
    public DTOferta[] listarOfertasFinalizadasAsoc(String nick) {
    	IUsuario iUsr = Fabrica.getIUsuario();
    	Set<DTOferta> ofe = iUsr.listarOfertasFinalizadasAsoc(nick);
    	DTOferta[] res = new DTOferta[ofe.size()];
       	ofe.toArray(res);
       	return res;
    }


    @WebMethod
    public String[] getFavoritosUsuario(String nickname) throws NoExistePostulante {
    	IUsuario iUsr = Fabrica.getIUsuario();
    	Set<String> favs = iUsr.getFavoritosPostulante(nickname);
    	String[] res = new String[favs.size()];
    	favs.toArray(res);
    	return res;
    }

    @WebMethod
    public void marcarFavorito(String nickname, String oferta, boolean esFav) throws NoExistePostulante, NoExisteOferta {
    	IUsuario iUsr = Fabrica.getIUsuario();
    	iUsr.agregarFavoritos(nickname, oferta, esFav);
    }

    @WebMethod
    public String[] listarNombresTipos() throws NoExisteTipoPublicacion {
    	IOferta iOf = Fabrica.getIOferta();
    	Set<String> tipos = iOf.listarNombresTipos();
    	String[] res = new String[tipos.size()];
		return tipos.toArray(res);
    }

    @WebMethod
    public void asignarOrdenPostulacion(String nickPost, String nomOferta, int orden, Date fS) throws NoExisteOfertaEmpresa, NoExistePostulante, NoExisteOferta {
    	IOferta iOfr = Fabrica.getIOferta();
    	iOfr.asignarOrdenPostulacion(nickPost, nomOferta, orden,fS);
    }

    @WebMethod
    public void cambiarEstadoOferta(String nombreOferta, Estados estado) throws NoExisteOferta {
    	IOferta iOfr = Fabrica.getIOferta();
    	iOfr.cambiarEstadoOferta(nombreOferta, estado);
    }

    @WebMethod
    public boolean vigenciaOferta(String nomOferta) throws NoExisteOferta {
    	IOferta iOfr = Fabrica.getIOferta();
    	return iOfr.vigenciaOferta(nomOferta);
    }

    @WebMethod
    public String[] mostrarComprasPaquetesEmpresa(String nickEmpresa) throws NoExisteEmpresa {
    	IUsuario iUsr = Fabrica.getIUsuario();
    	Set<String> paquetesComprados = iUsr.mostrarComprasPaquetesEmpresa(nickEmpresa);
    	String[] res = new String[paquetesComprados.size()];
    	return paquetesComprados.toArray(res);

    }

    @WebMethod
    public String[] mostrarOfertasDeEmpresaIngresadas(String nickE) throws NoExisteEmpresa {
    	IUsuario iUsr = Fabrica.getIUsuario();
    	Set<String> ofertasEI = iUsr.mostrarOfertasDeEmpresaIngresadas(nickE);
    	String[] res = new String[ofertasEI.size()];
    	return ofertasEI.toArray(res);
    }

    @WebMethod
    public void ingresarOferta(String empresa, String nombreTP, String nomOferta, String descrip, String horario, double remuneracion, String ciudad, String depart, Date fechaAlta, String[] keywords, String imagen, byte[] datos) throws NombreOfertaExistente, NoExisteEmpresa, NoExisteTipoPublicacion, NoExisteKeyword {
    	IOferta iOfr = Fabrica.getIOferta();
        Set<String> keywordsSet = new HashSet<>(Arrays.asList(keywords));
        iOfr.ingresarOferta(empresa, nombreTP, nomOferta, descrip, horario, remuneracion, ciudad, depart, fechaAlta, keywordsSet, imagen, datos);
    }

    @WebMethod
	public void pagarConPaquete(String nombreOferta,String nombrePaq, String nombreTipo, String empresa) throws NoExistePaquete, NoExisteTipoPublicacion, NoExisteEmpresa, NoExisteOferta, NoHayTPRestantes {
    	IOferta iOfr = Fabrica.getIOferta();
    	iOfr.pagarConPaquete(nombreOferta, nombrePaq, nombreTipo, empresa);
    }

    @WebMethod
    public void modificarOferta(String empresa, String nomOferta, String descrip, String horario, double remuneracion, String ciudad, String depart, String[] keywords, String imagen, byte[] datos) throws NoExisteOferta, NoExisteEmpresa, NoExisteKeyword {
    	IOferta iOfr = Fabrica.getIOferta();
        Set<String> keywordsSet = new HashSet<>(Arrays.asList(keywords)); // Convert String[] to Set
    	iOfr.modificarOferta(empresa, nomOferta, descrip, horario, remuneracion, ciudad, depart, keywordsSet, imagen, datos);
    }

    @WebMethod
    public int getExposicionOferta(String oferta) throws NoExisteOferta {
    	IOferta iOfr = Fabrica.getIOferta();
    	return iOfr.getExposicionOferta(oferta);
    }

    @WebMethod
    public DTOferta getDTOfertaDAO(String oferta) throws NoExisteOferta{
    	IOferta iOfr = Fabrica.getIOferta();
    	return iOfr.getDTOfertaDAO(oferta);
    }

	public DTPostulacion[] listarDTPostulacionesDB(String nick){
		IUsuario iUsr = Fabrica.getIUsuario();
    	Set<DTPostulacion> postulaciones = iUsr.listarDTPostulacionesDB(nick);
    	DTPostulacion[] res = new DTPostulacion[postulaciones.size()];
    	return postulaciones.toArray(res);
	}
}