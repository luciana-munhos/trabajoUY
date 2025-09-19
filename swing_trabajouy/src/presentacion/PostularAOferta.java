package presentacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

import excepciones.ErrorFecha;
import excepciones.NoExisteEmpresa;
import excepciones.NoExisteOferta;
import excepciones.NoExisteOfertaEmpresa;
import excepciones.NoExistePostulante;
import excepciones.NoExistenEmpresas;
import excepciones.NoTieneOfertasConfirmadasVigentes;
import excepciones.OfertaExpirada;
import excepciones.PostulantePoseeOferta;
import logica.DTOferta;
import logica.IUsuario;


public class PostularAOferta extends JInternalFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private IUsuario IUsr;
	private DTOfertaComple ofertaComple;
	private JLabel ingresePos;
	private JLabel ingreseEmpresa;
	private JLabel ingreseOferta;
	private JLabel cvRed;
	private JLabel fecha;
	private JLabel motivacion;
	private Set<String> Empresas;
	private Set<String> Postulantes;
	private JComboBox<String> comboEmpresa;
	private JComboBox<String> comboOferta;
	private JComboBox<String> comboPostulante;
	private JTextArea textArea;
	private JTextArea textArea_1;
	private JButton btnPostular;
	private JButton btnCancelar;
	private JLabel lblNewLabel;
	private JDateChooser dateF;


	public PostularAOferta(IUsuario iu,DTOfertaComple frame) {
		IUsr = iu;
		ofertaComple = frame;
		Empresas = new HashSet<>();
		Postulantes = new HashSet<>();
		setResizable(true);
		setTitle("Postulacion a Oferta Laboral");
		setBounds(120, 100, 579, 385);


		GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] {100, 80, 56, 0, 120, 5};
        gridBagLayout.rowHeights = new int[] { 50, 30, 47, 17, 30, 30, 30, 30, 0, 48, 0};
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0, 0.0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 0.0 ,0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
        getContentPane().setLayout(gridBagLayout);
         /*ELEGIR EMPRESA*/

        lblNewLabel = new JLabel("Postular a oferta");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 29));
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.gridwidth = 6;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        getContentPane().add(lblNewLabel, gbc_lblNewLabel);


		/*ZONA ELEGIR OFERTA*/
        		ingreseEmpresa= new JLabel("Empresa:");
        		ingreseEmpresa.setHorizontalAlignment(SwingConstants.RIGHT);
        		GridBagConstraints gbc_lblIngreseNombre = new GridBagConstraints();
        		gbc_lblIngreseNombre.fill = GridBagConstraints.HORIZONTAL;
        		gbc_lblIngreseNombre.insets = new Insets(0, 0, 5, 5);
        		gbc_lblIngreseNombre.gridx = 0;
        		gbc_lblIngreseNombre.gridy = 2;
        		getContentPane().add(ingreseEmpresa, gbc_lblIngreseNombre);


        		comboEmpresa = new JComboBox<>();
        		GridBagConstraints gbc_elegirEmpresa = new GridBagConstraints();
        		gbc_elegirEmpresa.gridwidth = 2;
        		gbc_elegirEmpresa.fill = GridBagConstraints.HORIZONTAL;
        		gbc_elegirEmpresa.insets = new Insets(0, 0, 5, 5);
        		gbc_elegirEmpresa.gridx = 1;
        		gbc_elegirEmpresa.gridy = 2;
        		getContentPane().add(comboEmpresa , gbc_elegirEmpresa);
        		comboEmpresa.addItemListener(new ItemListener() {
        			@Override
        			public void itemStateChanged(ItemEvent arg0) {
        				if(arg0.getStateChange()==ItemEvent.SELECTED) { //Si selecciono una opcion permito obtener ofertas
        					ofertaComple.setVisible(false);

        					if(comboOferta != null) {


        						comboOferta.removeAllItems();
        						comboOferta.addItem("Elegir Oferta");

        					}
        					if(!comboEmpresa.getSelectedItem().toString().equals("Elegir Empresa")) {
        						//Mostras las ofertas adecuadas
        						/*A comentar*/
        						/*Set<String> ofertas = new HashSet<String>();
        						ofertas.add("of1");
        						ofertas.add("of2");*/
        						try {
        							Set<String> ofertas = IUsr.mostrarOfertasVigentesEmpresa(comboEmpresa.getSelectedItem().toString());
        							for(String o: ofertas) {
        								comboOferta.addItem(o);
        							}
        							comboOferta.setEnabled(true);
        							btnPostular.setEnabled(true);

        						}catch (NoExisteOfertaEmpresa neoe){
        							JOptionPane.showMessageDialog(null, "La empresa no posee ofertas", "Postular a Oferta",
        				                    JOptionPane.ERROR_MESSAGE);
        						} catch (NoExisteEmpresa e) {
	        				JOptionPane.showMessageDialog(null, "No existe la empresa", "Postular a Oferta",
	        	                    JOptionPane.ERROR_MESSAGE);
        						} catch (NoTieneOfertasConfirmadasVigentes e) {
        							JOptionPane.showMessageDialog(null, "No tiene ofertas confirmadas vigentes", "Postular a Oferta",
        	        	                    JOptionPane.ERROR_MESSAGE);
									comboOferta.setEnabled(false);
									btnPostular.setEnabled(false);
								}

        					}
        				}
        			}
        		});
        fecha = new JLabel("Fecha postulacion:");
        fecha.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseF= new GridBagConstraints();
        gbc_lblIngreseF.anchor = GridBagConstraints.SOUTHWEST;
        gbc_lblIngreseF.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseF.gridx = 4;
        gbc_lblIngreseF.gridy = 2;
        getContentPane().add(fecha, gbc_lblIngreseF);
        ingreseOferta= new JLabel("Oferta:");
        ingreseOferta.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseO= new GridBagConstraints();
        gbc_lblIngreseO.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblIngreseO.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseO.gridx = 0;
        gbc_lblIngreseO.gridy = 3;
        getContentPane().add(ingreseOferta, gbc_lblIngreseO);
        ingreseOferta.setHorizontalAlignment(SwingConstants.RIGHT);

                comboOferta = new JComboBox<>();
                GridBagConstraints gbc_elegirOferta= new GridBagConstraints();
                gbc_elegirOferta.gridwidth = 2;
                gbc_elegirOferta.fill = GridBagConstraints.HORIZONTAL;
                gbc_elegirOferta.insets = new Insets(0, 0, 5, 5);
                gbc_elegirOferta.gridx = 1;
                gbc_elegirOferta.gridy = 3;
                getContentPane().add(comboOferta, gbc_elegirOferta);
                comboOferta.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0){
				if(arg0.getStateChange()==ItemEvent.SELECTED) { //Si selecciono una opcion muestro la info detallada y luego los postulantes
					if(!comboOferta.getSelectedItem().toString().equals("Elegir Oferta")) {
						/*Mostar Oferta completa*/
						DTOferta oferta;
						try {
							oferta = IUsr.mostrarInfoDetalladaOferta(comboOferta.getSelectedItem().toString());
							ofertaComple.cargarDatos(oferta);
							ofertaComple.setVisible(true);
							/*if(ofertaComple.cargarDatos(oferta)) {
								ofertaComple.setVisible(true);
							}*/
						} catch (NoExisteOferta e) {
							JOptionPane.showMessageDialog(null, "No existe la oferta", "Postular a Oferta",
	        	                    JOptionPane.ERROR_MESSAGE);
						}


						/*Fin mostrar oferta completa*/
					}
					}
				}
			});
                comboOferta.addItem("Elegir Oferta");

        				dateF = new JDateChooser();
        				GridBagConstraints gbc_dateChooser = new GridBagConstraints();
        				gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
        				gbc_dateChooser.fill = GridBagConstraints.BOTH;
        				gbc_dateChooser.gridx = 4;
        				gbc_dateChooser.gridy = 3;
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


        				ingresePos= new JLabel("Postulante:");
        				GridBagConstraints gbc_lblIngreseP= new GridBagConstraints();
        				gbc_lblIngreseP.anchor = GridBagConstraints.EAST;
        				gbc_lblIngreseP.insets = new Insets(0, 0, 5, 5);
        				gbc_lblIngreseP.gridx = 0;
        				gbc_lblIngreseP.gridy = 4;
        				getContentPane().add(ingresePos, gbc_lblIngreseP);


        		comboPostulante = new JComboBox<>();
        		GridBagConstraints gbc_comboPostulante = new GridBagConstraints();
        		gbc_comboPostulante.anchor = GridBagConstraints.SOUTH;
        		gbc_comboPostulante.fill = GridBagConstraints.HORIZONTAL;
        		gbc_comboPostulante.insets = new Insets(0, 0, 5, 5);
        		gbc_comboPostulante.gridwidth = 2;
        		gbc_comboPostulante.gridx = 1;
        		gbc_comboPostulante.gridy = 4;
        		getContentPane().add(comboPostulante, gbc_comboPostulante);
        /*MOTIVACION*/
        motivacion= new JLabel("Motivacion:");
        motivacion.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseM= new GridBagConstraints();
        gbc_lblIngreseM.anchor = GridBagConstraints.SOUTHWEST;
        gbc_lblIngreseM.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseM.gridx = 4;
        gbc_lblIngreseM.gridy = 4;
        getContentPane().add(motivacion, gbc_lblIngreseM);
        cvRed= new JLabel("CV Reducido:");
        cvRed.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseC= new GridBagConstraints();
        gbc_lblIngreseC.anchor = GridBagConstraints.SOUTHEAST;
        gbc_lblIngreseC.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseC.gridx = 0;
        gbc_lblIngreseC.gridy = 5;
        getContentPane().add(cvRed, gbc_lblIngreseC);


        textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		textArea.setCaretPosition(textArea.getDocument().getLength());
		textArea.setLineWrap(true);

        GridBagConstraints gbc_textArea = new GridBagConstraints();
        gbc_textArea.gridwidth = 2;
        gbc_textArea.gridheight = 4;
        gbc_textArea.insets = new Insets(0, 0, 5, 5);
        gbc_textArea.fill = GridBagConstraints.BOTH;
        gbc_textArea.gridx = 1;
        gbc_textArea.gridy = 5;
		getContentPane().add(scrollPane, gbc_textArea);
		JScrollPane scrollPane_1 = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        GridBagConstraints gbc_textArea_1 = new GridBagConstraints();
        gbc_textArea_1.gridheight = 4;
        gbc_textArea_1.insets = new Insets(0, 0, 5, 5);
        gbc_textArea_1.fill = GridBagConstraints.BOTH;
        gbc_textArea_1.gridx = 4;
        gbc_textArea_1.gridy = 5;
        getContentPane().add(scrollPane_1, gbc_textArea_1);


                textArea_1 = new JTextArea();
                scrollPane_1.setViewportView(textArea_1);

                		textArea_1.setCaretPosition(textArea.getDocument().getLength());
                		textArea_1.setLineWrap(true);

        btnPostular = new JButton("Postular");
        btnPostular.setForeground(new Color(60, 179, 113));
        btnPostular.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		if(validarFormulario()) {
        			try {
        				Calendar calendar = Calendar.getInstance();

				        calendar.setTime(dateF.getDate());
			            int anio = calendar.get(Calendar.YEAR);
			            int mes = calendar.get(Calendar.MONTH) + 1;
			            int dia = calendar.get(Calendar.DAY_OF_MONTH);
			            LocalDate localDate = LocalDate.of(anio, mes, dia);
			            Date f = Date.valueOf(localDate);

        				IUsr.postularAOferta(comboPostulante.getSelectedItem().toString(),textArea.getText(),textArea_1.getText(),f,comboOferta.getSelectedItem().toString());

        				JOptionPane.showMessageDialog(null, "Postulación exitosa.", "Postular a Oferta",
        	                    JOptionPane.INFORMATION_MESSAGE);
        				setVisible(false);
        				frame.setVisible(false);
        				borrarFormulario();
        			}catch (NoExisteOferta no) {
        				JOptionPane.showMessageDialog(null, "No existe la oferta", "Postular a Oferta",
        	                    JOptionPane.ERROR_MESSAGE);
        			} catch (NoExistePostulante ep) {
        				JOptionPane.showMessageDialog(null, "El postulante no existe", "Postular a Oferta",
        	                    JOptionPane.ERROR_MESSAGE);
					} catch (PostulantePoseeOferta e) {
						JOptionPane.showMessageDialog(null, "El postulante ya esta postulado a dicha empresa", "Postular a Oferta",
        	                    JOptionPane.ERROR_MESSAGE);
					} catch (ErrorFecha ef) {
						JOptionPane.showMessageDialog(null, "La fecha de postulacion es anterior a la fecha de alta de la oferta", "Postular a Oferta",
        	                    JOptionPane.ERROR_MESSAGE);
					} catch(OfertaExpirada oe) {
						JOptionPane.showMessageDialog(null, "La fecha de postulacion es posterior al vencimiento de la oferta", "Postular a Oferta",
        	                    JOptionPane.ERROR_MESSAGE);
					}
        		}

        	}
        });
        GridBagConstraints gbc_btnPostular = new GridBagConstraints();
        gbc_btnPostular.insets = new Insets(0, 0, 0, 5);
        gbc_btnPostular.gridx = 1;
        gbc_btnPostular.gridy = 9;
        getContentPane().add(btnPostular, gbc_btnPostular);

                btnCancelar = new JButton("Cancelar");
                btnCancelar.setForeground(Color.RED);
                btnCancelar.addActionListener(new ActionListener() {
                	@Override
			public void actionPerformed(ActionEvent arg0) {
                		borrarFormulario();
                		setVisible(false);
                		frame.setVisible(false);
                	}
                });
                GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
                gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
                gbc_btnCancelar.gridx = 4;
                gbc_btnCancelar.gridy = 9;
                getContentPane().add(btnCancelar, gbc_btnCancelar);

	}

	private void borrarFormulario() {
		textArea.setText("");
		textArea_1.setText("");
		dateF.setDate(null);
		comboEmpresa.removeAllItems();
		comboOferta.removeAllItems();
		comboPostulante.removeAllItems();
	}

	private boolean validarFormulario() {
		String e = comboEmpresa.getSelectedItem().toString();
		String p = comboPostulante.getSelectedItem().toString();
		String o = comboOferta.getSelectedItem().toString();
		String cv = textArea.getText();
		String mot = textArea_1.getText();
		if (e.equals("Elegir empresa") || o.equals("Elegir Oferta") || p.equals("Elegir Postulante") || dateF.getDate()==null || cv.isEmpty() || mot.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No puede haber campos vacíos", "Postular a Oferta",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }


		return true;
	}

	public boolean cargarDatosPrevios() {
		comboEmpresa.addItem("Elegir Empresa");
		try {
			Empresas = IUsr.mostrarEmpresas();
			for(String e: Empresas) {
				comboEmpresa.addItem(e);
			}

		}catch (NoExistenEmpresas efe) {
			JOptionPane.showMessageDialog(null, "Aun no existen empresas", "Postular a Oferta",
                    JOptionPane.ERROR_MESSAGE);
			borrarFormulario();
    		return false;
		}

		comboPostulante.addItem("Elegir Postulante");
		try {
			Postulantes = IUsr.mostrarPostulantes();
			for(String p: Postulantes) {
				comboPostulante.addItem(p);
			}
		}catch (Exception efe) {
			JOptionPane.showMessageDialog(null, "Aun no existen postulantes", "Postular a Oferta",
                    JOptionPane.ERROR_MESSAGE);
			borrarFormulario();
    		return false;
		}
		return true;
	}
}
