package com.yingshibao.cet4.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES加密
 * @author malinkang
 *
 */

public class DESUtil {
	private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

	/**
	 * 创建密匙
	 * 
	 * @param algorithm
	 *            加密算法,可用 DES,DESede,Blowfish
	 * @return SecretKey 秘密（对称）密钥
	 */
	public static SecretKey createSecretKey(String algorithm) {
		// 声明KeyGenerator对象
		KeyGenerator keygen;
		// 声明 密钥对象
		SecretKey deskey = null;
		try {
			// 返回生成指定算法的秘密密钥的 KeyGenerator 对象
			keygen = KeyGenerator.getInstance(algorithm);
			// 生成一个密钥
			deskey = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 返回密匙
		return deskey;
	}
	/**
	 * 加密字符串
	 * @param encryptStr
	 * @param encryptKey
	 * @return
	 * @throws Exception
	 */
	public static String encryptDES(String encryptStr, String encryptKey){
		try {
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes("UTF-8"),
					"DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
			byte[] encryptedData = cipher.doFinal(encryptStr.getBytes("UTF-8"));
			// 返回密文的十六进制形式
			LogUtil.e("加密成功"+new String(encryptedData));
			return byte2hex(encryptedData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtil.e("加密失败"+e.toString());
			return null;
		} 
		
	}
	/**
	 * 加密bytes
	 * @param encryptbyte
	 * @param encryptKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptDESBytes(String encryptStr, String encryptKey){
		try {
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes("UTF-8"),
					"DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
			LogUtil.e("加密成功");
			return cipher.doFinal(encryptStr.getBytes("UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtil.e("加密失败"+e.toString());
			return null;
		}
	}
	/**
	 * 解密字符串
	 * @param decryptString
	 * @param decryptKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptDES(String decryptString, String decryptKey)
			throws Exception {
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes("UTF-8"),
				"DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		byte decryptedData[] = cipher.doFinal(hex2byte(decryptString));
		return new String(decryptedData, "UTF-8");
	}
	/**
	 * 解密bytes
	 * @param decryptbytes
	 * @param decryptKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptDESBytes(byte[] decryptbytes, String decryptKey){
		try {
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes("UTF-8"),
					"DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			byte decryptedData[] = cipher.doFinal(decryptbytes);
			return new String(decryptedData, "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
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
	 * 十六进制字符串转化为2进制
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hex2byte(String hex) {
		byte[] ret = null;
		// try {
		byte[] tmp = hex.getBytes();
		int length = tmp.length / 2;
		ret = new byte[length];
		for (int i = 0; i < length; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return ret;
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
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}
	
	/**
	 * 密钥 Hex=0102030405060708
	 */
	protected static final byte[] keyBytes={1,2,3,4,5,6,7,8};
	
	/**
	 * 加密
	 * @param json
	 * @return
	 */
	public static byte[] encrypt(String json){
		try{
			SecretKeySpec keySpec=new SecretKeySpec(keyBytes, "DES");
			Cipher cipher=Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			byte[] jsonBytes=json.getBytes("UTF-8");
			return cipher.doFinal(jsonBytes);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * @param encrytedJson
	 * @return
	 */
	public static String decrypt(byte[] encrytedJson){
		try{
			SecretKeySpec keySpec=new SecretKeySpec(keyBytes, "DES");
			Cipher cipher=Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			byte[] jsonBytes=cipher.doFinal(encrytedJson);
			return new String(jsonBytes,"UTF-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String []args){
		try {
			System.out.print(decryptDES("{\"requestCourseLevel\":\"1\",\"password\":\"lounien\",\"username\":\"lounien\"}","12345678"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}