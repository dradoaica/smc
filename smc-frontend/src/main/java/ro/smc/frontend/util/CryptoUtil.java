package ro.smc.frontend.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoUtil {
	private Cipher initCipher(final int mode, final String initialVectorString, final String secretKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException {
		final SecretKeySpec skeySpec = new SecretKeySpec(hash(secretKey).getBytes(), "AES");
		final IvParameterSpec initialVector = new IvParameterSpec(initialVectorString.getBytes());
		final Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
		cipher.init(mode, skeySpec, initialVector);

		return cipher;
	}

	public String hash(final String dataToHash) {
		String hashedData = null;

		try {
			final MessageDigest md = MessageDigest.getInstance("MD5");
			final byte[] hashedByteArray = md.digest(dataToHash.getBytes());
			hashedData = (new BASE64Encoder()).encode(hashedByteArray);
		} catch (Exception ex) {
			System.err.println("Problem hashing the data");
			ex.printStackTrace();
		}

		return hashedData;
	}

	public String encrypt(final String dataToEncrypt, final String initialVector, final String secretKey) {
		String encryptedData = null;
		try {
			final Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, initialVector, secretKey);
			final byte[] encryptedByteArray = cipher.doFinal(dataToEncrypt.getBytes());
			encryptedData = (new BASE64Encoder()).encode(encryptedByteArray);
		} catch (Exception ex) {
			System.err.println("Problem encrypting the data");
			ex.printStackTrace();
		}

		return encryptedData;
	}

	public String decrypt(final String encryptedData, final String initialVector, final String secretKey) {
		String decryptedData = null;
		try {
			final Cipher cipher = initCipher(Cipher.DECRYPT_MODE, initialVector, secretKey);
			final byte[] encryptedByteArray = (new BASE64Decoder()).decodeBuffer(encryptedData);
			final byte[] decryptedByteArray = cipher.doFinal(encryptedByteArray);
			decryptedData = new String(decryptedByteArray, "UTF8");
		} catch (Exception ex) {
			System.err.println("Problem decrypting the data");
			ex.printStackTrace();
		}

		return decryptedData;
	}
}