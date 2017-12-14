/**
 * 
 */
package com.ecmdeveloper.ant.cetasks;

import org.apache.tools.ant.BuildException;

import com.ecmdeveloper.ant.ceactions.ObjectStoreAction;
import com.ecmdeveloper.ant.ceactions.UpdatePropertiesAction;
import com.ecmdeveloper.ant.ceactions.UpdateSecurityAction;
import com.ecmdeveloper.ant.cetypes.PermissionValue;
import com.ecmdeveloper.ant.cetypes.PropertyValue;
import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.CustomObject;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ReferentialContainmentRelationship;
import com.filenet.api.core.UpdatingBatch;
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
	
	private UpdatePropertiesAction updateObjectStoreObjectAction = new UpdatePropertiesAction();
	private UpdateSecurityAction updateSecurityAction = new UpdateSecurityAction();
	
	private String id;
	private String filter;
	
	public void execute() throws BuildException {

		boolean newCustomObject = false;

		UpdatingBatch batch = UpdatingBatch.createUpdatingBatchInstance(getObjectStore().get_Domain(), RefreshMode.NO_REFRESH);

		ObjectStoreAction objectStoreAction = new ObjectStoreAction() {};
		String query = "SELECT [This] FROM CustomObject WHERE " + getWhereClause();
		customObject = objectStoreAction.doQuery(CustomObject.class, getObjectStore(), query); // TODO make sure only on object matches?
		
		if ( customObject == null) {
			newCustomObject = true;
			log("Creating new Custom Object");
			if ( id == null ) {
				customObject = Factory.CustomObject.createInstance(getObjectStore(), className);
			} else {
				customObject = Factory.CustomObject.createInstance(getObjectStore(), className, new Id(id) );
			}
		} else {
			log("Updating existing Custom Object");
		}
		
		updateObjectStoreObjectAction.execute(this, customObject);
		updateSecurityAction.execute(this, customObject);
		
		batch.add(customObject, null);
		
		if ( newCustomObject && parentPath != null) {
			log("filing custom object to '" + parentPath + "'");
			Folder folder = Factory.Folder.fetchInstance(getObjectStore(), parentPath, null);
			ReferentialContainmentRelationship relationship = folder.file(customObject, AutoUniqueName.NOT_AUTO_UNIQUE, name, DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
			batch.add(relationship, null);
		}

		batch.updateBatch();
	}

	private String getWhereClause() {
		if ( id != null ) {
			return "Id = " + id;
		} else if ( filter == null) {
			throw new BuildException("You must either provide a filter of an id value");
		}
		return filter;
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
	
	public void addConfiguredPermission(PermissionValue permissionValue) {
		log("Adding permission value");
		updateSecurityAction.add(permissionValue);
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}
}
