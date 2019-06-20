package co.edu.uniquindio.AAAD.persistencia;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Informacion basica de cada una de las familias asociadas al herbario
 * 
 * @author Daniel Bonilla Guevara
 * @author Andres Felipe Llinas
 * @version 1.0
 */
@Entity
@NamedQueries({@NamedQuery(name=Familia.LISTAR_TODOS, query="select p from Familia p"),
	@NamedQuery(name=Familia.CONTAR, query="select count(p) from Familia p"),
	@NamedQuery(name=Familia.OBTENER_FAMILIA_MAS_ESPECIES, query="select fam,max(select count(esp) from Genero gen inner join gen.especiesDelGenero esp where gen.familiaDelGenero.id=fam.id) from Familia fam"),
	@NamedQuery(name=Familia.BUSCAR_POR_NOMBRE, query="select p from Familia p where p.nombre =:nombre")})
public class Familia implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * referencia para listar las familias
	 */
	public static final String LISTAR_TODOS = "listarFamilias";
	
	
	public static final String OBTENER_FAMILIA_MAS_ESPECIES="obtener la familia con mas especies";
	
	/**
	 * referencia para contar las familias
	 */
	public static final String CONTAR="ContarFamilias";
	
	/**
	 * referencia para contar las familias
	 */
	public static final String OBTENER_CANTIDAD_MAYOR_ESPECIES_POR_FAMILIA="Obtiene la cantidad de especies de la familia mas grande";
	
	/**
	 * referencia para buscar una familia por su nombre
	 */
	public static final String BUSCAR_POR_NOMBRE = "Buscar familia por nombre";
	
	/**
	 * id de la familia
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	/**
	 * nombre de la familia
	 */
	@Column(nullable=false, length=50, unique=true)
	private String nombre;
	
	/**
	 * orden de la familia
	 */
	@ManyToOne
	private Orden ordenDelaFamilia;
	/**
	 * lista de generos de la familia
	 */
	@OneToMany(cascade = {CascadeType.REMOVE},mappedBy = "familiaDelGenero")
	private List<Genero> generosDeLaFamilia;
	

	public Familia() {
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
	 * @return the generosDeLaFamilia
	 */
	public List<Genero> getGenerosDeLaFamilia() {
		return generosDeLaFamilia;
	}

	/**
	 * @param generosDeLaFamilia the generosDeLaFamilia to set
	 */
	public void setGenerosDeLaFamilia(List<Genero> generosDeLaFamilia) {
		this.generosDeLaFamilia = generosDeLaFamilia;
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
		Familia other = (Familia) obj;
		if (id != other.id)
			return false;
		return true;
	}

	   
	
	
   
}
