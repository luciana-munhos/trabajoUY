package com.trabajouy.model;

import java.util.Comparator;

import servidor.DtOferta;
import servidor.NoExisteOferta_Exception;

public class ComparadorExposicionSet implements Comparator<DtOferta> {
	@Override
    public int compare(DtOferta of1, DtOferta of2) {
    	try {
    		servidor.PublicadorService service = new servidor.PublicadorService();
	        servidor.Publicador port = service.getPublicadorPort();

    		String str1 = of1.getNombre();
    		String str2 = of2.getNombre();
			Integer exp1 = port.getExposicionOferta(str1);
			Integer exp2 = port.getExposicionOferta(str2);
			int res = exp1 - exp2;
			if(res==0) {
				return str1.compareTo(str2);
			}
			return res;

		} catch (NoExisteOferta_Exception eve) {
			eve.printStackTrace();
		}
    	return 0;
    }
}
