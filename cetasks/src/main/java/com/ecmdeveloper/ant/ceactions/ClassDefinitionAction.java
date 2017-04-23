/**
 * 
 */
package com.ecmdeveloper.ant.ceactions;

import java.text.MessageFormat;

import com.filenet.api.admin.ClassDefinition;
import com.filenet.api.core.ObjectStore;

/**
 * @author Ricardo Belfor
 *
 */
public abstract class ClassDefinitionAction extends ObjectStoreAction {

	public ClassDefinition getBySymbolicName(String name, ObjectStore objectStore) {
		String queryFormat = "SELECT [This], PropertyDefinitions, Id FROM [ClassDefinition] WHERE ([SymbolicName] = ''{0}'')";
		String query = MessageFormat.format(queryFormat, name );
		return doQuery(ClassDefinition.class, objectStore, query);
	}	
}
