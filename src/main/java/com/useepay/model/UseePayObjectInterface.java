package com.useepay.model;

import com.useepay.net.UseePayResponse;

public interface UseePayObjectInterface {
  public UseePayResponse getLastResponse();

  public void setLastResponse(UseePayResponse response);
}
