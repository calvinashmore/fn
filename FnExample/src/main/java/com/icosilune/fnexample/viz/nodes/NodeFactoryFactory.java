/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz.nodes;

import com.icosilune.fn.AbstractFn;
import com.icosilune.fn.FnType;
import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.nodes.ConstantNode;
import com.icosilune.fn.nodes.FnNode;
import com.icosilune.fn.nodes.NodeGraph;
import com.icosilune.fn.nodes.SinkNode;
import com.icosilune.fnexample.viz.nodes.labels.HorizontalSliderLabelPanel;
import com.icosilune.fnexample.viz.nodes.labels.SimpleNodeLabelPanel;
import com.icosilune.fnexample.viz.nodes.labels.SinkNodeLabelPanel;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author ashmore
 */
public class NodeFactoryFactory {

  private final NodeGraph nodeGraph;
  private final CircleMouseListener circleListener;

  public NodeFactoryFactory(NodeGraph nodeGraph, CircleMouseListener circleListener) {
    this.nodeGraph = nodeGraph;
    this.circleListener = circleListener;
  }

  public NodeFactory getFnFactory(Class<AbstractFn> fnType) {
    return new FnNodeFactory(fnType);
  }

  public NodePanel createPanelForNode(AbstractNode node) {
    if(node instanceof SinkNode) {
      return new SinkNodeLabelPanel((SinkNode) node);
    } else if(node instanceof ConstantNode
            && ((ConstantNode)node).getType().isAssignableFrom(FnType.fromString("double"))) {
      return new HorizontalSliderLabelPanel((ConstantNode) node, -10, 10);
    } else {
      return new SimpleNodeLabelPanel(node);
    }
  }

  private class FnNodeFactory extends NodeFactory {

    private final Constructor<? extends AbstractFn> constructor;

    public FnNodeFactory(Class<AbstractFn> fnType) {
      try {
        constructor = fnType.getConstructor();
      } catch (NoSuchMethodException | SecurityException ex) {
        throw new RuntimeException(ex);
      }
    }

    @Override
    public NodeFactory.NodeAndPanel create() {
      FnNode fnNode;
      try {
        fnNode = new FnNode(nodeGraph, constructor.newInstance());
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
        throw new RuntimeException(ex);
      }

      return NodeFactory.NodeAndPanel.create(fnNode, createPanelForNode(fnNode));
    }
  }
}
