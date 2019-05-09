package co.edu.uniquindio.AAAD;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Tribu
 *
 */
@Entity
@NamedQueries({@NamedQuery(name=Clase.LISTAR_TODOS, query="select p from Clase p")})
public class Clase implements Serializable {

	public static final String LISTAR_TODOS="listarClases";
	   
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	@Column(nullable=false, length=50)
	private String nombre;
	private static final long serialVersionUID = 1L;
	
	
	
	@OneToMany(mappedBy = "claseDelOrden")
	private List<Orden> ordenesDeLaClase;

	public Clase() {
		super();
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}   
	
	
}
