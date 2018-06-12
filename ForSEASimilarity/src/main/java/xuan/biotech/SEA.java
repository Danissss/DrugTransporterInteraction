package xuan.biotech;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.HashMap;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import weka.core.Attribute;

import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.io.SDFWriter;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.fingerprint.IBitFingerprint;
import org.openscience.cdk.fingerprint.IFingerprinter;
import org.openscience.cdk.fingerprint.MACCSFingerprinter;
import org.openscience.cdk.fingerprint.PubchemFingerprinter;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.fingerprint.Fingerprinter;

public class SEA {
	
	public String SMILES1;
	public IAtomContainer container_1;
	public IBitFingerprint  fingerprint_1; //1024 bit
	
	
	
	public HashMap<String,Float> SMILES_rawScore = new HashMap<String,Float>(); //this smiles is not the query smiles
	public HashMap<String,Float> SMILES_Z_Score = new HashMap<String,Float>(); //same	
	public HashMap<String,Float> SMILES_E_value = new HashMap<String,Float>(); //same
	
//	public float rawScore;
//	public float Z_Score;
//	public float E_value;
	
	public int rank = 10;
	
	public SEA(String SMILES1) throws CDKException{
		this.SMILES1 = SMILES1;
		SmilesParser temp_smiles = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		IAtomContainer atom_container   = temp_smiles.parseSmiles(this.SMILES1);
		StructureDiagramGenerator sdg = new StructureDiagramGenerator();
		sdg.setMolecule(atom_container);
		sdg.generateCoordinates();
		this.container_1 = sdg.getMolecule();
		
		//get FingerPrint
		IFingerprinter fingerprinter = new Fingerprinter();
		this.fingerprint_1 = fingerprinter.getBitFingerprint(this.container_1);
		
		
		//GenerateFingerPrint GFP = new GenerateFingerPrint();
		//IBitFingerprint IB = GFP.getCDKFingerPrint(this.SMILES1);
		//System.out.println(IB.size());
		//System.out.println(this.fingerprint_1.size()); //return 1024
		
	}
	
	
	//for each compound, get SMILES => IAtomContainer => Fingerprinter
	//calculate the raw_score, then, return all the raw score (highest rank can
	//be determined at E-value 
	public HashMap<String,Float> Calculate_Raw_Score(ArrayList<String> drugDatabase) {
		
		
		
		
		
		return this.SMILES_rawScore;
	}
	
	// take this.rawScore to convert to Z_Score
	public HashMap<String,Float> Calculate_Z_Score() {
		//for each compound, get SMILES => IAtomContainer => Fingerprinter
		//calculate the raw_score, then, return all the raw score (highest rank can
		//be determined at E-value 
		
		
		
		return this.SMILES_Z_Score;
	}
	
	
	// take this.SMILES_Z_Score to convert to E-value
	public  HashMap<String,Float> Calculate_E_value() {
		//for each compound, get SMILES => IAtomContainer => Fingerprinter
		//calculate the raw_score, then, return all the raw score (highest rank can
		//be determined at E-value 
		
		
		
		return this.SMILES_E_value;
	}
	
	
	
	
	
	
	
	
	
	
	public int getRank() {
		return this.rank;
	}
	/*
	 */
	public void setRank(int ranks) {
		this.rank = ranks;
	}

}


































