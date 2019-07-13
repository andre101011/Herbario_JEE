package co.edu.uniquindio.AAAD.bean;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Named;

import co.edu.uniquindio.AAAD.ejb.AdminEJB;
import co.edu.uniquindio.AAAD.ejb.NegocioEJB;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.persistencia.Especie;
import co.edu.uniquindio.AAAD.persistencia.Familia;
import co.edu.uniquindio.AAAD.persistencia.Genero;
import co.edu.uniquindio.AAAD.persistencia.Persona;
import co.edu.uniquindio.AAAD.persistencia.Registro;
@FacesConfig(version = Version.JSF_2_3)
@Named("especieBean")
@ApplicationScoped
public class EspecieBean {
	private Persona usuario;
	private String nombre;
	private Genero genero;
	private Especie especie;
	private String categoria;
	private List<Especie> especies;
	
	@EJB
	private AdminEJB adminEJB;
	@EJB
	private NegocioEJB negocioEJB;
	/**
	 * inicializa la info basica del bean
	 */
	@PostConstruct
	private void init() {
		especies=negocioEJB.listarEspecies();
		
		
	}

	/**
	 * obtiene la información del genero a registrar
	 * @return pagina que lista la data de los generos
	 */
	public String registrar() {
		
		Especie especie = new Especie();
		especie.setNombre(nombre);
		especie.setGeneroDeEspecie(genero);
		//especie.setImagen(imagen);
		Registro registro=new Registro();
		registro.setEnviadorDelRegistro(usuario);
		registro.setEspecieEnviada(especie);
		registro.setFecha(new Date());
		
		try {
			negocioEJB.registrarEspecie(registro);
		} catch (ElementoRepetidoException e) {
			
			e.printStackTrace();
		}
		
		
		
		return "/index";
		
		
	}
	
	public String listar(int i) {
		
		switch (i) {
		case 0:
			System.out.println("no está listando");
			especies=negocioEJB.listarEspecies();
		case 1:
			System.out.println("no está listando");
			especies=negocioEJB.listarEspeciesAceptadas();
			
		case 2:
			especies=negocioEJB.listarEspeciesRechazados();
		case 3:
			especies=negocioEJB.listarEspeciesPorClase(adminEJB.buscarClasePorNombre(categoria));
		case 4:
			especies=negocioEJB.listarEspeciesPorOrden(adminEJB.buscarOrdenPorNombre(categoria));
		case 5:
			especies=negocioEJB.listarEspeciesPorFamilia(adminEJB.buscarFamiliaPorNombre(categoria));
		case 6:
			especies=negocioEJB.listarEspeciesPorGenero(adminEJB.buscarGeneroPorNombre(categoria));
		default:
			
		}
		
		return "/especies";
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
	 * @return the usuario
	 */
	public Persona getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Persona usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the genero
	 */
	public Genero getGenero() {
		return genero;
	}

	/**
	 * @param genero the genero to set
	 */
	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	/**
	 * @return the especies
	 */
	public List<Especie> getEspecies() {
		return especies;
	}

	/**
	 * @param especies the especies to set
	 */
	public void setEspecies(List<Especie> especies) {
		this.especies = especies;
	}

	/**
	 * @return the especie
	 */
	public Especie getEspecie() {
		return especie;
	}

	/**
	 * @param especie the especie to set
	 */
	public void setEspecie(Especie especie) {
		this.especie = especie;
	}

	/**
	 * @return the categoria
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	

	

	
}
