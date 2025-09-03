package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;


public class UITest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    public void openExampleDotCom() {
        driver.get("http://192.168.30.38/");
        // Ожидание кликабельности кнопки с id="myButton" и клик по ней
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div/div[1]/div/button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("username"))).sendKeys("gpay-admins");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("password"))).sendKeys("admin");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("kc-login"))).click();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
