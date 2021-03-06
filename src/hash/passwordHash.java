package src.hash;
//AES ECB PKCS7 padding mode
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;

/**
 * hash the password and pass the password in encrypted status
 * The hashing method uses AES standard with ECB subject to PKCS7 version international financial institution regulations
 * Because the password cannot pass in plain text and store in the database, we set up hash key to set the hash the password into certain pattern and decrypted to check the validity
 */
public class passwordHash {

	private final static String alg = "AES";
	private final static String cI = "AES/ECB/PKCS7Padding";
	private final static String key = "0123456789ABCDEF";

	// encode text function
	// using cipher in the javax library
	public static StringBuffer encrypt(StringBuffer plainText) throws Exception {
		if(plainText.length() == 0){
			StringBuffer ERR = new StringBuffer();
			ERR.append("ERR");
			return ERR;
		}
		try {
			//generate the size of input text and key
			byte[] input = String.valueOf(plainText).getBytes();
			byte[] keyBytes = key.getBytes();
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance(cI, "BC");
			SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, alg);

			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = new byte[cipher.getOutputSize(input.length)];
			int ctLength = cipher.update(input, 0, input.length, encrypted, 0);
			ctLength += cipher.doFinal(encrypted, ctLength);

			String s = new String(encodeBase64(encrypted));
			return new StringBuffer(s);
		}catch(Exception e) {
			e.getStackTrace();
			throw e;
		}

	}

	// decode text function
	// using the provided hash key to decrypt the ciphered text
	public static StringBuffer decrypt(StringBuffer cipherText) throws Exception {

		try {
			//generate the size of input text and key
			byte[] input = decodeBase64(String.valueOf(cipherText));
			byte[] keyBytes = key.getBytes();

			Cipher cipher = Cipher.getInstance(cI, "BC");
			SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, alg);

			int ctLength = input.length;

			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] decrypted = new byte[cipher.getOutputSize(ctLength)];
			int ptLength = cipher.update(input, 0, ctLength, decrypted, 0);
			ptLength += cipher.doFinal(decrypted, ptLength);

			String s = new String(decrypted, "UTF-8");
			return new StringBuffer(s);
		}catch(Exception e) {
			e.getStackTrace();
			throw e;
		}
	}

}