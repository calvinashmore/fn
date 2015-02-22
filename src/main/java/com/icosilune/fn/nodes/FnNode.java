/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.nodes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.icosilune.fn.AbstractFn;
import com.icosilune.fn.EvaluationContext;
import com.icosilune.fn.FnType;
import java.util.Map;

/**
 * This is a node that uses a
 */
public class FnNode extends AbstractNode {

  private final AbstractFn fn;
  private final ImmutableMap<String, Socket> inputSockets;
  private final ImmutableMap<String, Socket> outputSockets;

  public FnNode(NodeGraph graph, AbstractFn fn) {
    super(graph);
    this.fn = fn;

    ImmutableMap.Builder<String, Socket> inputSockets = ImmutableMap.builder();
    ImmutableMap.Builder<String, Socket> outputSockets = ImmutableMap.builder();

    for (Map.Entry<String, FnType> inputEntry : fn.getInputTypes().entrySet()) {
      inputSockets.put(inputEntry.getKey(),
              Socket.create(inputEntry.getKey(), inputEntry.getValue(), Socket.SocketType.INPUT));
    }
    for (Map.Entry<String, FnType> outputEntry : fn.getOutputTypes().entrySet()) {
      outputSockets.put(outputEntry.getKey(),
              Socket.create(outputEntry.getKey(), outputEntry.getValue(), Socket.SocketType.OUTPUT));
    }
    this.inputSockets = inputSockets.build();
    this.outputSockets = outputSockets.build();
  }

  @Override
  public String getName() {
    return fn.getClass().getSimpleName();
  }

  @Override
  public ImmutableMap<String, Socket> getInputSockets() {
    return inputSockets;
  }

  @Override
  public ImmutableMap<String, Socket> getOutputSockets() {
    return outputSockets;
  }

  @Override
  public boolean isContextDependent() {
    return fn.isContextDependent();
  }

  @Override
  public void evaluate(EvaluationContext context) {
    setOutputs(fn.evaluateWrapper(context, readInputs()));
    getGraph().onNodeUpdated(this);
  }
}
