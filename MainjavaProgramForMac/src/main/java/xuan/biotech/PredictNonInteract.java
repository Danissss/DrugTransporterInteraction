package xuan.biotech;


import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.smiles.SmilesParser;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import weka.classifiers.trees.RandomForest;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;




public class PredictNonInteract {
	
	
	
	/*****
	 * 
	 * @Para: inputFilePath - should be the path for csv file with all the feature value
	 * 
	 * 
	 */
	public String predictNonInteract(String inputFilePath, String smilesFile) throws FileNotFoundException, Exception {
		
		//model path 
		String workingDir = System.getProperty("user.dir");
		String substrateModelPath = workingDir+"/wekaMachineLearningModel/MDR1Substrate.model";
		String inhibitorModelPath = workingDir+"/wekaMachineLearningModel/MDR1Inhibitor.model";
		RandomForest RFSusbtrate = (RandomForest) SerializationHelper.read(new FileInputStream(substrateModelPath));
		RandomForest RFInhibitor = (RandomForest) SerializationHelper.read(new FileInputStream(inhibitorModelPath));
		
		//store the Molecule name and SMILES from inputFilePath
		CSVReader reader = new CSVReader(new FileReader(smilesFile));
		ArrayList<String> SMILESArray = new ArrayList<String>();
		ArrayList<String> NameArray = new ArrayList<String>();
		String [] nextLine;
		 
		//this loop will read all smile string, and convert it to sdf format of molecule
	    //then, write it back to sdf file SDFWriter sdw
	    while ((nextLine = reader.readNext()) != null) {    
	    	   String smile_string = nextLine[0];    //contain smile string
	    	   String name_string = nextLine[1];
	    	   SMILESArray.add(smile_string); 
	    	   NameArray.add(name_string);
	    }
	    reader.close();
	    
		
		
		
		
		//generate the arff file for prediction
		String arffFilePath = workingDir + "/forTempFile/temp.arff";
		// load CSV
		CSVLoader loader = new CSVLoader();
		Instances data = null;
		File inputFile = new File(inputFilePath);

		loader.setSource(inputFile);
		data = loader.getDataSet();
		
		int totalInstanceNumber = data.numInstances();
		FastVector<String> association = new FastVector<String>();
		association.addElement("Yes");
		association.addElement("No");
		// yes ; no means non-whatever
		data.insertAttributeAt(new Attribute("Association",association), data.numAttributes());
	
		// save ARFF
		ArffSaver saver = new ArffSaver();
		//System.out.println(data); //data is correct with ? at end
		
		saver.setInstances(data);
		try {
			saver.setFile(new File(arffFilePath));
			saver.writeBatch();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		MakePrediction MP = new MakePrediction();
		
		
//		String[] substrateResult = new String[totalInstanceNumber];
//		String[] inhibitorResult = new String[totalInstanceNumber];
		
 		String[] substrateResult = MP.makePrediction_Random_Forest_GUILargeScale(substrateModelPath, arffFilePath, "MDR1");
 		String[] inhibitorResult = MP.makePrediction_Random_Forest_GUILargeScale(inhibitorModelPath, arffFilePath, "MDR1");
		
		
//		System.out.println(Arrays.toString(substrateResult));
//		System.out.println(Arrays.toString(inhibitorResult));

		PrintWriter writer = new PrintWriter("/Users/xuan/Desktop/PredictedResult.csv", "UTF-8");
		for (int i = 0; i < SMILESArray.size(); i++) {
			String[] toFile = new String[3];
			String forCSV = "";
			toFile[0] = SMILESArray.get(i);
			toFile[1] = NameArray.get(i);
			forCSV += SMILESArray.get(i);
			forCSV += ","+ NameArray.get(i);
			
			String substrate_result = substrateResult[i];
			String inhibitor_result = inhibitorResult[i];
			//System.out.println(substrate_result);
			
			String result = null;
			if (substrate_result.equals(inhibitor_result) && substrate_result.equals("Yes")) {
				result = "interacting";
			}else if (substrate_result.equals(inhibitor_result) && substrate_result.equals("No")) {
				result = "NonInteracting";
			}else if (substrate_result.equals("Yes") && inhibitor_result.equals("No")) {
				result = "substrate";
			}else {
				result = "inhibitor";
			}
			toFile[2] = result;
//			System.out.println(Arrays.toString(toFile));
			forCSV += ","+result;
			writer.println(forCSV);
//			System.out.println(forCSV);
			
		}
		writer.close();
		System.exit(0);
		//inst = 9,5,0,3,19,0,11,-2.7457,7.538868,65.6159,-4.23,1.46,39.947481,197.62,307.083806,25.356519,0.831149,-0.338743,-0.11123,0.252759,-0.35329,31.854508,23.493247,31.481472,33.401626,31.993195,2,1,?,?,?,?,?,?,?,?,?
		//can only have one ? mark at the end

//        double result = RFSusbtrate.classifyInstance(inst);
//        String results = predict.classAttribute().value((int)result);
//        System.out.println(results);
		
		
		
		
		return "done";
	}
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException, Exception {
		
		String Path = "/Users/xuan/Desktop/Output.csv";
		String smilePath = "/Users/xuan/Desktop/test.csv";
		PredictNonInteract PNI = new PredictNonInteract();
		PNI.predictNonInteract(Path,smilePath);
		
		
		
	}
	

}
