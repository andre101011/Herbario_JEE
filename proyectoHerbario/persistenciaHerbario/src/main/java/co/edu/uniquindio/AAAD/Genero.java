package co.edu.uniquindio.AAAD;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Genero
 *
 */
@Entity
@NamedQueries({@NamedQuery(name=Genero.LISTAR_TODOS, query="select p from Genero p")})
public class Genero implements Serializable {
	
	public static final String LISTAR_TODOS="ListarGeneros";
	
	
	@Id
	private String id;
	@Column(nullable=false, length=50)
	private String nombre;
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Familia familiaDelGenero;
	
	@OneToMany(mappedBy = "generoDeEspecie")
	private List<Especie> especiesDelGenero;

	public Genero() {
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
	 * @return the familiaDelGenero
	 */
	public Familia getFamiliaDelGenero() {
		return familiaDelGenero;
	}

	/**
	 * @param familiaDelGenero the familiaDelGenero to set
	 */
	public void setFamiliaDelGenero(Familia familiaDelGenero) {
		this.familiaDelGenero = familiaDelGenero;
	}

	/**
	 * @return the especiesDelGenero
	 */
	public List<Especie> getEspeciesDelGenero() {
		return especiesDelGenero;
	}

	/**
	 * @param especiesDelGenero the especiesDelGenero to set
	 */
	public void setEspeciesDelGenero(List<Especie> especiesDelGenero) {
		this.especiesDelGenero = especiesDelGenero;
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
		Genero other = (Genero) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}   
	
	
   
}
