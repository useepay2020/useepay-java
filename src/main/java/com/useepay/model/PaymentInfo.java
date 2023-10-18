package com.useepay.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @BelongsProject: useepay-java
 * @Author: zhi.wang
 * @CreateTime: 2023-09-27  11:56
 * @Description: 支付信息
 * @Version: 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentInfo extends UseePayObject{
    /**
     * 接口版本
     */
    @NotEmpty(message = "version不能为空")
    @Length(max = 5,message = "version不能多于5位")
    private String version;
    /**
     * 商户号，由 USEEPAY 分配
     */
    @NotEmpty(message = "version不能为空")
    @Length(max = 16,message = "version不能多于5位")
    private String merchantNo;

    /**
     * 商户订单号，建议每次请求的 transactionId 唯一，有利于后续订单查询和对账
     */
    @NotEmpty(message = "商户订单号[transactionId]不能为空")
    @Length(max = 64,message = "商户订单号[transactionId]不能多于64位")
    private String transactionId;

    /**
     * 交易类型：pay
     */
    @NotEmpty(message = "交易类型[transactionType]不能为空")
    @Length(max = 18,message = "交易类型[transactionType]不能多于18位")
    private String transactionType;

    /**
     * 交易类型：pay
     */
    @Length(max = 6,message = "订单过期时长[transactionExpirationTime]不能多于6位")
    private String transactionExpirationTime;
    /**
     * 商户在 USEEPAY 登记的应用 ID
     */
    @NotEmpty(message = "应用ID[appId]不能为空")
    @Length(max = 128,message = "应用ID[appId]不能多于128位")
    private String appId;
    /**
     * 订单金额（必须大于 0），单位为对应币种的最小货币单位(详见 ISO 4217)
     */
    @NotEmpty(message = "订单金额[amount]不能为空")
    @Length(max = 12,message = "订单金额[amount]不能多于128位")
    private String amount;
    /**
     * 3 位 ISO 大写字母货币代码(详见 ISO 4217)
     */
    @NotEmpty(message = "订单金额[currency]不能为空")
    @Length(max = 3,message = "订单金额[currency]不能多于128位")
    private String currency;
    /**
     * 订单信息，JSON 格式（详见 订单信息）
     */
    @NotEmpty(message = "订单信息[orderInfo]不能为空")
    private String orderInfo;
    /**
     * 付款方信息，JSON 格式（详见 付款方信息）
     */
    @NotEmpty(message = "付款方信息[payerInfo]不能为空")
    private String payerInfo;
    /**
     * 用户信息，JSON 格式（详见 用户信息）
     */
    @NotEmpty(message = "用户信息[userInfo]不能为空")
    private String userInfo;
    /**
     * 回调商户地址
     */
    @Length(max = 512,message = "回调商户地址[notifyUrl]不能多于512位")
    private String notifyUrl;
    /**
     * USEEPAY 跳转商户地址
     */
    private String redirectUrl;
    /**
     * 回声参数，响应报文会原样返回
     */
    private String echoParam;
    /**
     * 预留字段
     */
    private String reserved;
    /**
     * max=4 商户生成签名字符串所使用的签名算法类型，目前支持：RSA
     */
    @NotEmpty(message = "签名算法类型[signType]不能为空")
    private String signType;
    /**
     * max=256 商户请求参数的签名串
     */
    @NotEmpty(message = "签名[sign]不能为空")
    private String sign;
}
