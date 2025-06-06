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
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Mobile device management
 *
 * @author Monalis Behera
 */
@Slf4j
public class MobileDevice extends AppiumDriverInstance {

    @Getter
    private MobileScreen screen;

    /*protected MobileDevice() {
    }*/

    protected MobileDevice(final MobileDeviceConfig mobileDeviceConfig, final DesiredCapabilities extraCapabilities) {
        createAppiumDriver(mobileDeviceConfig.getDeviceUrl(), MobilePlatform.valueOf(mobileDeviceConfig.getPlatformName().toUpperCase()),
                mobileDeviceConfig.getDeviceCapabilities().merge(extraCapabilities));
        this.screen = new MobileScreen();
    }

    public static MobileDeviceBuilder builder(final MobileDeviceConfig mobileDeviceConfig) {
        return new MobileDeviceBuilder(mobileDeviceConfig);
    }

    @RequiredArgsConstructor
    public static class MobileDeviceBuilder {

        @NonNull
        private MobileDeviceConfig mobileDeviceConfig;
        private DesiredCapabilities extraCapabilities;


		public MobileDeviceBuilder extraCapabilities(final DesiredCapabilities extraCapabilities) {
            this.extraCapabilities = extraCapabilities;
            return this;
        }

        public MobileDevice build() {
            return new MobileDevice(mobileDeviceConfig, extraCapabilities);
        }
    }

    /**
     * Lock the device if it is not locked.
     */
    public MobileDevice lockDevice() {
        ((LocksDevice) getDriver()).lockDevice();
        return this;
    }

    /**
     * Unlock the device if it is locked.
     */
    public MobileDevice unlockDevice() {
        ((LocksDevice) getDriver()).unlockDevice();
        return this;
    }

    /**
     * Install an app on the mobile device.
     *
     * @param appFilepath
     */
    public MobileDevice installApp(final String appFilepath) {
        ((InteractsWithApps) getDriver()).installApp(appFilepath);
        return this;
    }

    /**
     * Launches the app, which was provided in the capabilities at session creation,
     * and (re)starts the session.
     */
    public MobileDevice launchApp(final String bundleId) {
        ((InteractsWithApps) getDriver()).activateApp(bundleId);
        return this;
    }

    /**
     * Close the app which was provided in the capabilities at session creation
     * and quits the session.
     */
    public MobileDevice closeApp(final String bundleId) {
        ((InteractsWithApps) getDriver()).terminateApp(bundleId);
        return this;
    }


    /**
     * Checks if an app is installed on the device.
     *
     * @param bundleId the bundle ID of the app
     * @return true if app is installed, false otherwise
     */
    public boolean isAppInstalled(final String bundleId) {
        return ((InteractsWithApps) getDriver()).isAppInstalled(bundleId);
    }

    /**
     * Check if the device is locked.
     *
     * @return true if the device is locked or false otherwise
     */
    public boolean isDeviceLocked() {
        return ((LocksDevice) getDriver()).isDeviceLocked();
    }


    public MobileDevice toggleWifi() {
        ((SupportsNetworkStateManagement) getDriver()).toggleWifi();
        return this;
    }

    public MobileDevice toggleAirplaneMode() {
        ((SupportsNetworkStateManagement) getDriver()).toggleAirplaneMode();
        return this;
    }

    public MobileDevice toggleData() {
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
