package com.useepay.exception;

import com.useepay.model.ErrorCode;

public class AuthenticationException extends UseePayException {
  private static final long serialVersionUID = 2L;

  public AuthenticationException(
          ErrorCode errorCode, String requestId, Integer statusCode, Throwable e) {
    super(errorCode, requestId,  statusCode, e);
  }
}
