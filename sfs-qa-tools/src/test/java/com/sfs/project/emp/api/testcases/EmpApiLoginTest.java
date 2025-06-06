package com.sfs.project.emp.api.testcases;

import org.testng.annotations.Test;
import static com.sfs.project.emp.executions.categories.Category.RegressionGroup;

import com.sfs.global.qa.core.config.TafConfig;
import com.sfs.global.qa.core.data.annotations.TmsData;
import com.sfs.global.qa.core.data.enums.TcType;
import com.sfs.project.emp.base.behaviour.core.EmpApiTestBase;
import com.sfs.project.emp.core.Constants;


public class EmpApiLoginTest extends EmpApiTestBase {


	@Test(groups = {RegressionGroup})
    @TmsData.Tc(tcId = 123,tcName = "test",tcType = TcType.REGRESSION)
    public void apiloginEmpApplication() {
        user = givenAUser(Constants.UserKEY);
        System.out.println(getLoginBehavior().getdata(baseUri));
    }
}
