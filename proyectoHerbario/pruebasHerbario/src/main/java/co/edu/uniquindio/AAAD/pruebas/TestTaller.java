package co.edu.uniquindio.AAAD.pruebas;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import co.edu.uniquindio.AAAD.dto.PersonaDTO;
import co.edu.uniquindio.AAAD.persistencia.Familia;
import co.edu.uniquindio.AAAD.persistencia.Persona;
import co.edu.uniquindio.AAAD.persistencia.Registro.Estado;

/**
 * Clase de pruebas dedicada para la pruebas del segundo taller de queries
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
	 * Permite probar crear una consulta que permita deteerminar el número de
	 * familias que se han registrado.
	 */
	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void contarFamiliasTest() {

		TypedQuery<Long> query = entityManager.createNamedQuery(Familia.CONTAR, Long.class);
		List<Long> familias = query.getResultList();

		Assert.assertEquals(3, familias.get(0).longValue());

	}

	/**
	 * Permite probar crear una consulta que permita deteerminar el número de
	 * personas con registros aceptados por dia.
	 */
	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void contarPersonasConRegistros() {
		Calendar inicio = new GregorianCalendar(1992, Calendar.DECEMBER, 28);
		TypedQuery<Long> query = entityManager.createNamedQuery(Persona.CONTAR_PERSONAS, Long.class);
		query.setParameter("est", Estado.Aceptado);

		List<Long> conteo = query.getResultList();
		Assert.assertEquals(1, conteo.get(0).longValue());

	}

	/**
	 * Permite probar crear una consulta que permita listar las personas que no han
	 * realizado registros
	 */
	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void contarPersonasSinRegistros() {

		TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.lISTAR_SIN_REGISTROS, Persona.class);
		List<Persona> personas = query.getResultList();
		Assert.assertEquals(2, personas.size());

	}

	/**
	 * Permite probar Crear una consulta que permita determinar cuantos registros ha
	 * realizado cada empleado (o administrador). Devuelva un DTO con cedula
	 * empleado y número de registros.
	 */
	@Test
	@UsingDataSet({ "persona.json", "clase.json", "orden.json", "familia.json", "genero.json", "especie.json",
			"registro.json" })
	@Transactional(value = TransactionMode.ROLLBACK)
	public void obtenerDTO() {

		TypedQuery<PersonaDTO> query = entityManager.createNamedQuery(Persona.LISTAR_DTO, PersonaDTO.class);
		List<PersonaDTO> personas = query.getResultList();

		for (PersonaDTO personaDTO : personas) {
			System.out.println(personaDTO.getCedula() + " : " + personaDTO.getNumRegistros());
		}

		Assert.assertEquals(2, personas.size());

	}

	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void determinarFamiliaMayorTest() {

		TypedQuery<Object[]> query = entityManager.createNamedQuery(Familia.OBTENER_FAMILIA_MAS_ESPECIES, Object[].class);
		List<Object[]> resul = query.getResultList();
		String id=((Familia)(resul.get(0)[0])).getId();
		Long cantidad=(Long)(resul.get(0)[1]);
		Assert.assertEquals("1", id);
		Assert.assertEquals(2, cantidad.longValue());

	}

}
