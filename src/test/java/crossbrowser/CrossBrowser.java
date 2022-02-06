package crossbrowser;

import static org.openqa.selenium.remote.CapabilityType.PROXY;

import com.gargoylesoftware.htmlunit.WebClient;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.Optional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class CrossBrowser {
    private WebDriver driver;
    private WebClient webClient;

    @Test
    public void T01_FirefoxTest() {
        //WebDriverManager.firefoxdriver().setup(); //Rather than webdriver manager, I used local gecko driver.
        //Create a new Profile
        FirefoxProfile profile = new FirefoxProfile();
        FirefoxOptions options = new FirefoxOptions();
        //Setting Preferences
        profile.setPreference("intl.accept_languages", "no,en-us,en");
        //Declaring Webdriver instance
        driver = new FirefoxDriver(options.setProfile(profile));
        driver.navigate().to("https://www.swtestacademy.com");
    }

    @Test
    public void T02_DesiredCapabilitiesTest() {
        //Setup Firefox Driver by Bonigarcia Library.
        //WebDriverManager.firefoxdriver().setup(); //Rather than webdriver manager, I used local gecko driver.

        //Setup Proxy
        String httpProxy = "127.0.0.1:8080";
        Proxy seleniumProxy = new org.openqa.selenium.Proxy();
        seleniumProxy.setHttpProxy(httpProxy)
            .setSslProxy(httpProxy);

        //Create Desired Capabilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(PROXY, seleniumProxy);
        FirefoxOptions options = new FirefoxOptions(capabilities);

        //Instantiate driver and open the page
        driver = new FirefoxDriver(options);
        driver.navigate().to("http://www.swtestacademy.com");
    }

    @Test
    @SneakyThrows
    public void T03_HtmlUnitDriverTest() {
        //Instantiate driver and open the page
        webClient = new WebClient();
        //Disable JavaScript processing for one WebClient,
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getPage("http://www.swtestacademy.com");
        //Enable JavaScript processing for one WebClient,
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getPage("http://www.google.com");
    }

    @Test
    @Disabled("It has some problems.")
    public void T04_PhantomJSTest() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("phantomjs.binary.path", "/usr/local/bin/phantomjs");
        driver = new PhantomJSDriver(caps);
        driver.navigate().to("https://www.google.com");
    }

    @Test
    public void T05_ChromeTest() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver(options);
        driver.navigate().to("https://www.google.com");
    }

    @Test
    public void T06_EdgeTest() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.setHeadless(true);
        driver = new EdgeDriver(options);
        driver.navigate().to("https://www.google.com");
    }

    @Test
    public void T07_OperaTest() {
        WebDriverManager.operadriver().setup();
        driver = new OperaDriver();
        driver.navigate().to("https://www.google.com");
    }

    @Test
    public void T08_SafariTest() {
        driver = new SafariDriver();
        driver.navigate().to("https://www.google.com");
    }

    @AfterEach
    public void tearDown() {
        Optional.ofNullable(webClient)
            .ifPresent(WebClient::close);
        Optional.ofNullable(driver)
            .ifPresent(WebDriver::quit);
    }
}