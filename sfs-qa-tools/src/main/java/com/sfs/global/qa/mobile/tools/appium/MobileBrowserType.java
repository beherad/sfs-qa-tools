package com.sfs.global.qa.mobile.tools.appium;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.sfs.global.qa.web.tools.webdriver.BrowserOptions;

/**
 * Mobile browser type
 *
 * @author Monalis Behera
 */
@Getter
@AllArgsConstructor
public enum MobileBrowserType {

    CHROME ( "Chrome", BrowserOptions.chrome().build() ),
    SAFARI ( "Safari", BrowserOptions.safari().build() );

    private String browserName;
    private BrowserOptions browserOptions;

    public DesiredCapabilities buildCapabilities() {
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, getBrowserName());
        return capabilities;
    }
}
