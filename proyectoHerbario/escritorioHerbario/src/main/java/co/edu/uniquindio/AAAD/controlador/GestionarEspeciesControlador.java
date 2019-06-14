package co.edu.uniquindio.AAAD.controlador;

import co.edu.uniquindio.AAAD.modelo.EspecieObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class GestionarEspeciesControlador {

	private Stage escenario;

	private ManejadorEscenarios manejadorEscenarios;

	/**
	 * table donde se almacena la informacion de las categorias los empleados
	 */
	@FXML
	private TableView<EspecieObservable> tablaFamilias;
	/**
	 * hace referencia a la columna con las superCategorias
	 */
	@FXML
	private TableColumn<EspecieObservable, Number> idColumna;
	/**
	 * hace referencia a la columna de los nombres de las categorias
	 */
	@FXML
	private TableColumn<EspecieObservable, String> nombreColumna;

	@FXML
	private Label txtNombre;
	/**
	 * etiqueta de email
	 */

	private EspecieObservable EspecieObservable;

	public GestionarEspeciesControlador() {
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		idColumna.setCellValueFactory(especieCelda -> especieCelda.getValue().getId());
		nombreColumna.setCellValueFactory(especieCelda -> especieCelda.getValue().getNombre());

		mostrarDetallesCategoria(null);

		tablaFamilias.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetallesCategoria(newValue));

	}

	/**
	 * permite mostrar la informacion del empleado seleccionado
	 * 
	 * @param especie empleado al que se le desea mostrar el detalle
	 */
	public void mostrarDetallesCategoria(EspecieObservable especie) {

		if (especie != null) {
			EspecieObservable = especie;
			txtNombre.setText(especie.getNombre().getValue());

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
