package co.edu.uniquindio.AAAD;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Inheritance;
import static javax.persistence.InheritanceType.JOINED;

/**
 * Informacion basica de cada una de las personas asociadas al herbario
 * 
 * @author Daniel Bonilla Guevara
 * @author Andres Felipe Llinas
 * @version 1.0
 */
@Entity
@NamedQueries({ @NamedQuery(name = Persona.LISTAR_TODOS, query = "select p from Persona p"),
	@NamedQuery(name=Persona.INICIO_SESION, query="select p from Persona p where p.email= :email1 and p.clave= :clave1"),
	@NamedQuery(name=Persona.CONTAR_PERSONAS, query="select count(DISTINCT registro.p ) from Registro registro where registro.fecha =:fecha and registro.estado =:est")})
@Inheritance(strategy = JOINED)
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * referencia para contar personas a las que les han aceptado un registro en un mismo dia
	 */
	public static final String CONTAR_PERSONAS="ContarPersonaALaQuelesHanAceptadoRegistros";
	
	/**
	 * referencia para el inicio de sesión
	 */
	public static final String INICIO_SESION="InicioSesion";
	
	/**
	 * referencia para listar los clientes
	 */
	public static final String LISTAR_TODOS = "ListarLosClientes";

	/**
	 * id de la persona
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	/**
	 * cedula de la persona
	 */
	@Column(nullable=false, length=20)
	private String cedula;
	/**
	 * nombre de la persona
	 */
	@Column(length=50)
	private String nombre;
	
	/**
	 * email de la persona
	 */
	@Column(nullable=false, length=50)
	private String email;
	/**
	 * clave de acceso a la plataforma
	 */
	@Column(nullable=false, length=50)
	private String clave;

	/**
	 * Lista de los registros enviados
	 */
	@OneToMany(mappedBy = "enviadorDelRegistro")
	private List<Registro> registrosEnviados;
	
	public Persona() {
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
	 * @return the cedula
	 */
	public String getCedula() {
		return cedula;
	}

	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(String cedula) {
		this.cedula = cedula;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * @return the registrosEnviados
	 */
	public List<Registro> getRegistrosEnviados() {
		return registrosEnviados;
	}

	/**
	 * @param registrosEnviados the registrosEnviados to set
	 */
	public void setRegistrosEnviados(List<Registro> registrosEnviados) {
		this.registrosEnviados = registrosEnviados;
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
		Persona other = (Persona) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	
	
	


}
