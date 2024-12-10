package com.ninza.hrm.api.genericutility;


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//private Key=AcO3tEam@j!tu_#1
public class EncrypAndDecrypUtility {
	public String encrypt(String input,String secretKey) throws Throwable, Exception   {
		SecretKeySpec secretKeyspec = new SecretKeySpec(secretKey.getBytes(),"AES");
		IvParameterSpec ivparameterSpec = new IvParameterSpec("4234567890123456".getBytes());
		Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeyspec,ivparameterSpec);
		byte[] encrypted= cipher.doFinal(input.getBytes());
		return Base64.getEncoder().encodeToString(encrypted);
	}
	
	public String decrypt(String input,String secretKey) throws Throwable {
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(),"AES");
		IvParameterSpec ivParameterSpec= new IvParameterSpec("4234567890123456".getBytes());
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec,ivParameterSpec);
		byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(input));
		return new String(decrypted);
		
}
}
