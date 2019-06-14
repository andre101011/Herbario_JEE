package co.edu.uniquindio.AAAD.controlador;

import co.edu.uniquindio.AAAD.modelo.FamiliaObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class GestionarFamiliasControlador {

	private Stage escenario;

	private ManejadorEscenarios manejadorEscenarios;

	/**
	 * table donde se almacena la informacion de las categorias los empleados
	 */
	@FXML
	private TableView<FamiliaObservable> tablaFamilias;
	/**
	 * hace referencia a la columna con las superCategorias
	 */
	@FXML
	private TableColumn<FamiliaObservable, Number> idColumna;
	/**
	 * hace referencia a la columna de los nombres de las categorias
	 */
	@FXML
	private TableColumn<FamiliaObservable, String> nombreColumna;
	/**
	 * etiqueta de cedula
	 */

	@FXML
	private Label txtNombre;
	/**
	 * etiqueta de email
	 */


	private FamiliaObservable FamiliaObservable;

	public GestionarFamiliasControlador() {
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		idColumna.setCellValueFactory(familiaCelda -> familiaCelda.getValue().getId());
		nombreColumna.setCellValueFactory(familiaCelda -> familiaCelda.getValue().getNombre());

		mostrarDetallesCategoria(null);

		tablaFamilias.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetallesCategoria(newValue));

	}

	/**
	 * permite mostrar la informacion del empleado seleccionado
	 * 
	 * @param familia empleado al que se le desea mostrar el detalle
	 */
	public void mostrarDetallesCategoria(FamiliaObservable familia) {

		if (familia != null) {
			FamiliaObservable = familia;
			txtNombre.setText(familia.getNombre().getValue());

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
