/**
 * 
 */
package com.ecmdeveloper.ant.ceactions;

import java.text.MessageFormat;
import java.util.Iterator;

import com.filenet.api.admin.ClassDefinition;
import com.filenet.api.admin.PropertyTemplate;
import com.filenet.api.collection.RepositoryRowSet;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.FilterElement;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.query.RepositoryRow;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;

/**
 * @author Ricardo Belfor
 *
 */
public abstract class AbstractClassDefinitionAction extends ObjectStoreAction {

	public ClassDefinition getBySymbolicName(String name, ObjectStore objectStore) {
		String queryFormat = "SELECT [This], PropertyDefinitions, Id FROM [ClassDefinition] WHERE ([SymbolicName] = ''{0}'')";
		String query = MessageFormat.format(queryFormat, name );
		return doQuery(ClassDefinition.class, objectStore, query);
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
