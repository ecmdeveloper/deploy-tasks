package com.ecmdeveloper.ant.ceactions;

import java.text.MessageFormat;
import java.util.Iterator;

import org.apache.tools.ant.BuildException;

import com.filenet.api.admin.LocalizedString;
import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.core.Factory;
import com.filenet.api.core.IndependentObject;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.events.ClassSubscription;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.Id;

public abstract class ObjectStoreAction {

	public <T extends IndependentObject> T getByDisplayName(String name, Class<T> type, ObjectStore objectStore) {
		return getByDisplayName(name,type, "", objectStore);
	}
	
	public <T extends IndependentObject> T getByDisplayName(String name, Class<T> type, String filter, ObjectStore objectStore) {

		String queryFormat = "SELECT [This] FROM [{0}] WHERE ([DisplayName] = ''{1}'')";
		if ( !filter.isEmpty() ) {
			queryFormat += "AND " + filter;
		}
		
		String query = MessageFormat.format(queryFormat, type.getSimpleName(), name );
		return doQuery(type, objectStore, query);
	}

	public <T extends IndependentObject> T getByName(String name, Class<T> type, String filter, ObjectStore objectStore) {

		String queryFormat = "SELECT [This] FROM [{0}] WHERE ([DocumentTitle] = ''{1}'')";
		if ( !filter.isEmpty() ) {
			queryFormat += " AND ( " + filter + " )";
		}
		
		String query = MessageFormat.format(queryFormat, type.getSimpleName(), name );
		return doQuery(type, objectStore, query);
	}

	public <T extends IndependentObject> T getById(Id id, Class<T> type, ObjectStore objectStore) {
		String queryFormat = "SELECT [This] FROM [{0}] WHERE ([Id] = ''{1}'')";
		String query = MessageFormat.format(queryFormat, type.getSimpleName(), id );
		return doQuery(type, objectStore, query);
	}
	
	public <T extends IndependentObject> T getBySymbolicName(String name, Class<T> type, ObjectStore objectStore) {
		final String queryFormat = "SELECT [This] FROM [{0}] WHERE ([SymbolicName] = ''{1}'')";
		final String query = MessageFormat.format(queryFormat, type.getSimpleName(), name );
		return doQuery(type, objectStore, query);
	}
	
	public <T extends IndependentObject> T doQuery(Class<T> type,
			ObjectStore objectStore, String query) {
		SearchScope scope = new SearchScope( objectStore );
		IndependentObjectSet objects = scope.fetchObjects(new SearchSQL(query), null, null, Boolean.FALSE);
		Iterator<?> iterator = objects.iterator();

		if ( !iterator.hasNext() )
		{
			return null;
		}
		return type.cast(iterator.next());
	}
	
	protected LocalizedString createLocalizedString(String name, String localeName) {
		LocalizedString localizedString = Factory.LocalizedString.createInstance();

		localizedString.set_LocalizedText( name );
		localizedString.set_LocaleName(localeName);
		return localizedString;
	}
	
	protected <T extends IndependentObject> T getByIdOrDisplayName(Class<T> type, String id, String displayName, ObjectStore objectStore) {
		T independentObject = null;
		if ( id != null ) {
			independentObject = getById( new Id(id), type, objectStore);
		} else if ( displayName != null ) {
			independentObject = getByDisplayName(displayName, type, objectStore);
		} else {
			throw new BuildException(type.getName() + " must be identified by id or display name");
		}
		return independentObject;
	}
}
