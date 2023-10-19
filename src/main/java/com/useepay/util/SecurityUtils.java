package com.useepay.util;

import com.useepay.UseePay;
import com.useepay.exception.InvalidRequestException;
import com.useepay.exception.UseePayException;
import com.useepay.model.ErrorCode;
import com.useepay.model.SignType;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

/**
 * @BelongsProject: useepay-java
 * @Author: zhi.wang
 * @CreateTime: 2023-10-18  15:07
 * @Description: TODO
 * @Version: 1.0
 */
public class SecurityUtils {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    /**
     * 签名
     * @param param
     * @return
     */
    public static String sign(Map<String,Object> param) throws UseePayException {
        String signData = buildParam(param);
        String signType = UseePay.getSignType();
        if(SignType.MD5.name().equalsIgnoreCase(signType)){
            signData = signData +"pkey="+UseePay.getAppKey();
            System.out.println("signData="+signData);
            return md5(signData);
        }else if(SignType.RSA.name().equalsIgnoreCase(signType)){
            signData = signData.substring(0,signData.length()-1);
            System.out.println("signData="+signData);
            return rsaSign(UseePay.getAppKey(),signData);
        }else{
            throw new InvalidRequestException(ErrorCode.E1001.getCode(),"无效的签名类型，signType="+signType);
        }

    }

    /**
     * 生成请求字符串
     * @param param
     * @return
     */
    public static String buildParam(Map<String,Object> param){
        StringBuffer sb = new StringBuffer();
        List<String> keys = new ArrayList<>(param.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            if(Objects.isNull(key) || key.trim().length() == 0
                    || "sign".equalsIgnoreCase(key.trim()))
                continue;
            sb.append(key).append("=").append(param.get(key)).append("&");

        }
        return sb.toString().replaceAll("\r","").
                replaceAll("\n","").replaceAll("\r\n","");
    }

    /**
     * MD5签名
     * @param signData
     * @return
     */
    public static String md5(String signData) throws UseePayException{
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(signData.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashText = no.toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText.toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidRequestException(ErrorCode.E1001.getCode(),"RSA签名失败"+e.getMessage());
        }

    }

    /**
     * RSA签名
     * @param signData
     * @return
     */
    public static String rsaSign(String privateKeyString,String signData) throws UseePayException{
        Signature signature = null;
        try {
            String realKey = privateKeyString.replaceAll("[\r\n]", "")
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "").replaceAll(" ","")
                    .replaceAll("\n","").replaceAll("\r\n","");
            byte[] privateKeyBytes = Base64.getDecoder().decode(realKey);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(signData.getBytes());
            byte[] signatureBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidRequestException(ErrorCode.E1001.getCode(),"RSA签名失败"+e.getMessage());
        }
    }

    public static void main(String[] args) {
        String key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALQ32TGpss0n8bXm\n" +
                "mDNg9AfA/qHP0F22A1S/4rrWsgeyP+VPjQY5+2eCmE4bmUa5Q0ytLEhraK9g7VJC\n" +
                "s2iA+oPjuODo8NCrLALFNFhnfpGvwJpF31lw6ru4Ns5xPYfCThrDFThkjAjADUZG\n" +
                "Gks31nNDd6zP2sCTiIqOh03Dp0L9AgMBAAECgYEAptTwln3vDOaGZwryKNX+8lKi\n" +
                "Ji9YR/gf+0urW6U2p9NGPUS4W2Q/rKID1oLMEOGeQJQrY1szTO9gov8mUH7d6L6p\n" +
                "1a1ZNSNau9sWHLznXlNETY7MTPe2z1NY6/DxWYhMxWZ6IKPmU49CRoCmkMJf5+Yc\n" +
                "S0ysDcFODT8FFQe1+cECQQDY/PU3Ve6+ONIypGnZuQ+5H0w7xsViP9fPDUY2P0Tv\n" +
                "jFHtKTzV2SPipulJXHGaHcijU64F80GeOkCy/y4ZgJmRAkEA1J6Gf4TFCSRK5Bou\n" +
                "EC2o1xxyWmEJ4saoz2aCNamgSyMdkIPq/beTalGkRhhb8HLIxTrwmBLFQWuLhd9C\n" +
                "Mum8rQJBAIXZzL48wRAQZwGcUDthICTW/KSnId1QeqSbkKMn9jM6wNLmLEPSNYO7\n" +
                "6I47e0xRcRHnLaM4AlNDpeOE7AAJRzECQGIWSAIoJCYVgh89HKcGCXBDhVXAkqj1\n" +
                "8QbNSaCsAnDnYEo9MXme6PkRdPGJZ1DEA29Jw5jEgA+wJ4u49Xc7cXkCQQDUx+HP\n" +
                "PFg8uugUNYqYnbHu0W6/kn5QKeBA32awe5NNAZvvI04TlPEOxeuZ42EJ0lJkNFst\n" +
                "EJ4mL5EiKhJV3+Db";
        String sign = null;
        try {
            sign = rsaSign(key,"test");
            System.out.println(sign);
        } catch (UseePayException e) {
            throw new RuntimeException(e);
        }

    }
}
