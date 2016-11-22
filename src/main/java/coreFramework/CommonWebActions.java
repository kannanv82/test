package coreFramework;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * This class consists of all the common methods required to perform
 * actions on web application
 * 
 * @author  Nagaraj Jayagondra
 * @version 1.0
 * @since   10-Dec-2015 
 */
public class CommonWebActions extends ReportUtil {

	public static WebDriver wd = null;
	static RemoteWebDriver remoteWD = null;
	static boolean isElementDisplayed;
	static JavascriptExecutor javaScriptDriver;

	/**
	 * This method is used to launch the application.
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param strURL This is the URL of the AUT
	 */
	public static void launch(String strEnvURL){
		try{
			ReportUtil.startReporting();

			String browserType = ORUtil.getConfigValue("BrowserType");
			String url = ORUtil.getConfigValue(strEnvURL);
			String implicitWait = ORUtil.getConfigValue("Implicit_Wait");
			int waitTime = Integer.parseInt(implicitWait);

			String ieDriverPath = System.getProperty("user.dir") + "\\src\\main\\resources\\IEDriverServer_Win32_2.48.0\\IEDriverServer.exe";
			String chromeDriverPath = System.getProperty("user.dir") + "\\src\\main\\resources\\chromedriver_2.20_win32\\chromedriver.exe";
			
			
			if (browserType.equalsIgnoreCase("firefox")){
				wd = new FirefoxDriver();
				javaScriptDriver = (JavascriptExecutor) wd;
			} else if (browserType.equalsIgnoreCase("iexplore")) {
				System.setProperty("webdriver.ie.driver", ieDriverPath);
//				DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
//				capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
//				capability.setCapability("useLegacyInternalServer", true);
//				capability.setCapability("nativeEvents", false);
//				wd = new InternetExplorerDriver(capability);
				wd = new InternetExplorerDriver();
				javaScriptDriver = (JavascriptExecutor) wd;
			} else if (browserType.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", chromeDriverPath);
				wd = new ChromeDriver();
				javaScriptDriver = (JavascriptExecutor) wd;
			}

			webImplicitWait(wd, waitTime);
			wd.manage().window().maximize();
			wd.get(url);
			ReportUtil.reporterEvent("pass", "Launched browser [ " + browserType + " ] with url [ " + url + " ]");
		}catch(Exception e){
			e.printStackTrace();
			ReportUtil.reporterEvent("fatal", "Unable to launch the application" + captureScreenshotAsBase64());
		}
	}

	public static void reLaunchURL(String strLaunchURL)
	{
		try
		{
//		wd.get(strLaunchURL);
		String url = ORUtil.getConfigValue(strLaunchURL);
		wd.navigate().to(url);
		ReportUtil.reporterEvent("pass", "Launched application with url [ " + strLaunchURL + " ]");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			ReportUtil.reporterEvent("fatal", "Unable to launch the application" + captureScreenshotAsBase64());
		}
	}
	
	/**
	 * This method is used to locate the web object in the application.
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  objLogicalName This is the logical name of the web object in property file (OR)
	 * @return WebElement This returns the WebElement if web object is found in the AUT
	 */
	public static WebElement getWebElement(String objLogicalName){

		WebElement webEle = null;
		try {
			String ORValue = ORUtil.getValue(objLogicalName);
			String propType = null;
			String propActVal = null;
			if (ORValue.equals("ELEMENTNOTFOUND")) {
				ReportUtil.reporterEvent("fail","Object with logical name [ " + objLogicalName + " ] not found in Object Repository. Please check...");
				Assert.assertEquals(ORValue.equals("ELEMENTNOTFOUND"), false);
			}
			else {
				String[] parts = ORValue.split(":=");
				propType = parts[0];
				propActVal = parts[1];

				if (propType.equalsIgnoreCase("id")) {
					webEle = wd.findElement(By.id(propActVal));
				} else if (propType.equalsIgnoreCase("name")) {
					webEle = wd.findElement(By.name(propActVal));
				} else if (propType.equalsIgnoreCase("xpath")) {
					webEle = wd.findElement(By.xpath(propActVal));
				} else if (propType.equalsIgnoreCase("linktext")) {
					webEle = wd.findElement(By.linkText(propActVal));
				} else if (propType.equalsIgnoreCase("cssselector")) {
					webEle = wd.findElement(By.cssSelector(propActVal));
				} else if (propType.equalsIgnoreCase("partiallinktext")) {
					webEle = wd.findElement(By.partialLinkText(propActVal));
				} else if (propType.equalsIgnoreCase("classname")) {
					webEle = wd.findElement(By.className(propActVal));
				} else {
					ReportUtil.reporterEvent("fail","Invalid property type defined in Object Repository for web element [ " + objLogicalName + " ]");
				}
			}

		} catch (Exception e) {
			//			ReportUtil.reporterEvent("fatal", "Unable to find web element Error [ " + e.getMessage() + " ]");
			e.printStackTrace();
		}
		return webEle;
	}
	/**
	 * This method is used to locate the web object in the application.
	 * 
	 * @author Kannan Venkadasamy 
	 * @param  objLogicalName This is the logical name of the web object in property file (OR)
	 * @return List This returns the WebElements if web object is found in the AUT
	 */
	public static List<WebElement> getWebElements(String objLogicalName){

		List<WebElement> webEle = null;
		try {
			String ORValue = ORUtil.getValue(objLogicalName);
			String propType = null;
			String propActVal = null;
			if (ORValue.equals("ELEMENTNOTFOUND")) {
				ReportUtil.reporterEvent("fail","Object with logical name [ " + objLogicalName + " ] not found in Object Repository. Please check...");
				Assert.assertEquals(ORValue.equals("ELEMENTNOTFOUND"), false);
			}
			else {
				String[] parts = ORValue.split(":=");
				propType = parts[0];
				propActVal = parts[1];

				if (propType.equalsIgnoreCase("id")) {
					webEle = wd.findElements(By.id(propActVal));
				} else if (propType.equalsIgnoreCase("name")) {
					
					webEle = wd.findElements(By.name(propActVal));
				} else if (propType.equalsIgnoreCase("xpath")) {
					
					webEle = wd.findElements(By.xpath(propActVal));
				} else if (propType.equalsIgnoreCase("linktext")) {
					webEle = wd.findElements(By.linkText(propActVal));
				} else if (propType.equalsIgnoreCase("cssselector")) {
					webEle = wd.findElements(By.cssSelector(propActVal));
				} else if (propType.equalsIgnoreCase("partiallinktext")) {
					webEle = wd.findElements(By.partialLinkText(propActVal));
				} else if (propType.equalsIgnoreCase("classname")) {
					webEle = wd.findElements(By.className(propActVal));
				} else {
					ReportUtil.reporterEvent("fail","Invalid property type defined in Object Repository for web element [ " + objLogicalName + " ]");
				}
			}

		} catch (Exception e) {
			//			ReportUtil.reporterEvent("fatal", "Unable to find web element Error [ " + e.getMessage() + " ]");
			e.printStackTrace();
		}
		return webEle;
	}
	/**
	 * This method is used to check Stins is matches to given Regular Expression
	 * 
	 * @author Kannan Venkadasamy 
	 * @param  element This is the WebElement 
	 * @return returns true if matches else false
	 */
	public static boolean stringMatchesToRegExp(String str, String regExp){
		boolean flag;
		if(str.matches(regExp))
			flag=true;
		else 
			flag=false;
		return flag;
			
	}
	

	
	/**
	 * This method is used to check if the elements exists or not
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  element This is the WebElement 
	 * @return returns true if object exists else false
	 */
	public static boolean webExists(WebElement element){
		try{
			return element.isDisplayed();
		}catch(NoSuchElementException e){
			return false;
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * This method is used to click on the specified web element
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  objName This is the logical name of the web object in property file (OR)
	 */
	public static void webClick(String objName){
		WebElement lwebElement = null;
		try {
			lwebElement = getWebElement(objName);
			lwebElement.click();
			ReportUtil.reporterEvent("info", "Clicked on [ " + objName + " ] web element");
		} catch (Exception e) {
			ReportUtil.reporterEvent("fail", "Web Element [ " + objName + " ] NOT FOUND on UI" + captureScreenshotAsBase64());
			Assert.assertEquals(webExists(lwebElement), true, "Object Not Found UI. Hence stoping the execution of test case");
		}

	}
	
	public static void webClick(WebElement objName){
		
		try {
			
			objName.click();
			ReportUtil.reporterEvent("info", "Clicked on [ " + objName + " ] web element");
		} catch (Exception e) {
			ReportUtil.reporterEvent("fail", "Web Element [ " + objName + " ] NOT FOUND on UI" + captureScreenshotAsBase64());
			Assert.assertEquals(webExists(objName), true, "Object Not Found UI. Hence stoping the execution of test case");
		}

	}

	/**
	 * This method is used to enter value in the text box
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  objName This is the logical name of the web object in property file (OR)
	 * @param  strTextToSend This is the text to be entered or the column name in the test data sheet
	 */
	public static void webSet(String objName, String strTextToSend){
		WebElement lwebElement = null;
		try {
			lwebElement = getWebElement(objName);
			strTextToSend = ExcelUtil.getDataFromExcel(onlyTestCaseName,strTextToSend);
			lwebElement.clear();
			lwebElement.sendKeys(strTextToSend);
			ReportUtil.reporterEvent("info", "Text [ " + strTextToSend + " ] entered in the web element [ " + objName + " ]" );
		} catch (Exception e) {
			ReportUtil.reporterEvent("fail", "Web Element [ " + objName + " ] NOT FOUND on UI" + captureScreenshotAsBase64());
			Assert.assertEquals(webExists(lwebElement), true, "Object Not Found UI. Hence stoping the execution of test case");
		}

	}

	public static void webSetText(String objName, String strTextToSend){
		WebElement lwebElement = null;
		try {
			lwebElement = getWebElement(objName);
//			strTextToSend = ExcelUtil.getDataFromExcel(onlyTestCaseName,strTextToSend);
			lwebElement.clear();
			lwebElement.sendKeys(strTextToSend);
			ReportUtil.reporterEvent("info", "Text [ " + strTextToSend + " ] entered in the web element [ " + objName + " ]" );
		} catch (Exception e) {
			ReportUtil.reporterEvent("fail", "Web Element [ " + objName + " ] NOT FOUND on UI" + captureScreenshotAsBase64());
			Assert.assertEquals(webExists(lwebElement), true, "Object Not Found UI. Hence stoping the execution of test case");
		}

	}


	/**
	 * This method is used to verify inner text of an element
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  objName This is the logical name of the web object in property file (OR)
	 * @param  expectedInnerText This is the inner text to be verified or the column name in the test data sheet
	 * @return returns true if the inner text matches or else returns false
	 */
	public static boolean webVerifyInnerText(String objName, String expectedInnerText){
		boolean lResultFlag = false;
		try {			
			WebElement lwebElement = getWebElement(objName);
			expectedInnerText = ExcelUtil.getDataFromExcel(onlyTestCaseName,expectedInnerText);
			String actualInnerText = lwebElement.getText();
			System.out.println("expectedInnerText: "+expectedInnerText);
			System.out.println("actualInnerText: "+actualInnerText);
			if(actualInnerText.equals(expectedInnerText)){
				
				ReportUtil.reporterEvent("pass", "For [ " + objName + " ] actual text [ " + actualInnerText + "] same as expected [ " + expectedInnerText + " ]" + captureScreenshotAsBase64());
				lResultFlag = true;
			} else {
				
				ReportUtil.reporterEvent("fail", "For [ " + objName + " ] actual text [" + actualInnerText + "] is not same as expected [" + expectedInnerText + "]" + captureScreenshotAsBase64());
				lResultFlag = false;
				//Assert.assertEquals(actualInnerText, expectedInnerText);
			}
		} catch (Exception e) {
			ReportUtil.reporterEvent("fail", "Web Element [ " + objName + " ] NOT FOUND on UI" + captureScreenshotAsBase64());
		}
		return lResultFlag;
	}
	
	
	/**
	 * This method is used to verify page title
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param expectedPageTitle This is the expected page title
	 * @param continueExecution <b>true</b> if you want to continue the execution or else <b>false</b> to stop the execution 
	 */
	public static void webVerifyPageTitle(String expectedPageTitle, Boolean continueExecution){
		try{
			expectedPageTitle = ExcelUtil.getDataFromExcel(onlyTestCaseName,expectedPageTitle);
			String actualTitle = wd.getTitle();
			if(expectedPageTitle.equals(actualTitle)){
				ReportUtil.reporterEvent("pass", " Actual page title [ " + actualTitle + " ] same as expected title [ " + expectedPageTitle + " ]" + captureScreenshotAsBase64());
			} else {
				ReportUtil.reporterEvent("fail", "Actual page title [ " + actualTitle + " ] not same as expected title [ " + expectedPageTitle + " ]" + captureScreenshotAsBase64());
				if(continueExecution.equals(false)){
					Assert.assertEquals(actualTitle, expectedPageTitle);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}


	/**
	 * This method is used to enter secured value in the text box
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  objName This is the logical name of the web object in property file (OR)
	 * @param  strEncodedTextToSend This is the encrypted text to be entered
	 */
	public static void webSecureSet(String objName, String strEncodedTextToSend){
		WebElement lwebElement = null;
		try {
			lwebElement = getWebElement(objName);
			strEncodedTextToSend = ExcelUtil.getDataFromExcel(onlyTestCaseName,strEncodedTextToSend);
			String decodedValue = EncryptUtil.decryption(strEncodedTextToSend);
			lwebElement.sendKeys(decodedValue);
			ReportUtil.reporterEvent("info", "Secure Text [ " + strEncodedTextToSend + " ] entered in the web element [ " + objName + " ]" );
		} catch (Exception e) {
			ReportUtil.reporterEvent("fail", "Web Element [ " + objName + " ] NOT FOUND on UI" + captureScreenshotAsBase64());
			Assert.assertEquals(webExists(lwebElement), true, "Object Not Found UI. Hence stoping the execution of test case");
		}
	}
	
	/**
	 * This method is used to mouse hover on web element
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  elementName This is the logical name of the web object in property file (OR)
	 */
	public static void webMouseHover(String elementName){
		WebElement lwebElement = null;
		try {
			lwebElement = getWebElement(elementName);
			Actions action = new Actions(wd);
			action.moveToElement(lwebElement).perform();
			ReportUtil.reporterEvent("info", "Mouse hover done on element [ " + elementName + " ]");
		} catch (Exception e) {
			ReportUtil.reporterEvent("fail", "Web Element [ " + elementName + " ] NOT FOUND on UI" + captureScreenshotAsBase64());
			Assert.assertEquals(webExists(lwebElement), true, "Object Not Found UI. Hence stoping the execution of test case");
		}
	}
	
	
	/**
	 * This method is used to drag and drop an element
	 * 
	 * 
	 * @param sorceObjectName This is the source element name
	 * @param targetObjectName This is the target element name
	 * @author Nagaraj Jayagondra 
	 */
	public static void webDragAndDrop(String sorceObjectName, String targetObjectName){
		WebElement lwebElement1 = null;
		WebElement lwebElement2 = null;
		try {
			lwebElement1 = getWebElement(sorceObjectName);
			lwebElement2 = getWebElement(targetObjectName);
			Actions action = new Actions(wd);
			action.dragAndDrop(lwebElement1, lwebElement2).perform();
			ReportUtil.reporterEvent("info", "Element [ " + sorceObjectName + " ] dragged and dropped in the web element [ " + targetObjectName + " ]" );
		} catch (Exception e) {
			ReportUtil.reporterEvent("fail", "Web Element [ " + sorceObjectName + " ] and [ " + targetObjectName + " ] NOT FOUND on UI" + captureScreenshotAsBase64());
			Assert.assertEquals(webExists(lwebElement1), true, "Object Not Found UI. Hence stoping the execution of test case");
			Assert.assertEquals(webExists(lwebElement2), true, "Object Not Found UI. Hence stoping the execution of test case");
		}
	}

	/**
	 * This method is used to close the browser
	 * 
	 * 
	 * @author Nagaraj Jayagondra 
	 */
	public static void closeBrowser(){
		wd.close();
		ReportUtil.reporterEvent("info", "Closed the browser window");
	}
	
	
	/**
	 * This method is used to switch to a particular frame
	 * 
	 * @param frameNameOrID frame ID
	 * @author Nagaraj Jayagondra 
	 */
	public static void switchToFrameWithID(String frameID){
		try{
			wd.switchTo().frame(frameID);
			ReportUtil.reporterEvent("pass", "Switched to Frame with ID [ " + frameID + " ]");
		}catch(Exception e){
			ReportUtil.reporterEvent("fail", "Frame with ID [ " + frameID + " ] NOT FOUND" + captureScreenshotAsBase64());
		}
	}
	
	/**
	 * This method is used to switch to a particular window
	 * 
	 * @param windowNameOrHandle This is a window name or window handle
	 * @author Nagaraj Jayagondra
	 */
	public static void switchToWindow(String windowNameOrHandle){
		try{
			wd.switchTo().window(windowNameOrHandle);
			ReportUtil.reporterEvent("pass", "Switched to Window with Name/Handle [ " + windowNameOrHandle + " ]" + captureScreenshotAsBase64());
		}catch(Exception e){
			ReportUtil.reporterEvent("fail", "Unable to switch to Window with Name/Handle [ " + windowNameOrHandle + " ]" + captureScreenshotAsBase64());
		}
	}
	
	
	/**
	 * This method is used to get all the window handles
	 * 
	 * @return returns an array of window handles
	 * @author Nagaraj Jayagondra
	 */
	public static Set<String> getAllWindowHandles(){
		Set<String> winhandles = null;
		try{
			winhandles = wd.getWindowHandles();
		}catch(Exception e){
			ReportUtil.reporterEvent("fail", "Unable to retrive window handles");
		}
		return winhandles;
	}
	
	
	/**
	 * This method is used to get current window handle
	 * 
	 * @return returns current window handle
	 * @author Nagaraj Jayagondra
	 */
	public static String getCurrentWindowHandle(){
		String winhandle = null;
		try{
			winhandle = wd.getWindowHandle();
		}catch(Exception e){
			ReportUtil.reporterEvent("fail", "Unable to retrive current window handle");
		}
		return winhandle;
	}
	

	/**
	 * This method is used to capture screen shot as BASE 64 
	 * 
	 * 
	 * @author Nagaraj Jayagondra 
	 */
	public static String captureScreenshotAsBase64(){
		String screenshotBase64 = ((TakesScreenshot)wd).getScreenshotAs(OutputType.BASE64);
		screenshotBase64 = "data:image/gif;base64," + screenshotBase64;
		String imageInBase64 = logger.addScreenCapture(screenshotBase64);
		return imageInBase64;
	}


	/**
	 * This method is used to get run time value from the AUT
	 * 
	 * @param objectLogicalName Logical name of the object in OR
	 * @param attributeName Name of the attribute to be captured
	 * @return returns the captured value
	 * @author Nagaraj Jayagondra 
	 */
	public static String webGetAttribute(String objectLogicalName, String attributeName){
		String attributeValue = null;
		try{
			WebElement lwebElement = getWebElement(objectLogicalName);
			attributeValue = lwebElement.getAttribute(attributeName);
			ReportUtil.reporterEvent("info", 
					"Value [ " + attributeValue + " ] retrived from web element [ " + objectLogicalName + " ]" );
		}catch(Exception e){
			ReportUtil.reporterEvent("fail", "Web Element [ " + objectLogicalName + " ] NOT FOUND on UI" + captureScreenshotAsBase64());
		}
		return attributeValue;
	}

	/**
	 * This method is used to explicitly wait for an element to be clickable
	 * 
	 * @param logicalNameOfObject Logical name of the object in OR
	 * @param timeInSeconds Time to wait
	 * @author Nagaraj Jayagondra 
	 */
	public static void webExplicitWait(String logicalNameOfObject, int timeInSeconds){
		WebElement lWebElement = null;
        lWebElement = getWebElement(logicalNameOfObject);
		(new WebDriverWait(wd, timeInSeconds)).until(ExpectedConditions.elementToBeClickable(lWebElement));
	}
	
	

	/**
	 * This method is used to check if the web element is enabled or not
	 * 
	 * @param objectLogicalName Logical name of the web element in OR
	 * @param expectedValue This is a boolean value
	 * @author Nagaraj Jayagondra 
	 */
	public static void webVerifyIsElementEnabled(String objectLogicalName, Boolean expectedValue){
		WebElement lWebElement = null;
		Boolean actualValue = null;
		try{
			lWebElement = getWebElement(objectLogicalName);
			actualValue = lWebElement.isEnabled();
			if(actualValue.equals(expectedValue)){
				ReportUtil.reporterEvent("pass", "Web element [ " + objectLogicalName + " ] is as expected" + captureScreenshotAsBase64());
			}else{
					ReportUtil.reporterEvent("fail", "Web element [ " + objectLogicalName + " ] is not same as expected" + captureScreenshotAsBase64());
				}		
		}catch(Exception e){
			ReportUtil.reporterEvent("fail", "Web Element [ " + objectLogicalName + " ] NOT FOUND on UI" + captureScreenshotAsBase64());
		}
	}


	/**
	 * This method is used for implicit wait
	 * 
	 * @param pDriver WebDriver instance
	 * @param timeInSeconds Time to wait
	 * @author Nagaraj Jayagondra 
	 */
	public static void webImplicitWait(WebDriver pDriver, int timeInSeconds){
		wd.manage().timeouts().implicitlyWait(timeInSeconds, TimeUnit.SECONDS);	
	}
	
	
	/**
	 * This method is used abort execution of a test case
	 * 
	 * @param reasonToExit This is the reason to abort the test execution
	 * @author Nagaraj Jayagondra 
	 */
	public static void endTestCaseExecution(String reasonToExit){
		ReportUtil.reporterEvent("fatal", "Ending test case execution [" + reasonToExit + " ]");
		Assert.assertEquals(true, false);
	}

	/**
	 * This method is used for debugging purpose
	 * 
	 * 
	 * @author Nagaraj Jayagondra 
	 */
	public static void msgbox(String s){
		JOptionPane.showMessageDialog(null, s);
	}
	
	
	/**
	 * This method is used highlight an element on GUI
	 * 
	 * @param objLogicalName This is the logical name of the object in OR
	 * @author Nagaraj Jayagondra 
	 */
	public static void highLightElement(String objLogicalName){
		
		WebElement lWebElement;
		lWebElement = getWebElement(objLogicalName);

		javaScriptDriver.executeScript("arguments[0].setAttribute('style','border: solid 2px red')", lWebElement); 

		try 
		{
			Thread.sleep(1000);
		} 
		catch (InterruptedException e) {

			System.out.println(e.getMessage());
		} 
		javaScriptDriver.executeScript("arguments[0].setAttribute('style','border: solid 2px white')", lWebElement); 

	}
	
	
	/**
	 * This method is used execute java script
	 * 
	 * @param script This is the script to be executed
	 * @return Returns an object
	 * @author Nagaraj Jayagondra 
	 */
	public static Object executeJavaScript(String script){
		return javaScriptDriver.executeScript(script);
	}
	
	
	/**
	 * This method is used wait till the page is completely loaded
	 * 
	 * @author Nagaraj Jayagondra 
	 */
	public static void waitTillPageIsLoadedFully(){
		try {
			String loading = "return document.readyState";
			Object state = executeJavaScript(loading);
			while (state.toString() != "complete") {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to get the properties from OR property file
	 * 
	 * @param objectLogicalName This is the logical name of the object in OR
	 * @return Returns command as a string
	 * @author Nagaraj Jayagondra 
	 */
	public static String getJavaScriptPropertyAndCommand(String objectLogicalName){
		String script = null;
		try {
			String ORValue = ORUtil.getValue(objectLogicalName);
			String propType = null;
			String propActVal = null;
			if (ORValue.equals("ELEMENTNOTFOUND")) {
				ReportUtil.reporterEvent("fail","Object with logical name [ " + objectLogicalName + " ] not found in Object Repository. Please check...");
				Assert.assertEquals(ORValue.equals("ELEMENTNOTFOUND"), false);
			}
			else {
				String[] parts = ORValue.split(":=");
				propType = parts[0];
				propActVal = parts[1];

				if (propType.equalsIgnoreCase("id")) {
					script = "document.getElementById('"+ propActVal + "')";
				} 
				else {
					ReportUtil.reporterEvent("fail","Invalid property type defined in Object Repository for web element [ " + objectLogicalName + " ]");
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return script;
	}
		
	
	/**
	 * This method is used to click on a web element using java script
	 * 
	 * @param objectLogicalName This is the logical name of the object in OR
	 * @author Nagaraj Jayagondra 
	 */
	public static void clickUsingJS(String objectLogicalName){
		String script = null;
		try {
			script = getJavaScriptPropertyAndCommand(objectLogicalName);
			script = script + ".click();";	
			executeJavaScript(script);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method is used to scroll the web page till the given web element
	 * 
	 * @param objectLogicalName This is the logical name of the object in OR
	 * @author Nagaraj Jayagondra 
	 */
	public static void scrollIntoWebElement(String objectLogicalName){
		String script = null;
		try {
			script = getJavaScriptPropertyAndCommand(objectLogicalName);
			script = script + ".scrollIntoView(true);";
			executeJavaScript(script);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void scrollIntoWebElement_new(String objectLogicalName){
		javaScriptDriver.executeScript("arguments[0].scrollIntoView(true);",CommonWebActions.getWebElement(objectLogicalName));
	}
	
	public static WebElement helpNSupportGetWebElement(String objLogicalName){

		WebElement webEle = null;
		try {
			String ORValue = ORUtil.getHelpNSupportValue(objLogicalName);
			String propType = null;
			String propActVal = null;
			if (ORValue.equals("ELEMENTNOTFOUND")) {
				ReportUtil.reporterEvent("fail","Object with logical name [ " + objLogicalName + " ] not found in Object Repository. Please check...");
				Assert.assertEquals(ORValue.equals("ELEMENTNOTFOUND"), false);
			}
			else {
				String[] parts = ORValue.split(":=");
				propType = parts[0];
				propActVal = parts[1];

				if (propType.equalsIgnoreCase("id")) {
					webEle = wd.findElement(By.id(propActVal));
				} else if (propType.equalsIgnoreCase("name")) {
					webEle = wd.findElement(By.name(propActVal));
				} else if (propType.equalsIgnoreCase("xpath")) {
					webEle = wd.findElement(By.xpath(propActVal));
				} else if (propType.equalsIgnoreCase("linktext")) {
					webEle = wd.findElement(By.linkText(propActVal));
				} else if (propType.equalsIgnoreCase("cssselector")) {
					webEle = wd.findElement(By.cssSelector(propActVal));
				} else if (propType.equalsIgnoreCase("partiallinktext")) {
					webEle = wd.findElement(By.partialLinkText(propActVal));
				} else if (propType.equalsIgnoreCase("classname")) {
					webEle = wd.findElement(By.className(propActVal));
				} else {
					ReportUtil.reporterEvent("fail","Invalid property type defined in Object Repository for web element [ " + objLogicalName + " ]");
				}
			}

		} catch (Exception e) {
			//			ReportUtil.reporterEvent("fatal", "Unable to find web element Error [ " + e.getMessage() + " ]");
			e.printStackTrace();
		}
		return webEle;
	}
/**
 * This function will be use to get the text from the webelement
 * @param logicalName its a string represent of object name from properties file
 * @return it will return the value of webelement as String format
 * @author Kannan Venkadasamy
 */
	public static String getWebElementText(String logicalName)
	{
		if (webExists(getWebElement(logicalName)))
		{
			 return getWebElement(logicalName).getText();
		}
		else
			return null;
	}
	/** This function will use to compare the string
	 * return boolean value will return
	 * @author Kannan Venkadasamy
	 */
	public static boolean stringMatch(String str1, String str2){
		
		if(str1.equals(str2))
			return true;
		else 
			return false;
	}
	/** This function will return list of elements
	 * 
	 * @param element List of elements
	 * @return this will return list
	 * @author Kannan Venkadasamy
	 */
	public static List<String> addItemsToList(List<WebElement> element){
		List<String> listItems = new ArrayList<String>();
		
		for(WebElement item : element){
			
			listItems.add(item.getText().toString());
		}
		return listItems;
	}
	/**
	 * This will return the list of strings
	 * @param str str is string with delimiter
	 * @return return list
	 */
	public static List<String> split(String str){
		String[] strs = str.split(":=");
		
		List<String> listStrs = new ArrayList<String>();
		for(int i=0;i<strs.length;i++){
			listStrs.add(strs[i]);
		}
		return listStrs;
	}
		
	public static int zustTry()
	{
		List<WebElement> forms = wd.findElements(By.xpath("//div[@class='col-md-12 announcements-feed']/ul/li"));
		int count = forms.size();
		return count;
	}
	
	

}
