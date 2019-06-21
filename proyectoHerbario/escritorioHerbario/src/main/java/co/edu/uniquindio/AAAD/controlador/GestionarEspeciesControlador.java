package co.edu.uniquindio.AAAD.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.modelo.AdministradorDelegado;
import co.edu.uniquindio.AAAD.modelo.ClaseObservable;
import co.edu.uniquindio.AAAD.modelo.EspecieObservable;
import co.edu.uniquindio.AAAD.modelo.FamiliaObservable;
import co.edu.uniquindio.AAAD.modelo.GeneroObservable;
import co.edu.uniquindio.AAAD.modelo.OrdenObservable;
import co.edu.uniquindio.AAAD.persistencia.Especie;
import co.edu.uniquindio.AAAD.persistencia.Registro.Estado;
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

public class GestionarEspeciesControlador {

	private Stage escenario;

	private ManejadorEscenarios manejadorEscenarios;

	private ObservableList<EspecieObservable> especiesObservables;

	private EspecieObservable especieObservable;

	private AdministradorDelegado administradorDelegado;

	// Componentes FXML

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField jtfBuscar;

	@FXML
	private Button btnBuscar;

	@FXML
	private ComboBox<String> comboCategoriasBusqueda;

	@FXML
	private ComboBox<String> comboBusqueda;

	@FXML
	private TableView<EspecieObservable> tablaEspecies;

	@FXML
	private TableColumn<EspecieObservable, Number> columnaId;

	@FXML
	private TableColumn<EspecieObservable, String> columnaGenero;

	@FXML
	private TableColumn<EspecieObservable, String> columnaNombre;

	@FXML
	private TextField jtfNombre;

	@FXML
	private Button btnEliminar;

	@FXML
	private Button btnEditar;

	@FXML
	private ComboBox<GeneroObservable> comboGenero;

	@FXML
	private ComboBox<FamiliaObservable> comboFamilia;

	@FXML
	private ComboBox<OrdenObservable> comboOrden;

	@FXML
	private ComboBox<ClaseObservable> comboClase;

	@FXML
	private ComboBox<Estado> comboEstado;

	@FXML
	private DatePicker PickerFecha;

	@FXML
	private TextArea jtfJustificacion;

	@FXML
	private Label labelEnviador;

	@FXML
	private Label labelEvaluador;

	@FXML
	private Button btnSeleccionarImagen;

	@FXML
	private ImageView campoImagen;

	public GestionarEspeciesControlador() {
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		columnaId.setCellValueFactory(ordenCelda -> ordenCelda.getValue().getId());
		columnaGenero.setCellValueFactory(ordenCelda -> ordenCelda.getValue().getGenero());
		columnaNombre.setCellValueFactory(ordenCelda -> ordenCelda.getValue().getNombre());

		mostrarDetalleEspecie(null);

		tablaEspecies.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetalleEspecie(newValue));
		administradorDelegado = AdministradorDelegado.administradorDelegado;
		especiesObservables = FXCollections.observableArrayList();

		actualizarTabla();

		comboBusqueda.getItems().removeAll(comboBusqueda.getItems());
		comboBusqueda.getItems().addAll("Buscar por nombre", "Buscar por ID");
		comboBusqueda.getSelectionModel().selectFirst();

		comboGeneros.getItems().removeAll(comboGeneros.getItems());
		comboGeneros.getItems().addAll(administradorDelegado.listarGenerosObservables());
		comboGeneros.getSelectionModel().selectFirst();

	}

	/**
	 * permite mostrar la informacion del especie seleccionado
	 * 
	 * @param especie especie al que se le desea mostrar el detalle
	 */
	public void mostrarDetalleEspecie(EspecieObservable especie) {

		if (especie != null) {
			especieObservable = especie;

			jtfNombre.setText(especie.getNombre().getValue());

		} else {
			jtfNombre.setText("");
		}

	}

	void actualizarTabla() {
		int indice = tablaEspecies.getSelectionModel().getSelectedIndex();
		tablaEspecies.setItems((ObservableList<EspecieObservable>) administradorDelegado.listarEspeciesObservables());
		tablaEspecies.getSelectionModel().clearSelection();
		tablaEspecies.getSelectionModel().select(indice);
	}

	/**
	 * permite eliminar un especie seleccionado
	 */
	@FXML
	public void eliminarEspecie() {

		int indice = tablaEspecies.getSelectionModel().getSelectedIndex();

		Especie especie = tablaEspecies.getItems().get(indice).getEspecie();

		try {
			if (administradorDelegado.eliminar(especie)) {
				tablaEspecies.getItems().remove(indice);
				Utilidades.mostrarMensaje("Borrar", "El especie ha sido eliminado con exito");
			} else {
				Utilidades.mostrarMensaje("Error", "El especie no pudo ser eliminado");
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
	public void editarEspecie() {

		int indice = tablaEspecies.getSelectionModel().getSelectedIndex();
		if (indice != -1) {

			Especie especie = tablaEspecies.getItems().get(indice).getEspecie();

			if (jtfNombre.getText().equals(especie.getNombre())) {
				Utilidades.mostrarMensaje("Info", "Cambia algun atributo del especie");
			} else {
				especie.setNombre(jtfNombre.getText());

				try {
					if (administradorDelegado.modificarEspecie(especie)) {
						Utilidades.mostrarMensaje("Borrar", "El especie ha sido modificado con exito");
						jtfNombre.setText("");
					} else {
						Utilidades.mostrarMensaje("Error", "El especie no pudo ser modificado");
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
				ObservableList<EspecieObservable> especies = null;
				if (comboBusqueda.getValue().equals("Buscar por nombre")) {
					especies = administradorDelegado.listarEspeciesObservablesPorSuNombre(criterio);
				} else {
					EspecieObservable especie = new EspecieObservable(
							administradorDelegado.buscarEspecie(Long.parseLong(criterio)));
					especies.add(especie);
				}
				if (especies == null) {
					Utilidades.mostrarMensaje("Especies no encontradas",
							"Intenta con otro parametro o método de busqueda");

				} else {
					ObservableList<EspecieObservable> lista = FXCollections.observableArrayList();
					lista.addAll(especies);
					tablaEspecies.setItems(lista);
					tablaEspecies.getSelectionModel().clearSelection();
				}
			} catch (Exception e) {
			}
		} else {
			tablaEspecies
					.setItems((ObservableList<EspecieObservable>) administradorDelegado.listarEspeciesObservables());
			tablaEspecies.getSelectionModel().clearSelection();
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
	public void agregarALista(Especie especie) {
		especiesObservables.add(new EspecieObservable(especie));
	}
}
