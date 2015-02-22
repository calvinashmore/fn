/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn;

import com.google.auto.value.AutoValue;
import java.lang.reflect.Type;
import java.util.Objects;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

/**
 *
 * @author ashmore
 */
@AutoValue
public abstract class FnType {

  public abstract Type getType();

  public static FnType fromString(String s) {
    try {
      Type type = Class.forName(s);
      return new AutoValue_FnType(type);
    } catch (ClassNotFoundException ex) {
      throw new RuntimeException(ex);
    }
  }

  public boolean isIterable() {
    return false;
//    throw new UnsupportedOperationException();
  }



  // T is class
  // ****** there is java reflect Type which may be better than this
  // or, we could just keep a reference to that, and use additional stuff

  // denotes input/output type, maybe has case for error conditions, defaults, etc
  // class + maybe generics???
}
