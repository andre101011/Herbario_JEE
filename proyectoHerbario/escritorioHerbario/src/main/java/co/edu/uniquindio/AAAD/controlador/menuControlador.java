package co.edu.uniquindio.AAAD.controlador;

import java.awt.TextField;

import co.edu.uniquindio.AAAD.modelo.AdministradorDelegado;
import co.edu.uniquindio.AAAD.modelo.EmpleadoObservable;
import co.edu.uniquindio.AAAD.util.Utilidades;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

/**
 * @author Andres y Daniel
 */
public class menuControlador {

	@FXML
	private Button btnGestionarClases;
	@FXML
	private Button btnGestionarOrdenes;
	@FXML
	private Button btnGestionarFamilias;
	@FXML
	private Button btnGestionarGeneros;
	@FXML
	private Button btnGestionarEspecies;
	@FXML
	private Button btnGestionarEmpleados;
	@FXML
	private Button btnGestionarRecolectores;
	@FXML
	private Button btnCerrarSesion;

	private ManejadorEscenarios manejadorEscenarios;

	private Stage escenario;

	public menuControlador() {
	}

	@FXML
	private void initialize() {
	}

	@FXML
	public void abrirGestionarClases() {
		manejadorEscenarios.cargarEscenarioGestionarClases();
	}

	@FXML
	public void abrirGestionarOrdenes() {
		manejadorEscenarios.cargarEscenarioGestionarOrdenes();
	}

	@FXML
	public void abrirGestionarFamilias() {
		manejadorEscenarios.cargarEscenarioGestionarFamilias();
	}

	@FXML
	public void abrirGestionarGenero() {
		manejadorEscenarios.cargarEscenarioGestionarGeneros();
	}

	@FXML
	public void abrirGestionarEspecies() {
		manejadorEscenarios.cargarEscenarioGestionarEspecies();
	}

	public void abrirVentanaGestionarEmpleados() {
		manejadorEscenarios.cargarEscenarioGestionarEmpleados();
	}
//
//	public void abrirVentanaGestionarRecolectores() {
//		escenarioInicial.cargarEscenaGestionarPersonas("recolector"));
//	}

	public void setEscenario(Stage escenario) {
		this.escenario = escenario;

	}

	public void setManejador(ManejadorEscenarios manejadorEscenarios) {
		this.manejadorEscenarios = manejadorEscenarios;
	}

}
