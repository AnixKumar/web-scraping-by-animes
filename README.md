# Web Scraping Automation Framework

This project is an automation framework designed for **web scraping and content translation** using **Selenium WebDriver, Rest Assured, and TestNG**. The framework supports **local execution** and **BrowserStack** for cloud-based testing.

## Features
- **Automated Web Scraping**: Extracts articles from a webpage.
- **Content Translation API**: Uses an API to translate scraped content.
- **Logging with Logback**: Generates logs for debugging.
- **Image Processing Utility**: Fetches and downloads images from web pages.
- **REST API Testing**: Uses Rest Assured for API validation.

## Prerequisites
- Install Java and Maven.
- Download the required dependencies.

## Run sample build
- To run it locally, set use_browserstack flag as false in config.properties file and then run the WebScrapingTestForWebDriver test class.
- To run it on browserstack parallely on 5 different platform across desktop and mobile(configs can be set in browserstack.yml), set use_browserstack flag as false in config.properties file and then run the WebScrapingTestForWebDriver test class.
- Once the run is completed, the logs can be found in /logs directory under root level, named as test-log.log
---