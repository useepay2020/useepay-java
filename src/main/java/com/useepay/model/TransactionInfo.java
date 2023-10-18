package com.useepay.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @BelongsProject: useepay-java
 * @Author: zhi.wang
 * @CreateTime: 2023-09-26  17:51
 * @Description: TODO
 * @Version: 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TransactionInfo extends UseePayObject{
    /**
     * 商户订单号,max=64 必填
     */
    @NotEmpty(message = "商户订单号[transactionId]不能为空")
    @Length(max = 128,message = "商户订单号[transactionId]不能多于128位")
    private String transactionId;
    /**
     * max=64 原始商户订单号,有条件必填。 退款,预授权完成必填
     */
    @Length(max = 64 ,message = "原始商户订单号[originalTransactionId]不能多于64位")
    private String originalTransactionId;
    /**
     * max=32 USEEPAY 订单号
     */
    @Length(max = 32 ,message = "USEEPAY 订单号[reference]不能多于32位")
    private String reference;
    /**
     * max=64 pay； authorization; refund; capture;authorizationVoid;
     */
    @NotEmpty(message = "交易类型[transactionType]不能为空")
    private String transactionType;
}
