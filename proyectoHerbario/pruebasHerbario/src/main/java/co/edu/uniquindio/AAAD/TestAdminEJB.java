package co.edu.uniquindio.AAAD;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.edu.uniquindio.AAAD.ejb.AdminEJB;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;


/**
 *Prueba para todas las operaciones expuestas del administrador
 * @author EinerZG
 * @version 1.0
 *
 */
@RunWith(Arquillian.class)
public class TestAdminEJB {

	
	/*
	 * Instancia para manejar las operaciones de negocio del admin
	 */
	@EJB
	private AdminEJB adminEJB;
	
	@Deployment
	public static Archive<?> createDeploymentPackage() {
	return ShrinkWrap.create(JavaArchive.class).addClass(AdminEJB.class)
	.addPackage(Persona.class.getPackage())
	.addAsResource("persistenceForTest.xml", "META-INF/persistence.xml")
	.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Test
	@Transactional(value =TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void insertarEmpleadoTest() {
		
		Empleado empleado=new Empleado();
		empleado.setId("123");
		empleado.setCedula("123");
		empleado.setNombre("daniel");
		empleado.setClave("12345");
		empleado.setEmail("sdsa@gmail.com");
		
		try {
			Assert.assertNotNull(adminEJB.insertarEmpleado(empleado));
		}catch(ElementoRepetidoException e) {
			Assert.fail(e.getMessage());
		}catch (Exception e) {
			Assert.fail("Error inesperado");
		}
			
			
	
		
	}
	
}
