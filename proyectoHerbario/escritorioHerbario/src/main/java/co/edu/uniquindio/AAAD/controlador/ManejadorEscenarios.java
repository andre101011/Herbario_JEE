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
	 * recibe el escenario principal de la aplicacion
	 * 
	 * @param escenario inicial
	 */

	public ManejadorEscenarios(Stage escenario) {

		this.escenario = escenario;

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
			escenario.setMinWidth(900);
			escenario.setMinHeight(200);
			escenario.centerOnScreen();
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
		escenario.hide();
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
			escenario.setMinWidth(900);
			escenario.setMinHeight(600);
			escenario.centerOnScreen();

			// se carga el controlador
			GestionarEmpleadosControlador empleadoControlador = loader.getController();
			empleadoControlador.setEscenario(escenario);
			empleadoControlador.setManejador(this);

			// se muestra el escenario
			escenario.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

		escenario.show();
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
			escenario.setMinWidth(900);
			escenario.setMinHeight(600);
			escenario.centerOnScreen();

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
	 * muestra el escenario para gestionar ordens
	 */
	public void cargarEscenarioGestionarOrdenes() {

		escenario.hide();
		try {
			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("./vista/gestionarOrdenes.fxml"));
			BorderPane page = (BorderPane) loader.load();

			// se crea el escenario
			Stage escenario = new Stage();
			escenario.setTitle("gestionar Ordenes");
			Scene scene = new Scene(page);
			escenario.setScene(scene);
			escenario.setMinWidth(900);
			escenario.setMinHeight(600);
			escenario.centerOnScreen();

			// se carga el controlador
			GestionarOrdenesControlador ordenControlador = loader.getController();
			ordenControlador.setEscenario(escenario);
			ordenControlador.setManejador(this);

			// se muestra el escenario
			escenario.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
		escenario.show();
	}

	/**
	 * muestra el escenario para gestionar familias
	 */
	public void cargarEscenarioGestionarFamilias() {

		escenario.hide();
		try {
			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("./vista/gestionarFamilias.fxml"));
			BorderPane page = (BorderPane) loader.load();

			// se crea el escenario
			Stage escenario = new Stage();
			escenario.setTitle("gestionar Familias");
			Scene scene = new Scene(page);
			escenario.setScene(scene);
			escenario.setMinWidth(900);
			escenario.setMinHeight(600);
			escenario.centerOnScreen();

			// se carga el controlador
			GestionarFamiliasControlador familiaControlador = loader.getController();
			familiaControlador.setEscenario(escenario);
			familiaControlador.setManejador(this);

			// se muestra el escenario
			escenario.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
		escenario.show();
	}

	/**
	 * muestra el escenario para gestionar generos
	 */
	public void cargarEscenarioGestionarGeneros() {

		escenario.hide();
		try {
			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("./vista/gestionarGeneros.fxml"));
			BorderPane page = (BorderPane) loader.load();

			// se crea el escenario
			Stage escenario = new Stage();
			escenario.setTitle("gestionar Generos");
			Scene scene = new Scene(page);
			escenario.setScene(scene);
			escenario.setMinWidth(900);
			escenario.setMinHeight(600);
			escenario.centerOnScreen();

			// se carga el controlador
			GestionarGenerosControlador familiaControlador = loader.getController();
			familiaControlador.setEscenario(escenario);
			familiaControlador.setManejador(this);

			// se muestra el escenario
			escenario.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
		escenario.show();
	}

	/**
	 * muestra el escenario para gestionar especies
	 */
	public void cargarEscenarioGestionarEspecies() {

		escenario.hide();
		try {
			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("./vista/gestionarEspecies.fxml"));
			BorderPane page = (BorderPane) loader.load();

			// se crea el escenario
			Stage escenario = new Stage();
			escenario.setTitle("gestionar Especies");
			Scene scene = new Scene(page);
			escenario.setScene(scene);
			escenario.setMinWidth(1200);
			escenario.setMinHeight(700);
			escenario.centerOnScreen();

			// se carga el controlador
			GestionarEspeciesControlador familiaControlador = loader.getController();
			familiaControlador.setEscenario(escenario);
			familiaControlador.setManejador(this);

			// se muestra el escenario
			escenario.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
		escenario.show();
	}

	/**
	 * devuelve una instancia del escenario
	 * 
	 * @return escenario
	 */
	public Stage getEscenario() {
		return escenario;
	}

}
