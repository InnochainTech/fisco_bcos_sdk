package inno.fisco.bcos.be.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * @Description: 传输密钥加密
 * @Author peifeng
 * @Date 2021/8/17 14:55
 */
public class EncrypeUtils {

    Logger log = LoggerFactory.getLogger(EncrypeUtils.class);
    /**
     * 编码格式
     */
    private static final String ENCODING = "UTF-8";
    /**
     * 加密算法
     */
    public static final String KEY_ALGORITHM = "AES";
    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA1PRNG";

    /**
     * 加密
     *
     * @param content 待加密内容
     * @param key     加密的密钥
     * @return
     */
    public static String AESEncode(String key,String content) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
        random.setSeed(key.getBytes(ENCODING));
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        byte[] byteContent = content.getBytes(ENCODING);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] byteResult = cipher.doFinal(byteContent);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteResult.length; i++) {
            String hex = Integer.toHexString(byteResult[i] & 0xFF);
            if (hex.length() == 1){
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @param key     解密的密钥
     * @return
     */
    public static String AESDncode( String key,String content) throws Exception {
        if (content.length() < 1) {
            return null;
        }
        byte[] byteResult = new byte[content.length() / 2];
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
            byteResult[i] = (byte) (high * 16 + low);
        }

        KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
        random.setSeed(key.getBytes(ENCODING));
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] result = cipher.doFinal(byteResult);
        return new String(result, ENCODING);

    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @return
     */
    public static String Hash(String content) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(content.getBytes("UTF-8"));
        byte[] result = md.digest();

        return new BigInteger(1, result).toString(16);
    }


    public static void main(String[] args) throws Exception {
        System.out.println(AESEncode("dev-key","603864b8f51e8d1db96ea3a0ecee3119b1e5d3fb8e3fd49cc73fbc13a22b512a"));
        System.out.println(AESDncode("dev-key","CFE37E1819CC2BE36B82F0D5C102A8830A35E9FF877E7294D1D98D67D07F44F8A761B18135B4F7113258267701C93F35E31F83D46F5CD819804237BF52E753BD399735C1A6B3347F4E99C263498DA231"));

        System.out.println(AESEncode("dev-key","ecabacb3b3b21ac139af8601d2d7ab7bed7b5b801fddaf9921356cbe8a2e3118"));
        System.out.println(AESDncode("dev-key","E8F5E3212A8BF0283A1B29704F9C01ACA84AD9BC07E33383A1FB279208EB752FEF5E1CC36BCF275D77B7B6F5510FD32AC47203A321D73A47FE340683A27C8035399735C1A6B3347F4E99C263498DA231"));

    }
}
