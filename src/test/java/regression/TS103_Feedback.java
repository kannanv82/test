package regression;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import coreFramework.ReportUtil;
import applicationSpecific.AUT;

public class TS103_Feedback {


	@Test
	public void testCaseFlow() throws InterruptedException {

		ReportUtil.startReporting();
		ReportUtil.reporterEvent("info", "This automation script covers Test Case Ids: 435-189, 435-190, 435-192, 435-193");
		AUT.login("url_QA","UserName","Password");
		
		AUT.validateFeedbackWindow();
		
		//commented due to defect #317 dashboard is not displayed
//		AUT.clickLeftMenu("HomeIcon", "DashboardHomePage");
				
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
