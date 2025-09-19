package presentacion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import excepciones.ErrorFecha;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import logica.CargarDatos;
import logica.Fabrica;
import logica.IOferta;
import logica.IUsuario;
import servidor.Publicador;


public class Principal {
	private JFrame frame; //Frame principal

	private IUsuario IUsr = Fabrica.getIUsuario();
	private IOferta IOf = Fabrica.getIOferta();

	private CrearPaqueteVacio cpvIntFrame;
	private PostularAOferta paoIntFrame; //poner privados atributos
	private CrearTP ctpIntFrame;
	private DarAltaUsuario dauIntFrame;
	private AltaDeOfertaLaboral adolIntFrame; //M
	private ConsultaDeOfertaLaboral cdolIntFrame; //M
	private AgregarTPaP tpapIntFrame;
	private DTOfertaComple dtocIntFrame; //A
	private ConsultarUsuario cuIntFrame;
	private ModificarDatosUsuario mduIntFrame;
	private ConsultarPaquete cpIntFrame;
	private AceptarRechazarOferta aroIntFrame;
	private Imagen imaIntFrame;
	private Compras comprasIntFrame;
	private ComprarPaquete comprarPaqIntFrame;
	private CantidadVisitas cantVisIntFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Principal window = new Principal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Publicador p = new Publicador();
        p.publicar();
	}

	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();
		//Inicializacion
				//No funciona al crear contUsuario
		//Crear internal frames para no enlentecer ejecucion
		EntityManagerFactory emf = null;
		EntityManager entm = null;
    	emf = Persistence.createEntityManagerFactory("trabajouy");
		entm = emf.createEntityManager();
		entm.close();
		emf.close();

		comprarPaqIntFrame = new ComprarPaquete();
		comprarPaqIntFrame.setVisible(false);

		comprasIntFrame = new Compras();
		comprasIntFrame.setVisible(false);

		ctpIntFrame = new CrearTP(IOf);
		ctpIntFrame.setResizable(true);
		ctpIntFrame.setVisible(false);


		cpvIntFrame = new CrearPaqueteVacio(IOf);
		cpvIntFrame.setVisible(false);

		imaIntFrame = new Imagen();
		imaIntFrame.setVisible(false);


		dauIntFrame = new DarAltaUsuario(IUsr);
		dauIntFrame.setVisible(false);

		adolIntFrame = new AltaDeOfertaLaboral(IOf, IUsr); //M
		adolIntFrame.setVisible(false);

		dtocIntFrame = new DTOfertaComple(IOf);
		dtocIntFrame.setVisible(false);

		cdolIntFrame = new ConsultaDeOfertaLaboral(IOf, dtocIntFrame, imaIntFrame); //M en progreso
		cdolIntFrame.setVisible(false);

		paoIntFrame = new PostularAOferta(IUsr, dtocIntFrame);
		paoIntFrame.setVisible(false);

		tpapIntFrame = new AgregarTPaP(IOf);
		tpapIntFrame.setVisible(false);

		aroIntFrame = new AceptarRechazarOferta(IUsr);
		aroIntFrame.setVisible(false);

		cuIntFrame = new ConsultarUsuario(IUsr, dtocIntFrame, imaIntFrame, comprasIntFrame);
		cuIntFrame.setVisible(false);

		mduIntFrame = new ModificarDatosUsuario(IUsr);
		mduIntFrame.setVisible(false);

		cpIntFrame = new ConsultarPaquete(IOf, imaIntFrame);
		cpIntFrame.setVisible(false);

		cantVisIntFrame = new CantidadVisitas();
		cantVisIntFrame.setVisible(false);

        frame.getContentPane().setLayout(null);

		frame.getContentPane().add(ctpIntFrame, BorderLayout.CENTER);
		frame.getContentPane().add(cpvIntFrame);
		frame.getContentPane().add(paoIntFrame);
		frame.getContentPane().add(dauIntFrame);
		frame.getContentPane().add(adolIntFrame);
		frame.getContentPane().add(dtocIntFrame);
		frame.getContentPane().add(cdolIntFrame);
		frame.getContentPane().add(tpapIntFrame);
		frame.getContentPane().add(cuIntFrame);
		frame.getContentPane().add(mduIntFrame);
		frame.getContentPane().add(cpIntFrame);
		frame.getContentPane().add(aroIntFrame);
		frame.getContentPane().add(imaIntFrame);
		frame.getContentPane().add(comprasIntFrame);
		frame.getContentPane().add(comprarPaqIntFrame);
		frame.getContentPane().add(cantVisIntFrame);


		centrarInternalFrame(ctpIntFrame);
		centrarInternalFrame(cpvIntFrame);
		//centrarInternalFrame(paoIntFrame);
		centrarInternalFrame(dauIntFrame);
		centrarInternalFrame(adolIntFrame); //M
		centrarInternalFrame(cdolIntFrame); //M
		centrarInternalFrame(mduIntFrame);
		centrarInternalFrame(cpIntFrame);
		centrarInternalFrame(tpapIntFrame);
		centrarInternalFrame(aroIntFrame);
		centrarInternalFrame(comprarPaqIntFrame);
		centrarInternalFrame(cantVisIntFrame);

		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Trabajo.uy");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 58));
		frame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);

		JLabel lblNewLabel_1 = new JLabel("Bienvenid@");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 19));
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblNewLabel_1, BorderLayout.CENTER);




	}

	private void centrarInternalFrame(JInternalFrame intFrame) {
		Dimension desktopSize = frame.getSize();
		Dimension jInternalFrameSize = intFrame.getSize();
		intFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
		    (desktopSize.height- jInternalFrameSize.height)/2);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setBounds(100, 100, 901, 623);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		frame.setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Agregar");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Usuario");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dauIntFrame.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem MenuItemOfertaLaboral = new JMenuItem("Oferta Laboral");
		MenuItemOfertaLaboral.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(adolIntFrame.cargarDatosPrevios()) {
					adolIntFrame.setVisible(true);
				}
			}
		});
		mnNewMenu.add(MenuItemOfertaLaboral);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Tipo de Publicación");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ctpIntFrame.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_4);

		JMenuItem menuItemCrearPaqueteVacio = new JMenuItem("Paquete");
		menuItemCrearPaqueteVacio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cpvIntFrame.setVisible(true);
			}
		});
		mnNewMenu.add(menuItemCrearPaqueteVacio);

		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Tipo de publicación a Paquete");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tpapIntFrame.abrir();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_7);

		JMenu mnNewMenu_1 = new JMenu("Consultar");
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Usuario");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cuIntFrame.abrir();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Oferta Laboral");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0 ) {
				if (cdolIntFrame.cargarDatosPrevios()) {
				cdolIntFrame.setVisible(true);
				}
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_3);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Paquete");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cpIntFrame.abrir();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_6);

		JMenuItem mntmVisitas = new JMenuItem("Visitas");
		mntmVisitas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cantVisIntFrame.abrir();
			}
		});
		mnNewMenu_1.add(mntmVisitas);

		JMenu mnNewMenu_2 = new JMenu("Otros");
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem_9 = new JMenuItem("Modificar datos");
		mntmNewMenuItem_9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mduIntFrame.abrir();
			}
		});

		JMenuItem MenuItemComprar = new JMenuItem("Comprar Paquete");
		MenuItemComprar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					comprarPaqIntFrame.cargarEmpresas();
				} catch (ErrorFecha e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnNewMenu_2.add(MenuItemComprar);
		mnNewMenu_2.add(mntmNewMenuItem_9);

		JMenuItem MenuItemPostular = new JMenuItem("Postular");
		MenuItemPostular.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(paoIntFrame.cargarDatosPrevios()) {
					paoIntFrame.setVisible(true);
				}
			}
		});
		mnNewMenu_2.add(MenuItemPostular);

		JMenuItem mntmNewMenuItem_10 = new JMenuItem("Aceptar/Rechazar Oferta");
		mntmNewMenuItem_10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aroIntFrame.abrir();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_10);

		JMenuItem mntmCargarDatos = new JMenuItem("Cargar datos");
		mntmCargarDatos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CargarDatos cd = new CargarDatos();
				cd.carga();
				mntmCargarDatos.setEnabled(false);
				//show dialog con mensaje de exito
				JOptionPane.showMessageDialog(null, "Datos válidos cargados correctamente", "Carga de datos",
								JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnNewMenu_2.add(mntmCargarDatos);
	}

}
