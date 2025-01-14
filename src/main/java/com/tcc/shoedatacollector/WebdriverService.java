package com.tcc.shoedatacollector;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebdriverService {
    @Getter
    public static WebDriver driverWithHead;
    @Getter
    private static WebDriver headlessDriver;

    public static WebDriver createDriver() {
        if (headlessDriver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            options.addArguments("--headless");
            headlessDriver = new ChromeDriver(options);
        }
        return headlessDriver;
    }

    public static void closeDriver() {
        if (headlessDriver != null) {
            headlessDriver.quit();
            headlessDriver = null;
        }
    }

    public static WebDriver createDriverWithHead() {
        if (driverWithHead == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            driverWithHead = new ChromeDriver(options);
        }
        return driverWithHead;
    }
}
