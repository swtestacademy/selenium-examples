package javascript;

import java.time.Duration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ExecuteAsyncJavascriptExamples {
    private        WebDriver          driver;
    private static String             url = "http://phppot.com/demo/jquery-dependent-dropdown-list-countries-and-states/";
    private        JavascriptExecutor js;

    @BeforeAll
    public void setupTest() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        driver.navigate().to(url);
    }

    @Test
    public void T01_browserSleepExampleTest() {
        //Set ScriptTimeout
        driver.manage().timeouts().setScriptTimeout(Duration.ofSeconds(10));

        //Declare and set start time
        long startTime = System.currentTimeMillis();

        //Call executeAsyncScript() method
        js.executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 4000);");

        //Get the difference (currentTime - startTime) it should be greater than 1500
        System.out.println("Passed time: " + (System.currentTimeMillis() - startTime));

        //Assert that the time difference is greater than 4000
        Assertions.assertTrue((System.currentTimeMillis() - startTime) > 4000,
            "Time difference must be greater than 4000 milliseconds");
    }

@Test
public void T02_sendXMLHTTPRequestTest() {
    Object response = null;

    //Set ScriptTimeout
    driver.manage().timeouts().setScriptTimeout(Duration.ofSeconds(10));

    //Declare JavascriptExecutor
    JavascriptExecutor js = (JavascriptExecutor) driver;

    //Injecting a XMLHttpRequest and waiting for the result
    //Ref1: https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/JavascriptExecutor.html
    //Ref2: http://www.openjs.com/articles/ajax_xmlhttp_using_post.php
    try {
        response = js.executeAsyncScript(
            //Declare callback first!
            "var callback = arguments[arguments.length - 1];" +

                //Declare url, parameters and method for POST
                //Send country_id=5 (USA)
                "var http = new XMLHttpRequest();" +
                "var url = 'get_state.php';" +  //url
                "var params = 'country_id=5';" +  //parameters
                "http.open('POST', url, true);" +

                //Send the proper header information along with the request
                "http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');" +
                "http.setRequestHeader('Content-length', params.length);" +
                "http.setRequestHeader('Connection', 'close');" +

                //Call a function when the state changes.
                "http.onreadystatechange = function() {" +
                "    if(http.readyState == 4) {" +
                "        callback(http.responseText);" +
                "    };" +
                "};" +
                "http.send(params);");
    }
    catch (UnhandledAlertException e) {
        System.err.println("Error Occurred!");
    }

    //Assert that returned cities are related with USA
    System.out.println((String) response);
    Assertions.assertTrue(((String) response).contains("<option value=\"4\">New York</option>"));
}

    @AfterAll
    public void quitDriver() {
        driver.quit();
    }
}

