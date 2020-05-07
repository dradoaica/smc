package ro.smc.engine.merge;

import java.sql.Connection;

public class DeleteStep extends DdlStep {
	@Override
	public boolean shouldExecute(Connection connection, MergeContext mergeContext) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(Connection connection, MergeContext mergeContext) {
		throw new UnsupportedOperationException();
	}
}
