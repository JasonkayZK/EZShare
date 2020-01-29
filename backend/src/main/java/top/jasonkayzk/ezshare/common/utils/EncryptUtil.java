package top.jasonkayzk.ezshare.common.utils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * 加密工具类
 *
 * @author zk
 */
public class EncryptUtil {

    /**
     * 默认密匙
     */
    private static final String DEFAULT_KEY = "default";

    /**
     * 加密
     */
    private Cipher encryptCipher;

    /**
     * 解密
     */
    private Cipher decryptCipher;

    public EncryptUtil() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this(DEFAULT_KEY);
    }

    EncryptUtil(String strKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = getKey(strKey.getBytes());

        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    private byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    private byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }

    String decrypt(String strIn) {
        try {
            return new String(decrypt(hexStr2ByteArr(strIn)));
        } catch (Exception e) {
            return "";
        }
    }

    private Key getKey(byte[] bytes) {
        byte[] arrB = new byte[8];
        for (int i = 0; i < bytes.length && i < arrB.length; i++) {
            arrB[i] = bytes[i];
        }
        return new javax.crypto.spec.SecretKeySpec(arrB, "DES");
    }

    private static byte[] hexStr2ByteArr(String strIn) {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    private static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        StringBuilder sb = new StringBuilder(iLen * 2);
        for (byte anArrB : arrB) {
            int intTmp = anArrB;
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

}
