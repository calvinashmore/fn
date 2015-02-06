/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn;

import java.lang.reflect.Type;

/**
 *
 * @author ashmore
 */
public class FnType {

  Type type;

  public static FnType fromString(String s) {
    throw new UnsupportedOperationException();
  }

  public boolean isIterable() {
    throw new UnsupportedOperationException();
  }

  // T is class
  // ****** there is java reflect Type which may be better than this
  // or, we could just keep a reference to that, and use additional stuff

  // denotes input/output type, maybe has case for error conditions, defaults, etc
  // class + maybe generics???
}
