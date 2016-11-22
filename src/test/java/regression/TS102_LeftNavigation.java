package regression;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import coreFramework.ReportUtil;
import applicationSpecific.AUT;

public class TS102_LeftNavigation {


	@Test
	public void testCaseFlow() throws InterruptedException {

		ReportUtil.startReporting();
		ReportUtil.reporterEvent("info", "This automation script covers Test Case Ids: 1-103, 1-104, 1-105, 1-106, 1-107, 1-108, 1-109");
		AUT.login("url_QA","UserName","Password");
		
	
		AUT.validateLeftBottomLinks();
		
		AUT.validateToggleShrink();
		
		AUT.validateMenuIconClick();
		
		AUT.toggleToExpand();
		
		AUT.validateMenuLinkClick();
		
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
