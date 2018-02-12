package com.ecmdeveloper.ant.cetasks.propertytemplate;

import com.ecmdeveloper.ant.ceactions.propertytemplate.BooleanPropertyTemplateAction;
import com.ecmdeveloper.ant.ceactions.propertytemplate.PropertyTemplateAction;

public class BooleanPropertyTemplateTask extends AbstractPropertyTemplateTask {

	private BooleanPropertyTemplateAction action = new BooleanPropertyTemplateAction();
	
	@Override
	protected PropertyTemplateAction getAction() {
		return action;
	}
	
	public void setDefaultBoolean(Boolean defaultBoolean) {
		action.setDefaultBoolean(defaultBoolean);
	}	
}
