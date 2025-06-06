package com.sfs.project.emp.web.behaviour.core.steps;

import com.sfs.global.qa.web.tools.webdriver.BrowserPage;
import com.sfs.project.emp.web.behaviour.pages.LoginPage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginPageSteps extends BrowserPage {
	private LoginPage loginPage = new LoginPage();

    public LoginPageSteps() {
        super();
    }

    public boolean loginInToApplication(String username,String password) {
        return loginPage.loginInToEmp(username,password);
    }
}
