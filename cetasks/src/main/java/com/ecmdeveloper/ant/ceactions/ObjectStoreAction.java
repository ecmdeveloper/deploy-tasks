package com.ecmdeveloper.ant.ceactions;

import java.text.MessageFormat;
import java.util.Iterator;

import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.core.IndependentObject;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;

public abstract class ObjectStoreAction {

//	protected ObjectStore objectStore;
//	protected Task task;
//	
//	public ObjectStoreAction(ObjectStore objectStore, Task task) {
//		this.objectStore = objectStore;
//		this.task = task;
//	}
	
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
}
