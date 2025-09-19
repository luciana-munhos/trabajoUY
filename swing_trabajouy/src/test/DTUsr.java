package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import logica.DTUsuario;

public class DTUsr {
	@Test
	void testDTUsuario() {
		Set<String> aux = new HashSet<>();
		DTUsuario usr1 = new DTUsuario();
		DTUsuario usr2 = new DTUsuario("nick","nom","ape","correo@correo.com","contra","");
		assertEquals(usr1.getApellido(),"");
		usr1.setSeguidores(aux);
		usr1.setSeguidos(aux);
		usr1.setContrasenia("asdf");
		usr1.setImagen("");
		usr1.setNickname("nick1");
		usr1.setNombre("nom");
		usr1.setApellido("ape");
		usr1.setCorreo("correo@correo.com");
		usr1.addSeguido("lgarcia");
		usr1.addSeguidor("lgarcia");
		usr1.removeSeguido("lgarcia");
		usr1.removeSeguidor("lgarcia");
		usr1.getSeguidores();
		usr1.getSeguidos();
		assertEquals(usr1.getNombre(),usr2.getNombre());

	}
}
