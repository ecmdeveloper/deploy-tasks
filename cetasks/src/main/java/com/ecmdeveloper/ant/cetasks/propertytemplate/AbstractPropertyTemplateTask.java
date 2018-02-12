/**
 * 
 */
package com.ecmdeveloper.ant.cetasks.propertytemplate;

import org.apache.tools.ant.BuildException;

import com.ecmdeveloper.ant.ceactions.propertytemplate.PropertyTemplateAction;
import com.ecmdeveloper.ant.cetasks.ObjectStoreNestedTask;

/**
 * @author Ricardo Belfor
 *
 */
public abstract class AbstractPropertyTemplateTask extends ObjectStoreNestedTask {

	abstract protected PropertyTemplateAction getAction();
	
	public void execute() throws BuildException {

		validateInput();
		
		try {
			getAction().execute(getObjectStore(), this);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
	}

	
	public void setName(String name) {
		getAction().setName(name);
	}

	public void setSymbolicName(String symbolicName) {
		getAction().setSymbolicName(symbolicName);
	}

	public void setMultivalue(Boolean multivalue) {
		getAction().setMultivalue(multivalue);
	}

	public void setOrdered(Boolean ordered) {
		getAction().setOrdered(ordered);
	}
	
	public void validateInput() {
//		checkForNull("name", name);
//		checkForNull("symbolicName", symbolicName);
	}

	public void setId(String id) {
		getAction().setId(id);
	}
	
	public void addText(String text) {
		if ( !text.trim().isEmpty() ) {
			getAction().setDescription(text.trim());
		}
	}
	
	public void setDescription(String description) {
		getAction().setDescription(description);
	}
	
	public void setHidden(Boolean hidden) {
		getAction().setHidden(hidden);
	}
	
	public void setNameProperty(Boolean nameProperty) {
		getAction().setNameProperty(nameProperty);
	}
	
	public void setRequired(Boolean required) {
		getAction().setRequired(required);
	}

	public void setSettability(String settability) {
		getAction().setSettability(settability);
	}
	
	public void setCategory(String category) {
		getAction().setCategory(category);
	}
}
