package com.ecmdeveloper.ant.ceactions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;

import com.ecmdeveloper.ant.cetasks.ObjectStoreNestedTask;
import com.filenet.api.collection.ContentElementList;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;

public class UpdateDocumentContentAction {

	private String stringContent;
    private ArrayList<FileSet> filesets = new ArrayList<FileSet>();

	public void execute(ObjectStoreNestedTask task, Document document) throws BuildException {
		ContentElementList contentElementList = createContent(task);
		document.set_ContentElements(contentElementList);
	}

	public boolean hasContent() {
		return stringContent != null || !filesets.isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	protected ContentElementList createContent(ObjectStoreNestedTask task) {
		
		ContentElementList contentElements = Factory.ContentElement.createList();

		if ( stringContent != null ) {
			task.log("Adding string content to document", Project.MSG_VERBOSE);
			contentElements.add(createStringContent());
		}
		
        for (FileSet fs : filesets) {
            addFileSet(fs, contentElements, task);
        }
		return contentElements;
	}

	private void addFileSet(FileSet fs, ContentElementList contentElements, ObjectStoreNestedTask task) {
		DirectoryScanner ds = fs.getDirectoryScanner( task.getProject() ); 
		String[] includedFiles = ds.getIncludedFiles();
		for(String includedFile : includedFiles) {
			addFile(ds.getBasedir() + "/" + includedFile, contentElements, task);
		}
	}

	@SuppressWarnings("unchecked")
	private void addFile(String pathname, ContentElementList contentElements, ObjectStoreNestedTask task) {
		File file = new File(pathname);
		task.log("Adding file '" + file.getAbsolutePath() + "' to document", Project.MSG_VERBOSE);

		ContentTransfer content = createFileContent(file);
		contentElements.add(content);
	}

	private ContentTransfer createStringContent() {
		  ContentTransfer content = Factory.ContentTransfer.createInstance();
		  content.set_RetrievalName( "data.txt" );
		  content.setCaptureSource( new ByteArrayInputStream( stringContent.getBytes() ) );
		  content.set_ContentType( "text/plain" );
		  return content;
	}
	
	private ContentTransfer createFileContent(File file) 
	{
		ContentTransfer content = Factory.ContentTransfer.createInstance();
		content.set_RetrievalName( file.getName() );
		try {
			content.setCaptureSource( new FileInputStream( file ) );
		} catch (FileNotFoundException e) {
			// Should not happen as only existing files are added...
		}
		
		content.set_ContentType( getFileContentType(file) );
		return content;
	}

    public void addFileset(FileSet fileset) {
        filesets.add(fileset);
    }
	
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.stringContent = content;
	}	

	private String getFileContentType(File file) 
	{
		String contentType = null;
		if ( file.getName().toLowerCase().endsWith(".jar") ||
			 file.getName().toLowerCase().endsWith(".zip") ) {
			contentType = "application/x-zip-compressed";
		} else if ( file.getName().endsWith( ".class" ) ) {
			contentType = "application/java";
		}
		return contentType;
	}	
}
