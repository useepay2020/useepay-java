package com.useepay;

import com.useepay.exception.UseePayException;
import com.useepay.model.PaymentIntent;
import com.useepay.model.SignType;
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
        UseePay.setAppInfo("www.pay.com","-----BEGIN PRIVATE KEY-----\n" +
                        "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALSaH29o7g4qCCx7\n" +
                        "uKdq7LUN01LYOwzDkvbYAeuWpdbHpM32w/ZtD2n3245KQBtwEWN370k8ag0lx0rR\n" +
                        "lVh9LyeM7ggC70qv0f57lY/J/o7EgP8VB8d1AZVoOsAhnJYI9os0pZGG/0eyeGvB\n" +
                        "KzEteY0Vd6fKnNxZaNenXwXbILbhAgMBAAECgYEAsYS01WuMYvvJoP/TVyxwkt6O\n" +
                        "E2ZbVIaAVIWqDYm8ZMtEIPcnU3eLqRtqAgjVzqJvVRg0agYqL9YseExdweb6ivzp\n" +
                        "buaxi6QJ/bGUsYgAzoKvvJmhfoRMpmlXUgLz7mNsmhT+ERgT5FVKmLwdD3EJNShs\n" +
                        "4m3H+4oJhvhtVGAymmECQQDaKLuxAqjuDEhyyNqWgiUNudqe8LZ86pQzOvbKGr7x\n" +
                        "nholEajaTLPaVShYNh64Ymiivy8sa2hoUbiCJelh62ldAkEA0+2u4DWR9TChPGWo\n" +
                        "C1S1z51ctmG+9idiBdIEGdicsvskF9nkNxPskGkWUVIM9Pj6hf6aCXmPh40lk1nC\n" +
                        "P6b3VQJAS1PHQx58//0jwuaRe2iirLOgxkKtcEJ7O5k/f5dumPUTZ/tKB152t4ux\n" +
                        "64t8XgQ0xwUmn7rSiTq2b9HxXMsxTQJBAJPQPZ6n/otzRaIojRKKHN0XtqiGeHib\n" +
                        "c2T33GBikrjLLZttIB3CScdXZHeHsP3UAnge4jUlkhRLQCAMBM38mJkCQAKCIZE2\n" +
                        "zota9hjqRQRDrEK0c1ns+xFbDmydeTAakfj+/eVjZ434boAhZdqIAIQ/A8onOvbm\n" +
                        "Dye7zg86gXfeNwA=\n" +
                        "-----END PRIVATE KEY-----"
                , SignType.RSA,"500000000007362");

//         UseePay.setAppInfo("www.pay.com","d8UdCDvsamd9rON7K3MMijF9pMUTChQBvcj0Sdd4uNZBvbv1m1Hdw0yj3bOzHL9J2NHB9kA20F"
//                             +"3EQ580HichCKgFq0ryAXM5fhPW7N9GqunpzBw0uONhhNHTbMn9RXnZ"
//                            , SignType.MD5,"500000000007362");
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
                .cardNo("4200000000000000")
                .expirationMonth("10")
                .expirationYear("24")
                .cvv("565")
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
                .amount("1234")//订单金额（必须大于 0），单位为对应币种的最小货币单位(详见 ISO 4217)参见 https://openapi-useepay.apifox.cn/doc-1810037
                              //传给 UseePay 的 amount 需要做一次转换，假设你的网站使用的货币单位为 USD, 商品价格为 12.11, 那么你应该将价格转成 12.11 * 10^2(USD 的最小单位是2),
                             // 即 1211, 传给 UseePay.
                .currency("USD")//3 位 ISO 大写字母货币代码(详见 ISO 4217)
                .orderInfo(orderInfo) //订单信息
                .payerInfo(payerInfo)//付款方信息
                .userInfo(userInfo)// 消费者信息
                .transactionExpirationTime("30")
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
