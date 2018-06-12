package xuan.biotech;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


public class DrugDatabase {

	
	public ArrayList<String> drug_s = new ArrayList<String>();
	
	public ArrayList<String> drugSMILES(String filePath) throws IOException{
		
		
		CSVReader reader = new CSVReader(new FileReader(filePath));
		
		String [] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			String smile_string = nextLine[0];
			drug_s.add(smile_string);
		}
		
		reader.close();
		
		return this.drug_s;
		
	}
}
