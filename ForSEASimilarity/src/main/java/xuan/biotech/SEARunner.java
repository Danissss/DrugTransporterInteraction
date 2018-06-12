package xuan.biotech;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.openscience.cdk.exception.CDKException;

public class SEARunner {

	
	
	public static void main(String[] args) throws IOException, CDKException {
		String querySMILES = args[0];
		
		
		String workingDir = System.getProperty("user.dir");
		workingDir += "/DatabaseFolder/allDrug.csv";
		
		DrugDatabase drugDatabase = new DrugDatabase();
		
		ArrayList<String> newList =  drugDatabase.drugSMILES(workingDir);
		
		
		
		SEA newSEA = new SEA(querySMILES);
		newSEA.Calculate_Raw_Score(newList);
		newSEA.Calculate_Z_Score();
		
	}
}
