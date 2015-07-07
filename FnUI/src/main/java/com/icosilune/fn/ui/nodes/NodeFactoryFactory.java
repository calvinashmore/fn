/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.ui.nodes;

import com.icosilune.fn.AbstractFn;
import com.icosilune.fn.FnType;
import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.nodes.ConstantNode;
import com.icosilune.fn.nodes.FnNode;
import com.icosilune.fn.nodes.NodeGraph;
import com.icosilune.fn.nodes.SinkNode;
import com.icosilune.fn.ui.nodes.labels.HorizontalSliderLabelPanel;
import com.icosilune.fn.ui.nodes.labels.SimpleNodeLabelPanel;
import com.icosilune.fn.ui.nodes.labels.SinkNodeLabelPanel;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author ashmore
 */
public class NodeFactoryFactory {

  private final NodeGraph nodeGraph;

  public NodeFactoryFactory(NodeGraph nodeGraph) {
    this.nodeGraph = nodeGraph;
  }

  public NodeFactory getFnFactory(AbstractFn fn) {
    return new FnNodeFactory(fn);
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
    private final AbstractFn fn;
    public FnNodeFactory(AbstractFn fn) {
      this.fn = fn;
    }

    @Override
    public NodeAndPanel create() {
      FnNode fnNode  = new FnNode(nodeGraph, fn);
      return NodeFactory.NodeAndPanel.create(fnNode, createPanelForNode(fnNode));
    }
  }

//  private class FnNodeFactory extends NodeFactory {
//
//    private final Constructor<? extends AbstractFn> constructor;
//
//    public FnNodeFactory(Class<AbstractFn> fnType) {
//      try {
//        constructor = fnType.getConstructor();
//      } catch (NoSuchMethodException | SecurityException ex) {
//        throw new RuntimeException(ex);
//      }
//    }
//
//    @Override
//    public NodeFactory.NodeAndPanel create() {
//      FnNode fnNode;
//      try {
//        fnNode = new FnNode(nodeGraph, constructor.newInstance());
//      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
//        throw new RuntimeException(ex);
//      }
//
//      return NodeFactory.NodeAndPanel.create(fnNode, createPanelForNode(fnNode));
//    }
//  }
}
