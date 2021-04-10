package actions;

import java.io.File;
import java.time.Duration;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ActionsTests {
    private       WebDriver     driver;
    private       WebDriverWait wait;
    final private String        baseURL = "http://the-internet.herokuapp.com";

    @BeforeAll
    public void setupTest() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void T01_clickAnElement() {
        driver.navigate().to(baseURL + "/login");
        WebElement loginButton = driver.findElement(By.cssSelector(".fa.fa-2x.fa-sign-in"));
        loginButton.click();
    }

    @Test
    public void T02_sendKeysToAnElement() {
        driver.navigate().to(baseURL + "/login");
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("Hello SW Test Academy!");
    }

    @Test
    public void T03_checkBoxElement() {
        driver.navigate().to(baseURL + "/checkboxes");
        WebElement firstCheckBox = driver.findElement(By.cssSelector("input:nth-of-type(1)"));
        WebElement secondCheckBox = driver.findElement(By.cssSelector("input:nth-of-type(2)"));
        //Select first checkbox
        firstCheckBox.click();
        Assertions.assertTrue(firstCheckBox.isEnabled());
        Assertions.assertTrue(firstCheckBox.isSelected());
        //Deselect select checkbox
        secondCheckBox.click();
        Assertions.assertTrue(secondCheckBox.isEnabled());
        Assertions.assertFalse(secondCheckBox.isSelected());
    }

    @Test
    public void T04_dropDown() {
        driver.navigate().to(baseURL + "/dropdown");
        Select dropDown = new Select(driver.findElement(By.id("dropdown")));
        //Select By Index
        dropDown.selectByIndex(2);
        //Select By Value
        dropDown.selectByValue("1");
        //Select By Visible Text
        dropDown.selectByVisibleText("Option 2");
    }

    @Test
    public void T04_multiSelect() {
        //Navigate to w3schools.com
        driver.navigate().to("http://www.w3schools.com/tags/tryit.asp?filename=tryhtml_select_multiple");
        driver.manage().window().maximize();

        //Switch to iframeResult iframe because all elements located in this iframe
        driver.switchTo().frame("iframeResult");

        //Find multiple select and its options
        WebElement multiSelect = driver.findElement(By.cssSelector("select[name='cars']"));
        List<WebElement> multiSelectOptions = multiSelect.findElements(By.tagName("option"));

        //Find submit button
        WebElement submitButton = driver.findElement(By.cssSelector("input[type='submit']"));

        //Select Volvo (0) and Opel (2)
        multiSelectOptions.get(0).click();
        multiSelectOptions.get(2).click();

        //Click Submit Button
        submitButton.click();

        //Check the result
        WebElement resultSecondLine = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='w3-container w3-large w3-border']")));
        System.out.println("ResultText: " + resultSecondLine.getText());
        Assertions.assertTrue(resultSecondLine.getText().contains("volvo"), "volvo is not selected!");
        Assertions.assertTrue(resultSecondLine.getText().contains("opel"), "opel is not selected!");
    }

    @Test
    @SneakyThrows
    public void T05_deSelect() {
        driver.navigate().to("http://www.w3schools.com/tags/tryit.asp?filename=tryhtml_select_multiple");
        driver.switchTo().frame("iframeResult");

        //Find submit button
        WebElement submitButton = driver.findElement(By.cssSelector("input[type='submit']"));
        //Select Element
        WebElement multiSelectLocation = driver.findElement(By.name("cars"));
        //Declare Select
        Select selectCars = new Select(multiSelectLocation);

        //Select Volvo (0) and Opel (2)
        selectCars.selectByVisibleText("Opel");
        selectCars.selectByVisibleText("Audi");

        Thread.sleep(500);
        selectCars.deselectAll();
        Thread.sleep(500);

        selectCars.selectByVisibleText("Volvo");
        selectCars.selectByVisibleText("Audi");

        Thread.sleep(500);
        //Click Submit Button
        submitButton.click();
        //Check the result
        Thread.sleep(500);
    }

    @Test
    @SneakyThrows
    public void T06_submittingFiles() {
        driver.navigate().to("http://www.w3schools.com/tags/tryit.asp?filename=tryhtml5_input_type_file");
        driver.manage().window().maximize();

        //Switch to iframeResult iframe because all elements located in this iframe
        driver.switchTo().frame("iframeResult");

        //Find the elements
        WebElement browseButton = driver.findElement(By.cssSelector("body > form:nth-child(3) > input:nth-child(2)"));
        WebElement submitButton = driver.findElement(By.cssSelector("body > form:nth-child(3) > input:nth-child(5)"));

        //Test file decleration
        File testFile = new File(this.getClass().getResource("/test.properties").toURI());

        //Select test file
        browseButton.sendKeys(testFile.getAbsolutePath());

        //Click submit button
        submitButton.click();

        //Check the result
        WebElement resultText = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.w3-container.w3-large.w3-border")));
        System.out.println("resulttext: " + resultText.getText());
        Assertions.assertTrue(resultText.getText().contains("test.properties"), "test.properties is not submitted!");
    }

    @Test
    @Disabled("The drag and drop action has some problems.")
    public void T07_actions() {
        driver.navigate().to(baseURL + "/drag_and_drop");
        WebElement elementA = driver.findElement(By.id("column-a"));
        WebElement elementB = driver.findElement(By.id("column-b"));

        Actions builder = new Actions(driver);

        Action dragAndDrop = builder.clickAndHold(elementA)
            .moveToElement(elementB)
            .release()
            .build();

        dragAndDrop.perform();

        Assertions.assertTrue(driver.findElement(By.cssSelector("div[id='column-b'] header")).getText().equals("A"));
    }


    @AfterAll
    public void quitDriver() {
        driver.quit();
    }
}
