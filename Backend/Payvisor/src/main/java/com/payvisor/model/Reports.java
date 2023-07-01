package com.payvisor.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reports")
public class Reports {
	
	@Id
	@Column(name="ReportID")
	private int reportID;
	
	@Column(name="ReportName")
	private String reportName;
	
	@Column(name="ReportQuery")
	private String reportQuery;
	
	@Column(name="QueryJson")
	private String queryJson;
	
	@Column(name="ChartType")
	private String chartType;
	
	@Column(name="ReportType")
	private String reportType;
	
	@Column(name = "CreatedDateTime")
	@CreationTimestamp
	private Date createdDateTime;
	
	@Column(name = "UpdatedDateTime")
	@UpdateTimestamp
	private Date updatedDateTime;

	public Reports() {
	}

	public Reports(int reportID, String reportName, String reportQuery, String queryJson, String chartType,
			String reportType, Date createdDateTime, Date updatedDateTime) {
		super();
		this.reportID = reportID;
		this.reportName = reportName;
		this.reportQuery = reportQuery;
		this.queryJson = queryJson;
		this.chartType = chartType;
		this.reportType = reportType;
		this.createdDateTime = createdDateTime;
		this.updatedDateTime = updatedDateTime;
	}



	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportQuery() {
		return reportQuery;
	}

	public void setReportQuery(String reportQuery) {
		this.reportQuery = reportQuery;
	}

	public String getQueryJson() {
		return queryJson;
	}

	public void setQueryJson(String queryJson) {
		this.queryJson = queryJson;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
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
		return "Reports [reportID=" + reportID + ", reportName=" + reportName + ", reportQuery=" + reportQuery
				+ ", queryJson=" + queryJson + ", chartType=" + chartType + ", reportType=" + reportType
				+ ", createdDateTime=" + createdDateTime + ", updatedDateTime=" + updatedDateTime + "]";
	}

}
