<div align="center">

# 🕋 KIAI Android App

### *KIAI – Islamic Study Discovery App*

![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)
![Language](https://img.shields.io/badge/Language-Kotlin-7F52FF?logo=kotlin&logoColor=white)
![Build](https://img.shields.io/badge/Build-Gradle-02303A?logo=gradle&logoColor=white)

</div>

---

## 📖 About
<!-- TODO: Replace with 2–3 sentences: what problem does KIAI solve, and who is it for? -->
**KIAI** is an Android application designed to help Muslims discover Islamic study sessions (kajian), explore nearby learning locations, find Islamic scholars, and stay connected with religious learning activities.

KIAI is a mobile application that provides a convenient way for users to discover and access information about Islamic learning activities.

The application focuses on connecting users with Islamic educational opportunities through location-based discovery, kajian schedules, and information about Islamic teachers (ustadz).

Whether users want to find upcoming study sessions, explore online kajian, or discover nearby learning locations, KIAI provides a centralized platform to support their learning journey.

---

## 📸 Features

<div align="center">

**Home Screen**

<img width="325" height="704" alt="home" src="https://github.com/user-attachments/assets/797dedef-4db9-4d10-af28-fa280201c3ad" />

**List of upcoming study sessions, categorized by type.**

<img width="562" height="402" alt="image" src="https://github.com/user-attachments/assets/0ae4f24a-e05a-4f45-9566-2fb04387adb5" />

**Detailed view and map with nearby studies**

<img width="489" height="515" alt="image" src="https://github.com/user-attachments/assets/fec3eafc-847b-4baa-b024-39e9b5bcbfce" />

** Application features follow, ustadz**

<img width="472" height="510" alt="image" src="https://github.com/user-attachments/assets/a8025190-c2c4-45ec-80f9-ac88f4c36985" />

</div>

KIAI provides several features to support **Islamic learning and community engagement**.

### 🔐 Authentication & User Management

| Feature | Description |
|---|---|
| 📝 **User Registration** | Create a new account to access application features |
| 🔑 **User Login** | Authenticate and access your personal account |
| 👤 **Profile Management** | View and update your profile information |
| 🔒 **Password Management** | Change your account password securely |

### 📚 Kajian Discovery

| Feature | Description |
|---|---|
| 🗓️ **Upcoming Kajian** | Discover scheduled Islamic study sessions |
| 💻 **Online Kajian** | Explore online Islamic learning activities |
| 🧕 **Muslimah Kajian** | Find study sessions specifically categorized for Muslimah |
| 🔍 **Kajian Search** | Search for available Islamic study activities |
| 📄 **Kajian Details** | View complete information about each kajian session |

### 📍 Location-Based Discovery

| Feature | Description |
|---|---|
| 🧭 **Nearby Kajian Locations** | Find Islamic learning locations using device location services |
| 🗺️ **Kajian Location Mapping** | Explore kajian locations through Google Maps integration |
| 🛰️ **Location Awareness** | Uses Android location permissions to power location-based features |

### 🎓 Islamic Scholar Directory

- 👳 **Ustadz Directory** – Browse information about Islamic teachers and scholars

### 🧩 Personalization & Navigation

- ❤️ **Following** – Access followed content through a dedicated section
- 📱 **Bottom Navigation** – Move quickly between the app's main sections
- ℹ️ **About Application** – Learn more about KIAI and its purpose

---

## 🛠️ Tech Stack / Built With

| Category | Technology |
|---|---|
| **Language** | [Kotlin](https://kotlinlang.org/) `1.3.72` |
| **Platform** | Android (native) |
| **Build System** | [Gradle](https://gradle.org/) (Groovy DSL, wrapper included) |
| **Android Gradle Plugin** | `4.1.2` |
| **IDE** | [Android Studio](https://developer.android.com/studio) |
| **Repositories** | Google Maven, JCenter |
| **Version Control** | Git & GitHub |

---

## 🚀 Getting Started

### Prerequisites

Make sure you have the following installed:

- **[Android Studio](https://developer.android.com/studio)** 4.1 or newer
- **JDK 8 or 11** (Android Studio's bundled JDK works)
- **Android SDK** with the platform version set as `compileSdkVersion` in `app/build.gradle`
- **An emulator or a physical device** with USB debugging enabled
- **Git**

### Installation

**1. Clone the repository**

```bash
git clone https://github.com/Dziqy/KIAI-android-app.git
cd KIAI-android-app
```

**2. Open in Android Studio**

- Launch Android Studio → **File ▸ Open…** → select the `KIAI-android-app` folder
- Wait for the Gradle sync to finish. Accept any prompts to install missing SDK components.

**3. Build the project**

```bash
# macOS / Linux
./gradlew assembleDebug

# Windows
gradlew.bat assembleDebug
```

The debug APK is generated at:

```
app/build/outputs/apk/debug/app-debug.apk
```

### Running Locally

**Option A – Android Studio:** select a device and click **Run ▶** (`Shift + F10`).

**Option B – Command line:**

```bash
# Build and install on a connected device/emulator
./gradlew installDebug

# Launch the app (replace with your actual package name)
adb shell am start -n com.example.kiai/.MainActivity
```

### Running Tests

```bash
./gradlew test                  # Unit tests
./gradlew connectedAndroidTest  # Instrumented tests (device/emulator required)
```

### Troubleshooting

| Problem | Fix |
|---|---|
| `Could not resolve` / JCenter errors | JCenter is deprecated. Replace `jcenter()` with `mavenCentral()` in the root `build.gradle`. |
| Gradle sync fails | Use **File ▸ Invalidate Caches / Restart**, then re-sync. |
| `adb` can't find the device | Enable USB debugging and run `adb devices` to verify. |

---

