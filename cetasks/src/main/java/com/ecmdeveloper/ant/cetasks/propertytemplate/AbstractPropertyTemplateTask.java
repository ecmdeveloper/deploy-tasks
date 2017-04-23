/**
 * 
 */
package com.ecmdeveloper.ant.cetasks.propertytemplate;

import com.ecmdeveloper.ant.cetasks.ObjectStoreNestedTask;

/**
 * @author Ricardo Belfor
 *
 */
public class AbstractPropertyTemplateTask extends ObjectStoreNestedTask {

	protected String name;
	protected String symbolicName;
	protected Boolean multivalue;
	protected Boolean ordered;
	protected String description;
	
	public void setName(String name) {
		this.name = name;
	}

	public void setSymbolicName(String symbolicName) {
		this.symbolicName = symbolicName;
	}

	public void setMultivalue(Boolean multivalue) {
		this.multivalue = multivalue;
	}

	public void setOrdered(Boolean ordered) {
		this.ordered = ordered;
	}
	
	public void validateInput() {
		checkForNull("name", name);
		checkForNull("symbolicName", symbolicName);
	}

	public void addText(String text) {
		if ( !text.trim().isEmpty() ) {
			description = text.trim();
		}
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
