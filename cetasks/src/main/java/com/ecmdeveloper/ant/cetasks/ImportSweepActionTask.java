package com.ecmdeveloper.ant.cetasks;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

import com.ecmdeveloper.ant.ceactions.UpdateSweepActionAction;

/**
 * 
 * @author Ricardo Belfor
 *
 */
public class ImportSweepActionTask extends ObjectStoreObjectTask {

	private String description;
	private String codeModuleName;
	private String progId;
	private String name;
	
	public void execute() throws BuildException {
		
		try {
			log("Running import sweep action task", Project.MSG_VERBOSE);
			UpdateSweepActionAction action = new UpdateSweepActionAction(getObjectStore(), this);
			action.execute(name, description, codeModuleName, progId);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
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
	
	public void setDescription(String description) {
		this.description = description;
	}

	public void addText(String text) {
		if ( !text.trim().isEmpty() ) {
			description = text.trim();
		}
	}
	
}
