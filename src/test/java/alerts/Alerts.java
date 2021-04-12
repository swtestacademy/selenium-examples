package alerts;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class Alerts {
    private WebDriver driver;

    @BeforeAll
    public void setupTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void T01_AlertTest() {
        driver.navigate().to("http://www.w3schools.com/js/tryit.asp?filename=tryjs_alert");
        //Switch to iframeResult iframe because all elements located in this iframe
        driver.switchTo().frame("iframeResult");

        //Find "Try it" button
        WebElement alertButton = driver.findElement(By.cssSelector("html>body>button"));

        //Click alert button ("Try it" button)
        alertButton.click();

        //Alert Message (Expected Text)
        String expectedAlertMessage = "I am an alert box!";

        //Captured Alert Text (Actual Text)
        String actualAlertMessage = driver.switchTo().alert().getText();

        //Assertion
        Assertions.assertEquals(expectedAlertMessage, actualAlertMessage);

        //Accept the alert (Click OK)
        driver.switchTo().alert().accept();
    }

    @Test
    public void T02_ConfirmTest() {
        driver.navigate().to("http://www.w3schools.com/js/tryit.asp?filename=tryjs_confirm");
        //Switch to iframeResult iframe because all elements located in this iframe
        driver.switchTo().frame("iframeResult");

        //Find "Try it" button
        WebElement confirmButton = driver.findElement(By.cssSelector("html>body>button"));

        //Actual Text Element
        WebElement actualConfirmMessage = driver.findElement(By.cssSelector("#demo"));

        //******************************
        // Accept Test (Test Scenario-1)
        //******************************
        //Click confirm button ("Try it" button)
        confirmButton.click();

        //Accept the alert (Click Ok button)
        driver.switchTo().alert().accept();

        //Assertion
        Assertions.assertEquals("You pressed OK!", actualConfirmMessage.getText());

        //******************************
        // Dismiss Test (Test Scenario-2)
        //******************************
        //Click confirm button ("Try it" button)
        confirmButton.click();

        //Accept the alert (Click Cancel)
        driver.switchTo().alert().dismiss();

        //Assertion
        Assertions.assertEquals("You pressed Cancel!", actualConfirmMessage.getText());
    }

    @Test
    public void T03_PromptTest() {
        driver.navigate().to("http://www.w3schools.com/js/tryit.asp?filename=tryjs_prompt");
        //Switch to iframeResult iframe because all elements located in this iframe
        //It will be described in next topics
        driver.switchTo().frame("iframeResult");

        //Find "Try it" button
        WebElement promptButton = driver.findElement(By.cssSelector("html>body>button"));

        //Actual Message
        WebElement actualPromptMessage = driver.findElement(By.cssSelector("#demo"));

        //Click confirm button ("Try it" button)
        promptButton.click();

        //Send "SW Test Academy" to Alert's text box
        driver.switchTo().alert().sendKeys("SW Test Academy");

        //Accept the alert (Click Ok button)
        driver.switchTo().alert().accept();

        //Assertion
        Assertions.assertEquals("Hello SW Test Academy! How are you today?", actualPromptMessage.getText());
    }

    @AfterAll
    public void quitDriver() {
        driver.quit();
    }
}
