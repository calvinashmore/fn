/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn;

import java.util.Map;

/**
 *
 * @author ashmore
 */
public abstract class AbstractFn {

  public abstract Map<String, FnType> getInputTypes();
  public abstract Map<String, FnType> getOutputTypes();

  /**
   * Types that use implement {@link Fn} and implement {@link AbstractFn} should
   * implement {@code evaluate(arguments...)}, which returns the single output type.
   * if there is a single output type. If there are multiple output types, then {@code evaluate}
   * should return a {@code Map<String, Object>}
   *
   *
   * ******* what if the output type is a map?
   * ** Also, how do we know the names ahead of time?
   */
  public abstract Map<String, Object> evaluateWrapper(
          EvaluationContext context, Map<String, Object> args);

  public boolean isContextDependent() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
