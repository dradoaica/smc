package ro.smc.engine.merge;

import java.sql.Connection;

import ro.smc.engine.metadata.ExternalConfigurationKey;
import ro.smc.engine.runtime.ConfigurationInstance;

public class CheckExternalConfigurationStep extends MergeStep {
	public CheckExternalConfigurationStep(ExternalConfigurationKey key, ConfigurationInstance instance) {
	}

	@Override
	public boolean shouldExecute(Connection connection, MergeContext mergeContext) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(Connection connection, MergeContext mergeContext) {
		throw new UnsupportedOperationException();
	}
}
