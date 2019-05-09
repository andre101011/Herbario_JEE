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
 * @author Daniel Bonilla
 * @author Andres Llinas
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class TestJPQL {

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
	 * Permite probar la obtención del listado de personas
	 */
	@Test
	@UsingDataSet({"persona.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void listarPersonasTest() {
		
		Query query = entityManager.createQuery("select p from Persona p");
		List list = query.getResultList();
		
		Assert.assertEquals(3, list.size());

	}
	
	
	/**
	 * Permite probar la obtención del listado de personas
	 */
	@Test
	@UsingDataSet({"persona.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void listarPersonasNamedTest() {
		
		TypedQuery<Persona>  query=entityManager.createNamedQuery(Persona.LISTAR_TODOS,Persona.class);
		List<Persona> personas = query.getResultList();
		Assert.assertEquals(3, personas.size());
	}
	
	@Test
	@UsingDataSet({"persona.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void logInTest() {
		
		try {
			TypedQuery<Persona> query=entityManager.createNamedQuery(Persona.INICIO_SESION, Persona.class);
			query.setParameter("email1", "mmartinez@mail.com");
			query.setParameter("clave1", "12345");
			Persona persona = query.getSingleResult();
			Assert.assertNotNull(persona);
		}catch(NoResultException e){
			
			Assert.fail("No se encontró la persona con las credenciales");
			
		}
	
		
	}

}
