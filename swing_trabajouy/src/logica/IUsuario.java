package logica;

import java.util.Date;
import java.util.Set;

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

/**
 *
 */
public interface IUsuario {

	public boolean nickValido(String nick);

    public Set<String> mostrarOfertasVigentesEmpresa(String empresa) throws NoExisteEmpresa, NoExisteOfertaEmpresa, NoTieneOfertasConfirmadasVigentes;

    public Set<String> mostrarEmpresas() throws NoExistenEmpresas;

    public DTOferta mostrarInfoDetalladaOferta(String nombreOferta) throws NoExisteOferta;

    public Set<String> mostrarPostulantes() throws NoExistenUsuarios;

    public void postularAOferta(String postulante, String cvRed, String motivacion, Date fecha, String video,String oferta) throws OfertaExpirada, NoExistePostulante, PostulantePoseeOferta , NoExisteOferta, ErrorFecha;
    public void postularAOferta(String postulante, String cvRed, String motivacion, Date fecha, String oferta) throws OfertaExpirada, NoExistePostulante, PostulantePoseeOferta , NoExisteOferta, ErrorFecha;

    public Set<String> listarNicknamesUsuarios();

    public Set<String> mostrarComprasPaquetesEmpresa(String nickElegido) throws NoExisteEmpresa;

    // para consulta de usuario de empresa
    public Set<DTOferta> mostrarOfertasConfirmadas(String nickElegido) throws NoExisteEmpresa, NoExistePostulante;

    public boolean iniciarSesion(String nickMail, String contra) throws NoExisteEmpresa, NoExistePostulante;

    public DTUsuario mostrarDatosUsuario(String nickElegido) throws NoExisteEmpresa, NoExistePostulante;

    public Set<DTOferta> mostrarInfoBasicaOfertas(String nickElegido) throws NoExistePostulante, NoExisteEmpresa;

    public Set<String> mostrarOfertasDeEmpresaIngresadas(String nickE) throws NoExisteEmpresa;

    public void modificarDatosPostulante(String nickElegido, String nombreU, String surnameU, String contra, String ima, String nac, Date fNac) throws NoExistePostulante;
    public void modificarDatosPostulante(String nickElegido, String nombreU, String surnameU, String contra, String ima, byte[] datos, String nac, Date fNac) throws NoExistePostulante;

    public void modificarDatosEmpresa(String nickElegido, String nombreU, String surnameU, String contra, String ima, String descE, String linkE) throws NoExisteEmpresa;
    public void modificarDatosEmpresa(String nickElegido, String nombreU, String surnameU, String contra, String ima, byte[] datos, String descE, String linkE) throws NoExisteEmpresa;

    public void darAltaPostulante(String nickU, String nomU, String surnameU, String mailU, String contra, String imagen, Date fechaNacU, String nacionU) throws NicknameUsuarioExistente, CorreoUsuarioExistente;
    public void darAltaPostulante(String nick, String nom, String ape, String mail, String contra, String img, byte[] datos, Date fecha, String pais) throws NicknameUsuarioExistente,CorreoUsuarioExistente;

    public void aceptarOferta(String nomO) throws NoExisteOferta;

    public void darAltaEmpresa(String nickU, String nomU, String surnameU, String mailU, String contra, String imagen, String descE, String linkE) throws NicknameUsuarioExistente, CorreoUsuarioExistente;
    public void darAltaEmpresa(String nickU, String nomU, String surnameU, String mailU, String contra, String imagen, byte[] datos, String descE, String linkE) throws NicknameUsuarioExistente, CorreoUsuarioExistente;

    public void rechazarOferta(String nomO) throws NoExisteOferta;

    public Set<String> mostrarOfertasVigentesConfirmadasEK(String empresa, Set<String> keyword) throws NoExisteEmpresa, NoTieneOfertasConfirmadasVigentes;

    public Set<String> mostrarOfertasPostulante(String postulante) throws NoExistePostulante;

    public DTPostulacion mostrarInfoPostulacion(String pos, String ofe) throws NoExistePostulante;

    public Set<String> listarPaquetesAdquiridosVigentesTipo(String nombreTP, String empresa) throws NoExisteEmpresa;

    public Set<String> mostrarPostulantes(String ofe) throws NoExisteOferta;

    public Set<DTCompra> getDTComprasEmpresa(String nick) throws NoExisteEmpresa;

    public void ingresarPaqueteAEmpresa(String nomPaquete, String empresa, Date fecha) throws NoExistePaquete, NoExisteEmpresa;

    public Set<String> getPaquetesSinComprar(String empresa) throws NoExisteEmpresa;

	public Set<DTEmpresa> mostrarDTEmpresas() throws NoExistenEmpresas, NoExisteEmpresa, NoExistePostulante;

	public DTPostulante getDTPostulante(String nick) throws NoExistePostulante;

	public void seguirUsuario(String miNick, String nickSeguir) throws NoExistePostulante, NoExisteEmpresa;

	public void dejarDeSeguirUsuario(String miNick, String nickDejar) throws NoExistePostulante, NoExisteEmpresa;

	public Set<DTOferta> mostrarOfertasConfirmadasNoVigentes(String empresa) throws NoExisteEmpresa, NoExistePostulante;
	public Set<DTOferta> mostrarOfertasConfirmadasVigentes(String empresa) throws NoExisteEmpresa, NoExistePostulante;

	public void finalizarOferta(String oferta, String empresa) throws NoExisteOferta, NoExisteEmpresa;

	public void guardarDatosOferta(String oferta) throws NoExisteOferta, NoExistePostulante;
	public DTEmpresa getDTEmpresa(String nick) throws NoExisteEmpresa;
	public Set<DTOferta> listarOfertasFinalizadasAsoc(String miNick);
	public Set<String> getFavoritosPostulante(String nickname) throws NoExistePostulante;

	void agregarFavoritos(String nickname, String oferta, boolean esFav) throws NoExistePostulante, NoExisteOferta;

	public Set<DTPostulacion> listarDTPostulacionesDB(String nick);

}
