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
import static com.ifmo.hatchery.init.DataInit.DONOR_DEFAULT_USERNAME;
import static com.ifmo.hatchery.ui.SeleniumTestLogin.loginCommon;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumDonorTest {
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
    public void testCreateOrder() {
        driver.get(BASE_URL + "/donor");
        loginCommon(driver, DONOR_DEFAULT_USERNAME, DEFAULT_PASSWORD);
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Give a biaomaterial')]")).isDisplayed(), "Login to '/donor' succeeds");
        //Create
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Give a biaomaterial')]")).isDisplayed(), "Biomaterial added");
    }
}
