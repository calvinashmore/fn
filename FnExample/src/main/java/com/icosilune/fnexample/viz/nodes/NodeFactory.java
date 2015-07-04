/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz.nodes;

import com.google.auto.value.AutoValue;
import com.icosilune.fn.AbstractFn;
import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.nodes.FnNode;
import com.icosilune.fn.nodes.NodeGraph;
import com.icosilune.fnexample.viz.nodes.AutoValue_NodeFactory_NodeAndPanel;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ashmore
 */
public abstract class NodeFactory {

  public abstract NodeAndPanel create();
//  public abstract NodePanel createPanelForNode();

  @AutoValue
  public static abstract class NodeAndPanel {
    public abstract AbstractNode getNode();
    public abstract NodePanel getNodePanel();

    public static NodeAndPanel create(AbstractNode node, NodePanel nodePanel) {
      return new AutoValue_NodeFactory_NodeAndPanel(node, nodePanel);
    }
  }
}
