package co.edu.uniquindio.AAAD.ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.persistencia.*;
import co.edu.uniquindio.AAAD.persistencia.Persona.Visibilidad;
import co.edu.uniquindio.AAAD.persistencia.Registro.Estado;

/**
 * Se encarga de manejar las operaciones realizadas por las personas
 * 
 * @author Daniel Bonilla
 * @author Andres llinas
 * @version Beta 1.0
 */
@Stateless
@LocalBean
public class NegocioEJB implements NegocioEJBRemote {

	/**
	 * permite manejar todas las transacciones del ejb
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public NegocioEJB() {
		// TODO Auto-generated constructor stub
	}
	/*
	 * (non-Javadoc)
	 * @see co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote#comprobarCredenciales(java.lang.String, java.lang.String)
	 */
	@Override
	public Persona comprobarCredenciales(String correo, String clave) {

		try {

			TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.INICIO_SESION, Persona.class);
			query.setParameter("email1", correo);
			query.setParameter("clave1", clave);
			query.setParameter("visibilidad", Visibilidad.HABILITADO);

			return query.getSingleResult();
		} catch (NoResultException e) {

			return null;

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote#registrarEspecie(co.edu.
	 * uniquindio.AAAD.persistencia.Registro)
	 */
	@Override
	public Especie registrarEspecie(Registro registro) throws ElementoRepetidoException {

		Especie especie = registro.getEspecieEnviada();
		
		try {
			entityManager.persist(registro);
			
			return especie;
		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote#insertarRecolector(co.edu.
	 * uniquindio.AAAD.persistencia.Recolector)
	 */
	@Override
	public Recolector insertarRecolector(Recolector recolector) throws ElementoRepetidoException {

		if (entityManager.find(Recolector.class, recolector.getCedula()) != null) {

			throw new ElementoRepetidoException("El recolector con esa cedula ya está registrado");
		} else if (buscarPorEmail(recolector) != null) {
			throw new ElementoRepetidoException("El recolector con ese email ya fue registrado");
		}

		try {
			entityManager.persist(recolector);
			return recolector;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote#modificarRecolector(co.edu.
	 * uniquindio.AAAD.persistencia.Recolector)
	 */
	@Override
	public Recolector modificarRecolector(Recolector recolector)
			throws ElementoNoEncontradoException, ElementoRepetidoException {
		if (buscarRecolector(recolector.getCedula()) == null) {

			throw new ElementoNoEncontradoException("la recolector con ese id no se encuentra en la base de datos");

		} else if (buscarPorEmail(recolector) != null) {
			throw new ElementoRepetidoException("El recolector con ese email ya fue registrado");
		}

		try {
			entityManager.merge(recolector);
			return recolector;
		} catch (Exception e) {
			return null;
		}
	}
	private Recolector buscarRecolector(String cedula) {

		try {

			TypedQuery<Recolector> query = entityManager.createNamedQuery(Recolector.BUSCAR_RECOLECTOR_POR_CEDULA,Recolector.class);
			query.setParameter("visibilidad", Visibilidad.HABILITADO);
			query.setParameter("cedula", cedula);
			Recolector recolector = query.getSingleResult();
			return recolector;
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * permite buscar un recolector por email
	 * 
	 * @param email email del recolector
	 * @return recolector encontrado o null
	 */
	private String buscarPorEmail(Recolector recolector) {
		try {
			TypedQuery<String> query = entityManager.createNamedQuery(Recolector.BUSCAR_RECOLECTOR_POR_EMAIL,
					String.class);
			query.setParameter("email", recolector.getEmail());
			query.setParameter("visibilidad", Visibilidad.HABILITADO);
			
			String cedula = query.getSingleResult();
			if (cedula.equals(recolector.getCedula())) {
				return null;
			}

			return cedula;
		} catch (NoResultException e) {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote#buscarEspecie(java.lang.String)
	 */
	@Override
	public Especie buscarEspecie(long id) {

		try {

			Especie especie = entityManager.find(Especie.class, id);
			return especie;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Especie> buscarEspeciePorSuNombre(String nombre) {
		try {
			TypedQuery<Especie> query= entityManager.createNamedQuery(Especie.BUSCAR_POR_NOMBRE,Especie.class);
			query.setParameter("nombre", nombre);
			List<Especie> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote#listarEspeciesAceptadas()
	 */
	@Override
	public List<Especie> listarEspeciesAceptadas() {

		try {
			TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_ESTADO, Especie.class);
			query.setParameter("est", Estado.Aceptado);
			List<Especie> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote#listarEspeciesRechazados()
	 */
	@Override
	public List<Especie> listarEspeciesRechazados() {

		try {
			TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_ESTADO, Especie.class);
			query.setParameter("est", Estado.Rechazado);
			List<Especie> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote#listarEspeciesPorClase(co.edu.
	 * uniquindio.AAAD.persistencia.Clase)
	 */
	@Override
	public List<Especie> listarEspeciesPorClase(Clase clase) {

		try {
			TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_CLASE, Especie.class);
			query.setParameter("clas", clase.getId());
			List<Especie> lista = query.getResultList();

			return lista;
		} catch (Exception e) {
			return null;
		}

	}
	/*
	 * (non-Javadoc)
	 * @see co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote#listarEspeciesPorOrden(co.edu.uniquindio.AAAD.persistencia.Orden)
	 */
	@Override
	public List<Especie> listarEspeciesPorOrden(Orden orden) {

		try {
			TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_ORDEN, Especie.class);
			query.setParameter("ord", orden.getId());
			List<Especie> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote#listarEspeciesPorGenero(co.edu.
	 * uniquindio.AAAD.persistencia.Genero)
	 */
	@Override
	public List<Especie> listarEspeciesPorGenero(Genero genero) {

		try {
			TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_GENERO, Especie.class);
			query.setParameter("gen", genero.getId());
			List<Especie> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote#listarEspeciesPorFamilia(co.edu.
	 * uniquindio.AAAD.persistencia.Familia)
	 */
	@Override
	public List<Especie> listarEspeciesPorFamilia(Familia familia) {

		try {
			TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_ESTADO, Especie.class);
			query.setParameter("fam", familia.getId());
			List<Especie> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * @see co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote#listarEspecies()
	 */
	@Override
	public List<Especie> listarEspecies() {

		try {
			TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_TODOS, Especie.class);
			List<Especie> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}
	
	/*
	 * (non-Javadoc)
	 * @see co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote#listarOrdenesPorClase()
	 */
	@Override
	public List<Orden> listarOrdenesPorClase(Clase clase) {

		try {
			TypedQuery<Orden> query = entityManager.createNamedQuery(Orden.LISTAR_POR_CLASE, Orden.class);
			query.setParameter("clase", clase.getId());
			List<Orden> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}
	/*
	 * (non-Javadoc)
	 * @see co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote#listarFamiliasPorOrden()
	 */
	@Override
	public List<Familia> listarFamiliasPorOrden(Orden orden) {

		try {
			TypedQuery<Familia> query = entityManager.createNamedQuery(Familia.LISTAR_POR_ORDEN, Familia.class);
			query.setParameter("clase", orden.getId());
			List<Familia> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}
	/*
	 * (non-Javadoc)
	 * @see co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote#listarGenerosPorFamilia()
	 */
	@Override
	public List<Genero> listarGenerosPorFamilia(Familia familia) {

		try {
			TypedQuery<Genero> query = entityManager.createNamedQuery(Genero.LISTAR_POR_FAMILIA, Genero.class);
			query.setParameter("clase", familia.getId());
			List<Genero> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}
	
	

}
