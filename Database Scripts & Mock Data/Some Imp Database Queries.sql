select distinct pfd.FailureReason,pfd.BucketName as predicted_bucketName,tml.BucketName as trained_bucketName,
case when pfd.BucketName=tml.BucketName then 'Correct Prediction'
when pfd.BucketName!=tml.BucketName then 'Wrong Prediction'
end as 'Match_or_NoMatch'
from PaymentFailuresData pfd 
inner join TrainMLFailureBucketsData tml on pfd.FailureReason=tml.FailureReason order by predicted_bucketName,pfd.FailureReason;

select Match_or_NoMatch, count(Match_or_NoMatch) from
(select distinct pfd.FailureReason,pfd.BucketName as predicted_bucketName,tml.BucketName as trained_bucketName,
case when pfd.BucketName=tml.BucketName then 'Correct Prediction'
when pfd.BucketName!=tml.BucketName then 'Wrong Prediction'
end as 'Match_or_NoMatch'
from PaymentFailuresData pfd 
inner join TrainMLFailureBucketsData tml on pfd.FailureReason=tml.FailureReason order by pfd.BucketName,pfd.FailureReason)abc
group by Match_or_NoMatch;


SET SQL_SAFE_UPDATES = 0;
update BucketEmailTemplates set sendEmail='Yes';