package com.useepay.util;

import com.useepay.UseePay;
import com.useepay.exception.UseePayException;

import java.util.*;

/**
 * @BelongsProject: useepay-java
 * @Author: zhi.wang
 * @CreateTime: 2023-10-18  15:07
 * @Description: TODO
 * @Version: 1.0
 */
public class SecurityUtils {
    /**
     * 签名
     * @param param
     * @return
     */
    public static String sign(Map<String,Object> param) throws UseePayException {
        String signData = buildParam(param);
        String signType = UseePay.getSignType();

        return null;
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
}
