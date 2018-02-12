/**
 * 
 */
package com.ecmdeveloper.ant.ceactions.events;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import com.ecmdeveloper.ant.ceactions.ObjectStoreAction;
import com.filenet.api.admin.CodeModule;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.events.EventAction;
import com.filenet.api.util.Id;

/**
 * @author Ricardo Belfor
 *
 */
public class EventActionAction extends ObjectStoreAction {

	private String name;
	private String id;
	private String description;
	private String codeModuleName;
	private String progId;
	private String scriptText;
		
	public void execute(ObjectStore objectStore, Task task) {
		
		EventAction eventAction = getByIdOrDisplayName(EventAction.class, new Id(id), name, objectStore);

		if ( eventAction == null ) {
			task.log("Creating event action '" + name + "'", Project.MSG_VERBOSE);
			eventAction = Factory.EventAction.createInstance(objectStore, null, new Id(id) );
		} else {
			eventAction.refresh( new String[] { PropertyNames.DISPLAY_NAME });
			task.log("Updating event action '" + name + "'", Project.MSG_VERBOSE);
		}

		eventAction.set_CodeModule( getCodeModule(objectStore, task) );
		eventAction.set_ProgId(progId);
		eventAction.set_DisplayName(name);
		eventAction.set_DescriptiveText(description);
		eventAction.set_ScriptText(scriptText);
		
		eventAction.save(RefreshMode.NO_REFRESH);
	}

	private CodeModule getCodeModule(ObjectStore objectStore, Task task) {
		CodeModule codeModule = getByName(codeModuleName, CodeModule.class, " IsCurrentVersion = TRUE", objectStore );
		if ( codeModule == null) {
			throw new BuildException("Code Module '" + codeModuleName + "' not found");
		}
		codeModule.refresh();
		task.log("\tCode Module version is " + codeModule.get_MajorVersionNumber() + "." + codeModule.get_MinorVersionNumber() );
		return codeModule;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCodeModuleName(String codeModuleName) {
		this.codeModuleName = codeModuleName;
	}

	public void setProgId(String progId) {
		this.progId = progId;
	}

	public void setScriptText(String scriptText) {
		this.scriptText = scriptText;
	}
}
