package co.edu.uniquindio.AAAD.pruebas;

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

import co.edu.uniquindio.AAAD.persistencia.Clase;
import co.edu.uniquindio.AAAD.persistencia.Orden;
import co.edu.uniquindio.AAAD.persistencia.Persona;


/**
 * Clase de pruebas dedicada para la pruebas de la entidad Orden
 * 
 * @author Daniel Bonilla
 * @author Andres Llinas
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class TestOrden {

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
	 * Permite probar la creación de un orden
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void crearOrdenTest() {
		
		Orden orden = new Orden();
		orden.setId("4");
		orden.setNombre("OrdenDePrueba");
		
		Clase clase = entityManager.find(Clase.class, "1");
		orden.setClaseDelOrden(clase);
		
		entityManager.persist(orden);
		
		Orden registrado = entityManager.find(Orden.class, "4");
		
		Assert.assertEquals(orden, registrado);

	}
	
	
	/**
	 * Permite probar la busqueda de un orden
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void buscarOrdenTest() {
		
		Orden orden = entityManager.find(Orden.class, "1");
		
		Assert.assertNotNull(orden);

	}
	
	/**
	 * Permite probar la modificación de un orden
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void modificarOrdenTest() {
		
		String modificacion="MODIFICACION";
		
		Orden orden = entityManager.find(Orden.class, "1");
		
		orden.setNombre(modificacion);
		
		entityManager.merge(orden);
		
		Orden modificado = entityManager.find(Orden.class, "1");
		
		Assert.assertEquals(modificacion, modificado.getNombre());

	}
	
	
	/**
	 * Permite probar la eliminación de un orden
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void eliminarOrdenTest() {
		
		Orden orden = entityManager.find(Orden.class, "1");
		
		Assert.assertNotNull(orden);
		
		entityManager.remove(orden);
		
		Orden eliminado = entityManager.find(Orden.class, "1");
		
		Assert.assertNull(eliminado);

	}
	
	/**
	 * Permite probar la obtención del listado de ordenes
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void listarOrdenTest() {
		
		TypedQuery<Orden>  query=entityManager.createNamedQuery(Orden.LISTAR_TODOS,Orden.class);
		List<Orden> ordenes = query.getResultList();
		Assert.assertEquals(3, ordenes.size());
	}
	

}
