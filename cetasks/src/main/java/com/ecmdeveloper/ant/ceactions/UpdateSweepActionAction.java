/**
 * 
 */
package com.ecmdeveloper.ant.ceactions;

import org.apache.tools.ant.BuildException;
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

	private String codeModuleName;
	private String progId;
	private String name;

	public UpdateSweepActionAction() {
	}
	
	public void execute(ObjectStore objectStore, Task task) {

		CodeModule codeModule = getByName(codeModuleName, CodeModule.class, " IsCurrentVersion = TRUE", objectStore );
		if ( codeModule == null) {
			throw new BuildException("Code Module '" + codeModuleName + "' not found");
		}
		
		CmSweepAction sweepAction = getByDisplayName(name, CmSweepAction.class, objectStore);
		
		if ( sweepAction == null ) {
			sweepAction = Factory.CmSweepAction.createInstance(objectStore, "CmSweepAction");
		}

		sweepAction.set_CodeModule(codeModule);
		sweepAction.set_ProgId(progId);
		sweepAction.set_DisplayName(name);
		sweepAction.save(RefreshMode.REFRESH);
	}

	public void setCodeModuleName(String codeModuleName) {
		this.codeModuleName = codeModuleName;
	}

	public void setProgId(String progId) {
		this.progId = progId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
