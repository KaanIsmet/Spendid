# Spendid 💰
An AI-powered expense tracking application that makes managing your finances splendid.

## Overview
Spendid is a modern expense tracking app that leverages AI to automatically categorize expenses, extract data from bank statements, and provide intelligent financial insights. Built with a focus on automation and user experience, Spendid eliminates the tedious manual entry traditionally associated with expense tracking.

**This project is an iteration of Credit Flux, repurposed and enhanced with a fully implemented expense tracking API, migrated to PostgreSQL, and expanded with AI-powered financial management features.**

## Architecture

### Technical Architecture
![Technical Architecture](.png)
*Overview of the system architecture showing backend, frontend, database, and AI integration layers*

### Entity Relationship Diagram
![Entity Relationship Diagram](./docs/images/erd-diagram.png)
*Database schema showing relationships between users, expenses, categories, and budgets*

### User Flow Diagram
![User Flow Diagram](./docs/images/user-flow-diagram.png)
*Primary user journeys through expense entry, categorization, and analysis*

## Features

### Core Functionality
- **Manual Expense Entry** - Quick and intuitive expense logging
- **AI-Powered Categorization** - Automatic expense classification using Groq AI
- **Bank Statement Import** - Upload CSV, PDF, or Excel bank statements for automatic processing
- **Smart Merchant Recognition** - Normalizes merchant names across different formats
- **Expense Analytics** - Visual insights into spending patterns
- **Budget Tracking** - Set and monitor budgets by category

### AI Features
- Natural language expense entry
- Intelligent transaction categorization
- Duplicate detection across uploads
- Spending pattern analysis
- Budget recommendations

## What's New in Spendid
- **PostgreSQL Migration** - Upgraded from MySQL to PostgreSQL for enhanced performance and features
- **Complete Expense API** - Fully implemented RESTful API for expense management operations
- **Enhanced Data Models** - Optimized schema for expense tracking and financial analytics
- **AI Integration** - New Groq API integration for intelligent expense processing
- **Advanced File Processing** - Support for multiple bank statement formats (CSV, PDF, Excel)

## Tech Stack

### Backend
- **Framework**: Spring Boot
- **Database**: PostgreSQL (migrated from MySQL)
- **AI Integration**: Groq API
- **Authentication**: OAuth2 with RBAC (inherited from Credit Flux)
- **Security**: API rate limiting, role-based access control

### Frontend
- **Framework**: Next.js (React)
- **Styling**: Tailwind CSS
- **State Management**: [To be determined]

### File Processing
- CSV parsing with Papaparse
- PDF extraction for bank statements
- Excel file support (XLSX)

## Project Structure
```
spendid/
├── backend/          # Spring Boot API
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── frontend/         # Next.js application
│   ├── src/
│   │   ├── app/
│   │   ├── components/
│   │   └── lib/
│   ├── public/
│   └── package.json
├── docs/
│   └── images/       # Documentation images
└── README.md
```

## Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- PostgreSQL 14+
- Groq API key

### Backend Setup
```bash
cd backend
# Configure PostgreSQL connection in application.properties
# Add your Groq API key to environment variables
# Build and run
./mvnw spring-boot:run
```

### Frontend Setup
```bash
cd frontend
# Install dependencies
npm install
# Configure environment variables
cp .env.example .env.local
# Add your API endpoint and any required keys
# Run development server
npm run dev
```

### Database Setup
```sql
-- Create database
CREATE DATABASE spendid;
-- Run migrations (details TBD)
```

## API Documentation
API documentation will be available at `/swagger-ui` when running the backend locally.

## Development Roadmap

### Phase 1: Foundation ✅
- [x] Fork Credit Flux and rebrand to Spendid
- [x] Migrate database from MySQL to PostgreSQL
- [x] Implement complete expense API with CRUD operations
- [ ] Set up Next.js frontend scaffold
- [ ] User authentication integration

### Phase 2: File Processing
- [ ] Bank statement upload interface
- [ ] CSV parsing implementation
- [ ] PDF statement extraction
- [ ] Excel file support

### Phase 3: AI Integration
- [ ] Groq API integration
- [ ] Automatic expense categorization
- [ ] Merchant name normalization
- [ ] Duplicate detection

### Phase 4: Advanced Features
- [ ] Natural language expense entry
- [ ] Spending insights dashboard
- [ ] Budget recommendations
- [ ] Export functionality

## Contributing
This is currently a personal project. Contributions may be accepted in the future.

## License
[To be determined]

## Acknowledgments
- Built upon Credit Flux foundation
- Powered by Groq AI
- Built with Spring Boot and Next.js

## Contact
[Your contact information]

---

**Status**: 🚧 In Development  
**Note**: This project builds on the Credit Flux codebase with significant enhancements for expense tracking and financial management.

Made with ❤️ for better financial clarity
