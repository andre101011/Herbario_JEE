package co.edu.uniquindio.AAAD;

import java.io.Serializable;
import javax.persistence.*;

import co.edu.uniquindio.AAAD.Persona;

/**
 * Informacion basica de cada una de las clases asociadas al herbario
 * 
 * @author Daniel Bonilla Guevara
 * @author Andres Felipe Llinas
 * @version 1.0
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@NamedQueries({ @NamedQuery(name = Empleado.LISTAR_EMPLEADO, query = "select p from Empleado p")})
public class Empleado extends Persona implements Serializable {

	/**
	 * Referencia para listar los empleados
	 */
	public static final String LISTAR_EMPLEADO = "listarEmpleado";
	
	private static final long serialVersionUID = 1L;

	public Empleado() {
		super();
	}
   
}
