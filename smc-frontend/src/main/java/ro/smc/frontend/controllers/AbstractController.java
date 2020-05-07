package ro.smc.frontend.controllers;

import ro.smc.frontend.facades.AbstractFacade;
import ro.smc.frontend.util.JsfUtil;

import java.util.List;
import java.util.Map;

public abstract class AbstractController<T> {
	private AbstractFacade<T> _ejbFacade;
	private Class<T> _itemClass;
	protected T _selected;
	protected List<T> _items;

	public AbstractController() {
	}

	public AbstractController(Class<T> itemClass) {
		_itemClass = itemClass;
	}

	protected AbstractFacade<T> getFacade() {
		return _ejbFacade;
	}

	protected void setFacade(AbstractFacade<T> ejbFacade) {
		_ejbFacade = ejbFacade;
	}

	public T getSelected() {
		return _selected;
	}

	public void setSelected(T selected) {
		_selected = selected;
	}

	public List<T> getItems() {
		if (_items == null)
			_items = _ejbFacade.findAll();

		return _items;
	}

	public void resetItems() {
		_items = null;
		getItems();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void resetItems(String query, Map parameters) {
		_items = _ejbFacade.find(query, parameters);
	}

	public void prepare4Add() {
		try {
			_selected = _itemClass.newInstance();
		} catch (Exception ex) {
			JsfUtil.addErrorMessage(ex, "Error on prepareing for add");
		}
	}

	protected void beforeAdd(T entity) {
	}

	protected void afterAdd(T entity) {
	}

	public void add() {
		try {
			T entity = _selected;

			beforeAdd(entity);

			_ejbFacade.add(entity);
			if (!isValidationFailed()) {
				_selected = null;
				_items = null;
			}

			afterAdd(entity);
		} catch (Exception ex) {
			JsfUtil.addErrorMessage(ex, "Error on persisting data");
		}
	}

	public void prepare4Modify() {
	}

	protected void beforeModify(T entity) {
	}

	protected void afterModify(T entity) {
	}

	public void modify() {
		try {
			T entity = _selected;

			beforeModify(entity);

			_ejbFacade.modify(entity);

			afterModify(entity);
		} catch (Exception ex) {
			JsfUtil.addErrorMessage(ex, "Error on merging data");
		}
	}

	protected void beforeDelete(T entity) {
	}

	protected void afterDelete(T entity) {
	}

	public void delete() {
		try {
			T entity = _selected;

			beforeDelete(entity);

			_ejbFacade.delete(entity);

			if (!isValidationFailed()) {
				_selected = null;
				_items = null;
			}

			afterDelete(entity);
		} catch (Exception ex) {
			JsfUtil.addErrorMessage(ex, "Error on removing data");
		}
	}

	protected boolean isValidationFailed() {
		return JsfUtil.isValidationFailed();
	}
}