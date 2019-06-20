/**
 * 
 */
package co.edu.uniquindio.AAAD.modelo;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import co.edu.uniquindio.AAAD.ejb.AdminEJBRemote;
import co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote;
import co.edu.uniquindio.AAAD.persistencia.Clase;
import co.edu.uniquindio.AAAD.persistencia.Empleado;
import co.edu.uniquindio.AAAD.persistencia.Especie;
import co.edu.uniquindio.AAAD.persistencia.Familia;
import co.edu.uniquindio.AAAD.persistencia.Genero;
import co.edu.uniquindio.AAAD.persistencia.Orden;
import co.edu.uniquindio.AAAD.persistencia.Persona;
import co.edu.uniquindio.AAAD.persistencia.Recolector;
import co.edu.uniquindio.AAAD.persistencia.Registro;
import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Delegado que permite conectar la capa de negocio con la de presentaciÃ³n
 * 
 * @author EinerZG
 * @version 1.0
 */
public class AdministradorDelegado {

	/**
	 * instancia que representa el ejb remoto de administrador
	 */
	private AdminEJBRemote adminEJB;
	/**
	 * instancia que representa el ejb remoto de negocio
	 */
	private NegocioEJBRemote negocioEJB;
	/**
	 * permite manejar una unica instancia para le manejo de delegados
	 */
	public static AdministradorDelegado administradorDelegado = instancia();
	/**
	 * Usuario que está usando la aplicación
	 */
	private Persona usuario;

	/**
	 * constructor para conectar con la capa de negocio
	 */
	private AdministradorDelegado() {
		try {
			adminEJB = (AdminEJBRemote) new InitialContext().lookup(AdminEJBRemote.JNDI);
			negocioEJB = (NegocioEJBRemote) new InitialContext().lookup(NegocioEJBRemote.JNDI);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permite devolver una unica instancia de delegado
	 * 
	 * @return instancia unica del delegado
	 */
	private static AdministradorDelegado instancia() {

		if (administradorDelegado == null) {
			administradorDelegado = new AdministradorDelegado();
			return administradorDelegado;
		}
		return administradorDelegado;
	}

	/**
	 * Comprueba las credenciales de una persona que inicio sesión
	 * 
	 * @param correo correo de la persona
	 * @param clave  clave de la persona
	 * @return true si el email y la clave son correctas sino false
	 */
	public boolean comprobarCredenciales(String correo, String clave) {

		Persona usuario = negocioEJB.comprobarCredenciales(correo, clave);

		if (usuario != null) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * inserta un nuevo empleado
	 * 
	 * @param empleado, empleado que se va a agregar
	 * @return true si se agregó el empleado sino false
	 * @throws ElementoRepetidoException cuando hay un empleado con cedula o email
	 *                                   repetido
	 */
	public boolean insertarEmpleado(Empleado empleado) throws ElementoRepetidoException {

		return adminEJB.insertarEmpleado(empleado) != null;

	}

	/**
	 * buscar un empleado de acuerdo a su cedula
	 * 
	 * @param cedula cedula del empleado a buscar
	 * @return empleado encontrado sino null
	 */
	public Empleado buscarEmpleado(String cedula) {

		return adminEJB.buscarEmpleado(cedula);

	}

	/**
	 * modifica un empleado
	 * 
	 * @param empleado empleado que se va a modificar
	 * @return true si se modificó el empleado sino false
	 * @throws ElementoNoEncontradoException si no existe el empleado que se va a
	 *                                       modificar
	 * @throws ElementoRepetidoException     si al empleado se le agregó un email
	 *                                       que posee otra persona
	 */
	public boolean modificarEmpleado(Empleado empleado)
			throws ElementoNoEncontradoException, ElementoRepetidoException {

		return adminEJB.modificarEmpleado(empleado) != null;

	}

	/**
	 * elimina el empleado
	 * 
	 * @param empleado empleado que se va a eliminar
	 * @return true si se eliminó el empleado sino false
	 * @throws ElementoNoEncontradoException si no existe el empleado que se va a
	 *                                       modificar
	 */
	public boolean eliminarEmpleado(Empleado empleado) throws ElementoNoEncontradoException {
		return adminEJB.eliminarEmpleado(empleado) != null;
	}

	/**
	 * Lista los empleados
	 * 
	 * @return lista de empleados
	 */
	public List<Empleado> listarEmpleados() {

		return adminEJB.listarEmpleados();

	}

	/**
	 * genera una lista de empleados observables
	 * 
	 * @return todos los empleados obsevables
	 */
	public ObservableList<EmpleadoObservable> listarEmpleadosObservables() {
		List<Empleado> empleados = listarEmpleados();
		ObservableList<EmpleadoObservable> empleadosObservables = FXCollections.observableArrayList();
		for (Empleado empleado : empleados) {
			empleadosObservables.add(new EmpleadoObservable(empleado));
		}
		return empleadosObservables;
	}

	/**
	 * inserta un nuevo recolector
	 * 
	 * @param recolector, recolector que se va a agregar
	 * @return true si se agregó el recolector sino false
	 * @throws ElementoRepetidoException cuando hay un recolector con cedula o email
	 *                                   repetido
	 */
	public boolean insertarRecolector(Recolector recolector) throws ElementoRepetidoException {

		return negocioEJB.insertarRecolector(recolector) != null;

	}

	/**
	 * buscar un recolector de acuerdo a su cedula
	 * 
	 * @param cedula cedula del recolector a buscar
	 * @return recolector encontrado sino null
	 */
	public Recolector buscarRecolector(String cedula) {

		return adminEJB.buscarRecolector(cedula);

	}

	/**
	 * modifica un recolector
	 * 
	 * @param recolector recolector que se va a modificar
	 * @return true si se modificó el recolector sino false
	 * @throws ElementoNoEncontradoException si no existe el recolector que se va a
	 *                                       modificar
	 * @throws ElementoRepetidoException     si al recolector se le agregó un email
	 *                                       que posee otra persona
	 */
	public boolean modificarRecolector(Recolector recolector)
			throws ElementoNoEncontradoException, ElementoRepetidoException {

		return negocioEJB.modificarRecolector(recolector) != null;

	}

	/**
	 * elimina el recolector
	 * 
	 * @param recolector recolector que se va a eliminar
	 * @return true si se eliminó el recolector sino false
	 * @throws ElementoNoEncontradoException si no existe el recolector que se va a
	 *                                       modificar
	 */
	public boolean eliminarRecolector(Recolector recolector) throws ElementoNoEncontradoException {

		return adminEJB.eliminarRecolector(recolector) != null;

	}

	/**
	 * Lista los recolectores
	 * 
	 * @return lista de recolectores
	 */
	public List<Recolector> listarRecolectores() {

		return adminEJB.listarRecolectores();

	}

	/**
	 * genera una lista de recolectores observables
	 * 
	 * @return todos los recolectores obsevables
	 */
	public ObservableList<RecolectorObservable> listarRecolectoresObservables() {
		List<Recolector> recolectores = listarRecolectores();
		ObservableList<RecolectorObservable> recolectoresObservables = FXCollections.observableArrayList();
		for (Recolector recolector : recolectores) {
			recolectoresObservables.add(new RecolectorObservable(recolector));
		}
		return recolectoresObservables;
	}

	/**
	 * inserta una nueva clase
	 * 
	 * @param clase, clase que se va a agregar
	 * @return true si se agregó la clase sino false
	 * @throws ElementoRepetidoException cuando hay una clase con nombre repetido
	 */
	public boolean insertarClase(Clase clase) throws ElementoRepetidoException {

		return adminEJB.insertarClase(clase) != null;

	}

	/**
	 * buscar un clase de acuerdo a su id
	 * 
	 * @param id id de la clase a buscar
	 * @return clase encontrado sino null
	 */
	public Clase buscarClase(String id) {

		return adminEJB.buscarClase(id);

	}

	/**
	 * buscar un clase de acuerdo a su nombre
	 * 
	 * @param nombre nombre de la clase a buscar
	 * @return clase encontrado sino null
	 */
	public Clase buscarClasePorSuNombre(String nombre) {

		return adminEJB.buscarClasePorNombre(nombre);

	}

	/**
	 * modifica una clase
	 * 
	 * @param clase clase que se va a modificar
	 * @return true si se modificó la clase sino false
	 * @throws ElementoNoEncontradoException si no existe la clase que se va a
	 *                                       modificar
	 * @throws ElementoRepetidoException     si la clase se le agregó un nombre que
	 *                                       posee otra
	 */
	public boolean modificarClase(Clase clase) throws ElementoNoEncontradoException, ElementoRepetidoException {

		return adminEJB.modificarClase(clase) != null;

	}

	/**
	 * elimina el clase
	 * 
	 * @param clase clase que se va a eliminar
	 * @return true si se eliminó el clase sino false
	 * @throws ElementoNoEncontradoException si no existe el clase que se va a
	 *                                       modificar
	 */
	public boolean eliminarClase(Clase clase) throws ElementoNoEncontradoException {
		return adminEJB.eliminarClase(clase) != null;
	}

	/**
	 * Lista los clases
	 * 
	 * @return lista de clases
	 */
	public List<Clase> listarClases() {

		return adminEJB.listarClases();

	}

	/**
	 * genera una lista de clases observables
	 * 
	 * @return todos los clases obsevables
	 */
	public ObservableList<ClaseObservable> listarClasesObservables() {
		List<Clase> clases = listarClases();
		ObservableList<ClaseObservable> clasesObservables = FXCollections.observableArrayList();
		for (Clase clase : clases) {
			clasesObservables.add(new ClaseObservable(clase));
		}
		return clasesObservables;
	}

	/**
	 * inserta una nueva orden
	 * 
	 * @param orden, orden que se va a agregar
	 * @return true si se agregó la orden sino false
	 * @throws ElementoRepetidoException cuando hay una orden con nombre repetido
	 */
	public boolean insertarOrden(Orden orden) throws ElementoRepetidoException {

		return adminEJB.insertarOrden(orden) != null;

	}

	/**
	 * buscar un orden de acuerdo a su id
	 * 
	 * @param id id de la orden a buscar
	 * @return orden encontrado sino null
	 */
	public Orden buscarOrden(String id) {

		return adminEJB.buscarOrden(id);

	}

	/**
	 * buscar un orden de acuerdo a su nombre
	 * 
	 * @param nombre nombre de la orden a buscar
	 * @return orden encontrado sino null
	 */
	public Orden buscarOrdenPorSuNombre(String nombre) {

		return adminEJB.buscarOrdenPorNombre(nombre);

	}

	/**
	 * modifica una orden
	 * 
	 * @param orden orden que se va a modificar
	 * @return true si se modificó la orden sino false
	 * @throws ElementoNoEncontradoException si no existe la orden que se va a
	 *                                       modificar
	 * @throws ElementoRepetidoException     si la orden se le agregó un nombre que
	 *                                       posee otra
	 */
	public boolean modificarOrden(Orden orden) throws ElementoNoEncontradoException, ElementoRepetidoException {

		return adminEJB.modificarOrden(orden) != null;

	}

	/**
	 * elimina el orden
	 * 
	 * @param orden orden que se va a eliminar
	 * @return true si se eliminó el orden sino false
	 * @throws ElementoNoEncontradoException si no existe el orden que se va a
	 *                                       modificar
	 */
	public boolean eliminarOrden(Orden orden) throws ElementoNoEncontradoException {

		return adminEJB.eliminarOrden(orden) != null;

	}

	/**
	 * Lista los ordenes
	 * 
	 * @return lista de ordenes
	 */
	public List<Orden> listarOrdenes() {

		return adminEJB.listarOrdenes();

	}

	/**
	 * genera una lista de ordenes observables
	 * 
	 * @return todos los ordenes obsevables
	 */
	public ObservableList<OrdenObservable> listarOrdenesObservables() {
		List<Orden> ordens = listarOrdenes();
		ObservableList<OrdenObservable> ordensObservables = FXCollections.observableArrayList();
		for (Orden orden : ordens) {
			ordensObservables.add(new OrdenObservable(orden));
		}
		return ordensObservables;
	}

	/**
	 * inserta una nueva familia
	 * 
	 * @param familia, familia que se va a agregar
	 * @return true si se agregó la familia sino false
	 * @throws ElementoRepetidoException cuando hay una familia con nombre repetido
	 */
	public boolean insertarFamilia(Familia familia) throws ElementoRepetidoException {

		return adminEJB.insertarFamilia(familia) != null;

	}

	/**
	 * buscar un familia de acuerdo a su id
	 * 
	 * @param id id de la familia a buscar
	 * @return familia encontrado sino null
	 */
	public Familia buscarFamilia(String id) {

		return adminEJB.buscarFamilia(id);

	}

	/**
	 * buscar un familia de acuerdo a su nombre
	 * 
	 * @param nombre nombre de la familia a buscar
	 * @return familia encontrado sino null
	 */
	public Familia buscarFamiliaPorSuNombre(String nombre) {

		return adminEJB.buscarFamiliaPorNombre(nombre);

	}

	/**
	 * modifica una familia
	 * 
	 * @param familia familia que se va a modificar
	 * @return true si se modificó la familia sino false
	 * @throws ElementoNoEncontradoException si no existe la familia que se va a
	 *                                       modificar
	 * @throws ElementoRepetidoException     si la familia se le agregó un nombre
	 *                                       que posee otra
	 */
	public boolean modificarFamilia(Familia familia) throws ElementoNoEncontradoException, ElementoRepetidoException {

		return adminEJB.modificarFamilia(familia) != null;

	}

	/**
	 * elimina el familia
	 * 
	 * @param familia familia que se va a eliminar
	 * @return true si se eliminó el familia sino false
	 * @throws ElementoNoEncontradoException si no existe el familia que se va a
	 *                                       modificar
	 */
	public boolean eliminarFamilia(Familia familia) throws ElementoNoEncontradoException {

		return adminEJB.eliminarFamilia(familia) != null;

	}

	/**
	 * Lista los familias
	 * 
	 * @return lista de familias
	 */
	public List<Familia> listarFamilias() {

		return adminEJB.listarFamilias();

	}

	/**
	 * genera una lista de familias observables
	 * 
	 * @return todos los familias obsevables
	 */
	public ObservableList<FamiliaObservable> listarFamiliasObservables() {
		List<Familia> familias = listarFamilias();
		ObservableList<FamiliaObservable> familiasObservables = FXCollections.observableArrayList();
		for (Familia familia : familias) {
			familiasObservables.add(new FamiliaObservable(familia));
		}
		return familiasObservables;
	}

	/**
	 * inserta una nueva genero
	 * 
	 * @param genero, genero que se va a agregar
	 * @return true si se agregó la genero sino false
	 * @throws ElementoRepetidoException cuando hay una genero con nombre repetido
	 */
	public boolean insertarGenero(Genero genero) throws ElementoRepetidoException {

		return adminEJB.insertarGenero(genero) != null;

	}

	/**
	 * buscar un genero de acuerdo a su id
	 * 
	 * @param id id de la genero a buscar
	 * @return genero encontrado sino null
	 */
	public Genero buscarGenero(String id) {

		return adminEJB.buscarGenero(id);

	}

	/**
	 * buscar un genero de acuerdo a su nombre
	 * 
	 * @param nombre nombre de la genero a buscar
	 * @return genero encontrado sino null
	 */
	public Genero buscarGeneroPorSuNombre(String nombre) {

		return adminEJB.buscarGeneroPorNombre(nombre);

	}

	/**
	 * modifica una genero
	 * 
	 * @param genero genero que se va a modificar
	 * @return true si se modificó la genero sino false
	 * @throws ElementoNoEncontradoException si no existe la genero que se va a
	 *                                       modificar
	 * @throws ElementoRepetidoException     si la genero se le agregó un nombre que
	 *                                       posee otra
	 */
	public boolean modificarGenero(Genero genero) throws ElementoNoEncontradoException, ElementoRepetidoException {

		return adminEJB.modificarGenero(genero) != null;

	}

	/**
	 * elimina el genero
	 * 
	 * @param genero genero que se va a eliminar
	 * @return true si se eliminó el genero sino false
	 * @throws ElementoNoEncontradoException si no existe el genero que se va a
	 *                                       modificar
	 */
	public boolean eliminarGenero(Genero genero) throws ElementoNoEncontradoException {

		return adminEJB.eliminarGenero(genero) != null;

	}

	/**
	 * Lista los generos
	 * 
	 * @return lista de generos
	 */
	public List<Genero> listarGeneros() {

		return adminEJB.listarGeneros();

	}

	/**
	 * genera una lista de generos observables
	 * 
	 * @return todos los generos obsevables
	 */
	public ObservableList<GeneroObservable> listarGenerosObservables() {
		List<Genero> generos = listarGeneros();
		ObservableList<GeneroObservable> generosObservables = FXCollections.observableArrayList();
		for (Genero genero : generos) {
			generosObservables.add(new GeneroObservable(genero));
		}
		return generosObservables;
	}

	/**
	 * registra una especie vegetal por medio de un registro
	 * 
	 * @param registro registro que contiene a la especie
	 * @return true si registró la especie, sino false
	 * @throws ElementoRepetidoException si hay un id repetido
	 */
	public boolean registrarEspecie(Registro registro) throws ElementoRepetidoException {
		return negocioEJB.registrarEspecie(registro) != null;
	}
	/**
	 * Acepta una especie
	 * @param especie especie que se va a aceptar
	 * @return true si la acepto, sino false
	 */
	public boolean aceptarEspecie(Especie especie) {

		return adminEJB.aceptarEspecie(especie) != null;
	}
	/**
	 * Rechaza una especie
	 * @param especie especie que se va a rechazar
	 * @return true si la rechazó, sino false
	 */
	public boolean rechazarEspecie(Especie especie) {

		return adminEJB.rechazarEspecie(especie) != null;
	}
	/**
	 * buscar una especie de acuerdo a un id
	 * @param id id de la especie que se va a buscar
	 * @return especie encontrada
	 */
	public Especie buscarEspecie(String id) {
		return negocioEJB.buscarEspecie(id);
	}
	/**
	 * busca una especie por su nombre
	 * @param nombre nombre de la especie que se va a buscar
	 * @return lista de especies con ese nombre
	 */
	public List<Especie> buscarEspeciePorSuNombre(String nombre) {
		return negocioEJB.buscarEspeciePorSuNombre(nombre);
	}

	/**
	 * Lista los especies
	 * 
	 * @return lista de especies
	 */
	public List<Especie> listarEspecies() {

		return negocioEJB.listarEspecies();

	}

	/**
	 * genera una lista de especies observables
	 * 
	 * @return todos los especies obsevables
	 */
	public ObservableList<EspecieObservable> listarEspeciesObservables() {
		List<Especie> especies = listarEspecies();
		ObservableList<EspecieObservable> especiesObservables = FXCollections.observableArrayList();
		for (Especie especie : especies) {
			especiesObservables.add(new EspecieObservable(especie));
		}
		return especiesObservables;
	}

	/**
	 * Lista los especies en espera
	 * 
	 * @return lista de especies en espera
	 */
	public List<Especie> listarEspeciesEnEspera() {

		return adminEJB.listarEspeciesEnEspera();

	}

	/**
	 * genera una lista de especies en espera observables
	 * 
	 * @return todos los especies en espera obsevables
	 */
	public ObservableList<EspecieObservable> listarEspeciesEnEsperaObservables() {
		List<Especie> especies = listarEspeciesEnEspera();
		ObservableList<EspecieObservable> especiesObservables = FXCollections.observableArrayList();
		for (Especie especie : especies) {
			especiesObservables.add(new EspecieObservable(especie));
		}
		return especiesObservables;
	}

	/**
	 * Lista los especies aceptadas
	 * 
	 * @return lista de especies aceptadas
	 */
	public List<Especie> listarEspeciesAceptadas() {

		return negocioEJB.listarEspeciesAceptadas();

	}

	/**
	 * genera una lista de especies en espera observables
	 * 
	 * @return todos los especies obsevables
	 */
	public ObservableList<EspecieObservable> listarEspeciesAceptadasObservables() {
		List<Especie> especies = listarEspeciesAceptadas();
		ObservableList<EspecieObservable> especiesObservables = FXCollections.observableArrayList();
		for (Especie especie : especies) {
			especiesObservables.add(new EspecieObservable(especie));
		}
		return especiesObservables;
	}

	/**
	 * Lista los especies rechazadas
	 * 
	 * @return lista de especies rechazadas
	 */
	public List<Especie> listarEspeciesRechazadas() {

		return negocioEJB.listarEspeciesRechazados();

	}

	/**
	 * genera una lista de especies rechazadas
	 * 
	 * @return todos los especies rechazadas
	 */
	public ObservableList<EspecieObservable> listarEspeciesRechazadasObservables() {
		List<Especie> especies = listarEspeciesRechazadas();
		ObservableList<EspecieObservable> especiesObservables = FXCollections.observableArrayList();
		for (Especie especie : especies) {
			especiesObservables.add(new EspecieObservable(especie));
		}
		return especiesObservables;
	}

	/**
	 * Lista los especies por orden
	 * 
	 * @return lista de especies por orden
	 */
	public List<Especie> listarEspeciesPorOrden(Orden orden) {

		return negocioEJB.listarEspeciesPorOrden(orden);

	}

	/**
	 * Genera una lista de especies por orden observables
	 * 
	 * @param orden orden de la especie
	 * @return lista de especies de acuerdo a un orden
	 */
	public ObservableList<EspecieObservable> listarEspeciesPorOrdenObservables(Orden orden) {
		List<Especie> especies = listarEspecies();
		ObservableList<EspecieObservable> especiesObservables = FXCollections.observableArrayList();
		for (Especie especie : especies) {
			especiesObservables.add(new EspecieObservable(especie));
		}
		return especiesObservables;
	}

	/**
	 * Lista los especies por clase
	 * 
	 * @return lista de especies por clase
	 */
	public List<Especie> listarEspeciesPorClase(Clase clase) {

		return negocioEJB.listarEspeciesPorClase(clase);

	}

	/**
	 * Genera una lista de especies por clase observables
	 * 
	 * @param clase clase de la especie
	 * @return lista de especies de acuerdo a un clase
	 */
	public ObservableList<EspecieObservable> listarEspeciesPorClaseObservables(Clase clase) {
		List<Especie> especies = listarEspecies();
		ObservableList<EspecieObservable> especiesObservables = FXCollections.observableArrayList();
		for (Especie especie : especies) {
			especiesObservables.add(new EspecieObservable(especie));
		}
		return especiesObservables;
	}

	/**
	 * Lista los especies por familia
	 * 
	 * @return lista de especies por familia
	 */
	public List<Especie> listarEspeciesPorFamilia(Familia familia) {

		return negocioEJB.listarEspeciesPorFamilia(familia);

	}

	/**
	 * Genera una lista de especies por familia observables
	 * 
	 * @param familia familia de la especie
	 * @return lista de especies de acuerdo a un familia
	 */
	public ObservableList<EspecieObservable> listarEspeciesPorFamiliaObservables(Familia familia) {
		List<Especie> especies = listarEspecies();
		ObservableList<EspecieObservable> especiesObservables = FXCollections.observableArrayList();
		for (Especie especie : especies) {
			especiesObservables.add(new EspecieObservable(especie));
		}
		return especiesObservables;
	}

	/**
	 * Lista los especies por genero
	 * 
	 * @return lista de especies por genero
	 */
	public List<Especie> listarEspeciesPorGenero(Genero genero) {

		return negocioEJB.listarEspeciesPorGenero(genero);

	}

	/**
	 * Genera una lista de especies por genero observables
	 * 
	 * @param genero genero de la especie
	 * @return lista de especies de acuerdo a un genero
	 */
	public ObservableList<EspecieObservable> listarEspeciesPorGeneroObservables(Genero genero) {
		List<Especie> especies = listarEspecies();
		ObservableList<EspecieObservable> especiesObservables = FXCollections.observableArrayList();
		for (Especie especie : especies) {
			especiesObservables.add(new EspecieObservable(especie));
		}
		return especiesObservables;
	}

	public Persona buscarPersonaPorEmail(String correo) {

		return adminEJB.buscarPersonaPorEmail(correo);

	}

	/**
	 * @return the usuario
	 */
	public Persona getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Persona usuario) {
		this.usuario = usuario;
	}

}
