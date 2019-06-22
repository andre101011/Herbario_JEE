package co.edu.uniquindio.AAAD.persistencia;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Informacion basica de cada una de los generos asociadas al herbario
 * 
 * @author Daniel Bonilla Guevara
 * @author Andres Felipe Llinas
 * @version 1.0
 */
@Entity
@NamedQueries({@NamedQuery(name=Genero.LISTAR_TODOS, query="select p from Genero p"),
	@NamedQuery(name = Genero.OBTENER_ESPECIES_POR_GENERO_IN, query="select especie from Genero genero, IN(genero.especiesDelGenero) especie where genero.id =:id"),
	@NamedQuery(name=Genero.BUSCAR_POR_NOMBRE, query="select p from Genero p where p.nombre =:nombre"),
	@NamedQuery(name=Genero.LISTAR_POR_FAMILIA, query="select p from Genero p where p.familiaDelGenero.id =:id")})
public class Genero implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * referencia para listar los generos
	 */
	public static final String LISTAR_TODOS="ListarGeneros";
	
	
	/**
	 * referencia para obtener la lista de especies por su genero con in
	 */
	public static final String OBTENER_ESPECIES_POR_GENERO_IN="OBTENER_ESPECIES_POR_GENERO_IN";
	
	public static final String LISTAR_POR_FAMILIA="listar por familia";
	
	/**
	 * id del genero
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable=false, length=50, unique=true)
	/**
	 * nombre del genero
	 */
	private String nombre;
	

	/**
	 * referencia para buscar un genero por su nombre
	 */
	public static final String BUSCAR_POR_NOMBRE = "buscar genero por nombre";
	/**
	 * familia del genero
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private Familia familiaDelGenero;
	/**
	 * lista de especies del genero
	 */
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval=true,mappedBy = "generoDeEspecie")
	private List<Especie> especiesDelGenero;
	

	public Genero() {
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
