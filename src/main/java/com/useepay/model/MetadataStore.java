package com.useepay.model;

import com.useepay.exception.UseePayException;
import com.useepay.net.RequestOptions;

import java.util.Map;

/** Common interface for Stripe objects that can store metadata. */
public interface MetadataStore<T> {
  Map<String, String> getMetadata();

  MetadataStore<T> update(Map<String, Object> params) throws UseePayException;

  MetadataStore<T> update(Map<String, Object> params, RequestOptions options)
      throws UseePayException;
}
