package org.ui.automate.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class LandingPage {
    private static final Logger logger = LoggerFactory.getLogger(LandingPage.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private By languageOfPage = By.tagName("html");
    private By acceptButton = By.xpath("//div[@id=\"buttons\"]//button[@id=\"didomi-notice-agree-button\"]");
    private By articleLink = By.xpath("//main[@id=\"main-content\"]//a[contains(text(),'Opinión')]");
    private By listOfArticles = By.xpath("//main[@id=\"main-content\"]//a[contains(text(),'Opinión')]/ancestor::section/div//article");


    public LandingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Explicit wait timeout

    }

    public String languagePage(){
        String lan = driver.findElement(languageOfPage).getAttribute("lang");
        return lan;
    }

    public void handlePopupIfExists() {
        try {
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(acceptButton));
            popup.click();
            logger.info("[Thread ID: " + Thread.currentThread().getId() + "]+++ Popup appeared and accepted. +++");
        } catch (TimeoutException e) {
            logger.info("[Thread ID: " + Thread.currentThread().getId() + "]+++ No popup appeared, continuing execution... +++");
        }
    }

    public WebElement getOpinionPathAndScroll() {
        handlePopupIfExists(); // Ensure popup is handled first

        // Wait until the article links are visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(articleLink));

        // Scroll to the element
        WebElement element = driver.findElement(articleLink);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);

        return driver.findElement(articleLink);
    }

    public List<WebElement> getListOfArticles() {
        return driver.findElements(listOfArticles);
    }


    public String getTitleOfArticle(WebElement element){
        String title = element.findElement(By.tagName("h2")).getText();
        return title;
    }

    public String getContentOfArticle(WebElement element){
        try {
            String content = element.findElement(By.tagName("p")).getText();
            return content;
        }catch (NoSuchElementException e){
            logger.warn("[Thread ID: " + Thread.currentThread().getId() + "]!!! No context found for above Title !!!");
            return "";
        }
    }
}
