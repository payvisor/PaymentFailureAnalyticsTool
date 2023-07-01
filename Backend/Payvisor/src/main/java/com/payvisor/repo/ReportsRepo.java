package com.payvisor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.payvisor.model.Reports;

@RepositoryRestResource(collectionResourceRel = "reports",path = "reports")
public interface ReportsRepo extends JpaRepository<Reports , Integer>{

}