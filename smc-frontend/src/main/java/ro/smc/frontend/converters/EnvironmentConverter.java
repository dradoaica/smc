package ro.smc.frontend.converters;

import ro.smc.frontend.entities.general.Environment;
import ro.smc.frontend.facades.general.EnvironmentFacade;
import ro.smc.frontend.util.JsfUtil;

import javax.inject.Named;
import javax.inject.Inject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@Named
public class EnvironmentConverter implements Converter {
	@Inject
	private EnvironmentFacade _ejbFacade;

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

		if (object instanceof Environment) {
			Environment o = (Environment) object;
			return o.getEnvironmentId().toString();
		} else
			return null;
	}
}
