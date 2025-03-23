package org.ui.automate.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class BaseTestForWebDriver {
    private static final ThreadLocal<Logger> threadLogger = ThreadLocal.withInitial(() -> {
        String threadName = Thread.currentThread().getName();
        return LoggerFactory.getLogger("TestLog-" + threadName);
    });
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<String> sessionId = new ThreadLocal<>();
    public Properties config;

    public void setup() throws IOException {
        config = new Properties();
        config.load(new FileInputStream("src/main/resources/config.properties"));

        boolean useBrowserStack = Boolean.parseBoolean(config.getProperty("use_browserstack"));

        if (useBrowserStack) {
            threadLogger.get().info("Running on browserstack machine...");
            WebDriverManager.chromedriver().setup();  // Automatically manages ChromeDriver
            driver.set(new ChromeDriver());
        } else {
            threadLogger.get().info("Running on local machine...");
            WebDriverManager.chromedriver().setup();  // Automatically manages ChromeDriver
            driver.set(new ChromeDriver());
            driver.get().manage().window().maximize();
        }
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    public void tearDown() {
        if (driver.get() != null) {
            threadLogger.get().info("Closing session: " + sessionId.get());
            driver.get().quit();
            threadLogger.get().info("Browser closed successfully.");
            driver.remove();
            sessionId.remove();
        }
    }
}
