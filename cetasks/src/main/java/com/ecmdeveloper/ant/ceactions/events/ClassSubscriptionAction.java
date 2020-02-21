/**
 * 
 */
package com.ecmdeveloper.ant.ceactions.events;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.ecmdeveloper.ant.ceactions.ObjectStoreAction;
import com.ecmdeveloper.ant.cetypes.Event;
import com.filenet.api.admin.ClassDefinition;
import com.filenet.api.admin.EventClassDefinition;
import com.filenet.api.collection.SubscribedEventList;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.events.ClassSubscription;
import com.filenet.api.events.EventAction;
import com.filenet.api.events.SubscribedEvent;
import com.filenet.api.util.Id;

/**
 * @author Ricardo Belfor
 *
 */
public class ClassSubscriptionAction extends ObjectStoreAction {

	private List<Event> events = new ArrayList<Event>();
	private String displayName;
	private String id;
	private String description;
	private String className;
	private Boolean includeSubclasses;
	private Boolean synchronous;
	private String userString;
	private Boolean enabled;
	private String filterExpression;
	private String eventActionName;
	private String filteredPropertyId;
	
	public void execute(ObjectStore objectStore, Task task) {
		
		ClassSubscription subscription = getByIdOrDisplayName(ClassSubscription.class, id, displayName, objectStore);
		
		if ( subscription == null) {
			task.log("Creating class description '" + displayName + "'");
			subscription = Factory.ClassSubscription.createInstance(objectStore, null, new Id(id) );
		} else {
			subscription.refresh( new String[] { PropertyNames.DISPLAY_NAME });
			task.log("Updating class description '" + subscription.get_DisplayName() + "'");
		}
		
		ClassDefinition classDefinition = Factory.ClassDefinition.fetchInstance(objectStore, className, null);
		
		subscription.set_DisplayName(displayName);
		subscription.set_SubscriptionTarget(classDefinition);
		subscription.set_IncludeSubclassesRequested(includeSubclasses);
		subscription.set_IsSynchronous(synchronous);
		subscription.set_DescriptiveText(description);
		subscription.set_UserString(userString);
		subscription.set_IsEnabled(enabled);
		subscription.set_FilterExpression(filterExpression);
		subscription.set_FilteredPropertyId(filteredPropertyId);
		
		EventAction eventAction = getByDisplayName(eventActionName, EventAction.class, objectStore);
		subscription.set_EventAction( eventAction );
		subscription.set_SubscribedEvents( getSubscribedEvents(objectStore) );
	
		subscription.save(RefreshMode.NO_REFRESH);	
	}
	
	@SuppressWarnings("unchecked")
	private SubscribedEventList getSubscribedEvents(ObjectStore objectStore) {
		SubscribedEventList subscribedEventList = Factory.SubscribedEvent.createList();
		for ( Event event : events ) {
			subscribedEventList.add( getSubscribedEvent(event.getName(), objectStore) );
		}
		return subscribedEventList;	
	}

	private Object getSubscribedEvent(String name, ObjectStore objectStore ) {
		EventClassDefinition eventClassDefinition = Factory.EventClassDefinition.fetchInstance(objectStore, name, null);
		SubscribedEvent subscribedEvent = Factory.SubscribedEvent.createInstance();		
		subscribedEvent.set_EventClass(eventClassDefinition);
		return subscribedEvent;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setIncludeSubclasses(Boolean includeSubclasses) {
		this.includeSubclasses = includeSubclasses;
	}

	public void setSynchronous(Boolean synchronous) {
		this.synchronous = synchronous;
	}

	public void setUserString(String userString) {
		this.userString = userString;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setFilterExpression(String filterExpression) {
		this.filterExpression = filterExpression;
	}

	public void setEventActionName(String eventActionName) {
		this.eventActionName = eventActionName;
	}

	public void setFilteredPropertyId(String filteredPropertyId) {
		this.filteredPropertyId = filteredPropertyId;
	}
	
	public void addEvent(Event event) {
		events.add(event);
	}
}
