/**
 * 
 */
package com.ecmdeveloper.ant.cetypes;

/**
 * @author Ricardo Belfor
 *
 */
public class BooleanPropertyValue extends PropertyValue {

	private Boolean value;
	
	/* (non-Javadoc)
	 * @see com.ecmdeveloper.ant.cetypes.PropertyValue#getObjectValue()
	 */
	@Override
	public Object getObjectValue() {
		return getValue();
	}
	
	/**
	 * @return the value
	 */
	public Boolean getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(Boolean value) {
		this.value = value;
	}
}
