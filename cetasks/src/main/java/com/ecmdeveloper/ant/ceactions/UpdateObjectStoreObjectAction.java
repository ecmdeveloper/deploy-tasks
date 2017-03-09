/**
 * 
 */
package com.ecmdeveloper.ant.ceactions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.ecmdeveloper.ant.cetasks.ObjectStoreNestedTask;
import com.ecmdeveloper.ant.cetypes.PropertyValue;
import com.filenet.api.core.IndependentlyPersistableObject;

/**
 * @author Ricardo Belfor
 *
 */
public class UpdateObjectStoreObjectAction {

	private ArrayList<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
	
	public void execute(ObjectStoreNestedTask task, Collection<IndependentlyPersistableObject> engineObjects) {
		for (IndependentlyPersistableObject engineObject : engineObjects ) {
			if ( !propertyValues.isEmpty() ) {
				for ( PropertyValue propertyValue : propertyValues ) {
					engineObject.getProperties().putObjectValue(propertyValue.getName(), propertyValue.getObjectValue() );
				}
			}
		}
	}

	public void execute(ObjectStoreNestedTask task, IndependentlyPersistableObject object) {
		execute( task, Arrays.asList(object) );
	}
	
	public void add(PropertyValue propertyValue) {
		propertyValues.add(propertyValue);
	} 
}
