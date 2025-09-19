package test;


import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import excepciones.NombreOfertaExistente;
import logica.CargarDatos;
import logica.ManejadorOferta;
import logica.Oferta;
import logica.TipoPublicacion;

class ManejadorOfertaTest {


	@BeforeAll
    //cargarDatos
    static void testcargarDatos() {
        CargarDatos cd = new CargarDatos();
        cd.carga();
    }

	//agregarOfertaExistente
	@Test
	void testAgregarOfertaExistente() throws NombreOfertaExistente {
		//create oferta
		String titulo = "titulo1";
		String descripcion = "descripcion1";
		String ciudad = "ciudad1";
		String departamento = "departamento1";
		String horario = "horario1";
		double remuneracion = 100;
		//fecha
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	    Date date = null;
		try {
			date = formatter.parse("09-11-2023");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//tipo publicacion


		TipoPublicacion tp = new TipoPublicacion("tipo1","",1,2,4,date);

		//crear oferta
		Oferta oferta = new Oferta(titulo, descripcion, ciudad, departamento, horario, remuneracion, date, tp);
		//agregar oferta
		ManejadorOferta mo = ManejadorOferta.getInstance();
		mo.agregarOferta(oferta);

		//agregar oferta existente. test ok if throw
        assertThrows(NombreOfertaExistente.class, ()->{mo.agregarOferta(oferta);});

	}

	@Test
	void ofeKw() {
		Set<String> kw = new HashSet<>();
		ManejadorOferta mof = ManejadorOferta.getInstance();
		kw = mof.obtenerOfertasKeywords(kw);
		assertTrue(kw.contains("Diseñador UX/UI"));
	}

}
