/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn;

import com.google.auto.value.AutoValue;
import com.google.common.reflect.TypeToken;
import org.jparsec.java.TypeParser;

/**
 *
 * @author ashmore
 */
@AutoValue
public abstract class FnType {

  public abstract TypeToken<?> getType();

  public static FnType fromString(String s) {
    return new AutoValue_FnType(new TypeParser().parse(s));
  }

  public boolean isIterable() {
    return false;
  }

  public final boolean isAssignableFrom(FnType other) {
    return getType().isAssignableFrom(other.getType());
  }
}
