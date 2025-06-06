package com.sfs.global.qa.core.utils.reporting.testngListner;

import lombok.extern.slf4j.Slf4j;
import org.testng.IExecutionListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.internal.IResultListener;

/**
 * Basic TestNgListener class for logging test execution events.
 * Implements ISuiteListener, IResultListener, IExecutionListener.
 */
@Slf4j
public class TestNgListener implements ISuiteListener, IResultListener, IExecutionListener {

    public TestNgListener() {}

    @SuppressWarnings("unused")
	private void performAction(final Runnable action, final String actionName) {
        try {
            action.run();
        } catch (Exception e) {
            log.error("Error during the {} action", actionName, e);
        }
    }

    @Override
    public void onExecutionStart() {
        log.info("=== Test Execution Started ===");
    }

    @Override
    public void onExecutionFinish() {
        log.info("=== Test Execution Finished ===");
    }

    @Override
    public void onStart(ISuite suite) {
        log.info("=== Suite Started: {} ===", suite.getName());
    }

    @Override
    public void onFinish(ISuite suite) {
        log.info("=== Suite Finished: {} ===", suite.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        log.info("=== Test Started: {} ===", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("=== Test Finished: {} ===", context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("Test started: {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Test passed: {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
    	try {
        log.error("Test failed: {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName(), result.getThrowable());
    	}catch(Exception ex) {
    		
    	}
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("Test skipped: {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName());
    }

    @Override
    public void beforeConfiguration(ITestResult result) {
        log.info("Before configuration: {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName());
    }

    @Override
    public void onConfigurationFailure(ITestResult result) {
        log.error("Configuration failed: {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName(), result.getThrowable());
    }

    @Override
    public void onConfigurationSuccess(ITestResult result) {
        log.info("Configuration succeeded: {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName());
    }

    @Override
    public void onConfigurationSkip(ITestResult result) {
        log.warn("Configuration skipped: {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName());
    }
}
