/**
 * 
 */
package com.ecmdeveloper.ant.ceactions;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.tools.ant.Task;

import com.ecmdeveloper.ant.cetasks.ObjectStoreNestedTask;
import com.ecmdeveloper.ant.cetypes.PermissionValue;
import com.ecmdeveloper.ant.cetypes.PropertyValue;
import com.filenet.api.collection.AccessPermissionList;
import com.filenet.api.constants.AccessType;
import com.filenet.api.core.Containable;
import com.filenet.api.core.Factory;
import com.filenet.api.core.IndependentlyPersistableObject;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.security.AccessPermission;

/**
 * @author Ricardo Belfor
 *
 */
public class UpdateSecurityAction {

	private ArrayList<PermissionValue> permissionValues = new ArrayList<PermissionValue>();
	
	@SuppressWarnings("unchecked")
	public void execute(ObjectStoreNestedTask task, Containable object) {
		
		AccessPermissionList permissionList = Factory.AccessPermission.createList();
		
		if ( !permissionValues.isEmpty() ) {
			for (PermissionValue permissionValue : permissionValues ) {
				permissionList.add( createPermission(permissionValue, task) );
			}
		}
		
		object.set_Permissions(permissionList);
	}

	private AccessPermission createPermission(PermissionValue permissionValue, ObjectStoreNestedTask task) {
	
		task.log("\tAdding permission for principal " + permissionValue.getGranteeName() );
		
		AccessPermission permission = Factory.AccessPermission.createInstance();
		permission.set_GranteeName(permissionValue.getGranteeName() );
		permission.set_AccessMask(permissionValue.getAccessMask() );
		permission.set_InheritableDepth( permissionValue.getInheritableDepth() );
		permission.set_AccessType(AccessType.ALLOW);
		
		return permission;
	}
	
	public void add(PermissionValue permissionValue) {
		permissionValues.add(permissionValue);
	} 
}
