# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

HereApp is an Android SMS-sending application built with Kotlin, Jetpack Compose, and Material Design 3. Users manage a list of message templates and contacts, set defaults for each, and quickly send an SMS with one tap from the home screen.

## Build & Test Commands

```bash
./gradlew assembleDebug           # Build debug APK
./gradlew assembleRelease         # Build release APK
./gradlew test                    # Run unit tests (local JVM)
./gradlew connectedAndroidTest    # Run instrumented tests (requires device/emulator)
./gradlew test --tests "*ClassName"  # Run a specific test class
./gradlew clean                   # Clean build artifacts
```

No linter or formatter is configured. Kotlin code style is set to `official` in `gradle.properties`.

## Architecture

**Single-module Gradle project** (`app/`) using Kotlin DSL build files.

### Layers

- **Data layer** (`data/`): Room database with `Message` and `Contact` entities, each with a DAO providing Flow-based reactive queries and suspend CRUD functions. `PreferencesManager` wraps DataStore to persist the default message/contact IDs.
- **UI layer** (`ui/screens/`, `ui/components/`): Compose screens (`HomeScreen`, `MessagesScreen`, `ContactsScreen`) with state hoisted to `MainActivity`. Reusable components (`ItemCard`, `EditDialog`, `DeleteConfirmationDialog`) live in `CommonComponents.kt`.
- **Business logic**: `SmsHelper` is a singleton object that checks/requests SMS permission and sends messages via `SmsManager`.
- **Navigation**: Compose Navigation with three routes (`home`, `messages`, `contacts`) set up in `MainActivity.kt` via `HereAppNavigation`.

### Data flow

`MainActivity` collects Flows from Room DAOs and DataStore, converts them to Compose state with `collectAsState`, and passes values down to screens as parameters. Database writes use `rememberCoroutineScope` to launch suspend DAO calls.

### Key technical details

- **Min SDK 26, Target SDK 34**, compiled with Java 17
- **Room database** (version 1) with singleton instance via `synchronized` block in `AppDatabase`
- **KSP** (not kapt) for Room annotation processing
- **Compose BOM 2023.10.01**, Kotlin compiler extension 1.5.5
- No ViewModel layer — state is managed directly in `MainActivity`
- No dependency injection framework — manual construction in `MainActivity`
- No tests exist yet; JUnit 4, Espresso, and Compose test dependencies are declared
