package com.useepay;

/**
 * API的环境配置
 */
public enum APIEnvironment {
    SANDBOX("UAT","https://pay-gateway1.uat.useepay.com"),
    PRODUCTION("PRODUCTION","https://pay-gateway.useepay.com");
    private String env;
    private String apiBase;

    APIEnvironment(String env, String apiBase) {
        this.env = env;
        this.apiBase = apiBase;
    }

    /**
     * 获取环境配置
     * @param env
     * @return
     */
    public static APIEnvironment get(String env){
        for (APIEnvironment apiEnv:APIEnvironment.values()) {
            if(apiEnv.getEnv().equalsIgnoreCase(env))
                return apiEnv;
        }
        return null;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getApiBase() {
        return apiBase;
    }

    public void setApiBase(String apiBase) {
        this.apiBase = apiBase;
    }
}
