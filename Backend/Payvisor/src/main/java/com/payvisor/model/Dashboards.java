package com.payvisor.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Dashboards")
public class Dashboards {
	
	@Id
	@Column(name="DashboardID")
	private int dashboardID;
	
	@Column(name="DashboardName")
	private String dashboardName;
	
	@Column(name="reportID")
	private int reportID;
	
	@Column(name="DashboardType")
	private String dashboardType;
	
	@Column(name = "CreatedDateTime")
	@CreationTimestamp
	private Date createdDateTime;
	
	@Column(name = "UpdatedDateTime")
	@UpdateTimestamp
	private Date updatedDateTime;

	public Dashboards() {
	}

	public Dashboards(int dashboardID, String dashboardName, int reportID, String dashboardType, Date createdDateTime,
			Date updatedDateTime) {
		this.dashboardID = dashboardID;
		this.dashboardName = dashboardName;
		this.reportID = reportID;
		this.dashboardType = dashboardType;
		this.createdDateTime = createdDateTime;
		this.updatedDateTime = updatedDateTime;
	}

	public int getDashboardID() {
		return dashboardID;
	}

	public void setDashboardID(int dashboardID) {
		this.dashboardID = dashboardID;
	}

	public String getDashboardName() {
		return dashboardName;
	}

	public void setDashboardName(String dashboardName) {
		this.dashboardName = dashboardName;
	}

	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

	public String getDashboardType() {
		return dashboardType;
	}

	public void setDashboardType(String dashboardType) {
		this.dashboardType = dashboardType;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	@Override
	public String toString() {
		return "Dashboards [dashboardID=" + dashboardID + ", dashboardName=" + dashboardName + ", reportID=" + reportID
				+ ", dashboardType=" + dashboardType + ", createdDateTime=" + createdDateTime + ", updatedDateTime="
				+ updatedDateTime + "]";
	}
}
