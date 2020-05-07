package ro.smc.frontend.controllers.general;

import ro.smc.frontend.controllers.AbstractController;
import ro.smc.frontend.entities.general.Dictionary;
import ro.smc.frontend.entities.general.DictionaryItem;
import ro.smc.frontend.facades.general.DictionaryItemFacade;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("dictionaryItemController")
@ViewScoped
public class DictionaryItemController extends AbstractController<DictionaryItem> implements Serializable {
	private static final long serialVersionUID = 1L;
	private Dictionary _selectedDictionary;
	private List<DictionaryItem> _genders;
	private List<DictionaryItem> _cityTypes;
	@EJB
	private DictionaryItemFacade _ejbFacade;

	public DictionaryItemController() {
		super(DictionaryItem.class);
	}

	@PostConstruct
	public void init() {
		super.setFacade(_ejbFacade);
	}

	public void setSelectedDictionary(Dictionary dictionary) {
		_selectedDictionary = dictionary;
		_selected = null;
		resetItems();
	}

	@Override
	public List<DictionaryItem> getItems() {
		if (_items == null) {
			Map<String, Object> parameters = new HashMap<>();
			String query = "SELECT d FROM DictionaryItem d WHERE d.dictionary.dictionaryId = :dictionaryId ORDER BY d.dictionaryItemCode";
			if (_selectedDictionary != null)
				parameters.put("dictionaryId", _selectedDictionary.getDictionaryId());
			else
				parameters.put("dictionaryId", -1);

			resetItems(query, parameters);
		}

		return _items;
	}

	public List<DictionaryItem> getGenders() {
		if (_genders == null)
			_genders = _ejbFacade.runNamedQuery("DictionaryItem.findAllGenders", null);

		return _genders;
	}

	public List<DictionaryItem> getCityTypes() {
		if (_cityTypes == null)
			_cityTypes = _ejbFacade.runNamedQuery("DictionaryItem.findAllCityTypes", null);

		return _cityTypes;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeAdd(DictionaryItem entity) {
		List rez = _ejbFacade.runNamedQueryRaw("DictionaryItem.getNextId", null);
		entity.setDictionaryItemId((Integer) rez.get(0));
		entity.setDictionary(_selectedDictionary);
	}
}
