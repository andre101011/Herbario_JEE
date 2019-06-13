package co.edu.uniquindio.AAAD.ejb;

import java.util.List;

import javax.ejb.Remote;

import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.persistencia.Clase;
import co.edu.uniquindio.AAAD.persistencia.Empleado;
import co.edu.uniquindio.AAAD.persistencia.Especie;
import co.edu.uniquindio.AAAD.persistencia.Familia;
import co.edu.uniquindio.AAAD.persistencia.Genero;
import co.edu.uniquindio.AAAD.persistencia.Orden;
import co.edu.uniquindio.AAAD.persistencia.Recolector;


@Remote
public interface AdminEJBRemote {
	
	String JNDI = "java:global/earHerbario/negocioHerbario/AdminEJB!co.edu.uniquindio.AAAD.ejb.AdminEJBRemote";
	
	/**
	 * permite agregar un empleado
	 * @param empleado empleado  a agregar
	 * @return empleado agregado o null
	 * @throws ElementoRepetidoException cuando ya se tienen un empleado con cedula o email repetido 
	 */
	Empleado insertarEmpleado(Empleado empleado) throws  ElementoRepetidoException;
	
	/**
	 * Permite modificar un empleado
	 * @param empleado empleado a modificar
	 * @return empleado modificada o null
	 * @throws ElementoNoEncontradoException cuando no se encuentra el empleado con el id especificado
	 * @throws ElementoRepetidoException cuando se quiere una cedula o email repetido
	 */
	Empleado modificarEmpleado(Empleado empleado) throws ElementoNoEncontradoException, ElementoRepetidoException;

	/**
	 * 
	 * @param id id de la empleado a buscar
	 * @return empleado buscada, null si no la encontró
	 */
	Empleado buscarEmpleado(String id);

	/**
	 * 
	 * @param empleado empleado que se quiere eliminar
	 * @return empleado que se eliminó o null
	 * @throws ElementoNoEncontradoException cuando no se encuentra el empleado en la base de datos 
	 */
	Empleado eliminarEmpleado(Empleado empleado) throws ElementoNoEncontradoException;

	/**
	 * 
	 * @return la lista de todas las empleados
	 */
	List<Empleado> listarEmpleados();

	/**
	 * 
	 * @param id id de la recolector a buscar
	 * @return recolector buscada, null si no la encontró
	 */
	Recolector buscarRecolector(String id);

	/**
	 * 
	 * @param recolector recolector que se quiere eliminar
	 * @return recolector que se eliminó o null
	 * @throws ElementoNoEncontradoException cuando no se encuentra el recolector en la base de datos 
	 */
	Recolector eliminarRecolector(Recolector recolector) throws ElementoNoEncontradoException;

	/**
	 * 
	 * @return la lista de todas las recolectores
	 */
	List<Recolector> listarRecolectores();
	
	/**
	 * Permite agregar una clase 
	 * @param clase clase a agregar
	 * @return clase agregada o null
	 * @throws ElementoRepetidoException cuando ya se tiene un nombre repetido
	 */
	Clase insertarClase(Clase clase) throws ElementoRepetidoException;
	
	/**
	 * Permite modificar una clase
	 * @param clase clase a modificar
	 * @return clase modificada o null
	 * @throws ElementoNoEncontradoException cuando no se encuentra la clase con el id especificado
	 * @throws ElementoRepetidoException cuando se quiere ingresar un nombre repetido
	 */
	Clase modificarClase(Clase clase) throws ElementoNoEncontradoException, ElementoRepetidoException;

	/**
	 * 
	 * @param id id de la clase a buscar
	 * @return clase buscada, null si no la encontró
	 */
	Clase buscarClase(String id);

	/**
	 * 
	 * @param clase clase que se quiere eliminar
	 * @return clase que se eliminó o null
	 * @throws ElementoNoEncontradoException cuando no se encuentra la clase en la base de datos 
	 */
	Clase eliminarClase(Clase clase) throws ElementoNoEncontradoException;

	/**
	 * 
	 * @return la lista de todas las clases
	 */
	List<Clase> listarClases();
	
	/**
	 * Permite agregar un orden 
	 * @param Orden orden a agregar
	 * @return orden agregado o null
	 * @throws ElementoRepetidoException cuando ya se tiene un nombre repetido
	 */
	Orden insertarOrden(Orden orden) throws ElementoRepetidoException;
	
	/**
	 * Permite modificar un orden
	 * @param orden orden a modificar
	 * @return orden modificado o null
	 * @throws ElementoNoEncontradoException cuando no se encuentra el orden con el id especificado
	 * @throws ElementoRepetidoException cuando se quiere ingresar un nombre repetido
	 */
	Orden modificarOrden(Orden orden) throws ElementoNoEncontradoException, ElementoRepetidoException;

	/**
	 * 
	 * @param id id del orden a buscar
	 * @return orden buscado, null si no lo encontró
	 */
	Orden buscarOrden(String id);

	/**
	 * 
	 * @param orden orden que se quiere eliminar
	 * @return orden que se eliminó o null
	 * @throws ElementoNoEncontradoException cuando no se encuentra el orden en la base de datos 
	 */
	Orden eliminarOrden(Orden orden) throws ElementoNoEncontradoException;

	/**
	 * 
	 * @return la lista de todas los ordenes
	 */
	List<Orden> listarOrdenes();

	/**
	 * Permite agregar un genero 
	 * @param genero ge a agregar
	 * @return genero agregado o null
	 * @throws ElementoRepetidoException cuando ya se tiene un nombre repetido
	 */
	Genero insertarGenero(Genero genero) throws ElementoRepetidoException;
	
	/**
	 * Permite modificar un genero
	 * @param genero genero a modificar
	 * @return genero modificado o null
	 * @throws ElementoNoEncontradoException cuando no se encuentra el genero con el id especificado
	 * @throws ElementoRepetidoException cuando el nombre está repetido

	 */
	Genero modificarGenero(Genero genero) throws ElementoNoEncontradoException, ElementoRepetidoException;

	/**
	 * 
	 * @param id id del genero a buscar
	 * @return genero buscado, null si no lo encontró
	 */
	Genero buscarGenero(String id);

	/**
	 * 
	 * @param genero genero que se quiere eliminar
	 * @return genero que se eliminó o null
	 * @throws ElementoNoEncontradoException cuando no se encuentra el genero en la base de datos 
	 */
	Genero eliminarGenero(Genero genero) throws ElementoNoEncontradoException;

	/**
	 * Permite listar los generos
	 * @return la lista de todas los generos
	 */
	List<Genero> listarGeneros();

	/**
	 * Permite agregar un familia 
	 * @param familia familia a agregar
	 * @return familia agregado o null
	 * @throws ElementoRepetidoException cuando ya se tiene un nombre repetido
	 */
	Familia insertarFamilia(Familia familia) throws ElementoRepetidoException;
	
	/**
	 * Permite modificar un familia
	 * @param familia familia a modificar
	 * @return familia modificado o null
	 * @throws ElementoNoEncontradoException cuando no se encuentra el familia con el id especificado
	 * @throws ElementoRepetidoException Cuando hay un nombre repetido
	 * @throws ElementoNuloException cuando el familia no tiene nombre
	 */
  Familia modificarFamilia(Familia familia) throws ElementoNoEncontradoException, ElementoRepetidoException;

	/**
	 * Permite buscar las familias
	 * @param id id del familia a buscar
	 * @return familia buscado, null si no lo encontró
	 */
	Familia buscarFamilia(String id);

	/**
	 * 
	 * @param familia familia que se quiere eliminar
	 * @return familia que se eliminó o null
	 * @throws ElementoNoEncontradoException cuando no se encuentra el familia en la base de datos 
	 */
	Familia eliminarFamilia(Familia familia) throws ElementoNoEncontradoException;

	/**
	 * Permite listar las familias
	 * @return la lista de todas los familias
	 */
	List<Familia> listarFamilias();

	/**
	 * Permite pedir la lista de especies en espera
	 * @return lista de especies con registro en espera, null si no hay
	 */
	List<Especie> listarEspeciesEnEspera();

	/**
	 * Permite marcar el registro de una especie como aceptado
	 * @param especie especie que se va a marcar como aceptada
	 * @return especie aceptada, null si hubo alguna excepción
	 */
	Especie aceptarEspecie(Especie especie);

	/**
	 * Permite marcar el registro de una especie como rechazado
	 * @param especie especie que se va a marcar como rechazada
	 * @return especie rechazada, null si hubo alguna excepcion
	 */
	Especie rechazarEspecie(Especie especie);

	/**
	 * Busca una clase de acuerdo a su nombre
	 * @param nombre, de la clase a buscar
	 * @return clase encontrada sino null
	 */
	Clase buscarClasePorNombre(String nombre);
	/**
	 * Busca una orden de acuerdo a su nombre
	 * @param nombre, de la orden a buscar
	 * @return orden encontrada sino null
	 */
	Orden buscarOrdenPorNombre(String nombre);
	/**
	 * Busca una familia de acuerdo a su nombre
	 * @param nombre, de la familia a buscar
	 * @return familia encontrada sino null
	 */
	Familia buscarFamiliaPorNombre(String nombre);
	/**
	 * Busca una genero de acuerdo a su nombre
	 * @param nombre, de la genero a buscar
	 * @return genero encontrada sino null
	 */
	Genero buscarGeneroPorNombre(String nombre);

	
	
	
	

}
