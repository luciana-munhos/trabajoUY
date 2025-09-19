package logica;

import java.util.Comparator;
import java.util.Date;

import excepciones.NoExisteOferta;

public class ComparadorExposicionSet implements Comparator<DTOferta> {
	@Override
    public int compare(DTOferta oferta1, DTOferta oferta2) {
    	try {
    		IOferta IOf = Fabrica.getIOferta();
    		String str1 = oferta1.getNombre();
    		String str2 = oferta2.getNombre();
			Integer exp1 = IOf.getExposicionOferta(str1);
			Integer exp2 = IOf.getExposicionOferta(str2);
			int res = exp1 - exp2;
			if(res==0) {
				Date fecha1 = oferta1.getFechaAlta();
				Date fecha2 = oferta2.getFechaAlta();
				if(fecha1.after(fecha2))
					return -1;
				else if(fecha2.after(fecha1))
					return 1;
				else
					return str1.compareTo(str2);
			}
			return res;

		} catch (NoExisteOferta exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
    	return 0;
    }
}
