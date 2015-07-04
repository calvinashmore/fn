/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz.nodes;

import com.icosilune.fn.nodes.AbstractNode;
import javax.swing.JPanel;

/**
 *
 * @author ashmore
 */
public abstract class NodePanel extends JPanel {

  private final AbstractNode node;

  public NodePanel(AbstractNode node) {
    this.node = node;
  }

  public AbstractNode getNode() {
    return node;
  }
}
