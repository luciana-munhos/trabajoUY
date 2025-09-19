package presentacion;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

public class Imagen extends JInternalFrame {

	JLabel lblNewLabel = new JLabel("");

	/**
	 * Create the frame.
	 */
	public Imagen() {
		setClosable(true);
		setBounds(100, 100, 553, 354);
		getContentPane().setLayout(null);
		lblNewLabel.setBounds(0, 0, 541, 325);
		getContentPane().add(lblNewLabel);
		this.setLocation(800, 200);



	}

	public void cargarImagen(String ruta) {

		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("data/"+ruta));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		//make the image fit the size of the label
		ImageIcon imageIcon = new ImageIcon(img.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH));
		lblNewLabel.setIcon(imageIcon);

	}



}
