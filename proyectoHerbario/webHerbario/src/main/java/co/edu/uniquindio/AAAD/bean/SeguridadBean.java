package co.edu.uniquindio.AAAD.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Named;

import co.edu.uniquindio.AAAD.ejb.AdminEJB;
import co.edu.uniquindio.AAAD.ejb.NegocioEJB;
import co.edu.uniquindio.AAAD.persistencia.Persona;
import co.edu.uniquindio.AAAD.util.Util;

@FacesConfig(version = Version.JSF_2_3)
@Named("seguridadBean")
@SessionScoped
public class SeguridadBean implements Serializable{
	
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
	
	@EJB
	private AdminEJB adminEJB;
	@EJB
	private NegocioEJB negocioEJB;
	
	/**
	 * Permite inicializar el  usuario a autenticar
	 */
	@PostConstruct
	private void init() {
		
		usuario = new Persona();
		
		
	}
	
	public void iniciarSesion() {
		Persona u=negocioEJB.comprobarCredenciales(usuario.getEmail(), usuario.getClave());
		if (u!=null) {
			usuario=u;
			autenticado=true;
			
		}else {
			Util.mostrarMensaje("verifique las credencialesde acceso", "verifique las credenciales de acceso");
		}
		
	}
	
	public void recuperarContraseña() {
		
		negocioEJB
		
		
		
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
	
	
	
	

}
