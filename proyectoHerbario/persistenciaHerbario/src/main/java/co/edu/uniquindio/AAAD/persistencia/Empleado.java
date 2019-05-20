package co.edu.uniquindio.AAAD.persistencia;

import java.io.Serializable;
import javax.persistence.*;

import co.edu.uniquindio.AAAD.persistencia.Persona;

/**
 * Informacion basica de cada una de las clases asociadas al herbario
 * 
 * @author Daniel Bonilla Guevara
 * @author Andres Felipe Llinas
 * @version 1.0
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@NamedQueries({ @NamedQuery(name = Empleado.LISTAR_EMPLEADO, query = "select p from Empleado p"),
	@NamedQuery(name= Empleado.BUSCAR_EMPLEADO_POR_EMAIL, query="select e from Empleado e where e.email=:email")})
public class Empleado extends Persona implements Serializable {

	/**
	 * Referencia para listar los empleados
	 */
	public static final String LISTAR_EMPLEADO = "listarEmpleado";
	
	private static final long serialVersionUID = 1L;
	
	public static final String BUSCAR_EMPLEADO_POR_EMAIL = "EmpleadoPorEmail";

	public Empleado() {
		super();
	}
   
}
