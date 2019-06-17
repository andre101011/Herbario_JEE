package co.edu.uniquindio.AAAD.pruebas;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.edu.uniquindio.AAAD.persistencia.Empleado;
import co.edu.uniquindio.AAAD.persistencia.Especie;
import co.edu.uniquindio.AAAD.persistencia.Persona;
import co.edu.uniquindio.AAAD.persistencia.Recolector;


/**
 * Clase de pruebas dedicada para la pruebas de las entidades
 * 
 * @author Daniel Bonilla
 * @author Andres Llinas
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class TestImagen {

	/**
	 * instancia para realizar las transaciones con las entidades
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * general el archivo de depliegue de pruebas
	 * 
	 * @return genera un archivo de configuracion web
	 */
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Persona.class.getPackage())
				.addAsResource("persistenceForTest.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

	}

	@Test
	public void test() {
	}
	
	
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	public void AgregarEspecieTest() {
		
		Especie especie = new Especie();
		especie.setId(1L);
		especie.setNombre("esp1");
		File archivo = new File("C:\\eclipse\\workspace\\Herbario_JEE\\proyectoHerbario\\escritorioHerbario\\src\\main\\java\\co\\edu\\uniquindio\\AAAD\\vista\\herbarioUQ.png");
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
		entityManager.persist(especie);//agregar
		
		especie = entityManager.find(Especie.class, 1L);
		imgFoto=especie.getImagen();
         BufferedImage image = null;
         InputStream in = new ByteArrayInputStream(imgFoto);
         try {
			image = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

         ImageIcon icono = new ImageIcon(image);
         JOptionPane.showMessageDialog(null, "Imagenes", "Imagen", JOptionPane.INFORMATION_MESSAGE, icono);
		
	
		
		

	}
	
	
	@UsingDataSet({"persona.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void buscarRecolectorTest() {
		
		Recolector recolector = entityManager.find(Recolector.class, "3");
		
		
		Assert.assertNotNull(recolector);
		
	}

}
