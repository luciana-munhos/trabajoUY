package logica;

import java.util.Date;
import java.util.Map;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTCompra {
	@XmlElement
	private String empresa;
	@XmlElement
	private DTPaquete paquete;
	@XmlElement
	private int vencimiento;
	@XmlElement
	private Date fechaCompra;
	@XmlElement
	private Map<String,Integer> cantActualTP;


	public DTCompra(String emp, DTPaquete paq, int venc, Date fecha) {
		empresa = emp;
		paquete = paq;
		vencimiento = venc;
		fechaCompra = fecha;
	}

	public DTCompra(String emp, DTPaquete paq,int venc, Date fecha, Map<String,Integer> cant) {
		empresa = emp;
		paquete = paq;
		vencimiento = venc;
		fechaCompra = fecha;
		cantActualTP = cant;
	}

	public Map<String,Integer> getCantActualTP() {
		return cantActualTP;
	}

	public String getEmpresa() {
		return empresa;
	}

	public DTPaquete getDTPaquete() {
		return paquete;
	}
	public int getVencimiento() {
		return vencimiento;
	}
	public Date getFechaCompra() {
		return fechaCompra;
	}
}
