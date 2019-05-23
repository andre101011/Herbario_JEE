package co.edu.uniquindio.AAAD.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.persistencia.Empleado;

/**
 * Se encarga de manejar las operaciones realizadas por el administrador
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
        // TODO Auto-generated constructor stub
    }
    
    /*
     * (non-Javadoc)
     * @see co.edu.uniquindio.AAAD.ejb.AdminEJBRemote#insertarEmpleado(co.edu.uniquindio.AAAD.Empleado)
     */
    public Empleado insertarEmpleado(Empleado empleado) throws ElementoRepetidoException{
    	
    	if(entityManager.find(Empleado.class, empleado.getId())!= null) {
    		
    		throw new ElementoRepetidoException("El empleado con ese id ya está registrado");
    	}else if(buscarPorCedula(empleado.getCedula())!=null) {
    		throw new ElementoRepetidoException("El empleado con esa cedula ya está registrado");
    			
    	}else if(buscarPorEmail(empleado.getEmail()) != null) {
    		throw new ElementoRepetidoException("El empleado con ese email ya fue registrado");
    	}
    	
    	try {
    		entityManager.persist(empleado);
    		return empleado;	
    	}catch (Exception e) {
    		return null;
    	}
    }
    
    /**
     * permite buscar un empleado por email
     * @param email email del empleado
     * @return empleado encontrado o null
     */
    private Empleado buscarPorEmail(String email) {
    	try {
    		TypedQuery<Empleado> query = entityManager.createNamedQuery(Empleado.BUSCAR_EMPLEADO_POR_EMAIL, Empleado.class);
        	query.setParameter("email", email);
        	return query.getSingleResult();
    	}catch (NoResultException e) {
    		return null;
		}
 
    }
    
    /**
     * permite buscar un empleado por cedula
     * @param cedula cedula del empleado
     * @return empleado encontrado o null
     */
    private Empleado buscarPorCedula(String cedula) {
    	try {
    		TypedQuery<Empleado> query = entityManager.createNamedQuery(Empleado.BUSCAR_EMPLEADO_POR_CEDULA, Empleado.class);
        	query.setParameter("cedula", cedula);
        	return query.getSingleResult();
    	}catch (NoResultException e) {
    		return null;
		}
 
    }

}
