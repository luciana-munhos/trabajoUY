package excepciones;

public class NoExistenUsuarios extends Exception{
	public NoExistenUsuarios(String mensaje) {
		super(mensaje);
	}
}
