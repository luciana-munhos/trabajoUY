package presentacion;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import excepciones.NoExistePaquete;
import excepciones.NoExisteTipoPublicacion;
import logica.DTPaquete;
import logica.DTTipoPublicacion;
import logica.IOferta;

public class ConsultarPaquete extends JInternalFrame{

	private IOferta ContOferta;
	private String  pSeleccionado;
	private String  tSeleccionado;
	private Set<String> tipos;

	private JComboBox<String> comboBoxP;
	private DTPaquete p;
	private DTTipoPublicacion tp;
	private JComboBox<String> comboBoxT;
	private JLabel  lblA;
	private JButton btnTerminar;
	private JLabel lblB;
	private JLabel lblC;
	private JLabel lblD;
	private JTable table2;
	private JLabel lblE;
	private JTable table1;
	private JLabel lblDesc1;
	private JTextArea textAreat2;
	private JLabel lblDesc2;
	private JTextArea textAreat1;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JLabel lblImagen;
	private JTextField textFieldImagen;
	private JButton btnVerImagen;
	private Map<String, Integer> cantidades;
	private JLabel lblCant;

	public ConsultarPaquete(IOferta iOf, Imagen imaIntFrame) {
		getContentPane().setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().setForeground(Color.WHITE);
		getContentPane().setBackground(new Color(240, 240, 240));
		ContOferta = iOf;
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBounds(50, 50, 450, 300);
        setSize(569,470);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{10, 121, 41, 121, 46, 10, 114, 10, 10};
        gridBagLayout.rowHeights = new int[]{57, 50, 106, 10, 44, 44, 10, 10, 10, 60};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0};
        getContentPane().setLayout(gridBagLayout);

                lblA = new JLabel("Consultar Paquete");
                lblA.setFont(new Font("Tahoma", Font.BOLD, 30));
                GridBagConstraints gbc_lblA = new GridBagConstraints();
                gbc_lblA.gridwidth = 7;
                gbc_lblA.insets = new Insets(0, 0, 5, 0);
                gbc_lblA.gridx = 1;
                gbc_lblA.gridy = 0;
                getContentPane().add(lblA, gbc_lblA);

                lblB = new JLabel("Paquete:");
                lblB.setFont(new Font("Tahoma", Font.BOLD, 11));
                GridBagConstraints gbc_lblSeleccionarUnPaquete = new GridBagConstraints();
                gbc_lblSeleccionarUnPaquete.anchor = GridBagConstraints.EAST;
                gbc_lblSeleccionarUnPaquete.insets = new Insets(0, 0, 5, 5);
                gbc_lblSeleccionarUnPaquete.gridx = 1;
                gbc_lblSeleccionarUnPaquete.gridy = 1;
                getContentPane().add(lblB, gbc_lblSeleccionarUnPaquete);

                        // COMBOBOX DE PAQUETES

                        comboBoxP = new JComboBox<>();
                        GridBagConstraints gbc_comboBox = new GridBagConstraints();
                        gbc_comboBox.gridwidth = 2;
                        gbc_comboBox.insets = new Insets(0, 0, 5, 5);
                        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
                        gbc_comboBox.gridx = 2;
                        gbc_comboBox.gridy = 1;
                        getContentPane().add(comboBoxP, gbc_comboBox);



                                // AGARRAR LA INFO DEL COMBOBOX DE PAQUETES
                                comboBoxP.addActionListener(new ActionListener() {
                                        	@Override
                                        	public void actionPerformed(ActionEvent e1) {
                                			 		pSeleccionado = (String) comboBoxP.getSelectedItem();
                                        			obtenerInfoP(pSeleccionado);

                                            }
                                            });

        lblDesc1 = new JLabel("Descripcion:");
        lblDesc1.setFont(new Font("Tahoma", Font.BOLD, 11));
        GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
        gbc_lblDescripcion.anchor = GridBagConstraints.SOUTH;
        gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
        gbc_lblDescripcion.gridx = 5;
        gbc_lblDescripcion.gridy = 1;
        getContentPane().add(lblDesc1, gbc_lblDescripcion);
        lblDesc1.setVisible(false);





        lblC = new JLabel("Informacion");
        lblC.setFont(new Font("Tahoma", Font.BOLD, 11));
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_3.gridx = 1;
        gbc_lblNewLabel_3.gridy = 2;
        getContentPane().add(lblC, gbc_lblNewLabel_3);
        lblC.setVisible(false);

        table1 = new JTable(5,2);
        table1.setEnabled(false);
        table1.setBorder(new LineBorder(Color.BLACK, 1, true));
        table1.setBackground(new Color(240, 240, 240));
        table1.setFont(new Font("Tahoma", Font.BOLD, 11));
        GridBagConstraints gbc_table_2 = new GridBagConstraints();
        gbc_table_2.gridwidth = 3;
        gbc_table_2.insets = new Insets(0, 0, 5, 5);
        gbc_table_2.fill = GridBagConstraints.BOTH;
        gbc_table_2.gridx = 2;
        gbc_table_2.gridy = 2;
        getContentPane().add(table1, gbc_table_2);
        table1.setVisible(false);

        textAreat1 = new JTextArea();
        textAreat1.setBackground(new Color(240, 240, 240));
        textAreat1.setFont(new Font("Tahoma", Font.BOLD, 12));
        textAreat1.setLineWrap(true);
        GridBagConstraints gbc_textArea = new GridBagConstraints();
        gbc_textArea.gridwidth = 4;
        gbc_textArea.insets = new Insets(0, 0, 5, 5);
        gbc_textArea.fill = GridBagConstraints.BOTH;
        gbc_textArea.gridx = 4;
        gbc_textArea.gridy = 3;
        getContentPane().add(textAreat1, gbc_textArea);
        textAreat1.setVisible(false);
        textAreat1.setEditable(false);

        scrollPane = new JScrollPane(textAreat1);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 2;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 5;
        gbc_scrollPane.gridy = 2;
        getContentPane().add(scrollPane, gbc_scrollPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPane.setVisible(false);

        lblImagen = new JLabel("Imagen");
        lblImagen.setFont(new Font("Tahoma", Font.BOLD, 10));
        GridBagConstraints gbc_lblImagen = new GridBagConstraints();
        gbc_lblImagen.anchor = GridBagConstraints.EAST;
        gbc_lblImagen.insets = new Insets(0, 0, 5, 5);
        gbc_lblImagen.gridx = 1;
        gbc_lblImagen.gridy = 3;
        getContentPane().add(lblImagen, gbc_lblImagen);

        textFieldImagen = new JTextField();
        textFieldImagen.setEditable(false);
        GridBagConstraints gbc_textFieldImagen = new GridBagConstraints();
        gbc_textFieldImagen.gridwidth = 4;
        gbc_textFieldImagen.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldImagen.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldImagen.gridx = 2;
        gbc_textFieldImagen.gridy = 3;
        getContentPane().add(textFieldImagen, gbc_textFieldImagen);
        textFieldImagen.setColumns(10);

        btnVerImagen = new JButton("Ver imagen");
        btnVerImagen.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		//show image in a new window
        		imaIntFrame.cargarImagen(p.getImagen());
                imaIntFrame.setVisible(true);
        	}
        });
        GridBagConstraints gbc_btnVerImagen = new GridBagConstraints();
        gbc_btnVerImagen.insets = new Insets(0, 0, 5, 5);
        gbc_btnVerImagen.gridx = 6;
        gbc_btnVerImagen.gridy = 3;
        getContentPane().add(btnVerImagen, gbc_btnVerImagen);


        lblD = new JLabel("Tipo de Publicacion:");
        lblD.setFont(new Font("Tahoma", Font.BOLD, 11));
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.gridx = 1;
        gbc_lblNewLabel_2.gridy = 4;
        getContentPane().add(lblD, gbc_lblNewLabel_2);
        lblD.setVisible(false);


	    //COMBOBOX CON LOS TIPOS ASOCIADOS A ESE PAQUETE

	    comboBoxT= new JComboBox<>();
	    GridBagConstraints gbc_comboBox1 = new GridBagConstraints();
	    gbc_comboBox1.gridwidth = 2;
	    gbc_comboBox1.insets = new Insets(0, 0, 5, 5);
	    gbc_comboBox1.fill = GridBagConstraints.HORIZONTAL;
	    gbc_comboBox1.gridx = 2;
	    gbc_comboBox1.gridy = 4;
	    getContentPane().add(comboBoxT, gbc_comboBox1);
	    comboBoxT.setVisible(false);



                            // AGARRAR LA INFO DEL COMBOBOX DE TIPOS

        comboBoxT.addActionListener(new ActionListener() {
        	@Override
        public void actionPerformed(ActionEvent e2) {

        			tSeleccionado = (String) comboBoxT.getSelectedItem();
        			obtenerInfoT(tSeleccionado);

        	}
        });

        lblCant = new JLabel("[2]");
        GridBagConstraints gbc_lblCant = new GridBagConstraints();
        gbc_lblCant.insets = new Insets(0, 0, 5, 5);
        gbc_lblCant.gridx = 4;
        gbc_lblCant.gridy = 4;
        getContentPane().add(lblCant, gbc_lblCant);
        lblCant.setVisible(false);

        lblDesc2 = new JLabel("Descripcion:");
        lblDesc2.setFont(new Font("Tahoma", Font.BOLD, 11));
        GridBagConstraints gbc_lblNewLabel1 = new GridBagConstraints();
        gbc_lblNewLabel1.anchor = GridBagConstraints.SOUTH;
        gbc_lblNewLabel1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel1.gridx = 5;
        gbc_lblNewLabel1.gridy = 4;
        getContentPane().add(lblDesc2, gbc_lblNewLabel1);
        lblDesc2.setVisible(false);




        lblE = new JLabel("Información:");
        lblE.setFont(new Font("Tahoma", Font.BOLD, 11));
        GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblNewLabel_4.gridheight = 4;
        gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_4.gridx = 1;
        gbc_lblNewLabel_4.gridy = 5;
        getContentPane().add(lblE, gbc_lblNewLabel_4);
        lblE.setVisible(false);

        table2 = new JTable(5,2);
        table2.setEnabled(false);
        table2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        table2.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc_table = new GridBagConstraints();
        gbc_table.gridheight = 4;
        gbc_table.gridwidth = 3;
        gbc_table.insets = new Insets(0, 0, 5, 5);
        gbc_table.fill = GridBagConstraints.BOTH;
        gbc_table.gridx = 2;
        gbc_table.gridy = 5;
        getContentPane().add(table2, gbc_table);
        table2.setVisible(false);

        textAreat2 = new JTextArea();
        textAreat2.setFont(new Font("Tahoma", Font.BOLD, 12));
        textAreat2.setBackground(new Color(240, 240, 240));
        textAreat2.setLineWrap(true);
        GridBagConstraints gbc_textArea_1 = new GridBagConstraints();
        gbc_textArea_1.gridheight = 4;
        gbc_textArea_1.gridwidth = 4;
        gbc_textArea_1.insets = new Insets(0, 0, 5, 5);
        gbc_textArea_1.fill = GridBagConstraints.BOTH;
        gbc_textArea_1.gridx = 4;
        gbc_textArea_1.gridy = 5;
        getContentPane().add(textAreat2, gbc_textArea_1);
        textAreat2.setVisible(false);
        textAreat2.setEditable(false);

        scrollPane_1 = new JScrollPane(textAreat2);
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.gridwidth = 2;
        gbc_scrollPane_1.gridheight = 4;
        gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.gridx = 5;
        gbc_scrollPane_1.gridy = 5;
        getContentPane().add(scrollPane_1, gbc_scrollPane_1);
        scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPane_1.setVisible(false);




        btnTerminar = new JButton("Terminar Consulta");
        btnTerminar.setBackground(Color.WHITE);
        btnTerminar.setFont(new Font("Tahoma", Font.BOLD, 11));
        GridBagConstraints gbc_btnTerminar = new GridBagConstraints();
        gbc_btnTerminar.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnTerminar.gridwidth = 3;
        gbc_btnTerminar.insets = new Insets(0, 0, 0, 5);
        gbc_btnTerminar.gridx = 2;
        gbc_btnTerminar.gridy = 9;
        getContentPane().add(btnTerminar, gbc_btnTerminar);
        btnTerminar.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg2) {
        		imaIntFrame.setVisible(false);
        		setVisible(false);


        	}
        });
	}




	private void obtenerInfoP(String pSeleccionado) {
		try {
				p = ContOferta.getInfoPaquete(pSeleccionado);
				tipos=p.getTiposPub();
				obtenernombresTipos(tipos);

				cantidades = p.getCantTotales();
				lblCant.setVisible(false);

			 	table1.setValueAt("Nombre", 0, 0);
			 	table1.setValueAt("Validez", 1, 0);
			 	table1.setValueAt("Descuento", 2, 0);
			 	table1.setValueAt("Costo", 3, 0);
			 	table1.setValueAt("Fecha de Alta", 4, 0);
			 	//table1.setValueAt("Cantidad de Ofertas", 5, 0);
			 	table1.setValueAt(p.getNombre(), 0, 1);
			 	table1.setValueAt(p.getValidez(), 1, 1);
			 	table1.setValueAt(p.getDescuento(), 2, 1);
			 	table1.setValueAt(p.getCosto(), 3, 1);
			 	table1.setValueAt(p.getFechaAlta(), 4, 1);
			 	//table1.setValueAt(p.getCantidadOfertas(), 5, 1);
			 	textAreat1.setText(p.getDescripcion());

			 	textFieldImagen.setText(p.getImagen());

			 	textAreat1.setVisible(true);
		        scrollPane.setVisible(true);

			 	lblDesc1.setVisible(true);
			 	table1.setVisible(true);
			 	lblC.setVisible(true);

		} catch (NoExistePaquete e) {
			// TODO Auto-generated catch block
			}

	}

	//llenado del combobox de tipo

	private void obtenernombresTipos(Set<String> tipos){
		comboBoxT.removeAllItems();

		table2.setVisible(false);
		lblE.setVisible(false);
		lblDesc2.setVisible(false);
		textAreat2.setVisible(false);
        scrollPane_1.setVisible(false);

		if(tipos.size()!=0) {
			comboBoxT.addItem("");
			for (String element : tipos) {
	        	comboBoxT.addItem(element);
			}
			comboBoxT.setVisible(true);
			lblD.setVisible(true);
		}else {
			comboBoxT.setVisible(false);
			lblD.setVisible(false);

		}

	}

	//cargar la tabla de tipo

	private void obtenerInfoT(String tSeleccionado){
				try {
					if(tSeleccionado!=null && tipos.size()!=0 && tSeleccionado!="") {
						tp = ContOferta.getInfoTipoPublicacion(tSeleccionado);

						table2.setValueAt("Nombre", 0, 0);
						table2.setValueAt("Exposicion", 1, 0);
						table2.setValueAt("Duracion", 2, 0);
						table2.setValueAt("Costo", 3, 0);
						table2.setValueAt("Fecha de Alta", 4, 0);
						table2.setValueAt(tp.getNombre(), 0, 1);
					 	table2.setValueAt(tp.getExposicion(), 1, 1);
					 	table2.setValueAt(tp.getDuracion(), 2, 1);
					 	table2.setValueAt(tp.getCosto(), 3, 1);
					 	table2.setValueAt(tp.getFechaAlta(), 4, 1);
					 	textAreat2.setText(tp.getDescripcion());
					 	table2.setVisible(true);
						lblE.setVisible(true);
						textAreat2.setVisible(true);
				        scrollPane_1.setVisible(true);

						lblDesc2.setVisible(true);
						lblCant.setText(cantidades.get(tSeleccionado).toString());
						lblCant.setVisible(true);

					}


				} catch (NoExisteTipoPublicacion e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}






	}



	public void abrir() {
		comboBoxP.removeAllItems();
		comboBoxT.removeAllItems();
		comboBoxT.setVisible(false);
		table2.setVisible(false);
		lblE.setVisible(false);
		textAreat2.setVisible(false);
		textFieldImagen.setText("");

        scrollPane_1.setVisible(false);

		lblDesc2.setVisible(false);
		Set<String> tiposP = ContOferta.listarPaquetesRegistrados();
		for(String s : tiposP)
			comboBoxP.addItem(s);
		if(comboBoxP.getItemCount()==0) {
     	   JOptionPane.showMessageDialog(null, "No hay Paquetes","Consultar Paquete",JOptionPane.ERROR_MESSAGE);
     	   setVisible(false);
		}else {
			setVisible(true);
		}

	}

}
