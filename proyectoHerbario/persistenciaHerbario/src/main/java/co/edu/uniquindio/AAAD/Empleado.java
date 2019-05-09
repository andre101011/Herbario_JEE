package co.edu.uniquindio.AAAD;

import java.io.Serializable;
import javax.persistence.*;

import co.edu.uniquindio.AAAD.Persona;

/**
 * Entity implementation class for Entity: Empleado
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@NamedQueries({ @NamedQuery(name = Empleado.LISTAR_EMPLEADO, query = "select p from Empleado p")})
public class Empleado extends Persona implements Serializable {

	public static final String LISTAR_EMPLEADO = "listarEmpleado";
	
	private static final long serialVersionUID = 1L;

	public Empleado() {
		super();
	}
   
}
