package co.edu.uniquindio.AAAD.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import co.edu.uniquindio.AAAD.persistencia.Administrador;
import co.edu.uniquindio.AAAD.persistencia.Persona.Visibilidad;

/**
 * Se encarga de cargar la preconfiguración de la capa de negocio
 * @author Daniel Bonilla
 * @author Andres Llinás
 * @version 1.0
 */
@Singleton
@LocalBean
@Startup
public class SetupEJB {

	/**
	 * Permite manejar las transacciones con 
	 */
	@PersistenceContext
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public SetupEJB() {
        
    }
    
    /**
     * inicialización de la preconfiguración
     */
    @PostConstruct
    private void init() {
    	TypedQuery<Long> query=entityManager.createNamedQuery(Administrador.CONTAR_ADMINS, Long.class);
    	query.setParameter("visibilidad", Visibilidad.HABILITADO);
    	long conteoAdmin=query.getSingleResult();
    	
    	if(conteoAdmin==0) {
    		
    		Administrador administrador = new Administrador();
    		//administrador.setId("10");
    		administrador.setNombre("Daniel Bonilla");
    		administrador.setCedula("455464");
    		administrador.setEmail("dbonillag_1@uqvirtual.edu.co");
    		administrador.setClave("12345");
    		administrador.setVisibilidad(Visibilidad.HABILITADO);
    		
    		entityManager.persist(administrador);
    		
    		
    	}
    	
    }

}
