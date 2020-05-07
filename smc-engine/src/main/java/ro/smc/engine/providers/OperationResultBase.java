package ro.smc.engine.providers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OperationResultBase {
	private final ArrayList<String> infos = new ArrayList<>();
	private final ArrayList<String> warnings = new ArrayList<>();
	private final ArrayList<Exception> errors = new ArrayList<>();

	public OperationResultBase() {
	}

	public OperationResultBase(Exception ex) {
		errors.add(ex);
	}

	public List<String> getInfos() {
		return Collections.unmodifiableList(infos);
	}

	public List<String> getWarnings() {
		return Collections.unmodifiableList(warnings);
	}

	public List<Exception> getErrors() {
		return Collections.unmodifiableList(errors);
	}

	public void addInfo(String message) {
		infos.add(message);
	}

	public void addWarning(String message) {
		warnings.add(message);
	}

	public void addError(Exception ex) {
		errors.add(ex);
	}

	public void addError(String message, Exception ex) {
		errors.add(new Exception(message, ex));
	}

	public boolean isSucces() {
		return !hasErrors();
	}

	public boolean hasInfos() {
		return !infos.isEmpty();
	}

	public boolean hasWarnings() {
		return !warnings.isEmpty();
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}
}
