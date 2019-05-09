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
 * Clase de pruebas dedicada para la pruebas de las entidades
 * 
 * @author EinerZG
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class TestClase {

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
	 * Permite probar la creación de una clase
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void crearClaseTest() {
		
		Orden orden = new Orden();
		orden.setId("4");
		orden.setNombre("OrdenDePrueba");
		
		entityManager.persist(orden);
		
		Orden registrado = entityManager.find(Orden.class, "4");
		
		Assert.assertEquals(orden, registrado);

	}
	
	
	/**
	 * Permite probar la busqueda de una clase
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void buscarClaseTest() {
		
		Orden orden = entityManager.find(Orden.class, "1");
		
		Assert.assertNotNull(orden);

	}
	
	/**
	 * Permite probar la modificación de una clase
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void modificarClaseTest() {
		
		String modificacion="MODIFICACION";
		
		Orden orden = entityManager.find(Orden.class, "1");
		
		orden.setNombre(modificacion);
		
		entityManager.merge(orden);
		
		Orden modificado = entityManager.find(Orden.class, "1");
		
		Assert.assertEquals(modificacion, modificado.getNombre());

	}
	
	
	/**
	 * Permite probar la modificación de una clase
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void eliminarClaseTest() {
		
		Orden orden = entityManager.find(Orden.class, "1");
		
		Assert.assertNotNull(orden);
		
		entityManager.remove(orden);
		
		Orden eliminado = entityManager.find(Orden.class, "1");
		
		Assert.assertNull(eliminado);

	}
	
	/**
	 * Permite probar la obtención del listado de personas
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void listarPersonasNamedTest() {
		
		TypedQuery<Persona>  query=entityManager.createNamedQuery(Persona.LISTAR_TODOS,Persona.class);
		List<Persona> personas = query.getResultList();
		Assert.assertEquals(3, personas.size());
	}
	

}
