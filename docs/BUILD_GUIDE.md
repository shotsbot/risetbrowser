# RiSETBrowser Build Configuration Guide

## Prerequisites

Ensure you have the following installed:
- Java Development Kit (JDK) 8 or higher
- Android SDK API Level 34 (or configure in build.gradle)
- Android Build Tools 34.0.0 (or higher)
- Gradle 7.4.2 (included in project wrapper)

## Setup Instructions

### 1. Configure Android SDK Location

Create a `local.properties` file in the project root:

```bash
cp local.properties.example local.properties
```

Edit `local.properties` and set the correct path to your Android SDK:

```properties
sdk.dir=/path/to/your/android/sdk
```

For example:
- Linux/Mac: `sdk.dir=/Users/username/Library/Android/sdk`
- Windows: `sdk.dir=C:\\Users\\username\\AppData\\Local\\Android\\sdk`

### 2. Build from Command Line

#### Debug APK
```bash
./gradlew assembleDebug
```

Output: `app/build/outputs/apk/debug/app-debug.apk`

#### Release APK (unsigned)
```bash
./gradlew assembleRelease
```

Output: `app/build/outputs/apk/release/app-release-unsigned.apk`

#### Release APK (signed)
First, create a keystore:
```bash
keytool -genkey -v -keystore riset.keystore -keyalg RSA -keysize 2048 -validity 10000 -alias riset
```

Then, sign the release APK:
```bash
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 \
    -keystore riset.keystore \
    app/build/outputs/apk/release/app-release-unsigned.apk riset
```

Or use zipalign to optimize:
```bash
zipalign -v 4 app-release-unsigned.apk app-release.apk
```

### 3. Install APK on Device

```bash
adb install -r app-release.apk
```

Or from Android Studio:
- Run > Run 'app'
- Select target device

## Build Tasks

```bash
# Clean build
./gradlew clean

# Build with logs
./gradlew build --info

# Show available tasks
./gradlew tasks

# Build and analyze
./gradlew build --scan

# Run tests
./gradlew test
./gradlew connectedAndroidTest
```

## Gradle Properties

Edit `gradle.properties` to customize build behavior:
- `org.gradle.jvmargs`: JVM memory allocation
- `android.useAndroidX`: Enable AndroidX
- `android.enableJetifier`: Enable Jetifier

## Build Variants

The project can be built with different configurations:
- **Debug**: Fast build, full debugging capabilities
- **Release**: Optimized, minified, obfuscated

Configure in `app/build.gradle`:

```gradle
buildTypes {
    debug { ... }
    release { ... }
}
```

## Troubleshooting

### Out of Memory Error
Increase Gradle heap size in `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx4096m
```

### SDK Not Found
Ensure `local.properties` correctly points to Android SDK location.

### Outdated Dependencies
Update dependencies:
```bash
./gradlew dependencyUpdates
```

### Dependency Conflicts
Resolve conflicts with duplicate class warnings:
```bash
./gradlew app:dependencies
```

## CI/CD Integration

For GitHub Actions, create `.github/workflows/build.yml`:

```yaml
name: Android Build

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: 8
      - run: ./gradlew build
```

## Build Performance Tips

1. Use gradle daemon: `org.gradle.daemon=true`
2. Enable parallel builds: `org.gradle.parallel=true`
3. Use gradle offline mode for faster builds: `--offline`
4. Update ProGuard rules to avoid unnecessary processing

## Security Notes

- Never commit `local.properties` or keystores to version control
- Use environment variables for sensitive build parameters
- Sign release builds with a secure keystore
- Keep dependencies updated for security patches

---

For more information, see [README.md](../../README.md)
