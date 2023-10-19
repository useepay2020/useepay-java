package com.useepay;

import com.useepay.model.SignType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * UseePay基础对象
 */
public abstract class UseePay {
  public static final int DEFAULT_CONNECT_TIMEOUT = 30 * 1000;
  public static final int DEFAULT_READ_TIMEOUT = 80 * 1000;
  public static final String API_VERSION = "1.0";
  /**
   * 环境变量
   */
  public static volatile String environment;

  // Note that URLConnection reserves the value of 0 to mean "infinite
  // timeout", so we use -1 here to represent an unset value which should
  // fall back to a default.
  private static volatile int connectTimeout = -1;
  private static volatile int readTimeout = -1;

  private static volatile int maxNetworkRetries = 0;

  private static volatile Map<String, String> appInfo = null;


  public static String getApiBase() {
    if(Objects.isNull(environment)){
       throw new RuntimeException("请设置运行环境:SANDBOX or PRODUCTION");
    }
    APIEnvironment apiEnvironment = APIEnvironment.get(environment);
    if(Objects.isNull(apiEnvironment)) {
      throw new RuntimeException("不支持的运行环境,仅支持SANDBOX or PRODUCTION");
    }
    return apiEnvironment.getApiBase();
  }


  /**
   * Returns the connection timeout.
   *
   * @return timeout value in milliseconds
   */
  public static int getConnectTimeout() {
    if (connectTimeout == -1) {
      return DEFAULT_CONNECT_TIMEOUT;
    }
    return connectTimeout;
  }

  /**
   * Sets the timeout value that will be used for making new connections to the Stripe API (in
   * milliseconds).
   *
   * @param timeout timeout value in milliseconds
   */
  public static void setConnectTimeout(final int timeout) {
    connectTimeout = timeout;
  }

  /**
   * Returns the read timeout.
   *
   * @return timeout value in milliseconds
   */
  public static int getReadTimeout() {
    if (readTimeout == -1) {
      return DEFAULT_READ_TIMEOUT;
    }
    return readTimeout;
  }

  /**
   * Sets the timeout value that will be used when reading data from an established connection to
   * the Stripe API (in milliseconds).
   *
   * <p>Note that this value should be set conservatively because some API requests can take time
   * and a short timeout increases the likelihood of causing a problem in the backend.
   *
   * @param timeout timeout value in milliseconds
   */
  public static void setReadTimeout(final int timeout) {
    readTimeout = timeout;
  }

  /**
   * Returns the maximum number of times requests will be retried.
   *
   * @return the maximum number of times requests will be retried
   */
  public static int getMaxNetworkRetries() {
    return maxNetworkRetries;
  }

  /**
   * Sets the maximum number of times requests will be retried.
   *
   * @param numRetries the maximum number of times requests will be retried
   */
  public static void setMaxNetworkRetries(final int numRetries) {
    maxNetworkRetries = numRetries;
  }

  /**
   * Sets information about your application. The information is passed along to Stripe.
   *
   * @param appId ID of your application (e.g. "myawesomeapp.info")
   * @param appKey
   * @param signType sign ar (e.g. "MD5")
   * @param merchantNo Your UseePay Merchant NO (e.g. "500000000001111111")
   */
  public static void setAppInfo(String appId, String appKey, SignType signType, String merchantNo) {
    if (appInfo == null) {
      appInfo = new HashMap<String, String>();
    }
    appInfo.put("appId", appId);
    appInfo.put("appKey", appKey);
    appInfo.put("signType", signType.name());
    appInfo.put("merchantNo", merchantNo);
  }

  public static Map<String, String> getAppInfo() {
    return appInfo;
  }

  /**
   * 根据key获取App的参数
   * @param key
   * @return
   */
  public static String getAppParamByKey(String key){
    if(Objects.isNull(appInfo))
      return null;
    return appInfo.get(key);
  }

  /**
   * 获取商户号
   * @return
   */
  public static String getMerchantNo(){
    return getAppParamByKey("merchantNo");
  }

  /**
   * 获取App的编号
   * @return
   */
  public static String getAppId(){
    return getAppParamByKey("appId");
  }

  /**
   * 获取App的密钥
   * @return
   */
  public static String getAppKey(){
    return getAppParamByKey("appKey");
  }

  /**
   * 获取签名类型
   * @return
   */
  public static String getSignType(){
    return getAppParamByKey("signType");
  }
}
