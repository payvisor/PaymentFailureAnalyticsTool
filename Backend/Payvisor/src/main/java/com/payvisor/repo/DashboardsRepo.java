package com.payvisor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.payvisor.model.Dashboards;

@RepositoryRestResource(collectionResourceRel = "dashboards",path = "dashboards")
public interface DashboardsRepo extends JpaRepository<Dashboards , Integer>{

}