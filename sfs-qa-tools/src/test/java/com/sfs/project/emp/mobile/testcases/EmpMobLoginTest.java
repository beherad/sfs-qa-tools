package com.sfs.project.emp.mobile.testcases;

import static com.sfs.project.emp.executions.categories.Category.RegressionGroup;

import org.testng.annotations.Test;

import com.sfs.global.qa.core.data.annotations.TmsData;
import com.sfs.global.qa.core.data.enums.TcType;
import com.sfs.project.emp.base.behaviour.core.EmpMobTestBase;
import com.sfs.project.emp.core.Constants;


public class EmpMobLoginTest extends EmpMobTestBase {


	@Test(groups = {RegressionGroup})
    @TmsData.Tc(tcId = 123,tcName = "test",tcType = TcType.REGRESSION)
    public void mobLoginEmpApplication() {
        user = givenAUser(Constants.UserKEY);
        assertions().equals(getMobLoginBehavior().loginInToApplication(user.getUsername(), user.getPassword()));
    }
}
