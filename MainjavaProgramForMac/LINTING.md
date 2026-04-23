# Java Linting Configuration for MainjavaProgramForMac

This document describes the linting and code quality tools configured for the MainjavaProgramForMac project.

## Tools Configured

### 1. **Checkstyle** - Code Style Checking
- **Plugin**: maven-checkstyle-plugin (3.1.2)
- **Config File**: `checkstyle.xml`
- **Purpose**: Enforces code style conventions and formatting standards
- **Severity**: Warning

**Key Rules:**
- Line length: Maximum 120 characters
- File length: Maximum 2000 lines per file
- Naming conventions for classes, methods, variables
- Javadoc documentation requirements (for public members)
- Import organization (no wildcard imports)
- Proper spacing and indentation

### 2. **PMD (Problem Finder)** - Code Smell Detection
- **Plugin**: maven-pmd-plugin (3.14.0)
- **Purpose**: Detects code smells, potential bugs, and design problems
- **Severity**: Failures with priority 5 and above

**Checks Include:**
- Unused variables
- Empty try/catch blocks
- Duplicate code
- Complex expressions
- Dead code
- Performance issues

### 3. **SpotBugs** - Bug Detection
- **Plugin**: spotbugs-maven-plugin (4.2.3)
- **Purpose**: Finds potential bugs in Java code
- **Effort Level**: Max
- **Threshold**: Medium and above

**Bug Categories:**
- Null pointer dereferences
- Unused fields and parameters
- Infinite loops
- Switch statement fall-through
- Resource leaks
- Synchronization issues

### 4. **Maven Compiler** - Java Compilation
- **Plugin**: maven-compiler-plugin (3.8.1)
- **Source/Target**: Java 1.8 (Java 8)
- **Encoding**: UTF-8

## Running Linting Tools

### Run All Checks
```bash
mvn clean verify
```

### Run Only Checkstyle
```bash
mvn checkstyle:check
```

### Run Only PMD
```bash
mvn pmd:check
```

### Run Only SpotBugs
```bash
mvn spotbugs:check
```

### Auto-Fix Formatting Issues

```bash
# Quick fix - Use the provided script (Java 8 compatible)
./fix-formatting.sh

# Or manually:
# 1. Convert tabs to spaces
find src -name "*.java" -exec sed -i '' 's/\t/    /g' {} \;

# 2. Remove trailing whitespace
find src -name "*.java" -exec sed -i '' 's/[[:space:]]*$//' {} \;

# 3. Verify build
mvn clean compile
```

**Note for Java 11+ users:** Consider using Google Java Format plugin:
```bash
# Add to pom.xml then run:
mvn com.spotify.fmt:fmt-maven-plugin:format
```

### Generate Reports
```bash
# Generate HTML reports for all plugins
mvn clean install
# Reports will be in target/site/
```

### View Reports
```bash
# Generate and open reports in browser
mvn site
open target/site/index.html
```

## Configuration Files

### `checkstyle.xml`
Main configuration file for Checkstyle. Based on Google Java Style Guide with customizations:
- Javadoc requirements for public members
- Naming conventions
- Code structure and formatting rules
- Maximum line length and file size

### `pom.xml` Build Section
Contains plugin configurations with execution bindings to Maven lifecycle phases:
- **validate phase**: Checkstyle checks
- **verify phase**: SpotBugs analysis

## Auto-Fix Capabilities

| Issue Type | Auto-Fix | Method |
|------------|----------|--------|
| **Tabs vs Spaces** | ✅ Yes | `./fix-formatting.sh` |
| **Trailing Whitespace** | ✅ Yes | `./fix-formatting.sh` |
| **File Line Endings** | ✅ Yes | `./fix-formatting.sh` |
| **Code Formatting** | ⚠️ IDE Only | IntelliJ/Eclipse auto-format |
| **Import Organization** | ⚠️ IDE Only | IntelliJ: Ctrl+Alt+O |
| **Indentation** | ⚠️ Partial | `./fix-formatting.sh` fixes tabs |
| **Missing Javadoc** | ❌ No | Manual documentation required |
| **PMD Code Smells** | ❌ No | Manual refactoring required |
| **SpotBugs Issues** | ❌ No | Manual code fixes required |
| **Long Methods** | ❌ No | Manual refactoring required |
| **Magic Numbers** | ❌ No | Manual extraction to constants |

**Java 8 Compatibility Note:** This project uses Java 8, so advanced auto-formatting
plugins requiring Java 9+ are not included. For full formatting support, use IDE tools
(IntelliJ IDEA, Eclipse) or upgrade to Java 11+.

## Ignoring Violations

### For Specific Code Blocks
Use Checkstyle suppression annotations:
```java
@SuppressWarnings("checkstyle:MethodLength")
public void longMethod() {
    // Long method code
}
```

### For Entire Files
Add to beginning of file:
```java
// CHECKSTYLE:OFF
// Your code here
// CHECKSTYLE:ON
```

## Common Issues & Solutions

### Issue: "Line too long"
**Solution**: Break long lines at logical points
```java
// Bad
String longString = "This is a very long string that exceeds the maximum line length";

// Good
String longString = "This is a very long string that "
    + "exceeds the maximum line length";
```

### Issue: "Method too long"
**Solution**: Break into multiple methods
```java
// Bad: Method with 200+ lines

// Good: Multiple focused methods
private void step1() { }
private void step2() { }
private void step3() { }
```

### Issue: "Missing Javadoc"
**Solution**: Add documentation comments
```java
/**
 * Predicts drug-transporter interactions.
 *
 * @param drugSmiles the SMILES representation of the drug
 * @return the prediction results
 */
public PredictionResult predict(String drugSmiles) {
    // Implementation
}
```

### Issue: "Unused variable"
**Solution**: Remove unused variables or use them
```java
// Bad
String unused = "This is never used";

// Good: Remove it
```

## CI/CD Integration

These linting checks can be integrated into CI/CD pipelines:

```bash
# Fail build if linting errors found
mvn verify -Dcheckstyle.failOnViolation=true
```

## Best Practices

1. **Run linting before committing**
   ```bash
   mvn clean verify
   ```

2. **Fix warnings incrementally** - Don't ignore all warnings at once

3. **Use IDE integration** - Configure your IDE to highlight Checkstyle violations

4. **Update rules as needed** - Modify `checkstyle.xml` if rules don't fit your project

5. **Document suppressions** - Always include a comment explaining why a rule is suppressed

## IDE Integration

### Eclipse
1. Install Checkstyle Plugin
2. Right-click project → Checkstyle → Activate Checkstyle
3. Configure with local checkstyle.xml

### IntelliJ IDEA
1. Preferences → Editor → Code Style → Java
2. Scheme → Import Scheme → Checkstyle Configuration
3. Select checkstyle.xml

### VS Code
1. Install Checkstyle for Java extension
2. Configure path to checkstyle.xml in settings

## Troubleshooting

### Checkstyle won't run
```bash
# Clear cache and retry
mvn clean checkstyle:check
```

### XML configuration errors
```bash
# Validate checkstyle.xml syntax
xmllint checkstyle.xml
```

### Too many violations
```bash
# Generate report to review all violations
mvn checkstyle:checkstyle
# View report at target/checkstyle-result.xml
```

## Related Documentation

- [Checkstyle Documentation](https://checkstyle.sourceforge.io/)
- [PMD Documentation](https://pmd.github.io/)
- [SpotBugs Documentation](https://spotbugs.readthedocs.io/)
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- [Maven Documentation](https://maven.apache.org/)

## Maintenance

### Updating Plugins
Check for plugin updates:
```bash
mvn versions:display-plugin-updates
```

### Adjusting Rules
Edit `checkstyle.xml` to:
- Increase/decrease `<property name="max">` values
- Add/remove check modules
- Change severity levels

---

**Last Updated**: April 2026
**Project**: DrugTransporterInteraction - MainjavaProgramForMac
