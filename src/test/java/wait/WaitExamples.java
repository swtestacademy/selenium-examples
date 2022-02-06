package wait;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class WaitExamples {
    private WebDriver driver;
    private String    url = "http://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx";
    LocalDate currentDate = LocalDate.now();
    Month     month       = currentDate.getMonth();
    int       year        = currentDate.getYear();
    DayOfWeek dayOfWeek   = LocalDate.of(year, month, 3).getDayOfWeek();

    @BeforeEach
    public void setupTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.navigate().to(url);
        driver.manage().window().maximize();
    }

    @Test
    public void T01_FirstFailedSeleniumWaitTest() {
        //Get the selected date text before AJAX call
        String selectedDateTextBeforeAjaxCall = driver.findElement(By.cssSelector("#ctl00_ContentPlaceholder1_Label1")).getText().trim();

        //Print selectedDateTextBeforeAjaxCall to the console
        System.out.println("selectedDateTextBeforeAjaxCall: " + selectedDateTextBeforeAjaxCall + "\n");

        //Find 3rd Day on the calendar
        WebElement thirdDayOfMonth = driver.findElement(By.xpath(".//*[contains(@class, 'rcRow')]/td/a[.='3']"));

        //Click 3rd Day
        thirdDayOfMonth.click();

        //Get the selected date text after AJAX call
        String selectedDateTextAfterAjaxCall = driver.findElement(By.cssSelector("#ctl00_ContentPlaceholder1_Label1")).getText().trim();

        //Print selectedDateTextAfterAjaxCall to the console
        System.out.println("selectedDateTextAfterAjaxCall: " + selectedDateTextAfterAjaxCall + "\n");

        //Check the Actual and Expected Text
        Assertions.assertEquals(getExpectedDate(), selectedDateTextAfterAjaxCall);
    }

    @Test
    public void T02_FirstSeleniumWaitTest() {
        //Declare a Webdriver Wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Wait until presence of container
        wait.until(
            driver -> ExpectedConditions.presenceOfElementLocated(By.cssSelector(".demo-container.size-narrow")).apply(driver));

        //Get the selected date text before AJAX call
        String selectedDateTextBeforeAjaxCall = driver.findElement(By.cssSelector("#ctl00_ContentPlaceholder1_Label1")).getText().trim();

        //Print selectedDateTextBeforeAjaxCall to the console
        System.out.println("selectedDateTextBeforeAjaxCall: " + selectedDateTextBeforeAjaxCall + "\n");

        //Find 3rd Day on the calendar
        WebElement thirdDayOfMonth = driver.findElement(By.xpath(".//*[contains(@class, 'rcRow')]/td/a[.='3']"));

        //Click 3rd Day
        thirdDayOfMonth.click();

        //Wait until invisibility of loader
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
            driver -> ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".raDiv")).apply(driver));

        //Wait until visibility of selected date text
        //Actually it is not necessary, I added this control to see an example of visibilityOfElementLocated usage.
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver ->
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ctl00_ContentPlaceholder1_Label1")).apply(driver));

        //Find Selected Dates Text
        String selectedDateTextAfterAjaxCall = driver.findElement(By.cssSelector("#ctl00_ContentPlaceholder1_Label1")).getText().trim();

        //Print selectedDateTextAfterAjaxCall to the console
        System.out.println("selectedDateTextAfterAjaxCall: " + selectedDateTextAfterAjaxCall + "\n");

        //Check the Actual and Expected Text
        Assertions.assertEquals(getExpectedDate(), selectedDateTextAfterAjaxCall);
    }

    @Test
    public void T03_CustomExpectedConditionWithNamedClassTest() {
        //Declare a Webdriver Wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Wait until presence of container
        wait.until(driver -> ExpectedConditions.presenceOfElementLocated(By.cssSelector(".demo-container.size-narrow")).apply(driver));

        //Get the selected date text before AJAX call
        String selectedDateTextBeforeAjaxCall = driver.findElement(By.cssSelector("#ctl00_ContentPlaceholder1_Label1")).getText().trim();

        //Print selectedDateTextBeforeAjaxCall to the console
        System.out.println("selectedDateTextBeforeAjaxCall: " + selectedDateTextBeforeAjaxCall + "\n");

        //Find 3rd January on the calendar
        WebElement thirdDayOfMonth = driver.findElement(By.xpath(".//*[contains(@class, 'rcRow')]/td/a[.='3']"));

        //Click 3rd Day
        thirdDayOfMonth.click();

        //This time we are using custom ExpectedCondition
        wait.until(
            driver -> new ElementContainsText(By.cssSelector("#ctl00_ContentPlaceholder1_Label1"), String.valueOf(year)).apply(driver));

        //Find Selected Dates Text
        String selectedDateTextAfterAjaxCall = driver.findElement(By.cssSelector("#ctl00_ContentPlaceholder1_Label1")).getText().trim();

        //Print selectedDateTextAfterAjaxCall to the console
        System.out.println("selectedDateTextAfterAjaxCall: " + selectedDateTextAfterAjaxCall + "\n");

        //Check the Actual and Expected Text
        Assertions.assertEquals(getExpectedDate(), selectedDateTextAfterAjaxCall);
    }

    @Test
    public void T04_CustomExpectedConditionWithAnonymousClassTest() {
        //Declare a Webdriver Wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Wait until presence of container
        wait.until(driver -> ExpectedConditions.presenceOfElementLocated(By.cssSelector(".demo-container.size-narrow")).apply(driver));

        //Get the selected date text before AJAX call
        String selectedDateTextBeforeAjaxCall = driver.findElement(By.cssSelector("#ctl00_ContentPlaceholder1_Label1")).getText().trim();

        //Print selectedDateTextBeforeAjaxCall to the console
        System.out.println("selectedDateTextBeforeAjaxCall: " + selectedDateTextBeforeAjaxCall + "\n");

        //Find 3rd Day of the month on the calendar
        WebElement thirdOfJanuary = driver.findElement(By.xpath(".//*[contains(@class, 'rcRow')]/td/a[.='3']"));

        //Click 3rd of Month
        thirdOfJanuary.click();

        //AdHoc Wait with Anonymous Class (Synchronization)
        wait.until(webDriver -> webDriver.findElement(By.cssSelector("#ctl00_ContentPlaceholder1_Label1")).getText().contains(String.valueOf(year)));

        //Find Selected Dates Text
        String selectedDateTextAfterAjaxCall = driver.findElement(
            By.cssSelector("#ctl00_ContentPlaceholder1_Label1")).getText().trim();

        //Print selectedDateTextAfterAjaxCall to the console
        System.out.println("selectedDateTextAfterAjaxCall: " + selectedDateTextAfterAjaxCall + "\n");

        //Check the Actual and Expected Text
        Assertions.assertEquals(getExpectedDate(), selectedDateTextAfterAjaxCall);
    }

    @Test
    public void T05_CustomExpectedConditionWithWrappedAnonymousClassTest() {
        //Declare a Webdriver Wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Wait until presence of container
        wait.until(driver -> ExpectedConditions.presenceOfElementLocated(By.cssSelector(".demo-container.size-narrow")).apply(driver));

        //Get the selected date text before AJAX call
        String selectedDateTextBeforeAjaxCall = driver.findElement(By.cssSelector("#ctl00_ContentPlaceholder1_Label1")).getText().trim();

        //Print selectedDateTextBeforeAjaxCall to the console
        System.out.println("selectedDateTextBeforeAjaxCall: " + selectedDateTextBeforeAjaxCall + "\n");

        //Find Day of the Month on the calendar
        WebElement thirdDayOfMonth = driver.findElement(By.xpath(".//*[contains(@class, 'rcRow')]/td/a[.='3']"));

        //Click 3rd Day of the month
        thirdDayOfMonth.click();

        //Wrapped Anonymous Class (Synchronization)
        wait.until(driver -> textDisplayed(By.cssSelector("#ctl00_ContentPlaceholder1_Label1"), String.valueOf(year)).apply(driver));

        //Find Selected Dates Text
        String selectedDateTextAfterAjaxCall = driver.findElement(By.cssSelector("#ctl00_ContentPlaceholder1_Label1")).getText().trim();

        //Print selectedDateTextAfterAjaxCall to the console
        System.out.println("selectedDateTextAfterAjaxCall: " + selectedDateTextAfterAjaxCall + "\n");

        //Check the Expected and Actual Text
        Assertions.assertEquals(getExpectedDate(), selectedDateTextAfterAjaxCall);
    }

    @Test
    public void T06_FluentWaitTest() {
        //Using findElement method to find selectedText element until timeout period.
        WebElement selectedTextElement = findElement(driver, By.cssSelector("#ctl00_ContentPlaceholder1_Label1"), 5);

        //SelectedDateElement
        String selectedDateBeforeAjaxCall = selectedTextElement.getText();

        //Print selectedDateTextBeforeAjaxCall to the console
        System.out.println("selectedDateTextBeforeAjaxCall: " + selectedDateBeforeAjaxCall + "\n");

        //Find 3rd Day on the calendar with findElement method which
        //comprises FluentWait implementation and returns Web element.
        WebElement thirdDayOfMonth = findElement(driver, By.xpath(".//*[contains(@class, 'rcRow')]/td/a[.='3']"), 5);

        //Click 3rd Day
        thirdDayOfMonth.click();

        //Call method which comprises of FluentWait implementation
        //It will wait until period time and checks the given locator's text contains year
        //If it contains then it will return whole text
        By locator = By.cssSelector("#ctl00_ContentPlaceholder1_Label1");
        String selectedDateAfterAjaxCall = textContainsKeyword(driver, locator, 20, String.valueOf(year));

        //Print selectedDateTextAfterAjaxCall to the console
        System.out.println("selectedDateTextAfterAjaxCall: " + selectedDateAfterAjaxCall + "\n");

        //Check the Expected and Actual Text
        Assertions.assertEquals(getExpectedDate(), selectedDateAfterAjaxCall);
    }

    /**
     * Private Methods for the tests.
     */
    //Custom Expected Condition Class
    private class ElementContainsText implements ExpectedCondition<Boolean> {
        private String textToFind;
        private By     findBy;

        //Constructor (Set the given values)
        public ElementContainsText(final By elementFindBy, final String textToFind) {
            this.findBy = elementFindBy;
            this.textToFind = textToFind;
        }

        //Override the apply method with your own functionality
        @Override
        public Boolean apply(WebDriver webDriver) {
            //Find the element with given By method (By CSS, XPath, Name, etc.)
            WebElement element = webDriver.findElement(this.findBy);

            //Check that the element contains given text?
            return element.getText().contains(this.textToFind);
        }

        //This is for log message. I override it because when test fails, it will give us a meaningful message.
        @Override
        public String toString() {
            return ": \"Does " + this.findBy + " contain " + this.textToFind + "?\"";
        }
    }

    //Wrapped Anonymous Class
    private ExpectedCondition<Boolean> textDisplayed(final By elementFindBy, final String text) {
        return webDriver -> webDriver.findElement(elementFindBy).getText().contains(text);
    }

    //Using FluentWait in a method with ExpectedCondition
    private String textContainsKeyword(WebDriver driver, By locator, int timeoutSeconds, String keyword) {
        //FluentWait Declaration
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(timeoutSeconds)) //Set timeout
            .pollingEvery(Duration.ofMillis(500)) //Set query/check/control interval
            .withMessage("Timeout occurred!") //Set timeout message
            .ignoring(NoSuchElementException.class); //Ignore NoSuchElementException

        //ExpectedCondition: Wait until text contains keyword until timeout period and return the whole text
        //If text does not contain keyword until timeout period, return null.
        return wait.until(webDriver -> {
            if (webDriver.findElement(locator).getText().contains(keyword)) {
                return webDriver.findElement(locator).getText();
            } else {
                return null;
            }
        });
    }

    //Find element with FluentWait
    private static WebElement findElement(WebDriver driver, By locator, int timeoutSeconds) {
        //FluentWait Declaration
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(timeoutSeconds)) //Set timeout
            .pollingEvery(Duration.ofMillis(100)) //Set query/check/control interval
            .withMessage("Timeout occurred!") //Set timeout message
            .ignoring(NoSuchElementException.class); //Ignore NoSuchElementException

        //Wait until timeout period and when an element is found, then return it.
        return wait.until(webDriver -> driver.findElement(locator));
    }

    private String getExpectedDate() {
        return StringUtils.capitalize(dayOfWeek.toString().toLowerCase()) + ", " + StringUtils.capitalize(month.toString().toLowerCase())
            + " 3, " + year;
    }

    //Close Driver
    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
