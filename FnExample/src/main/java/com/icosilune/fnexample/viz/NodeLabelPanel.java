/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz;

import com.icosilune.fn.nodes.AbstractNode;
import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author ashmore
 */
public class NodeLabelPanel extends JPanel {

  private final AbstractNode node;

  public NodeLabelPanel(AbstractNode node) {
    this.node = node;
    setBorder(new LineBorder(Color.DARK_GRAY, 2));
    add(new JLabel(node.getName()));
  }

  public AbstractNode getNode() {
    return node;
  }
}
