package co.edu.uniquindio.AAAD.bean;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import co.edu.uniquindio.AAAD.ejb.AdminEJB;
import co.edu.uniquindio.AAAD.ejb.NegocioEJB;
import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.persistencia.Especie;
import co.edu.uniquindio.AAAD.persistencia.Genero;
import co.edu.uniquindio.AAAD.persistencia.Registro;

@FacesConfig(version = Version.JSF_2_3)
@Named("registroPlantaBean")
@ApplicationScoped
public class RegistroPlantaBean {

	private String nombre;
	private Genero genero;
	private List<Genero> generos;

	@EJB
	private NegocioEJB negocioEJB;
	@EJB
	private AdminEJB adminEJB;

	/**
	 * Planta relacionada al bean
	 */
	private Especie especie;

	/**
	 * Imagen de la planta
	 */
	private UploadedFile imagen;
	/**
	 * para la carga de la imagen
	 */
	private StreamedContent chart;

	/**
	 * inicializa la info basica del bean
	 */
	@PostConstruct
	private void init() {
		setGeneros(adminEJB.listarGeneros());
	}

	/**
	 * obtiene la información del genero a registrar
	 * 
	 * @return pagina que lista la data de las especies
	 */

	public String registrar() {

		Especie especie = new Especie();
		especie.setNombre(nombre);
		especie.setGeneroDeEspecie(genero);
		// especie.setImagen(imagen);
		Registro registro = new Registro();
//		registro.setEnviadorDelRegistro(usuario);
		registro.setEspecieEnviada(especie);
		registro.setFecha(new Date());
		especie.setRegistroPlanta(registro);

		try {
			negocioEJB.registrarEspecie(registro);
		} catch (ElementoRepetidoException e) {

			e.printStackTrace();
		}

		return "/index";

	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {

		imagen = event.getFile();
		byte[] imagenBytes = new byte[(int) imagen.getSize()];
		imagenBytes = imagen.getContents();

		if (especie == null) {
			especie = new Especie();
		}
		especie.setImagen(imagenBytes);
		try {
			adminEJB.modificarEspecie(especie);
		} catch (ElementoNoEncontradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	 * @return the generos
	 */
	public List<Genero> getGeneros() {
		return generos;
	}

	/**
	 * @param generos the generos to set
	 */
	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
	}

	/**
	 * @return the negocioEJB
	 */
	public NegocioEJB getNegocioEJB() {
		return negocioEJB;
	}

	/**
	 * @param negocioEJB the negocioEJB to set
	 */
	public void setNegocioEJB(NegocioEJB negocioEJB) {
		this.negocioEJB = negocioEJB;
	}

}
