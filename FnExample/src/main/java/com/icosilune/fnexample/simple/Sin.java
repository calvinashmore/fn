/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.simple;

import com.icosilune.fn.AbstractFn;
import com.icosilune.fn.Fn;

/**
 *
 * @author ashmore
 */
@Fn
public abstract class Sin extends AbstractFn {
  public double evaluate(double x) {
    return Math.sin(x);
  }
}
