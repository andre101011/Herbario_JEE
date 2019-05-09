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
 * Clase de pruebas dedicada para las pruebas de la entidad genero
 * 
 * @author EinerZG
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class TestGenero {

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
	 * Permite probar la creación de un genero
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void crearGeneroTest() {
		
		Genero genero = new Genero();
		genero.setId("4");
		genero.setNombre("generoDePrueba");
		
		Familia familia= entityManager.find(Familia.class, "1");
		genero.setFamiliaDelGenero(familia);
		
		entityManager.persist(genero);
		
		Genero registrado = entityManager.find(Genero.class, "4");
		
		Assert.assertEquals(genero, registrado);

	}
	
	
	/**
	 * Permite probar la busqueda de un genero
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void buscarGeneroTest() {
		
		Genero genero = entityManager.find(Genero.class, "1");
		
		Assert.assertNotNull(genero);

	}
	
	/**
	 * Permite probar la modificación de un genero 
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void modificarGeneroTest() {
		
		String modificacion="MODIFICACION";
		
		Genero genero = entityManager.find(Genero.class, "1");
		
		genero.setNombre(modificacion);
		
		entityManager.merge(genero);
		
		Genero modificado = entityManager.find(Genero.class, "1");
		
		Assert.assertEquals(modificacion, modificado.getNombre());

	}
	
	
	/**
	 * Permite probar la eliminación de un genero
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void eliminarGeneroTest() {
		
		Genero genero = entityManager.find(Genero.class, "1");
		
		Assert.assertNotNull(genero);
		
		entityManager.remove(genero);
		
		Genero eliminado = entityManager.find(Genero.class, "1");
		
		Assert.assertNull(eliminado);

	}
	
	/**
	 * Permite probar la obtención del listado de generos
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void listarGenerosTest() {
		
		TypedQuery<Genero>  query=entityManager.createNamedQuery(Genero.LISTAR_TODOS,Genero.class);
		List<Genero> generos = query.getResultList();
		Assert.assertEquals(2, generos.size());
	}
	

}
