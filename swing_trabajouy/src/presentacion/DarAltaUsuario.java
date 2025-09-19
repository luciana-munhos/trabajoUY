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
import java.time.LocalDate;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
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

import excepciones.CorreoUsuarioExistente;
import excepciones.NicknameUsuarioExistente;
import logica.IUsuario;

public class DarAltaUsuario extends JInternalFrame {
	private IUsuario contU;
	private JTextField textNick;
	private JTextField textNombre;
	private JTextField textApellido;
	private JTextField textCorreo;
	private JTextField textFecha;
	private JTextField textNac_Link;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton buttonPostulante;
	private JRadioButton buttonEmpresa;
	private JTextArea textDesc;
	private JScrollPane scrollPane;
	private JDateChooser dateF;
	private JTextField textField_contrasenia;
	private JTextField textField_confContrasenia;
	private File imagen;
	private JLabel lblEstadoImagen;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public DarAltaUsuario(IUsuario IU) {
		contU = IU;
		setBounds(100, 100, 628, 497);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{47, 52, 64, 82, 81, 91, 52,51};
		gridBagLayout.rowHeights = new int[]{57, 34, 35, 36, 35, 37, 38, 120, 62};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("Registro de Usuario");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 8;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblNick = new JLabel("Nickname:");
		lblNick.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblNick = new GridBagConstraints();
		gbc_lblNick.anchor = GridBagConstraints.EAST;
		gbc_lblNick.insets = new Insets(0, 0, 5, 5);
		gbc_lblNick.gridx = 1;
		gbc_lblNick.gridy = 1;
		getContentPane().add(lblNick, gbc_lblNick);

		textNick = new JTextField();
		GridBagConstraints gbc_textNick = new GridBagConstraints();
		gbc_textNick.gridwidth = 2;
		gbc_textNick.insets = new Insets(0, 0, 5, 5);
		gbc_textNick.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNick.gridx = 2;
		gbc_textNick.gridy = 1;
		getContentPane().add(textNick, gbc_textNick);
		textNick.setColumns(10);

		JLabel lblCorreo = new JLabel("Correo:");
		GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
		gbc_lblCorreo.anchor = GridBagConstraints.EAST;
		gbc_lblCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCorreo.gridx = 4;
		gbc_lblCorreo.gridy = 1;
		getContentPane().add(lblCorreo, gbc_lblCorreo);

		textCorreo = new JTextField();
		GridBagConstraints gbc_textCorreo = new GridBagConstraints();
		gbc_textCorreo.gridwidth = 2;
		gbc_textCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_textCorreo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCorreo.gridx = 5;
		gbc_textCorreo.gridy = 1;
		getContentPane().add(textCorreo, gbc_textCorreo);
		textCorreo.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 2;
		getContentPane().add(lblNombre, gbc_lblNombre);

		textNombre = new JTextField();
		GridBagConstraints gbc_textNombre = new GridBagConstraints();
		gbc_textNombre.gridwidth = 2;
		gbc_textNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNombre.gridx = 2;
		gbc_textNombre.gridy = 2;
		getContentPane().add(textNombre, gbc_textNombre);
		textNombre.setColumns(10);

		JLabel lblApellido = new JLabel("Apellido:");
		GridBagConstraints gbc_lblApellido = new GridBagConstraints();
		gbc_lblApellido.anchor = GridBagConstraints.EAST;
		gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellido.gridx = 4;
		gbc_lblApellido.gridy = 2;
		getContentPane().add(lblApellido, gbc_lblApellido);

		textApellido = new JTextField();
		GridBagConstraints gbc_textApellido = new GridBagConstraints();
		gbc_textApellido.gridwidth = 2;
		gbc_textApellido.insets = new Insets(0, 0, 5, 5);
		gbc_textApellido.fill = GridBagConstraints.HORIZONTAL;
		gbc_textApellido.gridx = 5;
		gbc_textApellido.gridy = 2;
		getContentPane().add(textApellido, gbc_textApellido);
		textApellido.setColumns(10);

		JLabel lblcontrasenia = new JLabel("Contraseña:");
		GridBagConstraints gbc_lblcontrasenia = new GridBagConstraints();
		gbc_lblcontrasenia.anchor = GridBagConstraints.EAST;
		gbc_lblcontrasenia.insets = new Insets(0, 0, 5, 5);
		gbc_lblcontrasenia.gridx = 1;
		gbc_lblcontrasenia.gridy = 3;
		getContentPane().add(lblcontrasenia, gbc_lblcontrasenia);

		textField_contrasenia = new JPasswordField();
		GridBagConstraints gbc_textField_contrasenia = new GridBagConstraints();
		gbc_textField_contrasenia.gridwidth = 2;
		gbc_textField_contrasenia.insets = new Insets(0, 0, 5, 5);
		gbc_textField_contrasenia.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_contrasenia.gridx = 2;
		gbc_textField_contrasenia.gridy = 3;
		getContentPane().add(textField_contrasenia, gbc_textField_contrasenia);
		textField_contrasenia.setColumns(10);

		lblEstadoImagen = new JLabel("OK");
		GridBagConstraints gbc_lblEstadoImagen = new GridBagConstraints();
		gbc_lblEstadoImagen.anchor = GridBagConstraints.WEST;
		gbc_lblEstadoImagen.insets = new Insets(0, 0, 5, 0);
		gbc_lblEstadoImagen.gridx = 7;
		gbc_lblEstadoImagen.gridy = 3;
		getContentPane().add(lblEstadoImagen, gbc_lblEstadoImagen);
		lblEstadoImagen.setVisible(false);

		JLabel lblImagen = new JLabel("Imagen:");
		GridBagConstraints gbc_lblImagen = new GridBagConstraints();
		gbc_lblImagen.anchor = GridBagConstraints.EAST;
		gbc_lblImagen.insets = new Insets(0, 0, 5, 5);
		gbc_lblImagen.gridx = 4;
		gbc_lblImagen.gridy = 3;
		getContentPane().add(lblImagen, gbc_lblImagen);

		JButton btnSubir = new JButton("Subir");
		GridBagConstraints gbc_btnSubir = new GridBagConstraints();
		gbc_btnSubir.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSubir.gridwidth = 2;
		gbc_btnSubir.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubir.gridx = 5;
		gbc_btnSubir.gridy = 3;
		getContentPane().add(btnSubir, gbc_btnSubir);

		btnSubir.addActionListener(ev -> {
		      JFileChooser fc = new JFileChooser(System.getProperty("user.home"));
		      fc.setAcceptAllFileFilterUsed(false);
		      fc.addChoosableFileFilter(new FileNameExtensionFilter("Image files",
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

		JLabel lblConfirmarContrasenia = new JLabel("Confirmar contraseña: ");
		GridBagConstraints gbc_lblConfirmarContrasenia = new GridBagConstraints();
		gbc_lblConfirmarContrasenia.anchor = GridBagConstraints.EAST;
		gbc_lblConfirmarContrasenia.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmarContrasenia.gridx = 1;
		gbc_lblConfirmarContrasenia.gridy = 4;
		getContentPane().add(lblConfirmarContrasenia, gbc_lblConfirmarContrasenia);

		textField_confContrasenia = new JPasswordField();
		GridBagConstraints gbc_textField_confContrasenia = new GridBagConstraints();
		gbc_textField_confContrasenia.gridwidth = 2;
		gbc_textField_confContrasenia.insets = new Insets(0, 0, 5, 5);
		gbc_textField_confContrasenia.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_confContrasenia.gridx = 2;
		gbc_textField_confContrasenia.gridy = 4;
		getContentPane().add(textField_confContrasenia, gbc_textField_confContrasenia);
		textField_confContrasenia.setColumns(10);

		JLabel lblFechaNac = new JLabel("Fecha de nacimiento:");
		GridBagConstraints gbc_lblFechaNac = new GridBagConstraints();
		gbc_lblFechaNac.anchor = GridBagConstraints.EAST;
		gbc_lblFechaNac.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaNac.gridx = 5;
		gbc_lblFechaNac.gridy = 4;
		getContentPane().add(lblFechaNac, gbc_lblFechaNac);

		JLabel lblTipo = new JLabel("Tipo de usuario:");
		GridBagConstraints gbc_lblTipo = new GridBagConstraints();
		gbc_lblTipo.anchor = GridBagConstraints.EAST;
		gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipo.gridx = 1;
		gbc_lblTipo.gridy = 5;
		getContentPane().add(lblTipo, gbc_lblTipo);

		buttonPostulante = new JRadioButton("Postulante");
		buttonGroup.add(buttonPostulante);
		GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
		gbc_rdbtnNewRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton.gridx = 2;
		gbc_rdbtnNewRadioButton.gridy = 5;
		getContentPane().add(buttonPostulante, gbc_rdbtnNewRadioButton);
		buttonPostulante.setSelected(true);

		buttonEmpresa = new JRadioButton("Empresa");

		buttonGroup.add(buttonEmpresa);
		GridBagConstraints gbc_rdbtnNewRadioButton_1 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_1.gridx = 3;
		gbc_rdbtnNewRadioButton_1.gridy = 5;
		getContentPane().add(buttonEmpresa, gbc_rdbtnNewRadioButton_1);

		dateF = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.gridwidth = 2;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 5;
		gbc_dateChooser.gridy = 5;
		getContentPane().add(dateF, gbc_dateChooser);
		JCalendar jCalendar = dateF.getJCalendar();
		Calendar minYear = Calendar.getInstance();
        Calendar maxYear = Calendar.getInstance();
		maxYear.set(Calendar.YEAR, 2023);
		jCalendar.setMaxSelectableDate(maxYear.getTime());
		minYear.set(Calendar.DAY_OF_MONTH,1);
		minYear.set(Calendar.MONTH,0);
		minYear.set(Calendar.YEAR, 1900);
        jCalendar.setMinSelectableDate(minYear.getTime());


		dateF.setMaxSelectableDate(maxYear.getTime());
		((JTextFieldDateEditor) (dateF.getDateEditor())).setEditable(false);



		JLabel lblNac_Link = new JLabel("Nacionalidad:");
		GridBagConstraints gbc_lblNac_Link = new GridBagConstraints();
		gbc_lblNac_Link.anchor = GridBagConstraints.EAST;
		gbc_lblNac_Link.insets = new Insets(0, 0, 5, 5);
		gbc_lblNac_Link.gridx = 1;
		gbc_lblNac_Link.gridy = 6;
		getContentPane().add(lblNac_Link, gbc_lblNac_Link);

		textNac_Link = new JTextField();
		GridBagConstraints gbc_textNac_Link = new GridBagConstraints();
		gbc_textNac_Link.gridwidth = 2;
		gbc_textNac_Link.insets = new Insets(0, 0, 5, 5);
		gbc_textNac_Link.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNac_Link.gridx = 2;
		gbc_textNac_Link.gridy = 6;
		getContentPane().add(textNac_Link, gbc_textNac_Link);
		textNac_Link.setColumns(10);

		JLabel lblDesc = new JLabel("Descripcion:");
		GridBagConstraints gbc_lblDesc = new GridBagConstraints();
		gbc_lblDesc.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblDesc.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesc.gridx = 1;
		gbc_lblDesc.gridy = 7;
		getContentPane().add(lblDesc, gbc_lblDesc);
		lblDesc.setVisible(false);

		// ACCION EMPRESA ACA ASI RECONOCE LOS TEXTOS DE ABAJO
		buttonEmpresa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// oculto la fecha
				lblFechaNac.setVisible(false);
				dateF.setVisible(false);
				// cambio nacionalidad por link
				lblNac_Link.setText("Link: (opcional)");
				// el asociado al link es textField_5
				lblDesc.setVisible(true);
				textDesc.setVisible(true);
				scrollPane.setVisible(true);
				textNac_Link.setText("");
			}
		});

		buttonPostulante.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// oculto la descripcion
				lblDesc.setVisible(false);
				textDesc.setVisible(false);
				scrollPane.setVisible(false);
				// cambio link por nacionalidad
				lblNac_Link.setText("Nacionalidad:");
				// el asociado al link es textField_5
				// pongo la fecha
				lblFechaNac.setVisible(true);
				dateF.setVisible(true);
				textNac_Link.setText("");
			}
		});

		JButton buttonAceptar = new JButton("Aceptar");
		buttonAceptar.setForeground(new Color(60, 179, 113));
		buttonAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(verificarFormulario()) {
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
						nombre = "media/img/Usuarios/default/default.jpeg";
					}
					if(buttonPostulante.isSelected()) {
						try {
							Calendar calendar = Calendar.getInstance();

					        calendar.setTime(dateF.getDate());
				            int anio = calendar.get(Calendar.YEAR);
				            int mes = calendar.get(Calendar.MONTH) + 1;
				            int dia = calendar.get(Calendar.DAY_OF_MONTH);
				            LocalDate localDate = LocalDate.of(anio, mes, dia);
				            Date f = Date.valueOf(localDate);

							contU.darAltaPostulante(textNick.getText().toLowerCase(),textNombre.getText(),textApellido.getText(),textCorreo.getText(),textField_contrasenia.getText(),nombre,f,textNac_Link.getText());
							setVisible(false);
							JOptionPane.showMessageDialog(null, "El usuario se ha creado con éxito", "Dar Alta Usuario",
			                        JOptionPane.INFORMATION_MESSAGE);
						}catch(CorreoUsuarioExistente ef){
							JOptionPane.showMessageDialog(null, "Ya existe un usuario con dicho correo", "Dar Alta Usuario",
				                    JOptionPane.ERROR_MESSAGE);
							textCorreo.setText("");

						} catch (NicknameUsuarioExistente e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "Ya existe un usuario con dicho nickname", "Dar Alta Usuario",
				                    JOptionPane.ERROR_MESSAGE);
							textNick.setText("");
						}
					} else { // empresa is selected
						try {
							String desc = textDesc.getText();
							contU.darAltaEmpresa(textNick.getText(),textNombre.getText(),textApellido.getText(),textCorreo.getText(),textField_contrasenia.getText(),nombre,desc,textNac_Link.getText());
							setVisible(false);
							JOptionPane.showMessageDialog(null, "El usuario se ha creado con éxito", "Dar Alta Usuario",
			                        JOptionPane.INFORMATION_MESSAGE);
						}catch(CorreoUsuarioExistente ef){
							JOptionPane.showMessageDialog(null, "Ya existe un usuario con dicho correo", "Dar Alta Usuario",
				                    JOptionPane.ERROR_MESSAGE);
							textCorreo.setText("");

						} catch (NicknameUsuarioExistente e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "Ya existe un usuario con dicho nickname", "Dar Alta Usuario",
				                    JOptionPane.ERROR_MESSAGE);
							textNick.setText("");
						}
					}
					if(imagen!=null) {
						try {
							Path copia = Paths.get("data/"+nombre);
							Path img = imagen.toPath();
							Files.copy(img, copia);
						}catch(IOException exc) {
							System.out.println("No se pudo guardar la foto");
						    exc.printStackTrace();
						}
					}
					vaciarFormulario();
				}
			}
		});


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
		gbc_textDesc.gridwidth = 3;
		gbc_textDesc.insets = new Insets(0, 0, 5, 5);
		gbc_textDesc.fill = GridBagConstraints.BOTH;
		gbc_textDesc.gridx = 2;
		gbc_textDesc.gridy = 7;
		//getContentPane().add(textDesc, gbc_textDesc);
		getContentPane().add(scrollPane, gbc_textDesc);

		GridBagConstraints gbc_buttonAceptar = new GridBagConstraints();
		gbc_buttonAceptar.insets = new Insets(0, 0, 0, 5);
		gbc_buttonAceptar.gridx = 2;
		gbc_buttonAceptar.gridy = 8;
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
		gbc_buttonCancelar.gridx = 4;
		gbc_buttonCancelar.gridy = 8;
		getContentPane().add(buttonCancelar, gbc_buttonCancelar);

	}

	private boolean verificarFormulario() {
		String nick = textNick.getText();
		String nombre = textNombre.getText();
		String apellido = textApellido.getText();
		String correo = textCorreo.getText();
		String contra = textField_contrasenia.getText();
		String contra2 = textField_confContrasenia.getText();
		// imagen opcional -> no se chequea nada


		if(nick.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || contra.isEmpty() || contra2.isEmpty() ||
			nick.matches("^\\s+$") || nombre.matches("^\\s+$") || apellido.matches("^\\s+$") || correo.matches("^\\s+$") || contra.matches("^\\s+$") || contra2.matches("^\\s+$")) {
				JOptionPane.showMessageDialog(this, "No puede haber campos vacíos", "Dar Alta Usuario", JOptionPane.ERROR_MESSAGE);
				return false;
		}
		else if(!nombre.matches("^[A-Za-zÁÉÍÓÚÑáéíóúñüÜ\\s'-]+$") || nombre.charAt(0) == ' ') {
			JOptionPane.showMessageDialog(this, "Nombre no valido", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(!contra.equals(contra2)) {
			JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(nick.contains(" ")) {
			JOptionPane.showMessageDialog(this, "Nick no valido (no debe tener espacios)", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if (nick.contains("@")) {
			JOptionPane.showMessageDialog(this, "Nick no valido (no debe tener arrobas)", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(correo.contains(" ")) {
			JOptionPane.showMessageDialog(this, "Correo no valido (no debe tener espacios)", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(!apellido.matches("^[A-Za-zÁÉÍÓÚÑáéíóúñüÜ\\s'-]+$") ||apellido.charAt(0) == ' ') {
			JOptionPane.showMessageDialog(this, "Apellido no valido", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// reviso correo
		int count = 0; // cant de @ en el correo
        for (char c : correo.toCharArray()) {
            if (c == '@') {
                count++;
            }
        }
        if (count != 1) {
            JOptionPane.showMessageDialog(this, "El correo debe contener exactamente un '@'", "Error de Correo", JOptionPane.ERROR_MESSAGE);
            return false;
        }

		/*CHEQUEO NO CAMPOS OBLIGATORIOS VACIOS*/
		if (buttonPostulante.isSelected()) {
			String nacionalidad = textNac_Link.getText();

			if(nacionalidad.isEmpty() || dateF.getDate()==null || nacionalidad.matches("^\\s+$")) {
				JOptionPane.showMessageDialog(this, "No puede haber campos vacíos", "Dar Alta Usuario",
	                    JOptionPane.ERROR_MESSAGE);
				return false;
			}
			if(!nacionalidad.matches("^[A-Za-zÁÉÍÓÚÑáéíóúñüÜ\\s'-]+$")) {
				JOptionPane.showMessageDialog(this, "Nacionalidad no valido(Solo acepta letras)", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
			return false;
			}
		}
		else { // caso empresa
			String desc = textDesc.getText();
			// link opcional, no se chequea
			if(desc.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No puede haber campos vacíos", "Dar Alta Usuario",
                    JOptionPane.ERROR_MESSAGE);
			return false;
			}
			else if(desc.length() > 1000) {
				JOptionPane.showMessageDialog(this, "La descripción no puede tener mas de 1000 caracteres", "Dar Alta Usuario", JOptionPane.ERROR_MESSAGE);
			return false;
			}
			String link = textNac_Link.getText();

			if(link.contains(" ")) {
				JOptionPane.showMessageDialog(this, "Link no valido (no debe tener espacios)", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
			return false;
			}
		}

		return true;

	}

	private void vaciarFormulario() {
		textNick.setText("");
		textNombre.setText("");
		textApellido.setText("");
		textCorreo.setText("");
		textNac_Link.setText("");
		textDesc.setText("");
		dateF.setDate(null);
		textField_contrasenia.setText("");
		textField_confContrasenia.setText("");
		imagen = null;
		lblEstadoImagen.setVisible(false);
	}

	private static void setPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
    }

}
