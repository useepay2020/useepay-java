package com.useepay.exception;

import com.useepay.model.ErrorCode;

public class ApiException extends UseePayException {
  private static final long serialVersionUID = 2L;

  public ApiException(
          ErrorCode errorCode, String requestId, Integer statusCode,Throwable e) {
    super(errorCode, requestId,  statusCode, e);
  }
}
