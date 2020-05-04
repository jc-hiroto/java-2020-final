package src.hash;
//AES ECB PKCS7 padding mode
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;

public class passwordHash {
	
    private final static String alg = "AES";
    private final static String cI = "AES/ECB/PKCS7Padding";
    private final static String key = "0123456789ABCDEF";

    //encode text function
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
            
            String s = new String(encodeBase64(encrypted));
            return new StringBuffer(s);
		}catch(Exception e) {
			e.getStackTrace();
			throw e;
		}
    		
    }
    
    //decode text function
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
            
            String s = new String(decrypted, "UTF-8");
            return new StringBuffer(s);
    	}catch(Exception e) {
    		e.getStackTrace();
    		throw e;
		}
    }
    
}