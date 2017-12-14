package com.ecmdeveloper.ant.cetypes;


public class PermissionValue {

	private Integer inheritableDepth = 0;
	private Integer accessMask;
	private String 	granteeName;
	
	public Integer getInheritableDepth() {
		return inheritableDepth;
	}
	
	public void setInheritableDepth(Integer inheritableDepth) {
		this.inheritableDepth = inheritableDepth;
	}
	
	public Integer getAccessMask() {
		return accessMask;
	}
	
	public void setAccessMask(Integer accessMask) {
		this.accessMask = accessMask;
	}
	
	public String getGranteeName() {
		return granteeName;
	}
	
	public void setGranteeName(String granteeName) {
		this.granteeName = granteeName;
	}
}
