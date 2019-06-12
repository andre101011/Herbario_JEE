package co.edu.uniquindio.AAAD.modelo;

import co.edu.uniquindio.AAAD.persistencia.Genero;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Permite transformar una persona en formato observable
 * 
 * @author Daniel Bonilla
 * @author Andres Llinás
 * @version 1.0
 */
public class GeneroObservable {

	/**
	 * id observable de un genero
	 */
	private StringProperty id;
	/**
	 * familia observable de un genero
	 */
	private StringProperty familia;
	/**
	 * nombre observable de un genero
	 */
	private StringProperty nombre;
	/**
	 * genero asociado
	 */
	private Genero genero;

	/**
	 * constructor que genera con genero observable con base a un genero
	 * 
	 * @param genero que se quiere volver observable
	 */
	public GeneroObservable(Genero genero) {

		this.genero = (Genero) genero;
		this.id= new SimpleStringProperty(genero.getId());
		this.familia= new SimpleStringProperty(genero.getFamiliaDelGenero().getNombre());
		this.nombre = new SimpleStringProperty(genero.getNombre());


	}

	/**
	 * permite generar una instanci usando todos los generos
	 * 
	 * @param nombre
	 */
	public GeneroObservable(String id, String familia,String nombre) {
		
		this.id = new SimpleStringProperty(id);
		this.familia = new SimpleStringProperty(familia);
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
	 * @return the familia
	 */
	public StringProperty getFamilia() {
		return familia;
	}

	/**
	 * @param familia the familia to set
	 */
	public void setFamilia(StringProperty familia) {
		this.familia = familia;
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
	 * @return the genero
	 */
	public Genero getGenero() {
		return genero;
	}

	/**
	 * @param genero the genero to set
	 */
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
	

}
