package com.useepay;

import com.useepay.exception.UseePayException;
import com.useepay.model.PaymentIntent;
import com.useepay.param.PaymentIntentCreateParams;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: useepay-java
 * @Author: zhi.wang
 * @CreateTime: 2023-09-27  15:41
 * @Description: TODO
 * @Version: 1.0
 */
public class CreatePaymentTest {

    public static void main(String[] args) {
        //设置环境SANDBOX,PRODUCTION
        UseePay.environment = "SANDBOX";
        //设置APP相关信息
        UseePay.setAppInfo("www.sina.com","6666","MD5","520000001111101");

        //订单信息
        PaymentIntentCreateParams.OrderInfo orderInfo = PaymentIntentCreateParams.OrderInfo.builder()
                .subject("订单标题")
                //shippingAddress
                .shippingAddress(PaymentIntentCreateParams.Address.builder()
                        .email("haile1y@useepay.com")
                        .phoneNo("13524189698")
                        .firstName("Victor")
                        .lastName("Yang")
                        .street("Heathcoat House, 20 Savile Row")
                        .postalCode("W1S 3PR")
                        .city("London")
                        .state("LND")
                        .country("GB")
                        .build())
                .build();
        //添加商品1
        orderInfo.addGoodsInfo(PaymentIntentCreateParams.GoodsInfo.builder()
                .id("sku00001")
                .name("商品名称1")
                .quantity(1)
                .price(12.34)
                .body("商品描述1")
                .url("http://ur1")
                .build());
        //添加商品2
        orderInfo.addGoodsInfo(PaymentIntentCreateParams.GoodsInfo.builder()
                .id("sku00002")
                .name("商品名称2")
                .quantity(3)
                .price(12.34)
                .body("商品描述2")
                .url("http://ur12")
                .build());
        //付款方信息
        PaymentIntentCreateParams.PayerInfo payerInfo = PaymentIntentCreateParams.PayerInfo.builder()
                .paymentMethod("credit_card")//支付方式
                /**
                 * cvv （普通交易）
                 * threeds2.0 (3D)
                 */
                .authorizationMethod("cvv")
                //账单地址,根据实际情况可能跟收货地址一致
                .billingAddress(PaymentIntentCreateParams.Address.builder()
                        .email("haile1y@useepay.com")
                        .phoneNo("13524189698")
                        .firstName("Victor")
                        .lastName("Yang")
                        .street("Heathcoat House, 20 Savile Row")
                        .postalCode("W1S 3PR")
                        .city("London")
                        .state("LND")
                        .country("GB")
                        .build())
                .firstName("Victor")
                .lastName("lastName")
                /**
                 * TODO:3ds请求参数
                 */
                .build();
        //用户信息
        PaymentIntentCreateParams.UserInfo userInfo = PaymentIntentCreateParams.UserInfo.builder()
                .ip("103.25.65.178")
                .userId("victor1")
                .email("dynam1ic_3d@useepay.com")
                .build();

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .transactionId("m0000000000012201") //商户订单号，建议每次请求的 transactionId 唯一，有利于后续订单查询和对账
                .transactionType("pay")//交易类型
                .amount("120.12")//订单金额（必须大于 0），单位为对应币种的最小货币单位(详见 ISO 4217)
                .currency("USD")//3 位 ISO 大写字母货币代码(详见 ISO 4217)
                .orderInfo(orderInfo) //订单信息
                .payerInfo(payerInfo)//付款方信息
                .userInfo(userInfo)// 消费者信息
                .build();
        //预留字段,定义平台相关信息
        Map<String,Object> reserved = new HashMap<>();
        reserved.put("pluginName","Magento2");
        reserved.put("pluginVersion","1.0");
        params.setReserved(reserved);

        try {
            //生产支付
            PaymentIntent paymentIntent = PaymentIntent.create(params);

        } catch (UseePayException e) {
            e.printStackTrace();
        }
    }
}
