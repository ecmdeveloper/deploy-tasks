/**
 * 
 */
package com.ecmdeveloper.ant.cetypes;

/**
 * @author Ricardo Belfor
 *
 */
public abstract class PropertyValue {
	
	private String name;

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
	
	public abstract Object getObjectValue();
}
