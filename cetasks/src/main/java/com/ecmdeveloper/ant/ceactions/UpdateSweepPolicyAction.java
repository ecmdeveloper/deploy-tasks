/**
 * 
 */
package com.ecmdeveloper.ant.ceactions;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.filenet.api.constants.RefreshMode;
import com.filenet.api.constants.SweepMode;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.sweep.CmCustomSweepPolicy;
import com.filenet.api.sweep.CmSweepAction;

/**
 * @author ricardobelfor
 *
 */
public class UpdateSweepPolicyAction extends ObjectStoreAction {

	private ObjectStore objectStore;
	private Task task;

	public UpdateSweepPolicyAction(ObjectStore objectStore, Task task) {
		this.objectStore = objectStore;
		this.task = task;
	}

	public void execute(String name, String sweepTarget, String sweepActionName, String filterExpression, String description) {

		CmCustomSweepPolicy sweepPolicy = getByDisplayName(name, CmCustomSweepPolicy.class, objectStore);
		if ( sweepPolicy == null) {
			sweepPolicy = Factory.CmCustomSweepPolicy.createInstance(objectStore, "CmCustomSweepPolicy");
		}
		sweepPolicy.set_DisplayName(name);
		sweepPolicy.set_SweepTarget(Factory.DocumentClassDefinition.fetchInstance(objectStore, sweepTarget, null) );
		sweepPolicy.set_IncludeSubclassesRequested(Boolean.TRUE);
		sweepPolicy.set_SweepMode(SweepMode.SWEEP_MODE_NORMAL);
		
		sweepPolicy.set_DescriptiveText(description);

		CmSweepAction sweepAction = getByDisplayName(sweepActionName, CmSweepAction.class, objectStore);
		if ( sweepAction == null ) {
			throw new BuildException("Sweep Action '" + sweepActionName + "' not found");
		}

		sweepPolicy.set_SweepAction(sweepAction);
		sweepPolicy.set_FilterExpression(filterExpression);
		sweepPolicy.save(RefreshMode.NO_REFRESH);
	}
}
