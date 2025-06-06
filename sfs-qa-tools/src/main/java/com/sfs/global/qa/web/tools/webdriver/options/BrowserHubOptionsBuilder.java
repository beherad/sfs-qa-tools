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

import com.sfs.global.qa.web.tools.webdriver.BrowserOptions;
import com.sfs.global.qa.web.tools.webdriver.options.hub.CustomGridOptionsBuilder;
import com.sfs.global.qa.web.tools.webdriver.options.hub.SauceLabsOptionsBuilder;
import com.sfs.global.qa.web.tools.webdriver.options.hub.SelenoidOptionsBuilder;

/**
 * Browser hub(s) options builder
 *
 * @author Alexander Rabetski
 */
public class BrowserHubOptionsBuilder {

    /**
     * Custom grid options builder instantiation
     *
     * @param customGridHubUrl
     * @param browserOptions
     */
    public CustomGridOptionsBuilder customGrid(final String customGridHubUrl, final BrowserOptions browserOptions) {
        return new CustomGridOptionsBuilder(customGridHubUrl, browserOptions);
    }

    /**
     * Selenoid options builder instantiation
     * @param selenoidHubUrl
     * @param browserOptions
     */
    public SelenoidOptionsBuilder selenoid(final String selenoidHubUrl, final BrowserOptions browserOptions) {
        return new SelenoidOptionsBuilder(selenoidHubUrl, browserOptions);
    }

    /**
     * Browserstack options builder instantiation
     * @param bsUsername
     * @param bsAuthKey
     * @param browserOptions
     */
    /*public BrowserStackOptionsBuilder browserstack(final String bsUsername, final String bsAuthKey, final BrowserOptions browserOptions) {
        return new BrowserStackOptionsBuilder(bsUsername, bsAuthKey, browserOptions);
    }*/

    /**
     * Saucelabs options builder instantiation
     *
     * @param slWdHubUrl
     * @param slUsername
     * @param slAuthKey
     * @param browserOptions
     */
    public SauceLabsOptionsBuilder saucelabs(final String slWdHubUrl, final String slUsername, final String slAuthKey, final BrowserOptions browserOptions) {
        return new SauceLabsOptionsBuilder(slWdHubUrl, slUsername, slAuthKey, browserOptions);
    }
}
