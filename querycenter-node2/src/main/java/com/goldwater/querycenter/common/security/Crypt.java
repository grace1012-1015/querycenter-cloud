package com.goldwater.querycenter.common.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Crypt {
    private static Key key = null;
    public static BASE64Encoder base64en = new BASE64Encoder();
    public static BASE64Decoder base64De = new BASE64Decoder();
    private static String Algorithm = "DES"; // 定义 加密算法,可用
    // DES,DESede,Blowfish
    private static String Encrypt = "2d0a0ba4c857b8faad98663edcab76757b3fdeb00544fd737e98a55c147422d050643f179d67ef800bfab6a0408db7df0bfab6a0408db7df21bc8b0db3aa1828";

    static boolean debug = false;

    static {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
    }

    public Crypt() {
    }

    public static void getKey() {
        getKey(Encrypt);
    }

    public static void getKey(String strKey) {
        if (key != null)
            return;
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("DES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes("iso-8859-1"));
            _generator.init(56, secureRandom);
            // _generator.init(new SecureRandom(strKey.getBytes("iso-8859-1")));
            key = _generator.generateKey();
            // System.out.println(this.key.toString());

            _generator = null;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Crypt Error initializing getKey class. Cause: " + e);
        }
    }

    // 加密
    public static byte[] desEncrypt(byte[] input) throws Exception {
        getKey();
        if (debug) {
            System.out.println("加密前的二进串:" + byte2hex(input));
            System.out.println("加密前的字符串:" + new String(input));
        }
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] cipherByte = cipher.doFinal(input);
        if (debug)
            System.out.println("加密后的二进串:" + byte2hex(cipherByte));
        return cipherByte;
    }

    /* 解密 */
    public static String desEncrypt(String strMing) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        try {
            byteMing = strMing.getBytes("UTF8");
            byteMi = desEncrypt(byteMing);
            strMi = base64en.encode(byteMi);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }

    /* 解密 */
    public static String desDecrypt(String strMi) {
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            byteMi = base64De.decodeBuffer(strMi);
            byteMing = desDecrypt(byteMi);
            strMing = new String(byteMing, "UTF8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }

    // 解密
    public static byte[] desDecrypt(byte[] input) throws Exception {
        getKey();
        if (debug)
            System.out.println("解密前的信息:" + byte2hex(input));
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] clearByte = cipher.doFinal(input);
        if (debug) {
            System.out.println("解密后的二进串:" + byte2hex(clearByte));
            System.out.println("解密后的字符串:" + (new String(clearByte)));
        }
        return clearByte;
    }

    /**
     * md5()信息摘要, 不可逆
     *
     * @param input
     *            byte[]
     * @throws Exception
     * @return byte[]
     */
    public static byte[] md5(byte[] input) throws Exception {
        java.security.MessageDigest alg = java.security.MessageDigest
                .getInstance("MD5"); // or
        // "SHA-1"
        if (debug) {
            System.out.println("摘要前的二进串:" + byte2hex(input));
            System.out.println("摘要前的字符串:" + new String(input));
        }
        alg.update(input);
        byte[] digest = alg.digest();
        if (debug) {
            System.out.println("摘要后的二进串:" + byte2hex(digest));
            System.out.println("摘要后的字符串:" + new String(digest));
        }

        return digest;
    }

    /**
     *
     * @param input
     *            byte[]
     * @throws Exception
     * @return String
     */
    public static String md5Encrypt(byte[] input) throws Exception {
        // return new String(md5(input));
        return byte2hex(md5(input));
    }

    public static String md5Encrypt(String input) {
        // return new String(md5(input));
        String strMing = "";
        try {
            return strMing = byte2hex(md5(input.getBytes()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return strMing;
    }

    // 字节码转换成16进制字符串
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            // System.out.println(java.lang.Integer.toBinaryString(b[n] &
            // 0XFF));
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + "";// hs=hs+":";
        }
        return hs.toUpperCase();
    }

    public static void main(String[] args) throws Exception {
        debug = true;
        // byte[] key = getKey();
        // byte[] key = "password".getBytes();
        // decode(encode("加密前的文字".getBytes(),key),key);
        // md5("chenbingc".getBytes());
        // desEncrypt("你好啊");
        // String aa = getEncString("你好啊");
        // System.out.println(aa);
        // String bb = getDecString(aa);
        // System.out.println(bb);
        // System.out.println(md5Encrypt("你好啊".getBytes()));
        // System.out.println(md5Encrypt("你好啊"));
        //	System.out.println(desDecrypt("96E79218965EB72C92A549DD5A330112"));
        System.out.println(md5Encrypt("11111"));
        System.out.println(desEncrypt("B0BAEE9D279D34FA1DFD71AADB908C3F"));

    }
}
