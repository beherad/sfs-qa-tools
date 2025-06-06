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

import com.sfs.global.qa.core.test.TestContext;
import com.sfs.global.qa.utils.WaitUtils;

import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * WebDriver instance initializer
 *
 * @author Monalis Behera
 */
@Slf4j
public class WebDriverInstance<T extends WebDriver> extends TestContext {

    /**
     * Thread local drivers pool & local WebDriver instance getter
     */
    protected static final Map<Long, ThreadLocal<? extends WebDriver>> localDriversPool = new ConcurrentHashMap<>();

    //TODO: should be protected, set as public as a temporary workaround for some projects depending on browser.getDriver() usage
    public T getDriver() {
        final Long threadId = getCurrThreadId();
        if (localDriversPool.get(threadId) != null) {
            return (T) localDriversPool.get(threadId).get();
        }
        log.error("[ERROR] Initialized driver for current thread is NOT found: " +threadId);
        return null;
    }

    /**
     * Create a new instance of a local WebDriver.
     *
     * @param driverOptions The driver options.
     * @param extraCapabilities The desired capabilities.
     */
    @Synchronized
    void createLocalWebDriver(@NonNull final BrowserType browserType,
                              final AbstractDriverOptions<?> driverOptions, final DesiredCapabilities extraCapabilities) {
        final ThreadLocal<WebDriver> localDriver;
        switch (browserType) {
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                if (driverOptions != null) {
                    chromeOptions = chromeOptions.merge(driverOptions);
                }
                if (extraCapabilities != null) {
                    chromeOptions = chromeOptions.merge(extraCapabilities);
                }
                final ChromeOptions fChromeOptions = chromeOptions;
                localDriver = ThreadLocal.withInitial(() -> new ChromeDriver(fChromeOptions));
                break;
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (driverOptions != null) {
                    firefoxOptions = firefoxOptions.merge(driverOptions);
                }
                if (extraCapabilities != null) {
                    firefoxOptions = firefoxOptions.merge(extraCapabilities);
                }
                final FirefoxOptions fFirefoxOptions = firefoxOptions;
                localDriver = ThreadLocal.withInitial(() -> new FirefoxDriver(fFirefoxOptions));
                break;
            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                if (driverOptions != null) {
                    edgeOptions = edgeOptions.merge(driverOptions);
                }
                if (extraCapabilities != null) {
                    edgeOptions = edgeOptions.merge(extraCapabilities);
                }
                final EdgeOptions fEdgeOptions = edgeOptions;
                localDriver = ThreadLocal.withInitial(() -> new EdgeDriver(fEdgeOptions));
                break;
            case IE:
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                if (driverOptions != null) {
                    internetExplorerOptions = internetExplorerOptions.merge(driverOptions);
                }
                if (extraCapabilities != null) {
                    internetExplorerOptions = internetExplorerOptions.merge(extraCapabilities);
                }
                final InternetExplorerOptions fInternetExplorerOptions = internetExplorerOptions;
                localDriver = ThreadLocal.withInitial(() -> new InternetExplorerDriver(fInternetExplorerOptions));
                break;
            default:
                localDriver = ThreadLocal.withInitial(FirefoxDriver::new);
        }
        WaitUtils.timeout(2, TimeUnit.SECONDS);
        localDriversPool.put(getCurrThreadId(), localDriver);
    }

    /**
     * Create a new instance of a remote WebDriver.
     *
     * @param wdHubUrl The remote WebDriver hub URL.
     * @param driverOptions The driver options.
     * @param extraCapabilities The desired capabilities.
     */
    @Synchronized
    void createRemoteWebDriver(@NonNull final BrowserType browserType, @NonNull final String wdHubUrl,
                               final AbstractDriverOptions<?> driverOptions, final DesiredCapabilities extraCapabilities) {
        final URL url = Objects.requireNonNull(getURL(wdHubUrl));
        final ThreadLocal<WebDriver> localDriver = ThreadLocal.withInitial(() -> {
            if (driverOptions != null) {
                if (extraCapabilities != null) {
                    return new RemoteWebDriver(url, driverOptions.merge(extraCapabilities));
                }
                return new RemoteWebDriver(url, driverOptions);
            } else {
                if (extraCapabilities != null) {
                    return new RemoteWebDriver(url, browserType.getDefaultOptions().merge(extraCapabilities));
                }
            }
            return new RemoteWebDriver(Objects.requireNonNull(getURL(wdHubUrl)), browserType.getDefaultOptions());
        });
        localDriversPool.put(getCurrThreadId(), localDriver);
    }

    protected Long getCurrThreadId() {
        return Thread.currentThread().getId();
    }

    protected URL getURL(final String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Quit driver process
     */
    @Synchronized
    protected void quit() {
        getDriver().quit();
        localDriversPool.remove(getCurrThreadId());
    }

}
