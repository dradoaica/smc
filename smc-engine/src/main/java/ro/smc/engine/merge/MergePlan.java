package ro.smc.engine.merge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MergePlan {
	private final ArrayList<MergeStep> _steps = new ArrayList<>();

	public List<MergeStep> getSteps() {
		return Collections.unmodifiableList(_steps);
	}

	public void addStep(MergeStep step) {
		_steps.add(step);
	}
}
