package ro.smc.engine.providers;

import java.sql.Connection;

import org.javatuples.Triplet;

import ro.smc.engine.merge.MergeContext;
import ro.smc.engine.merge.MergeEngine;
import ro.smc.engine.merge.MergePlan;
import ro.smc.engine.merge.MergeStep;
import ro.smc.engine.metadata.MetadataManager;
import ro.smc.engine.runtime.ConfigurationInstance;

public class ConfigurationProvider {
	public OperationResultBase applyConfiguration(Connection connection, ConfigurationInstance configurationInstance) {
		OperationResultBase ret = new OperationResultBase();
		String version = configurationInstance.getMetadata() == null ? MetadataManager.getLatestVersion()
				: configurationInstance.getMetadata().getVersion();
		DbProvider provider = MetadataManager.getConfigurationProvider(version,
				configurationInstance.getConfigurationKey());

		ConfigurationInstance dbConfigurationInstance;
		ConfigurationAccessResult res = provider.getConfigurationInstance(connection,
				configurationInstance.getConfigurationKey(), configurationInstance.includesSecurity(), true);
		if (!res.isSucces())
			return res;

		dbConfigurationInstance = res.Result;
		MergeContext mergeContext = new MergeContext(configurationInstance, dbConfigurationInstance);
		MergePlan mergePlan = getMergePlan(connection, configurationInstance, dbConfigurationInstance);

		boolean success;
		if (mergePlan.getSteps().size() > 0)
			success = applyMergePlan(connection, mergePlan, mergeContext);
		else
			success = mergeContext.getErrors().isEmpty();

		if (!success)
			for (Triplet<MergeStep, String, Exception> error : mergeContext.getErrors())
				ret.addError(error.getValue1(), error.getValue2());

		return ret;
	}

	protected MergePlan getMergePlan(Connection connection, ConfigurationInstance configurationInstance,
			ConfigurationInstance dbConfigurationInstance) {
		return MergeEngine.getMergePlan(configurationInstance, dbConfigurationInstance);
	}

	protected Boolean applyMergePlan(Connection connection, MergePlan mergePlan, MergeContext mergeContext) {
		return MergeEngine.applyMergePlan(connection, mergePlan, mergeContext);
	}
}
