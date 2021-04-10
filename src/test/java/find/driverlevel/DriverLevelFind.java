package find.driverlevel;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class DriverLevelFind {
    private       WebDriver driver;
    final private String    URL = "https://www.swtestacademy.com";

    @BeforeAll
    public void setupTest() {
        driver = new ChromeDriver();
    }

    @BeforeEach
    public void navigateToSwTestAcademy() {
        //Go to www.swtestacademy.com
        driver.navigate().to(URL);
    }

    //.getTitle Example
    @Test
    public void T01_getTitle() {
        //Check title
        assertThat(driver.getTitle(), is("Software Test Academy"));
    }

    //.getCurrentURL Example
    @Test
    public void T02_getCurrentURL() {
        //Check Current URL
        assertThat(driver.getCurrentUrl(), is("https://www.swtestacademy.com/"));
    }

    //.getPageSource Example
    @Test
    public void T03_getPageSource() {
        //Get PageSource and save it into a String
        String pageSource = driver.getPageSource();

        //Check page source contains ""
        Assertions.assertTrue((pageSource.contains("swtestacademy")));
    }

    @AfterAll
    public void quitDriver() {
        driver.quit();
    }
}
