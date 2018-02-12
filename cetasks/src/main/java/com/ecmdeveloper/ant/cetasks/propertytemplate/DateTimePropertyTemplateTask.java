package com.ecmdeveloper.ant.cetasks.propertytemplate;

import com.ecmdeveloper.ant.ceactions.propertytemplate.PropertyTemplateAction;
import com.ecmdeveloper.ant.ceactions.propertytemplate.DateTimePropertyTemplateAction;

public class DateTimePropertyTemplateTask extends AbstractPropertyTemplateTask {

	private DateTimePropertyTemplateAction action = new DateTimePropertyTemplateAction();
	
	public void setIsDateOnly(Boolean isDateOnly) {
		action.setIsDateOnly(isDateOnly);
	}

	@Override
	protected PropertyTemplateAction getAction() {
		return action;
	}
}
