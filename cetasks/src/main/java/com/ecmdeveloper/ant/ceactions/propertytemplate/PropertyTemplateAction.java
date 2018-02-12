package com.ecmdeveloper.ant.ceactions.propertytemplate;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.ecmdeveloper.ant.ceactions.ObjectStoreAction;
import com.filenet.api.admin.ChoiceList;
import com.filenet.api.admin.LocalizedString;
import com.filenet.api.admin.PropertyTemplate;
import com.filenet.api.admin.PropertyTemplateDateTime;
import com.filenet.api.constants.Cardinality;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.constants.PropertySettability;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Factory;
import com.filenet.api.core.IndependentObject;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.Properties;
import com.filenet.api.util.Id;


public abstract class PropertyTemplateAction extends ObjectStoreAction {

	protected ObjectStore objectStore;
	protected Task task;
	private String id;
	private String name;
	private String symbolicName;
	private Boolean multivalue;
	private Boolean ordered;
	private String description;
	private Boolean hidden;
	private Boolean nameProperty;
	private Boolean required;
	private String settability;
	private String category;
	
	public PropertyTemplateAction() {
	}
	
	public void execute(ObjectStore objectStore, Task task) {
		this.objectStore = objectStore;
		this.task = task;
		execute();
	}
	abstract public void execute();
	
	@SuppressWarnings("unchecked")
	protected <T extends PropertyTemplate> T getPropertyTemplate() {
		
		T propertyTemplate = (T) getBySymbolicName(symbolicName, PropertyTemplate.class , objectStore);
		
		if ( propertyTemplate == null) {
			task.log("Creating property template '" + symbolicName + "'");
			propertyTemplate = (T) createInstance(objectStore, new Id(id) );
			propertyTemplate.set_Cardinality(getCardinality());
			propertyTemplate.set_SymbolicName(symbolicName);
		} else {
			propertyTemplate.refresh();
			task.log("Updating property template '" + symbolicName + "'");
		}
		propertyTemplate.set_DisplayNames(Factory.LocalizedString.createList());
		propertyTemplate.get_DisplayNames().add(createLocalizedString(name, objectStore.get_LocaleName() ));
		
		if ( description != null) {
			LocalizedString descriptionString = createLocalizedString(description, objectStore.get_LocaleName() );
			propertyTemplate.set_DescriptiveTexts( Factory.LocalizedString.createList() );
			propertyTemplate.get_DescriptiveTexts().add(descriptionString);
		}
		
		if ( hidden != null ) {
			propertyTemplate.set_IsHidden(hidden);
		}
		
		if (nameProperty != null && isSettable(propertyTemplate, PropertyNames.IS_NAME_PROPERTY ) ) {
			propertyTemplate.set_IsNameProperty(nameProperty);
		}
		
		if ( required != null) {
			propertyTemplate.set_IsValueRequired(required);
		}
		
		if ( settability != null) {
			propertyTemplate.set_Settability(getSettability());
		}
		
		propertyTemplate.set_PropertyDisplayCategory(category);
		
		return propertyTemplate;
	}

	protected ChoiceList getChoiceList(String displayName) {
		ChoiceList choiceList = getByDisplayName(displayName, ChoiceList.class, objectStore);

		if ( choiceList == null) {
			throw new BuildException("Choice list '" + displayName + "' not found!");
		}
		return choiceList;
	}
	
	private PropertySettability getSettability() {
			
		if ( PropertySettability.READ_ONLY.toString().equals(settability) ) {
			return PropertySettability.READ_ONLY;
		} else if ( PropertySettability.READ_WRITE.toString().equals(settability) ) {
			return PropertySettability.READ_WRITE;
		} else if ( PropertySettability.SETTABLE_ONLY_BEFORE_CHECKIN.toString().equals(settability) ) {
			return PropertySettability.SETTABLE_ONLY_BEFORE_CHECKIN;
		} else if ( PropertySettability.SETTABLE_ONLY_ON_CREATE.toString().equals(settability) ) {
			return PropertySettability.SETTABLE_ONLY_ON_CREATE;
		}
		throw new IllegalArgumentException(settability);
	}

	protected abstract PropertyTemplate createInstance(ObjectStore objectStore, Id id );
	
	private Cardinality getCardinality() {
		if ( multivalue == null || multivalue ) {
			return Cardinality.SINGLE;
		} else {
			return ordered != null && ordered ? Cardinality.LIST : Cardinality.ENUM;
		}
	}

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

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public void setNameProperty(Boolean nameProperty) {
		this.nameProperty = nameProperty;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public void setSettability(String settability) {
		this.settability = settability;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	protected boolean isSettable(PropertyTemplate propertyTemplate, String propertyName) {
		Properties properties = propertyTemplate.getProperties();
		if ( properties.isPropertyPresent(propertyName) ) {
			return properties.get(propertyName).isSettable();
		}
		return true;
	}
}
