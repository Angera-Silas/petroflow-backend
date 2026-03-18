#!/bin/bash
# ============================================================================
# PETROFLOW BACKEND - SECURE DEPLOYMENT SCRIPT
# ============================================================================
# This script securely deploys the PetroFlow backend to Railway with Redis
# Usage: bash deploy.sh

set -e  # Exit on error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# ============================================================================
# FUNCTIONS
# ============================================================================

log_info() {
    echo -e "${BLUE}ℹ️  $1${NC}"
}

log_success() {
    echo -e "${GREEN}✅ $1${NC}"
}

log_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

log_error() {
    echo -e "${RED}❌ $1${NC}"
}

# ============================================================================
# PRE-DEPLOYMENT CHECKS
# ============================================================================

log_info "Starting PetroFlow Backend Deployment..."
echo ""

# Check if running from correct directory
if [ ! -f "pom.xml" ]; then
    log_error "pom.xml not found. Please run this script from petroflow-backend directory"
    exit 1
fi

log_success "Found pom.xml - Correct directory"

# Check for .env file
if [ ! -f ".env" ]; then
    log_warning ".env file not found"
    log_info "Creating .env from .env.example..."

    if [ -f ".env.example" ]; then
        cp .env.example .env
        log_success "Created .env file"
        log_warning "IMPORTANT: Edit .env with your credentials before deploying!"
        log_info "Opening .env for editing..."
        # Uncomment if you want to auto-open in editor
        # nano .env

        read -p "Press enter after editing .env with your credentials..."
    else
        log_error "No .env.example file found"
        exit 1
    fi
else
    log_success ".env file exists"
fi

# ============================================================================
# LOAD ENVIRONMENT VARIABLES
# ============================================================================

log_info "Loading environment variables from .env..."
export $(cat .env | grep -v '^#' | xargs)

# Validate required variables
required_vars=("REDIS_URL" "DATABASE_URL" "JWT_SECRET")
for var in "${required_vars[@]}"; do
    if [ -z "${!var}" ]; then
        log_error "Required environment variable not set: $var"
        exit 1
    fi
done

log_success "All required environment variables loaded"
echo ""

# ============================================================================
# BUILD APPLICATION
# ============================================================================

log_info "Building application..."
echo "This may take 2-3 minutes..."
echo ""

# Build with Maven (if available)
if command -v mvn &> /dev/null; then
    mvn clean package -DskipTests -q
    log_success "Build completed"
else
    log_warning "Maven not found. Skipping build verification."
    log_info "The build will happen in Railway CI/CD"
fi

echo ""

# ============================================================================
# VERIFY CONFIGURATION
# ============================================================================

log_info "Verifying configuration files..."

# Check if .gitignore includes .env
if grep -q "\.env" .gitignore; then
    log_success ".env properly protected in .gitignore"
else
    log_warning ".env not in .gitignore! Adding now..."
    echo ".env" >> .gitignore
    log_success "Added .env to .gitignore"
fi

# Check consolidated properties
if grep -q "spring.data.redis.url" src/main/resources/application.properties; then
    log_success "Redis configuration found in application.properties"
else
    log_error "Redis configuration missing in application.properties"
    exit 1
fi

echo ""

# ============================================================================
# CONTAINERIZATION ARTIFACT BUILD (Optional)
# ============================================================================

read -p "Build Docker image artifact only (no docker run)? (y/n) " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    log_info "Building Docker image artifact for cross-platform packaging..."
    docker build -t petroflow-backend .
    log_success "Docker image artifact built"
    log_info "Runtime validation is performed with Maven/JAR + Nginx, not docker run"
fi

echo ""

# ============================================================================
# GIT SETUP
# ============================================================================

log_info "Setting up Git..."

# Check if git is initialized
if [ ! -d ".git" ]; then
    log_warning "Not a git repository yet"
    log_info "Running: git init"
    git init
fi

# Add files
log_info "Staging files for commit..."
git add . --verbose 2>&1 | grep -E "add|path" | head -20

# Verify .env is not staged
if git diff --cached --name-only | grep -q "\.env$"; then
    log_error ".env was staged! Removing from staging..."
    git rm --cached .env
    log_success "Removed .env from staging"
fi

log_success "Files staged"

echo ""

# ============================================================================
# COMMIT
# ============================================================================

read -p "Create commit? (y/n) " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    log_info "Creating commit..."
    git commit -m "Configure production deployment: Railway Redis, secure env vars, optimized profiles"
    log_success "Commit created"
else
    log_warning "Skipped commit"
fi

echo ""

# ============================================================================
# RAILWAY DEPLOYMENT
# ============================================================================

log_info "Preparing Railway deployment..."
echo ""

if command -v railway &> /dev/null; then
    log_success "Railway CLI found"

    log_info "Checking Railway login status..."
    if railway status > /dev/null 2>&1; then
        log_success "Already logged into Railway"
    else
        log_warning "Not logged into Railway"
        log_info "Run: railway login"
        read -p "Press enter after logging in..."
    fi

    log_info "Setting Railway environment variables..."
    railway variables set REDIS_URL="$REDIS_URL"
    railway variables set DATABASE_URL="$DATABASE_URL"
    railway variables set JWT_SECRET="$JWT_SECRET"
    railway variables set SPRING_PROFILES_ACTIVE="prod"
    log_success "Railway variables configured"

    read -p "Deploy now? (y/n) " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        log_info "Deploying to Railway..."
        railway up
        log_success "Deployment initiated"
    fi
else
    log_warning "Railway CLI not installed"
    log_info "Install: npm install -g @railway/cli"
    log_info "Then run: railway login && railway link"
fi

echo ""

# ============================================================================
# POST-DEPLOYMENT
# ============================================================================

log_info "Deployment configuration complete!"
echo ""
log_success "Next steps:"
echo "  1. Verify Railway deployment: railway logs --follow"
echo "  2. Test health endpoint: curl https://YOUR-RAILWAY-URL/api/health"
echo "  3. Test Redis: curl https://YOUR-RAILWAY-URL/api/health | jq '.components.redis'"
echo ""

log_warning "IMPORTANT REMINDERS:"
echo "  • NEVER commit .env to git"
echo "  • Keep Redis password confidential"
echo "  • Rotate credentials every 90 days"
echo "  • Monitor logs for errors"
echo ""

log_success "Deployment script completed!"
