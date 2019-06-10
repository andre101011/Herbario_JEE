package co.edu.uniquindio.AAAD.pruebas;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
public class TestModelo {

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
	
	
	@UsingDataSet({"persona.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void AgregarEmpleadoTest() {
		
		Empleado empleado =new Empleado();
		empleado.setCedula("6476785");
		empleado.setNombre("Lucia torres");
		empleado.setClave("12345");
		empleado.setEmail("ltorres@gmail.com");
		
		entityManager.persist(empleado);//agregar
		//entityManager.merge(empleado);actualizar
		//entityManager.remove(empleado);eliminar
		
		Empleado empleado2 = entityManager.find(Empleado.class, empleado.getCedula());//buscar
		
		Assert.assertNotNull(empleado2);

	}
	
	
	@UsingDataSet({"persona.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void buscarRecolectorTest() {
		
		Recolector recolector = entityManager.find(Recolector.class, "3");
		
		
		Assert.assertNotNull(recolector);
		
	}

}
