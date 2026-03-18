# PetroFlow Backend

**Centralized ERP Backend for Petroleum Station Operations** – Powering all PetroFlow clients (Mobile, Web, Desktop) with production-grade scalability, multi-tenancy, and offline-sync capabilities.

## 📋 Overview

PetroFlow Backend is a **Spring Boot microservice** that serves as the single source of truth for all PetroFlow applications (iOS/Android via Flutter, Web via React, Desktop via Flutter/Tauri). It implements:

- **Multi-tenant architecture** (schema-per-organization)
- **Real-time data synchronization** across all clients
- **Offline-sync orchestration** with conflict resolution
- **Device session management** (single login per device)
- **Advanced caching** (Redis) for high-performance dashboards
- **Load balancing & concurrency optimization**
- **Audit-grade logging** for compliance & forensics
- **Role-Based Access Control (RBAC)** with fine-grained permissions

## 🎯 Purpose & Context

PetroFlow addresses the operational challenges at petrol stations previously managed via paper logs and Excel sheets. This backend enables:
- **Real-time sales capture** from all devices
- **Inventory tracking** with low-stock alerts
- **Employee & shift management** with audit trails
- **Automated reporting** to head office
- **Multi-outlet visibility** for dealer networks and large operators

See [LLM_CONTEXT.md](../LLM_CONTEXT.md) for full business context and go-to-market strategy.

## 🛠 Tech Stack

### **Core Framework**
- **Java 17+** – Modern language features, security updates
- **Spring Boot 3.4.3+** – Microservices framework
- **Spring Data JPA** – ORM & repository pattern
- **Spring Security** – Authentication & authorization
- **Spring Cloud** – (Optional) distributed tracing, config management

### **Data Layer**
- **PostgreSQL 14+** – Relational DB with schema-per-tenant support
- **Redis** – Session cache, dashboard caching, rate limiting
- **Liquibase/Flyway** – Schema migration & versioning

### **Authentication & Integration**
- **JWT (JSON Web Token)** – Stateless auth with refresh tokens
- **Firebase Admin SDK** – Mobile push notifications & auth
- **Google OAuth2** – Enterprise SSO support
- **Spring Security** – RBAC with permission hierarchies

### **Production Features**
- **Lombok** – Reduce boilerplate
- **Spring Boot DevTools** – Fast reload during development
- **Micrometer / OpenTelemetry** – Metrics & distributed tracing
- **Resilience4J** – Circuit breakers, rate limiting, retry logic
- **Scheduled Tasks** – Job queuing for report generation, sync orchestration
- **Docker & Docker Compose** – Containerization & local stack

## ✨ Key Features

### 📊 **Core Operation Modules**
- **Sales Management** – Real-time transaction capture with edit/approval workflows
- **Inventory Management** – Stock tracking, transfers, low-stock alerts
- **Product Management** – CRUD with stock & performance insights
- **Employee Management** – Shifts, departments, assignment tracking
- **Incidents Management** – Incident reporting with approval flows
- **Requests Management** – Staff requests with managerial approval
- **Analytics & Reporting** – Dashboards, bulk reports, scheduled exports

### 🔒 **Security & Compliance**
- **RBAC** (Role-Based Access Control) – Attendant / Manager / Admin / Super-Admin
- **Audit Logging** – Immutable logs for sensitive actions (sales edits, refunds, approvals)
- **JWT with Refresh Tokens** – Secure, stateless authentication
- **TLS Encryption** – All APIs over HTTPS
- **Request Rate Limiting** – DDoS protection
- **Data Encryption** – Secrets stored encrypted (KMS/managed secrets)

### ⚡ **Performance & Scalability**
- **Redis Caching** – Dashboard data, session management, frequently accessed entities
- **Database Indexing** – Per-tenant schema index strategy
- **Connection Pooling** – HikariCP for high-concurrency DB connections
- **Async Processing** – Spring Async for heavy tasks (report generation)
- **Load Balancing Ready** – Horizontal scaling via container orchestration

### 🔄 **Multi-Tenancy & Offline Sync**
- **Schema-per-Tenant** – Complete data isolation
- **Automated Tenant Provisioning** – Schema creation + baseline migrations on signup
- **Sync API** – Bidirectional delta sync for mobile/desktop offline cache
- **Conflict Resolution** – Last-write-wins with timestamps
- **Device Session Tracking** – Enforce single-device login per user (optional feature)

## 📦 Installation & Setup

### **Prerequisites**
- Java 17+
- Maven 3.8+
- Nginx (recommended)
- Supabase PostgreSQL project
- Redis Cloud (Upstash/Redis Labs)

### **1. Clone & Navigate**
```bash
git clone https://github.com/Angera-Silas/petroflow-backend.git
cd petroflow-backend
```

### **2. Configure Environment**
Create a local env file with cloud service credentials:

```bash
cp .env.example .env
```

Required values:

```properties
# Supabase
SPRING_DATASOURCE_URL=jdbc:postgresql://db.<project-ref>.supabase.co:5432/postgres
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=<supabase-db-password>

# Redis Cloud
REDIS_URL=rediss://default:<token>@<endpoint>.upstash.io:<port>
REDIS_SSL_ENABLED=true

# Security
JWT_SECRET=<long-random-secret>
```

### **3. Build & Run (No Docker Runtime)**
```bash
# Clean build
mvn clean install

# Start Nginx + backend using local script
./run-local.sh

# Or package and run JAR
mvn clean package
java -jar target/petroflow-backend.jar --spring.profiles.active=prod
```

The backend starts on `http://localhost:8080` and is proxied by Nginx on `http://localhost/api`.

### **4. Verify Setup**
- **API Status (Nginx)**: `http://localhost/api/health`
- **API Status (Direct)**: `http://localhost:8080/api/health`
- **Nginx status**: `http://127.0.0.1:8888/nginx_status`

## 🏗 Architecture

### **Layered Architecture**
```
┌─────────────────────────────────────────────┐
│  Presentation Layer                         │
│  (REST Controllers, Global Error Handler)   │
├─────────────────────────────────────────────┤
│  Business Logic Layer                       │
│  (Services, RBAC, Sync Orchestration)       │
├─────────────────────────────────────────────┤
│  Data Access Layer                          │
│  (JPA Repositories, Queries, Transactions)  │
├─────────────────────────────────────────────┤
│  Identity Layer                             │
│  (Tenant Resolution, User Context)          │
├─────────────────────────────────────────────┤
│  Infrastructure Layer                       │
│  (Database, Redis, External APIs)           │
└─────────────────────────────────────────────┘
```

### **Multi-Tenancy Design**
- **Public Schema**: Tenants table, global auth, audit logs
- **Tenant Schemas**: `org_<orgId>` (isolated data per organization)
- **Tenant Resolution**: Via JWT claims or `X-Tenant-Id` header
- **Query Filtering**: Automatic tenant scoping via Spring Security context

### **Offline Sync Flow**
```
[Mobile/Desktop Offline]
         ↓ (Background)
    [Local SQLite Queue]
         ↓ (When Online)
    [POST /api/sync] → [Conflict Check] → [Apply Changes] → [Return Deltas]
         ↓
    [Update Local Cache]
```

## 📡 API Conventions

### **Request Format**
```http
POST /api/petrol-stations
X-Tenant-Id: org_123
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "name": "Shell Mlolongo",
  "location": "Nairobi, Kenya",
  "manager": "John Doe"
}
```

### **Tenant Resolution Priority**
1. JWT claims (`tenant_id`)
2. Request header (`X-Tenant-Id`)
3. Subdomain (`org-123.petrolflow.app` → extract `org-123`)
4. Default tenant (if user is super-admin)

### **Response Format**
```json
{
  "success": true,
  "data": { ... },
  "timestamp": "2026-03-15T10:30:00Z",
  "traceId": "abc-123-xyz"
}
```

### **Pagination**
```http
GET /api/sales?page=1&size=20&sort=date,desc
```

## 🔄 Offline Sync API

### **POST /api/sync**
Sync local changes (mobile/desktop offline cache) with backend.

**Request:**
```json
{
  "transactionType": "delta",
  "lastSyncTimestamp": "2026-03-15T10:00:00Z",
  "changes": [
    {
      "entity": "sales",
      "action": "create",
      "id": "sales_abc123",
      "data": { "amount": 5000, "product": "Premium Diesel", ... },
      "clientTimestamp": "2026-03-15T10:15:00Z"
    }
  ]
}
```

**Response:**
```json
{
  "success": true,
  "syncedAt": "2026-03-15T10:30:00Z",
  "appliedChanges": 3,
  "conflicts": [],
  "serverDeltas": [
    // Changes from other devices/users
  ],
  "newEntities": [...]
}
```

## 🔐 Device Session Management

Optional feature: **Single login per device per user**

### **Enforcement**
1. User logs in on Device A ✅ (Session ID: `session_a123`)
2. Same user tries Device B → Receives session conflict warning
3. User must explicitly logout on Device A or force-logout from settings
4. Device B session only becomes active after Device A explicitly logs out

**Endpoint:**
```http
POST /api/auth/logout-other-devices
Authorization: Bearer <JWT_TOKEN>
```

## 🚀 Deployment

### **Containerization (Build Artifact Only)**
```bash
# Build image for packaging / deployment pipelines
docker build -t petroflow-backend:latest .
```

Runtime uses native Java process + Nginx. Do not use `docker run` for local or production runtime in this setup.

### **Kubernetes (Helm)**
Example Helm values for multi-replica deployment:
```yaml
replicas: 3
resources:
  requests:
    cpu: 500m
    memory: 512Mi
  limits:
    cpu: 1000m
    memory: 1Gi
autoscaling:
  enabled: true
  minReplicas: 2
  maxReplicas: 5
  targetCPU: 70
```

### **Environment Variables**
```bash
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:postgresql://db.<project-ref>.supabase.co:5432/postgres
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=<secure-password>
REDIS_URL=rediss://default:<token>@<endpoint>:<port>
REDIS_SSL_ENABLED=true
JWT_SECRET=<secure-key>
FIREBASE_KEY_PATH=/secrets/firebase-key.json
```

## 🎓 Why Spring Boot?

Spring Boot was chosen for:
1. **Microservices Ready** – Build scalable, independent services
2. **RBAC Support** – Declarative permission management (`@PreAuthorize`, `@PostAuthorize`)
3. **Multi-Tenancy** – Filters, interceptors, custom annotations for tenant resolution
4. **Production Maturity** – Battle-tested in high-traffic systems globally
5. **Java Ecosystem** – Rich libraries (Spring Security, Data JPA, Cloud, etc.)
6. **Developer Experience** – DevTools, embedded servers, hot reload
7. **Cloud-Native** – Ready for Kubernetes, Cloud Run, Railway, Heroku, etc.

## 🔗 Integration with PetroFlow Ecosystem

### **Mobile App** (Flutter)
- Uses backend for real-time sync
- Offline SQLite cache with delta sync
- Single-device login enforcement
- Push notifications via Firebase

### **Web App** (React + Vite)
- Dashboard & admin interface
- Real-time data updates via WebSocket or polling
- Session management via JWT
- Multi-tenant org switching

### **Desktop App** (Flutter / Tauri)
- Cross-platform (Linux, Windows, macOS)
- Same offline-sync as mobile
- Local SQLite or IndexedDB cache
- Single login per device

## 📚 Documentation

- **[LLM_CONTEXT.md](../LLM_CONTEXT.md)** – Full business context, go-to-market, pricing
- **[ARCHITECTURE.md](../ARCHITECTURE.md)** – System-wide cross-platform design
- **[SETUP_LOCAL.md](./SETUP_LOCAL.md)** – Supabase + Redis Cloud + Nginx local setup (no Docker runtime)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit changes: `git commit -m "feat: add X capability"`
4. Push: `git push origin feature/your-feature`
5. Create Pull Request

## 📝 License

This project is licensed under the **MIT License**. See [LICENSE.md](./LICENSE.md) for details.

## 📧 Support & Questions

- **Issues**: [GitHub Issues](https://github.com/Angera-Silas/petroflow-backend/issues)
- **Email**: [angerasilas@gmail.com](mailto:angerasilas@gmail.com)
- **Project**: [https://github.com/Angera-Silas/petroflow-backend](https://github.com/Angera-Silas/petroflow-backend)