package saucelabs;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class SauceLabsExample {
    WebDriver driver;
    //Setting environmental variables
    String sauceUserName = System.getenv("SAUCE_USERNAME");
    String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
    String sauceURL = "https://ondemand.us-west-1.saucelabs.com:443/wd/hub";

    @Test
    public void shouldOpenSwTestAcademyOnSauceLabs() throws MalformedURLException {
        //Here we set the MutableCapabilities for "sauce:options", which is required for newer versions of Selenium.
        MutableCapabilities sauceOpts = new MutableCapabilities();
        sauceOpts.setCapability("username", sauceUserName);
        sauceOpts.setCapability("accessKey", sauceAccessKey);

        //In order to use w3c you must set the seleniumVersion
        sauceOpts.setCapability("seleniumVersion", "4.0.0-beta-2");
        sauceOpts.setCapability("name", "4-best-practices");

        //Tags are an excellent way to control and filter your test automation
        List<String> tags = Arrays.asList("sauceDemo", "demoTest", "module4", "javaTest");
        sauceOpts.setCapability("tags", tags);

        //Timeout capability
        sauceOpts.setCapability("maxDuration", 3600);

        //A Selenium crash might cause a session to hang indefinitely. Below is the max time allowed to wait for a Selenium command.
        sauceOpts.setCapability("commandTimeout", 600);

        //How long can the browser wait for a new command
        sauceOpts.setCapability("idleTimeout", 1000);

        //Setting a build name
        sauceOpts.setCapability("build", "SW Test Academy Sauce Example - Java-Junit5");

        //Required to set w3c protocol
        ChromeOptions chromeOpts = new ChromeOptions();
        chromeOpts.setExperimentalOption("w3c", true);

        //Set a second MutableCapabilities object to pass Sauce Options and Chrome Options
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("sauce:options", sauceOpts);
        capabilities.setCapability("goog:chromeOptions", chromeOpts);
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("platformVersion", "Windows 10");
        capabilities.setCapability("browserVersion", "latest");


        driver = new RemoteWebDriver(new URL(sauceURL), capabilities);
        driver.navigate().to("https://www.swtestacademy.com");
        assertTrue(driver.getTitle().contains("Software Test Academy"));
    }

    //Below we are performing 2 critical actions. Quitting the driver and passing the test result to Sauce Labs user interface.
    @AfterEach
    public void cleanUpAfterTestMethod() {
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + ("passed"));
        driver.quit();
    }
}
