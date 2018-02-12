/**
 * 
 */
package com.ecmdeveloper.ant.cetypes.propertydefs;

/**
 * @author ricardobelfor
 *
 */
public class StringPropertyDefinition extends BasePropertyDefinition {

	private Integer maximumLength;
	
	public Integer getMaximumLength() {
		return maximumLength;
	}
	public void setMaximumLength(Integer maximumLength) {
		this.maximumLength = maximumLength;
	}
}
