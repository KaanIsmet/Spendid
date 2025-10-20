# Spendid 💰

An AI-powered expense tracking application that makes managing your finances splendid.

## Overview

Spendid is a modern expense tracking app that leverages AI to automatically categorize expenses, extract data from bank statements, and provide intelligent financial insights. Built with a focus on automation and user experience, Spendid eliminates the tedious manual entry traditionally associated with expense tracking.

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

## Tech Stack

### Backend
- **Framework**: Spring Boot
- **Database**: PostgreSQL
- **AI Integration**: Groq API
- **Authentication**: [To be determined - JWT/OAuth]

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

# Configure database connection in application.properties
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

### Phase 1: Foundation
- [ ] Fork Credit Flux and rebrand to Spendid
- [ ] Set up Next.js frontend scaffold
- [ ] Basic CRUD operations for expenses
- [ ] User authentication

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

- Forked from Credit Flux
- Powered by Groq AI
- Built with Spring Boot and Next.js

## Contact

[Your contact information]

---

**Status**: 🚧 In Development

Made with ❤️ for better financial clarity
