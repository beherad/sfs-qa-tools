package com.sfs.global.qa.web.tools.webdriver;

import com.sfs.global.qa.core.data.enums.ImageFormat;
import com.sfs.global.qa.utils.DateFormatUtils;
import com.sfs.global.qa.utils.FileUtils;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Abstract browser class
 *
 * @param <B> The browser of GenericBrowser type.
 * @param <D> The driver of WebDriver type.
 */
@Slf4j
public abstract class AbstractBrowser<B extends GenericBrowser, D extends WebDriver> extends WebDriverInstance<D> {

    private static final Map<Long, GenericBrowser> localBrowsersPool = new ConcurrentHashMap<>();
    private static final Map<Long, Class<? extends GenericBrowser>> localBrowserTypesPool = new ConcurrentHashMap<>();

    protected AbstractBrowser() {
        super();
    }

    @Synchronized
    protected void setBrowser(B browser) {
        localBrowsersPool.put(getCurrThreadId(), browser);
    }

    protected B getBrowser() {
        return (B) localBrowsersPool.get(getCurrThreadId());
    }

    @Synchronized
    protected void setClazz(final Class<B> clazz) {
        localBrowserTypesPool.put(getCurrThreadId(), clazz);
    }

    protected Class<B> getClazz() {
        return (Class<B>) localBrowserTypesPool.get(getCurrThreadId());
    }

    /**
     * Close current browser window, quitting the browser if it's the last window currently open
     */
    @Synchronized
    public void close() {
        getDriver().close();
    }

    /**
     * Quits this driver, closing every associated window
     */
    @Synchronized
    public void quit() {
        getDriver().quit();
    }

    /**
     * Close current browser window & kill driver process
     */
    @Synchronized
    public void kill() {
        close();
        quit();
    }

    /**
     * Switch the focus of future commands for this driver to the current window.
     */
    public B windowFocus() {
        log.info("Switch focus to the browser window.");
        getDriver().switchTo().window(getDriver().getWindowHandle());
        return getClazz().cast(getBrowser());
    }

    /**
     * Maximizes the current window if it is not already maximized.
     */
    public B windowMaximize() {
        log.info("Maximize browser window.");
        getDriver().manage().window().maximize();
        return getClazz().cast(getBrowser());
    }

    /**
     * Set the size of the current window.
     *
     * @param width The width of the window.
     * @param height The height of the window.
     */
    public B setWindowSize(final int width, final int height) {
        log.info("Set browser window to [{}x{}] resolution size.", width, height);
        getDriver().manage().window().setSize(new Dimension(width,height));
        return getClazz().cast(getBrowser());
    }

    /**
     * Open a web page URL in the current browser window.
     *
     * @param url {@link String}
     */
    public B openURL(final String url) {
        log.info("Navigate to the url [{}].", url);
        getDriver().navigate().to(url);
        return getClazz().cast(getBrowser());
    }

    /**
     * Move one page back in the browser history.
     */
    public B back() {
        log.info("Go one page back.");
        getDriver().navigate().back();
        return getClazz().cast(getBrowser());
    }

    /**
     * Move one page forward in the browser history.
     * Does nothing if we are on the latest page viewed.
     */
    public B forward() {
        log.info("Go one page forward.");
        getDriver().navigate().forward();
        return getClazz().cast(getBrowser());
    }

    /**
     * Get the source of the last loaded page.
     */
    public String getPageSource() {
        return getDriver().getPageSource();
    }

    public Object jsExecuteScript(final String jsString) {
        log.info("Execute JS script [{}].", jsString);
        final JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        return jsExecutor.executeScript(jsString);
    }

    public Object jsExecuteScript(final String jsString, final WebElement element) {
        log.info("Execute JS script [{}].", jsString);
        final JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        return jsExecutor.executeScript(jsString, element);
    }

    public Object jsExecuteScript(final String jsString, final Object... args) {
        final JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        return jsExecutor.executeScript(jsString, args);
    }

    public Set<Cookie> getAllCookies() {
        return getDriver().manage().getCookies();
    }

    /**
     * Get all cookies as a string.
     *
     * @return cookies list represented as key-value pairs split by ';' as a {@link String} - "key1=value1;key2=value2;..."
     */
    public String getAllCookiesAsString() {
        final StringBuilder cookiesString = new StringBuilder();
        for (Cookie cookie : getAllCookies()) {
            cookiesString.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
        }
        return cookiesString.toString();
    }

    public B deleteCookie(final Cookie cookie) {
        getDriver().manage().deleteCookie(cookie);
        return getClazz().cast(getBrowser());
    }

    public B deleteCookie(final String cookieName) {
        log.info("Delete browser cookie [{}].", cookieName);
        getDriver().manage().deleteCookieNamed(cookieName);
        return getClazz().cast(getBrowser());
    }

    public B deleteAllCookies() {
        log.info("Delete all stored browser cookies.");
        getDriver().manage().deleteAllCookies();
        return getClazz().cast(getBrowser());
    }

    public B clearLocalStorage() {
        log.info("Clear all stored browser local storage data.");
        jsExecuteScript("window.localStorage.clear();");
        return getClazz().cast(getBrowser());
    }

    public B clearSessionData() {
        log.info("Clear all stored browser session data.");
        jsExecuteScript("sessionStorage.clear();");
        return getClazz().cast(getBrowser());
    }

    public void setImplicitWaitTimeout(final Duration duration) {
        getDriver().manage().timeouts().implicitlyWait(duration);
    }

    public void setImplicitWaitTimeout(final int timeInSeconds) {
        log.info("Set implicit wait timeout to {} seconds.", timeInSeconds);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSeconds));
    }

    public void setPageLoadTimeout(final int timeInSeconds) {
        log.info("Set page load timeout to {} seconds.", timeInSeconds);
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeInSeconds));
    }


    /**
     * Takes a screenshot using the current WebDriver.
     * The screenshot would be saved in the default directory "target/screenshots" with the name "screenshot_dd-MM-yyyy_HH-mm-ss.png".
     *
     * @return The file of screenshot taken.
     */
    public File takeScreenshot() {
        return takeScreenshot("target/screenshots",  "screenshot", ImageFormat.PNG, true);
    }

    /**
     * Takes a screenshot using the current WebDriver.
     *
     * @param destDirpath  - The destination directory path to save the screenshot.
     * @param imageName    - The name of the image.
     * @param imageFormat  - The format of the image, e.g. PNG, JPEG, etc., is used to identify the file extension.
     * @param addTimestamp - The flag to add or not a timestamp to the image name.
     * @return The file of screenshot taken.
     */
    public File takeScreenshot(final String destDirpath, String imageName, final ImageFormat imageFormat, final boolean addTimestamp) {
        if (addTimestamp) {
            imageName = imageName.concat("_").concat(DateFormatUtils.getTimestamp("dd-MM-yyyy_HH-mm-ss-SSS"));
        }
        imageName = imageName.concat(imageFormat.getExtension());
        final File screenshotFile = new File(destDirpath, imageName);
        final byte[] screenshotBytes = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
        try {
            FileUtils.writeByteArrayToFile(screenshotFile, screenshotBytes);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return screenshotFile;
    }

    /**
     * Takes a screenshot using the specified WebElement.
     * @param element - WebElement to be used for the screenshot.
     * @return The file of screenshot taken.
     */
    public File takeScreenshot(final WebElement element) {
        return element.getScreenshotAs(OutputType.FILE);
    }

}
