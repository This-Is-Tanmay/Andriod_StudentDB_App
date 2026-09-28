# 📱 Student & Faculty Database Management System

A simple Android application developed using **Android Studio** for managing student and faculty information using **SQLite**.

The project is designed as a basic academic Android application demonstrating fundamental Android concepts such as CRUD operations, intents, notifications, alert dialogs, camera integration, layouts, and user authentication.

## ✨ Features

### 🔐 Authentication
- User Login
- User Registration / Sign Up
- Simple local authentication
- Logout functionality

### 👨‍🎓 Student Database
- Add Student
- View Students
- Update Student
- Delete Student
- Store student information using SQLite
- Capture and store student photo using Camera Intent

### 👨‍🏫 Faculty Database
- Switch between Student and Faculty database
- View faculty information
- Simple faculty management interface

### 📱 Android Features Demonstrated
- **Explicit Intent** – Navigate between activities within the application
- **Implicit Intent** – Open a website using the device's browser
- **Camera Intent** – Capture student photographs
- **Notification** – Display notification after successfully adding a student
- **AlertDialog** – Confirm important actions such as saving or deleting data
- **Layouts** – XML-based Android layouts for designing the application UI
- **SQLite Database** – Store and manage application data locally
- **CRUD Operations** – Create, Read, Update and Delete student records

## 🛠️ Technologies Used

- **Android Studio**
- **Java**
- **XML**
- **SQLite**
- **Android SDK**
- **Gradle**

## 📂 Project Structure

```text
Student-Database/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com.example.studentdatabase/
│           │       ├── MainActivity.java
│           │       ├── AddStudentActivity.java
│           │       ├── ViewStudentActivity.java
│           │       ├── UpdateStudentActivity.java
│           │       ├── DeleteStudentActivity.java
│           │       └── DatabaseHelper.java
│           │
│           ├── res/
│           │   ├── layout/
│           │   ├── drawable/
│           │   └── values/
│           │
│           └── AndroidManifest.xml
│
└── build.gradle
```

## 🗄️ Database

The application uses **SQLite** as its local database.

The database helper class is responsible for:

- Creating the database
- Creating tables
- Inserting records
- Reading records
- Updating records
- Deleting records

The main database code can be found in:

```text
DatabaseHelper.java
```

## 🚀 How to Run

1. Download or clone this repository.
2. Open **Android Studio**.
3. Select **Open** and choose the project folder.
4. Allow Gradle to sync and download the required dependencies.
5. Connect an Android device or start an Android Emulator.
6. Click **Run ▶**.
7. The application will be installed on the selected device.

## 📋 Requirements

- Android Studio
- JDK compatible with the project's Gradle/Android plugin configuration
- Android SDK
- Android Emulator or physical Android device

## 📸 Application Flow

```text
Login
  ↓
Sign Up / Login
  ↓
Main Menu
  ↓
 ┌─────────────────────┐
 │ Student Database    │
 │ Faculty Database    │
 └─────────────────────┘
          ↓
Student Operations
  ├── Add Student
  ├── View Students
  ├── Update Student
  └── Delete Student

Add Student
  ↓
Enter Details
  ↓
Take Photo
  ↓
Save Student
  ↓
AlertDialog Confirmation
  ↓
SQLite Database
  ↓
Success Notification
```

## 🎯 Purpose of the Project

This project was created to understand and demonstrate the **basic concepts of Android application development and database management** through a simple real-world use case.

It is intentionally kept simple so that the implementation and individual Android concepts can be easily understood and explained.

## 👨‍💻 Author

**Tanmay Lade**

IT Undergraduate  
Interested in Android Development, Java, Python, Data Structures & Algorithms, and Full-Stack Development.

## 📄 License

This project is created for **educational and academic purposes**.
