import sqlite3
import csv
import os
import sys






def main():
	dbFile = 'test_drugbank.db'
	conn = sqlite3.connect(dbFile)
	c = conn.cursor()

	# Transporter
	transporter = open('allTransporter.txt','r').readlines()
	TransporterID = []
	TransporterName = []
	TransporterDictionary = {}

	for i in transporter:
		trans_splited = i.split("\t")
		T_ID = trans_splited[0]
		T_Name = trans_splited[1].replace("\n","")

		TransporterID.append(T_ID)
		TransporterName.append(T_Name)

		TransporterDictionary[T_ID] = T_Name

	if len(TransporterID) != len(TransporterName):
		print("TransporterID != TransporterName")


	# Drug
	DrugID = []
	DrugName = []
	DrugSMILES = []
	ID_NAME = {}
	NAME_SMILES = {}
	with open("allDrug.csv", encoding='ISO-8859-1',newline='') as csvfile:
		file = csv.reader(csvfile)
		for i in file:
			ID = i[1]
			Name = i[2]
			SMILES = i[0]
			DrugID.append(ID)
			DrugName.append(Name)
			DrugSMILES.append(SMILES)
			ID_NAME[ID] = Name
			NAME_SMILES[Name] = SMILES

	if len(DrugID) != len(DrugName) & len(DrugID) != len(DrugSMILES):
		print("something wrong")

	# Map Bipartite Graph 
	# Return the TransporterID
	newFile = open("MatchingTransTODrug.csv","w+")
	for i in DrugID:
		Str_for_csv = ""
		
		TransporterList = c.execute("SELECT drug_transport_id FROM drugbank_transport WHERE drug_id = '{}' ;".format(i)).fetchall()
		# TransporterList = c.execute("SELECT drug_transport_id FROM drugbank_transport WHERE drug_id = 'DB00197';").fetchall()
		
		Str_for_csv += str(i)
		for i in TransporterList:
			# List_for_csv.append(i[0])
			Str = ","+i[0]

			Str_for_csv += Str
		Str_for_csv += "\n"

		newFile.write(Str_for_csv)


	print("done")





if __name__ == "__main__":
	main()