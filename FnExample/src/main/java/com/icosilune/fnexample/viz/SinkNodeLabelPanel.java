/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fnexample.viz;

import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.nodes.SinkNode;
import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author ashmore
 */
public class SinkNodeLabelPanel extends JPanel {

  private final SinkNode node;
  private final JLabel mainLabel;

  public SinkNodeLabelPanel(SinkNode node) {
    this.node = node;
    setBorder(new LineBorder(Color.DARK_GRAY, 2));
    add(mainLabel = new JLabel("        "));

    node.addListener(value -> mainLabel.setText(String.valueOf(value)));
  }
}
