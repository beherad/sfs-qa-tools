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

import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.LocksDevice;
import io.appium.java_client.android.SupportsNetworkStateManagement;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.sfs.global.qa.web.tools.webdriver.GenericBrowser;
import com.sfs.global.qa.web.tools.webdriver.WebDriverInstance;

/**
 * Mobile device management
 *
 * @author Monalis Behera
 */
@Slf4j
public abstract class AbstractDevice<B extends GenericBrowser, D extends WebDriver> extends AppiumDriverInstance {

	 @Getter
	 private MobileScreen screen;

	 protected AbstractDevice(final MobileDeviceConfig mobileDeviceConfig, final DesiredCapabilities extraCapabilities) {
	      createAppiumDriver(
	          mobileDeviceConfig.getDeviceUrl(),
	          MobilePlatform.valueOf(mobileDeviceConfig.getPlatformName().toUpperCase()),
	          mobileDeviceConfig.getDeviceCapabilities().merge(extraCapabilities)
	      );
	      this.screen = new MobileScreen();
	 }

    @RequiredArgsConstructor
    public abstract static class MobileDeviceBuilder<T extends AbstractDevice<?, ?>> {

        @NonNull
        protected final MobileDeviceConfig mobileDeviceConfig;
        protected DesiredCapabilities extraCapabilities;

        public MobileDeviceBuilder<T> extraCapabilities(final DesiredCapabilities extraCapabilities) {
            this.extraCapabilities = extraCapabilities;
            return this;
        }

        public abstract T build();
    }

    /**
     * Lock the device if it is not locked.
     */
    public AbstractDevice<B, D> lockDevice() {
        ((LocksDevice) getDriver()).lockDevice();
        return this;
    }

    /**
     * Unlock the device if it is locked.
     */
    public AbstractDevice<B, D> unlockDevice() {
        ((LocksDevice) getDriver()).unlockDevice();
        return this;
    }

    public AbstractDevice<B, D> installApp(final String appFilepath) {
        ((InteractsWithApps) getDriver()).installApp(appFilepath);
        return this;
    }

    public AbstractDevice<B, D> launchApp(final String bundleId) {
        ((InteractsWithApps) getDriver()).activateApp(bundleId);
        return this;
    }

    public AbstractDevice<B, D> closeApp(final String bundleId) {
        ((InteractsWithApps) getDriver()).terminateApp(bundleId);
        return this;
    }

    public boolean isAppInstalled(final String bundleId) {
        return ((InteractsWithApps) getDriver()).isAppInstalled(bundleId);
    }

    public boolean isDeviceLocked() {
        return ((LocksDevice) getDriver()).isDeviceLocked();
    }

    public AbstractDevice<B, D> toggleWifi() {
        ((SupportsNetworkStateManagement) getDriver()).toggleWifi();
        return this;
    }

    public AbstractDevice<B, D> toggleAirplaneMode() {
        ((SupportsNetworkStateManagement) getDriver()).toggleAirplaneMode();
        return this;
    }

    public AbstractDevice<B, D> toggleData() {
        ((SupportsNetworkStateManagement) getDriver()).toggleData();
        return this;
    }

    /*public static void main(String[] args) {
        MobileDeviceConfig mobileDeviceConfig = MobileDeviceConfig.builder("http://127.0.0.1:4723/wd/hub", "Galaxy Nexus API 24",
                        MobilePlatform.ANDROID.getName(), "7.0")
                .udId("emulator-5554")
                .noReset(false)
                .androidOnly()
                    .autoGrantPermissions(true)
                .iosOnly()
                    //.ios*
                .build();

        *//*MobileDevice device = MobileDevice.builder(mobileDeviceConfig).build();
        device.unlockDevice();*//*

        MobileBrowser mobileBrowser = MobileBrowser.builder(mobileDeviceConfig, MobileBrowserType.CHROME)
                .browserOptions(BrowserOptions.hub()
                        .saucelabs("slWdHubUrl", "slUsername", "slAuthKey", BrowserOptions.chrome()
                                .disableInfobars()
                                .incognitoMode()
                                .build())
                        .build())
                .build();
        mobileBrowser.openURL("");
    }*/
}
