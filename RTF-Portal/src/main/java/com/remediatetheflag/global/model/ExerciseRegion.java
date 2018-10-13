package com.remediatetheflag.global.model;

import com.google.gson.annotations.Expose;

public class ExerciseRegion {
	
	@Expose
	private String name;
	@Expose
	private String fqdn;
	private Integer ping;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFqdn() {
		return fqdn;
	}
	public void setFqdn(String fqdn) {
		this.fqdn = fqdn;
	}
	public Integer getPing() {
		return ping;
	}
	public void setPing(Integer ping) {
		this.ping = ping;
	}

}
