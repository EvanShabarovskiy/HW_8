package org.example.extensions;

import org.example.utils.DriverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.utils.TestArtifactsUtils;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.WebDriver;

public class LoggingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private static final Logger logger = LoggerFactory.getLogger(LoggingExtension.class);
    private long startTime;

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        startTime = System.currentTimeMillis();
        String testName = context.getDisplayName();
        String className = context.getRequiredTestClass().getSimpleName();
        logger.info("🚀 Starting test: {}.{}  [Thread ID: {}]",
                className, testName, Thread.currentThread().getId());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        long duration = System.currentTimeMillis() - startTime;
        String className = context.getRequiredTestClass().getSimpleName();
        String methodName = context.getRequiredTestMethod().getName();

        if (context.getExecutionException().isPresent()) {
            Throwable error = context.getExecutionException().get();
            logger.error("❌ Test FAILED: {}.{} after {} ms\nReason: {}",
                    className, methodName, duration, error.getMessage(), error);

            WebDriver driver = DriverFactory.getDriver();
            TestArtifactsUtils.saveFailureArtifacts(driver, methodName, context.getDisplayName(), error);

        } else {
            logger.info("✅ Test PASSED: {}.{} ({} ms)", className, methodName, duration);
        }
    }
}

