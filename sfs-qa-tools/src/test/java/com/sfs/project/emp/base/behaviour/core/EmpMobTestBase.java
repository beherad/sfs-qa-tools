package com.sfs.project.emp.base.behaviour.core;

import org.openqa.selenium.OutputType;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.sfs.global.qa.core.config.TafConfig;
import com.sfs.global.qa.core.test.TestBase;
import com.sfs.global.qa.mobile.tools.appium.MobileDevice;
import com.sfs.project.emp.mob.behaviour.core.steps.LoginPageMobSteps;
import com.sfs.project.emp.web.common.User;
import com.sfs.project.emp.web.common.UserFactory;
import com.sfs.project.emp.web.common.Users;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmpMobTestBase extends TestBase{

  @Getter(AccessLevel.PROTECTED)
  private MobileDevice device;
  protected User user;
  private static final String EXEC_ENV_NAME = TafConfig.EXEC_ENV_NAME.getValue();
  
  @BeforeMethod(alwaysRun = true)
  public void setup() {
      user = new User();
      device.launchApp(EXEC_ENV_NAME);
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
	  closeDevice();
  }
  
  /**
   * Page classes
  **/
  
  public LoginPageMobSteps getMobLoginBehavior() {
      if (this.getDevice() == null) {
          this.device = getDevice();
      }
      return new LoginPageMobSteps(null, null);
  }
  
  
  /**
   * Generic functions
   * 
  **/

  public void closeDevice() {
      try {
    	  if (getDevice() != null) {
              log().image("Test result", getDevice().getDriver().getScreenshotAs(OutputType.FILE).getAbsoluteFile());
              getDevice().closeApp(EXEC_ENV_NAME);
              device = null;
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

  public EmpMobTestBase() {
      super();
  }

  public void setAuthor(String author) {
      log.info("Created by: " + author);
  }

  public void assertEquals(String message, boolean expected, boolean actual) {
      try {
          assertions().assertThat(actual).as(message).isEqualTo(expected);
          log().image(message,  getDevice().getDriver().getScreenshotAs(OutputType.FILE).getAbsoluteFile());
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
    	  log().image(message,  getDevice().getDriver().getScreenshotAs(OutputType.FILE).getAbsoluteFile());
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
}

