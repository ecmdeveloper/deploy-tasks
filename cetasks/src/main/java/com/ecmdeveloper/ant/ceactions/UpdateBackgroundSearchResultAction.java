package com.ecmdeveloper.ant.ceactions;

import java.text.MessageFormat;
import java.util.Iterator;

import org.apache.tools.ant.BuildException;

import com.ecmdeveloper.ant.cetasks.ImportBackgroundSearchResultTask;
import com.ecmdeveloper.ant.cetypes.PropertyTemplateValue;
import com.filenet.api.admin.ClassDefinition;
import com.filenet.api.admin.LocalizedString;
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
	public void execute(ImportBackgroundSearchResultTask task) {
		
		ObjectStore objectStore = task.getObjectStore();
		
		ClassDefinition parentClassDefinition = Factory.ClassDefinition.fetchInstance(objectStore, "CmAbstractSearchResult", null);
		ClassDefinition classDefinition = parentClassDefinition.createSubclass();

		LocalizedString localizedString = createLocalizedString(task.getName(),  task.getObjectStore().get_LocaleName() );

		classDefinition.set_DisplayNames(Factory.LocalizedString.createList());
		classDefinition.get_DisplayNames().add( localizedString );
		classDefinition.set_SymbolicName( task.getSymbolicName() );

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
		PropertyDefinitionList propertyDefinition = classDefinition.get_PropertyDefinitions(); 

		for ( PropertyTemplateValue propertTemplateValue : task.getPropertyTemplateValues() ) {
			PropertyTemplate propertyTemplate = getPropertyTemplate(propertTemplateValue.getName(), objectStore);
			
			if ( propertyTemplate == null) {
				throw new BuildException("Property template '" + propertTemplateValue.getName() + "' not found");
			}
			
			PropertyDefinition objPropDef = propertyTemplate.createClassProperty();
			propertyDefinition.add(objPropDef);
		}
		classDefinition.save(RefreshMode.REFRESH);		
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
