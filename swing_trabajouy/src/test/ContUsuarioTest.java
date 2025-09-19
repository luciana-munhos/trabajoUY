package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import excepciones.CorreoUsuarioExistente;
import excepciones.ErrorFecha;
import excepciones.NicknameUsuarioExistente;
import excepciones.NoExisteEmpresa;
import excepciones.NoExisteOferta;
import excepciones.NoExisteOfertaEmpresa;
import excepciones.NoExistePostulante;
import excepciones.NoExistenEmpresas;
import excepciones.NoTieneOfertasConfirmadasVigentes;
import excepciones.OfertaExpirada;
import excepciones.PostulantePoseeOferta;
import logica.CargarDatos;
import logica.DTCompra;
import logica.DTEmpresa;
import logica.DTOferta;
import logica.DTPaquete;
import logica.DTPostulacion;
import logica.DTPostulante;
import logica.DTUsuario;
import logica.Empresa;
import logica.Fabrica;
import logica.IOferta;
import logica.IUsuario;
import logica.ManejadorUsuario;
import logica.Oferta.Estados;
import logica.Usuario;

public class ContUsuarioTest {

	private static IUsuario contUsuario = Fabrica.getIUsuario();
    private static IOferta contOferta = Fabrica.getIOferta();

    @BeforeAll
    //cargarDatos
    static void testcargarDatos() {
        CargarDatos cd = new CargarDatos();
        cd.carga();
    }

	@Test
	void testdarAltaPostulanteOK() {
        String nickU = "master28";
        String nomU = "Juan";
        String surnameU = "Perez";
        String mailU = "masterP8_2@gmail.com";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1988);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date fechaNacU = cal.getTime();
        String nacionU = "Uruguay";
        String contra = "1234";
        String imagen = "Usuarios/Juan.jpg";
        try {
            contUsuario.darAltaPostulante(nickU, nomU, surnameU, mailU, contra, imagen, fechaNacU, nacionU);
        }
        catch(CorreoUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }
        catch(NicknameUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }
        assertThrows(NicknameUsuarioExistente.class, ()->{contUsuario.darAltaPostulante(nickU, nomU, surnameU, mailU, contra, "", null,fechaNacU, nacionU);});
        assertThrows(CorreoUsuarioExistente.class, ()->{contUsuario.darAltaPostulante("nachomanyA", nomU, surnameU, mailU, contra, "", null,fechaNacU, nacionU);});
        
    	try {
			contUsuario.darAltaPostulante("chona", nomU, surnameU, "chona@chona.com", contra, "", null, fechaNacU, nacionU);
		} catch (NicknameUsuarioExistente e) {
			fail("no");
		} catch (CorreoUsuarioExistente e) {
			fail("no");
		}
    	
    	assertThrows(NoExisteEmpresa.class, ()->{contUsuario.getDTEmpresa("nachitoplena");});
        try {
        	contUsuario.getDTPostulante(nickU);
        }catch(Exception e) {
        	fail("no");
        }	
        
        DTUsuario dtu = null;
        try {
            dtu = contUsuario.mostrarDatosUsuario(nickU);
        }catch(NoExisteEmpresa | NoExistePostulante e) {
        	fail("No deberia lanzar excepcion");
        }


        //dynamic cast a DTPostulante
        if(dtu != null && !(dtu instanceof DTPostulante)) {
            fail("No se obtuvo un DTPostulante");
        }
        else{
            DTPostulante dtp = (DTPostulante) dtu;
            assertEquals(nickU, dtp.getNickname());
            assertEquals(nomU, dtp.getNombre());
            assertEquals(surnameU, dtp.getApellido());
            assertEquals(mailU, dtp.getCorreo());
            assertEquals(fechaNacU, dtp.getFechaNac());
            assertEquals(nacionU, dtp.getNacionalidad());
            assertEquals(contra,dtp.getContrasenia());
            assertEquals(imagen,dtp.getImagen());
        }
	}

    @Test
    void testdarAltaEmpresaOK() {
        String nickU = "google";
        String nomU = "Google";
        String surnameU = "Inc";
        String mailU = "google@hotmail.com";
        String descE = "Empresa de tecnologia";
        String linkE = "www.google.com";
        String contra = "google123";
        String imagen = "Usuarios/Google.jpg";
        try {
            contUsuario.darAltaEmpresa(nickU, nomU, surnameU, mailU,contra, imagen,descE, linkE);
        }
        catch(CorreoUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }
        catch(NicknameUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }
        
        assertThrows(NicknameUsuarioExistente.class, ()->{contUsuario.darAltaEmpresa(nickU, nomU, surnameU, mailU, contra, "", null,descE, linkE);});
        assertThrows(CorreoUsuarioExistente.class, ()->{contUsuario.darAltaEmpresa("nachomanyA", nomU, surnameU, mailU, contra, "", null, descE, linkE);});

        try {
			contUsuario.darAltaEmpresa("nachito", nomU, surnameU, "chona@pimbal.com", contra, "", null, descE, linkE);
		} catch (NicknameUsuarioExistente e) {
			fail("no");
		} catch (CorreoUsuarioExistente e) {
			fail("no");
		}
        assertThrows(NoExisteEmpresa.class, ()->{contUsuario.getDTEmpresa("nachoplena");});
        try {
        	contUsuario.getDTEmpresa(nickU);
        }catch(Exception e) {
        	fail("no");
        }
        
    }

    @Test
    void testdarAltaPostulanteNickExistente() {
        String nickU = "lgarcia";
        String nomU = "Pedro";
        String surnameU = "Figari";
        String mailU = "pedrito28@hotmail.com";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1988);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date fechaNacU = cal.getTime();
        String nacionU = "Uruguay";
        String contra = "garciap4";
        String imagen = "Usuarios/Lucia.jpg";

        assertThrows(NicknameUsuarioExistente.class, ()->{contUsuario.darAltaPostulante(nickU, nomU, surnameU, mailU,contra, imagen, fechaNacU, nacionU);});
    }

    @Test
    void finalizarOfertaTest() {
    	try {
			contUsuario.finalizarOferta("Desarrollador Frontend","EcoTech");
		} catch (NoExisteOferta e) {
			fail("asdhj");
		} catch (NoExisteEmpresa e) {
			fail("no");
		}
    }

    @Test
    void dejarSeguir() {
    	try {
    		ManejadorUsuario musr = ManejadorUsuario.getInstance();
    		Usuario gonza = musr.buscarUsuarioPorCorreo("gonza95@yahoo.com");
			String aux = gonza.getNickname();
			DTPostulante dtpos = musr.getDTPostulante(aux);
			aux = dtpos.getNickname();
    		contUsuario.seguirUsuario("sicam", aux);
			contUsuario.dejarDeSeguirUsuario("sicam", aux);
			aux = "EcoTech";
			DTEmpresa dtemp = musr.getDTEmpresa(aux);
			aux = dtemp.getNickname();
			contUsuario.seguirUsuario("sicam", aux);
			contUsuario.dejarDeSeguirUsuario("sicam", aux);
		} catch (NoExistePostulante e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoExisteEmpresa e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Test
    void favs() {
    	try {
			Set<String> favs = contUsuario.getFavoritosPostulante("lgarcia");
		} catch (NoExistePostulante e) {
			fail("no");
		}

    }

    @Test
    void testdarAltaPostulanteCorreoExistente() {
        String nickU = "lolito28";
        String nomU = "Pedro";
        String surnameU = "Figari";
        String mailU = "lgarcia85@gmail.com";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1988);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date fechaNacU = cal.getTime();
        String nacionU = "Uruguay";
        String contra = "lilis947";
        String imagen = "Usuarios/Loli.jpg";

        assertThrows(CorreoUsuarioExistente.class, ()->{contUsuario.darAltaPostulante(nickU, nomU, surnameU, mailU,contra, imagen,fechaNacU, nacionU);});
    }

    @Test
    //listarNicknamesUsuarios
    void testlistarNicknamesUsuarios() {
        Set<String> nicknames = contUsuario.listarNicknamesUsuarios();
        assertEquals(false, nicknames.contains("ajoel"));

        String nickU = "ajoel";
        String nomU = "Joel";
        String surnameU = "Aguirre";
        String mailU = "joelcabre@hotmail.com.uy";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2002);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date fechaNacU = cal.getTime();
        String nacionU = "Uruguay";
        String contra = "ajoel123";
        String imagen = "Usuarios/joela.jpg";
        try {
            contUsuario.darAltaPostulante(nickU, nomU, surnameU, mailU,contra, imagen, fechaNacU, nacionU);
        }
        catch(NicknameUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }
        catch(CorreoUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }
        // siento q falta llamar a esa funcion de nuevo  !!!!!!!!!!!!!!!!
        assertEquals(true, nicknames.contains("ajoel"));
    }

    @Test
    //mostrarEmpresas
    void testmostrarEmpresas() {
        Set<String> empresas = new HashSet<>();
        try{
            empresas = contUsuario.mostrarEmpresas();
        }
        catch(Exception ErrorFecha) {
            fail("No deberia lanzar excepcion");
        }
        assertEquals(false, empresas.contains("yahoo"));

        String nickU = "yahoo";
        String nomU = "David";
        String surnameU = "Filo";
        String mailU = "davidisimo@yahoo.com";
        String descE = "Empresa de tecnologia";
        String linkE = "www.yahoo.com";
        String contra = "yahoo123";
        String imagen = "Usuarios/Emp.jpg";
        try {
            contUsuario.darAltaEmpresa(nickU, nomU, surnameU, mailU,contra,imagen, descE, linkE);
        }
        catch(CorreoUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }
        catch(NicknameUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }
        assertEquals(true, empresas.contains("yahoo"));
        assertEquals(false, empresas.contains("apple"));
        //add apple
        nickU = "apple";
        nomU = "Steve";
        surnameU = "Jobs";
        mailU = "steve@apple.com";
        descE = "Empresa de tecnologia";
        linkE = "www.apple.com";
        contra = "apple123";
        imagen = "Usuarios/Apple.jpg";
        try {
            contUsuario.darAltaEmpresa(nickU, nomU, surnameU, mailU,contra, imagen, descE, linkE);
        }
        catch(CorreoUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }
        catch(NicknameUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }
        assertThrows(CorreoUsuarioExistente.class, ()->{contUsuario.darAltaEmpresa("juancito", "Steve", "Jobs", "steve@apple.com", "apple123", "Usuarios/Apple.jpg", "Empresa de tecnologia", "Empresa de tecnologia");});
        assertEquals(true, empresas.contains("apple"));
    }

    @Test
    //modificarDatosPostulante
    void testmodificarDatosPostulante() {
        String nickU = "lucho";
        String nomU = "Luis";
        String surnameU = "Perez";
        String mailU = "luchito@fing.edu.uy";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1990);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date fechaNacU = cal.getTime();
        String nacionU = "Uruguay";
        String contra = "luchito123";
        String imagen = "Usuarios/Luis.jpg";
        try{
            contUsuario.darAltaPostulante(nickU, nomU, surnameU, mailU, contra, imagen, fechaNacU, nacionU);
        }
        catch(NicknameUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }
        catch(CorreoUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }

        //
        String nuevoNombre = "Lucas";
        String nuevoApellido = "Martinez";
        String nuevaNacionalidad = "Argentina";
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        Date nuevaFechaNac = cal.getTime();
        String nuevaContra = "nuevaContra";
        String nuevaImagen = "Usuarios/nuevaImagen.jpg";

        try {
            contUsuario.modificarDatosPostulante(nickU, nuevoNombre, nuevoApellido, nuevaContra, nuevaImagen, nuevaNacionalidad, nuevaFechaNac);
        } catch (NoExistePostulante e) {
            fail("No deberia lanzar excepcion");
        }

        DTUsuario dtu = null;
        try{
        	dtu = contUsuario.mostrarDatosUsuario(nickU);
        }catch(NoExisteEmpresa | NoExistePostulante e) {
        	fail("No deberia lanzar excepcion");
        }

        if((dtu != null) && !(dtu instanceof DTPostulante)) {
            fail("No se obtuvo un DTPostulante");
        }
        else{
            DTPostulante dtp = (DTPostulante) dtu;
            assertEquals(nuevoNombre, dtp.getNombre());
            assertEquals(nuevoApellido, dtp.getApellido());
            assertEquals(nuevaFechaNac, dtp.getFechaNac());
            assertEquals(nuevaNacionalidad, dtp.getNacionalidad());
            assertEquals(nuevaContra,dtp.getContrasenia());
            assertEquals(nuevaImagen,dtp.getImagen());
        }
    }

    @Test
    //modificarDatosEmpresa
    void testmodificarDatosEmpresa() {
        String nickU = "microsoft";
        String nomU = "Bill";
        String surnameU = "Gates";
        String mailU = "billg@microsoft.net";
        String descE = "Empresa de tecnologia";
        String linkE = "www.microsoft.com";
        String contra = "microsoft123";
        String imagen = "Usuarios/Microsoft.jpg";

        try {
            contUsuario.darAltaEmpresa(nickU, nomU, surnameU, mailU,contra, imagen, descE, linkE);
        }
        catch(CorreoUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }
        catch(NicknameUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }

        String nuevoNombre = "Billgito";
        String nuevoApellido = "Gatesito";
        String nuevaDesc = "Empresa genial";
        String nuevoLink = "www.microsoft.net";
        // nueva contr y nueva imagen
        String nuevaContra = "nuevaContrasenia";
        String nuevaImagen = "Usuarios/nuevaImagen.jpg";

        try {
            contUsuario.modificarDatosEmpresa(nickU, nuevoNombre, nuevoApellido, nuevaContra, nuevaImagen, nuevaDesc, nuevoLink);
        } catch (NoExisteEmpresa e) {
            fail("No deberia lanzar excepcion");
        }

        DTUsuario dtu = null;
        try{
        	dtu = contUsuario.mostrarDatosUsuario(nickU);
        }catch(NoExisteEmpresa | NoExistePostulante e) {
        	fail("No deberia lanzar excepcion");
        }

        if((dtu != null) && !(dtu instanceof DTEmpresa)) {
            fail("No se obtuvo un DTPostulante");
        }
        else{
            DTEmpresa dte = (DTEmpresa) dtu;
            assertEquals(nuevoNombre, dte.getNombre());
            assertEquals(nuevoApellido, dte.getApellido());
            assertEquals(nuevaDesc, dte.getDescripcion());
            assertEquals(nuevoLink, dte.getLink());
            assertEquals(nuevaContra,dte.getContrasenia());
            assertEquals(nuevaImagen,dte.getImagen());
        }
    }

    @Test
    //postular sin video
    void testpostularSV() { // ERROR FECHA
        String nickU = "sebgon";
        String nombreOferta = "Gerente de Proyecto";

        String cvRed = "Tengo experiencia en caja y atencion al cliente";
        String motivacion = "Me gusta la comida rapida";
        DTUsuario dtu = null;
        try{
        	dtu = contUsuario.mostrarDatosUsuario(nickU);
        }catch(NoExisteEmpresa | NoExistePostulante e) {
        	fail("No deberia lanzar excepcion");
        }
        //to DTPostulante
        if((dtu != null) && !(dtu instanceof DTPostulante)) {
            fail("No se obtuvo un DTPostulante");
        }
        else{
            DTPostulante dtp = (DTPostulante) dtu;
            Set<DTOferta> dtos = dtp.getOfertas();
            boolean res = false;
            for(DTOferta dto : dtos) {
                if(dto.getNombre().equals(nombreOferta)) {
                    res = true;
                }
            }
            assertEquals(false, res);
        }

    }


    @Test
    //postularAOferta
    void testpostularAOfertaOK() { // ERROR FECHA
        String nickU = "lgarcia";
        if(contUsuario.nickValido(nickU)) {
        	fail("no");
        }
        String nombreOferta = "Gerente de Proyecto";

        String cvRed = "Tengo experiencia en caja y atencion al cliente";
        String motivacion = "Me gusta la comida rapida";
        String video = "<iframe width=\"640\" height=\"360\" src=\"https://www.youtube.com/embed/gHMiklMA-ag\" title=\"NEW DUB REGGAE [[[(BEST SELECTION MIX)]]]\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        DTUsuario dtu = null;
        try{
        	dtu = contUsuario.mostrarDatosUsuario(nickU);
        }catch(NoExisteEmpresa | NoExistePostulante e) {
        	fail("No deberia lanzar excepcion");
        }
        //to DTPostulante
        if((dtu != null) && !(dtu instanceof DTPostulante)) {
            fail("No se obtuvo un DTPostulante");
        }
        else{
            DTPostulante dtp = (DTPostulante) dtu;
            Set<DTOferta> dtos = dtp.getOfertas();
            boolean res = false;
            for(DTOferta dto : dtos) {
                if(dto.getNombre().equals(nombreOferta)) {
                    res = true;
                }
            }
            assertEquals(false, res);
        }

        //fechaPub
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 10);
        Date fechaPub = cal.getTime();
     
            try {
				contUsuario.postularAOferta(nickU, cvRed, motivacion, fechaPub, video, nombreOferta);
			} catch (OfertaExpirada e) {
				fail("no");
			} catch (NoExistePostulante e) {
				fail("no");
			} catch (PostulantePoseeOferta e) {
				fail("no");
			} catch (NoExisteOferta e) {
				fail("no");
			} catch (ErrorFecha e) {
				fail("no");
			}  
        
            
        try{
        	dtu = contUsuario.mostrarDatosUsuario(nickU);
        	
        }catch(NoExisteEmpresa | NoExistePostulante e) {
        	fail("No deberia lanzar excepcion");
        }
        //to DTPostulante
        if((dtu != null) && !(dtu instanceof DTPostulante)) {
            fail("No se obtuvo un DTPostulante");
        }
        else{
            DTPostulante dtp = (DTPostulante) dtu;
            Set<DTOferta> dtos = dtp.getOfertas();
            boolean res = false;
            for(DTOferta dto : dtos) {
                if(dto.getNombre().equals(nombreOferta)) {
                    res = true;
                }
            }
            assertEquals(true, res);
        }


        // veo si el postulante quedo asociado a los postulantes de dicha oferta
        try {
            Set<String> postulantesAoferta = contUsuario.mostrarPostulantes(nombreOferta);
            boolean pertenece = postulantesAoferta.contains(nickU);
            assertEquals(true,pertenece);
        }catch(NoExisteOferta e) {
        	fail("No deberia lanzar excepcion");
        }



    }

    @Test
    void testpostularAOfertaErrorFecha() {
        String nombreOferta = "Estratega de Negocios";
        String nickU = "sebgon";
        String video = "<iframe width=\"640\" height=\"360\" src=\"https://www.youtube.com/embed/gHMiklMA-ag\" title=\"NEW DUB REGGAE [[[(BEST SELECTION MIX)]]]\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";

        String cvRed = "Tengo experiencia en caja y atencion al cliente";
        String motivacion = "Me gusta la comida rapida";

        //fechaPostulacion anterior a 14/08/2023
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 13);
        Date fechaPub = cal.getTime();

        assertThrows(ErrorFecha.class, ()->{contUsuario.postularAOferta(nickU, cvRed, motivacion, fechaPub, video,nombreOferta);});

    }


    @Test
    //mostrarOfertasVigentesEmpresa
    void testmostrarOfertasVigentesEmpresa() {
        String nickU = "renner_uy";
        String nomU = "Jose";
        String surnameU = "Gallo";
        String mailU = "joselo@renner.com.uy";
        String descE = "Empresa de ropa";
        String linkE = "www.renner.com.uy";
        String contra = "renner123";
        String imagen = "imagen";
        try {
            contUsuario.darAltaEmpresa(nickU, nomU, surnameU, mailU, contra, imagen,descE, linkE);
        }
        catch(CorreoUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }
        catch(NicknameUsuarioExistente e) {
            fail("No deberia lanzar excepcion");
        }
        

        //add tipo de publicacion
        String nombreTP = "Normal";
        String descTP = "Publicacion Normal";
        int expTP = 3;
        int duracionTP = 30;
        double costoTP = 500;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date fechaAltaTP = cal.getTime();
        try {
            contOferta.altaTipoDePublicacion(nombreTP, descTP, expTP, duracionTP, costoTP, fechaAltaTP);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion");
        }

        Set<String> ofertasEmpresa = new HashSet<>();

        assertThrows(NoTieneOfertasConfirmadasVigentes.class, ()->{contUsuario.mostrarOfertasVigentesEmpresa(nickU);});

        String nombreOferta = "Vendedor";
        String descripcionOferta = "Vendedor de ropa";
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 9);
        Date fechaPub = cal.getTime();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 9);
        String horario = "8:00 - 18:00";
        Double remuneracion = 20000.0;
        String ciudad = "Montevideo";
        String departamento = "Montevideo";
        Set<String> keywords = new HashSet<>();
        String imagenO = "Ofertas/of.jpg";

        try {
            contOferta.ingresarOferta(nickU,nombreTP,nombreOferta, descripcionOferta, horario, remuneracion,ciudad, departamento,fechaPub, keywords, imagenO);
            contOferta.cambiarEstadoOferta(nombreOferta, Estados.confirmada);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion");
        }

        try {
            ofertasEmpresa = contUsuario.mostrarOfertasVigentesEmpresa(nickU);
        } catch (NoExisteEmpresa e) {
            fail("No deberia lanzar excepcion");
        } catch (NoExisteOfertaEmpresa e) {
            fail("No deberia lanzar excepcion");
        } catch (NoTieneOfertasConfirmadasVigentes e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        assertEquals(true, ofertasEmpresa.contains(nombreOferta));
    }

    @Test
    //mostrarPostulantes
    void testmostrarPostulantes() {
        Set<String> postulantes = new HashSet<>();
        try{
            postulantes = contUsuario.mostrarPostulantes();
        }
        catch(Exception ErrorFecha) {
            fail("No deberia lanzar excepcion");
        }
        assertEquals(false, postulantes.contains("misterworldwide"));

        assertEquals(true, postulantes.contains("lgarcia"));
    }

    @Test
    //mostrarInfoBasicaOfertas
    void testmostrarInfoBasicaOfertasOK() {
        Set<DTOferta> dtos = new HashSet<>();
        try{
            dtos = contUsuario.mostrarInfoBasicaOfertas("javierf");
            String n = "Diseñador UX/UI";
        	String d = "Trabaja en colaboración con nuestro talentoso equipo de diseño para crear soluciones impactantes.";
        	String c = "Rosario";
        	String dep = "Colonia";
        	String h = "14:00 - 18:00";
        	double r = 65000.0;
        	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    	    Date date = null;
    		try {
    			date = formatter.parse("29-10-2023");
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

        	Date fA = date;

        	Set<String> pos = new HashSet<>();
        	pos.add("javierf");
        	String tipoPubli = "Estándar";
        	Set<String> keywords = new HashSet<>();
        	keywords.add("Mediotiempo");
        	keywords.add("Remoto");
        	keywords.add("Permanente");

        	DTPaquete paq = null;

        	if(dtos.size()!=1)
        		fail("Devuelve distinto de 1");
        	else {
        		for(DTOferta dt: dtos) {
                    assertEquals(n,dt.getNombre());
                    assertEquals(dt.getDescripcion(),d);
          			assertEquals(c,dt.getCiudad());
          			assertEquals(dep,dt.getDepartamento());
          			assertEquals(h,dt.getHorario());
          			System.out.print(dt.getFechaAlta());
          			System.out.print(fA);
          			assertEquals(fA,dt.getFechaAlta());
                    //check postulantes one by one and same size
                    Set<String> postulantes = dt.getPostul();
                    assertEquals(pos.size(), postulantes.size());
                    for(String p : postulantes) {
                        assertEquals(true, pos.contains(p));
                    }
          			assertEquals(tipoPubli,dt.getTipoPublicacion());
                    //check keywords one by one and same size
                    Set<String> kws = dt.getKeywords();
                    assertEquals(keywords.size(), kws.size());
                    for(String kw : kws) {
                        assertEquals(true, keywords.contains(kw));
                    }
          			assertEquals(paq,dt.getDTPaqueteAsociado());

        		}
        	}

        }
        catch(Exception NoExistePostulante) {
            fail("No deberia lanzar excepcion");
        }
        //Licenciada en Administración, experiencia en gestión de equipos y proyectos. Conocimientos en Microsoft Office.;Estoy emocionada por la oportunidad de formar parte de un equipo dinámico y contribuir con mis habilidades de liderazgo.;16/08/23

    }

    @Test
    //mostrarInfoBasicaOfertas
    void testmostrarInfoBasicaOfertasNoExisteUsuario() {
        assertThrows(NoExistePostulante.class, ()->{contUsuario.mostrarInfoBasicaOfertas("sabelousuario");});
    }

    @Test
    void testIniciarSesionOK() {
        String nickU = "lgarcia";
        String contra = "awdrg543";
        try {
            boolean ini = contUsuario.iniciarSesion(nickU, contra);
            assertEquals(true,ini);
        }catch(NoExistePostulante | NoExisteEmpresa e) {
        	fail("No deberia lanzar excepcion");
        }
    }

    @Test
    void testIniciarSesionNOT_OK() {
        String nickU = "lgarcia";
        String contra = "not_her_password";
        try {
            boolean ini = contUsuario.iniciarSesion(nickU, contra);
            assertEquals(false,ini);
        }catch(NoExistePostulante | NoExisteEmpresa e) {
        	fail("No deberia lanzar excepcion");
        }
    }

    @Test
    void testIniciarSesionNOT_EXISTS_USER() {
        String nickU = "usuario_inexistente";
        String contra = "not_a_password";
        // primero se lanza la de postulante
        assertThrows(NoExistePostulante.class, ()->{contUsuario.iniciarSesion(nickU,contra);});
    }

    @Test
    void testaltaEmpresExistente() {
        String nickU = "EcoTech";
        String nomU = "eco";
        String surnameU = "tech";
        String mailU = "ecotechi@renner.com.uy";
        String descE = "Empresa de ropa";
        String linkE = "www.renner.com.uy";
        String contra = "renner123";
        String imagen = "imagen.jpg";
        // primero se lanza la de postulante
        assertThrows(NicknameUsuarioExistente.class, ()->{contUsuario.darAltaEmpresa(nickU, nomU, surnameU, mailU, contra, imagen,descE, linkE);});
    }

    /* DA SIEMPRE VACIO LA FUNCION OFERTASEMPRESAINGRESADAS
    @Test
    void testmostrarOfertasDeEmpresaIngresadas() {
    	String nickE = "EcoTech";
    	Set<String> ofs = new HashSet<>();
    	try {
    		ofs = contUsuario.mostrarOfertasDeEmpresaIngresadas(nickE);
    		System.out.print(ofs);
    	}catch(NoExisteEmpresa e) {
    		fail("No deberia lanzar excepcion");
    	}
    	boolean pertenece = ofs.contains("Desarrollador de Software Senior");
    	assertEquals(true,pertenece);

    }*/


    @Test
    void testmostrarComprasPaquetesEmpresa() {
    	String nickE = "EcoTech";
    	Set<String> pqts = new HashSet<>();
    	try {
    		pqts = contUsuario.mostrarComprasPaquetesEmpresa(nickE);
    	}catch(NoExisteEmpresa e) {
    		fail("No deberia lanzar excepcion");
    	}
    	boolean pertenece = pqts.contains("Premium");
    	assertEquals(true,pertenece);

    }

    @Test
    void testMostrarOfertasConfirmadasNOT_EXISTS_EMPRESA() {
        String nickU = "NOT_EcoTech";
        assertThrows(NoExisteEmpresa.class, ()->{contUsuario.mostrarOfertasConfirmadas(nickU);});
    }


    @Test
    void testmostrarOfertasVigentesConfirmadas() {
    	String nickE = "EcoTech";
    	Set<String> ofs = new HashSet<>();
    	Set<String> keywords = new HashSet<>();
    	keywords.add("Remoto");
    	Set<DTOferta> ofsv = null;
    	boolean pertenece = false;
    	boolean pertenecevig = false;

    	boolean nov = false;
    	Set<DTOferta> ofsvn = null;
    	Set<DTOferta> ofsf = null;
    	boolean fin = false;
    	boolean total = false;
    	Set<String> ofertas = null;
    	String nomo = "A. de Marketing Digital";
    	try {
    		ofertas = contOferta.listarOfertasDeEmpresa(nickE);
    		ofs = contUsuario.mostrarOfertasVigentesConfirmadasEK(nickE, keywords);
    		ofsv = contUsuario.mostrarOfertasConfirmadasVigentes(nickE);
    		ofsvn = contUsuario.mostrarOfertasConfirmadasNoVigentes(nickE);
    		ofsf = contUsuario.listarOfertasFinalizadasAsoc(nickE);

    	}catch(NoExisteEmpresa e) {
    		fail("No deberia lanzar excepcion");
    	} catch (NoExistePostulante e) {
			// TODO Auto-generated catch block
    		fail("No deberia lanzar excepcion");
		} catch (NoExisteOfertaEmpresa e) {
			fail("no llega");
		} catch (NoTieneOfertasConfirmadasVigentes e) {
			fail("no");
		}
    	pertenece = ofs.contains(nomo);
    	try {
    		nov = false;
    		for (DTOferta oferta : ofsvn) {
                if (nomo.equals(oferta.getNombre())) {
                    nov = true;
                    break;
                }
            }
    		fin = ofsf.contains(contOferta.obtenerOfertaPorNombre(nomo));
			total = ofertas.contains(nomo);
			//System.out.println(ofsvn);
    		pertenecevig = ofsv.contains(contOferta.obtenerOfertaPorNombre(nomo));
		} catch (NoExisteOferta e) {
			fail("No entra");
		}
    	assertEquals(true,total);
    	assertEquals(false,nov);
    	assertEquals(false,fin);
    	assertEquals(false,pertenecevig);
    	assertEquals(false,pertenece);

    }

    @Test
    void testmostrarDTEmpresas() {
    	String aux = "EcoTech";
    	boolean auxb = false;
    	try {
			Set<DTEmpresa> emps = contUsuario.mostrarDTEmpresas();
    		for (DTEmpresa emp : emps) {
                if (emp.getNickname().equals(aux)) {
                    auxb = true;
                    break;
                }
            }
		} catch (NoExistenEmpresas | NoExisteEmpresa | NoExistePostulante e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	assertTrue(auxb);

    }

    @Test
    void testaceptarRechazarOferta() {
    	String oferta = "Desarrollador de Software Senior"; // ingresada

    	try {
    		contUsuario.rechazarOferta(oferta);
    	}catch(NoExisteOferta e) {
    		fail("No deberia lanzar excepcion");
    	}

    	DTOferta of = null;
    	try{
    		of = contOferta.getDTOferta(oferta);
    	}catch(NoExisteOferta e) {
    		fail("No deberia lanzar excepcion");
    	}

    	assertEquals(Estados.rechazada,of.getEstado());


    	// otra oferta
    	String oferta2 = "Ingeniero de Calidad de Software"; // ingresada

    	try {
    		contUsuario.aceptarOferta(oferta2);
    	}catch(NoExisteOferta e) {
    		fail("No deberia lanzar excepcion");
    	}

    	DTOferta of2 = null;
    	try{
    		of2 = contOferta.getDTOferta(oferta2);
    	}catch(NoExisteOferta e) {
    		fail("No deberia lanzar excepcion");
    	}

    	assertEquals(Estados.confirmada,of2.getEstado());

    }

    @Test
	public void testMostrarComprasPaquetesConElTipo() throws NoExisteEmpresa{
		String nombre_tipo = "Estándar";
		IUsuario IUsr = Fabrica.getIUsuario();

		Empresa e = ManejadorUsuario.getInstance().obtenerEmpresa("TechSolutions");
		String resEsp = "Destacado";
		Set<String> comps = e.mostrarComprasPaquetesConElTipo(nombre_tipo);

		for(String c:comps)
			assertEquals(resEsp, c);
	}

    @Test
  	public void testGetPaquetesSinComprar(){


    	try {
			Set<String> nombresPaq = contUsuario.getPaquetesSinComprar("FusionTech");
			assertFalse(nombresPaq.contains("Destacado"));
			assertTrue(nombresPaq.contains("Express"));
		} catch (NoExisteEmpresa e) {
			// TODO Auto-generated catch block
    		fail("No deberia lanzar excepcion");
		}

    }

	    @Test
	  	public void testGetDTComprasEmpresa(){
	    	 try {
				Set<DTCompra> comprasEmp = contUsuario.getDTComprasEmpresa("FusionTech");
				boolean variable1 = false;
				boolean variable2 = false;
				boolean variable3 = false;
				boolean variable4 = false;

				for(DTCompra compras :comprasEmp) {
					if("Destacado".equals(compras.getDTPaquete().getNombre())) {
						variable1=true;
					}
					if("Básico".equals(compras.getDTPaquete().getNombre())) {
						variable2=true;
					}
					if("Premium".equals(compras.getDTPaquete().getNombre())) {
						variable3=true;
					}
					if("Express".equals(compras.getDTPaquete().getNombre())) {
						variable4=true;
					}

				}
				assertTrue(variable1);

				assertFalse(variable2);
				assertFalse(variable3);
				assertFalse(variable4);

			} catch (NoExisteEmpresa e) {
				// TODO Auto-generated catch block
	    		fail("No deberia lanzar excepcion");
			}
	    }



	    @Test
	  	public void testListarPaquetesAdquiridosVigentesTipo(){

	    	 try {
				Set<String> paquetes = contUsuario.listarPaquetesAdquiridosVigentesTipo("Premium","EcoTech" );
				assertFalse(paquetes.contains("Destacado"));
				assertFalse(paquetes.contains("Básico"));
				assertFalse(paquetes.contains("Express"));
				assertTrue(paquetes.contains("Premium"));

			} catch (NoExisteEmpresa e) {
				// TODO Auto-generated catch block
	    		fail("No deberia lanzar excepcion");
			}
	    }



		    @Test
		  	public void testMostrarInfoPostulacion(){
				 try {
					DTPostulacion postulacion = contUsuario.mostrarInfoPostulacion("matilo", "Estratega de Negocios");
					assertEquals(postulacion.getCVR(),"Estudiante de Comunicación, habilidades en redacción y manejo de redes sociales. Experiencia en prácticas en medios locales.");
					assertEquals(postulacion.getMotivacion(),"Me encantaría formar parte de un equipo que me permita desarrollar mis habilidades en comunicación y marketing.");

				} catch (NoExistePostulante e) {
					// TODO Auto-generated catch block
		    		fail("No deberia lanzar excepcion");
				}

		    }



		    @Test
		    public void testMostrarOfertasPostulante(){
		    	try {
					Set<String> ofertas = contUsuario.mostrarOfertasPostulante("lgarcia");
					assertFalse(ofertas.contains("Desarrollador Frontend"));
					assertTrue(ofertas.contains("Estratega de Negocios"));
					assertFalse(ofertas.contains("Ingeniero de Calidad de Software"));

				} catch (NoExistePostulante e) {
					// TODO Auto-generated catch block
		    		fail("No deberia lanzar excepcion");
				}

		    }


				@Test
			    public void testMostrarOfertasDeEmpresaIngresadas(){
					 try {
						Set<String> ofertas = contUsuario.mostrarOfertasDeEmpresaIngresadas("TechSolutions");
						assertTrue(ofertas.contains("Desarrollador de Software Full Stack"));
						assertFalse(ofertas.contains("Soporte Técnico"));
						assertFalse(ofertas.contains("Gerente de Proyecto"));
						assertFalse(ofertas.contains("Desarrollador Frontend"));

					} catch (NoExisteEmpresa e) {
						// TODO Auto-generated catch block
			    		fail("No deberia lanzar excepcion");
					}
				}


				@Test
			    public void testMostrarOfertasConfirmadas(){
						try {
							boolean variable1 = false;
							boolean variable2 = false;
							boolean variable3 = false;
							boolean variable4 = false;
							Set<DTOferta> ofertasC = contUsuario.mostrarOfertasConfirmadas("TechSolutions");
							for(DTOferta oferta :ofertasC) {
								if("Desarrollador Frontend".equals(oferta.getNombre())) {
									variable1=true;
								}
								if("Gerente de Proyecto".equals(oferta.getNombre())) {
									variable2=true;
								}
								if("Soporte Técnico".equals(oferta.getNombre())) {
									variable3=true;
								}
								if("Desarrollador de Software Full Stack".equals(oferta.getNombre())) {
									variable4=true;
								}

							}
							assertFalse(variable1);
							assertFalse(variable4);

							assertTrue(variable2);
							assertTrue(variable3);

						} catch (NoExisteEmpresa | NoExistePostulante e) {
							// TODO Auto-generated catch block
				    		fail("No deberia lanzar excepcion");
						}
				}

				@Test
			    public void testMostrarInfoDetalladaOferta(){

					DTOferta oferta;
					try {
						oferta = contUsuario.mostrarInfoDetalladaOferta("Desarrollador Frontend");
						assertEquals(oferta.getCiudad(),"Montevideo");
						assertEquals(oferta.getDepartamento(),"Montevideo");
						assertEquals(oferta.getDescripcion(),"Únete a nuestro equipo de desarrollo frontend y crea experiencias de usuario excepcionales.");
						assertEquals(oferta.getNickEmpresa(),"EcoTech");
						assertEquals(oferta.getNombre(),"Desarrollador Frontend");
						assertEquals(oferta.getTipoPublicacion(),"Premium");
					} catch (NoExisteOferta e) {
						// TODO Auto-generated catch block
			    		fail("No deberia lanzar excepcion");
					}


				}

}
