# Contributing to DrugPorter

This document provides guidelines for contributing to the DrugPorter project.

## Getting Started

1. **Fork and Clone the Repository**
   ```bash
   git clone <your-fork-url>
   cd MainjavaProgramForMac
   ```

2. **Set Up Development Environment**
   ```bash
   mvn clean install
   ```

3. **Build and Test**
   ```bash
   mvn clean verify
   ```

## Code Quality Standards

All contributions must meet the following code quality standards:

### 1. Code Style
- **Line Length**: Maximum 120 characters
- **Indentation**: 4 spaces (no tabs)
- **Encoding**: UTF-8
- **Line Endings**: Unix (LF)

All code must pass **Checkstyle** validation:
```bash
mvn checkstyle:check
```

### 2. Code Smells
Code should not introduce new code smells detected by **PMD**:
```bash
mvn pmd:check
```

### 3. Bug Prevention
Code must pass **SpotBugs** analysis for potential bugs:
```bash
mvn spotbugs:check
```

### 4. Documentation
- Public classes and methods must have Javadoc comments
- Use `@param` and `@return` tags for method parameters and return values
- Document non-obvious logic with inline comments

Example:
```java
/**
 * Predicts drug-transporter interactions using a Random Forest model.
 *
 * @param drugSmiles the SMILES representation of the drug compound
 * @param transporterName the name of the target transporter
 * @return the prediction result (0.0 to 1.0) where 1.0 indicates high interaction probability
 * @throws IllegalArgumentException if drugSmiles or transporterName is null
 */
public double predict(String drugSmiles, String transporterName) {
    // Implementation
}
```

## Development Workflow

### 1. Before Starting
```bash
# Create a feature branch
git checkout -b feature/your-feature-name

# Update dependencies
mvn clean install
```

### 2. During Development
```bash
# Run checks frequently
./check-code-quality.sh
# or
mvn clean verify
```

### 3. Before Committing
```bash
# Fix all issues found
./check-code-quality.sh all

# Ensure tests pass
mvn test

# Check for any uncommitted changes
git status
```

### 4. Code Review Checklist

Before submitting a pull request, ensure:

- [ ] Code passes all linting checks (`mvn verify`)
- [ ] All tests pass (`mvn test`)
- [ ] New public methods have Javadoc comments
- [ ] Line length doesn't exceed 120 characters
- [ ] No new compiler warnings
- [ ] Commit messages are clear and descriptive
- [ ] Related issue is referenced (if applicable)

## Code Organization

### Package Structure
```
xuan.biotech
├── gui          # GUI components and windows
├── prediction   # Prediction logic and models
├── features     # Feature generation
├── io           # Input/Output operations
└── utils        # Utility classes
```

### Naming Conventions
- **Classes**: PascalCase (e.g., `GUIMainPage`, `MakePrediction`)
- **Methods**: camelCase (e.g., `makePrediction()`, `generateFeatures()`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_FEATURES`, `DEFAULT_MODEL_PATH`)
- **Variables**: camelCase (e.g., `drugSmiles`, `transporterList`)

## Common Issues and Solutions

### Issue: Checkstyle Violations
```bash
# View all violations
mvn checkstyle:checkstyle

# Fix common issues automatically (if supported)
mvn checkstyle:check -Dcheckstyle.consoleOutput=true
```

**Common Violations:**
- **Line too long**: Break lines at logical points
- **Missing Javadoc**: Add documentation for public members
- **Unused imports**: Remove unused imports
- **Incorrect spacing**: Use consistent spacing around operators

### Issue: Method Too Long
Break long methods into smaller, focused methods:
```java
// Before: Single 200-line method
private void complexMethod() {
    // 200 lines
}

// After: Decomposed into logical steps
private void executeWorkflow() {
    loadData();
    processData();
    saveResults();
}

private void loadData() { /* ... */ }
private void processData() { /* ... */ }
private void saveResults() { /* ... */ }
```

### Issue: High Cyclomatic Complexity
Reduce complexity by using polymorphism or extracting logic:
```java
// Before: Complex if-else chains
if (type == A) {
    // 20 lines
} else if (type == B) {
    // 20 lines
} else if (type == C) {
    // 20 lines
}

// After: Use strategy pattern
interface Strategy {
    void execute();
}

Map<String, Strategy> strategies = Map.of(
    "A", new StrategyA(),
    "B", new StrategyB(),
    "C", new StrategyC()
);
strategies.get(type).execute();
```

## Testing

### Unit Tests
- Write unit tests for new functionality
- Place tests in `src/test/java`
- Use meaningful test method names

```java
@Test
public void testPredictionWithValidDrugSmiles() {
    // Arrange
    String drugSmiles = "CCO";
    
    // Act
    PredictionResult result = predictor.predict(drugSmiles);
    
    // Assert
    assertNotNull(result);
    assertTrue(result.getScore() >= 0.0 && result.getScore() <= 1.0);
}
```

### Integration Tests
- Test components working together
- Use appropriate test fixtures
- Isolate external dependencies

## Performance Considerations

1. **Large-Scale Predictions**: Optimize batch processing
2. **Memory Usage**: Profile large compound batches
3. **I/O Operations**: Use efficient file reading/writing
4. **Model Loading**: Cache loaded models when possible

## Reporting Issues

When reporting bugs, please include:
1. Description of the issue
2. Steps to reproduce
3. Expected vs. actual behavior
4. Java version and OS
5. Relevant error logs

## Pull Request Process

1. **Create a descriptive PR title**
   ```
   Fix: Correct NullPointerException in feature generation
   Feature: Add support for SDF file batch import
   Docs: Update installation instructions for macOS
   ```

2. **Provide context in the description**
   - What problem does this solve?
   - How does it solve it?
   - Any breaking changes?

3. **Link related issues**
   - Use `Fixes #123` to auto-close issues

4. **Wait for review**
   - Address feedback promptly
   - Re-run checks after changes

## Resources

- [Checkstyle Documentation](https://checkstyle.sourceforge.io/)
- [PMD Documentation](https://pmd.github.io/)
- [SpotBugs Documentation](https://spotbugs.readthedocs.io/)
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- [EditorConfig](https://editorconfig.org/)

## Questions?

- Open an issue for bugs or feature requests
- Check existing issues before creating new ones
- Contact the maintainers for general questions

---

Thank you for contributing to DrugPorter!
