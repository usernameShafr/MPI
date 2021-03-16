package com.ifmo.hatchery.ui;

import com.ifmo.hatchery.model.system.Caste;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import static com.ifmo.hatchery.CommonConfiguration.BASE_URL;
import static com.ifmo.hatchery.CommonConfiguration.PATH_TO_CHROMEDRIVER;
import static com.ifmo.hatchery.init.DataInit.CUSTOMER_DEFAULT_USERNAME;
import static com.ifmo.hatchery.init.DataInit.DEFAULT_PASSWORD;
import static com.ifmo.hatchery.init.DataInit.DISPATCHER_DEFAULT_USERNAME;
import static com.ifmo.hatchery.init.DataInit.DONOR_DEFAULT_USERNAME;
import static com.ifmo.hatchery.init.DataInit.DONOR_FEMALE_USERNAME;
import static com.ifmo.hatchery.ui.SeleniumDispatcherTest.addAmount;
import static com.ifmo.hatchery.ui.SeleniumDispatcherTest.addSkills;
import static com.ifmo.hatchery.ui.SeleniumDispatcherTest.chooseCaste;
import static com.ifmo.hatchery.ui.SeleniumDispatcherTest.fertilizationTask;
import static com.ifmo.hatchery.ui.SeleniumTestLogin.logoutCommon;
import static com.ifmo.hatchery.ui.SeleniumTestLogin.loginCommon;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BusinessCaseTest {
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
    public void businessCaseTest() throws InterruptedException {
        donorPart(driver, false);
        donorPart(driver, true);
        orderPart(driver);
        dispatcherPart(driver);
    }

    private void donorPart(WebDriver driver, boolean male) {
        driver.get(BASE_URL + "/donor");
        loginCommon(driver, male ? DONOR_DEFAULT_USERNAME : DONOR_FEMALE_USERNAME, DEFAULT_PASSWORD);
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Give a biaomaterial')]")).isDisplayed(), "Login to '/donor' succeeds");
        //Create
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Give a biaomaterial')]")).isDisplayed(), "Biomaterial added");
        logoutCommon(driver);
    }

    private void orderPart(WebDriver driver) {
        driver.get(BASE_URL + "/order");
        loginCommon(driver, CUSTOMER_DEFAULT_USERNAME, DEFAULT_PASSWORD);
        assertTrue(driver.findElement(By.xpath("//h1[text()='New order']")).isDisplayed(), "Login to '/order' succeeds");

        int sizeBefore = driver.findElements(By.xpath("//table/tbody/tr")).size();
        //Select caste
        Select select = new Select(driver.findElement(By.xpath("//select[@id='caste']")));
        select.selectByVisibleText(Caste.ALPHA.toString());
        //Select Skills
        driver.findElement(By.xpath("//input[@auto-id='Stamina']")).click();
        //Create
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        int sizeAfter = driver.findElements(By.xpath("//table/tbody/tr")).size();

        assertTrue(sizeAfter > sizeBefore);
        logoutCommon(driver);
    }

    private void dispatcherPart(WebDriver driver) {
        driver.get(BASE_URL + "/dashboard");
        loginCommon(driver, DISPATCHER_DEFAULT_USERNAME, DEFAULT_PASSWORD);
        assertTrue(driver.findElement(By.xpath("//h1[text()='Dashboard']")).isDisplayed(), "Login to dashboard succeeds");

        int completedBefore = Integer.parseInt(driver.findElement(By.xpath("//*[@id='sum_store']")).getText());
        //Fertilization service
        fertilizationTask(driver);

        //Caste service
        chooseCaste(driver);

        //Bokanovskiy service
        addAmount(driver);

        //Skills service
        addSkills(driver);

        //Storage service
        int completedAfter = Integer.parseInt(driver.findElement(By.xpath("//*[@id='sum_store']")).getText());
        assertTrue(completedBefore < completedAfter, "Task finish flow");
        logoutCommon(driver);
    }
}
