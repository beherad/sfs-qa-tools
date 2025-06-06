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
package com.sfs.global.qa.mobile.tools.appium.capabilities;

import com.sfs.global.qa.mobile.tools.appium.MobileDeviceConfig.MobileDeviceConfigBuilder;

/**
 * Android device capabilities builder
 *
 * @author Monalis Behera
 */
public class AndroidDeviceCapabilitiesBuilder extends MobileDeviceConfigBuilder {

	/**
     * Skips unlock screen during session creation. Defaults to false. <br>
     * Notice! Doesn't work on devices locked with the pin or unlock patterns.
     *
     * @param flag
     */
    public AndroidDeviceCapabilitiesBuilder skipUnlock(final boolean flag) {
        capabilities.setCapability("skipUnlock", flag);
        return this;
    }

    public AndroidDeviceCapabilitiesBuilder unlockType(final String unlockType) {
        capabilities.setCapability("unlockType", unlockType);
        return this;
    }

    public AndroidDeviceCapabilitiesBuilder unlockKey(final String unlockKey) {
        capabilities.setCapability("unlockKey", unlockKey);
        return this;
    }

    /**
     * Set automatically determine which permissions your app requires and grant
     * them to the app on install. Defaults to false. <br>
     * Notice! If noReset is true, this capability doesn't work.
     *
     * @param flag
     */
    public AndroidDeviceCapabilitiesBuilder autoGrantPermissions(final boolean flag) {
        capabilities.setCapability("autoGrantPermissions", flag);
        return this;
    }

}
