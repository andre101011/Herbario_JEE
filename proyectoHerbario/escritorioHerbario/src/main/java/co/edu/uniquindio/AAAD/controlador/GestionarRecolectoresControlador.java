/**
 * 
 */
package co.edu.uniquindio.AAAD.controlador;

import co.edu.uniquindio.AAAD.persistencia.Recolector;

import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.modelo.AdministradorDelegado;
import co.edu.uniquindio.AAAD.modelo.RecolectorObservable;
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
public class GestionarRecolectoresControlador {

	/**
	 * table donde se almacena la informacion de los recolectores
	 */
	@FXML
	private TableView<RecolectorObservable> tablaRecolectores;
	/**
	 * hace referencia a la columna con las cedulas
	 */
	@FXML
	private TableColumn<RecolectorObservable, String> columnaCedula;
	/**
	 * hace referencia a la columna de los nombres de los recolectores
	 */
	@FXML
	private TableColumn<RecolectorObservable, String> columnaNombre;
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
	 * para almacenar recolectores observables
	 */
	private ObservableList<RecolectorObservable> recolectoresObservables;
	/**
	 * conexion con capa de negocio
	 */
	private AdministradorDelegado administradorDelegado;

	private RecolectorObservable recolectorObservable;
	private Stage escenario;

	public GestionarRecolectoresControlador() {
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		columnaCedula.setCellValueFactory(recolectorCelda -> recolectorCelda.getValue().getCedula());
		columnaNombre.setCellValueFactory(recolectorCelda -> recolectorCelda.getValue().getNombre());

		mostrarDetalleRecolector(null);

		tablaRecolectores.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetalleRecolector(newValue));

		administradorDelegado = AdministradorDelegado.administradorDelegado;
		recolectoresObservables = FXCollections.observableArrayList();

		actualizarTabla();
	}

	/**
	 * permite obtener una instancia del escenario general
	 * 
	 * @param escenarioInicial
	 */
	public void setEscenarioInicial(ManejadorEscenarios escenarioInicial) {
		this.manejadorEscenarios = escenarioInicial;
		tablaRecolectores.setItems(getRecolectoresObservables());
	}

	/**
	 * permite mostrar la informacion del recolector seleccionado
	 * 
	 * @param recolector recolector al que se le desea mostrar el detalle
	 */
	public void mostrarDetalleRecolector(RecolectorObservable recolector) {

		if (recolector != null) {
			recolectorObservable = recolector;

			jtfNombre.setText(recolector.getNombre().getValue());
			jtfCedula.setText(recolector.getCedula().getValue());
			jtfEmail.setText(recolector.getEmail().getValue());
			jtfClave.setText(recolector.getClave().getValue());

		} else {
			jtfNombre.setText("");
			jtfEmail.setText("");
			jtfCedula.setText("");
			jtfClave.setText("");
		}

	}

	/**
	 * permite mostrar la ventana de agregar recolector
	 */
	@FXML
	public void agregarRecolector() {

		if (jtfNombre.getText().isEmpty() || jtfCedula.getText().isEmpty() || jtfEmail.getText().isEmpty()
				|| jtfClave.getText().isEmpty()) {
			Utilidades.mostrarMensaje("Advertencia", "Llena todos los campos para continuar");
		} else {

			Recolector recolector = new Recolector();
			recolector.setCedula(jtfCedula.getText());
			recolector.setNombre(jtfNombre.getText());
			recolector.setClave(jtfClave.getText());
			recolector.setEmail(jtfEmail.getText());

			try {
				if (administradorDelegado.insertarRecolector(recolector)) {
					agregarALista(recolector);
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
		int indice = tablaRecolectores.getSelectionModel().getSelectedIndex();
		tablaRecolectores
				.setItems((ObservableList<RecolectorObservable>) administradorDelegado.listarRecolectoresObservables());
		tablaRecolectores.getSelectionModel().clearSelection();
		tablaRecolectores.getSelectionModel().select(indice);
	}

	/**
	 * permite eliminar un recolector seleccionado
	 */
	@FXML
	public void eliminarRecolector() {

		int indice = tablaRecolectores.getSelectionModel().getSelectedIndex();

		Recolector recolector = tablaRecolectores.getItems().get(indice).getRecolector();

		try {
			System.out.println(recolector.getCedula());
			if (administradorDelegado
					.eliminarRecolector(administradorDelegado.buscarRecolector(recolector.getCedula()))) {
				tablaRecolectores.getItems().remove(indice);
				Utilidades.mostrarMensaje("Borrar", "El recolector ha sido eliminado con exito");
			} else {
				Utilidades.mostrarMensaje("Error", "El recolector no pudo ser eliminado");
			}
		} catch (ElementoNoEncontradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actualizarTabla();
	}

	/**
	 * permite eliminar un recolector seleccionado
	 */
	@FXML
	public void editarRecolector() {

		int indice = tablaRecolectores.getSelectionModel().getSelectedIndex();

		if (indice != -1) {
			Recolector recolector = tablaRecolectores.getItems().get(indice).getRecolector();

			if (jtfNombre.getText().equals(recolector.getNombre()) && jtfClave.getText().equals(recolector.getClave())
					&& jtfEmail.getText().equals(recolector.getEmail())
					&& jtfCedula.getText().equals(recolector.getCedula())) {
				Utilidades.mostrarMensaje("Info", "Cambia algun atributo del recolector");
			} else if (!jtfCedula.getText().equals(recolector.getCedula())) {
				Utilidades.mostrarMensaje("Info", "No puedes modificar la cedula de un recolector");
			} else {
				recolector.setNombre(jtfNombre.getText());
				recolector.setClave(jtfClave.getText());
				recolector.setEmail(jtfEmail.getText());

				try {
					if (administradorDelegado.modificarRecolector(recolector)) {
						tablaRecolectores.getItems().remove(indice);
						Utilidades.mostrarMensaje("Borrar", "El recolector ha sido modificado con exito");
						jtfNombre.setText("");
						jtfEmail.setText("");
						jtfCedula.setText("");
						jtfClave.setText("");

					} else {
						Utilidades.mostrarMensaje("Error", "El recolector no pudo ser modificado");
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
				RecolectorObservable recolectorObservable = null;
				Recolector recolector = administradorDelegado.buscarRecolector(cedula);

				if (recolector == null) {
					Utilidades.mostrarMensaje("", "No se encontro");

				} else {
					recolectorObservable = new RecolectorObservable(recolector);
					ObservableList<RecolectorObservable> lista = FXCollections.observableArrayList();
					lista.add(recolectorObservable);
					tablaRecolectores.setItems(lista);
					tablaRecolectores.getSelectionModel().clearSelection();
				}
			} catch (Exception e) {
			}
		} else {
			tablaRecolectores.setItems(
					(ObservableList<RecolectorObservable>) administradorDelegado.listarRecolectoresObservables());
			tablaRecolectores.getSelectionModel().clearSelection();
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
	 * @return recolectores observables
	 */
	public ObservableList<RecolectorObservable> getRecolectoresObservables() {
		return recolectoresObservables;
	}

	/**
	 * permite agrega una liente a la lista observable
	 * 
	 * @param recolector
	 */
	public void agregarALista(Recolector recolector) {
		recolectoresObservables.add(new RecolectorObservable(recolector));
	}



}
