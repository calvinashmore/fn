/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Annotation, applied to a class of type AbstractFn
 * @author ashmore
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Fn {

  /**
   * If this is specified, we assume multi-out. The class implementing AbstractFn must
   * return {@code Map<String, Object>}, and the outs will be set that way.
   */
  OutParam[] out() default {};

  public @interface OutParam {
    String name();
    String type(); // eg "java.util.Set<String>", "int", etc
    // hopefully we can parse this....
  }
}
