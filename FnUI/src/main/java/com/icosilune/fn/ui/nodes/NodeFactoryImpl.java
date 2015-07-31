/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.ui.nodes;

import com.google.common.collect.ImmutableList;
import com.icosilune.fn.FnType;
import com.icosilune.fn.nodes.ConstantNode;
import com.icosilune.fn.nodes.FnNode;
import com.icosilune.fn.nodes.NodeGraph;
import com.icosilune.fn.nodes.SinkNode;
import com.icosilune.fn.ui.nodes.labels.HorizontalSliderNodePanel;
import com.icosilune.fn.ui.nodes.labels.SimpleNodePanel;
import com.icosilune.fn.ui.nodes.labels.StringNodePanel;

/**
 *
 * @author ashmore
 */
public class NodeFactoryImpl implements NodeFactory {

  private final NodeGraph nodeGraph;
  private final ImmutableList<? extends NodeFactoryImpl.NodeKey> nodeKeys;

  public NodeFactoryImpl(NodeGraph nodeGraph, Iterable<? extends NodeFactoryImpl.NodeKey> nodeKeys) {
    this.nodeGraph = nodeGraph;
    this.nodeKeys = ImmutableList.copyOf(nodeKeys);
  }

  @Override
  public ImmutableList<? extends NodeKey> getNodeKeys() {
    return nodeKeys;
  }

  /**
   * This should be overridden by subclasses to make custom node types.
   */
  @Override
  public NodeAndPanel createNode(NodeKey key) {
    if(key instanceof FnNodeKey) {
      FnNodeKey fnKey = (FnNodeKey) key;
      FnNode fnNode  = new FnNode(nodeGraph, fnKey.getFn());
      return NodeAndPanel.create(fnNode, new SimpleNodePanel(fnNode));
    } else if(key instanceof ConstantNodeKey) {
      ConstantNodeKey constantKey = (ConstantNodeKey) key;
      ConstantNode node = new ConstantNode(nodeGraph, constantKey.getType(), constantKey.getInitialValue());
      NodePanel panel;
      if(node.getType().isAssignableFrom(FnType.fromString("double"))) {
        panel = new HorizontalSliderNodePanel((ConstantNode) node, -10, 10);
      } else {
        panel = new SimpleNodePanel(node);
      }
      return NodeAndPanel.create(node, panel);
    } else if(key instanceof SinkNodeKey) {
      SinkNodeKey sinkNodeKey = (SinkNodeKey) key;
      SinkNode node = new SinkNode(nodeGraph, sinkNodeKey.getType());
      return NodeAndPanel.create(node, new StringNodePanel(node));
    } else {
      throw new IllegalArgumentException("Unsupported node key: "+key);
    }
  }
}
