package co.edu.uniquindio.AAAD.util;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import co.edu.uniquindio.AAAD.ejb.AdminEJB;
import co.edu.uniquindio.AAAD.persistencia.Genero;

@FacesConfig(version = Version.JSF_2_3)
@Named("generoConverter")
@ApplicationScoped
public class GeneroConverter implements Converter<Genero> {

	@EJB
	private AdminEJB AdminEJB;

	@Override
	public Genero getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.trim().equals("")) {
			Long idGenero = Long.parseLong(value);
			return AdminEJB.buscarGenero(idGenero);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Genero value) {

		return (value != null) ? value.getId().toString() : "";
	}

}
