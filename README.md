# 🔐 SecurityScanApp

**SecurityScanApp** is an Android attendance management system designed with integrated security features, including event management, attendance tracking, and data validation. The app is built using Jetpack Compose and Room Database, providing a modern solution for managing attendance in secure environments such as university systems, conferences, and restricted facility events.

---

## 🚀 Core Features

### 1. 📋 Attendance Tracking System
The app tracks event attendance with the following features:

- **Student Information**: Stores personal details such as name, registration number, email, and phone number.
- **Event Association**: Attendees are linked to specific events using foreign key relationships.
- **CRUD Operations**: Provides the ability to create, read, update, and delete attendance records.
- **Sorting**: Attendance records can be sorted by student name, registration number, or email.
- **Search**: Users can search across multiple fields to find specific attendees or events.
- **Relationship Tracking**: Maintains relationships between events and attendees, with cascade delete to maintain data integrity.

### 2. 📅 Event Management
- **Event Details**: Stores essential information about events, including name, date, and time.
- **Attendance Association**: Links event details to attendance records for easy tracking.
- **Cascade Delete**: Ensures that deleting an event automatically removes its related attendance records, maintaining database integrity.

### 3. 🔐 Security Features
- **Room Database Encryption**: Implied by the secure schema management for sensitive data.
- **Data Validation**: Ensures input validation through entity constraints and foreign key enforcement.
- **Relationship Constraints**: Ensures that all event and attendee data maintains referential integrity via foreign key constraints.

---

## 🛠 Technical Architecture

### 1. 💾 Data Layer
- **Room Database**: The core database layer for managing attendance and event records.

**Entities**:
- `Attendance`: Represents individual attendance records with a foreign key to the event.
- `Event`: Represents event details.

**DAOs**:
- Data access objects for querying the database:
  - Sorting attendance by name, email, or registration number.
  - Searching for attendees based on partial matches.
  - Relationship queries to link events with their attendance records.

**Repository Pattern**: Mediates between the DAOs and ViewModels, providing a clean API for data operations.

### 2. 📱 Modern Android Components
- **Jetpack Compose**: Used for building the user interface in a modern, declarative way.
- **Kotlin Coroutines**: Utilized in DAOs to perform asynchronous data operations with Flow for reactive data handling.
- **Room Persistence Library**: Simplifies local database management and provides robust support for complex queries and relationships.
- **KSP (Kotlin Symbol Processing)**: Used for annotation processing to handle dependency injection and other tasks.

---

## 🔮 Planned Features
- **Attendance Sheet Generation**: Future plans include generating QR/barcodes for attendance tracking (as suggested by `GenerateAttendanceSheetScreen.kt`).
- **Scan Verification**: Placeholder UI for scanning QR codes or barcodes to verify attendance (`SeeAttendanceSheetsScreen.kt`).
- **Attendance History**: A potential screen for viewing past attendance records and verifying scanning data (`AttendanceRecordsScreen.kt`).

---

## 🧭 Use Cases
This app is ideal for environments that require secure tracking of event attendance, such as:

- **University Security Systems**: Track and monitor student attendance at various campus events.
- **Conference Access Control**: Control access to restricted sessions based on pre-registered attendance.
- **Restricted Facility Event Management**: Manage attendance at secure or restricted-access events.

---

## 🏁 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/SecurityScanApp.git
```

---

## 🎞 Demo

![SecurityScanApp Demo](https://github.com/Sohilphilip/SecurityScanApp/blob/main/security%20scan%20demo%20comp.gif)
