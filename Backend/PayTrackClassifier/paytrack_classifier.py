import os
from flask import Flask, request, jsonify
import joblib
import requests
import logging
from logging.handlers import TimedRotatingFileHandler
from datetime import datetime

app = Flask(__name__)

# Get the current directory
current_dir = os.path.dirname(os.path.abspath(__file__))

# Create the ML_Log folder if it doesn't exist
log_folder = os.path.join(current_dir, 'ML_Log')
os.makedirs(log_folder, exist_ok=True)

# Configure logging
log_file = os.path.join(log_folder, "app.log")
logging.basicConfig(level=logging.INFO)
handler = TimedRotatingFileHandler(log_file, when="midnight", backupCount=30)
formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
handler.setFormatter(formatter)
logging.getLogger('').addHandler(handler)

@app.route('/predict_bucket', methods=['POST'])
def predict_bucket():
    # Variables
    response = ''
    data = request.json

    # Consider the failure reason as a single feature
    feature = data['failureReason']

    # Load the trained model and vectorizer
    model = joblib.load('paytrack_model.pkl')
    vectorizer = joblib.load('vectorizer.pkl')
    
    # Encode the feature using the vectorizer
    encoded_feature = vectorizer.transform([feature])

    # remove timestamp and input_type
    # Check if '@timestamp' column exists
    if '@timestamp' in data:
        del data['@timestamp']

    # Check if 'input_type' column exists    
    if 'input_type' in data:    
        del data['input_type']
    
    # Predict the bucket using the model and add bucket
    predicted_bucket = model.predict(encoded_feature)[0]
    data['bucketName'] = predicted_bucket

    # Add MySQL timestamp value
    timestamp = datetime.now().strftime("%Y-%m-%dT%H:%M:%S.%fZ")
    data['timestamp'] = timestamp
    
    logging.info(data)

    try:
        apiResponse = requests.post('http://payvisor.ap-south-1.elasticbeanstalk.com/paymentFailuresData/addAndNotify', json=data)
        logging.info(apiResponse)
        if apiResponse.status_code == 200:
            response = {"msg" : apiResponse.text}
        else:
            response = {"msg": "Failed to call AddandNotify API"}
    except requests.exceptions.RequestException as e:
        logging.error(e)
        response = {"msg": "Failed to call AddandNotify API"}
    
    logging.info(response)
    return jsonify(response)

@app.route('/getBucket', methods=['POST'])
def get_bucket():
    # Variables
    data = request.json

    # Consider the failure reason as a single feature
    feature = data['failureReason']

    # Load the trained model and vectorizer
    model = joblib.load('paytrack_model.pkl')
    vectorizer = joblib.load('vectorizer.pkl')
    
    # Encode the feature using the vectorizer
    encoded_feature = vectorizer.transform([feature])
    
    # Predict the bucket using the model and add bucket
    predicted_bucket = model.predict(encoded_feature)[0]
    
    return {"bucket": predicted_bucket}

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5050)
