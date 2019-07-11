package co.edu.uniquindio.AAAD.bean;

import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Named;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import co.edu.uniquindio.AAAD.ejb.AdminEJB;
import co.edu.uniquindio.AAAD.ejb.NegocioEJB;
import co.edu.uniquindio.AAAD.persistencia.Persona;
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

		} else {
			Util.mostrarMensaje("verifique las credencialesde acceso", "verifique las credenciales de acceso");
		}

	}

	public void recuperarContrasena() {

		String correo = usuario.getEmail();
		Persona persona = adminEJB.buscarPersonaPorEmail(correo);
		if (persona != null) {
			String clave = persona.getClave();
			enviarConGMail(correo, "Su contraseña es " + clave);
			Util.mostrarMensaje("Revise su correo", "La contraseña de su cuenta fue enviada a su correo");
		} else {
			Util.mostrarMensaje("Correo no encontrado", "El correo ingresado no existe");
		}
	}

	public void enviarConGMail(String destinatario, String mensaje) {
		// Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el
		// remitente tambien.
		String remitente = "herbariouniquindioAAAD";
		String clave = "herbario";
		// Para la direccion nomcuenta@gmail.com
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // El servidor SMTP de Google
		props.put("mail.smtp.user", remitente);
		props.put("mail.smtp.clave", clave); // La clave de la cuenta
		props.put("mail.smtp.auth", "true"); // Usar autenticacion mediante usuario y clave
		props.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
		props.put("mail.smtp.port", "587"); // El puerto SMTP seguro de Google
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);
		BodyPart texto = new MimeBodyPart();
		MimeMultipart multiParte = new MimeMultipart();
		try {
			texto.setText(mensaje);

			multiParte.addBodyPart(texto);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		try {
			message.setFrom(new InternetAddress(remitente));
			message.addRecipients(Message.RecipientType.TO, destinatario);

			message.setSubject("Recuperacion de contrasena Herbario Universidad del quindio:");
			message.setText("prueba");
			message.setContent(multiParte);

			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", remitente, clave);

			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
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
