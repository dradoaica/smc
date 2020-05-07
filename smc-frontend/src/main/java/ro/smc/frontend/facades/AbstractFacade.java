package ro.smc.frontend.facades;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class AbstractFacade<T> {
	private Class<T> _entityClass;

	public AbstractFacade(Class<T> entityClass) {
		_entityClass = entityClass;
	}

	protected abstract EntityManager getEntityManager();

	public void add(T entity) {
		getEntityManager().persist(entity);
		getEntityManager().flush();
		refresh(entity);
	}

	public void modify(T entity) {
		getEntityManager().merge(entity);
		getEntityManager().flush();
		refresh(entity);
	}

	public void delete(T entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
		getEntityManager().flush();
	}

	protected void onFind(T entity) {
	}

	public T find(Object id) {
		T entity = getEntityManager().find(_entityClass, id);
		onFind(entity);

		return entity;
	}

	public void refresh(T entity) {
		getEntityManager().refresh(getEntityManager().merge(entity));
		onFind(entity);
	}

	protected void onFind(List<T> entities) {
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Query cq = getEntityManager().createNamedQuery(_entityClass.getSimpleName() + ".findAll");
		List<T> entities = cq.getResultList();
		onFind(entities);

		return entities;
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String query, Map<String, Object> parameters) {
		Query cq = getEntityManager().createQuery(query);

		if (parameters != null)
			for (String s : parameters.keySet())
				cq.setParameter(s, parameters.get(s));

		List<T> entities = cq.getResultList();
		onFind(entities);

		return entities;
	}

	@SuppressWarnings("unchecked")
	public List<T> runNamedQuery(String namedQuery, Map<String, Object> parameters) {
		Query cq = getEntityManager().createNamedQuery(namedQuery);

		if (parameters != null)
			for (String s : parameters.keySet())
				cq.setParameter(s, parameters.get(s));

		List<T> entities = cq.getResultList();
		onFind(entities);

		return entities;
	}

	@SuppressWarnings("unchecked")
	public <R> List<R> runNamedQuery(Class<R> c, String namedQuery, Map<String, Object> parameters) {
		Query cq = getEntityManager().createNamedQuery(namedQuery);

		if (parameters != null)
			for (String s : parameters.keySet())
				cq.setParameter(s, parameters.get(s));

		return cq.getResultList();
	}

	@SuppressWarnings({ "rawtypes" })
	public List runNamedQueryRaw(String namedQuery, Map<String, Object> parameters) {
		Query cq = getEntityManager().createNamedQuery(namedQuery);

		if (parameters != null)
			for (String s : parameters.keySet())
				cq.setParameter(s, parameters.get(s));

		return cq.getResultList();
	}

	@SuppressWarnings({ "rawtypes" })
	public List runNativeQueryRaw(String sqlString, Map<String, Object> parameters) {
		Query cq = getEntityManager().createNativeQuery(sqlString);

		if (parameters != null)
			for (String s : parameters.keySet())
				cq.setParameter(s, parameters.get(s));

		return cq.getResultList();
	}
}
