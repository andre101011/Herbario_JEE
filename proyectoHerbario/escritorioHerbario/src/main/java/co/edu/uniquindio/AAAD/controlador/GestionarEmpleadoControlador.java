/**
 * 
 */
package co.edu.uniquindio.AAAD.controlador;

import co.edu.uniquindio.AAAD.persistencia.Empleado;
import co.edu.uniquindio.AAAD.modelo.EmpleadoObservable;
import co.edu.uniquindio.AAAD.util.Utilidades;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * @author EinerZG
 */
public class GestionarEmpleadoControlador {

	/**
	 * table donde se almacena la informacion de los empleados
	 */
	@FXML
	private TableView<EmpleadoObservable> tablaEmpleados;
	/**
	 * hace referencia a la columna con las cedulas
	 */
	@FXML
	private TableColumn<EmpleadoObservable, String> cedulaColumna;
	/**
	 * hace referencia a la columna de los nombres de los empleados
	 */
	@FXML
	private TableColumn<EmpleadoObservable, String> nombreColumna;
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

	/**
	 * instancia del manejador de escenario
	 */
	private ManejadorEscenarios manejadorEscenarios;

	private EmpleadoObservable empleadoObservable;
	private Stage escenario;

	public GestionarEmpleadoControlador() {
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		cedulaColumna.setCellValueFactory(empleadoCelda -> empleadoCelda.getValue().getCedula());
		nombreColumna.setCellValueFactory(empleadoCelda -> empleadoCelda.getValue().getNombre());

		mostrarDetalleEmpleado(null);

		tablaEmpleados.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetalleEmpleado(newValue));

	}

	/**
	 * permite obtener una instancia del escenario general
	 * 
	 * @param escenarioInicial
	 */
	public void setEscenarioInicial(ManejadorEscenarios escenarioInicial) {
		this.manejadorEscenarios = escenarioInicial;
		tablaEmpleados.setItems(escenarioInicial.getEmpleadosObservables());
	}

	/**
	 * permite mostrar la informacion del empleado seleccionado
	 * 
	 * @param empleado empleado al que se le desea mostrar el detalle
	 */
	public void mostrarDetalleEmpleado(EmpleadoObservable empleado) {

		if (empleado != null) {
			empleadoObservable = empleado;
			txtCedula.setText(empleado.getCedula().getValue());
			txtNombre.setText(empleado.getNombre().getValue());
			txtEmail.setText(empleado.getEmail().getValue());
			txtClave.setText(empleado.getClave().getValue());
		} else {
			txtCedula.setText("");
			txtNombre.setText("");
			txtEmail.setText("");
			txtClave.setText("");
		}

	}

	/**
	 * permite mostrar la ventana de agregar empleado
	 */
	@FXML
	public void agregarEmpleado() {
		manejadorEscenarios.cargarEscenarioGestionarEmpleados();
		tablaEmpleados.refresh();
	}

	/**
	 * permite eliminar un empleado seleccionado
	 */
	@FXML
	public void eliminarEmpleado() {

		int indice = tablaEmpleados.getSelectionModel().getSelectedIndex();

		System.out.println(tablaEmpleados.getItems().size());

		Empleado empleado = tablaEmpleados.getItems().get(indice).getEmpleado();

		if (manejadorEscenarios.eliminarEmpleado(empleado)) {
			tablaEmpleados.getItems().remove(indice);
			Utilidades.mostrarMensaje("Borrar", "El empleado ha sido eliminado con exito");
		} else {
			Utilidades.mostrarMensaje("Error", "El empleado no pudo ser eliminado");
		}

	}

	public void setEscenario(Stage escenario) {
		this.escenario = escenario;

	}

	public void setManejador(ManejadorEscenarios manejadorEscenarios) {
		this.manejadorEscenarios = manejadorEscenarios;
	}

}
