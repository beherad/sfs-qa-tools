package com.sfs.global.qa.core.framework.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Deprecated
public class TestCaseReport {

    /*//private static final String REPORT_RESOURCE_NAME = "resources";
    private static final String ASSERT_EQUALS = " assertEquals";
    //private static final String SOFT_ASSERT_EQUALS = " softAssertEquals";
    private static final String ASSERT_NOT_EQUALS = " assertNotEquals";
    private static final String ASSERT_CONTAINS = " assertContains";
    private static final String ASSERT_NULL = " assertNull";
    private static final String ASSERT_NOT_NULL = " assertNotNull";
    private static final String OPEN_INITIAL_A_TAG_HTML = "<a href='";
    private static final String CLOSE_INITIAL_A_TAG_HTML = "'/>";

    //private TestSuiteReport testSuiteReport;

    private static AtomicInteger count = new AtomicInteger(0);

    //@Getter
    //private String testName;
    //@Getter
    //private List<String> comments = new ArrayList<>();
    private String resourceFolder;

    public TestCaseReport(*//*TestSuiteReport testSuiteReport, String testName*//*) {
        //this.testSuiteReport = testSuiteReport;
        //this.testName = testName;
    }*/

    /*private static synchronized String returnHtmlCaptureName() {
        final String HTML_FORMAT = ".html";
        return getStringDateFolderFormat() + count.incrementAndGet() + HTML_FORMAT;
    }

    private static synchronized String returnImageCaptureName() {
        final String PNG_FORMAT = ".png";
        return getStringDateFolderFormat() + count.incrementAndGet() + PNG_FORMAT;
    }

    private static final String FOLDER_FORMAT = "MMddyyyyHHmmss";

    private static String getStringDateFolderFormat() {
        DateFormat logFormat = new SimpleDateFormat(FOLDER_FORMAT);
        return logFormat.format(Calendar.getInstance().getTime());
    }*/

    /*private String getStepAndMessage(String stepName, String message) {
        if (!stepName.isEmpty()) {
            return stepName + " - " + message;
        } else {
            return message;
        }
    }*/

    /*public void logImage(LogStatus logStatus, String message, File file) {
        logImage(logStatus, "", message, file);
    }*/

    /*public void logImage(LogStatus logStatus, String stepName, String message, File file) {
        String pathToImage = createResourceScreenshot(returnImageCaptureName(), file);
        extentTest.log(logStatus.getStatus(), getStepAndMessage(stepName, message),
                MediaEntityBuilder.createScreenCaptureFromPath(pathToImage).build());

    }*/

    /*public void logHtmlCapture(LogStatus logStatus, String message, String htmlCode) {
        logHtmlCapture(logStatus, "", message, htmlCode);
    }*/

    /*public void logHtmlCapture(LogStatus logStatus, String stepName, String message, String htmlCode) {
        extentTest.log(logStatus.getStatus(), OPEN_INITIAL_A_TAG_HTML + createResourceFile(returnHtmlCaptureName(), htmlCode)
                + CLOSE_INITIAL_A_TAG_HTML + getStepAndMessage(stepName, message));
    }*/

    /**
     * Log a link to the report.html file.
     * @param logStatus of the message.
     * @param stepName of the message.
     * @param htmlLink to be attached to the report.
     */
    /*public void logHtmlLink(LogStatus logStatus, String stepName, String htmlLink) {
        String fileLink = OPEN_INITIAL_A_TAG_HTML + htmlLink +  "' target='_blank' style='color: #04a1f4;"
                + CLOSE_INITIAL_A_TAG_HTML + "<b>" + stepName + "</b>";
        extentTest.log(logStatus.getStatus(), MarkupHelper.createLabel(fileLink, ExtentColor.TRANSPARENT));
    }*/

    /**
     * Log a link to the report.html file.
     * @param logStatus of the message.
     //* @param htmlLink to be attached to the report.
     */
    /*public void logHtmlLink(LogStatus logStatus, String htmlLink) {
        logHtmlLink(logStatus, htmlLink, htmlLink);
    }*/

    /*public void logHtmlTextarea(LogStatus logStatus, String stepName, String text) {
        final String open_initial_tag_html = "<textarea>";
        final String close_initial_tag_html = "</textarea>";
        extentTest.log(logStatus.getStatus(),
                open_initial_tag_html + getStepAndMessage(stepName, text)
                        + close_initial_tag_html);
    }*/

    /*@Deprecated
    private void assertLog(LogStatus logStatus, String message, String expected, String actual) {
        final String assert_result_message =
                message + " result is " + logStatus.name() + ": expected is: '" + expected
                        + "' , actual is: '" + actual + "'";
        log.info(assert_result_message);
    }*/

    /*@Deprecated
    private List<String> getListString(String input) {
        String[] listString;
        try {
            listString = input.split("\\r?\\n");
            return Arrays.asList(listString);
        } catch (Exception e) {
            log.error("Error reading - ".concat(input) + " - " + e.getMessage(), e);
            //addComment("Error reading - ".concat(e.getMessage()));
            //logMessage(LogStatus.ERROR, e, "Error reading - ".concat(input));
            throw e;
        }
    }

    @Deprecated
    private void assertHtml(String message, List<String> lfile1, List<String> lfile2) throws IOException {
        String html;
        html = FileDiff.getFileDiffAsHtml(lfile1, lfile2);
        try {
            Assert.assertTrue(html.compareTo("") == 0, message);
            //logHtmlCapture(LogStatus.PASS, "They are equal, click here to see diff", FileDiff.turnListIntoSLined(lfile1));
        } catch (AssertionError e) {
            //logHtmlCapture(LogStatus.ERROR, "Not equal, click here to see diff", html);
            throw e;
        } catch (Exception e) {
            log.error("Error comparing - ".concat(e.getMessage()), e);
            throw e;
        }
    }*/

    /*@Deprecated
    private void assertEqualsString(String message, String expected, String actual) {
        if (expected == null && actual == null) {
            Assert.assertEquals(message, expected, actual);
            assertLog(LogStatus.PASS, message + ASSERT_EQUALS, expected, actual);
        } else {
            if (expected == null || actual == null) {
                assertLog(LogStatus.ERROR, message + ASSERT_EQUALS, String.valueOf(expected), String.valueOf(actual));
                Assert.assertEquals(message, expected, actual);
            } else {
                List<String> lfile1 = getListString(expected);
                List<String> lfile2 = getListString(actual);
                try {
                    assertHtml(message, lfile1, lfile2);
                    assertLog(LogStatus.PASS, message + ASSERT_EQUALS, expected, actual);
                } catch (AssertionError e) {
                    assertLog(LogStatus.ERROR, message + ASSERT_EQUALS, expected, actual);
                    throw e;
                } catch (IOException e) {
                    log.error("Error generating html - ".concat(e.getMessage()), e);
                }
            }
        }
    }

    @Deprecated
    private void assertEqualsObject(String message, Object expected, Object actual) {
        try {
            Assert.assertEquals(actual, expected, message);
            assertLog(LogStatus.PASS, message + ASSERT_EQUALS, String.valueOf(expected), String.valueOf(actual));
        } catch (Exception e) {
            //log.error("Assert equals error - , actual is: '" + actual + "', expected is: '" + expected + "'", e);
            assertLog(LogStatus.ERROR, message + ASSERT_EQUALS, String.valueOf(expected), String.valueOf(actual));
            throw e;
        }
    }*/

    /*@Deprecated
    public void softAssertEquals(String message, Object expected, Object actual) {
        softAssertEqualsObject(message, expected, actual);
    }

    private void softAssertEqualsObject(String message, Object expected, Object actual) {
        try {
            Assert.assertEquals(actual, expected, message);
            assertLog(LogStatus.PASS, message, String.valueOf(expected), String.valueOf(actual));
        } catch (AssertionError e) {
            assertLog(LogStatus.ERROR, message, String.valueOf(expected), String.valueOf(actual));
        }
    }*/

    /*@Deprecated
    public void assertEquals(String message, String expected, String actual) {
        try {
            assertEqualsObject(message, expected, actual);
        } catch (Exception e) {
            assertEqualsString(message, String.valueOf(expected), String.valueOf(actual));
        }
    }*/

    /*@Deprecated
    public void assertEquals(String message, Object expected, Object actual) {
        assertEqualsObject(message, expected, actual);
    }

    @Deprecated
    public void assertEquals(String message, double expected, double actual, double delta) {
        try {
            Assert.assertEquals(actual, expected, delta, message);
            assertLog(LogStatus.PASS, message + ASSERT_EQUALS, String.valueOf(expected), String.valueOf(actual));
        } catch (Exception e) {
            assertLog(LogStatus.ERROR, message + ASSERT_EQUALS, String.valueOf(expected), String.valueOf(actual));
            throw e;
        }
    }*/

    /*@Deprecated
    public void assertNotEquals(String message, Object expected, Object actual) {
        try {
            Assert.assertNotEquals(actual, expected, message);
            assertLog(LogStatus.PASS, message + ASSERT_NOT_EQUALS, String.valueOf(expected), String.valueOf(actual));
        } catch (Exception e) {
            assertLog(LogStatus.ERROR, message + ASSERT_NOT_EQUALS, String.valueOf(expected), String.valueOf(actual));
            throw e;
        }
    }*/

    /*@Deprecated
    public void assertContains(String message, String expected, String actual) {
        if (bothNotNull(expected, actual) && !justOneIsEmpty(expected, actual) && (
                expected.contains(actual) || actual.contains(expected))) {
            assertLog(LogStatus.PASS, message + ASSERT_CONTAINS, expected, actual);
        } else {
            assertLog(LogStatus.ERROR, message + ASSERT_CONTAINS, expected, actual);
            throw new AssertionError(ASSERT_CONTAINS + expected + actual);
        }
    }*/

    /*@Deprecated
    private boolean bothNotNull(String expected, String actual) {
        return (actual != null) && (expected != null);
    }

    private boolean justOneIsEmpty(String expected, String actual) {
        return (!expected.isEmpty() && actual.isEmpty()) || (expected.isEmpty() && !actual
                .isEmpty());
    }

    @Deprecated
    public void assertNotNull(String message, Object object) {
        try {
            Assert.assertNotNull(object);
            assertLog(LogStatus.PASS, message + ASSERT_NOT_NULL, null, String.valueOf(object));
        } catch (Exception e) {
            assertLog(LogStatus.ERROR, message + ASSERT_NOT_NULL, null, String.valueOf(object));
            throw e;
        }
    }

    @Deprecated
    public void assertNull(String message, Object object) {
        try {
            Assert.assertNull(object);
            assertLog(LogStatus.PASS, message + ASSERT_NULL, null, String.valueOf(object));
        } catch (Exception e) {
            assertLog(LogStatus.ERROR, message + ASSERT_NULL, null, String.valueOf(object));
            throw e;
        }
    }*/

    /*private String createResourceFile(String fileName, String content) {
        FolderManager folderManager = new FolderManager();
        resourceFolder = folderManager.createFolder(testSuiteReport.getTestReportDirpath(), REPORT_RESOURCE_NAME);
        String path = folderManager.createFile(resourceFolder, fileName);
        try {
            PrintWriter writer = new PrintWriter(path, "UTF-8");
            writer.println(content);
            writer.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return "." + File.separator + REPORT_RESOURCE_NAME + File.separator + fileName;
    }

    private String createResourceScreenshot(String fileName, File screenshot) {
        FolderManager folderManager = new FolderManager();
        resourceFolder = folderManager.createFolder(testSuiteReport.getTestReportDirpath(), REPORT_RESOURCE_NAME);
        String destinationPath = resourceFolder + File.separator + fileName;
        File destination = new File(destinationPath);
        try {
            FileUtils.copyFile(screenshot, destination);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return "." + File.separator + REPORT_RESOURCE_NAME + File.separator + fileName;
    }*/

    /*public void addComment(String comment) {
        comments.add(comment.substring(0, Math.min(comment.length(), 1000000)));
    }*/
}