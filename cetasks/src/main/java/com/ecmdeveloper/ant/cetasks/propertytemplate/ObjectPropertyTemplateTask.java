package com.ecmdeveloper.ant.cetasks.propertytemplate;

import com.ecmdeveloper.ant.ceactions.propertytemplate.ObjectPropertyTemplateAction;
import com.ecmdeveloper.ant.ceactions.propertytemplate.PropertyTemplateAction;
import com.filenet.api.constants.SecurityProxyType;

public class ObjectPropertyTemplateTask extends AbstractPropertyTemplateTask {

	private ObjectPropertyTemplateAction action = new ObjectPropertyTemplateAction();
	
	@Override
	protected PropertyTemplateAction getAction() {
		return action;
	}

	public void setAllowsForeignObject(Boolean allowsForeignObject) {
		action.setAllowsForeignObject(allowsForeignObject);
	}

	public void setSecurityProxyType(String securityProxyType) {
		action.setSecurityProxyType(securityProxyType);
	}
}
