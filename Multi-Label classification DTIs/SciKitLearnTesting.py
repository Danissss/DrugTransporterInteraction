

from sklearn.datasets import make_multilabel_classification
from sklearn.model_selection import train_test_split
from sklearn.metrics import hamming_loss
from skmultilearn.ext import Meka
from scipy.io import arff
from sklearn.naive_bayes import GaussianNB


X, y = make_multilabel_classification(sparse = True,
    return_indicator = 'sparse', allow_unlabeled = False)

X_train, X_test, y_train, y_test = train_test_split(X.toarray(),y,test_size=0.33)


classifier = GaussianNB()
classifier.fit(X_train, y_train)
sys.exit(0)
predictions  = classifier.predict(X_test)
sys.exit(0)
accuracyScore = accuracy_score(y_test,predictions)
print(accuracyScore)