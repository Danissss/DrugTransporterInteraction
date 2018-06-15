package xuan.biotech;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import com.opencsv.CSVReader;
//import com.opencsv.CSVWriter;
//
//import weka.core.Attribute;

import org.openscience.cdk.smiles.SmilesParser;
//import org.openscience.cdk.tools.CDKHydrogenAdder;
//import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
//import org.openscience.cdk.interfaces.IAtomContainer;
//import org.openscience.cdk.interfaces.IAtomContainerSet;
//import org.openscience.cdk.interfaces.IChemObjectBuilder;
//import org.openscience.cdk.io.SDFWriter;
import org.openscience.cdk.layout.StructureDiagramGenerator;
//import org.openscience.cdk.silent.SilentChemObjectBuilder;
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.commons.lang3.StringUtils;
import org.openscience.cdk.DefaultChemObjectBuilder;
//import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.fingerprint.IBitFingerprint;
import org.openscience.cdk.fingerprint.IFingerprinter;
import org.openscience.cdk.interfaces.IAtomContainer;
//import org.openscience.cdk.fingerprint.MACCSFingerprinter;
//import org.openscience.cdk.fingerprint.PubchemFingerprinter;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.fingerprint.Fingerprinter;
import org.openscience.cdk.similarity.Tanimoto;

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
		Fingerprinter fingerprinter = new Fingerprinter();
		this.fingerprint_1 = fingerprinter.getBitFingerprint(this.container_1);
		
		
		//GenerateFingerPrint GFP = new GenerateFingerPrint();
		//IBitFingerprint IB = GFP.getCDKFingerPrint(this.SMILES1);
		//System.out.println(IB.size());
		//System.out.println(this.fingerprint_1.size()); //return 1024
		
	}
	
	
	//for each compound, get SMILES => IAtomContainer => Fingerprinter
	//calculate the raw_score, then, return all the raw score (highest rank can
	//be determined at E-value 
	public HashMap<String,Float> Calculate_Raw_Score(ArrayList<String> drugDatabase) throws CDKException {
		
		Fingerprinter fingerprinter_1 = new Fingerprinter();
		BitSet fingerprint1_bitset = fingerprinter_1.getFingerprint(this.container_1);
		
		
		for (int i = 0; i < drugDatabase.size(); i++) {
			Fingerprinter fingerprinter_2 = new Fingerprinter();
			
			// Get "second" smiles
			String smiles_2 = drugDatabase.get(i);
			SmilesParser temp_smiles = new SmilesParser(DefaultChemObjectBuilder.getInstance());
			IAtomContainer atom_container   = temp_smiles.parseSmiles(smiles_2);
			StructureDiagramGenerator sdg = new StructureDiagramGenerator();
			sdg.setMolecule(atom_container);
			sdg.generateCoordinates();
			
			IAtomContainer container_2 = sdg.getMolecule();
			BitSet fingerprint2_bitset = fingerprinter_1.getFingerprint(container_2);
			
			float tanimoto_coefficient = Tanimoto.calculate(fingerprint1_bitset, fingerprint2_bitset);
			//System.out.println(tanimoto_coefficient);
			this.SMILES_rawScore.put(smiles_2, tanimoto_coefficient);
			
		}
		
		System.out.println(this.SMILES1);
		
		
		return this.SMILES_rawScore;
	}
	
	// take this.rawScore to convert to Z_Score
	public HashMap<String,Float> Calculate_Z_Score() {
		for (Map.Entry<String, Float> entry : this.SMILES_rawScore.entrySet()) {
		    String Smiles = entry.getKey();
		    Float Tc = entry.getValue();
		    
		}
		
		
		
		return this.SMILES_Z_Score;
	}
	
	
	// take this.SMILES_Z_Score to convert to E-value
	public  HashMap<String,Float> Calculate_E_value() {

		
		
		
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
	
	
	
	//convert RawScore to ZScore
	//reference: Relating protein pharmacology by ligand chemistry
	public Float Zscore(Float score) {
		float Tc_cutoffs = (float) 0.57;
		float raw_score_mean = (float) 4.24e-4;
		float raw_score_sd = (float) 4.49e-3;
		float raw_score_sd_exp = (float) 6.65e-1;
		int size = 100; //not right size
		
		
		float z_score = (float) ((score - raw_score_mean*(size))/raw_score_sd*(Math.pow(size, raw_score_sd_exp)));
		
		return z_score;
	}

}


































