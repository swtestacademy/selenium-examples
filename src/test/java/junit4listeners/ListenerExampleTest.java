package junit4listeners;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@RunWith(MyTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListenerExampleTest {

    static WebDriver driver;
    final private static String URL = "https://www.swtestacademy.com";

    @BeforeClass
    public static void setupTest(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver(options);
        driver.get(URL);
    }

    //Passed
    @Test
    public void T01_PassTest() {
        assertThat(driver.getTitle(), is("Software Test Academy"));
    }

    //Failed
    @Test
    public void T02_FailTest() {
        assertEquals("WRONG TITLE", driver.getTitle());
    }

    //Ignore/Skip
    @Ignore
    public void T03_IgnoreTest() {
        assertThat(driver.getTitle(), is("Software Test Academy"));
    }

    //Throw Exception
    @Test
    public void T04_ExceptionTest() {
        throw new RuntimeException();
    }
}