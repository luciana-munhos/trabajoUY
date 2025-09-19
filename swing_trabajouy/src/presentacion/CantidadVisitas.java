package presentacion;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import excepciones.NoExisteOferta;
import logica.DTOferta;
import logica.Fabrica;
import logica.IOferta;

public class CantidadVisitas extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTable tabla;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					CantidadVisitas frame = new CantidadVisitas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CantidadVisitas() {
		setBounds(100, 100, 679, 242);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{46, 0, 51, 0};
		gridBagLayout.rowHeights = new int[]{57, 217, 38, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblTitulo = new JLabel("TOP 5: Ofertas");
		lblTitulo.setFont(new Font("Source Serif Pro Semibold", Font.PLAIN, 30));
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitulo.gridx = 1;
		gbc_lblTitulo.gridy = 0;
		getContentPane().add(lblTitulo, gbc_lblTitulo);

		String[] columnas = {"#",
				"Oferta Laboral",
                "Empresa",
                "Tipo de publicación",
                "Cantidad de visitas"};

		DefaultTableModel model = new DefaultTableModel(5, columnas.length) {
		  /**
			 *
			 */
			private static final long serialVersionUID = 1L;

		@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		model.setColumnIdentifiers(columnas);


		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});



		GridBagConstraints gbc = new GridBagConstraints();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		getContentPane().add(scrollPane, gbc_scrollPane);

		tabla = new JTable(model);
		scrollPane.setViewportView(tabla);


		btnCerrar.setForeground(Color.RED);
		GridBagConstraints gbc_btnCerrar = new GridBagConstraints();
		gbc_btnCerrar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCerrar.gridx = 1;
		gbc_btnCerrar.gridy = 2;
		getContentPane().add(btnCerrar, gbc_btnCerrar);


	}

	public void abrir() {
		IOferta iOf = Fabrica.getIOferta();
		Set<DTOferta> res;
		try {
			res = iOf.getTOPofertas();
			int i = 0;
			for (DTOferta dtOf : res) {
				tabla.setValueAt(i+1, i, 0);
				tabla.setValueAt(dtOf.getNombre(), i, 1);
				tabla.setValueAt(dtOf.getNickEmpresa(), i, 2);
				tabla.setValueAt(dtOf.getTipoPublicacion(), i, 3);
				tabla.setValueAt(dtOf.getCantVisitas(), i, 4);
				i++;
				if (i == 5) break;
			}
			setVisible(true);
		} catch (NoExisteOferta e) {
			JOptionPane.showMessageDialog(null, "No existe ninguna oferta confirmada en el sistema", "TOP5",
	                JOptionPane.ERROR_MESSAGE);
		}

	}

}
