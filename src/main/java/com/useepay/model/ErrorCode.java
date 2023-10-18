package com.useepay.model;

public enum ErrorCode {
    E0000("0000","Approved or completed successfully","受理成功"),
    E0053("0053","Payment failed, blocked by payment company risk control system","付款失败，被付款公司风险控制系统所阻止"),
    E1001("1001","Invalid parameter","参数错误或不合法，具体参数错误原因"),
    ;
    private String code;
    private String message_en;
    private String message_cn;

    ErrorCode(String code, String message_en, String message_cn) {
        this.code = code;
        this.message_en = message_en;
        this.message_cn = message_cn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage_en() {
        return message_en;
    }

    public void setMessage_en(String message_en) {
        this.message_en = message_en;
    }

    public String getMessage_cn() {
        return message_cn;
    }

    public void setMessage_cn(String message_cn) {
        this.message_cn = message_cn;
    }
}
