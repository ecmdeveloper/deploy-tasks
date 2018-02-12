/**
 * 
 */
package com.ecmdeveloper.ant.cetasks.events;

import org.apache.tools.ant.BuildException;

import com.ecmdeveloper.ant.ceactions.events.ClassSubscriptionAction;
import com.ecmdeveloper.ant.cetasks.ObjectStoreNestedTask;
import com.ecmdeveloper.ant.cetypes.Event;

/**
 * @author Ricardo Belfor
 *
 */
public class ClassSubscriptionTask extends ObjectStoreNestedTask {

	private ClassSubscriptionAction action = new ClassSubscriptionAction();

	public void execute() throws BuildException {
		action.execute(getObjectStore(), this);
	}	

	public void setDisplayName(String displayName) {
		action.setDisplayName(displayName);
	}

	public void setId(String id) {
		action.setId(id );
	}

	public void setDescription(String description) {
		action.setDescription(description);
	}

	public void setClassName(String className) {
		action.setClassName(className);
	}

	public void setIncludeSubclasses(Boolean includeSubclasses) {
		action.setIncludeSubclasses(includeSubclasses);
	}

	public void setSynchronous(Boolean synchronous) {
		action.setSynchronous(synchronous);
	}

	public void setUserString(String userString) {
		action.setUserString(userString);
	}

	public void setEnabled(Boolean enabled) {
		action.setEnabled(enabled);
	}

	public void setFilterExpression(String filterExpression) {
		action.setFilterExpression(filterExpression);
	}

	public void setEventActionName(String eventActionName) {
		action.setEventActionName(eventActionName);
	}

	public void setFilteredPropertyId(String filteredPropertyId) {
		action.setFilteredPropertyId(filteredPropertyId);
	}
	
	public void addText(String text) {
		if ( text != null && !text.trim().isEmpty() ) {
			action.setDescription(text.trim() );
		}
	}
	
	public void addConfigured(Event event) {
		action.addEvent(event);
	}	
}
