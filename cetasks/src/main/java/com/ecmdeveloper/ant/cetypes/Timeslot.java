/**
 * 
 */
package com.ecmdeveloper.ant.cetypes;

import com.filenet.api.constants.Weekday;

/**
 * @author Ricardo Belfor
 *
 */
public class Timeslot {

	private Weekday weekday;
	private Integer startTime;
	private Integer duration;
	
	public void setWeekday(int weekday) {
		this.weekday = Weekday.getInstanceFromInt(weekday);
	}
	
	public Weekday getWeekday() {
		return weekday;
	}
	
	public void setStartTime(String startTime) {
		if ( startTime == null) {
			throw new IllegalArgumentException("Start time is empty");
		}
		
		String[] split = startTime.split(":");
		if ( split.length != 2 ) {
			throw new IllegalArgumentException("Start time must be in the form hh:mm");
		}
		
		this.startTime = Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]); 
	}
	
	public int getStartTime() {
		return startTime;
	}

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
}
