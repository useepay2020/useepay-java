package com.useepay.net;

/** A response from Stripe's API, with body represented as a String. */
public class UseePayResponse extends AbstractUseePayResponse<String> {
  /**
   * Initializes a new instance of the {@link UseePayResponse} class.
   *
   * @param code the HTTP status code of the response
   * @param headers the HTTP headers of the response
   * @param body the body of the response
   * @throws NullPointerException if {@code headers} or {@code body} is {@code null}
   */
  public UseePayResponse(int code, HttpHeaders headers, String body) {
    super(code, headers, body);
  }
}
