/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.nodes;

import com.google.common.collect.ImmutableMap;
import com.icosilune.fn.EvaluationContext;
import com.icosilune.fn.FnType;
import java.util.Map;

/**
 *
 * @author ashmore
 */
public class ConstantNode extends AbstractNode {

  private static final String OUTPUT_NAME = "output";
  private final Map<String, Socket> outputSocket;
  private Map<String, Object> outputValue;

  public ConstantNode(NodeGraph graph, FnType type, Object initialValue) {
    super(graph);
    this.outputSocket = ImmutableMap.of(OUTPUT_NAME,
            Socket.create(OUTPUT_NAME, type, Socket.SocketType.OUTPUT));

    setValue(initialValue);
  }

  public FnType getType() {
    return getOutputSockets().get(OUTPUT_NAME).getType();
  }

  public final void setValue(Object value) {
    // TODO: check type.
    outputValue = ImmutableMap.of(OUTPUT_NAME, value);
    setOutputs(outputValue);

    getGraph().onNodeUpdated(this);
  }

  @Override
  public Map<String, Socket> getInputSockets() {
    return ImmutableMap.of();
  }

  @Override
  public Map<String, Socket> getOutputSockets() {
    return outputSocket;
  }

  @Override
  public String getName() {
    return "constant";
  }

  @Override
  public boolean isContextDependent() {
    return false;
  }

  @Override
  public void evaluate(EvaluationContext context) {
    setOutputs(outputValue);
  }
}
