/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.nodes;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.icosilune.fn.EvaluationContext;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a node.
 * Is all mutable and stuff.
 */
public abstract class AbstractNode {

  private Map<String, Object> evaluatedOutputs = new HashMap<>();
  private Map<String, Connection> inputConnections = new HashMap<>();

  // these will not change over the
  public abstract Map<String, Socket> getInputSockets();
  public abstract Map<String, Socket> getOutputSockets();

  public abstract String getName();

  private final NodeGraph graph;

  public AbstractNode(NodeGraph graph) {
    this.graph = graph;
  }

  public Map<String, Connection> getInputConnections() {
    return ImmutableMap.copyOf(inputConnections);
  }

  public NodeGraph getGraph() {
    return graph;
  }

  // do we want to expose this logic here, or in the node?
  public void addInputConnection(AbstractNode otherNode, String outputSocketName, String inputSocketName) {
    Preconditions.checkArgument(getInputSockets().containsKey(inputSocketName));
    Preconditions.checkArgument(otherNode.getOutputSockets().containsKey(outputSocketName));

    if(inputConnections.containsKey(inputSocketName)) {
      // notify that this connection is being removed
    }

    Connection connection = Connection.create(this, otherNode, inputSocketName, outputSocketName);

    inputConnections.put(inputSocketName, connection);
    // notify that connection was established?
    // notify other node, too?

    // ******* seems like listeners belong on the graph.
  }

  protected final void setOutputs(Map<String,Object> outputs) {
    // reassign?
    this.evaluatedOutputs = outputs;
  }

  public final Object getOutput(String socketName) {
    Preconditions.checkArgument(getOutputSockets().containsKey(socketName),
            "Node %s does not contain output socket %s", getName(), socketName);
    return evaluatedOutputs.get(socketName);
  }

  private Object getDefaultInput(String inputSocketName) {
    // TODO: replace with something better.
    return null;
  }

  protected final Map<String, Object> readInputs() {
    return Maps.transformEntries(inputConnections, (inputSocketName, connection) ->
            connection == null ? getDefaultInput(inputSocketName) :
            connection.getOutputNode().getOutput(connection.getOutputSocket().getName()));
  }

  /**
   * Whether this node relies on context. This must not be mutable.
   */
  public abstract boolean isContextDependent();

  // called when inputs have been updated, and this should be re-evaluated,
  // or if this is context dependent, this is called when the context is updated.

  // ******** is this how we want to do it?
  public abstract void evaluate(EvaluationContext context);

  // alternatives:
  // could: we track output connections, and update them, or notify them. (nodes are listeners)
  // or: NodeGraph is responsible for tracking updating and stuff

  // maybe have listeners on some nodes?
}
