package co.edu.uniquindio.AAAD.ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Typed;
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
 * Se encarga de manejar las operaciones realizadas por el administrador
 * 
 * @author Daniel Bonilla
 * @author Andres llinas
 * @version Beta 1.0
 */
@Stateless
@LocalBean
public class AdminEJB implements AdminEJBRemote {

	/**
	 * permite manejar todas las transacciones del ejb
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public AdminEJB() {

	}

	@Override
	public Empleado insertarEmpleado(Empleado empleado) throws ElementoRepetidoException {

		if (entityManager.find(Empleado.class, empleado.getCedula()) != null) {

			throw new ElementoRepetidoException("El empleado con esa cedula ya está registrado");
		} else if (comprobarEmailRepetido(empleado) != null) {
			throw new ElementoRepetidoException("El empleado con ese email ya fue registrado");
		}

		try {
			entityManager.persist(empleado);
			return empleado;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * permite buscar un empleado por email
	 * 
	 * @param email email del empleado
	 * @return empleado encontrado o null
	 */
	private String comprobarEmailRepetido(Empleado empleado) {
		try {
			TypedQuery<String> query = entityManager.createNamedQuery(Empleado.BUSCAR_EMPLEADO_POR_EMAIL, String.class);
			query.setParameter("email", empleado.getEmail());
			String cedula = query.getSingleResult();
			if (cedula.equals(empleado.getCedula())) {
				return null;
			}

			return cedula;
		} catch (NoResultException e) {
			return null;
		}

	}
	
	@Override
	public Persona buscarPersonaPorEmail(String correo) {
		try {
			System.out.println("pasa por ejb");
			TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.BUSCAR_PERSONA_POR_EMAIL, Persona.class);
			query.setParameter("email", correo);
			query.setParameter("visibilidad", Visibilidad.HABILITADO);
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#buscarEmpleado(java.lang.String)
	 */
	@Override
	public Empleado buscarEmpleado(String cedula) {

		try {

			TypedQuery<Empleado> query = entityManager.createNamedQuery(Empleado.BUSCAR_EMPLEADO_POR_CEDULA,Empleado.class);
			query.setParameter("visibilidad", Visibilidad.HABILITADO);
			Empleado empleado = query.getSingleResult();
			return empleado;
		} catch (NoResultException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#modificarEmpleado(co.edu.uniquindio
	 * .AAAD.persistencia.Empleado)
	 */
	@Override
	public Empleado modificarEmpleado(Empleado empleado)
			throws ElementoNoEncontradoException, ElementoRepetidoException {
		if (buscarEmpleado(empleado.getCedula()) == null) {

			throw new ElementoNoEncontradoException("la empleado con ese id no se encuentra en la base de datos");

		} else if (comprobarEmailRepetido(empleado) != null) {
			throw new ElementoRepetidoException("El empleado con ese email ya fue registrado");
		}

		try {
			entityManager.merge(empleado);
			return empleado;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#eliminarEmpleado(co.edu.uniquindio.
	 * AAAD.persistencia.Empleado)
	 */
	@Override
	public Empleado eliminarEmpleado(Empleado empleado) throws ElementoNoEncontradoException {
		if (buscarEmpleado(empleado.getCedula()) == null) {

			throw new ElementoNoEncontradoException("la empleado con esa cedula no se encuentra en la base de datos");
		}

		try {
			entityManager.remove(empleado);
			return empleado;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#listarEmpleados()
	 */
	@Override
	public List<Empleado> listarEmpleados() {
		try {
			TypedQuery<Empleado> query = entityManager.createNamedQuery(Empleado.LISTAR_EMPLEADO, Empleado.class);
			query.setParameter("visibilidad", Visibilidad.HABILITADO);
			List<Empleado> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#buscarRecolector(java.lang.String)
	 */
	@Override
	public Recolector buscarRecolector(String cedula) {

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#eliminarRecolector(co.edu.
	 * uniquindio.AAAD.persistencia.Recolector)
	 */
	@Override
	public Recolector eliminarRecolector(Recolector recolector) throws ElementoNoEncontradoException {
		if (buscarRecolector(recolector.getCedula()) == null) {

			throw new ElementoNoEncontradoException("la recolector con esa cedula no se encuentra en la base de datos");
		}

		try {
			entityManager.remove(recolector);
			return recolector;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#listarRecolectors()
	 */
	@Override
	public List<Recolector> listarRecolectores() {
		try {
			TypedQuery<Recolector> query = entityManager.createNamedQuery(Recolector.LISTAR_RECOLECTOR, Recolector.class);
			query.setParameter("visibilidad", Visibilidad.HABILITADO);
			List<Recolector> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * @see co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#insertarClase(co.edu.uniquindio.AAAD.persistencia.Clase)
	 */
	@Override
	public Clase insertarClase(Clase clase) throws ElementoRepetidoException {
		System.out.println("pasa por ejb");
		 if (comprobarNombreRepetido(clase) != null) {
			throw new ElementoRepetidoException("La clase con ese nombre ya está registrado");

		}

		try {
			entityManager.persist(clase);
			return clase;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#buscarClase(java.lang.String)
	 */
	@Override
	public Clase buscarClase(String id) {

		try {

			Clase clase = entityManager.find(Clase.class, id);
			return clase;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * metodo que comprueba si una clase tiene nombre repetido
	 * 
	 * @param clase clase a comprobar
	 * @return clase encontrada o null
	 */
	private Clase comprobarNombreRepetido(Clase clase) {
		try {
			TypedQuery<Clase> query = entityManager.createNamedQuery(Clase.BUSCAR_POR_NOMBRE, Clase.class);
			query.setParameter("nombre", clase.getNombre());
			Clase clase2 = query.getSingleResult();
			if (clase2.getNombre().equals(clase.getNombre())) {
				return null;
			}

			return clase2;
		} catch (NoResultException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#buscarClasePorNombre(java.lang.
	 * String)
	 */
	@Override
	public Clase buscarClasePorNombre(String nombre) {
		try {
			TypedQuery<Clase> query = entityManager.createNamedQuery(Clase.BUSCAR_POR_NOMBRE, Clase.class);
			query.setParameter("nombre", nombre);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#modificarClase(co.edu.uniquindio.
	 * AAAD.persistencia.Clase)
	 */
	@Override
	public Clase modificarClase(Clase clase) throws ElementoNoEncontradoException, ElementoRepetidoException {
		if (entityManager.find(Clase.class, clase.getId()) == null) {

			throw new ElementoNoEncontradoException("la clase con ese id no se encuentra en la base de datos");
		}
//		else if(buscarClasePorNombre(clase.getNombre()) != null) {
//			
//			throw new ElementoRepetidoException("La clase con ese nombre ya está registrada");
//		}

		try {
			entityManager.merge(clase);
			return clase;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#eliminarClase(co.edu.uniquindio.
	 * AAAD.persistencia.Clase)
	 */
	@Override
	public Clase eliminarClase(Clase clase) throws ElementoNoEncontradoException {
		if (entityManager.find(Clase.class, clase.getId()) == null) {

			throw new ElementoNoEncontradoException("la clase con ese id no se encuentra en la base de datos");
		}

		try {
			entityManager.remove(clase);
			return clase;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#listarClases()
	 */
	@Override
	public List<Clase> listarClases() {
		try {
			TypedQuery<Clase> query = entityManager.createNamedQuery(Clase.LISTAR_TODOS, Clase.class);
			List<Clase> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#insertarOrden(co.edu.uniquindio.
	 * AAAD.persistencia.Orden)
	 */
	@Override
	public Orden insertarOrden(Orden orden) throws ElementoRepetidoException {

		if (comprobarNombreRepetido(orden) != null) {
			throw new ElementoRepetidoException("el orden con ese nombre ya está registrado");

		}

		try {
			entityManager.persist(orden);
			return orden;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#buscarOrden(java.lang.String)
	 */
	@Override
	public Orden buscarOrden(String id) {

		try {

			Orden orden = entityManager.find(Orden.class, id);
			return orden;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * metodo que comprueba si una orden tiene nombre repetido
	 * 
	 * @param orden orden a comprobar
	 * @return orden encontrada o null
	 */
	private Orden comprobarNombreRepetido(Orden orden) {
		try {
			TypedQuery<Orden> query = entityManager.createNamedQuery(Orden.BUSCAR_POR_NOMBRE, Orden.class);
			query.setParameter("nombre", orden.getNombre());
			Orden orden2 = query.getSingleResult();
			if (orden2.getNombre().equals(orden.getNombre())) {
				return null;
			}

			return orden2;
		} catch (NoResultException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#buscarOrdenPorNombre(java.lang.
	 * String)
	 */
	@Override
	public Orden buscarOrdenPorNombre(String nombre) {
		try {
			TypedQuery<Orden> query = entityManager.createNamedQuery(Orden.BUSCAR_POR_NOMBRE, Orden.class);
			query.setParameter("nombre", nombre);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#modificarOrden(co.edu.uniquindio.
	 * AAAD.persistencia.Orden)
	 */
	@Override
	public Orden modificarOrden(Orden orden) throws ElementoNoEncontradoException, ElementoRepetidoException {
		if (entityManager.find(Orden.class, orden.getId()) == null) {

			throw new ElementoNoEncontradoException("el orden con ese id no se encuentra en la base de datos");
		}
//		else if(buscarOrdenPorNombre(orden.getNombre()) != null) {
//			
//			throw new ElementoRepetidoException("El orden con ese nombre ya está registrada");
//		}

		try {
			entityManager.merge(orden);
			return orden;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#eliminarOrden(co.edu.uniquindio.
	 * AAAD.persistencia.Orden)
	 */
	@Override
	public Orden eliminarOrden(Orden orden) throws ElementoNoEncontradoException {
		if (entityManager.find(Orden.class, orden.getId()) == null) {

			throw new ElementoNoEncontradoException("el orden con ese id no se encuentra en la base de datos");
		}

		try {
			entityManager.remove(orden);
			return orden;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#listarOrdenes()
	 */
	@Override
	public List<Orden> listarOrdenes() {
		try {
			TypedQuery<Orden> query = entityManager.createNamedQuery(Orden.LISTAR_TODOS, Orden.class);
			List<Orden> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#insertarGenero(co.edu.uniquindio.
	 * AAAD.persistencia.Genero)
	 */
	@Override
	public Genero insertarGenero(Genero genero) throws ElementoRepetidoException {

		if (comprobarNombreRepetido(genero) != null) {
			throw new ElementoRepetidoException("el genero con ese nombre ya está registrado");

		}

		try {
			entityManager.persist(genero);
			return genero;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#buscarGenero(java.lang.String)
	 */
	@Override
	public Genero buscarGenero(String id) {

		try {

			Genero genero = entityManager.find(Genero.class, id);
			return genero;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * metodo que comprueba si una genero tiene nombre repetido
	 * 
	 * @param genero genero a comprobar
	 * @return genero encontrada o null
	 */
	private Genero comprobarNombreRepetido(Genero genero) {
		try {
			TypedQuery<Genero> query = entityManager.createNamedQuery(Genero.BUSCAR_POR_NOMBRE, Genero.class);
			query.setParameter("nombre", genero.getNombre());
			Genero genero2 = query.getSingleResult();
			if (genero2.getNombre().equals(genero.getNombre())) {
				return null;
			}

			return genero2;
		} catch (NoResultException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#buscarGeneroPorNombre(java.lang.
	 * String)
	 */
	@Override
	public Genero buscarGeneroPorNombre(String nombre) {
		try {
			TypedQuery<Genero> query = entityManager.createNamedQuery(Genero.BUSCAR_POR_NOMBRE, Genero.class);
			query.setParameter("nombre", nombre);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#modificarGenero(co.edu.uniquindio.
	 * AAAD.persistencia.Genero)
	 */
	@Override
	public Genero modificarGenero(Genero genero) throws ElementoNoEncontradoException, ElementoRepetidoException {
		if (entityManager.find(Genero.class, genero.getId()) == null) {

			throw new ElementoNoEncontradoException("el genero con ese id no se encuentra en la base de datos");
		}
//		else if(buscarGeneroPorNombre(genero.getNombre()) != null) {
//			
//			throw new ElementoRepetidoException("El genero con ese nombre ya está registrado");
//		}

		try {
			entityManager.merge(genero);
			return genero;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#eliminarGenero(co.edu.uniquindio.
	 * AAAD.persistencia.Genero)
	 */
	@Override
	public Genero eliminarGenero(Genero genero) throws ElementoNoEncontradoException {
		if (entityManager.find(Genero.class, genero.getId()) == null) {

			throw new ElementoNoEncontradoException("el genero con ese id no se encuentra en la base de datos");
		}

		try {
			entityManager.remove(genero);
			return genero;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#listarGeneros()
	 */
	@Override
	public List<Genero> listarGeneros() {
		try {
			TypedQuery<Genero> query = entityManager.createNamedQuery(Genero.LISTAR_TODOS, Genero.class);
			List<Genero> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#insertarFamilia(co.edu.uniquindio.
	 * AAAD.persistencia.Familia)
	 */
	@Override
	public Familia insertarFamilia(Familia familia) throws ElementoRepetidoException {

		if (comprobarNombreRepetido(familia) != null) {
			throw new ElementoRepetidoException("el familia con ese nombre ya está registrado");

		}

		try {
			entityManager.persist(familia);
			return familia;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#buscarFamilia(java.lang.String)
	 */
	@Override
	public Familia buscarFamilia(String id) {

		try {

			Familia familia = entityManager.find(Familia.class, id);
			return familia;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * metodo que comprueba si una familia tiene nombre repetido
	 * 
	 * @param familia familia a comprobar
	 * @return familia encontrada o null
	 */
	private Familia comprobarNombreRepetido(Familia familia) {
		try {
			TypedQuery<Familia> query = entityManager.createNamedQuery(Familia.BUSCAR_POR_NOMBRE, Familia.class);
			query.setParameter("nombre", familia.getNombre());
			Familia familia2 = query.getSingleResult();
			if (familia2.getNombre().equals(familia.getNombre())) {
				return null;
			}

			return familia2;
		} catch (NoResultException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#buscarFamiliaPorNombre(java.lang.
	 * String)
	 */
	@Override
	public Familia buscarFamiliaPorNombre(String nombre) {
		try {
			TypedQuery<Familia> query = entityManager.createNamedQuery(Familia.BUSCAR_POR_NOMBRE, Familia.class);
			query.setParameter("nombre", nombre);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#modificarFamilia(co.edu.uniquindio.
	 * AAAD.persistencia.Familia)
	 */
	@Override
	public Familia modificarFamilia(Familia familia) throws ElementoNoEncontradoException, ElementoRepetidoException {
		if (entityManager.find(Familia.class, familia.getId()) == null) {

			throw new ElementoNoEncontradoException("el familia con ese id no se encuentra en la base de datos");
		}
//		else if(buscarFamiliaPorNombre(familia.getNombre()) != null) {
//			
//			throw new ElementoRepetidoException("La familia con ese nombre ya está registrada");
//		}

		try {
			entityManager.merge(familia);
			return familia;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#eliminarFamilia(co.edu.uniquindio.
	 * AAAD.persistencia.Familia)
	 */
	@Override
	public Familia eliminarFamilia(Familia familia) throws ElementoNoEncontradoException {
		if (entityManager.find(Familia.class, familia.getId()) == null) {

			throw new ElementoNoEncontradoException("el familia con ese id no se encuentra en la base de datos");
		}

		try {
			entityManager.remove(familia);
			return familia;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#listarFamilias()
	 */
	@Override
	public List<Familia> listarFamilias() {
		try {
			TypedQuery<Familia> query = entityManager.createNamedQuery(Familia.LISTAR_TODOS, Familia.class);
			List<Familia> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#listarEspeciesEnEspera()
	 */
	@Override
	public List<Especie> listarEspeciesEnEspera() {

		try {

			TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_ESTADO, Especie.class);
			query.setParameter("est", Estado.Espera);
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
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#aceptarEspecie(co.edu.uniquindio.
	 * AAAD.persistencia.Especie)
	 */
	@Override
	public Especie aceptarEspecie(Especie especie) {

		try {
			especie.getRegistroPlanta().setEstado(Estado.Aceptado);

			entityManager.merge(especie);

			return especie;
		} catch (Exception e) {

			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#rechazarEspecie(co.edu.uniquindio.
	 * AAAD.persistencia.Especie)
	 */
	@Override
	public Especie rechazarEspecie(Especie especie) {

		try {
			especie.getRegistroPlanta().setEstado(Estado.Rechazado);

			entityManager.merge(especie);

			return especie;
		} catch (Exception e) {
			return null;
		}
	}

}
