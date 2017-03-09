/**
 * 
 */
package com.ecmdeveloper.ant.cetasks;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.apache.tools.ant.BuildException;

import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.IndependentlyPersistableObject;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.exception.EngineRuntimeException;
import com.filenet.api.exception.ExceptionCode;

/**
 * @author Ricardo Belfor
 *
 */
public class ObjectStoreObjectTask extends ObjectStoreNestedTask {

	private String path;
	private String className;
	private String type;
	private String parentPath;
	private String name;
	private Boolean forceCreate = false;
	
	private ArrayList<IndependentlyPersistableObject> engineObjects;

	/**
	 * @return the engineObjects
	 */
	protected ArrayList<IndependentlyPersistableObject> getEngineObjects() {
		
		if ( engineObjects != null ) {
			return engineObjects;
		}

		validateInput();
		
		engineObjects = new ArrayList<IndependentlyPersistableObject>();
		
		String effectivePath = getEffectivePath();
		
		if ( effectivePath != null) {
			if ( "folder".equalsIgnoreCase(type) ) {
				engineObjects.add( fetchFolderByPath(effectivePath) );
			} else if ( "document".equalsIgnoreCase(type) ) {
				
			}
		}
		
		log( engineObjects.size() + " objects found" );
		return engineObjects;
	}

	private Folder fetchFolderByPath(String effectivePath) {
		try {
			return Factory.Folder.fetchInstance(getObjectStore(), effectivePath, null);
		} catch (EngineRuntimeException erte) {
			if ( erte.getExceptionCode().equals( ExceptionCode.E_OBJECT_NOT_FOUND ) ) {
				if ( forceCreate ) {
					return createNewFolder();
				} else {
					throw new BuildException("The object '" + effectivePath + "' was not found");
				}
			} else {
				throw new BuildException( erte.getLocalizedMessage() );
			}
		}
	}

	private Folder createNewFolder() {
		Folder newFolder = Factory.Folder.createInstance(getObjectStore(), className);
		newFolder.set_FolderName(name);
		newFolder.set_Parent( Factory.Folder.fetchInstance(getObjectStore(), parentPath, null) );
		return newFolder;
	}
	
	private void validateInput() {
		if ( path != null && parentPath != null ) {
			throw new BuildException("The attributes 'path' and 'parentPath' cannot be combined.");
		}
		
		if ( parentPath != null && name == null ) {
			throw new BuildException("The attribute 'name' cannot be empty if the attribute 'parentPath' has a value");
		}
		
		if ( path != null && Boolean.TRUE.equals(forceCreate) ) {
			throw new BuildException("The attribute 'forceCreate' cannot be combined with 'path' use 'parentPath' and 'name' instead");
		}
		
	}

	private String getEffectivePath() {
		
		if ( path != null) {
			return path;
		} else if ( parentPath != null && name != null ) {
			return MessageFormat.format("{0}/{1}", parentPath, name);
		} else {
			return null;
		}
	}
	
	/**
	 * Sets the path of the object store object.
	 * 
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param parentPath the parentPath to set
	 */
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param forceCreate the forceCreate to set
	 */
	public void setForceCreate(Boolean forceCreate) {
		this.forceCreate = forceCreate;
	}
}

/*
 * 		ObjectStore os = null;
		PropertyFilter filter = null;
		Id objectId = null;

		com.filenet.api.core.Factory.Folder.fetchInstance(os, path, filter);
		com.filenet.api.core.Factory.Folder.getInstance(os, className, objectId);
		
	
		String classId = null;
		
		Factory.Document.createInstance(os, classId);
		Factory.Document.fetchInstance(os, objectId, filter);
		Factory.Document.fetchInstance(os, path, filter);
		Factory.Document.getInstance(os, className, objectId);
		Factory.Document.getInstance(os, className, path);
*/
