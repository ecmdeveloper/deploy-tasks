/**
 * 
 */
package com.ecmdeveloper.ant.cetasks;

import org.apache.tools.ant.BuildException;

import com.ecmdeveloper.ant.ceactions.UpdateCodeModuleActionsAction;

/**
 * @author Ricardo Belfor
 *
 */
public class CodeModuleTask extends DocumentTask {

	public void execute() throws BuildException {
		
		setClassName("CodeModule");
		setForceCreate(true);
		setParentPath("/CodeModules");
		
		super.execute();
		
		try {
		
			UpdateCodeModuleActionsAction action = new UpdateCodeModuleActionsAction(getObjectStore(), this);
			action.execute( getDocumentId() );
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
	}	

}
