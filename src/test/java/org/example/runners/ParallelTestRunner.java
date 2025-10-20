package org.example.runners;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.*;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

public class ParallelTestRunner {

    public static void main(String[] args) throws Exception {
        List<Class<?>> testClasses = findAllTestClasses("org.example");

        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        System.out.println("Running tests using " + threads + " threads");
        System.out.println("🧩 Found " + testClasses.size() + " test classes");

        for (Class<?> testClass : testClasses) {
            executor.submit(() -> runWithJUnit(testClass));
        }

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.MINUTES);

        System.out.println("All tests finished!");
    }

    private static void runWithJUnit(Class<?> testClass) {
        try {
            System.out.println("\n🔹 Running " + testClass.getSimpleName() +
                    " in thread " + Thread.currentThread().getName());

            int methodThreads = Math.max(2, Runtime.getRuntime().availableProcessors() / 2);
            ExecutorService methodExecutor = Executors.newFixedThreadPool(methodThreads);

            Arrays.stream(testClass.getDeclaredMethods())
                    .filter(m -> m.isAnnotationPresent(org.junit.jupiter.api.Test.class))
                    .forEach(method -> methodExecutor.submit(() -> {
                        try {
                            System.out.println("🧪 Running test method: " + method.getName() +
                                    " [Thread: " + Thread.currentThread().getName() + "]");

                            LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                                    .selectors(DiscoverySelectors.selectMethod(testClass, method.getName()))
                                    .build();

                            Launcher launcher = LauncherFactory.create();
                            TestExecutionListener listener = new SummaryGeneratingListener();
                            launcher.registerTestExecutionListeners(listener);
                            launcher.execute(request);

                        } catch (Exception e) {
                            System.err.println("❌ Error in method " + method.getName() + ": " + e);
                        }
                    }));

            methodExecutor.shutdown();
            methodExecutor.awaitTermination(30, TimeUnit.MINUTES);

        } catch (Exception e) {
            System.err.println("❌ Error in class " + testClass.getSimpleName() + ": " + e);
        }
    }

    private static List<Class<?>> findAllTestClasses(String packageName) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');
        URL root = Thread.currentThread().getContextClassLoader().getResource(path);

        if (root == null) {
            throw new IllegalArgumentException("Cannot find package: " + packageName);
        }

        File directory = new File(root.toURI());
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                classes.addAll(findAllTestClasses(packageName + "." + file.getName()));
            } else if (file.getName().endsWith("Test.class")) {
                String className = packageName + '.' + file.getName().replace(".class", "");
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }
}
