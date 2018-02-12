/**
 * 
 */
package com.ecmdeveloper.ant.cetasks;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Echo;

import com.ecmdeveloper.ant.cetasks.propertytemplate.BooleanPropertyTemplateTask;
import com.ecmdeveloper.ant.cetasks.propertytemplate.DateTimePropertyTemplateTask;
import com.ecmdeveloper.ant.cetasks.propertytemplate.ObjectPropertyTemplateTask;
import com.ecmdeveloper.ant.cetasks.propertytemplate.StringPropertyTemplateTask;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;

/**
 * @author Ricardo Belfor
 *
 */
public class ObjectStoreTask extends Task {

    private String url;
    private String objectStoreName;
    private String userName;
    private String password;
	protected int verbosity = Project.MSG_INFO;

    private List<Task> tasks = new ArrayList<Task>();
	private ObjectStore objectStore;
	
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
 
    public String getObjectStoreName() {
		return objectStoreName;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	/**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
     
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
     
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
     
    /**
     * @param objectStoreName the objectStoreName to set
     */
    public void setObjectStoreName(String objectStoreName) {
        this.objectStoreName = objectStoreName;
    }
    
    protected void validateInput() {
        checkForNull("userName", userName);
        checkForNull("password", password);
        checkForNull("url", url);
        checkForNull("objectStoreName", objectStoreName);
    }
    
    protected void checkForNull(String attributeName, String attributeValue) {
        if ( attributeValue == null || attributeValue.isEmpty() ) {
            throw new BuildException(MessageFormat.format("The attribute ''{0}'' was not set", attributeName), getLocation());
        }
    }

    public void setObjectStore(ObjectStore objectStore) {
		this.objectStore = objectStore;
    	
    }
    
    protected ObjectStore getObjectStore() {
    	
    	if ( objectStore == null ) {
	        Connection connection = Factory.Connection.getConnection(getUrl());
	 
	        Subject sub = UserContext.createSubject(connection,getUserName(),getPassword(),null);
	        UserContext uc = UserContext.get();
	        uc.pushSubject(sub);
	        Domain domain = Factory.Domain.fetchInstance(connection, null, null);
	        objectStore = Factory.ObjectStore.fetchInstance(domain, getObjectStoreName(), null);
	         
	        log("Using object store '" + objectStore.get_Name() + "'", verbosity);
    	}
         
        return objectStore;
    }       
     
    public void setVerbose(boolean verbose) {
        if (verbose) {
            this.verbosity  = Project.MSG_INFO;
        } else {
            this.verbosity = Project.MSG_VERBOSE;
        }
    }

	public void addEcho(Echo nestedTask) {
		addTask(nestedTask);
	}
	
	protected boolean addTask(Task nestedTask) {
		return tasks.add(nestedTask);
	}
	
    public void execute() throws BuildException {
    	
    	validateInput();
    	
    	for ( Task task : getTasks() ) {
    		initializeTask(task);
    		task.perform();
    	}
    }

	protected List<Task> getTasks() {
		return tasks;
	}

	protected void initializeTask(Task task) {
		if ( task instanceof ObjectStoreNestedTask ) {
			((ObjectStoreNestedTask)task).setObjectStore(getObjectStore());
		}
	}
	
	public void addConfigured(ObjectStoreNestedTask objectStoreNestedTask) {
		log("Adding task " + objectStoreNestedTask.getTaskName() );
		
		tasks.add(objectStoreNestedTask);
	}

	public void addConfiguredUpdatecustomobject(CustomObjectTask objectStoreNestedTask) {
		log("Adding task " + objectStoreNestedTask.getTaskName() );
		
		tasks.add(objectStoreNestedTask);
	}
	
	public void addConfiguredDatetimepropertytemplate(DateTimePropertyTemplateTask task) {
		tasks.add(task);
	}

	public void addConfiguredStringpropertytemplate(StringPropertyTemplateTask task) {
		tasks.add(task);
	}

	public void addConfiguredObjectpropertytemplate(ObjectPropertyTemplateTask task) {
		tasks.add(task);
	}

	public void addConfiguredBooleanpropertytemplate(BooleanPropertyTemplateTask task) {
		tasks.add(task);
	}
	
	public void addConfiguredChoicelist(ChoiceListTask task) {
		tasks.add(task);
	}

	public void addConfiguredClassdefinition(ClassDefinitionTask task) {
		tasks.add(task);
	}
}
