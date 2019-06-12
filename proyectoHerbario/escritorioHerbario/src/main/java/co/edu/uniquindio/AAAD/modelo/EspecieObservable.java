package co.edu.uniquindio.AAAD.modelo;

import co.edu.uniquindio.AAAD.persistencia.Especie;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Permite transformar una persona en formato observable
 * 
 * @author Daniel Bonilla
 * @author Andres Llinás
 * @version 1.0
 */
public class EspecieObservable {

	/**
	 * id observable de un especie
	 */
	private StringProperty id;
	/**
	 * genero observable de un especie
	 */
	private StringProperty genero;
	/**
	 * nombre observable de un especie
	 */
	private StringProperty nombre;
	/**
	 * estado observable de un especie
	 */
	private StringProperty estado;
	/**
	 * recolector observable de un especie
	 */
	private StringProperty recolector;
	/**
	 * evaluador observable de un especie
	 */
	private StringProperty evaluador;
	
	/**
	 * especie asociado
	 */
	private Especie especie;

	/**
	 * constructor que genera con especie observable con base a un especie
	 * 
	 * @param especie que se quiere volver observable
	 */
	public EspecieObservable(Especie especie) {

		this.especie = (Especie) especie;
		this.id= new SimpleStringProperty(especie.getId());
		this.genero= new SimpleStringProperty(especie.getGeneroDeEspecie().getNombre());
		this.nombre = new SimpleStringProperty(especie.getNombre());
		this.estado=new SimpleStringProperty(especie.getRegistroPlanta().getEstado().toString());
		this.recolector=new SimpleStringProperty(especie.getRegistroPlanta().getEnviadorDelRegistro().getNombre());
		this.evaluador=new SimpleStringProperty(especie.getRegistroPlanta().getEvaluadorDelRegistro().getNombre());


	}

	/**
	 * permite generar una instanci usando todos los especies
	 * 
	 * @param nombre
	 */
	public EspecieObservable(String id, String genero,String nombre) {
		
		this.id = new SimpleStringProperty(id);
		this.genero = new SimpleStringProperty(genero);
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
	 * @return the genero
	 */
	public StringProperty getGenero() {
		return genero;
	}

	/**
	 * @param genero the genero to set
	 */
	public void setGenero(StringProperty genero) {
		this.genero = genero;
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
	 * @return the especie
	 */
	public Especie getEspecie() {
		return especie;
	}

	/**
	 * @param especie the especie to set
	 */
	public void setEspecie(Especie especie) {
		this.especie = especie;
	}
	
	

}
