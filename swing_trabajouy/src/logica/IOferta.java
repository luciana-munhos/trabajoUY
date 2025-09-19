package logica;

import java.util.Date;
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
import logica.Oferta.Estados;

public interface IOferta {

    public void altaKeyword(String nombreK) throws NombreKeywordExistente;

	public Set<String> listarPaquetesLibres();

    public Set<String> listarPaquetesRegistrados();

    public DTPaquete getInfoPaquete(String nombreP) throws NoExistePaquete;

    public DTTipoPublicacion getInfoTipoPublicacion(String nombreTP) throws NoExisteTipoPublicacion;

    public void altaTipoDePublicacion(String nombreTP, String descTP, int expTP, int duracionTP, double costoTP, Date fechaAltaTP) throws NombreTipoPublicacionExistente, NombreTipoDePublicacionExistente;

    public Set<String> listarNombresTipos() throws NoExisteTipoPublicacion;

    public void ingresarTipoAPaquete(String nombreP, String nombreTP, int cantTP) throws NoExisteTipoPublicacion, NoExistePaquete;

    public void crearPaquete(String nombre, String descripcion, int validez, double descuento, Date fechaAlta, String img) throws NombrePaqueteExistente;

    public void ingresarOferta(String empresa, String nombreTP, String nomOferta, String descrip, String horario, double remuneracion, String ciudad, String depart, Date fechaAlta, Set<String> keywords, String imagen) throws NombreOfertaExistente, NoExisteEmpresa, NoExisteTipoPublicacion, NoExisteKeyword;
    public void ingresarOferta(String empresa, String nombreTP, String nomOferta, String descrip, String horario, double remuneracion, String ciudad, String depart, Date fechaAlta, Set<String> keywords, String imagen, byte[] datos) throws NombreOfertaExistente, NoExisteEmpresa, NoExisteTipoPublicacion, NoExisteKeyword;


    public Set<String> listarNombresKeywords() throws NoExisteKeyword;

    public Set<String> listarEmpresas() throws NoExistenEmpresas;

    public Set<String> listarOfertasDeEmpresa(String empresa) throws NoExisteEmpresa, NoExisteOfertaEmpresa;

	public DTOferta obtenerOfertaPorNombre(String selectedOferta) throws NoExisteOferta;

	public int getCantPorTP(String pSeleccionado, String tSeleccionado) throws NoExistePaquete;

	public boolean fuePagadaConPaquete(String nomOferta) throws NoExistePaquete, NoExisteOferta;

	public void pagarConPaquete(String nombreOferta,String nombrePaq, String nombreTipo, String empresa) throws NoExistePaquete, NoExisteTipoPublicacion, NoExisteEmpresa, NoExisteOferta, NoHayTPRestantes; //M

    public DTOferta getDTOferta(String nombreOferta) throws NoExisteOferta;

    public Set<String> getKeywords();

    public Map<String, DTOferta> getOfertasVigentes();

    public DTPostulacion getDTPostulacion(String nickname, String nombreOferta) throws NoExisteOferta;

    public int getExposicionOferta(String oferta) throws NoExisteOferta;

	public void comprarPaquete(String nombreP, String empresa) throws NoExisteEmpresa, NoExistePaquete, PaqueteYaComprado;

    public void crearPaqueteConImg(String nombre, String descripcion, int validez, double descuento, Date fechaAlta, String img) throws NombrePaqueteExistente;

    public DTPaquete getInfoPaqueteConImg(String nombreP) throws NoExistePaquete;


    public void cambiarEstadoOferta(String nombreOferta, Estados estado) throws NoExisteOferta;

    public void modificarOferta(String empresa, String nomOferta, String descrip, String horario, double remuneracion, String ciudad, String depart, Set<String> keywords, String imagen) throws NoExisteOferta, NoExisteEmpresa, NoExisteKeyword;
    public void modificarOferta(String empresa, String nomOferta, String descrip, String horario, double remuneracion, String ciudad, String depart, Set<String> keywords, String imagen, byte[] datos) throws NoExisteOferta, NoExisteEmpresa, NoExisteKeyword;


    public TreeSet<DTOferta> getOfertasVigentesOrdenadas();

	public DTOferta obtenerDTUltimaOfertaVigente(String empresa) throws NoExisteEmpresa, NoExisteOfertaEmpresa, NoTieneOfertasConfirmadasVigentes;

	public int getCantVisitas(String nom_oferta) throws NoExisteOferta;

	public void incrementarVisitas(String nom_oferta) throws NoExisteOferta;

	public Set<DTOferta> getTOPofertas() throws NoExisteOferta;

	public void asignarOrdenPostulacion(String nickPost, String nomOferta, int orden, Date fechaSelec) throws NoExisteOfertaEmpresa, NoExistePostulante, NoExisteOferta;

	public boolean vigenciaOferta(String nomOferta) throws NoExisteOferta;

    public DTOferta getDTOfertaDAO(String nombreOferta) throws NoExisteOferta;

}