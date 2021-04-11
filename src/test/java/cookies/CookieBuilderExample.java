package cookies;

import java.util.Date;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Cookie;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class CookieBuilderExample {
    Date today    = new Date();
    Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));

    Cookie newCookie = new Cookie.Builder("myCookie","my value") //Name & value pair of the cookie (mandatory fields)
        .domain("www.swtestacademy.com") //Domain of the cookie
        .path("/") //Path of the cookie
        .expiresOn(tomorrow) //Expiration date
        .isSecure(true) //Is it secure or not?
        .build(); //Finally build it with .build() call

    @Test
    public void promptCookie (){
        System.out.println("My new Cookie: " + newCookie);
    }
}
