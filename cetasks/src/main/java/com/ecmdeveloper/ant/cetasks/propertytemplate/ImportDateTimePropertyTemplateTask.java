package com.ecmdeveloper.ant.cetasks.propertytemplate;

import org.apache.tools.ant.BuildException;

import com.ecmdeveloper.ant.ceactions.propertytemplate.UpdateDateTimePropertyTemplateAction;

public class ImportDateTimePropertyTemplateTask extends AbstractPropertyTemplateTask {

	private Boolean isDateOnly;

	public void execute() throws BuildException {

		validateInput();
		
		try {
			UpdateDateTimePropertyTemplateAction action = new UpdateDateTimePropertyTemplateAction(
					getObjectStore(), this, name, symbolicName, description, multivalue, ordered);
			action.execute(isDateOnly);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
	}

	public void setIsDateOnly(Boolean isDateOnly) {
		this.isDateOnly = isDateOnly;
	}
}
