package presentacion;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import excepciones.NoExisteEmpresa;
import excepciones.NoExisteKeyword;
import excepciones.NoExisteOferta;
import excepciones.NoExistePaquete;
import excepciones.NoExisteTipoPublicacion;
import excepciones.NoExistenEmpresas;
import excepciones.NoHayTPRestantes;
import excepciones.NombreOfertaExistente;
import logica.IOferta;
import logica.IUsuario;

public class AltaDeOfertaLaboral extends JInternalFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private IOferta IOfr;
	private IUsuario IUsr;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;
	private JComboBox comboBoxHoraInicio;
	private JComboBox comboBoxHoraFin;
	private JComboBox comboBoxMinutosInicio;
	private JComboBox comboBoxMinutosFin;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private DefaultListModel<String> keywordListModel;
	private JLabel lblNewLabel_10;
	private Set<String> Empresas;
	private Set<String> tiposPubli;
	private JDateChooser dateF;
	private JLabel lblEstadoImagen;
	private File imagen;
	private JComboBox comboPago;




	public AltaDeOfertaLaboral(IOferta iof, IUsuario iusr) {
		IOfr = iof;
		IUsr = iusr;
		Empresas = new HashSet<>();
		tiposPubli = new HashSet<>();




		setTitle("Alta de Oferta Laboral");
		setBounds(100, 100, 551, 564);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Seleccionar empresa:");
		lblNewLabel.setBounds(16, 6, 207, 30);
		getContentPane().add(lblNewLabel);

		comboBox = new JComboBox();
		comboBox.setForeground(Color.GRAY);
		comboBox.setBounds(235, 9, 234, 27);
		getContentPane().add(comboBox);

		JLabel lblNewLabel_1 = new JLabel("Seleccionar tipo de publicacion:");
		lblNewLabel_1.setBounds(16, 42, 218, 30);
		getContentPane().add(lblNewLabel_1);

		comboBox_1 = new JComboBox();
		comboBox_1.setForeground(Color.GRAY);
		comboBox_1.setBounds(235, 45, 234, 27);
		getContentPane().add(comboBox_1);

		JLabel lblNewLabel_2 = new JLabel("Ingrese nombre:");
		lblNewLabel_2.setBounds(16, 75, 151, 16);
		getContentPane().add(lblNewLabel_2);

		textField = new JTextField();
		textField.setBounds(235, 70, 234, 26);
		getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Descripcion:");
		lblNewLabel_3.setBounds(16, 103, 105, 16);
		getContentPane().add(lblNewLabel_3);

		textField_1 = new JTextField();
		textField_1.setBounds(235, 98, 234, 35);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);

		/*SELECTOR DE HORAS DE LA JORNADA LABORAL */
		JLabel lblNewLabel_4 = new JLabel("Hora de comienzo:");
		lblNewLabel_4.setBounds(16, 149, 151, 16);
		getContentPane().add(lblNewLabel_4);

		// Selector de hora de inicio
		comboBoxHoraInicio = new JComboBox<>();
        comboBoxHoraInicio.setForeground(Color.GRAY);
        comboBoxHoraInicio.setBounds(235, 145, 70, 27);
        getContentPane().add(comboBoxHoraInicio);
        for (int i = 0; i < 24; i++) {
            comboBoxHoraInicio.addItem(String.format("%02d", i));
        }

        // Selector de minutos de inicio
		comboBoxMinutosInicio = new JComboBox<>();
        comboBoxMinutosInicio.setForeground(Color.GRAY);
        comboBoxMinutosInicio.setBounds(317, 145, 70, 27);
        getContentPane().add(comboBoxMinutosInicio);

        JLabel lblNewLabel_5 = new JLabel("Hora de finalizacion:");
        lblNewLabel_5.setBounds(16, 188, 167, 16);
        getContentPane().add(lblNewLabel_5);
        for (int i = 0; i < 60; i++) {
            comboBoxMinutosInicio.addItem(String.format("%02d", i));
        }

        //Selector de hora de finalización
		comboBoxHoraFin = new JComboBox<>();
        comboBoxHoraFin.setForeground(Color.GRAY);
        comboBoxHoraFin.setBounds(235, 184, 70, 27);
        getContentPane().add(comboBoxHoraFin);
        for (int i = 0; i < 24; i++) {
            comboBoxHoraFin.addItem(String.format("%02d", i));
        }

        //Selector de minutos de finalización
		comboBoxMinutosFin = new JComboBox<>();
        comboBoxMinutosFin.setForeground(Color.GRAY);
        comboBoxMinutosFin.setBounds(317, 184, 70, 27);
        getContentPane().add(comboBoxMinutosFin);

        JLabel lblNewLabel_6 = new JLabel("Remuneracion:");
        lblNewLabel_6.setBounds(16, 218, 117, 16);
        getContentPane().add(lblNewLabel_6);

        textField_2 = new JTextField();
        textField_2.setBounds(235, 213, 234, 26);
        getContentPane().add(textField_2);
        textField_2.setColumns(10);
        for (int i = 0; i < 60; i++) {
            comboBoxMinutosFin.addItem(String.format("%02d", i));
        }

        /* SELECTOR DE KEYWORDS */
        JLabel lblNewLabel_7 = new JLabel("Keywords:");
        lblNewLabel_7.setBounds(16, 340, 105, 16);
        getContentPane().add(lblNewLabel_7);

        //Selector de Keywords, es un JList que tiene multiple interval selection
        keywordListModel = new DefaultListModel<>();
        JList<String> keywordList = new JList<>(keywordListModel);
        keywordList.setForeground(Color.BLACK);
        keywordList.setBounds(235, 340, 233, 59);
        keywordList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); //permite seleccionar varios items de la lista
        getContentPane().add(keywordList);

        //para agregar keywords a la lista
        JButton addButton = new JButton("Agregar");
        addButton.setBounds(149, 345, 85, 29);
        getContentPane().add(addButton);


        addButton.addActionListener(e -> {
            try {
                Set<String> availableKeywords = IOfr.listarNombresKeywords();
                if (!availableKeywords.isEmpty()) {
                    JComboBox<String> keywordComboBox = new JComboBox<>(availableKeywords.toArray(new String[0]));
                    int result = JOptionPane.showConfirmDialog(
                            this,
                            keywordComboBox,
                            "Seleccione una palabra clave:",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );

                    if (result == JOptionPane.OK_OPTION) {
                        String selectedKeyword = keywordComboBox.getSelectedItem().toString();
                        if (keywordListModel.contains(selectedKeyword)) {
                            JOptionPane.showMessageDialog(this,"Ya has agregado esta palabra clave.","Aviso",JOptionPane.WARNING_MESSAGE);
                        } else {
                            keywordListModel.addElement(selectedKeyword);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No hay palabras clave disponibles.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NoExisteKeyword ex) {
                JOptionPane.showMessageDialog(this, "No hay palabras clave en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });



        //para remover keywords de la lista
        JButton removeButton = new JButton("Eliminar");
        removeButton.setBounds(149, 370, 85, 29);
        getContentPane().add(removeButton);

        removeButton.addActionListener(e -> {
            int[] selectedIndices = keywordList.getSelectedIndices(); //Obtengo los indices q seleccione en la JList
            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                keywordListModel.removeElementAt(selectedIndices[i]); //Remuevo del modelo las keywords que seleccione
            }

        });


        JLabel lblNewLabel_8 = new JLabel("Ciudad:");
        lblNewLabel_8.setBounds(16, 251, 61, 16);
        getContentPane().add(lblNewLabel_8);

        textField_3 = new JTextField();
        textField_3.setBounds(235, 246, 234, 26);
        getContentPane().add(textField_3);
        textField_3.setColumns(10);

        JLabel lblNewLabel_9 = new JLabel("Departamento:");
        lblNewLabel_9.setBounds(16, 279, 134, 16);
        getContentPane().add(lblNewLabel_9);

        //JComboBox comboBox_2 = new JComboBox();
        comboBox_2 = new JComboBox();
        comboBox_2.setForeground(Color.GRAY);
        comboBox_2.setBounds(235, 275, 234, 27);
        getContentPane().add(comboBox_2);

        Set<String> departs = new HashSet<>();
		departs.add("Artigas");
		departs.add("Canelones");
		departs.add("Cerro Largo");
		departs.add("Colonia");
		departs.add("Durazno");
		departs.add("Flores");
		departs.add("Florida");
		departs.add("Lavalleja");
		departs.add("Maldonado");
		departs.add("Montevideo");
		departs.add("Paysandú");
		departs.add("Río Negro");
		departs.add("Rivera");
		departs.add("Rocha");
		departs.add("Salto");
		departs.add("San José");
		departs.add("Soriano");
		departs.add("Tacuarembó");
		departs.add("Treinta y Tres");

		for(String d : departs) {
			comboBox_2.addItem(d);
		}

		JLabel lblNewLabel_11 = new JLabel("Fecha de alta:");
        lblNewLabel_11.setBounds(16, 307, 191, 16);
        getContentPane().add(lblNewLabel_11);

        dateF = new JDateChooser();
        dateF.setBounds(235, 313, 134, 20);
        getContentPane().add(dateF);
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

        //CANCELAR
        JButton btnNewButton_1 = new JButton("Cancelar");
        btnNewButton_1.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
	    		borrarFormulario();
	    		setVisible(false);
        	}
        });
        btnNewButton_1.setForeground(new Color(255, 0, 0));
        btnNewButton_1.setBounds(259, 478, 140, 29);
        getContentPane().add(btnNewButton_1);


        //CREAR
        JButton btnNewButton = new JButton("Crear");
        btnNewButton.setForeground(new Color(60, 179, 113));
        btnNewButton.setBounds(138, 478, 117, 29);
        getContentPane().add(btnNewButton);

        JLabel lblImagen = new JLabel("Imagen:");
        lblImagen.setBounds(16, 415, 45, 13);
        getContentPane().add(lblImagen);

        JButton btnSubir = new JButton("Subir");
        btnSubir.setBounds(71, 411, 85, 21);
        getContentPane().add(btnSubir);

        lblEstadoImagen = new JLabel("OK");
        lblEstadoImagen.setBounds(159, 415, 45, 13);
        getContentPane().add(lblEstadoImagen);

        JLabel lblPago = new JLabel("Pago:");
        lblPago.setBounds(260, 415, 45, 13);
        getContentPane().add(lblPago);

        comboPago = new JComboBox();
        comboPago.setEnabled(false);
        comboPago.setBounds(296, 411, 173, 21);
        getContentPane().add(comboPago);
        lblEstadoImagen.setVisible(false);

        btnSubir.addActionListener(ev -> {
		      JFileChooser fc = new JFileChooser(System.getProperty("user.home"));
		      fc.setAcceptAllFileFilterUsed(false);
		      fc.addChoosableFileFilter(new FileNameExtensionFilter("Imagenes",
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



        btnNewButton.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		if(crearOferta(arg0)) {
        			borrarFormulario();
        			setVisible(false);
        		}
        	}
        });

        comboBox.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent ev) {
        		try {
					cargarPaquetes();
				} catch (NoExisteEmpresa e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });



        comboBox_1.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent ev) {
        		try {
					cargarPaquetes();
				} catch (NoExisteEmpresa e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
	}

	private void cargarPaquetes() throws NoExisteEmpresa{
    	comboPago.removeAllItems();
		comboPago.addItem("Sin paquete");
    	if(comboBox_1.getSelectedItem()!=null &&comboBox.getSelectedItem()!=null) {
			Set<String> paquetes = IUsr.listarPaquetesAdquiridosVigentesTipo(comboBox_1.getSelectedItem().toString(),comboBox.getSelectedItem().toString());

			comboPago.setEnabled(paquetes.size()>0);
			for(String paq : paquetes) {
				comboPago.addItem(paq);
			}
    	}
    }


	private void borrarFormulario() {
		textField.setText("");
        textField_1.setText("");
        textField_2.setText("");
        textField_3.setText("");
		dateF.setDate(null);

		comboBox.removeAllItems(); //Empresas
		comboBox_1.removeAllItems(); //Tipos de publi
		comboBox_2.setSelectedIndex(0); //Departamentos
		comboBoxHoraInicio.setSelectedIndex(0);
		comboBoxMinutosInicio.setSelectedIndex(0);
		comboBoxHoraFin.setSelectedIndex(0);
		comboBoxMinutosFin.setSelectedIndex(0);
		keywordListModel.clear();

		lblEstadoImagen.setVisible(false);
		comboPago.removeAllItems();
	}

	//CHEQUEO LOS DATOS ESTEN COHERENTES
	private boolean chequearDatos() {
	    String empresa = this.comboBox.getSelectedItem().toString();
	    String tipo = this.comboBox_1.getSelectedItem().toString();
	    String nombre = this.textField.getText().trim();
	    String descripcion = this.textField_1.getText();
	    Time horaInicio = Time.valueOf(comboBoxHoraInicio.getSelectedItem().toString() + ":" + comboBoxMinutosInicio.getSelectedItem().toString() + ":00");
	    Time horaFin = Time.valueOf(comboBoxHoraFin.getSelectedItem().toString() + ":" + comboBoxMinutosFin.getSelectedItem().toString() + ":00");
	    String remuneracion = this.textField_2.getText();
	    String ciudad = this.textField_3.getText();
	    String departamento = this.comboBox_2.getSelectedItem().toString();

	    //Veo que los campos rellenables no esten vacios
	    if (empresa.isEmpty() || tipo.isEmpty() || nombre.isEmpty() || descripcion.isEmpty() || remuneracion.isEmpty() || ciudad.isEmpty() || dateF.getDate()==null) {
	        JOptionPane.showMessageDialog(this, "No puede haber campos vacios", "Error de validación", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }
	    if(nombre.charAt(0) == ' ') {
			JOptionPane.showMessageDialog(this, "Nombre no valido", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
		return false;
		}
	    if(!ciudad.matches("^[a-zA-ZÁÉÍÓÚáéíóúñÑÜü\\s]+$")) {
			JOptionPane.showMessageDialog(this, "Ciudad no valida", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
		return false;
		}

	    if (comboBox.getItemCount() == 0) {
	        JOptionPane.showMessageDialog(this, "No existen empresas en el sistema. Debes dar de alta tu empresa antes de publicar una oferta.", "Error", JOptionPane.ERROR_MESSAGE);
	        dispose(); //cierro ventana
	    }
	    try {
	        String name = this.textField.getText().trim();
	    } catch (IllegalArgumentException e) {
	        JOptionPane.showMessageDialog(this, "El nombre solo puede contener letras.", "Error de validación", JOptionPane.ERROR_MESSAGE);
	        textField.setText("");
	        return false;
	    }
	    try {
	    	String desc = this.textField_1.getText();
	    } catch (IllegalArgumentException e) {
	        JOptionPane.showMessageDialog(this, "La descripcion solo puede ser un texto escrito.", "Error de validación", JOptionPane.ERROR_MESSAGE);
	        textField_1.setText("");
	        return false;
	    }
	    try {
	        String city = this.textField_3.getText();
	    } catch (IllegalArgumentException e) {
	        JOptionPane.showMessageDialog(this, "La ciudad solo puede contener letras.", "Error de validación", JOptionPane.ERROR_MESSAGE);
	        textField_3.setText("");
	        return false;
	    }
	    try {
	        double remuneracionValor = Double.parseDouble(remuneracion);
	        if(remuneracionValor<=0) {
				JOptionPane.showMessageDialog(this, "Remuneracion no valida", "Dar Alta Usuario",JOptionPane.ERROR_MESSAGE);
			return false;
			}
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "La remuneración debe ser un valor numérico válido.", "Error de validación", JOptionPane.ERROR_MESSAGE);
	        textField_2.setText("");
	        return false;
	    }

	    if (horaInicio.compareTo(horaFin)==0) {
	        JOptionPane.showMessageDialog(this, "La hora de inicio no puede ser igual a la hora de fin", "Error de validación", JOptionPane.ERROR_MESSAGE);
	        return false;
        }

	    return true;
	}

	private Set<String> getKeywordsFromListModel(DefaultListModel<String> model) {
	    Set<String> keywords = new HashSet<>();
	    for (int i = 0; i < model.size(); i++) {
	        keywords.add(model.getElementAt(i));
	    }
	    return keywords;
	}

	private String formatoTime(int hours, int minutes) {
	    String formattedHours = String.format("%02d", hours);
	    String formattedMinutes = String.format("%02d", minutes);
	    return formattedHours + ":" + formattedMinutes;
	}

	protected boolean crearOferta(ActionEvent arg0) {
		if(chequearDatos()){
			String nombrePaq = "";
			if(!comboPago.getSelectedItem().toString().equals("Sin paquete")) {
				nombrePaq = comboPago.getSelectedItem().toString();
			}
			Set<String> keywords = getKeywordsFromListModel(keywordListModel); // consigo las keywords del modelo

			//Paso horario a string con el formato deseado
			String horarioInicio = formatoTime(
	                Integer.parseInt(comboBoxHoraInicio.getSelectedItem().toString()),
	                Integer.parseInt(comboBoxMinutosInicio.getSelectedItem().toString())
	            );

	            String horarioFin = formatoTime(
	                Integer.parseInt(comboBoxHoraFin.getSelectedItem().toString()),
	                Integer.parseInt(comboBoxMinutosFin.getSelectedItem().toString())
	            );
	            String horarioJornada = horarioInicio + " - " + horarioFin;

    		try {
    			double remuneracion = Double.parseDouble(textField_2.getText());
    			Calendar calendar = Calendar.getInstance();

		        calendar.setTime(dateF.getDate());
	            int anio = calendar.get(Calendar.YEAR);
	            int mes = calendar.get(Calendar.MONTH) + 1;
	            int dia = calendar.get(Calendar.DAY_OF_MONTH);
	            LocalDate localDate = LocalDate.of(anio, mes, dia);
	            Date fechaAlta = Date.valueOf(localDate);

	            String nombre = "";
				String ext = "";
				if(imagen!=null) {
					nombre = imagen.getName();
					ext = "";
					int i = nombre.lastIndexOf('.');
					ext = nombre.substring(i + 1);
					nombre = "media/img/OfertasLaborales/" + textField.getText().replaceAll("\\s", "_") +"."+ ext;
					Path copia = Paths.get("data/"+nombre);
					Path img = imagen.toPath();
					Files.copy(img, copia);
				}
				else {
					nombre = "media/img/OfertasLaborales/default/default.jpg";
				}
				String nombreTipo = comboBox_1.getSelectedItem().toString();
				String empresa = comboBox.getSelectedItem().toString();

    			IOfr.ingresarOferta(empresa, nombreTipo, textField.getText(), textField_1.getText(), horarioJornada, remuneracion, textField_3.getText(), comboBox_2.getSelectedItem().toString(),fechaAlta, keywords,nombre);
    			if(!nombrePaq.equals(""))
    				IOfr.pagarConPaquete(textField.getText(),nombrePaq, nombreTipo, empresa);

    			JOptionPane.showMessageDialog(this, "La oferta se ha creado con éxito", "Alta de oferta", JOptionPane.INFORMATION_MESSAGE);
    			return true;
    		}catch (NombreOfertaExistente e){
    			textField.setText("");
    			JOptionPane.showMessageDialog(this, "Ya existe una oferta con el nombre ingresado", "Error de validacion", JOptionPane.ERROR_MESSAGE);
    			return false;
    		} catch (NoExisteEmpresa e) {
    			JOptionPane.showMessageDialog(this, "No hay empresas en el sistema, debe darse de alto antes que una oferta.", "Error de validacion", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (NoExisteTipoPublicacion e) {
    			JOptionPane.showMessageDialog(this, "No hay tipo de publicacion en el sistema, debe darse de alto antes que una oferta.", "Error de validacion", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (NoExisteKeyword e) { //ESTO HAY Q CAMBIARLO DESPUES
    			JOptionPane.showMessageDialog(this, "No hay keywords en el sistema, debe darse de alto antes que una oferta.", "Error de validacion", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}catch(IOException exc) {
    			JOptionPane.showMessageDialog(this, "No se pudo guardar la foto", "Error de subida", JOptionPane.ERROR_MESSAGE);
				lblEstadoImagen.setText("ERROR");
    			lblEstadoImagen.setVisible(true);
			    exc.printStackTrace();
			}catch(NoExistePaquete e) {
    			JOptionPane.showMessageDialog(this, "No existe el paquete en el sistema", "Error de validacion", JOptionPane.ERROR_MESSAGE);
    			e.printStackTrace();
			} catch (NoExisteOferta e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoHayTPRestantes e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}return false;
	}

	public boolean cargarDatosPrevios() {
		try { //Si Empresas sigue vacio dsps de intentar de llenar -> no hay empresas
			Empresas = IUsr.mostrarEmpresas();
			for(String e: Empresas) {
				comboBox.addItem(e);
			}
		}catch (NoExistenEmpresas efe) {
			JOptionPane.showMessageDialog(null, "Aun no existen empresas.", "Alta de Oferta", JOptionPane.ERROR_MESSAGE);
			borrarFormulario();
    		return false;
		}
		try {
		    tiposPubli = IOfr.listarNombresTipos();
		    for(String t : tiposPubli) {
		        comboBox_1.addItem(t);
		    }
		    // Si tiposPubli sigue vacio dsps de intentar de llenar -> no hay tipos de publi
		    if (tiposPubli.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Aun no existen Tipos de Publicacion.", "Alta de Oferta",
		                JOptionPane.ERROR_MESSAGE);
		        return false;
		    }
		} catch (NoExisteTipoPublicacion efe) {
		    JOptionPane.showMessageDialog(null, efe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		    borrarFormulario();
		    return false;
		}

		return true;
	}
}
