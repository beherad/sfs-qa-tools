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
package com.sfs.global.qa.mobile.tools.appium;

import io.appium.java_client.AppiumDriver;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.sfs.global.qa.core.config.TafConfig;
import com.sfs.global.qa.web.tools.webdriver.AbstractBrowser;
import com.sfs.global.qa.web.tools.webdriver.BrowserOptions;
import com.sfs.global.qa.web.tools.webdriver.GenericBrowser;

import java.time.Duration;

/**
 * Mobile browser management
 *
 * @author Monalis Behera
 */
@Slf4j
public class MobileBrowser extends AbstractBrowser<MobileBrowser, AppiumDriver> /*MobileScreen*/ implements GenericBrowser {

    @Getter
    private MobileDevice device;

    private static final int BROWSER_IMPLICITLYWAIT_TIMEOUT = Integer.parseInt(TafConfig.Mobile.DEVICE_BROWSER_IMPLICITLYWAIT_TIMEOUT.getValue());
    private static final int BROWSER_PAGELOAD_TIMEOUT = Integer.parseInt(TafConfig.Mobile.DEVICE_BROWSER_PAGELOAD_TIMEOUT.getValue());
    private static final int BROWSER_ELEMENTLOAD_TIMEOUT = Integer.parseInt(TafConfig.Mobile.DEVICE_BROWSER_ELEMENTLOAD_TIMEOUT.getValue());

    protected MobileBrowser() {
    }

    private MobileBrowser(final MobileDeviceConfig mobileDeviceConfig, final DesiredCapabilities extraCapabilities) {
        initAppiumDriver(mobileDeviceConfig, mobileDeviceConfig.getDeviceCapabilities().merge(extraCapabilities));
        setImplicitWaitTimeout(BROWSER_IMPLICITLYWAIT_TIMEOUT);
        setBrowser(this);
        setClazz(MobileBrowser.class);
    }

    public static MobileBrowserBuilder builder(final MobileDeviceConfig mobileDeviceConfig, final MobileBrowserType mobileBrowserType) {
        return new MobileBrowserBuilder(mobileDeviceConfig, mobileBrowserType);
    }

    @RequiredArgsConstructor
    public static class MobileBrowserBuilder {

        @NonNull
        private MobileDeviceConfig mobileDeviceConfig;
        @NonNull
        private MobileBrowserType mobileBrowserType;
        private BrowserOptions browserOptions;

        public MobileBrowserBuilder browserOptions(final BrowserOptions browserOptions) {
            this.browserOptions = browserOptions;
            return this;
        }

        public MobileBrowser build() {
            if (browserOptions == null) {
                return new MobileBrowser(mobileDeviceConfig, mobileBrowserType.buildCapabilities());
            }
            return new MobileBrowser(mobileDeviceConfig, mobileBrowserType.buildCapabilities().merge(browserOptions.getDriverOptions()));
        }
    }

    private void initAppiumDriver(final MobileDeviceConfig mobileDeviceConfig, final DesiredCapabilities extraCapabilities) {
        this.device = new MobileDevice(mobileDeviceConfig, extraCapabilities);
    }

    @Override
    public int getImplicitlyWaitTimeout() {
        log.debug("Implicitly wait timeout is set to [{}] seconds", BROWSER_IMPLICITLYWAIT_TIMEOUT);
        return BROWSER_IMPLICITLYWAIT_TIMEOUT;
    }

    @Override
    public int getPageLoadTimeout() {
        log.debug("Page load timeout is set to [{}] seconds", BROWSER_PAGELOAD_TIMEOUT);
        return BROWSER_PAGELOAD_TIMEOUT;
    }

    @Override
    public int getElementLoadTimeout() {
        log.debug("Element load timeout is set to [{}] seconds", BROWSER_ELEMENTLOAD_TIMEOUT);
        return BROWSER_ELEMENTLOAD_TIMEOUT;
    }

    @Override
    public void setDefaultImplicitWaitTimeout() {
        log.debug("Default implicit wait timeout is set to [{}] seconds", BROWSER_IMPLICITLYWAIT_TIMEOUT);
        setImplicitWaitTimeout(Duration.ofSeconds(BROWSER_IMPLICITLYWAIT_TIMEOUT));
    }
}
