package com.sfs.global.qa.core.test;


import com.sfs.global.qa.core.utils.assertions.assertj.AssertjAssertions;
import com.sfs.global.qa.core.utils.reporting.extentreports.ExtentReportsTestNgListener;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestContext {

    protected static ExtentReportsTestNgListener.ExtentTestDataAppender log() {
        return ExtentReportsTestNgListener.dataAppender();
    }

    protected static AssertjAssertions assertions() {
        return new AssertjAssertions();
    }

}
