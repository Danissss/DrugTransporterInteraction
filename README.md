# DrugTransporterInteraction

A comprehensive research project for predicting and analyzing drug-transporter interactions using machine learning and cheminformatics approaches.

## Overview

This project provides multiple tools and methodologies for studying drug-transporter interactions, combining Java-based GUI applications, Python machine learning scripts, and similarity-based analysis methods. The system focuses on 10 major membrane transporters involved in drug disposition.

## Project Structure

### 1. **MainjavaProgramForMac/**
The main Java Swing GUI application (DrugPorter) for drug-transporter interaction prediction.

- **Technology**: Java 8+, Maven, Weka, CDK
- **Features**:
  - Interactive GUI for prediction and browsing
  - Pre-trained Random Forest models for 10 transporters
  - Chemical structure search and analysis
  - Batch prediction capabilities
  - Molecular fingerprint and feature generation
- **See**: [MainjavaProgramForMac/README.md](MainjavaProgramForMac/README.md) for detailed documentation

### 2. **ForSEASimilarity/**
Java application for similarity-based drug-transporter interaction analysis.

- **Technology**: Java, Maven, Weka, CDK
- **Purpose**: Calculate chemical similarity and predict interactions using SEA (Similarity Ensemble Approach)
- **Components**:
  - Drug database processing
  - Machine learning model training/testing
  - ARFF/CSV file generation

### 3. **Multi-Label classification DTIs/**
Python-based multi-label classification for drug-transporter interactions.

- **Technology**: Python, scikit-learn, skmultilearn
- **Features**:
  - Multi-label classification algorithms (Binary Relevance, Classifier Chain, Label Powerset, MLkNN)
  - Handles multiple simultaneous transporter predictions
  - Feature extraction and model evaluation
- **Key Files**:
  - `Multi_Label_classification.py` - Main classification pipeline
  - `ForSEA.py` - Data preparation for SEA analysis
  - `OutputTOP20MF.csv` - Top 20 molecular features
  - `TOP20Transporter.csv` - Focus on 20 most important transporters

### 4. **SEA Similarity Score/**
Python scripts for calculating Similarity Ensemble Approach (SEA) scores.

- **Technology**: Python, SQLite
- **Purpose**: Calculate drug-transporter interaction probabilities based on chemical similarity
- **Components**:
  - Drug-transporter mapping
  - Similarity score calculation
  - Database querying and export

## Target Transporters

The project focuses on 10 major drug transporters:

### ABC Transporters (ATP-Binding Cassette)
- **ABCC1** (MRP1) - Multidrug Resistance Protein 1
- **ABCC2** (MRP2) - Multidrug Resistance Protein 2
- **ABCG2** (BCRP) - Breast Cancer Resistance Protein
- **MDR1** (ABCB1/P-gp) - P-glycoprotein

### SLC Transporters (Solute Carrier)
- **SLC22A1** (OCT1) - Organic Cation Transporter 1
- **SLC22A2** (OCT2) - Organic Cation Transporter 2
- **SLC22A6** (OAT1) - Organic Anion Transporter 1
- **SLC22A8** (OAT3) - Organic Anion Transporter 3

### SLCO Transporters (Organic Anion Transporting Polypeptides)
- **SLCO1A2** (OATP1A2)
- **SLCO1B1** (OATP1B1)

## Technologies Used

### Java Applications
- Chemistry Development Kit (CDK) 2.1.1 - Chemical structure processing
- Weka 3.8.2 - Machine learning
- OpenCSV 4.1 - CSV handling
- MySQL & SQLite - Database connectivity

### Python Scripts
- scikit-learn - Machine learning algorithms
- skmultilearn - Multi-label classification
- pandas/numpy - Data processing
- scipy - Scientific computing

## Getting Started

### Prerequisites
- **Java**: JDK 8 or higher
- **Maven**: 3.x
- **Python**: 3.x (for Python scripts)
- **Database**: MySQL or SQLite (optional, for full functionality)

### Quick Start - Java GUI Application

```bash
cd MainjavaProgramForMac
mvn clean install
mvn exec:java -Dexec.mainClass="xuan.biotech.GUIMainPage"
```

### Quick Start - Python Multi-Label Classification

```bash
cd "Multi-Label classification DTIs"
pip install scikit-learn scikit-multilearn pandas numpy scipy
python Multi_Label_classification.py
```

## Machine Learning Models

The project includes pre-trained Random Forest models for each transporter, stored in:
- `MainjavaProgramForMac/wekaMachineLearningModel/`
- `MainjavaProgramForMac/DatabaseFolder/`
- `ForSEASimilarity/wekaMachineLearningModel/`

Models are trained on:
- Molecular fingerprints (CDK fingerprints)
- Molecular features (physicochemical properties)
- Known drug-transporter interaction data

## Data Files

- **allDrug.csv** - Comprehensive drug database
- **allTransporter.txt** - Transporter information
- **TOP20Transporter.csv** - Focus set of 20 important transporters
- **.arff files** - Weka-compatible machine learning datasets
- **.model files** - Pre-trained Weka models

## Output Formats

- **CSV** - Predictions and analysis results
- **ARFF** - Weka machine learning format
- **SDF** - Chemical structure files

## Use Cases

1. **Drug Development**: Predict transporter interactions for novel compounds
2. **ADME Prediction**: Understand drug absorption, distribution, metabolism, and excretion
3. **Drug-Drug Interactions**: Identify potential interactions via transporter competition
4. **Pharmacokinetics**: Predict tissue distribution and clearance
5. **Research**: Study structure-activity relationships for transporters

## Project Workflows

### Workflow 1: Single Drug Prediction
1. Open MainjavaProgramForMac GUI
2. Input SMILES or upload SDF
3. Generate molecular features
4. Run prediction across all transporters
5. View/export results

### Workflow 2: Large-Scale Analysis
1. Prepare drug dataset (CSV/SDF)
2. Use Python scripts for feature extraction
3. Run multi-label classification
4. Analyze results statistically

### Workflow 3: Similarity-Based Prediction
1. Use ForSEASimilarity application
2. Calculate chemical similarity scores
3. Apply SEA methodology
4. Generate predictions based on similar compounds

## Research Applications

This project supports research in:
- Computational pharmacology
- Drug design and discovery
- ADME/Tox prediction
- Systems pharmacology
- Precision medicine

## Contributing

This is a research project. For questions or collaborations, please contact the authors.

## Citation

If you use this project in your research, please cite appropriately.

## License

[Specify license here]

## Authors

Xuan Cao

## Acknowledgments

This project utilizes:
- DrugBank database
- Chemistry Development Kit (CDK)
- Weka machine learning library
- scikit-learn and scikit-multilearn

---

**Note**: Model files are pre-trained and stored in the repository. For retraining or model updates, see individual component documentation.