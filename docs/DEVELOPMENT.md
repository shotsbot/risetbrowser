# RiSETBrowser Development Guide

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                     UI Layer (Jetpack)                       │
│  ┌──────────────┬──────────────┬──────────────┬────────────┐
│  │   MainActivity │ SettingsActivity│DownloadActivity│Others│
│  └──────────────┴──────────────┴──────────────┴────────────┘
└─────────────────────────────────────────────────────────────┘
                              ⬇
┌─────────────────────────────────────────────────────────────┐
│                 Service Layer                                │
│  ┌──────────────┬─────────────────┬─────────────────────┐
│  │ AdBlocker   │ PopupBlocker    │ DownloadService    │
│  │ BlockerService               │ FileManager        │
│  └──────────────┴─────────────────┴─────────────────────┘
└─────────────────────────────────────────────────────────────┘
                              ⬇
┌─────────────────────────────────────────────────────────────┐
│              Data & Persistence Layer                        │
│  ┌──────────────┬──────────────┬──────────────┬────────────┐
│  │  BrowserDB  │ Preferences  │ Cache Mgr  │ Session Mgr│
│  └──────────────┴──────────────┴──────────────┴────────────┘
└─────────────────────────────────────────────────────────────┘
```

## Key Components

### MainActivity
- WebView management
- Navigation bar controls
- User input handling
- Ad/popup blocking integration

### Services
- **AdBlocker**: Pattern matching for ad domains
- **PopupBlocker**: JavaScript injection to prevent popups
- **DownloadService**: Background download management

### Database
- SQLite database for history, bookmarks, downloads
- ContentProvider for data access
- Automatic schema management

### Utilities
- **BrowserUtil**: File size formatting, URL validation
- **SearchEngineUtil**: Multi-engine search support
- **CacheManager**: Disk & memory caching
- **NetworkUtil**: Network connectivity checks

## Development Workflow

### Adding a New Feature

1. **Create necessary Java classes**
   ```java
   // In appropriate package under com.risetbrowser.android
   public class MyFeature { ... }
   ```

2. **Add database models if needed**
   ```java
   @Entity(tableName = "my_table")
   public class MyModel { ... }
   ```

3. **Update UI (layout XML)**
   ```xml
   <!-- In res/layout/ -->
   <LinearLayout>...</LinearLayout>
   ```

4. **Add strings and resources**
   ```xml
   <!-- In res/values/strings.xml -->
   <string name="feature_name">My Feature</string>
   ```

5. **Update AndroidManifest.xml if needed**
   - Register new activities/services
   - Add required permissions

6. **Test the feature**
   ```bash
   ./gradlew build
   ./gradlew assembleDebug
   ```

### Code Style Guidelines

- Use Java 8 features (lambdas, method references)
- Follow Android naming conventions
- Use meaningful variable and method names
- Add Javadoc comments for public APIs
- Keep methods small and focused

### Performance Considerations

1. **Threading**
   - Use background threads for I/O operations
   - Never block the UI thread
   - Use Handler or AsyncTask for updates

2. **Memory**
   - Properly release WebView resources
   - Implement lifecycle callbacks
   - Monitor heap usage

3. **Network**
   - Implement connection timeouts
   - Handle network failures gracefully
   - Cache responses when possible

## Testing

### Unit Tests
```bash
./gradlew test
```

### Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### Manual Testing Checklist
- [ ] WebView loading
- [ ] Navigation buttons work
- [ ] Ad blocking active
- [ ] Popup blocking active
- [ ] Downloads function
- [ ] History saved
- [ ] Bookmarks work
- [ ] Settings persist
- [ ] Night mode works

## Debugging

### Enable WebView Debugging
```java
if (BuildConfig.DEBUG) {
    WebView.setWebContentsDebuggingEnabled(true);
}
```

### Logcat
```bash
adb logcat com.risetbrowser.android:V
```

### Android Studio Debugger
- Set breakpoints
- Run in debug mode
- Step through code
- Inspect variables

## Deployment

### Creating Release Build
1. Update version in `app/build.gradle`
2. Create signed APK with keystore
3. Test on multiple devices
4. Generate APK/AAB for Play Store

### Version Management
- Update `versionCode` for each release
- Update `versionName` for user-visible version
- Add release notes

## Troubleshooting Common Issues

### WebView crashes
- Update WebView package
- Check for conflicting libraries
- Validate URL before loading

### Performance issues
- Profile with Android Profiler
- Check memory usage
- Reduce image sizes
- Cache aggressively

### Permission issues
- Request at runtime (API 23+)
- Handle permission denial
- Document required permissions

---

For more details, see the full [README.md](../../README.md)
