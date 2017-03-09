package com.ecmdeveloper.ant.cetasks;

import java.text.MessageFormat;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import com.filenet.api.core.ObjectStore;

public class ObjectStoreNestedTask extends Task {

	private ObjectStore objectStore;
	protected int verbosity = Project.MSG_INFO;

	public ObjectStore getObjectStore() {
		return objectStore;
	}

	public void setObjectStore(ObjectStore objectStore) {
		this.objectStore = objectStore;
	}
	
	protected void checkForNull(String attributeName, String attributeValue) {
	    if ( attributeValue == null || attributeValue.isEmpty() ) {
	        throw new BuildException(MessageFormat.format("The attribute ''{0}'' was not set", attributeName), getLocation() );
	    }
	}

	protected void checkForNull(String attributeName, Object attributeValue) {
	    if ( attributeValue == null ) {
	        throw new BuildException(MessageFormat.format("The attribute ''{0}'' was not set", attributeName), getLocation() );
	    }
	}
	
    public void setVerbose(boolean verbose) {
        if (verbose) {
            this.verbosity  = Project.MSG_INFO;
        } else {
            this.verbosity = Project.MSG_VERBOSE;
        }
    }
}
