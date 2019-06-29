package co.edu.uniquindio.AAAD.correo;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EnviarCorreo {

	public static void enviarConGMail(String destinatario, String mensaje) {
		// Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el
		// remitente tambien.
		String remitente="herbariouniquindioAAAD";
		String clave="herbario";
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

}