package Tool;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CreateMD5 {
	
	/* md5
	 * @param plainText 字符串
	 * @param length    生成length长度 的字符串
	 * @return randomChars length长度的字符串
	 */
	public static String getMd5(String plainText,int length) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
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
			if (length == 32){
				//32
				return buf.toString();
			}else if(length == 16){
				// 16
				return buf.toString().substring(8, 24);
			}
			//默认返回32
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String content = "2";
		System.out.println("原值：" + content);	//a0b923820dcc509a
		System.out.println("MD5值：" + getMd5(content,16));
	}
}