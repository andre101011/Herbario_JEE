package co.edu.uniquindio.AAAD.controlador;

import java.util.GregorianCalendar;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.modelo.AdministradorDelegado;
import co.edu.uniquindio.AAAD.modelo.ClaseObservable;
import co.edu.uniquindio.AAAD.modelo.EspecieObservable;
import co.edu.uniquindio.AAAD.modelo.FamiliaObservable;
import co.edu.uniquindio.AAAD.modelo.GeneroObservable;
import co.edu.uniquindio.AAAD.modelo.OrdenObservable;
import co.edu.uniquindio.AAAD.persistencia.Administrador;
import co.edu.uniquindio.AAAD.persistencia.Clase;
import co.edu.uniquindio.AAAD.persistencia.Especie;
import co.edu.uniquindio.AAAD.persistencia.Orden;
import co.edu.uniquindio.AAAD.persistencia.Registro;
import co.edu.uniquindio.AAAD.persistencia.Registro.Estado;
import co.edu.uniquindio.AAAD.util.Utilidades;
import imagen.Imagen;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GestionarEspeciesControlador {

	private Stage escenario;

	private ManejadorEscenarios manejadorEscenarios;

	private ObservableList<EspecieObservable> especiesObservables;

	private EspecieObservable especieObservable;

	private AdministradorDelegado administradorDelegado;

	private Imagen imagen;

	private String ruta;

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
	private Button btnEvaluar;

	@FXML
	private ComboBox<String> comboCategoriasBusqueda;

	@FXML
	private ComboBox<String> comboBusqueda;

	@FXML
	private TableView<EspecieObservable> tablaEspecies;

	@FXML
	private TableColumn<EspecieObservable, Number> columnaId;

	@FXML
	private TableColumn<EspecieObservable, String> columnaEstado;

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
	private ComboBox<Estado> comboEstado;

	@FXML
	private Label labelFecha;

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
		columnaEstado.setCellValueFactory(ordenCelda -> ordenCelda.getValue().getEstado());
		columnaGenero.setCellValueFactory(ordenCelda -> ordenCelda.getValue().getGenero());
		columnaNombre.setCellValueFactory(ordenCelda -> ordenCelda.getValue().getNombre());

		mostrarDetalleEspecie(null);

		tablaEspecies.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetalleEspecie(newValue));
		administradorDelegado = AdministradorDelegado.administradorDelegado;
		especiesObservables = FXCollections.observableArrayList();

		actualizarTabla();

		comboBusqueda.getItems().removeAll(comboBusqueda.getItems());
		comboBusqueda.getItems().addAll("Buscar por nombre", "Buscar por ID", "Buscar por genero", "Buscar por familia",
				"Buscar por orden", "Buscar por clase");
		comboBusqueda.getSelectionModel().selectFirst();

		// Carga todas las clases en el comboBox correspondiente
		comboEstado.getItems().removeAll(comboEstado.getItems());
		comboEstado.getItems().addAll(Estado.Aceptado, Estado.Rechazado);
		comboEstado.getSelectionModel().selectFirst();

		// Carga todas los generos en el comboBox correspondiente
		comboGenero.getItems().removeAll(comboGenero.getItems());
		comboGenero.getItems().addAll(administradorDelegado.listarGenerosObservables());
		comboGenero.getSelectionModel().selectFirst();

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

			// Pone el genero al que pertenece la especie en el comboBox correspondiente
			GeneroObservable generoObservable = new GeneroObservable(
					especieObservable.getEspecie().getGeneroDeEspecie());
			comboGenero.getSelectionModel().select(generoObservable);

			labelEnviador.setText(especie.getEspecie().getRegistroPlanta().getEnviadorDelRegistro().getNombre());
			labelEvaluador.setText(especie.getEspecie().getRegistroPlanta().getEvaluadorDelRegistro().getNombre());
			labelFecha.setText(especie.getEspecie().getRegistroPlanta().getFecha().toString());
			comboEstado.getSelectionModel().select(especie.getEspecie().getRegistroPlanta().getEstado());
			jtfJustificacion.setText(especie.getEspecie().getRegistroPlanta().getJustificacion());

//			Image image = Imagen.obtenerImagen(especie.getEspecie());
//			campoImagen.setImage(image);

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

	@FXML
	public void agregarEspecie() {

		if (jtfNombre.getText().isEmpty()) {
			Utilidades.mostrarMensaje("Advertencia", "Ingresa un nombre para continuar");
		} else {
			if (comboGenero.getValue() == null) {
				Utilidades.mostrarMensaje("Advertencia",
						"Debe seleccionar todas las categorias a las que pertenece la especie");
			} else {
				if (ruta == null) {
					Utilidades.mostrarMensaje("Advertencia", "Seleccione una imagen");
				} else {
					try {

						Registro registro = new Registro();
						registro.setEnviadorDelRegistro(administradorDelegado.getUsuario());
						registro.setEvaluadorDelRegistro((Administrador) administradorDelegado.getUsuario());
						registro.setEstado(Estado.Espera);
						registro.setFecha(new GregorianCalendar().getTime());
						registro.setJustificacion(jtfJustificacion.getText());

						Especie especie = new Especie();
						especie.setNombre(jtfNombre.getText());
						especie.setGeneroDeEspecie(comboGenero.getValue().getGenero());

						// imagen.agregarImagen(especie, ruta);

						especie.setRegistroPlanta(registro);
						registro.setEspecieEnviada(especie);

						especie.setNombre(jtfNombre.getText());

						if (administradorDelegado.registrarEspecie(registro)) {
							Utilidades.mostrarMensaje("Enhorabuena!", "La especie ha sido agregada con exito");
							jtfNombre.setText("");
						} else {
							Utilidades.mostrarMensaje("Error", "La especie no pudo ser agregada");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					actualizarTabla();

				}
			}
		}
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
				Utilidades.mostrarMensaje("Borrar", "La especie ha sido eliminado con exito");
			} else {
				Utilidades.mostrarMensaje("Error", "La especie no pudo ser eliminado");
			}
		} catch (ElementoNoEncontradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actualizarTabla();

	}

	/**
	 * permite editar una especie seleccionado
	 */
	@FXML
	public void editarEspecie() {

		int indice = tablaEspecies.getSelectionModel().getSelectedIndex();
		Especie especie = tablaEspecies.getItems().get(indice).getEspecie();
		try {
			administradorDelegado.modificarEspeciee(especie);
		} catch (ElementoNoEncontradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actualizarTabla();
	}

	@FXML
	public void buscar() {

		String criterio = jtfBuscar.getText();

		// Muestra todas las especies
//			tablaEspecies
//					.setItems((ObservableList<EspecieObservable>) administradorDelegado.listarEspeciesObservables());
//			tablaEspecies.getSelectionModel().clearSelection();

		try {
			ObservableList<EspecieObservable> especies = FXCollections.observableArrayList();
			String categoria = comboCategoriasBusqueda.getValue();

			switch (comboBusqueda.getValue()) {

			case "Buscar por nombre":
				especies = administradorDelegado.listarEspeciesObservablesPorSuNombre(criterio);

				break;

			case "Buscar por ID":
				EspecieObservable especie = new EspecieObservable(
						administradorDelegado.buscarEspecie(Long.parseLong(criterio)));
				especies.add(especie);
				break;

			case "Buscar por genero":
				especies = administradorDelegado
						.listarEspeciesPorGeneroObservables(administradorDelegado.buscarGeneroPorSuNombre(categoria));

				break;

			case "Buscar por familia":
				especies = administradorDelegado
						.listarEspeciesPorFamiliaObservables(administradorDelegado.buscarFamiliaPorSuNombre(categoria));
				break;

			case "Buscar por orden":
				especies = administradorDelegado
						.listarEspeciesPorOrdenObservables(administradorDelegado.buscarOrdenPorSuNombre(categoria));
				break;

			case "Buscar por clase":
				especies = administradorDelegado
						.listarEspeciesPorClaseObservables(administradorDelegado.buscarClasePorSuNombre(categoria));
				break;

			default:
				break;
			}

			if (especies == null) {
				Utilidades.mostrarMensaje("Especies no encontradas", "Intenta con otro parametro o método de busqueda");

			} else {
				tablaEspecies.setItems(especies);
				tablaEspecies.getSelectionModel().clearSelection();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Utilidades.mostrarMensaje("Especies no encontradas", "Hubo un error en la busqueda");
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

	@FXML
	public void cargarClasificacionesBusqueda() {

		// TODO
		// Listar por categorias

		// "Buscar por nombre", "Buscar por ID", "Buscar por genero", "Buscar por
		// familia",
//		"Buscar por orden", "Buscar por clase");

		comboCategoriasBusqueda.getItems().removeAll(comboCategoriasBusqueda.getItems());

		switch (comboBusqueda.getValue()) {
		case "Buscar por genero":

			for (GeneroObservable generoObservable : administradorDelegado.listarGenerosObservables()) {
				comboCategoriasBusqueda.getItems().add(generoObservable.getGenero().getNombre());
			}
			comboCategoriasBusqueda.getSelectionModel().selectFirst();
			break;

		case "Buscar por familia":

			for (FamiliaObservable familiaObservable : administradorDelegado.listarFamiliasObservables()) {
				comboCategoriasBusqueda.getItems().add(familiaObservable.getFamilia().getNombre());
			}
			comboCategoriasBusqueda.getSelectionModel().selectFirst();
			break;

		case "Buscar por orden":

			for (OrdenObservable ordenObservable : administradorDelegado.listarOrdenesObservables()) {
				comboCategoriasBusqueda.getItems().add(ordenObservable.getOrden().getNombre());
			}
			comboCategoriasBusqueda.getSelectionModel().selectFirst();
			break;

		case "Buscar por clase":

			for (ClaseObservable claseObservable : administradorDelegado.listarClasesObservables()) {
				comboCategoriasBusqueda.getItems().add(claseObservable.getClase().getNombre());
			}
			comboCategoriasBusqueda.getSelectionModel().selectFirst();
			break;

		default:
			break;
		}
	}

	public void evaluar() {
		int indice = tablaEspecies.getSelectionModel().getSelectedIndex();
		Especie especie = tablaEspecies.getItems().get(indice).getEspecie();
		if (!jtfJustificacion.getText().isEmpty()) {
			if (especie.getRegistroPlanta().getEstado() == Estado.Espera) {
				if (comboEstado.getValue() != Estado.Espera) {
					if (comboEstado.getValue() == Estado.Aceptado) {
						administradorDelegado.aceptarEspecie(especie);
					} else if (comboEstado.getValue() == Estado.Rechazado) {
						administradorDelegado.rechazarEspecie(especie);
					}
					String justificacion = jtfJustificacion.getText();
					especie.getRegistroPlanta().setJustificacion(justificacion);
					Utilidades.mostrarMensaje("Info", "Se ha modificado el registro exitosamente");
				} else {
					Utilidades.mostrarMensaje("Advertencia", "Seleccione un estado que no sea Espera");
				}
			} else {
				Utilidades.mostrarMensaje("Advertencia", "Solo se pueden evaluar especies en espera");
			}
		} else {
			Utilidades.mostrarMensaje("Advertencia", "Ingrese primero una justificación");
		}
		actualizarTabla();

	}

	@FXML
	private void mostrarFileChooser() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Seleccionar imagen");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif"));
		File file = fileChooser.showOpenDialog(new Stage());
		if (file != null) {
			try {

				ruta = file.getAbsolutePath();
				Image image = new Image(file.toURI().toString());
				campoImagen.setImage(image);
				Utilidades.mostrarMensaje("", "La imagen fue cargada exitosamente");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				Utilidades.mostrarMensaje("", "Error al cargar la imagen");
				e.printStackTrace();

			}
		}

	}
}
