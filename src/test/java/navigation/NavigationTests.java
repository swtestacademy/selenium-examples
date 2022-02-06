package navigation;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class NavigationTests {

    private       WebDriver     driver;
    private       WebDriverWait wait;
    final private String        URL1 = "http://www.yahoo.com";
    final private String        URL2 = "http://www.amazon.com";

    @BeforeEach
    public void setupTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void quitDriver() {
        driver.quit();
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
        Assertions.assertEquals("Amazon.com. Spend less. Smile more.", driver.getTitle());
    }

    @Test
    //Back - Forward - Refresh Example
    public void T03_backForwardRefreshExample() {
        //Go to www.yahoo.com
        driver.navigate().to(URL1);
        wait.until(driver -> driver.getTitle().contentEquals("Yahoo"));
        //Check title is correct
        Assertions.assertEquals(driver.getTitle(), "Yahoo");

        //Go to www.amazon.com
        driver.navigate().to(URL2);
        wait.until(driver -> driver.getTitle().contentEquals("Amazon.com. Spend less. Smile more."));
        //Check title is correct
        Assertions.assertEquals("Amazon.com. Spend less. Smile more.", driver.getTitle());

        //***Navigate Back***
        driver.navigate().back();
        wait.until(driver -> driver.getTitle().contentEquals("Yahoo"));
        //Check title is correct
        Assertions.assertEquals(driver.getTitle(), "Yahoo");

        //***Navigate Forward***
        driver.navigate().forward();
        wait.until(driver -> driver.getTitle().contentEquals("Amazon.com. Spend less. Smile more."));
        //Check title is correct
        Assertions.assertEquals("Amazon.com. Spend less. Smile more.", driver.getTitle());

        //***Refresh The Page***
        driver.navigate().refresh();
        wait.until(driver -> driver.getTitle().contentEquals("Amazon.com. Spend less. Smile more."));
        Assertions.assertEquals("Amazon.com. Spend less. Smile more.", driver.getTitle());
    }
}
