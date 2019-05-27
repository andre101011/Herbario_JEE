package co.edu.uniquindio.AAAD.dto;
/**
 * 
 * Dto con cedula y cantidad de registros de una persona
 * 
 * @author Daniel Bonilla
 * @author Andres Llinas
 * @version 1.0
 *
 */
public class PersonaDTO {
	/**
	 * Cedula de la persona
	 */
	private String cedula;
	/**
	 * cantidad de registros de la persona
	 */
	private long numRegistros;
	
	public PersonaDTO(String cedula, Long numRegistros) {
		super();
		this.cedula = cedula;
		this.numRegistros = numRegistros;
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
	 * @return the numRegistros
	 */
	public long getNumRegistros() {
		return numRegistros;
	}

	/**
	 * @param numRegistros the numRegistros to set
	 */
	public void setNumRegistros(long numRegistros) {
		this.numRegistros = numRegistros;
	}
	
	
	
	
	

}
