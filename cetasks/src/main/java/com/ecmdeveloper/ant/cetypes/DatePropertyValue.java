/**
 * 
 */
package com.ecmdeveloper.ant.cetypes;

import java.util.Date;

/**
 * @author Ricardo Belfor
 *
 */
public class DatePropertyValue extends PropertyValue {

	private Date value;
	
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
	public Date getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Date value) {
		this.value = value;
	}
}
