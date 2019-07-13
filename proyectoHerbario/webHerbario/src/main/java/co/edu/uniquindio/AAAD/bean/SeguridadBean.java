package co.edu.uniquindio.AAAD.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Named;

import co.edu.uniquindio.AAAD.correo.EnviarCorreo;
import co.edu.uniquindio.AAAD.ejb.AdminEJB;
import co.edu.uniquindio.AAAD.ejb.NegocioEJB;
import co.edu.uniquindio.AAAD.persistencia.Persona;
import co.edu.uniquindio.AAAD.persistencia.Recolector;
import co.edu.uniquindio.AAAD.util.Util;

@FacesConfig(version = Version.JSF_2_3)
@Named("seguridadBean")
@SessionScoped
public class SeguridadBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * usuario que inicia sesión
	 */
	private Persona usuario;

	/**
	 * determina si la persona inicio sesion o no
	 */
	private boolean autenticado;

	private boolean empleado;

	private boolean recolector;

	@EJB
	private AdminEJB adminEJB;
	@EJB
	private NegocioEJB negocioEJB;

	/**
	 * Permite inicializar el usuario a autenticar
	 */
	@PostConstruct
	private void init() {

		usuario = new Persona();

	}

	public void iniciarSesion() {
		Persona u = negocioEJB.comprobarCredenciales(usuario.getEmail(), usuario.getClave());
		if (u != null) {
			usuario = u;
			autenticado = true;
			if (usuario instanceof Recolector) {
				empleado = false;
				recolector = true;
			} else {
				empleado = true;
				recolector = false;
			}

		} else {
			autenticado = false;
			Util.mostrarMensaje("verifique las credencialesde acceso", "verifique las credenciales de acceso");
		}

	}

	public void recuperarContrasena() {

		String correo = usuario.getEmail();
		Persona persona = adminEJB.buscarPersonaPorEmail(correo);
		if (persona != null) {
			String clave = persona.getClave();
			EnviarCorreo.enviarConGMail(correo, "Su contraseña es " + clave);
			Util.mostrarMensaje("Revise su correo", "La contraseña de su cuenta fue enviada a su correo");
		} else {
			Util.mostrarMensaje("Correo no encontrado", "El correo ingresado no existe");
		}
	}

	/**
	 * navegación del menu principal
	 * 
	 * @param i
	 * @return
	 */
	public String navegacion(int i) {
		switch (i) {
		case 1:
			return "/gestionar_recolectores";
		case 2:
			return "/gestionar_perfil";
		case 3:
			return "/registrar_especie";
		case 4:
			return "/ver_especies";
		default:
			return null;
		}
	}

	public String crearCuenta() {
		return "/registrar_recolector";
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
	 * @return the autenticado
	 */
	public boolean isAutenticado() {
		return autenticado;
	}

	/**
	 * @param autenticado the autenticado to set
	 */
	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}

	/**
	 * @return the empleado
	 */
	public boolean isEmpleado() {
		return empleado;
	}

	/**
	 * @param empleado the empleado to set
	 */
	public void setEmpleado(boolean empleado) {
		this.empleado = empleado;
	}

	/**
	 * @return the recolector
	 */
	public boolean isRecolector() {
		return recolector;
	}

	/**
	 * @param recolector the recolector to set
	 */
	public void setRecolector(boolean recolector) {
		this.recolector = recolector;
	}

	/**
	 * permite cerrar sesion
	 */
	public void cerrarSesion() {
		if (usuario != null) {
			usuario = null;
			autenticado = false;
			init();
		}
	}

}
