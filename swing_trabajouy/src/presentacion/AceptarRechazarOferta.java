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
import javax.swing.WindowConstants;

import excepciones.NoExisteEmpresa;
import excepciones.NoExisteOferta;
import excepciones.NoExistenEmpresas;
import logica.IUsuario;

public class AceptarRechazarOferta extends JInternalFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private IUsuario ContUsuario;

	private JComboBox<String> comboO;
	private JComboBox<String> comboE;
	private JLabel textoE;
	private JLabel textoOf;
	private JLabel titulo;
	private JButton aceptar;
	private JButton rechazar;

	private String ESeleccionado;
	private String OSeleccionado;
	private JButton cancelar;
	private  Set<String> ofertas;


	public AceptarRechazarOferta(IUsuario iUs){
		ContUsuario = iUs;
        setTitle("Aceptar/Rechazar Oferta");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBounds(50, 50, 450, 300);
        setSize(360,318);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{64, 38, 49, 75, 82, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 44, 0, 25, 25, 25, 20, 70};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        getContentPane().setLayout(gridBagLayout);

        titulo = new JLabel("Aceptar o Rechazar Ofertas Laborales");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 15));
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.gridheight = 3;
        gbc_lblNewLabel_2.gridwidth = 5;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel_2.gridx = 0;
        gbc_lblNewLabel_2.gridy = 1;
        getContentPane().add(titulo, gbc_lblNewLabel_2);

        textoE = new JLabel("Empresas :");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.gridwidth = 2;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 4;
        getContentPane().add(textoE, gbc_lblNewLabel);

        comboE = new JComboBox<>();
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.gridwidth = 2;
        gbc_comboBox.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.gridx = 2;
        gbc_comboBox.gridy = 4;
        getContentPane().add(comboE, gbc_comboBox);

        textoOf = new JLabel("Ofertas : ");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.gridwidth = 2;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 6;
        getContentPane().add(textoOf, gbc_lblNewLabel_1);

        comboO = new JComboBox<>();
        GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
        gbc_comboBox_1.gridwidth = 2;
        gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_1.gridx = 2;
        gbc_comboBox_1.gridy = 6;
        getContentPane().add(comboO, gbc_comboBox_1);

         aceptar = new JButton("Aceptar");
         aceptar.setForeground(new Color(0, 128, 0));
        GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
        gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_1.gridx = 1;
        gbc_btnNewButton_1.gridy = 8;
        getContentPane().add(aceptar, gbc_btnNewButton_1);

         rechazar = new JButton("Rechazar");
         rechazar.setForeground(new Color(0, 128, 0));
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton.gridx = 3;
        gbc_btnNewButton.gridy = 8;
        getContentPane().add(rechazar, gbc_btnNewButton);

        cancelar = new JButton("Cancelar");
        cancelar.setForeground(Color.RED);
        cancelar.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        	}
        });
        GridBagConstraints gbc_btnNewButton1 = new GridBagConstraints();
        gbc_btnNewButton1.insets = new Insets(0, 0, 0, 5);
        gbc_btnNewButton1.gridx = 2;
        gbc_btnNewButton1.gridy = 9;
        getContentPane().add(cancelar, gbc_btnNewButton1);



        comboE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 ESeleccionado = (String) comboE.getSelectedItem();
            	 comboO.removeAllItems();

				try {
					ofertas = ContUsuario.mostrarOfertasDeEmpresaIngresadas(ESeleccionado);
					for(String o : ofertas)
     				comboO.addItem(o);


            	}
				 catch (NoExisteEmpresa e1) {
					// TODO Auto-generated catch block
				}
            }
            });

        comboO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            		OSeleccionado = (String) comboO.getSelectedItem();

            	}
            });


        rechazar.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg2) {
        		try {
					ContUsuario.rechazarOferta(OSeleccionado);
					setVisible(false);
					JOptionPane.showMessageDialog(null, "Oferta rechazada", "Aceptar o Rechazar Ofertas Laborales ",JOptionPane.INFORMATION_MESSAGE);


				} catch (NoExisteOferta e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Seleccione una Oferta", "Aceptar o Rechazar Ofertas Laborales ",JOptionPane.ERROR_MESSAGE);
				}

        	}
        });


        aceptar.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg1) {
        		try {
					ContUsuario.aceptarOferta(OSeleccionado);
					setVisible(false);
					JOptionPane.showMessageDialog(null, "Oferta aceptada", "Aceptar o Rechazar Ofertas Laborales ",JOptionPane.INFORMATION_MESSAGE);


				} catch (NoExisteOferta e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Seleccione una Oferta", "Aceptar o Rechazar Ofertas Laborales ",JOptionPane.ERROR_MESSAGE);

				}

        	}

        });

        cancelar.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg1) {
        		comboE.removeAllItems();
				comboO.removeAllItems();
				setVisible(false);

        	}

        });


	}



	public void abrir() {

		comboE.removeAllItems();
			Set<String> tiposT;
			try {
				tiposT = ContUsuario.mostrarEmpresas();
				for(String t : tiposT)
				comboE.addItem(t);
				setVisible(true);
			} catch (NoExistenEmpresas e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, "No hay Empresas", "Aceptar o Rechazar Ofertas Laborales",JOptionPane.ERROR_MESSAGE);

			}





	}
}
