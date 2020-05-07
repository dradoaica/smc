package ro.smc.engine.merge;

import java.sql.Connection;

import ro.smc.engine.runtime.ConfigurationSqlItem;

public abstract class ScriptableObjectStep extends MergeStep {
	public ConfigurationSqlItem NewItem;
	public ConfigurationSqlItem OldItem;

	@Override
	public boolean shouldExecute(Connection connection, MergeContext mergeContext) {
		return true;
	}
}
