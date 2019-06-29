package co.edu.uniquindio.AAAD.web;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Named;

import co.edu.uniquindio.AAAD.ejb.AdminEJB;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.persistencia.Familia;



/**
 * permite gestionar la informacion de una familia de plantas
 * 
 * @author stiven @version1.0
 *
 */
@FacesConfig(version = Version.JSF_2_3)
@Named(value = "familiaBean")
@ApplicationScoped
public class FamiliaBean {

	/**
	 * nombre de la familia de plantas
	 */
	private String nombre;
	private Familia f;

	/**
	 * referencia a la capa de negocio
	 */
	@EJB
	private AdminEJB adminEJB;

	public String registrar() {
		try {
			Familia miFamilia = new Familia();
			miFamilia.setNombre(nombre);
			f=adminEJB.insertarFamilia(miFamilia);
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
	 * @return the f
	 */
	public Familia getF() {
		return f;
	}

	/**
	 * @param f the f to set
	 */
	public void setF(Familia f) {
		this.f = f;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
