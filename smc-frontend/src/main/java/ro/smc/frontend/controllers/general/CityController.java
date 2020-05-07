package ro.smc.frontend.controllers.general;

import ro.smc.frontend.controllers.AbstractController;
import ro.smc.frontend.entities.general.City;
import ro.smc.frontend.facades.general.CityFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("cityController")
@ViewScoped
public class CityController extends AbstractController<City> implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<City> _cities;
	private List<City> _districts;
	private List<City> _countries;
	@EJB
	private CityFacade _ejbFacade;

	public CityController() {
		super(City.class);
	}

	@PostConstruct
	public void init() {
		super.setFacade(_ejbFacade);
	}

	public List<City> completeCities(String query) {
		List<City> suggestions = new ArrayList<>();
		String queryLC = query.toLowerCase();

		for (City c : getCities())
			if (c.getCityName().toLowerCase().startsWith(queryLC))
				suggestions.add(c);

		return suggestions;
	}

	public List<City> completeDistricts(String query) {
		List<City> suggestions = new ArrayList<>();
		String queryLC = query.toLowerCase();

		for (City c : getDistricts())
			if (c.getCityName().toLowerCase().startsWith(queryLC))
				suggestions.add(c);

		return suggestions;
	}

	public List<City> completeCountries(String query) {
		List<City> suggestions = new ArrayList<>();
		String queryLC = query.toLowerCase();

		for (City c : getCountries())
			if (c.getCityName().toLowerCase().startsWith(queryLC))
				suggestions.add(c);

		return suggestions;
	}

	public List<City> getCities() {
		if (_cities == null)
			_cities = _ejbFacade.runNamedQuery("City.findAllCities", null);

		return _cities;
	}

	public List<City> getDistricts() {
		if (_districts == null)
			_districts = _ejbFacade.runNamedQuery("City.findAllDistricts", null);

		return _districts;
	}

	public List<City> getCountries() {
		if (_countries == null)
			_countries = _ejbFacade.runNamedQuery("City.findAllCountries", null);

		return _countries;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeAdd(City entity) {
		List rez = _ejbFacade.runNamedQueryRaw("City.getNextId", null);
		entity.setCityId((Integer) rez.get(0));
	}
}
