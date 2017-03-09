/**
 * 
 */
package com.ecmdeveloper.ant.cetasks;

import java.util.ArrayList;

import org.apache.tools.ant.BuildException;

import com.ecmdeveloper.ant.cetypes.PropertyValue;
import com.filenet.api.action.PendingAction;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.IndependentlyPersistableObject;

/**
 * @author Ricardo Belfor
 *
 */
public class UpdateObjectStoreObjectTask extends ObjectStoreObjectTask {

	private ArrayList<PropertyValue> propertyValues = new ArrayList<PropertyValue>();

	public void execute() throws BuildException {
		
		try {
			log("Running update task");
			for (IndependentlyPersistableObject engineObject : getEngineObjects() ) {
				if ( !propertyValues.isEmpty() ) {
					for ( PropertyValue propertyValue : propertyValues ) {
						engineObject.getProperties().putObjectValue(propertyValue.getName(), propertyValue.getObjectValue() );
					}
				}
				
				if ( engineObject.getPendingActions().length > 0 ) {
					for ( PendingAction p : engineObject.getPendingActions() ) {
						log(p.toString());
					}
					engineObject.save(RefreshMode.NO_REFRESH);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
	}
	
	public void addConfigured(PropertyValue propertyValue) {
		propertyValues.add(propertyValue);
	} 
	
}
