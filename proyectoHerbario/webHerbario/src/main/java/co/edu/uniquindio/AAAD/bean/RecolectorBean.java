package co.edu.uniquindio.AAAD.bean;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Named;

import co.edu.uniquindio.AAAD.ejb.AdminEJB;
import co.edu.uniquindio.AAAD.ejb.NegocioEJB;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.persistencia.Recolector;



/**
 * permite gestionar la informacion de una familia de plantas
 * 
 * @author stiven @version1.0
 *
 */
@FacesConfig(version = Version.JSF_2_3)
@Named(value = "recolectorBean")
@ApplicationScoped
public class RecolectorBean {

	/**
	 * nombre de la familia de plantas
	 */
	private String nombre;
	/**
	 * cedula del empleado
	 */
	private String cedula;
	/**
	 * email del empleado
	 */
	private String email;
	/**
	 * clave del  empleado
	 */
	private String clave;
	
	private Recolector f;

	/**
	 * referencia de la capa de negocio
	 */
	@EJB
	private NegocioEJB negocioEJB;

	public String registrar() {
		try {
			Recolector miRecolector = new Recolector();
			miRecolector.setNombre(nombre);
			f=negocioEJB.insertarRecolector(miRecolector);
			return "/detalle_familia";
		} catch (ElementoRepetidoException e) {
			// Util
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
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
	 * @return the f
	 */
	public Recolector getF() {
		return f;
	}

	/**
	 * @param f the f to set
	 */
	public void setF(Recolector f) {
		this.f = f;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
