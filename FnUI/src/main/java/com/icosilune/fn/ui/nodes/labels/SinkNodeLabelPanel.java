/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icosilune.fn.ui.nodes.labels;

import com.icosilune.fn.nodes.AbstractNode;
import com.icosilune.fn.nodes.SinkNode;
import com.icosilune.fn.ui.nodes.NodePanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author ashmore
 */
public class SinkNodeLabelPanel extends NodePanel {

  private final JLabel mainLabel;

  public SinkNodeLabelPanel(SinkNode node) {
    super(node);
    setBorder(new LineBorder(Color.DARK_GRAY, 2));
    add(mainLabel = new JLabel());
    mainLabel.setPreferredSize(new Dimension(100, 20));

    node.addListener(value -> {
      mainLabel.setText(String.valueOf(value));
      setSize(getPreferredSize());
    });
  }
}
