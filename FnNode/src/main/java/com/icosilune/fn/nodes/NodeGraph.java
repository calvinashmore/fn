/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.nodes;

import com.google.common.collect.ImmutableSet;
import com.icosilune.fn.EvaluationContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ashmore
 */
public class NodeGraph {

  private final Set<NodeGraphListener> listeners = new HashSet<>();

  private final Set<AbstractNode> allNodes = new HashSet<>();

  // ?? what is an output node, really?
//  private final List<AbstractNode> outputNodes = new ArrayList<>();

  public void addListener(NodeGraphListener listener) {
    listeners.add(listener);
  }

  public void removeListener(NodeGraphListener listener) {
    listeners.remove(listener);
  }

  public ImmutableSet<AbstractNode> getNodes() {
    return ImmutableSet.copyOf(allNodes);
  }

  // *******
  // Here's what we want to do:
  // the evaluation context updates, then the input nodes update, and propagate their values downward.

  // do not keep context, rather we evaluate the entire graph with a context
  //private EvaluationContext context;

  // getContextDependentNodes

  void evaluateGraph(EvaluationContext context) {

  }

  void onNodeUpdated(AbstractNode node) {
    // ???
  }

  public void addConnection(AbstractNode inNode, AbstractNode outNode, String outputSocketName, String inputSocketName) {
    inNode.addInputConnection(outNode, outputSocketName, inputSocketName);
  }

  public void addNode(AbstractNode node) {
    if (allNodes.add(node)) {
      listeners.forEach(listener -> listener.nodeChanged(this, node, NodeChangeType.ADDED));
    } else {
      throw new IllegalArgumentException();
    }
  }

  public void removeNode(AbstractNode node) {
    if (allNodes.remove(node)) {
      listeners.forEach(listener -> listener.nodeChanged(this, node, NodeChangeType.REMOVED));
    } else {
      throw new IllegalArgumentException();
    }
  }

  public enum NodeChangeType {
    ADDED,
    REMOVED,
  }

  public enum ConnectionChangeType {
    ADDED,
    REMOVED,
  }

  public interface NodeGraphListener {
    public void nodeChanged(NodeGraph graph, AbstractNode node, NodeChangeType change);
    public void connectionChanged(NodeGraph graph, Connection node, ConnectionChangeType change);
  }
}
