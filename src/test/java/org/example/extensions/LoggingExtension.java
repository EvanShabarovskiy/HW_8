package org.example.extensions;

import org.junit.jupiter.api.extension.*;

public class LoggingExtension implements BeforeEachCallback, AfterTestExecutionCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        System.out.println("ℹ️ Starting test: " + context.getDisplayName());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        boolean failed = context.getExecutionException().isPresent();
        if (failed) {
            System.err.println("❌ Test failed: " + context.getDisplayName());
        } else {
            System.out.println("✅ Test passed: " + context.getDisplayName());
        }
    }
}
