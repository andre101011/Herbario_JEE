package co.edu.uniquindio.AAAD.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.modelo.AdministradorDelegado;
import co.edu.uniquindio.AAAD.modelo.EspecieObservable;
import co.edu.uniquindio.AAAD.modelo.FamiliaObservable;
import co.edu.uniquindio.AAAD.modelo.GeneroObservable;
import co.edu.uniquindio.AAAD.modelo.OrdenObservable;
import co.edu.uniquindio.AAAD.persistencia.Especie;
import co.edu.uniquindio.AAAD.persistencia.Familia;
import co.edu.uniquindio.AAAD.persistencia.Genero;
import co.edu.uniquindio.AAAD.util.Utilidades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class GestionarGenerosControlador {

	private Stage escenario;

	private ManejadorEscenarios manejadorEscenarios;

	/**
	 * para almacenar empleados observables
	 */
	private ObservableList<GeneroObservable> generosObservables;

	private GeneroObservable generoObservable;

	private AdministradorDelegado administradorDelegado;

	// FXML

	@FXML
	private TableView<GeneroObservable> tablaGeneros;

	@FXML
	private TableColumn<GeneroObservable, String> columnaFamilias;

	@FXML
	private TableColumn<GeneroObservable, String> columnaNombre;

	@FXML
	private TextField jtfNombre;

	@FXML
	private Button btnEliminar;

	@FXML
	private Button btnEditar;

	@FXML
	private Button btnAgregar;

	@FXML
	private ComboBox<FamiliaObservable> comboFamilias;

	@FXML
	private TextField jtfBuscar;

	@FXML
	private ComboBox<String> comboBusqueda;

	@FXML
	private Button btnBuscar;

	public GestionarGenerosControlador() {
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		columnaFamilias.setCellValueFactory(ordenCelda -> ordenCelda.getValue().getFamilia());
		columnaNombre.setCellValueFactory(ordenCelda -> ordenCelda.getValue().getNombre());

		mostrarDetalleGenero(null);

		tablaGeneros.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetalleGenero(newValue));
		administradorDelegado = AdministradorDelegado.administradorDelegado;
		generosObservables = FXCollections.observableArrayList();

		actualizarTabla();

		comboBusqueda.getItems().removeAll(comboBusqueda.getItems());
		comboBusqueda.getItems().addAll("Buscar por nombre", "Buscar por ID");
		comboBusqueda.getSelectionModel().selectFirst();

		comboFamilias.getItems().removeAll(comboFamilias.getItems());
		comboFamilias.getItems().addAll(administradorDelegado.listarFamiliasObservables());
		comboFamilias.getSelectionModel().selectFirst();

	}

	/**
	 * permite mostrar la informacion del genero seleccionado
	 * 
	 * @param genero genero al que se le desea mostrar el detalle
	 */
	public void mostrarDetalleGenero(GeneroObservable genero) {

		if (genero != null) {
			generoObservable = genero;

			jtfNombre.setText(genero.getNombre().getValue());

		} else {
			jtfNombre.setText("");
		}

	}

	/**
	 * permite mostrar la ventana de agregar genero
	 */
	@FXML
	public void agregarGenero() {
		Genero genero = new Genero();
		genero.setNombre(jtfNombre.getText());

		if (jtfNombre.getText().isEmpty()) {
			Utilidades.mostrarMensaje("Advertencia", "Ingresa un nombre para continuar");
		} else {
			if (comboFamilias.getValue() == null) {
				Utilidades.mostrarMensaje("Advertencia", "Debe crear al menos una familia primero");
			} else {
				try {
					genero.setFamiliaDelGenero((comboFamilias.getValue().getFamilia()));
					if (administradorDelegado.insertarGenero(genero)) {
						agregarALista(administradorDelegado.buscarGeneroPorSuNombre(genero.getNombre()));
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
		int indice = tablaGeneros.getSelectionModel().getSelectedIndex();
		tablaGeneros.setItems((ObservableList<GeneroObservable>) administradorDelegado.listarGenerosObservables());
		tablaGeneros.getSelectionModel().clearSelection();
		tablaGeneros.getSelectionModel().select(indice);
	}

	/**
	 * permite eliminar un genero seleccionado
	 */
	@FXML
	public void eliminarGenero() {

		int indice = tablaGeneros.getSelectionModel().getSelectedIndex();

		Genero genero = tablaGeneros.getItems().get(indice).getGenero();

		try {
			if (administradorDelegado.eliminarGenero(genero)) {
				tablaGeneros.getItems().remove(indice);
				Utilidades.mostrarMensaje("Borrar", "El genero ha sido eliminado con exito");
			} else {
				Utilidades.mostrarMensaje("Error", "El genero no pudo ser eliminado");
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
	public void editarGenero() {

		int indice = tablaGeneros.getSelectionModel().getSelectedIndex();
		if (indice != -1) {

			Genero genero = tablaGeneros.getItems().get(indice).getGenero();

			if (jtfNombre.getText().equals(genero.getNombre())) {
				Utilidades.mostrarMensaje("Info", "Cambia algun atributo del genero");
			} else {
				genero.setNombre(jtfNombre.getText());

				try {
					if (administradorDelegado.modificarGenero(genero)) {
						Utilidades.mostrarMensaje("Borrar", "El genero ha sido modificado con exito");
						jtfNombre.setText("");
					} else {
						Utilidades.mostrarMensaje("Error", "El genero no pudo ser modificado");
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
				GeneroObservable generoObservable = null;
				Genero genero = new Genero();
				if (comboBusqueda.getValue().equals("Buscar por nombre")) {
					genero = administradorDelegado.buscarGeneroPorSuNombre(criterio);
				} else {
					genero = administradorDelegado.buscarGenero(Long.parseLong(criterio));
				}
				if (genero == null) {
					Utilidades.mostrarMensaje("Genero no encontrado",
							"Intenta con otro parametro o método de busqueda");

				} else {
					generoObservable = new GeneroObservable(genero);
					ObservableList<GeneroObservable> lista = FXCollections.observableArrayList();
					lista.add(generoObservable);
					tablaGeneros.setItems(lista);
					tablaGeneros.getSelectionModel().clearSelection();
				}
			} catch (Exception e) {
			}
		} else {
			tablaGeneros.setItems((ObservableList<GeneroObservable>) administradorDelegado.listarGenerosObservables());
			tablaGeneros.getSelectionModel().clearSelection();
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
	public void agregarALista(Genero genero) {
		generosObservables.add(new GeneroObservable(genero));
	}
}
