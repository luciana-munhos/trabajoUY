package presentacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import logica.IOferta;

public class CrearTP extends JInternalFrame {
	IOferta contOferta = null;
	private JFormattedTextField textFieldNombre;
	private JFormattedTextField textFieldExp;
	private JFormattedTextField textFieldDur;
	private JFormattedTextField textFieldCosto;
	private JTextArea textAreaDescripcion;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JLabel lblError;

	private String nombre;
	private String descripcion;
	private String exposicionString;
	private String duracionString;
	private String costoString;
	private int exposicion;
	private int duracion;
	private double costo;
	private JDateChooser dateF;

	/**
	 * Create the frame.
	 */
	public CrearTP(IOferta io) {
		setTitle("Agregar tipo de publicación");
		setResizable(true);
		contOferta = io;
		setBounds(100, 100, 367, 419);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{84, 88, 97, 63, 0};
		gridBagLayout.rowHeights = new int[]{58, 45, 0, 0, 57, 0, 0, 31, 22, 20, 38, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		agregarComponentes();
	}

	private void agregarComponentes() {
		//Titulo
		JLabel lblNewLabel = new JLabel("Nuevo tipo de publicación");
		lblNewLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridwidth = 4;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		//subtitulo
		JLabel lblNewLabel_4 = new JLabel("Ingrese los datos del tipo de publicación que desea agregar");
		lblNewLabel_4.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel_4.gridwidth = 4;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 1;
		getContentPane().add(lblNewLabel_4, gbc_lblNewLabel_4);


		//formulario
		JLabel lblNombre = new JLabel("Nombre");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 2;
		getContentPane().add(lblNombre, gbc_lblNombre);


		textFieldNombre = new JFormattedTextField();
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.gridwidth = 2;
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.gridx = 1;
		gbc_textFieldNombre.gridy = 2;
		getContentPane().add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);




		JLabel lblDescripcin = new JLabel("Descripción");
		GridBagConstraints gbc_lblDescripcin = new GridBagConstraints();
		gbc_lblDescripcin.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcin.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcin.gridx = 0;
		gbc_lblDescripcin.gridy = 3;
		getContentPane().add(lblDescripcin, gbc_lblDescripcin);

		textAreaDescripcion = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textAreaDescripcion,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		textAreaDescripcion.setEditable(true);

		textAreaDescripcion.setCaretPosition(textAreaDescripcion.getDocument().getLength());
		textAreaDescripcion.setLineWrap(true);

		GridBagConstraints gbc_textAreaDescripcion = new GridBagConstraints();
		gbc_textAreaDescripcion.gridheight = 2;
		gbc_textAreaDescripcion.gridwidth = 2;
		gbc_textAreaDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_textAreaDescripcion.fill = GridBagConstraints.BOTH;
		gbc_textAreaDescripcion.gridx = 1;
		gbc_textAreaDescripcion.gridy = 3;

		getContentPane().add(scrollPane, gbc_textAreaDescripcion);

		JLabel lblNewLabel_1 = new JLabel("Exposición");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 5;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

		textFieldExp = new JFormattedTextField(NumberFormat.getNumberInstance());
		GridBagConstraints gbc_textFieldExp = new GridBagConstraints();
		gbc_textFieldExp.gridwidth = 2;
		gbc_textFieldExp.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldExp.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldExp.gridx = 1;
		gbc_textFieldExp.gridy = 5;
		getContentPane().add(textFieldExp, gbc_textFieldExp);
		textFieldExp.setColumns(10);

		JLabel lblDuracin = new JLabel("Duración");
		GridBagConstraints gbc_lblDuracin = new GridBagConstraints();
		gbc_lblDuracin.anchor = GridBagConstraints.EAST;
		gbc_lblDuracin.insets = new Insets(0, 0, 5, 5);
		gbc_lblDuracin.gridx = 0;
		gbc_lblDuracin.gridy = 6;
		getContentPane().add(lblDuracin, gbc_lblDuracin);

		textFieldDur = new JFormattedTextField();
		GridBagConstraints gbc_textFieldDur = new GridBagConstraints();
		gbc_textFieldDur.gridwidth = 2;
		gbc_textFieldDur.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDur.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDur.gridx = 1;
		gbc_textFieldDur.gridy = 6;
		getContentPane().add(textFieldDur, gbc_textFieldDur);
		textFieldDur.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Costo");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 7;
		getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);

		textFieldCosto = new JFormattedTextField();
		GridBagConstraints gbc_textFieldCosto = new GridBagConstraints();
		gbc_textFieldCosto.gridwidth = 2;
		gbc_textFieldCosto.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCosto.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCosto.gridx = 1;
		gbc_textFieldCosto.gridy = 7;
		getContentPane().add(textFieldCosto, gbc_textFieldCosto);
		textFieldCosto.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Fecha de alta");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 8;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);


		//botones
		btnGuardar = new JButton("Guardar");
		btnGuardar.setForeground(new Color(60, 179, 113));
		btnGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cmdDarAltaTP();
			}
		});

		lblError = new JLabel(" ");
		lblError.setFont(new Font("Tahoma", Font.ITALIC, 8));
		lblError.setVisible(false);

		dateF = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.gridwidth = 2;
		gbc_dateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.gridx = 1;
		gbc_dateChooser.gridy = 8;
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
        ((JTextFieldDateEditor) (dateF.getDateEditor())).setEditable(false);


		lblError.setForeground(Color.RED);
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.anchor = GridBagConstraints.WEST;
		gbc_lblError.gridwidth = 2;
		gbc_lblError.insets = new Insets(0, 0, 5, 5);
		gbc_lblError.gridx = 1;
		gbc_lblError.gridy = 9;
		getContentPane().add(lblError, gbc_lblError);
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.insets = new Insets(0, 0, 0, 5);
		gbc_btnGuardar.gridx = 1;
		gbc_btnGuardar.gridy = 10;
		getContentPane().add(btnGuardar, gbc_btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.RED);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				borrarFormulario();
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 2;
		gbc_btnCancelar.gridy = 10;
		getContentPane().add(btnCancelar, gbc_btnCancelar);


	}

	private void borrarFormulario() {
		textFieldNombre.setText("");
		textAreaDescripcion.setText("");
		textFieldExp.setText("");
		textFieldCosto.setText("");
		textFieldDur.setText("");
		dateF.setDate(null);
	}

	protected void cmdDarAltaTP(){
		if(checkFormulario()){
			try {
				Calendar calendar = Calendar.getInstance();

		        calendar.setTime(dateF.getDate());
	            int anio = calendar.get(Calendar.YEAR);
	            int mes = calendar.get(Calendar.MONTH) + 1;
	            int dia = calendar.get(Calendar.DAY_OF_MONTH);
	            LocalDate localDate = LocalDate.of(anio, mes, dia);
	            Date f = Date.valueOf(localDate);
				contOferta.altaTipoDePublicacion(nombre, descripcion, exposicion, duracion, costo, f);
				//show message dialog "Tipo de publicación agregado con éxito" with the name of the new TP
				JOptionPane.showMessageDialog(this, "Tipo de publicación '"+nombre+"' agregado con éxito", "Agregar tipo de publicación", JOptionPane.INFORMATION_MESSAGE);
				borrarFormulario();
				setVisible(false);
			}
			catch (Exception e){
				JOptionPane.showMessageDialog(this, e.getMessage(), "Agregar tipo de publicación", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private boolean checkFormulario(){
		nombre = textFieldNombre.getText();
		descripcion = textAreaDescripcion.getText();
		exposicionString = textFieldExp.getText();
		duracionString = textFieldDur.getText();
		costoString = textFieldCosto.getText();

		if(nombre.equals("") || nombre.matches("^\\s+$")){
			JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío", "Agregar tipo de publicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(descripcion.equals("") || descripcion.matches("^\\s+$")){
			JOptionPane.showMessageDialog(this, "La descripción no puede estar vacía", "Agregar tipo de publicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(exposicionString.equals("")|| exposicionString.matches("^\\s+$")){
			JOptionPane.showMessageDialog(this, "La exposición no puede estar vacía", "Agregar tipo de publicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(duracionString.equals("")|| duracionString.matches("^\\s+$")){
			JOptionPane.showMessageDialog(this, "La duración no puede estar vacía", "Agregar tipo de publicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(costoString.equals("")|| costoString.matches("^\\s+$")){
			JOptionPane.showMessageDialog(this, "El costo no puede estar vacío", "Agregar tipo de publicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(dateF.getDate()==null){
			JOptionPane.showMessageDialog(this, "La fecha de alta no puede estar vacía", "Agregar tipo de publicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(!nombre.matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑüÜ_.\\s]+$")){ //solo letras, numeros y espacios
			JOptionPane.showMessageDialog(this, "El nombre solo puede contener letras y espacios", "Agregar tipo de publicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(nombre.length() > 100){
			JOptionPane.showMessageDialog(this, "El nombre no puede tener mas de 100 caracteres", "Agregar tipo de publicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(descripcion.length() > 500){
			JOptionPane.showMessageDialog(this, "La descripción no puede tener mas de 500 caracteres", "Agregar tipo de publicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try{
			exposicion = Integer.parseInt(textFieldExp.getText());
		}
		catch (NumberFormatException e1){
			//exception bad input type
			JOptionPane.showMessageDialog(this, "El valor en el campo Exposición debe ser un número", "Agregar tipo de publicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try{
			duracion = Integer.parseInt(textFieldDur.getText());
		}
		catch (NumberFormatException e2){
			JOptionPane.showMessageDialog(this, "El valor en el campo Duración debe ser un número", "Agregar tipo de publicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try{
			costo = Double.parseDouble(textFieldCosto.getText());
			if (costo <= 0) {
				JOptionPane.showMessageDialog(this, "El valor en el campo Costo debe ser positivo", "Agregar tipo de publicación", JOptionPane.ERROR_MESSAGE);
				textFieldCosto.setText("");
				return false;
			}
		}
		catch (NumberFormatException e3){
			JOptionPane.showMessageDialog(this, "El valor en el campo Costo debe ser un número", "Agregar tipo de publicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;

	}

}
