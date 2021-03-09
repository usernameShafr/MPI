package com.ifmo.hatchery.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.ifmo.hatchery.CommonConfiguration.BASE_URL;
import static com.ifmo.hatchery.CommonConfiguration.PATH_TO_CHROMEDRIVER;
import static com.ifmo.hatchery.init.DataInit.DEFAULT_PASSWORD;
import static com.ifmo.hatchery.init.DataInit.DISPATCHER_DEFAULT_USERNAME;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumTestLogin {
    private WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", PATH_TO_CHROMEDRIVER);
    }

    @BeforeEach
    public void setDriver() {
        driver = new ChromeDriver();
    }
    @AfterEach
    public void releaseDriver() {
        if(driver != null) {
            driver.close();
        }
    }

    @Test
    public void testLogin() {
        driver.get(BASE_URL + "/dashboard");
        loginCommon(driver, DISPATCHER_DEFAULT_USERNAME, DEFAULT_PASSWORD);

        assertTrue(driver.findElement(By.xpath("//h1[text()='Dashboard']")).isDisplayed(), "Login to dashboard succeeds");
    }

    public static void loginCommon(WebDriver driver, String usermame, String password) {
        assertTrue(driver.findElement(By.xpath("//input[@id='username']")).isDisplayed(), "Login form is visible");

        driver.findElement(By.xpath("//input[@id='username']")).sendKeys(usermame);
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
        driver.findElement(By.xpath("//input[@type='submit']")).click();
    }
}
