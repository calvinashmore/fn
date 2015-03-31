/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz;

import com.icosilune.fn.nodes.AbstractNode;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Visual element for a node
 */
public class NodePanel extends JPanel {

  private final AbstractNode node;

  public NodePanel(AbstractNode node) {
    this.node = node;

    //setPreferredSize(new Dimension(100, 100));
    setBackground(Color.LIGHT_GRAY);

    add(new JLabel(node.getName()));

    DragHandler dragHandler = new DragHandler(this);
    addMouseListener(dragHandler);
    addMouseMotionListener(dragHandler);
  }

}
