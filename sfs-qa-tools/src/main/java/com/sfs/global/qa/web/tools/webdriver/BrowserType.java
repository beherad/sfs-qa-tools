package com.sfs.global.qa.web.tools.webdriver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.safari.SafariOptions;

@AllArgsConstructor
public enum BrowserType {

    CHROME ("chrome", new ChromeOptions()),
    FIREFOX ("firefox", new FirefoxOptions()),
    EDGE ("MicrosoftEdge", new EdgeOptions()),
    IE ("internet explorer", new InternetExplorerOptions()),
    SAFARI ("safari", new SafariOptions());
    //ANDROID("Android"),
    //IOS("iOS")
    /*,
    AWS("AWS")*/
    ;

    @Getter
    private final String browserName;
    @Getter
    private final AbstractDriverOptions defaultOptions;

    /**
     * Gets the supported browser from the given browser name.
     * @param browserName The browser name.
     * @return The supported browser.
     */
    public static BrowserType getByName(final String browserName) {
        switch (browserName.toLowerCase()) {
            case "ch":
            case "chrome":
                return CHROME;
            case "ff":
            case "firefox":
                return FIREFOX;
            case "edge":
            case "microsoftedge":
                return EDGE;
            case "ie":
            case "internet explorer":
                return IE;
            case "safari":
                return SAFARI;
            /*case "android":
                return ANDROID;
            case "ios":
                return IOS;*/
            /*case "aws":
                return AWS;*/
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName
                        + ". Supported browsers are: IE, FF, CH, EDGE, ANDROID, IOS, AWS.");
        }
    }
}