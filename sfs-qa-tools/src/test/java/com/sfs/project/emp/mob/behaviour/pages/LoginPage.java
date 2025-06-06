package com.sfs.project.emp.mob.behaviour.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sfs.global.qa.mobile.tools.appium.MobileDevice;

public class LoginPage extends MobileDevice {

    @FindBy(xpath = "//input[@id='first_name']")
    WebElement userName;
    @FindBy(xpath = "//input[@id='last_name']")
    WebElement password;
    @FindBy(xpath = "//input[@id='company']")
    WebElement signInBtn;

    public LoginPage() {
        super(null, null);
    }


    public boolean loginInToEmp(String username, String password) {
      return true;
    }
}
