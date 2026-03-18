#!/bin/bash

# ============================================================================
# LOCAL DEVELOPMENT STARTUP SCRIPT - PARROT LINUX (NO DOCKER)
# ============================================================================
# This script sets up and starts all services for local development:
# - Backend (Spring Boot via Maven)
# - Nginx reverse proxy
# Requires: .env file configured with Supabase and Redis cloud credentials

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Script directory
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BACKEND_DIR="$SCRIPT_DIR"

echo -e "${BLUE}═══════════════════════════════════════════════════════════${NC}"
echo -e "${BLUE}  PetroFlow Local Development Startup${NC}"
echo -e "${BLUE}═══════════════════════════════════════════════════════════${NC}"

# Check prerequisites
echo -e "\n${YELLOW}Checking prerequisites...${NC}"

# Check .env exists
if [ ! -f "$BACKEND_DIR/.env" ]; then
    echo -e "${RED}✗ .env not found!${NC}"
    echo -e "${YELLOW}Please copy .env.example and fill in your Supabase and Redis credentials:${NC}"
    echo -e "  1. Create Supabase project at https://app.supabase.com"
    echo -e "  2. Create Redis instance at Upstash (https://upstash.com) or Redis Labs (https://redis.com)"
    echo -e "  3. Copy .env.example to .env and fill in the credentials"
    echo -e "  4. Run this script again"
    exit 1
fi
echo -e "${GREEN}✓ .env found${NC}"

# Load environment variables
echo -e "\n${YELLOW}Loading environment variables from .env...${NC}"
set -a
source "$BACKEND_DIR/.env"
set +a
echo -e "${GREEN}✓ Environment variables loaded${NC}"

# Normalize datasource URL for Supabase-style URLs
if [[ -z "$SPRING_DATASOURCE_URL" && -n "$DATABASE_URL" ]]; then
    SPRING_DATASOURCE_URL="$DATABASE_URL"
fi

if [[ -n "$SPRING_DATASOURCE_URL" ]]; then
    if [[ "$SPRING_DATASOURCE_URL" == postgresql://* ]]; then
        SPRING_DATASOURCE_URL="jdbc:${SPRING_DATASOURCE_URL}"
        export SPRING_DATASOURCE_URL
        echo -e "${GREEN}✓ Normalized SPRING_DATASOURCE_URL to JDBC format${NC}"
    elif [[ "$SPRING_DATASOURCE_URL" == postgres://* ]]; then
        SPRING_DATASOURCE_URL="jdbc:postgresql://${SPRING_DATASOURCE_URL#postgres://}"
        export SPRING_DATASOURCE_URL
        echo -e "${GREEN}✓ Normalized SPRING_DATASOURCE_URL to JDBC format${NC}"
    fi
fi

# Default Redis TLS for rediss URLs
if [[ "$REDIS_URL" == rediss://* && -z "$REDIS_SSL_ENABLED" ]]; then
    export REDIS_SSL_ENABLED=true
fi

# Validate required environment variables
echo -e "\n${YELLOW}Validating configuration...${NC}"

errors=0

# Check database configuration
if [[ -z "$SPRING_DATASOURCE_URL" && -z "$DATABASE_URL" ]]; then
    echo -e "${RED}✗ Database configuration missing${NC}"
    echo -e "  Set SPRING_DATASOURCE_URL (jdbc:postgresql://...) in .env"
    errors=$((errors + 1))
else
    echo -e "${GREEN}✓ Database configured${NC}"
fi

# Check Redis configuration
if [[ -z "$REDIS_URL" && ( -z "$SPRING_DATA_REDIS_HOST" || -z "$SPRING_DATA_REDIS_PORT" ) ]]; then
    echo -e "${RED}✗ Redis configuration missing${NC}"
    echo -e "  Set REDIS_URL (recommended) or REDIS_HOST/REDIS_PORT in .env"
    errors=$((errors + 1))
else
    echo -e "${GREEN}✓ Redis configured${NC}"
fi

# Check JWT secret
if [[ -z "$JWT_SECRET" ]]; then
    echo -e "${RED}✗ JWT_SECRET not configured${NC}"
    errors=$((errors + 1))
else
    echo -e "${GREEN}✓ JWT secret configured${NC}"
fi

if [ $errors -gt 0 ]; then
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}✗ Maven not found. Please install Maven.${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Maven is installed${NC}"

# Check if Nginx is installed
if ! command -v nginx &> /dev/null; then
    echo -e "${YELLOW}⚠ Nginx not found. Installing Nginx...${NC}"
    echo "Run: sudo apt-get install nginx"
    # Don't exit - backend can run without Nginx
else
    echo -e "${GREEN}✓ Nginx is installed${NC}"
fi

# Cleanup function
cleanup() {
    echo -e "\n${YELLOW}Cleaning up...${NC}"

    # Kill Nginx if running
    if pgrep -x "nginx" > /dev/null; then
        echo "Stopping Nginx..."
        sudo nginx -s quit 2>/dev/null || true
        sleep 1
    fi

    echo -e "${GREEN}Cleanup complete${NC}"
}

# Set trap for cleanup on exit
trap cleanup EXIT INT TERM

# ============================================================================
# Start Services
# ============================================================================

echo -e "\n${BLUE}═══════════════════════════════════════════════════════════${NC}"
echo -e "${BLUE}  Starting Services${NC}"
echo -e "${BLUE}═══════════════════════════════════════════════════════════${NC}"

# Kill any existing processes on port 80 and 8080
echo -e "\n${YELLOW}Checking ports...${NC}"

# Check if something is already on port 8080
if lsof -Pi :8080 -sTCP:LISTEN -t >/dev/null 2>&1; then
    echo -e "${YELLOW}⚠ Port 8080 is already in use. Killing existing process...${NC}"
    sudo kill -9 $(lsof -t -i:8080) 2>/dev/null || true
fi

# Check if something is already on port 80
if lsof -Pi :80 -sTCP:LISTEN -t >/dev/null 2>&1; then
    echo -e "${YELLOW}⚠ Port 80 is already in use. Killing existing process...${NC}"
    sudo kill -9 $(lsof -t -i:80) 2>/dev/null || true
fi

sleep 1
echo -e "${GREEN}✓ Ports cleared${NC}"

# Start Nginx
echo -e "\n${YELLOW}Starting Nginx reverse proxy...${NC}"
if command -v nginx &> /dev/null; then
    # Stop existing nginx instances
    sudo nginx -s stop 2>/dev/null || true
    sleep 1

    # Start nginx with dev config
    sudo nginx -c "$BACKEND_DIR/nginx-dev.conf"
    sleep 2

    if pgrep -x "nginx" > /dev/null; then
        echo -e "${GREEN}✓ Nginx started (PID: $(pgrep -x nginx))${NC}"
        echo -e "  Access at: http://localhost/api"
    else
        echo -e "${RED}✗ Nginx failed to start${NC}"
        echo -e "${YELLOW}Check logs: sudo tail -f /var/log/nginx/petroflow_dev_error.log${NC}"
    fi
else
    echo -e "${YELLOW}⚠ Nginx not installed. Skipping. Install with: sudo apt-get install nginx${NC}"
fi

# Start Backend
echo -e "\n${YELLOW}Starting Spring Boot backend...${NC}"
echo -e "${YELLOW}This may take a minute to start...${NC}"

# Create logs directory if it doesn't exist
mkdir -p "$BACKEND_DIR/logs"

# Run Maven with environment variables
cd "$BACKEND_DIR"

# Use ./mvnw if available, otherwise use maven
if [ -f "$BACKEND_DIR/mvnw" ]; then
    ./mvnw clean spring-boot:run \
        2>&1 | tee logs/backend_$(date +%Y%m%d_%H%M%S).log
else
    mvn clean spring-boot:run \
        2>&1 | tee logs/backend_$(date +%Y%m%d_%H%M%S).log
fi

