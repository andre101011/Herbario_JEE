package co.edu.uniquindio.AAAD.persistencia;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;


/**
 * Informacion basica de cada una de las clases asociadas al herbario
 * 
 * @author Daniel Bonilla Guevara
 * @author Andres Felipe Llinas
 * @version 1.0
 */
@Entity
@NamedQueries({@NamedQuery(name=Clase.LISTAR_TODOS, query="select p from Clase p"),
	@NamedQuery(name=Clase.BUSCAR_POR_NOMBRE, query="select p from Clase p where p.nombre =:nombre")})
public class Clase extends Categoria implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -5662602601628056541L;


	/**
	 * Referencia para seleccionar una clase por su nombre
	 */
	public static final String BUSCAR_POR_NOMBRE="buscar clase por nombre";
	
	/**
	 * Referencia para listar las clases
	 */
	public static final String LISTAR_TODOS="listarClases";
	 
	
	
	/**
	 * lista de las ordenes de la clase
	 */
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval=true, mappedBy = "claseDelOrden")
	private List<Orden> ordenesDeLaClase;
	
	public Clase() {
		super();
	}

	
	

	/**
	 * @return the ordenesDeLaClase
	 */
	public List<Orden> getOrdenesDeLaClase() {
		return ordenesDeLaClase;
	}

	/**
	 * @param ordenesDeLaClase the ordenesDeLaClase to set
	 */
	public void setOrdenesDeLaClase(List<Orden> ordenesDeLaClase) {
		this.ordenesDeLaClase = ordenesDeLaClase;
	}


	

	   
	
	
}
