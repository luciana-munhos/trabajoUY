package test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import logica.Compra;

public class CompraTest{
    private Compra c = null;


    @Test
    //Constructor
    public void testCompra(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 27);
        Date fechaCompra = cal.getTime();

        Compra c = new Compra(fechaCompra,0);

        assertEquals(0, c.getVencimiento());

        Date f = c.getFecha();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(f);

        assertEquals(cal.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
        assertEquals(cal.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));

    }

    @Test
    //setFecha
    public void testGetFecha(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 27);
        Date fechaCompra = cal.getTime();

        Compra c = new Compra(fechaCompra,0);

        cal.set(Calendar.YEAR, 2024);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        fechaCompra = cal.getTime();


        c.setFecha(fechaCompra);

        Date f = c.getFecha();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(f);

        assertEquals(cal.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
        assertEquals(cal.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    //setVencimiento
    public void testGetVencimiento(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 27);
        Date fechaCompra = cal.getTime();

        Compra c = new Compra(fechaCompra,0);

        c.setVencimiento(1);

        assertEquals(1, c.getVencimiento());
    }
}
