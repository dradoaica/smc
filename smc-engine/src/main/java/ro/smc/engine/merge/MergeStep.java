package ro.smc.engine.merge;

import java.sql.Connection;

public abstract class MergeStep {
	public abstract boolean shouldExecute(Connection connection, MergeContext mergeContext);

	public abstract boolean execute(Connection connection, MergeContext mergeContext);
}
