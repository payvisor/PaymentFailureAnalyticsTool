import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split
import joblib

# Step 1: Data loading
data = pd.read_csv('../train/training-data-buckets-tags-failurereasons.csv')

# Step 2: Data preprocessing
# Assuming no additional preprocessing is required

# Step 3: Feature engineering
vectorizer = TfidfVectorizer()
X = vectorizer.fit_transform(data['failureReason'] + ' ' + data['tags'])
y = data['bucketName']

# Save the vectorizer
joblib.dump(vectorizer, 'vectorizer.pkl')

# Step 4: Splitting data into training and testing sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Step 5: Model training
model = LogisticRegression()
model.fit(X_train, y_train)

# Step 6: Model evaluation
accuracy = model.score(X_test, y_test)
print("Accuracy:", accuracy)

# Step 4: Save the trained model
joblib.dump(model, 'paytrack_model.pkl')
