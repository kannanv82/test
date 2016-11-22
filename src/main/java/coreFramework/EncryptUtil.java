package coreFramework;

import org.apache.commons.codec.binary.Base64;

/**
* This class consists of methods to encrypt and decrypt strings
* 
* @author  Nagaraj Jayagondra
* @version 1.0
* @since   11-Dec-2015 
*/
public class EncryptUtil {

	/**
	 * This method is used to encrypt a string
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  stringToBeEncrypted This is the string which needs to be encrypted
	 */
	public static String encryption(String stringToBeEncrypted){
		byte[]   bytesEncoded = Base64.encodeBase64(stringToBeEncrypted.getBytes());
		String encodedString = new String(bytesEncoded);
//		System.out.println("Encoded value is [ " + encodedString + " ]");
		return encodedString;
	}
	
	
	/**
	 * This method is used to decrypt a encrypted string
	 * 
	 * @author Nagaraj Jayagondra 
	 * @param  stringToBeDecrypted This is the string which needs to be decrypted
	 */
	public static String decryption(String stringToBeDecrypted){
		byte[] valueDecoded= Base64.decodeBase64(stringToBeDecrypted);
		String decodedString = new String(valueDecoded);
//		System.out.println("Decoded value is [ " + decodedString + " ]" );
		return decodedString;		
	}
}
