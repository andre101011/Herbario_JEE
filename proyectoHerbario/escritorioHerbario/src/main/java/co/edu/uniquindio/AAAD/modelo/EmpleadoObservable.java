package co.edu.uniquindio.AAAD.modelo;

import co.edu.uniquindio.AAAD.persistencia.Empleado;
import co.edu.uniquindio.AAAD.persistencia.Persona;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Permite transformar una persona en formato observable
 * 
 * @author Daniel Bonilla
 * @author Andres Llinás
 * @version 1.0
 */
public class EmpleadoObservable {

	/**
	 * cedula observable de un empleado
	 */
	private StringProperty cedula;
	/**
	 * nombre observable de una persona
	 */
	private StringProperty nombre;
	/**
	 * email observable de un empleado
	 */
	private StringProperty email;
	/**
	 * clave observable de un empleado
	 */
	private StringProperty clave;
	/**
	 * empleado asociado
	 */
	private Empleado empleado;

	/**
	 * 
	 * @param cedula
	 * @param nombre
	 */
	public EmpleadoObservable(String cedula, String nombre) {

		this.cedula = new SimpleStringProperty(cedula);
		this.nombre = new SimpleStringProperty(nombre);
		this.email = new SimpleStringProperty("algo@mail.com");
		this.clave = new SimpleStringProperty("12345");

	}

	/**
	 * constructor que genera con empleado observable con base a un empleado
	 * 
	 * @param empleado que se quiere volver observable
	 */
	public EmpleadoObservable(Empleado empleado) {

		this.empleado = empleado;
		this.cedula = new SimpleStringProperty(empleado.getCedula());
		this.nombre = new SimpleStringProperty(empleado.getNombre());
		this.email = new SimpleStringProperty(empleado.getEmail());
		this.clave = new SimpleStringProperty(empleado.getClave());


	}

	/**
	 * permite generar una instanci usando todos los empleados
	 * 
	 * @param cedula
	 * @param nombre
	 * @param email
	 * @param clave
	 */
	public EmpleadoObservable(String cedula, String nombre, String email, String clave) {

		this.cedula = new SimpleStringProperty(cedula);
		this.nombre = new SimpleStringProperty(nombre);
		this.email = new SimpleStringProperty(email);
		this.clave = new SimpleStringProperty(clave);
		

	}

	/**
	 * @return the cedula
	 */
	public StringProperty getCedula() {
		return cedula;
	}

	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(StringProperty cedula) {
		this.cedula = cedula;
	}

	/**
	 * @return the nombre
	 */
	public StringProperty getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(StringProperty nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the email
	 */
	public StringProperty getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(StringProperty email) {
		this.email = email;
	}

	/**
	 * @return the clave
	 */
	public StringProperty getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(StringProperty clave) {
		this.clave = clave;
	}

	/**
	 * @return the empleado
	 */
	public Empleado getEmpleado() {
		return empleado;
	}

	/**
	 * @param empleado the empleado to set
	 */
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

}
