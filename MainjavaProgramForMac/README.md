# DrugPorter - Main Java Application for macOS

A Java Swing GUI application for predicting and analyzing drug-transporter interactions using machine learning models.

## Overview

DrugPorter is a desktop application that allows researchers and scientists to:
- **Predict drug-transporter interactions** using pre-trained Random Forest models
- **Search for drugs** by name or chemical structure
- **View transporter information** for various membrane transporters
- **Perform large-scale predictions** on multiple compounds
- **Explore molecular features** and chemical fingerprints

## Features

### 1. Drug Database Browser
- View and search the drug database
- Browse drug information and known interactions
- Filter by drug properties

### 2. Transporter Information
- Access detailed information about membrane transporters including:
  - ABCC1, ABCC2, ABCG2
  - MDR1 (P-glycoprotein)
  - SLC22A1, SLC22A2, SLC22A6, SLC22A8
  - SLCO1A2, SLCO1B1

### 3. Interaction Prediction
- Predict drug-transporter interactions using machine learning
- Support for single compound or batch predictions
- Input methods:
  - SMILES notation
  - SDF file upload
  - Chemical structure drawing

### 4. Chemical Structure Analysis
- Generate molecular fingerprints
- Calculate molecular features
- Chemical similarity searching using CDK (Chemistry Development Kit)

### 5. Large-Scale Prediction
- Batch prediction for multiple compounds
- Export predictions to CSV/ARFF formats
- Integrate with Weka machine learning workflows

## Technologies Used

- **Java Swing** - GUI framework
- **Maven** - Dependency management and build tool
- **CDK (Chemistry Development Kit) 2.1.1** - Chemical structure processing
- **Weka 3.8.2** - Machine learning algorithms and models
- **OpenCSV 4.1** - CSV file handling
- **MySQL Connector 5.1.45** - MySQL database connectivity
- **SQLite JDBC 3.21.0.1** - SQLite database support

## Project Structure

```
MainjavaProgramForMac/
├── src/
│   └── main/
│       └── java/
│           └── xuan/biotech/
│               ├── GUIMainPage.java          # Main entry point
│               ├── GUIPredictionClass.java   # Prediction interface
│               ├── GUIViewDrugs.java         # Drug browser
│               ├── GUIViewTransporter.java   # Transporter browser
│               ├── MakePrediction.java       # Prediction logic
│               ├── FeatureGeneration.java    # Molecular features
│               ├── ChemicalFingerprint.java  # Fingerprint generation
│               └── ...
├── DatabaseFolder/                   # Pre-trained ML models
│   ├── ABCC1MRandomForest.model
│   ├── MDR1MRandomForest.model
│   └── ...
├── wekaMachineLearningModel/        # Additional models
├── forTempFile/                      # Temporary working files
├── images/                           # GUI images and icons
└── pom.xml                          # Maven configuration
```

## Prerequisites

- **Java JDK 8 or higher**
- **Maven 3.x**
- **macOS** (optimized for Mac, but should work on other platforms)

## Building the Project

```bash
cd MainjavaProgramForMac
mvn clean install
```

## Running the Application

```bash
mvn exec:java -Dexec.mainClass="xuan.biotech.GUIMainPage"
```

Or run the compiled JAR:

```bash
java -jar target/test-0.0.1-SNAPSHOT.jar
```

## Code Quality & Linting

The project includes comprehensive code quality tools to maintain high code standards:

### Linting Tools Configured
- **Checkstyle** - Code style and formatting validation
- **PMD** - Code smell and quality analysis
- **SpotBugs** - Potential bug detection
- **EditorConfig** - Consistent formatting across IDEs

### Running Code Quality Checks

```bash
# Run all quality checks
mvn clean verify

# Run specific checks
mvn checkstyle:check    # Code style
mvn pmd:check          # Code smells
mvn spotbugs:check     # Bug detection

# Auto-fix formatting issues (Java 8 compatible)
./fix-formatting.sh     # Convert tabs→spaces, remove trailing whitespace

# Generate detailed reports
mvn clean install
# Reports available at: target/site/
```

For detailed information about linting configuration and troubleshooting, see [LINTING.md](LINTING.md).

## Usage

1. **Launch the application** - The main window will display options for different functionalities
2. **View Drugs** - Browse the drug database and view known interactions
3. **View Transporters** - Access information about membrane transporters
4. **Make Prediction** - Input a compound (SMILES or SDF) to predict interactions
5. **Large-Scale Prediction** - Upload a file with multiple compounds for batch processing

## Machine Learning Models

The application includes pre-trained Random Forest models for 10 major drug transporters:

- **ABC Transporters**: ABCC1, ABCC2, ABCG2
- **SLC Transporters**: SLC22A1, SLC22A2, SLC22A6, SLC22A8
- **SLCO Transporters**: SLCO1A2, SLCO1B1
- **MDR1** (Multi-Drug Resistance Protein 1)

Models are trained on molecular features and chemical fingerprints to predict drug-transporter interactions.

## Output Formats

- **CSV** - Comma-separated values for easy data analysis
- **ARFF** - Weka's native format for further machine learning processing
- **SDF** - Structure-Data File for chemical structures

## Notes

- The `forTempFile/` directory is used for temporary processing files
- Model files (`.model`) are pre-trained and should not be modified
- Ensure sufficient memory for large-scale predictions (adjust JVM heap size if needed)

## Troubleshooting

### Out of Memory Errors
```bash
java -Xmx2g -jar target/test-0.0.1-SNAPSHOT.jar
```

### CDK/Weka Library Issues
```bash
mvn clean install -U
```

## Related Projects

- **ForSEASimilarity** - Similarity-based drug-transporter interaction analysis
- **Multi-Label classification DTIs** - Python-based multi-label classification
- **SEA Similarity Score** - Similarity Ensemble Approach scoring

## License

This project is part of the DrugTransporterInteraction research project.

## Authors

Xuan Cao

## Contact

For questions or issues, please open an issue in the repository.
