/**
 * 
 */
package com.ecmdeveloper.ant.cetypes;

/**
 * @author Ricardo Belfor
 *
 */
public class DoublePropertyValue extends PropertyValue {

	private Double value;
	
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
	public Double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}
}
