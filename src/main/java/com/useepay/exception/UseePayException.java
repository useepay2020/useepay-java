package com.useepay.exception;

import com.useepay.model.ErrorCode;
import com.useepay.model.UseePayError;
import lombok.Getter;
import lombok.Setter;
/**
 * @BelongsProject: useepay-java
 * @Author: zhi.wang
 * @CreateTime: 2023-09-26  10:05
 * @Description: TODO
 * @Version: 1.0
 */
@Getter
public abstract class UseePayException extends Exception {
  private static final long serialVersionUID = 2L;

  /** The error resource returned by UseePay's API that caused the exception. */
  // transient so the exception can be serialized, as UseePayObject does not
  // implement Serializable
  @Setter transient UseePayError UseePayError;

  /**
   * Returns the error code of the response that triggered this exception. For {@link ApiException}
   * the error code will be equal to {@link UseePayError#getCode()}.
   *
   * @return the string representation of the error code.
   */
  private String code;

  /**
   * Returns the request ID of the request that triggered this exception.
   *
   * @return the request ID.
   */
  private String requestId;

  /**
   * Returns the status code of the response that triggered this exception.
   *
   * @return the status code.
   */
  private Integer statusCode;

  protected UseePayException(ErrorCode errorCode, String requestId, Integer statusCode) {
    this(errorCode.getCode(), errorCode.getMessage_en(), requestId, statusCode, null);
  }

  protected UseePayException(ErrorCode errorCode, String requestId, Integer statusCode, Throwable e) {
    this(errorCode.getCode(), errorCode.getMessage_en(), requestId, statusCode, e);
  }

  /** Constructs a new UseePay exception with the specified details. */
  protected UseePayException(
          String code,String message, String requestId, Integer statusCode, Throwable e) {
    super(message, e);
    this.code = code;
    this.requestId = requestId;
    this.statusCode = statusCode;
  }

  /**
   * Returns a description of the exception, including the HTTP status code and request ID (if
   * applicable).
   *
   * @return a string representation of the exception.
   */
  @Override
  public String getMessage() {
    String additionalInfo = "";
    if (code != null) {
      additionalInfo += "; code: " + code;
    }
    if (requestId != null) {
      additionalInfo += "; request-id: " + requestId;
    }
    return super.getMessage() + additionalInfo;
  }

  /**
   * Returns a description of the user facing exception
   *
   * @return a string representation of the user facing exception.
   */
  public String getUserMessage() {
    return super.getMessage();
  }
}
