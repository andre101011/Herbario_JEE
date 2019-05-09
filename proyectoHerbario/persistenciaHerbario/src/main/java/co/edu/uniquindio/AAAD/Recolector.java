package co.edu.uniquindio.AAAD;

import java.io.Serializable;
import javax.persistence.*;

import co.edu.uniquindio.AAAD.Persona;

/**
 * Entity implementation class for Entity: Recolector
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@NamedQueries({ @NamedQuery(name = Recolector.LISTAR_RECOLECTOR, query = "select p from Recolector p")})
public class Recolector extends Persona implements Serializable {

	public static final String LISTAR_RECOLECTOR = "listarRecolector";
	
	private static final long serialVersionUID = 1L;

	public Recolector() {
		super();
	}
   
}
