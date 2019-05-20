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

import co.edu.uniquindio.AAAD.persistencia.Familia;
import co.edu.uniquindio.AAAD.persistencia.Orden;
import co.edu.uniquindio.AAAD.persistencia.Persona;


/**
 * Clase de pruebas dedicada para las pruebas de la entidad familia
 * 
 * @author Daniel Bonilla
 * @author Andres Llinas
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class TestFamilia {

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
	 * Permite probar la creación de una familia
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void crearFamiliaTest() {
		
		Familia familia = new Familia();
		familia.setId("4");
		familia.setNombre("FamiliaDePrueba");
		
		Orden orden = entityManager.find(Orden.class, "1");
		familia.setOrdenDelaFamilia(orden);
		
		entityManager.persist(familia);
		
		Familia registrado = entityManager.find(Familia.class, "4");
		
		Assert.assertEquals(familia, registrado);

	}
	
	
	/**
	 * Permite probar la busqueda de una familia
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void buscarOrdenTest() {
		
		Familia familia = entityManager.find(Familia.class, "1");
		
		Assert.assertNotNull(familia);

	}
	
	/**
	 * Permite probar la modificación de una familia
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void modificarOrdenTest() {
		
		String modificacion="MODIFICACION";
		
		Familia familia = entityManager.find(Familia.class, "1");
		
		familia.setNombre(modificacion);
		
		entityManager.merge(familia);
		
		Familia modificado = entityManager.find(Familia.class, "1");
		
		Assert.assertEquals(modificacion, modificado.getNombre());

	}
	
	
	/**
	 * Permite probar la eliminación de una familia
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void eliminarOrdenTest() {
		
		Familia familia = entityManager.find(Familia.class, "1");
		
		Assert.assertNotNull(familia);
		
		entityManager.remove(familia);
		
		Familia eliminado = entityManager.find(Familia.class, "1");
		
		Assert.assertNull(eliminado);

	}
	
	/**
	 * Permite probar la obtención del listado de familias
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void listarFamiliasTest() {
		
		TypedQuery<Familia>  query=entityManager.createNamedQuery(Familia.LISTAR_TODOS,Familia.class);
		List<Familia> familias = query.getResultList();
		Assert.assertEquals(3, familias.size());
	}
	

}
