#!/bin/bash

# Code Quality Check Script for DrugPorter
# This script runs all linting and code quality tools
# Usage: ./check-code-quality.sh [option]

set -e

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

print_header() {
    echo -e "\n${GREEN}================================${NC}"
    echo -e "${GREEN}$1${NC}"
    echo -e "${GREEN}================================${NC}\n"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}\n"
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}\n"
}

print_error() {
    echo -e "${RED}✗ $1${NC}\n"
}

# Show usage
show_usage() {
    cat << EOF
Usage: $0 [option]

Options:
    all         Run all checks (default)
    checkstyle  Run Checkstyle checks only
    pmd         Run PMD checks only
    spotbugs    Run SpotBugs checks only
    compile     Compile project only
    report      Generate HTML reports
    help        Show this help message

Examples:
    $0              # Run all checks
    $0 checkstyle   # Run Checkstyle only
    $0 report       # Generate HTML reports
EOF
}

# Function to run all checks
run_all_checks() {
    print_header "Running All Code Quality Checks"
    
    print_header "1. Compilation Check"
    if mvn clean compile -q; then
        print_success "Compilation successful"
    else
        print_error "Compilation failed"
        exit 1
    fi
    
    print_header "2. Checkstyle Check"
    if mvn checkstyle:check -q; then
        print_success "Checkstyle check passed"
    else
        print_warning "Checkstyle violations found (see above)"
    fi
    
    print_header "3. PMD Check"
    if mvn pmd:check -q 2>/dev/null; then
        print_success "PMD check passed"
    else
        print_warning "PMD violations found (see above)"
    fi
    
    print_header "4. SpotBugs Check"
    if mvn spotbugs:check -q 2>/dev/null; then
        print_success "SpotBugs check passed"
    else
        print_warning "SpotBugs violations found (see above)"
    fi
    
    print_header "Code Quality Check Complete"
}

# Function to run checkstyle only
run_checkstyle() {
    print_header "Running Checkstyle"
    mvn checkstyle:check
}

# Function to run PMD only
run_pmd() {
    print_header "Running PMD"
    mvn pmd:check
}

# Function to run SpotBugs only
run_spotbugs() {
    print_header "Running SpotBugs"
    mvn spotbugs:check
}

# Function to compile only
run_compile() {
    print_header "Compiling Project"
    mvn clean compile
}

# Function to generate reports
run_report() {
    print_header "Generating Code Quality Reports"
    
    print_header "Building and generating reports..."
    if mvn clean verify site -q; then
        print_success "Reports generated successfully"
        print_header "Opening report in browser..."
        
        # Open in default browser (macOS specific)
        if command -v open &> /dev/null; then
            open "${PROJECT_DIR}/target/site/index.html"
            print_success "Report opened in browser"
        else
            print_warning "Report available at: ${PROJECT_DIR}/target/site/index.html"
        fi
    else
        print_error "Failed to generate reports"
        exit 1
    fi
}

# Main execution
cd "$PROJECT_DIR"

case "${1:-all}" in
    all)
        run_all_checks
        ;;
    checkstyle)
        run_checkstyle
        ;;
    pmd)
        run_pmd
        ;;
    spotbugs)
        run_spotbugs
        ;;
    compile)
        run_compile
        ;;
    report)
        run_report
        ;;
    help|--help|-h)
        show_usage
        ;;
    *)
        print_error "Unknown option: $1"
        show_usage
        exit 1
        ;;
esac

exit 0
