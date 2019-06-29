package co.edu.uniquindio.AAAD.controlador;

import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.modelo.AdministradorDelegado;
import co.edu.uniquindio.AAAD.modelo.ClaseObservable;
import co.edu.uniquindio.AAAD.modelo.FamiliaObservable;
import co.edu.uniquindio.AAAD.modelo.OrdenObservable;
import co.edu.uniquindio.AAAD.persistencia.Orden;
import co.edu.uniquindio.AAAD.util.Utilidades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GestionarOrdenesControlador {

	private Stage escenario;

	private ManejadorEscenarios manejadorEscenarios;

	/**
	 * table donde se almacena la informacion de las categorias los ordens
	 */
	@FXML
	private TableView<OrdenObservable> tablaOrdenes;
	/**
	 * hace referencia a la columna con las superCategorias
	 */
	@FXML
	private TableColumn<OrdenObservable, String> columnaClase;
	/**
	 * hace referencia a la columna de los nombres de las categorias
	 */
	@FXML
	private TableColumn<OrdenObservable, String> columnaNombre;

	/**
	 * para almacenar empleados observables
	 */
	private ObservableList<OrdenObservable> ordenesObservables;

	@FXML
	private TextField jtfNombre;

	@FXML
	private TextField jtfBuscar;

	@FXML
	private ComboBox<String> comboBusqueda;

	@FXML
	private ComboBox<ClaseObservable> comboClases;

	private OrdenObservable ordenObservable;

	private AdministradorDelegado administradorDelegado;

	public GestionarOrdenesControlador() {
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		columnaClase.setCellValueFactory(ordenCelda -> ordenCelda.getValue().getClase());
		columnaNombre.setCellValueFactory(ordenCelda -> ordenCelda.getValue().getNombre());

		mostrarDetalleOrden(null);

		tablaOrdenes.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetalleOrden(newValue));
		administradorDelegado = AdministradorDelegado.administradorDelegado;
		ordenesObservables = FXCollections.observableArrayList();

		actualizarTabla();

		comboBusqueda.getItems().removeAll(comboBusqueda.getItems());
		comboBusqueda.getItems().addAll("Buscar por nombre", "Buscar por ID");
		comboBusqueda.getSelectionModel().selectFirst();

		comboClases.getItems().removeAll(comboClases.getItems());
		comboClases.getItems().addAll(administradorDelegado.listarClasesObservables());
		comboClases.getSelectionModel().selectFirst();

	}

	/**
	 * permite mostrar la informacion del orden seleccionado
	 * 
	 * @param ordenObservable orden al que se le desea mostrar el detalle
	 */
	public void mostrarDetalleOrden(OrdenObservable ordenObservable) {

		if (ordenObservable != null) {
			ordenObservable = ordenObservable;

			jtfNombre.setText(ordenObservable.getNombre().getValue());
			ClaseObservable claseObservable = new ClaseObservable(ordenObservable.getOrden().getClaseDelOrden());
			comboClases.getSelectionModel().select(claseObservable);

		} else {
			jtfNombre.setText("");
		}

	}

	/**
	 * permite mostrar la ventana de agregar orden
	 */
	@FXML
	public void agregarOrden() {

		if (jtfNombre.getText().isEmpty()) {
			Utilidades.mostrarMensaje("Advertencia", "Ingresa un nombre para continuar");
		} else {
			if (comboClases.getValue() == null) {
				Utilidades.mostrarMensaje("Advertencia", "Debe crear al menos una clase primero");
			} else {
				Orden orden = new Orden();
				orden.setNombre(jtfNombre.getText());

				try {
					orden.setClaseDelOrden(comboClases.getValue().getClase());
					if (administradorDelegado.insertarOrden(orden)) {
						agregarALista(administradorDelegado.buscarOrdenPorSuNombre(orden.getNombre()));
						Utilidades.mostrarMensaje("Registro", "Registro exitoso!!");
						jtfNombre.setText("");
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
	}

	void actualizarTabla() {
		int indice = tablaOrdenes.getSelectionModel().getSelectedIndex();
		tablaOrdenes.setItems((ObservableList<OrdenObservable>) administradorDelegado.listarOrdenesObservables());
		tablaOrdenes.getSelectionModel().clearSelection();
		tablaOrdenes.getSelectionModel().select(indice);
	}

	/**
	 * permite eliminar un orden seleccionado
	 */
	@FXML
	public void eliminarOrden() {

		int indice = tablaOrdenes.getSelectionModel().getSelectedIndex();

		Orden orden = tablaOrdenes.getItems().get(indice).getOrden();

		try {
			if (administradorDelegado.eliminarOrden(orden)) {
				tablaOrdenes.getItems().remove(indice);
				Utilidades.mostrarMensaje("Enhorabuena!", "La orden ha sido eliminada con exito");
			} else {
				Utilidades.mostrarMensaje("Error", "La orden no pudo ser eliminada");
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
	public void editarOrden() {

		int indice = tablaOrdenes.getSelectionModel().getSelectedIndex();
		if (indice != -1) {
			Orden orden = tablaOrdenes.getItems().get(indice).getOrden();

			if (jtfNombre.getText().equals(orden.getNombre())) {
				Utilidades.mostrarMensaje("Info", "Cambia algun atributo de la orden");
			} else {
				orden.setNombre(jtfNombre.getText());

				try {
					if (administradorDelegado.modificarOrden(orden)) {
						Utilidades.mostrarMensaje("Enhorabuena!", "La orden ha sido modificada con exito");
						jtfNombre.setText("");
					} else {
						Utilidades.mostrarMensaje("Error", "La orden no pudo ser modificada");
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
		String criterio = jtfBuscar.getText();

		if (!criterio.isEmpty()) {
			try {
				OrdenObservable ordenObservable = null;
				Orden orden = new Orden();
				if (comboBusqueda.getValue().equals("Buscar por nombre")) {
					orden = administradorDelegado.buscarOrdenPorSuNombre(criterio);
				} else {
					orden = administradorDelegado.buscarOrden(Long.parseLong(criterio));
				}
				if (orden == null) {
					Utilidades.mostrarMensaje("Orden no encontrado", "Intenta con otro parametro o método de busqueda");

				} else {
					ordenObservable = new OrdenObservable(orden);
					ObservableList<OrdenObservable> lista = FXCollections.observableArrayList();
					lista.add(ordenObservable);
					tablaOrdenes.setItems(lista);
					tablaOrdenes.getSelectionModel().clearSelection();
				}
			} catch (Exception e) {
			}
		} else {
			tablaOrdenes.setItems((ObservableList<OrdenObservable>) administradorDelegado.listarOrdenesObservables());
			tablaOrdenes.getSelectionModel().clearSelection();
		}
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
	public void agregarALista(Orden orden) {
		ordenesObservables.add(new OrdenObservable(orden));
	}
}
