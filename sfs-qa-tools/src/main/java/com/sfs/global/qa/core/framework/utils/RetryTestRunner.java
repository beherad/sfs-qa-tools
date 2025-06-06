package com.sfs.global.qa.core.framework.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
/*import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;*/


/**
 * The Objective of the RetryTestRunner class is to run failed tests in the automation project so as to remove the flaky
 * test failure results in the automation
 * You can add retry.rule and retry.times in your property file which will be
 * applicable for all your tests although not using them will not throw a null pointer exception.
 * The default retry execution will be 2 times if retry.times is not specified in the properties file.
 * You can  override the properties file at the test level by using RetryTestRunner.setRetryCount(n)
 * after initiateTemplate method.
 * where n is the number of retry attempts at the top in @Test annotation.
 *  @author  Luthra Saibi
 */
@Slf4j
public class RetryTestRunner /*implements TestRule*/ {

    /*private static int retryCount = 1;

    @Getter
    private boolean retryRule;

    @Getter
    private int retryLogger = 0;

    public RetryTestRunner() {
        //new EnvironmentPropertiesBase(System.getProperty("envConfig"));
        setRetryRule();
        setRetryCount();
    }

    private void setRetryRule() {
        //retryRule = EnvironmentPropertiesBase.getRetryRule();
    }

    private void setRetryCount() {
        if (retryRule) {
            //retryCount = EnvironmentPropertiesBase.getRetryTimes() + 1;
        }
    }

    public void setRetryCount(int retryTimes) {
        if (retryRule) {
            retryCount = retryTimes + 1;
        }
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return statement(base, description);
    }

    private Statement statement(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Throwable caughtThrowable = null;

                for (int i = 0; i < retryCount; i++) {
                    try {
                        base.evaluate();
                        return;
                        // Adding MultipleFailureException to catch Hard and Soft assertion fail in the same execution
                    } catch (AssertionError | MultipleFailureException t) {
                        caughtThrowable = t;
                        if (retryCount > 1) {
                            if ((i + 1) != retryCount) { retryLogger += 1; }
                            log.info(description.getMethodName() + ": Attempt " + (i + 1) + " failed");

                        }
                    }
                }
                if (retryCount > 1) {
                    log.error(description.getMethodName() + ": Test failed after " + retryCount + " failures");
                }
                assert caughtThrowable != null;
                throw caughtThrowable;
            }
        };
    }*/
}