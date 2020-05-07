package ro.smc.engine.merge;

import java.sql.Connection;

import ro.smc.engine.runtime.FragmentRow;

public class InsertStep extends DdlStep implements IDDLStep {
	@Override
	public FragmentRow getDataRow() {
		throw new UnsupportedOperationException();
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
