/*
 * Copyright (c) 2013-2022 CodeMinter, the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sfs.global.qa.web.tools.webdriver.options.hub;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.AbstractDriverOptions;

import com.sfs.global.qa.web.tools.webdriver.BrowserOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * SauceLabs options builder
 *
 * @author Alexander Rabetski
 */
public class SauceLabsOptionsBuilder {

    private String wdHubUrl;
    private Map<String, Object> sauceOptions;
    private AbstractDriverOptions driverOptions;


    public SauceLabsOptionsBuilder(final String wdHubUrl,final String username, final String authKey, final BrowserOptions browserOptions) {
        this.wdHubUrl = wdHubUrl;
        this.sauceOptions = new HashMap<>();
        this.driverOptions = browserOptions.getDriverOptions();

        sauceOptions.put("username", username);
        sauceOptions.put("accessKey", authKey);
    }

    public SauceLabsOptionsBuilder tunnelName(final String tunnelName) {
        sauceOptions.put("tunnelName", tunnelName);
        return this;
    }

    public SauceLabsOptionsBuilder platformName(final String platformName) {
        sauceOptions.put("platformName", platformName);
        return this;
    }

    public SauceLabsOptionsBuilder browserName(final String browserName) {
        sauceOptions.put("browserName", browserName);
        return this;
    }

    public SauceLabsOptionsBuilder browserVersion(final String browserVersion) {
        sauceOptions.put("browserVersion", browserVersion);
        return this;
    }

    public SauceLabsOptionsBuilder screenResolution(final String screenResolution) {
        sauceOptions.put("screenResolution", screenResolution);
        return this;
    }

    public SauceLabsOptionsBuilder recordLogs(final boolean enableLogsRecording) {
        sauceOptions.put("recordLogs", enableLogsRecording);
        return this;
    }

    public SauceLabsOptionsBuilder recordScreenshots(final boolean enableScreenshotsRecording) {
        sauceOptions.put("recordScreenshots", enableScreenshotsRecording);
        return this;
    }

    public SauceLabsOptionsBuilder recordVideo(final boolean enableVideoRecording) {
        sauceOptions.put("recordVideo", enableVideoRecording);
        return this;
    }

    public SauceLabsOptionsBuilder buildName(final String buildName) {
        sauceOptions.put("build", buildName);
        return this;
    }

    public SauceLabsOptionsBuilder testName(final String testName) {
        sauceOptions.put("name", testName);
        return this;
    }

    public SauceLabsOptionsBuilder setSauceOption(final String optionName, final Object value) {
        sauceOptions.put(optionName, value);
        return this;
    }


    /*public SauceLabsOptionsBuilder acceptSslCerts(final boolean acceptSslCerts) {
        driverOptions.setCapability("acceptSslCerts", acceptSslCerts);
        return this;
    }

    public SauceLabsOptionsBuilder setUnhandledPromptBehaviour(final UnexpectedAlertBehaviour unexpectedAlertBehaviour) {
        driverOptions.setUnhandledPromptBehaviour(unexpectedAlertBehaviour);
        return this;
    }

    *//**
     * Sets the proxy settings.
     * @see AbstractDriverOptions#setProxy(Proxy)
     *//*
    public SauceLabsOptionsBuilder setProxy(final Proxy proxy) {
        driverOptions.setProxy(proxy);
        return this;
    }

    public SauceLabsOptionsBuilder setCapability(final String capabilityName, final boolean value) {
        driverOptions.setCapability(capabilityName, value);
        return this;
    }

    public SauceLabsOptionsBuilder setCapability(final String capabilityName, final String value) {
        driverOptions.setCapability(capabilityName, value);
        return this;
    }

    public SauceLabsOptionsBuilder setCapability(final String capabilityName, final Object value) {
        driverOptions.setCapability(capabilityName, value);
        return this;
    }*/

    public BrowserOptions build() {
        driverOptions.setCapability("sauce:options", sauceOptions);
        return new BrowserOptions(wdHubUrl, driverOptions);
    }
}
