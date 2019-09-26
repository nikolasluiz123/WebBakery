package br.com.WebBakery.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash_Util {

    public static Integer generateJavaHashCode(Object obj) {
        return obj.hashCode();
    }

    public static String generateHash(String value, HashTypeEnum hashType) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance(hashType.name().toUpperCase());
            byte[] result = mDigest.digest(value.getBytes());
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String generateHashMaxSecurity(String value) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance(HashTypeEnum.SHA512.name().toUpperCase());
            byte[] result = mDigest.digest(value.getBytes());
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
