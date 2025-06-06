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
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.sfs.global.qa.web.tools.webdriver.BrowserOptions;

/**
 * Firefox options builder
 *
 * @author Alexander Rabetski
 */
public class FirefoxOptionsBuilder {

    private FirefoxOptions firefoxOptions;

    public FirefoxOptionsBuilder() {
        firefoxOptions = new FirefoxOptions();
    }

    public FirefoxOptionsBuilder setAcceptInsecureCerts(final boolean acceptInsecureCerts) {
        firefoxOptions.setAcceptInsecureCerts(acceptInsecureCerts);
        return this;
    }

    public FirefoxOptionsBuilder setUnhandledPromptBehaviour(final UnexpectedAlertBehaviour unexpectedAlertBehaviour) {
        firefoxOptions.setUnhandledPromptBehaviour(unexpectedAlertBehaviour);
        return this;
    }

    public FirefoxOptionsBuilder setLanguage(final String localeName) {
        firefoxOptions.addPreference("intl.accept_languages", localeName);
        return this;
    }

    /**
     * Sets the path to the Firefox executable.
     * @see FirefoxOptions#setBinary(String)
     */
    public FirefoxOptionsBuilder setBinary(final String binaryPath) {
        firefoxOptions.setBinary(binaryPath);
        return this;
    }

    /**
     * Sets the profile.
     * @see FirefoxOptions#setProfile(FirefoxProfile)
     */
    public FirefoxOptionsBuilder setProfile(final FirefoxProfile profile) {
        firefoxOptions.setProfile(profile);
        return this;
    }

    /**
     * Sets the proxy settings.
     * @see FirefoxOptions#setProxy(Proxy)
     */
    public FirefoxOptionsBuilder setProxy(final Proxy proxy) {
        firefoxOptions.setProxy(proxy);
        return this;
    }

    public FirefoxOptionsBuilder addArguments(final String... arguments) {
        firefoxOptions.addArguments(arguments);
        return this;
    }

    public FirefoxOptionsBuilder addPreference(final String key, final String value) {
        firefoxOptions.addPreference(key, value);
        return this;
    }

    public FirefoxOptionsBuilder setCapability(final String capabilityName, final boolean value) {
        firefoxOptions.setCapability(capabilityName, value);
        return this;
    }

    public FirefoxOptionsBuilder setCapability(final String capabilityName, final String value) {
        firefoxOptions.setCapability(capabilityName, value);
        return this;
    }

    public FirefoxOptionsBuilder setCapability(final String capabilityName, final Object value) {
        firefoxOptions.setCapability(capabilityName, value);
        return this;
    }

    public BrowserOptions build() {
        return new BrowserOptions(firefoxOptions);
    }

}
