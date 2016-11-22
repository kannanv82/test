package coreFramework;

import java.io.PrintStream;
import java.sql.Timestamp;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


/**
* This class consists of methods to perform reporting
* 
* @author  Nagaraj Jayagondra
* @version 1.0
* @since   10-Dec-2015 
*/

public class ReportUtil extends CoreListener {
	
	static ExtentReports report;
	static ExtentTest logger;
	public static String onlyTestCaseName;

	/**
	   * This method is used to initiate the reporting
	   * 
	   * @author Nagaraj Jayagondra
	   */
	public static void startReporting(){
		try{
			String currDir = System.getProperty("user.dir");
			String testCaseName = testCaseNameListner.toString();
			String[] arrtestCaseNameDetails = testCaseName.split("=");
			String testCaseNameOnly = arrtestCaseNameDetails[1];
			testCaseNameOnly = testCaseNameOnly.replace("class ", "");
			testCaseNameOnly = testCaseNameOnly.replace("]", "");
			String lTimeStamp = getTimeAsString();

			String[] packageNameDetails = testCaseNameOnly.split("\\.");
			String testPackageName = packageNameDetails[0];
			onlyTestCaseName = packageNameDetails[1];

			String reportPath = currDir + "\\report-output\\" + testPackageName + "\\" + testCaseNameOnly + "_" + lTimeStamp + ".html";
			
			
			report=new ExtentReports(reportPath);
			logger=report.startTest(onlyTestCaseName, testCaseNameOnly);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	   * This method is used to get time stamp as a formated string
	   * 
	   * @return time stamp as formated string
	   * @author Nagaraj Jayagondra
	   */
	public static String getTimeAsString(){
		java.util.Date date= new java.util.Date();
		Timestamp currentTimestamp= new Timestamp(date.getTime());
		String currentTimeStampAsString = currentTimestamp.toString();
		currentTimeStampAsString = currentTimeStampAsString.replace("-", "");
		currentTimeStampAsString = currentTimeStampAsString.replace(":", "");
		currentTimeStampAsString = currentTimeStampAsString.replace(".", "");
		currentTimeStampAsString = currentTimeStampAsString.replace(" ", "");
		return currentTimeStampAsString;
	}

	/**
	   * This method is report the step details in the overall automation report
	   * 
	   * @author Nagaraj Jayagondra 
	   * @param status This is the status to be set. Example pass, info, fail, fatal, warning, error, skip and unknown
	   * @param description This is the description you want to be in the report
	   */
	public static void reporterEvent(String status, String description){
		
		if (status.equals("info")) 
		
			logger.log(LogStatus.INFO, description);			
		else if (status.equals("fail"))
		
			logger.log(LogStatus.FAIL, description);
		else if (status.equals("pass"))
		
			logger.log(LogStatus.PASS, description);
		else if (status.equals("error"))
		
			logger.log(LogStatus.ERROR, description);
		else if (status.equals("fatal"))
		
			logger.log(LogStatus.FATAL, description);
		else if (status.equals("warning"))
		
			logger.log(LogStatus.WARNING, description);
		else if (status.equals("skip"))
		
			logger.log(LogStatus.SKIP, description);
		else if (status.equals("unknown"))
		
			logger.log(LogStatus.UNKNOWN, description);
			

		
		
	}
	
	
	/**
	   * This method is used to close the reporter event. To be called at the end of all steps
	   * 
	   * @author Nagaraj Jayagondra
	 * @return 
	   */
	public static void endReporter(){
		//CommonWebActions.closeBrowser();
		report.endTest(logger);
		report.flush();
	}

	
	
	
	

}
