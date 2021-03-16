package com.ifmo.hatchery.ui;

import com.ifmo.hatchery.model.system.Caste;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import java.util.stream.Collectors;

import static com.ifmo.hatchery.CommonConfiguration.BASE_URL;
import static com.ifmo.hatchery.CommonConfiguration.PATH_TO_CHROMEDRIVER;
import static com.ifmo.hatchery.init.DataInit.DEFAULT_PASSWORD;
import static com.ifmo.hatchery.init.DataInit.DISPATCHER_DEFAULT_USERNAME;
import static com.ifmo.hatchery.ui.SeleniumTestLogin.loginCommon;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void testProcessOrder() throws InterruptedException {
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
    }

    public static void fertilizationTask(WebDriver driver) {
        driver.findElement(By.xpath("//a[@id='fertilization_task']")).click();
        Select select = new Select(driver.findElement(By.xpath("//select[@id='maleBioMaterial']")));
        select.selectByIndex(0);
        select = new Select(driver.findElement(By.xpath("//select[@id='femaleBioMaterial']")));
        select.selectByIndex(0);
        driver.findElement(By.xpath("//input[@type='submit']")).click();
    }

    public static void chooseCaste(WebDriver driver) {
        driver.findElement(By.xpath("//a[@id='choose_caste_task']")).click();
        Select select = new Select(driver.findElement(By.xpath("//select[@id='caste']")));
        select.selectByValue(select.getFirstSelectedOption().getText());
        driver.findElement(By.xpath("//input[@type='submit']")).click();
    }

    public static void addAmount(WebDriver driver) {
        driver.findElement(By.xpath("//a[@id='bokanovskiy_task']")).click();
        Caste caste = Caste.valueOf(driver.findElement(By.xpath("//*[@for='caste']/../input")).getAttribute("value"));
        driver.findElement(By.xpath("//input[@id='amount']")).sendKeys("" + (caste.getMaxAmount() + caste.getMinAmount())/2);
        driver.findElement(By.xpath("//input[@type='submit']")).click();
    }

    public static void addSkills(WebDriver driver) {
        driver.findElement(By.xpath("//a[@id='add_skills_task']")).click();
        List<String> selectedCheckboxOptions = driver.findElements(By.xpath("//input[@type='checkbox' and @checked='checked']"))
                .stream()
                .map(e -> e.getAttribute("value"))
                .collect(Collectors.toList());

        for(String checkboxValue : selectedCheckboxOptions) {
            WebElement element = driver.findElement(By.xpath(String.format("//input[@type='checkbox' and @value='%s']", checkboxValue)));
            if(!element.isSelected()) {
                element.click();
            }
        }
        driver.findElement(By.xpath("//input[@type='submit']")).click();
    }
}
