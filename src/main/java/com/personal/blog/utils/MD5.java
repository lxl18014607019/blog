package com.personal.blog.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
* @description:
* @author: luxinlong
**/

public class MD5 {

    public static String getMD5(String str){

        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte[] byteDiget = messageDigest.digest();
            int i;
            StringBuffer stringBuffer = new StringBuffer("");
            for (int offset = 0; offset < byteDiget.length; offset++){
                i = byteDiget[offset];
                if (i<0)
                    i+=256;
                if (i<16)
                    stringBuffer.append("0");
                stringBuffer.append(Integer.toHexString(i));
            }
            //32位
            return stringBuffer.toString();
        }catch (NoSuchAlgorithmException exception){
            exception.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(getMD5("admin"));
    }

}
