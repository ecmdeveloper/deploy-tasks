/**
 * 
 */
package com.ecmdeveloper.ant.ceactions;

import java.util.Collection;
import java.util.Iterator;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.ecmdeveloper.ant.cetypes.Timeslot;
import com.filenet.api.admin.CmTimeslot;
import com.filenet.api.collection.CmSweepPolicyRelationshipSet;
import com.filenet.api.collection.CmTimeslotList;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.constants.SweepMode;
import com.filenet.api.constants.Weekday;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.sweep.CmCustomSweepPolicy;
import com.filenet.api.sweep.CmPolicyControlledSweep;
import com.filenet.api.sweep.CmSweepAction;
import com.filenet.api.sweep.CmSweepPolicyRelationship;

/**
 * @author ricardobelfor
 *
 */
public class UpdateSweepPolicyAction extends ObjectStoreAction {

	private ObjectStore objectStore;
	private Task task;

	public UpdateSweepPolicyAction(ObjectStore objectStore, Task task) {
		this.objectStore = objectStore;
		this.task = task;
	}

	public void execute(String name, String sweepTarget, String sweepActionName, String filterExpression, String description, Collection<Timeslot> timeslots) {

		CmCustomSweepPolicy sweepPolicy = getByDisplayName(name, CmCustomSweepPolicy.class, objectStore);
		if ( sweepPolicy == null) {
			sweepPolicy = Factory.CmCustomSweepPolicy.createInstance(objectStore, "CmCustomSweepPolicy");
			sweepPolicy.set_DisplayName(name);
			sweepPolicy.set_SweepTarget(Factory.ClassDefinition.fetchInstance(objectStore, sweepTarget, null) );
		}
		sweepPolicy.set_IncludeSubclassesRequested(Boolean.TRUE);
		sweepPolicy.set_SweepMode(SweepMode.SWEEP_MODE_NORMAL);
		
		sweepPolicy.set_DescriptiveText(description);

		CmSweepAction sweepAction = getByDisplayName(sweepActionName, CmSweepAction.class, objectStore);
		if ( sweepAction == null ) {
			throw new BuildException("Sweep Action '" + sweepActionName + "' not found");
		}

		sweepPolicy.set_SweepAction(sweepAction);
		sweepPolicy.set_FilterExpression(filterExpression);
		sweepPolicy.save(RefreshMode.REFRESH);
		
		if ( timeslots != null && !timeslots.isEmpty() ) {
			CmPolicyControlledSweep controlledSweep = getPolicyControlledSweep(sweepPolicy);
			controlledSweep.set_SweepTimeslots( getTimeslotList(timeslots) );
			controlledSweep.save(RefreshMode.NO_REFRESH);
		}
	}

	private CmPolicyControlledSweep getPolicyControlledSweep(CmCustomSweepPolicy sweepPolicy) {
		CmSweepPolicyRelationshipSet sweepSubscriptions = sweepPolicy.get_SweepSubscriptions();
		Iterator<?> iterator = sweepSubscriptions.iterator();
		CmPolicyControlledSweep controlledSweep  = ((CmSweepPolicyRelationship)iterator.next()).get_Sweep();
		return controlledSweep;
	}

	@SuppressWarnings("unchecked")
	private CmTimeslotList getTimeslotList(Collection<Timeslot> timeslots) {
		CmTimeslotList timeslotList = Factory.CmTimeslot.createList();
		for (Timeslot timeslot : timeslots) {
			timeslotList.add(getTimeslot(timeslot));
		}
		return timeslotList;
	}

	private CmTimeslot getTimeslot(Timeslot timeslot) {
		CmTimeslot cmTimeslot = Factory.CmTimeslot.createInstance();
		cmTimeslot.set_Duration( timeslot.getDuration() );
		cmTimeslot.set_StartMinutesPastMidnight( timeslot.getStartTime() );
		cmTimeslot.set_Weekday( timeslot.getWeekday() );
		return cmTimeslot;
	}
}
