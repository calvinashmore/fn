/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.ui.nodes;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.icosilune.fn.AbstractFn;
import com.icosilune.fn.nodes.AbstractNode;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author ashmore
 */
public interface NodeFactory {

  NodeAndPanel createNode(NodeKey key);

  NodePanel createPanelForNode(AbstractNode node);

  ImmutableList<? extends NodeKey> getNodeKeys();


  @AutoValue
  public static abstract class NodeAndPanel {
    public abstract AbstractNode getNode();
    public abstract NodePanel getNodePanel();

    public static NodeAndPanel create(AbstractNode node, NodePanel nodePanel) {
      return new AutoValue_NodeFactory_NodeAndPanel(node, nodePanel);
    }
  }

  public interface NodeKey {}

  @AutoValue
  public static abstract class FnNodeKey implements NodeKey {
    public abstract AbstractFn getFn();
    public static FnNodeKey create(AbstractFn fn) {
      return new AutoValue_NodeFactory_FnNodeKey(fn);
    }

    @Override
    public String toString() {
      return getFn().getClass().getSimpleName();
    }

    public static List<FnNodeKey> fromInstances(Collection<AbstractFn> fns) {
      return fns.stream().map(FnNodeKey::create).collect(Collectors.toList());
    }
  }
}
