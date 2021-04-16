package junit4rerun;

import static org.hamcrest.MatcherAssert.assertThat;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class RetryRuleTest {
    static        WebDriver driver;
    final private String    URL = "https://www.swtestacademy.com";

    @BeforeClass
    public static void setupTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    //Set retry count argument
    @Rule
    public RetryRule retryRule = new RetryRule(3);

    @Test
    public void getURLExample() {
        driver.get(URL);
        assertThat(driver.getTitle(), CoreMatchers.is("WRONG TITLE"));
    }
}
