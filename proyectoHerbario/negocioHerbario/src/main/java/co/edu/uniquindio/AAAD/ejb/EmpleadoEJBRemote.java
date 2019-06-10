package co.edu.uniquindio.AAAD.ejb;

import java.util.List;

import javax.ejb.Remote;

import co.edu.uniquindio.AAAD.excepciones.ElementoRepetidoException;
import co.edu.uniquindio.AAAD.persistencia.Clase;
import co.edu.uniquindio.AAAD.persistencia.Empleado;
import co.edu.uniquindio.AAAD.persistencia.Especie;
import co.edu.uniquindio.AAAD.persistencia.Familia;
import co.edu.uniquindio.AAAD.persistencia.Genero;
import co.edu.uniquindio.AAAD.persistencia.Orden;

@Remote
public interface EmpleadoEJBRemote {
	
	String JNDI = "java:global/earHerbario/negocioHerbario/EmpleadoEJB!co.edu.uniquindio.AAAD.ejb.EmpleadoEJBRemote";

	/**
	 * Lista las especies por familia
	 * @return
	 */
	List<Especie> listarEspeciesPorFamilia(Familia familia);

	List<Especie> listarEspeciesAceptadas();

	List<Especie> listarEspeciesRechazados();

	List<Especie> listarEspeciesPorClase(Clase clase);

	List<Especie> listarEspeciesPorOrden(Orden orden);

	List<Especie> listarEspeciesPorGenero(Genero genero);
	


}
