package co.edu.uniquindio.AAAD;

import java.io.Serializable;
import javax.persistence.*;

import co.edu.uniquindio.AAAD.Persona;

/**
 * Informacion basica de cada una de los recolectores asociados al herbario
 * 
 * @author Daniel Bonilla Guevara
 * @author Andres Felipe Llinas
 * @version 1.0
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@NamedQueries({ @NamedQuery(name = Recolector.LISTAR_RECOLECTOR, query = "select p from Recolector p")})
public class Recolector extends Persona implements Serializable {

	/**
	 * referencia para listar los recolectores
	 */
	public static final String LISTAR_RECOLECTOR = "listarRecolector";
	
	private static final long serialVersionUID = 1L;

	public Recolector() {
		super();
	}
   
}
