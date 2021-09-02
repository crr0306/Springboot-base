package com.spring.base.utils.aes;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;


/**
 * 
 * @author crr
 * * 前后端数据传输加密工具类
 *  参考内容：https://blog.csdn.net/junmoxi/article/details/80917234
 */
public class AesEncryptUtils {
    private static Logger logger = Logger.getLogger(AesEncryptUtils.class);
    /**可配置到Constant中，并读取配置文件注入*/
    private static final String KEY = "abcdef0123456789";
    public static boolean initialized = false;

    /**参数分别代表 算法名称/加密模式/数据填充方式*/
    private static final String ALGORITHMSTR = "AES/CBC/PKCS7Padding";

    private static final String WATERMARK = "watermark";
    private static final String APPID = "appid";

    /**
     * 加密
     * @param content 加密的字符串
     * @param encryptKey key值
     * @return
     * @throws Exception
     */
    public  static String encrypt(String content, String encryptKey) throws Exception {
        //1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //2.生成一个128位的随机源
        kgen.init(128);
        //根据指定算法AES自成密码器
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        //根据密码器的初始化方式--加密：将数据加密
        byte[] b = cipher.doFinal(content.getBytes("utf-8"));
        //将加密后的数据转换为字符串
        return Base64.encodeBase64String(b);
    }

    /**
     * 解密
     * @param encryptStr 解密的字符串
     * @param decryptKey 解密的key值
     * @return
     * @throws Exception
     */
    public  static String decrypt(String encryptStr, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] encryptBytes = Base64.decodeBase64(encryptStr);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    public  static String encrypt(String content) throws Exception {
        return encrypt(content, KEY);
    }
    public  static String decrypt(String encryptStr) throws Exception {
        return decrypt(encryptStr, KEY);
    }

    /**
     * AES解密
     *
     * @param
     *
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchProviderException
     */
    public static void initialize() {
        if (initialized)
            return;
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }
    // 生成iv
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }
    public static byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) throws InvalidAlgorithmParameterException {
        initialize();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            Key sKeySpec = new SecretKeySpec(keyByte, "AES");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 删除解密后明文的补位字符
     *
     * @param decrypted
     *            解密后的明文
     * @return 删除补位字符后的明文
     */
    public static byte[] decode(byte[] decrypted) {
        int pad = decrypted[decrypted.length - 1];
        if (pad < 1 || pad > 32) {
            pad = 0;
        }
        return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
    }
    /**
     * 解密数据
     * @return
     * @throws Exception
     */
    public static String decrypt(String appId, String encryptedData, String sessionKey, String iv){
        String result = "";
        try {

            byte[] resultByte = decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
            if(null != resultByte && resultByte.length > 0){
                result = new String(decode(resultByte));
                logger.info("the result of des"+result);

            }
        } catch (Exception e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        try {
//            encryptedData:f7KBxq7XZ1SGBYAVNUPLqsBX6bdKLTTwlN+BvvKg1YY92eOxg0EisRRRlYYG2ZAbpDlCeWT1GoGYvk4nrc0brLPrwgIfSSDQo7QAq5iUFtXi7t/p3p/t8CrS28rJB9niTsrteS8g78tASaiDB1WIt34Qjx0TjtIIMjFVY/FXjRtltuve3ADPrefYOkBoU2TRCpXXvdBIxTFx6GyyzZb/pQ==,
//                    iv:PD89jqhSTSDCB3dA146Ffw==,
//                    sessionKey：xAunW5fXE1dEdMZYsx1AbA==

            String appId = "wxddc0c0e3ee0a48a2";
            String encryptedData = "BjwR2Jt9s1qOp9lp8s9L30PQFN2oz17tVJ0qufd0TR3+an4kMM7NBqSWeu771+BHwjqt7Pz+sN1BoLGNe6HawY+/hSTxCO4pOeGCV8fkg0vWOxNFsV/gMP5yHxUV7w68znvz4Irwu+McRtMj6khWk1xAEgjRaQ/YC2LsfOqS/K1fdWuqeOzi1TJIj9AuaR005j861+0+EI6lnSrhsZ12tA==";
            String sessionKey = "1C0kLUJk8ejjA2FpsYN0mA==";
            String iv = "mb6z82jLnV1m7GM1Ez/3vQ==";
            System.out.println(decrypt(appId, encryptedData, sessionKey, iv));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

