package com.ecmdeveloper.ant.ceactions.propertytemplate;

import com.filenet.api.admin.PropertyTemplate;
import com.filenet.api.admin.PropertyTemplateObject;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.constants.SecurityProxyType;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.Id;

public class ObjectPropertyTemplateAction extends PropertyTemplateAction {

	private Boolean allowsForeignObject;
	private String securityProxyType;

	public void execute() {
		
		PropertyTemplateObject propertyTemplate = getPropertyTemplate();
		propertyTemplate.set_SecurityProxyType(getSecurityProxyType());
		if ( allowsForeignObject != null && isSettable(propertyTemplate, PropertyNames.ALLOWS_FOREIGN_OBJECT ) ) {
			propertyTemplate.set_AllowsForeignObject(allowsForeignObject);
		}
		propertyTemplate.save(RefreshMode.REFRESH);
	}

	@Override
	protected PropertyTemplate createInstance(ObjectStore objectStore, Id id) {
		return Factory.PropertyTemplateObject.createInstance(objectStore, id);
	}

	public void setAllowsForeignObject(Boolean allowsForeignObject) {
		this.allowsForeignObject = allowsForeignObject;
	}

	public void setSecurityProxyType(String securityProxyType) {
		this.securityProxyType = securityProxyType;
	}
	
	private SecurityProxyType getSecurityProxyType() {
		
		if ( securityProxyType == null) {
			return null;
		}
		
		if ( securityProxyType.equals(SecurityProxyType.FULL.toString()) ) {
			return SecurityProxyType.FULL;
		} else if ( securityProxyType.equals(SecurityProxyType.INHERITANCE.toString()) ) {
			return SecurityProxyType.INHERITANCE;
		} else if ( securityProxyType.equals(SecurityProxyType.NONE.toString()) ) {
			return SecurityProxyType.NONE;
		}
			
		throw new IllegalArgumentException(securityProxyType);
	}	
}
