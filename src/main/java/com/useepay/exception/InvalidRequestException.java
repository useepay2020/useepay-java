package com.useepay.exception;

import com.useepay.model.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidRequestException extends UseePayException {
  private static final long serialVersionUID = 2L;

  private final String param;

  public InvalidRequestException(
          ErrorCode errorCode, String requestId, Integer statusCode,String param,
          Throwable e) {
    super(errorCode, requestId,statusCode,e);
    this.param = param;
  }
}
