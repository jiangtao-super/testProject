package com.dianju.crm.tool;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class EncUtil {
		
	/**
	 * DES算法密钥
	 */
	private static final String DES_KEY = "N0BRd0Uqa18=";
	
	private static final String AES_KEY = "IStaMCpVTDtTZOa3jU0/GQ==";
	
	private static String digest_alg = "SHA-1";
	private static String key_alg = "AES";
	private static String crypto_alg = "AES/ECB/PKCS5Padding";
	
	/**
	 * 基于MD5算法的单向加密
	 * @param strSrc 明文
	 * @return 返回密文
	 */
	public static String getMd5(String strSrc) {
		String outString = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(strSrc.getBytes("UTF-8"));
			byte b[] = md5.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			outString = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outString;
	}
	/**
	 * 获取值的sha-1的指纹的值
	 * @param data
	 * @return
	 */
	public static String getSha1(byte[] data) {
		String res = null;
		try {
			MessageDigest md = MessageDigest.getInstance(digest_alg);
			byte[] sha = md.digest(data);
			res = Base64.encodeToString(sha);
			res = res.replaceAll("\r\n", "");
			res = res.replaceAll("\n", "");
			res = res.replaceAll("\r", "");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * des加密算法
	 * @param data 要进行加密的数据
	 * @param skey 加密的密码
	 * @return 加密后的字符串
	 */
	public static String encodeDES(String data,String skey){
		String encryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(skey.getBytes("utf-8"));
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 加密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			// 加密，并把字节数组编码成字符串
			encryptedData = Base64.encodeToString(cipher.doFinal(data.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("DES加密错误，错误信息：", e);
		}
		return encryptedData;
	}
	
	public static String decodeDES(String cryptData,String skey){
		String decryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(skey.getBytes("utf-8"));
			//创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 解密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			// 把字符串解码为字节数组，并解密
			decryptedData = new String(cipher.doFinal(Base64.decode(cryptData)),"utf-8");
		} catch (Exception e) {
			throw new RuntimeException("DES解密错误，错误信息：", e);
		}
		return decryptedData;
	}

	/**
	 * 数据加密，算法（DES）
	 * 
	 * @param data
	 *            要进行加密的数据
	 * @return 加密后的数据
	 */
	public static String encodeDES(String data) {
		String encryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			byte[] tkey=Base64.decode(DES_KEY);
			DESKeySpec deskey = new DESKeySpec(tkey);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey); 
			// 加密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			// 加密，并把字节数组编码成字符串
			encryptedData = Base64.encodeToString(cipher.doFinal(data.getBytes("utf-8")));
		} catch (Exception e) {
			throw new RuntimeException("DES加密错误，错误信息：", e);
		}
		return encryptedData;
	}

	/**
	 * 数据解密，算法（DES）
	 * @param cryptData 加密数据
	 * @return 解密后的数据
	 */
	public static String decodeDES(String cryptData) {
		String decryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			byte[] tkey=Base64.decode(DES_KEY);
			DESKeySpec deskey = new DESKeySpec(tkey);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 解密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			// 把字符串解码为字节数组，并解密
			decryptedData = new String(cipher.doFinal(Base64.decode(cryptData)),"utf-8");
		} catch (Exception e) {
			throw new RuntimeException("DES解密错误，错误信息：", e);
		}
		return decryptedData;
	}
	/**
	 * 数据加密AES算法
	 * @param cryptData 被加密的数据
	 * @param base64key 加密key的base64字符串
	 * @return
	 */
	public static String encodeAES(String cryptData,String bkey){
		 String res = "";
		    try {
		      byte[] keyB = Base64.decode(bkey);
		      SecretKeySpec keyspec = new SecretKeySpec(keyB, key_alg);
		      Cipher cipher = Cipher.getInstance(crypto_alg);
		      cipher.init(1, keyspec);
		      byte[] data=cryptData.getBytes("utf-8");
		      byte[] enc = cipher.doFinal(data);
		      res = Base64.encodeToString(enc);
		      res = res.replaceAll("\r\n", "");
		      res = res.replaceAll("\n", "");
		      res = res.replaceAll("\r", "");
		    } catch (Exception e) {
		    	throw new RuntimeException("AES加密错误，错误信息:",e);		    	
		    }
		    return res;
	}
	/**
	 * 用默认的密码做AES加密
	 * @param cryptData 被加密数据
	 * @return
	 */
	public static String encodeAES(String cryptData){
		 String res = "";
		    try {
		      byte[] keyB = Base64.decode(AES_KEY);
		      SecretKeySpec keyspec = new SecretKeySpec(keyB, key_alg);
		      Cipher cipher = Cipher.getInstance(crypto_alg);
		      cipher.init(1, keyspec);
		      byte[] data=cryptData.getBytes("utf-8");
		      byte[] enc = cipher.doFinal(data);
		      res = Base64.encodeToString(enc);
		      res = res.replaceAll("\r\n", "");
		      res = res.replaceAll("\n", "");
		      res = res.replaceAll("\r", "");
		    } catch (Exception e) {
		    	throw new RuntimeException("AES加密错误，错误信息:",e);		    	
		    }
		    return res;
	}
	
	public static String decodeAES(String cryptData,String bkey){
		String dec=null;
	    try {
	      byte[] keyB = Base64.decode(bkey);
	      SecretKeySpec keyspec = new SecretKeySpec(keyB, key_alg);
	      Cipher cipher = Cipher.getInstance(crypto_alg);
	      cipher.init(2, keyspec);
	      byte[] data = Base64.decode(cryptData);
	      
	      dec = new String(cipher.doFinal(data),"utf-8");
	    } catch (Exception e) {
	    	throw new RuntimeException("AES解密错误，错误信息:",e);		    	
	    }
	    return dec;
	}
	
	public static String decodeAES(String cryptData){
		String dec=null;
	    try {
	      byte[] keyB = Base64.decode(AES_KEY);
	      SecretKeySpec keyspec = new SecretKeySpec(keyB, key_alg);
	      Cipher cipher = Cipher.getInstance(crypto_alg);
	      cipher.init(2, keyspec);
	      byte[] data = Base64.decode(cryptData);
	      dec = new String(cipher.doFinal(data),"utf-8");
	    } catch (Exception e) {
	    	throw new RuntimeException("AES解密错误，错误信息:",e);		    	
	    }
	    return dec;
	}
	
}
