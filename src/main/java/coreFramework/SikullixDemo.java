package coreFramework;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;

public class SikullixDemo {
	
	static Screen applicationScreen = new Screen();
	static Pattern controlImage = null;
	static String currDir = System.getProperty("user.dir");
	static String imageRepoLocation = "\\src\\test\\resources\\testArtifacts\\imageRepo\\";

	public static void main(String[] args) throws FindFailed {
//		winClick("windowsLogo");
		controlImage = new Pattern("C:\\99 Training\\workspaceluna\\SeFramework\\src\\test\\resources\\testArtifacts\\imageRepo\\firefoxLaunch.PNG");
		applicationScreen.click(controlImage);
	}
	
	
	/**
	 * This method is used to click on windows controls or anything displayed on the GUI
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  imageLocationInRepository This is the logical name of the of the location of the image in the Image Repo
	 */
	public static void winClick(String imageLocationInRepository){
		try{
			String actualImageLocation = ORUtil.getImageLocationValue(imageLocationInRepository);
			checkImageValueExistsForKey(actualImageLocation);
			actualImageLocation = currDir + imageRepoLocation + actualImageLocation + ".PNG";
//			Thread.sleep(2000);
//			applicationScreen.click("C:\\99 Training\\workspaceluna\\SeFramework\\src\\test\\resources\\testArtifacts\\imageRepo\\imageWindowsLogo.PNG");
//			CommonWebActions.msgbox(actualImageLocation);
			controlImage = new Pattern(actualImageLocation);
			applicationScreen.click(controlImage);
			CommonWebActions.msgbox("click done");
			ReportUtil.reporterEvent("info", "Clicked on [ " + imageLocationInRepository + " ]");
		}catch(Exception e){
			ReportUtil.reporterEvent("fail", "Image/Control [ " + imageLocationInRepository + " ] NOT FOUND on UI" + CommonWebActions.captureScreenshotAsBase64());
		}
	}

	
	/**
	 * This method is used to enter text on windows text boxes or any text boxes
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  imageLocationInRepository This is the logical name of the of the location of the image in the Image Repo
	 * @param  textToEnter This is the text to be entered
	 */
	public static void winSet(String imageLocationInRepository, String textToEnter){
		try{
			String actualImageLocation = ORUtil.getImageLocationValue(imageLocationInRepository);
			checkImageValueExistsForKey(actualImageLocation);
			actualImageLocation = currDir + actualImageLocation;
			controlImage = new Pattern(actualImageLocation);
			applicationScreen.type(textToEnter);
			ReportUtil.reporterEvent("info", "Text [ " + textToEnter + " ] entered in the web element [ " + imageLocationInRepository + " ]" );
		}catch(Exception e){
			ReportUtil.reporterEvent("fail", "Text box [ " + imageLocationInRepository + " ] NOT FOUND on UI" + CommonWebActions.captureScreenshotAsBase64());
		}
	}
	
	/**
	 * This method is used to check if the image name is present in the image repository
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  imageKeyValue This is the logical name of the image in the repository
	 */
	public static void checkImageValueExistsForKey(String imageKeyValue){
		if (imageKeyValue.equals("IMAGELOCATIONNOTFOUND")) {
			ReportUtil.reporterEvent("fail","Image's logical name [ " + imageKeyValue + " ] not found in Image Repository. Please check...");
			Assert.assertEquals(imageKeyValue.equals("IMAGELOCATIONNOTFOUND"), false);
		}		
	}


}
