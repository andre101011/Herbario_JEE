package co.edu.uniquindio.AAAD;

import static org.junit.Assert.assertEquals;

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
 * Clase de pruebas dedicada para la pruebas de la entidad especie
 * 
 * @author Daniel Bonilla
 * @author Andres Llinas
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class TestEspecie {

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
	 * Permite probar la obtención del listado de especies
	 */

	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void listarEspeciesTest() {

		TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_TODOS, Especie.class);
		List<Especie> especies = query.getResultList();
		Assert.assertEquals(3, especies.size());
	}

	/**
	 * Permite probar la evaluacion de las especies por parte de un administrador
	 */
	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void evaluarEspeciesTest() {

		TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_ESTADO, Especie.class);
		query.setParameter("est", Estado.Espera);
		List<Especie> especies = query.getResultList();

		Especie especie = especies.get(0);
		especie.getRegistroPlanta().setEstado(Estado.Aceptado);
		especie.getRegistroPlanta().setJustificacion("porque si");
		Administrador admin = entityManager.find(Administrador.class, "2");
		especie.getRegistroPlanta().setEvaluadorDelRegistro(admin);
		entityManager.merge(especie);
		Especie aceptada = entityManager.find(Especie.class, especie.getId());
		Assert.assertEquals(aceptada.getRegistroPlanta().getEstado(), Estado.Aceptado);

	}
	
	/**
	 * Permite probar la visualización de la información de una especie
	 */
	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void verInformacionEspecieTest() {

		Especie especie = entityManager.find(Especie.class, "1");

		
		Assert.assertNotNull(especie.getId());
		Assert.assertNotNull(especie.getNombre());
		Assert.assertNotNull(especie.getGeneroDeEspecie());
		Assert.assertNotNull(especie.getRegistroPlanta());
		

	}
	

	/**
	 * Permite probar listar las especies aceptadas
	 */

	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void listarEspeciesAceptadasTest() {

		TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_ESTADO, Especie.class);
		query.setParameter("est", Estado.Aceptado);
		List<Especie> especies = query.getResultList();
		Especie especie = especies.get(0);
		Assert.assertEquals(especie.getRegistroPlanta().getEstado(), Estado.Aceptado);
		Assert.assertEquals(1, especies.size());

	}

	/**
	 * Permite probar listar las especies rechazadas
	 */

	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void listarEspeciesRechazadasTest() {

		TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_ESTADO, Especie.class);
		query.setParameter("est", Estado.Rechazado);
		List<Especie> especies = query.getResultList();
		Especie especie = especies.get(0);
		Assert.assertEquals(especie.getRegistroPlanta().getEstado(), Estado.Rechazado);
		Assert.assertEquals(1, especies.size());

	}

	/**
	 * Permite probar listar las especies por familia
	 */

	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void listarEspeciesPorFamiliaTest() {

		Familia miFamilia = entityManager.find(Familia.class, "1");
		TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_FAMILIA, Especie.class);
		query.setParameter("fam", miFamilia);
		List<Especie> especies = query.getResultList();
		Assert.assertEquals(2, especies.size());
	}

	/**
	 * Permite probar listar las especies por genero
	 */
	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void listarEspeciesPorGeneroTest() {

		Genero miGenero = entityManager.find(Genero.class, "1");
		TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_GENERO, Especie.class);
		query.setParameter("gen", miGenero);
		List<Especie> especies = query.getResultList();
		Assert.assertEquals(2, especies.size());

	}
	

	/**
	 * Permite probar listar las especies por Clase
	 */
	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void listarEspeciesPorClaseTest() {

		Clase miClase = entityManager.find(Clase.class, "1");
		TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_CLASE, Especie.class);
		query.setParameter("clas", miClase);
		List<Especie> especies = query.getResultList();
		Assert.assertEquals(2, especies.size());

	}
	
	/**
	 * Permite probar listar las especies por orden
	 */
	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void listarEspeciesPorOrdenTest() {

		Orden miOrden = entityManager.find(Orden.class, "1");
		TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_ORDEN, Especie.class);
		query.setParameter("ord", miOrden);
		List<Especie> especies = query.getResultList();
		Assert.assertEquals(2, especies.size());

	}
	
	/**
	 * Permite probar listar las especies aceptadas por familia
	 */

	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void listarEspeciesAceptadasPorFamiliaTest() {

		Familia miFamilia = entityManager.find(Familia.class, "1");
		TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_FAMILIA_ACEPTADA, Especie.class);
		query.setParameter("fam", miFamilia);
		query.setParameter("est", Estado.Aceptado);
		List<Especie> especies = query.getResultList();
		Assert.assertEquals(1, especies.size());
	}

	/**
	 * Permite probar listar las especies aceptadas por genero
	 */
	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void listarEspeciesAceptadasPorGeneroTest() {

		Genero miGenero = entityManager.find(Genero.class, "1");
		TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_GENERO_ACEPTADA, Especie.class);
		query.setParameter("gen", miGenero);
		query.setParameter("est", Estado.Aceptado);
		List<Especie> especies = query.getResultList();
		Assert.assertEquals(1, especies.size());

	}
	

	/**
	 * Permite probar listar las especies aceptadas por Clase
	 */
	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void listarEspeciesAceptadasPorClaseTest() {

		Clase miClase = entityManager.find(Clase.class, "1");
		TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_CLASE_ACEPTADA, Especie.class);
		query.setParameter("clas", miClase);
		query.setParameter("est", Estado.Aceptado);
		List<Especie> especies = query.getResultList();
		Assert.assertEquals(1, especies.size());

	}
	
	/**
	 * Permite probar listar las especies aceptadas por orden
	 */
	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void listarEspeciesAceptadasPorOrdenTest() {

		Orden miOrden = entityManager.find(Orden.class, "1");
		TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_ORDEN_ACEPTADA, Especie.class);
		query.setParameter("ord", miOrden);
		query.setParameter("est", Estado.Aceptado);
		List<Especie> especies = query.getResultList();
		Assert.assertEquals(1, especies.size());

	}

	/**
	 * Permite probar el registro de una especie
	 */
	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void registrarEspecieTest() {

		Registro registro = new Registro();

		registro.setId("4");

		Persona enviadorDelRegistro = entityManager.find(Persona.class, "1");

		registro.setEnviadorDelRegistro(enviadorDelRegistro);

		registro.setEstado(Estado.Espera);

		registro.setFecha(new Date());

		Especie especie = new Especie();
		especie.setId("4");
		especie.setNombre("generoDePrueba");

	}
	
	/**
	 * Permite probar listar las especies enviadas de un recolector
	 */
	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void listarEspeciesDeRecolectorTest() {

		Recolector recolector = entityManager.find(Recolector.class, "3");
		TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_RECOLECTOR, Especie.class);
		query.setParameter("rec", recolector);
		List<Especie> especies = query.getResultList();
		Assert.assertEquals(2, especies.size());
	}


}
