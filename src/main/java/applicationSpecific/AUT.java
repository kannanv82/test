package applicationSpecific;

import coreFramework.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import coreFramework.CommonWebActions;
import coreFramework.ExcelUtil;
import coreFramework.ReportUtil;
import coreFramework.CoreListener;
import edu.emory.mathcs.backport.java.util.Collections;

import org.openqa.selenium.JavascriptExecutor;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
/**
 * This class consists of methods specific to application under test
 * 
 * @author  Nagaraj Jayagondra
 * @version 1.0
 * @since   10-Dec-2015 
 */
public class AUT {
	

	/**
	 * This method is used to Login to the NetStar5 application
	 * @param strEnvURL - Environment name present in config file
	 * @param strUserName - User name present in data sheet
	 * @param strPassword - encoded password present in data sheet
	 */
	public static void login(String strEnvURL, String strUserName, String strPassword){
		try{
			CommonWebActions.launch(strEnvURL);
			CommonWebActions.webSet("LoginId", strUserName);
			CommonWebActions.webSecureSet("LoginPassword", strPassword);
//			CommonWebActions.webClick("chkRemember");
			CommonWebActions.webClick("LoginButton");
			CommonWebActions.webExplicitWait("CommunicationLink", 180);
			CommonWebActions.webVerifyPageTitle("PageTitle",true);
//			CommonWebActions.endTestCaseExecution("Title did not match");
			CommonWebActions.webExplicitWait("DealearshipName", 180);
			if (CommonWebActions.webVerifyInnerText("DealearshipName", "DealershipName"))
			{
				if (CommonWebActions.webVerifyInnerText("UserName", "UserName2"))
				{
					ReportUtil.reporterEvent("pass", "Login Successful - Dealership name displayed");
				}
				else 
					ReportUtil.reporterEvent("fail", "Login issue user name didnt matched");	
				}
			else
			{
				CommonWebActions.webVerifyInnerText("UserName", "UserName2");
				
				ReportUtil.reporterEvent("fail", "Unable to login successfully - Dealership not matched");
			}
			
			//Commented the validation of Dashboard due to defect #317 on 29th Feb. Should be uncommented after 317 gets fixed.
			/*CommonWebActions.webExplicitWait("DashboardHomePage", 180);
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("DashboardHomePage")))
			{
				ReportUtil.reporterEvent("pass", "Home Page Displayed with Dashboard");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Dashboard not displayed after login");
			}*/
			
			
		}
		catch(Exception e){
			e.printStackTrace();
			ReportUtil.reporterEvent("fatal", "Login failure" + CommonWebActions.captureScreenshotAsBase64());
		}

	}

	/**
	 * This method is used to login on already open browser in previuos steps
	 * @param strEnvURL - Environment name present in config file
	 * @param strUserName - user name present in data sheet
	 * @param strPassword - encoded password present in data sheet
	 * @author Preetam Gupta
	 */
	public static void loginOnExistingBrowser(String strEnvURL, String strUserName, String strPassword){
		try{
			CommonWebActions.reLaunchURL(strEnvURL);
			CommonWebActions.webSet("LoginId", strUserName);
			CommonWebActions.webSecureSet("LoginPassword", strPassword);
//			CommonWebActions.webClick("chkRemember");
			CommonWebActions.webClick("LoginButton");
			CommonWebActions.webExplicitWait("CommunicationLink", 180);
			CommonWebActions.webVerifyPageTitle("PageTitle",true);
//			CommonWebActions.endTestCaseExecution("Title did not match");
			CommonWebActions.webExplicitWait("DealearshipName", 180);
			if (CommonWebActions.webVerifyInnerText("DealearshipName", "DealershipName"))
			{
				if (CommonWebActions.webVerifyInnerText("UserName", "UserName2"))
				{
					ReportUtil.reporterEvent("pass", "Login Successful on existing browser");
				}
				else 
					ReportUtil.reporterEvent("fail", "Unable to login successfully on existing browser");	
				}
			else
			{
				CommonWebActions.webVerifyInnerText("UserName", "UserName2");
				
				ReportUtil.reporterEvent("fail", "Unable to login successfully on existing browser");
			}
			
			//Commented the validation of Dashboard due to defect #317 on 29th Feb. Should be uncommented after 317 gets fixed.
			/*CommonWebActions.webExplicitWait("DashboardHomePage", 180);
						
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("DashboardHomePage")))
			{
				ReportUtil.reporterEvent("pass", "Home Page Displayed with Dashboard after relogin");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Dashboard not displayed after relogin");
			}
			*/
			
		}catch(Exception e){
			e.printStackTrace();
			ReportUtil.reporterEvent("fatal", "Login failure" + CommonWebActions.captureScreenshotAsBase64());
		}

	}

	/**
	 * This method is used to logoff from the NetStar5 application
	 * @author Preetam Gupta
	 */
	public static void logOff(){
		try{
			CommonWebActions.webExplicitWait("UserName", 60);
			CommonWebActions.webClick("UserName");
			CommonWebActions.webClick("LogoutButton");
			CommonWebActions.webExplicitWait("LogoutMessage", 180);
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("LogoutMessage"))){
				ReportUtil.reporterEvent("pass", "Logoff Successful, [You have been Logged out] messgae displayed" + CommonWebActions.captureScreenshotAsBase64());
//				ReportUtil.endReporter();
			}
			else {
				ReportUtil.reporterEvent("fail", "Unable to logoff successfully" + CommonWebActions.captureScreenshotAsBase64());
//				ReportUtil.endReporter();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			ReportUtil.reporterEvent("fail", "Logoff failure" + CommonWebActions.captureScreenshotAsBase64());
		}
	}
	
	public static void closeBrowserWindow(){
		CommonWebActions.closeBrowser();
	}
	
	/**
	 * This method is used to validate links present at the bottom part of left menu
	 * @author Preetam Gupta
	 */
	public static void validateLeftBottomLinks()

	{
		try
		{
			//Validation for Help and Support menu
			
			CommonWebActions.webExplicitWait("HelpNSupportLink", 180);
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("HelpNSupportLink")))
			{
				ReportUtil.reporterEvent("pass", "Help and Support link is present in left menu"     + CommonWebActions.captureScreenshotAsBase64());
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Help and Support link is not present in left menu" + CommonWebActions.captureScreenshotAsBase64());
			}
		
			//Validation for feedback menu link
			CommonWebActions.webExplicitWait("FeedbackLink", 180);
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("FeedbackLink")))
			{
				ReportUtil.reporterEvent("pass", "Feedback menu link is present in left menu"     + CommonWebActions.captureScreenshotAsBase64());
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Feedback menu link is not present in left menu" + CommonWebActions.captureScreenshotAsBase64());
			}
			
			//Validation for Toggle menu link
			CommonWebActions.webExplicitWait("ToggleMenuLink", 180);
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("ToggleMenuLink")))
			{
				ReportUtil.reporterEvent("pass", "Toggle menu link is present in left menu"     + CommonWebActions.captureScreenshotAsBase64());
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Toggle menu link is not present in left menu" + CommonWebActions.captureScreenshotAsBase64());
			}
			
		}
			
		 catch(Exception e)
		{
			e.printStackTrace();
			ReportUtil.reporterEvent("fatal", "Login failure" + CommonWebActions.captureScreenshotAsBase64());
		}
	}
	
	/**
	 * This method is used to shrink the left menu by clicking on the toggle icon
	 */
	public static void toggleToShrink()
	{
		try
		{
			CommonWebActions.webExplicitWait("ToggleIconLeftArrow", 180);
			CommonWebActions.webClick("ToggleIconLeftArrow");
			CommonWebActions.webExplicitWait("ToggleIconRightArrow", 60);
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("ToggleIconRightArrow")))
			{
				ReportUtil.reporterEvent("info", "Left menu shrinked after toggle");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Left menu not shrinked after toggle" + CommonWebActions.captureScreenshotAsBase64());
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			ReportUtil.reporterEvent("fatal", "Toggle Shrink Failure" + CommonWebActions.captureScreenshotAsBase64());
		}
	}

	/**
	 * This method is used to expand the left menu by clickin on toggle icon when left menu is shrinked
	 */
	public static void toggleToExpand()
	{
		try
		{
			CommonWebActions.webExplicitWait("ToggleIconRightArrow", 180);
			CommonWebActions.webClick("ToggleIconRightArrow");
			CommonWebActions.webExplicitWait("ToggleIconLeftArrow", 60);
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("ToggleIconLeftArrow")))
			{
				ReportUtil.reporterEvent("pass", "Left menu expanded after toggling back");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Left menu not expanded after toggling back" + CommonWebActions.captureScreenshotAsBase64());
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			ReportUtil.reporterEvent("fatal", "Toggle Expand Failure" + CommonWebActions.captureScreenshotAsBase64());
		}
	} //End of toggleToExpand method ********************************************
	
	public static void validateToggleShrink()
	{
		try
		{
			toggleToShrink();
			//validate Home icon
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("HomeIcon")))
			{
				ReportUtil.reporterEvent("pass", "Home icon displayed after toggle shrink");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Home icon not displayed after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}
			//validate Home link
			if (!CommonWebActions.webExists(CommonWebActions.getWebElement("HomeLink")))
			{
				ReportUtil.reporterEvent("pass", "Home link not displayed after toggle shrink as expected");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Home link displayed even after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}
			
			//validate communication icon
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("CommunicationIcon")))
			{
				ReportUtil.reporterEvent("pass", "Communication icon displayed after toggle shrink");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Communication icon not displayed after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}
			//validate Communication link
			if (!CommonWebActions.webExists(CommonWebActions.getWebElement("CommunicationLink")))
			{
				ReportUtil.reporterEvent("pass", "Communication link not displayed after toggle shrink as expected");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Communication link displayed even after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}
			//validate Directory icon
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("DirectoryIcon")))
			{
				ReportUtil.reporterEvent("pass", "Directory icon displayed after toggle shrink");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Directory icon not displayed after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}
			//validate Directory link
			if (!CommonWebActions.webExists(CommonWebActions.getWebElement("DirectoryLink")))
			{
				ReportUtil.reporterEvent("pass", "Directory link not displayed after toggle shrink as expected");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Directory link displayed even after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}
			//validate Report icon
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("ReportIcon")))
			{
				ReportUtil.reporterEvent("pass", "Report icon displayed after toggle shrink");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Report icon not displayed after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}
			//validate Report link
			/*if (!CommonWebActions.webExists(CommonWebActions.getWebElement("ReportLink")))
			{
				ReportUtil.reporterEvent("pass", "Report link not displayed after toggle shrink as expected");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Report link displayed even after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}*/
			//validate Operational System icon
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("OperationalSystemIcon")))
			{
				ReportUtil.reporterEvent("pass", "OperationalSystem icon displayed after toggle shrink");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "OperationalSystem icon not displayed after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}
			//validate Operational System link
			/*if (!CommonWebActions.webExists(CommonWebActions.getWebElement("OperationalSystemLink")))
			{
				ReportUtil.reporterEvent("pass", "OperationalSystem link not displayed after toggle shrink as expected");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "OperationalSystem link displayed even after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}*/
			//validate Administration icon
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("AdministrationIcon")))
			{
				ReportUtil.reporterEvent("pass", "Administration icon displayed after toggle shrink");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Administration icon not displayed after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}
			//validate Administration link
			/*if (!CommonWebActions.webExists(CommonWebActions.getWebElement("AdministrationLink")))
			{
				ReportUtil.reporterEvent("pass", "Administration link not displayed after toggle shrink as expected");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Administration link displayed even after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}*/
			//validate Help And Support icon
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("HelpNSupportIcon")))
			{
				ReportUtil.reporterEvent("pass", "HelpNSupport icon displayed after toggle shrink");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "HelpNSupport icon not displayed after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}
			//validate Help and Support link
			if (!CommonWebActions.webExists(CommonWebActions.getWebElement("HelpNSupportLink")))
			{
				ReportUtil.reporterEvent("pass", "HelpNSupport link not displayed after toggle shrink as expected");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "HelpNSupport link displayed even after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}
			
			//validate Feedback icon
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("FeedbackIcon")))
			{
				ReportUtil.reporterEvent("pass", "Feedback icon displayed after toggle shrink");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Feedback icon not displayed after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}
			//validate Feedback link
			if (!CommonWebActions.webExists(CommonWebActions.getWebElement("FeedbackLink")))
			{
				ReportUtil.reporterEvent("pass", "Feedback link not displayed after toggle shrink as expected");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Feedback link displayed even after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}
			
			//validate Toggle to Expand icon
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("ToggleIconRightArrow")))
			{
				ReportUtil.reporterEvent("pass", "Toggle icon displayed after toggle shrink");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Toggle icon not displayed after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}
			//validate Toggle link
			if (!CommonWebActions.webExists(CommonWebActions.getWebElement("ToggleMenuLink")))
			{
				ReportUtil.reporterEvent("pass", "ToggleMenu link not displayed after toggle shrink as expected");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "ToggleMenu link displayed even after toggle shrink" + CommonWebActions.captureScreenshotAsBase64());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			ReportUtil.reporterEvent("fatal", "Toggle Shrink Failure" + CommonWebActions.captureScreenshotAsBase64());
		}
	}

	/**
	 * This method will click on the left menu icon and validate the main page header
	 * @param logicalNameMenuIcon - Logical name of the menu icon to be clicked
	 * @param logicalNameHeaderItem - Logical name of the header on the main page to be validated
	 * @author Preetam Gupta
	 */
	public static void clickLeftMenu(String logicalNameMenuIcon, String logicalNameHeaderItem)
	{
		try
		{
			CommonWebActions.webExplicitWait(logicalNameMenuIcon, 180);
			CommonWebActions.webClick(logicalNameMenuIcon);
			CommonWebActions.webExplicitWait(logicalNameHeaderItem, 180);
			if (CommonWebActions.webExists(CommonWebActions.getWebElement(logicalNameHeaderItem)))
			{
				ReportUtil.reporterEvent("pass",logicalNameHeaderItem + " displayed after clicking " + logicalNameMenuIcon );
			}
			else
			{
				ReportUtil.reporterEvent("fail",logicalNameHeaderItem + " not displayed after clicking " + logicalNameMenuIcon );
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//ReportUtil.reporterEvent("fatal", "Menu click failure on " + logicalNameMenuIcon + CommonWebActions.captureScreenshotAsBase64());
		}
	}//end of clickLeftMenu method
	
	public static void clickLeftMenu2Level(String logicalNameMenuIcon, String logicalNameMenuLink, String logicalNameHeaderItem)
	{
		try
		{
			CommonWebActions.webExplicitWait(logicalNameMenuIcon, 180);
			CommonWebActions.webClick(logicalNameMenuIcon);
			CommonWebActions.webExplicitWait(logicalNameMenuLink, 180);
			CommonWebActions.webClick(logicalNameMenuLink);
			CommonWebActions.webExplicitWait(logicalNameHeaderItem, 180);
			if (CommonWebActions.webExists(CommonWebActions.getWebElement(logicalNameHeaderItem)))
			{
				ReportUtil.reporterEvent("pass",logicalNameHeaderItem + " displayed after clicking " + logicalNameMenuLink );
			}
			else
			{
				ReportUtil.reporterEvent("fail",logicalNameHeaderItem + " not displayed after clicking " + logicalNameMenuLink );
			}
//			CommonWebActions.webClick(logicalNameMenuIcon);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//ReportUtil.reporterEvent("fatal", "Menu click failure on " + logicalNameMenuIcon + CommonWebActions.captureScreenshotAsBase64());
		}
	}//end of clickLeftMenu2Level method
	
	/**
	 * This method will click the left menu icons and validate the main page content with header text
	 * @author Preetam Gupta
	 */
	public static void validateMenuIconClick()
	{
		try
		{
			clickLeftMenu2Level("CommunicationIcon", "NewsLink", "NewsHeader");
			Thread.sleep(5000);
//			clickLeftMenu2Level("CommunicationIcon", "AnnouncementsLink", "AnnouncementHeader");
//			Thread.sleep(5000);
//			clickLeftMenu2Level("CommunicationIcon", "NotificationsLink", "NotificationsHeader");
//			clickLeftMenu("HomeIcon", "DashboardHomePage");
			Thread.sleep(5000);
			clickLeftMenu("ReportIcon", "ReportsHeader");
			Thread.sleep(5000);
			clickLeftMenu2Level("DirectoryIcon", "USDealersLink", "DirectoryHeader");
			Thread.sleep(5000);
//			clickLeftMenu2Level("DirectoryIcon", "PeopleLink", "PeopleHeader");
//			Thread.sleep(5000);
		
			clickLeftMenu("AdministrationIcon", "AdministrationHeader");
			Thread.sleep(5000);
			clickLeftMenu("HelpNSupportIcon", "HelpNSupportHeader");
			Thread.sleep(5000);
//			clickLeftMenu("FeedbackIcon", "FeedbackHeader");
			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			ReportUtil.reporterEvent("fatal", "Menu Icon click failure" + CommonWebActions.captureScreenshotAsBase64());
		}
	}
	
	public static void validateMenuLinkClick()
	{
		try
		{
			clickLeftMenu2Level("CommunicationLink", "NewsLink", "NewsHeader");
			Thread.sleep(5000);
//			clickLeftMenu2Level("CommunicationLink", "AnnouncementsLink", "AnnouncementHeader");
//			Thread.sleep(5000);
//			clickLeftMenu2Level("CommunicationLink", "NotificationsLink", "NotificationsHeader");
			clickLeftMenu("ReportLink", "ReportsHeader");
			
			Thread.sleep(5000);
			clickLeftMenu2Level("DirectoryLink", "USDealersLink", "DirectoryHeader");
			Thread.sleep(5000);
//			clickLeftMenu2Level("DirectoryLink", "PeopleLink", "PeopleHeader");
//			Thread.sleep(5000);
		
			clickLeftMenu("NetStarLogo", "DashboardHomePage");
			Thread.sleep(5000);
			clickLeftMenu("AdministrationLink", "AdministrationHeader");
			Thread.sleep(5000);
			clickLeftMenu("HelpNSupportLink", "HelpNSupportHeader");
			Thread.sleep(5000);
//			clickLeftMenu("FeedbackIcon", "FeedbackHeader");
			clickLeftMenu("HomeLink", "DashboardHomePage");
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			//ReportUtil.reporterEvent("fatal", "Menu Link click failure" + CommonWebActions.captureScreenshotAsBase64());
		}
	}
	
	public static void validateElement(String LogicalName)
	{
		CommonWebActions.webExplicitWait(LogicalName, 180);
		if (CommonWebActions.webExists(CommonWebActions.getWebElement(LogicalName)))
		{
			ReportUtil.reporterEvent("pass", LogicalName + " displayed");
		}
		else
		{
			ReportUtil.reporterEvent("fail", LogicalName + " not displayed");
		}
	}
	
	public static void validateFeedbackWindow() throws InterruptedException
	{
		CommonWebActions.webExplicitWait("FeedbackLink", 180);
		CommonWebActions.webClick("FeedbackLink");
		
		validateElement("FeedbackWindowTitle");
		validateElement("FeedbackTextArea");
		validateElement("FeedbackMessage");
		validateElement("FeedbackCrossButton");
		validateElement("FeedbackCancelButton");
		CommonWebActions.webSet("FeedbackTextArea", "FeedbackText");
		CommonWebActions.webClick("FeedbackCancelButton");
		
		CommonWebActions.webExplicitWait("FeedbackLink", 180);
		CommonWebActions.webClick("FeedbackLink");
		String feedbackText3000="a*";
		for (int i=2; i<3000; i++)
		{
			feedbackText3000=feedbackText3000+"1";
		}
		
		CommonWebActions.webSetText("FeedbackTextArea", feedbackText3000);
		validateElement("FeedbackSubmitButton");
		
		CommonWebActions.webClick("FeedbackSubmitButton");
		Thread.sleep(5000);
		
//		CommonWebActions.webExplicitWait("FeedbackLink", 180);
//		CommonWebActions.webClick("FeedbackLink");
//		CommonWebActions.webSet("FeedbackTextArea", "FeedbackText");
//		validateElement("FeedbackSubmitButton");
//		
//		CommonWebActions.webClick("FeedbackSubmitButton");
		
	}

	public static void validateUserProfile() throws Exception
	{
		CommonWebActions.webExplicitWait("UserName", 60);
		CommonWebActions.webClick("UserName");
		CommonWebActions.webExplicitWait("MyAccountButton", 60);
		CommonWebActions.webClick("MyAccountButton");
		
		validateElement("UserProfileBreadcrumb");
		validateElement("UserProfileMessage");
		validateElement("EditProfileButton");
		validateElement("UserProfilePicture");
		validateElement("ProfilePictureEditButton");
		validateElement("ContactInformationName");
		validateElement("ContactInformationDealership");
		validateElement("ContactInformationEmail");
		validateElement("ContactInformationWorkPhone");
		validateElement("ContactInformationMobile");
		validateElement("LabelAdditionalContactInformation");
		
		CommonWebActions.webClick("LinksManagementTab");
		CommonWebActions.webExplicitWait("LinksManagementBreadcrumb", 60);
		validateElement("LinksManagementBreadcrumb");
		
		CommonWebActions.webClick("NotificationSettingstab");
		CommonWebActions.webExplicitWait("NotificationSettingsBreadcrumb", 60);
		validateElement("NotificationSettingsBreadcrumb");
		
		CommonWebActions.webClick("UserName");
		CommonWebActions.webExplicitWait("MyAccountButton", 60);
		CommonWebActions.webClick("MyAccountButton");
		
		CommonWebActions.webExplicitWait("UserProfileBreadcrumb", 60);
		
		if(CommonWebActions.webVerifyInnerText("UserProfileMessageAddInfo", "UserProfileMessageAddInfo"))
			{
				ReportUtil.reporterEvent("pass", "User Profile displayed with message["+ CommonWebActions.getWebElementText("UserProfileMessage") + "]");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "User Profile is not displayed with message" + CommonWebActions.captureScreenshotAsBase64());
			}
		Thread.sleep(4000);
		CommonWebActions.webClick("UserProfileAddContact");
		Thread.sleep(4000);
		List<WebElement> ContactList = new ArrayList<WebElement>();
		ContactList = CommonWebActions.getWebElements("UserProfileAddContactList");
		List<String> ContactListValues = new ArrayList<String>();
		for(WebElement ele : ContactList){
			ContactListValues.add(ele.getText());
		}
		List<String> c = new ArrayList<String>();
		c.add("Email");
		c.add("Facebook");
		c.add("Linkedin");
		c.add("Mobile");
		c.add("Other #");
		c.add("Twitter");
		if(ContactListValues.containsAll(c))
			{
				ReportUtil.reporterEvent("pass", "Add Contact Info dropdown displayed with"+ c);
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Add Contact Info dropdown not displayed with values" + CommonWebActions.captureScreenshotAsBase64());
			}
		
		
		
		CommonWebActions.webClick("UserProfileAddContactListEmail");
		CommonWebActions.webExplicitWait("ContactFieldText", 180);
		CommonWebActions.webSet("ContactFieldText", "UserEmail");
		CommonWebActions.webExplicitWait("ContactFieldSave", 180);
		CommonWebActions.webClick("ContactFieldSave");
		CommonWebActions.webExplicitWait("ContactSaveMessage", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("ContactSaveMessage")))
		{
			ReportUtil.reporterEvent("pass", "Email added Successfully." + CommonWebActions.captureScreenshotAsBase64());
			ReportUtil.reporterEvent("pass", "Email added Successfully with upto 255 characters." + CommonWebActions.captureScreenshotAsBase64());
		}
		else
		{
			ReportUtil.reporterEvent("fail", "Email is not added Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		CommonWebActions.webClick("MessageClose");
		CommonWebActions.webClick("UserName");
		CommonWebActions.webExplicitWait("MyAccountButton", 60);
		CommonWebActions.webClick("MyAccountButton");
		CommonWebActions.webExplicitWait("ContactListItem", 180);
		CommonWebActions.webClick("ContactListItem");
		CommonWebActions.webExplicitWait("ContactListItemEdit", 180);
		CommonWebActions.webClick("ContactListItemEdit");
		CommonWebActions.webExplicitWait("ContactFieldTextEdit", 180);
		CommonWebActions.webClick("ContactFieldTextEdit");
		String str1 = CommonWebActions.getWebElement("ContactFieldTextEdit").getAttribute("value");
		String str2 = ExcelUtil.getDataFromExcel(ReportUtil.onlyTestCaseName,"UserEmail");
		System.out.println(str1+" "+str2);
		if(CommonWebActions.stringMatch(str1,str2))
		{
			ReportUtil.reporterEvent("pass", "Email Edit Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		else
		{
			ReportUtil.reporterEvent("fail", "Email Edit not Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		CommonWebActions.webClick("ContactfieldTextCancel");
		CommonWebActions.webClick("ContactListItem");
		CommonWebActions.webExplicitWait("ContactListItemDelete", 180);
		CommonWebActions.webClick("ContactListItemDelete");
		ReportUtil.reporterEvent("pass", "Email Deleted Successfully." + CommonWebActions.captureScreenshotAsBase64());
		
		CommonWebActions.webClick("UserName");
		CommonWebActions.webExplicitWait("MyAccountButton", 60);
		CommonWebActions.webClick("MyAccountButton");
		
		
		CommonWebActions.webExplicitWait("UserProfileAddContact", 180);
		Thread.sleep(4000);
		CommonWebActions.webClick("UserProfileAddContact");
		Thread.sleep(4000);
		CommonWebActions.webClick("UserProfileAddContactListFacebook");
		CommonWebActions.webExplicitWait("ContactFieldText", 180);
		CommonWebActions.webSet("ContactFieldText", "UserEmail");
		CommonWebActions.webExplicitWait("ContactFieldSave", 180);
		CommonWebActions.webClick("ContactFieldSave");
		CommonWebActions.webExplicitWait("ContactSaveMessage", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("ContactSaveMessage")))
		{
			ReportUtil.reporterEvent("pass", "Facebook added Successfully." + CommonWebActions.captureScreenshotAsBase64());
			ReportUtil.reporterEvent("pass", "Facebook added Successfully wit upto 255 characters." + CommonWebActions.captureScreenshotAsBase64());
		}
		else
		{
			ReportUtil.reporterEvent("fail", "Facebook is not added Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		CommonWebActions.webClick("MessageClose");
		CommonWebActions.webClick("UserName");
		CommonWebActions.webExplicitWait("MyAccountButton", 60);
		CommonWebActions.webClick("MyAccountButton");
		CommonWebActions.webExplicitWait("ContactListItem", 180);
		CommonWebActions.webClick("ContactListItem");
		CommonWebActions.webExplicitWait("ContactListItemEdit", 180);
		CommonWebActions.webClick("ContactListItemEdit");
		CommonWebActions.webExplicitWait("ContactFieldTextEdit", 180);
		CommonWebActions.webClick("ContactFieldTextEdit");
		if(CommonWebActions.stringMatch(CommonWebActions.getWebElement("ContactFieldTextEdit").getAttribute("value"),ExcelUtil.getDataFromExcel(ReportUtil.onlyTestCaseName,"UserEmail")))
		{
			ReportUtil.reporterEvent("pass", "FaceBook Edit Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		else
		{
			ReportUtil.reporterEvent("fail", "Email Edit not Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		CommonWebActions.webClick("ContactfieldTextCancel");
		CommonWebActions.webClick("ContactListItem");
		CommonWebActions.webExplicitWait("ContactListItemDelete", 180);
		CommonWebActions.webClick("ContactListItemDelete");
		ReportUtil.reporterEvent("pass", "Facebook Deleted Successfully." + CommonWebActions.captureScreenshotAsBase64());
		
		
		
		CommonWebActions.webClick("UserName");
		CommonWebActions.webExplicitWait("MyAccountButton", 60);
		CommonWebActions.webClick("MyAccountButton");
		CommonWebActions.webExplicitWait("UserProfileAddContact", 180);
		Thread.sleep(4000);
		CommonWebActions.webClick("UserProfileAddContact");
		Thread.sleep(4000);
		CommonWebActions.webClick("UserProfileAddContactListLinkedin");
		CommonWebActions.webExplicitWait("ContactFieldText", 180);
		CommonWebActions.webSet("ContactFieldText", "UserEmail");
		CommonWebActions.webExplicitWait("ContactFieldSave", 180);
		CommonWebActions.webClick("ContactFieldSave");
		CommonWebActions.webExplicitWait("ContactSaveMessage", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("ContactSaveMessage")))
		{
			ReportUtil.reporterEvent("pass", "Linkedin added Successfully." + CommonWebActions.captureScreenshotAsBase64());
			ReportUtil.reporterEvent("pass", "Linkedin added Successfully with upto 255 characters." + CommonWebActions.captureScreenshotAsBase64());
		}
		else
		{
			ReportUtil.reporterEvent("fail", "Linkedin is not added Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		CommonWebActions.webClick("MessageClose");
		CommonWebActions.webClick("UserName");
		CommonWebActions.webExplicitWait("MyAccountButton", 60);
		CommonWebActions.webClick("MyAccountButton");
		CommonWebActions.webExplicitWait("ContactListItem", 180);
		CommonWebActions.webClick("ContactListItem");
		CommonWebActions.webExplicitWait("ContactListItemEdit", 180);
		CommonWebActions.webClick("ContactListItemEdit");
		CommonWebActions.webExplicitWait("ContactFieldTextEdit", 180);
		CommonWebActions.webClick("ContactFieldTextEdit");
		if(CommonWebActions.stringMatch(CommonWebActions.getWebElement("ContactFieldTextEdit").getAttribute("value"),ExcelUtil.getDataFromExcel(ReportUtil.onlyTestCaseName,"UserEmail")))
		{
			ReportUtil.reporterEvent("pass", "Linkedin Edit Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		else
		{
			ReportUtil.reporterEvent("fail", "Linkedin Edit not Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		CommonWebActions.webClick("ContactfieldTextCancel");
		CommonWebActions.webClick("ContactListItem");
		CommonWebActions.webExplicitWait("ContactListItemDelete", 180);
		CommonWebActions.webClick("ContactListItemDelete");
		ReportUtil.reporterEvent("pass", "Linkedin Deleted Successfully." + CommonWebActions.captureScreenshotAsBase64());
		
		
		CommonWebActions.webClick("UserName");
		CommonWebActions.webExplicitWait("MyAccountButton", 60);
		CommonWebActions.webClick("MyAccountButton");
		CommonWebActions.webExplicitWait("UserProfileAddContact", 180);
		Thread.sleep(4000);
		CommonWebActions.webClick("UserProfileAddContact");
		Thread.sleep(4000);
		CommonWebActions.webClick("UserProfileAddContactListMobile");
		CommonWebActions.webExplicitWait("ContactFieldText", 180);
		CommonWebActions.webSet("ContactFieldText", "UserEmail");
		CommonWebActions.webExplicitWait("ContactFieldSave", 180);
		CommonWebActions.webClick("ContactFieldSave");
		CommonWebActions.webExplicitWait("ContactSaveMessage", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("ContactSaveMessage")))
		{
			ReportUtil.reporterEvent("pass", "Mobile added Successfully." + CommonWebActions.captureScreenshotAsBase64());
			ReportUtil.reporterEvent("pass", "Mobile added Successfully with upto 255 characters." + CommonWebActions.captureScreenshotAsBase64());
		}
		else
		{
			ReportUtil.reporterEvent("fail", "Mobile is not added Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		CommonWebActions.webClick("MessageClose");
		CommonWebActions.webClick("UserName");
		CommonWebActions.webExplicitWait("MyAccountButton", 60);
		CommonWebActions.webClick("MyAccountButton");
		CommonWebActions.webExplicitWait("ContactListItem", 180);
		CommonWebActions.webClick("ContactListItem");
		CommonWebActions.webExplicitWait("ContactListItemEdit", 180);
		CommonWebActions.webClick("ContactListItemEdit");
		CommonWebActions.webExplicitWait("ContactFieldTextEdit", 180);
		CommonWebActions.webClick("ContactFieldTextEdit");
		if(CommonWebActions.stringMatch(CommonWebActions.getWebElement("ContactFieldTextEdit").getAttribute("value"),ExcelUtil.getDataFromExcel(ReportUtil.onlyTestCaseName,"UserEmail")))
		{
			ReportUtil.reporterEvent("pass", "Mobile Edit Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		else
		{
			ReportUtil.reporterEvent("fail", "Mobile Edit not Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		CommonWebActions.webClick("ContactfieldTextCancel");
		CommonWebActions.webClick("ContactListItem");
		CommonWebActions.webExplicitWait("ContactListItemDelete", 180);
		CommonWebActions.webClick("ContactListItemDelete");
		ReportUtil.reporterEvent("pass", "Mobile Deleted Successfully." + CommonWebActions.captureScreenshotAsBase64());
		
		
		CommonWebActions.webClick("UserName");
		CommonWebActions.webExplicitWait("MyAccountButton", 60);
		CommonWebActions.webClick("MyAccountButton");
		CommonWebActions.webExplicitWait("UserProfileAddContact", 180);
		Thread.sleep(4000);
		CommonWebActions.webClick("UserProfileAddContact");
		Thread.sleep(4000);
		CommonWebActions.webClick("UserProfileAddContactListOther");
		CommonWebActions.webExplicitWait("ContactFieldText", 180);
		CommonWebActions.webSet("ContactFieldText", "UserEmail");
		CommonWebActions.webExplicitWait("ContactFieldSave", 180);
		CommonWebActions.webClick("ContactFieldSave");
		CommonWebActions.webExplicitWait("ContactSaveMessage", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("ContactSaveMessage")))
		{
			ReportUtil.reporterEvent("pass", "Other added Successfully." + CommonWebActions.captureScreenshotAsBase64());
			ReportUtil.reporterEvent("pass", "Other added Successfully with upto 255 characters." + CommonWebActions.captureScreenshotAsBase64());
		}
		else
		{
			ReportUtil.reporterEvent("fail", "Other is not added Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		CommonWebActions.webClick("MessageClose");
		CommonWebActions.webClick("UserName");
		CommonWebActions.webExplicitWait("MyAccountButton", 60);
		CommonWebActions.webClick("MyAccountButton");
		CommonWebActions.webExplicitWait("ContactListItem", 180);
		CommonWebActions.webClick("ContactListItem");
		CommonWebActions.webExplicitWait("ContactListItemEdit", 180);
		CommonWebActions.webClick("ContactListItemEdit");
		CommonWebActions.webExplicitWait("ContactFieldTextEdit", 180);
		CommonWebActions.webClick("ContactFieldTextEdit");
		if(CommonWebActions.stringMatch(CommonWebActions.getWebElement("ContactFieldTextEdit").getAttribute("value"),ExcelUtil.getDataFromExcel(ReportUtil.onlyTestCaseName,"UserEmail")))
		{
			ReportUtil.reporterEvent("pass", "Other Edit Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		else
		{
			ReportUtil.reporterEvent("fail", "Other Edit not Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		CommonWebActions.webClick("ContactfieldTextCancel");
		CommonWebActions.webClick("ContactListItem");
		CommonWebActions.webExplicitWait("ContactListItemDelete", 180);
		CommonWebActions.webClick("ContactListItemDelete");
		ReportUtil.reporterEvent("pass", "Other Deleted Successfully." + CommonWebActions.captureScreenshotAsBase64());
		
		
		CommonWebActions.webClick("UserName");
		CommonWebActions.webExplicitWait("MyAccountButton", 60);
		CommonWebActions.webClick("MyAccountButton");
		CommonWebActions.webExplicitWait("UserProfileAddContact", 180);
		Thread.sleep(4000);
		CommonWebActions.webClick("UserProfileAddContact");
		Thread.sleep(4000);
		CommonWebActions.webClick("UserProfileAddContactListTwitter");
		CommonWebActions.webExplicitWait("ContactFieldText", 180);
		CommonWebActions.webSet("ContactFieldText", "UserEmail");
		CommonWebActions.webExplicitWait("ContactFieldSave", 180);
		CommonWebActions.webClick("ContactFieldSave");
		CommonWebActions.webExplicitWait("ContactSaveMessage", 180);
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("ContactSaveMessage")))
		{
			ReportUtil.reporterEvent("pass", "Twitter added Successfully." + CommonWebActions.captureScreenshotAsBase64());
			ReportUtil.reporterEvent("pass", "Twitter added Successfully with upto 255 characters." + CommonWebActions.captureScreenshotAsBase64());
		}
		else
		{
			ReportUtil.reporterEvent("fail", "Twitter is not added Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		CommonWebActions.webClick("MessageClose");
		CommonWebActions.webClick("UserName");
		CommonWebActions.webExplicitWait("MyAccountButton", 60);
		CommonWebActions.webClick("MyAccountButton");
		CommonWebActions.webExplicitWait("ContactListItem", 180);
		CommonWebActions.webClick("ContactListItem");
		CommonWebActions.webExplicitWait("ContactListItemEdit", 180);
		CommonWebActions.webClick("ContactListItemEdit");
		CommonWebActions.webExplicitWait("ContactFieldTextEdit", 180);
		CommonWebActions.webClick("ContactFieldTextEdit");
		if(CommonWebActions.stringMatch(CommonWebActions.getWebElement("ContactFieldTextEdit").getAttribute("value"),ExcelUtil.getDataFromExcel(ReportUtil.onlyTestCaseName,"UserEmail")))
		{
			ReportUtil.reporterEvent("pass", "Twitter Edit Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		else
		{
			ReportUtil.reporterEvent("fail", "Twitter Edit not Successfully." + CommonWebActions.captureScreenshotAsBase64());
		}
		CommonWebActions.webClick("ContactfieldTextCancel");
		CommonWebActions.webClick("ContactListItem");
		CommonWebActions.webExplicitWait("ContactListItemDelete", 180);
		CommonWebActions.webClick("ContactListItemDelete");
		ReportUtil.reporterEvent("pass", "Twitter Deleted Successfully." + CommonWebActions.captureScreenshotAsBase64());
		
		CommonWebActions.webExplicitWait("UserEditProfile", 180);
		CommonWebActions.webClick("UserEditProfile");
		
		Set<String> handles = new HashSet<String>();
		handles = CommonWebActions.wd.getWindowHandles();
		List<String> handlesList = new ArrayList<String>(handles);
		CommonWebActions.wd.switchTo().window(handlesList.get(1));
		CommonWebActions.webClick("MyData");
		CommonWebActions.webClick("ModifyMyData");
		CommonWebActions.webSet("UserMobile", "UserMobile");
		CommonWebActions.webSet("UserTelephone", "UserTelephone");
		CommonWebActions.webClick("SaveModifications");
		CommonWebActions.wd.close();
		CommonWebActions.wd.switchTo().window(handlesList.get(0));
		
		
	}
	
	public static void helpNSupportValidation()
	{
		ReportUtil.reporterEvent("info", "Help N Support screenshot" + CommonWebActions.captureScreenshotAsBase64());
		helpNSupportDataComp("CustomerExperienceProgram", "CEPContact_201-573-5333");
		helpNSupportDataComp("CustomerAssistanceCenterDealerHotline", "CACD_888-628-7232");
		helpNSupportDataComp("EuropeanDelivery", "ED_800-243-3876");
		helpNSupportDataComp("F&IProSupport", "F&IContact_866-485-8567");
		helpNSupportDataComp("FleetOperations", "FOContact_866-628-7232");
		helpNSupportDataComp("Freightliner", "FlContact_503-745-8000");
		helpNSupportDataComp("MBFinancialDealerSupport", "MBFDSContact_800-530-9997");
		helpNSupportDataComp("MBCPOELWPPMSupport", "MBCSContact_877-202-6262");
		helpNSupportDataComp("MBFCommercialServicesSupport", "MBFSSContact_866-351-4672");
		helpNSupportDataComp("MercedesBenzCreditEastCoast", "MBCECContact_800-441-7528");
		helpNSupportDataComp("MercedesBenzCreditWestCoast", "MBCWCContact_800-547-4260");
		helpNSupportDataComp("MercedesBenzFinancialServices", "MBFSContact_800-654-6222");
		helpNSupportDataComp("MSSSupportHotline", "MSHContact_201-573-4321");
		helpNSupportDataComp("NetStarSupport", "NSSContact_888-963-8782");
		helpNSupportDataComp("PartsAssistanceCenter", "PACContact_877-727-8762");
		helpNSupportDataComp("SprinterAssistanceCenter", "SACContact_877-762-8267");
		helpNSupportDataComp("SprinterDistributionSupport", "SDSContact_877-597-8267");
		helpNSupportDataComp("SprinterEngineeringSupport", "SESContact_877-367-0024");
		helpNSupportDataComp("StarDiagnosisSystemSupport", "SDSContact_201-505-4630");
		helpNSupportDataComp("VehicleDistributionHotline", "VDHContact_800-634-6262");
		helpNSupportDataComp("WarrantyServiceCenter", "WSCContact_877-202-6262");
		helpNSupportDataComp("WarrantyServiceGroup", "WSGContact_877-974-6287");
		
		
		
		
	}
	
	public static void helpNSupportDataComp(String logicalNameContactName, String logicalNameContact)
	{
		CommonWebActions.webExplicitWait(logicalNameContactName, 180);
		if (CommonWebActions.webExists(CommonWebActions.getWebElement(logicalNameContactName)))
		{
			if (CommonWebActions.webExists(CommonWebActions.getWebElement(logicalNameContact)))
				ReportUtil.reporterEvent("pass", logicalNameContactName + " and " + logicalNameContact + " displayed together in Help and Support page");
			else
				ReportUtil.reporterEvent("fail", logicalNameContactName + " displayed but " + logicalNameContact + " not displayed in Help and Support page");
		}
		else if(CommonWebActions.webExists(CommonWebActions.getWebElement(logicalNameContact)))
			ReportUtil.reporterEvent("fail", logicalNameContactName + " not displayed but " + logicalNameContact + " displayed in Help and Support page");
		else
		{
			ReportUtil.reporterEvent("fail", logicalNameContactName + " and " + logicalNameContact + " both not displayed in Help and Support page");
		}
	}
	
	public static void validateAnnouncements() throws InterruptedException
	{
		
		CommonWebActions.webExplicitWait("MyAnnWidgetHeader", 120);
		if (CommonWebActions.webExists(CommonWebActions.getWebElement("MyAnnWidgetHeader")))
		{
			ReportUtil.reporterEvent("pass", "My Announcements widget displayed at home page with text [" + CommonWebActions.getWebElementText("MyAnnWidgetHeader") + "]");
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("AnnouncementUnreadLebel")))
			{
				ReportUtil.reporterEvent("pass", "Unread message displayed in My Announcement widget-["+ CommonWebActions.getWebElementText("AnnouncementUnreadLebel") + "]");
			}
			else
			{
				ReportUtil.reporterEvent("fail", "Unread message not displayed for My Announcement widget" + CommonWebActions.captureScreenshotAsBase64());
			}
			
			if (CommonWebActions.webExists(CommonWebActions.getWebElement("MyAnnWidgetViewAllBtn")))
			{
				ReportUtil.reporterEvent("pass", "View All button is present in My Announcement widget with text- " + CommonWebActions.getWebElementText("MyAnnWidgetViewAllBtn"));
			}
			else
			{
				ReportUtil.reporterEvent("fail", "View All button is not present in My Announcemnt widget" + CommonWebActions.captureScreenshotAsBase64());
			}
		}
		else
		{
			ReportUtil.reporterEvent("fail", "My Announcements widget not displayed at home page" + CommonWebActions.captureScreenshotAsBase64());
		}
		
		clickLeftMenu2Level("CommunicationLink", "AnnouncementsLink", "AnnouncementHeader");
		Thread.sleep(5000);
		CommonWebActions.webClick("FirstAnnouncement");
		CommonWebActions.webExplicitWait("AnnouncementHeader", 180);
		ReportUtil.reporterEvent("pass", "Announcement items in larger view"+ CommonWebActions.captureScreenshotAsBase64());
		
		CommonWebActions.webClick("HomeLink");
		CommonWebActions.webExplicitWait("MyAnnWidgetViewAllBtn", 180);
		
		CommonWebActions.webClick("MyAnnWidgetViewAllBtn");
		CommonWebActions.webExplicitWait("AnnouncementHeader", 180);
		ReportUtil.reporterEvent("pass", "Announcement items in larger view"+ CommonWebActions.captureScreenshotAsBase64());
		Thread.sleep(10000);
		CommonWebActions.webClick("HomeLink");
		CommonWebActions.webExplicitWait("AnnouncementUnread", 180);
		
		String unread = CommonWebActions.getWebElementText("AnnouncementUnread");
		
		String unreaditem = unread.substring(0, 1);
		int unreadvalue = Integer.valueOf(unreaditem);
		unreadvalue = unreadvalue - 1;
		System.out.println("unread: "+unreadvalue);
		clickLeftMenu2Level("CommunicationLink", "AnnouncementsLink", "AnnouncementHeader");
		Thread.sleep(5000);
		Actions action = new Actions(CommonWebActions.wd);
		action.moveToElement(CommonWebActions.getWebElement("FirstUnreadAnnouncement")).click().perform();
		CommonWebActions.webClick("HomeLink");
		CommonWebActions.webExplicitWait("Dashboardbreadcrumb", 180);
		String unread1 = CommonWebActions.getWebElementText("AnnouncementUnread");
		String unreaditem1 = unread1.substring(0, 1);
		int unreadvalue1 = Integer.valueOf(unreaditem1);
		ReportUtil.reporterEvent("pass", "Announcement marked as read"+ CommonWebActions.captureScreenshotAsBase64());
		if(unreadvalue == unreadvalue1)
			ReportUtil.reporterEvent("pass", "Announcement marked as read and unread count is decreased"+ CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Announcement is not marked as read  and unread count is not decreased"+ CommonWebActions.captureScreenshotAsBase64());
		//Thread.sleep(5000);
		clickLeftMenu2Level("CommunicationLink", "AnnouncementsLink", "AnnouncementHeader");
		Thread.sleep(5000);
		
		if (CommonWebActions.webExists(CommonWebActions.getWebElement("FirstAnnouncement")))
		{
			ReportUtil.reporterEvent("pass", "Announcement is present [" + CommonWebActions.getWebElementText("FirstAnnouncement") + "]" + CommonWebActions.captureScreenshotAsBase64());
		
		}
		else
		{
			ReportUtil.reporterEvent("fail", "Announcement is not present" + CommonWebActions.captureScreenshotAsBase64() );
		}
		
		CommonWebActions.webExplicitWait("AnnouncementPDFFirst", 180);
		CommonWebActions.webClick("AnnouncementPDFFirst");
		
		
		Thread.sleep(10000);
		Set<String> handles = new HashSet<String>();
		handles = CommonWebActions.wd.getWindowHandles();
		List<String> handlesList = new ArrayList<String>(handles);
		CommonWebActions.wd.switchTo().window(handlesList.get(1));
		CommonWebActions.wd.close();
		CommonWebActions.wd.switchTo().window(handlesList.get(0));
		
		ReportUtil.reporterEvent("pass", "User allowed to open the attachment" + CommonWebActions.captureScreenshotAsBase64());
	}
	
	public static void displayOfAnnouncements(){
		List<WebElement> allNewsTimes = new ArrayList<WebElement>();
		allNewsTimes = CommonWebActions.getWebElements("AnnouncementRecentTime");
boolean flag=true;
		List<String> daysFormat = new ArrayList<String>();
		List<String> allTimes = new ArrayList<String>();
		List<String> dateFormat = new ArrayList<String>();
		int daysCount=0,dateFormatCount=0;
		//validate time format
		for(WebElement s:allNewsTimes){
			
			if(s.getText().toString().contains("a day ago")){
				flag = true;
				daysCount = daysCount + 1;
				daysFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else if(s.getText().toString().contains("hours ago")){
				flag = true;
				daysCount++;
				daysFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else if(s.getText().toString().contains("days ago")){
				
				flag = true;
				daysCount++;
				daysFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else if(s.getText().toString().contains(",")){
				flag = true;
				dateFormatCount = dateFormatCount + 1;
				dateFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else
				flag=false;
			
			
		}
		System.out.println(allTimes.size());
		System.out.println(daysFormat.size());
		System.out.println(dateFormat.size());
		System.out.println(daysCount);
		System.out.println(dateFormatCount);
		boolean flagOrderday = false,flagOrderDate=false,daysFormatOrderFlag=false;
		//validate days format
		for(int i=0;i<daysFormat.size();i++){
			if(CommonWebActions.stringMatchesToRegExp(allTimes.get(i).substring(2),"([1-9]|[1-9][0-9]) days ago"))
				flagOrderday=true;
			else
				flagOrderday=false;
		}
		List<String> daysFormatOrder = new ArrayList<String>();
		daysFormatOrder = daysFormat;
		Collections.sort(daysFormat);
		if(daysFormatOrder == daysFormat)
			daysFormatOrderFlag=true;
		else
			daysFormatOrderFlag=false;
		//validate month format
		for(int i=daysFormat.size();i<dateFormat.size();i++){
			if(CommonWebActions.stringMatchesToRegExp(allTimes.get(i).substring(2),"[(January|February|March|April|May|June|July|September|August|October|November|December)]*\\s([1-9]|[1-9][1-9])\\,\\s([1-2]([0]|[9])[0-9][0-9])"))
				flagOrderDate=true;
			else
				flagOrderDate=false;
		}
		
		
		if(daysFormat.size()>0){
			if(flagOrderday && daysFormatOrderFlag)
				ReportUtil.reporterEvent("pass", "Day Items are displayed in reverse order - as most recent first and oldest in last "+CommonWebActions.captureScreenshotAsBase64());
			else
				ReportUtil.reporterEvent("fail", "Day Items are not displayed in reverse order - as most recent first and oldest in last  "+CommonWebActions.captureScreenshotAsBase64());
			}
			else
				ReportUtil.reporterEvent("info", "There is no Day items"+CommonWebActions.captureScreenshotAsBase64());
		
		
			
		
		if(dateFormat.size()>0){
		if(flagOrderDate)
			ReportUtil.reporterEvent("pass", "Items are displayed in reverse order - as most recent first and oldest in last "+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Items are not displayed in reverse order - as most recent first and oldest in last  "+CommonWebActions.captureScreenshotAsBase64());
		}
		else
			ReportUtil.reporterEvent("info", "There is no Date items"+CommonWebActions.captureScreenshotAsBase64());
	}
	
	public static void displayOfAnnouncementsInwidget(){
		CommonWebActions.webClick("HomeLink");
		//CommonWebActions.webExplicitWait("Dashboardbreadcrumb", 180);
		CommonWebActions.webExplicitWait("AnnouncementWidgetUnread", 180);
		List<WebElement> allNewsTimes = new ArrayList<WebElement>();
		allNewsTimes = CommonWebActions.getWebElements("AnnouncementRecentTimeInwidget");
boolean flag=true;
		List<String> daysFormat = new ArrayList<String>();
		List<String> allTimes = new ArrayList<String>();
		List<String> dateFormat = new ArrayList<String>();
		int daysCount=0,dateFormatCount=0;
		//validate time format
		for(WebElement s:allNewsTimes){
			
			if(s.getText().toString().contains("a day ago")){
				flag = true;
				daysCount = daysCount + 1;
				daysFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else if(s.getText().toString().contains("hours ago")){
				flag = true;
				daysCount++;
				daysFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else if(s.getText().toString().contains("days ago")){
				
				flag = true;
				daysCount++;
				daysFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else if(s.getText().toString().contains(",")){
				flag = true;
				dateFormatCount = dateFormatCount + 1;
				dateFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else
				flag=false;
			
			
		}
		System.out.println(allTimes.size());
		System.out.println(daysFormat.size());
		System.out.println(dateFormat.size());
		System.out.println(daysCount);
		System.out.println(dateFormatCount);
		boolean flagOrderday = false,flagOrderDate=false,daysFormatOrderFlag=false;
		
		//validate days format
		for(int i=0;i<daysFormat.size();i++){
			int n = allTimes.get(i).length()-2;
			if(CommonWebActions.stringMatchesToRegExp(allTimes.get(i).substring(2,n),"([1-9]|[1-9][0-9]) days ago"))
				flagOrderday=true;
			else
				flagOrderday=false;
		}
		List<String> daysFormatOrder = new ArrayList<String>();
		daysFormatOrder = daysFormat;
		Collections.sort(daysFormat);
		if(daysFormatOrder == daysFormat)
			daysFormatOrderFlag=true;
		else
			daysFormatOrderFlag=false;
		//validate month format
		for(int i=daysFormat.size();i<dateFormat.size();i++){
			int m = allTimes.get(i).length()-2;
			System.out.println(allTimes.get(i).substring(2,m));
			if(CommonWebActions.stringMatchesToRegExp(allTimes.get(i).substring(2,m),"[(January|February|March|April|May|June|July|September|August|October|November|December)]*\\s([1-9]|[1-9][1-9])\\,\\s([1-2]([0]|[9])[0-9][0-9])"))
				flagOrderDate=true;
			else
				flagOrderDate=false;
		}
		
		
		if(daysFormat.size()>0){
			if(flagOrderday && daysFormatOrderFlag)
				ReportUtil.reporterEvent("pass", "Day Items are displayed in reverse order - as most recent first and oldest in last "+CommonWebActions.captureScreenshotAsBase64());
			else
				ReportUtil.reporterEvent("fail", "Day Items are not displayed in reverse order - as most recent first and oldest in last  "+CommonWebActions.captureScreenshotAsBase64());
			}
			else
				ReportUtil.reporterEvent("info", "There is no Day items"+CommonWebActions.captureScreenshotAsBase64());
		
		
			
		
		if(dateFormat.size()>0){
		if(flagOrderDate)
			ReportUtil.reporterEvent("pass", "Items are displayed in reverse order - as most recent first and oldest in last "+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Items are not displayed in reverse order - as most recent first and oldest in last  "+CommonWebActions.captureScreenshotAsBase64());
		}
		else
			ReportUtil.reporterEvent("info", "There is no Date items"+CommonWebActions.captureScreenshotAsBase64());
		
	}
	
	public static void validateDealership(String lgNmDealership, String exlDealership)
	{
		System.out.println("ValidateDealership Method starts");
		CommonWebActions.webExplicitWait(lgNmDealership, 60);
		
		if (CommonWebActions.webVerifyInnerText(lgNmDealership, exlDealership))
		{
			if (CommonWebActions.webVerifyInnerText("UserName", "UserName2"))
			{
				ReportUtil.reporterEvent("pass", "Dealership name and User name displayed as expected");
			}
			else 
				ReportUtil.reporterEvent("fail", "User name not displayed as expected");	
			}
		else
		{
					
			ReportUtil.reporterEvent("fail", "Dealership name not displayed as expected");
		}
		
		System.out.println("ValidateDealership Method ends");
		
	}
	
	
	
	public static void changeDealership(String lgNmDealership) throws InterruptedException
	{
		
		CommonWebActions.webExplicitWait("UserName", 60);
		CommonWebActions.webClick("UserName");
		CommonWebActions.webExplicitWait(lgNmDealership,60);
		System.out.println(CommonWebActions.getWebElementText(lgNmDealership));
		CommonWebActions.webClick(lgNmDealership);
//		ReportUtil.reporterEvent("info", "wait starts");
		Thread.sleep(10000);
//		ReportUtil.reporterEvent("info", "wait over");
		System.out.println("changeDealership Method over");
	}
	
	public static void validateRecentUpdateTime(List<WebElement> list){
		boolean flag=true;
		
		for(WebElement s:list){
			if(CommonWebActions.stringMatchesToRegExp(s.getText().toString(),"A DAY AGO"))
				flag = true;
			else if(CommonWebActions.stringMatchesToRegExp(s.getText().toString(),"[1-9]|[1-9][0-9] HOURS AGO"))
				flag = true;
			else if(CommonWebActions.stringMatchesToRegExp(s.getText().toString(),"[1-9]|[1-9][0-9] DAYS AGO"))
				flag = true;
			else if(CommonWebActions.stringMatchesToRegExp(s.getText().toString(),"[(JANUARY|FEBRUARY|MARCH|APRIL|MAY|JUNE|JULY|SEPTEMBER|AUGUST|OCTOBER|NOVEMBER|DECEMBER)]*\\s([1-9]|[1-9][1-9])\\,\\s([1-2]([0]|[9])[0-9][0-9])"))
				flag = true;
			else
				flag=false;
		}
		
		List<String> times = CommonWebActions.addItemsToList(CommonWebActions.getWebElements("RecentUpdateTime"));
		List<String> reports = CommonWebActions.addItemsToList(CommonWebActions.getWebElements("AllReports"));
		
		List<String> arr = new ArrayList<String>();int j=0; String all;
		for(int i=1;i<reports.size();i++){
			all ="<br>("+ i + ") "+reports.get(i) + "&nbsp&nbsp&nbsp&nbsp&nbsp"+ times.get(j);
			arr.add(all);
			j=j+1;
		}
		
		if(flag == true)
			ReportUtil.reporterEvent("pass", "Recent Update Times  is present for all below reports<br>"+arr+CommonWebActions.captureScreenshotAsBase64());
		else if(flag == false)
			ReportUtil.reporterEvent("fail", "Recent Update Time is not present for all below reports<br> "+arr+ CommonWebActions.captureScreenshotAsBase64() );
	}
	
	public static void validateReportIcon(List<WebElement> list){
		boolean flag = true;
		for(WebElement s:list){
		if(CommonWebActions.webExists(s))
			flag = true;
		else
			flag = false;
	}
		List<String> reports = CommonWebActions.addItemsToList(CommonWebActions.getWebElements("AllReports"));
		List<String> arr = new ArrayList<String>();String all;
		for(int i=1;i<reports.size();i++){
			all ="<br>("+ i + ") "+reports.get(i);
			arr.add(all);
		}
		
		if(flag == true)
			ReportUtil.reporterEvent("pass", "Report Icon present for all below reports"+arr+CommonWebActions.captureScreenshotAsBase64());
		else if(flag == false)
			ReportUtil.reporterEvent("fail", "Report Icon is not present for all below reports"+arr+ CommonWebActions.captureScreenshotAsBase64() );
}
	
	public static void validateFavoriteIcon(WebElement element){
		if(CommonWebActions.webExists(element))
			ReportUtil.reporterEvent("pass", "Favourite Icon present in the menu item"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Favourite Icon is not present in the menu item "+ CommonWebActions.captureScreenshotAsBase64() );
	}
	
	public static void addToFavoriteItem(){
		
		String val = CommonWebActions.getWebElementText("ReportNotFavorited");
		
		CommonWebActions.webClick("NotFavorited");
		CommonWebActions.webClick("FavoritesIcon");
		List<WebElement> list = CommonWebActions.getWebElements("ReportFavorited");
		boolean flag = false ;
		for(WebElement s:list){
			if(CommonWebActions.stringMatch(s.getText(),val))
				{
					flag = true;
					break;
				}
			else
				flag = false;
		}
		if(flag == true)
			ReportUtil.reporterEvent("pass", "Report ["+val+"] is added in to Favorite "+CommonWebActions.captureScreenshotAsBase64());
		else if(flag == false)
			ReportUtil.reporterEvent("fail", "Report ["+val+"] is not added in to Favorite "+ CommonWebActions.captureScreenshotAsBase64() );
		}
	
	public static void validateReportDisplayedWithYellowColorStarFavoriteIcon(){
		List<WebElement> list = CommonWebActions.getWebElements("Favorited");
		boolean flag = false;
		for(WebElement ele : list){
			if(CommonWebActions.stringMatch(ele.getCssValue("color"),"rgba(253, 215, 0, 1)"))
				flag = true;
			else
				flag = false;
		}
		
		List<String> reportFavorited = CommonWebActions.addItemsToList(CommonWebActions.getWebElements("ReportFavorited"));
		List<String> arr = new ArrayList<String>();String all;
		for(int i=1;i<reportFavorited.size();i++){
			all ="<br>("+ i + ") "+reportFavorited.get(i);
			arr.add(all);
		}
		
		if(flag == true)
			ReportUtil.reporterEvent("pass", "All below Report titles are displayed with Favorite icon under Favorite list"+arr+CommonWebActions.captureScreenshotAsBase64());
		else if(flag == false)
			ReportUtil.reporterEvent("fail", "All below Report titles are displayed with Favorite icon under Favorite list" +arr+ CommonWebActions.captureScreenshotAsBase64() );
	}
	
	public static void validateUserPopUp(){
		CommonWebActions.webClick("UserName");
		CommonWebActions.webExplicitWait("MyAccountButton", 60);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("UserStep2PopUp")))
			ReportUtil.reporterEvent("pass", "PopUp Displayed with Account Information and Dealership information"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "PopUp is not Displayed with Account Information and Dealership information"+CommonWebActions.captureScreenshotAsBase64());
		
	}
	
	public static void validateAccountPageDisplay(){
		CommonWebActions.webClick("MyAccountButton");
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("UserProfileTab")))
			ReportUtil.reporterEvent("pass", "Account Page is Displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Account Page is not Displayed"+CommonWebActions.captureScreenshotAsBase64());
	}
	
	public static void validateLinksManagementPageDisplay(){
		CommonWebActions.webClick("LinksManagementTab");
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("LinksManagementBreadcrumb")))
			ReportUtil.reporterEvent("pass", "Link Management Page is Displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Link Management Page is not Displayed"+CommonWebActions.captureScreenshotAsBase64());
	}
	
	public static void validateLinkManagementMessageDisplay(){
		CommonWebActions.webExplicitWait("LinkManagementMessage", 180);
		if(CommonWebActions.webVerifyInnerText("LinkManagementMessage", "LinkManagementMessage"))
			ReportUtil.reporterEvent("pass", "Link Management Page is Displayed wit Message [To add links to My Links, select from the list below]"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Link Management Page is not Displayed with message [To add links to My Links, select from the list below]"+CommonWebActions.captureScreenshotAsBase64());
	}
	
	public static void validateAvailableLinks(){
		if(CommonWebActions.webVerifyInnerText("AvailableLinks", "AvailableLinkMessage"))
			ReportUtil.reporterEvent("pass", "Link Management Page is Displayed wit Message [Available Links]"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Link Management Page is not Displayed with message [Available Links]"+CommonWebActions.captureScreenshotAsBase64());
	}
	
	public static void validateMyLinks(){
		if(CommonWebActions.webVerifyInnerText("MyLinks", "MyLinkMessage"))
			ReportUtil.reporterEvent("pass", "Link Management Page is Displayed wit Message [My Links]"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Link Management Page is not Displayed with message [My Links]"+CommonWebActions.captureScreenshotAsBase64());
	}
	
	public static void validateCategoriesAndLinks() throws Exception{
		
		List<WebElement> allCategories = CommonWebActions.getWebElements("Categories");
		String[] allCatArrays = new String[allCategories.size()];
		for(int m=0;m<allCategories.size();m++)
			allCatArrays[m]=allCategories.get(m).getText();
		List<String> allCategoriesLinks = new ArrayList<String>();
		for(int i=0;i<allCatArrays.length;i++){
			allCategoriesLinks = CommonWebActions.split(ExcelUtil.getDataFromExcel(ReportUtil.onlyTestCaseName,allCatArrays[i]));
			CommonWebActions.webClick(allCatArrays[i]);
			CommonWebActions.webExplicitWait("Header", 180);
			List<WebElement> allCatCheckBox = CommonWebActions.getWebElements("HeaderCheckbox");
			List<WebElement> allLinkCheckBox = CommonWebActions.getWebElements("linkCheckBox");
			boolean catcheckbox = false,linkcheckbox = false;
			for(WebElement catCheckBox : allCatCheckBox)
			{
				if(catCheckBox.getAttribute("type").equals("checkbox"))
					catcheckbox = true;break;
				
			}
			if(catcheckbox == true)
				ReportUtil.reporterEvent("pass", "Category["+allCatArrays[i]+"]has check box"+CommonWebActions.captureScreenshotAsBase64());
			else
				ReportUtil.reporterEvent("fail", "Category["+allCatArrays[i]+"]does not have check box"+CommonWebActions.captureScreenshotAsBase64());
			
			for(WebElement linkCheckBox : allLinkCheckBox)
			{
				if(linkCheckBox.getAttribute("type").equals("checkbox"))
					linkcheckbox = true;
				else
					linkcheckbox = false;break;
				
			}
			
			List<WebElement> allCategoryLinksFromApp = new ArrayList<WebElement>();
			allCategoryLinksFromApp = CommonWebActions.getWebElements("CategoryLinks");
			
			List<String> alllinkchecks = new ArrayList<String>();
			for(int n=1;n<allCategoryLinksFromApp.size();n++)
				alllinkchecks.add("<br>"+allCategoryLinksFromApp.get(n).getText());
			if(linkcheckbox != false)
				ReportUtil.reporterEvent("pass", "Under this Category All Links has check box"+alllinkchecks+CommonWebActions.captureScreenshotAsBase64());
			else if(linkcheckbox == false)
				ReportUtil.reporterEvent("fail", "All Links does not have check box"+alllinkchecks+CommonWebActions.captureScreenshotAsBase64());
			
			
			boolean flagLinks = false;int count = 0;
			List<String> all = new ArrayList<String>();
			all.add("<br>("+i+") Category Name ["+allCatArrays[i]+"]"+"<br>");
			for(int j=0;j<allCategoriesLinks.size();j++){
				for(WebElement catName : allCategoryLinksFromApp){
					if(CommonWebActions.stringMatch(catName.getText(), allCategoriesLinks.get(j))){
						flagLinks = true;
						all.add("BookMarked Link:["+allCategoriesLinks.get(j)+"] is present in the My Link:"+catName.getText()+"]<br>");
						count = count +1;
					}
				}
			}
			if(flagLinks == true)
				ReportUtil.reporterEvent("pass", all+CommonWebActions.captureScreenshotAsBase64());
			else if(count > 1)
				ReportUtil.reporterEvent("fail", "links are present more than once: Times:["+count+"]"+CommonWebActions.captureScreenshotAsBase64());
			else 
				ReportUtil.reporterEvent("fail", "Category and links are not present"+CommonWebActions.captureScreenshotAsBase64());
		}
	}
	
	public static void validateSavedLinks(){
		CommonWebActions.webClick("ALL");
		List<WebElement> allSavedLinks = CommonWebActions.getWebElements("SavedLinks");
		Set<String> allSavedLinksList = new HashSet<String>();String s="";
		List<WebElement> allSavedLinksText = CommonWebActions.getWebElements("SavedLinksText");
		
		for(int i=0;i<allSavedLinks.size();i++){
			if(allSavedLinks.get(i).isSelected()){
			s=allSavedLinksText.get(i).getText().toString();
			allSavedLinksList.add(s);
			}
		}
	
		List<WebElement> myLinksSaved = CommonWebActions.getWebElements("MyLinksSaved");
		List<String> myLinksSavedList = new ArrayList<String>();
		for(int i=0;i<myLinksSaved.size();i++)
			myLinksSavedList.add(myLinksSaved.get(i).getText());
			
		List<String> allSavedLinksListToList = new ArrayList<String>(allSavedLinksList);
		Collections.sort(allSavedLinksListToList);
		Collections.sort(myLinksSavedList);
		if(allSavedLinksListToList.equals(myLinksSavedList))
				ReportUtil.reporterEvent("pass", "Available or Saved Links<br>"+allSavedLinksListToList+" <br><br>are present in my links<br><br>"+myLinksSavedList+CommonWebActions.captureScreenshotAsBase64());
		else
				ReportUtil.reporterEvent("fail", "Saved Links are not present in my links"+CommonWebActions.captureScreenshotAsBase64());
	}
	
	
	public static void validateNoLinks(){
		CommonWebActions.webClick("HomeLink");
		CommonWebActions.webClick("Link");
		CommonWebActions.webExplicitWait("PopupMyLink", 180);
		CommonWebActions.webExists(CommonWebActions.getWebElement("PopupMyLink"));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CommonWebActions.webClick("MyLinkSetting");
		CommonWebActions.webExplicitWait("LinksManagementBreadcrumb", 180);
		CommonWebActions.webExists(CommonWebActions.getWebElement("LinksManagementBreadcrumb"));
		List<WebElement> allMyLinks = CommonWebActions.getWebElements("MyLinksSaved");
		/*for(int i=0;i<allMyLinks.size();i++) 
			System.out.println(allMyLinks.get(i).getText());*/
		List<WebElement> allDeleteMyLinks = CommonWebActions.getWebElements("DeleteMyLink");
		//Delete all links
		for(int i=0;i<allMyLinks.size();i++) {
			
				
			
				Actions actions = new Actions(CommonWebActions.wd);
				CommonWebActions.webImplicitWait(CommonWebActions.wd, 5);
				actions.moveToElement(allMyLinks.get(i)).click().build().perform();
				CommonWebActions.webImplicitWait(CommonWebActions.wd, 5);
				actions.moveToElement(allDeleteMyLinks.get(i)).click().build().perform();
			
		}
		CommonWebActions.webClick("HomeLink");
		CommonWebActions.webClick("Link");
		CommonWebActions.webExplicitWait("PopupMyLink", 180);
		CommonWebActions.webVerifyInnerText("DeleteMyLinkMessage", "MyLinkDeleteMessage");	
		
	}
	
	public static void SeeAllInLinksDropdown(){
		CommonWebActions.webClick("HomeLink");
		CommonWebActions.webClick("Link");
		CommonWebActions.webExplicitWait("PopupMyLink", 180);
		CommonWebActions.webVerifyInnerText("SeeAll", "SeeAll");
		CommonWebActions.webClick("SeeAll");
		CommonWebActions.webExplicitWait("LinksManagementBreadcrumb", 180);
		CommonWebActions.webExists(CommonWebActions.getWebElement("LinksManagementBreadcrumb"));
		List<WebElement> myLinksSaved = CommonWebActions.getWebElements("MyLinksSaved");
		List<String> myLinksSavedList = new ArrayList<String>();
		for(int i=0;i<myLinksSaved.size();i++)
			myLinksSavedList.add(myLinksSaved.get(i).getText());
		if(!myLinksSavedList.isEmpty())
		ReportUtil.reporterEvent("pass", "My Links is displaying below links<br>"+myLinksSavedList+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "My Links is not displaying Links"+CommonWebActions.captureScreenshotAsBase64());
	}
	
	public static void validateMyLinksScrollDown(){
		CommonWebActions.webClick("HomeLink");
		CommonWebActions.webClick("Link");
		CommonWebActions.webExplicitWait("PopupMyLink", 180);
		CommonWebActions.webExists(CommonWebActions.getWebElement("PopupMyLink"));
		CommonWebActions.scrollIntoWebElement_new("lastMyLink");
		List<WebElement> allLinkPopUp = CommonWebActions.getWebElements("AllLinksInPopup");
		List<String> allLinkPopUpNames = new ArrayList<String>();
		for(int i=0;i<allLinkPopUp.size();i++)
			allLinkPopUpNames.add(allLinkPopUp.get(i).getText());
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("lastMyLink")))
			ReportUtil.reporterEvent("pass", "Remaining Links shown in when scroll<br>"+allLinkPopUpNames+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Remaining Links not shown in when scroll"+CommonWebActions.captureScreenshotAsBase64());
		CommonWebActions.webClick("Link");
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("PopupMyLink"))==false)
			ReportUtil.reporterEvent("pass", "My Links Popup is closed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "My Links Popup is not closed"+CommonWebActions.captureScreenshotAsBase64());
	}
	
	public static void validateNewsSettingsListBox(){
		CommonWebActions.webClick("CommunicationLink");
		CommonWebActions.webExplicitWait("NewsLink", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NewsLink")))
			ReportUtil.reporterEvent("pass", "News link is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "News link is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		CommonWebActions.webClick("NewsLink");
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NewsLinkBreadscrumb")))
			ReportUtil.reporterEvent("pass", "News link page is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "News link page is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		CommonWebActions.webClick("MyNewsSettingsIcon");
		CommonWebActions.webExplicitWait("NewsSubscriptionsMessage", 180);
		CommonWebActions.webVerifyInnerText("NewsSubscriptionsMessage","MyLinksSettingMessage");
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("FranchiseName")))
			ReportUtil.reporterEvent("pass", "FranchiseName column is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "FranchiseName column is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("EmailNotification")))
			ReportUtil.reporterEvent("pass", "EmailNotification column is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "EmailNotification column is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		List<WebElement> allNewsChannels = new ArrayList<WebElement>();
		allNewsChannels = CommonWebActions.getWebElements("NewsChannels");
		List<WebElement> allMBPCPortal = CommonWebActions.getWebElements("MBPCPortal");
		List<WebElement> allMBPCEmail = new ArrayList<WebElement>();
		allMBPCEmail = CommonWebActions.getWebElements("MBPCEmail");
		List<WebElement> allSmartPortal = new ArrayList<WebElement>();
		allSmartPortal = CommonWebActions.getWebElements("SmartPortal");
		List<WebElement> allSmartEmail = new ArrayList<WebElement>();
		allSmartEmail = CommonWebActions.getWebElements("SmartEmail");
		
		boolean checkFlagFalse1 = false,checkFlagFalse2 = false,checkFlagFalse3 = false,checkFlagFalse4 = false;
		for(WebElement ele : allMBPCPortal)
		{
			if(ele.getAttribute("type").equals("checkbox"))
				checkFlagFalse1 = true;
			else
				checkFlagFalse1 = false;
			
		}
		for(WebElement ele : allMBPCEmail)
		{
			if(ele.getAttribute("type").equals("checkbox"))
				checkFlagFalse2 = true;
			else
				checkFlagFalse2 = false;
			
		}
		for(WebElement ele : allSmartPortal)
		{
			if(ele.getAttribute("type").equals("checkbox"))
				checkFlagFalse3 = true;
			else
				checkFlagFalse3 = false;
			
		}
		for(WebElement ele : allSmartEmail)
		{
			if(ele.getAttribute("type").equals("checkbox"))
				checkFlagFalse4 = true;
			else
				checkFlagFalse4 = false;
			
		}
		List<String> allNewsChannelsString = new ArrayList<String>();
		for(int i=0;i<allNewsChannels.size();i++)
			allNewsChannelsString.add(allNewsChannels.get(i).getText());
		if(checkFlagFalse1==true&&checkFlagFalse2==true&&checkFlagFalse3==true&&checkFlagFalse4 == true)
			ReportUtil.reporterEvent("pass", "All News channels  are displayed with check box[MBPC Portal, MBPC Email, Smart Portal, Smart Email] "+allNewsChannelsString+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "All News channels   are not displayed with check box[MBPC Portal, MBPC Email, Smart Portal, Smart Email] "+allNewsChannelsString+CommonWebActions.captureScreenshotAsBase64());
		CommonWebActions.scrollIntoWebElement_new("LastNewChannel");
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("LastNewChannel")))
			ReportUtil.reporterEvent("pass", "Remaining News Channels shown in when scroll<br>"+allNewsChannelsString+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Remaining News Channels not shown in when scroll"+CommonWebActions.captureScreenshotAsBase64());
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NewsChannelOKButton")))
			ReportUtil.reporterEvent("pass", "OK button is present in News Channel Settings page"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "OK button is not present in News Channel Settings page"+CommonWebActions.captureScreenshotAsBase64());
	}
	
	public static void validateMyNewsWidget(){
		CommonWebActions.webClick("HomeLink");
		CommonWebActions.webExplicitWait("Dashboardbreadcrumb", 180);
		
		if(CommonWebActions.getWebElements("MyNewsUnreadItems").isEmpty())
			ReportUtil.reporterEvent("info", "There is no UnRead items");
		else
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("MyNewsUnreadItemslabel")))
				ReportUtil.reporterEvent("pass", "Unread Item is present "+CommonWebActions.captureScreenshotAsBase64());
			else
				ReportUtil.reporterEvent("fail", "Unread Item is not present "+CommonWebActions.captureScreenshotAsBase64());
		if(CommonWebActions.stringMatchesToRegExp(CommonWebActions.getWebElement("MyNewsText").getText().toString(), "My News [1-9][0-9] UNREAD"))
			ReportUtil.reporterEvent("pass", "Unread Item["+CommonWebActions.getWebElement("MyNewsText").getText().toString()+"] is present "+CommonWebActions.captureScreenshotAsBase64());
		else
				ReportUtil.reporterEvent("fail", "Unread Item is not present "+CommonWebActions.captureScreenshotAsBase64());
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("SettingsGearIcon")))
			ReportUtil.reporterEvent("pass", "Settings Gear icon is present "+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Settings Gear icon is not present "+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.getWebElements("AllMyNewsItems").isEmpty())
			ReportUtil.reporterEvent("info", "My News or subscription items are empty.");
		else if(!CommonWebActions.getWebElements("AllMyNewsItems").isEmpty())
			ReportUtil.reporterEvent("Pass", "My News or subscription items are displayed in widget. "+CommonWebActions.captureScreenshotAsBase64());
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("MyNewsViewAll")))
			ReportUtil.reporterEvent("pass", "View All link is present in the center bottom of the widget"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "View All link is not present in the center bottom of the widget "+CommonWebActions.captureScreenshotAsBase64());			
	}
	
	public static void displayOfNewsSettingsPage(){
		CommonWebActions.webClick("CommunicationLink");
		CommonWebActions.webExplicitWait("NewsLink", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NewsLink")))
			ReportUtil.reporterEvent("pass", "News link is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "News link is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		CommonWebActions.webClick("NewsLink");
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NewsLinkBreadscrumb")))
			ReportUtil.reporterEvent("pass", "News link page is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "News link page is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		CommonWebActions.webClick("MyNewsSettingsIcon");
		CommonWebActions.webExplicitWait("NewsSubscriptionsMessage", 180);
		CommonWebActions.webVerifyInnerText("NewsSubscriptionsMessage","MyLinksSettingMessage");
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("ShowMeDropdown")))
			ReportUtil.reporterEvent("pass", "Show Me dropdown is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Show Me dropdown is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		if(CommonWebActions.webVerifyInnerText("ShowMeDefault", "ShowMeDefault"))
			ReportUtil.reporterEvent("pass", "Everything is displayed as defaulty."+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Everything is not displayed as defaulty."+CommonWebActions.captureScreenshotAsBase64());
				
	}
	
	public static void notificationSettingsListBox(){
		CommonWebActions.webClick("CommunicationLink");
		CommonWebActions.webExplicitWait("NotificationsLink", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NotificationsLink")))
			ReportUtil.reporterEvent("pass", "Notifications link is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Notifications link is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		CommonWebActions.webClick("NotificationsLink");
		CommonWebActions.webExplicitWait("NotificationsHeader", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NotificationsHeader")))
			ReportUtil.reporterEvent("pass", "Notifications page is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Notifications page is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		CommonWebActions.webExplicitWait("NotificationSettingsIcon", 180);
		CommonWebActions.webClick("NotificationSettingsIcon");
		CommonWebActions.webExplicitWait("NotificationSettingsBreadcrumb", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NotificationSettingsBreadcrumb")))
			ReportUtil.reporterEvent("pass", "Notifications Settings page is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Notifications settings page is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NotifyMeWhen")))
			ReportUtil.reporterEvent("pass", "NotifyMeWhen column is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "NotifyMeWhen column is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("EmailNotificationNotifications")))
			ReportUtil.reporterEvent("pass", "EmailNotification column is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "EmailNotification column is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		List<WebElement> allNewsChannels = new ArrayList<WebElement>();
		allNewsChannels = CommonWebActions.getWebElements("Notifications");
		List<WebElement> allMBPCPortal = CommonWebActions.getWebElements("MBPCPortalNotifications");
		List<WebElement> allMBPCEmail = new ArrayList<WebElement>();
		allMBPCEmail = CommonWebActions.getWebElements("MBPCEmailNotifications");
		List<WebElement> allSmartPortal = new ArrayList<WebElement>();
		allSmartPortal = CommonWebActions.getWebElements("SmartPortalNotifications");
		List<WebElement> allSmartEmail = new ArrayList<WebElement>();
		allSmartEmail = CommonWebActions.getWebElements("SmartEmailNotifications");
		
		boolean checkFlagFalse1 = false,checkFlagFalse2 = false,checkFlagFalse3 = false,checkFlagFalse4 = false;
		for(WebElement ele : allMBPCPortal)
		{
			if(ele.getAttribute("type").equals("checkbox"))
				checkFlagFalse1 = true;
			else
				checkFlagFalse1 = false;
			
		}
		for(WebElement ele : allMBPCEmail)
		{
			if(ele.getAttribute("type").equals("checkbox"))
				checkFlagFalse2 = true;
			else
				checkFlagFalse2 = false;
			
		}
		for(WebElement ele : allSmartPortal)
		{
			if(ele.getAttribute("type").equals("checkbox"))
				checkFlagFalse3 = true;
			else
				checkFlagFalse3 = false;
			
		}
		for(WebElement ele : allSmartEmail)
		{
			if(ele.getAttribute("type").equals("checkbox"))
				checkFlagFalse4 = true;
			else
				checkFlagFalse4 = false;
			
		}
		List<String> allNewsChannelsString = new ArrayList<String>();
		for(int i=0;i<allNewsChannels.size();i++)
			allNewsChannelsString.add(allNewsChannels.get(i).getText());
		if(checkFlagFalse1==true&&checkFlagFalse2==true&&checkFlagFalse3==true&&checkFlagFalse4 == true)
			ReportUtil.reporterEvent("pass", "All Notificationd   are displayed with check box[MBPC Portal, MBPC Email, Smart Portal, Smart Email] "+allNewsChannelsString+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "All Notificationd   are not displayed with check box[MBPC Portal, MBPC Email, Smart Portal, Smart Email] "+allNewsChannelsString+CommonWebActions.captureScreenshotAsBase64());
	}
	
	public static void markAsReadForNotifications(){
		logOff();
		AUT.loginOnExistingBrowser("url_QA","UserName","Password");
		CommonWebActions.webClick("HomeLink");
		CommonWebActions.webExplicitWait("Dashboardbreadcrumb", 180);
		CommonWebActions.webClick("CommunicationLink");
		CommonWebActions.webExplicitWait("NotificationsLink", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NotificationsLink")))
			ReportUtil.reporterEvent("pass", "Notifications link is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Notifications link is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CommonWebActions.webClick("NotificationsLink");
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NotificationsHeader")))
			ReportUtil.reporterEvent("pass", "Notifications page is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Notifications page is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		String FirstUnreadName = CommonWebActions.getWebElementText("FirstUnreadNotificationName");
		
		boolean readFlag = false,FirstUnreadNotificationsFlag=false;
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("FirstUnreadNotifications"))){
			FirstUnreadNotificationsFlag =true;
		Actions actions = new Actions(CommonWebActions.wd);
		actions.moveToElement(CommonWebActions.getWebElement("FirstUnreadNotifications")).click().build().perform();
		CommonWebActions.webImplicitWait(CommonWebActions.wd, 5);
		List<WebElement> allreadNames = CommonWebActions.getWebElements("AllreadNotificationNames");
		
		for(WebElement ele : allreadNames){
			
		if(CommonWebActions.stringMatch(ele.getText().toString(), FirstUnreadName))
			{
				readFlag = true;
				break;
			}
		}
		}
		if(readFlag)
			ReportUtil.reporterEvent("pass", "["+FirstUnreadName+"]Marked as Read by denoting in visual difference"+CommonWebActions.captureScreenshotAsBase64());
		else if(!readFlag)
			ReportUtil.reporterEvent("fail", "Failed for Marked as Read by denoting in visual difference<br>Or<br>There is no Unread Notification items"+CommonWebActions.captureScreenshotAsBase64());
		
		 if(FirstUnreadNotificationsFlag)
			 ReportUtil.reporterEvent("info", "There is no Unread Notification items"+CommonWebActions.captureScreenshotAsBase64());
			
	}
	
	public static void notificationSettings(){
		CommonWebActions.webClick("BellIcon");
		CommonWebActions.webExplicitWait("BellIconDropdown", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("BellIconDropdown")))
			ReportUtil.reporterEvent("pass", "Dropdown view is displaying after click on Bell Icon"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Dropdown view is not displaying after click on Bell Icon"+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NotificationGearIcon")))
			ReportUtil.reporterEvent("pass", "Notification Gear Icon is displaying after click on Bell Icon"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Notification Gear Icon is not displaying after click on Bell Icon"+CommonWebActions.captureScreenshotAsBase64());
		CommonWebActions.webClick("NotificationGearIcon");
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NotificationSettingsBreadcrumb")))
			ReportUtil.reporterEvent("pass", "Notification Settings Page is displaying after click on Bell Icon"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Notification Settings Page is not displaying after click on Bell Icon"+CommonWebActions.captureScreenshotAsBase64());
		
	}
	
	public static void displayOfDealers(){
		CommonWebActions.webClick("DirectoryLink");
		CommonWebActions.webExplicitWait("USDealersLink", 180);
		CommonWebActions.webClick("USDealersLink");
		CommonWebActions.webExplicitWait("USDealersLinkBreadscrumb", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealersLinkBreadscrumb")))
			ReportUtil.reporterEvent("pass", "US Dealers Page is displaying after click on US Dealer"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying after click on US Dealer"+CommonWebActions.captureScreenshotAsBase64());
		List<WebElement> allPictures = CommonWebActions.getWebElements("USDealersPicture");
		boolean flag = false;
		//Validate picture is present or not
		for(WebElement pic : allPictures){
			if(pic.getTagName().equals("img"))
				flag = true;
			else
			{
				flag = false;
				ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying Dealership Picture"+CommonWebActions.captureScreenshotAsBase64());
				break;
			}
		}
		if(flag == true)
			ReportUtil.reporterEvent("pass", "US Dealers Page is displaying Dealership Picture"+CommonWebActions.captureScreenshotAsBase64());
		
		List<WebElement> allNames = CommonWebActions.getWebElements("USDealersNames");
		boolean flagName = false;
		// validate US Dealer name is present
		for(WebElement name : allNames){
			if(name.getText().length()>0)
				flagName = true;
			else
			{
				flagName = false;
				ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying Dealership Name"+CommonWebActions.captureScreenshotAsBase64());
				break;
			}
		}
		if(flagName == true)
			ReportUtil.reporterEvent("pass", "US Dealers Page is displaying Dealership Name"+CommonWebActions.captureScreenshotAsBase64());
		
		
		List<WebElement> allMapIcons = CommonWebActions.getWebElements("MapIcon");
		boolean flagMapIcon = false;
		//Validate Map Icon is present
		for(WebElement MapIcon : allMapIcons){
			if(MapIcon.isDisplayed())
				flagMapIcon = true;
			else
			{
				flagMapIcon = false;
				ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying Map Icon"+CommonWebActions.captureScreenshotAsBase64());
				break;
			}
		}
		if(flagMapIcon == true)
			ReportUtil.reporterEvent("pass", "US Dealers Page is displaying Map Icon"+CommonWebActions.captureScreenshotAsBase64());
		if(CommonWebActions.getWebElement("MapIconLink").getTagName().equals("a"))
			ReportUtil.reporterEvent("pass", "US Dealers Page is displaying Map Icon with linkable"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying Map Icon with linkable"+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.webVerifyInnerText("MapAddress","MapAddress"))
			ReportUtil.reporterEvent("pass", "US Dealers Page is displaying Address["+CommonWebActions.getWebElement("MapAddress").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying Address["+CommonWebActions.getWebElement("MapAddress").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
		if(CommonWebActions.getWebElement("MapAddress").getTagName().equals("a"))
			ReportUtil.reporterEvent("pass", "US Dealers Page is displaying Map Address with linkable"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying Map Address with linkable"+CommonWebActions.captureScreenshotAsBase64());
		List<WebElement> allPhones = CommonWebActions.getWebElements("Phonenumber");
		boolean flagPhone = false;
		//validate Phone number is present
		for(WebElement phone : allPhones){
			if(phone.getText().length()>0)
				flagPhone = true;
			else
			{
				flagPhone = false;
				ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying Phone Nunmer"+CommonWebActions.captureScreenshotAsBase64());
				break;
			}
		}
		if(flagPhone == true)
			ReportUtil.reporterEvent("pass", "US Dealers Page is displaying Phone Number"+CommonWebActions.captureScreenshotAsBase64());
		
		List<WebElement> allPhoneIcons = CommonWebActions.getWebElements("PhoneIcon");
		boolean flagPhoneIcon = false;
		//validate phone icon is present
		for(WebElement PhoneIcon : allPhoneIcons){
			if(PhoneIcon.isDisplayed())
				flagPhoneIcon = true;
			else
			{
				flagPhoneIcon = false;
				ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying Phone Icon"+CommonWebActions.captureScreenshotAsBase64());
				break;
			}
		}
		if(flagPhoneIcon == true)
			ReportUtil.reporterEvent("pass", "US Dealers Page is displaying Phone Icon"+CommonWebActions.captureScreenshotAsBase64());
		
	}
	
	public static void employeesListDealership(){
		AUT.logOff();
		AUT.loginOnExistingBrowser("url_QA", "UserName", "Password");
		CommonWebActions.webClick("HomeLink");
		CommonWebActions.webExplicitWait("Dashboardbreadcrumb", 180);
		CommonWebActions.webClick("DirectoryLink");
		CommonWebActions.webExplicitWait("PeopleLink", 180);
		CommonWebActions.webClick("PeopleLink");
		CommonWebActions.webExplicitWait("PeopleHeader", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleHeader")))
			ReportUtil.reporterEvent("pass", "People Page is displaying after click on People"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "People Page is not displaying after click on People"+CommonWebActions.captureScreenshotAsBase64());
		
		
		List<WebElement> allPeopleNames = CommonWebActions.getWebElements("PeopleName");
		List<String> peopleNmae = new ArrayList<String>();
		boolean flag = false;
		boolean flagExpandName=false;
		boolean flagExpandTitle=false;
		boolean flagExpandEmail=false;
		boolean flagExpandWork=false;
		boolean flagExpandMobile=false;
		boolean flagExpandOther=false;
		boolean flagExpandTwitter=false;
		boolean flagExpandRightMobile=false;
		boolean flagExpandFacebook=false;
		boolean flagExpandLinkedIn=false;
		boolean flagExpandRightEmail=false;
		//validate People name is present
		for(WebElement PeopleName : allPeopleNames){
			if(!PeopleName.getText().isEmpty()){
				flag = true;
				peopleNmae.add(PeopleName.getText());
			}
			else
			{
				flag = false;
				ReportUtil.reporterEvent("fail", "People Page is not displaying Name"+CommonWebActions.captureScreenshotAsBase64());
				break;
			}
		}
		if(flag == true)
			ReportUtil.reporterEvent("pass", "People Page is displaying Name"+peopleNmae+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleHeaderName")))
			ReportUtil.reporterEvent("pass", "People Page is displaying Header Name"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "People Page is not displaying Header Name"+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleHeaderTitle")))
			ReportUtil.reporterEvent("pass", "People Page is displaying Header Title"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "People Page is not displaying Header Title"+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleHeaderPhone")))
			ReportUtil.reporterEvent("pass", "People Page is displaying Header Phone"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "People Page is not displaying Header Phone"+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleHeaderEmail")))
			ReportUtil.reporterEvent("pass", "People Page is displaying Header Email"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "People Page is not displaying Header Email"+CommonWebActions.captureScreenshotAsBase64());
		
		
		/*for(WebElement PeopleName : allPeopleNames)
		{*/
			CommonWebActions.webClick(allPeopleNames.get(0));
			
			CommonWebActions.webExplicitWait("PeopleExpandName", 180);
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleExpandName"))){
				flagExpandName=true;
				ReportUtil.reporterEvent("pass", "People Page is displaying Person Profile Name["+CommonWebActions.getWebElement("PeopleExpandName").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
				}
			else{
				flagExpandName = false;
				ReportUtil.reporterEvent("fail", "People Page is not displaying Person Profile Name"+CommonWebActions.captureScreenshotAsBase64());
				
			}
			
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleExpandTitle"))){
				flagExpandTitle=true;
				ReportUtil.reporterEvent("pass", "People Page is displaying Person Profile Title["+CommonWebActions.getWebElement("PeopleExpandTitle").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
			}
			else{
				flagExpandTitle=false;
				ReportUtil.reporterEvent("info", "People Page is not displaying Person Profile Title"+CommonWebActions.captureScreenshotAsBase64());
				
			}
			
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleExpandEmail"))){
				flagExpandEmail=true;
				ReportUtil.reporterEvent("pass", "People Page is displaying Person Profile Email["+CommonWebActions.getWebElement("PeopleExpandEmail").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
			}
			else{
				flagExpandEmail = false;
				ReportUtil.reporterEvent("info", "People Page is not displaying Person Profile Email"+CommonWebActions.captureScreenshotAsBase64());
				
			}
			
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleExpandWork"))){
				flagExpandWork=true;
				ReportUtil.reporterEvent("pass", "People Page is displaying Person Profile Work["+CommonWebActions.getWebElement("PeopleExpandWork").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
				
				
			}
			else{
				flagExpandWork = false;
				ReportUtil.reporterEvent("info", "People Page is not displaying Person Profile Work"+CommonWebActions.captureScreenshotAsBase64());
				
			}
			
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleExpandMobile"))){
				flagExpandMobile=true;
				ReportUtil.reporterEvent("pass", "People Page is displaying Person Profile Mobile["+CommonWebActions.getWebElement("PeopleExpandMobile").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
				
			}
			else{
				flagExpandMobile = false;
				ReportUtil.reporterEvent("info", "People Page is not displaying Person Profile Mobile"+CommonWebActions.captureScreenshotAsBase64());
				
			}
System.out.println("size: "+CommonWebActions.getWebElements("PeopleExpandNoRight").size());
			if(!CommonWebActions.getWebElements("PeopleExpandNoRight").isEmpty()){
				if(CommonWebActions.getWebElements("PeopleExpandNoRight").size()>=1){
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleExpandOther"))){
				flagExpandOther=true;

				ReportUtil.reporterEvent("pass", "People Page is displaying Person Profile Other["+CommonWebActions.getWebElement("PeopleExpandOther").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
			
			}
			else{
				flagExpandOther = false;
				ReportUtil.reporterEvent("info", "People Page is not displaying Person Profile Other"+CommonWebActions.captureScreenshotAsBase64());
				
			}
				}
				if(CommonWebActions.getWebElements("PeopleExpandNoRight").size()>=2){
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleExpandTwitter"))){
				flagExpandTwitter=true;

				ReportUtil.reporterEvent("pass", "People Page is displaying Person Profile Twitter["+CommonWebActions.getWebElement("PeopleExpandTwitter").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
			
				
			}
			else{
				flagExpandTwitter = false;
				ReportUtil.reporterEvent("info", "People Page is not displaying Person Profile Twitter"+CommonWebActions.captureScreenshotAsBase64());
				
			}
				}
				if(CommonWebActions.getWebElements("PeopleExpandNoRight").size()>=3){
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleExpandRightMobile"))){
				flagExpandRightMobile=true;
				ReportUtil.reporterEvent("pass", "People Page is displaying Person Profile Mobile["+CommonWebActions.getWebElement("PeopleExpandRightMobile").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
				
				
			}
			else{
				flagExpandRightMobile = false;
				ReportUtil.reporterEvent("info", "People Page is not displaying Person Profile Mobile"+CommonWebActions.captureScreenshotAsBase64());
				
			}
				}
				if(CommonWebActions.getWebElements("PeopleExpandNoRight").size()>=4){
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleExpandFacebook"))){
				flagExpandFacebook=true;
				ReportUtil.reporterEvent("pass", "People Page is displaying Person Profile Facebook["+CommonWebActions.getWebElement("PeopleExpandFacebook").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
				
				
			}
			else{
				flagExpandFacebook = false;
				ReportUtil.reporterEvent("info", "People Page is not displaying Person Profile Facebook"+CommonWebActions.captureScreenshotAsBase64());
				
			}
				}
				if(CommonWebActions.getWebElements("PeopleExpandNoRight").size()>=5){
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleExpandLinkedIn"))){
				flagExpandLinkedIn=true;
				ReportUtil.reporterEvent("pass", "People Page is displaying Person Profile LinkedIn["+CommonWebActions.getWebElement("PeopleExpandLinkedIn").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
				
				
				}
			else{
				flagExpandLinkedIn = false;
				ReportUtil.reporterEvent("info", "People Page is not displaying Person Profile LinkedIn"+CommonWebActions.captureScreenshotAsBase64());
				
			}
				}
				if(CommonWebActions.getWebElements("PeopleExpandNoRight").size()>=6){
					System.out.println("Yes");
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleExpandRightEmail"))){
				flagExpandRightEmail=true;
				ReportUtil.reporterEvent("pass", "People Page is displaying Person Profile Email["+CommonWebActions.getWebElement("PeopleExpandRightEmail").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
			}
			else{
				flagExpandRightEmail = false;
				ReportUtil.reporterEvent("info", "People Page is not displaying Person Profile Email"+CommonWebActions.captureScreenshotAsBase64());
				
			}
				}
			}
		//}
		
		
			
	}
	
	public static void validateEmployeedDetails(){
		employeesListDealership();
		CommonWebActions.webClick("UserName");
		CommonWebActions.webExplicitWait("DealershipAt2ndPosition", 180);
		String a = CommonWebActions.getWebElementText("DealershipAt2ndPosition");
		CommonWebActions.webClick("DealershipAt2ndPosition");
		do{
			
		}while(CommonWebActions.getWebElementText("DealearshipName").equals(a));
		employeesListDealership();
	}
	
	public static void displayOfUSDealersPage(){
		CommonWebActions.webClick("DirectoryLink");
		CommonWebActions.webExplicitWait("USDealersLink", 180);
		CommonWebActions.webClick("USDealersLink");
		CommonWebActions.webExplicitWait("USDealersLinkBreadscrumb", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealersLinkBreadscrumb")))
			ReportUtil.reporterEvent("pass", "US Dealers Page is displaying after click on US Dealer"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying after click on US Dealer"+CommonWebActions.captureScreenshotAsBase64());
		List<WebElement> allNames = CommonWebActions.getWebElements("USDealersNames");
		boolean flagName = false;
		//validate Dealership name is present
		for(WebElement name : allNames){
			if(name.getText().length()>0)
				flagName = true;
			else
			{
				flagName = false;
				ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying Dealership Name"+CommonWebActions.captureScreenshotAsBase64());
				break;
			}
		}
		if(flagName == true)
			ReportUtil.reporterEvent("pass", "US Dealers Page is displaying Dealership Name"+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.getWebElement("ViewAll").getCssValue("color").equals("rgba(255, 255, 255, 1)"))
			ReportUtil.reporterEvent("pass", "View All is highlighted by default"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "View All is not highlighted by default"+CommonWebActions.captureScreenshotAsBase64());
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("FilterBy")))
			ReportUtil.reporterEvent("pass", "FilterBy option is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "FilterBy option is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("OrByState")))
			ReportUtil.reporterEvent("pass", "OrByState option is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "OrByState option is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("SearchLocation")))
			ReportUtil.reporterEvent("pass", "SearchLocation option is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "SearchLocation option is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		if(CommonWebActions.getWebElement("ShowingDealers") != null)
			ReportUtil.reporterEvent("pass", "Displayed as["+CommonWebActions.getWebElement("ShowingDealers").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Showing Dealer count is not displayed"+CommonWebActions.captureScreenshotAsBase64());
	}
	
	public static void DisplayofDealershipProfilePage(){
		try{
		CommonWebActions.webClick("DirectoryLink");
		CommonWebActions.webExplicitWait("USDealersLink", 180);
		CommonWebActions.webClick("USDealersLink");
		CommonWebActions.webExplicitWait("USDealersLinkBreadscrumb", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealersLinkBreadscrumb")))
			ReportUtil.reporterEvent("pass", "US Dealers Page is displaying after click on US Dealer"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying after click on US Dealer"+CommonWebActions.captureScreenshotAsBase64());
		List<WebElement> allNames = CommonWebActions.getWebElements("USDealersNames");
		boolean flagName = false;
		//validate dealership name is present
		for(WebElement name : allNames){
			if(name.getText().length()>0)
				flagName = true;
			else
			{
				flagName = false;
				ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying Dealership Name"+CommonWebActions.captureScreenshotAsBase64());
				break;
			}
		}
		if(flagName == true)
			ReportUtil.reporterEvent("pass", "US Dealers Page is displaying Dealership Name"+CommonWebActions.captureScreenshotAsBase64());
		
		String USDealerName = CommonWebActions.getWebElementText("FirstUSDealerName");
		Thread.sleep(10000);
		CommonWebActions.webClick("FirstUSDealerName");
		CommonWebActions.webExplicitWait("USDealerHeaderName", 180);
		if(CommonWebActions.getWebElementText("USDealerHeaderName").equals(USDealerName))
			ReportUtil.reporterEvent("pass", "Page Header is displayed US Dealer name as ["+USDealerName+"]"+CommonWebActions.captureScreenshotAsBase64());
		else 
			ReportUtil.reporterEvent("fail", "Page Header is not displayed with US Dealer name "+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealerImage")))
			ReportUtil.reporterEvent("pass", "US Dealer page is displayed with Image"+CommonWebActions.captureScreenshotAsBase64());
		else 
			ReportUtil.reporterEvent("fail", "US Dealer page is not displayed with Image "+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealerName")))
			ReportUtil.reporterEvent("pass", "US Dealer page is displayed with Name ["+CommonWebActions.getWebElement("USDealerName").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
		else 
			ReportUtil.reporterEvent("fail", "US Dealer page is not displayed with Name "+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealerEmail")))
			ReportUtil.reporterEvent("pass", "US Dealer page is displayed with Email ["+CommonWebActions.getWebElement("USDealerEmail").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
		else 
			ReportUtil.reporterEvent("fail", "US Dealer page is not displayed with Email "+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealerWeb")))
			ReportUtil.reporterEvent("pass", "US Dealer page is displayed with Web ["+CommonWebActions.getWebElement("USDealerWeb").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
		else 
			ReportUtil.reporterEvent("fail", "US Dealer page is not displayed with Web "+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealerPhone")))
			ReportUtil.reporterEvent("pass", "US Dealer page is displayed with Phone ["+CommonWebActions.getWebElement("USDealerPhone").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
		else 
			ReportUtil.reporterEvent("fail", "US Dealer page is not displayed with Phone "+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealerAddress")))
			ReportUtil.reporterEvent("pass", "US Dealer page is displayed with Address ["+CommonWebActions.getWebElement("USDealerAddress").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
		else 
			ReportUtil.reporterEvent("fail", "US Dealer page is not displayed with Address "+CommonWebActions.captureScreenshotAsBase64());
		Thread.sleep(10000);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealerMap")))
			ReportUtil.reporterEvent("pass", "US Dealer page is displayed with Map "+CommonWebActions.captureScreenshotAsBase64());
		else 
			ReportUtil.reporterEvent("fail", "US Dealer page is not displayed with Map "+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDEalerNotes")))
			ReportUtil.reporterEvent("pass", "US Dealer page is displayed with Notes "+CommonWebActions.captureScreenshotAsBase64());
		else 
			ReportUtil.reporterEvent("fail", "US Dealer page is not displayed with Notes "+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealerSave")))
			ReportUtil.reporterEvent("pass", "US Dealer page is displayed with Save button "+CommonWebActions.captureScreenshotAsBase64());
		else 
			ReportUtil.reporterEvent("fail", "US Dealer page is not displayed with Save button "+CommonWebActions.captureScreenshotAsBase64());
		
		CommonWebActions.webClick("USDealerHeaderLink");
				
		CommonWebActions.webExplicitWait("USDealersLinkBreadscrumb", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealersLinkBreadscrumb")))
			ReportUtil.reporterEvent("pass", "US Dealers Page is displaying after click on US Dealer"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying after click on US Dealer"+CommonWebActions.captureScreenshotAsBase64());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//ReportUtil.reporterEvent("fatal", "Menu Link click failure" + CommonWebActions.captureScreenshotAsBase64());
		}
	}
	
	public static void dealershipRoster(){
		try{
			logOff();
			AUT.loginOnExistingBrowser("url_QA","UserName","Password");
			CommonWebActions.webClick("HomeLink");
			CommonWebActions.webExplicitWait("Dashboardbreadcrumb", 180);
			CommonWebActions.webClick("DirectoryLink");
			CommonWebActions.webExplicitWait("USDealersLink", 180);
			CommonWebActions.webClick("USDealersLink");
			CommonWebActions.webExplicitWait("USDealersLinkBreadscrumb", 180);
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealersLinkBreadscrumb")))
				ReportUtil.reporterEvent("pass", "US Dealers Page is displaying after click on US Dealer"+CommonWebActions.captureScreenshotAsBase64());
			else
				ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying after click on US Dealer"+CommonWebActions.captureScreenshotAsBase64());
			CommonWebActions.webSet("USDealerSearch", "DealerName");
			Thread.sleep(5000);
			String USDealerName = CommonWebActions.getWebElementText("FirstUSDealerName");
			CommonWebActions.webClick("FirstUSDealerName");
			CommonWebActions.webExplicitWait("USDealerHeaderName", 180);
			if(CommonWebActions.getWebElementText("USDealerHeaderName").equals(USDealerName))
				ReportUtil.reporterEvent("pass", "Page Header is displayed US Dealer name as ["+USDealerName+"]"+CommonWebActions.captureScreenshotAsBase64());
			else 
				ReportUtil.reporterEvent("fail", "Page Header is not displayed with US Dealer name "+CommonWebActions.captureScreenshotAsBase64());
			
		
			List<WebElement> allEmployeesNames = CommonWebActions.getWebElements("USDealersEmployeeNames");
			boolean flagName = false;
			//validate Dealership name is present
			for(WebElement name : allEmployeesNames){
				if(name.getText().length()>0)
					flagName = true;
				else
				{
					flagName = false;
					ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying Dealership Name"+CommonWebActions.captureScreenshotAsBase64());
					break;
				}
			}
			if(flagName == true)
				ReportUtil.reporterEvent("pass", "US Dealers Page is displaying Dealership Name"+CommonWebActions.captureScreenshotAsBase64());
			
			if(CommonWebActions.webVerifyInnerText("DealershipRosterHeader", "RosterHeader"))
				ReportUtil.reporterEvent("pass", "US Dealers Page is displaying Roster Header Name as [Employees]"+CommonWebActions.captureScreenshotAsBase64());
			else
				ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying Roster Header Name"+CommonWebActions.captureScreenshotAsBase64());
			
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealerEmpName")))
				ReportUtil.reporterEvent("pass", "US Dealers Page is displaying Employee Name as["+CommonWebActions.getWebElement("USDealerEmpName").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
			else
				ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying Employee Name"+CommonWebActions.captureScreenshotAsBase64());
			
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealerEmpTitle")))
				ReportUtil.reporterEvent("pass", "US Dealers Page is displaying Employee Title as["+CommonWebActions.getWebElement("USDealerEmpTitle").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
			else
				ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying Employee Title"+CommonWebActions.captureScreenshotAsBase64());
			
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealerEmpPhone")))
				ReportUtil.reporterEvent("pass", "US Dealers Page is displaying Employee Phone as["+CommonWebActions.getWebElement("USDealerEmpPhone").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
			else
				ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying Employee Phone"+CommonWebActions.captureScreenshotAsBase64());
			
			if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealerEmpEmail")))
				ReportUtil.reporterEvent("pass", "US Dealers Page is displaying Employee Email as["+CommonWebActions.getWebElement("USDealerEmpEmail").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
			else
				ReportUtil.reporterEvent("fail", "US Dealers Page is not displaying Employee Email"+CommonWebActions.captureScreenshotAsBase64());
			
			boolean flagExpandName=false;
			boolean flagExpandTitle=false;
			boolean flagExpandEmail=false;
			boolean flagExpandWork=false;
			boolean flagExpandMobile=false;
			boolean flagExpandOther=false;
			boolean flagExpandTwitter=false;
			boolean flagExpandRightMobile=false;
			boolean flagExpandFacebook=false;
			boolean flagExpandLinkedIn=false;
			boolean flagExpandRightEmail=false;
			
			List<WebElement> allPeopleNames = CommonWebActions.getWebElements("USDealersEmployeeNames");
			//for(WebElement PeopleName : allPeopleNames){
						CommonWebActions.webClick(allPeopleNames.get(0));
						
						CommonWebActions.webExplicitWait("EmpPeopleExpandName", 180);
						if(CommonWebActions.webExists(CommonWebActions.getWebElement("EmpPeopleExpandName"))){
							flagExpandName=true;
							ReportUtil.reporterEvent("pass", "People Page is displaying Person Profile Name["+CommonWebActions.getWebElement("EmpPeopleExpandName").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
							}
						else{
							flagExpandName = false;
							ReportUtil.reporterEvent("fail", "People Page is not displaying Person Profile Name"+CommonWebActions.captureScreenshotAsBase64());
							
						}
						
	
					//}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//ReportUtil.reporterEvent("fatal", "Menu Link click failure" + CommonWebActions.captureScreenshotAsBase64());
		}
		
	}
	
	public static void validateReportDownload(){
		
		try{
		List<WebElement> allReports = new ArrayList<WebElement>();
		allReports = CommonWebActions.getWebElements("OpenReportIcon");
		CommonWebActions.webClick(allReports.get(0));
		Thread.sleep(10000);
		Set<String> handles = new HashSet<String>();
		handles = CommonWebActions.wd.getWindowHandles();
		List<String> handlesList = new ArrayList<String>(handles);
		CommonWebActions.wd.switchTo().window(handlesList.get(1));
		CommonWebActions.wd.close();
		CommonWebActions.wd.switchTo().window(handlesList.get(0));
		
		ReportUtil.reporterEvent("pass", "User allowed to open the attachment" + CommonWebActions.captureScreenshotAsBase64());
		}
	
	catch(Exception e)
	{
		e.printStackTrace();
		//ReportUtil.reporterEvent("fatal", "Menu Link click failure" + CommonWebActions.captureScreenshotAsBase64());
	}
}
	
	public static void displayOfArticlesMBNews(){try{
		AUT.logOff();
		AUT.loginOnExistingBrowser("url_QA", "UserName", "Password");
		CommonWebActions.webClick("HomeLink");
		CommonWebActions.webExplicitWait("Dashboardbreadcrumb", 180);
		CommonWebActions.webClick("CommunicationLink");
		CommonWebActions.webExplicitWait("NewsLink", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NewsLink")))
			ReportUtil.reporterEvent("pass", "News link is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "News link is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		CommonWebActions.webClick("NewsLink");
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NewsLinkBreadscrumb")))
			ReportUtil.reporterEvent("pass", "News link page is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "News link page is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		if(!(CommonWebActions.getWebElements("AllMyNewsItems").isEmpty()))
			ReportUtil.reporterEvent("pass", "My News or subscription items are displayed in widget. "+CommonWebActions.captureScreenshotAsBase64());
		else if(CommonWebActions.getWebElements("AllMyNewsItems").isEmpty())
			ReportUtil.reporterEvent("fail", "My News or subscription items are empty."+CommonWebActions.captureScreenshotAsBase64());
		
		List<WebElement> allNewsTimes = new ArrayList<WebElement>();
		allNewsTimes = CommonWebActions.getWebElements("NewsRecentTime");
boolean flag=true;
		List<String> daysFormat = new ArrayList<String>();
		List<String> allTimes = new ArrayList<String>();
		List<String> dateFormat = new ArrayList<String>();
		int daysCount=0,dateFormatCount=0;
		//validate time format
		for(WebElement s:allNewsTimes){
			
			if(s.getText().toString().contains("a day ago")){
				flag = true;
				daysCount = daysCount + 1;
				daysFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else if(s.getText().toString().contains("hours ago")){
				flag = true;
				daysCount++;
				daysFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else if(s.getText().toString().contains("days ago")){
				
				flag = true;
				daysCount++;
				daysFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else if(s.getText().toString().contains(",")){
				flag = true;
				dateFormatCount = dateFormatCount + 1;
				dateFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else
				flag=false;
			
			
		}
		System.out.println(allTimes.size());
		System.out.println(daysFormat.size());
		System.out.println(dateFormat.size());
		System.out.println(daysCount);
		System.out.println(dateFormatCount);
		boolean flagOrderday = false,flagOrderDate=false,daysFormatOrderFlag=false;
		//validate days ago format
		for(int i=0;i<daysFormat.size();i++){
			if(CommonWebActions.stringMatchesToRegExp(allTimes.get(i).substring(2),"([1-9]|[1-9][0-9]) days ago"))
				flagOrderday=true;
			else
				flagOrderday=false;
		}
		List<String> daysFormatOrder = new ArrayList<String>();
		daysFormatOrder = daysFormat;
		Collections.sort(daysFormat);
		//validate days format are in sort
		if(daysFormatOrder == daysFormat)
			daysFormatOrderFlag=true;
		else
			daysFormatOrderFlag=false;
		//validate Month format
		for(int i=daysFormat.size();i<dateFormat.size();i++){
			if(CommonWebActions.stringMatchesToRegExp(allTimes.get(i).substring(2),"[(January|February|March|April|May|June|July|September|August|October|November|December)]*\\s([1-9]|[1-9][1-9])\\,\\s([1-2]([0]|[9])[0-9][0-9])"))
				flagOrderDate=true;
			else
				flagOrderDate=false;
		}
		
		
		
		
		
		
		
		if(flagOrderday && flagOrderDate && daysFormatOrderFlag)
			ReportUtil.reporterEvent("pass", "Items are displayed in reverse order - as most recent first and oldest in last "+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Items are not displayed in reverse order - as most recent first and oldest in last  "+CommonWebActions.captureScreenshotAsBase64());
		
		Actions action = new Actions(CommonWebActions.wd);
		action.moveToElement(CommonWebActions.getWebElement("FirstNewsAttachment")).build().perform();
		
			Thread.sleep(10000);
		
			ReportUtil.reporterEvent("pass", "Attachment url is displayed when mouse over on Attachment icon"+CommonWebActions.captureScreenshotAsBase64());
		
		CommonWebActions.scrollIntoWebElement_new("LastNewsItems");
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("LastNewsItems")))
			ReportUtil.reporterEvent("pass", "Remaining Links shown in when scroll"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Remaining Links not shown in when scroll"+CommonWebActions.captureScreenshotAsBase64());
		
		if(!(CommonWebActions.getWebElements("AllMyNewsItems").isEmpty()))
			ReportUtil.reporterEvent("pass", "My News or subscription items are displayed in with title. "+CommonWebActions.captureScreenshotAsBase64());
		else if(CommonWebActions.getWebElements("AllMyNewsItems").isEmpty())
			ReportUtil.reporterEvent("fail", "My News or subscription items are not displayed in with title."+CommonWebActions.captureScreenshotAsBase64());
		
		if(!(CommonWebActions.getWebElements("NewsDescription").isEmpty()))
			ReportUtil.reporterEvent("pass", "My News or subscription items are displayed in with description. "+CommonWebActions.captureScreenshotAsBase64());
		else if(CommonWebActions.getWebElements("NewsDescription").isEmpty())
			ReportUtil.reporterEvent("fail", "My News or subscription items are not displayed in with description."+CommonWebActions.captureScreenshotAsBase64());
		
		if(!(CommonWebActions.getWebElements("NewsRecentTime").isEmpty()))
			ReportUtil.reporterEvent("pass", "My News or subscription items are displayed in with delivery date. "+CommonWebActions.captureScreenshotAsBase64());
		else if(CommonWebActions.getWebElements("NewsRecentTime").isEmpty())
			ReportUtil.reporterEvent("fail", "My News or subscription items are not displayed in with delivery date."+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.getWebElements("MyNewsUnreadItems").isEmpty())
			ReportUtil.reporterEvent("info", "There is no UnRead items");
		else
			ReportUtil.reporterEvent("pass", "My News or subscription items are displayed in with Unread items. "+CommonWebActions.captureScreenshotAsBase64());
		
		if(!(CommonWebActions.getWebElements("NewsFavoriteIcon").isEmpty()))
			ReportUtil.reporterEvent("pass", "My News or subscription items are displayed in with Favorite icon. "+CommonWebActions.captureScreenshotAsBase64());
		else if(CommonWebActions.getWebElements("NewsFavoriteIcon").isEmpty())
			ReportUtil.reporterEvent("fail", "My News or subscription items are not displayed in with Favorite icon."+CommonWebActions.captureScreenshotAsBase64());
		
		if(!(CommonWebActions.getWebElements("NewsAttachment").isEmpty()))
			ReportUtil.reporterEvent("pass", "My News or subscription items are displayed in with Attached files. "+CommonWebActions.captureScreenshotAsBase64());
		else if(CommonWebActions.getWebElements("NewsAttachment").isEmpty())
			ReportUtil.reporterEvent("fail", "My News or subscription items are not displayed in with Attached files."+CommonWebActions.captureScreenshotAsBase64());
		
		boolean flagOrderday1 = false,flagOrderDate1=false;
		for(int i=0;i<daysFormat.size();i++){
			
			
			String format = daysFormat.get(i);
			
			System.out.println(format);
			System.out.println(format.substring(2));
			//System.out.println(a[1]);
			if(CommonWebActions.stringMatchesToRegExp(daysFormat.get(i).substring(2),"([1-9]|[1-9][0-9]) days ago"))
				flagOrderday1=true;
			else
				flagOrderday1=false;
		}
		for(int i=daysFormat.size();i<dateFormat.size();i++){
			if(CommonWebActions.stringMatchesToRegExp(dateFormat.get(i).substring(2),"[(January|February|March|April|May|June|July|September|August|October|November|December)]*\\s([1-9]|[1-9][1-9])\\,\\s([1-2]([0]|[9])[0-9][0-9])"))
				flagOrderDate1=true;
			else
				flagOrderDate1=false;
		}
		
		if(flagOrderday1 && flagOrderDate1)
			ReportUtil.reporterEvent("pass", "Until 2 weeks it is mentioned as days ago and if it exceeds 2 weeks then it is mentioned as the delevered date "+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", " Until 2 weeks it is not mentioned as days ago and if it exceeds 2 weeks then it is not mentioned as the delevered date "+CommonWebActions.captureScreenshotAsBase64());
	}
		catch(Exception e)
		{
			e.printStackTrace();
			//ReportUtil.reporterEvent("fatal", "Menu Link click failure" + CommonWebActions.captureScreenshotAsBase64());
		}
		
	}
	
	public static void displayOfArticlesMynews(){try{
		CommonWebActions.webClick("CommunicationLink");
		CommonWebActions.webExplicitWait("NewsLink", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NewsLink")))
			ReportUtil.reporterEvent("pass", "News link is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "News link is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		CommonWebActions.webClick("NewsLink");
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NewsLinkBreadscrumb")))
			ReportUtil.reporterEvent("pass", "News link page is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "News link page is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		CommonWebActions.webClick("MyNewsLink");
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NewsLinkBreadscrumb")))
			ReportUtil.reporterEvent("pass", "News link page is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "News link page is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		
		if(!(CommonWebActions.getWebElements("AllMyNewsItems").isEmpty()))
			ReportUtil.reporterEvent("pass", "My News or subscription items are displayed in widget. "+CommonWebActions.captureScreenshotAsBase64());
		else if(CommonWebActions.getWebElements("AllMyNewsItems").isEmpty())
			ReportUtil.reporterEvent("fail", "My News or subscription items are empty."+CommonWebActions.captureScreenshotAsBase64());
		
		List<WebElement> allNewsTimes = new ArrayList<WebElement>();
		allNewsTimes = CommonWebActions.getWebElements("NewsRecentTime");
boolean flag=true;
		List<String> daysFormat = new ArrayList<String>();
		List<String> allTimes = new ArrayList<String>();
		List<String> dateFormat = new ArrayList<String>();
		int daysCount=0,dateFormatCount=0;
		//validate time format
		for(WebElement s:allNewsTimes){
			
			if(s.getText().toString().contains("a day ago")){
				flag = true;
				daysCount = daysCount + 1;
				daysFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else if(s.getText().toString().contains("hours ago")){
				flag = true;
				daysCount++;
				daysFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else if(s.getText().toString().contains("days ago")){
				
				flag = true;
				daysCount++;
				daysFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else if(s.getText().toString().contains(",")){
				flag = true;
				dateFormatCount = dateFormatCount + 1;
				dateFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else
				flag=false;
			
			
		}
		System.out.println(allTimes.size());
		System.out.println(daysFormat.size());
		System.out.println(dateFormat.size());
		System.out.println(daysCount);
		System.out.println(dateFormatCount);
		boolean flagOrderday = false,flagOrderDate=false,daysFormatOrderFlag=false;
		//validate days format
		for(int i=0;i<daysFormat.size();i++){
			if(CommonWebActions.stringMatchesToRegExp(allTimes.get(i).substring(2),"([1-9]|[1-9][0-9]) days ago"))
				flagOrderday=true;
			else
				flagOrderday=false;
		}
		List<String> daysFormatOrder = new ArrayList<String>();
		daysFormatOrder = daysFormat;
		Collections.sort(daysFormat);
		if(daysFormatOrder == daysFormat)
			daysFormatOrderFlag=true;
		else
			daysFormatOrderFlag=false;
		//validate month format
		for(int i=daysFormat.size();i<dateFormat.size();i++){
			if(CommonWebActions.stringMatchesToRegExp(allTimes.get(i).substring(2),"[(January|February|March|April|May|June|July|September|August|October|November|December)]*\\s([1-9]|[1-9][1-9])\\,\\s([1-2]([0]|[9])[0-9][0-9])"))
				flagOrderDate=true;
			else
				flagOrderDate=false;
		}
		
		
		
		
		
		
		
		if(flagOrderday && flagOrderDate && daysFormatOrderFlag)
			ReportUtil.reporterEvent("pass", "Items are displayed in reverse order - as most recent first and oldest in last "+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Items are not displayed in reverse order - as most recent first and oldest in last  "+CommonWebActions.captureScreenshotAsBase64());
		
		Actions action = new Actions(CommonWebActions.wd);
		action.moveToElement(CommonWebActions.getWebElement("FirstNewsAttachment")).build().perform();
		
			Thread.sleep(10000);
		
			ReportUtil.reporterEvent("pass", "Attachment url is displayed when mouse over on Attachment icon"+CommonWebActions.captureScreenshotAsBase64());
		
		CommonWebActions.scrollIntoWebElement_new("LastNewsItems");
		Thread.sleep(5000);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("LastNewsItems")))
			ReportUtil.reporterEvent("pass", "Remaining Links shown in when scroll"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("pass", "Remaining Links shown in when scroll"+CommonWebActions.captureScreenshotAsBase64());
		
		if(!(CommonWebActions.getWebElements("AllMyNewsItems").isEmpty()))
			ReportUtil.reporterEvent("pass", "My News or subscription items are displayed in with title. "+CommonWebActions.captureScreenshotAsBase64());
		else if(CommonWebActions.getWebElements("AllMyNewsItems").isEmpty())
			ReportUtil.reporterEvent("fail", "My News or subscription items are not displayed in with title."+CommonWebActions.captureScreenshotAsBase64());
		
		if(!(CommonWebActions.getWebElements("NewsDescription").isEmpty()))
			ReportUtil.reporterEvent("pass", "My News or subscription items are displayed in with description. "+CommonWebActions.captureScreenshotAsBase64());
		else if(CommonWebActions.getWebElements("NewsDescription").isEmpty())
			ReportUtil.reporterEvent("fail", "My News or subscription items are not displayed in with description."+CommonWebActions.captureScreenshotAsBase64());
		
		if(!(CommonWebActions.getWebElements("NewsRecentTime").isEmpty()))
			ReportUtil.reporterEvent("pass", "My News or subscription items are displayed in with delivery date. "+CommonWebActions.captureScreenshotAsBase64());
		else if(CommonWebActions.getWebElements("NewsRecentTime").isEmpty())
			ReportUtil.reporterEvent("fail", "My News or subscription items are not displayed in with delivery date."+CommonWebActions.captureScreenshotAsBase64());
		
		if(CommonWebActions.getWebElements("MyNewsUnreadItems").isEmpty())
			ReportUtil.reporterEvent("info", "There is no UnRead items");
		else
			ReportUtil.reporterEvent("pass", "My News or subscription items are displayed in with Unread items. "+CommonWebActions.captureScreenshotAsBase64());
		
		if(!(CommonWebActions.getWebElements("NewsFavoriteIcon").isEmpty()))
			ReportUtil.reporterEvent("pass", "My News or subscription items are displayed in with Favorite icon. "+CommonWebActions.captureScreenshotAsBase64());
		else if(CommonWebActions.getWebElements("NewsFavoriteIcon").isEmpty())
			ReportUtil.reporterEvent("fail", "My News or subscription items are not displayed in with Favorite icon."+CommonWebActions.captureScreenshotAsBase64());
		
		if(!(CommonWebActions.getWebElements("NewsAttachment").isEmpty()))
			ReportUtil.reporterEvent("pass", "My News or subscription items are displayed in with Attached files. "+CommonWebActions.captureScreenshotAsBase64());
		else if(CommonWebActions.getWebElements("NewsAttachment").isEmpty())
			ReportUtil.reporterEvent("fail", "My News or subscription items are not displayed in with Attached files."+CommonWebActions.captureScreenshotAsBase64());
		
		boolean flagOrderday1 = false,flagOrderDate1=false;
		for(int i=0;i<daysFormat.size();i++){
			
			
			String format = daysFormat.get(i);
			
			System.out.println(format);
			System.out.println(format.substring(2));
			//System.out.println(a[1]);
			if(CommonWebActions.stringMatchesToRegExp(daysFormat.get(i).substring(2),"([1-9]|[1-9][0-9]) days ago"))
				flagOrderday1=true;
			else
				flagOrderday1=false;
		}
		for(int i=daysFormat.size();i<dateFormat.size();i++){
			if(CommonWebActions.stringMatchesToRegExp(dateFormat.get(i).substring(2),"[(January|February|March|April|May|June|July|September|August|October|November|December)]*\\s([1-9]|[1-9][1-9])\\,\\s([1-2]([0]|[9])[0-9][0-9])"))
				flagOrderDate1=true;
			else
				flagOrderDate1=false;
		}
		
		if(flagOrderday1 && flagOrderDate1)
			ReportUtil.reporterEvent("pass", "Until 2 weeks it is mentioned as days ago and if it exceeds 2 weeks then it is mentioned as the delevered date "+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", " Until 2 weeks it is not mentioned as days ago and if it exceeds 2 weeks then it is not mentioned as the delevered date "+CommonWebActions.captureScreenshotAsBase64());
	}
		catch(Exception e)
		{
			e.printStackTrace();
			//ReportUtil.reporterEvent("fatal", "Menu Link click failure" + CommonWebActions.captureScreenshotAsBase64());
		}
	}
	
	public static void displayOfNotifications(){
		logOff();
		AUT.loginOnExistingBrowser("url_QA","UserName","Password");
		CommonWebActions.webClick("CommunicationLink");
		CommonWebActions.webExplicitWait("NotificationsLink", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NotificationsLink")))
			ReportUtil.reporterEvent("pass", "Notifications link is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Notifications link is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		CommonWebActions.webClick("NotificationsLink");
		CommonWebActions.webExplicitWait("NotificationsHeader", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NotificationsHeader")))
			ReportUtil.reporterEvent("pass", "Notifications page is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Notifications page is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		List<WebElement> allreadNames = CommonWebActions.getWebElements("AllreadNotificationNames");
		
		if(!allreadNames.isEmpty())
			ReportUtil.reporterEvent("pass", "Notifications page is displayed with Notifications"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Notifications page is not displayed with Notifications"+CommonWebActions.captureScreenshotAsBase64());
		
		
		List<WebElement> AllreadNotificationNames = new ArrayList<WebElement>();
		AllreadNotificationNames = CommonWebActions.getWebElements("NewsRecentNotificationTime");
boolean flag=true;
		List<String> daysFormat = new ArrayList<String>();
		List<String> allTimes = new ArrayList<String>();
		List<String> dateFormat = new ArrayList<String>();
		int daysCount=0,dateFormatCount=0;
		//validate time format
		for(WebElement s:AllreadNotificationNames){
			
			if(s.getText().toString().contains("a day ago")){
				flag = true;
				daysCount = daysCount + 1;
				daysFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else if(s.getText().toString().contains("hours ago")){
				flag = true;
				daysCount++;
				daysFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else if(s.getText().toString().contains("days ago")){
				
				flag = true;
				daysCount++;
				daysFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else if(s.getText().toString().contains(",")){
				flag = true;
				dateFormatCount = dateFormatCount + 1;
				dateFormat.add(s.getText().toString());
				allTimes.add(s.getText().toString());
			}
			else
				flag=false;
			
			
		}
		System.out.println(allTimes.size());
		System.out.println(daysFormat.size());
		System.out.println(dateFormat.size());
		System.out.println(daysCount);
		System.out.println(dateFormatCount);
		boolean flagOrderday = false,flagOrderDate=false,daysFormatOrderFlag=false;
		if(!daysFormat.isEmpty()){
		//validate days format
			for(int i=0;i<daysFormat.size();i++){
			if(CommonWebActions.stringMatchesToRegExp(allTimes.get(i).substring(2),"([1-9]|[1-9][0-9]) days ago"))
				flagOrderday=true;
			else
				flagOrderday=false;
		}
		}
		else
			flagOrderday=true;
		List<String> daysFormatOrder = new ArrayList<String>();
		daysFormatOrder = daysFormat;
		Collections.sort(daysFormat);
		if(daysFormatOrder == daysFormat)
			daysFormatOrderFlag=true;
		else
			daysFormatOrderFlag=false;
		//validate month format
		for(int i=daysFormat.size();i<dateFormat.size();i++){
			if(CommonWebActions.stringMatchesToRegExp(allTimes.get(i).substring(2),"[(January|February|March|April|May|June|July|September|August|October|November|December)]*\\s([1-9]|[1-9][1-9])\\,\\s([1-2]([0]|[9])[0-9][0-9])"))
				flagOrderDate=true;
			else
				flagOrderDate=false;
		}
		
		
		
		
		
		
		
		if(flagOrderday && flagOrderDate && daysFormatOrderFlag)
			ReportUtil.reporterEvent("pass", "Items are displayed in reverse order - as most recent first and oldest in last "+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Items are not displayed in reverse order - as most recent first and oldest in last  "+CommonWebActions.captureScreenshotAsBase64());
		
		

		
		boolean flagOrderday1 = false,flagOrderDate1=false;
		if(!daysFormat.isEmpty()){
		for(int i=0;i<daysFormat.size();i++){
			
			
			String format = daysFormat.get(i);
			
			System.out.println(format);
			System.out.println(format.substring(2));
			//System.out.println(a[1]);
			if(CommonWebActions.stringMatchesToRegExp(daysFormat.get(i).substring(2),"([1-9]|[1-9][0-9]) days ago"))
				flagOrderday1=true;
			else
				flagOrderday1=false;
		}
		}
		else
			flagOrderday1=true;
		for(int i=daysFormat.size();i<dateFormat.size();i++){
			if(CommonWebActions.stringMatchesToRegExp(dateFormat.get(i).substring(2),"[(January|February|March|April|May|June|July|September|August|October|November|December)]*\\s([1-9]|[1-9][1-9])\\,\\s([1-2]([0]|[9])[0-9][0-9])"))
				flagOrderDate1=true;
			else
				flagOrderDate1=false;
		}
		
		if(flagOrderday1 && flagOrderDate1)
			ReportUtil.reporterEvent("pass", "Until 2 weeks it is mentioned as days ago and if it exceeds 2 weeks then it is mentioned as the delevered date "+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", " Until 2 weeks it is not mentioned as days ago and if it exceeds 2 weeks then it is not mentioned as the delevered date "+CommonWebActions.captureScreenshotAsBase64());
		
		if(!(CommonWebActions.getWebElements("AllreadNotificationNames").isEmpty()))
			ReportUtil.reporterEvent("pass", "Notifications page is displayed in with title. "+CommonWebActions.captureScreenshotAsBase64());
		else if(CommonWebActions.getWebElements("AllreadNotificationNames").isEmpty())
			ReportUtil.reporterEvent("fail", "Notifications page is not displayed in with title."+CommonWebActions.captureScreenshotAsBase64());
		
		if(!(CommonWebActions.getWebElements("NewsRecentNotificationTime").isEmpty()))
			ReportUtil.reporterEvent("pass", "Notifications page is displayed in with timestamp. "+CommonWebActions.captureScreenshotAsBase64());
		else if(CommonWebActions.getWebElements("NewsRecentNotificationTime").isEmpty())
			ReportUtil.reporterEvent("fail", "Notifications page is not displayed in with timestamp."+CommonWebActions.captureScreenshotAsBase64());
		
		if(!(CommonWebActions.getWebElements("TimeStampNotification").isEmpty()))
			ReportUtil.reporterEvent("pass", "Notifications page is displayed in with Description. "+CommonWebActions.captureScreenshotAsBase64());
		else if(CommonWebActions.getWebElements("TimeStampNotification").isEmpty())
			ReportUtil.reporterEvent("fail", "Notifications page is not displayed in with Description."+CommonWebActions.captureScreenshotAsBase64());
		
		if(!(CommonWebActions.getWebElements("FirstUnreadNotificationName").isEmpty()))
			ReportUtil.reporterEvent("pass", "Notifications page is displayed in with Unread with link. "+CommonWebActions.captureScreenshotAsBase64());
		else if(CommonWebActions.getWebElements("FirstUnreadNotificationName").isEmpty())
			ReportUtil.reporterEvent("fail", "Notifications page is not displayed in with Unread with link."+CommonWebActions.captureScreenshotAsBase64());
		
CommonWebActions.scrollIntoWebElement_new("LastNotification");
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("LastNotification")))
			ReportUtil.reporterEvent("pass", "Remaining Links shown in when scroll"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Remaining Links not shown in when scroll"+CommonWebActions.captureScreenshotAsBase64());
		
		
	}
	
	public static void displayOfTimeBucketGroup(){
		CommonWebActions.webClick("CommunicationLink");
		CommonWebActions.webExplicitWait("NotificationsLink", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NotificationsLink")))
			ReportUtil.reporterEvent("pass", "Notifications link is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Notifications link is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		CommonWebActions.webClick("NotificationsLink");
		CommonWebActions.webExplicitWait("NotificationsHeader", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NotificationsHeader")))
			ReportUtil.reporterEvent("pass", "Notifications page is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Notifications page is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		List<WebElement> allreadNames = CommonWebActions.getWebElements("AllreadNotificationNames");
		
		if(!allreadNames.isEmpty())
			ReportUtil.reporterEvent("pass", "Notifications page is displayed with Notifications"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Notifications page is not displayed with Notifications"+CommonWebActions.captureScreenshotAsBase64());
		
		boolean flagLastMonth = false,flagThisMonth = false;
		List<String> callenderMonth = new ArrayList<String>();
		
		callenderMonth.add("January");
		callenderMonth.add("February");
		callenderMonth.add("March");
		callenderMonth.add("April");
		callenderMonth.add("May");
		callenderMonth.add("June");
		callenderMonth.add("July");
		callenderMonth.add("August");
		callenderMonth.add("September");
		callenderMonth.add("October");
		callenderMonth.add("November");
		callenderMonth.add("December");
		
		List<WebElement> AllTimesLastMonth = new ArrayList<WebElement>();
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("TimeBucketsLastMonth"))){
			AllTimesLastMonth = CommonWebActions.getWebElements("TimesLastMonth");
			
		//validate Last month bucket
		
		for(int i=0; i<AllTimesLastMonth.size();i++)
		{
			String value = AllTimesLastMonth.get(i).getText().substring(2);
			for(int j=0;j<callenderMonth.size();j++){
				if(value.contains(callenderMonth.get(j)))
				{
					Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
					int month = calendar.get(Calendar.MONTH) + 1;
					
					if((month-2) == j)
						flagLastMonth = true;
					else
						flagLastMonth = false;
					
				}
			}
		}
		}
		
		else
			ReportUtil.reporterEvent("info", "No Notifications are present in Last Month frame"+CommonWebActions.captureScreenshotAsBase64());
			
		if(flagLastMonth)
			ReportUtil.reporterEvent("pass", "Notifications are present in Last Month frame"+CommonWebActions.captureScreenshotAsBase64());
		else if(!flagLastMonth)
			ReportUtil.reporterEvent("info", "No Notifications are present in Last Month frame"+CommonWebActions.captureScreenshotAsBase64());
		
		
		List<WebElement> AllTimesThisMonth = new ArrayList<WebElement>();
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("TimeBucketsThisMonth"))){
			AllTimesLastMonth = CommonWebActions.getWebElements("TimesThisMonth");
			
		
		//validate this month bucket
		for(int i=0; i<AllTimesThisMonth.size();i++)
		{
			String value = AllTimesThisMonth.get(i).getText().substring(2);
			for(int j=0;j<callenderMonth.size();j++){
				if(value.contains(callenderMonth.get(j)))
				{
					Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
					int month = calendar.get(Calendar.MONTH) + 1;
					
					if((month-1) == j)
						flagThisMonth = true;
					else
						flagThisMonth = false;
					
				}
			}
		}
		}
		
			
		if(flagThisMonth)
			ReportUtil.reporterEvent("pass", "Notifications are present in This Month frame"+CommonWebActions.captureScreenshotAsBase64());
		else if(!flagThisMonth)
			ReportUtil.reporterEvent("info", "No Notifications are present in This Month frame"+CommonWebActions.captureScreenshotAsBase64());
		
		boolean flagToday = false;
List<WebElement> AllTimesToday = new ArrayList<WebElement>();
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("TimeBucketsToday"))){
			AllTimesToday = CommonWebActions.getWebElements("TimesThisMonth");
			
		
		//validate today bucket
		for(int i=0; i<AllTimesToday.size();i++)
		{
			String value = AllTimesToday.get(i).getText().substring(2);
			
				Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
				int day = calendar.get(Calendar.DATE);
				if(value.contains(String.valueOf(day)))
								
						flagToday = true;
					else
						flagToday = false;
							
			
		}
		}
		
			
		if(flagToday)
			ReportUtil.reporterEvent("pass", "Notifications are present in Today frame"+CommonWebActions.captureScreenshotAsBase64());
		else if(!flagToday)
			ReportUtil.reporterEvent("info", "No Notifications are present in Today frame"+CommonWebActions.captureScreenshotAsBase64());
		
		
		boolean flagThisWeek = false;
		List<WebElement> AllTimesThisWeek = new ArrayList<WebElement>();
				
				if(CommonWebActions.webExists(CommonWebActions.getWebElement("TimeBucketsThisWeek"))){
					AllTimesThisWeek = CommonWebActions.getWebElements("TimesThisWeek");
					
				
				//validate this week time bucket
				for(int i=0; i<AllTimesThisWeek.size();i++)
				{
					String value = AllTimesThisWeek.get(i).getText().substring(2);
					
						Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
						int day = calendar.get(Calendar.DATE);
						int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
						int dayOfWeekAdd = 7 - dayOfWeek;
						int startday = day + (dayOfWeek-1);
						int endday = day + dayOfWeekAdd;
					
						if(value.contains(String.valueOf(startday<=day && day<=endday)))
										
							flagThisWeek = true;
							else
								flagThisWeek = false;
									
					
				}
				}
				
					
				if(flagThisWeek)
					ReportUtil.reporterEvent("pass", "Notifications are present in This Week frame"+CommonWebActions.captureScreenshotAsBase64());
				else if(!flagThisWeek)
					ReportUtil.reporterEvent("info", "No Notifications are present in This Week frame"+CommonWebActions.captureScreenshotAsBase64());
		
		boolean flagOldMonthThisYear=false,flagOldMonthlessThisYear=false;
				List<WebElement> AllTimesOlder = new ArrayList<WebElement>();
				if(CommonWebActions.webExists(CommonWebActions.getWebElement("TimeBucketsOlder"))){
					AllTimesLastMonth = CommonWebActions.getWebElements("TimesOlder");
					
				
				//validate older time bucket
				for(int i=0; i<AllTimesOlder.size();i++)
				{
					String value = AllTimesOlder.get(i).getText().substring(2);
					for(int j=0;j<callenderMonth.size();j++){
						Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
						int year = calendar.get(Calendar.YEAR);
						if(value.contains(String.valueOf(year)))
						{
						if(value.contains(callenderMonth.get(j)))
						{
							
							int month = calendar.get(Calendar.MONTH) + 1;
							
							if(j<(month-2))
								flagOldMonthThisYear = true;
							else
								flagOldMonthThisYear = false;
							
						}
						}
						else if(!value.contains(String.valueOf(year))){
							flagOldMonthlessThisYear = true;
						}
					}
				}
				}
				
				else
					ReportUtil.reporterEvent("info", "No Notifications are present in Older frame"+CommonWebActions.captureScreenshotAsBase64());
					
				if(flagOldMonthThisYear || flagOldMonthlessThisYear)
					ReportUtil.reporterEvent("pass", "Notifications are present in Older frame"+CommonWebActions.captureScreenshotAsBase64());
				else if(!flagLastMonth || !flagOldMonthlessThisYear)
					ReportUtil.reporterEvent("info", "No Notifications are present in Older frame"+CommonWebActions.captureScreenshotAsBase64());
				
				
		/*Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
	      //getTime() returns the current date in default time zone
	      Date date = calendar.getTime();
	      int day = calendar.get(Calendar.DATE);
	      //Note: +1 the month for current month
	      int month = calendar.get(Calendar.MONTH) + 1;
	      int year = calendar.get(Calendar.YEAR);
	      int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	      
	      System.out.println("Current Date is: " + date);
	      System.out.println("Current Day is:: " + day);
	      System.out.println("Current Month is:: " + month);
	      System.out.println("Current Year is: " + year);
	      System.out.println("Current Day of Week is: " + dayOfWeek);*/
	}
	
	public static void criticalCodeWidget(){
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("CriticalCodeHeader"))){
			ReportUtil.reporterEvent("info", "Critical code widget is present"+CommonWebActions.captureScreenshotAsBase64());
		}
		else{
			ReportUtil.reporterEvent("info", "Critical code widget is not present"+CommonWebActions.captureScreenshotAsBase64());
		}
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("CriticalCodeClass"))){
			ReportUtil.reporterEvent("info", "Critical code widget is present"+CommonWebActions.captureScreenshotAsBase64());
		}
		else{
			ReportUtil.reporterEvent("info", "Critical code widget is not present"+CommonWebActions.captureScreenshotAsBase64());
		}
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("CriticalCodeSeries"))){
			ReportUtil.reporterEvent("info", "Critical code widget is present"+CommonWebActions.captureScreenshotAsBase64());
		}
		else{
			ReportUtil.reporterEvent("info", "Critical code widget is not present"+CommonWebActions.captureScreenshotAsBase64());
		}
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("CriticalCodeOptcode"))){
			ReportUtil.reporterEvent("info", "Critical code widget is present"+CommonWebActions.captureScreenshotAsBase64());
		}
		else{
			ReportUtil.reporterEvent("info", "Critical code widget is not present"+CommonWebActions.captureScreenshotAsBase64());
		}
	}
	
	public static void displayOfScorecard(){
		
		
		if(CommonWebActions.webVerifyInnerText("Scorecard1", "Scorecard1")){
			ReportUtil.reporterEvent("info", "Scorecard CARS is present"+CommonWebActions.captureScreenshotAsBase64());
		}
		else{
			ReportUtil.reporterEvent("info", "Scorecard CARS is not present"+CommonWebActions.captureScreenshotAsBase64());
		}
		
		if(CommonWebActions.webVerifyInnerText("Scorecard2", "Scorecard2")){
			ReportUtil.reporterEvent("info", "Scorecard VANS is present"+CommonWebActions.captureScreenshotAsBase64());
		}
		else{
			ReportUtil.reporterEvent("info", "Scorecard VANS is not present"+CommonWebActions.captureScreenshotAsBase64());
		}
		
		if(CommonWebActions.webVerifyInnerText("Scorecard3", "Scorecard3")){
			ReportUtil.reporterEvent("info", "Scorecard smart is present"+CommonWebActions.captureScreenshotAsBase64());
		}
		else{
			ReportUtil.reporterEvent("info", "Scorecard smart is not present"+CommonWebActions.captureScreenshotAsBase64());
		}
		}
	
	public static void switchdealers() throws InterruptedException{
		changeDealership("DealershipAt2ndPosition");
		displayOfScorecard();
	}
	
	public static void dealershipEmpsAvailability(){
		CommonWebActions.webExplicitWait("AdministrationLink", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("AdministrationLink"))){
			ReportUtil.reporterEvent("pass", "Administration link is present"+CommonWebActions.captureScreenshotAsBase64());
		}
		else{
			ReportUtil.reporterEvent("fail", "Administration link is not present"+CommonWebActions.captureScreenshotAsBase64());
		}
		CommonWebActions.webClick("AdministrationLink");
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("AdministrationHeader")))
			ReportUtil.reporterEvent("pass", "Administration page is displayed"+CommonWebActions.captureScreenshotAsBase64());
	
	else{
		ReportUtil.reporterEvent("fail", "Administration page is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		}
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("AllDealrshipEmp")))
			ReportUtil.reporterEvent("pass", "All Dealership Employees page is displayed"+CommonWebActions.captureScreenshotAsBase64());
	
	else{
		ReportUtil.reporterEvent("fail", "All Dealership Employees page is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		}
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("Dealershipname")))
			ReportUtil.reporterEvent("pass", "Dealership Name is displayed"+CommonWebActions.captureScreenshotAsBase64());
	
	else{
		ReportUtil.reporterEvent("fail", "Dealership Name is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		}
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("DealershipCDID")))
			ReportUtil.reporterEvent("pass", "Dealership CDID is  displayed"+CommonWebActions.captureScreenshotAsBase64());
	
	else{
		ReportUtil.reporterEvent("fail", "Dealership CDID is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		}
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("DealershipMBName")))
			ReportUtil.reporterEvent("pass", "Dealership MB Name is displayed"+CommonWebActions.captureScreenshotAsBase64());
	
	else{
		ReportUtil.reporterEvent("fail", "Dealership MB Name is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		}
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("DealershipMBID")))
			ReportUtil.reporterEvent("pass", "Dealership MBID is  displayed"+CommonWebActions.captureScreenshotAsBase64());
	
	else{
		ReportUtil.reporterEvent("fail", "Dealership MBID is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		}
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("EmpFromLeft")))
			ReportUtil.reporterEvent("pass", "Employee From Left button is  displayed"+CommonWebActions.captureScreenshotAsBase64());
	
	else{
		ReportUtil.reporterEvent("fail", "Employee From Left button is  not displayed"+CommonWebActions.captureScreenshotAsBase64());
		}
	}	
	
	public static void CheckLogo(){
		
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("NetstarLogo")))
			ReportUtil.reporterEvent("pass", "NetStar logo is displayed"+CommonWebActions.captureScreenshotAsBase64());
	
	else{
		ReportUtil.reporterEvent("fail", "Netstar logo is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		}
		
	}
	
	public static void verifyIconsRightCorner(){
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("ProfilePicture")))
			ReportUtil.reporterEvent("pass", "ProfilePicture is displayed"+CommonWebActions.captureScreenshotAsBase64());
	
	else{
		ReportUtil.reporterEvent("fail", "ProfilePicture is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		}
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("ProfileLink")))
			ReportUtil.reporterEvent("pass", "ProfileLink is displayed"+CommonWebActions.captureScreenshotAsBase64());
	
	else{
		ReportUtil.reporterEvent("fail", "ProfileLink is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		}
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("BellIcon")))
			ReportUtil.reporterEvent("pass", "BellIcon is displayed"+CommonWebActions.captureScreenshotAsBase64());
	
	else{
		ReportUtil.reporterEvent("fail", "BellIcon is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		}
	}
	
	public static void profilePicture() throws InterruptedException{
		if(CommonWebActions.webVerifyInnerText("ProfileUserName", "UserName2"))
			ReportUtil.reporterEvent("pass", "Profile User name is displayed"+CommonWebActions.captureScreenshotAsBase64());
		
		else{
			ReportUtil.reporterEvent("fail", "Profile User name is not displayed"+CommonWebActions.captureScreenshotAsBase64());
			}
		Thread.sleep(5000);
		CommonWebActions.webExplicitWait("ProfileUserName", 180);
		CommonWebActions.webClick("ProfileUserName");
		CommonWebActions.webExplicitWait("ProfileUserDropdown", 180);
		Thread.sleep(5000);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("ProfileUserDropdown")))
ReportUtil.reporterEvent("pass", "User various dealership is visible with home dealer ship on the top of the dropdown"+CommonWebActions.captureScreenshotAsBase64());
		
		else{
			ReportUtil.reporterEvent("fail", "User various dealership is not visible with home dealer ship on the top of the dropdown"+CommonWebActions.captureScreenshotAsBase64());
			}
		CommonWebActions.webClick("ProfileUserNamesecond");
	}
	
	public static void LinksIcon(){
		CommonWebActions.webClick("Link");
		CommonWebActions.webExplicitWait("LinkPopUp", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("LinkPopUp")))
			ReportUtil.reporterEvent("pass", "Drop down opens up and links added to this user is displayed."+CommonWebActions.captureScreenshotAsBase64());
					
					else{
						ReportUtil.reporterEvent("fail", "Drop down opens up and links added to this user is not displayed."+CommonWebActions.captureScreenshotAsBase64());
						}
		CommonWebActions.webClick("Link");
	}
	
	public static void notificationsicon(){
		CommonWebActions.webClick("BellIcon");
		CommonWebActions.webExplicitWait("Notificationpopup", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("Notificationpopup")))
			ReportUtil.reporterEvent("pass", "Drop down opens up and notifications are displayed ."+CommonWebActions.captureScreenshotAsBase64());
					
					else{
						ReportUtil.reporterEvent("fail", "Drop down opens not up and notifications are not displayed ."+CommonWebActions.captureScreenshotAsBase64());
						}
		CommonWebActions.webClick("BellIconsecond");
	}
	
	public static void SearchDealerSwitch() throws InterruptedException{
		CommonWebActions.webClick("ProfileUserName");
		CommonWebActions.webExplicitWait("DealerSearch", 180);
		CommonWebActions.webSet("DealerSearch", "DealershipName");
		Thread.sleep(5000);
		if(CommonWebActions.webVerifyInnerText("DealerSearchText", "DealershipName"))
			ReportUtil.reporterEvent("pass", "Search results should be displayed based on the dealer name ."+CommonWebActions.captureScreenshotAsBase64());
		
		else{
			ReportUtil.reporterEvent("fail", "Search results not displayed based on the dealer name ."+CommonWebActions.captureScreenshotAsBase64());
			}
		CommonWebActions.webClick("ProfileUserNamesecond");
	}
	
	
	public static void validateSearchBox() throws InterruptedException{
		CommonWebActions.webClick("DirectoryLink");
		CommonWebActions.webExplicitWait("USDealersLink", 180);
		CommonWebActions.webClick("USDealersLink");
		CommonWebActions.webExplicitWait("USDealersLinkBreadscrumb", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealersLinkBreadscrumb")))
ReportUtil.reporterEvent("pass", "Search field is displayed in US Dealers page"+CommonWebActions.captureScreenshotAsBase64());
		
		else{
			ReportUtil.reporterEvent("fail", "Search field is not displayed in US Dealers page."+CommonWebActions.captureScreenshotAsBase64());
			}
		if(CommonWebActions.getWebElement("USDealerSearchPlaceholder").getAttribute("placeholder").equals("Search locations"))
ReportUtil.reporterEvent("pass", "Search field is displayed with detault as Search Locations"+CommonWebActions.captureScreenshotAsBase64());
		
		else{
			ReportUtil.reporterEvent("fail", "Search field is not displayed with detault as Search Locations"+CommonWebActions.captureScreenshotAsBase64());
			}
		CommonWebActions.webSet("DealerSearch", "USDealerSearchName");
		Thread.sleep(5000);
		if(CommonWebActions.webVerifyInnerText("DealerAfterSearchName", "USDealerSearchName"))
ReportUtil.reporterEvent("pass", "Search name field is displayed"+CommonWebActions.captureScreenshotAsBase64());
		
		else{
			ReportUtil.reporterEvent("fail", "Search name field is not "+CommonWebActions.captureScreenshotAsBase64());
			}
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealerUnreadcount")))
ReportUtil.reporterEvent("pass", "US Dealer Unread count is displayed["+CommonWebActions.getWebElement("USDealerUnreadcount").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
		
		else{
			ReportUtil.reporterEvent("fail", "Search name field is not "+CommonWebActions.captureScreenshotAsBase64());
			}
	}
	
	public static void FavoriteIcon() throws InterruptedException{
		logOff();
		AUT.loginOnExistingBrowser("url_QA","UserName","Password");
		CommonWebActions.webClick("HomeLink");
		CommonWebActions.webExplicitWait("Dashboardbreadcrumb", 180);
		CommonWebActions.webClick("DirectoryLink");
		CommonWebActions.webExplicitWait("USDealersLink", 180);
		CommonWebActions.webClick("USDealersLink");
		CommonWebActions.webExplicitWait("USDealersLinkBreadscrumb", 180);
		String USDealerNonFavoriteName = CommonWebActions.getWebElementText("USDealerFirstNonFavoriteName");
		CommonWebActions.webClick("USDealerFirstNonFavorite");
		CommonWebActions.webExplicitWait("USDealerSaveMessage", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("USDealerSaveMessage")))
ReportUtil.reporterEvent("pass", "US Dealer page is displayed["+CommonWebActions.getWebElement("USDealerSaveMessage").getText()+"]"+CommonWebActions.captureScreenshotAsBase64());
		
		else{
			ReportUtil.reporterEvent("fail", "Saved message is not displayed"+CommonWebActions.captureScreenshotAsBase64());
			}
		CommonWebActions.webClick("USDealerSaveClose");
		CommonWebActions.webClick("USDealerFavoritetab");
		CommonWebActions.webExplicitWait("DealerSearch", 180);
		CommonWebActions.webSetText("DealerSearch", USDealerNonFavoriteName);
		Thread.sleep(5000);
		
ReportUtil.reporterEvent("pass", "US Dearler Favorite item is displayed"+CommonWebActions.captureScreenshotAsBase64());
		
		
	}
	
	public static void clickOwnEmpName() throws InterruptedException{
		logOff();
		AUT.loginOnExistingBrowser("url_QA","UserName","Password");
		CommonWebActions.webClick("HomeLink");
		CommonWebActions.webExplicitWait("Dashboardbreadcrumb", 180);
		CommonWebActions.webExplicitWait("Dashboardbreadcrumb", 180);
		CommonWebActions.webClick("DirectoryLink");
		CommonWebActions.webExplicitWait("PeopleLink", 180);
		CommonWebActions.webClick("PeopleLink");
		CommonWebActions.webExplicitWait("PeopleHeader", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("PeopleHeader")))
			ReportUtil.reporterEvent("pass", "People Page is displaying after click on People"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "People Page is not displaying after click on People"+CommonWebActions.captureScreenshotAsBase64());
		CommonWebActions.webClick("peopleownempname");
		CommonWebActions.webExplicitWait("peopleownempnameedit", 180);
		if(CommonWebActions.webExists(CommonWebActions.getWebElement("peopleownempnameedit")))
			ReportUtil.reporterEvent("pass", "Edit button is displayed"+CommonWebActions.captureScreenshotAsBase64());
		else
			ReportUtil.reporterEvent("fail", "Edit button is not displayed"+CommonWebActions.captureScreenshotAsBase64());
		Thread.sleep(5000);
		CommonWebActions.webClick("peopleownempnameedit");
		Thread.sleep(5000);
		CommonWebActions.webExplicitWait("UserEditProfile", 180);
		CommonWebActions.webClick("UserEditProfile");
		
		Set<String> handles = new HashSet<String>();
		handles = CommonWebActions.wd.getWindowHandles();
		List<String> handlesList = new ArrayList<String>(handles);
		CommonWebActions.wd.switchTo().window(handlesList.get(1));
		CommonWebActions.webClick("MyData");
		CommonWebActions.webClick("ModifyMyData");
		CommonWebActions.webSet("UserMobile", "UserMobile");
		CommonWebActions.webSet("UserTelephone", "UserTelephone");
		CommonWebActions.webClick("SaveModifications");
		CommonWebActions.wd.close();
		CommonWebActions.wd.switchTo().window(handlesList.get(0));
	}
}