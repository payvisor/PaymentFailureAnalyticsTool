from faker import Faker
import random
from datetime import datetime, timedelta

fake = Faker()

payment_methods = ["Visa", "Mastercard", "American Express", "PayPal", "Stripe"]

buckets = {
    'Card Related Issues' : [
    "Invalid card number",
    "Card expired",
    "Card declined by issuer",
    "Incorrect CVV/CVC",
    "Card limit exceeded",
    "Card reported lost or stolen",
    "Card blocked by cardholder",
    "Card not supported",
    "Incorrect cardholder name",
    "Card not activated",
    "Incorrect card type selected"
    ],
    'Authorization & Account Issues' : [
    "Insufficient funds",
    "Bank authorization failed",
    "Account frozen",
    "Account closed",
    "Account verification required",
    "Insufficient credit limit",
    "Account on hold",
    "Bank account closed"],
    'Transaction Issues' : [
    "Payment Limit Exceeded Exception",
    "Invalid payment amount",
    "Transaction declined by merchant",
    "Transaction amount exceeds limit",
    "Invalid transaction ID",
    "Duplicate transaction",
    "Transaction ID already used",
    "Transaction amount too low",
    "Invalid merchant identification",
    "Invalid payment request",
    "Payment request expired",
    "Payment request canceled",
    "Duplicate payment request"],
    'Payment Gateway Issues' : [
    "PaymentGatewayErrorException",
    "PaymentGatewayDowntimeException",
    "PaymentTimeoutException",
    "Payment gateway error",
    "Network connection timeout",
    "Payment gateway maintenance",
    "Payment processor error",
    "Payment gateway misconfiguration",
    "Payment processor unavailable",
    "System upgrade in progress",
    "Payment gateway blocked",
    "Payment gateway integration error",
    "Technical issue with payment gateway",
    "Payment gateway security breach",
    "Payment gateway capacity reached",
    "Payment gateway configuration issue"],
    'Fraud & Security Issues' : [
    "PaymentDeclinedByIssuerException",
    "Suspicious activity detected",
    "Cardholder authentication required",
    "Cardholder disputed the transaction",
    "Payment source not authorized",
    "Fraud detection triggered",
    "Payment declined by third-party fraud system",
    "Cardholders bank flagged transaction as fraudulent",
    "Payment security violation"],
    'Communication & Connection Issues' : [
    "Payment timeout",
    "Server error",
    "Payment processor downtime",
    "Cardholders bank not participating",
    "Cardholders bank not reachable",
    "Cardholders bank offline",
    "Blocked IP address",
    "Network connectivity issues",
    "Service temporarily unavailable",
    "Temporary issue with payment service provider",
    "Server overload"],
    'Payment Method Issues' : [
    "PaymentValidationException",
    "PaymentVerificationFailureException",
    "Incompatible payment method",
    "Payment method expired",
    "Invalid API credentials",
    "Incorrect payment method selected",
    "Payment method not accepted",
    "Payment source expired",
    "Payment method revoked"],
    'Currency & Conversion Issues' : [
    "Invalid currency",
    "Currency conversion error"],
    'Data & Information Issues' : [
    "Invalid billing address",
    "Invalid expiration date",
    "Invalid security code",
    "Incorrectly formatted payment data",
    "Incorrect billing information",
    "Invalid account holder name",
    "Invalid check digit",
    "Invalid merchant category code"],
    'Miscellaneous Issues' : [
    "Payment processing error",
    "Incompatible browser or device",
    "Restricted card type",
    "Restricted country for payment",
    "Declined by risk management",
    "PaymentLimitExceededException",
    "Payment processing limit exceeded",
    "Exceeded transaction frequency limit",
    "Exceeded maximum allowable retries",
    "Exceeded maximum transaction amount",
    "Exceeded maximum transaction limit"],
    'System Issues' : [
    "PaymentProcessingException",
    "ClassNotFoundException",
    "InvocationTargetException",
    "InterruptedException",
    "NoSuchMethodException",
    "NullPointerException",
    "ArrayIndexOutOfBoundsException",
    "IllegalStateException",
    "ClassCastException",
    "IllegalArgumentException"
    ]
}

error_codes = ["ERR1001", "ERR2002", "ERR3003", "ERR4004", "ERR5005"]

transaction_statuses = [
    "Success",
    "Failed"
]

email_domains = [
    "@gmail.com", 
    "@hotmail.com", 
    "@yahoo.com",
    "@outlook.com",
    "@zoho.com"
]

insertIntopaymentsData = "INSERT INTO PaymentsData (TransactionID, FirstName, LastName, EmailID, Address, Country, Amount, Currency, PaymentMethod, PaymentLocation, TransactionStatus, FailureReason, ErrorCode, TransactionDateTime) VALUES \n"

for idx in range(1, 10001):
    time_stamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    transaction_id = str(fake.random_int(min=1, max=10000))+str(idx)
    first_name = fake.first_name()
    last_name = fake.last_name()
    email_id = first_name+last_name+str(fake.random_int(1, 1000))+fake.random_element(email_domains)
    address = fake.street_address().replace("'", "")
    country = fake.country().replace("'", "")
    amount = str(fake.random_int(1, 1000))
    currency = fake.currency_code()
    payment_method = fake.random_element(payment_methods)
    payment_location = fake.city()
    transaction_status = fake.random_element(transaction_statuses)
    error_code=""
    failure_reason=""
    if(transaction_status=='Failed'):
        error_code = fake.random_element(error_codes)
        failure_reason = failure_reason = random.choice(buckets[random.choice(list(buckets.keys()))])
    else:
        error_code = None
        failure_reason = None
    txn_datetime = fake.date_time_this_year().strftime("%Y-%m-%d %H:%M:%S")

    if(error_code is None and failure_reason is None):
        paymentsDataEntry ="("+ f"'{transaction_id}','{first_name}','{last_name}','{email_id}','{address}','{country}',{amount},'{currency}','{payment_method}','{payment_location}','{transaction_status}',null,null,'{txn_datetime}'"+"),\n"
    else:
        paymentsDataEntry ="("+ f"'{transaction_id}','{first_name}','{last_name}','{email_id}','{address}','{country}',{amount},'{currency}','{payment_method}','{payment_location}','{transaction_status}','{failure_reason}','{error_code}','{txn_datetime}'"+"),\n"
    insertIntopaymentsData += paymentsDataEntry

def findTagsForBucket(bucket):
    if bucket in buckets:
        return ','.join(buckets[bucket])
    
insertIntoTrainMLData = "INSERT INTO TrainMLFailureBucketsData (FailureReason, BucketName) VALUES \n"
insertIntoBucketsData = "INSERT INTO BucketsData (BucketName, Tags, CreatedDateTime) VALUES \n"
insertIntoBucketETemplatesData = "INSERT INTO BucketEmailTemplates (BucketName, CreatedDateTime) VALUES \n"

for bucketName, failureReasons in buckets.items():
    for failureReason in failureReasons:
        failure_reason = failureReason
        bucket_name = bucketName
        trainMLDataEntry ="("+ f"'{failure_reason}','{bucket_name}'"+"),\n"
        insertIntoTrainMLData += trainMLDataEntry

for name, bucket in buckets.items():
    bucket_name = name
    tags=findTagsForBucket(bucket_name)
    bucketsDataEntry ="("+ f"'{bucket_name}','{tags}',sysdate()"+"),\n"
    bucketEmailTemplatesDataEntry ="("+ f"'{bucket_name}',sysdate()"+"),\n"
    insertIntoBucketsData += bucketsDataEntry
    insertIntoBucketETemplatesData += bucketEmailTemplatesDataEntry

with open("Mock Data for PaymentsData BucketsData BucketEmailTemplatesData and TrainMLFailureBucketsData.sql", "w") as paymentsDataFile:
    paymentsDataFile.write(insertIntopaymentsData[:-2]+";\n\n\n")

with open("Mock Data for PaymentsData BucketsData BucketEmailTemplatesData and TrainMLFailureBucketsData.sql", "a") as bucketsDataFile:
    bucketsDataFile.write(insertIntoBucketsData[:-2]+";\n\n\n")

with open("Mock Data for PaymentsData BucketsData BucketEmailTemplatesData and TrainMLFailureBucketsData.sql", "a") as bucketEmailTemplatesDataFile:
    bucketEmailTemplatesDataFile.write(insertIntoBucketETemplatesData[:-2]+";\n\n\n")

with open("Mock Data for PaymentsData BucketsData BucketEmailTemplatesData and TrainMLFailureBucketsData.sql", "a") as trainMLDataFile:
    trainMLDataFile.write(insertIntoTrainMLData[:-2]+";")
