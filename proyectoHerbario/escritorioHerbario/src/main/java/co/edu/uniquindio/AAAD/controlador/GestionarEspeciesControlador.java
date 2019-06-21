package co.edu.uniquindio.AAAD.controlador;

import org.glassfish.admin.rest.adapter.AdminJerseyServiceIteratorProvider;

import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.modelo.AdministradorDelegado;
import co.edu.uniquindio.AAAD.modelo.FamiliaObservable;
import co.edu.uniquindio.AAAD.modelo.GeneroObservable;
import co.edu.uniquindio.AAAD.modelo.EspecieObservable;
import co.edu.uniquindio.AAAD.modelo.OrdenObservable;
import co.edu.uniquindio.AAAD.persistencia.Especie;
import co.edu.uniquindio.AAAD.util.Utilidades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GestionarEspeciesControlador {

	private Stage escenario;

	private ManejadorEscenarios manejadorEscenarios;

	/**
	 * table donde se almacena la informacion de las categorias los especies
	 */
	@FXML
	private TableView<EspecieObservable> tablaEspecies;
	/**
	 * hace referencia a la columna con las superCategorias
	 */
	@FXML
	private TableColumn<EspecieObservable, String> columnaFamilias;
	/**
	 * hace referencia a la columna de los nombres de las categorias
	 */
	@FXML
	private TableColumn<EspecieObservable, String> columnaNombre;

	/**
	 * para almacenar empleados observables
	 */
	private ObservableList<EspecieObservable> especiesObservables;

	@FXML
	private TextField jtfNombre;

	@FXML
	private TextField jtfBuscar;

	@FXML
	private ComboBox<String> comboBusqueda;

	@FXML
	private ComboBox<GeneroObservable> comboGeneros;

	private EspecieObservable especieObservable;

	private AdministradorDelegado administradorDelegado;

	public GestionarEspeciesControlador() {
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		columnaFamilias.setCellValueFactory(ordenCelda -> ordenCelda.getValue().getGenero());
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
			if (administradorDelegado.eliminarEspecie(especie)) {
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
