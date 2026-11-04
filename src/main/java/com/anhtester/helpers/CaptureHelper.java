package com.anhtester.helpers;

import com.anhtester.constants.ConfigData;
import com.anhtester.drivers.DriverManager;
import com.anhtester.utils.LogUtils;
import org.apache.commons.io.FileUtils;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

/** BT12: Chụp ảnh màn hình và record video */
public class CaptureHelper extends ScreenRecorder {
    private static ScreenRecorder screenRecorder;
    private final String name;

    private CaptureHelper(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat,
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
        return new File(movieFolder, name + "_" + SystemHelper.getDateTimeNow() + "." + Registry.getInstance().getExtension(fileFormat));
    }

    /** Chụp ảnh màn hình, trả về đường dẫn file */
    public static String takeScreenshot(String screenName) {
        try {
            String folder = SystemHelper.getCurrentDir() + ConfigData.SCREENSHOT_PATH();
            SystemHelper.createFolder(folder);
            File source = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
            String path = folder + File.separator + SystemHelper.makeSlug(screenName) + "_" + SystemHelper.getDateTimeNow() + ".png";
            FileUtils.copyFile(source, new File(path));
            LogUtils.info("Screenshot: " + path);
            return path;
        } catch (Exception e) {
            LogUtils.error("Không chụp được màn hình: " + e.getMessage());
            return null;
        }
    }

    public static byte[] takeScreenshotBytes() {
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    public static void startRecord(String methodName) {
        if (GraphicsEnvironment.isHeadless()) {
            LogUtils.warn("Môi trường headless => bỏ qua record video");
            return;
        }
        try {
            File file = new File(SystemHelper.getCurrentDir() + ConfigData.VIDEO_PATH());
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle captureSize = new Rectangle(0, 0, screenSize.width, screenSize.height);
            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
            screenRecorder = new CaptureHelper(gc, captureSize,
                    new Format(MediaTypeKey, FormatKeys.MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, FormatKeys.MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                            QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, FormatKeys.MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                    null, file, methodName);
            screenRecorder.start();
        } catch (Exception e) {
            LogUtils.error("Không record được video: " + e.getMessage());
        }
    }

    public static void stopRecord() {
        try {
            if (screenRecorder != null) {
                screenRecorder.stop();
            }
        } catch (Exception e) {
            LogUtils.error("Không dừng record được: " + e.getMessage());
        }
    }
}
