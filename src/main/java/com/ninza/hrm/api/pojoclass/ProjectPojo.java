package com.ninza.hrm.api.pojoclass;

public class ProjectPojo {
    
	//variable
	String projectName;
	String status;
	String createdBy;
	int teamSize;
	
	//constructor
	public ProjectPojo() {}        // empty constructor for deserialization

	public ProjectPojo(String projectName, String status,
			String createdBy, int teamSize) {
		super();
		this.projectName = projectName;
		this.status = status;
		this.createdBy = createdBy;
		this.teamSize = teamSize;
	}

	//getter and setter
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public int getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}
	
	
	
}
