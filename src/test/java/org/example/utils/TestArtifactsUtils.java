package org.example.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class TestArtifactsUtils {

    private static final Logger logger = LoggerFactory.getLogger(TestArtifactsUtils.class);

    //--------- LOGS SETTINGS----------

    public static boolean SAVE_SCREENSHOTS = true;
    public static boolean SAVE_HTML = false;
    public static boolean SAVE_LOGS = true;

    private static final int MAX_FILES = 3;

    //---------------------------------
    public static void saveFailureArtifacts(WebDriver driver, String methodName, String displayName, Throwable error) {
        if (SAVE_SCREENSHOTS) { saveScreenshot(driver, methodName); }
        else { logger.warn("ℹ️ Screenshot artifacts are disabled."); }
        if (SAVE_HTML) { savePageSource(driver, methodName); }
        else { logger.warn("ℹ️ HTML artifacts are disabled."); }
        if (SAVE_LOGS) { saveErrorLog(methodName, error); }
        else { logger.warn("ℹ️ Logs artifacts are disabled."); }
    }

    // -------------------------
    // Screenshot
    // -------------------------
    private static void saveScreenshot(WebDriver driver, String methodName) {
        if (driver == null) {
            logger.warn("⚠️ Could not take screenshot: driver == null");
            return;
        }
        if (!(driver instanceof TakesScreenshot)) {
            logger.warn("⚠️ Driver does not support screenshots (not TakesScreenshot)");
            return;
        }

        File dir = new File("target/screenshots");
        ensureDir(dir);

        String timestamp = new SimpleDateFormat("dd_MM_HH-mm-ss-SSS").format(new Date());
        String fileName = sanitize(methodName) + "-" + timestamp + ".png";
        File dest = new File(dir, fileName);

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, dest);
            logger.info("📸 Screenshot saved: {}", dest.getAbsolutePath());
            rotateOldFiles(dir);
        } catch (IOException e) {
            logger.warn("⚠️ Could not save screenshot: {}", e.toString());
        } catch (Exception e) {
            logger.warn("⚠️ Screenshot capture failed: {}", e.toString());
        }
    }

    // -------------------------
    // HTML (page source)
    // -------------------------
    private static void savePageSource(WebDriver driver, String methodName) {
        if (driver == null) {
            logger.warn("⚠️ Could not save HTML: driver == null");
            return;
        }

        File dir = new File("target/html");
        ensureDir(dir);

        String timestamp = new SimpleDateFormat("dd_MM_HH-mm-ss-SSS").format(new Date());
        String fileName = sanitize(methodName) + "-" + timestamp + ".html";
        File dest = new File(dir, fileName);

        try {
            String pageSource;
            try {
                pageSource = (String) ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("return document.documentElement.outerHTML;");
            } catch (Exception jsEx) {
                pageSource = driver.getPageSource();
            }
            pageSource = pageSource.replaceAll("><", ">\n<");
            FileUtils.writeStringToFile(dest, pageSource, "UTF-8");
            logger.info("🌐 Page source saved: {}", dest.getAbsolutePath());
            rotateOldFiles(dir);
        } catch (IOException e) {
            logger.warn("⚠️ Could not save HTML: {}", e.toString());
        } catch (Exception e) {
            logger.warn("⚠️ Failed to capture HTML: {}", e.toString());
        }
    }

    // -------------------------
    // Error log
    // -------------------------
    private static void saveErrorLog(String methodName, Throwable error) {
        File dir = new File("target/logs");
        ensureDir(dir);

        String timestamp = new SimpleDateFormat("dd_MM_HH-mm-ss-SSS").format(new Date());
        String fileName = sanitize(methodName) + "-" + timestamp + ".log";
        File dest = new File(dir, fileName);

        try (FileWriter writer = new FileWriter(dest)) {
            writer.write("Test method: " + methodName + "\n");
            writer.write("Time: " + new Date() + "\n\n");
            if (error != null) {
                writer.write("Error message: " + error.getMessage() + "\n\n");
                writer.write("Stack trace:\n");
                for (StackTraceElement el : error.getStackTrace()) {
                    writer.write(el.toString() + "\n");
                }
            } else {
                writer.write("No Throwable provided.\n");
            }
            writer.flush();
            logger.info("📝 Error log saved: {}", dest.getAbsolutePath());
            rotateOldFiles(dir);
        } catch (IOException e) {
            logger.warn("⚠️ Could not save error log: {}", e.toString());
        }
    }

    // -------------------------
    // Helpers
    // -------------------------
    private static void ensureDir(File dir) {
        if (!dir.exists() && !dir.mkdirs()) {
            logger.warn("⚠️ Failed to create directory: {}", dir.getAbsolutePath());
        }
    }

    private static void rotateOldFiles(File dir) {
        if (!dir.exists()) return;

        File[] files = dir.listFiles();
        if (files == null || files.length <= MAX_FILES) return;

        Arrays.sort(files, Comparator.comparingLong(File::lastModified));

        int filesToDelete = files.length - MAX_FILES;
        for (int i = 0; i < filesToDelete; i++) {
            File file = files[i];
            try {
                if (file.delete()) {
                    logger.info("🗑️ Deleted old artifact: {}", file.getName());
                } else {
                    logger.warn("⚠️ Failed to delete old artifact: {}", file.getName());
                }
            } catch (Exception e) {
                logger.error("❌ Error deleting file {}: {}", file.getName(), e.getMessage());
            }
        }
    }

    private static String sanitize(String s) {
        if (s == null || s.trim().isEmpty()) return "unknown";
        String out = s.replaceAll("[^A-Za-z0-9._-]", "_");
        out = out.replaceAll("_+", "_");
        if (out.length() > 100) out = out.substring(0, 100);
        return out;
    }
}
