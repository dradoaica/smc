package ro.smc.frontend.controllers.general;

import ro.smc.frontend.controllers.AbstractController;
import ro.smc.frontend.entities.general.Person;
import ro.smc.frontend.facades.general.PersonFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("personController")
@ViewScoped
public class PersonController extends AbstractController<Person> implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private PersonFacade _ejbFacade;

	public PersonController() {
		super(Person.class);
	}

	@PostConstruct
	public void init() {
		super.setFacade(_ejbFacade);
	}

	public List<Person> completeItems(String query) {
		List<Person> suggestions = new ArrayList<>();
		String queryLC = query.toLowerCase();

		for (Person p : getItems())
			if (p.getFullName().toLowerCase().startsWith(queryLC))
				suggestions.add(p);

		return suggestions;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeAdd(Person entity) {
		List rez = _ejbFacade.runNamedQueryRaw("Person.getNextId", null);
		entity.setPersonId((Integer) rez.get(0));
	}
}
