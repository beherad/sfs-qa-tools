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
import org.openqa.selenium.safari.SafariOptions;

import com.sfs.global.qa.web.tools.webdriver.BrowserOptions;

/**
 * Safari options builder
 *
 * @author Alexander Rabetski
 */
public class SafariOptionsBuilder {

    private SafariOptions safariOptions;

    public SafariOptionsBuilder() {
        safariOptions = new SafariOptions();
    }

    public SafariOptionsBuilder setAcceptInsecureCerts(final boolean acceptInsecureCerts) {
        safariOptions.setAcceptInsecureCerts(acceptInsecureCerts);
        return this;
    }

    public SafariOptionsBuilder setUnhandledPromptBehaviour(final UnexpectedAlertBehaviour unexpectedAlertBehaviour) {
        safariOptions.setUnhandledPromptBehaviour(unexpectedAlertBehaviour);
        return this;
    }

    /**
     * Sets the proxy settings.
     * @see SafariOptions#setProxy(Proxy)
     */
    public SafariOptionsBuilder setProxy(final Proxy proxy) {
        safariOptions.setProxy(proxy);
        return this;
    }

    public SafariOptionsBuilder setCapability(final String capabilityName, final boolean value) {
        safariOptions.setCapability(capabilityName, value);
        return this;
    }

    public SafariOptionsBuilder setCapability(final String capabilityName, final String value) {
        safariOptions.setCapability(capabilityName, value);
        return this;
    }

    public SafariOptionsBuilder setCapability(final String capabilityName, final Object value) {
        safariOptions.setCapability(capabilityName, value);
        return this;
    }

    public BrowserOptions build() {
        return new BrowserOptions(safariOptions);
    }
}
