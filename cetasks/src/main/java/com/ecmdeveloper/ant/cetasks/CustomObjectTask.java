/**
 * 
 */
package com.ecmdeveloper.ant.cetasks;

import org.apache.tools.ant.BuildException;

import com.ecmdeveloper.ant.ceactions.ObjectStoreAction;
import com.ecmdeveloper.ant.ceactions.UpdateObjectStoreObjectAction;
import com.ecmdeveloper.ant.ceactions.UpdateSecurityAction;
import com.ecmdeveloper.ant.cetypes.PermissionValue;
import com.ecmdeveloper.ant.cetypes.PropertyValue;
import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.CustomObject;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ReferentialContainmentRelationship;
import com.filenet.api.util.Id;

/**
 * @author Ricardo Belfor
 *
 */
public class CustomObjectTask extends ObjectStoreNestedTask {

	private String className;
	private String parentPath;
	private String name;
	private CustomObject customObject;
	
	private UpdateObjectStoreObjectAction updateObjectStoreObjectAction = new UpdateObjectStoreObjectAction();
	private UpdateSecurityAction updateSecurityAction = new UpdateSecurityAction();
	
	private String id;
	
	
	public void execute() throws BuildException {

		boolean newCustomObject = false;
		
		if ( id == null ) {
			customObject = Factory.CustomObject.createInstance(getObjectStore(), className);
		} else {
			ObjectStoreAction objectStoreAction = new ObjectStoreAction() {};
			String query = "SELECT [This] FROM CustomObject WHERE Id = " + id;
			customObject = objectStoreAction.doQuery(CustomObject.class, getObjectStore(), query);
			if (customObject == null) {
				customObject = Factory.CustomObject.createInstance(getObjectStore(), className, new Id(id) );
				newCustomObject = true;
			}
		}
		
		updateObjectStoreObjectAction.execute(this, customObject);
		updateSecurityAction.execute(this, customObject);
		
		customObject.save(RefreshMode.REFRESH);
		
		if ( newCustomObject && parentPath != null) {
			log("filing custom object to '" + parentPath + "'");
			Folder folder = Factory.Folder.fetchInstance(getObjectStore(), parentPath, null);
			ReferentialContainmentRelationship relationship = folder.file(customObject, AutoUniqueName.NOT_AUTO_UNIQUE, name, DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
			relationship.save(RefreshMode.NO_REFRESH);
		}
		
	}

	public void addConfigured(PropertyValue propertyValue) {
		updateObjectStoreObjectAction.add(propertyValue);
	}
	
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
		
	}

	/**
	 * @param parentPath the parentPath to set
	 */
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
	
	public void addConfigured(PermissionValue permissionValue) {
		updateSecurityAction.add(permissionValue);
	}
}
