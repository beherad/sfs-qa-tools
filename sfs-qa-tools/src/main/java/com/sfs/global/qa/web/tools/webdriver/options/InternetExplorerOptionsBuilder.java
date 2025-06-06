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

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.ie.InternetExplorerOptions;

import com.sfs.global.qa.web.tools.webdriver.BrowserOptions;

/**
 * Internet Explorer options builder
 *
 * @author Alexander Rabetski
 */
public class InternetExplorerOptionsBuilder {

    private InternetExplorerOptions internetExplorerOptions;

    public InternetExplorerOptionsBuilder() {
        internetExplorerOptions = new InternetExplorerOptions();
    }

    public InternetExplorerOptionsBuilder setAcceptInsecureCerts(final boolean acceptInsecureCerts) {
        internetExplorerOptions.setAcceptInsecureCerts(acceptInsecureCerts);
        return this;
    }

    public InternetExplorerOptionsBuilder setUnhandledPromptBehaviour(final UnexpectedAlertBehaviour unexpectedAlertBehaviour) {
        internetExplorerOptions.setUnhandledPromptBehaviour(unexpectedAlertBehaviour);
        return this;
    }

    public InternetExplorerOptionsBuilder setCapability(final String capabilityName, final boolean value) {
        internetExplorerOptions.setCapability(capabilityName, value);
        return this;
    }

    public InternetExplorerOptionsBuilder setCapability(final String capabilityName, final String value) {
        internetExplorerOptions.setCapability(capabilityName, value);
        return this;
    }

    public InternetExplorerOptionsBuilder setCapability(final String capabilityName, final Object value) {
        internetExplorerOptions.setCapability(capabilityName, value);
        return this;
    }

    public BrowserOptions build() {
        return new BrowserOptions(internetExplorerOptions);
    }
}
