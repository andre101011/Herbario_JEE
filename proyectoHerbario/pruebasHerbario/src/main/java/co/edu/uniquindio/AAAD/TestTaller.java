package co.edu.uniquindio.AAAD;

import java.util.Date;
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

import co.edu.uniquindio.AAAD.Registro.Estado;


/**
 * Clase de pruebas dedicada para la pruebas de la entidad clase
 * 
 * @author Daniel Bonilla
 * @author Andres Llinas
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class TestTaller {

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
	 * Permite probar crear una consulta que permita deteerminar el número de familias que se han registrado. Use COUNT.
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void contarFamiliasTest() {
		
		TypedQuery<Long> query=entityManager.createNamedQuery(Familia.CONTAR,Long.class );
		List<Long> familias=query.getResultList();
		
		Assert.assertEquals(3, familias.get(0).longValue());

	}
	
	/**
	 * Permite probar crear una consulta que permita deteerminar el número de familias que se han registrado. Use COUNT.
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void contarPersonasConRegistros() {
		
		TypedQuery<Long> query=entityManager.createNamedQuery(Persona.CONTAR_PERSONAS,Long.class );
		query.setParameter("fecha", new Date(1992, 12, 18));
		query.setParameter("est", Estado.Aceptado);
		
		List<Long> conteo=query.getResultList();
		Assert.assertEquals(3, conteo.get(0).longValue());

	}
	
	

}
