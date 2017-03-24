package com.ecmdeveloper.ant.cetasks;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

import com.ecmdeveloper.ant.ceactions.UpdateSweepActionAction;
import com.ecmdeveloper.ant.ceactions.UpdateSweepPolicyAction;
import com.ecmdeveloper.ant.cetypes.Timeslot;

/**
 * 
 * @author Ricardo Belfor
 *
 */
public class ImportSweepPolicyTask extends ObjectStoreObjectTask {

	private String name; 
	private String sweepTarget;
	private String sweepActionName;
	private String filterExpression; 
	private String description;
	private ArrayList<Timeslot> timeslots = new ArrayList<Timeslot>();
	
	public void execute() throws BuildException {
		
		try {
			log("Running import sweep action task", Project.MSG_VERBOSE);
			UpdateSweepPolicyAction action = new UpdateSweepPolicyAction(getObjectStore(), this);
			action.execute(name, sweepTarget, sweepActionName, filterExpression, description, timeslots);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
	}
	
	public void addText(String text) {
		if ( !text.trim().isEmpty() ) {
			description = text.trim();
		}
	}
	
	public void addConfigured(Timeslot timeslot) {
		timeslots.add(timeslot);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSweepTarget(String sweepTarget) {
		this.sweepTarget = sweepTarget;
	}

	public void setSweepActionName(String sweepActionName) {
		this.sweepActionName = sweepActionName;
	}

	public void setFilterExpression(String filterExpression) {
		this.filterExpression = filterExpression;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
