package regression;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import coreFramework.CommonWebActions;
import coreFramework.ReportUtil;
import applicationSpecific.AUT;

public class TS105_HelpAndSupport {


	@Test
	public void testCaseFlow() {

		ReportUtil.startReporting();
		AUT.login("url_QA","UserName","Password");
		
		AUT.clickLeftMenu("HelpNSupportLink", "HelpNSupportHeader");
		
		AUT.validateElement("HotlineTab");
		
		AUT.helpNSupportValidation();
		
		
		AUT.logOff();
		
		AUT.closeBrowserWindow();
		ReportUtil.endReporter();

	}


	@AfterMethod
	public void tearDown(ITestResult result) {
		if(result.getStatus()==ITestResult.FAILURE){
			ReportUtil.reporterEvent("fatal", "Test case Failed or Incomplete");
			CommonWebActions.closeBrowser();
			ReportUtil.endReporter();
		}
	}
}
