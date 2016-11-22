package regression;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import coreFramework.ReportUtil;
import applicationSpecific.AUT;

/****** This script covers below test cases
*Test Case 27-98 - User Name Display in Home Page
*Test Case 113-126-Log out and login again on the same browser by hitting the Url
*
*/
public class TS101_NetStarLoginLogout {

	@Test
	public void testCaseFlow() {
		
		ReportUtil.startReporting();
		ReportUtil.reporterEvent("info", "This automation script covers Test Case Ids: 27-98 and 113-126");
		AUT.login("url_QA","UserName","Password");

		AUT.logOff();
		
		AUT.loginOnExistingBrowser("url_QA","UserName","Password");
		
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