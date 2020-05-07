package ro.smc.engine.merge;

import java.sql.Connection;
import java.sql.Statement;

import org.javatuples.Triplet;

import ro.smc.engine.runtime.ConfigurationSqlItem;

public class CreateViewStep extends ScriptableObjectStep {
	public CreateViewStep(ConfigurationSqlItem newItem, ConfigurationSqlItem oldItem) {
		NewItem = newItem;
		OldItem = oldItem;
	}

	@Override
	public boolean execute(Connection connection, MergeContext mergeContext) {
		try {
			String query = "IF EXISTS (SELECT * FROM sys.objects WHERE type in( N'V' ) AND name = '%1$s' and schema_id = schema_id('%2$s')) drop view %3$s";
			Statement statement = connection.createStatement();
			statement.execute(String.format(query, OldItem.Name.getTable(), OldItem.Name.getSchema(), OldItem.Name));
		} catch (Exception ex) {
			mergeContext.addError(new Triplet<MergeStep, String, Exception>(this,
					String.format("error dropping view %1$s", OldItem.Name), ex));
			return false;
		}

		try {
			Statement statement = connection.createStatement();
			statement.execute(NewItem.Definition);
			return true;
		} catch (Exception ex) {
			mergeContext.addError(new Triplet<MergeStep, String, Exception>(this,
					String.format("error creating view %1$s", OldItem.Name), ex));
			return false;
		}
	}
}
