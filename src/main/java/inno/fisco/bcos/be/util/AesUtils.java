package inno.fisco.bcos.be.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import inno.fisco.bcos.be.constant.Constant;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesUtils {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_IV = "abcdefgh12345678";
    private static final String CBC_PATTERN = "CBC";

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @return 加密数据
     */
    public  static String aesEncrypt(String content) {
        return  aesEncrypt( content,  Constant.aesKey,null);
    }

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param password 加密密码
     * @param iv 使用CBC模式，需要一个向量iv，可增加加密算法的强度
     * @return 加密数据
     */
    public  static String aesEncrypt(String content, String password, String iv) {
        if(StringUtils.isBlank(iv)) {
            iv = DEFAULT_IV;
        }
        try {
            //创建密码器
            Cipher cipher = Cipher.getInstance(getDefaultAesCipherPattern());

            //密码key(超过16字节即128bit的key，需要替换jre中的local_policy.jar和US_export_policy.jar，否则报错：Illegal key size)
            SecretKeySpec keySpec = new SecretKeySpec(password.getBytes("utf-8"), KEY_ALGORITHM);

            //向量iv
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("utf-8"));

            //初始化为加密模式的密码器
            if (CBC_PATTERN.equals(Constant.aesPattern)) {
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            }

            //加密
            byte[] byteContent = content.getBytes("utf-8");
            byte[] result = cipher.doFinal(byteContent);

            return Base64.getEncoder().encodeToString(result);
        } catch (Exception ex) {
            return null;
        }
    }


    /**
     * AES 解密操作
     *
     * @param content 密文
     * @return 明文
     */
    public  static String aesDecrypt(String content) {
        return aesDecrypt(content,  Constant.aesKey,null);
    }


    /**
     * AES 解密操作
     *
     * @param content 密文
     * @param password 密码
     * @param iv 使用CBC模式，需要一个向量iv，可增加加密算法的强度
     * @return 明文
     */
    public  static String aesDecrypt(String content, String password,String iv) {
        if(StringUtils.isBlank(iv)) {
            iv = DEFAULT_IV;
        }

        try {
            //创建密码器
            Cipher cipher = Cipher.getInstance(getDefaultAesCipherPattern());

            //密码key
            SecretKeySpec keySpec = new SecretKeySpec(password.getBytes(StandardCharsets.UTF_8),KEY_ALGORITHM);

            //向量iv
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(
                StandardCharsets.UTF_8));

            //初始化为解密模式的密码器
            if (CBC_PATTERN.equals(Constant.aesPattern)) {
                cipher.init(Cipher.DECRYPT_MODE,keySpec,ivParameterSpec);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
            }
            //执行操作
            byte[] encrypted1 = Base64.getDecoder().decode(content);
            byte[] result = cipher.doFinal(encrypted1);

            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception ex) {
        }

        return null;
    }

    /**
     * before v1.4.0, pattern default "ECB", after v1.4.0 use "CBC" as default
     * @return
     */
    private static String getDefaultAesCipherPattern() {
        // CBC as default
        String aesPattern = Constant.aesPattern;
        String cipherPattern = "AES/" + aesPattern + "/PKCS5Padding";
        return cipherPattern;
    }

    public static void main(String[] args) {
        String s = AesUtils.aesDecrypt("V5a1WJc05LjW8y9UAMOdscYIIDRIac6ISEJTzdDdg3lJzA+PKY84eW/9rsZUbnCygwvSr1E1vonFWGr6S9Dh3gUpLr41UOM0IixiDhX0CsI=");
        String s1 = AesUtils.aesEncrypt("1f1c017c37d7b29cf2a9e1ddcebc7a7b41d7401b1cbec2cdf5f30f3cf7657b67");
        System.out.println(s);
        System.out.println(s1);
    }



}
