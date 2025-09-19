package test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import excepciones.CorreoUsuarioExistente;
import excepciones.NicknameUsuarioExistente;
import excepciones.NoExistePostulante;
import logica.Fabrica;
import logica.IUsuario;
import logica.ManejadorUsuario;
import logica.Oferta;
import logica.Postulacion;
import logica.Postulante;

public class PostulacionTest {

    private static Postulacion postulacion;
    private static Postulante postulante;
    private static Oferta oferta;
    private static Date fecha;

    //primero cargo los datos
    @BeforeAll
    static void setUp() {
        postulante = new Postulante("User123", "Nombre Postulante", "Apellido Postulante", "user@example.com",
                null, null, new Date(), "Nacionalidad");
        fecha = new Date();
        oferta = new Oferta("Oferta1", "Descripción de oferta", "Ciudad1", "Departamento1", "Horario completo", 1000.0, fecha);
        postulacion = new Postulacion(postulante, "CVR Example", "Motivación para la postulación", fecha);
        postulacion.addOferta(oferta);
    }

    //pruebo getters
    @Test
    void testGetFecha() {
        assertEquals(fecha, postulacion.getFecha());
    }

    @Test
    void testGetCVR() {
        assertEquals("CVR Example", postulacion.getCVR());
    }

    @Test
    void testGetMotivacion() {
        assertEquals("Motivación para la postulación", postulacion.getMotivacion());
    }

    @Test
    void testGetMiOferta() {
        assertEquals(oferta, postulacion.getMiOferta());
    }

    @Test
    void testGetNicknamePostulante() {
        assertEquals("User123", postulacion.getNicknamePostulante());
    }

    @Test
    void testgetSet() {
    	IUsuario contUsuario = Fabrica.getIUsuario();
        Set<String> nicknames = contUsuario.listarNicknamesUsuarios();

    	String nickU = "joel";
        String nomU = "Joel";
        String surnameU = "Aguirre";
        String mailU = "joelcabj@hotmail.com.uy";
        Calendar cal2 = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2002);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date fechaNacU = cal.getTime();
        String nacionU = "Uruguay";
        String contra = "ajoel123";
        String imagen = "Usuarios/joela.jpg";
        try {
            contUsuario.darAltaPostulante(nickU, nomU, surnameU, mailU,contra, imagen, fechaNacU, nacionU);
        }
        catch(NicknameUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }
        catch(CorreoUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }
        // siento q falta llamar a esa funcion de nuevo  !!!!!!!!!!!!!!!!
        assertEquals(true, nicknames.contains("ajoel"));

        cal.set(Calendar.YEAR, 2023);
	    cal.set(Calendar.MONTH, Calendar.NOVEMBER);
	    cal.set(Calendar.DAY_OF_MONTH, 9);
	    fechaNacU = cal.getTime();

	    cal2.set(Calendar.YEAR, 2023);
	    cal2.set(Calendar.MONTH, Calendar.NOVEMBER);
	    cal2.set(Calendar.DAY_OF_MONTH, 10);
	    Date fecha = cal2.getTime();
	    ManejadorUsuario musr = ManejadorUsuario.getInstance();
	    Postulante pos = null;
		try {
			pos = musr.obtenerInstanciaP(nickU);
		} catch (NoExistePostulante e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Postulacion aux = new Postulacion (pos, "holahola", "motiva", fechaNacU, "", 2, fecha);
        aux.setVideo("");
        aux.setFecha(fechaNacU);
        aux.setCVR("nuevoCV");
        aux.setMotivacion("nuevaMotivacion");
        assertEquals(aux.getOrden(),2);
        assertEquals(aux.getFechaSelec(),fecha);
        assertEquals(aux.getCVR(),"nuevoCV");
    }
}


