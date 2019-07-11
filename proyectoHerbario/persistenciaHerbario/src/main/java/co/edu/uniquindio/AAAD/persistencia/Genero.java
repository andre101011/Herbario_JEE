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
public class Genero extends Categoria implements Serializable {
	
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

	
	
	
	
   
}
