package ro.smc.engine.merge;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import ro.smc.engine.metadata.ExternalConfigurationKey;
import ro.smc.engine.providers.SqlObjectType;
import ro.smc.engine.runtime.ConfigurationDataItem;
import ro.smc.engine.runtime.ConfigurationInstance;
import ro.smc.engine.runtime.ConfigurationItem;
import ro.smc.engine.runtime.ConfigurationSqlItem;
import ro.smc.engine.merge.DeleteStep;

public class MergeEngine {
	public static MergePlan getMergePlan(ConfigurationInstance newInstance, ConfigurationInstance oldInstance) {
		MergePlan ret = new MergePlan();
		populateSteps(ret, newInstance, oldInstance);

		return ret;
	}

	public static void populateSteps(MergePlan mergePlan, ConfigurationInstance newInstance,
			ConfigurationInstance oldInstance) {
		for (ExternalConfigurationKey key : newInstance.getExternalConfigurations())
			mergePlan.addStep(new CheckExternalConfigurationStep(key, newInstance));

		for (ConfigurationItem item : newInstance.getConfigurationItems().stream()
				.filter(x -> x instanceof ConfigurationSqlItem)
				.filter(x -> ((ConfigurationSqlItem) x).DbObjectType == SqlObjectType.TABLE)
				.collect(Collectors.toList()))
			mergePlan.addStep(new CheckIfTableExistsStep((ConfigurationSqlItem) item));

		if (oldInstance == null)
			for (ConfigurationItem item : newInstance.getConfigurationItems()) {
				if (item instanceof ConfigurationSqlItem)
					generateStepForSqlItem(mergePlan, (ConfigurationSqlItem) item, null);
				else if (item instanceof ConfigurationDataItem)
					generateStepForDataItem(mergePlan, (ConfigurationDataItem) item, null);
			}
		else
			for (ConfigurationItem item : newInstance.getConfigurationItems()) {
				if (item instanceof ConfigurationSqlItem) {
					ConfigurationSqlItem oldItem = (ConfigurationSqlItem) oldInstance.getConfigurationItems().stream()
							.filter(x -> x instanceof ConfigurationSqlItem)
							.filter(x -> ((ConfigurationSqlItem) x).Name.equals(((ConfigurationSqlItem) item).Name))
							.findFirst().orElse(null);
					generateStepForSqlItem(mergePlan, (ConfigurationSqlItem) item, oldItem);
				} else if (item instanceof ConfigurationDataItem) {
					ConfigurationDataItem oldItem = (ConfigurationDataItem) oldInstance.getConfigurationItems().stream()
							.filter(x -> x instanceof ConfigurationDataItem).findFirst().orElse(null);
					generateStepForDataItem(mergePlan, (ConfigurationDataItem) item, oldItem);
				}
			}
	}

	private static void generateStepForSqlItem(MergePlan mergePlan, ConfigurationSqlItem newItem,
			ConfigurationSqlItem oldItem) {
		if (newItem != null) {
			switch (newItem.DbObjectType) {
			case SqlObjectType.FUNCTION:
				mergePlan.addStep(new CreateFunctionStep(newItem, oldItem));
				break;
			case SqlObjectType.VIEW:
				mergePlan.addStep(new CreateViewStep(newItem, oldItem));
				break;
			case SqlObjectType.STORED_PROCEDURE:
				mergePlan.addStep(new CreateProcedureStep(newItem, oldItem));
				break;
			}
		} else {
			// nothing to do in case some sql object references change, we will
			// not drop the old referenced objects to prevent breaking
			// things!!!!
		}
	}

	private static void generateStepForDataItem(MergePlan mergePlan, ConfigurationDataItem newItem,
			ConfigurationDataItem oldItem) {
		// TODO GenerateStepForDataItem
	}

	public static Boolean applyMergePlan(Connection connection, MergePlan mergePlan, MergeContext mergeContext) {
		List<MergeStep> deleteSteps = Lists.reverse(
				mergePlan.getSteps().stream().filter(x -> x instanceof DeleteStep).collect(Collectors.toList()));
		List<MergeStep> checkExternalSteps = mergePlan.getSteps().stream()
				.filter(x -> x instanceof CheckExternalConfigurationStep).collect(Collectors.toList());
		List<MergeStep> allOtherSteps = mergePlan.getSteps().stream().filter(x -> !deleteSteps.contains(x))
				.filter(x -> !checkExternalSteps.contains(x)).collect(Collectors.toList());

		for (MergeStep step : deleteSteps)
			if (step.shouldExecute(connection, mergeContext))
				step.execute(connection, mergeContext);

		for (MergeStep step : checkExternalSteps)
			if (step.shouldExecute(connection, mergeContext))
				step.execute(connection, mergeContext);

		for (MergeStep step : allOtherSteps)
			if (step.shouldExecute(connection, mergeContext))
				step.execute(connection, mergeContext);

		return mergeContext.getErrors().size() == 0;
	}
}
