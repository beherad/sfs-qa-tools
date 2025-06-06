package com.sfs.project.emp.mob.behaviour.core.steps;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.sfs.global.qa.mobile.tools.appium.MobileDevice;
import com.sfs.global.qa.mobile.tools.appium.MobileDeviceConfig;
import com.sfs.project.emp.web.behaviour.pages.LoginPage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginPageMobSteps extends MobileDevice {
	public LoginPageMobSteps(MobileDeviceConfig mobileDeviceConfig, DesiredCapabilities extraCapabilities) {
		super(mobileDeviceConfig, extraCapabilities);
		// TODO Auto-generated constructor stub
	}



	private LoginPage loginPage = new LoginPage();



    public boolean loginInToApplication(String username,String password) {
        return loginPage.loginInToEmp(username,password);
    }
}
