package ro.smc.engine.merge;

import java.sql.Connection;

import ro.smc.engine.runtime.ConfigurationSqlItem;

public class CheckIfTableExistsStep extends MergeStep {
	public CheckIfTableExistsStep(ConfigurationSqlItem item) {
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
