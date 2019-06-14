package co.edu.uniquindio.AAAD.modelo;

import co.edu.uniquindio.AAAD.persistencia.Orden;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Permite transformar una persona en formato observable
 * 
 * @author Daniel Bonilla
 * @author Andres Llinás
 * @version 1.0
 */
public class OrdenObservable {

	/**
	 * id observable de un orden
	 */
	private LongProperty id;
	/**
	 * clase observable de un orden
	 */
	private StringProperty clase;
	/**
	 * nombre observable de un orden
	 */
	private StringProperty nombre;
	/**
	 * orden asociado
	 */
	private Orden orden;

	/**
	 * constructor que genera con orden observable con base a un orden
	 * 
	 * @param orden que se quiere volver observable
	 */
	public OrdenObservable(Orden orden) {

		this.orden = (Orden) orden;
		this.id = new SimpleLongProperty(orden.getId());
		this.clase = new SimpleStringProperty(orden.getClaseDelOrden().getNombre());
		this.nombre = new SimpleStringProperty(orden.getNombre());

	}

	/**
	 * permite generar una instanci usando todos los ordens
	 * 
	 * @param nombre
	 */
	public OrdenObservable(Long id, String clase, String nombre) {

		this.id = new SimpleLongProperty(id);
		this.clase = new SimpleStringProperty(clase);
		this.nombre = new SimpleStringProperty(nombre);
	}

	/**
	 * @return the id
	 */
	public LongProperty getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(LongProperty id) {
		this.id = id;
	}

	/**
	 * @return the clase
	 */
	public StringProperty getClase() {
		return clase;
	}

	/**
	 * @param clase the clase to set
	 */
	public void setClase(StringProperty clase) {
		this.clase = clase;
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
	 * @return the orden
	 */
	public Orden getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Orden orden) {
		this.orden = orden;
	}

}
