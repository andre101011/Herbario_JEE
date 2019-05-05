package co.edu.uniquindio.AAAD;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Familia
 *
 */
@Entity

public class Familia implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	@Column(nullable=false, length=50)
	private String nombre;
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Orden ordenDelaFamilia;
	
	@OneToMany(mappedBy = "familiaDeLaTribu")
	private List<Tribu> tribusDeLaFamilia;

	public Familia() {
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
	 * @return the ordenDelaFamilia
	 */
	public Orden getOrdenDelaFamilia() {
		return ordenDelaFamilia;
	}

	/**
	 * @param ordenDelaFamilia the ordenDelaFamilia to set
	 */
	public void setOrdenDelaFamilia(Orden ordenDelaFamilia) {
		this.ordenDelaFamilia = ordenDelaFamilia;
	}

	/**
	 * @return the tribusDeLaFamilia
	 */
	public List<Tribu> getTribusDeLaFamilia() {
		return tribusDeLaFamilia;
	}

	/**
	 * @param tribusDeLaFamilia the tribusDeLaFamilia to set
	 */
	public void setTribusDeLaFamilia(List<Tribu> tribusDeLaFamilia) {
		this.tribusDeLaFamilia = tribusDeLaFamilia;
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
		Familia other = (Familia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}   
	
	
   
}
