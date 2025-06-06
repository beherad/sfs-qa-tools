package com.sfs.global.qa.web.tools.webdriver;

import com.sfs.global.qa.taf.web.exceptions.ElementNotFoundException;
import com.sfs.global.qa.utils.WaitUtils;
import com.sfs.global.qa.web.framework.data.WebElementAttributes;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public abstract class AbstractPage<B extends GenericBrowser, D extends WebDriver> extends AbstractBrowser<B, D> {

    private static final String NO_SUCH_ELEMENT_EXCEPTION = "NoSuchElementException thrown";
    private static final String STALE_ELEMENT_REFERENCE_EXCEPTION = "StaleElementReferenceException thrown";
    private static final long MIN_WAIT_TIME = 1_000;
    private static final long POLLING_TIME = 500;
    private static final String MIN_TIME_MESSAGE = "Minimum recommended timeout in milliseconds is: ";

    private String byType;
    private String byValue;

    protected AbstractPage() {
        PageFactory.initElements(getDriver(), this);
    }

    /**
     * Gets wait object with the specified timeout.
     *
     * @param timeout the timeout in seconds.
     * @return WebDriverWait object
     */
    protected WebDriverWait getWait(final long timeout) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(timeout), Duration.ofSeconds(1));
    }

    /**
     * Gets actions object.
     *
     * @return Actions object
     */
    protected Actions getActions() {
        return new Actions(getDriver());
    }

    public int getImplicitlyWaitTimeout() {
        return getBrowser().getImplicitlyWaitTimeout();
    }

    public int getPageLoadTimeout() {
        return getBrowser().getPageLoadTimeout();
    }

    public int getElementLoadTimeout() {
        return getBrowser().getElementLoadTimeout();
    }

    public void setDefaultImplicitWaitTimeout() {
        getBrowser().setDefaultImplicitWaitTimeout();
    }

    /**
     * Gets the current page title.
     *
     * @return the title of the current page as a {@link String}.
     */
    protected String getTitle() {
        return getDriver().getTitle();
    }

    /**
     * Gets the current URL of the browser.
     *
     * @return the current URL.
     */
    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    /**
     * Refreshes the current page.
     */
    public AbstractPage<B, D> refresh() {
        log.info("Refresh the page.");
        getDriver().navigate().refresh();
        return this;
    }

    /**
     * Get the source of the last loaded page and check that current page contains expected text
     *
     * @param text the text to check for.
     * @return true if this page contains expected text, false otherwise
     */
    public boolean containsText(final String text) {
        log.info("Check that current page contains [{}] text.", text);
        return getDriver().getPageSource().contains(text);
    }

    public boolean waitForPageToLoad() {
        // wait for JavaScript to load
        final ExpectedCondition<Boolean> jsLoad = driver ->
                jsExecuteScript("return document.readyState").toString().equals("complete");
        // wait for jQuery to load
        final ExpectedCondition<Boolean> jQueryLoad = driver -> {
            try {
                return ((Long) jsExecuteScript("return jQuery.active") == 0);
            } catch (WebDriverException e) {
                return true;
            }
        };
        final WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(getPageLoadTimeout()));
        return wait.until(jsLoad) && wait.until(jQueryLoad);
    }

    public AbstractPage<B, D> switchToWindow(final String windowNameOrHandle) {
        log.debug("Switch interaction to window [{}].", windowNameOrHandle);
        getDriver().switchTo().window(windowNameOrHandle);
        return this;
    }

    public AbstractPage<B, D> switchToFrame(final WebElement iframeElement) {
        log.debug("Switch interaction to frame element.");
        getDriver().switchTo().frame(iframeElement);
        return this;
    }

    public AbstractPage<B, D> switchToFrame(final int iframeIndex) {
        log.debug("Switch interaction to frame with index [{}].", iframeIndex);
        getDriver().switchTo().frame(iframeIndex);
        return this;
    }

    public AbstractPage<B, D> switchToFrame(final String iframeName) {
        log.debug("Switch interaction to frame [{}].", iframeName);
        try {
            getDriver().switchTo().frame(iframeName);
        } catch (NoSuchFrameException e) {
            log.error(e.getMessage(), e);
            throw new NoSuchElementException("[Page] Iframe block with name [" +iframeName+ "] is NOT found!");
        }
        return this;
    }

    /**
     * Switches the driver focus to the default content.
     */
    public void switchToDefaultContent() {
        log.info("Switch interaction to default content.");
        getDriver().switchTo().defaultContent();
    }

    /**
     * Scrolls the page by the given x and y coordinates.
     *
     * @param x the x-coordinate to scroll to.
     * @param y the y-coordinate to scroll to.
     */
    public AbstractPage<B, D> scrollByCoords(final int x, final int y) {
        jsExecuteScript("window.scrollBy(" + x + "," + y + ")");
        return this;
    }



    //Element interaction methods

    /**
     * Moves the mouse to the middle of the element. The element is scrolled into view.
     *
     * @param element the element to move to.
     */
    public AbstractPage<B, D> moveToElement(final WebElement element) {
        getActions().moveToElement(element).perform();
        return this;
    }

    /**
     * Scrolls the page to the given element.
     *
     * @param element the element to scroll to.
     */
    public AbstractPage<B, D> scrollTo(final WebElement element) {
        jsExecuteScript("arguments[0].scrollIntoView(true);", element);
        return this;
    }

    /**
     * The same as {@link #moveToElement(WebElement)}.
     * Moves the mouse to the middle of the element. The element is scrolled into view.
     *
     * @param element the element to move to.
     */
    public AbstractPage<B, D> mouseHover(final WebElement element) {
        return moveToElement(element);
    }

    /**
     * Gets the first matching element on the current page.
     *
     * @param by the By locator of the element.
     * @return the WebElement.
     * @throws ElementNotFoundException if the element is not found.
     * @see WebDriver#findElement(By)
     */
    protected WebElement getElement(final By by) {
        try {
            return getDriver().findElement(by);
        } catch (WebDriverException e) {
            log.error(e.getMessage(), e);
        }
        throw new ElementNotFoundException("Element is NOT found!");
    }

    /**
     * Gets the first matching element for the given parent element.
     *
     * @param parentElement the parent element.
     * @param by            the By locator of the element.
     * @return the WebElement.
     * @throws ElementNotFoundException if the element is not found.
     */
    protected WebElement getElement(final @NonNull WebElement parentElement, final By by) {
        try {
            return parentElement.findElement(by);
        } catch (WebDriverException e) {
            log.error(e.getMessage(), e);
        }
        throw new ElementNotFoundException("Element is NOT found!");
    }


    /**
     * Gets a list of WebElements.
     *
     * @param by the By locator of the elements.
     * @return the list of WebElements.
     * @see WebElement#findElements(By)
     */
    protected List<WebElement> getElements(By by) {
        return getDriver().findElements(by);
    }

    /**
     * Checks that an element is present in the DOM of a page. This does not
     * necessarily mean that the element is visible.
     *
     * @return element is present or not
     */
    public boolean isPresent(final WebElement element) {
        try {
            if (element != null) {
                return true;
            }
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            log.debug(e.getMessage(), e);
            return false;
        }
        return false;
    }

    /**
     * Checks that an element which should be present in DOM is visible, method
     * is an equivalent of isDisplayed().<br>
     * If element is not present in DOM this respectively is not visible.
     *
     * @return element is visible or not
     */
    public boolean isVisible(final WebElement element) {
        try {
            if (element.isDisplayed()) {
                return true;
            }
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            log.debug(e.getMessage(), e);
            return false;
        }
        return false;
    }

    /**
     * Checks that an element is displayed (present in DOM and visible), method
     * is an equivalent of isVisible().
     *
     * @return element is displayed or not
     */
    public boolean isDisplayed(final WebElement element) {
        return isPresent(element) && isVisible(element);
    }

    public boolean isEnabled(final WebElement element) {
        log.info("Check if element is enabled.");
        return element.isEnabled();
    }

    public AbstractPage<B, D> clear(final WebElement element) {
        log.info("Clear the input.");
        element.clear();
        return this;
    }

    /**
     * Alternative way to clear an input when clear method doesn't remove the old value correctly
     */
    public AbstractPage<B, D> clearWithBackspace(final WebElement element) {
        log.info("Clear the input with backspace.");
        final String value = getValue(element);
        for (int i=0; i<value.length(); i++) {
            element.sendKeys(Keys.BACK_SPACE);
        }
        return this;
    }

    public AbstractPage<B, D> click(final WebElement element) {
        log.info("Click on the element.");
        element.click();
        return this;
    }

    public AbstractPage<B, D> doubleClick(final WebElement element) {
        log.info("Double click on the element.");
        getActions().doubleClick(element).build().perform();
        return this;
    }

    public AbstractPage<B, D> multipleClick(final WebElement element) {
        log.info("Multiple click on the element.");
        setImplicitWaitTimeout(0);
        for (int i=0; i<3; i++) {
            click(element);
        }
        setDefaultImplicitWaitTimeout();
        return this;
    }

    public AbstractPage<B, D> jsClick(final WebElement element) {
        jsExecuteScript("arguments[0].click();", element);
        return this;
    }

    public AbstractPage<B, D> sendKeys(final @NonNull WebElement element, final CharSequence... keys) {
        log.info("Send keys: " + Arrays.toString(keys));
        element.sendKeys(keys);
        return this;
    }

    public AbstractPage<B, D> setValue(final @NonNull WebElement element, final CharSequence... value) {
        log.info("Set value: " + Arrays.toString(value));
        WaitUtils.timeout(50, TimeUnit.MILLISECONDS);
        Arrays.toString(value).chars().forEach(c -> {
            element.sendKeys(String.valueOf((char) c));
            WaitUtils.timeout(10, TimeUnit.MILLISECONDS);
        });
        return this;
    }

    public AbstractPage<B, D> setValue(final @NonNull WebElement element, final File file) {
        log.info("Set value: " + file.getPath());
        if (getDriver() instanceof RemoteWebDriver) {
            ((RemoteWebDriver) getDriver()).setFileDetector(new LocalFileDetector());
        }
        WaitUtils.timeout(50, TimeUnit.MILLISECONDS);
        element.sendKeys(file.getAbsolutePath());
        WaitUtils.timeout(10, TimeUnit.MILLISECONDS);
        return this;
    }

    /**
     * Gets element tag name
     * @return tag name
     */
    public String getTagName(final WebElement element) {
        return element.getTagName();
    }

    /**
     * Gets element value
     * @return value of the element "value" attribute
     */
    public String getValue(final WebElement element) {
        return element.getAttribute("value");
    }

    /**
     * Gets element specific attribute value
     * @param attribute name {@link String}
     * @return value of the specified element attribute as {@link String}
     */
    public String getSpecificAttributeValue(final WebElement element, final String attribute) {
        return element.getAttribute(attribute);
    }

    /**
     * Get the visible (not hidden) text of the element, including sub-elements.
     * @return text {@link String}
     */
    public String getText(final WebElement element) {
        return element.getText();
    }

    public boolean hasText(final WebElement element, final String text) {
        return getText(element).equals(text);
    }

    public boolean containsText(final WebElement element, final String text) {
        return getText(element).contains(text);
    }


    @Deprecated
    protected void sleep(int timeoutInMilliseconds) {
        try {
            Thread.sleep(timeoutInMilliseconds);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Switches the driver focus to the desired frame.
     * It will log an error message if the desired frame cannot be found.
     * @param newFrameName the frame to switchTo.
     * @param timeoutInMilliseconds the max time to wait for the frame to be available and switch to it.
     */
    @Deprecated
    protected void switchToFrame(String newFrameName, int timeoutInMilliseconds) {
        final String startMessage = "Start switching to frame with name: ";
        final String finishMessage = "Finished switching to frame with name: ";
        log.info(startMessage + newFrameName);
        setImplicitWaitTimeout(Duration.ZERO);
        try {
            FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.getDriver())
                    .withTimeout(Duration.ofMillis(timeoutInMilliseconds))
                    .pollingEvery(Duration.ofMillis(POLLING_TIME))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchFrameException.class);
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(newFrameName));
            log.info(finishMessage + newFrameName);
        } catch (TimeoutException e) {
            log.error(e.getMessage(), e);
            getDriver().switchTo().defaultContent();
        }
        setDefaultImplicitWaitTimeout();
    }

    /**
     * Switches the driver focus to the desired frame.
     * It will log an error message if the desired frame cannot be found.
     * @param frameWebElement the frame to switchTo.
     * @param timeoutInMilliseconds the max time to wait for the frame to be available and switch to it.
     */
    @Deprecated
    protected void switchToFrame(WebElement frameWebElement, int timeoutInMilliseconds) {
        final String startMessage = "Start switching to frame with name: ";
        final String finishMessage = "Finished switching to frame with name: ";
        log.info(startMessage);
        setImplicitWaitTimeout(Duration.ZERO);
        try {
            FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.getDriver())
                    .withTimeout(Duration.ofMillis(timeoutInMilliseconds))
                    .pollingEvery(Duration.ofMillis(POLLING_TIME))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchFrameException.class);
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameWebElement));
            log.info(finishMessage);
        } catch (TimeoutException e) {
            log.error(e.getMessage(), e);
            getDriver().switchTo().defaultContent();
        }
        setDefaultImplicitWaitTimeout();
    }

    /*@Deprecated
    public void moveToElementJavaScript(WebElement element) {
        final String javaScript = "var element = arguments[0];"
                + "var mouseEventObj = document.createEvent('MouseEvents');"
                + "mouseEventObj.initEvent( 'mouseover', true, true );"
                + "element.dispatchEvent(mouseEventObj);";
        final String moveFinished = "JavaScript move to OK";
        jsExecuteScript(javaScript, element);
        log.info(moveFinished);
    }*/


    /*private void markElement(WebElement element, String javascriptColor) {
        try {
            if (getDriver() instanceof JavascriptExecutor) {
                jsExecuteScript(javascriptColor, element);
            }
        } catch (NoSuchElementException noSuchElementException) {
            logError(NO_SUCH_ELEMENT_EXCEPTION);
        }
    }*/

    protected void selectListItem(WebElement element, String itemValue) {
        checkElementPreAction(element);
        Select list = new Select(element);
        if (element.getText().contains(itemValue)) {
            list.selectByVisibleText(itemValue);
            log.info(itemValue + " value selected from list.");
        } else {
            log.error(itemValue + " value not found in list. Possible values: " + element.getText());
        }
    }

    protected void selectListItemByValue(WebElement element, String itemValue) {
        checkElementPreAction(element);
        Select list = new Select(element);
        if (element.getAttribute(WebElementAttributes.INNER_HTML.value()).contains(itemValue)) {
            list.selectByValue(itemValue);
            log.info(itemValue + " value selected from list.");
        } else {
            log.error(itemValue + " value not found in list. Possible values: " + element.getAttribute(WebElementAttributes.INNER_HTML.value()));
        }
    }

    @Deprecated
    public void clickAction(WebElement element) {
        checkElementPreAction(element);
        waitForElementToBeClickable(element, MIN_WAIT_TIME);
        getActions().click(element).build().perform();
        log.info("clicked OK");
    }

    /*@Deprecated
    public void clickJavaScript(WebElement element) {
        final String JAVA_SCRIPT_CLICK = "arguments[0].click();";
        final String finishedClick = "JavaScript click OK";
        checkElementPreAction(element);
        waitForElementToBeClickable(element, MIN_WAIT_TIME);
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript(JAVA_SCRIPT_CLICK, element);
        log.info(finishedClick);
    }*/



    /*@Deprecated
    protected void sendKeysToPresentElement(@NonNull WebElement element, String keys) {
        waitForElementPresence(element);
        log.info("Send keys: " + keys);
        element.sendKeys(keys);
    }*/

    protected void dragAndDrop(WebElement origin, WebElement destination) {
        checkElementPreAction(origin);
        checkElementPreAction(destination);
        getActions().dragAndDrop(origin, destination).perform();
    }

    /**
     * Will wait for the element to be present then check if it is currently displayed.
     * <p><b>!Element must be present in the DOM!</b></p>
     * @param element to be checked.
     * @return true if the element is displayed, false if it isn't.
     */
    /*protected boolean isDisplayed(WebElement element) {
        boolean displayed;
        try {
            displayed = element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException noSuchElementException) {
            displayed = false;
        }
        return displayed;
    }*/


    /**
     * Will wait for the element to be present then check if it is currently displayed.
     * <p><b>!Element must be present in the DOM!</b></p>
     * @param element to be checked.
     * @param waitTimeoutInSeconds the maximum amount of time to wait for the element to be displayed.
     * Set to 0 to remove the implicit wait.
     * @return true if the element is displayed, false if it isn't.
     */
    @Deprecated
    public boolean isDisplayed(WebElement element, long waitTimeoutInSeconds) {
        boolean displayed;
        setImplicitWaitTimeout(Duration.ofSeconds(waitTimeoutInSeconds));
        displayed = isDisplayed(element);
        setDefaultImplicitWaitTimeout();
        return displayed;
    }

    /**
     * Will wait for the element to be present then check if it is currently displayed.
     * <p><b>!Element must be present in the DOM!</b></p>
     * @param element to be checked.
     * @param waitTimeoutInSeconds the maximum amount of time to wait for the element to be displayed.
     * Set to 0 to remove the implicit wait.
     * @return true if the element is not displayed, false if it is.
     */
    @Deprecated
    protected boolean isNotDisplayed(WebElement element, long waitTimeoutInSeconds) {
        return !isDisplayed(element, waitTimeoutInSeconds);
    }

    /**
     * Checks if the element isSelected().
     *  <p>#see {@link WebElement#isSelected()}</p>
     * @param element The element to check.
     * @return true if the element is selected, false if it isn't.
     */
    public boolean isSelected(WebElement element) {
        boolean selected = false;
        //getByFromWebElement(element);
        waitForElementPresence(element);
        selected = element.isSelected();
        final String messageSelected = "Element selected = ";
        log.info(messageSelected + selected);
        return selected;
    }

    protected List<String> getText(List<WebElement> elements) {
        List<String> returnList = new ArrayList<>();
        for (WebElement element : elements) {
            returnList.add(getText(element));
        }
        return returnList;
    }

    @Deprecated
    protected String getInnerHtml(WebElement element) {
        String attribute = "innerHTML";
        return getSpecificAttributeValue(element, attribute);
    }

    @Deprecated
    protected String getOuterHtml(WebElement element) {
        String attribute = "outerHTML";
        return getSpecificAttributeValue(element, attribute);
    }

    protected String alertAccept() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(getImplicitlyWaitTimeout()));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = getDriver().switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }

    protected String alertDismiss() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(getImplicitlyWaitTimeout()));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = getDriver().switchTo().alert();
        String alertText = alert.getText();
        alert.dismiss();
        return alertText;
    }

    /*protected String getAttribute(WebElement element, String attribute) {
        String attributeValue = "";
        checkElementPreAction(element);
        attributeValue = element.getAttribute(attribute);
        final String messageText = "Attribute value extracted = ";
        testCaseReport.logMessage(LogStatus.INFO, messageText + attributeValue);
        return attributeValue;
    }*/






    /**
     * Gets a list of WebElements.
     * <p>Will wait implicitly based on <b>{@code timeoutMilliseconds}</b></p>
     * #see {@link WebElement#findElements(By)}
     * @param by the By locator of the elements.
     * @param timeoutMilliseconds the time to wait for the elements to be present.
     * @return the list of WebElements.
     */
    @Deprecated
    protected List<WebElement> getElements(By by, long timeoutMilliseconds) {
        setImplicitWaitTimeout(Duration.ofMillis(timeoutMilliseconds));
        List<WebElement> elements = getElements(by);
        setDefaultImplicitWaitTimeout();
        return elements;
    }

    /**
     * Gets a list of WebElements from a parent element.
     * <p>Will wait implicitly based on <b>{@code timeoutMilliseconds}</b></p>
     * <p>Will wait for the parentElement to be present</p>
     * #see {@link WebElement#findElements(By)}
     * @param by the By locator of the elements.
     * @param parentElement the parent element.
     * @return the list of WebElements.
     */
    protected List<WebElement> getElements(By by, WebElement parentElement, long timeoutMilliseconds) {
        if (waitForElementPresence(parentElement, timeoutMilliseconds + MIN_WAIT_TIME)) {
            setImplicitWaitTimeout(Duration.ofMillis(timeoutMilliseconds));
            List<WebElement> elements = parentElement.findElements(by);
            setDefaultImplicitWaitTimeout();
            return elements;
        }
        log.warn("Couldn't find " + parentElement + " will return empty list");
        return new ArrayList<>();
    }

    /*protected WebElement getElementFromList(List<WebElement> listWebElements, int position) {
        int listSize = listWebElements.size();
        if (listSize == 0) {
            final String EMPTY = "List is empty";
            log.error(EMPTY);
            logWebDriverScreenShot(LogStatus.ERROR, EMPTY);
        }
        return listWebElements.get(position);
    }*/

    /*protected void logWebDriverHtmlCapture(String message) {
        log().htmlCapture(message, getPageSource());
        //testCaseReport.logHtmlCapture(logStatus, message, getDriver().getPageSource());
    }*/

    /*protected void logWebDriverScreenShot(LogStatus logStatus, String message) {
        testCaseReport = logWebDriverScreenShot(testCaseReport, logStatus, "logWebDriverScreenShot", message);
    }

    protected void logWebElementScreenShot(LogStatus logStatus, String message, WebElement element) {
        testCaseReport = logWebDriverScreenShot(testCaseReport, element, logStatus, message);
    }*/

    @Deprecated
    private boolean checkElementPreAction(WebElement element) {
        boolean result = false;
        //getByFromWebElement(element);
        try {
            if (waitForElementVisibility(element)) {
                result = true;
                /*if (EnvironmentProperties.getDebugMode()) {
                    final String captureHtml = "HTML capture: ";
                    final String captureImage = "IMAGE capture: ";
                    final String RED_COLOR = "arguments[0].style.border='3px solid red'";
                    final String BLACK_COLOR = "arguments[0].style.border='3px solid black'";
                    markElement(element, RED_COLOR);
                    logWebDriverHtmlCapture(LogStatus.INFO, captureHtml + webElementByToText());
                    logWebDriverScreenShot(LogStatus.INFO, captureImage + webElementByToText());
                    markElement(element, BLACK_COLOR);
                }*/
            }
        } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * <p>Waits for the element to be visible in the Page.</p>
     * <p>Polling time is 500ms.</p>
     * #see {@link ExpectedConditions#visibilityOf(WebElement)}
     * @param element the element to be visible
     * @param timeoutMilliseconds The maximum amount of time to wait for the element to be visible.
     * @return true if the WebElement is found before time elapses, false if it isn't found.
     */
    protected boolean waitForElementVisibility(WebElement element, long timeoutMilliseconds) {
        final long startTime = System.currentTimeMillis();
        if (timeoutMilliseconds < MIN_WAIT_TIME) {
            log.warn(MIN_TIME_MESSAGE + MIN_WAIT_TIME);
        }
        setImplicitWaitTimeout(Duration.ZERO);
        try {
            FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                    .withTimeout(Duration.ofMillis(timeoutMilliseconds))
                    .pollingEvery(Duration.ofMillis(POLLING_TIME))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class)
                    .ignoring(Exception.class);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException
                | UnreachableBrowserException e) {
            log.error(e.getLocalizedMessage());
        }
        long endTime = System.currentTimeMillis();
        setDefaultImplicitWaitTimeout();
        return (endTime - startTime) < timeoutMilliseconds;
    }

    /**
     * <p>Waits for the element to be visible in the Page.</p>
     * <p>The timeout is the default implicit wait.</p>
     * <p>Polling time is 500ms.</p>
     * #see {@link ExpectedConditions#visibilityOf(WebElement)}
     * @param element the element to be visible
     * @return true if the WebElement is found before time elapses, false if it isn't found.
     */
    protected boolean waitForElementVisibility(WebElement element) {
        return waitForElementVisibility(element, getImplicitlyWaitTimeout() * 1000L);
    }

    protected boolean waitForElementToBeClickable(WebElement element) {
        return waitForElementToBeClickable(element, getImplicitlyWaitTimeout() * 1000L);
    }

    protected boolean waitForElementToBeClickable(WebElement element, long timeoutMilliseconds) {
        final long startTime = System.currentTimeMillis();
        if (timeoutMilliseconds < MIN_WAIT_TIME) {
            log.warn(MIN_TIME_MESSAGE + MIN_WAIT_TIME);
        }
        setImplicitWaitTimeout(Duration.ZERO);
        try {
            FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                    .withTimeout(Duration.ofMillis(timeoutMilliseconds))
                    .pollingEvery(Duration.ofMillis(POLLING_TIME))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class)
                    .ignoring(Exception.class);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
            log.error(e.getLocalizedMessage());
        }
        long endTime = System.currentTimeMillis();
        setDefaultImplicitWaitTimeout();
        return (endTime - startTime) < timeoutMilliseconds;
    }

    /**
     * <p>Waits for the element to be invisible.</p>
     * <p>The timeout is the default implicit wait.</p>
     * <p>Polling time is 500ms.</p>
     * #see {@link ExpectedConditions#invisibilityOf(WebElement)}
     * @param element The element to be invisible.
     * @return true if the WebElement isn't found before time elapses, false if it is found.
     */
    public boolean waitForElementInvisibility(WebElement element) {
        return waitForElementInvisibility(element, getImplicitlyWaitTimeout() * 1000L);
    }

    /**
     * <p>Waits for the element to be invisible.</p>
     * <p>Polling time is 500ms.</p>
     * <p>Minimum wait time must be above 1.000ms</p>
     * #see {@link ExpectedConditions#invisibilityOf(WebElement)}
     * @param element The element to be invisible.
     * @param timeoutMilliseconds The maximum amount of time to wait for the element to be found.
     * @return true if the WebElement isn't found before time elapses, false if it is found.
     */
    public boolean waitForElementInvisibility(WebElement element, long timeoutMilliseconds) {
        boolean isInvisible;
        if (timeoutMilliseconds < MIN_WAIT_TIME) {
            log.warn(MIN_TIME_MESSAGE + MIN_WAIT_TIME);
        }
        setImplicitWaitTimeout(Duration.ZERO);
        try {
            FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                    .withTimeout(Duration.ofMillis(timeoutMilliseconds))
                    .pollingEvery(Duration.ofMillis(POLLING_TIME));
            wait.until(ExpectedConditions.invisibilityOf(element));
            isInvisible = true;
        } catch (TimeoutException e) {
            log.error(e.getLocalizedMessage());
            isInvisible = false;
        }
        setDefaultImplicitWaitTimeout();
        return isInvisible;
    }


    /**
     * <p>Waits for the By to be invisible or not be present in the DOM.</p>
     * <p>The timeout is the default implicit wait.</p>
     * <p>Polling time is 500ms.</p>
     * {@link ExpectedConditions#invisibilityOfElementLocated(By)}
     * @param by To be invisible.
     * @return true if the By isn't found before time elapses, false if it is found.
     */
    public boolean waitForElementInvisibility(By by) {
        return waitForElementInvisibility(by, getImplicitlyWaitTimeout() * 1000L);
    }

    /**
     * <p>Waits for the By to be invisible or not be present in the DOM.</p>
     * <p>Polling time is 500ms.</p>
     * <p>Minimum wait time must be above 1.000ms</p>
     * {@link ExpectedConditions#invisibilityOfElementLocated(By)}
     * @param by to be invisible.
     * @param timeoutMilliseconds The maximum amount of time to wait for the element to be found.
     * @return true if the By isn't found before time elapses, false if it is found.
     */
    public boolean waitForElementInvisibility(By by, long timeoutMilliseconds) {
        boolean isInvisible;
        if (timeoutMilliseconds < MIN_WAIT_TIME) {
            log.warn(MIN_TIME_MESSAGE + MIN_WAIT_TIME);
        }
        setImplicitWaitTimeout(Duration.ZERO);
        try {
            FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                    .withTimeout(Duration.ofMillis(timeoutMilliseconds))
                    .pollingEvery(Duration.ofMillis(POLLING_TIME));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            isInvisible = true;
        } catch (TimeoutException e) {
            isInvisible = false;
            log.error(e.getLocalizedMessage());
        }
        setDefaultImplicitWaitTimeout();
        return isInvisible;
    }

    /**
     * <p>Waits for the element to be present in the DOM.</p>
     * <p>The timeout is the default implicit wait.</p>
     * <p>Polling time is 500ms.</p>
     * #see {@link ExpectedConditions#presenceOfElementLocated(By)}
     * @param by The By locator of the element.
     * @return true if the WebElement is found before time elapses, false if it isn't found.
     */
    protected boolean waitForElementPresence(By by) {
        return waitForElementPresence(by, getImplicitlyWaitTimeout() * 1000L);
    }

    /**
     * <p>Waits for the element to be present in the DOM.</p>
     * <p>Polling time is 500ms.</p>
     * #see {@link ExpectedConditions#presenceOfElementLocated(By)}
     * @param by The By locator of the element.
     * @param timeoutMilliseconds The maximum amount of time to wait for the element to be found.
     * @return true if the WebElement is found before time elapses, false if it isn't found.
     */
    protected boolean waitForElementPresence(By by, long timeoutMilliseconds) {
        final long startTime = System.currentTimeMillis();
        if (timeoutMilliseconds < MIN_WAIT_TIME) {
            log.warn(MIN_TIME_MESSAGE + MIN_WAIT_TIME);
        }
        setImplicitWaitTimeout(Duration.ZERO);
        try {
            FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                    .withTimeout(Duration.ofMillis(timeoutMilliseconds))
                    .pollingEvery(Duration.ofMillis(POLLING_TIME))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class)
                    .ignoring(Exception.class);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
            log.error(e.getLocalizedMessage());
        }
        long stopTime = System.currentTimeMillis();
        setDefaultImplicitWaitTimeout();
        return (stopTime - startTime) < timeoutMilliseconds;
    }

    /**
     * Waits for the element to be present in the DOM. The timeout is the default implicit wait. Pooling time is 500ms.
     * #see {@link ExpectedConditions#presenceOfElementLocated(By)}
     * @param element The element to be found.
     * @return true if the WebElement is found before time elapses, false if it isn't found.
     */
    protected boolean waitForElementPresence(WebElement element) {
        return waitForElementPresence(element, getImplicitlyWaitTimeout() * 1000L);
    }

    /**
     * Waits for the element to be present in the DOM. Pooling time is 500ms.
     * #see {@link ExpectedConditions#presenceOfElementLocated(By)}
     * @param element The element to be found.
     * @param timeoutMilliseconds The maximum amount of time to wait for the element to be found.
     * @return true if the WebElement is found before time elapses, false if it isn't found.
     */
    protected boolean waitForElementPresence(WebElement element, long timeoutMilliseconds) {
        final long startTime = System.currentTimeMillis();
        if (timeoutMilliseconds < MIN_WAIT_TIME) {
            log.warn(MIN_TIME_MESSAGE + MIN_WAIT_TIME);
        }
        setImplicitWaitTimeout(Duration.ZERO);
        By by = getByFromWebElement(element);
        try {
            FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                    .withTimeout(Duration.ofMillis(timeoutMilliseconds))
                    .pollingEvery(Duration.ofMillis(POLLING_TIME))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class)
                    .ignoring(Exception.class);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
            log.error(e.getLocalizedMessage());
        }
        long stopTime = System.currentTimeMillis();
        setDefaultImplicitWaitTimeout();
        return (stopTime - startTime) < timeoutMilliseconds;
    }

    //TODO: remove this method
    @Deprecated
    private By getByFromWebElement(WebElement element) {
        final String stepName = "getByFromWebElement";
        final String elementStringFormatOne = "] -> ";
        final String elementStringFormatTwo = " 'By.";
        if (element == null) {
            log.error("Null element");
        } else {
            String data = element.toString();
            if (data == null) {
                log.error("Null element.toString()");
            } else {
                if (data.contains(elementStringFormatOne)) {
                    data = data.split(elementStringFormatOne)[1];
                } else if (data.contains(elementStringFormatTwo)) {
                    data = data.split(elementStringFormatTwo)[1];
                } else {
                    log.error("Invalid By extracted: '" + data + "'");
                }
                final String delimiter = ": ";
                final int delimiterIndex = data.indexOf(delimiter);
                byType = data.substring(0, delimiterIndex);
                byValue = data.substring(delimiterIndex + delimiter.length(), data.length() - 1);
                switch (byType) {
                    case "xpath":
                        return By.xpath(byValue);
                    case "css selector":
                        return By.cssSelector(byValue);
                    case "id":
                        return By.id(byValue);
                    case "tag name":
                        return By.tagName(byValue);
                    case "name":
                        return By.name(byValue);
                    case "link text":
                        return By.linkText(byValue);
                    case "class name":
                        return By.className(byValue);
                    default:
                        log.error("Undefined or not well transformed By from " + data + " to " + byType);
                }
            }
        }
        return (By) element;
    }

    private void logError(String errorMessage) {
        log.error(errorMessage);
        log().htmlCode(errorMessage, getPageSource());
        log().image(errorMessage, takeScreenshot());
    }


    @Deprecated
    protected String getTextJavaScript(WebElement element) {
        checkElementPreAction(element);
        return String.valueOf(((JavascriptExecutor) getDriver()).executeScript("return arguments[0].value;", element));
    }

    @Deprecated
    protected void checkText(WebElement webElementToCheckText, String expectedValue) {
        log.info("Check element text - actual: " + getText(webElementToCheckText) + " expected: " + expectedValue);
        assertThat(getText(webElementToCheckText))
                .as("Check element text - actual: " + getText(webElementToCheckText) + " expected: " + expectedValue)
                .isEqualTo(expectedValue);
    }

    /*protected void checkSelectValue(String expectedValue, WebElement selectWebElementToCheckValue) {
        final String CHECK_VALUES = "WebElement check text value: ";
        checkElementPreAction(selectWebElementToCheckValue);
        Select select = new Select(selectWebElementToCheckValue);
        WebElement option = select.getFirstSelectedOption();
        testCaseReport.assertEquals(CHECK_VALUES, expectedValue, getText(option));
    }*/

    private String getAttachmentsFolderFilePath() {
        return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath()
                + "attachments" + "/";
    }

    @Deprecated
    protected void uploadFile(@NonNull WebElement element, String fileName) {
        log.info("String upload of file " + fileName);
        String filePath = getAttachmentsFolderFilePath() + fileName;
        ((RemoteWebDriver) getDriver()).setFileDetector(new LocalFileDetector());
        waitForElementPresence(element);
        element.sendKeys(filePath);
        log.info("Upload of file " + fileName + " finished");
    }

    protected void webElementScreenshot(WebElement element, String resultPath) throws IOException {
        Screenshot screenshot = new AShot().coordsProvider(
                new WebDriverCoordsProvider()).takeScreenshot(getDriver(), element);
        File result = new File(resultPath);
        ImageIO.write(screenshot.getImage(), "png", result);
        log().image("Screenshot: ", result);
    }

    protected void takePageScreenshot(String finalPath) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) getDriver();
        File result = new File(finalPath);
        ImageIO.write(ImageIO.read(ts.getScreenshotAs(OutputType.FILE)),
                "png", result);
    }

    protected void compareWebElementsByImageDiff(File originWebElement, File baseWebElement, String resultPath)
            throws IOException {
        log().image("Base WebElement: ", baseWebElement);
        log().image("Compare Image: ", originWebElement);
        BufferedImage baImage = ImageIO.read(baseWebElement);
        BufferedImage coImage = ImageIO.read(originWebElement);
        ImageDiffer imageDiffer = new ImageDiffer();
        ImageDiff diff = imageDiffer.makeDiff(baImage, coImage);
        File result = new File(resultPath);
        ImageIO.write(diff.getMarkedImage(), "png", result);
        log().image("Comparison Result: ", result);
        double differencePercent = 0;
        if (diff.hasDiff()) {
            differencePercent = new BigDecimal(Double.toString(getDifferencePercent(baImage, coImage))).setScale(2,
                    RoundingMode.HALF_UP).doubleValue();
        }

        log.info("Percentage difference between images: " + differencePercent * 100 + ", actual: " + diff.hasDiff() + ", expected: false");
        assertThat(diff.hasDiff())
                .as("Percentage difference between images: " + differencePercent * 100 + ", actual: " + diff.hasDiff() + ", expected: false")
                .isEqualTo(false);
    }

    protected double getDifferencePercent(BufferedImage img1, BufferedImage img2) {
        int width = img1.getWidth();
        int height = img1.getHeight();
        int width2 = img2.getWidth();
        int height2 = img2.getHeight();
        if (width != width2 || height != height2) {
            throw new IllegalArgumentException(String.format(
                    "Images must have the same dimensions: (%d,%d) vs. (%d,%d)", width, height,
                    width2, height2));
        }

        long diff = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                diff += pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y));
            }
        }
        long maxDiff = 3L * 255 * width * height;

        return 100.0 * diff / maxDiff;
    }

    private static int pixelDiff(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >> 8) & 0xff;
        int b1 = rgb1 & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >> 8) & 0xff;
        int b2 = rgb2 & 0xff;
        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }


    /*public void waitForJavaScriptLoad(RemoteWebDriver remoteWebDriver) {
        try {
            waitUntilJQueryReady(remoteWebDriver);
            waitUntilAngularReady(remoteWebDriver);
            waitUntilFramesJQueryReady(remoteWebDriver);
        } catch (Exception wde) {
            //testCaseReport.logMessage(LogStatus.INFO, wde, "waitForJavaScriptLoad");
            //sleep(NO_JS_WAIT);
        }
    }*/

    /*private void waitForJQueryLoad(RemoteWebDriver remoteWebDriver) {
        boolean jqueryReady = (Boolean) remoteWebDriver.executeScript("return jQuery.active==0");
        if (!jqueryReady) {
            WebDriverWait wait = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(getImplicitlyWaitTimeout()));
            ExpectedCondition<Boolean> jqueryload = driver -> ((Long) remoteWebDriver.executeScript("return jQuery.active") == 0);
            wait.until(jqueryload);
        }
    }*/

    /*private void waitForFrameJQueryLoad(RemoteWebDriver remoteWebDriver, int frameNumber) {
        boolean jqueryReady = (Boolean) remoteWebDriver.executeScript(JS_FRAME + frameNumber + "].jQuery.active==0");
        if (!jqueryReady) {
            WebDriverWait wait = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(getImplicitlyWaitTimeout()));
            ExpectedCondition<Boolean> jqueryload = driver -> ((Long) (remoteWebDriver).executeScript(JS_FRAME + frameNumber + "].jQuery.active") == 0);
            wait.until(jqueryload);
        }
    }

    private void waitForAngularLoad(RemoteWebDriver remoteWebDriver) {
        String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";
        boolean angularReady = Boolean.parseBoolean(remoteWebDriver.executeScript(angularReadyScript).toString());
        if (!angularReady) {
            WebDriverWait wait = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(getImplicitlyWaitTimeout()));
            ExpectedCondition<Boolean> angularLoad = driver -> Boolean.valueOf((remoteWebDriver).executeScript(angularReadyScript).toString());
            wait.until(angularLoad);
        }
    }*/

    /*private void waitUntilJSReady(RemoteWebDriver remoteWebDriver) {
        final String JS_READY_STATE = "return document.readyState";
        final String jsReadyStateComplete = "complete";
        boolean jsReady = remoteWebDriver.executeScript(JS_READY_STATE).toString().equals(jsReadyStateComplete);
        if (!jsReady) {
            WebDriverWait wait = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(getImplicitlyWaitTimeout()));
            ExpectedCondition<Boolean> jsLoad = driver -> (remoteWebDriver).executeScript(JS_READY_STATE).toString().equals(jsReadyStateComplete);
            wait.until(jsLoad);
        }
    }

    private void waitUntilFramesJSReady(int frameNumber, RemoteWebDriver remoteWebDriver) {
        final String jsReadyStateComplete = "complete";
        boolean jsReady = remoteWebDriver.executeScript(JS_FRAME + frameNumber + "].document.readyState").toString().equals(jsReadyStateComplete);
        if (!jsReady) {
            WebDriverWait wait = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(getImplicitlyWaitTimeout()));
            ExpectedCondition<Boolean> jsLoad = driver -> (remoteWebDriver).executeScript(JS_FRAME + frameNumber + "].document.readyState").toString().equals(jsReadyStateComplete);
            wait.until(jsLoad);
        }
    }*/

    /*private void waitUntilJQueryReady(RemoteWebDriver remoteWebDriver) {
        Boolean jquerydefined = (Boolean) remoteWebDriver.executeScript("return typeof jQuery != 'undefined'");
        if (jquerydefined) {
            //sleep(JS_WAIT);
            waitForJQueryLoad(remoteWebDriver);
            waitUntilJSReady(remoteWebDriver);
            //sleep(JS_WAIT);
        }
    }

    private void waitUntilFramesJQueryReady(RemoteWebDriver remoteWebDriver) {
        String numIFrames = remoteWebDriver.executeScript("return window.length").toString();
        int iframes = Integer.parseInt(numIFrames);
        int i = 0;
        if (iframes > 0) {
            while (i < iframes) {
                //sleep(JS_WAIT);
                waitUntilFramesJSReady(i, remoteWebDriver);
                //sleep(JS_WAIT);
                Boolean jquerydefined = (Boolean) remoteWebDriver.executeScript("return (typeof window.frames[" + i + "].jQuery) != 'undefined'");
                if (jquerydefined) {
                    //sleep(JS_WAIT);
                    waitForFrameJQueryLoad(remoteWebDriver, i);
                    //sleep(JS_WAIT);
                }
                i++;
            }
        }
    }*/

    /*private void waitUntilAngularReady(RemoteWebDriver remoteWebDriver) {
        Boolean angularUnDefined = (Boolean) remoteWebDriver.executeScript("return window.angular === undefined");
        if (!angularUnDefined) {
            Boolean angularInjectorUnDefined = (Boolean) remoteWebDriver.executeScript("return angular.element(document).injector() === undefined");
            if (!angularInjectorUnDefined) {
                //sleep(JS_WAIT);
                waitForAngularLoad(remoteWebDriver);
                waitUntilJSReady(remoteWebDriver);
                //sleep(JS_WAIT);
            }
        }
    }*/
}