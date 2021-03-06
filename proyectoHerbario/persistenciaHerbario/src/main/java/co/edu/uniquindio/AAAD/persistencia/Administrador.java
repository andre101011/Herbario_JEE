package co.edu.uniquindio.AAAD.persistencia;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import co.edu.uniquindio.AAAD.persistencia.Persona;

/**
 * Informacion basica de cada uno de los empleados asociados al herbario
 * 
 * @author Daniel Bonilla Guevara
 * @author Andres Felipe Llinas
 * @version 1.0
 */
@Entity
@NamedQueries({ @NamedQuery(name = Administrador.LISTAR_ADMINISTRADOR, query = "select p from Administrador p where p.visibilidad =:visibilidad"),
	@NamedQuery(name=Administrador.CONTAR_ADMINS, query="select count(a) from Administrador a where a.visibilidad =:visibilidad")})
public class Administrador extends Persona implements Serializable {

	/**
	 * referencia para listar los administradores
	 */
	public static final String LISTAR_ADMINISTRADOR = "listarAdministrador";
	/**
	 * referencia para contar los administradores
	 */
	public static final String CONTAR_ADMINS= "contarAdmins";
	
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * lista de registros evaluados por el administrador
	 */
	@OneToMany(mappedBy = "evaluadorDelRegistro")
	private List<Registro> registrosEvaluados;

	public Administrador() {
		super();
	}

	/**
	 * @return the registrosEvaluados
	 */
	public List<Registro> getRegistrosEvaluados() {
		return registrosEvaluados;
	}

	/**
	 * @param registrosEvaluados the registrosEvaluados to set
	 */
	public void setRegistrosEvaluados(List<Registro> registrosEvaluados) {
		this.registrosEvaluados = registrosEvaluados;
	}
	
	
	
   
}
