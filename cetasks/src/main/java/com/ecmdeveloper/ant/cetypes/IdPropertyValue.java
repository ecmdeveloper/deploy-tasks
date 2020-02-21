/**
 * 
 */
package com.ecmdeveloper.ant.cetypes;

import com.filenet.api.util.Id;

/**
 * @author Ricardo Belfor
 *
 */
public class IdPropertyValue extends PropertyValue {

	private String value;
	
	/* (non-Javadoc)
	 * @see com.ecmdeveloper.ant.cetypes.PropertyValue#getObjectValue()
	 */
	@Override
	public Object getObjectValue() {
		return new Id( getValue() );
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
