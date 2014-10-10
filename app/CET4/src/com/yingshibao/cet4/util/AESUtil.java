package com.yingshibao.cet4.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AESUtil AES工具类
 * @author Rey
 *
 */
public class AESUtil {
	
	/**
	 * 加密
	 * 
	 * @param content
	 * @param password
	 * @return
	 */
	public static String encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(
					new byte[cipher.getBlockSize()]));
			byte[] result = cipher.doFinal(byteContent);
			return byte2hex(result); 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 * @param password
	 * @return
	 */
	public static String decrypt(String content, String password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(
					new byte[cipher.getBlockSize()]));
			byte[] result = cipher.doFinal(hex2byte(content));
			return new String(result, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 十六进制字符串转化为2进制
	 * 
	 * @param hex
	 * @return
	 */
	private static byte[] hex2byte(String hex) {
		byte[] ret = null;
		// try {
		byte[] tmp = hex.getBytes();
		int length = tmp.length / 2;
		ret = new byte[length];
		for (int i = 0; i < length; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	/**
	 * 将二进制转化为16进制字符串
	 * 
	 * @param b
	 *            二进制字节数组
	 * @return String
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
	/**
	 * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
	 * 
	 * @param src0
	 *            byte
	 * @param src1
	 *            byte
	 * @return byte
	 */
	private static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}
}
