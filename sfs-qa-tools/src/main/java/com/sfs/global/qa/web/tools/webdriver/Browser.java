package com.sfs.global.qa.web.tools.webdriver;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.sfs.global.qa.core.config.TafConfig;

import java.time.Duration;

@Slf4j
public class Browser extends AbstractBrowser<Browser, WebDriver> implements GenericBrowser {

    private static final int BROWSER_IMPLICITLYWAIT_TIMEOUT = Integer.parseInt(TafConfig.Web.BROWSER_IMPLICITLYWAIT_TIMEOUT.getValue());
    private static final int BROWSER_PAGELOAD_TIMEOUT = Integer.parseInt(TafConfig.Web.BROWSER_PAGELOAD_TIMEOUT.getValue());
    private static final int BROWSER_ELEMENTLOAD_TIMEOUT = Integer.parseInt(TafConfig.Web.BROWSER_ELEMENTLOAD_TIMEOUT.getValue());

    /**
     * Protected constructor to avoid direct instantiation & enforce the use of the builder pattern.
     * Required for WebDriver inheritance within page classes.
     */
    protected Browser() {
    }

    private Browser(final BrowserType browserType, final DesiredCapabilities extraCapabilities) {
        initWebDriver(browserType, null, null, extraCapabilities);
        setBrowser(this);
        setClazz(Browser.class);
    }

    private Browser(final BrowserType browserType, final BrowserOptions browserOptions, final DesiredCapabilities extraCapabilities) {
        initWebDriver(browserType, browserOptions.getWdHubUrl(), browserOptions.getDriverOptions(), extraCapabilities);
        setBrowser(this);
        setClazz(Browser.class);
    }

    public static BrowserBuilder builder(final BrowserType browserType) {
        return new BrowserBuilder(browserType);
    }

    @RequiredArgsConstructor
    public static class BrowserBuilder {

        @NonNull
        private BrowserType browserType;
        private BrowserOptions browserOptions;
        private DesiredCapabilities extraCapabilities;

        public BrowserBuilder browserOptions(final BrowserOptions browserOptions) {
            this.browserOptions = browserOptions;
            return this;
        }

        public BrowserBuilder extraCapabilities(final DesiredCapabilities extraCapabilities) {
            this.extraCapabilities = extraCapabilities;
            return this;
        }

        public Browser build() {
            if (browserOptions == null) {
                if (extraCapabilities == null) {
                    return new Browser(browserType, null);
                }
                return new Browser(browserType, extraCapabilities);
            } else {
                if (extraCapabilities == null) {
                    return new Browser(browserType, browserOptions, null);
                }
                return new Browser(browserType, browserOptions, extraCapabilities);
            }
        }
    }

    /**
     * Use this constructor in order to create a Browser with custom capabilities.
     * @param browserType The browser type.
     * @param wdHubUrl The local or remote WebDriver hub URL.
     * @param driverOptions The driver options.
     * @param extraCapabilities The extra capabilities.
     */
    @Synchronized
    private void initWebDriver(@NonNull final BrowserType browserType, final String wdHubUrl,
                               final AbstractDriverOptions driverOptions, final DesiredCapabilities extraCapabilities) {
        try {
            if (wdHubUrl == null || wdHubUrl.isEmpty()) {
                createLocalWebDriver(browserType, driverOptions, extraCapabilities);
            } else {
                createRemoteWebDriver(browserType, wdHubUrl, driverOptions, extraCapabilities);
            }
        } catch (WebDriverException e) {
            log.error(e.getMessage(), e);
        }
        // Setting browser timeouts
        setImplicitWaitTimeout(BROWSER_IMPLICITLYWAIT_TIMEOUT);
        setPageLoadTimeout(BROWSER_PAGELOAD_TIMEOUT);
    }

    @Override
    public int getImplicitlyWaitTimeout() {
        log.debug("Implicitly wait timeout is set to [{}] seconds", BROWSER_IMPLICITLYWAIT_TIMEOUT);
        return BROWSER_IMPLICITLYWAIT_TIMEOUT;
    }

    @Override
    public int getPageLoadTimeout() {
        log.debug("Page load timeout is set to [{}] seconds", BROWSER_PAGELOAD_TIMEOUT);
        return BROWSER_PAGELOAD_TIMEOUT;
    }

    @Override
    public int getElementLoadTimeout() {
        log.debug("Element load timeout is set to [{}] seconds", BROWSER_ELEMENTLOAD_TIMEOUT);
        return BROWSER_ELEMENTLOAD_TIMEOUT;
    }

    @Override
    public void setDefaultImplicitWaitTimeout() {
        log.debug("Default implicit wait timeout is set to [{}] seconds", BROWSER_IMPLICITLYWAIT_TIMEOUT);
        setImplicitWaitTimeout(Duration.ofSeconds(BROWSER_IMPLICITLYWAIT_TIMEOUT));
    }

    /*private void createRemoteWebDriver(final DesiredCapabilities desiredCapabilities) {
        *//*if (driverOptions != null) {
            createRemoteWebDriverWithCapabilities(driverOptions);
            return;
        }*//*
        //TODO it works ok even when getHostUrl() get null, so it is local.host.url necessary?
        //String seleniumProxyHost = getSeleniumProxy();
        //TODO ask why selenium proxy was only applied to FF and CH

        *//*Proxy proxy = new Proxy();
        if (seleniumProxyHost != null) {
            proxy.setHttpProxy(HTTP + seleniumProxyHost);
            proxy.setSslProxy(HTTP + seleniumProxyHost);
        }*//*

        //new Proxy().setHttpProxy(HTTP + seleniumProxyHost).setSslProxy(HTTP + seleniumProxyHost);

        switch (BrowserType.getByName("BROWSER_NAME")) {
            case IE:
                DesiredCapabilities capabilities = new DesiredCapabilities();
                *//*if (seleniumProxyHost != null) {
                    capabilities.setCapability(CapabilityType.PROXY, proxy);
                }*//*
                capabilities.setBrowserName(org.openqa.selenium.remote.Browser.IE.browserName());
                //Capability SUPPORTS_JAVASCRIPT, has been removed because is non W3C compliant.
                //ACCEPT_INSECURE_CERTS replaces the capability ACCEPT_SSL_CERTS
                //capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                //UNHANDLED_PROMPT_BEHAVIOUR replaces the capability SUPPORTS_ALERTS and UNEXPECTED_ALERT_BEHAVIOR
                //capabilities.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
                //Capability HAS_NATIVE_EVENTS, has been removed because is non W3C compliant.
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                        true);
                capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, false);
                capabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, false);
                capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                internetExplorerOptions = internetExplorerOptions.merge(capabilities);
                //Selenium default PageLoadStrategy is NORMAL
                //driver = new RemoteWebDriver(getHostUrl(), internetExplorerOptions);
                break;
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                *//*if (seleniumProxyHost != null) {
                    firefoxOptions.setCapability(CapabilityType.PROXY, proxy);
                }*//*
                firefoxOptions.addPreference("media.navigator.streams.fake", true);
                firefoxOptions.addPreference("permissions.default.camera", 1);
                firefoxOptions.addPreference("permissions.default.geo", 1);
                //Selenium default PageLoadStrategy is NORMAL
                //firefoxOptions.setAcceptInsecureCerts(true);
                firefoxOptions.setLogLevel(FirefoxDriverLogLevel.ERROR);
                //ACCEPT_INSECURE_CERTS replaces the capability ACCEPT_SSL_CERTS
                //firefoxOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                //Capability SUPPORTS_JAVASCRIPT, has been removed because is non W3C compliant.
                //UNHANDLED_PROMPT_BEHAVIOUR replaces the capability SUPPORTS_ALERTS and UNEXPECTED_ALERT_BEHAVIOR
                *//*firefoxOptions.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR,
                        UnexpectedAlertBehaviour.IGNORE);*//*
                //driver = new RemoteWebDriver(getHostUrl(), firefoxOptions);
                break;
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                *//*if (seleniumProxyHost != null) {
                    chromeOptions.setCapability(CapabilityType.PROXY, proxy);
                }*//*
                chromeOptions.addArguments("allow-file-access-from-files");
                //TODO: check!!
                //chromeOptions.addArguments(USE_FAKE_DEVICE_FOR_MEDIA_STREAM);
                //chromeOptions.addArguments(USE_FAKE_UI_FOR_MEDIA_STREAM);
                chromeOptions.addArguments("unlimited-storage");
                //UNHANDLED_PROMPT_BEHAVIOUR replaces the capability SUPPORTS_ALERTS and UNEXPECTED_ALERT_BEHAVIOR
                //chromeOptions.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
                //ACCEPT_INSECURE_CERTS replaces the capability ACCEPT_SSL_CERTS
                //chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                //Capability SUPPORTS_JAVASCRIPT, has been removed because is non W3C compliant.
                //driver = new RemoteWebDriver(getHostUrl(), chromeOptions);
                break;
            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                *//*if (seleniumProxyHost != null) {
                    edgeOptions.setCapability(CapabilityType.PROXY, proxy);
                }*//*
                //TODO: check!!
                *//*List<String> args = Arrays.asList(USE_FAKE_UI_FOR_MEDIA_STREAM, USE_FAKE_DEVICE_FOR_MEDIA_STREAM);
                Map<String, Object> map = new HashMap<>();
                map.put("args", args);
                edgeOptions.setCapability("ms:edgeOptions", map);*//*
                //Selenium default PageLoadStrategy is NORMAL
                //UNHANDLED_PROMPT_BEHAVIOUR replaces the capability SUPPORTS_ALERTS and UNEXPECTED_ALERT_BEHAVIOR
                //edgeOptions.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
                //ACCEPT_INSECURE_CERTS replaces the capability ACCEPT_SSL_CERTS
                //edgeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                //Capability SUPPORTS_JAVASCRIPT, has been removed because is non W3C compliant.
                //Capability HAS_NATIVE_EVENTS, has been removed because is non W3C compliant.
                //driver = new RemoteWebDriver(getHostUrl(), edgeOptions);
                break;
            *//*case ANDROID:
            case IOS:
                createAppiumDriver();
                break;*//*
            //TODO Understand what is AWS
            *//*case AWS:
                testCaseReport.logMessage(LogStatus.ERROR, "AWS Selected.");
                throw new IllegalArgumentException("Unsupported driver options type.");*//*
            default:
                //throw new IllegalArgumentException("Invalid browser name defined: " +BROWSER_NAME);
        }
        *//*SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
        SeleniumGrid seleniumGrid = new SeleniumGrid(HUB_URL);
        testCaseReport.logMessage(LogStatus.INFO,
                "Selenium Grid api session information: " + seleniumGrid
                        .getSeleniumGridSessionInformation(sessionId, (RemoteWebDriver) driver));*//*
    }*/

    /*private void createRemoteWebDriverWithCapabilities(Object driverOptions) {
        SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
        SeleniumGrid seleniumGrid = new SeleniumGrid(HUB_URL);
        testCaseReport.logMessage(LogStatus.INFO, "Selenium Grid api session information: " + seleniumGrid
                .getSeleniumGridSessionInformation(sessionId, (RemoteWebDriver) driver));*//*
    }*/

    /*private void createAppiumDriver() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        //ALL - common capabilities
        desiredCapabilities.setCapability(CapabilityType.PLATFORM_NAME, EnvironmentProperties.getDevicePlatformName());
        desiredCapabilities.setCapability(PLATFORM_VERSION, EnvironmentProperties.getDevicePlatformVersion());
        desiredCapabilities.setCapability(DEVICE_NAME, EnvironmentProperties.getDeviceName());
        if (EnvironmentProperties.isDeviceOrientationDefined()) {
            desiredCapabilities.setCapability(ORIENTATION, EnvironmentProperties.getDeviceOrientation());
        } else {
            desiredCapabilities.setCapability(ORIENTATION, "LANDSCAPE");
        }
        //Appium - orientation (Sim/Emu-only).LANDSCAPE or PORTRAIT. It works for real device in appium 1.20.2
        //SauceLabs - For virtual device mobile tests (deviceOrientation) for real device tests (orientation)
        //LANDSCAPE by default if property not defined

        if (EnvironmentProperties.getEnvironmentCloud()) {
            //Yes -> Saucelabs
            desiredCapabilities.setCapability("testobject_session_create_timeout", "300000");
            desiredCapabilities.setCapability("name", EnvironmentProperties.getTestRailProjectName());
            desiredCapabilities.setCapability("username", EnvironmentProperties.getCloudUserName());
            desiredCapabilities.setCapability("accessKey", EnvironmentProperties.getCloudAccessKey());
            if (EnvironmentProperties.isCloudTunnelNameDefined()) {
                desiredCapabilities.setCapability("tunnelIdentifier", EnvironmentProperties.getCloudTunnelName());
            }
            //TabletOnly or PhoneOnly
            if (EnvironmentProperties.isCloudTabletOnlyDefinedAndTrue()) {
                desiredCapabilities.setCapability("tabletOnly", "true");
            }
            if (EnvironmentProperties.isCloudPhoneOnlyDefinedAndTrue()) {
                desiredCapabilities.setCapability("phoneOnly", "true");
            }
        } else {
            //No -> local execution -> local machine or local network by Appium client
            //device browser not needed for Saucelabs real device, it will be native browser by default
            desiredCapabilities.setBrowserName(EnvironmentProperties.getDeviceBrowserName());
            desiredCapabilities
                    .setCapability("newCommandTimeout", EnvironmentProperties.getBrowserExecutionWaitTimeout());
            desiredCapabilities.setCapability("autoGrantPermissions", true);
            desiredCapabilities.setCapability(UDID, EnvironmentProperties.getDeviceUDID());

            if (EnvironmentProperties.getSelectedBrowser().equals(SupportedBrowsers.CH)) {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("allow-file-access-from-files");
                chromeOptions.addArguments(USE_FAKE_DEVICE_FOR_MEDIA_STREAM);
                chromeOptions.addArguments(USE_FAKE_UI_FOR_MEDIA_STREAM);
                //UNHANDLED_PROMPT_BEHAVIOUR replaces the capability SUPPORTS_ALERTS and UNEXPECTED_ALERT_BEHAVIOR
                chromeOptions.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
                //ACCEPT_INSECURE_CERTS replaces the capability ACCEPT_SSL_CERTS
                chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                //Capability SUPPORTS_JAVASCRIPT, has been removed because is non W3C compliant.
                chromeOptions = chromeOptions.merge(desiredCapabilities);
            }

            //capabilities for appium through Mac - XCUITest
            *//*if (EnvironmentProperties.getDevicePlatformName().equalsIgnoreCase(SupportedBrowsers.IOS.browserName)) {
                desiredCapabilities.setCapability("automationName", "XCUITest");
                desiredCapabilities.setCapability("xcodeOrgId", "J6LE3U4Z4Y");
                desiredCapabilities.setCapability("xcodeSigningId", "iPhone Developer");
                desiredCapabilities.setCapability("showXcodeLog", true);
                desiredCapabilities.setCapability("updatedWDABundleId", "com.behera.qa.WebDriverAgentRunner");
            }*//*
        }
        switch (EnvironmentProperties.getDevicePlatformName()) {
            case "android":
            case "ios":
                driver = new RemoteWebDriver(getHostUrl(), desiredCapabilities);
                break;
            default:
                testCaseReport.logMessage(LogStatus.ERROR, "device.platform.name property",
                        "Unsupported device.platform.name: " + EnvironmentProperties.getDevicePlatformName());
        }
    }*/

    public static void main(String[] args) {
        Browser browser = Browser.builder(BrowserType.CHROME)
                .browserOptions(BrowserOptions.chrome()
                        .incognitoMode()
                        .disableInfobars()
                        .setAcceptInsecureCerts(true)
                        .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE)
                        //.setProxy(...)
                        //.addArguments(...)
                        //.setCapability(...)
                        .build())
                //.extraCapabilities(new DesiredCapabilities())
                .build();

        browser.windowFocus()
               .windowMaximize()
               .openURL("https://www.google.com");

        browser.close();


        Browser customGridBrowser = Browser.builder(BrowserType.getByName(TafConfig.Web.BROWSER_NAME.getValue()))
                .browserOptions(BrowserOptions.hub()
                        .customGrid(TafConfig.Web.HUB_URL.getValue(), BrowserOptions.chrome()
                                .incognitoMode()
                                .disableInfobars()
                                .setAcceptInsecureCerts(true)
                                .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE)
                                //.setProxy(...)
                                //.addArguments(...)
                                //.setCapability(...)
                                .build())
                        .build())
                .build();

        customGridBrowser.close();

        Browser sauceLabsBrowser = Browser.builder(BrowserType.getByName(TafConfig.Web.BROWSER_NAME.getValue()))
                .browserOptions(BrowserOptions.hub()
                        .saucelabs(TafConfig.Web.HUB_URL.getValue(), "username", "password", BrowserOptions.chrome()
                                .incognitoMode()
                                .disableInfobars()
                                .setAcceptInsecureCerts(true)
                                .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE)
                                //.setProxy(...)
                                //.addArguments(...)
                                //.setCapability(...)
                                .build())
                        .platformName("Windows 10")
                        .browserName("chrome")
                        .browserVersion("latest")
                        .screenResolution("1920x1080")
                        .recordLogs(true)
                        .recordScreenshots(true)
                        .recordVideo(false)
                        //.setSauceOption(...)
                        .build())
                .build();

        sauceLabsBrowser.close();
    }
}