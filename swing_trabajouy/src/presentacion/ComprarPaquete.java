package presentacion;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import excepciones.ErrorFecha;
import excepciones.NoExisteEmpresa;
import excepciones.NoExistePaquete;
import excepciones.NoExistenEmpresas;
import logica.Fabrica;
import logica.IOferta;
import logica.IUsuario;

public class ComprarPaquete extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> comboBoxEmpresas;
	private JComboBox<String> comboBoxPaquetes;
	private JButton btnComprar;
	/**
	 * Create the frame.
	 */
	public ComprarPaquete() {
		setBounds(100, 100, 450, 255);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{62, 69, 123, 84, 95, 0};
		gridBagLayout.rowHeights = new int[]{50, 18, 25, 0, 0, 0, 33, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblTitulo = new JLabel("Comprar Paquete");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitulo.gridwidth = 5;
		gbc_lblTitulo.gridx = 0;
		gbc_lblTitulo.gridy = 0;
		getContentPane().add(lblTitulo, gbc_lblTitulo);

		JLabel lblOferta = new JLabel("Empresa:");
		GridBagConstraints gbc_lblOferta = new GridBagConstraints();
		gbc_lblOferta.anchor = GridBagConstraints.EAST;
		gbc_lblOferta.insets = new Insets(0, 0, 5, 5);
		gbc_lblOferta.gridx = 1;
		gbc_lblOferta.gridy = 2;
		getContentPane().add(lblOferta, gbc_lblOferta);

		comboBoxEmpresas = new JComboBox<>();
		GridBagConstraints gbc_comboBoxEmpresas = new GridBagConstraints();
		gbc_comboBoxEmpresas.gridwidth = 2;
		gbc_comboBoxEmpresas.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEmpresas.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEmpresas.gridx = 2;
		gbc_comboBoxEmpresas.gridy = 2;
		getContentPane().add(comboBoxEmpresas, gbc_comboBoxEmpresas);

		btnComprar = new JButton("Comprar");
		btnComprar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IUsuario IUsr = Fabrica.getIUsuario();
				Date fecha = new Date();
				try {
					String paquete = comboBoxPaquetes.getSelectedItem().toString();
					String empresa = comboBoxEmpresas.getSelectedItem().toString();
					IUsr.ingresarPaqueteAEmpresa(paquete, empresa, fecha);
					//show JDialog
					JOptionPane.showMessageDialog(null, "Paquete comprado con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				} catch (NoExistePaquete | NoExisteEmpresa e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnComprar = new GridBagConstraints();
		gbc_btnComprar.anchor = GridBagConstraints.WEST;
		gbc_btnComprar.insets = new Insets(0, 0, 0, 5);
		gbc_btnComprar.gridx = 2;
		gbc_btnComprar.gridy = 6;
		getContentPane().add(btnComprar, gbc_btnComprar);
		btnComprar.setEnabled(false);

		comboBoxEmpresas.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0){
					if(arg0.getStateChange()==ItemEvent.SELECTED) {

						comboBoxPaquetes.removeAllItems();
						IUsuario IUsr = Fabrica.getIUsuario();
						String empresa = comboBoxEmpresas.getSelectedItem().toString();
						try {
							Set<String> paquetes = IUsr.getPaquetesSinComprar(empresa);
							for(String paq : paquetes)
								comboBoxPaquetes.addItem(paq);

							comboBoxPaquetes.setEnabled(paquetes.size()>0);
							btnComprar.setEnabled(paquetes.size()>0);

						} catch (NoExisteEmpresa e) {
							e.printStackTrace();
						}
					}
			}
		});

		JLabel lblPaquete = new JLabel("Paquete:");
		GridBagConstraints gbc_lblPaquete = new GridBagConstraints();
		gbc_lblPaquete.anchor = GridBagConstraints.EAST;
		gbc_lblPaquete.insets = new Insets(0, 0, 5, 5);
		gbc_lblPaquete.gridx = 1;
		gbc_lblPaquete.gridy = 4;
		getContentPane().add(lblPaquete, gbc_lblPaquete);

		comboBoxPaquetes = new JComboBox<>();
		comboBoxPaquetes.setEnabled(false);
		GridBagConstraints gbc_comboBoxPaquetes = new GridBagConstraints();
		gbc_comboBoxPaquetes.gridwidth = 2;
		gbc_comboBoxPaquetes.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxPaquetes.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxPaquetes.gridx = 2;
		gbc_comboBoxPaquetes.gridy = 4;
		getContentPane().add(comboBoxPaquetes, gbc_comboBoxPaquetes);

		comboBoxPaquetes.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0){
					if(arg0.getStateChange()==ItemEvent.SELECTED) {
						btnComprar.setEnabled(true);
					}
			}
		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 6;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
	}

	public void cargarEmpresas() throws ErrorFecha {
		try {
			IUsuario IUsr = Fabrica.getIUsuario();
			Set<String> empresas = IUsr.mostrarEmpresas();
			IOferta IOf = Fabrica.getIOferta();

			if(empresas.size()>0 && IOf.listarPaquetesRegistrados().size()>0) {
				comboBoxEmpresas.removeAllItems();
				for(String empresa : empresas) {
					comboBoxEmpresas.addItem(empresa);
				}
				setVisible(true);
			}else if(empresas.size()==0) {
				//show JDialog
				JOptionPane.showMessageDialog(null, "No hay empresas registradas", "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				//show JDialog
				JOptionPane.showMessageDialog(null, "No hay paquetes registrados", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}catch(NoExistenEmpresas e) {
			JOptionPane.showMessageDialog(null, "No hay empresas registradas", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
