package com.trabajouy.model;

import java.util.Comparator;

import servidor.NoExisteOferta_Exception;


public class ExposicionComparator implements Comparator<String> {
    @Override
    public int compare(String str1, String str2) {
    	servidor.PublicadorService service = new servidor.PublicadorService();
        servidor.Publicador port = service.getPublicadorPort();
    	try {
			Integer exp1 = port.getExposicionOferta(str1);
			Integer exp2 = port.getExposicionOferta(str2);
			int res = exp1 - exp2;
			if(res==0) {
				return str1.compareTo(str2);
			}
			return res;

		} catch (NoExisteOferta_Exception e) {
			e.printStackTrace();
		}
    	return 0;
    }
}


