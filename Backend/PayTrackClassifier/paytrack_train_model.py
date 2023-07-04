import argparse
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split
import joblib
from flask import Flask, request
import pymysql
import schedule
import time
import logging
from logging.handlers import TimedRotatingFileHandler
from datetime import datetime

def db_connection():
    # Database connection parameters
    host = 'payvisordb.ce47grpolldi.ap-south-1.rds.amazonaws.com'
    user = 'admin'
    password = 'Payvisor.123'
    database = 'payvisor'
    port = 3306

    # Establish a database connection
    connection = pymysql.connect(
        host=host,
        user=user,
        password=password,
        database=database,
        port=port
    )

    return connection

# Step 1: Data loading from the database query
def load_data_from_db():
    # Database connection and query execution
    connection = db_connection()
    query = "select tml.failureReason,b.bucketName,b.tags from TrainMLFailureBucketsData tml right join BucketsData b on tml.BucketName=b.BucketName order by b.bucketName"
    data = pd.read_sql(query, connection)
    connection.close()
    return data

# Configure logging
log_file = 'ML_Train_Log/training.log'
handler = TimedRotatingFileHandler(log_file, when="midnight", backupCount=30)
formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
handler.setFormatter(formatter)
logging.getLogger('').addHandler(handler)
logging.getLogger('').setLevel(logging.INFO)

# Step 8: Expose API using Flask
app = Flask(__name__)

@app.route('/trainModel', methods=['GET'])
def train_model():
    # Step 2: Feature engineering
    vectorizer = TfidfVectorizer()
    data = load_data_from_db()

    # Preprocess data to replace np.nan values with empty string
    data.fillna('', inplace=True)

    X = vectorizer.fit_transform(data['failureReason'] + ' ' + data['tags'])
    y = data['bucketName']

    # Save the vectorizer
    joblib.dump(vectorizer, 'vectorizer.pkl')

    # Step 3: Splitting data into training and testing sets
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

    # Step 4: Model training
    model = LogisticRegression()
    model.fit(X_train, y_train)

    # Step 5: Model evaluation
    accuracy = model.score(X_test, y_test)
    logging.info("Accuracy: %s", accuracy)

    # Step 6: Save the trained model
    joblib.dump(model, 'paytrack_model.pkl')
    logging.info("Model trained successfully!")

    return "Model trained successfully!"

def train_model_periodically():
    logging.info("Training model...")
    train_model()

def main(hours):
    # If hours is provided, schedule the job
    if hours is not None:
        # Run the application to expose the API
        app.run(port=7777)
        # Schedule the job to run every specified hours
        logging.info("Scheduler setup completed for every " + str(hours) + " hours")
        schedule.every(hours).hours.do(train_model_periodically)
        while True:
            schedule.run_pending()
            time.sleep(1)
    else:
        # Run the application to expose the API
        app.run(port=7777)

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--hours', type=int, default=None, help='Number of hours between model training')
    args = parser.parse_args()

    main(args.hours)
