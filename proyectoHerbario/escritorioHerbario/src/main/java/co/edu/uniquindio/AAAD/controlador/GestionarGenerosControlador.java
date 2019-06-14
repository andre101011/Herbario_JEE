package co.edu.uniquindio.AAAD.controlador;

import co.edu.uniquindio.AAAD.modelo.GeneroObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class GestionarGenerosControlador {

	private Stage escenario;

	private ManejadorEscenarios manejadorEscenarios;

	/**
	 * table donde se almacena la informacion de las categorias los empleados
	 */
	@FXML
	private TableView<GeneroObservable> tablaGeneros;
	/**
	 * hace referencia a la columna con las superCategorias
	 */
	@FXML
	private TableColumn<GeneroObservable, Number> idColumna;
	/**
	 * hace referencia a la columna de los nombres de las categorias
	 */
	@FXML
	private TableColumn<GeneroObservable, String> nombreColumna;


	@FXML
	private Label txtNombre;
	/**
	 * etiqueta de email
	 */


	private GeneroObservable GeneroObservable;

	public GestionarGenerosControlador() {
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		idColumna.setCellValueFactory(generoCelda -> generoCelda.getValue().getId());
		nombreColumna.setCellValueFactory(generoCelda -> generoCelda.getValue().getNombre());

		mostrarDetallesCategoria(null);

		tablaGeneros.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetallesCategoria(newValue));

	}

	/**
	 * permite mostrar la informacion del empleado seleccionado
	 * 
	 * @param genero empleado al que se le desea mostrar el detalle
	 */
	public void mostrarDetallesCategoria(GeneroObservable genero) {

		if (genero != null) {
			GeneroObservable = genero;
			txtNombre.setText(genero.getNombre().getValue());

		} else {
			txtNombre.setText("");
		}

	}

	public void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}

	public void setManejador(ManejadorEscenarios manejadorEscenarios) {
		this.manejadorEscenarios = manejadorEscenarios;

	}

}
