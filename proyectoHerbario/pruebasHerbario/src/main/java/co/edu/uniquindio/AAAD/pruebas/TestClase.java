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
import co.edu.uniquindio.AAAD.persistencia.Persona;


/**
 * Clase de pruebas dedicada para la pruebas de la entidad clase
 * 
 * @author Daniel Bonilla
 * @author Andres Llinas
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
		
		Clase clase = new Clase();
		clase.setId("4");
		clase.setNombre("ClaseDePrueba");
		
		entityManager.persist(clase);
		
		Clase registrado = entityManager.find(Clase.class, "4");
		
		Assert.assertEquals(clase, registrado);

	}
	
	
	/**
	 * Permite probar la busqueda de una clase
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void buscarClaseTest() {
		
		Clase clase = entityManager.find(Clase.class, "1");
		
		Assert.assertNotNull(clase);

	}
	
	/**
	 * Permite probar la modificación de una clase
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void modificarClaseTest() {
		
		String modificacion="MODIFICACION";
		
		Clase clase = entityManager.find(Clase.class, "1");
		
		clase.setNombre(modificacion);
		
		entityManager.merge(clase);
		
		Clase modificado = entityManager.find(Clase.class, "1");
		
		Assert.assertEquals(modificacion, modificado.getNombre());

	}
	
	
	/**
	 * Permite probar la eliminación de una clase
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void eliminarClaseTest() {
		
		Clase clase = entityManager.find(Clase.class, "1");
		
		Assert.assertNotNull(clase);
		
		entityManager.remove(clase);
		
		Clase eliminado = entityManager.find(Clase.class, "1");
		
		Assert.assertNull(eliminado);

	}
	
	/**
	 * Permite probar la obtención del listado de clases
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void listarClaseTest() {
		
		TypedQuery<Clase>  query=entityManager.createNamedQuery(Clase.LISTAR_TODOS,Clase.class);
		List<Clase> clases = query.getResultList();
		Assert.assertEquals(2, clases.size());
	}
	

}
