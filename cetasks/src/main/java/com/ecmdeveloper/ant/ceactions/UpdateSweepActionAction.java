/**
 * 
 */
package com.ecmdeveloper.ant.ceactions;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import com.filenet.api.admin.CodeModule;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.sweep.CmSweepAction;

/**
 * @author Ricardo Belfor
 *
 */
public class UpdateSweepActionAction extends ObjectStoreAction {

	private ObjectStore objectStore;
	private Task task;

	public UpdateSweepActionAction(ObjectStore objectStore, Task task) {
		this.objectStore = objectStore;
		this.task = task;
	}
	
	public void execute(String name, String description, String codeModuleName, String progId) {

		task.log("Fetching code module '" + codeModuleName + "'", Project.MSG_VERBOSE);
		
		CodeModule codeModule = getByName(codeModuleName, CodeModule.class, " IsCurrentVersion = TRUE", objectStore );
		if ( codeModule == null) {
			throw new BuildException("Code Module '" + codeModuleName + "' not found");
		}
		
		task.log("Fetching sweep action '" + name + "'", Project.MSG_VERBOSE);

		CmSweepAction sweepAction = getByDisplayName(name, CmSweepAction.class, objectStore);
		
		if ( sweepAction == null ) {
			sweepAction = Factory.CmSweepAction.createInstance(objectStore, "CmSweepAction");
		}

		sweepAction.set_CodeModule(codeModule);
		sweepAction.set_ProgId(progId);
		sweepAction.set_DisplayName(name);
		sweepAction.set_DescriptiveText(description);
		task.log("Saving sweep action '" + name + "'", Project.MSG_VERBOSE);
		
		sweepAction.save(RefreshMode.REFRESH);
	}
}
