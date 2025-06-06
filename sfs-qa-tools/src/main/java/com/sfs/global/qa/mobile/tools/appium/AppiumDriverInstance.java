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
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.windows.WindowsDriver;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.sfs.global.qa.utils.WaitUtils;
import com.sfs.global.qa.web.tools.webdriver.WebDriverInstance;

import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * AppiumDriver instance initializer
 *
 * @author Monalis Behera
 */
@Slf4j
class AppiumDriverInstance extends WebDriverInstance<AppiumDriver> {

    @Synchronized
    void createAppiumDriver(@NonNull final String deviceUrl, @NonNull final MobilePlatform mobilePlatform,
                            final DesiredCapabilities capabilities) {
        try {
            ThreadLocal<AppiumDriver> localDriver = getDriver(deviceUrl, mobilePlatform, capabilities);
            WaitUtils.timeout(2, TimeUnit.SECONDS);
            localDriversPool.put(getCurrThreadId(), localDriver);
            //getDriver().manage().timeouts().pageLoadTimeout(.., ..);
            //getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (WebDriverException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Synchronized
    private ThreadLocal<AppiumDriver> getDriver(final String deviceUrl, final MobilePlatform mobilePlatform,
                                                final DesiredCapabilities capabilities) {
        final URL url = Objects.requireNonNull(getURL(deviceUrl));
        switch (mobilePlatform) {
            case ANDROID:
                return ThreadLocal.withInitial(() -> new AndroidDriver(url, capabilities));
            case IOS:
                return ThreadLocal.withInitial(() -> new IOSDriver(url, capabilities));
            case WINDOWS:
                return ThreadLocal.withInitial(() -> new WindowsDriver(url, capabilities));
            default:
                return null;
        }
    }

}
