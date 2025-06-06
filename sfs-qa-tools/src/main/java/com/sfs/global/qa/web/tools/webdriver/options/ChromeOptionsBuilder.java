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
package com.sfs.global.qa.web.tools.webdriver.options;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;

import com.sfs.global.qa.web.tools.webdriver.BrowserOptions;

import java.io.File;
import java.util.List;

/**
 * Chrome options builder
 *
 * @author Alexander Rabetski
 */
public class ChromeOptionsBuilder {

    private ChromeOptions chromeOptions;

    public ChromeOptionsBuilder() {
        chromeOptions = new ChromeOptions();
    }

    public ChromeOptionsBuilder incognitoMode() {
        addArguments("--incognito");
        return this;
    }

    public ChromeOptionsBuilder disableInfobars() {
        addArguments("--disable-infobars");
        return this;
    }

    public ChromeOptionsBuilder disableSearchEngineSelection() {
        addArguments("--disable-search-engine-choice-screen");
        return this;
    }

    public ChromeOptionsBuilder setAcceptInsecureCerts(final boolean acceptInsecureCerts) {
        chromeOptions.setAcceptInsecureCerts(acceptInsecureCerts);
        return this;
    }

    public ChromeOptionsBuilder setUnhandledPromptBehaviour(final UnexpectedAlertBehaviour unexpectedAlertBehaviour) {
        chromeOptions.setUnhandledPromptBehaviour(unexpectedAlertBehaviour);
        return this;
    }



    public ChromeOptionsBuilder setLanguage(final String localeName) {
        chromeOptions.addArguments("--lang=" + localeName);
        return this;
    }

    /**
     * Sets the path to the Chrome executable.
     * @see ChromeOptions#setBinary(String)
     */
    public ChromeOptionsBuilder setBinary(final String binaryPath) {
        chromeOptions.setBinary(binaryPath);
        return this;
    }

    /**
     * Sets the proxy settings.
     * @see ChromeOptions#setProxy(Proxy)
     */
    public ChromeOptionsBuilder setProxy(final Proxy proxy) {
        chromeOptions.setProxy(proxy);
        return this;
    }

    /**
     * Sets the headless mode.
     * @since Selenium v4.8.0
     */
    public ChromeOptionsBuilder setHeadless() {
        addArguments("--headless=new");
        return this;
    }

    public ChromeOptionsBuilder addArguments(final String... arguments) {
        chromeOptions.addArguments(arguments);
        return this;
    }

    public ChromeOptionsBuilder setCapability(final String capabilityName, final boolean value) {
        chromeOptions.setCapability(capabilityName, value);
        return this;
    }

    public ChromeOptionsBuilder setCapability(final String capabilityName, final String value) {
        chromeOptions.setCapability(capabilityName, value);
        return this;
    }

    public ChromeOptionsBuilder setCapability(final String capabilityName, final Object value) {
        chromeOptions.setCapability(capabilityName, value);
        return this;
    }

    public ChromeOptionsBuilder addExtensions(final List<File> filepaths) {
        chromeOptions.addExtensions(filepaths);
        return this;
    }

    public ChromeOptionsBuilder setExperimentalOption(final String name, final Object value) {
        chromeOptions.setExperimentalOption(name, value);
        return this;
    }

    public BrowserOptions build() {
        return new BrowserOptions(chromeOptions);
    }
}
