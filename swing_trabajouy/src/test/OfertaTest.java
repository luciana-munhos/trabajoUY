package test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logica.Oferta;

public class OfertaTest {

    private static Oferta oferta;

    //primero cargo los datos
    @BeforeAll
    static void setUp() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 27);
        Date fechaAlta = cal.getTime();

        oferta = new Oferta("Oferta1", "Descripción de Oferta1", "Ciudad1", "Departamento1",
                "Horario completo", 1000.0, fechaAlta);
    }

    //pruebo getters/setters
    @Test
    void testGetNombre() {
        assertEquals("NuevoNombre", oferta.getNombre());
    }

    @Test
    void testGetDescripcion() {
        assertEquals("NuevaDescripción", oferta.getDescripcion());
    }

    @Test
    void testGetCiudad() {
        assertEquals("NuevaCiudad", oferta.getCiudad());
    }

    @Test
    void testGetDepartamento() {
        assertEquals("Departamento1", oferta.getDepartamento());
    }

    @Test
    void testGetHorario() {
        assertEquals("NuevoHorario", oferta.getHorario());
    }

    @Test
    void testGetRemuneracion() {
        assertEquals(1000.0, oferta.getRemuneracion(), 0.0001);
    }

    @Test
    void testGetFechaAlta() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 27);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(oferta.getFechaAlta());

        //check every field
        assertEquals(cal.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
        assertEquals(cal.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    void testSetNombre() {
        oferta.setNombre("NuevoNombre");
        assertEquals("NuevoNombre", oferta.getNombre());
    }

    @Test
    void testSetDescripcion() {
        oferta.setDescripcion("NuevaDescripción");
        assertEquals("NuevaDescripción", oferta.getDescripcion());
    }

    @Test
    void testSetCiudad() {
        oferta.setCiudad("NuevaCiudad");
        assertEquals("NuevaCiudad", oferta.getCiudad());
    }

    @Test
    void testSetDepartamento() {
        oferta.setDepartamento("NuevoDepartamento");
        assertEquals("NuevoDepartamento", oferta.getDepartamento());
    }

    @Test
    void testSetHorario() {
        oferta.setHorario("NuevoHorario");
        assertEquals("NuevoHorario", oferta.getHorario());
    }

    @Test
    void testSetRemuneracion() {
        oferta.setRemuneracion(2000.0);
        assertEquals(2000.0, oferta.getRemuneracion(), 0.0001);
    }

    @Test
    void testSetFechaAlta() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 27);
        Date newFechaAlta = cal.getTime();

        oferta.setFechaAlta(newFechaAlta);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(oferta.getFechaAlta());

        //check every field
        assertEquals(cal.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
        assertEquals(cal.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));
    }
}
