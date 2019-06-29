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
import co.edu.uniquindio.AAAD.persistencia.Familia;

@FacesConfig(version=Version.JSF_2_3)
@Named("familiaConverter")
@ApplicationScoped
public class FamiliaConverter implements Converter<Familia> {

	@EJB
	private AdminEJB AdminEJB;
	
	
	@Override
	public Familia getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null &&!value.trim().equals("")) {
			Long idFamilia=Long.parseLong(value);
			return AdminEJB.buscarFamilia(idFamilia);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Familia value) {
		
		return (value != null)?value.getId().toString():"";
	}
	
	

}
