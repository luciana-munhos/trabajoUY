package presentacion;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import excepciones.NoExisteEmpresa;
import excepciones.NoExisteOferta;
import excepciones.NoExisteOfertaEmpresa;
import excepciones.NoExistenEmpresas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import logica.DTOferta;
import logica.IOferta;
import logicaDAO.EmpresaDAO;
import logicaDAO.OfertaDAO;

//TODAVIA LO ESTOY HACIENDO
public class ConsultaDeOfertaLaboral extends JInternalFrame {
	private IOferta IOfr;
	private JComboBox<String> empresaComboBox;
	private JComboBox<String> ofertaComboBox;
    Set<String> Empresas;

    private JDesktopPane desktopPane;
    private JTextField textImagen;


    private DTOferta ofertaInfo;

	public ConsultaDeOfertaLaboral(IOferta iof,DTOfertaComple frame, Imagen im) {
		IOfr = iof;

		/*
		try {
		iof.altaTipoDePublicacion("Gold", "dESCRIP",7,7,2100, Date.valueOf("2021-01-01"));
		IUsr.darAltaEmpresa("NicknameEmpresa", "Nombre", "Apellido", "mail@gmail.com", "descrip", "link.com");
		iof.ingresarOferta("NicknameEmpresa","Gold","NombreOferta","Descripcion","10:00 - 13:00",150,"Montevideo","Montevideo",Date.valueOf("2023-08-25"), null,true);
		}
		catch (Exception e){
		}
		*/

		JDesktopPane desktopPane_1 = new JDesktopPane();
		desktopPane_1.setBounds(267, 178, 1, 1);
		getContentPane().add(desktopPane_1);



		setTitle("Consulta de Oferta Laboral");
		setBounds(100, 100, 450, 270);
		getContentPane().setLayout(null);

		//xd?
        desktopPane = new JDesktopPane();
        desktopPane.setBounds(-30, 368, 605, -422);
        getContentPane().add(desktopPane);

		empresaComboBox = new JComboBox<>();
		empresaComboBox.setBounds(200, 34, 164, 27);
		getContentPane().add(empresaComboBox);

        ofertaComboBox = new JComboBox<>();
        ofertaComboBox.setBounds(200, 86, 164, 27);
		getContentPane().add(ofertaComboBox);

		JLabel lblNewLabel = new JLabel("Seleccione la empresa:");
		lblNewLabel.setBounds(38, 38, 164, 16);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Seleccione la oferta:");
		lblNewLabel_1.setBounds(38, 90, 139, 16);
		getContentPane().add(lblNewLabel_1);


        empresaComboBox.addActionListener(e -> {
        	ofertaComboBox.removeAllItems();
            Object selectedEmpresaItem = empresaComboBox.getSelectedItem();

            if (selectedEmpresaItem != null) {
                String selectedEmpresa = selectedEmpresaItem.toString();

              //Consigo las ofertas de la empresa que selecciono y las pongo en el comboBox
                Set<String> ofertas = new HashSet<>();
    			try {
    				EntityManagerFactory emf = Persistence.createEntityManagerFactory("trabajouy");
			 		EntityManager entm = emf.createEntityManager();
    				TypedQuery<EmpresaDAO> query = entm.createQuery("SELECT e FROM EmpresaDAO e WHERE e.nickname = :nickname", EmpresaDAO.class);
			 		query.setParameter("nickname", selectedEmpresa);
			 		List<EmpresaDAO> empresas = query.getResultList();
			        if(!empresas.isEmpty()) {//ENTRA SI LA OFERTA NO ESTA PERSISTIDA
					     List<OfertaDAO> ofe = empresas.get(0).getOfertasFinalizadas(); // Obtiene el elemento único
					     for(OfertaDAO ofer : ofe) {
					    	 ofertas.add(ofer.getNombre());
					     }
					}
    				ofertas.addAll(IOfr.listarOfertasDeEmpresa(selectedEmpresa));

    				entm.close();
    				emf.close();
    			} catch (NoExisteEmpresa e1) {
    				JOptionPane.showMessageDialog(this, "No hay empresas en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
    			} catch (NoExisteOfertaEmpresa e1) {
    				JOptionPane.showMessageDialog(this, "No hay ofertas pertenecientes a esa empresa.", "Error", JOptionPane.ERROR_MESSAGE);
    			}
    			ofertaComboBox.addItem("Elegir oferta");
                for (String oferta : ofertas) {
                    ofertaComboBox.addItem(oferta);
                }
                textImagen.setText("");
                im.setVisible(false);
            }
        });

        ofertaComboBox.addActionListener(e->{
        	Object of = ofertaComboBox.getSelectedItem();
        	if(of!=null) {
	        	String selectedOferta = of.toString();
	        	if(selectedOferta != null && !selectedOferta.equals("Elegir oferta")) {
					try {

						EntityManagerFactory emf = Persistence.createEntityManagerFactory("trabajouy");
				 		EntityManager entm = emf.createEntityManager();
				 		TypedQuery<OfertaDAO> query = entm.createQuery("SELECT o FROM OfertaDAO o WHERE o.nombre = :nombre", OfertaDAO.class);
				 		query.setParameter("nombre", selectedOferta);
				 		List<OfertaDAO> resultados = query.getResultList();
				        if(resultados.isEmpty()) {//ENTRA SI LA OFERTA NO ESTA PERSISTIDA
				        	ofertaInfo = IOfr.obtenerOfertaPorNombre(selectedOferta);
				        }
				    	else {
				    		ofertaInfo = resultados.get(0).getDTOferta(); // Obtiene el elemento único
						}
				        entm.close();
				        emf.close();
						textImagen.setText(ofertaInfo.getImagen());
		        		im.setVisible(false);
					} catch (NoExisteOferta e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					textImagen.setText("");
				}
        	}
        	else {
        		textImagen.setText("");
        		im.setVisible(false);
        	}
        });

        //MOSTRAR
        JButton botonMostrar = new JButton("Mostrar datos");
		botonMostrar.setBounds(93, 178, 117, 29);
		getContentPane().add(botonMostrar);

		botonMostrar.addActionListener(e -> {
            String selectedOferta = ofertaComboBox.getSelectedItem().toString();
            if(selectedOferta == "Elegir oferta") {
            	JOptionPane.showMessageDialog(this, "Debe seleccionar alguna oferta", "Error", JOptionPane.ERROR_MESSAGE);
            }else {
				frame.cargarDatos(ofertaInfo);
				frame.setVisible(true);
				setVisible(false);
            }
        });

		//CANCELAR
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
	    		borrarFormulario();
	    		setVisible(false);
	    		im.setVisible(false);
	    		frame.setVisible(false);

        	}
        });
		botonCancelar.setForeground(Color.RED);
		botonCancelar.setBounds(231, 178, 117, 29);
		getContentPane().add(botonCancelar);

		JLabel lblImagen = new JLabel("Imagen:");
		lblImagen.setBounds(38, 131, 45, 13);
		getContentPane().add(lblImagen);

		textImagen = new JTextField();
		textImagen.setEditable(false);
		textImagen.setBounds(93, 128, 188, 19);
		getContentPane().add(textImagen);
		textImagen.setColumns(10);

		JButton btnVerImagen = new JButton("Ver Imagen");
		btnVerImagen.setBounds(292, 127, 104, 21);
		getContentPane().add(btnVerImagen);
		btnVerImagen.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		//show image in a new window
        		if(!textImagen.getText().equals("")) {
	        		im.cargarImagen(ofertaInfo.getImagen());
	                im.setVisible(true);
        		}
        	}
        });

	}

	private void borrarFormulario() {
	    if (empresaComboBox != null) {
	        empresaComboBox.removeAllItems();
	    }
	    if (ofertaComboBox != null) {
	        ofertaComboBox.removeAllItems();
	    }
	}


	public boolean cargarDatosPrevios() {
		borrarFormulario();
		try {
			Empresas = IOfr.listarEmpresas();
			for(String e: Empresas) {
				empresaComboBox.addItem(e);
			}
		} catch (NoExistenEmpresas e) {
			JOptionPane.showMessageDialog(this, "Aun no existen empresas.", "Error.", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
