package com.sfs.global.qa.core.utils.xunit.testng;

import com.sfs.global.qa.core.data.annotations.KnownIssue;
import com.sfs.global.qa.core.test.TestContext;
import com.sfs.global.qa.core.utils.assertions.assertj.AssertjAssertions;

import lombok.extern.slf4j.Slf4j;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * DefaultTestNgListener class is used to listen to the TestNG events and update the test results accordingly.
 *
 * @author Monalis Behera
 */
@Slf4j
public class DefaultTestNgListener extends TestContext implements IHookable, ITestListener {

    @Override
    public void onTestStart(final ITestResult result) {
        log.debug(result.getMethod().getMethodName() + " started!");
        assertions().soft(AssertjAssertions.AssertjSoftAssertions.class).removeAll();
    }

    @Override
    public void run(final IHookCallBack callBack, final ITestResult result) {
        callBack.runTestMethod(result);

        try {
            assertions().soft(AssertjAssertions.AssertjSoftAssertions.class).assertAll();
        } catch (AssertionError e) {
            log.error(e.getMessage().replaceFirst("\r\n", ""), e);
            result.setStatus(ITestResult.FAILURE);
        }

        if (result.getThrowable() != null) {
            log.error("The following assertion failed: \n" + result.getThrowable().getCause().getMessage(),
                    ((InvocationTargetException) result.getThrowable()).getTargetException());
            result.setStatus(ITestResult.FAILURE);
        }

        final Method method = result.getMethod().getConstructorOrMethod().getMethod();
        if (method.isAnnotationPresent(KnownIssue.class)) {
            final KnownIssue knownIssue = method.getAnnotation(KnownIssue.class);
            log.warn("Failed test has @KnownIssue annotation defined & would be marked as SKIPPED!");
            log.warn("Known Issue: " + knownIssue.issueTitle());
            log.warn(String.format("Known Issue Link: %s", knownIssue.issueLink()));
            result.setStatus(ITestResult.SKIP);
        }
    }

    @Override
    public void onTestSuccess(final ITestResult result) {
        log.debug(result.getMethod().getMethodName() + " passed!");
    }

    @Override
    public void onTestFailure(final ITestResult result) {
        log.debug(result.getMethod().getMethodName() + " failed!");
    }

    @Override
    public void onTestSkipped(final ITestResult result) {
        log.debug(result.getMethod().getMethodName() + " skipped!");
    }

}