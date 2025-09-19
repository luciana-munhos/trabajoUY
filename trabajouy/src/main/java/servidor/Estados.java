
package servidor;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para estados.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <pre>{@code
 * <simpleType name="estados">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="ingresada"/>
 *     <enumeration value="confirmada"/>
 *     <enumeration value="rechazada"/>
 *     <enumeration value="finalizada"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "estados")
@XmlEnum
public enum Estados {

    @XmlEnumValue("ingresada")
    INGRESADA("ingresada"),
    @XmlEnumValue("confirmada")
    CONFIRMADA("confirmada"),
    @XmlEnumValue("rechazada")
    RECHAZADA("rechazada"),
    @XmlEnumValue("finalizada")
    FINALIZADA("finalizada");
    private final String value;

    Estados(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Estados fromValue(String v) {
        for (Estados c: Estados.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
