# NoteFlow

NoteFlow is an Android notes application built with an offline-first approach and cloud synchronization. It allows users to create, view, and manage personal notes locally using Room Database while seamlessly syncing data to Firebase Firestore when authenticated.

---

## About the Project

NoteFlow is designed to provide a smooth and reliable note-taking experience. Notes are stored locally so the app works even without an internet connection. When a user logs in, notes are synchronized with Firebase Firestore, making them accessible across multiple devices under the same account.

The project follows Clean Architecture principles to ensure scalability, maintainability, and clear separation of concerns.

---

## Features

- Email and Password authentication using Firebase Authentication  
- Offline-first note storage with Room Database  
- Cloud synchronization using Firebase Firestore  
- User-specific data isolation  
- Modern UI built with Jetpack Compose  
- Reactive data handling with Kotlin Flow  
- Clean Architecture (Presentation, Domain, Data layers)

---

## Architecture

The application follows Clean Architecture:

```
Presentation (Jetpack Compose UI)
└── ViewModel
    └── Domain (Use Cases)
        └── Repository (Interface)
            └── Data Sources
                ├── Room Database (Local)
                └── Firestore (Remote)
```


This structure improves testability, readability, and long-term maintainability.

---

## Installation

1. Clone the repository:

```bash
git clone https://github.com/Eternull30/NoteFlow
```
2. Open the project in Android Studio.
3. Add `google-services.json` to the `app/` directory.
4. In Firebase Console:
   - Enable Email/Password authentication
   - Create a Firestore database
5. Sync Gradle and run the app.

## Usage

1. Launch the app on an emulator or physical device.
2. Sign up or log in using email and password.
3. Create notes — they are saved locally and synced to Firestore.
4. Log in on another device with the same account to access synced notes.

## Built With
  - Kotlin
  - Jetpack Compose
  - Room Database
  - Firebase Firestore
  - Firebase Authentication
  - Hilt for Dependency Injection
  - Kotlin Coroutines & Flow

<h2 align="center">LICENSE</h2>

<div align="center">

```text
Copyright (c) 2026 Jeet Tanwar

Permission is granted to use, modify, and merge this software for personal or
internal use.

Redistribution, sublicensing, publishing, or selling copies of the software,
in source or binary form, is strictly prohibited without prior written
permission from the author.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.
