package presentacion;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import excepciones.NoExistePostulante;
import excepciones.NoExisteTipoPublicacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import logica.DTOferta;
import logica.DTPaquete;
import logica.DTPostulacion;
import logica.DTTipoPublicacion;
import logica.IOferta;
import logica.ManejadorUsuario;
import logica.Oferta.Estados;
import logica.Postulante;
import logicaDAO.OfertaDAO;
import logicaDAO.PostulacionDAO;

public class DTOfertaComple extends JInternalFrame {
	private IOferta IOfr;
	private DTOferta miDT;

	/**
	 * Create the frame.
	 */

	private JLabel ciudad;
	private JTextArea textArea;
	private JLabel departamento;
	private JComboBox<String> comboPostulantes;
	private JLabel horario; //= new JLabel(miDT.getHorario().toString());
	private JLabel remuneracion;
	private JLabel fechaAlta;
	private JLabel fechaBaja;

	private JLabel lblNewLabel;

	private JScrollPane scrollPane;
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;
	private JLabel lblPostulantes;
	private JLabel lblPaquetes;
	private JButton btnCerrar;
	private JLabel lblNombre;
	private JLabel Nombre;
	private JLabel lblTP;
	private JLabel tipoPublicacion;
	private JLabel lblFechaVenc;
	private JLabel fechaVenc;
	private JLabel lblCVR;
	private JLabel lblMotiv;
	private JLabel lblKeywords;
	private JComboBox<String> comboBox;
	private JTextArea textArea_1;
	private JTextArea textArea_2;
	private JLabel lblFechaPostulacion;
	private JLabel fechaPostulacionDin;
	private JLabel lblNomPaquete;
	private JLabel lblEstado;
	private JLabel lblFechaBaja;
	private JLabel fechaBaja_1;



	public DTOfertaComple(IOferta iof){
		IOfr = iof;
		getContentPane().setFont(new Font("Tahoma", Font.BOLD, 10));
		setTitle("Datos Oferta");
		//miDT = dt;

		// a ver si es null NO NO ES
        //System.out.println("Value of ofertaInfo: " + dt);
		//setVisible(true);


		setBounds(700, 100, 587, 487);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 40, 46, 32, 20, 106, 16, 103, 37, 40};
		gridBagLayout.rowHeights = new int[]{44, 0, 85, 30, 25, 25, 25, 25, 25, 25, 25, 5, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);

		lblNewLabel = new JLabel("Datos Oferta");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 27));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 9;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		getContentPane().add(lblNombre, gbc_lblNombre);

		Nombre = new JLabel("New label");
		GridBagConstraints gbc_Nombre = new GridBagConstraints();
		gbc_Nombre.anchor = GridBagConstraints.WEST;
		gbc_Nombre.gridwidth = 4;
		gbc_Nombre.insets = new Insets(0, 0, 5, 5);
		gbc_Nombre.gridx = 2;
		gbc_Nombre.gridy = 1;
		getContentPane().add(Nombre, gbc_Nombre);

		lblEstado = new JLabel("[estado]");
		GridBagConstraints gbc_lblEstado = new GridBagConstraints();
		gbc_lblEstado.insets = new Insets(0, 0, 5, 5);
		gbc_lblEstado.gridx = 7;
		gbc_lblEstado.gridy = 1;
		getContentPane().add(lblEstado, gbc_lblEstado);

		JLabel lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 1;
		gbc_lblDescripcion.gridy = 2;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		textArea.setCaretPosition(textArea.getDocument().getLength());
		textArea.setLineWrap(true);

		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridwidth = 4;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 2;
		gbc_textArea.gridy = 2;
		getContentPane().add(scrollPane, gbc_textArea);

		lblTP = new JLabel("Tipo de publicación");
		lblTP.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_lblTP = new GridBagConstraints();
		gbc_lblTP.insets = new Insets(0, 0, 5, 5);
		gbc_lblTP.gridx = 1;
		gbc_lblTP.gridy = 3;
		getContentPane().add(lblTP, gbc_lblTP);

		tipoPublicacion = new JLabel("New label");
		tipoPublicacion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_tipoPublicacion = new GridBagConstraints();
		gbc_tipoPublicacion.anchor = GridBagConstraints.WEST;
		gbc_tipoPublicacion.gridwidth = 3;
		gbc_tipoPublicacion.insets = new Insets(0, 0, 5, 5);
		gbc_tipoPublicacion.gridx = 2;
		gbc_tipoPublicacion.gridy = 3;
		getContentPane().add(tipoPublicacion, gbc_tipoPublicacion);

						lblPostulantes = new JLabel("Postulantes");
						lblPostulantes.setFont(new Font("Tahoma", Font.BOLD, 10));
						GridBagConstraints gbc_lblPostulantes = new GridBagConstraints();
						gbc_lblPostulantes.anchor = GridBagConstraints.SOUTHWEST;
						gbc_lblPostulantes.insets = new Insets(0, 0, 5, 5);
						gbc_lblPostulantes.gridx = 5;
						gbc_lblPostulantes.gridy = 3;
						getContentPane().add(lblPostulantes, gbc_lblPostulantes);

		lblFechaPostulacion = new JLabel("Fecha Postulacion");
		lblFechaPostulacion.setFont(new Font("Dialog", Font.BOLD, 10));
		GridBagConstraints gbc_lblFechaPostulacion = new GridBagConstraints();
		gbc_lblFechaPostulacion.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaPostulacion.gridx = 7;
		gbc_lblFechaPostulacion.gridy = 3;
		getContentPane().add(lblFechaPostulacion, gbc_lblFechaPostulacion);
		lblFechaPostulacion.setVisible(false);
		//getContentPane().add(textArea, gbc_textArea);
		//textArea.setText(miDT.getDescripcion());

		JLabel lblCiudad = new JLabel("Ciudad:");
		lblCiudad.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_lblCiudad = new GridBagConstraints();
		gbc_lblCiudad.anchor = GridBagConstraints.EAST;
		gbc_lblCiudad.insets = new Insets(0, 0, 5, 5);
		gbc_lblCiudad.gridx = 1;
		gbc_lblCiudad.gridy = 4;
		getContentPane().add(lblCiudad, gbc_lblCiudad);

		ciudad = new JLabel("");
		GridBagConstraints gbc_ciudad = new GridBagConstraints();
		gbc_ciudad.anchor = GridBagConstraints.WEST;
		gbc_ciudad.gridwidth = 3;
		gbc_ciudad.insets = new Insets(0, 0, 5, 5);
		gbc_ciudad.gridx = 2;
		gbc_ciudad.gridy = 4;
		getContentPane().add(ciudad, gbc_ciudad);

		comboPostulantes = new JComboBox<>();
		comboPostulantes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboPostulantes.getSelectedItem() != null && comboPostulantes.getSelectedItem() != "Postulantes") {
					String nickPostulante = comboPostulantes.getSelectedItem().toString();
					ManejadorUsuario mu = ManejadorUsuario.getInstance();
					try {

						DTPostulacion pos = null;

						EntityManagerFactory emf = Persistence.createEntityManagerFactory("trabajouy");
				 		EntityManager entm = emf.createEntityManager();
				 		TypedQuery<OfertaDAO> query = entm.createQuery("SELECT o FROM OfertaDAO o WHERE o.nombre = :nombre", OfertaDAO.class);
				 		query.setParameter("nombre", miDT.getNombre());
				 		List<OfertaDAO> oferta = query.getResultList();

				 		if(!oferta.isEmpty()) {
				 			Set<PostulacionDAO> postu = oferta.get(0).getPostulacionesDAO();
				 			for(PostulacionDAO post : postu) {
				 				if(nickPostulante.equals(post.getIdPostulante().getNickname())){
				 					pos = post.getDTPostulacion();
				 				}
				 			}
				        }
				 		else {
							Postulante p = mu.obtenerInstanciaP(nickPostulante);
							pos = p.infoPostulacion(miDT.getNombre());
				 		}
				 		entm.close();
				 		emf.close();
						SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
						fechaPostulacionDin.setText(formatoFecha.format(pos.getFecha()));
						textArea_2.setText(pos.getMotivacion());
						textArea_1.setText(pos.getCVR());
						textArea_2.setCaretPosition(0);
						textArea_1.setCaretPosition(0);
						fechaPostulacionDin.setVisible(true);
						textArea_1.setVisible(true);
						textArea_2.setVisible(true);
						scrollPane1.setVisible(true);
						scrollPane2.setVisible(true);
						lblCVR.setVisible(true);
						lblMotiv.setVisible(true);
						lblFechaPostulacion.setVisible(true);
					} catch (NoExistePostulante e1) {
						// no pasa nunca
						e1.printStackTrace();
					}
				}else {
					fechaPostulacionDin.setVisible(false);
					textArea_1.setVisible(false);
					textArea_2.setVisible(false);
					scrollPane1.setVisible(false);
					scrollPane2.setVisible(false);
					lblCVR.setVisible(false);
					lblMotiv.setVisible(false);
					lblFechaPostulacion.setVisible(false);
				}
			}
		});
		GridBagConstraints gbc_comboPostulantes = new GridBagConstraints();
		gbc_comboPostulantes.insets = new Insets(0, 0, 5, 5);
		gbc_comboPostulantes.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboPostulantes.gridx = 5;
		gbc_comboPostulantes.gridy = 4;
		getContentPane().add(comboPostulantes, gbc_comboPostulantes);
								comboPostulantes.setEnabled(true); //Hay q dejarlo true para poder ver

				fechaPostulacionDin = new JLabel("New label");
				GridBagConstraints gbc_fechaPostulacionDin = new GridBagConstraints();
				gbc_fechaPostulacionDin.insets = new Insets(0, 0, 5, 5);
				gbc_fechaPostulacionDin.gridx = 7;
				gbc_fechaPostulacionDin.gridy = 4;
				getContentPane().add(fechaPostulacionDin, gbc_fechaPostulacionDin);
				fechaPostulacionDin.setVisible(false);

				JLabel lblDepartamento = new JLabel("Departamento:");
				lblDepartamento.setFont(new Font("Tahoma", Font.BOLD, 10));
				GridBagConstraints gbc_lblDepartamento = new GridBagConstraints();
				gbc_lblDepartamento.anchor = GridBagConstraints.EAST;
				gbc_lblDepartamento.insets = new Insets(0, 0, 5, 5);
				gbc_lblDepartamento.gridx = 1;
				gbc_lblDepartamento.gridy = 5;
				getContentPane().add(lblDepartamento, gbc_lblDepartamento);

						departamento = new JLabel();
						GridBagConstraints gbc_departamento = new GridBagConstraints();
						gbc_departamento.anchor = GridBagConstraints.WEST;
						gbc_departamento.gridwidth = 3;
						gbc_departamento.insets = new Insets(0, 0, 5, 5);
						gbc_departamento.gridx = 2;
						gbc_departamento.gridy = 5;
						getContentPane().add(departamento, gbc_departamento);
						departamento.setText("New label");

				lblCVR = new JLabel("CV Reducido:");
				lblCVR.setFont(new Font("Tahoma", Font.BOLD, 10));
				GridBagConstraints gbc_lblCVR = new GridBagConstraints();
				gbc_lblCVR.anchor = GridBagConstraints.WEST;
				gbc_lblCVR.insets = new Insets(0, 0, 5, 5);
				gbc_lblCVR.gridx = 5;
				gbc_lblCVR.gridy = 5;
				getContentPane().add(lblCVR, gbc_lblCVR);
				lblCVR.setVisible(false);

				lblMotiv = new JLabel("Motivacion:");
				lblMotiv.setFont(new Font("Tahoma", Font.BOLD, 10));
				GridBagConstraints gbc_lblMotiv = new GridBagConstraints();
				gbc_lblMotiv.insets = new Insets(0, 0, 5, 5);
				gbc_lblMotiv.gridx = 7;
				gbc_lblMotiv.gridy = 5;
				getContentPane().add(lblMotiv, gbc_lblMotiv);
				lblMotiv.setVisible(false);

				JLabel lblHorario = new JLabel("Horario:");
				lblHorario.setFont(new Font("Tahoma", Font.BOLD, 10));
				GridBagConstraints gbc_lblHorario = new GridBagConstraints();
				gbc_lblHorario.anchor = GridBagConstraints.EAST;
				gbc_lblHorario.insets = new Insets(0, 0, 5, 5);
				gbc_lblHorario.gridx = 1;
				gbc_lblHorario.gridy = 6;
				getContentPane().add(lblHorario, gbc_lblHorario);

				horario = new JLabel();
				GridBagConstraints gbc_horario = new GridBagConstraints();
				gbc_horario.anchor = GridBagConstraints.WEST;
				gbc_horario.gridwidth = 3;
				gbc_horario.insets = new Insets(0, 0, 5, 5);
				gbc_horario.gridx = 2;
				gbc_horario.gridy = 6;
				getContentPane().add(horario, gbc_horario);
				horario.setText("New label");

				textArea_1 = new JTextArea();
				textArea_1.setEditable(false);
				scrollPane1 = new JScrollPane(textArea_1,
						ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				scrollPane1.setVisible(false);
				textArea_1.setCaretPosition(textArea_1.getDocument().getLength());
				textArea_1.setLineWrap(true);
				textArea_1.setVisible(false);

				GridBagConstraints gbc_textArea_1 = new GridBagConstraints();
				gbc_textArea_1.gridheight = 2;
				gbc_textArea_1.insets = new Insets(0, 0, 5, 5);
				gbc_textArea_1.fill = GridBagConstraints.BOTH;
				gbc_textArea_1.gridx = 5;
				gbc_textArea_1.gridy = 6;
				getContentPane().add(scrollPane1, gbc_textArea_1);

				textArea_2 = new JTextArea();
				textArea_2.setEditable(false);
				scrollPane2 = new JScrollPane(textArea_2,
						ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				scrollPane2.setVisible(false);
				textArea_2.setCaretPosition(textArea_2.getDocument().getLength());
				textArea_2.setLineWrap(true);
				textArea_2.setVisible(false);

				GridBagConstraints gbc_textArea_2 = new GridBagConstraints();
				gbc_textArea_2.gridheight = 2;
				gbc_textArea_2.insets = new Insets(0, 0, 5, 5);
				gbc_textArea_2.fill = GridBagConstraints.BOTH;
				gbc_textArea_2.gridx = 7;
				gbc_textArea_2.gridy = 6;
				getContentPane().add(scrollPane2, gbc_textArea_2);


				JLabel lblRemuneracion = new JLabel("Remuneracion:");
				lblRemuneracion.setFont(new Font("Tahoma", Font.BOLD, 10));
				GridBagConstraints gbc_lblRemuneracion = new GridBagConstraints();
				gbc_lblRemuneracion.anchor = GridBagConstraints.EAST;
				gbc_lblRemuneracion.insets = new Insets(0, 0, 5, 5);
				gbc_lblRemuneracion.gridx = 1;
				gbc_lblRemuneracion.gridy = 7;
				getContentPane().add(lblRemuneracion, gbc_lblRemuneracion);

				remuneracion = new JLabel();
				GridBagConstraints gbc_remuneracion = new GridBagConstraints();
				gbc_remuneracion.anchor = GridBagConstraints.WEST;
				gbc_remuneracion.gridwidth = 3;
				gbc_remuneracion.insets = new Insets(0, 0, 5, 5);
				gbc_remuneracion.gridx = 2;
				gbc_remuneracion.gridy = 7;
				getContentPane().add(remuneracion, gbc_remuneracion);
				remuneracion.setText("New label");

				JLabel lblFechaAlta = new JLabel("Fecha Alta:");
				lblFechaAlta.setFont(new Font("Tahoma", Font.BOLD, 10));
				GridBagConstraints gbc_lblFechaAlta = new GridBagConstraints();
				gbc_lblFechaAlta.anchor = GridBagConstraints.EAST;
				gbc_lblFechaAlta.insets = new Insets(0, 0, 5, 5);
				gbc_lblFechaAlta.gridx = 1;
				gbc_lblFechaAlta.gridy = 8;
				getContentPane().add(lblFechaAlta, gbc_lblFechaAlta);

				fechaAlta = new JLabel();
				GridBagConstraints gbc_fechaAlta = new GridBagConstraints();
				gbc_fechaAlta.anchor = GridBagConstraints.WEST;
				gbc_fechaAlta.gridwidth = 3;
				gbc_fechaAlta.insets = new Insets(0, 0, 5, 5);
				gbc_fechaAlta.gridx = 2;
				gbc_fechaAlta.gridy = 8;
				getContentPane().add(fechaAlta, gbc_fechaAlta);
				fechaAlta.setText("New label");



				ciudad.setText("New label");

				lblPaquetes = new JLabel("Paquete");
				lblPaquetes.setFont(new Font("Tahoma", Font.BOLD, 10));
				GridBagConstraints gbc_lblPaquetes = new GridBagConstraints();
				gbc_lblPaquetes.anchor = GridBagConstraints.SOUTHWEST;
				gbc_lblPaquetes.insets = new Insets(0, 0, 5, 5);
				gbc_lblPaquetes.gridx = 5;
				gbc_lblPaquetes.gridy = 8;
				getContentPane().add(lblPaquetes, gbc_lblPaquetes);

				lblPaquetes.setVisible(true);

						lblKeywords = new JLabel("Keywords");
						lblKeywords.setFont(new Font("Tahoma", Font.BOLD, 10));
						GridBagConstraints gbc_lblKeywords = new GridBagConstraints();
						gbc_lblKeywords.anchor = GridBagConstraints.WEST;
						gbc_lblKeywords.insets = new Insets(0, 0, 5, 5);
						gbc_lblKeywords.gridx = 7;
						gbc_lblKeywords.gridy = 8;
						getContentPane().add(lblKeywords, gbc_lblKeywords);

				lblFechaVenc = new JLabel("Fecha Vencimiento:");
				lblFechaVenc.setFont(new Font("Tahoma", Font.BOLD, 10));
				GridBagConstraints gbc_lblFechaVenc = new GridBagConstraints();
				gbc_lblFechaVenc.insets = new Insets(0, 0, 5, 5);
				gbc_lblFechaVenc.gridx = 1;
				gbc_lblFechaVenc.gridy = 9;
				getContentPane().add(lblFechaVenc, gbc_lblFechaVenc);

				fechaVenc = new JLabel("New label");
				GridBagConstraints gbc_fechaVenc = new GridBagConstraints();
				gbc_fechaVenc.anchor = GridBagConstraints.WEST;
				gbc_fechaVenc.gridwidth = 3;
				gbc_fechaVenc.insets = new Insets(0, 0, 5, 5);
				gbc_fechaVenc.gridx = 2;
				gbc_fechaVenc.gridy = 9;
				getContentPane().add(fechaVenc, gbc_fechaVenc);

				lblNomPaquete = new JLabel("nombre_paquete");
				GridBagConstraints gbc_lblNomPaquete = new GridBagConstraints();
				gbc_lblNomPaquete.anchor = GridBagConstraints.WEST;
				gbc_lblNomPaquete.insets = new Insets(0, 0, 5, 5);
				gbc_lblNomPaquete.gridx = 5;
				gbc_lblNomPaquete.gridy = 9;
				getContentPane().add(lblNomPaquete, gbc_lblNomPaquete);

				comboBox = new JComboBox();
				GridBagConstraints gbc_comboBox = new GridBagConstraints();
				gbc_comboBox.insets = new Insets(0, 0, 5, 5);
				gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_comboBox.gridx = 7;
				gbc_comboBox.gridy = 9;
				getContentPane().add(comboBox, gbc_comboBox);
		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ocultarDatos();
				setVisible(false);
			}
		});

		lblFechaBaja = new JLabel("Fecha Baja:");
		lblFechaBaja.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_lblFechaBaja = new GridBagConstraints();
		gbc_lblFechaBaja.anchor = GridBagConstraints.EAST;
		gbc_lblFechaBaja.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaBaja.gridx = 1;
		gbc_lblFechaBaja.gridy = 10;
		getContentPane().add(lblFechaBaja, gbc_lblFechaBaja);
		lblFechaBaja.setVisible(false);

		fechaBaja = new JLabel();
		fechaBaja.setText("New label");
		GridBagConstraints gbc_fechaBaja_1 = new GridBagConstraints();
		gbc_fechaBaja_1.anchor = GridBagConstraints.WEST;
		gbc_fechaBaja_1.gridwidth = 3;
		gbc_fechaBaja_1.insets = new Insets(0, 0, 5, 5);
		gbc_fechaBaja_1.gridx = 2;
		gbc_fechaBaja_1.gridy = 10;
		getContentPane().add(fechaBaja, gbc_fechaBaja_1);
		fechaBaja.setVisible(false);


		GridBagConstraints gbc_btnCerrar = new GridBagConstraints();
		gbc_btnCerrar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCerrar.gridx = 1;
		gbc_btnCerrar.gridy = 11;
		getContentPane().add(btnCerrar, gbc_btnCerrar);
	}



	/*Crear frame*/


	public void cargarDatos(DTOferta dt) {
		miDT = dt;
		tipoPublicacion.setText(dt.getTipoPublicacion());
		Nombre.setText(dt.getNombre());
		textArea.setText(miDT.getDescripcion());
		ciudad.setText(miDT.getCiudad());
		departamento.setText(miDT.getDepartamento());
		lblEstado.setText("Estado: "+miDT.getEstado().toString().toUpperCase());

		comboPostulantes.removeAllItems();
		Set<String> postula = new HashSet<>();



 		postula = miDT.getPostul();
		if(!postula.isEmpty()) {
			comboPostulantes.addItem("Postulantes");
			for(String p: postula) { // esto tendria q ser add item q sea el nickname asociado a esa postulacion
				comboPostulantes.addItem(p);
			}
		}
		horario.setText(miDT.getHorario().toString());

		// ALERTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT///////////////////////////////////////////////////////////////////////////////////

		DTPaquete asoc = miDT.getDTPaqueteAsociado();
		if (asoc != null) {
			lblNomPaquete.setText(asoc.getNombre());
		}
		else
			lblNomPaquete.setText("No utilizó");

		//comboPaquetes.addItem(miDT.getDTPaqueteAsociado().getNombre());


		comboBox.removeAllItems();
		Set<String> kw = new HashSet<>();
		kw = miDT.getKeywords();
		if(!kw.isEmpty()) {
			comboBox.addItem("Keywords");
			for(String k: kw) { // esto tendria q ser add item q sea el nickname asociado a esa postulacion
				comboBox.addItem(k);
			}
		}
		remuneracion.setText(String.valueOf(miDT.getRemuneracion()));
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
		fechaAlta.setText(formatoFecha.format(miDT.getFechaAlta()));
		if(miDT.getEstado()==Estados.finalizada) {
			lblFechaBaja.setVisible(true);
			fechaBaja.setVisible(true);
			fechaBaja.setText(formatoFecha.format(miDT.getFechaBaja()));
		}
		else {
			fechaBaja.setVisible(false);
			lblFechaBaja.setVisible(false);
		}
		// fecha de vencimiento calcular
		String nombreTP = miDT.getTipoPublicacion(); // el tipo de publicacion guardado es el nombreTP
		try { // nunca va a pasar por la manera en que se elije el nombreTP
			DTTipoPublicacion tp = IOfr.getInfoTipoPublicacion(nombreTP);
			Date dfv = miDT.fechaVencimiento(tp.getDuracion());
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
	        String fechaFormateada = formato.format(dfv);
	        fechaVenc.setText(fechaFormateada);

		}catch(NoExisteTipoPublicacion e) {
			JOptionPane.showMessageDialog(null, "No existe ese tipo de publicacion", "DTOfertaComple",
                    JOptionPane.ERROR_MESSAGE);
		}


	}

	public void ocultarDatos() {
		fechaPostulacionDin.setVisible(false);
		textArea_1.setVisible(false);
		textArea_2.setVisible(false);
		scrollPane1.setVisible(false);
		scrollPane2.setVisible(false);
		lblCVR.setVisible(false);
		lblMotiv.setVisible(false);
		lblFechaPostulacion.setVisible(false);
		lblFechaBaja.setVisible(false);
		fechaBaja.setVisible(false);
	}
}
