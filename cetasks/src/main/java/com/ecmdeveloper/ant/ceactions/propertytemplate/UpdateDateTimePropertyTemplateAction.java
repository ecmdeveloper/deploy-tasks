package com.ecmdeveloper.ant.ceactions.propertytemplate;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.apache.tools.ant.Task;

import com.filenet.api.admin.PropertyTemplate;
import com.filenet.api.admin.PropertyTemplateDateTime;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;

public class UpdateDateTimePropertyTemplateAction extends PropertyTemplateAction {

	public UpdateDateTimePropertyTemplateAction(ObjectStore objectStore,
			Task task, String name, String symbolicName, String description, Boolean multivalue,
			Boolean ordered) {
		super(objectStore, task, name, symbolicName, description, multivalue, ordered);
	}

	public void execute(Boolean isDateOnly) {
		
		PropertyTemplateDateTime propertyTemplate = getPropertyTemplate();
		
		if (isDateOnly != null) {
			propertyTemplate.set_IsDateOnly(isDateOnly);
		}
		propertyTemplate.save(RefreshMode.REFRESH);
	}

	@Override
	protected PropertyTemplate createInstance(ObjectStore objectStore) {
		return Factory.PropertyTemplateDateTime.createInstance(objectStore);
	}
}
