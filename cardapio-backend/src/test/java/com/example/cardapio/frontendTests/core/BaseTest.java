package com.example.cardapio.frontendTests.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = configurarChrome();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if(driver != null) {
            driver.quit();
        }
    }

    private ChromeOptions configurarChrome() {
        ChromeOptions options = new ChromeOptions();

//        options.addArguments("--headless=new");
        options.addArguments("--incognito");
        options.addArguments("--window-size=1280,800");
        options.addArguments("--disable-notifications");
        return options;
    }
}
