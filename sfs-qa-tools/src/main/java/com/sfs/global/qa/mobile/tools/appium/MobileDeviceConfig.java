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

import com.sfs.global.qa.mobile.tools.appium.capabilities.AndroidDeviceCapabilitiesBuilder;
import com.sfs.global.qa.mobile.tools.appium.capabilities.IOSDeviceCapabilitiesBuilder;

import io.appium.java_client.remote.AutomationName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Mobile device config
 *
 * @author Monalis Behera
 */
@Getter
public class MobileDeviceConfig {

    private final String deviceUrl;
    private final String platformName;
    private final DesiredCapabilities deviceCapabilities;

    private MobileDeviceConfig(final String deviceUrl, final String platformName, final DesiredCapabilities deviceCapabilities) {
        this.deviceUrl = deviceUrl;
        this.platformName = platformName;
        this.deviceCapabilities = deviceCapabilities;
    }

    /**
     * Connected device URL, udId, name, platform name & version
     *
     * @param deviceUrl       - device URL
     * @param deviceName      - device name
     * @param platformName    - platform name
     * @param platformVersion - platform version
     * @return MobileDeviceConfigBuilder
     */
    public static MobileDeviceConfigBuilder builder(final String deviceUrl, final String deviceName,
                                                    final String platformName, final String platformVersion) {
        return new MobileDeviceConfigBuilder(deviceUrl, deviceName, platformName, platformVersion);
    }

    @NoArgsConstructor
    public static class MobileDeviceConfigBuilder {

        private String deviceUrl;
        protected static DesiredCapabilities capabilities;

        public MobileDeviceConfigBuilder(final String deviceUrl, final String deviceName,
                                         final String platformName, final String platformVersion) {
            this.deviceUrl = deviceUrl;
            this.capabilities = new DesiredCapabilities();
            //capabilities.setCapability(MobileCapabilityType.DEVICE_URL, deviceUrl);
            //capabilities.setCapability("appium:udid", deviceUdId);
            capabilities.setCapability("appium:deviceName", deviceName);
            capabilities.setCapability(CapabilityType.PLATFORM_NAME, platformName);
            capabilities.setCapability("appium:platformVersion", platformVersion);
        }

        public MobileDeviceConfigBuilder udId(final String udId) {
            capabilities.setCapability("appium:udid", udId);
            return this;
        }

        public MobileDeviceConfigBuilder automationName(final String automationName) {
            capabilities.setCapability("appium:automationName", automationName); //AutomationName.ANDROID_UIAUTOMATOR2
            return this;
        }

        /**
         * Don't reset app state before starting new session. Default false.
         *
         * @param flag - true or false
         */
        public MobileDeviceConfigBuilder noReset(final boolean flag) {
            capabilities.setCapability("appium:noReset", flag);
            return this;
        }

        /**
         * (iOS) Delete the entire simulator folder.
         * (Android) Reset app state by uninstalling app instead of clearing app data.
         * On Android, this will also remove the app after the session is complete. Default false.
         *
         * @param flag - true or false
         */
        public MobileDeviceConfigBuilder fullReset(final boolean flag) {
            capabilities.setCapability("appium:fullReset", flag);
            return this;
        }

        /**
         * Start device in a certain orientation. <br>
         * Notice! Works in simulators and emulators only.
         *
         //* @param screenOrientation
         */
        /*public MobileDeviceConfigBuilder orientation(final ScreenOrientation screenOrientation) {
            capabilities.setCapability(MobileCapabilityType.ORIENTATION, screenOrientation);
            return this;
        }*/

        public AndroidDeviceCapabilitiesBuilder androidOnly() {
            return new AndroidDeviceCapabilitiesBuilder();
        }

        public IOSDeviceCapabilitiesBuilder iosOnly() {
            return new IOSDeviceCapabilitiesBuilder();
        }

        public MobileDeviceConfigBuilder setCapability(final String capabilityName, final Object capabilityValue) {
            capabilities.setCapability(capabilityName, capabilityValue);
            return this;
        }

        public MobileDeviceConfig build() {
            return new MobileDeviceConfig(deviceUrl, capabilities.getCapability(CapabilityType.PLATFORM_NAME).toString(), capabilities);
        }
    }
}