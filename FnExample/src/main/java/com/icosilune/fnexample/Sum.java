/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample;

import com.icosilune.fn.AbstractFn;
import com.icosilune.fn.Fn;

/**
 *
 * @author ashmore
 */
@Fn
public abstract class Sum extends AbstractFn {

//  public double evaluate(Iterable<Double> input) {
//    double sum = 0;
//    for(Double v : input) {
//      sum += v;
//    }
//    return sum;
//  }

  public Double evaluate(Double x, Double y) {
    return x + y;
  }
}