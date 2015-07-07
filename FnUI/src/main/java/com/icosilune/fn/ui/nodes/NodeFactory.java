/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.ui.nodes;

import com.google.auto.value.AutoValue;
import com.icosilune.fn.nodes.AbstractNode;

/**
 *
 * @author ashmore
 */
public abstract class NodeFactory {

  public abstract NodeAndPanel create();

  @AutoValue
  public static abstract class NodeAndPanel {
    public abstract AbstractNode getNode();
    public abstract NodePanel getNodePanel();

    public static NodeAndPanel create(AbstractNode node, NodePanel nodePanel) {
      return new AutoValue_NodeFactory_NodeAndPanel(node, nodePanel);
    }
  }
}
