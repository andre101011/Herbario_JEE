package co.edu.uniquindio.AAAD.modelo;

import co.edu.uniquindio.AAAD.persistencia.Familia;
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
public class FamiliaObservable {

	/**
	 * id observable de un familia
	 */
	private LongProperty id;
	/**
	 * orden observable de un familia
	 */
	private StringProperty orden;
	/**
	 * nombre observable de un familia
	 */
	private StringProperty nombre;
	/**
	 * familia asociado
	 */
	private Familia familia;

	/**
	 * constructor que genera con familia observable con base a un familia
	 * 
	 * @param familia que se quiere volver observable
	 */
	public FamiliaObservable(Familia familia) {

		this.familia = (Familia) familia;
		this.id = new SimpleLongProperty(familia.getId());
		this.orden = new SimpleStringProperty(familia.getOrdenDelaFamilia().getNombre());
		this.nombre = new SimpleStringProperty(familia.getNombre());

	}

	/**
	 * permite generar una instanci usando todos los familias
	 * 
	 * @param nombre
	 */
	public FamiliaObservable(Long id, String orden, String nombre) {

		this.id = new SimpleLongProperty(id);
		this.orden = new SimpleStringProperty(orden);
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
	 * @return the orden
	 */
	public StringProperty getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(StringProperty orden) {
		this.orden = orden;
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
	 * @return the familia
	 */
	public Familia getFamilia() {
		return familia;
	}

	/**
	 * @param familia the familia to set
	 */
	public void setFamilia(Familia familia) {
		this.familia = familia;
	}

}
