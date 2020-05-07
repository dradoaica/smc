package ro.smc.frontend.converters;

import ro.smc.frontend.entities.general.EnvDatabase;
import ro.smc.frontend.facades.general.EnvDatabaseFacade;
import ro.smc.frontend.util.JsfUtil;

import javax.inject.Named;
import javax.inject.Inject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@Named
public class EnvDatabaseConverter implements Converter {
	@Inject
	private EnvDatabaseFacade _ejbFacade;

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value))
			return null;

		return _ejbFacade.find(Integer.valueOf(value));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
		if (object == null || (object instanceof String && ((String) object).length() == 0))
			return null;

		if (object instanceof EnvDatabase) {
			EnvDatabase o = (EnvDatabase) object;
			return o.getEnvDatabaseId().toString();
		} else
			return null;
	}
}
