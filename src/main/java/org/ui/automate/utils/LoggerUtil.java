package org.ui.automate.utils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoggerUtil {
    private static final AtomicBoolean isConfigured = new AtomicBoolean(false);
    // Common method to set up logging
    public static void configureLogging() {
        if (isConfigured.getAndSet(true)) {
            return;
        }
        String logFilePath = "logs/test-log.log";
        File logFile = new File(logFilePath);
        if (logFile.exists()) {
            logFile.delete();  // Remove old logs before writing new ones
        }

        System.setProperty("LOG_FILE", logFilePath);

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset();

        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);

        try {
            configurator.doConfigure("src/main/resources/logback.xml"); // Reconfigure logback
        } catch (Exception e) {
            e.printStackTrace();
        }

        StatusPrinter.printInCaseOfErrorsOrWarnings(context);
    }
}
