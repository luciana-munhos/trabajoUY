package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import logica.DTOferta;
import logica.DTPaquete;

public class DTOfertaTest {
	@Test
    void testDTOferta() {
        // Create a sample Set of postulations and packages
        Set<String> postulaciones = new HashSet<>();
        postulaciones.add("User1");
        postulaciones.add("User2");

        DTPaquete paquete = new DTPaquete("Paquete1", "Desc1", 30, 0.1, 500.0, new Date(), new HashSet<>());

        // Create a sample DTOferta instance
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1988);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date fechaAlta = cal.getTime();
        cal.set(Calendar.YEAR, 1988);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 2);
        Date fechaBaja = cal.getTime();
        // Test the getters
        try {
        	Set<String> str = new HashSet();
        	DTOferta aux = new DTOferta("ofe","des","mdeo","mdeo","Horario Completo",1000.0,fechaAlta,fechaBaja, str,"", "","");
        	DTOferta oferta = new DTOferta("Oferta1", "Descripción de oferta", "Ciudad1", "Departamento1",
                    "Horario completo", 1000.0, fechaAlta, postulaciones, paquete);
        	assertEquals("Oferta1", oferta.getNombre());
	            assertEquals("Descripción de oferta", oferta.getDescripcion());
	            assertEquals("Ciudad1", oferta.getCiudad());
	            assertEquals("Departamento1", oferta.getDepartamento());
	            assertEquals("Horario completo", oferta.getHorario());
            assertEquals(1000.0, oferta.getRemuneracion(), 0.0001);
            assertEquals(fechaAlta, oferta.getFechaAlta());
            Set<String> retrievedPostulaciones = oferta.getPostul();
            assertEquals(postulaciones, retrievedPostulaciones);

            assertEquals(paquete, oferta.getDTPaqueteAsociado()); // YO LUSIANA TESTER
            //Set<String> expectedPaquetes = new HashSet<>();
            //expectedPaquetes.add("Paquete1");
            //expectedPaquetes.add("Paquete2");
            //assertEquals(expectedPaquetes, oferta.getPaquetes());
        } catch (Exception e) {
            fail("Excepcion en DTOferta");
        }
    }

	@Test
	void pruebaCrear() {
		Date fecha = new Date();
		Set<String> aux = new HashSet<>();
		DTOferta ofe = new DTOferta("nom", "desc", "ciu", "dep", "08:00", 1000.0, fecha, aux, null, "", aux);
		ofe.setCantFavoritos(3);
		ofe.setFechaAlta(fecha);
		ofe.setCantVisitas(3);
		ofe.setCiudad("ciudad2");
		ofe.setDepartamento("dep2");
		ofe.setDescripcion("des2");
		ofe.setDTPaqueteAsociado(null);
		ofe.setFechaBaja(fecha);
		ofe.setHorario("hora nueva");
		ofe.setImagen("");
		ofe.setKeywords(null);
		ofe.setNickEmpresa("nuevoNick");
		ofe.setNombre("nom2");
		ofe.setPostul(null);
		ofe.setRemuneracion(0.0);
		ofe.setTipoPublicacion(null);
		assertEquals(ofe.getFechaBaja(),fecha);
		assertEquals(ofe.getCantFavoritos(),3);
		assertEquals(ofe.getCiudad(),"ciudad2");

	}


}
