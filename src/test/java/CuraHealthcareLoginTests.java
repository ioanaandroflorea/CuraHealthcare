import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class CuraHealthcareLoginTests extends BaseTest {

    @DataProvider(name = "loginDataProvider")
    public Object[][] loginDataProvider() {
        return new Object[][]{
                {"John Doe", "ThisIsNotAPassword", "validCredentials"},
                {"John Do", "ThisIsNotAPassword", "invalidCredentials"}
        };
    }

    @Test(dataProvider = "loginDataProvider", groups = "login")
    public void loginWithCredentials(String username, String password, String credentialsType) {
        WebElement menu = driver.findElement(By.cssSelector(".btn-dark.btn-lg.toggle"));
        menu.click();
        WebElement loginLink = driver.findElement(By.linkText("Login"));
        loginLink.click();
        Assert.assertEquals(driver.getCurrentUrl(),
                "https://katalon-demo-cura.herokuapp.com/profile.php#login",
                "Couldn't navigate to login page!");
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys(username);
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(password);
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        if (credentialsType.equals("validCredentials")) {
            Assert.assertEquals(driver.getCurrentUrl(),
                    "https://katalon-demo-cura.herokuapp.com/#appointment",
                    "Couldn't navigate to appointment page!");
        } else {
            WebElement unsuccessfulLoginMessage = driver.findElement(By.className("text-danger"));
            Assert.assertEquals(unsuccessfulLoginMessage.getText(),
                    "Login failed! Please ensure the username and password are valid.",
                    "Shouldn't navigate to appointment page!");
        }
    }

    @Test(groups = "redirect")
    public void verifyRedirectToLoginWhenUserNotLoggedIn() {
        WebElement makeAppointmentButton = driver.findElement(By.id("btn-make-appointment"));
        makeAppointmentButton.click();
        Assert.assertEquals(driver.getCurrentUrl(),
                "https://katalon-demo-cura.herokuapp.com/profile.php#login",
                "User is not redirected to login page when not logged in!");
    }

    @DataProvider(name = "appointmentDataProvider")
    public Object[][] appointmentDataProvider() {
        return new Object[][]{
                {"01/02/2023", "positive scenario", true},
                {"", "negative scenario", false}
        };
    }

    @Test(dataProvider = "appointmentDataProvider")
    public void makeAppointment(String data, String comm, boolean test) {
        WebElement makeAppointmentButton = driver.findElement(By.id("btn-make-appointment"));
        makeAppointmentButton.click();
        WebElement inputUsername = driver.findElement(By.id("txt-username"));
        inputUsername.click();
        inputUsername.sendKeys("John Doe");
        WebElement inputPassword = driver.findElement(By.id("txt-password"));
        inputPassword.click();
        inputPassword.sendKeys("ThisIsNotAPassword");
        WebElement loginSubmitButton = driver.findElement(By.id("btn-login"));
        loginSubmitButton.click();
        WebElement visitDate = driver.findElement(By.id("txt_visit_date"));
        visitDate.sendKeys(data);
        WebElement commentText = driver.findElement(By.id("txt_comment"));
        commentText.sendKeys(comm);
        WebElement bookAppointment = driver.findElement(By.id("btn-book-appointment"));
        bookAppointment.click();
        if (test) {
            assertTrue(driver.findElement(By.cssSelector("#summary > div > div > div.col-xs-12.text-center > h2")).isDisplayed());
        } else {
            Assert.assertEquals("https://katalon-demo-cura.herokuapp.com/#appointment", driver.getCurrentUrl());
        }
    }

    @Test
    public void returnToHomePageFromMenu() {
        WebElement menuButton = driver.findElement(By.id("menu-toggle"));
        menuButton.click();
        WebElement homeFromMenuButton = driver.findElement(By.xpath("//*[@id='sidebar-wrapper']/ul/li[2]/a"));
        homeFromMenuButton.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/");
    }

    @Test
    public void returnToHomePageFromAppointmentPage() {
        WebElement makeAppointmentButton = driver.findElement(By.id("btn-make-appointment"));
        makeAppointmentButton.click();
        WebElement inputUsername = driver.findElement(By.id("txt-username"));
        inputUsername.click();
        inputUsername.sendKeys("John Doe");
        WebElement inputPassword = driver.findElement(By.id("txt-password"));
        inputPassword.click();
        inputPassword.sendKeys("ThisIsNotAPassword");
        WebElement loginSubmit = driver.findElement(By.id("btn-login"));
        loginSubmit.click();
        WebElement menuButton = driver.findElement(By.id("menu-toggle"));
        menuButton.click();
        WebElement homeFromMenuInAppointmentPage = driver.findElement(By.xpath("//*[@id='sidebar-wrapper']/ul/li[2]/a"));
        homeFromMenuInAppointmentPage.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/");
    }
}

