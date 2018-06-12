package xuan.biotech;

import java.util.HashMap;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.fingerprint.Fingerprinter;
import org.openscience.cdk.fingerprint.IBitFingerprint;
import org.openscience.cdk.fingerprint.IFingerprinter;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.smiles.SmilesParser;

public class GenerateFingerPrint {

	
	
	
	public IBitFingerprint getCDKFingerPrint(String SMILES) throws CDKException {
		
		
		SmilesParser temp_smiles = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		IAtomContainer atom_container   = temp_smiles.parseSmiles(SMILES);
		StructureDiagramGenerator sdg = new StructureDiagramGenerator();
		sdg.setMolecule(atom_container);
		sdg.generateCoordinates();
		
		IAtomContainer container_1 = sdg.getMolecule();
		HashMap<Object,Object> properties = new HashMap<Object,Object>();
		properties.put("SMILES", SMILES);
		container_1.addProperties(properties);
		
		IFingerprinter fingerprinter = new Fingerprinter();
		IBitFingerprint fingerprint_1 = fingerprinter.getBitFingerprint(container_1);
		
		return fingerprint_1;
		
	}
}
