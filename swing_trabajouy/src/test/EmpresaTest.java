package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import logica.Empresa;

class EmpresaTest {

	@Test
	void testSetters() {
		String nickname = "nick_empresa";
		String nombre = "nombre_empresa";
		String apellido = "apellido_empresa";
		String correo = "correo_empresa";
		String contrasenia = "contrasenia_empresa";
		String imagen = "imagen_empresa";
		String descripcion = "descripcion_empresa";
		String link = "link_empresa";
		Empresa e = new Empresa(nickname, nombre, apellido, correo, contrasenia, imagen, descripcion, link);

		//set nickname, correo, link
		String nickname2 = "nick_empresa2";
		String correo2 = "correo_empresa2";
		String link2 = "link_empresa2";
		e.setNickname(nickname2);
		e.setCorreo(correo2);
		e.setLink(link2);

		assertEquals(nickname2, e.getNickname());
		assertEquals(correo2, e.getCorreo());
		assertEquals(link2, e.getLink());
	}



}
