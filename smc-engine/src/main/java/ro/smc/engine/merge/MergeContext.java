package ro.smc.engine.merge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.javatuples.Triplet;

import ro.smc.engine.runtime.ConfigurationInstance;

public class MergeContext {
	private final ArrayList<Triplet<MergeStep, String, Exception>> _errors = new ArrayList<>();
	private ConfigurationInstance _newInstance;
	private ConfigurationInstance _oldInstance;

	public MergeContext(ConfigurationInstance newInstance, ConfigurationInstance oldInstance) {
		_newInstance = newInstance;
		_oldInstance = oldInstance;
	}

	public ConfigurationInstance getNewInstance() {
		return _newInstance;
	}

	public ConfigurationInstance getOldInstance() {
		return _oldInstance;
	}

	public List<Triplet<MergeStep, String, Exception>> getErrors() {
		return Collections.unmodifiableList(_errors);
	}

	public void addError(Triplet<MergeStep, String, Exception> error) {
		_errors.add(error);
	}
}
