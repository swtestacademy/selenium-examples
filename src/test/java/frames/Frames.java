package frames;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class Frames {
    private WebDriver driver;

    @BeforeAll
    public void setupTest() {
        driver = new ChromeDriver();
        driver.navigate().to("http://www.londonfreelance.org/courses/frames/index.html");
        driver.manage().window().maximize();
    }

    @Test
    public void T01_FrameSwitchToFrameByIndex() {
        //Switch to main frame with its index (top.html)
        //top.html is in 3rd frame so its index is 2
        driver.switchTo().frame(2);

        //Check the H2 tag's text is "Title bar (top.html)
        WebElement h2Tag = driver.findElement(By.cssSelector("html>body>h2"));
        Assertions.assertEquals("Title bar (top.html)", h2Tag.getText());
    }

    @Test
    public void T02_FrameSwitchToFrameByName() {
        //Switch to main frame with its name (top.html)
        //This frame's name is main
        driver.switchTo().frame("main");

        //Check the H2 tag's text is "Title bar (top.html)
        WebElement h2Tag = driver.findElement(By.cssSelector("html>body>h2"));
        Assertions.assertEquals("Title bar (top.html)", h2Tag.getText());
    }

    @Test
    public void T03_FrameSwitchToFrameByWebElement() {
        //Switch to main frame with its webelement (top.html)
        //Find/locate the main frame
        WebElement mainFrame = driver.findElement(By.cssSelector("html>frameset>frameset>frameset>frame:nth-child(1)"));

        driver.switchTo().frame(mainFrame);

        //Check the H2 tag's text is "Title bar (top.html)
        WebElement h2Tag = driver.findElement(By.cssSelector("html>body>h2"));
        Assertions.assertEquals("Title bar (top.html)", h2Tag.getText());
    }

    @Test
    public void T04_FrameSwitchToFrameDefaultContent() {
        //Switch to main frame with its index (top.html)
        //This frame's name is main
        driver.switchTo().frame("main");

        //Check the H2 tag's text is "Title bar (top.html)
        WebElement h2Tag = driver.findElement(By.cssSelector("html>body>h2"));
        Assertions.assertEquals("Title bar (top.html)", h2Tag.getText());

        //Switch to Default Content (main page)
        driver.switchTo().defaultContent();

        //Check the main page's title
        Assertions.assertEquals("Sample frames page", driver.getTitle());
    }

    @AfterEach
    public void returnTheDefaultContent() {
        driver.switchTo().defaultContent();
    }

    @AfterAll
    public void tearDown() {
        driver.quit();
    }
}
