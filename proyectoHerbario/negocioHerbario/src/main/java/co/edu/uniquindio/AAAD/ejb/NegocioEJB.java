package co.edu.uniquindio.AAAD.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.persistencia.*;

/**
 * Se encarga de manejar las operaciones realizadas por las personas
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
    
    @Override
    public Especie registrarEspecie(Registro registro) throws ElementoRepetidoException {

		Especie especie = registro.getEspecieEnviada();

		if (entityManager.find(Especie.class, especie.getId()) != null) {

			throw new ElementoRepetidoException("la especie con ese id ya est� registrada");
		} else if (entityManager.find(Registro.class, registro.getId()) != null) {

			throw new ElementoRepetidoException("El registro con ese id ya est� registrado");

		}

		try {
			entityManager.persist(especie);
			return especie;
		} catch (Exception e) {
			return null;
		}

	}
    
    @Override
	public Recolector insertarRecolector(Recolector recolector) throws ElementoRepetidoException {

		if (entityManager.find(Recolector.class, recolector.getCedula()) != null) {

			throw new ElementoRepetidoException("El recolector con esa cedula ya est� registrado");
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
    
    @Override
	public Recolector modificarRecolector(Recolector recolector)
			throws ElementoNoEncontradoException, ElementoRepetidoException {
		if (entityManager.find(Recolector.class, recolector.getCedula()) == null) {

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
			String cedula = query.getSingleResult();
			if (cedula.equals(recolector.getCedula())) {
				return null;
			}

			return cedula;
		} catch (NoResultException e) {
			return null;
		}

	}
    
    @Override
	public Especie buscarEspecie(String id) {

		try {

			Especie especie = entityManager.find(Especie.class, id);
			return especie;
		} catch (Exception e) {
			return null;
		}
	}

}
