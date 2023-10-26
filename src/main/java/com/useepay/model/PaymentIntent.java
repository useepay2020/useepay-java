package com.useepay.model;

import com.alibaba.fastjson.JSON;
import com.useepay.UseePay;
import com.useepay.exception.InvalidRequestException;
import com.useepay.exception.UseePayException;
import com.useepay.net.RequestOptions;
import com.useepay.param.PaymentIntentCreateParams;
import com.useepay.util.HttpClientUtils;
import com.useepay.util.SecurityUtils;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    /**
     * 金额
     */
    private String amount;
    /**
     * 业务返回码
     * 判断交易是否成功 请参考业务结果码
     */
    private String resultCode;
    /**
     * 签名
     */
    private String sign;
    /**
     * 错误返回码
     * 用于展示给消费者具体的错误信息，可参考错误码页面
     */
    private String errorCode;
    /**
     * 商户订单号
     */
    private String transactionId;
    /**
     * 错误返回信息
     */
    private String errorMsg;
    /**
     * 当resultCode=received时存在
     */
    private String token;
    /**
     * 交易类型
     * pay/authorization
     */
    private String transactionType;
    /**
     * UseePay的业务流水号
     */
    private String reference;
    /**
     *
     */
    private String echoParam;
    /**
     * 签名类型
     */
    private String signType;
    /**
     * 币种
     */
    private String currency;
    /**
     * 商户号
     */
    private String merchantNo;
    /**
     *
     */
    private String redirectUrl;


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
        String response = HttpClientUtils.doPost(UseePay.getApiBase()+path,paymentMap);
        System.out.println(response);
        if(Objects.isNull(response))
            return null;
        return JSON.parseObject(response,PaymentIntent.class);
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
