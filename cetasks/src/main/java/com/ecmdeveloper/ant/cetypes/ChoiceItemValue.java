/**
 * 
 */
package com.ecmdeveloper.ant.cetypes;

/**
 * @author Ricardo Belfor
 *
 */
public class ChoiceItemValue {

	private Integer integerValue;
	private String stringValue;
	private String displayName;

	public Integer getIntegerValue() {
		return integerValue;
	}
	
	public void setIntegerValue(Integer integerValue) {
		if ( stringValue != null) {
			throw new IllegalArgumentException("Choice item must have either a string or an integer value");
		}
		this.integerValue = integerValue;
	}
	
	public String getStringValue() {
		return stringValue;
	}
	
	public void setStringValue(String stringValue) {
		if ( integerValue != null) {
			throw new IllegalArgumentException("Choice item must have either a string or an integer value");
		}
		this.stringValue = stringValue;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	@Override
	public String toString() {
		if ( displayName != null) {
			return displayName;
		}
		
		if ( stringValue != null) {
			return stringValue;
		}
		
		if ( integerValue != null) {
			return integerValue.toString();
		}
		
		throw new IllegalStateException("Choice item must have a string or an integer value");
	}
}
