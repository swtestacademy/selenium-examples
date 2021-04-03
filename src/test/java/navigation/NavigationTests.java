package navigation;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NavigationTests {

    private       WebDriver driver;
    final private String    URL1 = "http://www.yahoo.com";
    final private String    URL2 = "http://www.amazon.com";

    @BeforeAll
    public void setupTest() {
        driver = new ChromeDriver();
    }

    //.get Example
    @Test
    public void T01_getURLExample() {
        //Go to www.yahoo.com
        driver.get(URL1);

        //Check title is correct
        Assertions.assertEquals(driver.getTitle(), "Yahoo");
    }

    //.Navigate().to example
    @Test
    public void T02_navigateToExample() {
        //Go to www.amazon.com
        driver.navigate().to(URL2);

        //Check title is correct
        Assertions.assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");
    }

    @Test
    //Back - Forward - Refresh Example
    public void T03_backForwardRefreshExample() {
        //Go to www.yahoo.com
        driver.navigate().to(URL1);
        //Check title is correct
        Assertions.assertEquals(driver.getTitle(), "Yahoo");

        //Go to www.amazon.com
        driver.navigate().to(URL2);
        //Check title is correct
        Assertions.assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");

        //***Navigate Back***
        driver.navigate().back();
        //Check title is correct
        Assertions.assertEquals(driver.getTitle(), "Yahoo");

        //***Navigate Forward***
        driver.navigate().forward();
        //Check title is correct
        Assertions.assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");

        //***Refresh The Page***
        driver.navigate().refresh();
        Assertions.assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");
    }

    @AfterAll
    public void quitDriver() {
        driver.quit();
    }
}
