/**
 * 
 */
package co.edu.uniquindio.AAAD.controlador;

import co.edu.uniquindio.AAAD.persistencia.Empleado;

import java.awt.TextField;

import co.edu.uniquindio.AAAD.modelo.AdministradorDelegado;
import co.edu.uniquindio.AAAD.modelo.EmpleadoObservable;
import co.edu.uniquindio.AAAD.util.Utilidades;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author EinerZG
 */
public class LogInControlador {

	private TextField jtfCorreo;
	private TextField jtfClave;
	private Button btnIngresar;
	private Hyperlink btnOlvideContrasenia;

	private AdministradorDelegado administradorDelegado;

	private ManejadorEscenarios escenarioInicial;

	private EmpleadoObservable empleadoObservable;

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
		}

	}

}