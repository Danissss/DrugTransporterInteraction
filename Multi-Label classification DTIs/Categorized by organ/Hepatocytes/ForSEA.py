import sqlite3
import csv
import os
import sys


TOP20Transporter = [
"BE0001061",
"BE0001188",
"BE0003640",
"BE0003648",
"BE0003647",
"BE0001004",
"BE0003659",
"BE0001042",
"BE0003644",
"BE0003667",
"BE0003668",
"BE0001069",
"BE0003657",
"BE0001067",
"BE0000703",
"BE0001032"]

def complie_to_trainingset(ID_SMILES,TransporterDictionary):

	# select available transporter from each drug
	# for each transporter, map to the list
	# if exist, = 1; = 0 otherwise
	TOP20TransporterFile = open("Hepatocytes.csv","w+")
	

	for key, value in ID_SMILES.items():
		transporter_list = c.execute("SELECT drug_transport_id,drug_transport_name FROM \
			drugbank_transport WHERE drug_id = '{}';".format(key)).fetchall()
		tempList = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
		tempStr = ""
		# transporter_list = c.execute("SELECT drug_transport_id,drug_transport_name FROM \
		# 	drugbank_transport WHERE drug_id = 'DB00514';").fetchall()
		for i in transporter_list:
			transporter_id = i[0]
			transporter_name = i[1]
			if transporter_id in TOP20Transporter:
				index = TOP20Transporter.index(transporter_id)
				tempList[index] = 1

		
		# generate string to write to csv file
		tempStr += str(value)
		for i in tempList:
			tempIntStr = ","+str(i)
			tempStr += tempIntStr
		tempStr += "\n"

		TOP20TransporterFile.write(tempStr)

		
		# transporter_id = transporter_list[0]
		# transporter_name = transporter_list[1]
		# for i in TOP20Transporter:
		# 	if i == transporter_id:

		# print(transporter_list)
		# sys.exit(0)
	return None




def main():
	dbFile = 'test_drugbank.db'
	conn = sqlite3.connect(dbFile)
	global c
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
	ID_SMILES = {}
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
			ID_SMILES[ID] = SMILES
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


	complie_to_trainingset(ID_SMILES,TransporterDictionary)

	print("complie_to_trainingset done!")





if __name__ == "__main__":
	main()