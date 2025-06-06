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
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.sfs.global.qa.utils.WaitUtils;
import com.sfs.global.qa.web.tools.webdriver.WebDriverInstance;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Mobile screen management
 *
 * @author Monalis Behera
 */
@Slf4j
public class MobileScreen extends WebDriverInstance<AppiumDriver> /*extends MobileDevice*/ {

    protected MobileScreen() {
    }

    /*protected MobileScreen(final MobileDeviceConfig mobileDeviceConfig, final DesiredCapabilities extraCapabilities) {
        super(mobileDeviceConfig, extraCapabilities);
    }*/

    protected Dimension getDimension() {
        return getDriver().manage().window().getSize();
    }

    protected int getHeight() {
        return getDimension().getHeight();
    }

    protected int getWidth() {
        return getDimension().getWidth();
    }

    public MobileScreen swipeUp() {
        int width = getWidth() / 2;
        int start = (int) (getHeight() * 0.5);
        int end = (int) (getHeight() * 0.95);
        swipe(width, start, width, end, 1000);
        return this;
    }

    public MobileScreen swipeDown() {
        int width = getWidth() / 2;
        int start = (int) (getHeight() * 0.5);
        int end = (int) (getHeight() * 0.05);
        swipe(width, start, width, end, 1000);
        return this;
    }

    public MobileScreen swipeLeft() {
        int height = getHeight() / 2;
        int start = (int) (getWidth() * 0.05);
        int end = (int) (getWidth() * 0.95);
        swipe(start, height, end, height, 1500);
        WaitUtils.timeout(1000, TimeUnit.MILLISECONDS);
        return this;
    }

    public MobileScreen swipeRight() {
        int heigh = getHeight() / 2;
        int start = (int) (getWidth() * 0.95);
        int end = (int) (getWidth() * 0.05);
        swipe(start, heigh, end, heigh, 1000);
        WaitUtils.timeout(1000, TimeUnit.MILLISECONDS);
        return this;
    }

    public MobileScreen swipeLeft(final int ycoord) {
        int start = (int) (getWidth() * 0.05);
        int end = (int) (getWidth() * 0.95);
        swipe(start, ycoord, end, ycoord, 1000);
        WaitUtils.timeout(1000, TimeUnit.MILLISECONDS);
        return this;
    }

    public MobileScreen swipeRight(int ycoord) {
        int start = (int) (getWidth() * 0.95);
        int end = (int) (getWidth() * 0.05);
        swipe(start, ycoord, end, ycoord, 1000);
        WaitUtils.timeout(1000, TimeUnit.MILLISECONDS);
        return this;
    }

    private void swipe(int startx, int starty, int endx, int endy, int duration) {
        log.debug("Perform swipe action from the [" + startx + ", " + starty + "] coordinates to the ["
                + endx + ", " + endy + "] coordinates");
        getTouchAction().press(PointOption.point(startx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(duration)))
                .moveTo(PointOption.point(endx, endy))
                .release()
                .perform();
    }

    private TouchAction getTouchAction() {
        return new TouchAction((PerformsTouchActions) getDriver());
    }
}
