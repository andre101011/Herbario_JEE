package co.edu.uniquindio.AAAD;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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


/**
 * Clase de pruebas dedicada para las pruebas de la entidad Peronas y sus hijos
 * 
 * @author Daniel Bonilla
 * @author Andres Llinas
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class TestPersona {

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

	
	/**
	 * Permite probar la creación de un empleado
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void crearEmpleadoTest() {
		
		Empleado empleado = new Empleado();
		empleado.setId("4");
		empleado.setNombre("EmpleadoDePrueba");
		empleado.setCedula("1010104164");
		empleado.setEmail("danivara2008@gmail.com");
		empleado.setClave("contrasena");
		
		entityManager.persist(empleado);
		
		Empleado registrado = entityManager.find(Empleado.class, "4");
		
		Assert.assertEquals(empleado, registrado);

	}
	
	
	/**
	 * Permite probar la busqueda de un empleado
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void buscarEmpleadoTest() {
		
		Empleado empleado = entityManager.find(Empleado.class, "1");
		
		Assert.assertNotNull(empleado);

	}
	
	/**
	 * Permite probar la modificación de un empleado 
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void modificarEmpleadoTest() {
		
		String modificacion="MODIFICACION";
		
		Empleado empleado = entityManager.find(Empleado.class, "1");
		
		empleado.setNombre(modificacion);
		
		entityManager.merge(empleado);
		
		Empleado modificado = entityManager.find(Empleado.class, "1");
		
		Assert.assertEquals(modificacion, modificado.getNombre());

	}
	
	
	/**
	 * Permite probar la eliminación de un empleado
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void eliminarEmpleadoTest() {
		
		Empleado empleado = entityManager.find(Empleado.class, "1");
		
		Assert.assertNotNull(empleado);
		
		entityManager.remove(empleado);
		
		Empleado eliminado = entityManager.find(Empleado.class, "1");
		
		Assert.assertNull(eliminado);

	}
	
	/**
	 * Permite probar la obtención del listado de empleados
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void listarEmpleadosTest() {
		
		TypedQuery<Empleado>  query=entityManager.createNamedQuery(Empleado.LISTAR_EMPLEADO,Empleado.class);
		List<Empleado> empleados = query.getResultList();
		Assert.assertEquals(1, empleados.size());
	}
	
	
	
	/**
	 * Permite probar la creación de un Administrador
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void crearAdministradorTest() {
		
		Administrador administrador = new Administrador();
		administrador.setId("4");
		administrador.setNombre("Prueba");
		administrador.setCedula("1010104164");
		administrador.setEmail("danivara2008@gmail.com");
		administrador.setClave("contrasena");
		
		entityManager.persist(administrador);
		
		Administrador registrado = entityManager.find(Administrador.class, "4");
		
		Assert.assertEquals(administrador, registrado);

	}
	
	
	/**
	 * Permite probar la busqueda de un administrador
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void buscarAdministradorTest() {
		
		Administrador administrador = entityManager.find(Administrador.class, "2");
		
		Assert.assertNotNull(administrador);

	}
	
	/**
	 * Permite probar la modificación de un adminstrador
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void modificarAdministradorTest() {
		
		String modificacion="MODIFICACION";
		
		Administrador administrador = entityManager.find(Administrador.class, "2");
		
		administrador.setNombre(modificacion);
		
		entityManager.merge(administrador);
		
		Administrador modificado = entityManager.find(Administrador.class, "2");
		
		Assert.assertEquals(modificacion, modificado.getNombre());

	}
	
	
	/**
	 * Permite probar la eliminación de un administrador
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void eliminarAdministradorTest() {
		
		Administrador administrador = entityManager.find(Administrador.class, "2");
		
		Assert.assertNotNull(administrador);
		
		entityManager.remove(administrador);
		
		Administrador eliminado = entityManager.find(Administrador.class, "2");
		
		Assert.assertNull(eliminado);

	}
	
	/**
	 * Permite probar la obtención del listado de administradores
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void listarAdministradoresTest() {
		
		TypedQuery<Administrador>  query=entityManager.createNamedQuery(Administrador.LISTAR_ADMINISTRADOR,Administrador.class);
		List<Administrador> administradores = query.getResultList();
		Assert.assertEquals(1, administradores.size());
	}
	
	
	/**
	 * Permite probar la creación de un recolector
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void crearRecolectorTest() {
		
		Recolector recolector = new Recolector();
		recolector.setId("4");
		recolector.setNombre("Prueba");
		recolector.setCedula("1010104164");
		recolector.setEmail("danivara2008@gmail.com");
		recolector.setClave("contrasena");
		
		entityManager.persist(recolector);
		
		Recolector registrado = entityManager.find(Recolector.class, "4");
		
		Assert.assertEquals(recolector, registrado);

	}
	
	
	/**
	 * Permite probar la busqueda de un recolector
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void buscarRecolectorTest() {
		
		Recolector recolector = entityManager.find(Recolector.class, "3");
		
		Assert.assertNotNull(recolector);

	}
	
	/**
	 * Permite probar la modificación de un recolector
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void modificarRecolectorTest() {
		
		String modificacion="MODIFICACION";
		
		Recolector recolector = entityManager.find(Recolector.class, "3");
		
		recolector.setNombre(modificacion);
		
		entityManager.merge(recolector);
		
		Recolector modificado = entityManager.find(Recolector.class, "3");
		
		Assert.assertEquals(modificacion, modificado.getNombre());

	}
	
	
	/**
	 * Permite probar la eliminación de un Recolector
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void eliminarRecolectorTest() {
		
		Recolector recolector = entityManager.find(Recolector.class, "3");
		
		Assert.assertNotNull(recolector);
		
		entityManager.remove(recolector);
		
		Recolector eliminado = entityManager.find(Recolector.class, "3");
		
		Assert.assertNull(eliminado);

	}
	
	/**
	 * Permite probar la obtención del listado de recolectores
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void listarRecolectoresTest() {
		
		TypedQuery<Recolector>  query=entityManager.createNamedQuery(Recolector.LISTAR_RECOLECTOR,Recolector.class);
		List<Recolector> recolectores = query.getResultList();
		Assert.assertEquals(1, recolectores.size());
	}

}
