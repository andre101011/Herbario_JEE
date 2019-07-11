package co.edu.uniquindio.AAAD.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Named;

import co.edu.uniquindio.AAAD.ejb.AdminEJB;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.persistencia.Familia;
import co.edu.uniquindio.AAAD.persistencia.Genero;
@FacesConfig(version = Version.JSF_2_3)
@Named("generoBean")
@ApplicationScoped
public class GeneroBean {
	
	private String nombre;
	private Familia familia;
	
	private List<Familia> familias;
	
	private List<Genero> generos;
	
	@EJB
	private AdminEJB adminEJB;
	/**
	 * inicializa la info basica del bean
	 */
	@PostConstruct
	private void init() {
		familias=adminEJB.listarFamilias();
		setGeneros(adminEJB.listarGeneros());
	}

	/**
	 * obtiene la información del genero a registrar
	 * @return pagina que lista la data de los generos
	 */
	public String registrar() {
		
		Genero genero = new Genero();
		genero.setNombre(nombre);
		genero.setFamiliaDelGenero(familia);
		
		try {
			adminEJB.insertarGenero(genero);
		} catch (ElementoRepetidoException e) {
			
			e.printStackTrace();
		}
		
		generos=adminEJB.listarGeneros();
		
		return "seguro/generos";
		
		
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
	 * @return the familia
	 */
	public Familia getFamilia() {
		return familia;
	}

	/**
	 * @param familia the familia to set
	 */
	public void setFamilia(Familia familia) {
		this.familia = familia;
	}

	/**
	 * @return the familias
	 */
	public List<Familia> getFamilias() {
		return familias;
	}

	/**
	 * @param familias the familias to set
	 */
	public void setFamilias(List<Familia> familias) {
		this.familias = familias;
	}

	public List<Genero> getGeneros() {
		return generos;
	}

	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
	}

	
}
