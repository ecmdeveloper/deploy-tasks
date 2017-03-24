/**
 * 
 */
package com.ecmdeveloper.ant.ceactions;

import java.text.MessageFormat;

import com.filenet.api.admin.ClassDefinition;
import com.filenet.api.admin.LocalizedString;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;

/**
 * @author Ricardo Belfor
 *
 */
public abstract class ClassDefinitionAction extends ObjectStoreAction {

	protected LocalizedString createLocalizedString(String name, String localeName) {
		LocalizedString localizedString = Factory.LocalizedString.createInstance();

		localizedString.set_LocalizedText( name );
		localizedString.set_LocaleName(localeName);
		return localizedString;
	}	
	
	public ClassDefinition getBySymbolicName(String name, ObjectStore objectStore) {
		String queryFormat = "SELECT [This], PropertyDefinitions FROM [ClassDefinition] WHERE ([SymbolicName] = ''{0}'')";
		String query = MessageFormat.format(queryFormat, name );
		return doQuery(ClassDefinition.class, objectStore, query);
	}	
}
