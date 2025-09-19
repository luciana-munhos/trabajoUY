package logica;

import java.util.Comparator;

import excepciones.NoExisteEmpresa;
import excepciones.NoExisteOfertaEmpresa;
import excepciones.NoTieneOfertasConfirmadasVigentes;


public class ComparadorDTEmpresa implements Comparator<DTEmpresa> {
	@Override
    public int compare(DTEmpresa usuario1, DTEmpresa usuario2) {

		String str1 = usuario1.getNickname();
		String str2 = usuario2.getNickname();


		IOferta iOfe = Fabrica.getIOferta();
		DTOferta oferta1 = null;
		DTOferta oferta2 = null;
		try {
			oferta1 = iOfe.obtenerDTUltimaOfertaVigente(str1);
		} catch (NoTieneOfertasConfirmadasVigentes | NoExisteEmpresa | NoExisteOfertaEmpresa e) {

		}
		try {
			oferta2 = iOfe.obtenerDTUltimaOfertaVigente(str2);
		} catch (NoExisteEmpresa | NoExisteOfertaEmpresa | NoTieneOfertasConfirmadasVigentes e) {

		}

		if(oferta1 == null && oferta2 == null)
			return str1.toLowerCase().compareTo(str2.toLowerCase());
		else if(oferta1 == null)
			return 1;
		else if(oferta2 == null)
			return -1;

		if(oferta1.getFechaAlta().after(oferta2.getFechaAlta()))
			return -1;
		else if(oferta1.getFechaAlta().before(oferta2.getFechaAlta()))
			return 1;
		else
			return str1.toLowerCase().compareTo(str2.toLowerCase());

    }
}
