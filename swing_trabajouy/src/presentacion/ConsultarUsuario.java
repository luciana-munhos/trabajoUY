package presentacion;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import excepciones.NoExisteEmpresa;
import excepciones.NoExistePostulante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import logica.DTEmpresa;
import logica.DTOferta;
import logica.DTPostulante;
import logica.DTUsuario;
import logica.Fabrica;
import logica.IOferta;
import logica.IUsuario;
import logicaDAO.EmpresaDAO;
import logicaDAO.OfertaDAO;
import logicaDAO.PostulacionDAO;
import logicaDAO.PostulanteDAO;

public class ConsultarUsuario extends JInternalFrame {
	private IUsuario contU;
	private JTextField textFieldNombre;
	private JTextField textFieldApellido;
	private JTextField textFieldCorreo;
	private JTextField textFieldLink;
	private JTextArea textAreaDescripcion;
	private JComboBox<String> comboBoxUsuario;
	private JComboBox<String> comboBoxOfertas;
	private JTextField textFieldFechaNac;
	private JTextField textFieldNacionalidad;
	private JScrollPane scrollPane;
	private JLabel lblDescripcion;
	private JLabel lblLink;
	private JLabel lblOfertas;
	private JLabel lblFechaNac;
	private JLabel lblNacionalidad;
	private GridBagConstraints gbc_lblDescripcion;
	private GridBagConstraints gbc_lblLink;
	private GridBagConstraints gbc_lblOfertas;
	private GridBagConstraints gbc_lblFechaNac;
	private GridBagConstraints gbc_lblNacionalidad;
	private GridBagConstraints gbc_textAreaDescripcion;
	private GridBagConstraints gbc_textFieldFechaNac;
	private GridBagConstraints gbc_textFieldNacionalidad;
	private GridBagConstraints gbc_textFieldLink;
	private GridBagConstraints gbc_comboBoxOfertas;
	private GridBagLayout gridBagLayout;
	private DTOfertaComple ocIntFrame;
	private JTextField textImagen;
	private Imagen imaIntFrame;
	private String urlImagen;
	private JButton btnVerImagen;
	private JButton btnPaquetes;
	private JLabel lblPaquetes;

	/**
	 * Create the frame
	 */
	public ConsultarUsuario(IUsuario iu, DTOfertaComple oc, Imagen im,Compras comprasIntFrame) {
		ocIntFrame = oc;
		imaIntFrame = im;
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		contU = Fabrica.getIUsuario();
		setBounds(100, 100, 545, 446);



		agregarComponentes();

		lblPaquetes = new JLabel("Paquetes");
		GridBagConstraints gbc_lblPaquetes = new GridBagConstraints();
		gbc_lblPaquetes.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblPaquetes.insets = new Insets(0, 0, 5, 0);
		gbc_lblPaquetes.gridx = 3;
		gbc_lblPaquetes.gridy = 9;
		getContentPane().add(lblPaquetes, gbc_lblPaquetes);
		lblPaquetes.setVisible(false);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 1;
		gbc_horizontalStrut.gridy = 10;
		getContentPane().add(horizontalStrut, gbc_horizontalStrut);
		getContentPane().add(comboBoxOfertas, gbc_comboBoxOfertas);
		getContentPane().add(lblOfertas, gbc_lblOfertas);
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				borrarFormulario();
				oc.setVisible(false);
				btnVerImagen.setEnabled(false);
				im.setVisible(false);
				comprasIntFrame.setVisible(false);
			}
		});

		btnPaquetes = new JButton("Ver Compras");
		btnPaquetes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comprasIntFrame.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnPaquetes = new GridBagConstraints();
		gbc_btnPaquetes.anchor = GridBagConstraints.WEST;
		gbc_btnPaquetes.insets = new Insets(0, 0, 5, 0);
		gbc_btnPaquetes.gridx = 3;
		gbc_btnPaquetes.gridy = 11;
		getContentPane().add(btnPaquetes, gbc_btnPaquetes);
		btnPaquetes.setVisible(false);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_1.gridx = 1;
		gbc_horizontalStrut_1.gridy = 12;
		getContentPane().add(horizontalStrut_1, gbc_horizontalStrut_1);
		GridBagConstraints gbc_btnCerrar = new GridBagConstraints();
		gbc_btnCerrar.anchor = GridBagConstraints.SOUTH;
		gbc_btnCerrar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCerrar.gridx = 1;
		gbc_btnCerrar.gridy = 13;
		getContentPane().add(btnCerrar, gbc_btnCerrar);

		comboBoxUsuario.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0){
				try {
					if(arg0.getStateChange()==ItemEvent.SELECTED) {
						comprasIntFrame.setVisible(false);
						if(comboBoxOfertas.getSelectedItem() != null) {
							comboBoxOfertas.removeAllItems();
							comboBoxOfertas.setSelectedIndex(-1);
						}
						if(comboBoxUsuario.getSelectedItem()!=null) {
							comboBoxOfertas.setEnabled(true);

							getContentPane().add(comboBoxOfertas, gbc_comboBoxOfertas);
							getContentPane().add(lblOfertas, gbc_lblOfertas);

							String nickname = comboBoxUsuario.getSelectedItem().toString();
							DTUsuario info = contU.mostrarDatosUsuario(nickname);

							btnVerImagen.setEnabled(true);
							urlImagen = info.getImagen();
							Set<DTOferta> ofertas = new HashSet<>();

							EntityManagerFactory emf = Persistence.createEntityManagerFactory("trabajouy");
					 		EntityManager entm = emf.createEntityManager();
							if(info instanceof DTEmpresa) {

						 		TypedQuery<EmpresaDAO> query = entm.createQuery("SELECT e FROM EmpresaDAO e WHERE e.nickname = :nickname", EmpresaDAO.class);
						 		query.setParameter("nickname", nickname);
						 		List<EmpresaDAO> empresas = query.getResultList();

						 		if(!empresas.isEmpty()) {
						 			List<OfertaDAO> ofertasF = empresas.get(0).getOfertasFinalizadas();
						 			for(OfertaDAO ofe : ofertasF) {
						 				ofertas.add(ofe.getDTOferta());
						 			}
						        }
							}
							else {
						 		TypedQuery<PostulanteDAO> query2 = entm.createQuery("SELECT p FROM PostulanteDAO p WHERE p.nickname = :nickname", PostulanteDAO.class);
						 		query2.setParameter("nickname", nickname);
						 		List<PostulanteDAO> postulantes = query2.getResultList();
						 		if(!postulantes.isEmpty()) {//ENTRA SI LA OFERTA NO ESTA PERSISTIDA
						        	Set<PostulacionDAO> postu = postulantes.get(0).getPostulaciones();
						        	for(PostulacionDAO pos : postu) {
						        		ofertas.add(pos.getIdOferta().getDTOferta());
						        	}
						        }
							}
							try {
								ofertas.addAll(contU.mostrarInfoBasicaOfertas(nickname));
							}
							catch(Exception e) {
								e.printStackTrace();
							}
							entm.close();
							emf.close();

							textFieldNombre.setText(info.getNombre());
							textFieldApellido.setText(info.getApellido());
							textFieldCorreo.setText(info.getCorreo());
							textImagen.setText(info.getImagen());

							if(info instanceof DTEmpresa){
								textFieldLink.setText(((DTEmpresa) info).getLink());
								textAreaDescripcion.setText(((DTEmpresa) info).getDescripcion());

								IUsuario IU = Fabrica.getIUsuario();

								btnPaquetes.setVisible(true);
								lblPaquetes.setVisible(true);

								try {
									if(comprasIntFrame.cargarDatos(IU.getDTComprasEmpresa(comboBoxUsuario.getSelectedItem().toString()))) {
										btnPaquetes.setEnabled(true);
									}else {
										btnPaquetes.setEnabled(false);
									}

								} catch (NoExisteEmpresa e1) {
									e1.printStackTrace();
								}




								gridBagLayout.rowHeights = new int[]{56, 22, 0, 32, 30, 31, 70, 0, 26, 0, 0, 0, 0};

								getContentPane().remove(lblFechaNac);
								getContentPane().remove(textFieldFechaNac);
								getContentPane().remove(lblNacionalidad);
								getContentPane().remove(textFieldNacionalidad);

								getContentPane().add(lblDescripcion, gbc_lblDescripcion);
								getContentPane().add(lblLink,gbc_lblLink);
								getContentPane().add(scrollPane,gbc_textAreaDescripcion);
								getContentPane().add(textFieldLink,gbc_textFieldLink);
							}
							else{
								textFieldNacionalidad.setText(((DTPostulante) info).getNacionalidad());
								textFieldFechaNac.setText(((DTPostulante) info).getFechaNac().toString());

								gridBagLayout.rowHeights = new int[]{56, 22, 0, 32, 30, 31, 29, 0, 26, 0, 0, 0, 0};

								btnPaquetes.setVisible(false);
								lblPaquetes.setVisible(false);

								getContentPane().remove(lblDescripcion);
								getContentPane().remove(lblLink);
								getContentPane().remove(scrollPane);
								getContentPane().remove(textFieldLink);

								getContentPane().add(lblFechaNac,gbc_lblFechaNac);
								getContentPane().add(textFieldFechaNac,gbc_textFieldFechaNac);
								getContentPane().add(lblNacionalidad,gbc_lblNacionalidad);
								getContentPane().add(textFieldNacionalidad,gbc_textFieldNacionalidad);
							}
							Set<String> nombreOfertas = new HashSet<>();
							//get nombre from ofertas

							for(DTOferta o : ofertas) {
								nombreOfertas.add(o.getNombre());
							}
							//delete all items in comboboxofertas
							comboBoxOfertas.removeAllItems();

							for(String o : nombreOfertas)
								comboBoxOfertas.addItem(o);

							revalidate();
							repaint();
							comboBoxOfertas.setSelectedIndex(-1);
							ocIntFrame.setVisible(false);
							imaIntFrame.setVisible(false);

							if(comboBoxUsuario.getSelectedItem() == null) {
								btnPaquetes.setVisible(false);
								lblPaquetes.setVisible(false);
							}
						}
						else {
							btnVerImagen.setEnabled(false);

						}
					}
				}catch(NoExisteEmpresa  e1) {
					e1.printStackTrace();
				}catch(NoExistePostulante e2) {
					e2.printStackTrace();
				}
			}
		});

		comboBoxOfertas.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange()==ItemEvent.SELECTED) { //Si selecciono una opcion permito obtener ofertas
					if(comboBoxOfertas.getSelectedItem() != null){

						String nombreOferta = comboBoxOfertas.getSelectedItem().toString();
						IOferta Iof = Fabrica.getIOferta();
						try{
							 //SI LA OFERTA ESTA EN LA BASE DE DATOS NO LA PONE EN MISPAQUETES DE EMPRESA
					 		EntityManagerFactory emf = Persistence.createEntityManagerFactory("trabajouy");
					 		EntityManager entm = emf.createEntityManager();
					 		TypedQuery<OfertaDAO> query = entm.createQuery("SELECT o FROM OfertaDAO o WHERE o.nombre = :nombre", OfertaDAO.class);
					 		query.setParameter("nombre", nombreOferta);
					 		List<OfertaDAO> resultados = query.getResultList();
					 		DTOferta infoOferta;
					        if(resultados.isEmpty()) {//ENTRA SI LA OFERTA NO ESTA PERSISTIDA
					        	infoOferta = Iof.obtenerOfertaPorNombre(nombreOferta);
					        }
					    	else {
							     infoOferta = resultados.get(0).getDTOferta(); // Obtiene el elemento único
							}
					        entm.close();
					        emf.close();
							ocIntFrame.cargarDatos(infoOferta);
							ocIntFrame.setVisible(true);
						}
						catch(Exception ex){
							ex.printStackTrace();
						}

					}
				}
			}
		});
	}

	private void borrarFormulario() {
		comboBoxOfertas.removeAllItems();
		textFieldNombre.setText("");
		textFieldApellido.setText("");
		textAreaDescripcion.setText("");
		textFieldCorreo.setText("");
		textFieldNacionalidad.setText("");
		textFieldFechaNac.setText("");
		textImagen.setText("");

		getContentPane().remove(lblDescripcion);
		getContentPane().remove(lblLink);
		getContentPane().remove(scrollPane);
		getContentPane().remove(textFieldLink);

		getContentPane().remove(lblFechaNac);
		getContentPane().remove(textFieldFechaNac);
		getContentPane().remove(lblNacionalidad);
		getContentPane().remove(textFieldNacionalidad);

		revalidate();
		repaint();

		comboBoxOfertas.setSelectedIndex(-1);
		comboBoxUsuario.setSelectedIndex(-1);

		btnPaquetes.setVisible(false);
		lblPaquetes.setVisible(false);
	}

	private void agregarComponentes() {
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{113, 231, 69, 123, 0};
		gridBagLayout.rowHeights = new int[]{56, 22, 0, 32, 30, 31, 29, 0, 26, 52, 0, 19, 0, 33, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblTitle = new JLabel("Consulta de Usuario");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 29));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.gridwidth = 4;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		getContentPane().add(lblTitle, gbc_lblTitle);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 0;
		gbc_lblUsuario.gridy = 1;
		getContentPane().add(lblUsuario, gbc_lblUsuario);

		comboBoxUsuario = new JComboBox<>();
		GridBagConstraints gbc_comboBoxUsuario = new GridBagConstraints();
		gbc_comboBoxUsuario.gridwidth = 2;
		gbc_comboBoxUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxUsuario.gridx = 1;
		gbc_comboBoxUsuario.gridy = 1;
		getContentPane().add(comboBoxUsuario, gbc_comboBoxUsuario);

		JLabel lblImagen = new JLabel("Imagen:");
		GridBagConstraints gbc_lblImagen = new GridBagConstraints();
		gbc_lblImagen.anchor = GridBagConstraints.EAST;
		gbc_lblImagen.insets = new Insets(0, 0, 5, 5);
		gbc_lblImagen.gridx = 0;
		gbc_lblImagen.gridy = 2;
		getContentPane().add(lblImagen, gbc_lblImagen);

		textImagen = new JTextField();
		textImagen.setEditable(false);
		GridBagConstraints gbc_textImagen = new GridBagConstraints();
		gbc_textImagen.gridwidth = 2;
		gbc_textImagen.insets = new Insets(0, 0, 5, 5);
		gbc_textImagen.fill = GridBagConstraints.HORIZONTAL;
		gbc_textImagen.gridx = 1;
		gbc_textImagen.gridy = 2;
		getContentPane().add(textImagen, gbc_textImagen);
		textImagen.setColumns(10);

		btnVerImagen = new JButton("Ver Imagen");
		btnVerImagen.setEnabled(false);
		btnVerImagen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!textImagen.getText().equals("")) {
					imaIntFrame.cargarImagen(urlImagen);
	                imaIntFrame.setVisible(true);
				}
			}
		});
		GridBagConstraints gbc_btnVerImagen = new GridBagConstraints();
		gbc_btnVerImagen.anchor = GridBagConstraints.WEST;
		gbc_btnVerImagen.insets = new Insets(0, 0, 5, 0);
		gbc_btnVerImagen.gridx = 3;
		gbc_btnVerImagen.gridy = 2;
		getContentPane().add(btnVerImagen, gbc_btnVerImagen);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 3;
		getContentPane().add(lblNombre, gbc_lblNombre);

		textFieldNombre = new JTextField();
		textFieldNombre.setEditable(false);
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.gridwidth = 2;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.gridx = 1;
		gbc_textFieldNombre.gridy = 3;
		getContentPane().add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);

		JLabel lblNewLabelApellido = new JLabel("Apellido:");
		lblNewLabelApellido.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNewLabelApellido = new GridBagConstraints();
		gbc_lblNewLabelApellido.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabelApellido.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabelApellido.gridx = 0;
		gbc_lblNewLabelApellido.gridy = 4;
		getContentPane().add(lblNewLabelApellido, gbc_lblNewLabelApellido);

		textFieldApellido = new JTextField();
		textFieldApellido.setEditable(false);
		GridBagConstraints gbc_textFieldApellido = new GridBagConstraints();
		gbc_textFieldApellido.gridwidth = 2;
		gbc_textFieldApellido.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldApellido.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldApellido.gridx = 1;
		gbc_textFieldApellido.gridy = 4;
		getContentPane().add(textFieldApellido, gbc_textFieldApellido);
		textFieldApellido.setColumns(10);

		JLabel lblNewLabelCorreo = new JLabel("Correo:");
		lblNewLabelCorreo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNewLabelCorreo = new GridBagConstraints();
		gbc_lblNewLabelCorreo.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabelCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabelCorreo.gridx = 0;
		gbc_lblNewLabelCorreo.gridy = 5;
		getContentPane().add(lblNewLabelCorreo, gbc_lblNewLabelCorreo);

		textFieldCorreo = new JTextField();
		textFieldCorreo.setEditable(false);
		GridBagConstraints gbc_textFieldCorreo = new GridBagConstraints();
		gbc_textFieldCorreo.gridwidth = 2;
		gbc_textFieldCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCorreo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCorreo.gridx = 1;
		gbc_textFieldCorreo.gridy = 5;
		getContentPane().add(textFieldCorreo, gbc_textFieldCorreo);
		textFieldCorreo.setColumns(10);

		lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setVerticalAlignment(SwingConstants.BOTTOM);
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 1;
		gbc_lblDescripcion.gridy = 6;
		//getContentPane().add(lblDescripcion, gbc_lblDescripcion);



		textAreaDescripcion = new JTextArea();
		textAreaDescripcion.setEditable(false);
		scrollPane = new JScrollPane(textAreaDescripcion,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		textAreaDescripcion.setCaretPosition(textAreaDescripcion.getDocument().getLength());
		textAreaDescripcion.setLineWrap(true);

		gbc_textAreaDescripcion = new GridBagConstraints();
		gbc_textAreaDescripcion.gridheight = 2;
		gbc_textAreaDescripcion.gridwidth = 2;
		gbc_textAreaDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_textAreaDescripcion.fill = GridBagConstraints.BOTH;
		gbc_textAreaDescripcion.gridx = 2;
		gbc_textAreaDescripcion.gridy = 6;
		//getContentPane().add(scrollPane, gbc_textAreaDescripcion);

		lblFechaNac = new JLabel("Fecha de Nacimiento");
		lblFechaNac.setFont(new Font("Tahoma", Font.PLAIN, 12));
		gbc_lblFechaNac = new GridBagConstraints();
		gbc_lblFechaNac.anchor = GridBagConstraints.EAST;
		gbc_lblFechaNac.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaNac.gridx = 1;
		gbc_lblFechaNac.gridy = 6;
		//getContentPane().add(lblFechaNac, gbc_lblFechaNac);

		textFieldFechaNac = new JTextField();
		textFieldFechaNac.setEditable(false);
		gbc_textFieldFechaNac = new GridBagConstraints();
		gbc_textFieldFechaNac.gridwidth = 2;
		gbc_textFieldFechaNac.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldFechaNac.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldFechaNac.gridx = 2;
		gbc_textFieldFechaNac.gridy = 6;
		//getContentPane().add(textFieldFechaNac, gbc_textFieldFechaNac);
		textFieldFechaNac.setColumns(10);

		lblNacionalidad = new JLabel("Nacionalidad:");
		lblNacionalidad.setFont(new Font("Tahoma", Font.PLAIN, 12));
		gbc_lblNacionalidad = new GridBagConstraints();
		gbc_lblNacionalidad.anchor = GridBagConstraints.EAST;
		gbc_lblNacionalidad.insets = new Insets(0, 0, 5, 5);
		gbc_lblNacionalidad.gridx = 1;
		gbc_lblNacionalidad.gridy = 7;
		//getContentPane().add(lblNacionalidad, gbc_lblNacionalidad);

		textFieldNacionalidad = new JTextField();
		textFieldNacionalidad.setEditable(false);
		gbc_textFieldNacionalidad = new GridBagConstraints();
		gbc_textFieldNacionalidad.gridwidth = 2;
		gbc_textFieldNacionalidad.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNacionalidad.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNacionalidad.gridx = 2;
		gbc_textFieldNacionalidad.gridy = 7;
		//getContentPane().add(textFieldNacionalidad, gbc_textFieldNacionalidad);
		textFieldNacionalidad.setColumns(10);

		lblLink = new JLabel("Link:");
		lblLink.setFont(new Font("Tahoma", Font.PLAIN, 12));
		gbc_lblLink = new GridBagConstraints();
		gbc_lblLink.anchor = GridBagConstraints.EAST;
		gbc_lblLink.insets = new Insets(0, 0, 5, 5);
		gbc_lblLink.gridx = 1;
		gbc_lblLink.gridy = 8;
		//getContentPane().add(lblLink, gbc_lblLink);

		textFieldLink = new JTextField();
		textFieldLink.setEditable(false);
		gbc_textFieldLink = new GridBagConstraints();
		gbc_textFieldLink.gridwidth = 2;
		gbc_textFieldLink.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldLink.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLink.gridx = 2;
		gbc_textFieldLink.gridy = 8;
		//getContentPane().add(textFieldLink, gbc_textFieldLink);
		textFieldLink.setColumns(10);

		lblOfertas = new JLabel("Ofertas asociadas");
		gbc_lblOfertas = new GridBagConstraints();
		gbc_lblOfertas.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblOfertas.insets = new Insets(0, 0, 5, 5);
		gbc_lblOfertas.gridx = 1;
		gbc_lblOfertas.gridy = 9;
		//getContentPane().add(lblOfertas, gbc_lblOfertas);

		comboBoxOfertas = new JComboBox<>();
		comboBoxOfertas.setEnabled(false);
		gbc_comboBoxOfertas = new GridBagConstraints();
		gbc_comboBoxOfertas.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxOfertas.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxOfertas.gridx = 1;
		gbc_comboBoxOfertas.gridy = 11;
		//getContentPane().add(comboBoxOfertas, gbc_comboBoxOfertas);
	}

	public void abrir() {
		comboBoxUsuario.removeAllItems();
		Set<String> usuarios = contU.listarNicknamesUsuarios();
		if(usuarios.isEmpty())
			JOptionPane.showMessageDialog(null, "No hay usuarios registrados", "Error", JOptionPane.ERROR_MESSAGE);
		else{
			comboBoxUsuario.setSelectedIndex(-1);
			for(String u : usuarios)
				comboBoxUsuario.addItem(u);

			borrarFormulario();
			comboBoxUsuario.setSelectedIndex(-1);
			setVisible(true);
			ocIntFrame.setVisible(false);
		}
	}
}
