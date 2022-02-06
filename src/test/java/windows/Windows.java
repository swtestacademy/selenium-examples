package windows;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class Windows {
    private WebDriver driver;

    @BeforeAll
    public void setupTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void T01_SwitchToWindows() {
        //1) Navigate to URL
        driver.navigate().to("http://www.w3schools.com/tags/tryit.asp?filename=tryhtml_link_target");
        driver.manage().window().maximize();

        //2) Get the current window's handle and write to the console window. It must be first window handle.
        System.out.println("Current Window Handle: " + driver.getWindowHandle() + "\n");

        //Switch to iframeResult iframe because all elements located in this iframe
        driver.switchTo().frame("iframeResult");

        //3) Locate the link and click it
        WebElement visitLink = driver.findElement(By.linkText("Visit W3Schools.com!"));
        visitLink.click();

        //4) Get all window handles and hold them in a list.
        Set<String> windowHandles = driver.getWindowHandles();
        List<String> windowHandlesList = new ArrayList<>(windowHandles); //Set to List Conversion

        //5) Write to total window handle number to the console.
        System.out.println("Total window number: " + windowHandlesList.size() + "\n");

        //6) Switch to second window
        driver.switchTo().window(windowHandlesList.get(1));

        //7) Get the current window's handle and write to the console window. It must be second window handle.
        System.out.println("Current Window Handle: " + driver.getWindowHandle() + "\n");

        //8) Check the upper left side logo
        WebElement logo = driver.findElement(By.cssSelector(".fa.fa-logo"));
        Assertions.assertTrue(logo.isDisplayed());

        //9) Go back (Switch) to first window
        driver.switchTo().window(windowHandlesList.get(0));

        //10) Get the current window's handle and write to the console window. It must be first window handle.
        System.out.println("Current Window Handle: " + driver.getWindowHandle() + "\n");

        //11) Check the Run Button Text
        WebElement seeResultButton = driver.findElement(By.cssSelector("button[onclick*='submitTryit(1)'"));
        Assertions.assertTrue(seeResultButton.getText().contains("Run ❯"));
    }

    @Test
    @SneakyThrows
    public void T02_ManageWindows() {
        //1) Navigate to URL
        driver.navigate().to("http://www.w3schools.com/tags/tryit.asp?filename=tryhtml_link_target");

        //2) Maximize the window
        driver.manage().window().maximize();

        //3) Get size of the window and write the full screen size to the console
        Dimension windowSize = driver.manage().window().getSize();
        System.out.println("***Full Size Values for Current Window***\n");
        System.out.println("Screen Width: " + windowSize.getWidth() + "\n");
        System.out.println("Screen Height: " + windowSize.getHeight() + "\n");
        Thread.sleep(500);

        //4) Minimize the window by 1/4 and write the new screen size to the console
        driver.manage().window().setSize(new Dimension(windowSize.getWidth() / 4, windowSize.getHeight() / 4));
        Dimension quarterWindowSize = driver.manage().window().getSize();
        System.out.println("*** 1/4 Size Values for Current Window***\n");
        System.out.println("Screen Width: " + quarterWindowSize.getWidth() + "\n");
        System.out.println("Screen Height: " + quarterWindowSize.getHeight() + "\n");
        Thread.sleep(500);

        //5) Get window position and write it to the console
        Point windowPosition = driver.manage().window().getPosition();
        System.out.println("*** Window Position for Current Window***\n");
        System.out.println("Window X position: " + windowPosition.getX() + "\n");
        System.out.println("Window Y position: " + windowPosition.getY() + "\n");
        Thread.sleep(500);

        //6) Move window position x=200 and y=200 and write to the console
        Point newWindowPosition = windowPosition.moveBy(200, 200);
        driver.manage().window().setPosition(newWindowPosition);
        System.out.println("*** Window Position for Current Window***\n");
        System.out.println("Window X position: " + driver.manage().window().getPosition().getX() + "\n");
        System.out.println("Window Y position: " + driver.manage().window().getPosition().getY() + "\n");
        Thread.sleep(500);
    }

    @AfterAll
    public void tearDown() {
        driver.quit();
    }
}
