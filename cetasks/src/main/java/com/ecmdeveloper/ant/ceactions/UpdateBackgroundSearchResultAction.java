package com.ecmdeveloper.ant.ceactions;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.tools.ant.BuildException;

import com.ecmdeveloper.ant.cetasks.ImportBackgroundSearchResultTask;
import com.ecmdeveloper.ant.cetypes.PropertyTemplateValue;
import com.filenet.api.admin.ClassDefinition;
import com.filenet.api.admin.PropertyDefinition;
import com.filenet.api.admin.PropertyTemplate;
import com.filenet.api.collection.PropertyDefinitionList;
import com.filenet.api.collection.RepositoryRowSet;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.FilterElement;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.query.RepositoryRow;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;

public class UpdateBackgroundSearchResultAction extends ClassDefinitionAction {

	@SuppressWarnings("unchecked")
	public void execute(ImportBackgroundSearchResultTask task, String description) {
		
		ObjectStore objectStore = task.getObjectStore();
		
		ClassDefinition classDefinition = getBySymbolicName(task.getSymbolicName(), objectStore);
		if ( classDefinition == null ) {
			ClassDefinition parentClassDefinition = Factory.ClassDefinition.fetchInstance(objectStore, "CmAbstractSearchResult", null);
			classDefinition = parentClassDefinition.createSubclass();
			classDefinition.set_SymbolicName( task.getSymbolicName() );
		}

		String localeName = task.getObjectStore().get_LocaleName();

		classDefinition.set_DisplayNames(Factory.LocalizedString.createList());
		classDefinition.get_DisplayNames().add( createLocalizedString(task.getName(),  localeName ) );

		if ( description != null) {
			classDefinition.set_DescriptiveTexts(Factory.LocalizedString.createList());
			classDefinition.get_DescriptiveTexts().add( createLocalizedString(description, localeName) );
		}
/*		
		// Create property template for DateTime property to use for property mapping (DateCreated AS DocCreationDate).
		String strTemplateName = "DocCreationDate";
		PropertyTemplateDateTime objPropTemplate = Factory.PropertyTemplateDateTime.createInstance(objectStore);

		// Set cardinality of properties that will be created from the property template.
		objPropTemplate.set_Cardinality(Cardinality.SINGLE);

		// Define locale for property template.
		LocalizedString objLocStrPT = Factory.LocalizedString.createInstance();
		objLocStrPT.set_LocalizedText(strTemplateName);
		objLocStrPT.set_LocaleName(objectStore.get_LocaleName());

		// Set display name for property template.
		objPropTemplate.set_DisplayNames(Factory.LocalizedString.createList());
		objPropTemplate.get_DisplayNames().add(objLocStrPT);

		// Save new property template to the server.
		objPropTemplate.save(RefreshMode.REFRESH);
*/
		ArrayList<PropertyTemplateValue> propertyTemplateValues = task.getPropertyTemplateValues();
		addPropertyDefinitions(objectStore, classDefinition, propertyTemplateValues);
		classDefinition.save(RefreshMode.REFRESH);		
	}

	private void addPropertyDefinitions(ObjectStore objectStore,
			ClassDefinition classDefinition,
			ArrayList<PropertyTemplateValue> propertyTemplateValues) {
		PropertyDefinitionList propertyDefinitionList = classDefinition.get_PropertyDefinitions(); 

		for ( PropertyTemplateValue propertTemplateValue : propertyTemplateValues ) {
			
			String name = propertTemplateValue.getName();
			if ( containsProperty(propertyDefinitionList, name) ) {
				continue;
			};			
			
			PropertyTemplate propertyTemplate = getPropertyTemplate(name, objectStore);
			
			if ( propertyTemplate == null) {
				throw new BuildException("Property template '" + name + "' not found");
			}

			PropertyDefinition objPropDef = propertyTemplate.createClassProperty();
			propertyDefinitionList.add(objPropDef);
		}
	}

	private boolean containsProperty(PropertyDefinitionList propertyDefinition,
			String name) {
		Iterator<PropertyDefinition> iterator = propertyDefinition.iterator();
		boolean found = false;
		while (iterator.hasNext()) {
			PropertyDefinition p = iterator.next();
			if ( p.get_SymbolicName().equals(name ) ) {
				found = true;
				break;
			}
		}
		return found;
	}

	public PropertyTemplate getPropertyTemplate(String name, ObjectStore objectStore ) 
	{
		String queryFormat = "SELECT [This] FROM [PropertyTemplate] WHERE ([SymbolicName] = ''{0}'')";		
		SearchScope scope = new SearchScope( objectStore );
		PropertyFilter pf = new PropertyFilter();
		pf.addIncludeProperty( new FilterElement(0, null, Boolean.TRUE, PropertyNames.ID, null) );
		String query = MessageFormat.format(queryFormat, name );
		RepositoryRowSet fetchRows = scope.fetchRows(new SearchSQL( query ), new Integer(1999), pf, Boolean.TRUE );
		Iterator<?> iterator = fetchRows.iterator();
		if ( !iterator.hasNext() )
		{
			return null;
		}

		RepositoryRow row = (RepositoryRow) iterator.next();
		return (PropertyTemplate) row.getProperties().getObjectValue("This");
	}
	
}
