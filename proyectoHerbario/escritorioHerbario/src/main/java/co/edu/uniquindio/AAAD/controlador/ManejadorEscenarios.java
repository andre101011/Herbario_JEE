package co.edu.uniquindio.AAAD.controlador;

import java.io.IOException;

import co.edu.uniquindio.AAAD.Main;
import co.edu.uniquindio.AAAD.persistencia.Empleado;
import co.edu.uniquindio.AAAD.persistencia.Persona;
import co.edu.uniquindio.AAAD.controlador.LogInControlador;
import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.modelo.AdministradorDelegado;
import co.edu.uniquindio.AAAD.modelo.EmpleadoObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
	 * recibe el escenario principal de la aplicacion
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
			System.out.println("cate pasó por aqui");
			cargarEscenariologIn();

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
			loader.setLocation(Main.class.getResource("vista/logIn.fxml"));
			BorderPane page = (BorderPane) loader.load();
			panelPrincipal.setCenter(page);

			LogInControlador controlador = loader.getController();
			controlador.setManejadorEscenarios(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void cargarEscenarioMenu() {

		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("vista/menu.fxml"));
			BorderPane page = (BorderPane) loader.load();
			escenario.setMinWidth(900);
			escenario.setMinHeight(200);

			panelPrincipal.setCenter(page);

			menuControlador controlador = loader.getController();
			controlador.setManejador(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * muestra el escenario para crear un empleado nuevo
	 */
	public void cargarEscenarioGestionarEmpleados() {

		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("./vista/gestionarEmpleados.fxml"));
			BorderPane page = (BorderPane) loader.load();

			// se crea el escenario
			Stage escenario = new Stage();
			escenario.setTitle("gestionar Empleados");
			Scene scene = new Scene(page);
			escenario.setScene(scene);

			// se carga el controlador
			GestionarEmpleadosControlador empleadoControlador = loader.getController();
			empleadoControlador.setEscenario(escenario);
			empleadoControlador.setManejador(this);

			// se muestra el escenario
			escenario.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * muestra el escenario para gestionar clases
	 */
	public void cargarEscenarioGestionarClases() {
		escenario.hide();
		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("./vista/gestionarClases.fxml"));
			BorderPane page = (BorderPane) loader.load();

			// se crea el escenario
			Stage escenario = new Stage();
			escenario.setTitle("gestionar Clases");
			Scene scene = new Scene(page);
			escenario.setScene(scene);

			// se carga el controlador
			GestionarClasesControlador claseControlador = loader.getController();
			claseControlador.setEscenario(escenario);
			claseControlador.setManejador(this);

			// se muestra el escenario
			escenario.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
		escenario.show();
	}

	/**
	 * 
	 * @return empleados observables
	 */
	public ObservableList<EmpleadoObservable> getEmpleadosObservables() {
		return empleadosObservables;
	}

	/**
	 * permite agrega una liente a la lista observable
	 * 
	 * @param empleado
	 */
	public void agregarALista(Empleado empleado) {
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
			return administradorDelegado.insertarEmpleado(empleado);
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
		try {
			return administradorDelegado.eliminarEmpleado(empleado);
		} catch (ElementoNoEncontradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
