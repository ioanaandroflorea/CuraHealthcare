package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;

public class BrowserManager {

    public static ChromeDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    public static void closeBrowser(ChromeDriver driver) {
        driver.quit();
    }

    public static ChromeDriver createChromeDriverForDesktop() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(options);
    }

    public static ChromeDriver createChromeDriverForMobile() {
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "Galaxy S8");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        return new ChromeDriver(chromeOptions);
    }
}
