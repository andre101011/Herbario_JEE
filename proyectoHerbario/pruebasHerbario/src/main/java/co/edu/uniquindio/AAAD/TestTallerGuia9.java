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
public class TestTallerGuia9 {

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
	 * Permite probar la obtención de la familia por el id de la especie
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void obtenerFamiliaPorIdEspecieTest() {
		
		TypedQuery<Familia> query=entityManager.createNamedQuery(Especie.OBTENER_FAMILIA_POR_ID_ESPECIE,Familia.class);
		query.setParameter("id", "1");
		List<Familia> lista=query.getResultList();
		
		Assert.assertEquals("1", lista.get(0).getId());
		
	}
	
	
	/**
	 * Permite probar la obtención del listado de especies a partir de su genero
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void listarEspeciesPorGeneroInTest() {
		
		TypedQuery<Especie> query=entityManager.createNamedQuery(Genero.OBTENER_ESPECIES_POR_GENERO_IN,Especie.class);
		query.setParameter("id", "1");
		List<Especie> lista=query.getResultList();
		
		Assert.assertEquals("1", lista.get(0).getId());
		
	}
	
	/**
	 * Permite probar la obtención del listado de registros a partir del cedula de la persona
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void listarRegistrosPorPersonaInnerTest() {
		
		TypedQuery<Registro> query=entityManager.createNamedQuery(Persona.OBTENER_REGISTROS_POR_CEDULA_PERSONA,Registro.class);
		query.setParameter("cedula", "123456789");
		List<Registro> lista=query.getResultList();
		
		Assert.assertEquals(1,lista.size());
		
	}
	
	/**
	 * Permite probar la obtención del listado de recolectores que tienen registros
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void listarRecolectoresConRegistrosTest() {
		
		TypedQuery<Registro> query=entityManager.createNamedQuery(Persona.OBTENER_REGISTROS_POR_CEDULA_PERSONA,Registro.class);
		query.setParameter("cedula", "123456789");
		List<Registro> lista=query.getResultList();
		
		Assert.assertEquals(1,lista.size());
		
	}

}
