package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import excepciones.CorreoUsuarioExistente;
import excepciones.NicknameUsuarioExistente;
import excepciones.NoExisteEmpresa;
import excepciones.NoExisteKeyword;
import excepciones.NoExisteOferta;
import excepciones.NoExisteOfertaEmpresa;
import excepciones.NoExistePaquete;
import excepciones.NoExistePostulante;
import excepciones.NoExisteTipoPublicacion;
import excepciones.NoExistenEmpresas;
import excepciones.NombreKeywordExistente;
import excepciones.NombreOfertaExistente;
import excepciones.NombrePaqueteExistente;
import excepciones.NombreTipoDePublicacionExistente;
import excepciones.NombreTipoPublicacionExistente;
import excepciones.PaqueteYaComprado;
import logica.CargarDatos;
import logica.DTCompra;
import logica.DTOferta;
import logica.DTPaquete;
import logica.DTPostulacion;
import logica.DTTipoPublicacion;
import logica.Fabrica;
import logica.IOferta;
import logica.IUsuario;
import logica.Oferta.Estados;

public class ContOfertaTest {
	private static IOferta contOferta = Fabrica.getIOferta();
	private static IUsuario contUsuario = Fabrica.getIUsuario();

    @BeforeAll
    //cargarDatos
    static void testcargarDatos() {
    	@SuppressWarnings("unused")
		Fabrica fabrica = Fabrica.getInstance();
        CargarDatos cd = new CargarDatos();
        cd.carga();
        Set<String> paquetes = contOferta.listarPaquetesLibres();
    	assertTrue(paquetes.isEmpty());
    }

	@Test
	void testCrearPaqueteValido() {
			String nombreP = "master";
	      	String descP = "Publica ofertas laborales destacadas que se mostrará en la parte superior de los resultados de búsqueda por 45 días";
	      	int validezP = 4;
	      	double descuentoP = 20;
	      	Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.YEAR, 1988);
	        cal.set(Calendar.MONTH, Calendar.JANUARY);
	        cal.set(Calendar.DAY_OF_MONTH, 1);
	        Date fechaAltaP = cal.getTime();
	        String img = "Paquetes/Paq1.jpg";

	        try {
				contOferta.crearPaquete(nombreP, descP, validezP, descuentoP, fechaAltaP, img);
			} catch (NombrePaqueteExistente e) {
				// TODO Auto-generated catch block
	            fail("No deberia lanzar excepcion");
			}
	        try {

				DTPaquete dtp = contOferta.getInfoPaquete(nombreP);
				assertEquals(contOferta.listarPaquetesRegistrados().contains("master"),true);

				double delta = 0.0001;
				assertEquals(nombreP,dtp.getNombre());
				assertEquals(descP,dtp.getDescripcion());
				assertEquals(validezP,dtp.getValidez());
				assertEquals(fechaAltaP,dtp.getFechaAlta());
				assertEquals(descuentoP,dtp.getDescuento(),delta);
				assertEquals(img,dtp.getImagen());


	        } catch (NoExistePaquete e) {
				// TODO Auto-generated catch block
	            fail("No deberia lanzar excepcion");
			}

	}
	        @Test
	        void testCrearPaqueteNombreExistente() {

				String nombreP = "premium";
		      	String descP = "Publica ofertas laborales destacadas que se mostrará en la parte superior de los resultados de búsqueda por 45 días";
		      	int validezP = 4;
		      	double descuentoP = 20;
		      	Calendar cal = Calendar.getInstance();
		        cal.set(Calendar.YEAR, 1988);
		        cal.set(Calendar.MONTH, Calendar.JANUARY);
		        cal.set(Calendar.DAY_OF_MONTH, 1);
		        Date fechaAltaP = cal.getTime();
		        String imgP = "Paquetes/Paq2.jpg";


		        try {
					contOferta.crearPaquete(nombreP, descP, validezP, descuentoP, fechaAltaP,imgP);
				} catch (NombrePaqueteExistente e) {
					// TODO Auto-generated catch block
		            fail("No deberia lanzar excepcion");
				}
		        try {

					DTPaquete dtp = contOferta.getInfoPaquete(nombreP);
					double delta = 0.0001;



		        } catch (NoExistePaquete e) {
					// TODO Auto-generated catch block
		            fail("No deberia lanzar excepcion");
				}


		        assertThrows(NombrePaqueteExistente.class, ()->{contOferta.crearPaquete(nombreP, descP, validezP, descuentoP, fechaAltaP, imgP);});
	}


	        @Test
	    	void testAltaKeywordValidas() {
				String nombreK = "validadado";
				String nombreK2 = "noExistente";
				String nombreK3 = "clave";

				try {
					contOferta.altaKeyword(nombreK);
					contOferta.altaKeyword(nombreK2);
					contOferta.altaKeyword(nombreK3);

				} catch (NombreKeywordExistente e) {
					// TODO Auto-generated catch block
		            fail("No deberia lanzar excepcion");
				}

					try {
						assertEquals(contOferta.listarNombresKeywords().contains(nombreK3),true);
						assertEquals(contOferta.listarNombresKeywords().contains(nombreK2),true);
						assertEquals(contOferta.listarNombresKeywords().contains(nombreK),true);
					} catch (NoExisteKeyword e) {
						// TODO Auto-generated catch block
			            fail("No deberia lanzar excepcion");
					}



	        }


	        @Test
	    	void testAltaKeywordNoValidas() {
				String nombreK4 = "noValida";
				String nombreK5 = "Existente";

				try {
					contOferta.altaKeyword(nombreK4);
					contOferta.altaKeyword(nombreK5);

				} catch (NombreKeywordExistente e) {
					// TODO Auto-generated catch block
		            fail("No deberia lanzar excepcion");
				}

		        assertThrows(NombreKeywordExistente.class, ()->{contOferta.altaKeyword(nombreK4);});



	        }

	        @Test
	        void testListarPaquetesRegistrados() {
	        	String nombreP = "premiumV2";
		      	String descP = "Publica ofertas laborales destacadas que se mostrará en la parte superior de los resultados de búsqueda por 45 días";
		      	int validezP = 4;
		      	double descuentoP = 20;
		      	Calendar cal = Calendar.getInstance();
		        cal.set(Calendar.YEAR, 1988);
		        cal.set(Calendar.MONTH, Calendar.JANUARY);
		        cal.set(Calendar.DAY_OF_MONTH, 1);
		        Date fechaAltaP = cal.getTime();
		        String imgP = "Paquetes/Paq2_V2.jpg";


		        try {
					contOferta.crearPaquete(nombreP, descP, validezP, descuentoP, fechaAltaP, imgP);
				} catch (NombrePaqueteExistente e) {
					// TODO Auto-generated catch block
		            fail("No deberia lanzar excepcion");
				}
				assertEquals(contOferta.listarPaquetesRegistrados().contains("premiumV2"),true);


	        }
	        @Test
	        void testInfoTipoPublicacionValido() {
	            String nombreTP = "golden";
	            String descTP = "Esta cosa no sirve";
	            int expTP = 4;
	            int duracionTP = 10;
	            double costoTP = 300;
	        	Calendar cal = Calendar.getInstance();
		        cal.set(Calendar.YEAR, 1988);
		        cal.set(Calendar.MONTH, Calendar.JANUARY);
		        cal.set(Calendar.DAY_OF_MONTH, 1);
		        Date fechaAltaTP = cal.getTime();
		        try {
					contOferta.altaTipoDePublicacion(nombreTP, descTP, expTP, duracionTP, costoTP, fechaAltaTP);
				} catch (NombreTipoPublicacionExistente | NombreTipoDePublicacionExistente e) {
					// TODO Auto-generated catch block
		            fail("No deberia lanzar excepcion");
				}

		        try {
					DTTipoPublicacion tp = contOferta.getInfoTipoPublicacion(nombreTP);
					assertEquals(nombreTP,tp.getNombre());
					assertEquals(descTP,tp.getDescripcion());
					assertEquals(expTP,tp.getExposicion());
					assertEquals(fechaAltaTP,tp.getFechaAlta());
					assertEquals(duracionTP,tp.getDuracion());
					assertEquals(costoTP,tp.getCosto(),0.000001);

				} catch (NoExisteTipoPublicacion e) {
					// TODO Auto-generated catch block
		            fail("No deberia lanzar excepcion");
				}
	        }


	        @Test
	        void testListarNombreTiposPublicacionValido() {
	        	 String nombreTP = "goldenV2";
		            String descTP = "Esta cosa no sirve";
		            int expTP = 4;
		            int duracionTP = 10;
		            double costoTP = 300;
		        	Calendar cal = Calendar.getInstance();
			        cal.set(Calendar.YEAR, 1988);
			        cal.set(Calendar.MONTH, Calendar.JANUARY);
			        cal.set(Calendar.DAY_OF_MONTH, 1);
			        Date fechaAltaTP = cal.getTime();
			        try {
						contOferta.altaTipoDePublicacion(nombreTP, descTP, expTP, duracionTP, costoTP, fechaAltaTP);
					} catch (NombreTipoPublicacionExistente | NombreTipoDePublicacionExistente e) {
						// TODO Auto-generated catch block
			            fail("No deberia lanzar excepcion");
					}

			        try {
			        	assertEquals(contOferta.listarNombresTipos().contains("goldenV2"),true);
			        } catch (NoExisteTipoPublicacion e) {
			        	// TODO Auto-generated catch block
			        	fail("No deberia lanzar excepcion");
			        }


	        }


	        @Test
	        void testInfoPaqueteValido() {
	        	String nombreP = "PreMium1";
		      	String descP = "Publica ofertas laborales destacadas que se mostrará en la parte superior de los resultados de búsqueda por 45 días";
		      	int validezP = 4;
		      	double descuentoP = 20;
		      	Calendar cal = Calendar.getInstance();
		        cal.set(Calendar.YEAR, 1988);
		        cal.set(Calendar.MONTH, Calendar.JANUARY);
		        cal.set(Calendar.DAY_OF_MONTH, 1);
		        Date fechaAltaP = cal.getTime();
		        String imgP = "Paquetes/Paq3.jpg";

		        try {
					contOferta.crearPaquete(nombreP, descP, validezP, descuentoP, fechaAltaP, imgP);
				} catch (NombrePaqueteExistente e) {
					// TODO Auto-generated catch block
		            fail("No deberia lanzar excepcion");
				}

		        try {
					contOferta.altaTipoDePublicacion("micasa", "notengo", 4, 4,10, fechaAltaP);
				} catch (NombreTipoPublicacionExistente | NombreTipoDePublicacionExistente e) {
					// TODO Auto-generated catch block
		            fail("No deberia lanzar excepcion");
				}
		        try {
					contOferta.ingresarTipoAPaquete(nombreP, "micasa", 1);
				} catch (NoExisteTipoPublicacion | NoExistePaquete e) {
					// TODO Auto-generated catch block
		            fail("No deberia lanzar excepcion");
				}
		       try {
				DTPaquete p=contOferta.getInfoPaquete(nombreP);
		       	assertEquals(nombreP,p.getNombre());
		       	assertEquals(descP,p.getDescripcion());
		       	assertEquals(validezP,p.getValidez());
		       	assertEquals(fechaAltaP,p.getFechaAlta());
		       	assertEquals(imgP,p.getImagen());
		       	Set<String> tipos = p.getTiposPub();
		       	assertEquals(tipos.contains("micasa"),true);

		       } catch (NoExistePaquete e) {
		    	   // TODO Auto-generated catch block
		    	   fail("No deberia lanzar excepcion");
		       }


	        }
	        @Test
	        void testObtenerOfertasValidas() {
	        	 String nombreTP = "minombreTP";
		            String descTP = "Esta cosa no sirve";
		            int expTP = 4;
		            int duracionTP = 10;
		            double costoTP = 300;
		        	Calendar cal = Calendar.getInstance();
			        cal.set(Calendar.YEAR, 1988);
			        cal.set(Calendar.MONTH, Calendar.JANUARY);
			        cal.set(Calendar.DAY_OF_MONTH, 1);
			        Date fechaAltaTP = cal.getTime();
			        try {
						contOferta.altaTipoDePublicacion(nombreTP, descTP, expTP, duracionTP, costoTP, fechaAltaTP);
					} catch (NombreTipoPublicacionExistente | NombreTipoDePublicacionExistente e) {
						// TODO Auto-generated catch block
			            fail("No deberia lanzar excepcion");
					}
			        String nickU = "googlee";
			        String nomU = "Google";
			        String surnameU = "Inc";
			        String mailU = "google@hotmails.com";
			        String descE = "Empresa de tecnologia";
			        String linkE = "www.google.com";
					String contra = "1234";
					String imagen = "Usuarios/U25.jpg"; // lo agregue pero no tengo ni idea, att lu. gracias lu <3
			        try {
			            contUsuario.darAltaEmpresa(nickU, nomU, surnameU, mailU, contra, imagen, descE, linkE);
			        }
			        catch(CorreoUsuarioExistente e) {
			            fail("No deberia lanzar excepcion");
			        }
			        catch(NicknameUsuarioExistente e) {
			            fail("No deberia lanzar excepcion");
			        }
			    String nomOferta = "miOferta";
	        	String desc = "notengo";
	        	String horario = "no";
	        	double remuneracion = 20;
	        	String ciudad = "montevideo";
	        	String depar = "montevideo";
	        	String imagenO = "Ofertas/O25.jpg";
		        try {
					contOferta.altaKeyword("holaa");
					contOferta.altaKeyword("note");

				} catch (NombreKeywordExistente e) {
					// TODO Auto-generated catch block
			    	   fail("No deberia lanzar excepcion");
				}

		        Set<String> key=null;
				try {
					key = contOferta.listarNombresKeywords();
				} catch (NoExisteKeyword e) {
					// TODO Auto-generated catch block
			    	   fail("No deberia lanzar excepcion");
				}
	        	try {
					contOferta.ingresarOferta(nickU, nombreTP, nomOferta, desc, horario, remuneracion, ciudad, depar, fechaAltaTP, key, imagenO);
	        	} catch (NombreOfertaExistente | NoExisteEmpresa | NoExisteTipoPublicacion | NoExisteKeyword e) {
					// TODO Auto-generated catch block
			    	   fail("No deberia lanzar excepcion");
				}
	        	try {
					DTOferta of =contOferta.obtenerOfertaPorNombre(nomOferta);
					assertEquals(of.getDepartamento(),depar);
					assertEquals(of.getCiudad(),ciudad);
					assertEquals(of.getNombre(),nomOferta);
					assertEquals(of.getDescripcion(),desc);
					assertEquals(of.getHorario(),horario);
					assertEquals(of.getRemuneracion(),remuneracion,0.001);
					assertEquals(of.getFechaAlta(),fechaAltaTP);
					assertEquals(of.getImagen(), imagenO);
				} catch (NoExisteOferta e) {
					// TODO Auto-generated catch block
			    	   fail("No deberia lanzar excepcion");
				}

	        }

	        @Test
	        //modificar oferta
	        void testmodificarOferta() {
	        	String empresa = "EcoTech";
	            String nombre = "Ingeniero de Calidad de Software";
	           // String nombre = "Desarrollador Frontend";
	        	String descripcion = "d";
	        	String ciudad = "c";
	        	String departamento = "dep";
	        	double remuneracion = 200;

	            DTOferta of = null;
	            try{
	            	of = contOferta.getDTOferta(nombre);
	            }catch(NoExisteOferta eee) {
	            	fail("No deberia saltar excepcion");
	            }
	            Date fechaAlta = of.getFechaAlta();
	            Estados estado = Estados.ingresada;
	            String horario = "tiesas";
	            String img = "imagen.jpg";
	            Set<String> keywords = new HashSet<>();
	            keywords.add("Remoto");
	            try {
	                contOferta.modificarOferta(empresa, nombre, descripcion, horario, remuneracion, ciudad, departamento, keywords, img);
	            } //catch (NoExisteEmpresa
	            //		| NoExisteOferta | NoExisteKeyword e) {
	              //  fail("No deberia lanzar excepcion");
	         //   }
	            catch (NoExisteEmpresa | NoExisteOferta | NoExisteKeyword e) {
	                fail("No deberia");
	            }

	            DTOferta dto = null;
	            try{
	            	dto = contOferta.getDTOferta(nombre);
	            }catch(NoExisteOferta e) {
	            	fail("No deberia lanzar excepcion");
	            }

	                assertEquals(nombre, dto.getNombre());
	                assertEquals(empresa, dto.getNickEmpresa());
	                assertEquals(fechaAlta, dto.getFechaAlta());
	                assertEquals(ciudad, dto.getCiudad());
	                assertEquals(departamento,dto.getDepartamento());
	                assertEquals(img,dto.getImagen());
	                assertEquals(estado,dto.getEstado());
	                assertEquals(remuneracion, dto.getRemuneracion(), 0.001);

	        }

	        @Test
	        void testPaqueteConImagen() {
	        	DTPaquete p = null;
	        	try{
	        		p = contOferta.getInfoPaquete("Express");
	        	}catch(NoExistePaquete e){
	        		fail("no debe tirar excepcion");
	        	}
	        	String nom = "Nuevo";
	        	double costo = p.getCosto();
	        	String descr = "Muy bueno";
	        	double descu = p.getDescuento();
	        	Date f = p.getFechaAlta();
	        	String img = "IMAGEN.jpg";
	        	int validez = p.getValidez();
	        	try {
	        		contOferta.crearPaqueteConImg(nom, descr, validez, descu, f, img);
	        	}catch(NombrePaqueteExistente eee) {
	        		fail("No deberia tirar excepcion");
	        	}
	        	DTPaquete nuevo = null;
	        	try {
	        		nuevo = contOferta.getInfoPaqueteConImg(nom);
	        	}catch(NoExistePaquete eee) {
	        		fail("No deberia tirar excepcion");
	        	}
	        	assertEquals(img,nuevo.getImagen());


	        }

	        @Test
	        void testListados() {
	        	// listar empresas
	        	Set<String> emp = new HashSet<>();
	        	try{
	        		emp = contOferta.listarEmpresas();
	        	}catch(NoExistenEmpresas e) {
	        		fail("no deberia pasar");
	        	}
	        	assertEquals(true,emp.contains("EcoTech"));
	        	assertEquals(true,emp.contains("GlobalHealth"));

	        	Set<String> ofertas = new HashSet<>();
	        	try {
	        		ofertas = contOferta.listarOfertasDeEmpresa("GlobalHealth");
	        	}catch(NoExisteOfertaEmpresa | NoExisteEmpresa eee) {
	        		fail("No deberia pasar");
	        	}
	        	assertEquals(true,ofertas.contains("Estratega de Negocios"));

	        }

	        @Test
	        void testGetKeywords() {
	        	Set<String> keys = contOferta.getKeywords();
	        	assertEquals(true,keys.contains("Remoto"));
	        }

	        @Test
	        void testGetDTPostulacion() {
	        	String nick = "lgarcia";
	        	String nomOferta = "Estratega de Negocios";
	        	DTPostulacion dtp = null;
	        	try {
	        		dtp = contOferta.getDTPostulacion(nick, nomOferta);
	        		
	        	}catch(NoExisteOferta eee) {
	        		fail("No deberia suceder");
	        	}
	        	String CVR = "Licenciada en Administración, me considero genia, experiencia en gestión de equipos y proyectos. Conocimientos en Microsoft Office.";
	        	String motivacion = "Estoy emocionada por la oportunidad de formar parte de un equipo dinámico y contribuir con mis habilidades de liderazgo.";
	        	DTOferta ofDePostulacion = dtp.getMiOferta();

	        	assertEquals(dtp.getCVR(),CVR);
	        	assertEquals(dtp.getMotivacion(),motivacion);
	        	assertEquals(nomOferta,ofDePostulacion.getNombre());
	        }

	        @Test
	    	public void getOfertasVigentesOrdenadas(){
	    		Set<DTOferta> ofertas = contOferta.getOfertasVigentesOrdenadas();

	    		String[] resEsp = {
	    			    "A. de Marketing Digital",
	    			    "Técnico/a Básico Red",
	    			    "Gerente de Proyecto",
	    			    "Diseñador UX/UI",
	    			};

	    		int cont = 0;
	    		for(DTOferta ofe:ofertas) {
	    			assertEquals(ofe.getNombre(),resEsp[cont]);
	    			cont++;
	    		}
	    	}
	        @Test
	    	public void testComprarPaquete(){


	        	String nickEmpr = "EcoTech";
	        	String paqValido = "Destacado";
	        	String paqInvalido = "Premium";

	        	try {
					contOferta.comprarPaquete(paqValido, nickEmpr);
				} catch (NoExisteEmpresa | NoExistePaquete | PaqueteYaComprado e) {
					// TODO Auto-generated catch block
	        		fail("No deberia suceder");
	        	}

	        	try {
					Set<DTCompra> compras = contUsuario.getDTComprasEmpresa(nickEmpr);
					for(DTCompra com :compras) {
						if("Premium".equals(com.getDTPaquete().getNombre())) {
			    			assertEquals("Premium",com.getDTPaquete().getNombre());
						}
					}
				} catch (NoExisteEmpresa e) {
					// TODO Auto-generated catch block
	        		fail("No deberia suceder");
				}
	            assertThrows(PaqueteYaComprado.class, ()->{contOferta.comprarPaquete(paqInvalido, nickEmpr);});
	    	}


	        @Test
	        void testFuePagadaConPaquete() {

	        	try {
					boolean ofe = contOferta.fuePagadaConPaquete("Desarrollador de Software Senior");
		        	assertEquals(ofe,true);

				} catch (NoExistePaquete | NoExisteOferta e) {
					// TODO Auto-generated catch block
	        		fail("No deberia suceder");
				}
	        }


	        @Test
	        void testListarPaquetesLibres() {
	        	Calendar cal = Calendar.getInstance();
		        cal.set(Calendar.YEAR, 1988);
		        cal.set(Calendar.MONTH, Calendar.JANUARY);
		        cal.set(Calendar.DAY_OF_MONTH, 1);
		        Date fechaAltaP = cal.getTime();

	        	try {
					contOferta.crearPaquete("miPaq","no tengo", 100, 20,fechaAltaP,"hola");
				} catch (NombrePaqueteExistente e) {
					// TODO Auto-generated catch block
	        		fail("No deberia suceder");
				}
	        	Set<String> paquetes = contOferta.listarPaquetesLibres();
	        	assertTrue(paquetes.contains("miPaq"));
	        	assertTrue(!paquetes.contains("Premium"));

	        }
	        @Test
	        void testGetCantPorTP() {
	        	try {
					int cantidad = contOferta.getCantPorTP("Premium","Premium");
		        	assertEquals(cantidad,2);

				} catch (NoExistePaquete e) {
					// TODO Auto-generated catch block
	        		fail("No deberia suceder");
				}

	        }
	        @Test
	        void testGetOfertasVigentes() {
	        	 Map<String, DTOferta> ofertasV = contOferta.getOfertasVigentes();
	        	 System.out.print(ofertasV);
	        	 assertFalse(ofertasV.containsKey("Desarrollador Frontend"));
	        	 assertFalse(ofertasV.containsKey("Estratega de Negocios"));
	        	 assertTrue(ofertasV.containsKey("Diseñador UX/UI"));
	        	 assertTrue(ofertasV.containsKey("A. de Marketing Digital"));
	        	 assertTrue(ofertasV.containsKey("Técnico/a Básico Red"));
	        	 assertTrue(ofertasV.containsKey("Gerente de Proyecto"));
	        	 assertFalse(ofertasV.containsKey("Soporte Técnico"));
	        	 assertFalse(ofertasV.containsKey("Contador Senior"));
	        }

	        @Test
	        void testOrden() {
	        	try {
	        		Date fecha = new Date();
					Set<DTOferta> ofsMV = contOferta.getTOPofertas();
					for(DTOferta ofe: ofsMV) {
		        		if(!contOferta.vigenciaOferta(ofe.getNombre())) {
		        			try {
								contOferta.asignarOrdenPostulacion("lgarcia", "Estratega de Negocios", 2, fecha);
							} catch (NoExisteOfertaEmpresa e) {
								fail("jasdf");
							} catch (NoExistePostulante e) {
								// TODO Auto-generated catch block
								fail("kjsjdn");
							}
		        		}
		        	}
				} catch (NoExisteOferta e) {
					fail("no");
				}


	        }

}
