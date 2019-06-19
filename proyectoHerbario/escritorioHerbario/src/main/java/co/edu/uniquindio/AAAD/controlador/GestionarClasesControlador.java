package co.edu.uniquindio.AAAD.controlador;

import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.modelo.AdministradorDelegado;
import co.edu.uniquindio.AAAD.modelo.ClaseObservable;
import co.edu.uniquindio.AAAD.persistencia.Clase;
import co.edu.uniquindio.AAAD.persistencia.Empleado;
import co.edu.uniquindio.AAAD.util.Utilidades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GestionarClasesControlador {

	private Stage escenario;

	private ManejadorEscenarios manejadorEscenarios;

	/**
	 * table donde se almacena la informacion de las categorias los clases
	 */
	@FXML
	private TableView<ClaseObservable> tablaClases;
	/**
	 * hace referencia a la columna con las superCategorias
	 */
	@FXML
	private TableColumn<ClaseObservable, Number> columnaId;
	/**
	 * hace referencia a la columna de los nombres de las categorias
	 */
	@FXML
	private TableColumn<ClaseObservable, String> columnaNombre;

	/**
	 * para almacenar empleados observables
	 */
	private ObservableList<ClaseObservable> clasesObservables;

	@FXML
	private TextField jtfNombre;

	private ClaseObservable claseObservable;

	private AdministradorDelegado administradorDelegado;

	public GestionarClasesControlador() {
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		columnaId.setCellValueFactory(claseCelda -> claseCelda.getValue().getId());
		columnaNombre.setCellValueFactory(claseCelda -> claseCelda.getValue().getNombre());

		mostrarDetalleClase(null);

		tablaClases.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetalleClase(newValue));
		administradorDelegado = AdministradorDelegado.administradorDelegado;
		clasesObservables = FXCollections.observableArrayList();

		actualizarTabla();
	}

	/**
	 * permite mostrar la informacion del clase seleccionado
	 * 
	 * @param clase clase al que se le desea mostrar el detalle
	 */
	public void mostrarDetalleClase(ClaseObservable clase) {

		if (clase != null) {
			claseObservable = clase;

			jtfNombre.setText(clase.getNombre().getValue());

		} else {
			jtfNombre.setText("");
		}

	}

	/**
	 * permite mostrar la ventana de agregar clase
	 */
	@FXML
	public void agregarClase() {
		Clase clase = new Clase();
		clase.setNombre(jtfNombre.getText());

		try {
			if (administradorDelegado.insertarClase(clase)) {
				agregarALista(administradorDelegado.buscarClasePorSuNombre(clase.getNombre()));
				Utilidades.mostrarMensaje("Registro", "Registro exitoso!!");
			} else {
				Utilidades.mostrarMensaje("Registro", "Error en registro!!");
			}
		} catch (ElementoRepetidoException e) {
			Utilidades.mostrarMensaje("Error", e.toString());
			e.printStackTrace();
		}
		actualizarTabla();

	}

	void actualizarTabla() {
		int indice = tablaClases.getSelectionModel().getSelectedIndex();
		tablaClases.setItems((ObservableList<ClaseObservable>) administradorDelegado.listarClasesObservables());
		tablaClases.getSelectionModel().clearSelection();
		tablaClases.getSelectionModel().select(indice);
	}

	/**
	 * permite eliminar un clase seleccionado
	 */
	@FXML
	public void eliminarClase() {

		int indice = tablaClases.getSelectionModel().getSelectedIndex();

		Clase clase = tablaClases.getItems().get(indice).getClase();

		try {
			if (administradorDelegado.eliminarClase(clase)) {
				tablaClases.getItems().remove(indice);
				Utilidades.mostrarMensaje("Borrar", "La clase ha sido eliminada con exito");
			} else {
				Utilidades.mostrarMensaje("Error", "La clase no pudo ser eliminada");
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
	public void editarClase() {

		int indice = tablaClases.getSelectionModel().getSelectedIndex();

		Clase clase = tablaClases.getItems().get(indice).getClase();

		if (jtfNombre.getText().equals(clase.getNombre())) {
			Utilidades.mostrarMensaje("Info", "Cambia algun atributo de la clase");
		} else {
			clase.setNombre(jtfNombre.getText());

			try {
				if (administradorDelegado.modificarClase(clase)) {
					Utilidades.mostrarMensaje("Borrar", "La clase ha sido modificada con exito");
				} else {
					Utilidades.mostrarMensaje("Error", "La clase no pudo ser modificada");
				}
			} catch (ElementoRepetidoException | ElementoNoEncontradoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		actualizarTabla();

	}

	public void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}

	public void setManejador(ManejadorEscenarios manejadorEscenarios) {
		this.manejadorEscenarios = manejadorEscenarios;

	}

	/**
	 * permite agrega una liente a la lista observable
	 * 
	 * @param empleado
	 */
	public void agregarALista(Clase clase) {
		clasesObservables.add(new ClaseObservable(clase));
	}
}
