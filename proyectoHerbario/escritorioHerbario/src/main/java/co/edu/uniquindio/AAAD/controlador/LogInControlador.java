/**
 * 
 */
package co.edu.uniquindio.AAAD.controlador;

import java.awt.TextField;

import co.edu.uniquindio.AAAD.modelo.AdministradorDelegado;
import co.edu.uniquindio.AAAD.modelo.EmpleadoObservable;
import co.edu.uniquindio.AAAD.util.Utilidades;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

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

	private ManejadorEscenarios escenarioInicial;

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
			escenarioInicial.cargarEscenarioMenu();
		} else {
			Utilidades.mostrarMensaje("Datos equivocados!", "Por favor verifica tu correo y contraseña");
		}

	}

}