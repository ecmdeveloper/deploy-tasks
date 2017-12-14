/**
 * 
 */
package com.ecmdeveloper.ant.cetasks;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.FileSet;

import com.ecmdeveloper.ant.ceactions.UpdateDocumentContentAction;
import com.ecmdeveloper.ant.ceactions.UpdatePropertiesAction;
import com.ecmdeveloper.ant.cetypes.PropertyValue;
import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.constants.ReservationType;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.IndependentlyPersistableObject;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.ReferentialContainmentRelationship;
import com.filenet.api.core.VersionSeries;
import com.filenet.api.exception.EngineRuntimeException;
import com.filenet.api.exception.ExceptionCode;

/**
 * @author Ricardo Belfor
 *
 */
public class DocumentTask extends ObjectStoreNestedTask {

	private String path;
	private String className;
	private String type;
	private String parentPath;
	private String name;
	private Boolean forceCreate = false;
	
	private UpdatePropertiesAction updateObjectStoreObjectAction = new UpdatePropertiesAction();
	private UpdateDocumentContentAction updateDocumentContentAction = new UpdateDocumentContentAction();
	private Document document;
	
	public void execute() throws BuildException {

		validateInput();
		
		try {
			String effectivePath = getEffectivePath();
			boolean newDocument = false;
			boolean checkin = false;
			
			document = null;
			if ( effectivePath != null) {
				document = fetchDocumentByPath(effectivePath);
				if ( document == null) {
					document = Factory.Document.createInstance(getObjectStore(), className);
					newDocument = true;
					checkin = true;
					log("Creating new document");
				} else if ( updateDocumentContentAction.hasContent() ) {
					log("Updating existing document");
					document = getReservation(document);
					checkin = true;
				}
			}
			
			updateObjectStoreObjectAction.execute(this, Arrays.asList((IndependentlyPersistableObject) document) );
			
			if ( checkin ) {
				updateDocumentContentAction.execute(this, document);
				document.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY,CheckinType.MAJOR_VERSION);
			}
			
			document.save(RefreshMode.REFRESH);
			
			if ( checkin) {
				log("\tNew document version is " + document.get_MajorVersionNumber() + "." + document.get_MinorVersionNumber() );
			}

			
			if ( newDocument && parentPath != null) {
				log("filing document to '" + parentPath + "'");
				Folder folder = Factory.Folder.fetchInstance(getObjectStore(), parentPath, null);
				ReferentialContainmentRelationship relationship = folder.file(document, AutoUniqueName.NOT_AUTO_UNIQUE, name, DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
				relationship.save(RefreshMode.NO_REFRESH);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
	}

	protected String getDocumentId() {
		return document.get_Id().toString();
	}

	private Document getReservation(Document document) {
		VersionSeries versionSeries = document.get_VersionSeries(); 
		versionSeries.fetchProperties( new String[]  { PropertyNames.CURRENT_VERSION } );
		document = (Document) versionSeries.get_CurrentVersion();
		
		document.checkout(ReservationType.EXCLUSIVE, null, document.getClassName(), null);
		document.save(RefreshMode.REFRESH);
		document = (Document) document.get_Reservation();
		return document;
	}
	
	private Document fetchDocumentByPath(String effectivePath) {
		try {
			return Factory.Document.fetchInstance(getObjectStore(), effectivePath, null);
		} catch (EngineRuntimeException erte) {
			if ( erte.getExceptionCode().equals( ExceptionCode.E_OBJECT_NOT_FOUND ) ) {
				if ( forceCreate ) {
					return null;
				} else {
					throw new BuildException("The object '" + effectivePath + "' was not found");
				}
			} else {
				throw new BuildException( erte.getLocalizedMessage() );
			}
		}
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
	
	public void addConfigured(PropertyValue propertyValue) {
		updateObjectStoreObjectAction.add(propertyValue);
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		updateDocumentContentAction.setContent(content);
	} 
	
	public void addText(String text) {
		if ( !text.trim().isEmpty() ) {
			updateDocumentContentAction.setContent(text.trim());
		}
	}

	public void addFileset(FileSet fileset) {
		updateDocumentContentAction.addFileset(fileset);
    }
	
}
