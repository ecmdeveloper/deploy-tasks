package com.ecmdeveloper.ant.ceactions.propertytemplate;

import org.apache.tools.ant.Task;

import com.ecmdeveloper.ant.ceactions.ObjectStoreAction;
import com.filenet.api.admin.LocalizedString;
import com.filenet.api.admin.PropertyTemplate;
import com.filenet.api.admin.PropertyTemplateDateTime;
import com.filenet.api.constants.Cardinality;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Factory;
import com.filenet.api.core.IndependentObject;
import com.filenet.api.core.ObjectStore;


public abstract class PropertyTemplateAction extends ObjectStoreAction {

	final protected ObjectStore objectStore;
	final protected Task task;
	final private String name;
	final private String symbolicName;
	final private Boolean multivalue;
	final private Boolean ordered;
	final private String description;

	public PropertyTemplateAction(ObjectStore objectStore, Task task, String name, String symbolicName, String description, Boolean multivalue, Boolean ordered) {
		this.objectStore = objectStore;
		this.task = task;
		this.name = name;
		this.symbolicName = symbolicName;
		this.description = description;
		this.multivalue = multivalue;
		this.ordered = ordered;
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends PropertyTemplate> T getPropertyTemplate() {
		T propertyTemplate = (T) getBySymbolicName(symbolicName, PropertyTemplate.class , objectStore);
		
		if ( propertyTemplate == null) {
			task.log("Creating property template '" + name + "'");
			propertyTemplate = (T) createInstance(objectStore);
			propertyTemplate.set_Cardinality(getCardinality());
			propertyTemplate.set_SymbolicName(name);
		} else {
			task.log("Updating property template '" + name + "'");
		}
		propertyTemplate.set_DisplayNames(Factory.LocalizedString.createList());
		propertyTemplate.get_DisplayNames().add(createLocalizedString(name, objectStore.get_LocaleName() ));
		
		if ( description != null) {
			LocalizedString descriptionString = createLocalizedString(description, objectStore.get_LocaleName() );
			propertyTemplate.set_DescriptiveTexts( Factory.LocalizedString.createList() );
			propertyTemplate.get_DescriptiveTexts().add(descriptionString);
		}
		
		return propertyTemplate;
	}

	protected abstract PropertyTemplate createInstance(ObjectStore objectStore);
	
	private Cardinality getCardinality() {
		if ( multivalue == null || multivalue ) {
			return Cardinality.SINGLE;
		} else {
			return ordered != null && ordered ? Cardinality.LIST : Cardinality.ENUM;
		}
	}
}
