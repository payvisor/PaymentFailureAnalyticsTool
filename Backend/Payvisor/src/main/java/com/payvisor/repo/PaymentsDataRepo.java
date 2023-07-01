package com.payvisor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.payvisor.model.PaymentsData;

@RepositoryRestResource(collectionResourceRel = "paymentsData",path = "paymentsData")
public interface PaymentsDataRepo extends JpaRepository<PaymentsData , Integer>{
	
	PaymentsData findOneByTransactionID(String txnId);
	
}