package wait;

import java.time.Duration;
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
public class ImplicitWaitExample {
    private        WebDriver driver;
    private static String    url = "http://the-internet.herokuapp.com/dynamic_loading/2";

    //Setup Driver
    @BeforeAll
    public void setupTest() {
        driver = new ChromeDriver();
        driver.navigate().to(url);
        driver.manage().window().maximize();
    }

    @Test
    public void ImplicitWaitTest() {
        // wait for 10 seconds implicitly
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //Get the selected date text before AJAX call
        WebElement startButton = driver.findElement(By.cssSelector("#start>button"));

        //Click the button
        startButton.click();

        //Find the result text
        WebElement resultText = driver.findElement(By.cssSelector("#finish>h4"));

        //Check the Expected and Actual Text
        Assertions.assertEquals("Hello World!", resultText.getText().trim());
    }

    //Close Driver
    @AfterAll
    public void quitDriver() {
        driver.quit();
    }
}
