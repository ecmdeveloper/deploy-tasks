/**
 * 
 */
package com.ecmdeveloper.ant.cetasks;

import org.apache.tools.ant.BuildException;

import com.ecmdeveloper.ant.ceactions.ClassDefinitionAction;
import com.ecmdeveloper.ant.cetypes.PermissionValue;
import com.ecmdeveloper.ant.cetypes.PropertyTemplateValue;
import com.ecmdeveloper.ant.cetypes.propertydefs.BasePropertyDefinition;
import com.ecmdeveloper.ant.cetypes.propertydefs.ObjectPropertyDefinition;

/**
 * @author Ricardo Belfor
 *
 */
public class ClassDefinitionTask extends ObjectStoreNestedTask{

	private ClassDefinitionAction action = new ClassDefinitionAction();

	public void execute() throws BuildException {

		try {
			action.execute(getObjectStore(), this);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
	}
	
	public void setName(String name) {
		action.setName(name);
	}

	public void setSymbolicName(String symbolicName) {
		action.setSymbolicName(symbolicName);
	}
	
	public void addConfigured(BasePropertyDefinition propertyDefinition) {
		action.add(propertyDefinition);
	}

	public void addConfigured(ObjectPropertyDefinition propertyDefinition) {
		action.add(propertyDefinition);
	}

	public void addConfiguredInstancepermission(PermissionValue permissionValue ) {
		action.addInstancePermission(permissionValue);
	}
	
	public void setDescription(String description) {
		action.setDescription(description);
	}

	public void setId(String id) {
		action.setId(id);
	}
	
	public void addText(String text) {
		if ( !text.trim().isEmpty() ) {
			action.setDescription(text.trim());
		}
	}
	
	public void setParentClass(String parentClass) {
		action.setParentClass(parentClass);
	}
}
