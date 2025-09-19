package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import logica.DTCompra;
import logica.DTPaquete;

class DTCompraTest {

	//constructor
	@Test
	public void test() throws ParseException {
		DTPaquete paq;
		String nombre = "paquete1";
		String descripcion = "descripcion1";
		int validez = 1;
		double descuento = 50;
		double costo = 100;
		Date fechaAlta = new Date();
		Set<String> tiposPub = new HashSet<>();
		String imagen = "imagen1";

		//create tipos pub
		tiposPub.add("tipo1");
		tiposPub.add("tipo2");
		tiposPub.add("tipo3");

		paq = new DTPaquete(nombre, descripcion, validez, descuento, costo, fechaAlta, tiposPub, imagen);

		//create dtcompra
		String empresa = "empresa1";
		int vencimiento = 1;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	    Date date = formatter.parse("04-10-2023");

		DTCompra dtc = new DTCompra(empresa, paq, vencimiento, date);

		//test
		assertEquals(dtc.getEmpresa(), empresa);
		//check one by one dtpaquete
		assertEquals(dtc.getDTPaquete().getNombre(), nombre);
		assertEquals(dtc.getDTPaquete().getDescripcion(), descripcion);
		assertEquals(dtc.getDTPaquete().getValidez(), validez);
		assertEquals(dtc.getDTPaquete().getDescuento(), descuento);
		assertEquals(dtc.getDTPaquete().getCosto(), costo);
		assertEquals(dtc.getDTPaquete().getFechaAlta(), fechaAlta);
		assertEquals(dtc.getDTPaquete().getImagen(), imagen);
		//check tipos pub one by one
		Set<String> tiposPub2 = new HashSet<>();
		tiposPub2 = dtc.getDTPaquete().getTiposPub();
		for(String s : tiposPub2) {
			assertTrue(tiposPub.contains(s));
		}
		assertEquals(dtc.getVencimiento(), vencimiento);
		assertEquals(dtc.getFechaCompra(), date);

	}

}
