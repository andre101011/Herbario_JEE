package co.edu.uniquindio.AAAD.controlador;

import co.edu.uniquindio.AAAD.modelo.ClaseObservable;
import co.edu.uniquindio.AAAD.modelo.EmpleadoObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class GestionarClasesControlador {

	private Stage escenario;

	private ManejadorEscenarios manejadorEscenarios;

	/**
	 * table donde se almacena la informacion de las categorias los empleados
	 */
	@FXML
	private TableView<ClaseObservable> tablaClases;
	/**
	 * hace referencia a la columna con las superCategorias
	 */
	@FXML
	private TableColumn<ClaseObservable, Number> idColumna;
	/**
	 * hace referencia a la columna de los nombres de las categorias
	 */
	@FXML
	private TableColumn<ClaseObservable, String> nombreColumna;
	/**
	 * etiqueta de cedula
	 */
	@FXML
	private Label txtCedula;
	/**
	 * etiqueta de nombre
	 */
	@FXML
	private Label txtNombre;
	/**
	 * etiqueta de email
	 */
	@FXML
	private Label txtEmail;
	/**
	 * etiqueta de clave
	 */
	@FXML
	private Label txtClave;

	private ClaseObservable claseObservable;

	public GestionarClasesControlador() {
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		idColumna.setCellValueFactory(claseCelda -> claseCelda.getValue().getId());
		nombreColumna.setCellValueFactory(claseCelda -> claseCelda.getValue().getNombre());

		mostrarDetallesCategoria(null);

		tablaClases.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetallesCategoria(newValue));

	}

	/**
	 * permite mostrar la informacion del empleado seleccionado
	 * 
	 * @param clase empleado al que se le desea mostrar el detalle
	 */
	public void mostrarDetallesCategoria(ClaseObservable clase) {

		if (clase != null) {
			claseObservable = clase;
			txtNombre.setText(clase.getNombre().getValue());

		} else {
			txtNombre.setText("");
		}

	}

	public void setEscenarioEditar(Stage escenario) {
		this.escenario = escenario;
	}

	public void setManejador(ManejadorEscenarios manejadorEscenarios) {
		this.manejadorEscenarios = manejadorEscenarios;

	}

}
