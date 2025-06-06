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
package com.sfs.global.qa.web.tools.webdriver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.remote.AbstractDriverOptions;

import com.sfs.global.qa.web.tools.webdriver.options.BrowserHubOptionsBuilder;
import com.sfs.global.qa.web.tools.webdriver.options.ChromeOptionsBuilder;
import com.sfs.global.qa.web.tools.webdriver.options.EdgeOptionsBuilder;
import com.sfs.global.qa.web.tools.webdriver.options.FirefoxOptionsBuilder;
import com.sfs.global.qa.web.tools.webdriver.options.InternetExplorerOptionsBuilder;
import com.sfs.global.qa.web.tools.webdriver.options.SafariOptionsBuilder;

/**
 * Browser options builder factories
 *
 * @author Alexander Rabetski
 */
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class BrowserOptions {

    private String wdHubUrl;
    @NonNull
    private AbstractDriverOptions driverOptions;

    public static ChromeOptionsBuilder chrome() {
        return new ChromeOptionsBuilder();
    }

    public static FirefoxOptionsBuilder firefox() {
        return new FirefoxOptionsBuilder();
    }

    public static EdgeOptionsBuilder edge() {
        return new EdgeOptionsBuilder();
    }

    public static InternetExplorerOptionsBuilder ie() {
        return new InternetExplorerOptionsBuilder();
    }

    public static SafariOptionsBuilder safari() {
        return new SafariOptionsBuilder();
    }

    public static BrowserHubOptionsBuilder hub() {
        return new BrowserHubOptionsBuilder();
    }

}
