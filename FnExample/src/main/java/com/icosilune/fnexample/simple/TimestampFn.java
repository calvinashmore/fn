/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.simple;

import com.google.common.collect.ImmutableMap;
import com.icosilune.fn.AbstractFn;
import com.icosilune.fn.EvaluationContext;
import com.icosilune.fn.FnType;
import java.util.Map;

/**
 *
 * @author ashmore
 */
public class TimestampFn extends AbstractFn{

  @Override
  public Map<String, FnType> getInputTypes() {
    return ImmutableMap.of();
  }

  @Override
  public Map<String, FnType> getOutputTypes() {
    return ImmutableMap.of("timestamp", FnType.fromString("double"));
  }

  @Override
  public Map<String, Object> evaluateWrapper(EvaluationContext context, Map<String, Object> args) {
    double timestamp = (Double) context.getValue("timestamp");
    return ImmutableMap.of("timestamp", timestamp);
  }

  @Override
  public boolean isContextDependent() {
    return true;
  }
}
