package com.sfs.global.qa.core.test;

import com.sfs.global.qa.core.config.TafConfig;
import com.sfs.global.qa.core.utils.reporting.extentreports.ExtentReportsTestNgListener;
import com.sfs.global.qa.core.utils.reporting.testngListner.TestNgListener;
import com.sfs.global.qa.core.utils.xunit.testng.DefaultTestNgListener;
import com.sfs.global.qa.core.utils.xunit.testng.TestNgRetryListener;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Listeners;

@Slf4j
@Listeners({DefaultTestNgListener.class,
            TestNgRetryListener.class,
            ExtentReportsTestNgListener.class,
            TestNgListener.class})
public class TestBase extends TestConfig {

    static {
        log.info("Init TestConfig & assign env properties to TafConfig");
        TafConfig.setEnvProperties(getEnvProperties());
    }

    protected TestBase() {
        super();
    }

}
