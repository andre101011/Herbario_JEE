package co.edu.uniquindio.AAAD.persistencia;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@NamedQueries({ @NamedQuery(name = Persona.LISTAR_TODOS, query = "select p from Persona p where p.visibilidad =:visibilidad"),
	@NamedQuery(name=Persona.INICIO_SESION, query="select p from Persona p where p.email= :email1 and p.clave= :clave1 and p.visibilidad =:visibilidad"),
	@NamedQuery(name=Persona.CONTAR_PERSONAS, query="select count(distinct registro.enviadorDelRegistro ) from Registro registro where registro.estado =:est group by registro.fecha"),
	@NamedQuery(name=Persona.lISTAR_SIN_REGISTROS, query="select persona from Persona persona LEFT JOIN persona.registrosEnviados registro where persona.registrosEnviados IS EMPTY and persona.visibilidad =:visibilidad"),
	@NamedQuery(name=Persona.LISTAR_DTO, query="select new co.edu.uniquindio.AAAD.dto.PersonaDTO(persona.cedula,count(persona.registrosEnviados)) from Persona persona where persona.visibilidad =:visibilidad group by persona.cedula"),
	@NamedQuery(name=Persona.OBTENER_REGISTROS_POR_CEDULA_PERSONA, query="select registro from Persona p INNER JOIN p.registrosEnviados registro where p.cedula=:cedula and p.visibilidad =:visibilidad"),
	@NamedQuery(name=Persona.LISTAR_CEDULAS_CON_REGISTROS, query="select persona.cedula,registro from Persona persona LEFT JOIN persona.registrosEnviados registro where persona.visibilidad =:visibilidad"),
	@NamedQuery(name= Persona.BUSCAR_PERSONA_POR_EMAIL, query="select e from Persona e where e.email=:email")})
@Inheritance(strategy = JOINED)
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * referencia para obtener registros por cedula de la persona
	 */
	public static final String OBTENER_REGISTROS_POR_CEDULA_PERSONA="OBTENER_REGISTROS_POR_CEDULA_PERSONA";
	/**
	 * referencia para listar la cedula con cada uno de los registros
	 */
	public static final String LISTAR_CEDULAS_CON_REGISTROS ="LISTAR_CEDULAS_CON_REGISTROS";
	
	/**
	 * referencia para listar los dto con la cedula y la cantidad de registros de cada persona
	 */
	public static final String LISTAR_DTO="listaLosDTOsConCedulaYCantidadDeRegistros";
	
	/**
	 * referencia para contar personas a las que les han aceptado un registro en un mismo dia
	 */
	public static final String CONTAR_PERSONAS="ContarPersonaALaQuelesHanAceptadoRegistros";
	
	/**
	 * referencia para obtener personas que no han realizado registros
	 */
	public static final String lISTAR_SIN_REGISTROS="ListaPersonasSinRegistros";
	
	/**
	 * referencia para el inicio de sesión
	 */
	public static final String INICIO_SESION="InicioSesion";
	/**
	 * referencia para listar los clientes
	 */
	public static final String LISTAR_TODOS = "ListarLosClientes";
	/**
	 * referencia para buscar una persona por su email
	 */
	public static final String BUSCAR_PERSONA_POR_EMAIL = "Buscar persona por email";
	
	

	
	/**
	 * cedula de la persona
	 */
	@Id
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
	
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private Visibilidad visibilidad;
	
	public enum Visibilidad {
		HABILITADO,
		INHABILITADO
	}

	/**
	 * Lista de los registros enviados
	 */
	@OneToMany(mappedBy = "enviadorDelRegistro")
	private List<Registro> registrosEnviados;
	
	public Persona() {
		super();
		this.visibilidad=Visibilidad.HABILITADO;
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

	/**
	 * @return the visibilidad
	 */
	public Visibilidad getVisibilidad() {
		return visibilidad;
	}



	/**
	 * @param visibilidad the visibilidad to set
	 */
	public void setVisibilidad(Visibilidad visibilidad) {
		this.visibilidad = visibilidad;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cedula == null) ? 0 : cedula.hashCode());
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
		if (cedula == null) {
			if (other.cedula != null)
				return false;
		} else if (!cedula.equals(other.cedula))
			return false;
		return true;
	}

	

	
	
	


}
