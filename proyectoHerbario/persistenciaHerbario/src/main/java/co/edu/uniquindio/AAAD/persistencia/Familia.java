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
	@NamedQuery(name=Familia.BUSCAR_POR_NOMBRE, query="select p from Familia p where p.nombre =:nombre"),
	@NamedQuery(name=Familia.LISTAR_POR_ORDEN, query="select p from Familia p where p.ordenDelaFamilia.id =:id")})
public class Familia extends Categoria implements Serializable {
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
	
	public static final String LISTAR_POR_ORDEN="Listar por orden";
	
	/**
	 * referencia para contar las familias
	 */
	public static final String OBTENER_CANTIDAD_MAYOR_ESPECIES_POR_FAMILIA="Obtiene la cantidad de especies de la familia mas grande";
	
	/**
	 * referencia para buscar una familia por su nombre
	 */
	public static final String BUSCAR_POR_NOMBRE = "Buscar familia por nombre";
	
	/**
	 * orden de la familia
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private Orden ordenDelaFamilia;
	/**
	 * lista de generos de la familia
	 */
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval=true,mappedBy = "familiaDelGenero")
	private List<Genero> generosDeLaFamilia;
	

	public Familia() {
		super();
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



	   
	
	
   
}
