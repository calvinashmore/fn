/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.nodes;

import com.icosilune.fn.FnType;
import java.util.Map;
import java.util.Set;

/**
 * Represents a node.
 * Is all mutable and stuff.
 */
public abstract class AbstractNode {

  // need something

  private Map<String, Object> evaluatedOutputs;
  private Map<String, Connection> inputConnections;

  public abstract Map<String, Socket> getInputSockets();
  public abstract Map<String, Socket> getOutputSockets();

//  public abstract Map<String, FnType> getInputSockets();
//  public abstract Map<String, FnType> getOutputSockets();

}
