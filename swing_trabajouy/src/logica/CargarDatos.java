package logica;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import excepciones.NoExisteOferta;
import excepciones.NoExistePostulante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import logica.Oferta.Estados;
import logicaDAO.OfertaDAO;

public class CargarDatos {
	IUsuario IUsr = Fabrica.getIUsuario();
	IOferta IOf = Fabrica.getIOferta();

	Map<String,Usuario> refUsuarios = new HashMap<>();



	public void carga() {
		Map<String,String> usuarios = new HashMap<>();
		Map<String,String> usuariosP = new HashMap<>();
		Map<String,String> usuariosE = new HashMap<>();
		Map<String,String> keywords = new HashMap<>();
		Map<String,String> ofertas_keywords = new HashMap<>();
		Map<String,String> tiposPublicacion = new HashMap<>();
		Map<String,String> paquetes = new HashMap<>();
		Map<String,String> ofertas = new HashMap<>();
		Set<String> ofertasFinalizadas = new HashSet<>();
		//cargarMisDatitosLu();

		try{
			//File file = new File("data/Usuarios.csv");
			//FileReader isr = new FileReader(file);
			InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("/csv/Usuarios.csv"),"UTF-8");
			BufferedReader buf = new BufferedReader(isr);

			String linea = buf.readLine(); //saco el header
			while((linea = buf.readLine())!=null){
				//Ref;Tipo;Nickname;Nombre;Apellido;Email
				String[] datos = linea.split(";");
				usuarios.put(datos[0], linea);
			}
			//fr.close();
			isr.close();
			buf.close();

			isr = new InputStreamReader(getClass().getResourceAsStream("/csv/Usuarios-Postulantes.csv"),"UTF-8");
			buf = new BufferedReader(isr);
			/*file = new File("data/Usuarios-Postulantes.csv");
			isr = new FileReader(file);
			buf = new BufferedReader(isr);*/


			linea = buf.readLine(); //saco el header
			while((linea = buf.readLine())!=null){
				//Ref;FechaNacimiento;Nacionalidad
				String[] datos = linea.split(";");
				usuariosP.put(datos[0], linea);
			}
			//fr.close();
			isr.close();
			buf.close();

			isr = new InputStreamReader(getClass().getResourceAsStream("/csv/Usuarios-Empresas.csv"),"UTF-8");
			buf = new BufferedReader(isr);

			/*file = new File("data/Usuarios-Empresas.csv");
			isr = new FileReader(file);
			buf = new BufferedReader(isr);*/

			linea = buf.readLine(); //saco el header
			while((linea = buf.readLine())!=null){
				//Ref;Descripcion;Web
				String[] datos = linea.split(";");
				usuariosE.put(datos[0], linea);
			}
			//fr.close();
			isr.close();
			buf.close();

			//create usuarios
			try{
				for (Map.Entry<String, String> entry : usuarios.entrySet()) {
					String[] datos = entry.getValue().split(";");
					String img = "media/img/Usuarios/"+datos[0]+".jpg";
					if(datos[1].equals("P")){
						//Ref;Tipo;Nickname;Nombre;Apellido;Email
						//Ref;FechaNacimiento;Nacionalidad
						String[] datosP = usuariosP.get(datos[0]).split(";");
						//transform date from dd/mm/yyyy to yyyy-mm-dd
						String[] fechaS = datosP[1].split("/");
						String fechaN = fechaS[2]+"-"+fechaS[1]+"-"+fechaS[0];
						Date fecha = Date.valueOf(fechaN);

						IUsr.darAltaPostulante(datos[2], datos[3], datos[4], datos[5], datos[6],img,fecha, datosP[2]);

					}
					else{
						//Ref;Tipo;Nickname;Nombre;Apellido;Email
						//Ref;Descripcion;Web
						String[] datosE = usuariosE.get(datos[0]).split(";");
						IUsr.darAltaEmpresa(datos[2], datos[3], datos[4], datos[5], datos[6], img, datosE[1], datosE[2]);

					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}

			//seguidores
			try {
				isr = new InputStreamReader(getClass().getResourceAsStream("/csv/Usuarios-Seguidores.csv"));
				buf = new BufferedReader(isr);
				linea = buf.readLine(); //saco el header
				while((linea = buf.readLine())!=null){
					//Ref;RefSeguidor;RefSeguido
					String[] datos = linea.split(";");
					IUsr.seguirUsuario(usuarios.get(datos[1]).split(";")[2], usuarios.get(datos[2]).split(";")[2]);

				}
			}catch(Exception e){
				e.printStackTrace();

			}

			//create keywords
			try{
				isr = new InputStreamReader(getClass().getResourceAsStream("/csv/Keywords.csv"),"UTF-8");
				buf = new BufferedReader(isr);
				/*file = new File("data/Keywords.csv");
				isr = new FileReader(file);
				buf = new BufferedReader(isr);*/

				linea = buf.readLine(); //saco el header
				while((linea = buf.readLine())!=null){
					//Ref;Palabra
					String[] datos = linea.split(";");
					//delete spaces and altaKeyword
					datos[1] = datos[1].replaceAll("\\s+","");
					IOf.altaKeyword(datos[1]);
					keywords.put(datos[0], datos[1]);
				}
				//fr.close();
				isr.close();
				buf.close();


			}
			catch(Exception e){
				e.printStackTrace();
			}

			//TipoPublicacion.csv
			try{
				isr = new InputStreamReader(getClass().getResourceAsStream("/csv/TipoPublicacion.csv"),"UTF-8");
				buf = new BufferedReader(isr);

				/*file = new File("data/TipoPublicacion.csv");
				isr = new FileReader(file);
				buf = new BufferedReader(isr);*/

				linea = buf.readLine(); //saco el header
				while((linea = buf.readLine())!=null){
					//Ref;Nombre;Descripcion;Exposicion;Duracion;Costo;FechaAlta
					String[] datos = linea.split(";");
					//transform date from dd/mm/yyyy to yyyy-mm-dd
					String[] fechaS = datos[6].split("/");
					String fechaN = fechaS[2]+"-"+fechaS[1]+"-"+fechaS[0];
					Date fecha = Date.valueOf(fechaN);
					IOf.altaTipoDePublicacion(datos[1],datos[2], Integer.parseInt(datos[3]), Integer.parseInt(datos[4]), Double.parseDouble(datos[5]), fecha);
					tiposPublicacion.put(datos[0], datos[1]);
				}
				//fr.close();
				buf.close();
				isr.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}

			//OfertasLaboralesKeywords.csv
			//each oferta has a list of keywords (O1;K1, K2,...)
			try{
				InputStreamReader isr2 = new InputStreamReader(getClass().getResourceAsStream("/csv/OfertasLaboralesKeywords.csv"),"UTF-8");
				BufferedReader buf2 = new BufferedReader(isr2);
				/*File file2 = new File("data/OfertasLaboralesKeywords.csv");
				FileReader isr2 = new FileReader(file2);
				BufferedReader buf2 = new BufferedReader(isr2);*/


				String linea2 = buf2.readLine(); //saco el header
				//Oferta;Keyword
				while((linea2 = buf2.readLine())!=null){
					String[] datos = linea2.split(";");
					ofertas_keywords.put(datos[0], datos[1]);

				}
				buf2.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}

			//Paquetes.csv
			try{

				/*file = new File("data/Paquetes.csv");
				isr = new FileReader(file);
				buf = new BufferedReader(isr);*/

				isr = new InputStreamReader(getClass().getResourceAsStream("/csv/Paquetes.csv"),"UTF-8");
				buf = new BufferedReader(isr);

				linea = buf.readLine(); //saco el header
				while((linea = buf.readLine())!=null){
					//Ref;Nombre;Descripcion;Período;Descuento;Fecha;Costo;Ímagen
					String[] datos = linea.split(";");
					//transform date from dd/mm/yyyy to yyyy-mm-dd
					String[] fechaS = (datos[5]).split("/");
					String fechaN = fechaS[2]+"-"+fechaS[1]+"-"+fechaS[0];
					Date fecha = Date.valueOf(fechaN);
					//validez x días
					//remove everything but numbers
					int validez = Integer.parseInt(datos[3].replaceAll("[^0-9]", ""));
					String img = "media/img/Paquetes/"+datos[0]+".jpg";
					//get url img from shortener, entering the short url

					IOf.crearPaquete(datos[1],datos[2], validez, Double.parseDouble(datos[4]), fecha, img);
					paquetes.put(datos[0], datos[1]);
				}
				//fr.close();
				isr.close();
				buf.close();

				/*file = new File("data/PaquetesCompras.csv");
				isr = new FileReader(file);
				buf = new BufferedReader(isr);*/
			}
			catch(Exception e){
				e.printStackTrace();
			}

			//TiposPublicacionPaquetes.csv

			try{
				/*File file2 = new File("data/TiposPublicacionPaquetes.csv");
				FileReader isr2 = new FileReader(file2);
				BufferedReader buf2 = new BufferedReader(isr2);*/

				InputStreamReader isr2 = new InputStreamReader(getClass().getResourceAsStream("/csv/TiposPublicacionPaquetes.csv"),"UTF-8");
				BufferedReader buf2 = new BufferedReader(isr2);

				String linea2 = buf2.readLine(); //saco el header
				//Ref;Paquete;Tipos;Cantidad
				while((linea2 = buf2.readLine())!=null){
					String[] datos = linea2.split(";");
					//add paquete
					String paquete = paquetes.get(datos[1]);
					String tipo = tiposPublicacion.get(datos[2]);
					int cantidad = Integer.parseInt(datos[3]);
					IOf.ingresarTipoAPaquete(paquete, tipo, cantidad);

				}
				buf2.close();
				isr2.close();

				isr = new InputStreamReader(getClass().getResourceAsStream("/csv/PaquetesCompras.csv"),"UTF-8");
				buf = new BufferedReader(isr);

				linea = buf.readLine(); //saco el header
				while((linea = buf.readLine())!=null){
					//Ref;Usuario;Paquete;Fecha;Valor
					String[] datos = linea.split(";");
					//add paquete
					String empresa = usuarios.get(datos[1]).split(";")[2];
					String paquete = paquetes.get(datos[2]);
					//transform date from dd/mm/yyyy to yyyy-mm-dd
					String[] fechaS = datos[3].split("/");
					String fechaN = fechaS[2]+"-"+fechaS[1]+"-"+fechaS[0];
					Date fecha = Date.valueOf(fechaN);
					IUsr.ingresarPaqueteAEmpresa(paquete, empresa, fecha);

				}

			}
			catch(Exception e){
				e.printStackTrace();
			}

			//OfertasLaborales.csv
			try{

				/*file = new File("data/OfertasLaborales.csv");
				isr = new FileReader(file);
				buf = new BufferedReader(isr);*/
				isr = new InputStreamReader(getClass().getResourceAsStream("/csv/OfertasLaborales.csv"),"UTF-8");
				buf = new BufferedReader(isr);

				linea = buf.readLine(); //saco el header
				while((linea = buf.readLine())!=null){
					//Ref;Nombre;Descripcion;Departamento;Ciudad;Horario;Remuneracion;Usuario;TipoPublicacion;FechaAlta;Estado;CompraPaquete;Imagen;Visitas

					String[] datos = linea.split(";");
					//transform date from dd/mm/yyyy to yyyy-mm-dd
					String[] fechaS = datos[9].split("/");
					String fechaN = fechaS[2]+"-"+fechaS[1]+"-"+fechaS[0];
					Date fecha = Date.valueOf(fechaN);
					//create oferta
					Set<String> keyw = new HashSet<>();
					Set<String> keywordsOf = new HashSet<>();
					//add keywords
					if(ofertas_keywords.get(datos[0])!=null){
						keyw.add(ofertas_keywords.get(datos[0]));
						//delete spaces and split
						String[] keywS = ofertas_keywords.get(datos[0]).replaceAll("\\s+","").split(",");

						for(String k : keywS){
							if(k!=null)
								keywordsOf.add(keywords.get(k));
						}
					}

					String empresa = usuarios.get(datos[7]).split(";")[2];
					String nombreTP = tiposPublicacion.get(datos[8]);
					String nomOferta = datos[1];
					String descripcion = datos[2];
					String horario = datos[5];
					int remuneracion = Integer.parseInt(datos[6]);
					String ciudad = datos[4];
					String depart = datos[3];
					String est = datos[10];
					//change first letter to lowercase
					est = est.substring(0, 1).toLowerCase() + est.substring(1);
					Estados estado = Estados.valueOf(est);
					String imagen = "media/img/OfertasLaborales/"+datos[0]+".jpg";
					IOf.ingresarOferta(empresa,nombreTP,nomOferta,descripcion,horario,remuneracion,ciudad,depart,fecha,keywordsOf,imagen);


					EntityManagerFactory emf = Persistence.createEntityManagerFactory("trabajouy");
			 		EntityManager entm = emf.createEntityManager();
			 		TypedQuery<OfertaDAO> query = entm.createQuery("SELECT o FROM OfertaDAO o WHERE o.nombre = :nombre", OfertaDAO.class);
			 		query.setParameter("nombre", nomOferta);
			 		List<OfertaDAO> resultados = query.getResultList();

			        if(resultados.isEmpty()) {
			        	IOf.cambiarEstadoOferta(nomOferta,estado);
					}
			        else {
			        	IOf.cambiarEstadoOferta(nomOferta, Estados.finalizada);
			        }

					if(estado == Estados.finalizada){
						ofertasFinalizadas.add(nomOferta);
					}

					ofertas.put(datos[0], datos[1]);

					if(paquetes.get(datos[11])!=null)
						IOf.pagarConPaquete(nomOferta, paquetes.get(datos[11]), nombreTP, empresa);

					for(int i = 0;i<Integer.parseInt(datos[13]);i++)
						IOf.incrementarVisitas(nomOferta);

				}
				isr.close();
				buf.close();
				//fr.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}

			try {
				isr = new InputStreamReader(getClass().getResourceAsStream("/csv/PostulantesOfertasLaboralesFavoritas.csv"));
				buf = new BufferedReader(isr);

				linea = buf.readLine(); //saco el header

				while((linea = buf.readLine())!=null){
					//Postulante;Oferta

					String[] datos = linea.split(";");
					IUsr.agregarFavoritos(usuarios.get(datos[0]).split(";")[2], ofertas.get(datos[1]), true);
				}
				isr.close();
				buf.close();
			}catch(Exception e) {
				e.printStackTrace();
			}



			//Postulaciones.csv
			try{

				/*File file2 = new File("data/Postulaciones.csv");
				FileReader isr2 = new FileReader(file2);
				BufferedReader buf2 = new BufferedReader(isr2);*/

				InputStreamReader isr2 = new InputStreamReader(getClass().getResourceAsStream("/csv/Postulaciones.csv"),"UTF-8");
				BufferedReader buf2 = new BufferedReader(isr2);

				String linea2 = buf2.readLine(); //saco el header
				//Ref;Usuario;CV;Motivación;Fecha;Oferta;Video
				while((linea2 = buf2.readLine())!=null){
					String[] datos = linea2.split(";");
					//add paquete
					String usuario = usuarios.get(datos[1]).split(";")[2];
					String oferta = ofertas.get(datos[5]);
					//transform date from dd/mm/yyyy to yyyy-mm-dd
					String[] fechaS = datos[4].split("/");
					String fechaN = fechaS[2]+"-"+fechaS[1]+"-"+fechaS[0];
					Date fecha = Date.valueOf(fechaN);
					String video = datos[6];

					if(video.equals("Sin Video"))
						video = "";
					else
						video = (video.split("//"))[1].split("/")[2];
					try{
						IUsr.postularAOferta(usuario, datos[2], datos[3], fecha, video,oferta);

					}
					catch(Exception e){
						System.out.println("Error en postulacion: "+usuario+" "+oferta);
					}

				}
				buf2.close();
				isr2.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//SI LA OFERTA ESTA EN LA BASE DE DATOS NO LA VUELVO A PERSISTIR

		for(String ofertaF : ofertasFinalizadas) {
	 		EntityManagerFactory emf = Persistence.createEntityManagerFactory("trabajouy");
	 		EntityManager entm = emf.createEntityManager();
	 		TypedQuery<OfertaDAO> query = entm.createQuery("SELECT o FROM OfertaDAO o WHERE o.nombre = :nombre", OfertaDAO.class);
	 		query.setParameter("nombre", ofertaF);
	 		List<OfertaDAO> resultados = query.getResultList();

	        if(resultados.isEmpty()) {
				try {
					IUsr.guardarDatosOferta(ofertaF);
				} catch (NoExisteOferta | NoExistePostulante e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		}
	}
}
