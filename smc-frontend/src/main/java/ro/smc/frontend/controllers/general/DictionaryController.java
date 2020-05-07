package ro.smc.frontend.controllers.general;

import ro.smc.frontend.controllers.AbstractController;
import ro.smc.frontend.entities.general.Dictionary;
import ro.smc.frontend.facades.general.DictionaryFacade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("dictionaryController")
@ViewScoped
public class DictionaryController extends AbstractController<Dictionary> implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private DictionaryFacade _ejbFacade;

	public DictionaryController() {
		super(Dictionary.class);
	}

	@PostConstruct
	public void init() {
		super.setFacade(_ejbFacade);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeAdd(Dictionary entity) {
		List rez = _ejbFacade.runNamedQueryRaw("Dictionary.getNextId", null);
		_selected.setDictionaryId((Integer) rez.get(0));
	}
}
