package coreFramework;

import org.testng.IClass;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;


/**
 * This class consists of all listners to TestNG
 * 
 * @author  Nagaraj Jayagondra
 * @version 1.0
 * @since   14-Dec-2015 
 */
public class CoreListener extends TestListenerAdapter {
	
	static IClass testCaseNameListner;
	
	/**
	   * This method is used to get the test case details
	   * 
	   * @author Nagaraj Jayagondra
	   */
	@Override
	public void onTestStart(ITestResult result) {

		  testCaseNameListner = result.getTestClass();

		}

}
