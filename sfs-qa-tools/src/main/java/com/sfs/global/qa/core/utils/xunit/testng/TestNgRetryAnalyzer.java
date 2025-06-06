package com.sfs.global.qa.core.utils.xunit.testng;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.sfs.global.qa.core.config.TafConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TestNgRetryAnalyzer is used to retry the failed tests by following the number of retries count defined
 *
 * <p>Implementation of {@link IRetryAnalyzer} interface</p>
 * <p>This class is used in combination with {@link TestNgRetryAnalyzer}</p>
 *
 * @author Monalis Behera
 */
@Slf4j
public class TestNgRetryAnalyzer implements IRetryAnalyzer {

    private static final int MAX_ALLOWED_RETRIES_COUNT = 3;
    private static int RETRIES_COUNT = Integer.parseInt(TafConfig.TEST_RUN_RETRIES_COUNT.getValue());

    private final Map<Integer, AtomicInteger> retriesCountMap = new HashMap<>();

    private AtomicInteger getRetriesCount(final ITestResult result) {
        final int testId = new HashCodeBuilder()
                .append(Thread.currentThread().getId())
                .append(result.getMethod().getConstructorOrMethod().getMethod().getName())
                .append(result.getParameters())
                .toHashCode();

        if (retriesCountMap.get(testId) == null) {
            if (RETRIES_COUNT > MAX_ALLOWED_RETRIES_COUNT) {
                log.warn("The maximum number of failed test retries is set to [{}] - it's a maximum allowed retries count!", MAX_ALLOWED_RETRIES_COUNT);
                RETRIES_COUNT = MAX_ALLOWED_RETRIES_COUNT;
            }
            retriesCountMap.put(testId, new AtomicInteger(RETRIES_COUNT));
        }
        return retriesCountMap.get(testId);
    }

    @Override
    public boolean retry(final ITestResult result) {
        if (RETRIES_COUNT == 0) {
            return false;
        }
        final int retriesRemaining = getRetriesCount(result).getAndDecrement();
        final boolean retryAgain = retriesRemaining > 0;
        if (retryAgain) {
            log.warn("Test failed, but has retries count defined - result would be marked as SKIPPED, test would be retried!");
            log.warn("Remaining retries: {}", retriesRemaining);
        } else {
            log.warn("Test failed & do NOT have remaining retries - result would be marked as FAILED!");
        }
        return retryAgain;
    }

}
