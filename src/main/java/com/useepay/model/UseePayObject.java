package com.useepay.model;

import com.alibaba.fastjson.JSON;
import com.useepay.net.UseePayResponse;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 */
public abstract class UseePayObject implements UseePayObjectInterface {
  //验证器对象
  private static Validator validator;

  private transient UseePayResponse lastResponse;

  @Override
  public String toString() {
    return String.format(
            "<%s@%s id=%s> JSON: %s",
            this.getClass().getName(),
            System.identityHashCode(this),
            this.getIdString(),
            JSON.toJSONString(this));
  }

  /**
   * 转换为JSON字符串
   * @return
   */
  public String toJson(){
    return JSON.toJSONString(this);
  }

  @Override
  public UseePayResponse getLastResponse() {
    return lastResponse;
  }

  @Override
  public void setLastResponse(UseePayResponse response) {
    this.lastResponse = response;
  }

  private Object getIdString() {
    try {
      Field idField = this.getClass().getDeclaredField("id");
      return idField.get(this);
    } catch (SecurityException e) {
      return "";
    } catch (NoSuchFieldException e) {
      return "";
    } catch (IllegalArgumentException e) {
      return "";
    } catch (IllegalAccessException e) {
      return "";
    }
  }

  protected static boolean equals(Object a, Object b) {
    return a == null ? b == null : a.equals(b);
  }

  /**
   * 对象验证
   * @param t
   * @param errors
   * @return
   * @param <T>
   */
  public static  <T> boolean validate(T t, List<String> errors){
    if(Objects.isNull(errors))
      throw new RuntimeException("入参errors不能为空");
    if(null == validator)
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<T>> set = validator.validate(t);
    if(null == set || set.size() ==0)
      return true;
    for (ConstraintViolation<T> item: set) {
        errors.add(item.getMessage());
    }
    return false;
  }

  /**
   * 将字符串转为Map
   * @param params
   * @return
   */
  public static Map<String, Object> paramsToMap(String params) {
    return params == null ? null : JSON.parseObject(params,Map.class);
  }
}
