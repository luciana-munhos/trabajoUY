package logica;

import java.util.Comparator;

import excepciones.NoExisteOferta;

public class ExposicionComparator implements Comparator<String> {
    @Override
    public int compare(String str1, String str2) {
    	IOferta IO = Fabrica.getIOferta();
    	try {
			Integer exp1 = IO.getExposicionOferta(str1);
			Integer exp2 = IO.getExposicionOferta(str2);
			int res = exp1 - exp2;
			if(res==0) {
				return str1.compareTo(str2);
			}
			return res;

		} catch (NoExisteOferta e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return 0;
    }
}


