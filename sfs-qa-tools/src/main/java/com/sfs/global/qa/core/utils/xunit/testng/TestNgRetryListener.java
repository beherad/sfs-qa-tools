package com.sfs.global.qa.core.utils.xunit.testng;

import lombok.extern.slf4j.Slf4j;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.internal.annotations.DisabledRetryAnalyzer;

import java.lang.reflect.Method;

/**
 * TestNgRetryListener class is used to automatically assign the TestNgRetryAnalyzer to the test methods
 *
 * @author Monalis Behera
 */
@Slf4j
public class TestNgRetryListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        final Method method = result.getMethod().getConstructorOrMethod().getMethod();
        if (method.getAnnotation(Test.class).retryAnalyzer() == DisabledRetryAnalyzer.class) {
            result.getMethod().setRetryAnalyzerClass(TestNgRetryAnalyzer.class);
        }
    }
}