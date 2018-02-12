/**
 * 
 */
package com.ecmdeveloper.ant.cetasks.propertytemplate;

import com.ecmdeveloper.ant.ceactions.propertytemplate.PropertyTemplateAction;
import com.ecmdeveloper.ant.ceactions.propertytemplate.StringPropertyTemplateAction;

/**
 * @author Ricardo Belfor
 *
 */
public class StringPropertyTemplateTask extends AbstractPropertyTemplateTask {

	private StringPropertyTemplateAction action = new StringPropertyTemplateAction( getObjectStore(), this );
	
	@Override
	protected PropertyTemplateAction getAction() {
		return action;
	}

	public void setMaximumLength(Integer maximumLength) {
		action.setMaximumLength(maximumLength);
	}

	public void setDefaultString(String defaultString) {
		action.setDefaultString(defaultString);
	}

	public void setUsesLongColumn(Boolean usesLongColumn) {
		action.setUsesLongColumn(usesLongColumn);
	}
	
	public void setChoiceList(String choiceList) {
		action.setChoiceList(choiceList);
	}
}
