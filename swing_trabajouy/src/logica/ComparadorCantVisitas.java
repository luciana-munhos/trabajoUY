package logica;

import java.util.Comparator;


public class ComparadorCantVisitas implements Comparator<DTOferta> {
	@Override
    public int compare(DTOferta of1, DTOferta of2) {

		int res = 0;
		res = of2.getCantVisitas() - of1.getCantVisitas();

		if(res == 0)
			res = of1.getNombre().toLowerCase().compareTo(of2.getNombre().toLowerCase());

		return res;
    }
}
