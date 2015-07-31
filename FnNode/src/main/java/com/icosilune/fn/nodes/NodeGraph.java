/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.nodes;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import com.icosilune.fn.EvaluationContext;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author ashmore
 */
public class NodeGraph {

  private final Set<NodeGraphListener> listeners = new HashSet<>();
  private final Set<AbstractNode> allNodes = new HashSet<>();
  private final EvaluationContextFactory contextFactory;

  public NodeGraph(EvaluationContextFactory contextFactory) {
    this.contextFactory = contextFactory;
  }

  public void addListener(NodeGraphListener listener) {
    listeners.add(listener);
  }

  public void removeListener(NodeGraphListener listener) {
    listeners.remove(listener);
  }

  public ImmutableSet<AbstractNode> getNodes() {
    return ImmutableSet.copyOf(allNodes);
  }

  /**
   * Called when the node has changed value outside the process of evaluation.
   */
  public void onNodeUpdated(AbstractNode node) {
    // TODO: later this should just queue this node up,
    // so we don't necessarily update the whole graph when something changes (e.g.) in a UI thread.
    propagateChanges(ImmutableSet.of(node), contextFactory.get());
  }

  public void addConnection(NodeAndSocket socket1, NodeAndSocket socket2) {
    Preconditions.checkArgument(socket1.getSocket().canConnectTo(socket2.getSocket()),
            "Sockets %s and %s cannot connect", socket1.getSocket(), socket2.getSocket());

    NodeAndSocket inputSocket;
    NodeAndSocket outputSocket;
    if (socket1.getSocket().getSocketType() == Socket.SocketType.INPUT) {
      inputSocket = socket1;
      outputSocket = socket2;
    } else {
      inputSocket = socket2;
      outputSocket = socket1;
    }

    addConnection(inputSocket.getNode(), outputSocket.getNode(),
            outputSocket.getSocket().getName(), inputSocket.getSocket().getName());
  }

  public void addConnection(AbstractNode inNode, AbstractNode outNode, String outputSocketName, String inputSocketName) {
    inNode.addInputConnection(outNode, outputSocketName, inputSocketName);

    propagateChanges(ImmutableSet.of(inNode), contextFactory.get());
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
        downstreamConnections.put(connection.getOutputNode(), connection.getInputNode());
      }
    }
    return downstreamConnections;
  }

  /**
   * Updates all context dependent nodes, and propagates values downward.
   */
  public void step() {
    // TODO: also handle cycles
    Set<AbstractNode> contextDependentNodes = allNodes.stream()
            .filter(AbstractNode::isContextDependent)
            .collect(Collectors.toSet());

    propagateChanges(contextDependentNodes, contextFactory.get());
  }

  private void propagateChanges(Set<AbstractNode> changedNodes, EvaluationContext context) {
    Set<AbstractNode> nodesToUpdate = changedNodes;

    Set<AbstractNode> updatedNodes = new HashSet();

    SetMultimap<AbstractNode, AbstractNode> downstreamConnections = getDownstreamConnections();

    // It is possible that we will have cycles in this graph,
    // so if there is a cycle, only update a node once.
    while(!nodesToUpdate.isEmpty()) {
      Set<AbstractNode> touchedNodes = new HashSet();

      for(AbstractNode node : nodesToUpdate) {
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

  /**
   * Represents a node and socket pairing
   */
  @AutoValue
  public static abstract class NodeAndSocket {
    public abstract AbstractNode getNode();
    public abstract Socket getSocket();
    public static NodeAndSocket create(AbstractNode node, Socket socket) {
      if(socket.getSocketType() == Socket.SocketType.INPUT) {
        Preconditions.checkArgument(
                node.getInputSockets().values().contains(socket),
                "Node %s expected to contain input socket %s, but contains %s",
                node, socket, node.getInputSockets());
      } else if(socket.getSocketType() == Socket.SocketType.OUTPUT) {
        Preconditions.checkArgument(
                node.getOutputSockets().values().contains(socket),
                "Node %s expected to contain output socket %s, but contains %s",
                node, socket, node.getOutputSockets());
      } else {
        throw new IllegalArgumentException("Unrecognized socket type: "+socket);
      }

      return new AutoValue_NodeGraph_NodeAndSocket(node, socket);
    }
  }
}
