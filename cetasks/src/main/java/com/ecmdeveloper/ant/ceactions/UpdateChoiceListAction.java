/**
 * 
 */
package com.ecmdeveloper.ant.ceactions;

import java.util.ArrayList;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.ecmdeveloper.ant.cetypes.ChoiceItemValue;
import com.filenet.api.admin.Choice;
import com.filenet.api.admin.ChoiceList;
import com.filenet.api.constants.ChoiceType;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.constants.TypeID;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.events.ClassSubscription;
import com.filenet.api.util.Id;

/**
 * @author Ricardo Belfor
 *
 */
public class UpdateChoiceListAction extends ObjectStoreAction {

	private ArrayList<ChoiceItemValue> choiceItemValues = new ArrayList<ChoiceItemValue>();
	private String displayName;
	private String id;
	private String dataType;
	private String description;
	
	public void execute(ObjectStore objectStore, Task task) {
		
		ChoiceList choiceList = getByIdOrDisplayName(ChoiceList.class, id, displayName, objectStore);
		
		if ( choiceList == null) {
			task.log("Creating choice list '" + displayName + "'");
			choiceList = Factory.ChoiceList.createInstance(objectStore, new Id(id) );
			choiceList.set_DataType( isStringChoiceList(dataType) ? TypeID.STRING : TypeID.LONG );
		} else {
			choiceList.refresh( new String[] { PropertyNames.DISPLAY_NAME });
			task.log("Updating choice list '" + choiceList.get_DisplayName() + "'");
		}
		choiceList.set_DisplayName(displayName);
		choiceList.set_ChoiceValues(getChoices());
		if ( description != null) {
			choiceList.set_DescriptiveText(description);
		}
		choiceList.save(RefreshMode.NO_REFRESH);
	}

	@SuppressWarnings("unchecked")
	private com.filenet.api.collection.ChoiceList getChoices() 
	{
		com.filenet.api.collection.ChoiceList choiceValues = Factory.Choice.createList();

        for ( ChoiceItemValue choiceItem: choiceItemValues ) 
        {
    		Choice choice = Factory.Choice.createInstance();
    		if ( isStringChoiceList(dataType) ) {
    			choice.set_ChoiceStringValue(choiceItem.getStringValue());
        		choice.set_ChoiceType(ChoiceType.STRING);
    		} else {
    			choice.set_ChoiceIntegerValue(choiceItem.getIntegerValue());
        		choice.set_ChoiceType(ChoiceType.INTEGER);
    		}
			choice.set_DisplayName( choiceItem.toString() );
    		choiceValues.add(choice);
        }
		return choiceValues;
	}
	
	public void add(ChoiceItemValue choiceItemValue) {
		choiceItemValues.add(choiceItemValue);
	}

	private boolean isStringChoiceList(String dataType) {
		return TypeID.STRING.toString().equalsIgnoreCase(dataType);
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getId() {
		if ( id != null) {
			return id.toString();
		} 
		return null;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	} 
}
