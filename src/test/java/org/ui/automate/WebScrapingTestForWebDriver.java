package org.ui.automate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.ui.automate.base.BaseTestForRestAssured;
import org.ui.automate.base.BaseTestForWebDriver;
import org.ui.automate.pages.LandingPage;
import org.ui.automate.utils.GeneralUtil;
import org.ui.automate.utils.ImageUtil;
import org.ui.automate.utils.LoggerUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebScrapingTestForWebDriver extends BaseTestForWebDriver {
    private static final Logger logger = LoggerFactory.getLogger(WebScrapingTestForWebDriver.class);
    private BaseTestForRestAssured apiBase = new BaseTestForRestAssured();  // Using RestAssuredBase via composition

    @BeforeClass
    public void setUp() throws IOException {
        setup();
        apiBase.setupApi();

        if (getDriver() instanceof RemoteWebDriver) {
            String sessionId = ((RemoteWebDriver) getDriver()).getSessionId().toString();
            LoggerUtil.configureLogging();
            logger.info("***** Running Test. Thread ID: " + Thread.currentThread().getId() +
                    ", Session ID: " + sessionId + " *****");
        } else {
            LoggerUtil.configureLogging();
            logger.info("***** Running Test Locally. Thread ID: " + Thread.currentThread().getId() + " *****");
        }
    }

    @Test
    public void scrapeAndTranslateArticles() throws IOException {
        getDriver().get(config.getProperty("test.base_url"));
        LandingPage landingPage = new LandingPage(getDriver());

        String language = landingPage.languagePage();
        if(language.equals(config.getProperty("test.website_language"))){
            logger.info("[Thread ID: " + Thread.currentThread().getId() + "] ***** Verified that Page is displayed in Spanish Language *****");
        }

        WebElement article = landingPage.getOpinionPathAndScroll();
        List<WebElement> articleList = landingPage.getListOfArticles();

        int count = 0;
        Map<String, String> translatedTitles = new HashMap<>();

        for (WebElement ele : articleList) {
            if (count >= 5) break;
            String title = landingPage.getTitleOfArticle(ele);
            String content = landingPage.getContentOfArticle(ele);
            logger.info("[Thread ID: " + Thread.currentThread().getId() + "] @@@ Title " + (count + 1) + ": " + title);
            logger.info("[Thread ID: " + Thread.currentThread().getId() + "] ### Content " + (count + 1) + ": " + content);
            String imgString = ImageUtil.checkIfImagePresentThenFetchLocation(ele); //TODO: check this since not working
            if(!imgString.isEmpty()){
                ImageUtil.downloadImage(imgString);
            }
            String translatedTitle = apiBase.callApiForTranslation(title);
            logger.info("[Thread ID: " + Thread.currentThread().getId() + "] @@@ Translated Title " + (count + 1) + ": " + translatedTitle);
            translatedTitles.put(translatedTitle, title);

            count++;
        }
        GeneralUtil.analyzeHeaders(translatedTitles);
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
