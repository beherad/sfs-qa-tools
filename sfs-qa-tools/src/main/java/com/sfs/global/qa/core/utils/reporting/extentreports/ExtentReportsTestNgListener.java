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
package com.sfs.global.qa.core.utils.reporting.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.sfs.global.qa.core.config.TafConfig;
import com.sfs.global.qa.core.data.annotations.KnownIssue;
import com.sfs.global.qa.core.test.TestContext;
import com.sfs.global.qa.core.utils.assertions.assertj.AssertjAssertions;
import com.sfs.global.qa.utils.DateFormatUtils;
import com.sfs.global.qa.utils.FileUtils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ExtentReports HTML report builder based on TestNG events listener
 *
 * @author Monalis Behera
 */
@Slf4j
public class ExtentReportsTestNgListener extends TestContext implements ITestListener {

    private static final ExtentReports extentReports;
    private static final ExtentSparkReporter extentHtmlV2Reporter;

    static {
        extentReports = new ExtentReports();
        extentHtmlV2Reporter = new ExtentSparkReporter(TafConfig.TEST_REPORTS_DIRPATH.getValue()
                .concat("/").concat(TafConfig.TEST_RUN_NAME.getValue())
                .concat("/results.html").replaceAll("/+", "/"));
        //extentHtmlV2Reporter.config().setAutoCreateRelativePathMedia(true);
        extentHtmlV2Reporter.config().setCss("div.test-content-detail { overflow: auto; }");
        extentHtmlV2Reporter.config().setTheme(Theme.STANDARD);
        extentHtmlV2Reporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
    }

    private static final ThreadLocal<com.aventstack.extentreports.ExtentTest> localTest = new ThreadLocal<>();

    @Override
    public synchronized void onStart(final ITestContext context) {
        if (context.getAllTestMethods().length != 0) {
            final String testClassName = context.getAllTestMethods()[0].getTestClass().getRealClass().getSimpleName();
            final ExtentReport extentReport = context.getAllTestMethods()[0].getTestClass().getRealClass().getAnnotation(ExtentReport.class);
            final String reportName = (extentReport != null) ? extentReport.name() : testClassName;

            // = new ExtentHtmlReporter("target/extentreports/".concat(reportName).concat(".html"));
            if (extentHtmlV2Reporter.config().getDocumentTitle().isEmpty()) {
                extentHtmlV2Reporter.config().setDocumentTitle(reportName);
            }
            if (extentHtmlV2Reporter.config().getReportName().isEmpty()) {
                extentHtmlV2Reporter.config().setReportName(reportName);
            }
            extentReports.attachReporter(extentHtmlV2Reporter);
        }
    }

    @Override
    public synchronized void onFinish(final ITestContext context) {
        extentReports.flush();
    }

    @Override
    public synchronized void onTestStart(final ITestResult result) {
        final Method method = result.getMethod().getConstructorOrMethod().getMethod();
        final com.aventstack.extentreports.ExtentTest test = extentReports.createTest(buildTestName(method, result), result.getMethod().getDescription());
        final ExtentTest extentTest = method.getAnnotation(ExtentTest.class);
        final String[] tags = (extentTest != null) ? extentTest.tags() : new String[]{};
        if (tags.length > 0) {
            Arrays.stream(tags).filter(tag -> !tag.isEmpty()).forEach(tag -> {
                log.debug("Test marked with tag: " + tag);
                test.assignCategory(tag);
            });
        }
        final KnownIssue knownIssue = method.getAnnotation(KnownIssue.class);
        if (knownIssue != null) {
            test.assignCategory("[KNOWN_ISSUE]");
        }
        localTest.set(test);
        localTest.get().log(Status.INFO, "============= [TEST STARTED] =============");
    }

    private final Map<Integer, AtomicInteger> retriedCountMap = new HashMap<>();

    private int getRetriedCount(final ITestResult result) {
        final int testId = new HashCodeBuilder().append(Thread.currentThread().getId())
                .append(result.getMethod().getConstructorOrMethod().getMethod().getName())
                .append(result.getParameters())
                .toHashCode();
        if (retriedCountMap.get(testId) == null) {
            retriedCountMap.put(testId, new AtomicInteger(0));
            return retriedCountMap.get(testId).get();
        }
        return retriedCountMap.get(testId).incrementAndGet();
    }

    private String buildTestName(final Method method, final ITestResult result) {
        String testName = method.getAnnotation(Test.class).testName();
        if (testName == null || testName.isEmpty()) {
            testName = result.getMethod().getMethodName();
        }
        log.debug("Method parameters count: " + result.getParameters().length);
        if (result.getParameters().length > 0) {
            testName = testName.concat(" : [");
            for (Object parameter : result.getParameters()) {
                testName = testName.concat(parameter.toString()).concat(" : ");
            }
            testName = testName.replaceAll(" : " + "$", "").concat("]");
        }
        final int retriedCount = getRetriedCount(result);
        if (retriedCount > 0) {
            testName = testName.concat(" : [retry #").concat(Integer.toString(retriedCount)).concat("]");
        }
        return testName;
    }

    @Override
    public synchronized void onTestSuccess(final ITestResult result) {
        localTest.get().log(Status.INFO, "============= [TEST FINISHED] =============");
        localTest.get().pass("Test passed");
        localTest.get().assignCategory("[PASSED]");
        localTest.remove();
    }

    @Override
    public synchronized void onTestFailure(final ITestResult result) {
        localTest.get().log(Status.INFO, "============= [TEST FINISHED] =============");

        try {
            assertions().soft(AssertjAssertions.AssertjSoftAssertions.class).assertAll();
        } catch (AssertionError e) {
            localTest.get().fail(e);
        }
        if (result.getThrowable() != null) {
            localTest.get().fail(result.getThrowable());

            /*if (getBrowser(result) != null) {
                try {
                    localTest.get().fail("Screenshot:", MediaEntityBuilder
                            .createScreenCaptureFromPath(getBrowser(result)
                                    .takeScreenshot("screenshots", result.getMethod().getMethodName(), true, ImageFormat.PNG)
                                    .getPath())
                            .build());
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }*/
        }
        localTest.get().fail("Test failed");
        localTest.get().assignCategory("[FAILED]");
        localTest.remove();
    }

    /*private Browser getBrowser(final ITestResult testResult) {
        try {
            Class<?> baseTestClass = testResult.getMethod().getRealClass().getSuperclass();

            Field browserField = baseTestClass.getDeclaredField("browser");
            browserField.setAccessible(true);
            return (Browser) browserField.get(testResult.getInstance());
        } catch (NoSuchFieldException e) {
            log.warn("[ExtentReportsListener] Field is NOT found in superclass hierarchy: " +e.getMessage());
            return null;
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }*/

    @Override
    public synchronized void onTestSkipped(final ITestResult result) {
        localTest.get().log(Status.INFO, "============= [TEST FINISHED] =============");

        final Method method = result.getMethod().getConstructorOrMethod().getMethod();
        final KnownIssue knownIssue = method.getAnnotation(KnownIssue.class);
        if (knownIssue != null) {
            localTest.get().warning("Failed test has @KnownIssue annotation defined & would be marked as SKIPPED!");
            localTest.get().warning("Known Issue: " + knownIssue.issueTitle());
            localTest.get().warning(String.format("Known Issue Link: <a href='%s'>%s</a>", knownIssue.issueLink(), knownIssue.issueLink()));
        }

        try {
            assertions().soft(AssertjAssertions.AssertjSoftAssertions.class).assertAll();
        } catch (AssertionError e) {
            localTest.get().skip(e);
        }

        localTest.get().skip(result.getThrowable());
        localTest.get().skip("Test skipped");
        localTest.get().assignCategory("[SKIPPED]");
        localTest.remove();
    }

    public static void log(final Status status, final String details) {
        if (localTest.get() != null) {
            localTest.get().log(status, details);
        }
    }

    public static ExtentTestDataAppender dataAppender() {
        return new ExtentTestDataAppender();
    }

    public static class ExtentTestDataAppender {

        private static final String REPORT_RESOURCES_FOLDER = "resources";
        private static final String RESOURCES_DIRPATH = TafConfig.TEST_REPORTS_DIRPATH.getValue()
                .concat("/").concat(TafConfig.TEST_RUN_NAME.getValue())
                .concat("/").concat(REPORT_RESOURCES_FOLDER);

        public void link(final String linkText, final String linkUrl) {
            if (localTest.get() == null) {
                log.warn("Extent test object is NOT initialized, adding link to the report would be skipped!");
                return;
            }
            final String link = "<a href='" + linkUrl + "' target='_blank' style='color: #04a1f4;" + "'/>" + "<b>" + linkText + "</b>";
            localTest.get().log(Status.INFO, MarkupHelper.createLabel(link, ExtentColor.TRANSPARENT));
        }

        public void image(final String message, final @NonNull File file) {
            image(message, file.getPath().replaceAll("\\\\", "/"));
        }

        public void image(final String message, String filepath) {
            if (localTest.get() == null) {
                log.warn("Extent test object is NOT initialized, adding image to the report would be skipped!");
                return;
            }
            try {
                final File file = FileUtils.getFile(filepath);
                if (filepath.startsWith("target/")) {
                    filepath = filepath.replace("target/", "");
                }
                FileUtils.copyFile(file, FileUtils.getFile(RESOURCES_DIRPATH.concat("/").concat(filepath)));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            localTest.get().log(Status.INFO, message,
                    MediaEntityBuilder.createScreenCaptureFromPath(REPORT_RESOURCES_FOLDER.concat("/").concat(filepath)).build());
        }

        public void htmlCode(final String message, final String htmlCode) {
            if (localTest.get() == null) {
                log.warn("Extent test object is NOT initialized, adding HTML code snippet to the report would be skipped!");
                return;
            }
            final String htmlName = "html_".concat(DateFormatUtils.getTimestamp("dd-MM-yyyy_HH-mm-ss-SSS"));
            final String filepath = RESOURCES_DIRPATH.concat("/").concat("htmls").concat("/").concat(htmlName).concat(".html");
            FileUtils.createParentDirectories(filepath).createFile(filepath, false);
            FileUtils.writeToFile(filepath, htmlCode.getBytes());
            localTest.get().log(Status.INFO, "<a href='" + filepath.replace(RESOURCES_DIRPATH, REPORT_RESOURCES_FOLDER)
                    + "'/>" + "<b>" + message + "</b>");
        }

        public void textarea(final String text) {
            if (localTest.get() == null) {
                log.warn("Extent test object is NOT initialized, adding textarea to the report would be skipped!");
                return;
            }
            localTest.get().log(Status.INFO, "<textarea>" + text + "</textarea>");
        }

        public void codeBlock(final String code, final CodeLanguage codeLanguage) {
            if (localTest.get() == null) {
                log.warn("Extent test object is NOT initialized, adding code block to the report would be skipped!");
                return;
            }
            localTest.get().log(Status.INFO, MarkupHelper.createCodeBlock(code, codeLanguage));
        }

    }

}