/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn;

/** eventually, this will have stuff about the environment.
  * So for a rendering application, it could be x,y coords. It could also be things like the current time,
  * local variables, or other such things.
  *
  */
public interface EvaluationContext {

  public Object getValue(String key);
}
