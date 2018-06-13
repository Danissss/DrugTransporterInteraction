
# Scikit-learn for multi-label classification

import scipy
import numpy as np
import pandas as pd
from scipy.io import arff

from skmultilearn.dataset import Dataset
from skmultilearn.problem_transform import BinaryRelevance
from skmultilearn.problem_transform import ClassifierChain
from skmultilearn.problem_transform import LabelPowerset
from skmultilearn.adapt import MLkNN

from sklearn.naive_bayes import GaussianNB
from sklearn.metrics import accuracy_score
from sklearn.cross_validation import train_test_split




def problemTransformation(data):

	# Binary Relevance
	# Classifier Chains
	# Label Powerset 

	# initialize multi-label classifier 
	# with a gaussian naive bayes base classifier
	classifier = BinaryRelevance(GaussianNB())   
	classifier.fit(X_train, y_train) # train
	predictions = classifier.predict(X_test) # predict 
	accuracyScore = accuracy_score(y_test,predictions)


	classifier = ClassifierChain(GaussianNB())
	classifier.fit(X_train, y_train)
	predictions = classifier.predict(X_test)
	accuracyScore = accuracy_score(y_test,predictions)


	classifier = LabelPowerset(GaussianNB())
	classifier.fit(X_train, y_train)
	predictions = classifier.predict(X_test)
	accuracyScore = accuracy_score(y_test,predictions)
	return None


def adapted(data):


	classifier = MLkNN(k=20)
	classifier.fit(X_train, y_train)
	predictions = classifier.predict(X_test)
	accuracyScore = accuracy_score(y_test,predictions)
	return None


def main():

	data, meta = scipy.io.arff.loadarff('OutputTOP20MFML.arff')
	X,y = skmultilearn.dataset.Dataset.load_arff_to_numpy('OutputTOP20MFML.arff')

	df = pd.DataFrame(data)
	df.header()

	problemTransformation(data)
	adapted(data)

	print("Done!")


if __name__ == "__main__":
	main()