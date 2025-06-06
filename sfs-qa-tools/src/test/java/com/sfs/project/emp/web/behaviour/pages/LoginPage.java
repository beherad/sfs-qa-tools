package com.sfs.project.emp.web.behaviour.pages;

import com.sfs.global.qa.web.tools.webdriver.BrowserPage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BrowserPage {

    @FindBy(xpath = "//input[@id='first_name']")
    WebElement userName;
    @FindBy(xpath = "//input[@id='last_name']")
    WebElement password;
    @FindBy(xpath = "//input[@id='company']")
    WebElement signInBtn;

    public LoginPage() {
        super();
    }


    public boolean loginInToEmp(String username, String password) {
      return true;
    }
}
