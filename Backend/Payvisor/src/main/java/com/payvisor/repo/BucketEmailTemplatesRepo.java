package com.payvisor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.payvisor.model.BucketEmailTemplates;

@RepositoryRestResource(collectionResourceRel = "bucketEmailTemplates",path = "bucketEmailTemplates")
public interface BucketEmailTemplatesRepo extends JpaRepository<BucketEmailTemplates , Integer>{
	
	BucketEmailTemplates findOneByBucketName(String bucketName);

}