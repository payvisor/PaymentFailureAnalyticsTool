package com.payvisor.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.payvisor.model.DateWiseFailCount;
import com.payvisor.model.GroupByAndCount;
import com.payvisor.model.GroupByCountWithSeverity;
import com.payvisor.model.PaymentFailuresData;
import com.payvisor.model.WeekWiseFailCount;

@RepositoryRestResource(collectionResourceRel = "paymentFailuresData",path = "paymentFailuresData")
public interface PaymentFailuresDataRepo extends JpaRepository<PaymentFailuresData , Integer>{
	
	PaymentFailuresData findOneByTransactionID(String txnId);
	
	List<PaymentFailuresData> findAllByTransactionStatus(String txnStatus);
	
	List<PaymentFailuresData> findAllByEmailID(String emailID);
	
	@Query(value="SELECT bd.bucketName as groupBy, COUNT(paymentFailuresDataID) as count, severity as severity from BucketsData bd left join PaymentFailuresData pfd on bd.bucketName=pfd.bucketName group by bd.bucketName order by bd.Severity,bd.BucketName",nativeQuery = true)
	List<GroupByCountWithSeverity> countByAllBuckets();
	
	@Query(value="SELECT bd.bucketName as groupBy, COUNT(paymentFailuresDataID) as count, severity as severity from BucketsData bd left join PaymentFailuresData pfd on bd.bucketName=pfd.bucketName WHERE pfd.transactionDateTime >= DATE_SUB(CURDATE(), INTERVAL :noOfMonths MONTH) or pfd.transactionDateTime is null group by bd.bucketName order by bd.Severity,bd.BucketName",nativeQuery = true)
	List<GroupByCountWithSeverity> countByAllBucketsFromNMonths(int noOfMonths);
	
	@Query(value="SELECT failureReason as groupBy, count(paymentFailuresDataID) as count from PaymentFailuresData WHERE transactionDateTime >= DATE_SUB(CURDATE(), INTERVAL :noOfMonths MONTH) group by failureReason",nativeQuery = true)
	List<GroupByAndCount> countByAllFailureReasonsFromNMonths(int noOfMonths);

	@Query(value="SELECT failureReason as groupBy, count(paymentFailuresDataID) as count from PaymentFailuresData WHERE bucketName=:bucketName and transactionDateTime >= DATE_SUB(CURDATE(), INTERVAL :noOfMonths MONTH) group by failureReason",nativeQuery = true)
	List<GroupByAndCount> countByAllFailureReasonsByBucketFromNMonths(String bucketName,int noOfMonths);
	
	@Query(value = "SELECT CONCAT('Week ',(WEEK(TransactionDateTime) - WEEK(DATE_FORMAT(TransactionDateTime, '%Y-%m-01')) + 1)) as week, DATE_FORMAT(TransactionDateTime, '%M') AS month,COUNT(*) AS count FROM PaymentFailuresData where transactionDateTime >= DATE_SUB(CURDATE(), INTERVAL :noOfMonths MONTH) GROUP BY (WEEK(TransactionDateTime) - WEEK(DATE_FORMAT(TransactionDateTime, '%Y-%m-01')) + 1), MONTH(TransactionDateTime) ORDER BY MONTH(TransactionDateTime), WEEK(TransactionDateTime)", nativeQuery = true)
	List<WeekWiseFailCount> weekWiseCountByAllBucketsFromLastNMonths(int noOfMonths);
	
	@Query(value = "SELECT DATE_FORMAT(TransactionDateTime, '%Y-%m-%d') AS date, COUNT(*) AS count FROM PaymentFailuresData WHERE TransactionDateTime >= DATE_SUB(CURDATE(), INTERVAL :noOfMonths MONTH) GROUP BY date ORDER BY date ASC", nativeQuery = true)
	List<DateWiseFailCount> dateWiseCountByAllBucketsFromLastNMonths(int noOfMonths);
	
	@Query(value = "SELECT CONCAT('Week ',(WEEK(TransactionDateTime) - WEEK(DATE_FORMAT(TransactionDateTime, '%Y-%m-01')) + 1)) as week, DATE_FORMAT(TransactionDateTime, '%M') AS month,COUNT(*) AS count FROM PaymentFailuresData where bucketName=:bucketName and transactionDateTime >= DATE_SUB(CURDATE(), INTERVAL :noOfMonths MONTH) GROUP BY (WEEK(TransactionDateTime) - WEEK(DATE_FORMAT(TransactionDateTime, '%Y-%m-01')) + 1), MONTH(TransactionDateTime) ORDER BY MONTH(TransactionDateTime), WEEK(TransactionDateTime)", nativeQuery = true)
	List<WeekWiseFailCount> weekWiseCountByBucketFromLastNMonths(String bucketName, int noOfMonths);
	
	@Query(value = "SELECT DATE_FORMAT(TransactionDateTime, '%Y-%m-%d') AS date, COUNT(*) AS count FROM PaymentFailuresData WHERE bucketName=:bucketName and TransactionDateTime >= DATE_SUB(CURDATE(), INTERVAL :noOfMonths MONTH) GROUP BY date ORDER BY date ASC", nativeQuery = true)
	List<DateWiseFailCount> dateWiseCountByBucketFromLastNMonths(String bucketName, int noOfMonths);
	
}