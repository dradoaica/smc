package ro.smc.frontend.converters;

import ro.smc.frontend.entities.general.Permission;
import ro.smc.frontend.facades.general.PermissionFacade;
import ro.smc.frontend.util.JsfUtil;

import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@Named
public class PermissionConverter implements Converter {
	@Inject
	private PermissionFacade _ejbFacade;

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

		if (object instanceof Permission) {
			Permission o = (Permission) object;
			return o.getPermissionId().toString();
		} else
			return null;
	}
}
