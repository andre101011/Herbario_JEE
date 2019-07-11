package co.edu.uniquindio.AAAD.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Named;

import co.edu.uniquindio.AAAD.ejb.AdminEJB;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.persistencia.Categoria;
import co.edu.uniquindio.AAAD.persistencia.Clase;
import co.edu.uniquindio.AAAD.persistencia.Familia;
import co.edu.uniquindio.AAAD.persistencia.Genero;
import co.edu.uniquindio.AAAD.persistencia.Orden;



/**
 * permite gestionar la informacion de una familia de plantas
 * 
 * @author stiven @version1.0
 *
 */
@FacesConfig(version = Version.JSF_2_3)
@Named(value = "categoriaBean")
@ApplicationScoped
public class CategoriaBean {

	/**
	 * nombre de la familia de plantas
	 */
	private String nombre;
	private Categoria f;
	private Tipo tipo;
	
	private List<Categoria> superCategorias;
	private List<Categoria> categorias;
	
	enum Tipo{
		
		CLASE,
		ORDEN,
		FAMILIA,
		GENERO
		
		
	}

	/**
	 * referencia a la capa de negocio
	 */
	@EJB
	private AdminEJB adminEJB;

	public String registrar() {
		try {
			Categoria miCategoria = new Categoria();
			
			if (tipo==Tipo.CLASE) {
				miCategoria = new Clase();
				miCategoria.setNombre(nombre);
				f=adminEJB.insertarClase((Clase)miCategoria);
				
			}else if (tipo==Tipo.ORDEN) {
				miCategoria = new Orden();
				miCategoria.setNombre(nombre);
				f=adminEJB.insertarOrden((Orden)miCategoria);
			}else if (tipo==Tipo.FAMILIA) {
				miCategoria = new Familia();
				miCategoria.setNombre(nombre);
				f=adminEJB.insertarFamilia((Familia)miCategoria);
			}else {
				miCategoria = new Genero();
				miCategoria.setNombre(nombre);
				f=adminEJB.insertarGenero((Genero)miCategoria);
			}
			
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
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the f
	 */
	public Categoria getF() {
		return f;
	}

	/**
	 * @param f the f to set
	 */
	public void setF(Categoria f) {
		this.f = f;
	}

	/**
	 * @return the tipo
	 */
	public Tipo getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the superCategorias
	 */
	public List<Categoria> getSuperCategorias() {
		return superCategorias;
	}

	/**
	 * @param superCategorias the superCategorias to set
	 */
	public void setSuperCategorias(List<Categoria> superCategorias) {
		this.superCategorias = superCategorias;
	}

	/**
	 * @return the categorias
	 */
	public List<Categoria> getCategorias() {
		return categorias;
	}

	/**
	 * @param categorias the categorias to set
	 */
	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	/**
	 * @return the adminEJB
	 */
	public AdminEJB getAdminEJB() {
		return adminEJB;
	}

	/**
	 * @param adminEJB the adminEJB to set
	 */
	public void setAdminEJB(AdminEJB adminEJB) {
		this.adminEJB = adminEJB;
	}

}
