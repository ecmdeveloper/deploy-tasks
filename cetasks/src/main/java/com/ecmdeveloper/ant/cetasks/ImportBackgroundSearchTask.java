/**
 * 
 */
package com.ecmdeveloper.ant.cetasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;

import com.ecmdeveloper.ant.ceactions.UpdateBackgroundSearchAction;
import com.ecmdeveloper.ant.cetypes.PropertyTemplateValue;

/**
 * @author Ricardo Belfor
 *
 */
public class ImportBackgroundSearchTask extends ObjectStoreNestedTask {

	private String name;
	private String symbolicName;
	private String searchResultsClass;
	private String query;
	private String description;
	private String parentClass = "CmBackgroundSearch";
	private List<String> properties = new ArrayList<String>();
	
	public void execute() throws BuildException {

		try {
			UpdateBackgroundSearchAction action = new UpdateBackgroundSearchAction( getObjectStore(), this);
			action.execute(name, symbolicName, parentClass, searchResultsClass, query, description, properties);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param symbolicName the symbolicName to set
	 */
	public void setSymbolicName(String symbolicName) {
		this.symbolicName = symbolicName;
	}

	/**
	 * @param searchResultsClass the searchResultsClass to set
	 */
	public void setSearchResultsClass(String searchResultsClass) {
		this.searchResultsClass = searchResultsClass;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addText(String text) {
		if (!text.trim().isEmpty() ) {
			this.description = text.trim();
		}
	}

	/**
	 * @param parentClass the parentClass to set
	 */
	public void setParentClass(String parentClass) {
		this.parentClass = parentClass;
	}
	
	public void addConfigured(PropertyTemplateValue propertyTemplateValue) {
		properties.add(propertyTemplateValue.getName());
	}
}
