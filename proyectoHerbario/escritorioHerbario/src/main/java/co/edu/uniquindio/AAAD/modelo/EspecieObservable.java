package co.edu.uniquindio.AAAD.modelo;

import java.util.Date;

import co.edu.uniquindio.AAAD.persistencia.Especie;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
	 * justificación observable de una especie
	 */
	private StringProperty justificacion;
	/**
	 * fecha observable del registro de una especie
	 */
	private ObjectProperty<Date> fecha;
	
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
		this.justificacion=new SimpleStringProperty(especie.getRegistroPlanta().getJustificacion());
		this.fecha=new SimpleObjectProperty<Date>(especie.getRegistroPlanta().getFecha());


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

	/**
	 * @return the estado
	 */
	public StringProperty getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(StringProperty estado) {
		this.estado = estado;
	}

	/**
	 * @return the recolector
	 */
	public StringProperty getRecolector() {
		return recolector;
	}

	/**
	 * @param recolector the recolector to set
	 */
	public void setRecolector(StringProperty recolector) {
		this.recolector = recolector;
	}

	/**
	 * @return the evaluador
	 */
	public StringProperty getEvaluador() {
		return evaluador;
	}

	/**
	 * @param evaluador the evaluador to set
	 */
	public void setEvaluador(StringProperty evaluador) {
		this.evaluador = evaluador;
	}

	/**
	 * @return the justificacion
	 */
	public StringProperty getJustificacion() {
		return justificacion;
	}

	/**
	 * @param justificacion the justificacion to set
	 */
	public void setJustificacion(StringProperty justificacion) {
		this.justificacion = justificacion;
	}

	/**
	 * @return the fecha
	 */
	public ObjectProperty<Date> getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(ObjectProperty<Date> fecha) {
		this.fecha = fecha;
	}
	
	

}
