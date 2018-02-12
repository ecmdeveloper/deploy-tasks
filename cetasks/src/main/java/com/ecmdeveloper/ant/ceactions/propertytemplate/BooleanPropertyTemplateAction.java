package com.ecmdeveloper.ant.ceactions.propertytemplate;

import com.filenet.api.admin.PropertyTemplate;
import com.filenet.api.admin.PropertyTemplateBoolean;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.Id;

public class BooleanPropertyTemplateAction extends PropertyTemplateAction {

	private Boolean defaultBoolean;
	
	public void execute() {
		
		PropertyTemplateBoolean propertyTemplate = getPropertyTemplate();
		propertyTemplate.set_PropertyDefaultBoolean(defaultBoolean);
		propertyTemplate.save(RefreshMode.REFRESH);
	}

	@Override
	protected PropertyTemplate createInstance(ObjectStore objectStore, Id id) {
		return Factory.PropertyTemplateBoolean.createInstance(objectStore, id);
	}

	public void setDefaultBoolean(Boolean defaultBoolean) {
		this.defaultBoolean = defaultBoolean;
	}
}
