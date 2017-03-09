/**
 * 
 */
package com.ecmdeveloper.ant.cetasks;

import java.util.ArrayList;

import org.apache.tools.ant.BuildException;

import com.ecmdeveloper.ant.ceactions.UpdateBackgroundSearchResultAction;
import com.ecmdeveloper.ant.cetypes.PropertyTemplateValue;

/**
 * @author Ricardo Belfor
 *
 */
public class ImportBackgroundSearchResultTask extends ObjectStoreNestedTask{

	private String name;
	private String symbolicName;
	private ArrayList<PropertyTemplateValue> propertyTemplateValues = new ArrayList<PropertyTemplateValue>();
	
	public void execute() throws BuildException {

		try {
			UpdateBackgroundSearchResultAction action = new UpdateBackgroundSearchResultAction();
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
	
	public void addConfigured(PropertyTemplateValue propertyTemplateValue) {
		propertyTemplateValues.add(propertyTemplateValue);
	}

	public ArrayList<PropertyTemplateValue> getPropertyTemplateValues() {
		return propertyTemplateValues;
	}
}
