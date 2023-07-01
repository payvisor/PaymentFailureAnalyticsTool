package com.payvisor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.payvisor.model.BucketsData;

@RepositoryRestResource(collectionResourceRel = "bucketsData",path = "bucketsData")
public interface BucketsDataRepo extends JpaRepository<BucketsData , String>{

}