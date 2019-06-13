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
public class Clase implements Serializable {

	/**
	 * Referencia para seleccionar una clase por su nombre
	 */
	public static final String BUSCAR_POR_NOMBRE="buscar clase por nombre";
	
	/**
	 * Referencia para listar las clases
	 */
	public static final String LISTAR_TODOS="listarClases";
	 
	/**
	 * id de la clase
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	/**
	 * nombre de la clase
	 */
	@Column(nullable=false, length=50, unique=true)
	private String nombre;
	private static final Long serialVersionUID = 1L;
	/**
	 * lista de las ordenes de la clase
	 */
	@OneToMany(mappedBy = "claseDelOrden")
	private List<Orden> ordenesDeLaClase;

	public Clase() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Clase other = (Clase) obj;
		if (id != other.id)
			return false;
		return true;
	}

	   
	
	
}
