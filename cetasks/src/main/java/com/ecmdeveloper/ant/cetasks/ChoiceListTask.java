/**
 * 
 */
package com.ecmdeveloper.ant.cetasks;

import org.apache.tools.ant.BuildException;

import com.ecmdeveloper.ant.ceactions.UpdateChoiceListAction;
import com.ecmdeveloper.ant.cetypes.ChoiceItemValue;

/**
 * @author Ricardo Belfor
 *
 */
public class ChoiceListTask extends ObjectStoreNestedTask {

	private UpdateChoiceListAction action = new UpdateChoiceListAction();

	public void execute() throws BuildException {
		action.execute(getObjectStore(), this);
	}	
	
	public void setDisplayName(String displayName) {
		action.setDisplayName(displayName);
	}

	public void setId(String id) {
		action.setId(id);
	}

	public void setDataType(String dataType) {
		action.setDataType(dataType);
	}

	public void setDescription(String description) {
		action.setDescription(description);
	} 
	
	public void addText(String text) {
		if ( text != null) {
			action.setDescription(text.trim() );
		}
	}
	
	public void addConfigured(ChoiceItemValue item) {
		action.add(item);
	}
}
