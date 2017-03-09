package com.ecmdeveloper.ant.cetasks;

import org.apache.tools.ant.BuildException;

import com.ecmdeveloper.ant.ceactions.UpdateSweepActionAction;

/**
 * 
 * @author Ricardo Belfor
 *
 */
public class ImportSweepActionTask extends ObjectStoreObjectTask {

	private UpdateSweepActionAction action = new UpdateSweepActionAction();
	
	public void execute() throws BuildException {
		
		try {
			log("Running import sweep action task");
			action.execute(getObjectStore(), this);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
	}
	
	public void setCodeModuleName(String codeModuleName) {
		action.setCodeModuleName( codeModuleName );
	}

	public void setProgId(String progId) {
		action.setProgId(progId);
	}
	
	public void setName(String name) {
		action.setName(name);
	}	
}
