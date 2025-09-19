package presentacion;

import java.awt.Color;
import java.awt.Component;
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

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import excepciones.NombrePaqueteExistente;
import logica.IOferta;

public class CrearPaqueteVacio extends JInternalFrame {
	//controlador de oferta para accionar en el jinternalframe
	private IOferta interfazContOferta;
	private JTextField textFieldNombre;
	private JTextField textFieldValidez;
	private JTextField textFieldDescuento;
	private JTextArea textArea;
	private JLabel lblNombre;
	private JLabel lblValidez;
	private JLabel lblDescripcion;
	private JLabel lblFecha;
	private JButton btnCrear;
	private JButton btnCancelar;
	private JLabel lblTituloPaquete;
	private Component verticalStrut;
	private GridBagConstraints gbc_lblTituloPaquete;
	private JDateChooser dateF;
	private JLabel lblImagen;
	private JButton btnSubir;
	private JLabel lblEstadoImagen;
	private File imagen;


	/**
	 * Create the frame.
	 */
	public CrearPaqueteVacio(IOferta iOf) {
		interfazContOferta = iOf;
		setResizable(true);
        setTitle("Crear Paquete de Tipos de Publicacion de Oferta Laboral");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBounds(50, 50, 450, 300);
        setSize(573,313);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{94, 150, 25, 57, 67, 40, 50, 0};
        gridBagLayout.rowHeights = new int[]{75, 35, 37, 39, 53, 12, 44, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);

        /*NOMBRE*/

        lblTituloPaquete = new JLabel("Crear nuevo Paquete");
        lblTituloPaquete.setFont(new Font("Tahoma", Font.BOLD, 28));
        gbc_lblTituloPaquete = new GridBagConstraints();
        gbc_lblTituloPaquete.gridwidth = 7;
        gbc_lblTituloPaquete.insets = new Insets(0, 0, 5, 0);
        gbc_lblTituloPaquete.gridx = 0;
        gbc_lblTituloPaquete.gridy = 0;
        getContentPane().add(lblTituloPaquete, gbc_lblTituloPaquete);
        lblNombre= new JLabel("Nombre");
        GridBagConstraints gbc_lblTituloPaquete = new GridBagConstraints();
        gbc_lblTituloPaquete.anchor = GridBagConstraints.EAST;
        gbc_lblTituloPaquete.fill = GridBagConstraints.VERTICAL;
        gbc_lblTituloPaquete.insets = new Insets(0, 0, 5, 5);
        gbc_lblTituloPaquete.gridx = 0;
        gbc_lblTituloPaquete.gridy = 1;
        getContentPane().add(lblNombre, gbc_lblTituloPaquete);

        textFieldNombre = new JTextField();
        GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
        gbc_textFieldNombre.fill = GridBagConstraints.BOTH;
        gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldNombre.gridx = 1;
        gbc_textFieldNombre.gridy = 1;
        getContentPane().add(textFieldNombre, gbc_textFieldNombre);
        textFieldNombre.setColumns(10);


        /*VALIDEZ*/
        lblDescripcion = new JLabel("Descripcion");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.gridwidth = 2;
        gbc_lblNewLabel_1.anchor = GridBagConstraints.SOUTH;
        gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 3;
        gbc_lblNewLabel_1.gridy = 1;
        getContentPane().add(lblDescripcion, gbc_lblNewLabel_1);
        lblValidez= new JLabel("Validez (en dias)");
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_3.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_3.gridx = 0;
        gbc_lblNewLabel_3.gridy = 2;
        getContentPane().add(lblValidez, gbc_lblNewLabel_3);

        textFieldValidez = new JTextField();
        GridBagConstraints gbc_textFieldValidez = new GridBagConstraints();
        gbc_textFieldValidez.fill = GridBagConstraints.BOTH;
        gbc_textFieldValidez.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldValidez.gridx = 1;
        gbc_textFieldValidez.gridy = 2;
        getContentPane().add(textFieldValidez, gbc_textFieldValidez);
        textFieldValidez.setColumns(10);


        /*DESCRIPCION*/


		/*DESCUENTO*/

        verticalStrut = Box.createVerticalStrut(20);
        GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
        gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
        gbc_verticalStrut.gridx = 2;
        gbc_verticalStrut.gridy = 2;
        getContentPane().add(verticalStrut, gbc_verticalStrut);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea,
        		ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        textArea.setEditable(true);

        		textArea.setCaretPosition(textArea.getDocument().getLength());
        		textArea.setLineWrap(true);

        				GridBagConstraints gbc_textAreaDescripcion = new GridBagConstraints();
        				gbc_textAreaDescripcion.gridheight = 2;
        				gbc_textAreaDescripcion.gridwidth = 3;
        				gbc_textAreaDescripcion.insets = new Insets(0, 0, 5, 5);
        				gbc_textAreaDescripcion.fill = GridBagConstraints.BOTH;
        				gbc_textAreaDescripcion.gridx = 3;
        				gbc_textAreaDescripcion.gridy = 2;

        						getContentPane().add(scrollPane, gbc_textAreaDescripcion);
        lblDescripcion = new JLabel("Descuento");
        lblDescripcion.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_4.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_4.gridx = 0;
        gbc_lblNewLabel_4.gridy = 3;
        getContentPane().add(lblDescripcion, gbc_lblNewLabel_4);

        textFieldDescuento= new JTextField();
        GridBagConstraints gbc_textFieldDescuento = new GridBagConstraints();
        gbc_textFieldDescuento.fill = GridBagConstraints.BOTH;
        gbc_textFieldDescuento.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldDescuento.gridx = 1;
        gbc_textFieldDescuento.gridy = 3;
        getContentPane().add(textFieldDescuento, gbc_textFieldDescuento);
        textFieldDescuento.setColumns(10);


        /*FECHA*/
        lblFecha= new JLabel("Fecha de Alta ");
        GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
        gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_5.gridx = 0;
        gbc_lblNewLabel_5.gridy = 4;
        getContentPane().add(lblFecha, gbc_lblNewLabel_5);


        /*BOTONES*/
        btnCrear = new JButton("Crear");
        btnCrear.setForeground(new Color(60, 179, 113));
        btnCrear.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		if(crearPaquete(arg0)) {
        			borrarFormulario();
        			setVisible(false);
        		}
        	}
        });

        dateF = new JDateChooser();
        GridBagConstraints gbc_dateChooser = new GridBagConstraints();
        gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
        gbc_dateChooser.fill = GridBagConstraints.BOTH;
        gbc_dateChooser.gridx = 1;
        gbc_dateChooser.gridy = 4;
        getContentPane().add(dateF, gbc_dateChooser);

        lblImagen = new JLabel("Imagen:");
        GridBagConstraints gbc_lblImagen = new GridBagConstraints();
        gbc_lblImagen.anchor = GridBagConstraints.EAST;
        gbc_lblImagen.insets = new Insets(0, 0, 5, 5);
        gbc_lblImagen.gridx = 3;
        gbc_lblImagen.gridy = 4;
        getContentPane().add(lblImagen, gbc_lblImagen);

        btnSubir = new JButton("Subir");
        GridBagConstraints gbc_btnSubir = new GridBagConstraints();
        gbc_btnSubir.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnSubir.insets = new Insets(0, 0, 5, 5);
        gbc_btnSubir.gridx = 4;
        gbc_btnSubir.gridy = 4;
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

        lblEstadoImagen = new JLabel("OK");
        GridBagConstraints gbc_lblEstadoImagen = new GridBagConstraints();
        gbc_lblEstadoImagen.anchor = GridBagConstraints.WEST;
        gbc_lblEstadoImagen.insets = new Insets(0, 0, 5, 5);
        gbc_lblEstadoImagen.gridx = 5;
        gbc_lblEstadoImagen.gridy = 4;
        getContentPane().add(lblEstadoImagen, gbc_lblEstadoImagen);
        lblEstadoImagen.setVisible(false);

        GridBagConstraints gbc_btnCrear = new GridBagConstraints();
        gbc_btnCrear.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnCrear.insets = new Insets(0, 0, 0, 5);
        gbc_btnCrear.gridx = 1;
        gbc_btnCrear.gridy = 6;
        getContentPane().add(btnCrear, gbc_btnCrear);

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


        btnCancelar = new JButton("Cancelar");
        btnCancelar.setForeground(Color.RED);
        btnCancelar.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
	    		borrarFormulario();
	    		setVisible(false);
        	}
        });
        GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
        gbc_btnNewButton_1.gridwidth = 2;
        gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
        gbc_btnNewButton_1.gridx = 3;
        gbc_btnNewButton_1.gridy = 6;
        getContentPane().add(btnCancelar, gbc_btnNewButton_1);
	}

	private void setPlaceholder(JTextField textField, String placeholder) {

	}

	//Chequear datos coherentes
	private boolean formularioCorrecto() {
	        String nombreU = this.textFieldNombre.getText();
	        String des = this.textArea.getText();
	        String val = this.textFieldValidez.getText();
	        String descu = this.textFieldDescuento.getText();
	        int descuu;

	        if (nombreU.isEmpty() || des.isEmpty() || val.isEmpty() || descu.isEmpty() || dateF.getDate()==null) {
	            JOptionPane.showMessageDialog(this, "No puede haber campos vacíos", "Crear Paquete Vacio",
	                    JOptionPane.ERROR_MESSAGE);
	            return false;
	        }

	        if(!nombreU.matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑüÜ_.\\s]+$") ||nombreU.charAt(0) == ' '){
				JOptionPane.showMessageDialog(this, "Nombre no valido", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
			return false;
			}



	        try {
	            Integer.parseInt(val);
	            descuu = Integer.parseInt(descu);
	            if(descuu < 0 || descuu > 100) {
	            	JOptionPane.showMessageDialog(this, "El descuento es un porcentaje (0-100)", "Crear Paquete Vacio",
		                    JOptionPane.ERROR_MESSAGE);
	            textFieldDescuento.setText("");
	            return false;
	            }
	            if(Integer.parseInt(val)<=0) {
	    			JOptionPane.showMessageDialog(this, "Validez no valido", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
	    		return false;
	    		}
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "Los valores descuento y validez deben ser numeros", "Crear Paquete Vacio",
	                    JOptionPane.ERROR_MESSAGE);
	            textFieldValidez.setText("");
	    		textFieldDescuento.setText("");
	            return false;
	        }


	        return true;
	    }

	//Borrar informacion del formulario, pues ventanas no se borran sino que se ocultan
	private void borrarFormulario() {
		textFieldNombre.setText("");
		textFieldValidez.setText("");
		textFieldDescuento.setText("");
		dateF.setDate(null);
		textArea.setText("");
		lblEstadoImagen.setVisible(false);
	}

	private boolean crearPaquete(ActionEvent arg0) {
		if(formularioCorrecto()){
			String nombre = "";
			String ext = "";
			if(imagen!=null) {
				nombre = imagen.getName();
				ext = "";
				int i = nombre.lastIndexOf('.');
				ext = nombre.substring(i + 1);
				nombre = "media/img/Paquetes/" + textFieldNombre.getText().replaceAll("\\s", "_") +"."+ ext;
			}
			else {
				nombre = "media/img/Paquetes/default/default.jpg";
			}
    		try {


    			Calendar calendar = Calendar.getInstance();

		        calendar.setTime(dateF.getDate());
	            int anio = calendar.get(Calendar.YEAR);
	            int mes = calendar.get(Calendar.MONTH) + 1;
	            int dia = calendar.get(Calendar.DAY_OF_MONTH);
	            LocalDate localDate = LocalDate.of(anio, mes, dia);
	            Date f = Date.valueOf(localDate);

	            if(imagen!=null) {
					Path copia = Paths.get(nombre);
					Path img = imagen.toPath();
					System.out.println(copia.toAbsolutePath());
					Files.copy(img, copia);
				}

	            interfazContOferta.crearPaquete(textFieldNombre.getText(), textArea.getText(), Integer.parseInt(textFieldValidez.getText()), Double.parseDouble(textFieldDescuento.getText()),f,nombre);
    			JOptionPane.showMessageDialog(this, "El Paquete se ha creado con éxito", "Crear Paquete Vacio",
                        JOptionPane.INFORMATION_MESSAGE);



    			return true;
    		}catch (NombrePaqueteExistente e){
    			textFieldNombre.setText("");
    			JOptionPane.showMessageDialog(this, "Ya existe un paquete con el nombre ingresado", "Crear Paquete Vacio",
	                    JOptionPane.ERROR_MESSAGE);
    		}catch(IOException exc) {
    			JOptionPane.showMessageDialog(this, "No se pudo guardar la foto", "Error de validacion", JOptionPane.ERROR_MESSAGE);
				lblEstadoImagen.setText("ERROR");
    			lblEstadoImagen.setVisible(true);
			    exc.printStackTrace();
			}
		}return false;
	}

}
