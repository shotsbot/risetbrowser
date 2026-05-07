# RiSETBrowser - Quick Start Guide

## 🚀 Installation & First Run

### Step 1: Setup Android Environment

Make sure you have:
- Android Studio latest version OR
- Android SDK installed with API 34
- Java 8+ installed

### Step 2: Build the Application

```bash
# Navigate to project directory
cd /workspaces/risetbrowser

# Build debug version (quickest)
./gradlew assembleDebug

# OR build release version (optimized)
./gradlew assembleRelease
```

### Step 3: Install APK

```bash
# Using ADB
adb install -r app/build/outputs/apk/debug/app-debug.apk

# OR using Android Studio
# Click "Run" button in Android Studio
```

### Step 4: Launch the App

1. Find "RiSETBrowser" in your app drawer
2. Tap to launch
3. Grant requested permissions
4. Start browsing!

---

## 📖 First-Time User Guide

### Main Browser Interface

```
┌─────────────────────────────────────┐
│  ◄  ►  ↻  |  URL input  |  ⌂  ≡    │  ← Toolbar
├─────────────────────────────────────┤
│                                     │
│         WebView Content             │  ← Web Page
│                                     │
├─────────────────────────────────────┤
│  ★       ⤵️       ⋯                   │  ← Bottom Toolbar
└─────────────────────────────────────┘
```

### Key Controls

| Button | Function |
|--------|----------|
| ◄ | Go back |
| ► | Go forward |
| ↻ | Refresh page |
| ⌂ | Go to homepage |
| ≡ | Open menu |
| ★ | Add bookmark |
| ⤵️ | Share page |
| ⋯ | More options |

### Keyboard Shortcuts

- **Ctrl+T**: New tab (if implemented)
- **Ctrl+H**: Show history
- **Ctrl+B**: Show bookmarks
- **Ctrl+D**: Add bookmark
- **Ctrl+L**: Focus URL bar

---

## ⚙️ Initial Settings

### Recommended First Steps

1. **Set Homepage**
   - Menu → Settings → Homepage
   - Set your preferred start page

2. **Choose Search Engine**
   - Menu → Settings → Search Engine
   - Select from: Google, Bing, DuckDuckGo, Yahoo, Baidu, Yandex

3. **Enable Security**
   - Menu → Settings → Do Not Track (ON)
   - Ad Blocking (ON by default)
   - Popup Blocking (ON by default)

4. **Theme Selection**
   - Menu → Settings → Appearance
   - Choose Light, Dark, or Auto theme

---

## 🌐 Basic Browsing

### Enter a URL
```
1. Tap the URL input field (top of screen)
2. Type URL or search query
3. Press Enter or tap Search
```

### Navigate Website
```
- Scroll: Swipe up/down
- Links: Tap to follow
- Back: Tap ◄ button or swipe right
- Forward: Tap ► button or swipe left
```

### Search Web
```
1. Tap URL input field
2. Type search term
3. Press Enter
4. Results from selected search engine appear
```

---

## 📥 Download Files

### Download Media

When you find an image, video, or file:
```
1. Long-press on media
2. Tap "Download" option
3. Choose location (typically Downloads folder)
4. Tap "Download" to confirm
```

### View Downloads

```
Menu → Downloads
- See list of all downloads
- Tap file to open
- Long-press for options
```

---

## 📑 Bookmarks & History

### Add Bookmark
```
1. Browse to page you want to save
2. Tap ★ button in toolbar
3. Edit title if desired
4. Tap "Save"
```

### Access Bookmarks
```
Menu → Bookmarks
- View all saved bookmarks
- Tap to visit
- Long-press for edit/delete
```

### View History
```
Menu → History
- See recent visits
- Search through history
- Clear history as needed
```

---

## 🔒 Privacy & Security

### Private Mode
```
Menu → New Incognito Tab
- Browse without saving history
- Cookies cleared on close
- Search history not saved
```

### Clear Data
```
Menu → Settings → Storage
- Clear cache
- Clear cookies
- Clear history
- Clear browsing data
```

### Disable JavaScript (if needed)
```
Menu → Settings → Privacy
- Toggle JavaScript OFF
- Reload page (some features may not work)
```

---

## 🎨 Customization

### Change Theme
```
Menu → Settings → Appearance
- Light Theme
- Dark Theme
- Night Mode
```

### Adjust Text Size
```
Menu → Settings → Display
- Increase/decrease text size
- Changes apply to all websites
```

### Set Font Size
```
Menu → Settings → Display
- Adjust zoom level
- Default, Small, Medium, Large
```

---

## 🆘 Troubleshooting

### Page Won't Load
```
1. Tap ↻ to refresh
2. Check internet connection (WiFi/Mobile)
3. Wait a few seconds
4. Try different URL
```

### App Crashes
```
1. Close and reopen app
2. Force stop: Settings → Apps → RiSETBrowser → Force Stop
3. Clear cache: Settings → Apps → RiSETBrowser → Clear Cache
4. Reinstall app if problem persists
```

### Too Many Ads
```
Menu → Settings → Ad Blocking
- Confirm "Block Ads" is ON
- Refresh page (Ctrl+R)
- Clear cache if needed
```

### Slow Loading
```
1. Check internet connection
2. Close other apps
3. Clear cache: Menu → Settings → Storage → Clear Cache
4. Enable Data Saver mode
```

---

## 📱 Gesture Controls

### Swipe Gestures
| Gesture | Action |
|---------|--------|
| Swipe Right | Go back |
| Swipe Left | Go forward |
| Double Tap | Zoom in |
| Pinch Out | Zoom in |
| Pinch In | Zoom out |
| Long Press | Context menu |

---

## 🔐 Security Tips

1. ✅ Keep app updated
2. ✅ Use strong passwords
3. ✅ Enable Do Not Track (DNT)
4. ✅ Use private browsing for sensitive tasks
5. ✅ Clear cache regularly
6. ✅ Don't save passwords for important accounts
7. ✅ Keep Android OS updated

---

## 📞 Need Help?

### Resources
- **README.md** - Project overview
- **docs/BUILD_GUIDE.md** - Building instructions
- **docs/FEATURES.md** - Complete feature list
- **docs/DEVELOPMENT.md** - Technical details

### Common Issues

**Q: Where are downloads saved?**
A: Downloads folder on your device

**Q: How do I enable cookies?**
A: Menu → Settings → Privacy → Cookies (usually enabled by default)

**Q: Can I sync bookmarks?**
A: Currently bookmarks are local; cloud sync available in future versions

**Q: How do I uninstall?**
A: Settings → Apps → RiSETBrowser → Uninstall

---

## 🎉 Enjoy!

You're all set! Enjoy browsing the web with RiSETBrowser.

For more advanced features, see [FEATURES.md](docs/FEATURES.md)

---

**RiSETBrowser v1.0.0**
*Modern Android Browser with Ad Blocking & Download Manager*
