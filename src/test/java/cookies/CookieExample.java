package cookies;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class CookieExample {
    private        WebDriver driver;
    private static String    url = "https://www.swtestacademy.com";

    @BeforeAll
    public void setupTest() {
        driver = new ChromeDriver();
        driver.navigate().to(url);
        driver.manage().window().maximize();
    }

    @Test
    public void cookieTest() {
        //Get all the Cookies and print the total number of Cookies.
        printCookieNumber("First Time");

        //Get the cookie which name is “_gat” with getName() method and print its value.
        Cookie cookie = driver.manage().getCookieNamed("_gat");
        System.out.println("cookie: " + cookie +"\n");
        System.out.println("cookie value: " + cookie.getValue() + "\n");

        //Copy above cookie as copiedCookie and print it and total cookie number.
        Cookie copiedCookie = driver.manage().getCookieNamed("_gat");
        System.out.println("Copied cookie: " + copiedCookie +"\n");

        //Create new Cookie as “myCookie” with Cookie.Builder and print it.
        Cookie myCookie = new Cookie.Builder("myCookie", "Created by manually cookie.")
            .domain(cookie.getDomain())
            .path( cookie.getPath())
            .expiresOn(cookie.getExpiry())
            .isSecure(cookie.isSecure()).build();
        System.out.println("Built cookie: " + myCookie +"\n");

        //Delete the cookie
        driver.manage().deleteCookie(cookie);

        //print cookie number after deleting cookie.
        printCookieNumber("After Deleting the _gat Cookie");

        //Add new cookie by using “myCookie”
        driver.manage().addCookie(myCookie);

        //Add new cookie by using “copiedCookie”
        driver.manage().addCookie(copiedCookie);

        //Print cookie number after adding two cookies.
        printCookieNumber("After Adding Two Cookies");

        //Delete builtCookie by using .deleteCookieName
        driver.manage().deleteCookieNamed("myCookie");

        //Delete copiedCookie by using deleteCookieNamed
        driver.manage().deleteCookieNamed("_gat");

        //Print cookie number after deleting two cookies.
        printCookieNumber("After Deleting Two Cookies");

        //Delete all cookies.
        driver.manage().deleteAllCookies();

        //Print cookie number after deleting all the cookies.
        printCookieNumber("After Deleting All Cookies");

    }

    private void printCookieNumber (String message) {
        //Get all cookies of the site
        Set<Cookie> cookies = driver.manage().getCookies();
        List<Cookie> cookieList = new ArrayList<>(cookies); //Set to List Conversion
        System.out.println("Total number of Cookies ~~~" + message + "~~~ : " + cookieList.size() + "\n");

        //Print all cookie names and values
        for (int i=0; i<cookieList.size(); i++) {
            System.out.println("Cookie " + i + " name: " + cookieList.get(i).getName() +
                " value: " + cookieList.get(i).getValue());
        }
        System.out.println("---------------------------------------------------------\n");
    }

    @AfterAll
    public void quitDriver() {
        driver.quit();
    }
}
