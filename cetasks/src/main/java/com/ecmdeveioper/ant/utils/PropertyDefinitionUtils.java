/**
 * 
 */
package com.ecmdeveioper.ant.utils;

import java.util.Iterator;

import com.filenet.api.admin.PropertyDefinition;
import com.filenet.api.collection.PropertyDefinitionList;
import com.filenet.api.exception.EngineRuntimeException;

/**
 * @author ricardobelfor
 *
 */
public class PropertyDefinitionUtils {

	public static boolean containsProperty(PropertyDefinitionList propertyDefinitions, String name) {
		@SuppressWarnings("unchecked")
		Iterator<PropertyDefinition> iterator = propertyDefinitions.iterator();
		boolean found = false;
		while (iterator.hasNext()) {
			PropertyDefinition p = iterator.next();
			
			String propertyName = "";
			try {
				propertyName = p.get_SymbolicName();
			} catch (EngineRuntimeException erte) {
				// Somehow some properties don't have a symbolic name...
			}
			
			if ( propertyName.equals(name ) ) {
				found = true;
				break;
			}
		}
		return found;
	}
	
}
