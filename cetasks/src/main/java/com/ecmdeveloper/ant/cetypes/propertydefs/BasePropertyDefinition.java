package com.ecmdeveloper.ant.cetypes.propertydefs;

public class BasePropertyDefinition {

	private String name;
	private Boolean required;
	private Boolean hidden;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	} 
}
