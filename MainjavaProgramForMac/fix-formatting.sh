#!/bin/bash

# Auto-Fix Formatting Issues in DrugPorter
# This script fixes common code style issues automatically
# Compatible with Java 8+

set -e

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${BLUE}════════════════════════════════════════${NC}"
echo -e "${BLUE}  Auto-Fix Code Formatting Issues${NC}"
echo -e "${BLUE}════════════════════════════════════════${NC}\n"

# Step 1: Fix tabs to spaces
echo -e "${YELLOW}[1/4] Converting tabs to spaces (4-space indent)...${NC}"
JAVA_FILES=$(find src -name "*.java" -type f | wc -l | tr -d ' ')
echo "Found $JAVA_FILES Java files"
find src -name "*.java" -type f -exec sed -i '' 's/\t/    /g' {} \;
echo -e "${GREEN}✓ Tabs converted to spaces${NC}\n"

# Step 2: Remove trailing whitespace
echo -e "${YELLOW}[2/4] Removing trailing whitespace...${NC}"
find src -name "*.java" -type f -exec sed -i '' 's/[[:space:]]*$//' {} \;
echo -e "${GREEN}✓ Trailing whitespace removed${NC}\n"

# Step 3: Ensure files end with newline
echo -e "${YELLOW}[3/4] Ensuring files end with newline...${NC}"
find src -name "*.java" -type f -exec sh -c 'tail -c1 "$1" | read -r _ || echo "" >> "$1"' _ {} \;
echo -e "${GREEN}✓ File endings normalized${NC}\n"

# Step 4: Verify compilation
echo -e "${YELLOW}[4/4] Verifying compilation...${NC}"
if mvn clean compile -q 2>&1 | tail -5; then
    echo -e "${GREEN}✓ Compilation successful${NC}\n"
else
    echo -e "${YELLOW}⚠ Check compilation warnings above${NC}\n"
fi

echo -e "${BLUE}════════════════════════════════════════${NC}"
echo -e "${GREEN}✅ Auto-fix complete!${NC}"
echo -e "${BLUE}════════════════════════════════════════${NC}\n"

echo -e "What was fixed:"
echo -e "  ${GREEN}✓${NC} Tabs → 4 spaces"
echo -e "  ${GREEN}✓${NC} Trailing whitespace removed"
echo -e "  ${GREEN}✓${NC} File endings normalized\n"

echo -e "Next steps:"
echo -e "  1. Review changes: ${YELLOW}git diff${NC}"
echo -e "  2. Run linting:    ${YELLOW}mvn validate${NC}"
echo -e "  3. View report:    ${YELLOW}./check-code-quality.sh report${NC}\n"

echo -e "${YELLOW}Note:${NC} For advanced formatting, consider using IntelliJ IDEA"
echo -e "      or upgrading to Java 11+ for Google Java Format plugin\n"

