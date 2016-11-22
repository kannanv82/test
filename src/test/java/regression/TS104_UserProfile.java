package regression;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import coreFramework.ReportUtil;
import applicationSpecific.AUT;

public class TS104_UserProfile {


	@Test
	public void testCaseFlow() throws Exception {

		ReportUtil.startReporting();
		ReportUtil.reporterEvent("info", "This automation script covers Test Case Ids: 106-122, 106-123, 106-124, 632-430");
		AUT.login("url_QA","UserName","Password");
		
		AUT.validateUserProfile();
	
		
		AUT.logOff();
		
		AUT.closeBrowserWindow();
		
		ReportUtil.endReporter();

	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		if(result.getStatus()==ITestResult.FAILURE){
			ReportUtil.reporterEvent("fatal", "Test case incomplete");
			ReportUtil.endReporter();
		}
	}
}

