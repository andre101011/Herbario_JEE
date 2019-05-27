package co.edu.uniquindio.AAAD.pruebasTaller;

import java.util.Calendar;
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

import com.sun.corba.ee.impl.ior.EncapsulationUtility;

import co.edu.uniquindio.AAAD.dto.RegistroDTO;
import co.edu.uniquindio.AAAD.persistencia.Especie;
import co.edu.uniquindio.AAAD.persistencia.Familia;
import co.edu.uniquindio.AAAD.persistencia.Genero;
import co.edu.uniquindio.AAAD.persistencia.Persona;
import co.edu.uniquindio.AAAD.persistencia.Registro;


/**
 * Clase de pruebas dedicada para la pruebas del taller de la guia 9
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
		
		TypedQuery<Persona> query=entityManager.createNamedQuery(Registro.OBTENER_RECOLECTORES_CON_REGISTROS,Persona.class);
		List<Persona> lista=query.getResultList();
		
		Assert.assertEquals(2,lista.size());
		
	}
	
	
	/**
	 * Permite probar la obtención del listado de vectores con cedula y registro
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void listarCedulasYRegistrosTest() {
		
		TypedQuery<Object[]> query=entityManager.createNamedQuery(Persona.LISTAR_CEDULAS_CON_REGISTROS,Object[].class);

		
		
		List<Object[]> lista=query.getResultList();
		
		for (Object[] objects : lista) {
			String cedula = (String)objects[0];
			String idRegistro="";
			Registro registro = (Registro) objects[1];
			if (registro==null) {
				idRegistro="null";
			}else {
				idRegistro=registro.getId();
			}
			
			System.out.println(cedula+","+ idRegistro);
		}
		System.out.println("xsdasdasdsadd");
	}
	
	/**
	 * Permite probar la obtención del listado de vectores con datos relacionados al registro
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void obtenerArreglosDeDatosTest() {
		
		TypedQuery<Object[]> query = entityManager.createNamedQuery(Registro.OBTENER_ARREGLO_DE_DATOS, Object[].class);
		
		 Calendar inicio = new GregorianCalendar(1992, Calendar.DECEMBER, 28);
		  query.setParameter("fecha",
		               new java.util.Date(inicio.getTime().getTime()));
		  
		
		List<Object[]> lista=query.getResultList();
		
		for (Object[] objects : lista) {
			Assert.assertEquals(5, objects.length);
		}
		
		Assert.assertEquals(3, lista.size());
	}
	

	/**
	 * Permite probar la obtención del listado de dtos con datos relacionados al registro
	 */
	@Test
	@UsingDataSet({"persona.json","clase.json","orden.json","familia.json","genero.json","especie.json","registro.json"})
	@Transactional(value=TransactionMode.ROLLBACK)
	public void obtenerDTOsDeRegistroTest() {
		
		TypedQuery<RegistroDTO> query = entityManager.createNamedQuery(Registro.OBTENER_DTO_REGISTRO, RegistroDTO.class);
		 Calendar inicio = new GregorianCalendar(1992, Calendar.DECEMBER, 28);
		  query.setParameter("fecha",
		               new java.util.Date(inicio.getTime().getTime()));
		List<RegistroDTO> lista=query.getResultList();
		
		for (RegistroDTO registroDTO : lista) {
			Assert.assertNotNull(registroDTO.getCedula());
			Assert.assertNotNull(registroDTO.getEmail());
			Assert.assertNotNull(registroDTO.getGenero());
			Assert.assertNotNull(registroDTO.getId());
			Assert.assertNotNull(registroDTO.getPlanta());
		}
		
		Assert.assertEquals(3, lista.size());
	}

}
