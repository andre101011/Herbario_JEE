package co.edu.uniquindio.AAAD.ejb;

import javax.ejb.Remote;

import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.persistencia.*;

@Remote
public interface AdminEJBRemote {
	/**
	 * permite agregar un empleado
	 * @param empleado empleado  a agregar
	 * @return empleado agregado o null
	 * @throws ElementoRepetidoException cuando ya se tienen un con cedula o email repetido 
	 */
	Empleado insertarEmpleado(Empleado empleado) throws  ElementoRepetidoException;
	
	/**
	 * Permite agregar una clase 
	 * @param clase clase a agregar
	 * @return clase agregada o null
	 * @throws ElementoRepetidoException cuando ya se tiene un nombre repetido
	 */
	Clase insertarClase(Clase clase) throws ElementoRepetidoException;

}