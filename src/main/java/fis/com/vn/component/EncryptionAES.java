package fis.com.vn.component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fis.com.vn.common.StringUtils;

@Component
public class EncryptionAES {
	@Autowired ConfigProperties configProperties;
	private static byte[] key;
	String innitvector = "ab2b7261-ce5c-43ef-8292-0c72ae074a5e";
	String iv = "12119897-1cee-4388-9d4c-a3c9e8ea98ed";
	public byte[] sha256(String value) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] encodedhash = digest.digest(
	        		value.getBytes(StandardCharsets.UTF_8));
			
	        return encodedhash;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public String sha256Encode(String value) {
		try {
	        return Base64.getEncoder().encodeToString(sha256(value));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public String decrypt(String encrypted) {
		try {
			key = sha256(innitvector);
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, new GCMParameterSpec(128, iv.getBytes()));
			byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

			return new String(original, "UTF-8");
		} catch (Exception ex) {
		}

		return null;
	}
	
	public String encrypt(String value) {
		try {
			if(StringUtils.isEmpty(value)) return null;
			
			key = sha256(innitvector);
			
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new GCMParameterSpec(128, iv.getBytes()));
			
			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception ex) {
		}
		return null;
	}
}
