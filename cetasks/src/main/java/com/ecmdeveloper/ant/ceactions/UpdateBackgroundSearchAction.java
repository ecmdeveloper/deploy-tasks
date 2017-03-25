/**
 * 
 */
package com.ecmdeveloper.ant.ceactions;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.ecmdeveloper.ant.cetasks.ImportBackgroundSearchTask;
import com.filenet.api.admin.ClassDefinition;
import com.filenet.api.admin.LocalizedString;
import com.filenet.api.admin.PropertyDefinition;
import com.filenet.api.admin.PropertyDefinitionObject;
import com.filenet.api.admin.PropertyDefinitionString;
import com.filenet.api.collection.PropertyDefinitionList;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.sweep.CmBackgroundSearch;

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
	public void execute(String name, String symbolicName, String parentClass, String searchResultsClass, String query, String description) {

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
			
/*
		// Create property template for a DateTime custom property to use for the search parameter named StartDate.
		String strTemplateName = "StartDate";
		PropertyTemplateDateTime objPropTemplate = Factory.PropertyTemplateDateTime.createInstance(objectStore);

		// Set cardinality of properties that will be created from the property template.
		objPropTemplate.set_Cardinality(Cardinality.SINGLE);

		// Define locale for template.
		LocalizedString objLocStrPT = Factory.LocalizedString.createInstance();
		objLocStrPT.set_LocalizedText(strTemplateName);
		objLocStrPT.set_LocaleName(objectStore.get_LocaleName());

		// Set template display name.
		objPropTemplate.set_DisplayNames(Factory.LocalizedString.createList());
		objPropTemplate.get_DisplayNames().add(objLocStrPT);

		// Save new property template to the server.
		objPropTemplate.save(RefreshMode.REFRESH);
		// Create property definition from property template.
		PropertyDefinitionDateTime objPropDefDT = (PropertyDefinitionDateTime) objPropTemplate.createClassProperty();

		// Create custom property by adding the new property definition to the CmBackgroundSearch subclass definition.
		objPropDefs.add(objPropDefDT);
*/
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
