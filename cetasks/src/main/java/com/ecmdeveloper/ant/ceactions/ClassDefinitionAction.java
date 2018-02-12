/**
 * 
 */
package com.ecmdeveloper.ant.ceactions;

import java.util.ArrayList;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.ecmdeveioper.ant.utils.PropertyDefinitionUtils;
import com.ecmdeveloper.ant.cetypes.propertydefs.BasePropertyDefinition;
import com.ecmdeveloper.ant.cetypes.propertydefs.ObjectPropertyDefinition;
import com.ecmdeveloper.ant.cetypes.propertydefs.StringPropertyDefinition;
import com.filenet.api.admin.ClassDefinition;
import com.filenet.api.admin.PropertyDefinition;
import com.filenet.api.admin.PropertyDefinitionObject;
import com.filenet.api.admin.PropertyDefinitionString;
import com.filenet.api.admin.PropertyTemplate;
import com.filenet.api.collection.PropertyDefinitionList;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.Id;

/**
 * @author Ricardo Belfor
 *
 */
public class ClassDefinitionAction extends AbstractClassDefinitionAction {
	
	private String name;
	private String id;
	private String symbolicName;
	private String parentClass;
	private String description;
	private ObjectStore objectStore;
	private Task task;

	private ArrayList<BasePropertyDefinition> propertyDefinitions = new ArrayList<BasePropertyDefinition>();
	
	@SuppressWarnings("unchecked")
	public void execute(ObjectStore objectStore, Task task) {
		this.objectStore = objectStore;
		this.task = task;
	
		ClassDefinition classDefinition = getBySymbolicName(symbolicName, objectStore);
		boolean newDefinition;
		if ( classDefinition == null ) {
			task.log("Creating Class Definition '" +  symbolicName + "'");
			ClassDefinition parentClassDefinition = Factory.ClassDefinition.fetchInstance(objectStore, parentClass, null);
			classDefinition = parentClassDefinition.createSubclass(id != null? new Id(id) : null ); 
			classDefinition.set_SymbolicName( symbolicName );
			newDefinition = true;
		} else {
			task.log("Updating Background Search Result Class '" + symbolicName + "'");
			classDefinition = Factory.ClassDefinition.fetchInstance(objectStore, classDefinition.get_Id(), null);
			newDefinition = false;
		}
		
		String localeName = objectStore.get_LocaleName();

		classDefinition.set_DisplayNames(Factory.LocalizedString.createList());
		classDefinition.get_DisplayNames().add( createLocalizedString(name,  localeName ) );

		if ( description != null) {
			classDefinition.set_DescriptiveTexts(Factory.LocalizedString.createList());
			classDefinition.get_DescriptiveTexts().add( createLocalizedString(description, localeName) );
		}

		addPropertyDefinitions(objectStore, classDefinition, newDefinition);
		classDefinition.save(RefreshMode.REFRESH);		
	}

	@SuppressWarnings("unchecked")
	private void addPropertyDefinitions(ObjectStore objectStore2,
			ClassDefinition classDefinition, boolean newDefinition) {
		
		PropertyDefinitionList propertyDefinitionList = classDefinition.get_PropertyDefinitions(); 

		for ( BasePropertyDefinition propertDefinition : propertyDefinitions ) {
			
			String name = propertDefinition.getName();
			if ( !newDefinition && PropertyDefinitionUtils.containsProperty(propertyDefinitionList, name) ) {
				continue;
			};			
			
			PropertyTemplate propertyTemplate = getPropertyTemplate(name, objectStore);
			
			if ( propertyTemplate == null) {
				throw new BuildException("Property template '" + name + "' not found");
			}

			PropertyDefinition objPropDef = propertyTemplate.createClassProperty();
			
			if ( propertDefinition.getHidden() != null) {
				objPropDef.set_IsHidden(propertDefinition.getHidden());
			} 
			
			if ( propertDefinition.getRequired() != null ) {
				objPropDef.set_IsValueRequired(propertDefinition.getRequired() );
			}
			
			if ( objPropDef instanceof PropertyDefinitionString ) {
				
				StringPropertyDefinition stringPropDef = (StringPropertyDefinition) propertDefinition;
				if (stringPropDef.getMaximumLength() != null ) {
					((PropertyDefinitionString) objPropDef).set_MaximumLengthString(stringPropDef.getMaximumLength());
				}
			}
			
			if ( objPropDef instanceof PropertyDefinitionObject ) {
				String requiredClass = ((ObjectPropertyDefinition)propertDefinition).getRequiredClass();
				ClassDefinition requiredClassDefinition = getBySymbolicName(requiredClass, objectStore);
				((PropertyDefinitionObject) objPropDef).set_RequiredClassId(requiredClassDefinition.get_Id() );
			}
			
			propertyDefinitionList.add(objPropDef);
		}
	}

	public void add(BasePropertyDefinition propertyDefinition) {
		propertyDefinitions.add(propertyDefinition);
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setSymbolicName(String symbolicName) {
		this.symbolicName = symbolicName;
	}

	public void setParentClass(String parentClass) {
		this.parentClass = parentClass;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setObjectStore(ObjectStore objectStore) {
		this.objectStore = objectStore;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void setId(String id) {
		this.id = id;
	}
}
