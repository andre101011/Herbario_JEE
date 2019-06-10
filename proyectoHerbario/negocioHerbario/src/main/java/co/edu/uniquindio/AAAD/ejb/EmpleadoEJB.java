package co.edu.uniquindio.AAAD.ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import co.edu.uniquindio.AAAD.persistencia.*;
import co.edu.uniquindio.AAAD.persistencia.Registro.Estado;

/**
 * Se encarga de manejar las operaciones realizadas por las personas
 * @author Daniel Bonilla
 * @author Andres llinas
 * @version Beta 1.0 
 */
@Stateless
@LocalBean
public class EmpleadoEJB implements EmpleadoEJBRemote {

	/**
	 * permite manejar todas las transacciones del ejb
	 */
	@PersistenceContext
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public EmpleadoEJB() {
        // TODO Auto-generated constructor stub
    }
    
   
  
    
    @Override
    public List<Especie> listarEspeciesAceptadas(){

    	try {
			TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_ESTADO, Especie.class);
			query.setParameter("est", Estado.Aceptado);
			List<Especie> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}
    
    @Override
    public List<Especie> listarEspeciesRechazados(){

    	try {
			TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_ESTADO, Especie.class);
			query.setParameter("est", Estado.Rechazado);
			List<Especie> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}
    
    @Override
    public List<Especie> listarEspeciesPorClase(Clase clase){

    	try {
			TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_CLASE, Especie.class);
			query.setParameter("clas", clase.getId());
			List<Especie> lista = query.getResultList();
			
			return lista;
		} catch (Exception e) {
			return null;
		}

	}
    
    @Override
    public List<Especie> listarEspeciesPorOrden(Orden orden){

    	try {
			TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_ORDEN, Especie.class);
			query.setParameter("ord", orden.getId());
			List<Especie> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}
    
    @Override
    public List<Especie> listarEspeciesPorGenero(Genero genero){

    	try {
			TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_GENERO, Especie.class);
			query.setParameter("gen", genero.getId());
			List<Especie> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}
    
    @Override
    public List<Especie> listarEspeciesPorFamilia(Familia familia){

    	try {
			TypedQuery<Especie> query = entityManager.createNamedQuery(Especie.LISTAR_POR_ESTADO, Especie.class);
			query.setParameter("fam", familia.getId());
			List<Especie> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}

	}
    
    
    
}
