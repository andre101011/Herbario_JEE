package co.edu.uniquindio.AAAD.persistencia;

import java.io.Serializable;
import javax.persistence.*;

import co.edu.uniquindio.AAAD.persistencia.Persona;

/**
 * Informacion basica de cada una de los recolectores asociados al herbario
 * 
 * @author Daniel Bonilla Guevara
 * @author Andres Felipe Llinas
 * @version 1.0
 */
@Entity
@NamedQueries({ @NamedQuery(name = Recolector.LISTAR_RECOLECTOR, query = "select p from Recolector p where p.visibilidad =:visibilidad"),
	@NamedQuery(name= Recolector.BUSCAR_RECOLECTOR_POR_EMAIL, query="select e.cedula from Recolector e where e.email=:email and e.visibilidad =:visibilidad"),
	@NamedQuery(name= Recolector.BUSCAR_RECOLECTOR_POR_CEDULA, query="select e from Recolector e where e.cedula=:cedula and e.visibilidad =:visibilidad")})
public class Recolector extends Persona implements Serializable {

	/**
	 * referencia para listar los recolectores
	 */
	public static final String LISTAR_RECOLECTOR = "listarRecolector";
	
	/**
	 * referencia para buscar un recolector por su email
	 */
	public static final String BUSCAR_RECOLECTOR_POR_EMAIL="Buscar recolector por email";
	/**
	 * referencia para buscar un recolector por su cedula
	 */
	public static final String BUSCAR_RECOLECTOR_POR_CEDULA = "Busca recolector por cedula";
	private static final long serialVersionUID = 1L;

	

	public Recolector() {
		super();
	}
   
}
