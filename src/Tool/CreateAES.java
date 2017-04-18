package Tool;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 编码工具类
 * 结合base64实现加密
 * 1.AES加密
 * 2.AES加密为base 64 code
 * 3.将base 64 code AES解码
 * 4.AES解密
 */
public class CreateAES  {
	
	public static void main(String[] args) throws Exception {
		String content = "加油";
		System.out.println("加密前：" + content);

		String key = "111";
		System.out.println("加密密钥和解密密钥：" + key);
		
		String encrypt = aesEncrypt(content, key);
		System.out.println("加密后：" + encrypt);
		
		String decrypt = aesDecrypt(encrypt, key);
		System.out.println("解密后：" + decrypt);
		
	}
	
	/**
	 * AES加密
	 * @param content 待加密的内容
	 * @param encryptKey 加密密钥
	 * @return 加密后的String
	 * @throws Exception
	 */
	public static String aesEncrypt(String content, String encryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(encryptKey.getBytes()));

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		BASE64Encoder base64encoder = new BASE64Encoder(); 
		String encode=base64encoder.encode(cipher.doFinal(content.getBytes()));
		return encode;
	}
	
	
	/**
	 * AES解密
	 * @param encrypt 待解密的String
	 * @param decryptKey 解密密钥
	 * @return 解密后的String
	 * @throws Exception
	 */
	public static String aesDecrypt(String encrypt, String decryptKey) throws Exception {
		BASE64Decoder base64decoder = new BASE64Decoder(); 
		byte[] encodeByte = base64decoder.decodeBuffer(encrypt); 
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(decryptKey.getBytes()));
		
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encodeByte);
		
		return new String(decryptBytes);
	}
	
}
