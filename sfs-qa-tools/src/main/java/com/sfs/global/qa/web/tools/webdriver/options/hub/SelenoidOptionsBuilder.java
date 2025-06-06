package com.sfs.global.qa.web.tools.webdriver.options.hub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.AbstractDriverOptions;

import com.sfs.global.qa.web.tools.webdriver.BrowserOptions;

public class SelenoidOptionsBuilder {
    private String wdHubUrl;
    private Map<String, Object> selenoidOptions;
    private AbstractDriverOptions driverOptions;

    public SelenoidOptionsBuilder(final String wdHubUrl, final BrowserOptions browserOptions) {
        this.wdHubUrl = wdHubUrl;
        this.selenoidOptions = new HashMap<>();
        this.driverOptions = browserOptions.getDriverOptions();
    }

    public SelenoidOptionsBuilder platformName(final String platformName) {
        selenoidOptions.put("platformName", platformName);
        return this;
    }

    public SelenoidOptionsBuilder browserName(final String browserName) {
        selenoidOptions.put("browserName", browserName);
        return this;
    }

    public SelenoidOptionsBuilder browserVersion(final String browserVersion) {
        selenoidOptions.put("browserVersion", browserVersion);
        return this;
    }

    public SelenoidOptionsBuilder screenResolution(final String screenResolution) {
        selenoidOptions.put("screenResolution", screenResolution);
        return this;
    }

    public SelenoidOptionsBuilder enableVNC(final boolean enableVNC) {
        selenoidOptions.put("enableVNC", enableVNC);
        return this;
    }

    public SelenoidOptionsBuilder enableVideo(final boolean enableVideo) {
        selenoidOptions.put("enableVideo", enableVideo);
        return this;
    }

    public SelenoidOptionsBuilder videoName(final String videoName) {
        selenoidOptions.put("videoName", videoName);
        return this;
    }

    public SelenoidOptionsBuilder videoScreenSize(final String screenSize) {
        selenoidOptions.put("videoScreenSize", screenSize);
        return this;
    }

    public SelenoidOptionsBuilder videoFrameRate(final int frameRate) {
        selenoidOptions.put("videoFrameRate", frameRate);
        return this;
    }

    public SelenoidOptionsBuilder videoCodec(final String codec) {
        selenoidOptions.put("videoCodec", codec);
        return this;
    }

    public SelenoidOptionsBuilder enableLog(final boolean enableLog) {
        selenoidOptions.put("enableLog", enableLog);
        return this;
    }

    public SelenoidOptionsBuilder logName(final String logName) {
        selenoidOptions.put("logName", logName);
        return this;
    }

    public SelenoidOptionsBuilder timeZone(final String timeZone) {
        selenoidOptions.put("timezone", timeZone);
        return this;
    }

    public SelenoidOptionsBuilder sessionTimeout(final String timeout) {
        selenoidOptions.put("sessionTimeout", timeout);
        return this;
    }

    public SelenoidOptionsBuilder env(final List<String> environmentVars) {
        selenoidOptions.put("env", environmentVars);
        return this;
    }

    public SelenoidOptionsBuilder labels(final Map<String, String> labels) {
        selenoidOptions.put("labels", labels);
        return this;
    }

    public SelenoidOptionsBuilder buildName(final String buildName) {
        selenoidOptions.put("build", buildName);
        return this;
    }

    public SelenoidOptionsBuilder testName(final String testName) {
        selenoidOptions.put("name", testName);
        return this;
    }

    public SelenoidOptionsBuilder setSelenoidOption(final String optionName, final Object value) {
        selenoidOptions.put(optionName, value);
        return this;
    }
    
    public SelenoidOptionsBuilder mobileDeviceName(final String deviceName) {
        selenoidOptions.put("deviceName", deviceName);
        return this;
    }

    public SelenoidOptionsBuilder mobilePlatformVersion(final String platformVersion) {
        selenoidOptions.put("platformVersion", platformVersion);
        return this;
    }

    public SelenoidOptionsBuilder mobilePlatformName(final String platformName) {
        selenoidOptions.put("platformName", platformName);
        return this;
    }

    public SelenoidOptionsBuilder automationName(final String automationName) {
        selenoidOptions.put("automationName", automationName);
        return this;
    }

    public SelenoidOptionsBuilder appPackage(final String appPackage) {
        selenoidOptions.put("appPackage", appPackage);
        return this;
    }

    public SelenoidOptionsBuilder appActivity(final String appActivity) {
        selenoidOptions.put("appActivity", appActivity);
        return this;
    }

    public SelenoidOptionsBuilder app(final String appPath) {
        selenoidOptions.put("app", appPath);
        return this;
    }

    public BrowserOptions build() {
        driverOptions.setCapability("selenoid:options", selenoidOptions);
        return new BrowserOptions(wdHubUrl, driverOptions);
    }
}
