# HereApp

An Android SMS-sending app built with Kotlin, Jetpack Compose, and Material Design 3. Manage a list of message templates and contacts, set defaults for each, and send an SMS with one tap from the home screen.

## Features

- **One-tap sending** — Set a default message and contact, then hit the big Send button
- **Quick selection** — Tap the Message or Recipient card on the home screen to expand an inline picker and switch defaults without navigating away
- **Message templates** — Create, edit, and delete reusable SMS messages
- **Contact management** — Add contacts manually or pick from your phone's contact list
- **Defaults** — Star any message or contact to set it as the default

## Tech Stack

- Kotlin
- Jetpack Compose with Material Design 3
- Room database for persistent storage
- DataStore for user preferences
- Compose Navigation

## Building

```bash
./gradlew assembleDebug      # Build debug APK
./gradlew assembleRelease    # Build release APK
./gradlew installDebug       # Install on connected device
```

## Testing

```bash
./gradlew test                    # Unit tests (JVM)
./gradlew connectedAndroidTest    # Instrumented tests (requires device/emulator)
```

## Requirements

- Android Studio Hedgehog or later
- Min SDK 26 (Android 8.0)
- Target SDK 34 (Android 14)
- Java 17
