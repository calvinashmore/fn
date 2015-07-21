/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.ui.nodes;

import com.google.common.collect.ImmutableList;
import com.icosilune.fn.FnType;
import com.icosilune.fn.nodes.AbstractNode;
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

  @Override
  public NodeAndPanel createNode(NodeKey key) {
    if(key instanceof FnNodeKey) {
      FnNodeKey fnKey = (FnNodeKey) key;
      FnNode fnNode  = new FnNode(nodeGraph, fnKey.getFn());
      return NodeAndPanel.create(fnNode, createPanelForNode(fnNode));
    } else {
      throw new IllegalArgumentException("Unsupported node key: "+key);
    }
  }

  @Override
  public NodePanel createPanelForNode(AbstractNode node) {
    if(node instanceof SinkNode) {
      return new StringNodePanel((SinkNode) node);
    } else if(node instanceof ConstantNode
            && ((ConstantNode)node).getType().isAssignableFrom(FnType.fromString("double"))) {
      return new HorizontalSliderNodePanel((ConstantNode) node, -10, 10);
    } else {
      return new SimpleNodePanel(node);
    }
  }
}
