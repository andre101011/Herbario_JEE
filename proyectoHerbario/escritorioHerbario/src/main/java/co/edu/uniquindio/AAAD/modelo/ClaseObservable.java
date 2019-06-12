package co.edu.uniquindio.AAAD.modelo;

import co.edu.uniquindio.AAAD.persistencia.Clase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Permite transformar una persona en formato observable
 * 
 * @author Daniel Bonilla
 * @author Andres Llinás
 * @version 1.0
 */
public class ClaseObservable {

	/**
	 * id observable de una clase
	 */
	private StringProperty id;
	/**
	 * nombre observable de una clase
	 */
	private StringProperty nombre;	
	/**
	 * clase asociado
	 */
	private Clase clase;

	/**
	 * constructor que genera con clase observable con base a un clase
	 * 
	 * @param clase que se quiere volver observable
	 */
	public ClaseObservable(Clase clase) {
		
		this.clase = (Clase) clase;
		this.id=new SimpleStringProperty(clase.getId());
		this.nombre = new SimpleStringProperty(clase.getNombre());


	}
	/**
	 * permite generar una instanci usando todos los clases
	 * 
	 * @param nombre
	 */
	public ClaseObservable(String id,String nombre) {

		this.id=new SimpleStringProperty(id);
		this.nombre = new SimpleStringProperty(nombre);
	}

	
	
	/**
	 * @return the id
	 */
	public StringProperty getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(StringProperty id) {
		this.id = id;
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
	 * @return the clase
	 */
	public Clase getClase() {
		return clase;
	}

	/**
	 * @param clase the clase to set
	 */
	public void setClase(Clase clase) {
		this.clase = clase;
	}

}
