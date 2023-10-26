package com.useepay.param;

import com.alibaba.fastjson.annotation.JSONField;
import com.useepay.model.UseePayObject;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: useepay-java
 * @Author: zhi.wang
 * @CreateTime: 2023-09-27  14:50
 * @Description: TODO
 * @Version: 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentIntentCreateParams extends UseePayObject {
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
     * 订单信息
     */
    @NotNull(message = "订单信息[orderInfo]不能为空")
    @Valid
    private OrderInfo orderInfo;
    /**
     * 付款方信息
     */
    @NotNull(message = "付款方信息[payerInfo]不能为空")
    @Valid
    private PayerInfo payerInfo;
    /**
     * 用户信息，JSON 格式（详见 用户信息）
     */
    @NotNull(message = "用户信息[userInfo]不能为空")
    @Valid
    private UserInfo userInfo;
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
    private Map<String,Object> reserved;

    @Getter
    @Setter
    @Builder
    @EqualsAndHashCode(callSuper = false)
    public static class OrderInfo extends UseePayObject{
        /**
         * 订单标题
         */
        @NotEmpty(message = "订单标题[orderInfo.subject]不能为空")
        private String subject;
        /**
         * 商品信息列表
         */
        private List<GoodsInfo> goodsInfo;
        /**
         * 收货地址
         */
        @NotNull(message = "收货地址[orderInfo.shippingAddress]不能为空")
        @Valid
        private Address shippingAddress;

        public OrderInfo() {
        }

        public OrderInfo(String subject, List<GoodsInfo> goodsInfo, Address shippingAddress) {
            this.subject = subject;
            this.goodsInfo = goodsInfo;
            this.shippingAddress = shippingAddress;
        }

        public OrderInfo(String subject, Address shippingAddress) {
            this(subject,null,shippingAddress);
        }

        public void addGoodsInfo(GoodsInfo goods){
            if(null == this.goodsInfo)
                this.goodsInfo = new ArrayList<>();
            this.goodsInfo.add(goods);
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class GoodsInfo extends UseePayObject {
        /**
         * 	sku id
         */
        @NotEmpty(message = "商品id不能为空")
        private String id;
        /**
         * 商品名
         */
        @NotEmpty(message = "商品名name不能为空")
        private String name;
        /**
         * 商品描述
         */
        @NotEmpty(message = "商品描述body不能为空")
        private String body;
        /**
         * 商品数量
         */
        @NotEmpty(message = "商品数量quantity不能为空")
        private Integer quantity;
        /**
         * 商品价格,必须大于0，单位为对应货币的最小
         * 货币单位参考货币单位一节
         */
        @NotEmpty(message = "商品price不能为空")
        private Double price;
        /**
         * 商品链接
         */
        private String url;
        /**
         * 商品图片
         */
        private String image;

        public GoodsInfo(String id, String name, String body, Integer quantity, Double price) {
            this(id,name,body,quantity,price,null,null);
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class Address extends UseePayObject {
        /**
         * 收货邮箱,必填
         */
        @NotEmpty(message = "address.email不能为空")
        @Length(max = 128,message = "address.email不能多于128位")
        @Email( message = "address.email格式不正确")
        private String email;

        /** 用户手机号 max=32 **/
        @Length(max = 32,message = "address.phoneNo不能多于32位")
        private String phoneNo;
        /**
         * 收货人名
         */
        @NotEmpty(message = "address.firstName不能为空")
        private String firstName;
        /**
         * 收货人姓
         */
        @NotEmpty(message = "address.lastName不能为空")
        private String lastName;
        /**
         * 收货街道地址
         */
        @NotEmpty(message = "address.street不能为空")
        private String street;
        /**
         * 收货街道地址和门牌号当street存在时可以不填
         */
        private String houseNo;

        /** ZIP or postal code. */
        /**
         * 邮编，如果该国家或地区由邮编必须传，否则可以不传
         */
        @NotEmpty(message = "postalCode不能为空")
        private String postalCode;


        /** City, district, suburb, town, or village. */
        /**
         * 城市
         */
        private String city;

        /** 省/州/地区, 需满足 ISO 3166-2 */
        @NotEmpty(message = "address.state不能为空")
        private String state;
        /**
         * Two-letter country code (<a href="https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2">ISO 3166-1
         * alpha-2</a>).
         */
        @NotEmpty(message = "country不能为空")
        @Length(max = 2,min = 2,message = "country必须为2位")
        private String country;

        public Address(String email, String firstName, String lastName, String street, String postalCode, String city, String state, String country) {
            this(email,null,firstName,lastName,street,null,postalCode,city,state,country);
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class PayerInfo extends UseePayObject{
        /**
         * 支付方式
         */
        private String paymentMethod;
        /**
         * 用户授权方式：cvv/threeds1.0/threeds2.0
         */
        private String authorizationMethod;
        /**
         * 账单地址
         */
        private Address billingAddress;
        /**
         * 持卡人名
         */
        private String firstName;
        /**
         * 持卡人名
         */
        private String lastName;
        /**
         * 3ds参数。强制走3ds时必传
         */
        private String threeDS2RequestData;
        /**
         * 付款卡号
         */
        private String cardNo;
        /**
         * 付款卡有效期月
         */
        private String expirationMonth;
        /**
         * 	付款卡有效年
         */
        private String expirationYear;
        /**
         * CVV
         */
        private String cvv;
        /**
         * 第三方
         */
        @JSONField(serialize = false)
        private ThreeDS2Request threeDS2Request;

        public PayerInfo(String paymentMethod, String authorizationMethod, Address billingAddress, String firstName, String lastName, ThreeDS2Request threeDS2Request) {
            this(paymentMethod,authorizationMethod,billingAddress,firstName,lastName,null == threeDS2Request ? null:threeDS2Request.toJson()
                    ,null,null,null,null,threeDS2Request);
        }

        public String getThreeDS2RequestData() {
            return null == threeDS2Request ? null:threeDS2Request.toJson();
        }

        public void setThreeDS2RequestData(String threeDS2RequestData) {
            this.threeDS2RequestData = threeDS2RequestData;
        }
    }

    /**
     * 3ds参数对象。强制走3ds时必传
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class ThreeDS2Request extends UseePayObject{
        /**
         * 1.app 2. browser
         */
        private String deviceChannel;
        /**
         * 浏览器 accept header 的值
         */
        private String acceptHeader;
        /**
         * 用户浏览器的颜色深度，单位是位/像素。 可接受的值：1、4、8、15、16、24、30、32、48
         */
        private String colorDepth;
        /**
         * 用户浏览器是否支持 Java 执行：true\false
         */
        private String javaEnabled;
        /**
         * 用户浏览器是否支持 javaScript 执行：true\false。如果不传，默认为支持
         */
        private String javaScriptEnabled;
        /**
         * 用户浏览器语言
         */
        private String language;
        /**
         * 用户浏览器屏幕高度，单位是位/像素
         */
        private String screenHeight;
        /**
         * 用户浏览器屏幕高度，单位是位/像素
         */
        private String screenWidth;
        /**
         * 用户浏览器时间与 UTC 时间之差，单位是分钟
         */
        private String timeZoneOffset;
        /**
         * 用户浏览器代理
         */
        private String userAgent;
        /**
         * 3ds挑战窗口大小,可接受的值:1. 250x400 2. 390x400 3. 500x600 4. 500x600 5. full_screen
         */
        private String challengeWindowSize;
        /**
         * 3ds挑战偏好设置,可接受的值:1. no_preference: Don't have any preferences related to the Challengeflow
         * 2. no_challenge_requested: I prefer that a Challenge flow does not take place
         * 3. preference: A request for the Challenge flow to take place
         * 4. mandate: A Challenge flow must take place to fulfill a mandate
         */
        private String challengeIndicator;
        /**
         * 3D回调URL
         */
        private String threeDSMethodCallbackUrl;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class UserInfo extends UseePayObject {

        /** 用户id 非必填 max=128  **/
        @Length(max = 128,message = "userInfo.userId不能多于128位")
        private String userId;

        /** IPv4或IPv6 必填 max=128  **/
        @NotEmpty(message = "ip不能为空")
        @Length(max = 128,message = "userInfo.ip不能多于128位")
        private String ip;

        /** 用户邮箱 必填  max=128 **/
        @NotEmpty(message = "userInfo.email不能为空")
        @Length(max = 128,message = "userInfo.email不能多于128位")
        @Email( message = "userInfo.email格式不正确")
        private String email;

        /** 用户手机号 max=32 **/
        @Length(max = 32,message = "userInfo.phoneNo不能多于32位")
        private String phoneNo;

        public UserInfo(String ip, String email) {
            this(null,ip,email,null);
        }
    }
}
