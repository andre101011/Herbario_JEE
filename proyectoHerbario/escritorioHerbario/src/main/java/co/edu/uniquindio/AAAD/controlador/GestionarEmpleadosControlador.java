/**
 * 
 */
package co.edu.uniquindio.AAAD.controlador;

import co.edu.uniquindio.AAAD.persistencia.Empleado;

import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.modelo.AdministradorDelegado;
import co.edu.uniquindio.AAAD.modelo.EmpleadoObservable;
import co.edu.uniquindio.AAAD.util.Utilidades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * @author EinerZG
 */
public class GestionarEmpleadosControlador {

	/**
	 * table donde se almacena la informacion de los empleados
	 */
	@FXML
	private TableView<EmpleadoObservable> tablaEmpleados;
	/**
	 * hace referencia a la columna con las cedulas
	 */
	@FXML
	private TableColumn<EmpleadoObservable, String> columnaCedula;
	/**
	 * hace referencia a la columna de los nombres de los empleados
	 */
	@FXML
	private TableColumn<EmpleadoObservable, String> columnaNombre;
	/**
	 * etiqueta de cedula
	 */

	@FXML
	private TextField jtfBuscar;

	@FXML
	private ComboBox<String> comboBusqueda;

	@FXML
	private TextField jtfNombre;

	@FXML
	private TextField jtfCedula;

	@FXML
	private TextField jtfEmail;

	@FXML
	private TextField jtfClave;

	/**
	 * instancia del manejador de escenario
	 */
	private ManejadorEscenarios manejadorEscenarios;
	/**
	 * para almacenar empleados observables
	 */
	private ObservableList<EmpleadoObservable> empleadosObservables;
	/**
	 * conexion con capa de negocio
	 */
	private AdministradorDelegado administradorDelegado;

	private EmpleadoObservable empleadoObservable;
	private Stage escenario;

	public GestionarEmpleadosControlador() {
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		columnaCedula.setCellValueFactory(empleadoCelda -> empleadoCelda.getValue().getCedula());
		columnaNombre.setCellValueFactory(empleadoCelda -> empleadoCelda.getValue().getNombre());

		mostrarDetalleEmpleado(null);

		tablaEmpleados.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetalleEmpleado(newValue));

		administradorDelegado = AdministradorDelegado.administradorDelegado;
		empleadosObservables = FXCollections.observableArrayList();

		actualizarTabla();
	}

	/**
	 * permite obtener una instancia del escenario general
	 * 
	 * @param escenarioInicial
	 */
	public void setEscenarioInicial(ManejadorEscenarios escenarioInicial) {
		this.manejadorEscenarios = escenarioInicial;
		tablaEmpleados.setItems(getEmpleadosObservables());
	}

	/**
	 * permite mostrar la informacion del empleado seleccionado
	 * 
	 * @param empleado empleado al que se le desea mostrar el detalle
	 */
	public void mostrarDetalleEmpleado(EmpleadoObservable empleado) {

		if (empleado != null) {
			empleadoObservable = empleado;

			jtfNombre.setText(empleado.getNombre().getValue());
			jtfCedula.setText(empleado.getCedula().getValue());
			jtfEmail.setText(empleado.getEmail().getValue());
			jtfClave.setText(empleado.getClave().getValue());

		} else {
			jtfNombre.setText("");
			jtfEmail.setText("");
			jtfCedula.setText("");
			jtfClave.setText("");
		}

	}

	/**
	 * permite mostrar la ventana de agregar empleado
	 */
	@FXML
	public void agregarEmpleado() {

		if (jtfNombre.getText().isEmpty() || jtfCedula.getText().isEmpty() || jtfEmail.getText().isEmpty()
				|| jtfClave.getText().isEmpty()) {
			Utilidades.mostrarMensaje("Advertencia", "Llena todos los campos para continuar");
		} else {

			Empleado empleado = new Empleado();
			empleado.setCedula(jtfCedula.getText());
			empleado.setNombre(jtfNombre.getText());
			empleado.setClave(jtfClave.getText());
			empleado.setEmail(jtfEmail.getText());

			try {
				if (administradorDelegado.insertarEmpleado(empleado)) {
					agregarALista(empleado);
					Utilidades.mostrarMensaje("Registro", "Registro exitoso!!");
					jtfNombre.setText("");
					jtfEmail.setText("");
					jtfCedula.setText("");
					jtfClave.setText("");
				} else {
					Utilidades.mostrarMensaje("Registro", "Error en registro!!");
				}
			} catch (ElementoRepetidoException e) {
				Utilidades.mostrarMensaje("Error", e.toString());
				e.printStackTrace();
			}
			actualizarTabla();

		}
	}

	void actualizarTabla() {
		int indice = tablaEmpleados.getSelectionModel().getSelectedIndex();
		tablaEmpleados
				.setItems((ObservableList<EmpleadoObservable>) administradorDelegado.listarEmpleadosObservables());
		tablaEmpleados.getSelectionModel().clearSelection();
		tablaEmpleados.getSelectionModel().select(indice);
	}

	/**
	 * permite eliminar un empleado seleccionado
	 */
	@FXML
	public void eliminarEmpleado() {

		int indice = tablaEmpleados.getSelectionModel().getSelectedIndex();

		Empleado empleado = tablaEmpleados.getItems().get(indice).getEmpleado();

		try {
			System.out.println(empleado.getCedula());
			if (administradorDelegado.eliminarEmpleado(administradorDelegado.buscarEmpleado(empleado.getCedula()))) {
				tablaEmpleados.getItems().remove(indice);
				Utilidades.mostrarMensaje("Enhorabuena!", "El empleado ha sido eliminado con exito");
			} else {
				Utilidades.mostrarMensaje("Error", "El empleado no pudo ser eliminado");
			}
		} catch (ElementoNoEncontradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actualizarTabla();
	}

	/**
	 * permite eliminar un empleado seleccionado
	 */
	@FXML
	public void editarEmpleado() {

		int indice = tablaEmpleados.getSelectionModel().getSelectedIndex();

		if (indice != -1) {
			Empleado empleado = tablaEmpleados.getItems().get(indice).getEmpleado();

			if (jtfNombre.getText().equals(empleado.getNombre()) && jtfClave.getText().equals(empleado.getClave())
					&& jtfEmail.getText().equals(empleado.getEmail())
					&& jtfCedula.getText().equals(empleado.getCedula())) {
				Utilidades.mostrarMensaje("Info", "Cambia algun atributo del empleado");
			} else if (!jtfCedula.getText().equals(empleado.getCedula())) {
				Utilidades.mostrarMensaje("Info", "No puedes modificar la cedula de un empleado");
			} else {
				empleado.setNombre(jtfNombre.getText());
				empleado.setClave(jtfClave.getText());
				empleado.setEmail(jtfEmail.getText());

				try {
					if (administradorDelegado.modificarEmpleado(empleado)) {
						tablaEmpleados.getItems().remove(indice);
						Utilidades.mostrarMensaje("Enhorabuena!", "El empleado ha sido modificado con exito");
						jtfNombre.setText("");
						jtfEmail.setText("");
						jtfCedula.setText("");
						jtfClave.setText("");

					} else {
						Utilidades.mostrarMensaje("Error", "El empleado no pudo ser modificado");
					}
				} catch (ElementoRepetidoException | ElementoNoEncontradoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			actualizarTabla();
		} else {
			Utilidades.mostrarMensaje("", "Selecciona una fila de la tabla para poder editarla");
		}
	}

	@FXML
	public void buscar() {
		String cedula = jtfBuscar.getText();

		if (!cedula.isEmpty()) {
			try {
				EmpleadoObservable empleadoObservable = null;
				Empleado empleado = administradorDelegado.buscarEmpleado(cedula);

				if (empleado == null) {
					Utilidades.mostrarMensaje("", "No se encontro");

				} else {
					empleadoObservable = new EmpleadoObservable(empleado);
					ObservableList<EmpleadoObservable> lista = FXCollections.observableArrayList();
					lista.add(empleadoObservable);
					tablaEmpleados.setItems(lista);
					tablaEmpleados.getSelectionModel().clearSelection();
				}
			} catch (Exception e) {
			}
		} else {
			tablaEmpleados
					.setItems((ObservableList<EmpleadoObservable>) administradorDelegado.listarEmpleadosObservables());
			tablaEmpleados.getSelectionModel().clearSelection();
		}
	}

	public void setEscenario(Stage escenario) {
		this.escenario = escenario;

	}

	public void setManejador(ManejadorEscenarios manejadorEscenarios) {
		this.manejadorEscenarios = manejadorEscenarios;
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

}
