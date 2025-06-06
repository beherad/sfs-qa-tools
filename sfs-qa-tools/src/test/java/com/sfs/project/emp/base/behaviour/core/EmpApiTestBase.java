package com.sfs.project.emp.base.behaviour.core;

import org.testng.annotations.BeforeMethod;

import com.sfs.global.qa.core.config.TafConfig;
import com.sfs.global.qa.core.test.TestBase;
import com.sfs.project.emp.api.behaviour.LoginApiSteps;
import com.sfs.project.emp.web.common.User;
import com.sfs.project.emp.web.common.UserFactory;
import com.sfs.project.emp.web.common.Users;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmpApiTestBase extends TestBase {

  @Getter(AccessLevel.PROTECTED)
  protected User user;
  protected String baseUri;
  private static final String EXEC_ENV_NAME = TafConfig.EXEC_ENV_NAME.getValue();
  
  @BeforeMethod(alwaysRun = true)
  public void setup() {
      user = new User();
      baseUri = TafConfig.getAppConfigProperty("baseuri");
  }
  
  /**
   * Page classes
  **/
  
  public LoginApiSteps getLoginBehavior() {
      return new LoginApiSteps();
  }
  
  public static User givenAUser(String userKey) {
      UserFactory userFactory = new UserFactory();
      User user = userFactory.getUser(userKey);
      (new Users()).addUser(user);
      return user;
  }
}

