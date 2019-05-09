package co.edu.uniquindio.AAAD;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Registro
 *
 */
@Entity
@NamedQueries({ @NamedQuery(name = Registro.LISTAR_TODOS, query = "select p from Registro p")})


public class Registro implements Serializable {

	
	public static final String LISTAR_TODOS="Listar todos";
	   
	enum Estado{
		
		Aceptado,
		
		Rechazado,
		
		Espera
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private Estado estado;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@OneToOne(mappedBy = "registroPlanta")
	private Especie especieEnviada;
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Persona enviadorDelRegistro;
	
	@ManyToOne
	private Administrador evaluadorDelRegistro;

	public Registro() {
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
