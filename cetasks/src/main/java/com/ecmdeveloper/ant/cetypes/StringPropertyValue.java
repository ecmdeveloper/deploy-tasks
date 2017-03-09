/**
 * 
 */
package com.ecmdeveloper.ant.cetypes;

/**
 * @author ricardobelfor
 *
 */
public class StringPropertyValue extends PropertyValue {

	private String value;
	
	@Override
	public Object getObjectValue() {
		return getValue();
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
