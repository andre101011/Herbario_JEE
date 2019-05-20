package co.edu.uniquindio.AAAD.ejb;

import javax.ejb.Remote;

import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.persistencia.Empleado;

@Remote
public interface AdminEJBRemote {
	/**
	 * permite agregar un empleado
	 * @param empleado empleado  a agregar
	 * @return empleado agregado o null
	 * @throws ElementoRepetidoException cuando ya se tienen un con cedula o email repetido 
	 */
	Empleado insertarEmpleado(Empleado empleado) throws  ElementoRepetidoException;

}
