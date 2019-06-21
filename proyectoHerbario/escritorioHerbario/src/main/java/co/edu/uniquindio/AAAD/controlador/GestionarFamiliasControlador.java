package co.edu.uniquindio.AAAD.controlador;

import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.modelo.AdministradorDelegado;
import co.edu.uniquindio.AAAD.modelo.FamiliaObservable;
import co.edu.uniquindio.AAAD.modelo.OrdenObservable;
import co.edu.uniquindio.AAAD.persistencia.Familia;
import co.edu.uniquindio.AAAD.util.Utilidades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GestionarFamiliasControlador {

	private Stage escenario;

	private ManejadorEscenarios manejadorEscenarios;

	/**
	 * table donde se almacena la informacion de las categorias los familias
	 */
	@FXML
	private TableView<FamiliaObservable> tablaFamilias;
	/**
	 * hace referencia a la columna con las superCategorias
	 */
	@FXML
	private TableColumn<FamiliaObservable, String> columnaOrden;
	/**
	 * hace referencia a la columna de los nombres de las categorias
	 */
	@FXML
	private TableColumn<FamiliaObservable, String> columnaNombre;

	/**
	 * para almacenar empleados observables
	 */
	private ObservableList<FamiliaObservable> familiasObservables;

	@FXML
	private TextField jtfNombre;

	@FXML
	private TextField jtfBuscar;

	@FXML
	private ComboBox<String> comboBusqueda;

	@FXML
	private ComboBox<OrdenObservable> comboOrdenes;

	private FamiliaObservable familiaObservable;

	private AdministradorDelegado administradorDelegado;

	public GestionarFamiliasControlador() {
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		columnaOrden.setCellValueFactory(ordenCelda -> ordenCelda.getValue().getOrden());
		columnaNombre.setCellValueFactory(ordenCelda -> ordenCelda.getValue().getNombre());

		mostrarDetalleFamilia(null);

		tablaFamilias.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetalleFamilia(newValue));
		administradorDelegado = AdministradorDelegado.administradorDelegado;
		familiasObservables = FXCollections.observableArrayList();

		actualizarTabla();

		comboBusqueda.getItems().removeAll(comboBusqueda.getItems());
		comboBusqueda.getItems().addAll("Buscar por nombre", "Buscar por ID");
		comboBusqueda.getSelectionModel().selectFirst();

		comboOrdenes.getItems().removeAll(comboOrdenes.getItems());
		comboOrdenes.getItems().addAll(administradorDelegado.listarOrdenesObservables());
		comboOrdenes.getSelectionModel().selectFirst();

	}

	/**
	 * permite mostrar la informacion del familia seleccionado
	 * 
	 * @param familia familia al que se le desea mostrar el detalle
	 */
	public void mostrarDetalleFamilia(FamiliaObservable familia) {

		if (familia != null) {
			familiaObservable = familia;

			jtfNombre.setText(familia.getNombre().getValue());

		} else {
			jtfNombre.setText("");
		}

	}

	/**
	 * permite mostrar la ventana de agregar familia
	 */
	@FXML
	public void agregarFamilia() {
		Familia familia = new Familia();
		familia.setNombre(jtfNombre.getText());

		if (jtfNombre.getText().isEmpty()) {
			Utilidades.mostrarMensaje("Advertencia", "Ingresa un nombre para continuar");
		} else {
			if (comboOrdenes.getValue() == null) {
				Utilidades.mostrarMensaje("Advertencia", "Debe crear al menos un orden primero");
			} else {
				try {
					familia.setOrdenDelaFamilia(comboOrdenes.getValue().getOrden());
					if (administradorDelegado.insertarFamilia(familia)) {
						agregarALista(administradorDelegado.buscarFamiliaPorSuNombre(familia.getNombre()));
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
		int indice = tablaFamilias.getSelectionModel().getSelectedIndex();
		tablaFamilias.setItems((ObservableList<FamiliaObservable>) administradorDelegado.listarFamiliasObservables());
		tablaFamilias.getSelectionModel().clearSelection();
		tablaFamilias.getSelectionModel().select(indice);
	}

	/**
	 * permite eliminar un familia seleccionado
	 */
	@FXML
	public void eliminarFamilia() {

		int indice = tablaFamilias.getSelectionModel().getSelectedIndex();

		Familia familia = tablaFamilias.getItems().get(indice).getFamilia();

		try {
			if (administradorDelegado.eliminarFamilia(familia)) {
				tablaFamilias.getItems().remove(indice);
				Utilidades.mostrarMensaje("Borrar", "La familia ha sido eliminada con exito");
			} else {
				Utilidades.mostrarMensaje("Error", "La familia no pudo ser eliminada");
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
	public void editarFamilia() {

		int indice = tablaFamilias.getSelectionModel().getSelectedIndex();

		if (indice != -1) {
			Familia familia = tablaFamilias.getItems().get(indice).getFamilia();

			if (jtfNombre.getText().equals(familia.getNombre())) {
				Utilidades.mostrarMensaje("Info", "Cambia algun atributo de la familia");
			} else {
				familia.setNombre(jtfNombre.getText());

				try {
					if (administradorDelegado.modificarFamilia(familia)) {
						Utilidades.mostrarMensaje("Borrar", "La familia ha sido modificada con exito");
						jtfNombre.setText("");
					} else {
						Utilidades.mostrarMensaje("Error", "La familia no pudo ser modificada");
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
				FamiliaObservable familiaObservable = null;
				Familia familia = new Familia();
				if (comboBusqueda.getValue().equals("Buscar por nombre")) {
					familia = administradorDelegado.buscarFamiliaPorSuNombre(criterio);
				} else {
					familia = administradorDelegado.buscarFamilia(Long.parseLong(criterio));
				}
				if (familia == null) {
					Utilidades.mostrarMensaje("Familia no encontrado",
							"Intenta con otro parametro o método de busqueda");

				} else {
					familiaObservable = new FamiliaObservable(familia);
					ObservableList<FamiliaObservable> lista = FXCollections.observableArrayList();
					lista.add(familiaObservable);
					tablaFamilias.setItems(lista);
					tablaFamilias.getSelectionModel().clearSelection();
				}
			} catch (Exception e) {
			}
		} else {
			tablaFamilias
					.setItems((ObservableList<FamiliaObservable>) administradorDelegado.listarFamiliasObservables());
			tablaFamilias.getSelectionModel().clearSelection();
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
	public void agregarALista(Familia familia) {
		familiasObservables.add(new FamiliaObservable(familia));
	}
}
