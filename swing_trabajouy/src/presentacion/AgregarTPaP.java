package presentacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import excepciones.NoExistePaquete;
import excepciones.NoExisteTipoPublicacion;
import logica.DTPaquete;
import logica.IOferta;

public class AgregarTPaP extends JInternalFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String tSeleccionado;
	private String pSeleccionado;
	private int numero;
	private IOferta ContOferta;

	private JButton btnGuardar;
	private JButton btnCancelar;
	private JLabel labelcant_1;
	private JTextField textFieldC;
	private JComboBox<String> comboBoxP;
	private JComboBox<String> comboBoxT;



	/**
	 * Launch the application.
	 * @throws NoExistePaquete
	 * @throws NoExisteTipoPublicacion
	 */
	public AgregarTPaP(IOferta iOf){
		ContOferta = iOf;
        setTitle("Agregar Tipo de Publicacion a Paquete");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBounds(50, 50, 450, 300);
        setSize(509,240);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{128, 150, 182, 119, 0};
        gridBagLayout.rowHeights = new int[]{59, 25, 25, 25, 20, 46};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        getContentPane().setLayout(gridBagLayout);

       JLabel lblNewLabel_2 = new JLabel("Agregar Tipo de Publicación a Paquete");
       lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 21));
       GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
       gbc_lblNewLabel_2.gridwidth = 4;
       gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
       gbc_lblNewLabel_2.gridx = 0;
       gbc_lblNewLabel_2.gridy = 0;
       getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);





        JLabel lblNewLabel_1 = new JLabel("Ingresa el Tipo de Publicacion:");

        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 1;
        gbc_lblNewLabel_1.gridy = 1;
        getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);




	    //ComboBox de TIPO


	    comboBoxT = new JComboBox<>();
	    GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
	    gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
	    gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
	    gbc_comboBox_1.gridx = 2;
	    gbc_comboBox_1.gridy = 1;
	    getContentPane().add(comboBoxT, gbc_comboBox_1);

        comboBoxT.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	 tSeleccionado = (String) comboBoxT.getSelectedItem();
        	}
        });







                       JLabel lblNewLabel = new JLabel("Ingresa el Paquete:");
                       GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
                       gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
                       gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
                       gbc_lblNewLabel.gridx = 1;
                       gbc_lblNewLabel.gridy = 2;
                       getContentPane().add(lblNewLabel, gbc_lblNewLabel);



                //ComboBox de Paquete


                comboBoxP = new JComboBox<>();
                GridBagConstraints gbc_comboBox = new GridBagConstraints();
                gbc_comboBox.insets = new Insets(0, 0, 5, 5);

                gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
                gbc_comboBox.gridx = 2;
                gbc_comboBox.gridy = 2;
                getContentPane().add(comboBoxP, gbc_comboBox);

                comboBoxP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                		pSeleccionado = (String) comboBoxP.getSelectedItem();
                	}
                });








                //atributo


                labelcant_1 = new JLabel("Cantidad de Tipos por Paquete");
                GridBagConstraints gbc_labelcant_1 = new GridBagConstraints();
                gbc_labelcant_1.anchor = GridBagConstraints.EAST;
                gbc_labelcant_1.insets = new Insets(0, 0, 5, 5);
                gbc_labelcant_1.gridx = 1;
                gbc_labelcant_1.gridy = 3;
                getContentPane().add(labelcant_1, gbc_labelcant_1);

                textFieldC = new JTextField();
                GridBagConstraints gbc_textField = new GridBagConstraints();
                gbc_textField.fill = GridBagConstraints.HORIZONTAL;
                gbc_textField.insets = new Insets(0, 0, 5, 5);
                gbc_textField.gridx = 2;
                gbc_textField.gridy = 3;
                getContentPane().add(textFieldC, gbc_textField);
                textFieldC.setColumns(10);

                        textFieldC.addActionListener(new ActionListener() {
                        	@Override
                			public void actionPerformed(ActionEvent e) {
                        		if(!textFieldC.getText().isEmpty())   {

                        			Integer.parseInt(textFieldC.getText());
                        		}
                        		else {

                        		}
                        	}

                        });



        //BOTONES






        btnGuardar = new JButton("Guardar");
        btnGuardar.setForeground(new Color(60, 179, 113));
        GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
        gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
        gbc_btnNewButton_1.gridx = 1;
        gbc_btnNewButton_1.gridy = 5;
        getContentPane().add(btnGuardar, gbc_btnNewButton_1);
        btnGuardar.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg1) {

        			 try {
        		           numero = Integer.parseInt(textFieldC.getText());
        		           if(numero<1) {
        		        	   JOptionPane.showMessageDialog(null, "No puede ser un numero menor a 1","Agregar Tipo de Publicacion a Paquete",JOptionPane.ERROR_MESSAGE);

        		           }
        		           else {
        		        	   agregarAPaquete(arg1);
        		           }
        		     	} catch (NumberFormatException e) {
        		     		JOptionPane.showMessageDialog(null, "El valor debe ser un numero","Agregar Tipo de Publicacion a Paquete",JOptionPane.ERROR_MESSAGE);
        		        }


        	}

        });

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setForeground(Color.RED);
        GridBagConstraints gbc_btnNewButton_21 = new GridBagConstraints();
        gbc_btnNewButton_21.insets = new Insets(0, 0, 0, 5);
        gbc_btnNewButton_21.gridx = 2;
        gbc_btnNewButton_21.gridy = 5;
        getContentPane().add(btnCancelar, gbc_btnNewButton_21);
        btnCancelar.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg2) {
        			borrarFormulario();
        			setVisible(false);

        	}
        });



	}



	private void borrarFormulario() {
		textFieldC.setText("");
	}


	protected boolean agregarAPaquete(ActionEvent arg0) {
		if((comboBoxT.getSelectedItem()=="Elegir Tipo de Publicacion") || comboBoxP.getSelectedItem() =="Elegir Paquete"){
			JOptionPane.showMessageDialog(null, "No puede haber campos vacíos","Agregar Tipo de Publicacion a Paquete",JOptionPane.ERROR_MESSAGE);
		}
		else {

				try {
					DTPaquete p = ContOferta.getInfoPaquete(this.pSeleccionado);
					Set<String> tipos = p.getTiposPub();
					if(tipos.contains(this.tSeleccionado)) {
						JOptionPane.showMessageDialog(this, "El Tipo de publicacion ya esta en ese Paquete, ","Agregar Tipo de Publicacion a Paquete",JOptionPane.ERROR_MESSAGE);
					}
					else {
						ContOferta.ingresarTipoAPaquete(this.pSeleccionado,this.tSeleccionado,Integer.parseInt(textFieldC.getText()));
						JOptionPane.showMessageDialog(this, "El tipo de publicación se agrego con éxito.", "Agregar Tipo de Publicacion a Paquete",JOptionPane.INFORMATION_MESSAGE);
						setVisible(false);
						borrarFormulario();
					}
				} catch (NoExisteTipoPublicacion e) {
					// TODO Auto-generated catch block

				} catch (NoExistePaquete e) {
					// TODO Auto-generated catch block
				}


		}
		return true;
	}









	public void abrir() {

		comboBoxT.removeAllItems();
		try {
			Set<String> tiposT = ContOferta.listarNombresTipos();
			for(String t : tiposT)
				comboBoxT.addItem(t);

			comboBoxP.removeAllItems();

			comboBoxP.addItem("Elegir Paquete");
			comboBoxP.setSelectedItem("Elegir Paquete");

			Set<String> tiposP = ContOferta.listarPaquetesLibres();
			for(String s : tiposP)
				comboBoxP.addItem(s);
			comboBoxT.setSelectedItem(-1);
			setVisible(true);
		}
		catch(NoExisteTipoPublicacion e) {
			//show dialog
			JOptionPane.showMessageDialog(this, "No hay tipos de publicacion registrados", "Agregar Tipo de Publicacion a Paquete",JOptionPane.ERROR_MESSAGE);
		}

	}

}

