package co.edu.uniquindio.AAAD.modelo;

import co.edu.uniquindio.AAAD.persistencia.Recolector;
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
public class RecolectorObservable {

	/**
	 * cedula observable de un recolector
	 */
	private StringProperty cedula;
	/**
	 * nombre observable de una persona
	 */
	private StringProperty nombre;
	/**
	 * email observable de un recolector
	 */
	private StringProperty email;
	/**
	 * clave observable de un recolector
	 */
	private StringProperty clave;
	/**
	 * recolector asociado
	 */
	private Recolector recolector;

	/**
	 * 
	 * @param cedula
	 * @param nombre
	 */
	public RecolectorObservable(String cedula, String nombre) {

		this.cedula = new SimpleStringProperty(cedula);
		this.nombre = new SimpleStringProperty(nombre);
		this.email = new SimpleStringProperty("algo@mail.com");
		this.clave = new SimpleStringProperty("12345");

	}

	/**
	 * constructor que genera con recolector observable con base a un recolector
	 * 
	 * @param recolector que se quiere volver observable
	 */
	public RecolectorObservable(Persona recolector) {

		this.recolector = (Recolector) recolector;
		this.cedula = new SimpleStringProperty(recolector.getCedula());
		this.nombre = new SimpleStringProperty(recolector.getNombre());
		this.email = new SimpleStringProperty(recolector.getEmail());
		this.clave = new SimpleStringProperty(recolector.getClave());

	}

	/**
	 * permite generar una instanci usando todos los recolectores
	 * 
	 * @param cedula
	 * @param nombre
	 * @param email
	 * @param clave
	 */
	public RecolectorObservable(String cedula, String nombre, String email, String clave) {

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
	 * @return the recolector
	 */
	public Recolector getRecolector() {
		return recolector;
	}

	/**
	 * @param recolector the recolector to set
	 */
	public void setRecolector(Recolector recolector) {
		this.recolector = recolector;
	}

}
