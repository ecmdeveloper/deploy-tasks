/**
 * 
 */
package com.ecmdeveloper.ant.cetasks.events;

import org.apache.tools.ant.BuildException;

import com.ecmdeveloper.ant.ceactions.events.EventActionAction;
import com.ecmdeveloper.ant.cetasks.ObjectStoreNestedTask;

/**
 * @author Ricardo Belfor
 *
 */
public class EventActionTask extends ObjectStoreNestedTask {

	private EventActionAction action = new EventActionAction();

	public void execute() throws BuildException {
		action.execute(getObjectStore(), this);
	}	

	public void addText(String text) {
		if ( text != null && !text.trim().isEmpty() ) {
			action.setDescription(text.trim() );
		}
	}
	
	public void setName(String name) {
		action.setName(name);
	}

	public void setId(String id) {
		action.setId(id);
	}

	public void setDescription(String description) {
		action.setDescription(description);
	}

	public void setCodeModuleName(String codeModuleName) {
		action.setCodeModuleName(codeModuleName);
	}

	public void setProgId(String progId) {
		action.setProgId(progId);
	}

	public void setScriptText(String scriptText) {
		action.setScriptText(scriptText);
	}
}
