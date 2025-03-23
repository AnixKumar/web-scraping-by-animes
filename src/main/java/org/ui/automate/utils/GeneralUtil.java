package org.ui.automate.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class GeneralUtil {
    private static final Logger logger = LoggerFactory.getLogger(GeneralUtil.class);

    public static void analyzeHeaders(Map<String, String> articleTitles) {
        logger.info("***** Analyzing headers in Thread ID: " + Thread.currentThread().getId() + " *****");
        Map<String, Integer> wordCount = new HashMap<>();
        for (String title : articleTitles.keySet()) {
            for (String word : title.split(" ")) {
                word = word.toLowerCase().replaceAll("[^a-z]", "");
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > 2) {
                logger.info("[Thread ID: " + Thread.currentThread().getId() + "] Repeated Word: " + entry.getKey() + " - Count: " + entry.getValue());
            }
        }
    }
}
