
package servidor;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtPostulacion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtPostulacion">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         <element name="cVR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="motivacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="miOferta" type="{http://servidor/}dtOferta" minOccurs="0"/>
 *         <element name="video" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="orden" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="fechaSelec" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtPostulacion", propOrder = {
    "fecha",
    "cvr",
    "motivacion",
    "miOferta",
    "video",
    "orden",
    "fechaSelec"
})
public class DtPostulacion {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;
    @XmlElement(name = "cVR")
    protected String cvr;
    protected String motivacion;
    protected DtOferta miOferta;
    protected String video;
    protected int orden;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaSelec;

    /**
     * Obtiene el valor de la propiedad fecha.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Define el valor de la propiedad fecha.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Obtiene el valor de la propiedad cvr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCVR() {
        return cvr;
    }

    /**
     * Define el valor de la propiedad cvr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCVR(String value) {
        this.cvr = value;
    }

    /**
     * Obtiene el valor de la propiedad motivacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivacion() {
        return motivacion;
    }

    /**
     * Define el valor de la propiedad motivacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivacion(String value) {
        this.motivacion = value;
    }

    /**
     * Obtiene el valor de la propiedad miOferta.
     * 
     * @return
     *     possible object is
     *     {@link DtOferta }
     *     
     */
    public DtOferta getMiOferta() {
        return miOferta;
    }

    /**
     * Define el valor de la propiedad miOferta.
     * 
     * @param value
     *     allowed object is
     *     {@link DtOferta }
     *     
     */
    public void setMiOferta(DtOferta value) {
        this.miOferta = value;
    }

    /**
     * Obtiene el valor de la propiedad video.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVideo() {
        return video;
    }

    /**
     * Define el valor de la propiedad video.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVideo(String value) {
        this.video = value;
    }

    /**
     * Obtiene el valor de la propiedad orden.
     * 
     */
    public int getOrden() {
        return orden;
    }

    /**
     * Define el valor de la propiedad orden.
     * 
     */
    public void setOrden(int value) {
        this.orden = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaSelec.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaSelec() {
        return fechaSelec;
    }

    /**
     * Define el valor de la propiedad fechaSelec.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaSelec(XMLGregorianCalendar value) {
        this.fechaSelec = value;
    }

}
