package co.edu.uniquindio.AAAD.dto;

/**
 * dto con datos relacionados a un registro
 * 
 * @author Daniel Bonilla
 * @author Andres Llinas
 * @version 1.0
 *
 */
public class RegistroDTO {
	/**
	 * id del registro
	 */
	String id;
	/**
	 * nombre del genero de la planta relacionada al registro
	 */
	String genero;
	/**
	 * nombre de la planta relacionada al registro
	 */
	String planta;
	/**
	 * cedula de la persona que envió el registro
	 */
	String cedula;
	/**
	 * email de la persona que envió el registro
	 */
	String email;
	public RegistroDTO(String id, String genero, String planta, String cedula, String email) {
		super();
		this.id = id;
		this.genero = genero;
		this.planta = planta;
		this.cedula = cedula;
		this.email = email;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}
	/**
	 * @param genero the genero to set
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}
	/**
	 * @return the planta
	 */
	public String getPlanta() {
		return planta;
	}
	/**
	 * @param planta the planta to set
	 */
	public void setPlanta(String planta) {
		this.planta = planta;
	}
	/**
	 * @return the cedula
	 */
	public String getCedula() {
		return cedula;
	}
	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	

}
