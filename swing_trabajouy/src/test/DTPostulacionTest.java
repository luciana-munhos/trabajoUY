package test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logica.DTOferta;
import logica.DTPostulacion;

public class DTPostulacionTest {

    private static DTPostulacion dtPostulacion;
    private static Date fecha;
    private static String cvr;
    private static String motivacion;

    //primero cargo los datos
    @BeforeAll
    static void setUp() {
        fecha = new Date(System.currentTimeMillis());
        cvr = "CVR Example";
        motivacion = "Motivación for the postulation";
        dtPostulacion = new DTPostulacion(fecha, cvr, motivacion);
    }

    @Test
    void testGetSet() {
    	DTPostulacion pos = new DTPostulacion();
    	assertEquals(pos.getMiOferta(),null);
    	assertEquals(pos.getMotivacion(),"");
    	assertEquals(pos.getCVR(),"");
    	pos.setFecha(fecha);
    	pos.setFechaSelec(fecha);
    	pos.setcvr("cvr");
    	pos.setMot("moti");
    	pos.setVid("");
    	pos.setOferta(null);
    	pos.setOrden(2);
    	assertEquals(pos.getOrden(), 2);
    	assertEquals(pos.getVideo(),"");
    	assertEquals(pos.getFecha(),fecha);
    	assertEquals(pos.getFechaSelec(),fecha);
    	assertEquals(pos.getMiOferta(),null);

    	DTPostulacion pos2 = new DTPostulacion(fecha,"cv","moti",null,2,fecha);
    	assertEquals(pos2.getCVR(),"cv");

    	DTOferta aux = new DTOferta("", "", "", "", "", 10.0, fecha, null, null, "", null, "", "", null);
    	DTOferta aux2 = new DTOferta("", "", "", "", "", 10.0, fecha, null, null, "", null, "", "", null,0);

    	DTPostulacion pos3 = new DTPostulacion(fecha, "", "", aux);
    	assertEquals(pos3.getVideo(),pos2.getVideo());
    }

    //pruebo getters
    @Test
    void testGetFecha() {
        assertEquals(fecha, dtPostulacion.getFecha());
    }

    @Test
    void testGetCVR() {
        assertEquals(cvr, dtPostulacion.getCVR());
    }

    @Test
    void testGetMotivacion() {
        assertEquals(motivacion, dtPostulacion.getMotivacion());
    }
}
