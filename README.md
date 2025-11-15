# BCI - Library Management System

A Java-based library management system that handles user registrations, work cataloging (books, DVDs), borrowing requests, returns, fines, and notifications.

## Project Structure

The project consists of three main components:

- **`053/bci-core`**: Core business logic and domain models
  - Library management (users, works, requests)
  - User categories and behaviors (Dutiful, Defaulter, Active)
  - Work types (Books, DVDs)
  - Borrowing rules and fine calculations
  - Data persistence and import/export functionality

- **`053/bci-app`**: User interface and application layer
  - Interactive menu-driven interface
  - Commands for all library operations
  - File management (open, save)
  - Date management and work inventory control

- **`po-uilib`**: UI library providing forms, dialogs, and interaction components

## Features

- **User Management**: Register users with different categories and statuses
- **Work Catalog**: Manage books and DVDs with creators, categories, and stock
- **Borrowing System**: Request and return works with automatic fine calculation
- **Notifications**: Alert users when requested works become available
- **Search**: Find works by title, creator, or search terms
- **Date Simulation**: Advance system date to test time-based features
- **Persistence**: Save and load library state

## Requirements

- Java Development Kit (JDK) 8 or higher
- GNU Make

## Building the Project

1. **Build the UI library**:
   ```bash
   cd po-uilib
   make
   cd ..
   ```

2. **Build the core and application**:
   ```bash
   cd 053
   make
   cd ..
   ```

This will compile all Java source files and create JAR files:
- `po-uilib/po-uilib.jar`
- `053/bci-core/bci-core.jar`
- `053/bci-app/bci-app.jar`

## Running the Application

### Step 1: Set the CLASSPATH

```bash
export CLASSPATH=053/bci-app/bci-app.jar:053/bci-core/bci-core.jar:po-uilib/po-uilib.jar
```

### Step 2: Run the application

**Without importing data**:
```bash
java bci.app.App
```

**With data import** (if you have an import file):
```bash
java -Dimport=path/to/import-file.txt bci.app.App
```

### Complete Example

```bash
# From the project root directory
export CLASSPATH=053/bci-app/bci-app.jar:053/bci-core/bci-core.jar:po-uilib/po-uilib.jar
java bci.app.App
```

## Cleaning the Build

To remove all compiled files:

```bash
cd 053
make clean
cd ../po-uilib
make clean
cd ..
```

## Project Structure Detail

```
PO-Proj/
├── 053/
│   ├── bci-core/       # Core domain logic
│   ├── bci-app/        # Application layer
│   └── Makefile        # Build orchestration
├── po-uilib/           # UI library
│   └── Makefile
└── README.md
```
