package co.edu.uniquindio.AAAD.ejb;

import java.util.List;

import javax.ejb.Remote;

import co.edu.uniquindio.AAAD.excepciones.ElementoNoEncontradoException;
import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.persistencia.Clase;
import co.edu.uniquindio.AAAD.persistencia.Especie;
import co.edu.uniquindio.AAAD.persistencia.Familia;
import co.edu.uniquindio.AAAD.persistencia.Genero;
import co.edu.uniquindio.AAAD.persistencia.Orden;
import co.edu.uniquindio.AAAD.persistencia.Recolector;
import co.edu.uniquindio.AAAD.persistencia.Registro;

@Remote
public interface NegocioEJBRemote {
	
	String JNDI = "java:global/earHerbario/negocioHerbario/NegocioEJB!co.edu.uniquindio.AAAD.ejb.NegocioEJBRemote";

	/**
	 * Busca una especie
	 * @param id, de la especie a buscar
	 * @return Especie encontrada, null si no la encontró
	 */
	Especie buscarEspecie(String id);	
	/**
	 * permite agregar un recolector
	 * @param recolector recolector  a agregar
	 * @return recolector agregado o null
	 * @throws ElementoRepetidoException cuando ya se tienen un recolector con cedula o email repetido 
	 */
	Recolector insertarRecolector(Recolector recolector) throws  ElementoRepetidoException;
	
	/**
	 * Permite modificar un recolector
	 * @param recolector recolector a modificar
	 * @return recolector modificada o null
	 * @throws ElementoNoEncontradoException cuando no se encuentra el recolector con el id especificado
	 * @throws ElementoRepetidoException cuando se quiere una cedula o email repetido
	 */
	Recolector modificarRecolector(Recolector recolector) throws ElementoNoEncontradoException, ElementoRepetidoException;

	/**
	 * Permite registrar una especie
	 * @param registro registro con la especie a agregar
	 * @return Especie registrada
	 * @throws ElementoRepetidoException si ya se encuentra una especie o  registro con  el id
	 */
	Especie registrarEspecie(Registro registro) throws ElementoRepetidoException;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * lista las especies de acuerdo a una familia
	 * @param familia, familia a la que pertenecen las especies buscadas
	 * @return lista con las especies pertenecientes a una familia
	 */
	List<Especie> listarEspeciesPorFamilia(Familia familia);
	/**
	 * lista las especies aceptadas
	 * @return lista con las especies aceptadas
	 */
	List<Especie> listarEspeciesAceptadas();
	/**
	 * lista las especies rechazadas
	 * @return lista con las especies rechazadas
	 */
	List<Especie> listarEspeciesRechazados();
	/**
	 * lista las especies de acuerdo a una clase
	 * @param clase, clase a la que pertenecen las especies buscadas
	 * @return lista con las especies pertenecientes a una clase
	 */
	List<Especie> listarEspeciesPorClase(Clase clase);
	/**
	 * lista las especies de acuerdo a un orden
	 * @param orden, orden al que pertenecen las especies buscadas
	 * @return lista con las especies pertenecientes a un orden
	 */
	List<Especie> listarEspeciesPorOrden(Orden orden);
	/**
	 * lista las especies de acuerdo a un genero
	 * @param genero, genero al que pertenecen las especies buscadas
	 * @return lista con las especies pertenecientes a un genero
	 */
	List<Especie> listarEspeciesPorGenero(Genero genero);
	


}
