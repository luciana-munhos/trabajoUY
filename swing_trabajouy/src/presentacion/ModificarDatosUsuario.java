package presentacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import excepciones.NoExisteEmpresa;
import excepciones.NoExistePostulante;
import logica.DTEmpresa;
import logica.DTPostulante;
import logica.DTUsuario;
import logica.IUsuario;

public class ModificarDatosUsuario extends JInternalFrame {
	private IUsuario contU;


	private JTextField textNick;
	private JTextField textNombre;
	private JTextField textApellido;
	private JTextField textCorreo;
	private JTextField textNac_Link;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton buttonPostulante;
	private JRadioButton buttonEmpresa;
	private JTextArea textDesc;
	private JScrollPane scrollPane;
	private JComboBox<String> comboUsuario;
	private JLabel lblNac_Link;
	private JLabel lblFechaNac;
	private JLabel lblDesc;
	private JDateChooser dateF;
	private JLabel lblContra;
	private JPasswordField password;
	private JLabel lblImagen;
	private JButton btnSubir;


	private File imagen;
	private JLabel lblEstadoImagen;

	private DTUsuario dt = null;


	public ModificarDatosUsuario(IUsuario IU) {
		contU = IU;
		setBounds(100, 100, 421, 472);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{118, 113, 85, 82};
		gridBagLayout.rowHeights = new int[]{41, 35, 35, 35, 35, 35, 32, 38, 35, 40, 35, 73, 32};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("Modificación de Usuario");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 4;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblSeleccione = new JLabel("Seleccione un Usuario");
		GridBagConstraints gbc_lblSeleccione = new GridBagConstraints();
		gbc_lblSeleccione.anchor = GridBagConstraints.EAST;
		gbc_lblSeleccione.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccione.gridx = 0;
		gbc_lblSeleccione.gridy = 1;
		getContentPane().add(lblSeleccione, gbc_lblSeleccione);

		comboUsuario = new JComboBox<>();
		comboUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(comboUsuario.getSelectedItem() != null) {
					String nickname = comboUsuario.getSelectedItem().toString();

					try {
						dt = contU.mostrarDatosUsuario(nickname);
						// atributos generales
						textNick.setText(dt.getNickname());
						textNombre.setText(dt.getNombre());
						textApellido.setText(dt.getApellido());
						textCorreo.setText(dt.getCorreo());
						// no se muestra la contrasenia actual
						password.setText("");
						// atributos particulares
						if (dt instanceof DTPostulante) {
							DTPostulante dtp = (DTPostulante) dt;
							buttonPostulante.setSelected(true);
							// mostrar fecha
							Date f = (Date) dtp.getFechaNac();
					        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					        String fecha = dateFormat.format(f);
					        dateF.setDate(f);
					        dateF.setVisible(true);
					        lblFechaNac.setVisible(true);

					        // nacionalidadS
					        lblNac_Link.setText("Nacionalidad:");
					        lblNac_Link.setVisible(true);
							textNac_Link.setText(dtp.getNacionalidad());
							textNac_Link.setVisible(true);

							// ocultar desc por las dudas
							textDesc.setVisible(false);
					        scrollPane.setVisible(false);
					        lblDesc.setVisible(false);

						}else { // DTEmpresa
							DTEmpresa dte = (DTEmpresa) dt;
							buttonEmpresa.setSelected(true);

							// mostrar descripcion
					        textDesc.setText(dte.getDescripcion());
					        textDesc.setVisible(true);
					        scrollPane.setVisible(true);
					        lblDesc.setVisible(true);

					        // link
					        lblNac_Link.setText("Link:");
					        lblNac_Link.setVisible(true);
							textNac_Link.setText(dte.getLink());
							textNac_Link.setVisible(true);

							// ocultar la fecha por las dudas
							dateF.setVisible(false);
							lblFechaNac.setVisible(false);
						}

					} catch (NoExisteEmpresa | NoExistePostulante e1) {

						e1.printStackTrace();
					}


				}

			}
		});

		comboUsuario.setEditable(false);
		GridBagConstraints gbc_comboUsuario = new GridBagConstraints();
		gbc_comboUsuario.gridwidth = 2;
		gbc_comboUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_comboUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboUsuario.gridx = 1;
		gbc_comboUsuario.gridy = 1;
		getContentPane().add(comboUsuario, gbc_comboUsuario);

		JLabel lblNick = new JLabel("Nickname:");
		lblNick.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_lblNick = new GridBagConstraints();
		gbc_lblNick.anchor = GridBagConstraints.EAST;
		gbc_lblNick.insets = new Insets(0, 0, 5, 5);
		gbc_lblNick.gridx = 0;
		gbc_lblNick.gridy = 2;
		getContentPane().add(lblNick, gbc_lblNick);

		textNick = new JTextField();
		textNick.setEditable(false);
		GridBagConstraints gbc_textNick = new GridBagConstraints();
		gbc_textNick.gridwidth = 2;
		gbc_textNick.insets = new Insets(0, 0, 5, 5);
		gbc_textNick.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNick.gridx = 1;
		gbc_textNick.gridy = 2;
		getContentPane().add(textNick, gbc_textNick);
		textNick.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 3;
		getContentPane().add(lblNombre, gbc_lblNombre);

		textNombre = new JTextField();
		GridBagConstraints gbc_textNombre = new GridBagConstraints();
		gbc_textNombre.gridwidth = 2;
		gbc_textNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNombre.gridx = 1;
		gbc_textNombre.gridy = 3;
		getContentPane().add(textNombre, gbc_textNombre);
		textNombre.setColumns(10);

		JLabel lblApellido = new JLabel("Apellido:");
		GridBagConstraints gbc_lblApellido = new GridBagConstraints();
		gbc_lblApellido.anchor = GridBagConstraints.EAST;
		gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellido.gridx = 0;
		gbc_lblApellido.gridy = 4;
		getContentPane().add(lblApellido, gbc_lblApellido);

		textApellido = new JTextField();
		GridBagConstraints gbc_textApellido = new GridBagConstraints();
		gbc_textApellido.gridwidth = 2;
		gbc_textApellido.insets = new Insets(0, 0, 5, 5);
		gbc_textApellido.fill = GridBagConstraints.HORIZONTAL;
		gbc_textApellido.gridx = 1;
		gbc_textApellido.gridy = 4;
		getContentPane().add(textApellido, gbc_textApellido);
		textApellido.setColumns(10);

		JLabel lblCorreo = new JLabel("Correo:");
		GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
		gbc_lblCorreo.anchor = GridBagConstraints.EAST;
		gbc_lblCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCorreo.gridx = 0;
		gbc_lblCorreo.gridy = 5;
		getContentPane().add(lblCorreo, gbc_lblCorreo);

		textCorreo = new JTextField();
		textCorreo.setEditable(false);
		GridBagConstraints gbc_textCorreo = new GridBagConstraints();
		gbc_textCorreo.gridwidth = 2;
		gbc_textCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_textCorreo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCorreo.gridx = 1;
		gbc_textCorreo.gridy = 5;
		getContentPane().add(textCorreo, gbc_textCorreo);
		textCorreo.setColumns(10);

		lblContra = new JLabel("Contraseña:");
		GridBagConstraints gbc_lblContra = new GridBagConstraints();
		gbc_lblContra.anchor = GridBagConstraints.EAST;
		gbc_lblContra.insets = new Insets(0, 0, 5, 5);
		gbc_lblContra.gridx = 0;
		gbc_lblContra.gridy = 6;
		getContentPane().add(lblContra, gbc_lblContra);

		password = new JPasswordField();
		GridBagConstraints gbc_password = new GridBagConstraints();
		gbc_password.gridwidth = 2;
		gbc_password.insets = new Insets(0, 0, 5, 5);
		gbc_password.fill = GridBagConstraints.HORIZONTAL;
		gbc_password.gridx = 1;
		gbc_password.gridy = 6;
		getContentPane().add(password, gbc_password);

		lblImagen = new JLabel("Imagen:");
		GridBagConstraints gbc_lblImagen = new GridBagConstraints();
		gbc_lblImagen.anchor = GridBagConstraints.EAST;
		gbc_lblImagen.insets = new Insets(0, 0, 5, 5);
		gbc_lblImagen.gridx = 0;
		gbc_lblImagen.gridy = 7;
		getContentPane().add(lblImagen, gbc_lblImagen);

		btnSubir = new JButton("Subir");
		GridBagConstraints gbc_btnSubir = new GridBagConstraints();
		gbc_btnSubir.anchor = GridBagConstraints.WEST;
		gbc_btnSubir.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubir.gridx = 1;
		gbc_btnSubir.gridy = 7;
		getContentPane().add(btnSubir, gbc_btnSubir);

		btnSubir.addActionListener(ev -> {
		      JFileChooser fc = new JFileChooser(System.getProperty("user.home"));
		      fc.setAcceptAllFileFilterUsed(false);
		      fc.addChoosableFileFilter(new FileNameExtensionFilter("Archivos de imagen",
		          new String[] { "png", "jpg", "jpeg" }));
		      if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		        try {
		          imagen = (fc.getSelectedFile());
		          lblEstadoImagen.setText("OK");
		          lblEstadoImagen.setVisible(true);
		          }
		         catch (Exception ex) {
		          ex.printStackTrace();
		          lblEstadoImagen.setText("ERROR");
		        }
		      }
		    });

		lblEstadoImagen = new JLabel("OK");
		GridBagConstraints gbc_lblEstadoImagen = new GridBagConstraints();
		gbc_lblEstadoImagen.insets = new Insets(0, 0, 5, 5);
		gbc_lblEstadoImagen.gridx = 2;
		gbc_lblEstadoImagen.gridy = 7;
		getContentPane().add(lblEstadoImagen, gbc_lblEstadoImagen);
		lblEstadoImagen.setVisible(false);

		JLabel lblTipo = new JLabel("Tipo de usuario:");
		GridBagConstraints gbc_lblTipo = new GridBagConstraints();
		gbc_lblTipo.anchor = GridBagConstraints.EAST;
		gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipo.gridx = 0;
		gbc_lblTipo.gridy = 8;
		getContentPane().add(lblTipo, gbc_lblTipo);

		buttonPostulante = new JRadioButton("Postulante");
		buttonGroup.add(buttonPostulante);
		buttonPostulante.setEnabled(false);
		GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
		gbc_rdbtnNewRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton.gridx = 1;
		gbc_rdbtnNewRadioButton.gridy = 8;
		getContentPane().add(buttonPostulante, gbc_rdbtnNewRadioButton);
		buttonPostulante.setSelected(true);

		buttonEmpresa = new JRadioButton("Empresa");
		buttonGroup.add(buttonEmpresa);
		buttonEmpresa.setEnabled(false);
		GridBagConstraints gbc_rdbtnNewRadioButton_1 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_1.gridx = 2;
		gbc_rdbtnNewRadioButton_1.gridy = 8;
		getContentPane().add(buttonEmpresa, gbc_rdbtnNewRadioButton_1);

		lblFechaNac = new JLabel("Fecha de nacimiento:");
		lblFechaNac.setVisible(false);
		GridBagConstraints gbc_lblFechaNac = new GridBagConstraints();
		gbc_lblFechaNac.anchor = GridBagConstraints.EAST;
		gbc_lblFechaNac.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaNac.gridx = 0;
		gbc_lblFechaNac.gridy = 9;
		getContentPane().add(lblFechaNac, gbc_lblFechaNac);

		lblNac_Link = new JLabel("Nacionalidad:");
		lblNac_Link.setVisible(false);

		dateF = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.gridwidth = 2;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser.gridx = 1;
		gbc_dateChooser.gridy = 9;
		getContentPane().add(dateF, gbc_dateChooser);
		GridBagConstraints gbc_lblNac_Link = new GridBagConstraints();
		gbc_lblNac_Link.anchor = GridBagConstraints.EAST;
		gbc_lblNac_Link.insets = new Insets(0, 0, 5, 5);
		gbc_lblNac_Link.gridx = 0;
		gbc_lblNac_Link.gridy = 10;
		getContentPane().add(lblNac_Link, gbc_lblNac_Link);
		JCalendar jCalendar = dateF.getJCalendar();
        Calendar minYear = Calendar.getInstance();
        Calendar maxYear = Calendar.getInstance();
		maxYear.set(Calendar.YEAR, 2023);
		jCalendar.setMaxSelectableDate(maxYear.getTime());
		minYear.set(Calendar.DAY_OF_MONTH,1);
		minYear.set(Calendar.MONTH,0);
		minYear.set(Calendar.YEAR, 1900);
        jCalendar.setMinSelectableDate(minYear.getTime());
        ((JTextFieldDateEditor) (dateF.getDateEditor())).setEditable(false);

		textNac_Link = new JTextField();
		textNac_Link.setVisible(false);
		GridBagConstraints gbc_textNac_Link = new GridBagConstraints();
		gbc_textNac_Link.gridwidth = 2;
		gbc_textNac_Link.insets = new Insets(0, 0, 5, 5);
		gbc_textNac_Link.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNac_Link.gridx = 1;
		gbc_textNac_Link.gridy = 10;
		getContentPane().add(textNac_Link, gbc_textNac_Link);
		textNac_Link.setColumns(10);

		lblDesc = new JLabel("Descripcion:");
		lblDesc.setVisible(false);
		GridBagConstraints gbc_lblDesc = new GridBagConstraints();
		gbc_lblDesc.anchor = GridBagConstraints.EAST;
		gbc_lblDesc.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesc.gridx = 0;
		gbc_lblDesc.gridy = 11;
		getContentPane().add(lblDesc, gbc_lblDesc);
		lblDesc.setVisible(false);


		textDesc = new JTextArea();
		textDesc.setVisible(false);

		scrollPane = new JScrollPane(textDesc,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		textDesc.setEditable(true);
		scrollPane.setVisible(false);

		textDesc.setCaretPosition(textDesc.getDocument().getLength());
		textDesc.setLineWrap(true);

		GridBagConstraints gbc_textDesc = new GridBagConstraints();
		gbc_textDesc.gridwidth = 2;
		gbc_textDesc.insets = new Insets(0, 0, 5, 5);
		gbc_textDesc.fill = GridBagConstraints.BOTH;
		gbc_textDesc.gridx = 1;
		gbc_textDesc.gridy = 11;
		//getContentPane().add(textDesc, gbc_textDesc);
		getContentPane().add(scrollPane, gbc_textDesc);

		JButton buttonAceptar = new JButton("Aceptar");
		buttonAceptar.setForeground(new Color(60, 179, 113));
		buttonAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(verificarFormulario()) {
					try{
						String nombre = "";
						String ext = "";
						if(imagen!=null) {
							nombre = imagen.getName();
							ext = "";
							int i = nombre.lastIndexOf('.');
							ext = nombre.substring(i + 1);
							nombre = "media/img/Usuarios/" + textNick.getText().replaceAll("\\s", "_") +"."+ ext;
						}
						else {
							nombre = dt.getImagen();
						}
						if(buttonPostulante.isSelected()) {
			    			Calendar calendar = Calendar.getInstance();
							calendar.setTime(dateF.getDate());
				            int anio = calendar.get(Calendar.YEAR);
				            int mes = calendar.get(Calendar.MONTH) + 1;
				            int dia = calendar.get(Calendar.DAY_OF_MONTH);
				            LocalDate localDate = LocalDate.of(anio, mes, dia);
				            Date fecha = Date.valueOf(localDate);


				            // si la contrasenia es vacia, no se modifica. Eso en la logica
							contU.modificarDatosPostulante(textNick.getText(),textNombre.getText(),textApellido.getText(),password.getText(), nombre, textNac_Link.getText(),fecha);
							setVisible(false);
							JOptionPane.showMessageDialog(null, "El usuario se ha modificado con éxito", "Modificar Usuario",
									JOptionPane.INFORMATION_MESSAGE);
						} else { // empresa is selected
							String desc = textDesc.getText();
							contU.modificarDatosEmpresa(textNick.getText(),textNombre.getText(),textApellido.getText(),password.getText(), nombre,desc,textNac_Link.getText());
							setVisible(false);
							JOptionPane.showMessageDialog(null, "El usuario se ha modificado con éxito", "Modificar Usuario",
									JOptionPane.INFORMATION_MESSAGE);
						}
						if(imagen!=null) {
							try {
								Path copia = Paths.get(nombre);
								Path img = imagen.toPath();
								Files.copy(img, copia);
							}catch(IOException exc) {
								System.out.println("No se pudo guardar la foto");
							    exc.printStackTrace();
							}
						}
						vaciarFormulario();
					}
					catch (NoExistePostulante n) {
						JOptionPane.showMessageDialog(null, n.getMessage(), "Modificar Usuario",
								JOptionPane.ERROR_MESSAGE);
					}
					catch(NoExisteEmpresa r){
						JOptionPane.showMessageDialog(null, r.getMessage(), "Modificar Usuario",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		GridBagConstraints gbc_buttonAceptar = new GridBagConstraints();
		gbc_buttonAceptar.insets = new Insets(0, 0, 0, 5);
		gbc_buttonAceptar.gridx = 1;
		gbc_buttonAceptar.gridy = 12;
		getContentPane().add(buttonAceptar, gbc_buttonAceptar);

		JButton buttonCancelar = new JButton("Cancelar");
		buttonCancelar.setForeground(Color.RED);
		buttonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				vaciarFormulario();
			}
		});
		GridBagConstraints gbc_buttonCancelar = new GridBagConstraints();
		gbc_buttonCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_buttonCancelar.gridx = 2;
		gbc_buttonCancelar.gridy = 12;
		getContentPane().add(buttonCancelar, gbc_buttonCancelar);

	}

	private boolean verificarFormulario() {
		String nombre = textNombre.getText();
		String apellido = textApellido.getText();
		String contra = String.valueOf(password.getPassword());
		// si la contrasenia la dejan vacia, se asume que no se quiere modificar

		if(nombre.isEmpty() || apellido.isEmpty() || nombre.matches("^\\s+$") || apellido.matches("^\\s+$")) {
			JOptionPane.showMessageDialog(this, "No puede haber campos vacíos", "Modificar Usuario",
                    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		DTUsuario dt;
		try {
			dt = contU.mostrarDatosUsuario(textNick.getText());
			/*CHEQUEO NO CAMPOS OBLIGATORIOS VACIOS*/
			if (buttonPostulante.isSelected()) {
				String nacionalidad = textNac_Link.getText();
				if(!nombre.matches("^[A-Za-zÁÉÍÓÚÑáéíóúñüÜ\\s'-]+$")) {
					JOptionPane.showMessageDialog(this, "Nombre no valido (solo debe tener letras)", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
					return false;
				}
				if(!apellido.matches("^[A-Za-zÁÉÍÓÚÑáéíóúñüÜ\\s'-]+$")) {
					JOptionPane.showMessageDialog(this, "Apellido no valido (solo debe tener letras)", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
					return false;
				}
				if(!nacionalidad.matches("^[A-Za-zÁÉÍÓÚÑáéíóúñüÜ\\s'-]+$")) {
					JOptionPane.showMessageDialog(this, "Nacionalidad no valida (solo debe tener letras)", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
					return false;
				}
				if(nacionalidad.isEmpty() || dateF.getDate()==null|| nacionalidad.matches("^\\s+$")) {
					JOptionPane.showMessageDialog(this, "No puede haber campos vacíos", "Modificar Usuario",
		                    JOptionPane.ERROR_MESSAGE);
					return false;
				}


				// si no se cambia ningun atributo:
				DTPostulante dtp = (DTPostulante) dt;
				Calendar calendary = Calendar.getInstance();
		        calendary.setTime(dateF.getDate());
	            int anio = calendary.get(Calendar.YEAR);
	            int mes = calendary.get(Calendar.MONTH) + 1;
	            int dia = calendary.get(Calendar.DAY_OF_MONTH);
	            LocalDate localDate = LocalDate.of(anio, mes, dia);
	            Date fecha = Date.valueOf(localDate);
				if (nombre.equals(dtp.getNombre()) && apellido.equals(dtp.getApellido()) &&
						nacionalidad.equals(dtp.getNacionalidad()) &&  fecha.equals(dtp.getFechaNac()) && imagen == null
						&& (contra.equals(dtp.getContrasenia())
								|| contra.isEmpty() || contra.matches("^\\s+$"))) {
					JOptionPane.showMessageDialog(this, "No se realizaron modificaciones", "Modificar Usuario",
		                    JOptionPane.ERROR_MESSAGE);
					password.setText("");
					return false;
				}

			}else { // caso empresa
				String desc = textDesc.getText();

				// link opcional, no se chequea
				if(textNac_Link.getText().contains(" ")) {
					JOptionPane.showMessageDialog(this, "link no valido (no debe tener espacios)", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
				return false;
				}
				if(desc.isEmpty() || desc.matches("^\\s+$")) {
					JOptionPane.showMessageDialog(this, "No puede haber campos vacíos", "Modificar Usuario",
		                    JOptionPane.ERROR_MESSAGE);
					return false;
				}else if(desc.length() > 1000) {
					JOptionPane.showMessageDialog(this, "La descripción no puede tener mas de 1000 caracteres", "Modificar Usuario", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				// si no se cambia ningun atributo:
				DTEmpresa dte = (DTEmpresa) dt;
				String link = textNac_Link.getText();
				if (imagen == null && nombre.equals(dte.getNombre()) && apellido.equals(dte.getApellido()) &&
						link.equals(dte.getLink()) && desc.equals(dte.getDescripcion()) &&
						(contra.equals(dte.getContrasenia()) || contra.isEmpty() || contra.matches("^\\s+$"))){
					JOptionPane.showMessageDialog(this, "No se realizan modificaciones", "Modificar Usuario",
		                    JOptionPane.ERROR_MESSAGE);
					password.setText("");
					return false;
				}
			}
			return true;
		} catch (NoExisteEmpresa | NoExistePostulante e) {

			e.printStackTrace();
		}

		return false;
	}

	private void vaciarFormulario() {
		textNick.setText("");
		textNombre.setText("");
		textApellido.setText("");
		textCorreo.setText("");
		textNac_Link.setText("");
		textDesc.setText("");
		textNac_Link.setVisible(false);
		textDesc.setVisible(false);
		imagen = null;
		password.setText("");
		dateF.setVisible(false);
		lblFechaNac.setVisible(false);
		lblNac_Link.setVisible(false);
		scrollPane.setVisible(false);
		lblDesc.setVisible(false);


	}

	private static void setPlaceholder(JTextField textField, String placeholder) {
    }

	public void abrir() {
		comboUsuario.removeAllItems();
		Set<String> usuarios = contU.listarNicknamesUsuarios();
		if(usuarios.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay usuarios registrados", "Modificar Usuario",
					JOptionPane.ERROR_MESSAGE);
		}
		else{
			for(String u : usuarios)
				comboUsuario.addItem(u);
			comboUsuario.setSelectedIndex(-1);
			vaciarFormulario();
			setVisible(true);
		}
	}

}
