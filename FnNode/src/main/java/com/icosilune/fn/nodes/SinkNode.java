/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.nodes;

import com.google.common.collect.ImmutableMap;
import com.icosilune.fn.EvaluationContext;
import com.icosilune.fn.FnType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A node that has one input and no outputs, which can be used for display,
 * or transmission to some other destination.
 */
public class SinkNode extends AbstractNode {

  public interface SinkNodeListener {
    public void updated(Object value);
  }

  private static final String IN_NAME = "in";
  private final FnType type;
  private final List<SinkNodeListener> listeners = new ArrayList<>();
  private Object value = null;

  public SinkNode(NodeGraph graph, FnType type) {
    super(graph);
    this.type = type;
  }

  /**
   * Get the value of the sink node.
   */
  public Object getValue() {
    return value;
  }

  @Override
  public Map<String, Socket> getInputSockets() {
    return ImmutableMap.of(IN_NAME, Socket.create(IN_NAME, type, Socket.SocketType.INPUT));
  }

  @Override
  public Map<String, Socket> getOutputSockets() {
    return ImmutableMap.of();
  }

  @Override
  public String getName() {
    return "sink";
  }

  @Override
  public boolean isContextDependent() {
    return false;
  }

  @Override
  public void evaluate(EvaluationContext context) {
    this.value = readInputs().get(IN_NAME);

    for (SinkNodeListener listener : listeners) {
      listener.updated(value);
    }
  }

  public void addListener(SinkNodeListener listener) {
    listeners.add(listener);
  }
}
