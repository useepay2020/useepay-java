package com.useepay.net;

import java.util.Map;

/**
 * Super class to all api request params objects. It exposes a convenient method converting the
 * typed parameter into the legacy support of untyped {@code Map<String, Object>} param.
 */
public abstract class ApiRequestParams {

    /**
     * Convert `this` api request params to an untyped map. The conversion is specific to api request
     * params object. Please see documentation in {@link
     */
    public Map<String, Object> toMap() {
        return null;
    }

    /**
     * Convert `params` api request params to an untyped map. The conversion is specific to api
     * request params object. Please see documentation in {@link
     */
    public static Map<String, Object> paramsToMap(ApiRequestParams params) {
        if (params == null) {
            return null;
        }
        return params.toMap();
    }
}
