/**
 * 
 */
package com.ecmdeveloper.ant.cetasks;

import org.apache.tools.ant.BuildException;

import com.ecmdeveloper.ant.ceactions.UpdateBackgroundSearchAction;

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
	
	public void execute() throws BuildException {

		try {
			UpdateBackgroundSearchAction action = new UpdateBackgroundSearchAction();
			action.execute(this);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the symbolicName
	 */
	public String getSymbolicName() {
		return symbolicName;
	}

	/**
	 * @param symbolicName the symbolicName to set
	 */
	public void setSymbolicName(String symbolicName) {
		this.symbolicName = symbolicName;
	}

	/**
	 * @return the searchResultsClass
	 */
	public String getSearchResultsClass() {
		return searchResultsClass;
	}

	/**
	 * @param searchResultsClass the searchResultsClass to set
	 */
	public void setSearchResultsClass(String searchResultsClass) {
		this.searchResultsClass = searchResultsClass;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	public String getDescription() {
		return description;
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
	 * @return the parentClass
	 */
	public String getParentClass() {
		return parentClass;
	}

	/**
	 * @param parentClass the parentClass to set
	 */
	public void setParentClass(String parentClass) {
		this.parentClass = parentClass;
	}
}
