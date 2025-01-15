package com.tcc.shoedatacollector;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebdriverService {
    // Note that this class can not be used in a multi-threaded environment because the driver is static
    // and each thread will want its own driver.

    @Getter
    public static WebDriver driverWithHead;
    @Getter
    private static WebDriver headlessDriver;

    public static WebDriver createHeadlessDriver() {
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

    @SuppressWarnings("UnusedReturnValue")
    public static WebDriver openEbayHomepage() {
        WebDriver driver = createHeadlessDriver();
        getHeadlessDriver().get("https://www.ebay.com");
        return driver;
    }

    public static String getUrl() {
        return WebdriverService.getHeadlessDriver().getCurrentUrl();
    }

    @SuppressWarnings("UnusedReturnValue")
    public static WebDriver openAdvancedSearchPage() {
        WebDriver driver = createHeadlessDriver();
        WebdriverService.getHeadlessDriver().get("https://www.ebay.com/sch/ebayadvsearch");
        return driver;
    }
    public static WebDriver openSearchBySeller(String seller) {
        WebDriver driver = createHeadlessDriver();
        WebdriverService.getHeadlessDriver().get("https://www.ebay.com/sch/i.html?_ssn=" + seller + "&_ipg=240");
        return driver;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static WebDriver OpenSearchBySellerInBrowser(String seller) {
        WebDriver driver = createDriverWithHead();
        WebdriverService.driverWithHead.get("https://www.ebay.com/sch/i.html?_ssn=" + seller + "&_ipg=240");
        return driver;

    }
}
