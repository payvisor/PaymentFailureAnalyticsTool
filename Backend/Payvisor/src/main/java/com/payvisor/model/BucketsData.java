package com.payvisor.model;

import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="BucketsData")
public class BucketsData {
	
	@Id
	@Column(name = "BucketName")
	private String bucketName;
	
	@Column(name = "BucketDescription")
	private String bucketDescription;
	
	@Column(name = "Tags")
	private String tags;
	
	@Column(name = "Severity")
	private String severity;
	
	@Column(name = "CreatedDateTime")
	@CreationTimestamp
	private Date createdDateTime;
	
	@Column(name = "UpdatedDateTime")
	@UpdateTimestamp
	private Date updatedDateTime;

	public BucketsData() {
	}

	public BucketsData(String bucketName, String bucketDescription, String tags, String severity, Date createdDateTime,
			Date updatedDateTime) {
		super();
		this.bucketName = bucketName;
		this.bucketDescription = bucketDescription;
		this.tags = tags;
		this.severity = severity;
		this.createdDateTime = createdDateTime;
		this.updatedDateTime = updatedDateTime;
	}


	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getBucketDescription() {
		return bucketDescription;
	}

	public void setBucketDescription(String bucketDescription) {
		this.bucketDescription = bucketDescription;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
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
		return "BucketsData [bucketName=" + bucketName + ", bucketDescription=" + bucketDescription + ", tags=" + tags
				+ ", severity=" + severity + ", createdDateTime=" + createdDateTime + ", updatedDateTime="
				+ updatedDateTime + "]";
	}
	
}
