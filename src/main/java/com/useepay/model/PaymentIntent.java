package com.useepay.model;

import com.alibaba.fastjson.JSON;
import com.useepay.UseePay;
import com.useepay.exception.InvalidRequestException;
import com.useepay.exception.UseePayException;
import com.useepay.net.RequestOptions;
import com.useepay.param.PaymentIntentCreateParams;
import com.useepay.util.SecurityUtils;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: useepay-java
 * @Author: zhi.wang
 * @CreateTime: 2023-09-26  19:00
 * @Description: 支付意向对象
 *
 * @Version: 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentIntent extends UseePayObject{

    private String amount;
    private String redirectUrl;
    private String resultCode;
    public static PaymentIntent create(PaymentIntentCreateParams params) throws UseePayException {
        return create(params, (RequestOptions)null);
    }

    public static PaymentIntent create(PaymentIntentCreateParams params, RequestOptions options) throws UseePayException {
        String path = "/api";
        validate(params);
        PaymentInfo paymentInfo = buildPaymentInfo(params);
        Map<String,Object> paymentMap = paramsToMap(paymentInfo.toJson());
        String sign = SecurityUtils.sign(paymentMap);
        paymentMap.put("sign",sign);
        return null;
    }

    /**
     * 构建PaymentInfo对象
     * @param params
     * @return
     */
    public static PaymentInfo buildPaymentInfo(PaymentIntentCreateParams params){
        PaymentInfo paymentInfo = PaymentInfo.builder()
                .version(UseePay.API_VERSION)
                .merchantNo(UseePay.getMerchantNo())
                .appId(UseePay.getAppId())
                .transactionId(params.getTransactionId())
                .transactionType(params.getTransactionType())
                .transactionExpirationTime(params.getTransactionExpirationTime())
                .amount(params.getAmount())
                .currency(params.getCurrency())
                .orderInfo(params.getOrderInfo().toJson())
                .payerInfo(params.getPayerInfo().toJson())
                .userInfo(params.getUserInfo().toJson())
                .notifyUrl(params.getNotifyUrl())
                .redirectUrl(params.getRedirectUrl())
                .echoParam(params.getEchoParam())
                .signType(UseePay.getSignType())
                .build();
        return paymentInfo;
    }

    /**
     * 参数验证
     * @param params
     * @throws UseePayException
     */
    public static void validate(PaymentIntentCreateParams params) throws UseePayException{
        //验证PaymentIntentCreateParams入参的基础校验
        List<String> errors = new ArrayList<>();
        PaymentIntentCreateParams.validate(params,errors);
        if(errors.size() >=1){
            throw new InvalidRequestException(ErrorCode.E1001,params.getTransactionId(),null,JSON.toJSONString(errors),null);
        }
    }


}
