package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logica.CantidadPorTipo;
import logica.DTTipoPublicacion;
import logica.Paquete;
import logica.TipoPublicacion;

public class CantidadPorTipoTest {

    private static CantidadPorTipo cantidadPorTipo;
    private static TipoPublicacion tipoPublicacion;

    //primero cargo los datos
    @BeforeAll
    static void setUp() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 27);
        Date fechaAlta = cal.getTime();

        tipoPublicacion = new TipoPublicacion("Tipo1", "Descripción de Tipo1", 3, 7, 150.0, fechaAlta);
        cantidadPorTipo = new CantidadPorTipo(5, tipoPublicacion, null); // Adjust as needed
    }

    //pruebo getters/setters
    @Test
    public void testGetNombre() {
        assertEquals("Tipo1", tipoPublicacion.getNombre());
    }

    @Test
    public void testGetDescripcion() {
        assertEquals("Descripción de Tipo1", tipoPublicacion.getDescripcion());
    }

    @Test
    public void testGetExposicion() {
        assertEquals(3, tipoPublicacion.getExposicion());
    }

    @Test
    public void testGetDuracion() {
        assertEquals(7, tipoPublicacion.getDuracion());
    }

    @Test
    public void testGetCosto() {
        assertEquals(150.0, tipoPublicacion.getCosto(), 0.0001);
    }

    @Test
    public void testGetFechaAlta() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 27);
        Date cal2 = tipoPublicacion.getFechaAlta();
        //get calendar instance cal2
        Calendar tipoPublicacionCal = Calendar.getInstance();
        tipoPublicacionCal.setTime(cal2);

        //check one by one
        assertEquals(cal.get(Calendar.YEAR), tipoPublicacionCal.get(Calendar.YEAR));
        assertEquals(cal.get(Calendar.MONTH), tipoPublicacionCal.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), tipoPublicacionCal.get(Calendar.DAY_OF_MONTH));

    }

    @Test
    public void testGetInfo() {
        try {
            DTTipoPublicacion dtTipo = tipoPublicacion.getInfo();
            assertEquals("Tipo1", dtTipo.getNombre());
            assertEquals("Descripción de Tipo1", dtTipo.getDescripcion());
            assertEquals(3, dtTipo.getExposicion());
            assertEquals(7, dtTipo.getDuracion());
            assertEquals(150.0, dtTipo.getCosto(), 0.0001);
            //check date
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(tipoPublicacion.getFechaAlta());
            cal2.setTime(dtTipo.getFechaAlta());
            assertEquals(cal1.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
            assertEquals(cal1.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
            assertEquals(cal1.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));

        } catch (Exception e) {
            fail("Exception in TipoPublicacion");
        }
    }


    @Test
    public void testSetCantidad() {
        cantidadPorTipo.setCantidad(10);
        assertEquals(10, cantidadPorTipo.getCantidad());
    }

    @Test
    public void testSetTipoPublicacion() {
        TipoPublicacion newTipo = new TipoPublicacion("NuevoTipo", "Descripción del Nuevo Tipo", 2, 5, 100.0, new Date());
        cantidadPorTipo.setTipoPublicacion(newTipo);
        assertEquals(newTipo, cantidadPorTipo.getTipoPublicacion());
        //check everything
        assertEquals("NuevoTipo", cantidadPorTipo.getTipoPublicacion().getNombre());
        assertEquals("Descripción del Nuevo Tipo", cantidadPorTipo.getTipoPublicacion().getDescripcion());
        assertEquals(2, cantidadPorTipo.getTipoPublicacion().getExposicion());
        assertEquals(5, cantidadPorTipo.getTipoPublicacion().getDuracion());
        assertEquals(100.0, cantidadPorTipo.getTipoPublicacion().getCosto(), 0.0001);
        //check date
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(newTipo.getFechaAlta());
        cal2.setTime(cantidadPorTipo.getTipoPublicacion().getFechaAlta());
        assertEquals(cal1.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
        assertEquals(cal1.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
        assertEquals(cal1.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));


    }

    @Test
    public void testSetPaquete() {
    	String img = "Paquetes/Paq1.jpg"; // link inventado de imagen de paquete
        Paquete newPaquete = new Paquete("NuevoPaquete", "Descripción del Nuevo Paquete", 30, 0.1, new Date(),img);
        cantidadPorTipo.setPaquete(newPaquete);
        //check everything
        assertEquals("NuevoPaquete", cantidadPorTipo.getPaquete().getNombre());
        assertEquals("Descripción del Nuevo Paquete", cantidadPorTipo.getPaquete().getDescripcion());
        assertEquals(0.1, cantidadPorTipo.getPaquete().getDescuento());
        assertEquals(30, cantidadPorTipo.getPaquete().getValidez());
        assertEquals(img,cantidadPorTipo.getPaquete().getImagen());
        //check date
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(newPaquete.getFechaAlta());
        cal2.setTime(cantidadPorTipo.getPaquete().getFechaAlta());
        assertEquals(cal1.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
        assertEquals(cal1.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
        assertEquals(cal1.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));
    }
}
