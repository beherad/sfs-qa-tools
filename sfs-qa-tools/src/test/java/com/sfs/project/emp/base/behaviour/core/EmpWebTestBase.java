package com.sfs.project.emp.base.behaviour.core;

import java.util.Map;
import java.util.logging.Level;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.logging.LogType;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.sfs.global.qa.core.config.TafConfig;
import com.sfs.global.qa.core.test.TestBase;
import com.sfs.global.qa.web.tools.webdriver.Browser;
import com.sfs.global.qa.web.tools.webdriver.BrowserOptions;
import com.sfs.global.qa.web.tools.webdriver.BrowserType;
import com.sfs.project.emp.web.behaviour.core.steps.LoginPageSteps;
import com.sfs.project.emp.web.common.FilePathReader;
import com.sfs.project.emp.web.common.User;
import com.sfs.project.emp.web.common.UserFactory;
import com.sfs.project.emp.web.common.Users;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmpWebTestBase extends TestBase {

  @Getter(AccessLevel.PROTECTED)
  private Browser browser;
  protected User user;
  private static final String EXEC_ENV_NAME = TafConfig.EXEC_ENV_NAME.getValue();
  
  @BeforeMethod(alwaysRun = true)
  public void setup() {
      user = new User();
      startBrowser().getDriver().get(TafConfig.getAppConfigProperty("baseurl"));
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
      closeBrowser();
  }
  
  /**
   * Page classes
  **/
  
  public LoginPageSteps getLoginBehavior() {
      if (this.browser == null) {
          this.browser = getBrowser();
      }
      return new LoginPageSteps();
  }
  
  
  /**
   * Generic functions
   * 
  **/

  public void closeBrowser() {
      try {
    	  if (getBrowser() != null) {
              log().image("Test result", getBrowser().takeScreenshot());
              getBrowser().quit();
              browser = null;
          }
      } catch (Exception e) {
          log.error("Final screenshot take");
      }
  }

  public static User givenAUser(String userKey) {
      UserFactory userFactory = new UserFactory();
      User user = userFactory.getUser(userKey);
      (new Users()).addUser(user);
      return user;
  }

  public EmpWebTestBase() {
      super();
  }

  public void setAuthor(String author) {
      log.info("Created by: " + author);
  }

  public void assertEquals(String message, boolean expected, boolean actual) {
      try {
          assertions().assertThat(actual).as(message).isEqualTo(expected);
          log().image(message, getBrowser().takeScreenshot());
      } catch (AssertionError e) {
          log.error(this.getClass().getSimpleName(),e.getLocalizedMessage());
          throw e;
      }
  }

  public void softAssertEquals(String message, boolean expected, boolean actual) {
      try {
          assertions().soft().assertThat(actual).as(message).isEqualTo(expected);
      } catch (AssertionError e) {
          log.info(this.getClass().getSimpleName(),e.getLocalizedMessage());
      } finally {
    	  log().image(message, getBrowser().takeScreenshot());
      }

  }

  public void softAssertSkip(String message, boolean expected, boolean actual) {
      if (expected != actual) {
          log.warn("SKIPPED: {} - Expected: {}, Actual: {}", message, expected, actual);
          throw new SkipException("Test is skipped: " + message);
      }
  }


  public void logTestStep(String stepName, String stepDescription) {
      log.info("<h3><strong>" + stepName + ":</strong> " + stepDescription + "</h3>");
  }

  
  public Browser startBrowser() {
      if (browser == null) {
          /*browser = buildBrowser();*/
          if ((EXEC_ENV_NAME.contains("grid"))) {
              browser = Browser.builder(BrowserType.getByName(TafConfig.Web.BROWSER_NAME.getValue()))
                      .browserOptions(BrowserOptions.hub()
                              .customGrid(TafConfig.Web.HUB_URL.getValue(), BrowserOptions.chrome()
                                      .incognitoMode()
                                      .setHeadless()
                                      .disableInfobars()
                                      .setAcceptInsecureCerts(true)
                                      .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE)
                                      .disableSearchEngineSelection()
                                      // .setProxy(...)
                                      // .addArguments(...)
                                      // .setCapability(...)
                                      .build())
                              .build())
                      .build();
              browser.windowFocus()
                      .windowMaximize().deleteAllCookies();
              } else if ((EXEC_ENV_NAME.contains("selenoid"))) {
                  browser = Browser.builder(BrowserType.getByName(TafConfig.Web.BROWSER_NAME.getValue()))
                          .browserOptions(BrowserOptions.hub()
                                  .selenoid(TafConfig.Web.HUB_URL.getValue(), BrowserOptions.chrome()
                                          .incognitoMode()
                                          .setHeadless()
                                          .disableInfobars()
                                          .setAcceptInsecureCerts(true)
                                          .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE)
                                          .disableSearchEngineSelection()
                                          .setCapability("enableVNC", false)
                                          .setCapability("enableVideo", false)
                                          // .setProxy(...)
                                          // .addArguments(...)
                                          // .setCapability(...)
                                          .build())
                                  .build())
                          .build();
                  browser.windowFocus()
                          .windowMaximize().deleteAllCookies();

          } else {
              browser = Browser.builder(BrowserType.getByName(TafConfig.Web.BROWSER_NAME.getValue()))
                      .browserOptions(BrowserOptions.chrome()
                              .incognitoMode()
                              .disableInfobars()
                              .setAcceptInsecureCerts(true)
                              .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE)
                              .disableSearchEngineSelection()
                              // .setProxy(...)
                              // .addArguments(...)
                              // .setCapability(...)
                              .build())
                      .build();
              browser.windowFocus()
                      .windowMaximize().deleteAllCookies();
          }
      }
      return browser;

  }

  private BrowserOptions chromeOptions() {
      return BrowserOptions.chrome()
              .addArguments("allow-file-access-from-files",
                      "use-fake-device-for-media-stream",
                      "use-fake-ui-for-media-stream",
                      "--disable-popup-blocking",
                      "--force-fieldtrials=SiteIsolationExtensions/Control",
                      "--disable-backgrounding-occluded-windows",
                      "--disable-web-security",
                      "download.prompt_for_download=false",
                      "download.directory_upgrade=true",
                      "default_content_setting_values.notifications=0",
                      "content_settings.exceptions.automatic_downloads.*.setting=1",
                      "safebrowsing.disable_download_protection=true",
                      "default_content_settings.popups=0",
                      "managed_default_content_settings.popups=0",
                      "profile.password_manager_enabled=false",
                      "profile.default_content_setting_values.notifications=2",
                      "profile.default_content_settings.popups=0",
                      "profile.managed_default_content_settings.popups=0",
                      "profile.default_content_setting_values.automatic_downloads=1",
                      "safebrowsing-disable-extension-blacklist",
                      "safebrowsing-disable-download-protection",
                      "disable-popup-blocking",
                      "safebrowsing-disable-download-protection",
                      "test-type",
                      "disable-infobars",
                      "disable-notifications",
                      "disable-extensions",
                      "--disable-search-engine-choice-screen")
              .setExperimentalOption("prefs", Map.of(
                      "intl.accept_languages", "de-DE",
                      "safebrowsing.enabled", false,
                      "safebrowsing.disable_download_protection", true,
                      "download.default_directory", FilePathReader.getDownloadFolder(),
                      "savefile.default_directory", FilePathReader.getDownloadFolder(),
                      "download.prompt_for_download", false,
                      "download.directory_upgrade", true,
                      "profile.default_content_settings.popups", 0))
              .setCapability("unhandledPromptBehavior", UnexpectedAlertBehaviour.IGNORE)
              .setCapability("acceptInsecureCerts", true)
              .setCapability("goog:loggingPrefs", Map.of(
                      LogType.BROWSER, Level.ALL,
                      LogType.PERFORMANCE, Level.ALL))
              .build();
  }


  private Browser buildBrowser() {
      //Creating Browser Options
      BrowserOptions options;

      String hub = TafConfig.EXEC_ENV_NAME.getValue();
      if (hub.contains("grid")) {
          options = BrowserOptions.hub()
                  .customGrid(TafConfig.Web.HUB_URL.getValue(), chromeOptions()).build();
      } else if (hub.contains("selenoid")){
          options = BrowserOptions.hub()
                  .selenoid(TafConfig.Web.HUB_URL.getValue(), chromeOptions()).build();
      } else {
          options = chromeOptions();
      }
      return Browser.builder(BrowserType.getByName(TafConfig.Web.BROWSER_NAME.getValue()))
              .browserOptions(options).build();
  }
}

