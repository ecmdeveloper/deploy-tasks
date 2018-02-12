/**
 * 
 */
package com.ecmdeveloper.ant.ceactions.propertytemplate;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.apache.tools.ant.Task;

import com.filenet.api.admin.PropertyTemplate;
import com.filenet.api.admin.PropertyTemplateString;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.Id;

/**
 * @author Ricardo Belfor
 *
 */
public class StringPropertyTemplateAction extends PropertyTemplateAction {

	private Integer maximumLength;
	private String defaultString;
	private Boolean usesLongColumn;
	private String choiceList;
	
	public StringPropertyTemplateAction(ObjectStore objectStore, Task task) {
	}

	public void execute() {
		PropertyTemplateString propertyTemplate = getPropertyTemplate();
		
		if ( maximumLength != null) {
			propertyTemplate.set_MaximumLengthString(maximumLength);
		}
		
		if ( defaultString != null) {
			propertyTemplate.set_PropertyDefaultString(defaultString);
		}
		
		if ( usesLongColumn != null) {
			if ( isSettable(propertyTemplate,  PropertyNames.USES_LONG_COLUMN ) ) { 
				propertyTemplate.set_UsesLongColumn(usesLongColumn);
			}
		}
		
		if (choiceList != null) {
			propertyTemplate.set_ChoiceList(getChoiceList(choiceList));
		}
		
		propertyTemplate.save(RefreshMode.REFRESH);
	}

	@Override
	protected PropertyTemplate createInstance(ObjectStore objectStore, Id id) {
		return Factory.PropertyTemplateString.createInstance(objectStore, id);
	}

	public void setMaximumLength(Integer maximumLength) {
		this.maximumLength = maximumLength;
	}

	public void setDefaultString(String defaultString) {
		this.defaultString = defaultString;
	}

	public void setUsesLongColumn(Boolean usesLongColumn) {
		this.usesLongColumn = usesLongColumn;
	}

	public void setChoiceList(String choiceList) {
		this.choiceList = choiceList;
	}
}
