# Android apps (AOS)

Đặt file `.apk` cần test vào thư mục này.

Mặc định framework dùng app demo **ApiDemos**:

```bash
curl -L -o src/test/resources/apps/ApiDemos-debug.apk https://github.com/appium/android-apidemos/releases/download/v6.0.2/ApiDemos-debug.apk
```

Muốn test app khác: sửa `android.app`, `android.app.package`, `android.app.activity`
trong `src/test/resources/configs/config.properties`.
