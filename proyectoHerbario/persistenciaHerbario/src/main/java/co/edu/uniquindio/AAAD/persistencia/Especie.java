package co.edu.uniquindio.AAAD.persistencia;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Informacion basica de cada una de las especies asociadas al herbario
 * 
 * @author Daniel Bonilla Guevara
 * @author Andres Felipe Llinas
 * @version 1.0
 */
@Entity
@NamedQueries({ @NamedQuery(name = Especie.LISTAR_TODOS, query = "select p from Especie p"),
		@NamedQuery(name = Especie.LISTAR_POR_ESTADO, query = "select p from Especie p where p.registroPlanta.estado= :est"),
		@NamedQuery(name = Especie.LISTAR_POR_GENERO, query = "select p from Especie p where p.generoDeEspecie.id= :gen"),
		@NamedQuery(name = Especie.LISTAR_POR_FAMILIA, query = "select p from Especie p where p.generoDeEspecie.familiaDelGenero.id= :fam"),
		@NamedQuery(name = Especie.LISTAR_POR_RECOLECTOR, query = "select p from Especie p where p.registroPlanta.enviadorDelRegistro.cedula= :cedula"),
		@NamedQuery(name = Especie.LISTAR_POR_CLASE, query = "select p from Especie p where p.generoDeEspecie.familiaDelGenero.ordenDelaFamilia.claseDelOrden.id= :clas"),
		@NamedQuery(name = Especie.LISTAR_POR_ORDEN, query = "select p from Especie p where p.generoDeEspecie.familiaDelGenero.ordenDelaFamilia.id= :ord"),
		@NamedQuery(name = Especie.LISTAR_POR_GENERO_ACEPTADA, query = "select p from Especie p where p.generoDeEspecie.id= :gen and p.registroPlanta.estado= :est "),
		@NamedQuery(name = Especie.LISTAR_POR_FAMILIA_ACEPTADA, query = "select p from Especie p where p.generoDeEspecie.familiaDelGenero.id= :fam and p.registroPlanta.estado= :est"),
		@NamedQuery(name = Especie.LISTAR_POR_CLASE_ACEPTADA, query = "select p from Especie p where p.generoDeEspecie.familiaDelGenero.ordenDelaFamilia.claseDelOrden.id= :clas and p.registroPlanta.estado= :est"),
		@NamedQuery(name = Especie.LISTAR_POR_ORDEN_ACEPTADA, query = "select p from Especie p where p.generoDeEspecie.familiaDelGenero.ordenDelaFamilia.id= :ord and p.registroPlanta.estado= :est"),
		@NamedQuery(name = Especie.OBTENER_FAMILIA_POR_ID_ESPECIE, query = "select p.generoDeEspecie.familiaDelGenero from Especie p where p.id= :id"),
		@NamedQuery(name = Especie.BUSCAR_POR_NOMBRE, query = "select p from Especie p where p.nombre= :nombre")})

public class Especie implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Referencia para listar las especies
	 */
	public static final String LISTAR_TODOS = "ListarEspecies";
	/**
	 * Referencia para listar las de acuerdo a su estado
	 */
	public static final String LISTAR_POR_ESTADO = "ListarEspeciesPorEstado";
	/**
	 * Referencia para listar las de acuerdo a su clase
	 */
	public static final String LISTAR_POR_CLASE = "ListarEspeciesPorClase";
	
	/**
	 * Referencia para obtener una familia de acuerdo al id de la especie a la que pertenece
	 */
	public static final String OBTENER_FAMILIA_POR_ID_ESPECIE = "obtener familia por id de especie";
	
	/**
	 * Referencia para listar las de acuerdo a su orden
	 */
	public static final String LISTAR_POR_ORDEN = "ListarEspeciesPorOrden";
	/**
	 * Referencia para listar las de acuerdo a su genero
	 */
	public static final String LISTAR_POR_GENERO = "ListarEspeciesPorGenero";
	/**
	 * Referencia para listar las de acuerdo a su familia
	 */
	public static final String LISTAR_POR_FAMILIA = "ListarEspeciesPorFamilia";
	
	public static final String LISTAR_POR_CLASE_ACEPTADA = "ListarEspeciesAceptadasPorClase";
	/**
	 * Referencia para listar las de acuerdo a su orden
	 */
	public static final String LISTAR_POR_ORDEN_ACEPTADA = "ListarEspeciesAceptadasPorOrden";
	/**
	 * Referencia para listar las de acuerdo a su genero
	 */
	public static final String LISTAR_POR_GENERO_ACEPTADA = "ListarEspeciesAceptadasPorGenero";
	/**
	 * Referencia para listar las de acuerdo a su familia
	 */
	public static final String LISTAR_POR_FAMILIA_ACEPTADA = "ListarEspeciesAceptadasPorFamilia";
	/**
	 * Referencia para listar las de acuerdo a su recolector
	 */
	public static final String LISTAR_POR_RECOLECTOR = "ListarEspeciesPorRecolector";
	/**
	 * referencia para obtener las especies con un mismo nombre
	 */
	public static final String BUSCAR_POR_NOMBRE = "listarEspeciesPorNombre";

	/**
	 * id de la especie
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * nombre de la especie
	 */
	@Column(nullable = false, length = 50)
	private String nombre;
	/**
	 * registro de la especie
	 */
	private Registro registroPlanta;
	/**
	 * genero de la especie
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private Genero generoDeEspecie;
	/**
	 * imagen de la especie
	 */
	private byte[] imagen;

	public Especie() {
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
	 * @return the registroPlanta
	 */
	public Registro getRegistroPlanta() {
		return registroPlanta;
	}

	/**
	 * @param registroPlanta the registroPlanta to set
	 */
	public void setRegistroPlanta(Registro registroPlanta) {
		this.registroPlanta = registroPlanta;
	}

	/**
	 * @return the generoDeEspecie
	 */
	public Genero getGeneroDeEspecie() {
		return generoDeEspecie;
	}

	/**
	 * @param generoDeEspecie the generoDeEspecie to set
	 */
	public void setGeneroDeEspecie(Genero generoDeEspecie) {
		this.generoDeEspecie = generoDeEspecie;
	}
	

	/**
	 * @return the imagen
	 */
	public byte[] getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
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
		Especie other = (Especie) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

	

}
