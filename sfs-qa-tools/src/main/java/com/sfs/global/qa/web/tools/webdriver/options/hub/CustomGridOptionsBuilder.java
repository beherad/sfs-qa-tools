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

import org.openqa.selenium.remote.AbstractDriverOptions;

import com.sfs.global.qa.web.tools.webdriver.BrowserOptions;

/**
 * Custom grid options builder
 *
 * @author Alexander Rabetski
 */
public class CustomGridOptionsBuilder {

    private String wdHubUrl;
    private AbstractDriverOptions driverOptions;

    public CustomGridOptionsBuilder(final String wdHubUrl, final BrowserOptions browserOptions) {
        this.wdHubUrl = wdHubUrl;
        this.driverOptions = browserOptions.getDriverOptions();
    }

    public CustomGridOptionsBuilder setCapability(final String capabilityName, final boolean value) {
        driverOptions.setCapability(capabilityName, value);
        return this;
    }

    public CustomGridOptionsBuilder setCapability(final String capabilityName, final String value) {
        driverOptions.setCapability(capabilityName, value);
        return this;
    }

    public CustomGridOptionsBuilder setCapability(final String capabilityName, final Object value) {
        driverOptions.setCapability(capabilityName, value);
        return this;
    }

    public BrowserOptions build() {
        return new BrowserOptions(wdHubUrl, driverOptions);
    }

}
