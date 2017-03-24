/**
 * 
 */
package com.ecmdeveloper.ant.ceactions;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.tools.ant.Task;

import com.filenet.api.admin.CodeModule;
import com.filenet.api.collection.ActionSet;
import com.filenet.api.collection.VersionableSet;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.VersionSeries;
import com.filenet.api.events.Action;
import com.filenet.api.property.FilterElement;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.util.Id;

/**
 * @author Ricardo Belfor
 *
 */
public class UpdateCodeModuleActionsAction {

	private ObjectStore objectStore;
	private Task task;

	public UpdateCodeModuleActionsAction(ObjectStore objectStore, Task task) {
		this.objectStore = objectStore;
		this.task = task;
	}
	
	public void execute(String codeModuleId) {
		
		task.log("Updating Code Module Actions");

		CodeModule codeModule = Factory.CodeModule.fetchInstance(objectStore, new Id(codeModuleId), null);
		for (Action  action : getCodeModuleActions(codeModule) ) 
		{
			action.set_CodeModule(codeModule);
			action.save(RefreshMode.NO_REFRESH);
		}
	}

	private Collection<Action> getCodeModuleActions(CodeModule codeModule) {
		VersionableSet versions = getCodeModuleVersionsAndActions(codeModule);
		Iterator<?> versionsIterator = versions.iterator();
		Set<Action> actions = new HashSet<Action>();
		
		while ( versionsIterator.hasNext() ) {
			
			Document document = (Document) versionsIterator.next();
			ActionSet actionSet = ((CodeModule)document).get_ReferencingActions();
			Iterator<?> iterator = actionSet.iterator();
	
			while (iterator.hasNext()) {
				Action action = (Action) iterator.next();
				actions.add( action );
			}
		}
		
		return actions;
	}

	private VersionableSet getCodeModuleVersionsAndActions(CodeModule codeModule) {
		VersionSeries versionSeries = codeModule.get_VersionSeries(); 

		PropertyFilter propertyFilter = new PropertyFilter();
		propertyFilter.addIncludeProperty( new FilterElement(null, null, null, PropertyNames.VERSIONS, null ) );
		propertyFilter.addIncludeProperty( new FilterElement(null, null, null, PropertyNames.REFERENCING_ACTIONS, null ) );
		versionSeries.fetchProperties(propertyFilter);
		
		VersionableSet versions = versionSeries.get_Versions();
		return versions;
	}	
	
}
