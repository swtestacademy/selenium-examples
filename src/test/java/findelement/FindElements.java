package findelement;

import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FindElements {
    private       WebDriver driver;
    final private String    baseURL = "http://the-internet.herokuapp.com";

    @BeforeAll
    public void setupTest() {
        driver = new ChromeDriver();
        driver.navigate().to(baseURL + "/login");
    }

    @Test
    public void T01_findById() {
        //Find first name text box by By.id method
        WebElement userName = driver.findElement(By.id("username"));
        //Assert that text box is empty
        Assertions.assertEquals("", userName.getText());
    }

    @Test
    public void T02_findByName() {
        //Find first name text box by By.name method
        WebElement userName = driver.findElement(By.name("username"));
        //Assert that text box is empty
        Assertions.assertEquals("", userName.getText());
    }

    @Test
    public void T03_findLinkText() {
        //Find text by By.linkText method
        WebElement link = driver.findElement(By.linkText("Elemental Selenium"));
        //Assert that text box is empty
        Assertions.assertEquals("Elemental Selenium", link.getText());
    }

    @Test
    public void T04_findPartialLinkText() {
        //Find text by By.partialLinkText method
        WebElement link = driver.findElement(By.partialLinkText("Elemental Sel"));
        //Assert that text box is empty
        Assertions.assertEquals("Elemental Selenium", link.getText());
    }

    @Test
    public void T05_findClassName() {
        //Find subheader by using By.className
        WebElement subHeader = driver.findElement(By.className("subheader"));
        //Assert that subheader contains SuperSecretPassword
        Assertions.assertTrue(subHeader.getText().contains("SuperSecretPassword"));
    }

    @Test
    public void T06_findTagName() {
        //Find input fields by By.tagName method
        List<WebElement> inputTags = driver.findElements(By.tagName("input"));
        //Assert that text box is empty
        Assertions.assertEquals(2, inputTags.size());
    }

    @Test
    public void T07_findCSS() {
        //Find subheader by using By.className
        WebElement subHeader = driver.findElement(By.cssSelector(".subheader"));
        //Assert that subheader contains SuperSecretPassword
        Assertions.assertTrue(subHeader.getText().contains("SuperSecretPassword"));
    }

    @Test
    public void T07_findXPath() {
        //Find subheader by using By.className
        WebElement subHeader = driver.findElement(By.xpath("//h4[@class='subheader']"));
        //Assert that subheader contains SuperSecretPassword
        Assertions.assertTrue(subHeader.getText().contains("SuperSecretPassword"));
    }

    @AfterAll
    public void quitDriver() {
        driver.quit();
    }
}
