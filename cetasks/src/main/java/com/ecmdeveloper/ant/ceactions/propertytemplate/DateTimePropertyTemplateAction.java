package com.ecmdeveloper.ant.ceactions.propertytemplate;

import org.apache.tools.ant.Task;

import com.filenet.api.admin.PropertyTemplate;
import com.filenet.api.admin.PropertyTemplateDateTime;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.Id;

public class DateTimePropertyTemplateAction extends PropertyTemplateAction {

	private Boolean isDateOnly;
	
	public void execute() {
		
		PropertyTemplateDateTime propertyTemplate = getPropertyTemplate();
		
		if (isDateOnly != null) {
			propertyTemplate.set_IsDateOnly(isDateOnly);
		}
		propertyTemplate.save(RefreshMode.REFRESH);
	}

	@Override
	protected PropertyTemplate createInstance(ObjectStore objectStore, Id id) {
		return Factory.PropertyTemplateDateTime.createInstance(objectStore, id);
	}

	public void setIsDateOnly(Boolean isDateOnly) {
		this.isDateOnly = isDateOnly;
	}
}
