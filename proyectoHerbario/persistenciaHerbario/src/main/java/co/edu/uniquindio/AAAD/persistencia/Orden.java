package co.edu.uniquindio.AAAD.persistencia;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Informacion basica de cada una de los ordenes asociadas al herbario
 * 
 * @author Daniel Bonilla Guevara
 * @author Andres Felipe Llinas
 * @version 1.0
 */
@Entity
@NamedQueries({@NamedQuery(name=Orden.LISTAR_TODOS, query="select p from Orden p"),
	@NamedQuery(name=Orden.BUSCAR_POR_NOMBRE, query="select p from Orden p where p.nombre =:nombre"),
	@NamedQuery(name = Orden.LISTAR_POR_CLASE, query="select orden from Clase clase, IN(clase.ordenesDeLaClase) orden where clase.id =:id")})
public class Orden extends Categoria implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * referencia para listar los ordenes
	 */
	public static final String LISTAR_TODOS="ListarLosOrdenes";
	/**
	 * Referencia para buscar un orden por su nombre
	 */
	public static final String BUSCAR_POR_NOMBRE = "Buscar orden por nombre";
	public static final String LISTAR_POR_CLASE = "listar por Clase";
	
	/**
	 * clase del orden
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="CLASEDELORDEN_ID")
	private Clase claseDelOrden;
	
	
	/**
	 * lista de familias del orden
	 */
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval=true,mappedBy = "ordenDelaFamilia")
	private List<Familia> familiasDelOrden;

	public Orden() {
		super();
	}

	

	/**
	 * @return the familiasDelOrden
	 */
	public List<Familia> getFamiliasDelOrden() {
		return familiasDelOrden;
	}

	/**
	 * @param familiasDelOrden the familiasDelOrden to set
	 */
	public void setFamiliasDelOrden(List<Familia> familiasDelOrden) {
		this.familiasDelOrden = familiasDelOrden;
	}

	/**
	 * @return the claseDelOrden
	 */
	public Clase getClaseDelOrden() {
		return claseDelOrden;
	}

	/**
	 * @param claseDelOrden the claseDelOrden to set
	 */
	public void setClaseDelOrden(Clase claseDelOrden) {
		this.claseDelOrden = claseDelOrden;
	}


	

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getId()+","+getNombre();
	}
   
}
