package co.edu.uniquindio.AAAD.pruebas;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;

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
import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.persistencia.*;


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
	
	/**
	 * Permite probar crear una consulta que permita insertar un empleado por medio de EJB
	 */
	@Test
	@Transactional(value =TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void insertarEmpleadoTest() {
		
		Empleado empleado=new Empleado();
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

	/**
	 * Permite probar crear una consulta que permita insertar un empleado por medio de EJB
	 */
	@Test
	@Transactional(value =TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
	"registro.json" })
	public void insertarGeneroTest() {
		
		
		
		Genero genero=new Genero();
		genero.setId(123L);
		genero.setNombre("G1");
	
		
		
		try {
			Familia familia = adminEJB.buscarFamilia("1");
			genero.setFamiliaDelGenero(familia);
			Assert.assertNotNull(adminEJB.insertarGenero(genero));
		}catch(ElementoRepetidoException e) {
			Assert.fail(e.getMessage());
		}catch (Exception e) {
			Assert.fail("Error inesperado");
		}
	
	}
	
	
	
	/**
	 * Permite probar crear una consulta que permita modificar un genero por medio de EJB
	 */
	@Test
	@Transactional(value =TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
	"registro.json" })
	public void modificarGeneroTest() {
		
		
		
		Genero genero=adminEJB.buscarGenero("1");
		
		genero.setNombre("modificación");
	
		
		
		try {
			Assert.assertNotNull(adminEJB.modificarGenero(genero));
		}catch(ElementoNoEncontradoException e) {
			Assert.fail(e.getMessage());
		}catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	
	}
	
	
	
	/**
	 * Permite probar crear una consulta que permita eliminar un genero por medio de EJB
	 */
	@Test
	@Transactional(value =TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
	"registro.json" })
	public void eliminarGeneroTest() {
		
		
		
		Genero genero=adminEJB.buscarGenero("1");
		
		try {
			Assert.assertNotNull(adminEJB.eliminarGenero(genero));
		}catch(ElementoNoEncontradoException e) {
			Assert.fail(e.getMessage());
		}catch (Exception e) {
			Assert.fail("Error inesperado");
		}
	
	}
	
	
	/**
	 * Permite probar crear una consulta que permita listar los generos
	 */
	@Test
	@Transactional(value =TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
	"registro.json" })
	public void listarGenerosTest() {
		
		
		try {
			List<Genero> generos =adminEJB.listarGeneros();
		
			Assert.assertEquals(2, generos.size());
		}catch (Exception e) {
			Assert.fail("Error inesperado");
		}
	
	}
	
	
	/**
	 * Permite probar crear una consulta que permita listar las especies aceptadas
	 */
	@Test
	@Transactional(value =TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
	"registro.json" })
	public void listarEspeciesAceptadasTest() {
		
		
		try {
			List<Especie> lista= adminEJB.listarEspeciesAceptadas();
		
			Assert.assertEquals(2, lista.size());
		}catch (Exception e) {
			Assert.fail("Error inesperado");
		}
	
	}
}

