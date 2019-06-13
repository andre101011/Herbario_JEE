package co.edu.uniquindio.AAAD.controlador;

import java.io.IOException;

import co.edu.uniquindio.AAAD.Main;
import co.edu.uniquindio.AAAD.persistencia.Empleado;
import co.edu.uniquindio.AAAD.persistencia.Persona;
import co.edu.uniquindio.AAAD.controlador.LogInControlador;
import co.edu.uniquindio.AAAD.modelo.AdministradorDelegado;
import co.edu.uniquindio.AAAD.modelo.EmpleadoObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Permite manejar los escenarios que tiene la aplicacion
 * 
 * @author EinerZG
 * @version 1.0
 */
public class ManejadorEscenarios {

	/**
	 * contenedor prinpipal de la aplicacion
	 */
	private Stage escenario;
	/**
	 * tipo de panel inicial
	 */
	private BorderPane panelPrincipal;
	/**
	 * para almacenar empleados observables
	 */
	private ObservableList<EmpleadoObservable> empleadosObservables;
	/**
	 * conexion con capa de negocio
	 */
	private AdministradorDelegado administradorDelegado;

	/**
	 * recibe el escenario principla de la aplicacion
	 * 
	 * @param escenario inicial
	 */
	public ManejadorEscenarios(Stage escenario) {

		this.escenario = escenario;

		administradorDelegado = AdministradorDelegado.administradorDelegado;
		empleadosObservables = FXCollections.observableArrayList();

		try {
			// se inicializa el escenario
			escenario.setTitle("Herbario Universidad del Quindio");

			// se carga la vista
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("./vista/inicio.fxml"));

			panelPrincipal = (BorderPane) loader.load();

			// se carga la escena
			Scene scene = new Scene(panelPrincipal);
			escenario.setScene(scene);
			escenario.show();

			cargarEscena();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * muestra el escenario logIn
	 */
	public void cargarEscenariologIn() {

		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("./vista/logIn.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// se crea el escenario
			Stage escenariologIn = new Stage();
			escenariologIn.setTitle("Ingreso a la plataforma");
			Scene scene = new Scene(page);
			escenariologIn.setScene(scene);

			// se carga el controlador
			EdicionEmpleadoControlador empleadoControlador = loader.getController();
			empleadoControlador.setEscenarioEditar(escenariologIn);
			empleadoControlador.setManejador(this);

			// se muestra el escenario
			escenariologIn.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void cargarEscenarioMenu() {

		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("./vista/menu.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// se crea el escenario
			Stage escenariologIn = new Stage();
			escenariologIn.setTitle("Ingreso a la plataforma");
			Scene scene = new Scene(page);
			escenariologIn.setScene(scene);

			// se carga el controlador
			EdicionEmpleadoControlador empleadoControlador = loader.getController();
			empleadoControlador.setEscenarioEditar(escenariologIn);
			empleadoControlador.setManejador(this);

			// se muestra el escenario
			escenariologIn.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * carga una escena en el centro del escenario
	 */
	public void cargarEscena() {

		try {

			empleadosObservables = administradorDelegado.listarEmpleadosObservables();

			FXMLLoader loader2 = new FXMLLoader();
			loader2.setLocation(Main.class.getResource("vista/detallesEmpleados.fxml"));
			AnchorPane panel = (AnchorPane) loader2.load();
			panelPrincipal.setCenter(panel);

			LogInControlador controlador = loader2.getController();
			controlador.setEscenarioInicial(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * muestra el escenario para crear un empleado nuevo
	 */
	public void cargarEscenarioCrearEmpleado() {

		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("./vista/editarEmpleado.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// se crea el escenario
			Stage escenarioCrear = new Stage();
			escenarioCrear.setTitle("Crear Empleado");
			Scene scene = new Scene(page);
			escenarioCrear.setScene(scene);

			// se carga el controlador
			EdicionEmpleadoControlador empleadoControlador = loader.getController();
			empleadoControlador.setEscenarioEditar(escenarioCrear);
			empleadoControlador.setManejador(this);

			// se muestra el escenario
			escenarioCrear.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @return empleados observables
	 */
	public ObservableList<EmpleadoObservable> getEmpleadosObservables() {
		return empleadosObservables;
	}

	/**
	 * permite agrega una liente a la lista obsevable
	 * 
	 * @param empleado
	 */
	public void agregarALista(Persona empleado) {
		empleadosObservables.add(new EmpleadoObservable(empleado));
	}

	/**
	 * devuelve una instancia del escenario
	 * 
	 * @return escenario
	 */
	public Stage getEscenario() {
		return escenario;
	}

	/**
	 * permite registrar una persona en la base de datos
	 * 
	 * @param empleado a registrar
	 * @return true si quedo registrado
	 */
	public boolean registrarEmpleado(Empleado empleado) {
		try {
			return administradorDelegado.registrarEmpleado(empleado);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * permite eliminar un empleado
	 * 
	 * @param empleado a ser eliminado
	 * @return true si fue eliminado false si no
	 */
	public boolean eliminarEmpleado(Empleado empleado) {
		return administradorDelegado.eliminarEmpleado(empleado);
	}

}
