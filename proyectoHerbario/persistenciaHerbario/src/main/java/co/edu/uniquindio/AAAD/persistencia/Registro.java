package co.edu.uniquindio.AAAD.persistencia;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

/**
 * Informacion basica de cada una de los registros asociados al herbario
 * 
 * @author Daniel Bonilla Guevara
 * @author Andres Felipe Llinas
 * @version 1.0
 */
@Entity
@NamedQueries({ @NamedQuery(name = Registro.LISTAR_TODOS, query = "select p from Registro p"),
	@NamedQuery(name=Registro.OBTENER_RECOLECTORES_CON_REGISTROS, query ="select distinct r.enviadorDelRegistro from Registro r"),
	@NamedQuery(name=Registro.OBTENER_ARREGLO_DE_DATOS, query="select r.id,r.especieEnviada,r.especieEnviada.generoDeEspecie,r.enviadorDelRegistro.cedula,r.enviadorDelRegistro.email from Registro r where r.fecha =:fecha"),
	@NamedQuery(name=Registro.OBTENER_DTO_REGISTRO, query="select new co.edu.uniquindio.AAAD.dto.RegistroDTO(r.id,r.especieEnviada.generoDeEspecie.nombre,r.especieEnviada.nombre,r.enviadorDelRegistro.cedula,r.enviadorDelRegistro.email) from Registro r where r.fecha =:fecha")})

public class Registro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * refrencia para obtener una lista con dtos
	 */
	public static final String OBTENER_DTO_REGISTRO="Obtener DTO con datos relacionados al registro";
	
	/**
	 * refrencia para obtener una lista con vectores de datos
	 */
	public static final String OBTENER_ARREGLO_DE_DATOS="Obtener vector con datos relacionados al registro";
	
	/**
	 * referencia para obtener recolectores con registro
	 */
	public static final String OBTENER_RECOLECTORES_CON_REGISTROS="Obtener reolectores con registros";
	
	/**
	 * referencia para listar los registros
	 */
	public static final String LISTAR_TODOS="Listar los registros";
	   
	/**
	 * 
	 * @author Daniel Bonilla
	 * @author Andres Llinas
	 * 
	 * enum con los estados posibles del registro
	 *
	 */
	public enum Estado{
		
		Aceptado,
		
		Rechazado,
		
		Espera
		
	}
	
	/**
	 * id del registro
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	/**
	 * estado del registro
	 */
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private Estado estado;
	/**
	 * fecha del registro
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	/**
	 * justificacion del registro
	 */
	private String justificacion;
	/**
	 * especie enviada a travess del registro
	 */
	@OneToOne(mappedBy = "registroPlanta", cascade = CascadeType.PERSIST)
	private Especie especieEnviada;
	/**
	 * persona que envió el registro
	 */
	@ManyToOne
	private Persona enviadorDelRegistro;
	/**
	 * persona que evaluó el registró
	 */
	@ManyToOne
	private Administrador evaluadorDelRegistro;

	public Registro() {
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
	 * @return the estado
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	
	
	/**
	 * @return the especieEnviada
	 */
	public Especie getEspecieEnviada() {
		return especieEnviada;
	}

	/**
	 * @param especieEnviada the especieEnviada to set
	 */
	public void setEspecieEnviada(Especie especieEnviada) {
		this.especieEnviada = especieEnviada;
	}

	/**
	 * @return the enviadorDelRegistro
	 */
	public Persona getEnviadorDelRegistro() {
		return enviadorDelRegistro;
	}

	/**
	 * @param enviadorDelRegistro the enviadorDelRegistro to set
	 */
	public void setEnviadorDelRegistro(Persona enviadorDelRegistro) {
		this.enviadorDelRegistro = enviadorDelRegistro;
	}

	/**
	 * @return the evaluadorDelRegistro
	 */
	public Administrador getEvaluadorDelRegistro() {
		return evaluadorDelRegistro;
	}

	/**
	 * @param evaluadorDelRegistro the evaluadorDelRegistro to set
	 */
	public void setEvaluadorDelRegistro(Administrador evaluadorDelRegistro) {
		this.evaluadorDelRegistro = evaluadorDelRegistro;
	}

	/**
	 * @return the justificacion
	 */
	public String getJustificacion() {
		return justificacion;
	}

	/**
	 * @param justificacion the justificacion to set
	 */
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
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
		Registro other = (Registro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

	
	
	
   
}
