package coreFramework;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class consists of methods to connect to a property file
 * 
 * @author  Nagaraj Jayagondra
 * @version 1.0
 * @since   10-Dec-2015 
 */
public class ORUtil {

	static Properties ObjectRepo = null;
	static Properties ConfigFile = null;
	static Properties ImageRepo = null;
	static Properties HelpNSupport = null;
	
	static String strObjectRepoFilePath = "\\src\\test\\resources\\testArtifacts\\ObjectRepository.properties";
	static String strConfigFilePath = "\\src\\test\\resources\\testArtifacts\\Config.properties";
	static String strImageRepoFilePath = "\\src\\test\\resources\\testArtifacts\\ImageLocationRepository.properties";
	static String strHelpNSupportPath = "\\src\\test\\resources\\testData\\HelpAndSupportData.properties";
	
	static String strObjectRepoFilePath_Mymdfs = "\\src\\test\\resources\\testArtifacts\\Mymdfs\\ObjectRepository.properties";
	
	/**
	 * This method is used to connect to a property file
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  strFilePath This is the file path
	 */
	public static Properties ConnectToFile(String strFilePath) throws IOException {
		String currDir = System.getProperty("user.dir");
		String path = currDir + strFilePath;
		FileInputStream fis = new FileInputStream(path);
		Properties prop = new Properties();
		prop.load(fis);
		return prop;		
	}

	/**
	 * This method is used to get value from the property file
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  key This is the key name (logical name)
	 * @return String This returns the value from property file
	 */
	public static String getValue(String key) throws IOException{
		ObjectRepo = ConnectToFile(strObjectRepoFilePath);
		String objectPropertyVal = ObjectRepo.getProperty(key,"ELEMENTNOTFOUND");
		return objectPropertyVal;	
	}

	/**
	 * This method is used to set value from the property file
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  key This is the key name (logical name)
	 * @param valueToSet This is the value to be set in the property file
	 */
	public static void setValue(String key, String valueToSet) throws IOException{
		ObjectRepo = ConnectToFile(strObjectRepoFilePath);
		ObjectRepo.setProperty(key, valueToSet);
	}

	/**
	 * This method is used to get value from the property file
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  key This is the key name
	 * @return String This returns the value from property file
	 */
	public static String getConfigValue(String key) throws IOException{
		ConfigFile = ConnectToFile(strConfigFilePath);
		String configValue = ConfigFile.getProperty(key);
		return configValue;	
	}

	/**
	 * This method is used to set value from the property file
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  key This is the key name
	 * @param valueToSet This is the value to be set in the property file
	 */
	public static void setConfigValue(String key, String valueToSet) throws IOException{
		ConfigFile = ConnectToFile(strConfigFilePath);
		ConfigFile.setProperty(key, valueToSet);
	}
	
	/**
	 * This method is used to get value from the Image Location Repository property file
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  key This is the key name
	 * @return String This returns the value from property file
	 */
	public static String getImageLocationValue(String key) throws IOException{
		ConfigFile = ConnectToFile(strImageRepoFilePath);
		String configValue = ConfigFile.getProperty(key,"IMAGELOCATIONNOTFOUND");
		return configValue;	
	}
	
	/**
	 * @author Preetam Gupta
	 * @param key logical name of the items e.g. Name1, Contact1, Name2 etc.
	 * @return Value of the logical name present in the property file
	 * @throws IOException
	 */
	public static String getHelpNSupportValue(String key) throws IOException{
		HelpNSupport = ConnectToFile(strHelpNSupportPath);
		String helpNSupportValue = HelpNSupport.getProperty(key);
		return helpNSupportValue;	
	}
}
