package presentacion;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import logica.DTCompra;
import logica.DTPaquete;

public class Compras extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textFechaCompra;
	private JTextField textCosto;
	private JTextField textFechaVencimiento;
	private JComboBox<String> comboBoxPaquete;
	private Set<DTCompra> compras;
	private JComboBox<String> comboBoxTP;
	private Map<String,Integer> cantidades;
	private JLabel totales;
	private Map<String,Integer> cantTotales;



	/**
	 * Create the frame.
	 */
	public Compras() {
		setClosable(true);
		setBounds(800, 100, 619, 286);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{54, 122, 63, 55, 33, 85, 91, 116, 0};
		gridBagLayout.rowHeights = new int[]{45, 0, 43, 31, 26, 24, 0, 29, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblCompras = new JLabel("Compras");
		lblCompras.setFont(new Font("Tahoma", Font.BOLD, 20));
		GridBagConstraints gbc_lblCompras = new GridBagConstraints();
		gbc_lblCompras.gridwidth = 3;
		gbc_lblCompras.anchor = GridBagConstraints.SOUTH;
		gbc_lblCompras.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompras.gridx = 1;
		gbc_lblCompras.gridy = 0;
		getContentPane().add(lblCompras, gbc_lblCompras);

		comboBoxPaquete = new JComboBox<>();
		GridBagConstraints gbc_comboBoxPaquete = new GridBagConstraints();
		gbc_comboBoxPaquete.gridwidth = 3;
		gbc_comboBoxPaquete.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxPaquete.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxPaquete.gridx = 1;
		gbc_comboBoxPaquete.gridy = 1;
		getContentPane().add(comboBoxPaquete, gbc_comboBoxPaquete);

		JLabel lblNewLabel = new JLabel("Tipo de publicación");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 5;
		gbc_lblNewLabel.gridy = 2;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblFechaCompra = new JLabel("Fecha Compra:");
		GridBagConstraints gbc_lblFechaCompra = new GridBagConstraints();
		gbc_lblFechaCompra.gridwidth = 2;
		gbc_lblFechaCompra.anchor = GridBagConstraints.EAST;
		gbc_lblFechaCompra.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaCompra.gridx = 0;
		gbc_lblFechaCompra.gridy = 3;
		getContentPane().add(lblFechaCompra, gbc_lblFechaCompra);

		textFechaCompra = new JTextField();
		textFechaCompra.setEditable(false);
		GridBagConstraints gbc_textFechaCompra = new GridBagConstraints();
		gbc_textFechaCompra.gridwidth = 2;
		gbc_textFechaCompra.insets = new Insets(0, 0, 5, 5);
		gbc_textFechaCompra.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFechaCompra.gridx = 2;
		gbc_textFechaCompra.gridy = 3;
		getContentPane().add(textFechaCompra, gbc_textFechaCompra);
		textFechaCompra.setColumns(10);

		comboBoxTP = new JComboBox<>();
		comboBoxTP.setEnabled(false);
		GridBagConstraints gbc_comboBoxTP = new GridBagConstraints();
		gbc_comboBoxTP.gridwidth = 2;
		gbc_comboBoxTP.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxTP.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxTP.gridx = 5;
		gbc_comboBoxTP.gridy = 3;
		getContentPane().add(comboBoxTP, gbc_comboBoxTP);

		JLabel lblValor = new JLabel("Costo:");
		GridBagConstraints gbc_lblValor = new GridBagConstraints();
		gbc_lblValor.gridwidth = 2;
		gbc_lblValor.anchor = GridBagConstraints.EAST;
		gbc_lblValor.insets = new Insets(0, 0, 5, 5);
		gbc_lblValor.gridx = 0;
		gbc_lblValor.gridy = 4;
		getContentPane().add(lblValor, gbc_lblValor);

		textCosto = new JTextField();
		textCosto.setEditable(false);
		GridBagConstraints gbc_textCosto = new GridBagConstraints();
		gbc_textCosto.gridwidth = 2;
		gbc_textCosto.insets = new Insets(0, 0, 5, 5);
		gbc_textCosto.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCosto.gridx = 2;
		gbc_textCosto.gridy = 4;
		getContentPane().add(textCosto, gbc_textCosto);
		textCosto.setColumns(10);

		JLabel lblTotal = new JLabel("Total:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 5;
		gbc_lblNewLabel_3.gridy = 4;
		getContentPane().add(lblTotal, gbc_lblNewLabel_3);

		totales = new JLabel("[tot]");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 6;
		gbc_lblNewLabel_4.gridy = 4;
		getContentPane().add(totales, gbc_lblNewLabel_4);


		JLabel lblFechaVencimiento = new JLabel("Fecha de Vencimiento:");
		GridBagConstraints gbc_lblFechaVencimiento = new GridBagConstraints();
		gbc_lblFechaVencimiento.gridwidth = 2;
		gbc_lblFechaVencimiento.anchor = GridBagConstraints.EAST;
		gbc_lblFechaVencimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaVencimiento.gridx = 0;
		gbc_lblFechaVencimiento.gridy = 5;
		getContentPane().add(lblFechaVencimiento, gbc_lblFechaVencimiento);

		textFechaVencimiento = new JTextField();
		textFechaVencimiento.setEditable(false);
		GridBagConstraints gbc_textFechaVencimiento = new GridBagConstraints();
		gbc_textFechaVencimiento.gridwidth = 2;
		gbc_textFechaVencimiento.insets = new Insets(0, 0, 5, 5);
		gbc_textFechaVencimiento.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFechaVencimiento.gridx = 2;
		gbc_textFechaVencimiento.gridy = 5;
		getContentPane().add(textFechaVencimiento, gbc_textFechaVencimiento);
		textFechaVencimiento.setColumns(10);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		JLabel lblRestantes = new JLabel("Restantes:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 5;
		gbc_lblNewLabel_1.gridy = 5;
		getContentPane().add(lblRestantes, gbc_lblNewLabel_1);

		JLabel restantes = new JLabel("[res]");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 6;
		gbc_lblNewLabel_2.gridy = 5;
		getContentPane().add(restantes, gbc_lblNewLabel_2);
		GridBagConstraints gbc_btnCerrar = new GridBagConstraints();
		gbc_btnCerrar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCerrar.gridx = 1;
		gbc_btnCerrar.gridy = 7;
		getContentPane().add(btnCerrar, gbc_btnCerrar);

		comboBoxPaquete.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0){
					if(arg0.getStateChange()==ItemEvent.SELECTED) {
						DTCompra compra = findCompra(compras,comboBoxPaquete.getSelectedItem().toString());
						cargarCompra(compra);
					}
			}
		});

		comboBoxTP.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0){
					if(arg0.getStateChange()==ItemEvent.SELECTED) {
						totales.setText(cantTotales.get(comboBoxTP.getSelectedItem().toString()).toString());
						restantes.setText(cantidades.get(comboBoxTP.getSelectedItem().toString()).toString());
					}
			}
		});


	}

	private DTCompra findCompra(Set<DTCompra> compras,String nomPaquete) {
		for(DTCompra compra : compras) {
			if(compra.getDTPaquete().getNombre().equals(nomPaquete))
				return compra;
		}
		return null;
	}

	public void borrarFormulario() {
		textFechaCompra.setText("");
		textCosto.setText("");
		textFechaVencimiento.setText("");
		comboBoxPaquete.removeAll();
		comboBoxTP.removeAllItems();
	}

	public boolean cargarDatos(Set<DTCompra> com) {
		compras = com;
		comboBoxPaquete.removeAllItems();
		boolean res = false;
		for(DTCompra compra : com) {
			comboBoxPaquete.addItem(compra.getDTPaquete().getNombre());
			res = true;
		}
		return res;

	}

	public void cargarCompra(DTCompra com) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cantidades = com.getCantActualTP();

		Date fechaCompra = com.getFechaCompra();
		cal.setTime(fechaCompra);
		Date dateCompra = cal.getTime();
		String fechaCompraString = formato.format(dateCompra);

		DTPaquete dtpaq = com.getDTPaquete();
		Double costo = dtpaq.getCosto();
		cantTotales = dtpaq.getCantTotales();

		textFechaCompra.setText(fechaCompraString);
		textCosto.setText(String.valueOf(costo));

		comboBoxTP.removeAllItems();
		for(String tp : cantidades.keySet()) {
			comboBoxTP.addItem(tp);
		}

		comboBoxTP.setEnabled(cantidades.size()>0);




		int validez = com.getDTPaquete().getValidez();
		cal.add(Calendar.DAY_OF_MONTH, validez);
		Date date = cal.getTime();


		String fechaVenc = "";
		fechaVenc = formato.format(date);


		textFechaVencimiento.setText(fechaVenc+" ("+String.valueOf(validez)+" días)");
	}

}
