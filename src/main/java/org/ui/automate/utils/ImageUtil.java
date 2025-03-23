package org.ui.automate.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUtil {
    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    public static String checkIfImagePresentThenFetchLocation(WebElement element){
        try{
            String imgLocation = element.findElement(By.tagName("src")).getText();
            return imgLocation;
        }
        catch (NoSuchElementException e){
            logger.warn("[Thread ID: " + Thread.currentThread().getId() + "] No image found in the above element.");
            return ""; // Return empty string if no image is found
        }
    }

    public static void downloadImage(String imageUrl) {
        final String RUN_DIR = "test-runs/" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        try {
            // Ensure the run directory exists
            Files.createDirectories(Path.of(RUN_DIR));

            // Extract filename from URL or use default
            String fileName = "downloaded_image.png";
            Path savePath = Path.of(RUN_DIR, fileName);
            Files.copy(new URL(imageUrl).openStream(), savePath, StandardCopyOption.REPLACE_EXISTING);
            logger.info("[Thread ID: " + Thread.currentThread().getId() + "] Image downloaded successfully: " + savePath);
        } catch (IOException e) {
            logger.warn("[Thread ID: " + Thread.currentThread().getId() + "] Error downloading image: " + e.getMessage());
        }
    }
}
