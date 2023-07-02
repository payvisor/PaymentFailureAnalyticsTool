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
    query = "SELECT distinct p.failureReason as failureReason, p.BucketName as BucketName, b.tags as tags FROM PaymentFailuresData p inner join BucketsData b on p.BucketName=b.BucketName;"
    data = pd.read_sql(query, connection)
    print(data)
    connection.close()
    return data

# Step 8: Expose API using Flask
app = Flask(__name__)

@app.route('/trainModel', methods=['GET'])
def train_model():
    # Step 2: Feature engineering
    vectorizer = TfidfVectorizer()
    data = load_data_from_db()
    X = vectorizer.fit_transform(data['failureReason'] + ' ' + data['tags'])
    y = data['BucketName']

    # Save the vectorizer
    joblib.dump(vectorizer, 'vectorizer.pkl')

    # Step 3: Splitting data into training and testing sets
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

    # Step 4: Model training
    model = LogisticRegression()
    model.fit(X_train, y_train)

    # Step 5: Model evaluation
    accuracy = model.score(X_test, y_test)
    print("Accuracy:", accuracy)

    # Step 6: Save the trained model
    joblib.dump(model, 'paytrack_model.pkl')

    return "Model trained successfully!"

def train_model_periodically():
    print("Training model...")
    train_model()

def main(minutes):
    # If minutes is provided, schedule the job
    if minutes is not None:
        # Schedule the job to run every specified minutes
        schedule.every(minutes).minutes.do(train_model_periodically)
        while True:
            schedule.run_pending()
            time.sleep(1)
    else:
        # Run the application to expose the API
        app.run(port=7777)

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--minutes', type=int, default=None, help='Number of minutes between model training')
    args = parser.parse_args()

    main(args.minutes)
