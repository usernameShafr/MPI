package com.ifmo.hatchery.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
//import selenium.webdriver  ActionChains;
import static com.ifmo.hatchery.CommonConfiguration.BASE_URL;
import static com.ifmo.hatchery.CommonConfiguration.PATH_TO_CHROMEDRIVER;
import static com.ifmo.hatchery.init.DataInit.*;
import static com.ifmo.hatchery.ui.SeleniumTestLogin.loginCommon;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.By.id;

public class SeleniumDispatcherTest {
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
    public void testCreateOrder() throws InterruptedException {
        driver.get(BASE_URL + "/dashboard");
        loginCommon(driver, DISPATCHER_DEFAULT_USERNAME, DEFAULT_PASSWORD);
        assertTrue(driver.findElement(By.xpath("//h1[text()='Dashboard']")).isDisplayed(), "Login to dashboard succeeds");
        //Thread.sleep(2000);
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(id("fertilization_task"))).perform();
        action.click().perform();
        Thread.sleep(2000);
        //driver.findElement(id("fertilization_task")).click();
        //driver.findElement(By.xpath("//input[@type='submit']")).click();
        //assertTrue(driver.findElement(By.xpath("//h1[text()='Dashboard']")).isDisplayed(), "Fertilization task is completed");

    }
        //Create
        //driver.findElement(By.xpath("//input[@type='submit']")).click();
        //assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Give a biaomaterial')]")).isDisplayed(), "Biomaterial added");


}
