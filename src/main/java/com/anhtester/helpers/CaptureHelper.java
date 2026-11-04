package com.anhtester.helpers;

import com.anhtester.drivers.DriverManager;
import com.anhtester.utils.LogUtils;
import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaType;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

/**
 * BÀI TẬP 12: Chụp ảnh màn hình và record video.
 */
public class CaptureHelper extends ScreenRecorder {
    private static ScreenRecorder screenRecorder;
    private final String name;

    public CaptureHelper(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat,
                         Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }

    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }
        return new File(movieFolder, name + "_" + SystemHelper.getDateTimeNow() + "."
                + Registry.getInstance().getExtension(fileFormat));
    }

    //================ Screenshot ================

    /** Chụp ảnh màn hình lưu vào thư mục screenshot.path */
    public static String takeScreenshot(String screenshotName) {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            return null;
        }
        try {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String folder = SystemHelper.getCurrentDir() + PropertiesHelper.getValue("screenshot.path");
            SystemHelper.createFolder(folder);
            File dest = new File(folder, SystemHelper.makeSlug(screenshotName) + "_" + SystemHelper.getDateTimeNow() + ".png");
            Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            LogUtils.info("Screenshot: " + dest.getAbsolutePath());
            return dest.getAbsolutePath();
        } catch (Exception e) {
            LogUtils.error("Lỗi chụp màn hình: " + e.getMessage());
            return null;
        }
    }

    /** Chụp ảnh mỗi trang (bật/tắt bằng screenshot.all.pages trong config) */
    public static void takeScreenshotPage(String pageName) {
        if (PropertiesHelper.getBoolean("screenshot.all.pages")) {
            takeScreenshot("Page_" + pageName);
        }
    }

    //================ Record video ================

    public static void startRecord(String methodName) {
        if (!PropertiesHelper.getBoolean("video.record") || GraphicsEnvironment.isHeadless()) {
            return;
        }
        try {
            File folder = new File(SystemHelper.getCurrentDir() + PropertiesHelper.getValue("video.path"));
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle captureSize = new Rectangle(0, 0, screenSize.width, screenSize.height);
            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice().getDefaultConfiguration();

            screenRecorder = new CaptureHelper(gc, captureSize,
                    new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24,
                            FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                    null, folder, SystemHelper.makeSlug(methodName));
            screenRecorder.start();
            LogUtils.info("Bắt đầu record video: " + methodName);
        } catch (Exception e) {
            LogUtils.error("Lỗi record video: " + e.getMessage());
        }
    }

    public static void stopRecord() {
        if (screenRecorder == null) {
            return;
        }
        try {
            screenRecorder.stop();
            LogUtils.info("Dừng record video");
        } catch (IOException e) {
            LogUtils.error("Lỗi dừng record video: " + e.getMessage());
        } finally {
            screenRecorder = null;
        }
    }
}
