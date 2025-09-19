
package servidor;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the servidor package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _CorreoUsuarioExistente_QNAME = new QName("http://servidor/", "CorreoUsuarioExistente");
    private static final QName _ErrorFecha_QNAME = new QName("http://servidor/", "ErrorFecha");
    private static final QName _IOException_QNAME = new QName("http://servidor/", "IOException");
    private static final QName _NicknameUsuarioExistente_QNAME = new QName("http://servidor/", "NicknameUsuarioExistente");
    private static final QName _NoExisteEmpresa_QNAME = new QName("http://servidor/", "NoExisteEmpresa");
    private static final QName _NoExisteKeyword_QNAME = new QName("http://servidor/", "NoExisteKeyword");
    private static final QName _NoExisteOferta_QNAME = new QName("http://servidor/", "NoExisteOferta");
    private static final QName _NoExisteOfertaEmpresa_QNAME = new QName("http://servidor/", "NoExisteOfertaEmpresa");
    private static final QName _NoExistePaquete_QNAME = new QName("http://servidor/", "NoExistePaquete");
    private static final QName _NoExistePostulante_QNAME = new QName("http://servidor/", "NoExistePostulante");
    private static final QName _NoExisteTipoPublicacion_QNAME = new QName("http://servidor/", "NoExisteTipoPublicacion");
    private static final QName _NoExistenEmpresas_QNAME = new QName("http://servidor/", "NoExistenEmpresas");
    private static final QName _NoHayTPRestantes_QNAME = new QName("http://servidor/", "NoHayTPRestantes");
    private static final QName _NombreOfertaExistente_QNAME = new QName("http://servidor/", "NombreOfertaExistente");
    private static final QName _OfertaExpirada_QNAME = new QName("http://servidor/", "OfertaExpirada");
    private static final QName _ParseException_QNAME = new QName("http://servidor/", "ParseException");
    private static final QName _PostulantePoseeOferta_QNAME = new QName("http://servidor/", "PostulantePoseeOferta");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: servidor
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DtCompra }
     * 
     * @return
     *     the new instance of {@link DtCompra }
     */
    public DtCompra createDtCompra() {
        return new DtCompra();
    }

    /**
     * Create an instance of {@link DtCompra.CantActualTP }
     * 
     * @return
     *     the new instance of {@link DtCompra.CantActualTP }
     */
    public DtCompra.CantActualTP createDtCompraCantActualTP() {
        return new DtCompra.CantActualTP();
    }

    /**
     * Create an instance of {@link DtPaquete }
     * 
     * @return
     *     the new instance of {@link DtPaquete }
     */
    public DtPaquete createDtPaquete() {
        return new DtPaquete();
    }

    /**
     * Create an instance of {@link DtPaquete.CantTotales }
     * 
     * @return
     *     the new instance of {@link DtPaquete.CantTotales }
     */
    public DtPaquete.CantTotales createDtPaqueteCantTotales() {
        return new DtPaquete.CantTotales();
    }

    /**
     * Create an instance of {@link CorreoUsuarioExistente }
     * 
     * @return
     *     the new instance of {@link CorreoUsuarioExistente }
     */
    public CorreoUsuarioExistente createCorreoUsuarioExistente() {
        return new CorreoUsuarioExistente();
    }

    /**
     * Create an instance of {@link ErrorFecha }
     * 
     * @return
     *     the new instance of {@link ErrorFecha }
     */
    public ErrorFecha createErrorFecha() {
        return new ErrorFecha();
    }

    /**
     * Create an instance of {@link IOException }
     * 
     * @return
     *     the new instance of {@link IOException }
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link NicknameUsuarioExistente }
     * 
     * @return
     *     the new instance of {@link NicknameUsuarioExistente }
     */
    public NicknameUsuarioExistente createNicknameUsuarioExistente() {
        return new NicknameUsuarioExistente();
    }

    /**
     * Create an instance of {@link NoExisteEmpresa }
     * 
     * @return
     *     the new instance of {@link NoExisteEmpresa }
     */
    public NoExisteEmpresa createNoExisteEmpresa() {
        return new NoExisteEmpresa();
    }

    /**
     * Create an instance of {@link NoExisteKeyword }
     * 
     * @return
     *     the new instance of {@link NoExisteKeyword }
     */
    public NoExisteKeyword createNoExisteKeyword() {
        return new NoExisteKeyword();
    }

    /**
     * Create an instance of {@link NoExisteOferta }
     * 
     * @return
     *     the new instance of {@link NoExisteOferta }
     */
    public NoExisteOferta createNoExisteOferta() {
        return new NoExisteOferta();
    }

    /**
     * Create an instance of {@link NoExisteOfertaEmpresa }
     * 
     * @return
     *     the new instance of {@link NoExisteOfertaEmpresa }
     */
    public NoExisteOfertaEmpresa createNoExisteOfertaEmpresa() {
        return new NoExisteOfertaEmpresa();
    }

    /**
     * Create an instance of {@link NoExistePaquete }
     * 
     * @return
     *     the new instance of {@link NoExistePaquete }
     */
    public NoExistePaquete createNoExistePaquete() {
        return new NoExistePaquete();
    }

    /**
     * Create an instance of {@link NoExistePostulante }
     * 
     * @return
     *     the new instance of {@link NoExistePostulante }
     */
    public NoExistePostulante createNoExistePostulante() {
        return new NoExistePostulante();
    }

    /**
     * Create an instance of {@link NoExisteTipoPublicacion }
     * 
     * @return
     *     the new instance of {@link NoExisteTipoPublicacion }
     */
    public NoExisteTipoPublicacion createNoExisteTipoPublicacion() {
        return new NoExisteTipoPublicacion();
    }

    /**
     * Create an instance of {@link NoExistenEmpresas }
     * 
     * @return
     *     the new instance of {@link NoExistenEmpresas }
     */
    public NoExistenEmpresas createNoExistenEmpresas() {
        return new NoExistenEmpresas();
    }

    /**
     * Create an instance of {@link NoHayTPRestantes }
     * 
     * @return
     *     the new instance of {@link NoHayTPRestantes }
     */
    public NoHayTPRestantes createNoHayTPRestantes() {
        return new NoHayTPRestantes();
    }

    /**
     * Create an instance of {@link NombreOfertaExistente }
     * 
     * @return
     *     the new instance of {@link NombreOfertaExistente }
     */
    public NombreOfertaExistente createNombreOfertaExistente() {
        return new NombreOfertaExistente();
    }

    /**
     * Create an instance of {@link OfertaExpirada }
     * 
     * @return
     *     the new instance of {@link OfertaExpirada }
     */
    public OfertaExpirada createOfertaExpirada() {
        return new OfertaExpirada();
    }

    /**
     * Create an instance of {@link ParseException }
     * 
     * @return
     *     the new instance of {@link ParseException }
     */
    public ParseException createParseException() {
        return new ParseException();
    }

    /**
     * Create an instance of {@link PostulantePoseeOferta }
     * 
     * @return
     *     the new instance of {@link PostulantePoseeOferta }
     */
    public PostulantePoseeOferta createPostulantePoseeOferta() {
        return new PostulantePoseeOferta();
    }

    /**
     * Create an instance of {@link DtOferta }
     * 
     * @return
     *     the new instance of {@link DtOferta }
     */
    public DtOferta createDtOferta() {
        return new DtOferta();
    }

    /**
     * Create an instance of {@link DtUsuario }
     * 
     * @return
     *     the new instance of {@link DtUsuario }
     */
    public DtUsuario createDtUsuario() {
        return new DtUsuario();
    }

    /**
     * Create an instance of {@link DtTipoPublicacion }
     * 
     * @return
     *     the new instance of {@link DtTipoPublicacion }
     */
    public DtTipoPublicacion createDtTipoPublicacion() {
        return new DtTipoPublicacion();
    }

    /**
     * Create an instance of {@link DtPostulacion }
     * 
     * @return
     *     the new instance of {@link DtPostulacion }
     */
    public DtPostulacion createDtPostulacion() {
        return new DtPostulacion();
    }

    /**
     * Create an instance of {@link DtPostulante }
     * 
     * @return
     *     the new instance of {@link DtPostulante }
     */
    public DtPostulante createDtPostulante() {
        return new DtPostulante();
    }

    /**
     * Create an instance of {@link DtEmpresa }
     * 
     * @return
     *     the new instance of {@link DtEmpresa }
     */
    public DtEmpresa createDtEmpresa() {
        return new DtEmpresa();
    }

    /**
     * Create an instance of {@link DtOfertaArray }
     * 
     * @return
     *     the new instance of {@link DtOfertaArray }
     */
    public DtOfertaArray createDtOfertaArray() {
        return new DtOfertaArray();
    }

    /**
     * Create an instance of {@link DtPostulacionArray }
     * 
     * @return
     *     the new instance of {@link DtPostulacionArray }
     */
    public DtPostulacionArray createDtPostulacionArray() {
        return new DtPostulacionArray();
    }

    /**
     * Create an instance of {@link DtEmpresaArray }
     * 
     * @return
     *     the new instance of {@link DtEmpresaArray }
     */
    public DtEmpresaArray createDtEmpresaArray() {
        return new DtEmpresaArray();
    }

    /**
     * Create an instance of {@link DtCompraArray }
     * 
     * @return
     *     the new instance of {@link DtCompraArray }
     */
    public DtCompraArray createDtCompraArray() {
        return new DtCompraArray();
    }

    /**
     * Create an instance of {@link StringArray }
     * 
     * @return
     *     the new instance of {@link StringArray }
     */
    public StringArray createStringArray() {
        return new StringArray();
    }

    /**
     * Create an instance of {@link DtCompra.CantActualTP.Entry }
     * 
     * @return
     *     the new instance of {@link DtCompra.CantActualTP.Entry }
     */
    public DtCompra.CantActualTP.Entry createDtCompraCantActualTPEntry() {
        return new DtCompra.CantActualTP.Entry();
    }

    /**
     * Create an instance of {@link DtPaquete.CantTotales.Entry }
     * 
     * @return
     *     the new instance of {@link DtPaquete.CantTotales.Entry }
     */
    public DtPaquete.CantTotales.Entry createDtPaqueteCantTotalesEntry() {
        return new DtPaquete.CantTotales.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CorreoUsuarioExistente }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CorreoUsuarioExistente }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "CorreoUsuarioExistente")
    public JAXBElement<CorreoUsuarioExistente> createCorreoUsuarioExistente(CorreoUsuarioExistente value) {
        return new JAXBElement<>(_CorreoUsuarioExistente_QNAME, CorreoUsuarioExistente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorFecha }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ErrorFecha }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "ErrorFecha")
    public JAXBElement<ErrorFecha> createErrorFecha(ErrorFecha value) {
        return new JAXBElement<>(_ErrorFecha_QNAME, ErrorFecha.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "IOException")
    public JAXBElement<IOException> createIOException(IOException value) {
        return new JAXBElement<>(_IOException_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NicknameUsuarioExistente }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NicknameUsuarioExistente }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "NicknameUsuarioExistente")
    public JAXBElement<NicknameUsuarioExistente> createNicknameUsuarioExistente(NicknameUsuarioExistente value) {
        return new JAXBElement<>(_NicknameUsuarioExistente_QNAME, NicknameUsuarioExistente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoExisteEmpresa }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NoExisteEmpresa }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "NoExisteEmpresa")
    public JAXBElement<NoExisteEmpresa> createNoExisteEmpresa(NoExisteEmpresa value) {
        return new JAXBElement<>(_NoExisteEmpresa_QNAME, NoExisteEmpresa.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoExisteKeyword }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NoExisteKeyword }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "NoExisteKeyword")
    public JAXBElement<NoExisteKeyword> createNoExisteKeyword(NoExisteKeyword value) {
        return new JAXBElement<>(_NoExisteKeyword_QNAME, NoExisteKeyword.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoExisteOferta }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NoExisteOferta }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "NoExisteOferta")
    public JAXBElement<NoExisteOferta> createNoExisteOferta(NoExisteOferta value) {
        return new JAXBElement<>(_NoExisteOferta_QNAME, NoExisteOferta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoExisteOfertaEmpresa }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NoExisteOfertaEmpresa }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "NoExisteOfertaEmpresa")
    public JAXBElement<NoExisteOfertaEmpresa> createNoExisteOfertaEmpresa(NoExisteOfertaEmpresa value) {
        return new JAXBElement<>(_NoExisteOfertaEmpresa_QNAME, NoExisteOfertaEmpresa.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoExistePaquete }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NoExistePaquete }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "NoExistePaquete")
    public JAXBElement<NoExistePaquete> createNoExistePaquete(NoExistePaquete value) {
        return new JAXBElement<>(_NoExistePaquete_QNAME, NoExistePaquete.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoExistePostulante }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NoExistePostulante }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "NoExistePostulante")
    public JAXBElement<NoExistePostulante> createNoExistePostulante(NoExistePostulante value) {
        return new JAXBElement<>(_NoExistePostulante_QNAME, NoExistePostulante.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoExisteTipoPublicacion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NoExisteTipoPublicacion }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "NoExisteTipoPublicacion")
    public JAXBElement<NoExisteTipoPublicacion> createNoExisteTipoPublicacion(NoExisteTipoPublicacion value) {
        return new JAXBElement<>(_NoExisteTipoPublicacion_QNAME, NoExisteTipoPublicacion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoExistenEmpresas }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NoExistenEmpresas }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "NoExistenEmpresas")
    public JAXBElement<NoExistenEmpresas> createNoExistenEmpresas(NoExistenEmpresas value) {
        return new JAXBElement<>(_NoExistenEmpresas_QNAME, NoExistenEmpresas.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoHayTPRestantes }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NoHayTPRestantes }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "NoHayTPRestantes")
    public JAXBElement<NoHayTPRestantes> createNoHayTPRestantes(NoHayTPRestantes value) {
        return new JAXBElement<>(_NoHayTPRestantes_QNAME, NoHayTPRestantes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NombreOfertaExistente }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NombreOfertaExistente }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "NombreOfertaExistente")
    public JAXBElement<NombreOfertaExistente> createNombreOfertaExistente(NombreOfertaExistente value) {
        return new JAXBElement<>(_NombreOfertaExistente_QNAME, NombreOfertaExistente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OfertaExpirada }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OfertaExpirada }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "OfertaExpirada")
    public JAXBElement<OfertaExpirada> createOfertaExpirada(OfertaExpirada value) {
        return new JAXBElement<>(_OfertaExpirada_QNAME, OfertaExpirada.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParseException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ParseException }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "ParseException")
    public JAXBElement<ParseException> createParseException(ParseException value) {
        return new JAXBElement<>(_ParseException_QNAME, ParseException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PostulantePoseeOferta }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PostulantePoseeOferta }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "PostulantePoseeOferta")
    public JAXBElement<PostulantePoseeOferta> createPostulantePoseeOferta(PostulantePoseeOferta value) {
        return new JAXBElement<>(_PostulantePoseeOferta_QNAME, PostulantePoseeOferta.class, null, value);
    }

}
