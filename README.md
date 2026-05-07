# RiSETBrowser - Advanced Android Web Browser

![RiSETBrowser Logo](docs/logo.png)

## Deskripsi

RiSETBrowser adalah aplikasi browser Android yang canggih dan modern dengan fitur-fitur lengkap untuk pengalaman browsing terbaik.

## Fitur Utama

### ✨ Browsing Canggih
- **WebView Rendering**: Rendering halaman web yang cepat dan responsif
- **Tab Management**: Kelola multiple tab browsing
- **Incognito Mode**: Mode browsing pribadi tanpa menyimpan histori
- **Full Screen Mode**: Tampilan full screen untuk pengalaman immersive
- **Reader Mode**: Baca artikel tanpa distraksi

### 🛡️ Keamanan & Privasi
- **Ad Blocking**: Blokir semua iklan dan banner di semua website
- **Popup Blocker**: Cegah popup dan modal dialog yang mengganggu
- **Do Not Track (DNT)**: Kirim sinyal DNT ke server website
- **Clear Cache**: Hapus cache, cookies, dan data browsing
- **Private Browsing**: Mode incognito untuk privasi maksimal

### ⬇️ Download Manager (1DM-like Features)
- **Smart Download**: Unduh foto, video, audio dari website manapun
- **Download Queue**: Kelola antrian download
- **Resume Download**: Lanjutkan download yang tertunda
- **Download History**: Riwayat semua file yang diunduh
- **Auto Detection**: Deteksi otomatis media files di website

### 🔍 Fitur Pencarian
- **Multiple Search Engines**: Google, Bing, DuckDuckGo, Yahoo, Baidu, Yandex
- **Search Suggestions**: Saran pencarian real-time
- **Search History**: Riwayat pencarian
- **Voice Search**: Pencarian menggunakan suara

### 📚 Bookmarks & History
- **Bookmark Management**: Simpan dan kelola bookmark
- **Folder Organization**: Organisir bookmark dalam folder
- **Search Bookmarks**: Cari bookmark dengan mudah
- **Import/Export**: Impor dan ekspor bookmark
- **History Tracking**: Riwayat lengkap browsing

### 🎨 UI/UX Modern (2026 Edition)
- **Material Design 3**: Desain modern dengan Material Design 3
- **Night Mode**: Mode gelap untuk browsing malam hari
- **Adaptive Colors**: Warna yang menyesuaikan dengan device theme
- **Smooth Animations**: Animasi smooth dan responsive
- **Dark/Light Theme**: Tema gelap dan terang

### 🌐 Browser Features
- **Desktop Mode**: Mode desktop untuk akses versi desktop website
- **Multiple Gestures**: Gesture control untuk navigasi
- **Long Press Menu**: Menu context yang lengkap
- **Text Selection**: Seleksi dan copy teks dari website
- **Image Viewer**: Viewer bawaan untuk melihat gambar

### ⚙️ Pengaturan Lengkap
- **Homepage Configuration**: Atur halaman awal browser
- **Cache Management**: Kelola ukuran dan lokasi cache
- **JavaScript Control**: Enable/disable JavaScript
- **Geolocation**: Kontrol izin lokasi
- **Cookie Management**: Kelola cookies

### 🔗 Social & Sharing
- **Share Functionality**: Bagikan URL ke aplikasi lain
- **Copy Link**: Copy link ke clipboard
- **Share Image**: Bagikan gambar dari website
- **Social Integration**: Integrasi dengan media sosial

### 🚀 Performance Features
- **Page Caching**: Cache halaman untuk loading cepat
- **Image Compression**: Kompresi gambar otomatis
- **Resource Optimization**: Optimasi resource loading
- **Battery Saver**: Mode hemat baterai
- **Data Saver**: Mode hemat data untuk koneksi terbatas

## Persyaratan Sistem

- **Android Version**: Android 5.0 (API 21) atau lebih tinggi
- **RAM**: Minimum 2GB (recommended 4GB)
- **Storage**: Minimum 100MB untuk instalasi
- **Java**: Java 8 (kompatibel dengan versi yang lebih baru)

## Build Instructions

### Prerequisites
- Android Studio Arctic Fox atau lebih baru
- Java Development Kit 8 atau lebih baru
- Android SDK (API Level 34)
- Gradle 7.4.2

### Langkah Build

1. **Clone Repository**
   ```bash
   git clone https://github.com/shotsbot/risetbrowser.git
   cd risetbrowser
   ```

2. **Update SDK**
   ```bash
   # Pastikan Android SDK terbaru sudah terinstal
   ```

3. **Build Project**
   ```bash
   # Gunakan Gradle Wrapper
   ./gradlew build
   
   # Atau dengan Android Studio
   # Build > Build Bundle(s) / APK(s) > Build APK(s)
   ```

4. **Generate APK**
   ```bash
   ./gradlew assembleRelease    # Build release APK
   ./gradlew assembleDebug      # Build debug APK
   ```

5. **Output**
   - Debug APK: `app/build/outputs/apk/debug/app-debug.apk`
   - Release APK: `app/build/outputs/apk/release/app-release.apk`

## Architecture

### Project Structure
```
risetbrowser/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/risetbrowser/android/
│   │   │   │   ├── ui/              # Activities dan UI components
│   │   │   │   ├── service/         # Services (AdBlocker, PopupBlocker, Download)
│   │   │   │   ├── database/        # Database dan ContentProvider
│   │   │   │   ├── model/           # Data models
│   │   │   │   └── util/            # Utility classes
│   │   │   ├── res/                 # Resources (layouts, drawables, values)
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                    # Unit tests
│   │   └── androidTest/             # Instrumented tests
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
└── gradle.properties
```

### Key Components

1. **MainActivity.java**: Activity utama browser
2. **AdBlocker.java**: Sistem pemblokiran iklan
3. **PopupBlocker.java**: Sistem pemblokiran popup
4. **DownloadService.java**: Service untuk manajemen download
5. **BrowserDatabase.java**: Database untuk menyimpan data
6. **SearchEngineUtil.java**: Utilitas search engine

## Fitur-Fitur Lanjutan

### 1. Ad & Popup Blocking Engine
- Database domain blacklist untuk blocking ads
- Pattern matching untuk mendeteksi ad URLs
- JavaScript injection untuk block popup
- Real-time update untuk blocking lists

### 2. Download Manager 1DM-like
- Multi-threaded download capability
- Pause/Resume functionality
- Download history tracking
- Media detection dan auto-categorization
- File organization dalam folder

### 3. Modern UI Components
- Material Design 3 compliance
- Adaptive color system
- Smooth transitions dan animations
- Responsive layout untuk semua ukuran screen
- Custom toolbar dan bottom navigation

### 4. WebView Enhancements
- Custom WebViewClient untuk filtering
- Custom WebChromeClient untuk permission handling
- JavaScript interface untuk integrasi native code
- Download listener untuk file handling
- Cache management

## Performance Optimizations

1. **Memory Management**: Efficient WebView lifecycle management
2. **Image Optimization**: Image compression dan caching
3. **Network Optimization**: Connection pooling dengan OkHttp
4. **Storage**: SQLite database untuk fast queries
5. **Caching Strategy**: Multi-layer caching (memory, disk)

## Security Features

1. **URL Validation**: Validasi semua URLs sebelum loading
2. **Certificate Pinning**: Pin SSL certificates untuk critical domains
3. **Secure Storage**: Encrypted storage untuk sensitive data
4. **Permission Management**: Minimal permission requirements
5. **Code Obfuscation**: ProGuard untuk release builds

## Kontribusi

Kontribusi sangat diterima! Silakan membuat pull request dengan perbaikan atau fitur baru.

## License

Proyek ini dilisensikan di bawah MIT License - lihat file [LICENSE](LICENSE) untuk detail.

## Author

**RiSETBrowser** - 2026
- Dikembangkan dengan Java 8 untuk Android 5.0+
- Modern UI/UX untuk pengalaman browsing terbaik

## Support & Contact

Untuk pertanyaan, bug reports, atau saran fitur, silakan buka issue di GitHub.

---

**Made with ❤️ for modern Android browsing**
