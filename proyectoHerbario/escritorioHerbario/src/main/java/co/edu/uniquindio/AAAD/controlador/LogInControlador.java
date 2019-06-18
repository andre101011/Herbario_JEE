/**
 * 
 */
package co.edu.uniquindio.AAAD.controlador;

import co.edu.uniquindio.AAAD.correo.EnviarCorreo;
import co.edu.uniquindio.AAAD.modelo.AdministradorDelegado;
import co.edu.uniquindio.AAAD.persistencia.Persona;
import co.edu.uniquindio.AAAD.util.Utilidades;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Andres y Daniel
 */
public class LogInControlador {

	@FXML
	private TextField jtfCorreo;
	@FXML
	private TextField jtfClave;
	@FXML
	private Button btnIngresar;
	@FXML
	private Hyperlink btnOlvideContrasenia;

	private AdministradorDelegado administradorDelegado;
	private Stage escenario;
	private ManejadorEscenarios manejadorEscenarios;

	public LogInControlador() {
	}

	@FXML
	private void initialize() {

		administradorDelegado = AdministradorDelegado.administradorDelegado;
		jtfCorreo.setText("dbonillag_1@uqvirtual.edu.co");
		jtfClave.setText("12345");
	}

	@FXML
	public void comprobarCredenciales() {
		String correo = jtfCorreo.getText();
		String clave = jtfClave.getText();

		if (administradorDelegado.comprobarCredenciales(correo, clave)) {
			manejadorEscenarios.cargarEscenarioMenu();
		} else {
			Utilidades.mostrarMensaje("Datos equivocados!", "Por favor verifica tu correo y contraseña");
		}

	}

	@FXML
	public void recuperarContrasenia() {

		String correo = jtfCorreo.getText();
		Persona persona = administradorDelegado.buscarPersonaPorEmail(correo);
		if (persona != null) {
			String clave = persona.getClave();
			EnviarCorreo.enviarConGMail(correo, "Su contraseña es " + clave);
			Utilidades.mostrarMensaje("Revise su correo", "La contraseña de su cuenta fue enviada a su correo");
		} else {
			Utilidades.mostrarMensaje("Correo no encontrado", "El correo ingresado no existe");
		}

	}

	public void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}

	public void setManejadorEscenarios(ManejadorEscenarios manejadorEscenarios) {
		this.manejadorEscenarios = manejadorEscenarios;

	}

}