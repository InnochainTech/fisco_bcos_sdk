package inno.fisco.bcos.be.util;

import java.math.BigInteger;

/**
 * @author peifeng
 * @date 2021/12/2 4:13 下午
 */
public class CommonUtils {

    private String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    protected String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }

    /**
     * 16位转10位数字
     * @param hex
     * @return
     */
    public static String conver16to10(String hex){
        String substring = hex.substring(2);
        String s = substring.replaceAll("^(0+)", "");
        if(org.springframework.util.StringUtils.isEmpty(s)){
            return "0";
        }
        BigInteger x = BigInteger.valueOf(Long.valueOf(s,16));
        return x+"";
    }

    /**
     * 去掉返回的[]  [1625] ==> 1625
     * @param str
     * @return
     */
    public static String conver(String str){
        String s = str.replaceAll("\\[", "").replaceAll("\\]", "");
        return s;
    }

}
