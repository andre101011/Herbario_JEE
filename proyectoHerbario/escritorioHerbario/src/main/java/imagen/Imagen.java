package imagen;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import co.edu.uniquindio.AAAD.persistencia.Especie;

public class Imagen {
	
	
	public static void agregarImagen(Especie especie, String ruta ) {
		
		
		File archivo = new File(ruta);
        byte[] imgFoto = new byte[(int)archivo.length()];
		

        InputStream inte = null;
		try {
			inte = new FileInputStream(archivo);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        try {
			inte.read(imgFoto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        especie.setImagen(imgFoto);
		
	}
	
	public static ImageIcon obtenerImagen(Especie especie) {
		
		byte[] imgFoto = especie.getImagen();
        BufferedImage image = null;
        InputStream in = new ByteArrayInputStream(imgFoto);
        try {
			image = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        ImageIcon icono = new ImageIcon(image);
        return icono;
		
	}

}
