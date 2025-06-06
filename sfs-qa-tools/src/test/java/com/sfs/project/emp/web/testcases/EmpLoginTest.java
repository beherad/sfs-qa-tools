package com.sfs.project.emp.web.testcases;

import org.testng.annotations.Test;
import static com.sfs.project.emp.executions.categories.Category.RegressionGroup;
import com.sfs.global.qa.core.data.annotations.TmsData;
import com.sfs.global.qa.core.data.enums.TcType;
import com.sfs.project.emp.base.behaviour.core.EmpWebTestBase;
import com.sfs.project.emp.core.Constants;


public class EmpLoginTest extends EmpWebTestBase {


	@Test(groups = {RegressionGroup})
    @TmsData.Tc(tcId = 123,tcName = "test",tcType = TcType.REGRESSION)
    public void loginEmpApplication() {
        user = givenAUser(Constants.UserKEY);
        assertions().equals(getLoginBehavior().loginInToApplication(user.getUsername(), user.getPassword()));
    }
}
