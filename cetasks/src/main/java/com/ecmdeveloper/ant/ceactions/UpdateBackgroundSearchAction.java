/**
 * 
 */
package com.ecmdeveloper.ant.ceactions;

import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.ecmdeveioper.ant.utils.PropertyDefinitionUtils;
import com.filenet.api.admin.ClassDefinition;
import com.filenet.api.admin.LocalizedString;
import com.filenet.api.admin.PropertyDefinition;
import com.filenet.api.admin.PropertyDefinitionObject;
import com.filenet.api.admin.PropertyDefinitionString;
import com.filenet.api.admin.PropertyTemplate;
import com.filenet.api.collection.PropertyDefinitionList;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;

/**
 * @author Ricardo Belfor
 *
 */
public class UpdateBackgroundSearchAction extends ClassDefinitionAction {

	private ObjectStore objectStore;
	private Task task;

	public UpdateBackgroundSearchAction(ObjectStore objectStore, Task task) {
		this.objectStore = objectStore;
		this.task = task;
	}
	
	
	@SuppressWarnings("unchecked")
	public void execute(String name, String symbolicName, String parentClass, String searchResultsClass, String query, String description, List<String> properties) {

		ClassDefinition classDefinition = getBySymbolicName(symbolicName, objectStore);

		if ( classDefinition == null ) {
			task.log("Creating background search '" + name + "'");
			ClassDefinition parentClassDefinition = Factory.ClassDefinition.fetchInstance(objectStore, parentClass, null);
			classDefinition = parentClassDefinition.createSubclass();
			classDefinition.set_SymbolicName( symbolicName );
			
			ClassDefinition searchResultClass = Factory.ClassDefinition.fetchInstance(objectStore, searchResultsClass, null);
			PropertyDefinitionObject searchResultsDefinition = (PropertyDefinitionObject) getPropertyDefinition(classDefinition.get_PropertyDefinitions(), "SearchResults");
			searchResultsDefinition.set_RequiredClassId( searchResultClass.get_Id() );

		} else {
			task.log("Updating background search '" + name + "'");
		}
		
		LocalizedString localizedString = createLocalizedString(name,  objectStore.get_LocaleName() );
		classDefinition.set_DisplayNames(Factory.LocalizedString.createList());
		classDefinition.get_DisplayNames().add(localizedString);

		if ( description != null) {
			LocalizedString descriptionString = createLocalizedString(description, objectStore.get_LocaleName() );
			classDefinition.set_DescriptiveTexts( Factory.LocalizedString.createList() );
			classDefinition.get_DescriptiveTexts().add(descriptionString);
		}

		PropertyDefinitionList propertyDefinitions = classDefinition.get_PropertyDefinitions();
		PropertyDefinitionString searchExpressionDefintion = (PropertyDefinitionString) getPropertyDefinition(propertyDefinitions, "SearchExpression");
		searchExpressionDefintion.set_PropertyDefaultString( query );

		for (String property : properties) {
			if ( !PropertyDefinitionUtils.containsProperty(propertyDefinitions, property) ) {
				task.log("\tAdding search parameter '" + property + "'");
				PropertyTemplate propertyTemplate = getBySymbolicName(property, PropertyTemplate.class , objectStore);
				PropertyDefinition propertyDefinition = propertyTemplate.createClassProperty();
				propertyDefinitions.add(propertyDefinition);
			}
		}
		classDefinition.save(RefreshMode.REFRESH);	
	}

	private PropertyDefinition getPropertyDefinition(PropertyDefinitionList objPropDefs, String symbolicName) {
		for (int i=0; i < objPropDefs.size(); i++)
		{
			String strPropDefSymbolicName1 = ((PropertyDefinition) objPropDefs.get(i)).get_SymbolicName();
			if (strPropDefSymbolicName1.equalsIgnoreCase(symbolicName))
			{
				return (PropertyDefinition) objPropDefs.get(i);
			}
		}
		throw new BuildException("Property definition '" + symbolicName + "' not found");
	}
}
