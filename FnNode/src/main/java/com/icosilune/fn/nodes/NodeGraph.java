/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.nodes;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.icosilune.fn.EvaluationContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

//  void evaluateGraph(EvaluationContext context) {
//
//  }

  /**
   * Called when the node has changed value outside the process of evaluation.
   */
  void onNodeUpdated(AbstractNode node) {
    // TODO: later this should just queue this node up,
    // so we don't necessarily update the whole graph when something changes (e.g.) in a UI thread.
    propagateChanges(ImmutableSet.of(node), new EvaluationContext());
  }

  public void addConnection(AbstractNode inNode, AbstractNode outNode, String outputSocketName, String inputSocketName) {
    inNode.addInputConnection(outNode, outputSocketName, inputSocketName);

    propagateChanges(ImmutableSet.of(inNode), new EvaluationContext());
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

  /**
   * Returns a multimap of each node to its output nodes.
   */
  private SetMultimap<AbstractNode, AbstractNode> getDownstreamConnections() {
    SetMultimap<AbstractNode, AbstractNode> downstreamConnections = HashMultimap.create();
    for (AbstractNode node : allNodes) {
      for (Connection connection : node.getInputConnections().values()) {
//        downstreamConnections.put(connection.getInputNode(), connection.getOutputNode());
        downstreamConnections.put(connection.getOutputNode(), connection.getInputNode());
      }
    }
    return downstreamConnections;
  }

  /**
   * Updates all context dependent nodes, and propagates values downward.
   */
  public void step() {
    Set<AbstractNode> contextDependentNodes = allNodes.stream()
            .filter(AbstractNode::isContextDependent)
            .collect(Collectors.toSet());

    propagateChanges(contextDependentNodes, new EvaluationContext());
  }

  private void propagateChanges(Set<AbstractNode> changedNodes, EvaluationContext context) {
    System.out.println("propagating changes: "+changedNodes);

    Set<AbstractNode> nodesToUpdate = changedNodes;

    Set<AbstractNode> updatedNodes = new HashSet();

    SetMultimap<AbstractNode, AbstractNode> downstreamConnections = getDownstreamConnections();

    // It is possible that we will have cycles in this graph,
    // so if there is a cycle, only update a node once.
    while(!nodesToUpdate.isEmpty()) {
      Set<AbstractNode> touchedNodes = new HashSet();

      for(AbstractNode node : nodesToUpdate) {
        System.out.println("evaluating: "+node);
        node.evaluate(context);
        touchedNodes.addAll(downstreamConnections.get(node));
      }

      updatedNodes.addAll(nodesToUpdate);
      touchedNodes.removeAll(updatedNodes);

      nodesToUpdate = touchedNodes;
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
