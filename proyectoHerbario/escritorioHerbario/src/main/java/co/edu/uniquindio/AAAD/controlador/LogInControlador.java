/**
 * 
 */
package co.edu.uniquindio.AAAD.controlador;



import co.edu.uniquindio.AAAD.modelo.AdministradorDelegado;
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

	public void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}

	public void setManejadorEscenarios(ManejadorEscenarios manejadorEscenarios) {
		this.manejadorEscenarios = manejadorEscenarios;

	}

}